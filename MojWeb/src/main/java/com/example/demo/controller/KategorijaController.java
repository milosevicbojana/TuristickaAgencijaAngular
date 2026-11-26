package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.DTO.KategorijaResponseDTO;
import com.example.demo.service.KategorijaService;

import model.Kategorija;

@RestController
@RequestMapping("/api/kategorije")
public class KategorijaController {

	@Autowired
	private KategorijaService kategorijaService;

	@GetMapping
	public List<KategorijaResponseDTO> sveKategorije() {
		return kategorijaService.getKategorije().stream().map(KategorijaResponseDTO::new).toList();
	}

	@PostMapping
	public ResponseEntity<KategorijaResponseDTO> sacuvajKategoriju(@RequestBody Kategorija kategorija) {
		kategorijaService.sacuvaj(kategorija);
		return ResponseEntity.status(HttpStatus.CREATED).body(new KategorijaResponseDTO(kategorija));
	}

	@PutMapping("/{id}")
	public ResponseEntity<KategorijaResponseDTO> izmeniKategoriju(@PathVariable long id, @RequestBody Kategorija kategorija) {
		Kategorija postojeca = kategorijaService.pronadjiPoId(id);
		if (postojeca == null) {
			return ResponseEntity.notFound().build();
		}
		postojeca.setNaziv(kategorija.getNaziv());
		kategorijaService.sacuvaj(postojeca);
		return ResponseEntity.ok(new KategorijaResponseDTO(postojeca));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> obrisiKategoriju(@PathVariable long id) {
		boolean obrisano = kategorijaService.obrisi(id);
		if (!obrisano) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Ne možete obrisati kategoriju koja ima aranžmane!");
		}
		return ResponseEntity.noContent().build();
	}
}
