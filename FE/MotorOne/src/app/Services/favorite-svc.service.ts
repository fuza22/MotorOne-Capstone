import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { IFavorite } from '../Models/i-favorite';

@Injectable({
  providedIn: 'root'
})
export class FavoriteSvcService {

  constructor(
    private http:HttpClient,
  ) { }


  getFavorite(userId: number): Observable<IFavorite> {
    return this.http.get<IFavorite>(`${environment.backendUrl}/user/${userId}/favorite`);
  }

  addToFavorite(userId: number, driverId: number): Observable<void> {
    return this.http.post<void>(`${environment.backendUrl}/user/${userId}/favorite/drivers/${driverId}`, null);
  }

  removeFromFavorite(userId: number, driverId: number): Observable<void> {
    return this.http.delete<void>(`${environment.backendUrl}/user/${userId}/favorite/drivers/${driverId}`);
  }

}

