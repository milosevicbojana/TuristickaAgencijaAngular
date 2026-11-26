package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import model.Komentar;
import model.Aranzman;

public interface KomentarRepository extends JpaRepository<Komentar, Long> {
    List<Komentar> findByAranzman(Aranzman aranzman);
}
