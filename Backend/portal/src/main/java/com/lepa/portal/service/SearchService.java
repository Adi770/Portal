package com.lepa.portal.service;

import com.lepa.portal.dto.search_component.CpuDTO;
import com.lepa.portal.dto.search_component.GpuDTO;
import com.lepa.portal.model.search_component.CPU;
import com.lepa.portal.model.search_component.GPU;
import com.lepa.portal.repository.CPURepo;
import com.lepa.portal.repository.GPURepo;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class SearchService {

    private ModelMapper modelMapper;
    private CPURepo cpuRepo;
    private GPURepo gpuRepo;

    @Autowired
    public SearchService(CPURepo cpuRepo, GPURepo gpuRepo, ModelMapper modelMapper) {
        this.cpuRepo = cpuRepo;
        this.gpuRepo = gpuRepo;
        this.modelMapper=modelMapper;
    }

    public List<CPU> getListCPU() {
        return cpuRepo.findAll();
    }

    public List<GPU> getListGPU() {
        return gpuRepo.findAll();
    }

    public GPU findGPU() {
        long id = 1;
        CPU cpu = cpuRepo.findFirstById(2L);
        GPU gpu = gpuRepo.findFirstById(id);
        cpu.getGpu().add(gpu);
        gpu.getCpu().add(cpu);
        cpuRepo.save(cpu);
        return gpuRepo.findFirstByCpuId(id);
    }

    public CPU addCPU(CpuDTO cpuDTO) {
        return cpuRepo.save(modelMapper.map(cpuDTO,CPU.class));
    }
    public CPU editCPU(Long id, CpuDTO cpuDTO) {
        CPU cpu=modelMapper.map(cpuDTO,CPU.class);
        cpu.setId(id);
        return cpuRepo.save(cpu);
    }

    public GPU addGPU(GpuDTO gpuDTO) {
        return gpuRepo.save(modelMapper.map(gpuDTO,GPU.class));
    }

    public GPU editGPU(Long id, GpuDTO gpuDTO) {
        GPU gpu=modelMapper.map(gpuDTO,GPU.class);
        gpu.setId(id);
        return gpuRepo.save(new GPU());
    }

    public String connectHardware(long cpuId, long gpuId) {
        if (cpuRepo.existsById(cpuId) && gpuRepo.existsById(gpuId)) {

            CPU cpu = cpuRepo.findFirstById(cpuId);
            GPU gpu = gpuRepo.findFirstById(gpuId);
            cpu.getGpu().add(gpu);
            gpu.getCpu().add(cpu);
            cpuRepo.save(cpu);

            try {
                cpuRepo.save(cpu);
            } catch (Exception e) {
                return "assasiociat exist";
            }
        } else {
            return "not exist";
        }
        return null;
    }

    public String deleteCPU(long id) {
        cpuRepo.deleteById(id);
        return "delete" + id;
    }

    public String deleteGPU(long id) {
        gpuRepo.deleteById(id);
        return "delete" + id;
    }

    public List<CPU> findAllCPU() {
        return cpuRepo.findAll();
    }

    public List<GPU> findAllGPU() {
        return gpuRepo.findAll();
    }

    public CPU cpuToGpu(long idCpu, long idGpu) {
        CPU cpu = cpuRepo.findFirstById(idCpu);
        GPU gpu = gpuRepo.findFirstById(idGpu);
        cpu.getGpu().add(gpu);
        gpu.getCpu().add(cpu);
        return cpuRepo.save(cpu);
    }

    public List<CPU> findAllbyGpuID(long id) {
        return cpuRepo.findAllByGpuId(id);
    }

    public List<GPU> findAllbyCpuID(long id) {
        return gpuRepo.findAllByCpuId(id);
    }

    public List<GPU> findAllGpuByTop() {
        return gpuRepo.findAllByRecommendIsTrue();
    }

    public List<CPU> findAllCpuByTop() {
        return cpuRepo.findAllByRecommendIsTrue();
    }
}
