import { Router, ActivatedRoute } from '@angular/router';
import { HttpServiceForum } from '../../http.service.forum';
import { Component, OnInit } from '@angular/core';
import { query } from '@angular/animations';

@Component({
  selector: 'app-create-topic',
  templateUrl: './create-topic.component.html',
  styleUrls: ['./create-topic.component.css']
})

export class CreateTopicComponent implements OnInit {

  textTopic: string;
  textTitle: string;
  nameSection: string
  idSection: number;

  constructor(private httpServiceForum: HttpServiceForum, private router: Router, private activatedRoute: ActivatedRoute) {
    this.nameSection = this.activatedRoute.snapshot.params['subsection']
    this.idSection = this.activatedRoute.snapshot.queryParams['id']
    console.log(this.idSection)
  }

  ngOnInit() {

  }

  save() {
    let data: any = {
      titleTopic: this.textTitle,
      textTopic: this.textTopic,
      subSectionId: this.idSection
    };
    console.log(data)
    return this.httpServiceForum.createTopic(data).toPromise().then(res => {
      this.router.navigate(['/forum/' + this.nameSection], { queryParams: { id: this.idSection } });
    });
  }
}
