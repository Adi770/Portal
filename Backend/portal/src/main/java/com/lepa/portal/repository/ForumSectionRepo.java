package com.lepa.portal.repository;

import com.lepa.portal.model.forum.ForumSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ForumSectionRepo extends JpaRepository<ForumSection,Long> {


    ForumSection findForumSectionById(Long id);
}
