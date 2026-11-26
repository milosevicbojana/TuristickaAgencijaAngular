package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.DTO.KorisnikDTO;
import com.example.demo.DTO.LoginRequestDTO;
import com.example.demo.DTO.LoginResponseDTO;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.KorisnikService;

import jakarta.validation.Valid;
import model.Korisnik;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getKorisnickoIme(), loginRequest.getLozinka()));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pogrešno korisničko ime ili lozinka.");
		}

		Korisnik korisnik = korisnikService.pronadjiPoKorisnickomImenu(loginRequest.getKorisnickoIme());
		String uloga = korisnik.getUloga().getNaziv();
		String token = jwtUtil.generisiToken(korisnik.getKorisnickoIme(), korisnik.getId(), uloga);

		return ResponseEntity.ok(new LoginResponseDTO(token, korisnik.getId(), korisnik.getKorisnickoIme(), uloga));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody KorisnikDTO korisnikDTO) {

		if (korisnikService.postojiKorisnickoIme(korisnikDTO.getKorisnickoIme())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Korisničko ime već postoji!");
		}
		if (korisnikService.postojiEmail(korisnikDTO.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email je već korišćen!");
		}

		Korisnik korisnik = new Korisnik();
		korisnik.setKorisnickoIme(korisnikDTO.getKorisnickoIme());
		korisnik.setEmail(korisnikDTO.getEmail());
		korisnik.setLozinka(passwordEncoder.encode(korisnikDTO.getLozinka()));
		korisnik.setUloga(korisnikDTO.getUloga());

		korisnikService.sacuvajKorisnika(korisnik);

		return ResponseEntity.status(HttpStatus.CREATED).body("Uspešno ste registrovani! Možete se prijaviti.");
	}
}
