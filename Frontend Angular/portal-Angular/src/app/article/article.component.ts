import { Observable } from 'rxjs';
import { HttpServicePortal } from './../http.service.portal';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { analyzeAndValidateNgModules } from '@angular/compiler';
import * as ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import '@ckeditor/ckeditor5-build-classic/build/translations/pl';


@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.css']
})
export class ArticleComponent implements OnInit {


  public Editor = ClassicEditor;


  constructor(private httpClient: HttpClient, private router: Router,
    private httpServicePortal: HttpServicePortal, private activatedRoute: ActivatedRoute) {
    const snap = this.activatedRoute.snapshot;
    this.id = snap.params.id;
    console.log('id atcicle ' + this.id);
  }

  public config = {
    language: 'pl'
};

  id: number;
  textTitle: string;
  textArticle: string;
  nameImage: string;
  imageSrc: string;
  file: File;

  ngOnInit(): void {
    this.textArticle = '';
    if (this.id != null) {
      this.httpServicePortal.newsOne(this.id).toPromise().then(res => {
        this.textArticle = res.article;
        this.textTitle = res.title;
        this.imageSrc = this.imageServer(res.image);
      });
    }
  }

  onUpload(event): void {

    this.file = event.target.files[0];
    const reader = new FileReader();
    if (event.target.files && event.target.files.length) {
      const [file] = event.target.files;
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.imageSrc = reader.result as string;
        this.nameImage = this.file.name;
      };
    }
  }


  imageServer(imageUrl: string): string {
    const serverIP = 'http://127.0.0.1:8887/';
    const imagePath = 'C:\\Users\\Adi\\IdeaProjects\\portal\\src\\main\\resources\\image';
    if (imageUrl == null) {
      return 'assets/icons8-image-50.png';
    }
    return imageUrl.replace(imagePath, serverIP);
  }

  upload(): void {

    const data = new FormData();
    const news = {
      title: this.textTitle,
      article: this.textArticle
    };
    data.append('news', JSON.stringify(news));
    if (this.id != null) {
      this.httpServicePortal.updateNews(this.id, news).subscribe(res => {
        console.log(res);
        this.router.navigate(['/news/' + this.id]);
      });
    } else if (this.id === undefined) {
      data.append('image', this.file, this.file.name);
      this.httpServicePortal.uploadNews(data).subscribe(res => {
        console.log(res);
        this.router.navigate(['/news']);
      });
    }

  }
}

