package com.lepa.portal.repository;

import com.lepa.portal.model.portal.Comments;
import com.lepa.portal.model.portal.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentsRepo extends JpaRepository<Comments, Long> {

    Comments findCommentsById(Long id);

    List<Comments> findCommentsByUser(Users user);

    Page<Comments> findTop5ByOrderByIdDesc (Pageable pageable);

    List<Comments> findTop5ByOrderByIdDesc ();
}
