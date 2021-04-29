package com.lepa.portal.service;

import com.google.gson.Gson;
import com.lepa.portal.dto.CommentsDTO;
import com.lepa.portal.dto.NewsDTO;
import com.lepa.portal.dto.NewsMainPageDTO;
import com.lepa.portal.exception.NewsNotFoundException;
import com.lepa.portal.model.ArticleRating;
import com.lepa.portal.model.ArticleRatingKey;
import com.lepa.portal.model.portal.Comments;
import com.lepa.portal.model.portal.News;
import com.lepa.portal.model.portal.Users;
import com.lepa.portal.repository.CommentsRepo;
import com.lepa.portal.repository.ForumSectionRepo;
import com.lepa.portal.repository.NewsRepo;
import com.lepa.portal.repository.UsersRepo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Data
@Service
public class NewsService {

    private static final String NEWS_NOT_FOUND = "News doesn't exist";

    private ModelMapper modelMapper;
    private CommentsRepo commentsRepo;
    private NewsRepo newsRepo;
    private UsersRepo usersRepo;
    private UploadService uploadService;
    private UsersService usersService;
    private ForumSectionRepo forumSectionRepo;



    @Autowired
    public NewsService(ModelMapper modelMapper, CommentsRepo commentsRepo, NewsRepo newsRepo, UsersRepo usersRepo, UploadService uploadService, UsersService usersService, ForumSectionRepo forumSectionRepo) {
        this.modelMapper = modelMapper;
        this.commentsRepo = commentsRepo;
        this.newsRepo = newsRepo;
        this.usersRepo = usersRepo;
        this.uploadService = uploadService;
        this.usersService = usersService;
        this.forumSectionRepo = forumSectionRepo;
    }





    public List<News> allNews(int page, int size) {
        log.info("Load news list Page:"+page+" Size:"+size);
        Page<News> newsPage = newsRepo.findAllByOrderByIdDesc(PageRequest.of(page - 1, size));
        newsPage.forEach(d->d.setImage(encodeImage(d.getImage())));
        return newsPage.getContent();
    }

    public News addNews(String news, MultipartFile file) {

        log.info("Add news");
        if (file.isEmpty() || news == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Param is empty");
        }
        Gson gson = new Gson();
        NewsDTO newsDTO = gson.fromJson(news, NewsDTO.class);
        Users user = usersService.returnCurrentUser();
        News newsUp = new News();
        newsUp.setTitle(newsDTO.getTitle());
        newsUp.setArticle(newsDTO.getArticle());
        newsUp.setImage(uploadService.uploadToAWS(file));
        //newsUp.setImage(uploadService.uploadToFolder(file));
        newsUp.setUser(user);
        newsUp.setDate(LocalDate.now());

        return newsRepo.save(newsUp);
    }

    public News updateNews(long id, NewsDTO newsDTO) {
        log.info("Update news");
        News news = newsRepo.findNewsById(id).orElseThrow(()->new NewsNotFoundException(NEWS_NOT_FOUND));
        news.setDate(LocalDate.now());
        news.setTitle(newsDTO.getTitle());
        news.setArticle(newsDTO.getArticle());
        return newsRepo.save(news);
    }

    public void deleteNews(long id) {
        log.info("delete news "+id);
        newsRepo.deleteById(id);
    }

    public List<Comments> allCommentsUser(long id) {
        return commentsRepo.findCommentsByUser(usersRepo.getOne(id));
    }

    public Comments oneComments(long id) {
        if (!commentsRepo.existsById(id)) {
            throw new NullPointerException("Comment doesnt exist");
        }
        return commentsRepo.findCommentsById(id);
    }

    public void deleteComment(long id) {
        log.info("delete news "+id);
        if (!commentsRepo.existsById(id)) {
            throw new NullPointerException("id doesnt exist");
        }
        commentsRepo.deleteById(id);
    }

    public Comments updateComments(long idCom, CommentsDTO commentsDTO) {
        if (!commentsRepo.existsById(idCom)) {
            throw new NullPointerException("Comments doesn't exist");
        }
        if (commentsRepo.findCommentsByUser(usersService.returnCurrentUser()) == null) {
            throw new NullPointerException("This is not your comment");
        }
        Comments com = commentsRepo.findCommentsById(idCom);
        com.setText(commentsDTO.getText());

        return addComments(com);
    }

    public Comments createComment(long idNews, CommentsDTO commentsDTO) {
       News news =newsRepo.findNewsById(idNews).orElseThrow(()->new NewsNotFoundException(NEWS_NOT_FOUND));
        Comments com = modelMapper.map(commentsDTO, Comments.class);
        com.setNews(news);
        com.setUser(usersService.returnCurrentUser());
        return addComments(com);
    }

    public Comments addComments(Comments comments) {
        comments.setDate(LocalDate.now());
        return commentsRepo.save(comments);
    }

