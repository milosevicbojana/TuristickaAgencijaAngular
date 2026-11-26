import { CommonModule, CurrencyPipe } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { StatistikaService, StatistikaResponse } from '../core/services/statistika-service';

@Component({
  selector: 'app-statistika',
  imports: [CommonModule, CurrencyPipe],
  templateUrl: './statistika.html',
  styleUrl: './statistika.css',
})
export class Statistika implements OnInit {

  rezervacijePoAranzmanu: Array<[string, number]> = [];
  prihodiPoAranzmanu: Array<[string, number]> = [];

  constructor(
    private statistikaService: StatistikaService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.statistikaService.getStatistika().subscribe({
      next: (data: StatistikaResponse) => {
        this.rezervacijePoAranzmanu = data.rezervacijePoAranzmanu;
        this.prihodiPoAranzmanu = data.prihodiPoAranzmanu;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Greška pri učitavanju statistike:', err),
    });
  }

  maxVrednost(niz: Array<[string, number]>): number {
    if (niz.length === 0) return 1;
    return Math.max(...niz.map(stavka => stavka[1]));
  }

  procenat(vrednost: number, niz: Array<[string, number]>): number {
    const max = this.maxVrednost(niz);
    return max === 0 ? 0 : (vrednost / max) * 100;
  }
}