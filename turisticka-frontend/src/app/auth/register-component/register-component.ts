import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth-service';
import { UlogaService } from '../../core/services/uloga-service';
import { KorisnikDTO, Uloga } from '../../shared/models/auth';

@Component({
  selector: 'app-register',
  imports: [FormsModule],
  templateUrl: './register-component.html',
  styleUrl: './register-component.css',
})
export class RegisterComponent implements OnInit {

  podaci: KorisnikDTO = {
    korisnickoIme: '',
    email: '',
    lozinka: '',
    uloga: { id: 0 },
  };

  uloge: Uloga[] = [];
  greska: string = '';
  poruka: string = '';

  constructor(
    private authService: AuthService,
    private ulogaService: UlogaService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.ulogaService.getUloge().subscribe({
      next: (data) => {
        this.uloge = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Greška pri učitavanju uloga:', err),
    });
  }

  submit(forma: NgForm): void {
    if (forma.invalid) {
      return;
    }

    this.greska = '';
    this.poruka = '';

    this.authService.register(this.podaci).subscribe({
      next: () => {
        this.poruka = 'Uspešno ste registrovani! Preusmeravanje na prijavu...';
        this.cdr.detectChanges();
        setTimeout(() => this.router.navigate(['/login']), 1500);
      },
      error: (err) => {
        this.greska = err.error || 'Greška prilikom registracije.';
        this.cdr.detectChanges();
        console.error(err);
      },
    });
  }
}