package com.example.demo.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.service.KategorijaService;

import model.Kategorija;

@Component
public class KategorijaConverter implements Converter<String, Kategorija> {

    @Autowired
    private KategorijaService kategorijaService;

    @Override
    public Kategorija convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        Long id = Long.parseLong(source);
        return kategorijaService.pronadjiPoId(id);
    }
}