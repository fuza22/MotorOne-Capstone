import { NgModule } from '@angular/core';
import { RouterModule, Routes} from '@angular/router';
import { WelcomeComponent } from './Pages/homepage/welcome/welcome.component';
import { NoauthGuard } from './Pages/auth/noauth.guard';
import { AuthGuard } from './Pages/auth/auth.guard';
import { NotFoundComponent } from './Components/not-found/not-found.component';

const routes: Routes = [
{ path: '', pathMatch: "full", redirectTo: "/welcome"},
{ path: 'welcome', component: WelcomeComponent, title: 'Welcome - MotorOne'},
{ path: 'homepage', loadChildren: () => import('./Pages/homepage/homepage.module').then(m => m.HomepageModule), title: 'Homepage - MotorOne' },
{ path: 'auth', loadChildren: () => import('./Pages/auth/auth.module').then(m => m.AuthModule), canActivate: [NoauthGuard], canActivateChild: [NoauthGuard] },
{ path: 'profile/:id', loadChildren: () => import('./Pages/profile/profile.module').then(m => m.ProfileModule), canActivate: [AuthGuard], canActivateChild: [AuthGuard], title: 'Profile - MotorOne' },
{ path: 'drivers', loadChildren: () => import('./Pages/drivers/drivers.module').then(m => m.DriversModule) },
{ path: 'circuits', loadChildren: () => import('./Pages/circuits/circuits.module').then(m => m.CircuitsModule) },
{ path: '404', component: NotFoundComponent, title: 'Page not found - MotorOne' },
{ path: '**', redirectTo: '/404' }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
