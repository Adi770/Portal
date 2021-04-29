import { HttpServiceUsers } from './../../http.service.users';
import { HttpServiceForum } from './../../http.service.forum';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-topic',
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.css']
})
export class TopicComponent implements OnInit {

  dataTopic: any;
  name: string;
  username: string;
  postData: any;
  id: any;
  show: boolean;
  editObject: any;
  isLogin: boolean;
  role: string;

  constructor(private httpServiceForum: HttpServiceForum, private activatedRoute: ActivatedRoute
    ,         private httpServiceUsers: HttpServiceUsers) {
    this.activatedRoute.params.subscribe(res => {
      this.name = res.name;
      this.id = res.id;
      console.log(res);
      this.username = sessionStorage.getItem('User');
      console.log(this.username);
    });
    this.isLogin = httpServiceUsers.isUserLoggenIn();
    this.show = true;
  }


  ngOnInit(): void {
    this.generateTopic().subscribe(data => { this.dataTopic = data; });
    this.httpServiceUsers.getUserRole().subscribe(res => {
      this.role = res.role;
    });
    this.refreshData();
  }

  refreshData(): void {
    this.generateTopic().subscribe(res => {
      this.dataTopic = res;
    });
  }

  generateTopic() {
    return this.httpServiceForum.getTopic(this.id);
  }

  addPost(): void {
    console.log(this.postData);
    const data = {
      text: this.postData,
      userId: 1
    };
    console.log(data);
    this.httpServiceForum.addPost(this.id, data).toPromise().then(res => {
      this.refreshData();
    });
    this.postData = null;
  }

  editPost(commentId: number): void {
    this.httpServiceForum.getPost(commentId).toPromise().then(res => {
      this.editObject = {
        text: res.text,
        id: commentId
      };
      this.postData = this.editObject.text;
      console.log(res);
    });
    this.show = false;
  }

  updatePost(): void {
    const data = {
      text: this.postData
    };
    this.httpServiceForum.updatePost(this.editObject.id, data).toPromise().then(res => {
      console.log(res);
      this.refreshData();
    });
    this.show = true;
    this.postData = null;
  }

  deletePost(commentId: number): void {
    this.httpServiceForum.deletePost(commentId).toPromise().then(res => { this.refreshData(); });
  }

}
