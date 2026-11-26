import { Component, ChangeDetectorRef } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth-service';
import { LoginRequestDTO } from '../../shared/models/auth';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login-component.html',
  styleUrl: './login-component.css',
})
export class LoginComponent {

  podaci: LoginRequestDTO = {
    korisnickoIme: '',
    lozinka: '',
  };

  greska: string = '';

  constructor(
    private authService: AuthService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  submit(forma: NgForm): void {
    if (forma.invalid) {
      return;
    }

    this.greska = '';

    this.authService.login(this.podaci).subscribe({
      next: () => {
        this.router.navigate(['/']);
      },
      error: (err) => {
        if (err.status === 401) {
          this.greska = 'Pogrešno korisničko ime ili lozinka.';
        } else {
          this.greska = 'Došlo je do greške. Pokušajte ponovo.';
        }
        this.cdr.detectChanges();
        console.error(err);
      },
    });
  }
}