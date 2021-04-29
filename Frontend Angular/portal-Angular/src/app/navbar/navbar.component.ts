import { LoginComponent } from '../panel-user/login/login.component';
import { RegisterComponent } from '../panel-user/register/register.component';
import { SearchComponentsComponent } from './../search-components/search-components.component';
import { NavbarService } from './../navbar.service';
import { HttpServiceUsers } from './../http.service.users';
import { Component, OnInit, NgZone, Injectable, ChangeDetectorRef, Input, HostListener, ViewContainerRef, ComponentFactoryResolver } from '@angular/core';
import { MatDialog, MatDialogRef, MatDialogState, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})

@Injectable()
export class NavbarComponent implements OnInit {


  isShown = false;
  user: string;
  userIsLogin: boolean;
  userCurrentRole: string;




  @HostListener('window:popstate', ['$event'])
  onPopState(event) {
    this.ns.show();
  }

  constructor(public ns: NavbarService, private httpServiceUsers: HttpServiceUsers,
    private ViewContainerRef: ViewContainerRef, private ctr: ComponentFactoryResolver, public dialog: MatDialog) {
    this.ns.show();
    this.httpServiceUsers.isUserLoggedIn1.subscribe(value => {
      this.userIsLogin = value;
    });
    this.httpServiceUsers.getUserRole().subscribe(res => {
      this.userCurrentRole = res.role;
      console.log(res.role);
    });
  }

  // async loadSearch() {
  //   this.ViewContainerRef.clear();
  //   const { SearchComponentsComponent } = await import('./../search-components/search-components.component');
  //   this.ViewContainerRef.createComponent(this.ctr.resolveComponentFactory(SearchComponentsComponent));
  // }

  ngOnInit(): void {

    this.user = this.httpServiceUsers.getLoggedInUserName();
    console.log('Dane logwanie do systemu i jakis randowmy tesks' + this.user, this.userIsLogin);

    this.httpServiceUsers.isUserLoginObservable().subscribe(re => {
      this.userIsLogin = re;
    })
  }

  refresh() {
    this.userCurrentRole = null;
    this.ngOnInit();
  }

  logout() {
    this.httpServiceUsers.logout();
    this.refresh();
  }

  openLoginDialog() {
    this.dialog.closeAll();
    this.dialog.open(LoginComponent);
  }
  openRegisterDialog(){
    this.dialog.closeAll();
    this.dialog.open(RegisterComponent);

  }





}
