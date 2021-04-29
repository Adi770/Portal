import { LostPasswordComponent } from './../lost-password/lost-password.component'
import { MatDialog } from '@angular/material/dialog';
import { NavbarService } from '../../navbar.service';
import { NavbarComponent } from '../../navbar/navbar.component';
import { HttpServiceUsers } from '../../http.service.users';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  // tslint:disable-next-line: max-line-length
  constructor(private ns: NavbarService, private httpClient: HttpClient, private httpServiceUsers: HttpServiceUsers,
    private router: Router, private activatedRoute: ActivatedRoute, private dialog: MatDialog) { }


  password: string;
  login: string;
  email: string;

  ngOnInit(): void {
    if (this.httpServiceUsers.isUserLoggenIn() === true) {
      this.ns.show();
      this.router.navigate(['/news']);
    }
  }
  loginUser(): void {
    console.log(this.login, this.password);
    this.httpServiceUsers.authUser(this.login, this.password).subscribe(res => {
      console.log(this.httpServiceUsers.username);
      window.location.reload();
    });
  }

  lostPassword() {
    const dialogRef=this.dialog.open(LostPasswordComponent, {
      data: { email: this.email }
    });
    dialogRef.afterClosed().subscribe(res=>{
      console.log('The dialog was closed');
      console.log(res)
    })
  }

}
