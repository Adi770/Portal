import { CreateTopicComponent } from './create-topic/create-topic.component';
import { TopicComponent } from './../forum/topic/topic.component';
import { ForumComponent } from './forum.component';
import { SubSectionComponent } from './../forum/sub-section/sub-section.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
  {
    path: '',
    children:
      [
      { path: '', component: ForumComponent, },
      { path: ':subsection', component: SubSectionComponent },
      { path: ':section/Topic/:id', component: TopicComponent },
      { path: ':section/newTopic',component: CreateTopicComponent}
      ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ForumRoutingModule { }
