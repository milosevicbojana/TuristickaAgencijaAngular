package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repository.KorisnikRepository;

import model.Korisnik;


@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	KorisnikRepository korisnikRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Korisnik u = korisnikRepository.findByKorisnickoIme(username);
		if (u == null) {
			throw new UsernameNotFoundException("Korisnik '" + username + "' ne postoji.");
		}
		return new CustomUserDetails(u);
	}
}
