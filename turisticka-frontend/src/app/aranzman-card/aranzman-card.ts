import { CommonModule, CurrencyPipe, DatePipe } from '@angular/common';
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AranzmanResponseDTO } from '../shared/models/aranzman';

@Component({
  selector: 'app-aranzman-card',
  imports: [CommonModule, RouterModule, CurrencyPipe, DatePipe],
  templateUrl: './aranzman-card.html',
  styleUrl: './aranzman-card.css',
})
export class AranzmanCard {

  @Input() aranzman!: AranzmanResponseDTO;
  @Input() isAdmin: boolean = false;

  @Output() obrisi = new EventEmitter<AranzmanResponseDTO>();

  onObrisi(event: Event): void {
    event.stopPropagation();
    this.obrisi.emit(this.aranzman);
  }

  onIzmeniKlik(event: Event): void {
    event.stopPropagation();
  }
}