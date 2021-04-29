import { NavbarService } from './navbar.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  isShown: boolean = false;



  constructor(nav: NavbarService) {
    nav.show();
  }



}