    public List<Comments> lastFiveComments() {
        return commentsRepo.findTop5ByOrderByIdDesc();
    }

    public List<NewsMainPageDTO> getSomeNews() {
        List<News> listNews = newsRepo.findTop10ByOrderByIdDesc();

        //Lambda problem
        Converter<News, NewsMainPageDTO> myConverter = new Converter<News, NewsMainPageDTO>() {
            @Override
            public NewsMainPageDTO convert(MappingContext<News, NewsMainPageDTO> context) {
                News sour = context.getSource();
                NewsMainPageDTO dest = new NewsMainPageDTO();
                dest.setId(sour.getId());
                dest.setTitle(sour.getTitle());
                dest.setImage(encodeImage(sour.getImage()));
                dest.setUsername(sour.getUser().getUsername());
                return dest;
            }
        };
        modelMapper.addConverter(myConverter);
        return Arrays.asList(modelMapper.map(listNews, NewsMainPageDTO[].class));
    }

    public List<Comments> allComments() {
        return commentsRepo.findAll();
    }

    public News rateArticle(long id, int rate) {
        News news = newsRepo.findNewsById(id).orElseThrow(()->new NewsNotFoundException(NEWS_NOT_FOUND));
        long userId = usersService.returnCurrentUserId();

        ArticleRating articleRating = new ArticleRating();
        ArticleRatingKey articleRatingKey = new ArticleRatingKey();

        articleRatingKey.setArticleId(id);
        articleRatingKey.setUserId(userId);

        articleRating.setId(articleRatingKey);
        articleRating.setNews(news);
        articleRating.setUsers(usersService.returnCurrentUser());
        articleRating.setRating(rate);

        news.getRatings().add(articleRating);

        return newsRepo.save(news);
    }

    public List<NewsDTO> theBestArticle(long size) {
        List<News> newsList = newsRepo.findAllByRatingsIsNotNull()
                .stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparingLong(News::getId))), ArrayList::new));

        //lambda is wrong idea
        Converter<News, NewsDTO> myConverter = new Converter<News, NewsDTO>() {
            @Override
            public NewsDTO convert(MappingContext<News, NewsDTO> context) {
                News sour = context.getSource();
                NewsDTO dest = new NewsDTO();
                dest.setId(sour.getId());
                dest.setTitle(sour.getTitle());
                dest.setImage(encodeImage(sour.getImage()));
                dest.setUsername(sour.getUser().getUsername());
                int amount = 1;
                if (!sour.getRatings().isEmpty()) amount = sour.getRatings().size();
                double sum = (sour.getRatings().stream().mapToInt(ArticleRating::getRating).sum());
                dest.setRate(sum / amount);
                return dest;
            }
        };

        modelMapper.addConverter(myConverter);
        List<NewsDTO> nowalista = Arrays.asList(modelMapper.map(newsList, NewsDTO[].class));
        nowalista.sort(Comparator.comparing(NewsDTO::getRate).reversed());
        return nowalista.stream().limit(size).collect(Collectors.toList());
    }

    public int numberOfItems() {
        return (int) newsRepo.count();
    }

    public NewsDTO oneNews(long id) {
        News news = newsRepo.findNewsById(id).orElseThrow(()->new NewsNotFoundException(NEWS_NOT_FOUND));
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);

        Converter<News, NewsDTO> customConverter = new Converter<News, NewsDTO>() {
            @Override
            public NewsDTO convert(MappingContext<News, NewsDTO> context) {
                News source = context.getSource();
                NewsDTO destin = new NewsDTO();
                destin.setId(source.getId());
                destin.setTitle(source.getTitle());
                destin.setImage(encodeImage(source.getImage()));
                destin.setArticle(source.getArticle());
                destin.setUsername(source.getUser().getUsername());
                CommentsDTO[] listComm = modelMapper.map(source.getCommentsSet(), CommentsDTO[].class);
                destin.setCommentsSet(Arrays.asList(listComm));
                return destin;
            }
        };

        modelMapper.addConverter(customConverter);

        return modelMapper.map(news, NewsDTO.class);
    }

    public String encodeImage(String image) {
        Pattern pattern=Pattern.compile("resources.image");
        Matcher matcher= pattern.matcher(image);
        byte[] fileContent = new byte[10];
        if(matcher.find())
        {
            log.info("load from disk");
            try {
                log.info("load image");
                fileContent = FileUtils.readFileToByteArray(new File(image));
            } catch (IOException e) {
                log.info("Error with image");
                e.printStackTrace();
            }
            log.info("return image");
            return Base64.getEncoder().encodeToString(fileContent);
        }else
        {
            log.info("load from AWS");
            try {
                fileContent= IOUtils.toByteArray(uploadService.loadImageFromAWS(image));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Base64.getEncoder().encodeToString(fileContent);
        }
    }

}
