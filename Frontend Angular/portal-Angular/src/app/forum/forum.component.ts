import { HttpServiceForum } from './../http.service.forum';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ForumSection, ForumSubSections } from 'src/app/InterfacesPortal';

@Component({
  selector: 'app-forum',
  templateUrl: './forum.component.html',
  styleUrls: ['./forum.component.css']
})

export class ForumComponent implements OnInit {
  data: any;
  lastTopicData: any;

  constructor(private httpServiceForum: HttpServiceForum) {
  }

  ngOnInit(): void {
    this.forumGenerate().subscribe(information => {
      this.data = information;
    });
    this.forumLastTopic().subscribe(topic => {
      this.lastTopicData = topic;
      console.log(topic)
    });
  }

  forumGenerate() {
    return this.httpServiceForum.getMainForum();
  }
  forumLastTopic() {
    return this.httpServiceForum.getLastTopic();
  }

}
