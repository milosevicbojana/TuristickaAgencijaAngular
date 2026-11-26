import { CommonModule, CurrencyPipe, DatePipe } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { RezervacijaService } from '../core/services/rezervacija-service';
import { RezervacijaResponseDTO } from '../shared/models/rezervacija';

@Component({
  selector: 'app-moje-rezervacije',
  imports: [CommonModule, CurrencyPipe, DatePipe],
  templateUrl: './moje-rezervacije.html',
  styleUrl: './moje-rezervacije.css',
})
export class MojeRezervacije implements OnInit {

  rezervacije: RezervacijaResponseDTO[] = [];
  greska: string = '';

  constructor(
    private rezervacijaService: RezervacijaService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.ucitajRezervacije();
  }

  ucitajRezervacije(): void {
    this.rezervacijaService.getMojeRezervacije().subscribe({
      next: (data) => {
        this.rezervacije = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Greška pri učitavanju rezervacija:', err);
      },
    });
  }

  otkaziRezervaciju(rezervacija: RezervacijaResponseDTO): void {
    const potvrda = confirm(`Da li sigurno želite da otkažete rezervaciju za "${rezervacija.aranzmanNaziv}"?`);
    if (!potvrda) return;

    this.rezervacijaService.otkazi(rezervacija.id).subscribe({
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