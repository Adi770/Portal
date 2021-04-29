import { LostPasswordComponent } from './../app/panel-user/lost-password/lost-password.component';
import { NewsListComponent } from './news-list/news-list.component';
import { RankingComponent } from './ranking/ranking.component';
import { HardwareComponent } from './hardware/hardware.component';
import { SearchComponentsComponent } from './search-components/search-components.component';
import { PanelUserComponent } from './panel-user/panel-user.component';
import { HomeComponent } from './home/home.component';
import { NewsComponent } from './news/news.component';
import { RegisterComponent } from './panel-user/register/register.component';
import { LoginComponent } from './panel-user/login/login.component';
import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ForumComponent } from './forum/forum.component';
import { ArticleComponent } from './article/article.component';


const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'article', component: ArticleComponent },
  { path: 'article/:id', component: ArticleComponent },

  { path: 'news', component: NewsListComponent },
  { path: 'news/:id', component: NewsComponent },
  { path: 'userPanel', component: PanelUserComponent },
  { path: 'hardware', component: HardwareComponent },
  { path: 'hardware/:CpuID', component: HardwareComponent },
  { path: 'hardware/:GpuID', component: HardwareComponent },
  { path: 'search', component: SearchComponentsComponent },
  { path: 'ranking', component: RankingComponent },
  {
    path: 'newsearch',
    loadChildren: () => import('./newsearch/newsearch.module').then(m => m.NewsearchModule)
  },

  {
    path: 'forum', loadChildren: () => import('./forum/forum.module').then(m => m.ForumModule)
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }

export const routingComponents = [ForumComponent, ArticleComponent,
  LoginComponent, RegisterComponent, LostPasswordComponent ,NewsListComponent, NewsComponent, HomeComponent,
   PanelUserComponent, SearchComponentsComponent,
  HardwareComponent, RankingComponent];
