package com.example.demo.controller;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.demo.DTO.KomentarOcenaRequestDTO;
import com.example.demo.DTO.KomentarResponseDTO;
import com.example.demo.DTO.OcenaResponseDTO;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.AranzmanService;
import com.example.demo.service.KomentarService;
import com.example.demo.service.OcenaService;

import jakarta.validation.Valid;
import model.Aranzman;
import model.Komentar;
import model.Korisnik;
import model.Ocena;

@RestController
@RequestMapping("/api/aranzmani/{aranzmanId}")
public class KomentarController {

	@Autowired
	private AranzmanService aranzmanService;

	@Autowired
	private KomentarService komentarService;

	@Autowired
	private OcenaService ocenaService;

	@GetMapping("/komentari")
	public List<KomentarResponseDTO> komentariZaAranzman(@PathVariable Long aranzmanId) {
		Aranzman aranzman = aranzmanService.pronadjiPoId(aranzmanId);
		if (aranzman == null) {
			return List.of();
		}
		return komentarService.getKomentariZaAranzman(aranzman).stream().map(KomentarResponseDTO::new).toList();
	}

	@GetMapping("/ocene")
	public List<OcenaResponseDTO> oceneZaAranzman(@PathVariable Long aranzmanId) {
		Aranzman aranzman = aranzmanService.pronadjiPoId(aranzmanId);
		if (aranzman == null) {
			return List.of();
		}
		return ocenaService.getOceneZaAranzman(aranzman).stream().map(OcenaResponseDTO::new).toList();
	}

	@PostMapping("/komentari")
	public ResponseEntity<?> dodajKomentarOcenu(@PathVariable Long aranzmanId,
												@Valid @RequestBody KomentarOcenaRequestDTO dto, Authentication authentication) {

		Korisnik korisnik = ((CustomUserDetails) authentication.getPrincipal()).getKorisnik();

		Aranzman aranzman = aranzmanService.pronadjiPoId(aranzmanId);
		if (aranzman == null) {
			return ResponseEntity.notFound().build();
		}

		Komentar komentar = new Komentar();
		komentar.setSadrzaj(dto.getSadrzaj());
		komentar.setKorisnik(korisnik);
		komentar.setAranzman(aranzman);
		komentar.setDatumKreiranja(Timestamp.from(Instant.now()));
		komentarService.sacuvaj(komentar);

		Ocena ocena = new Ocena();
		ocena.setVrednost(dto.getVrednost());
		ocena.setKorisnik(korisnik);
		ocena.setAranzman(aranzman);
		ocena.setKomentar(komentar);
		ocenaService.sacuvaj(ocena);

		return ResponseEntity.status(HttpStatus.CREATED).body(new KomentarResponseDTO(komentar));
	}

	@DeleteMapping("/komentari/{komentarId}")
	public ResponseEntity<?> obrisiKomentar(@PathVariable Long aranzmanId, @PathVariable Long komentarId,
											Authentication authentication) {

		Korisnik korisnik = ((CustomUserDetails) authentication.getPrincipal()).getKorisnik();
		Komentar komentar = komentarService.pronadjiPoId(komentarId);

		if (komentar == null) {
			return ResponseEntity.notFound().build();
		}

		boolean jeVlasnik = komentar.getKorisnik().getId() == korisnik.getId();
		boolean jeAdmin = korisnik.getUloga().getNaziv().equals("ADMIN");

		if (!jeVlasnik && !jeAdmin) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Možeš obrisati samo svoj komentar.");
		}

		ocenaService.obrisiZaKomentar(komentar);
		komentarService.obrisi(komentarId);

		return ResponseEntity.noContent().build();
	}
}
