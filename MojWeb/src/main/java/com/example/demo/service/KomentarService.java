package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Komentar;
import model.Aranzman;
import com.example.demo.repository.KomentarRepository;

@Service
public class KomentarService {

    @Autowired
    private KomentarRepository komentarRepository;

    public void sacuvaj(Komentar komentar) {
        komentarRepository.save(komentar);
    }

    public List<Komentar> getKomentariZaAranzman(Aranzman aranzman) {
        return komentarRepository.findByAranzman(aranzman);
    }

    public Komentar pronadjiPoId(Long id) {
        return komentarRepository.findById(id).orElse(null);
    }

    public void obrisi(Long id) {
        komentarRepository.deleteById(id);
    }
}
