package com.example.demo.controller;

import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.demo.DTO.RezervacijaRequestDTO;
import com.example.demo.DTO.RezervacijaResponseDTO;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.AranzmanService;
import com.example.demo.service.RezervacijaService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import model.Aranzman;
import model.Korisnik;
import model.Rezervacija;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
@RequestMapping("/api/rezervacije")
public class RezervacijaController {

	@Autowired
	private RezervacijaService rezervacijaService;

	@Autowired
	private AranzmanService aranzmanService;

	@GetMapping("/moje")
	public List<RezervacijaResponseDTO> mojeRezervacije(Authentication authentication) {
		Korisnik korisnik = trenutniKorisnik(authentication);
		return rezervacijaService.getRezervacijeZaKorisnika(korisnik.getId()).stream()
				.map(RezervacijaResponseDTO::new).toList();
	}

	@PostMapping("/aranzman/{aranzmanId}")
	public ResponseEntity<?> rezervisi(@PathVariable Long aranzmanId, @Valid @RequestBody RezervacijaRequestDTO dto,
			Authentication authentication) {

		Korisnik korisnik = trenutniKorisnik(authentication);
		Aranzman aranzman = aranzmanService.pronadjiPoId(aranzmanId);

		if (aranzman == null || aranzman.getSlobodnaMesta() < dto.getBrojMesta()) {
			return ResponseEntity.badRequest().body("Nema dovoljno slobodnih mesta.");
		}

		rezervacijaService.napraviRezervaciju(korisnik, aranzman, dto.getBrojMesta());
		aranzmanService.sacuvaj(aranzman);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> otkaziRezervaciju(@PathVariable Long id, Authentication authentication) {
		Korisnik korisnik = trenutniKorisnik(authentication);

		Rezervacija rezervacija = rezervacijaService.pronadjiPoId(id);
		if (rezervacija == null || rezervacija.getKorisnik().getId() != korisnik.getId()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Nemate pristup ovoj rezervaciji.");
		}

		Aranzman aranzman = rezervacija.getAranzman();
		aranzman.setSlobodnaMesta(aranzman.getSlobodnaMesta() + rezervacija.getBrojOsoba());
		aranzmanService.sacuvaj(aranzman);

		rezervacijaService.obrisiRezervaciju(id);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}/izvestaj")
	public void generisiIzvestaj(@PathVariable Long id, HttpServletResponse response) {
		try {
			JasperPrint jasperReport = rezervacijaService.kreirajIzvestajZaRezervaciju(id);
			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition", "attachment; filename=Rezervacija_Korisnika_" + id + ".pdf");
			OutputStream out = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperReport, out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Korisnik trenutniKorisnik(Authentication authentication) {
		return ((CustomUserDetails) authentication.getPrincipal()).getKorisnik();
	}
}
