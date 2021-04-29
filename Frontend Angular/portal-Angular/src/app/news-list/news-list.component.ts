import { HttpServicePortal } from './../http.service.portal';
import { element } from 'protractor';
import { Router } from '@angular/router';
import { from, Observable } from 'rxjs';
import { News, Comments, Rating, NewsDTO } from './../InterfacesPortal';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { MatPaginatorModule } from '@angular/material/paginator';



@Component({
  selector: 'app-news-list',
  templateUrl: './news-list.component.html',
  styleUrls: ['./news-list.component.css']
})
export class NewsListComponent implements OnInit {

  constructor(private httpClient: HttpClient, router: Router, private httpServicePortal: HttpServicePortal, private domSanitizer: DomSanitizer) { }

  newsTheBestData: any;
  newsData: any;
  imagePath: string;
  serverIP: string;
  newUrlL: string;
  sum: number;

  page = 1;
  pageSize = 10;

  items = 100;


  ngOnInit(): void {

    this.returnNumberOfItems().subscribe(data => {

      this.items = parseInt(data.toString());

    })

    this.newsList(1, this.pageSize).subscribe(data => {
      console.log(data)
      this.newsData = data;
    });
    this.theBestNews().subscribe(data => {
      this.newsTheBestData = data;
    });
  }

  returnNumberOfItems() {
    return this.httpServicePortal.newsItems();
  }

  newsList(page, size): Observable<Array<News>> {
    return this.httpServicePortal.newsList(page, size);
  }

  imageServer(imageUrl: string) {
    console.log(imageUrl)
    return this.domSanitizer.bypassSecurityTrustUrl('data:image/jpg;base64,'+imageUrl);
  }

  // TODO last 5 comments from News
  theBestNews(): Observable<Array<NewsDTO>> {
    return this.httpServicePortal.theBestNews(5);
  }

  getPageFromService(page) {
    this.newsList(page, this.pageSize).subscribe(data => {
      this.newsData = data;
    });

  }

}
