import { NewsListPipe } from './news-list/news-list.pipe';
import { BasicAuthHtppInterceptorService } from './basic.auth.intercepter';
import { MatTabsModule } from '@angular/material/tabs';
import { HttpServiceForum } from './http.service.forum';
import { HttpServiceUsers } from './http.service.users';
import { HttpServicePortal } from './http.service.portal';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule, routingComponents } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ArticleComponent } from './article/article.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LoginComponent } from './panel-user/login/login.component';
import { RegisterComponent } from './panel-user/register/register.component';
import { FormsModule } from '@angular/forms';
import { NewsListComponent } from './news-list/news-list.component';
import { NewsComponent } from './news/news.component';
import { HomeComponent } from './home/home.component';
import { NavbarComponent } from './navbar/navbar.component';
import { PanelUserComponent } from './panel-user/panel-user.component';
import { SearchComponentsComponent } from './search-components/search-components.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HardwareComponent } from './hardware/hardware.component';
import { RankingComponent } from './ranking/ranking.component';
import { TopCPUComponent } from './ranking/top-cpu/top-cpu.component';
import { TopGPUComponent } from './ranking/top-gpu/top-gpu.component'
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import { FooterComponent } from './footer/footer.component';
import { MatDialogModule } from '@angular/material/dialog';
import { LostPasswordComponent } from './../app/panel-user/lost-password/lost-password.component';

@NgModule({
  declarations: [
    AppComponent,
    routingComponents,
    ArticleComponent,
    LoginComponent,
    RegisterComponent,
    LostPasswordComponent,
    NewsListComponent,
    NewsComponent,
    HomeComponent,
    NavbarComponent,
    PanelUserComponent,
    SearchComponentsComponent,
    HardwareComponent,
    RankingComponent,
    TopCPUComponent,
    TopGPUComponent,
    NewsListPipe,
    FooterComponent,
    LostPasswordComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgbModule,
    MatTabsModule,
    BrowserAnimationsModule,
    CKEditorModule,
    MatDialogModule
  ],
  exports: [CKEditorModule],
  providers: [HttpServiceForum, HttpServiceUsers, HttpServicePortal,
    {
      provide: HTTP_INTERCEPTORS, useClass: BasicAuthHtppInterceptorService, multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule {

}