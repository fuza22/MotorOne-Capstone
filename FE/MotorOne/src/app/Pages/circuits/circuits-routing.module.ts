import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CircuitsComponent } from './circuits.component';
import { CircuitsDetailsComponent } from './circuits-details/circuits-details.component';

const routes: Routes = [{ path: '', component: CircuitsComponent },
{ path: 'search', component: CircuitsComponent, title: 'Circuits - MotorOne'},
{ path: 'details/:circuit_key', component: CircuitsDetailsComponent, title: 'Circuits - MotorOne' }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CircuitsRoutingModule { }
