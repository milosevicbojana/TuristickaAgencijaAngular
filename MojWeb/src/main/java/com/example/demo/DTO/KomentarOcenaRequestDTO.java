package com.example.demo.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class KomentarOcenaRequestDTO {

	@NotBlank(message = "Komentar ne sme biti prazan.")
	private String sadrzaj;

	@Min(value = 1, message = "Ocena mora biti između 1 i 5.")
	@Max(value = 5, message = "Ocena mora biti između 1 i 5.")
	private int vrednost;

	public String getSadrzaj() {
		return sadrzaj;
	}

	public void setSadrzaj(String sadrzaj) {
		this.sadrzaj = sadrzaj;
	}

	public int getVrednost() {
		return vrednost;
	}

	public void setVrednost(int vrednost) {
		this.vrednost = vrednost;
	}
}
