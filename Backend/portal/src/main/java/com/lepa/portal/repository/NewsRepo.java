package com.lepa.portal.repository;

import com.lepa.portal.model.portal.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface NewsRepo extends JpaRepository<News,Long> {

    Optional<News> findNewsById(Long id);


    List<News> findTop10ByOrderByIdDesc();

    List<News> findAllByRatingsIsNotNull();

    Page<News> findAllByOrderByIdDesc(Pageable pageable);
}
