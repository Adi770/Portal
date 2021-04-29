import { HttpServiceForum } from './../../http.service.forum';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';



@Component({
  selector: 'app-sub-section',
  templateUrl: './sub-section.component.html',
  styleUrls: ['./sub-section.component.css']
})
export class SubSectionComponent implements OnInit {

  dataSection: any;
  name: string;
  id: number;

  constructor(private httpServiceForum: HttpServiceForum, private activatedRoute: ActivatedRoute, private router: Router) {
    const snap = this.activatedRoute.snapshot;
    this.name = snap.params['subsection']
    this.id = snap.queryParams['id'];
    console.log(this.name, this.id);
  }

  ngOnInit(): void {
    this.generateSubSection().subscribe(data => { this.dataSection = data; });
  }

  generateSubSection() {
    return this.httpServiceForum.getSubSection(this.id);
  }
  newTopic() {
    console.log('test');
    this.router.navigate(['/forum/' + this.name + '/newTopic'], { queryParams: { id: this.id } });
  }
}
