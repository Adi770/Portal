package com.lepa.portal.repository;

import com.lepa.portal.model.search_component.GPU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GPURepo extends JpaRepository<GPU, Long> {

    GPU findFirstByCpuId(long id);

    List<GPU> findAllByCpuId(long id);

    GPU findFirstById(long id);

    List<GPU> findAllByRecommendIsTrue();


}
