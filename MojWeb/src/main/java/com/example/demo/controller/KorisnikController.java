package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.DTO.UlogaResponseDTO;
import com.example.demo.service.KorisnikService;

@RestController
@RequestMapping("/api")
public class KorisnikController {

	@Autowired
	private KorisnikService korisnikService;

	@GetMapping("/uloge")
	public List<UlogaResponseDTO> sveUloge() {
		return korisnikService.getSveUloge().stream().map(UlogaResponseDTO::new).toList();
	}
}