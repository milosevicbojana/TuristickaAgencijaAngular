import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { KomentarResponseDTO, OcenaResponseDTO, KomentarOcenaRequestDTO } from '../../shared/models/komentar-ocena';

@Injectable({
  providedIn: 'root',
})
export class KomentarService {
  private apiUrl = '/api/aranzmani';

  constructor(private http: HttpClient) {}

  getKomentari(aranzmanId: number): Observable<KomentarResponseDTO[]> {
    return this.http.get<KomentarResponseDTO[]>(`${this.apiUrl}/${aranzmanId}/komentari`);
  }

  getOcene(aranzmanId: number): Observable<OcenaResponseDTO[]> {
    return this.http.get<OcenaResponseDTO[]>(`${this.apiUrl}/${aranzmanId}/ocene`);
  }

  dodajKomentarOcenu(aranzmanId: number, podaci: KomentarOcenaRequestDTO): Observable<KomentarResponseDTO> {
    return this.http.post<KomentarResponseDTO>(`${this.apiUrl}/${aranzmanId}/komentari`, podaci);
  }

  obrisiKomentar(aranzmanId: number, komentarId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${aranzmanId}/komentari/${komentarId}`);
  }
}