import { HttpServiceUsers } from './http.service.users';
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class BasicAuthHtppInterceptorService implements HttpInterceptor {

    constructor() { }

    // tslint:disable-next-line: typedef
    intercept(req: HttpRequest<any>, next: HttpHandler) {
        if (sessionStorage.getItem('User') && sessionStorage.getItem('tokenAccess')) {
            req = req.clone({
                setHeaders: {
                    Authorization: sessionStorage.getItem('tokenAccess')
                }
            });
        }
        return next.handle(req);
    }
}
