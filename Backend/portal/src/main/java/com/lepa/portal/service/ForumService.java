package com.lepa.portal.service;

import com.lepa.portal.dto.forum.*;
import com.lepa.portal.exception.UserNotFoundException;
import com.lepa.portal.model.forum.ForumSection;
import com.lepa.portal.model.forum.ForumSubSection;
import com.lepa.portal.model.forum.ForumTopic;
import com.lepa.portal.model.forum.ForumTopicComm;
import com.lepa.portal.model.portal.Users;
import com.lepa.portal.repository.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
@Service
public class ForumService {

    private ModelMapper modelMapper;
    private ForumSectionRepo forumSectionRepo;
    private ForumSubSectionRepo forumSubSectionRepo;
    private ForumTopicCommRepo forumTopicCommRepo;
    private ForumTopicRepo forumTopicRepo;
    private UsersRepo usersRepo;
    private UsersService usersService;

    @Autowired
    public ForumService(UsersService usersService, ForumSectionRepo forumSectionRepo, ForumSubSectionRepo forumSubSectionRepo, ForumTopicCommRepo forumTopicCommRepo, ForumTopicRepo forumTopicRepo, UsersRepo usersRepo, ModelMapper modelMapper) {
        this.forumSectionRepo = forumSectionRepo;
        this.forumSubSectionRepo = forumSubSectionRepo;
        this.forumTopicCommRepo = forumTopicCommRepo;
        this.forumTopicRepo = forumTopicRepo;
        this.usersRepo = usersRepo;
        this.usersService = usersService;
        this.modelMapper=modelMapper;
    }

    public List<ForumSectionDTO> forumMainPage() {

        return forumSectionRepo.findAll()
                .stream()
                .map(forum -> modelMapper.map(forum, ForumSectionDTO.class))
                .collect(Collectors.toList());
    }

    public List<ForumSectionDTO> sectionList() {
        return forumSectionRepo.findAll()
                .stream()
                .map(forum -> modelMapper.map(forum, ForumSectionDTO.class))
                .collect(Collectors.toList());
    }

    public List<ForumSubSection> subSectionList() {
        return forumSubSectionRepo.findAll();
    }

    public List<ForumTopic> topicsList() {
        return forumTopicRepo.findAll();
    }

    public List<ForumTopicComm> topicCommList() {
        return forumTopicCommRepo.findAll();
    }

    public ForumTopicComm topicCommList(long id) {
        return forumTopicCommRepo.findForumTopicCommById(id);
    }

    public ForumSection sectionList(long id) {
        return forumSectionRepo.findForumSectionById(id);
    }

    public ForumSubSection subSectionList(long id) {
        return forumSubSectionRepo.findForumSubSectionById(id);
    }

    public ForumTopicDTO topicsList(long id) {
        ForumTopic forumTopic = forumTopicRepo.findForumTopicById(id);
        ForumTopicDTO forumTopicDTO = modelMapper.map(forumTopic, ForumTopicDTO.class);

        List<ForumTopicCommDTO> forumTopicCommDTO = forumTopicDTO.getForumTopicCommList();
        forumTopicCommDTO.sort(Comparator.comparing(ForumTopicCommDTO::getId));
        forumTopicDTO.setForumTopicCommList(forumTopicCommDTO);
        return forumTopicDTO;
    }

    public ForumSection addSection(String section) {
        ForumSection sec = new ForumSection();
        sec.setSection(section);
        return forumSectionRepo.save(sec);
    }

    public ForumSubSection addForumSubSection(long id, String subSection) {
        ForumSubSection forumSubSection = new ForumSubSection();
        forumSubSection.setName(subSection);
        forumSubSection.setForumSection(forumSectionRepo.findForumSectionById(id));
        return forumSubSectionRepo.save(forumSubSection);
    }

