package com.lepa.portal.controller;

import com.lepa.portal.dto.CommentsDTO;
import com.lepa.portal.dto.NewsDTO;
import com.lepa.portal.dto.NewsMainPageDTO;
import com.lepa.portal.dto.RateDTO;
import com.lepa.portal.model.portal.Comments;
import com.lepa.portal.model.portal.News;
import com.lepa.portal.model.portal.Users;
import com.lepa.portal.service.DataService;
import com.lepa.portal.service.NewsService;
import com.lepa.portal.service.SearchService;
import com.lepa.portal.service.UsersService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
@RestController
@RequestMapping("/api/v1/Portal")
public class PortalController {

    private NewsService newsService;
    private UsersService usersService;
    private SearchService searchService;
    private DataService dataService;

    private Map<String, String> stateOk = Collections.singletonMap("Operation", "Successful");


    @Autowired
    public PortalController(NewsService newsService, UsersService usersService, SearchService searchService, DataService dataService) {
        this.newsService = newsService;
        this.usersService = usersService;
        this.searchService = searchService;
        this.dataService = dataService;
    }

    //region Get

    @GetMapping("/DataGenerator")
    public ResponseEntity<Object> dataGenerator() {
        dataService.portalData();
        return ResponseEntity.ok(stateOk);
    }

    @GetMapping("/Users")
    public List<Users> allUsers() {
        return this.usersService.getAllUsersWithPassword();
    }

    @GetMapping("/News")
    public List<News> allNews(@RequestParam("page") int page, @RequestParam("size") int size) {
        return newsService.allNews(page, size);
    }

    @GetMapping("/News/{id}")
    public NewsDTO oneNews(@PathVariable(value = "id") long id) {
        return newsService.oneNews(id);
    }

    @GetMapping("/Comment")
    public List<Comments> allCommentsUser(@RequestParam(value = "id") long id) {
        return newsService.allCommentsUser(id);
    }

    @GetMapping("/Comments")
    public List<Comments> allComents() {
        return newsService.allComments();
    }

    @GetMapping("/Comment/{id}")
    public Comments oneComments(@PathVariable(value = "id") long id) {
        return newsService.oneComments(id);
    }

    @GetMapping("/Comment/last5")
    public List<Comments> lastFiveComments() {
        return newsService.lastFiveComments();
    }

    @GetMapping("/News/Last10")
    public List<NewsMainPageDTO> getSomeNews() {
        return newsService.getSomeNews();
    }

    @GetMapping("/BestArticle/{size}")
    public List<NewsDTO> bestArticle(@PathVariable(value = "size") long size) {
        return newsService.theBestArticle(size);
    }

    @GetMapping("/News/Size")
    public Integer numberOfItems() {
        return newsService.numberOfItems();
    }

    //endregion

    //region Put
    @PutMapping("/News/{id}")
    public News updateNews(@PathVariable(value = "id") long id, @RequestBody NewsDTO news) {
        return newsService.updateNews(id, news);
    }

    @PutMapping("/Comment/{id}")
    public Comments updateComments(@PathVariable(value = "id") long id, @RequestBody CommentsDTO commentsDTO) {
        return newsService.updateComments(id, commentsDTO);
    }
    //endregion

    //region Post

    @PostMapping("/News")
    public News addNews(@RequestParam("news") String news, @RequestParam("images") MultipartFile file) {
        return newsService.addNews(news, file);
    }

    @PostMapping("/RateArticle/News/{id}")
    public News rateArticle(@PathVariable(value = "id") long idNews, @RequestBody RateDTO rate) {
        return newsService.rateArticle(idNews, rate.getRating());
    }

    @PostMapping("/News/{id}/Comment")
    public Comments addComment(@RequestBody CommentsDTO commentsDTO, @PathVariable(value = "id") long id) {
        return newsService.createComment(id, commentsDTO);
    }
    //endregion

    //region Delete
    @DeleteMapping("/News/{id}")
    public ResponseEntity<Object> deleteNews(@PathVariable(value = "id") long id) {
        newsService.deleteNews(id);
        return ResponseEntity.ok(stateOk);
    }

    @DeleteMapping("Comment/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable(value = "id") long id) {
        newsService.deleteComment(id);
        return ResponseEntity.ok(stateOk);
    }
//endregion


}
