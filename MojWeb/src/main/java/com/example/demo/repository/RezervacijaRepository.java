package com.example.demo.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.Rezervacija;

@Repository
public interface RezervacijaRepository extends JpaRepository<Rezervacija, Long> {
	List<Rezervacija> findByKorisnikId(Long korisnikId);
	
	@Query("SELECT r FROM Rezervacija r WHERE r.aranzman.id = :aranzmanId ORDER BY r.status DESC")
	List<Rezervacija> findByAranzmanId(Long aranzmanId);

	@Query("SELECT r.aranzman.naziv, COUNT(r.id) " + 
			"FROM Rezervacija r " + 
			"WHERE r.datumRezervacije >= :datumOd " + 
			"GROUP BY r.aranzman.naziv " + 
			"ORDER BY COUNT(r.id) DESC")
	List<Object[]> findBrojRezervacijaPoslednjihMesecDana(@Param("datumOd") Timestamp datumOd);

	@Query("SELECT r.aranzman.naziv, SUM(r.ukupnaCena) " + 
			"FROM Rezervacija r " + 
			"WHERE r.status = 'ODOBRENO' " + 
			"GROUP BY r.aranzman.naziv " + 
			"ORDER BY SUM(r.ukupnaCena) DESC")
	List<Object[]> findPrihodiPoAranzmanu();
}