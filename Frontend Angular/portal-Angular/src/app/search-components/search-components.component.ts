import { Observable } from 'rxjs';
import { HttpServicePortal } from './../http.service.portal';
import { Hardware, Cpu, Gpu } from './../InterfacesPortal';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-search-components',
  templateUrl: './search-components.component.html',
  styleUrls: ['./search-components.component.css']
})
export class SearchComponentsComponent implements OnInit {

  constructor(private httpServicePortal: HttpServicePortal) { }

  cpu: number;
  gpu: number;
  cpuList: any;
  gpuList: any;
  showAnswer = false;
  cpuNotFound = true;
  gpuNotFound = false;

  CPUrecommended: any;
  GPUrecommended: any;

  ngOnInit(): void {

    this.getCpuList().subscribe(res => {
      this.cpuList = res;
    });
    this.getGpuList().subscribe(res => {
      this.gpuList = res;
    });
  }

  getCpuList(): Observable<Array<Cpu>> {
    return this.httpServicePortal.loadListCpu();
  }

  getGpuList() {
    return this.httpServicePortal.loadListGpu();
  }



  checkComponent() {
    if (this.cpu !== undefined && this.gpu !== undefined) {
      this.showAnswer = true;
      this.cpuAnswer().subscribe(res => {
        if (res.length === 0) {
          this.cpuNotFound = true;
        } else {
          this.cpuNotFound = false;
        }
        this.CPUrecommended = res;
      });
      this.gpuAnswer().subscribe(res => {
        if (res.length === 0) {
          this.gpuNotFound = true;
        } else {
          this.gpuNotFound = false;
        }
        this.GPUrecommended = res;
      });
    }
  }



  cpuAnswer(): Observable<Array<Cpu>> {
    return this.httpServicePortal.searchCPUbyGPU(this.gpu);
  }
  gpuAnswer(): Observable<Array<Gpu>> {
    return this.httpServicePortal.searchGPUbyCPU(this.cpu);
  }



  editCPU(id: number) {
    //return this.
  }

  editGPU(id: number) {

  }

  deleteCPU(id: number) {
    return this.httpServicePortal.deleteCpu(id).subscribe(res => {
      console.log(res);

    });
  }
  deleteGPU(id: number) {
    return this.httpServicePortal.deleteGpu(id).subscribe(res => {
      console.log(res);

    });;
  }

}
