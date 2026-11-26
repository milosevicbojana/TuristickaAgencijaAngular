package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.KorisnikRepository;
import com.example.demo.repository.UlogaRepository;

import model.Korisnik;
import model.Uloga;

import java.util.List;

@Service
public class KorisnikService {

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private UlogaRepository ulogaRepository;

    public List<Uloga> getSveUloge() {
        return ulogaRepository.findAll();
    }

    public boolean postojiKorisnickoIme(String korisnickoIme) {
        return korisnikRepository.findByKorisnickoIme(korisnickoIme) != null;
    }
    
    public boolean postojiEmail(String email) {
        return korisnikRepository.findByEmail(email) != null;
    }

    public void sacuvajKorisnika(Korisnik korisnik) {
        korisnikRepository.save(korisnik);
    }

    public Korisnik pronadjiPoKorisnickomImenu(String korisnickoIme) {
        return korisnikRepository.findByKorisnickoIme(korisnickoIme);
    }
}
