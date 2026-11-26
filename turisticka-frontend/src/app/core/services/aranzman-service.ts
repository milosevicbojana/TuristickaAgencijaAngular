import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AranzmanFilter, AranzmanResponseDTO } from '../../shared/models/aranzman';

@Injectable({
  providedIn: 'root',
})
export class AranzmanService {

  private apiUrl = '/api/aranzmani';

  constructor(private http: HttpClient) {} 

  getAranzmani(filter?: AranzmanFilter): Observable<AranzmanResponseDTO[]> {
    let params = new HttpParams(); 

    if (filter) {
      if (filter.naziv) params = params.set('naziv', filter.naziv);
      if (filter.kategorijaId) params = params.set('kategorijaId', filter.kategorijaId);
      if (filter.datumOd) params = params.set('datumOd', filter.datumOd);
      if (filter.datumDo) params = params.set('datumDo', filter.datumDo);
      if (filter.cenaOd != null) params = params.set('cenaOd', filter.cenaOd);
      if (filter.cenaDo != null) params = params.set('cenaDo', filter.cenaDo);
    }

    return this.http.get<AranzmanResponseDTO[]>(this.apiUrl, { params });
  }

  getAranzman(id: number): Observable<AranzmanResponseDTO> {
    return this.http.get<AranzmanResponseDTO>(`${this.apiUrl}/${id}`);
  }

  dodajAranzman(aranzman: Partial<AranzmanResponseDTO>): Observable<AranzmanResponseDTO> {
    return this.http.post<AranzmanResponseDTO>(this.apiUrl, aranzman);
  }

  izmeniAranzman(id: number, aranzman: Partial<AranzmanResponseDTO>): Observable<AranzmanResponseDTO> {
    return this.http.put<AranzmanResponseDTO>(`${this.apiUrl}/${id}`, aranzman);
  }

  obrisiAranzman(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}