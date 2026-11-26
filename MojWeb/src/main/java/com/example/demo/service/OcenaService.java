package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Ocena;
import model.Aranzman;
import model.Komentar;
import com.example.demo.repository.OcenaRepository;

@Service
public class OcenaService {

    @Autowired
    private OcenaRepository ocenaRepository;

    public void sacuvaj(Ocena ocena) {
        ocenaRepository.save(ocena);
    }

    public List<Ocena> getOceneZaAranzman(Aranzman aranzman) {
        return ocenaRepository.findByAranzman(aranzman);
    }

    public void obrisiZaKomentar(Komentar komentar) {
        ocenaRepository.findByKomentar(komentar).ifPresent(ocena -> ocenaRepository.delete(ocena));
    }
}