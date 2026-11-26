package com.example.demo.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.RezervacijaRepository;

@Service
public class StatistikaService {

    @Autowired
    private RezervacijaRepository rezervacijaRepository;

    public List<Object[]> brojRezervacijaPoAranzmanu() {
        LocalDateTime preMesecDana = LocalDateTime.now().minusMonths(1);
        Timestamp datumOd = Timestamp.valueOf(preMesecDana);

        return rezervacijaRepository.findBrojRezervacijaPoslednjihMesecDana(datumOd);
    }

    public List<Object[]> prihodiPoAranzmanu() {
        return rezervacijaRepository.findPrihodiPoAranzmanu();
    }
}
