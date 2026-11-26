export interface KomentarResponseDTO {
  id: number;
  sadrzaj: string;
  datumKreiranja: string;
  korisnickoIme: string;
}

export interface OcenaResponseDTO {
  id: number;
  vrednost: number;
  korisnickoIme: string;
  komentarId: number | null;
}

export interface KomentarOcenaRequestDTO {
  sadrzaj: string;
  vrednost: number;
}