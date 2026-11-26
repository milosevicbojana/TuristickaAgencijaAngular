package com.example.demo.controller;

import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.DTO.RezervacijaResponseDTO;
import com.example.demo.DTO.StatusRequestDTO;
import com.example.demo.service.AranzmanService;
import com.example.demo.service.RezervacijaService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import model.Aranzman;
import model.Rezervacija;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
@RequestMapping("/api/admin/rezervacije")
public class AdminRezervacijaController {

	@Autowired
	private RezervacijaService rezervacijaService;

	@Autowired
	private AranzmanService aranzmanService;

	@GetMapping
	public List<RezervacijaResponseDTO> sveRezervacije(@RequestParam(required = false) Long aranzmanId) {
		List<Rezervacija> rezervacije;
		if (aranzmanId != null) {
			rezervacije = rezervacijaService.getRezervacijeZaAranzman(aranzmanId);
		} else {
			rezervacije = rezervacijaService.getSveRezervacije();
		}
		return rezervacije.stream().map(RezervacijaResponseDTO::new).toList();
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<?> promeniStatusRezervacije(@PathVariable Long id, @Valid @RequestBody StatusRequestDTO dto) {
		rezervacijaService.azurirajStatus(id, dto.getStatus());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}/otkazi")
	public ResponseEntity<?> otkaziRezervacijuAdmin(@PathVariable Long id) {
		Rezervacija rezervacija = rezervacijaService.pronadjiPoId(id);
		if (rezervacija == null) {
			return ResponseEntity.notFound().build();
		}

		Aranzman aranzman = rezervacija.getAranzman();
		aranzman.setSlobodnaMesta(aranzman.getSlobodnaMesta() + rezervacija.getBrojOsoba());
		aranzmanService.sacuvaj(aranzman);

		rezervacijaService.obrisiRezervaciju(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> obrisiRezervaciju(@PathVariable Long id) {
		rezervacijaService.obrisiRezervaciju(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/izvestaj/aranzman/{id}")
	public void generisiIzvestaj(@PathVariable Long id, HttpServletResponse response) {
		try {
			JasperPrint jasperReport = rezervacijaService.kreirajIzvestajZaAranzman(id);
			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition", "attachment; filename=Rezervacije_Aranzman_" + id + ".pdf");
			OutputStream out = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperReport, out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
