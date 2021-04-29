import { Component, OnInit } from '@angular/core';
import { HttpServicePortal } from './../../http.service.portal';

@Component({
  selector: 'app-top-gpu',
  templateUrl: './top-gpu.component.html',
  styleUrls: ['./top-gpu.component.css']
})
export class TopGPUComponent implements OnInit {
  constructor(private httpServicePortal: HttpServicePortal) { }

  listGpu: any;

  ngOnInit(): void {
    this.getRecommendGpu().subscribe(res => { this.listGpu = res; });
  }
  getRecommendGpu() {
    return this.httpServicePortal.recomendGPU();
  }

}
