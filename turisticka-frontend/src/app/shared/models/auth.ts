export interface LoginRequestDTO {
  korisnickoIme: string;
  lozinka: string;
}

export interface LoginResponseDTO {
  token: string;
  id: number;
  korisnickoIme: string;
  uloga: string; // "USER" ili "ADMIN"
}

export interface KorisnikDTO {
  korisnickoIme: string;
  email: string;
  lozinka: string;
  uloga: { id: number };
}

export interface Uloga {
  id: number;
  naziv: string;
}