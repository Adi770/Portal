package com.lepa.portal.repository;

import com.lepa.portal.model.forum.ForumTopicComm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ForumTopicCommRepo extends JpaRepository<ForumTopicComm,Long> {
    ForumTopicComm findForumTopicCommById(Long id);
}
