import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface StatistikaResponse {
  rezervacijePoAranzmanu: Array<[string, number]>;
  prihodiPoAranzmanu: Array<[string, number]>;
}

@Injectable({
  providedIn: 'root',
})
export class StatistikaService {
  private apiUrl = '/api/admin/statistika';

  constructor(private http: HttpClient) {}

  getStatistika(): Observable<StatistikaResponse> {
    return this.http.get<StatistikaResponse>(this.apiUrl);
  }
}