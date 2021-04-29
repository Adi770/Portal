import { Cpu, Gpu } from './../InterfacesPortal';
import { HttpServicePortal } from './../http.service.portal';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-hardware',
  templateUrl: './hardware.component.html',
  styleUrls: ['./hardware.component.css']
})
export class HardwareComponent implements OnInit {

  constructor(private httpServicePortal: HttpServicePortal) { }

  cpuList: any;
  gpuList: any;
  idinListCpu: number;
  idinListGpu: number;

  cpu: Cpu;
  gpu: Gpu;

  idGpu: number;
  manufacture: string;
  brandGpu: string;
  typeGpu: string;
  tdp: string;
  recommend: boolean;

  idCpu: number;
  brandCpu: string;
  typeCpu: string;
  socket: string;


  ngOnInit(): void {
    this.loadList();
    this.recommend = false;
  }

  loadList(): void {
    this.httpServicePortal.loadListGpu().subscribe(res => {
      this.gpuList = res;

    });
    this.httpServicePortal.loadListCpu().subscribe(res => {
      this.cpuList = res;
      console.log(this.cpuList);
    });
  }

  saveCPUtoGPU() {
    return this.httpServicePortal.cpuToGpu(this.idCpu, this.idGpu).toPromise().then(res => {
      console.log('succesfull connection');
    });

  }

  saveCPU() {
    let cpu: Cpu =
    {
      id: this.idCpu,
      brand: this.brandCpu,
      type: this.typeCpu,
      socket: this.socket,
      recommend: this.recommend
    };
    if (cpu.id === undefined) {
      return this.httpServicePortal.saveCPU(cpu).toPromise().then(res => {
        this.clearCpu();
      });
    } else {
      return this.httpServicePortal.editCPU(cpu.id, cpu).toPromise().then(res => {
        this.clearCpu();
      });
    }
  }
  clearCpu() {
    this.idCpu = undefined;
    this.brandCpu = undefined;
    this.typeCpu = undefined;
    this.socket = undefined;
    this.recommend = undefined;
  }
  saveGPU() {
    let gpu: Gpu = {
      id: this.idGpu,
      manufacture: this.manufacture,
      tdp: this.tdp,
      brand: this.brandGpu,
      type: this.typeGpu,
      recommend: this.recommend
    };
    if (gpu.id === undefined) {
      return this.httpServicePortal.saveGPU(gpu).toPromise().then();
    } else {
      return this.httpServicePortal.editGPU(gpu.id, gpu).toPromise().then();
    }
  }


  deleteCPU(id: number) {
    return this.httpServicePortal.deleteCpu(id).subscribe(res => {
      console.log(res);

    });
  }
  deleteGPU(id: number) {
    return this.httpServicePortal.deleteGpu(id).subscribe(res => {
      console.log(res);

    });
  }

  selectCPU(event) {
    this.idCpu = this.cpuList[this.idinListCpu].id;
    this.brandCpu = this.cpuList[this.idinListCpu].brand;
    this.typeCpu = this.cpuList[this.idinListCpu].type;
    this.socket = this.cpuList[this.idinListCpu].socket;
    this.recommend = this.cpuList[this.idinListCpu].recommend;
  }
  selectGPU(event) {
    this.idGpu = this.gpuList[this.idinListGpu].id;
    this.manufacture = this.gpuList[this.idinListGpu].manufacture;
    this.brandGpu = this.gpuList[this.idinListGpu].brand;
    this.typeGpu = this.gpuList[this.idinListGpu].type;
    this.tdp = this.gpuList[this.idinListGpu].tdp;
    this.recommend = this.cpuList[this.idinListGpu].recommend;
  }
}
