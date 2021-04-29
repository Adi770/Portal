package com.lepa.portal.repository;

import com.lepa.portal.model.forum.ForumSubSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ForumSubSectionRepo extends JpaRepository<ForumSubSection,Long> {
    ForumSubSection findForumSubSectionById(Long id);
}
