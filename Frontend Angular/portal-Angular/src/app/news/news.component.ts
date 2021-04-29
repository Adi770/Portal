import { DomSanitizer } from '@angular/platform-browser';
import { Rating } from './../InterfacesPortal';
import { HttpServicePortal } from './../http.service.portal';
import { HttpServiceUsers } from './../http.service.users';
import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { News } from '../InterfacesPortal';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent implements OnInit {
  newsData: any;
  commentData: any;
  imagePath: string;
  serverIP: string;
  newUrlL: string;
  User: boolean;
  id: number;
  rateNews: number;



  Rating: any[] = [{ value: 1 }, { value: 2 }, { value: 3 }, { value: 4 }, { value: 5 }];

  constructor(private httpClient: HttpClient, private activatedRoute: ActivatedRoute,
    private httpServiceUsers: HttpServiceUsers, private httpServicePortal: HttpServicePortal, private sanitizer: DomSanitizer) {
    this.User = httpServiceUsers.isUserLoggenIn();
    this.activatedRoute.params.subscribe(res => {
      this.id = res.id;
    });
  }

  ngOnInit(): void {
    this.refreshData();

  }

  refreshData(): void {
    this.newsOne().subscribe(data => { this.newsData = data; });
  }

  newsOne(): Observable<News> {
    return this.httpServicePortal.newsOne(this.id);
  }

  imageServer(imageUrl: string) {
      return this.sanitizer.bypassSecurityTrustUrl('data:image/jpg;base64,'+imageUrl);

  }

  addComment(): void {
    if (this.commentData == null) {
      console.log('error');
    } else {
      const data = {
        text: this.commentData,
        userID: 1 // TODO get user ID
      };
      this.commentPost(data);
      this.commentData = null;
    }
  }

  commentPost(data: object): void {
    this.httpServicePortal.commentPost(this.id, data).toPromise().then(res => {
      this.refreshData();
    });

  }

  rateArticle(): void {

    this.httpServicePortal.rateArticle(this.id, this.rateNews).toPromise().then(res => {
      console.log('ocenino');
    });
  }

  dataPass(data: string) {
    return this.sanitizer.bypassSecurityTrustHtml(data);
  }

}
