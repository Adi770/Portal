import { CreateTopicComponent } from './create-topic/create-topic.component';

import { TopicComponent } from './topic/topic.component';
import { SubSectionComponent } from './../forum/sub-section/sub-section.component';

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ForumRoutingModule } from './forum-routing.module';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';


@NgModule({
  imports: [
    CommonModule,
    ForumRoutingModule,
    FormsModule
  ],
  declarations:[SubSectionComponent, TopicComponent, CreateTopicComponent]
})

export class ForumModule { }
