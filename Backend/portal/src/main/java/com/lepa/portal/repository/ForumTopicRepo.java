package com.lepa.portal.repository;

import com.lepa.portal.model.forum.ForumTopic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ForumTopicRepo extends JpaRepository<ForumTopic,Long> {
    ForumTopic findForumTopicById(Long id);
    List<ForumTopic> findTop10ByOrderByIdDesc();
    List<ForumTopic> findByOrderByIdDesc(Pageable pageable);
}
