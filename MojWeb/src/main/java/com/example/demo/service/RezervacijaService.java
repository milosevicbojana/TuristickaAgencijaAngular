package com.example.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.AranzmanRepository;
import com.example.demo.repository.RezervacijaRepository;
import model.Aranzman;
import model.Korisnik;
import model.Rezervacija;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class RezervacijaService {

    @Autowired
    private RezervacijaRepository rezervacijaRepository;
    
    @Autowired
    private AranzmanService aranzmanService;
    
    @Autowired
    private AranzmanRepository aranzmanRepository;

    public void napraviRezervaciju(Korisnik korisnik, Aranzman aranzman, int brojOsoba) {
        Rezervacija r = new Rezervacija();
        r.setKorisnik(korisnik);
        r.setAranzman(aranzman);
        r.setBrojOsoba(brojOsoba);
        r.setDatumRezervacije(Timestamp.from(Instant.now()));
        r.setStatus("NA CEKANJU");
        
        BigDecimal ukupnaCena = aranzman.getCena().multiply(BigDecimal.valueOf(brojOsoba));
        r.setUkupnaCena(ukupnaCena);
        
        aranzman.setSlobodnaMesta(aranzman.getSlobodnaMesta() - brojOsoba);

        rezervacijaRepository.save(r);
    }
    
    public List<Rezervacija> getRezervacijeZaKorisnika(Long korisnikId) {
        return rezervacijaRepository.findByKorisnikId(korisnikId);
    }

    public void obrisiRezervaciju(Long id) {
        rezervacijaRepository.deleteById(id);
    }
    
    public Rezervacija pronadjiPoId(Long id) {
        return rezervacijaRepository.findById(id).orElse(null);
    }
    
    public List<Rezervacija> getSveRezervacije() {
        return rezervacijaRepository.findAll();
    }

    public void azurirajStatus(Long id, String noviStatus) {
        Rezervacija r = rezervacijaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rezervacija nije pronađena"));

        String stariStatus = r.getStatus();
        Aranzman aranzman = r.getAranzman();

        boolean bioOdbijen = "ODBIJENO".equals(stariStatus);
        boolean postajeOdbijen = "ODBIJENO".equals(noviStatus);

        if (!bioOdbijen && postajeOdbijen) {
            aranzman.setSlobodnaMesta(aranzman.getSlobodnaMesta() + r.getBrojOsoba());
            aranzmanRepository.save(aranzman);
        } else if (bioOdbijen && !postajeOdbijen) {
            aranzman.setSlobodnaMesta(aranzman.getSlobodnaMesta() - r.getBrojOsoba());
            aranzmanRepository.save(aranzman);
        }

        r.setStatus(noviStatus);
        rezervacijaRepository.save(r);
    }
    
    public List<Rezervacija> getRezervacijeZaAranzman(Long aranzmanId) {
        return rezervacijaRepository.findByAranzmanId(aranzmanId);
    }
    
    public JasperPrint kreirajIzvestajZaAranzman(Long aranzmanId) throws JRException, IOException{
    	JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(getRezervacijeZaAranzman(aranzmanId));
    	InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/RezervacijeAranzmana.jrxml");
    	JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
    	Map<String, Object> params = new HashMap<String, Object>();
        Aranzman a = aranzmanService.pronadjiPoId(aranzmanId);
        params.put("nazivAranzmana", a.getNaziv());
        params.put("datumPocetka", a.getDatumPocetka());
        params.put("datumZavrsetka", a.getDatumZavrsetka());
    	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
    	inputStream.close();
    	
    	return jasperPrint;
    }
    
    public JasperPrint kreirajIzvestajZaRezervaciju(Long rezervacijaId) throws JRException, IOException{
    	Rezervacija rezervacija = pronadjiPoId(rezervacijaId);

    	JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(List.of(rezervacija));
    	InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/Rezervacija.jrxml");
    	JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
    	Aranzman a = rezervacija.getAranzman();
    	Map<String, Object> params = new HashMap<>();
    	params.put("nazivAranzmana", a.getNaziv());
    	params.put("datumPocetka", a.getDatumPocetka());
    	params.put("datumZavrsetka", a.getDatumZavrsetka());
    	params.put("korisnikIme", rezervacija.getKorisnik().getKorisnickoIme());
    	params.put("brojOsoba", rezervacija.getBrojOsoba());
    	params.put("ukupnaCena", rezervacija.getUkupnaCena());
    	params.put("opis", rezervacija.getAranzman().getOpis());
    	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
    	inputStream.close();
    	
    	return jasperPrint;
    }
}
