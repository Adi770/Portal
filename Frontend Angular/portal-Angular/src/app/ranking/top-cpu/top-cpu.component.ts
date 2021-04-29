import { Cpu } from './../../InterfacesPortal';
import { Observable } from 'rxjs';
import { HttpServicePortal } from './../../http.service.portal';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-top-cpu',
  templateUrl: './top-cpu.component.html',
  styleUrls: ['./top-cpu.component.css']
})
export class TopCPUComponent implements OnInit {

  constructor(private httpServicePortal: HttpServicePortal) { }

  listCpu: any;

  ngOnInit(): void {
    this.getRecommendCpu().subscribe(res => { this.listCpu = res; });

  }


  getRecommendCpu() {
    return this.httpServicePortal.recomendCPU();
  }


}
