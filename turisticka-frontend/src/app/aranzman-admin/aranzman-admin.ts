import { CommonModule } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AranzmanService } from '../core/services/aranzman-service';
import { KategorijaService } from '../core/services/kategorija-service';
import { KategorijaResponseDTO } from '../shared/models/kategorija';

@Component({
  selector: 'app-aranzman-admin',
  imports: [CommonModule, FormsModule],
  templateUrl: './aranzman-admin.html',
  styleUrl: './aranzman-admin.css',
})
export class AranzmanAdmin implements OnInit {

  aranzmanId: number | null = null;
  jeIzmena: boolean = false;

  naziv: string = '';
  opis: string = '';
  cena: number = 0;
  slobodnaMesta: number = 0;
  datumPocetka: string = '';
  datumZavrsetka: string = '';
  kategorijaId: number = 0;
  
  danas: string = new Date().toISOString().substring(0, 10);

  kategorije: KategorijaResponseDTO[] = [];
  greska: string = '';
  poruka: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private aranzmanService: AranzmanService,
    private kategorijaService: KategorijaService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.ucitajKategorije();

    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.jeIzmena = true;
      this.aranzmanId = Number(idParam);
      this.ucitajAranzman(this.aranzmanId);
    }
  }

  ucitajKategorije(): void {
    this.kategorijaService.getKategorije().subscribe({
      next: (data) => {
        this.kategorije = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Greška pri učitavanju kategorija:', err),
    });
  }

  ucitajAranzman(id: number): void {
    this.aranzmanService.getAranzman(id).subscribe({
      next: (data) => {
        this.naziv = data.naziv;
        this.opis = data.opis;
        this.cena = data.cena;
        this.slobodnaMesta = data.slobodnaMesta;
        this.datumPocetka = data.datumPocetka.substring(0, 10);
        this.datumZavrsetka = data.datumZavrsetka.substring(0, 10);
        this.kategorijaId = data.kategorijaId;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Greška pri učitavanju aranžmana:', err),
    });
  }

  submitForma(forma: NgForm): void {
    if (forma.invalid) return;

    this.greska = '';
    this.poruka = '';

    const podaci = {
      naziv: this.naziv,
      opis: this.opis,
      cena: this.cena,
      slobodnaMesta: this.slobodnaMesta,
      datumPocetka: this.datumPocetka,
      datumZavrsetka: this.datumZavrsetka,
      kategorija: { id: this.kategorijaId },
    };

    const zahtev = this.jeIzmena
      ? this.aranzmanService.izmeniAranzman(this.aranzmanId!, podaci)
      : this.aranzmanService.dodajAranzman(podaci);

    zahtev.subscribe({
      next: () => {
        this.poruka = this.jeIzmena ? 'Aranžman uspešno izmenjen!' : 'Aranžman uspešno dodat!';
        this.cdr.detectChanges();
        setTimeout(() => this.router.navigate(['/']), 1000);
      },
      error: (err) => {
        this.greska = err.error || 'Greška prilikom čuvanja aranžmana.';
        this.cdr.detectChanges();
        console.error(err);
      },
    });
  }
}