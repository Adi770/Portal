package com.lepa.portal.repository;

import com.lepa.portal.model.search_component.CPU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CPURepo extends JpaRepository<CPU, Long> {

    CPU findFirstById(long id);

    List<CPU> findAllByGpuId(long id);

    List<CPU> findAllByRecommendIsTrue();

}
