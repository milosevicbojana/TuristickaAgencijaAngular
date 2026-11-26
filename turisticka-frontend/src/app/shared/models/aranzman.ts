export interface AranzmanResponseDTO {
  id: number;
  naziv: string;
  opis: string;
  cena: number;
  slobodnaMesta: number;
  datumPocetka: string;
  datumZavrsetka: string;
  kategorijaId: number;
  kategorijaNaziv: string;
}

export interface AranzmanFilter {
  naziv?: string;
  kategorijaId?: number;
  datumOd?: string;
  datumDo?: string;
  cenaOd?: number;
  cenaDo?: number;
}