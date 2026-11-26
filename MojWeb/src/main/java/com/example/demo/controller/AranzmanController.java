package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.DTO.AranzmanDTO;
import com.example.demo.DTO.AranzmanResponseDTO;
import com.example.demo.service.AranzmanService;
import com.example.demo.service.KategorijaService;

import jakarta.validation.Valid;
import model.Aranzman;
import model.Kategorija;

@RestController
@RequestMapping("/api/aranzmani")
public class AranzmanController {

	@Autowired
	private AranzmanService aranzmanService;

	@Autowired
	private KategorijaService kategorijaService;

	@GetMapping
	public List<AranzmanResponseDTO> sviAranzmani(
			@RequestParam(required = false) String naziv,
			@RequestParam(required = false) Long kategorijaId,
			@RequestParam(required = false) String datumOd,
			@RequestParam(required = false) String datumDo,
			@RequestParam(required = false) BigDecimal cenaOd,
			@RequestParam(required = false) BigDecimal cenaDo) {

		List<Aranzman> aranzmani;
		boolean imaFilter = naziv != null || kategorijaId != null || datumOd != null || datumDo != null
				|| cenaOd != null || cenaDo != null;

		if (imaFilter) {
			aranzmani = aranzmanService.filtrirajAranzmane(naziv, kategorijaId, datumOd, datumDo, cenaOd, cenaDo);
		} else {
			aranzmani = aranzmanService.getSviAranzmani();
		}

		return aranzmani.stream().map(AranzmanResponseDTO::new).toList();
	}

	@GetMapping("/{id}")
	public ResponseEntity<AranzmanResponseDTO> jedanAranzman(@PathVariable Long id) {
		Aranzman aranzman = aranzmanService.pronadjiPoId(id);
		if (aranzman == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(new AranzmanResponseDTO(aranzman));
	}

	@PostMapping
	public ResponseEntity<?> kreirajAranzman(@Valid @RequestBody AranzmanDTO aranzmanDTO) {

		if (aranzmanDTO.getDatumPocetka() != null && aranzmanDTO.getDatumZavrsetka() != null
				&& aranzmanDTO.getDatumPocetka().after(aranzmanDTO.getDatumZavrsetka())) {
			return ResponseEntity.badRequest().body("Datum početka mora biti pre datuma završetka.");
		}

		Aranzman aranzman = new Aranzman();
		popuniAranzmanIzDTO(aranzman, aranzmanDTO);

		aranzmanService.sacuvaj(aranzman);
		return ResponseEntity.status(HttpStatus.CREATED).body(new AranzmanResponseDTO(aranzman));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> izmeniAranzman(@PathVariable Long id, @Valid @RequestBody AranzmanDTO aranzmanDTO) {
		Aranzman aranzman = aranzmanService.pronadjiPoId(id);
		if (aranzman == null) {
			return ResponseEntity.notFound().build();
		}

		popuniAranzmanIzDTO(aranzman, aranzmanDTO);
		aranzmanService.sacuvaj(aranzman);

		return ResponseEntity.ok(new AranzmanResponseDTO(aranzman));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> obrisiAranzman(@PathVariable Long id) {
		if (aranzmanService.pronadjiPoId(id) == null) {
			return ResponseEntity.notFound().build();
		}
		aranzmanService.obrisiPoId(id);
		return ResponseEntity.noContent().build();
	}

	private void popuniAranzmanIzDTO(Aranzman aranzman, AranzmanDTO dto) {
		aranzman.setNaziv(dto.getNaziv());
		aranzman.setOpis(dto.getOpis());
		aranzman.setCena(dto.getCena());
		aranzman.setSlobodnaMesta(dto.getSlobodnaMesta());
		aranzman.setDatumPocetka(dto.getDatumPocetka());
		aranzman.setDatumZavrsetka(dto.getDatumZavrsetka());

		if (dto.getKategorija() != null && dto.getKategorija().getId() > 0) {
			Kategorija kat = kategorijaService.pronadjiPoId(dto.getKategorija().getId());
			aranzman.setKategorija(kat);
		}
	}
}