    public ForumTopic addForumTopic(long id, String topic)  {
        Users users = usersRepo.findUsersById(usersService.returnCurrentUserId()).orElseThrow(()-> new UserNotFoundException("User doesn't exist"));
        ForumTopic forumTopic = new ForumTopic();
        forumTopic.setTitleTopic(topic);
        forumTopic.setDate(LocalDate.now());
        forumTopic.setUser(users);
        forumTopic.setForumSubSection(forumSubSectionRepo.findForumSubSectionById(id));
        return forumTopicRepo.save(forumTopic);
    }

    public ForumTopicComm addForumTopicComm(long id, String topicComm) {
        Users users = usersRepo.findUsersById(usersService.returnCurrentUserId()).orElseThrow(()-> new UserNotFoundException("User doesn't exist"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("user name" + auth.getName());
        ForumTopicComm forumTopicComm = new ForumTopicComm();
        forumTopicComm.setText(topicComm);
        forumTopicComm.setDate(LocalDate.now());
        forumTopicComm.setUser(users);
        forumTopicComm.setForumTopic(forumTopicRepo.findForumTopicById(id));
        return forumTopicCommRepo.save(forumTopicComm);
    }

    public void deleteSection(long id) {
        forumSectionRepo.deleteById(id);
    }

    public void deleteSubSection(long id) {
        forumSubSectionRepo.deleteById(id);
    }

    public void deleteTopics(long id) {
        forumTopicRepo.deleteById(id);
    }

    public void deleteTopicsComm(long id) {
        long topicId = forumTopicCommRepo.findForumTopicCommById(id).getForumTopic().getId();
        forumTopicCommRepo.deleteById(id);
        if (forumTopicRepo.findForumTopicById(topicId).getForumTopicCommList().isEmpty()) {
            deleteTopics(topicId);
        }
    }

    public ForumSection updateSection(long id, String section) {

        return forumSectionRepo.findById(id).map(x -> {
            x.setSection(section);
            return forumSectionRepo.save(x);
        }).orElseThrow(() -> new RuntimeException("Section doesn't exist"));
    }

    public ForumSubSection updateSubSection(long id, String subSection) {
        return forumSubSectionRepo.findById(id).map(element -> {
            element.setName(subSection);
            return forumSubSectionRepo.save(element);
        }).orElseThrow(() -> new RuntimeException(""));
    }

    public ForumTopic updateForumTopic(long id, String topic) {
        return forumTopicRepo.findById(id).map(element -> {
            element.setTitleTopic(topic);
            return forumTopicRepo.save(element);
        }).orElseThrow(() -> new RuntimeException("Topic doesn't exist"));
    }

    public ForumTopicComm updateForumTopicComm(long id, String topicComm) {
        return forumTopicCommRepo.findById(id).map(element -> {
            element.setDate(LocalDate.now());
            element.setText(topicComm);
            return forumTopicCommRepo.save(element);
        }).orElseThrow(() -> new RuntimeException("something wrong"));
    }

    public List<ForumTopicLastDTO> getLastTopic(int number) {
        return Arrays.asList(modelMapper.map(forumTopicRepo.findByOrderByIdDesc(createPageRequest(number)), ForumTopicLastDTO[].class));
    }

    private Pageable createPageRequest(int number) {
        if (number > 0) {
            return PageRequest.of(0, number);
        } else {
            return PageRequest.of(0, 10);
        }
    }

    public ForumTopicComm createNewTopic(TopicDTO topicDTO) {
        ForumTopic topic = new ForumTopic();
        ForumTopicComm topicComments = new ForumTopicComm();

        topic.setDate(LocalDate.now());
        topic.setUser(usersService.returnCurrentUser());
        topic.setTitleTopic(topicDTO.getTitleTopic());
        topic.setForumSubSection(forumSubSectionRepo.findForumSubSectionById(topicDTO.getSubSectionId()));

        ForumTopic dataTopic = forumTopicRepo.save(topic);

        topicComments.setDate(LocalDate.now());
        topicComments.setUser(usersService.returnCurrentUser());
        topicComments.setText(topicDTO.getTextTopic());
        topicComments.setForumTopic(dataTopic);
        return forumTopicCommRepo.save(topicComments);


    }


}
