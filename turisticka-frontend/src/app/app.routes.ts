import { Routes } from '@angular/router';
import { AranzmanList } from './aranzman-list/aranzman-list';
import { LoginComponent } from './auth/login-component/login-component';
import { RegisterComponent } from './auth/register-component/register-component';
import { AranzmanDetails } from './aranzman-details/aranzman-details';
import { MojeRezervacije } from './moje-rezervacije/moje-rezervacije';
import { adminGuard, authGuard } from './core/guards/auth-guard';
import { AdminKategorijaList } from './admin-kategorija-list/admin-kategorija-list';
import { AranzmanAdmin } from './aranzman-admin/aranzman-admin';
import { AdminRezervacijeList } from './admin-rezervacije-list/admin-rezervacije-list';
import { Statistika } from './statistika/statistika';
import { PageNotFound } from './shared/page-not-found/page-not-found';

export const routes: Routes = [
    { path: '', component: AranzmanList },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'aranzman/:id', component: AranzmanDetails },
    { path: 'moje-rezervacije', component: MojeRezervacije, canActivate: [authGuard] },
    { path: 'admin/kategorije', component: AdminKategorijaList, canActivate: [adminGuard] },
    { path: 'admin/aranzman/novi', component: AranzmanAdmin, canActivate: [adminGuard] },
    { path: 'admin/aranzman/:id/izmeni', component: AranzmanAdmin, canActivate: [adminGuard] },
    { path: 'admin/rezervacije', component: AdminRezervacijeList, canActivate: [adminGuard] },
    { path: 'admin/statistika', component: Statistika, canActivate: [adminGuard] },
    { path: '**', component: PageNotFound },
];
