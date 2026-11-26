import { CommonModule, CurrencyPipe, DatePipe } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { RezervacijaService } from '../core/services/rezervacija-service';
import { RezervacijaResponseDTO } from '../shared/models/rezervacija';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-rezervacije-list',
  imports: [CommonModule, CurrencyPipe, DatePipe, FormsModule],
  templateUrl: './admin-rezervacije-list.html',
  styleUrl: './admin-rezervacije-list.css',
})
export class AdminRezervacijeList implements OnInit {

  rezervacije: RezervacijaResponseDTO[] = [];
  greska: string = '';

  readonly statusOptions = ['NA CEKANJU', 'ODOBRENO', 'ODBIJENO'];

  constructor(
    private rezervacijaService: RezervacijaService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.ucitajRezervacije();
  }

  ucitajRezervacije(): void {
    this.rezervacijaService.getSveRezervacije().subscribe({
      next: (data) => {
        this.rezervacije = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Greška pri učitavanju rezervacija:', err);
      },
    });
  }

  promeniStatus(rezervacija: RezervacijaResponseDTO, noviStatus: string): void {
    this.rezervacijaService.promeniStatus(rezervacija.id, noviStatus).subscribe({
      next: () => {
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.greska = 'Greška prilikom promene statusa.';
        this.cdr.detectChanges();
        console.error(err);
      },
    });
  }

  otkaziRezervaciju(rezervacija: RezervacijaResponseDTO): void {
    const potvrda = confirm(`Da li sigurno želiš da otkažeš rezervaciju za "${rezervacija.aranzmanNaziv}" (korisnik: ${rezervacija.korisnickoIme})?`);
    if (!potvrda) return;

    this.rezervacijaService.otkaziAdmin(rezervacija.id).subscribe({
      next: () => {
        this.rezervacije = this.rezervacije.filter(r => r.id !== rezervacija.id);
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.greska = 'Greška prilikom otkazivanja rezervacije.';
        this.cdr.detectChanges();
        console.error(err);
      },
    });
  }
}