import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { KategorijaResponseDTO } from '../../shared/models/kategorija';

@Injectable({
  providedIn: 'root',
})
export class KategorijaService {
  private apiUrl = '/api/kategorije';

  constructor(private http: HttpClient) {}

  getKategorije(): Observable<KategorijaResponseDTO[]> {
    return this.http.get<KategorijaResponseDTO[]>(this.apiUrl);
  }

  dodajKategoriju(naziv: string): Observable<KategorijaResponseDTO> {
    return this.http.post<KategorijaResponseDTO>(this.apiUrl, { naziv });
  }

  izmeniKategoriju(id: number, naziv: string): Observable<KategorijaResponseDTO> {
    return this.http.put<KategorijaResponseDTO>(`${this.apiUrl}/${id}`, { naziv });
  }

  obrisiKategoriju(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}