package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import model.Ocena;
import model.Aranzman;
import model.Korisnik;
import model.Komentar;

public interface OcenaRepository extends JpaRepository<Ocena, Long> {
    List<Ocena> findByAranzman(Aranzman aranzman);
    Optional<Ocena> findByKomentar(Komentar komentar);
}