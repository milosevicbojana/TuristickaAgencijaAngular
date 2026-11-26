import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RezervacijaResponseDTO, RezervacijaRequestDTO } from '../../shared/models/rezervacija';

@Injectable({
  providedIn: 'root',
})
export class RezervacijaService {
  private apiUrl = '/api/rezervacije';
  private adminApiUrl = '/api/admin/rezervacije';

  constructor(private http: HttpClient) {}

  getMojeRezervacije(): Observable<RezervacijaResponseDTO[]> {
    return this.http.get<RezervacijaResponseDTO[]>(`${this.apiUrl}/moje`);
  }

  rezervisi(aranzmanId: number, podaci: RezervacijaRequestDTO): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/aranzman/${aranzmanId}`, podaci);
  }

  otkazi(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getSveRezervacije(): Observable<RezervacijaResponseDTO[]> {
    return this.http.get<RezervacijaResponseDTO[]>(this.adminApiUrl);
  }

  promeniStatus(id: number, status: string): Observable<void> {
    return this.http.put<void>(`${this.adminApiUrl}/${id}/status`, { status });
  }

  otkaziAdmin(id: number): Observable<void> {
    return this.http.delete<void>(`${this.adminApiUrl}/${id}/otkazi`);
  }
}