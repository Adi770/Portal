import { environment } from './../environments/environment';
import { DialogData } from './panel-user/lost-password/lost-password.component';

import { ForumTopic, Comments, Role, UsersDTO } from './InterfacesPortal';
import { Observable, BehaviorSubject } from 'rxjs';
import { ForumSection, ForumSubSections } from 'src/app/InterfacesPortal';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, Output, EventEmitter } from '@angular/core';
import { map } from 'rxjs/operators';
import { of } from 'rxjs';


@Injectable()
export class HttpServiceUsers {

    constructor(private httpClient: HttpClient) { }
    userApiUrl = environment.baseURL+'api/v1/user';
    userApiLoginUrl = environment.baseURL+'login';

    username: string;
    password: string;

    tokenAcces = 'tokenAccess';
    userNameSessionAttributeName = 'User';


    public isUserLoggedIn1: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(this.isUserLoggenIn());

    /******Logowanie i sesja*********/
    getLoginUser(username: string, password: string) {
        const httpOptions = {
            headers: new HttpHeaders({
                authorization: this.createTokenUser(username, password)
            })
        };
        return this.httpClient.get(this.userApiUrl + '/Login', httpOptions);
    }

    authUser(username: string, password: string) {
        const httpOptions = {
            headers: new HttpHeaders({
                authorization: this.createTokenUser(username, password)
            })
        };
        return this.httpClient.get(this.userApiUrl + '/Login', httpOptions).pipe(map(res => {
            this.username = username;
            this.password = password;
            this.succesLogin(username, password);
        }));
    }

    succesLogin(username: string, password: string): void {
        sessionStorage.setItem(this.userNameSessionAttributeName, username);
    }

    createTokenUser(username: string, password: string): string {
        sessionStorage.setItem(this.tokenAcces, 'Basic ' + window.btoa(username + ':' + password));
        return 'Basic ' + window.btoa(username + ':' + password);
    }

    currenthttpOptions() {
        const a = sessionStorage.getItem(this.tokenAcces);
        console.log('Sessino storage ' + sessionStorage.getItem(this.tokenAcces));
        let httpOptions = {
            headers: new HttpHeaders({
                authorization: sessionStorage.getItem(this.tokenAcces)
            })
        };
        if (a === null) {
            httpOptions = {
                headers: new HttpHeaders({
                    authorization: 'not avaiable'
                })
            };
        }
        console.log('http options ' + httpOptions.headers);
        return httpOptions;
    }

    logout(): void {
        sessionStorage.removeItem(this.userNameSessionAttributeName);
        this.username = null;
        this.password = null;
    }

    isUserLoggenIn() {
        const user = sessionStorage.getItem(this.userNameSessionAttributeName);
        if (user === null) { return false; }
        return true;
    }

    getLoggedInUserName() {
        const user = sessionStorage.getItem(this.userNameSessionAttributeName);
        if (user === null) { return ''; }
        return user;
    }

    /************Dodatkowe metody***************** */
    getUserRole() {
        return this.httpClient.get<Role>(this.userApiUrl + '/Role');
    }

    userList() {
        return this.httpClient.get(this.userApiUrl + '/AllUsers');
    }

    roleList() {
        return this.httpClient.get(this.userApiUrl + '/AllRoles');
    }

    createUser(data: object) {
        console.log(data);
        let httpOptions = {
            headers: new HttpHeaders({
            })
        };
        return this.httpClient.post(this.userApiUrl + '/Register', data, httpOptions);
    }

    isUserLoginObservable(): Observable<boolean> {

        const user = sessionStorage.getItem(this.userNameSessionAttributeName);
        if (user === null) { return of(false); }
        return of(true);
    }

    changePassword(password: string) {

        return this.httpClient.post(this.userApiUrl + '/ChangePassword', password);
    }

    changeEmail(email: string) {
        console.log(email);
        return this.httpClient.post(this.userApiUrl + '/ChangeEmail', email);
    }

    changeRole(selectValue: number, selectValue2: number) {
        return this.httpClient.post(this.userApiUrl + '/SetRole/' + selectValue, selectValue2);
    }

    resetPassword(data: UsersDTO) {
        return this.httpClient.post(this.userApiUrl+'/ResetPasswordsMail', data)
    }
}
