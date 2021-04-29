import { HttpServiceForum } from './../http.service.forum';
import { Router } from '@angular/router';
import { HttpServicePortal } from './../http.service.portal';
import { Role, News, Comments, Users, UsersListDTO, ForumTopic } from './../InterfacesPortal';
import { HttpServiceUsers } from './../http.service.users';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { EmailValidator } from '@angular/forms';


@Component({
  selector: 'app-panel-user',
  templateUrl: './panel-user.component.html',
  styleUrls: ['./panel-user.component.css']
})
export class PanelUserComponent implements OnInit {


  constructor(private httpServiceForum: HttpServiceForum, private httpServiceUsers: HttpServiceUsers,
    private httpServicePortal: HttpServicePortal, private router: Router) { }

  news: News[];
  comments: Comments[];
  role: string;
  newPassword: string;
  newPassword2: string;
  newEmail: string;
  newEmail2: string;
  selectValue: number;
  selectValue2: number;
  userName: any;
  userRole: any;
  showEdit: boolean;
  textComment: string;
  topicList: any;
  commentList: any;
  textTopic: string;

  textTopicComm: string;
  password: string;

  showEditTopicComment = false;
  showEditTopic = false;

  ngOnInit(): void {
    this.httpServiceUsers.getUserRole().subscribe(res => {
      this.role = res.role;
      console.log(res.role);
    });
    this.newsList().subscribe(res => { this.news = res; });
    this.CommentsList().subscribe(res => {
      this.comments = res;
    });
    this.userNameList().toPromise().then(res => {
      this.userName = res;
    });
    this.userRoleList().toPromise().then(res => {
      this.userRole = res;
    });
    this.gettopicList().toPromise().then(res => {
      this.topicList = res;
    });
  }

  userNameList() {
    return this.httpServiceUsers.userList();
  }

  userRoleList() {
    return this.httpServiceUsers.roleList();
  }

  CommentsList() {
    return this.httpServicePortal.commentsList();
  }

  newsList(): Observable<Array<News>> {
    return this.httpServicePortal.newsList(1,10);
  }

  changePassword() {
    if (this.newPassword === this.newPassword2) {
      return this.httpServiceUsers.changePassword(this.newPassword).toPromise().then(res => { console.log('Metoda dział'); });
    }
    else { return console.log('Bład'); }
  }

  changeEmail() {
    if (this.newEmail === this.newEmail2) {
      return this.httpServiceUsers.changeEmail(this.newEmail).toPromise().then(res => { console.log('Metoda vdział'); });
    }
    else { return console.log('Bład'); }
  }

  changeRole() {
    if (this.selectValue && this.selectValue2 != null) {
      console.log('wchodzi');
      return this.httpServiceUsers.changeRole(this.selectValue, this.selectValue2).toPromise().then(() => {
        console.log('zmienia');
        this.ngOnInit();
      });
    }
  }

  deleteNews() {
    console.log(this.selectValue + 'coś');
    if (this.selectValue != null) {
      return this.httpServicePortal.deleteNews(this.selectValue).toPromise().then(res => {
        console.log('usnieto');
        this.ngOnInit();
      });
    }
  }

  deleteComments() {
    console.log(this.selectValue + 'coś');
    if (this.selectValue != null) {
      return this.httpServicePortal.deleteComm(this.selectValue).toPromise().then(res => {
        console.log('usnieto');
        this.ngOnInit();
      });
    }
  }

  editNews() {
    this.router.navigate(['/article/' + this.selectValue]);
  }

  editComments() {
    console.log(this.selectValue);
    if (this.selectValue !== undefined) {
      this.showEdit = true;
      this.httpServicePortal.loadComment(this.selectValue).toPromise().then(res => {
        this.textComment = res.text;
        console.log(res);
      });
    }
    else {
      console.log('Select comments');
      this.showEdit = false;
    }
  }

  saveComments() {
    const data = {
      text: this.textComment
    };
    this.httpServicePortal.saveEditComments(this.selectValue, data).toPromise().then();
  }

  gettopicList() {
    return this.httpServiceForum.getAllTopic();
  }

  getTopic() {
    return this.httpServiceForum.getTopic(this.selectValue).subscribe(res => {
      this.commentList = res;
    });
  }

  deleteTopic() {
    return this.httpServiceForum.deletTopic(this.selectValue).toPromise().then(res => {
      console.log('succes');
      this.ngOnInit();
    });
  }

  editTopic() {
    this.showEditTopic = true;
    this.textTopic = this.commentList.titleTopic;

  }

  saveTopic() {
    const data = {
      titleTopic: this.textTopic,
    };
    this.httpServiceForum.updateTopic(this.commentList.id, data).subscribe(res => {
      console.log('Update');
      this.ngOnInit();
    });
    this.showEditTopic = false;
  }

  editTopicComm() {
    this.showEditTopicComment = true;
    this.httpServiceForum.getPost(this.selectValue2).subscribe(res => {
      this.textTopicComm = res.text;
      console.log(this.commentList.forumTopicCommList);
    });
  }

  deleteTopicComm() {
    return this.httpServiceForum.deletePost(this.selectValue2).toPromise().then(res => { console.log('Delete '); });
  }

  saveTopicComm() {
    const data = {
      text: this.textTopicComm,
    };
    this.httpServiceForum.updatePost(this.selectValue2, data).subscribe(res => {
      console.log('Update');
      this.getTopic();
    });
    this.showEditTopicComment = false;
  }

}




