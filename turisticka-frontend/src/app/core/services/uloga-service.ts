import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Uloga } from '../../shared/models/auth';

@Injectable({
  providedIn: 'root',
})
export class UlogaService {
  private apiUrl = '/api/uloge';

  constructor(private http: HttpClient) {}

  getUloge(): Observable<Uloga[]> {
    return this.http.get<Uloga[]>(this.apiUrl);
  }
}