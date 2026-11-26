package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.StatistikaService;

@RestController
@RequestMapping("/api/admin/statistika")
public class StatistikaController {

	@Autowired
	private StatistikaService statistikaService;

	@GetMapping
	public Map<String, List<Object[]>> statistika() {
		return Map.of(
				"rezervacijePoAranzmanu", statistikaService.brojRezervacijaPoAranzmanu(),
				"prihodiPoAranzmanu", statistikaService.prihodiPoAranzmanu());
	}
}
