import { CommonModule, CurrencyPipe, DatePipe } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AranzmanService } from '../core/services/aranzman-service';
import { KomentarService } from '../core/services/komentar-service';
import { AuthService } from '../core/services/auth-service';
import { AranzmanResponseDTO } from '../shared/models/aranzman';
import { KomentarResponseDTO, OcenaResponseDTO, KomentarOcenaRequestDTO } from '../shared/models/komentar-ocena';
import { RezervacijaService } from '../core/services/rezervacija-service';

@Component({
  selector: 'app-aranzman-details',
  imports: [CommonModule, FormsModule, CurrencyPipe, DatePipe],
  templateUrl: './aranzman-details.html',
  styleUrl: './aranzman-details.css',
})
export class AranzmanDetails implements OnInit {

  aranzman: AranzmanResponseDTO | null = null;
  komentari: KomentarResponseDTO[] = [];
  ocene: OcenaResponseDTO[] = [];

  noviKomentar: KomentarOcenaRequestDTO = {
    sadrzaj: '',
    vrednost: 5,
  };

  greska: string = '';
  poruka: string = '';
  
  brojMesta: number = 1;
  porukaRezervacija: string = '';
  greskaRezervacija: string = '';

  constructor(
    private route: ActivatedRoute,
    private aranzmanService: AranzmanService,
    private komentarService: KomentarService,
    public authService: AuthService,
    private cdr: ChangeDetectorRef,
    private rezervacijaService: RezervacijaService
  ) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.ucitajAranzman(id);
    this.ucitajKomentare(id);
    this.ucitajOcene(id);
  }

  ucitajAranzman(id: number): void {
    this.aranzmanService.getAranzman(id).subscribe({
      next: (data) => {
        this.aranzman = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Greška pri učitavanju aranžmana:', err),
    });
  }

  ucitajKomentare(id: number): void {
    this.komentarService.getKomentari(id).subscribe({
      next: (data) => {
        this.komentari = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Greška pri učitavanju komentara:', err),
    });
  }

  ucitajOcene(id: number): void {
    this.komentarService.getOcene(id).subscribe({
      next: (data) => {
        this.ocene = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Greška pri učitavanju ocena:', err),
    });
  }

  prosecnaOcena(): number {
    if (this.ocene.length === 0) return 0;
    const zbir = this.ocene.reduce((acc, o) => acc + o.vrednost, 0);
    return Math.round((zbir / this.ocene.length) * 10) / 10;
  }

  submitKomentar(forma: NgForm): void {
    if (forma.invalid || !this.aranzman) return;

    this.greska = '';
    this.poruka = '';

    this.komentarService.dodajKomentarOcenu(this.aranzman.id, this.noviKomentar).subscribe({
      next: () => {
        this.poruka = 'Komentar uspešno dodat!';
        this.noviKomentar = { sadrzaj: '', vrednost: 5 };
        forma.resetForm({ vrednost: 5 });
        this.ucitajKomentare(this.aranzman!.id);
        this.ucitajOcene(this.aranzman!.id);
      },
      error: (err) => {
        this.greska = 'Greška prilikom slanja komentara.';
        this.cdr.detectChanges();
        console.error(err);
      },
    });
  }

  pronadjiOcenu(komentarId: number): number | null {
    const ocena = this.ocene.find(o => o.komentarId === komentarId);
    return ocena ? ocena.vrednost : null;
  }

  rezervisi(): void {
    if (!this.aranzman) return;

    this.greskaRezervacija = '';
    this.porukaRezervacija = '';

    this.rezervacijaService.rezervisi(this.aranzman.id, { brojMesta: this.brojMesta }).subscribe({
      next: () => {
        this.porukaRezervacija = 'Uspešno ste rezervisali!';
        this.ucitajAranzman(this.aranzman!.id);
      },
      error: (err) => {
        this.greskaRezervacija = err.error || 'Greška prilikom rezervacije.';
        this.cdr.detectChanges();
        console.error(err);
      },
    });
  }

  ukupnaCena(): number {
    if (!this.aranzman) return 0;
    return this.aranzman.cena * this.brojMesta;
  }

  obrisiKomentar(komentar: KomentarResponseDTO): void {
    if (!this.aranzman) return;
    const potvrda = confirm('Da li sigurno želiš da obrišeš ovaj komentar?');
    if (!potvrda) return;

    this.komentarService.obrisiKomentar(this.aranzman.id, komentar.id).subscribe({
      next: () => {
        this.ucitajKomentare(this.aranzman!.id);
        this.ucitajOcene(this.aranzman!.id);
      },
      error: (err) => {
        alert('Greška prilikom brisanja komentara.');
        console.error(err);
      },
    });
  }
}