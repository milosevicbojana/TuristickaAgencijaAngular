package com.example.demo.DTO;

import model.Uloga;

public class UlogaResponseDTO {
    private long id;
    private String naziv;

    public UlogaResponseDTO() {
    }

    public UlogaResponseDTO(Uloga u) {
        this.id = u.getId();
        this.naziv = u.getNaziv();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}