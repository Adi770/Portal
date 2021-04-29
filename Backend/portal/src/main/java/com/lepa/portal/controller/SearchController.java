package com.lepa.portal.controller;


import com.lepa.portal.dto.search_component.CpuDTO;
import com.lepa.portal.dto.search_component.GpuDTO;
import com.lepa.portal.model.search_component.CPU;
import com.lepa.portal.model.search_component.GPU;
import com.lepa.portal.service.SearchService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
@RestController
@RequestMapping("/api/v1/Portal/Search")
public class SearchController {

    private SearchService searchService;
    private Map<String, String> stateOk = Collections.singletonMap("Operation", "Successful");

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    //region Get
    @GetMapping("/Cpu")
    public List<CPU> getCpu() {
        return searchService.findAllCPU();
    }

    @GetMapping("/Gpu")
    public List<GPU> getGpu() {
        return searchService.findAllGPU();
    }

    @GetMapping("/Gpu/{id}/FindCpu")
    public List<CPU> getOneGpu(@PathVariable(value = "id") long id) {
        return searchService.findAllbyGpuID(id);
    }

    @GetMapping("/Cpu/{id}/FindGpu")
    public List<GPU> getOneCpu(@PathVariable(value = "id") long id) {
        return searchService.findAllbyCpuID(id);
    }

    @GetMapping("/Gpu/Top")
    public List<GPU> topGpu() {
        return searchService.findAllGpuByTop();
    }

    @GetMapping("/Cpu/Top")
    public List<CPU> topCpu() {
        return searchService.findAllCpuByTop();
    }
    //endregion

    //region Put

    @PutMapping("/Cpu/{id}")
    public CPU editCpu(@PathVariable(value = "id") long id, @RequestBody CpuDTO cpu) {
        return searchService.editCPU(id, cpu);
    }

    @PutMapping("/Gpu/{id}")
    public GPU editGpu(@PathVariable(value = "id") long id, @RequestBody GpuDTO gpu) {
        return searchService.editGPU(id, gpu);
    }

    //endregion

    //region Post

    @PostMapping("/Cpu")
    public Object addCpu(@RequestBody CpuDTO cpu) {
        return searchService.addCPU(cpu);
    }

    @PostMapping("/Gpu")
    public Object addGpu(@RequestBody GpuDTO gpu) {
        return searchService.addGPU(gpu);
    }

    @PostMapping("/Cpu/{idCpu}/Gpu/{idGpu}")
    public Object cpuToGpu(@PathVariable(value = "idCpu") long idCpu, @PathVariable(value = "idGpu") long idGpu) {
        searchService.cpuToGpu(idCpu, idGpu);
        return searchService.cpuToGpu(idCpu, idGpu);
    }

    //endregion

    //region Delete

    @DeleteMapping("/Cpu/{id}")
    public ResponseEntity<Object> deleteCpu(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok(searchService.deleteCPU(id));
    }

    @DeleteMapping("/Gpu/{id}")
    public ResponseEntity<Object> deleteGpu(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok(searchService.deleteGPU(id));
    }
    //endregion

}
