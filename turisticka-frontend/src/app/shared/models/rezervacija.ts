export interface RezervacijaResponseDTO {
  id: number;
  brojOsoba: number;
  datumRezervacije: string;
  status: string;
  ukupnaCena: number;
  aranzmanId: number;
  aranzmanNaziv: string;
  korisnikId: number;
  korisnickoIme: string;
}

export interface RezervacijaRequestDTO {
  brojMesta: number;
}