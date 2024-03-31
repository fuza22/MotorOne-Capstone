import { ApiF1Service } from './../../Services/api-f1.service';
import { IDriver } from './../../Models/i-driver';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-drivers',
  templateUrl: './drivers.component.html',
  styleUrl: './drivers.component.scss'
})
export class DriversComponent implements OnInit {
  drivers: IDriver[] = [];
  currentPage = 1;
  itemsPerPage = 12;

  constructor(private apiF1: ApiF1Service) { }

  ngOnInit(): void {
    this.loadDrivers();
  }

  loadDrivers(): void {
    this.apiF1.getAllDrivers().subscribe((res) => {
      this.drivers = res;
    });
  }

  paginate(drivers: IDriver[], page: number, itemsPerPage: number): IDriver[] {
    const startIndex = (page - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    return drivers.slice(startIndex, endIndex);
  }

  maxPages(drivers: IDriver[], itemsPerPage: number): number {
    return Math.ceil(drivers.length / itemsPerPage);
  }

  pagesArray(pages: number, currentPage: number): number[] {
    const totalPagesToShow = 10;
    let startPage = 1;
    let endPage = totalPagesToShow;

    if (currentPage > Math.floor(totalPagesToShow / 2)) {
      startPage = Math.max(currentPage - Math.floor(totalPagesToShow / 2), 1);
      endPage = Math.min(startPage + totalPagesToShow - 1, pages);
    }

    return Array.from({ length: endPage - startPage + 1 }, (_, index) => startPage + index);
  }

}
