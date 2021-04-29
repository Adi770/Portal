import { NewsearchComponent } from './newsearch.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NewsearchRoutingModule } from './newsearch-routing.module';



@NgModule({
  declarations: [NewsearchComponent],
  imports: [
    CommonModule,
    NewsearchRoutingModule
  ]
})
export class NewsearchModule { }
