import { NavbarService } from '../../navbar.service';
import { Users, UsersDTO } from '../../InterfacesPortal';
import { HttpServiceUsers } from '../../http.service.users';
import { Router } from '@angular/router';
import { LoginComponent } from '../login/login.component';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {



  login: string;
  email: string;
  password1: string;
  password2: string;

  constructor(private ns: NavbarService, private router: Router, private httpServiceUsers: HttpServiceUsers) { }

  ngOnInit(): void {
    if (this.httpServiceUsers.isUserLoggenIn() === true) {
      this.ns.show();
      this.router.navigate(['/news']);
    } else {

    }
  }

  register(): void {
    if (this.password1 === this.password2) {
      let data: UsersDTO = {
        username: this.login,
        email: this.email,
        password: this.password1
      };
      console.log(data);
      console.log(this.login);
      this.createNewUser(data);
    }
  }

  createNewUser(data: UsersDTO) {
    this.httpServiceUsers.createUser(data).toPromise().then(res => {
      console.log('utworzono user');
      this.httpServiceUsers.authUser(data.username, data.password).subscribe(res2 => {
        console.log('zalogowano jako' + this.httpServiceUsers.isUserLoggenIn)
      });
    });

    this.router.navigate(['/news']);
  }
  newMethodsApi(): void {
    this.httpServiceUsers.getLoginUser('ADMIN', 'nope').subscribe(
      res => {
        console.log(res);
      },
      error => {
        console.log('Error: ', error);
      });


  }

}
