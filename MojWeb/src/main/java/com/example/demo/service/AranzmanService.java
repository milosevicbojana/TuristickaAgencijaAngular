package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.converter.MyConverter;
import com.example.demo.repository.AranzmanRepository;

import model.Aranzman;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AranzmanService {

	@Autowired
	private AranzmanRepository aranzmanRepository;
	
	@Autowired
	private MyConverter myConverter;

	public List<Aranzman> getSviAranzmani() {
		return aranzmanRepository.findAll();
	}

	public List<Aranzman> filtrirajAranzmane(String naziv, Long kategorijaId, String datumOd, String datumDo, BigDecimal cenaOd, BigDecimal cenaDo) {

		List<Aranzman> svi = getSviAranzmani();

		return svi.stream()
				.filter(a -> naziv == null || naziv.isEmpty() || a.getNaziv().toLowerCase().contains(naziv.toLowerCase()))
				.filter(a -> kategorijaId == null || a.getKategorija().getId() == kategorijaId)
				.filter(a -> datumOd == null || datumOd.isEmpty() || !a.getDatumPocetka().before(myConverter.convert(datumOd)))
				.filter(a -> datumDo == null || datumDo.isEmpty() || !a.getDatumZavrsetka().after(myConverter.convert(datumDo)))
				.filter(a -> cenaOd == null || a.getCena().compareTo(cenaOd) >= 0)
				.filter(a -> cenaDo == null || a.getCena().compareTo(cenaDo) <= 0).toList();
	}

	public void sacuvaj(Aranzman aranzman) {
		aranzmanRepository.save(aranzman);
	}

	public void obrisiPoId(Long id) {
		aranzmanRepository.deleteById(id);
	}

	public Aranzman pronadjiPoId(Long id) {
		Optional<Aranzman> aranzman = aranzmanRepository.findById(id);
		return aranzman.orElse(null);
	}
}
