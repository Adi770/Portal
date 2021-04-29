import { Observable } from 'rxjs';
import { HttpServicePortal } from './../http.service.portal';
import { Component, OnInit } from '@angular/core';
import { News } from '../InterfacesPortal';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  serverIP: string;
  imagePath: string;
  newUrlL: string;
  newsData: any[];
  isDataAvailable: boolean = false;


  constructor(private httpServicePortal: HttpServicePortal, private domSantinizer: DomSanitizer) { }

  ngOnInit(): void {
    this.newsList().subscribe(res => {
      this.newsData = res;
      this.isDataAvailable = true;

    });
  }

  SafetyCheck(fn: any) {
    try {
      console.log('wtf' + fn + this.newsData[fn].id);
      return fn;
    }
    catch (e) {
      console.log(fn)
    }
  }

  newsList(): Observable<Array<News>> {
    return this.httpServicePortal.last10News();
  }

  imageServer(imageUrl: string) {
    console.log(imageUrl);
    return this.domSantinizer.bypassSecurityTrustUrl('data:image/jpg;base64,'+imageUrl);
  }



}
