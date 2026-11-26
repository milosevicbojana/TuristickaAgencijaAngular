import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../core/services/auth-service';

@Component({
  selector: 'app-navbar',
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {

  constructor(
    public authService: AuthService,
    private router: Router
  ) { }

  odjaviSe(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}