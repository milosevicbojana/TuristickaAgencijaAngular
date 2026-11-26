import { CommonModule } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { KategorijaService } from '../core/services/kategorija-service';
import { KategorijaResponseDTO } from '../shared/models/kategorija';

@Component({
  selector: 'app-admin-kategorija-list',
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-kategorija-list.html',
  styleUrl: './admin-kategorija-list.css',
})
export class AdminKategorijaList implements OnInit {

  kategorije: KategorijaResponseDTO[] = [];
  novaKategorija: string = '';
  izmenaId: number | null = null;
  izmenaNaziv: string = '';
  greska: string = '';

  constructor(
    private kategorijaService: KategorijaService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
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

  dodajKategoriju(forma: NgForm): void {
    if (forma.invalid) return;

    this.greska = '';
    this.kategorijaService.dodajKategoriju(this.novaKategorija).subscribe({
      next: () => {
        this.novaKategorija = '';
        forma.resetForm();
        this.ucitajKategorije();
      },
      error: (err) => {
        this.greska = 'Greška prilikom dodavanja kategorije.';
        this.cdr.detectChanges();
        console.error(err);
      },
    });
  }

  pocniIzmenu(kategorija: KategorijaResponseDTO): void {
    this.izmenaId = kategorija.id;
    this.izmenaNaziv = kategorija.naziv;
  }

  otkaziIzmenu(): void {
    this.izmenaId = null;
    this.izmenaNaziv = '';
  }

  sacuvajIzmenu(id: number): void {
    this.kategorijaService.izmeniKategoriju(id, this.izmenaNaziv).subscribe({
      next: () => {
        this.izmenaId = null;
        this.ucitajKategorije();
      },
      error: (err) => {
        this.greska = 'Greška prilikom izmene kategorije.';
        this.cdr.detectChanges();
        console.error(err);
      },
    });
  }

  obrisiKategoriju(kategorija: KategorijaResponseDTO): void {
    const potvrda = confirm(`Da li sigurno želiš da obrišeš kategoriju "${kategorija.naziv}"?`);
    if (!potvrda) return;

    this.kategorijaService.obrisiKategoriju(kategorija.id).subscribe({
      next: () => {
        this.kategorije = this.kategorije.filter(k => k.id !== kategorija.id);
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.greska = err.error || 'Ne možeš obrisati kategoriju koja ima aranžmane.';
        this.cdr.detectChanges();
        console.error(err);
      },
    });
  }
}