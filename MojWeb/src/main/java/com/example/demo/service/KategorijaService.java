package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.example.demo.repository.KategorijaRepository;

import model.Kategorija;

@Service
public class KategorijaService {

    @Autowired
    private KategorijaRepository kategorijaRepository;

    public List<Kategorija> getKategorije(){
    	return kategorijaRepository.findAll();
    }
    
    public Kategorija pronadjiPoId(long id) {
        return kategorijaRepository.findById(id).orElse(null);
    }
    
    public void sacuvaj(Kategorija kategorija) {
        kategorijaRepository.save(kategorija);
    }

    public boolean obrisi(long id) {
        Kategorija k = kategorijaRepository.findById(id).orElse(null);
        if (k == null) return false;

        if (k.getAranzmans() != null && !k.getAranzmans().isEmpty()) {
            return false;
        }

        kategorijaRepository.deleteById(id);
        return true;
    }
}