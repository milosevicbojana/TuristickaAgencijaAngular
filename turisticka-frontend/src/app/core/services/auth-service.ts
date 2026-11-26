import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { jwtDecode } from 'jwt-decode';
import { LoginRequestDTO, LoginResponseDTO, KorisnikDTO } from '../../shared/models/auth';

interface JwtPayload {
  sub: string;
  id: number;
  uloga: string;
  exp: number;
}

@Injectable({
  providedIn: 'root',
})

export class AuthService {
  private apiUrl = '/api/auth';
  private readonly TOKEN_KEY = 'token';

  constructor(private http: HttpClient) {}

  login(podaci: LoginRequestDTO): Observable<LoginResponseDTO> {
    return this.http.post<LoginResponseDTO>(`${this.apiUrl}/login`, podaci).pipe(
      tap((odgovor) => {
        localStorage.setItem(this.TOKEN_KEY, odgovor.token);
      })
    );
  }

  register(podaci: KorisnikDTO): Observable<string> {
    return this.http.post(`${this.apiUrl}/register`, podaci, { responseType: 'text' });
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    if (!token) return false;
    try {
      const decoded = jwtDecode<JwtPayload>(token);
      return decoded.exp * 1000 > Date.now();
    } catch {
      return false;
    }
  }

    isAdmin(): boolean {
    if (!this.isLoggedIn()) return false;
    const token = this.getToken();
    try {
      return jwtDecode<JwtPayload>(token!).uloga === 'ADMIN';
    } catch {
      return false;
    }
  }

  getKorisnickoIme(): string {
    const token = this.getToken();
    if (!token) return '';
    try {
      return jwtDecode<JwtPayload>(token).sub;
    } catch {
      return '';
    }
  }
}