import { CommonModule } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { AranzmanResponseDTO } from '../shared/models/aranzman';
import { AranzmanService } from '../core/services/aranzman-service';
import { AuthService } from '../core/services/auth-service';
import { AranzmanCard } from '../aranzman-card/aranzman-card';
import { KategorijaResponseDTO } from '../shared/models/kategorija';
import { KategorijaService } from '../core/services/kategorija-service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-aranzman-list',
  imports: [CommonModule, FormsModule, AranzmanCard],
  templateUrl: './aranzman-list.html',
  styleUrl: './aranzman-list.css',
})
export class AranzmanList implements OnInit {

  aranzmani: AranzmanResponseDTO[] = [];
  kategorije: KategorijaResponseDTO[] = [];

  naziv: string = '';
  kategorijaId: number = 0;
  cenaOd: number | null = null;
  cenaDo: number | null = null;
  datumOd: string = '';
  datumDo: string = '';

  constructor(
    private aranzmanService: AranzmanService,
    private kategorijaService: KategorijaService,
    public authService: AuthService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.ucitajAranzmane();
    this.ucitajKategorije();
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

  ucitajAranzmane(): void {
    this.aranzmanService.getAranzmani().subscribe({
      next: (data) => {
        this.aranzmani = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Greška pri učitavanju aranžmana: ', err);
      }
    });
  }

  pretrazi(): void {
    this.aranzmanService.getAranzmani({
      naziv: this.naziv || undefined,
      kategorijaId: this.kategorijaId || undefined,
      datumOd: this.datumOd || undefined,
      datumDo: this.datumDo || undefined,
      cenaOd: this.cenaOd ?? undefined,
      cenaDo: this.cenaDo ?? undefined,
    }).subscribe({
      next: (data) => {
        this.aranzmani = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Greška pri pretrazi:', err),
    });
  }

  resetFilter(): void {
    this.naziv = '';
    this.kategorijaId = 0;
    this.cenaOd = null;
    this.cenaDo = null;
    this.datumOd = '';
    this.datumDo = '';
    this.ucitajAranzmane();
  }

  obrisiAranzman(a: AranzmanResponseDTO): void {
    const potvrda = confirm(`Da li sigurno želiš da obrišeš "${a.naziv}"?`);
    if (!potvrda) return;

    this.aranzmanService.obrisiAranzman(a.id).subscribe({
      next: () => {
        this.aranzmani = this.aranzmani.filter(x => x.id !== a.id);
        this.cdr.detectChanges();
      },
      error: (err) => {
        alert('Greška prilikom brisanja. Možda ima aktivnih rezervacija.');
        console.error(err);
      },
    });
  }
}