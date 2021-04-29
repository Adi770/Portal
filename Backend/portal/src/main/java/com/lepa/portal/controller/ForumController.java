package com.lepa.portal.controller;

import com.lepa.portal.dto.forum.*;
import com.lepa.portal.model.forum.ForumSection;
import com.lepa.portal.model.forum.ForumSubSection;
import com.lepa.portal.model.forum.ForumTopic;
import com.lepa.portal.model.forum.ForumTopicComm;
import com.lepa.portal.service.ForumService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;


@Slf4j
@Data
@RestController
@RequestMapping("/api/v1/Forum")
public class ForumController {


    private ForumService forumService;
    private Map<String, String> stateOk = Collections.singletonMap("Operation", "Successful");

    @Autowired
    public ForumController(ForumService forumService) {
        this.forumService = forumService;
    }

    public ForumController() {
    }

    //region Get
    @GetMapping("/Generate")
    public List<ForumSectionDTO> forumMainPage() {
        return forumService.forumMainPage();
    }

    @GetMapping("/Section")
    public List<ForumSectionDTO> sectionList() {
        return forumService.sectionList();
    }

    @GetMapping("/SubSection")
    public List<ForumSubSection> subSectionList() {
        return forumService.subSectionList();
    }

    @GetMapping("/Topics")
    public List<ForumTopic> topicsList() {
        return forumService.topicsList();
    }

    @GetMapping("/Comments")
    public List<ForumTopicComm> topicCommList() {
        return forumService.topicCommList();
    }

    @GetMapping("/Comments/{id}")
    public ForumTopicComm topicCommList(@PathVariable("id") long id) {
        return forumService.topicCommList(id);
    }

    @GetMapping("/Section/{id}")
    public ForumSection sectionList(@PathVariable("id") long id) {
        return forumService.sectionList(id);
    }

    @GetMapping("/SubSection/{id}")
    public ForumSubSection subSectionList(@PathVariable("id") long id) {
        return forumService.subSectionList(id);
    }

    @GetMapping("/Topics/{id}")
    public ForumTopicDTO topicsList(@PathVariable("id") long id) {
        return forumService.topicsList(id);
    }

    @GetMapping("/ForumLastTopic/{number}")
    public List<ForumTopicLastDTO> getLastTopic(@PathVariable("number") int number) {
        return forumService.getLastTopic(number);
    }
    //endregion

    //region Post
    @PostMapping("/Section")
    public ForumSection addSection(@RequestBody NameDTO section) {
        return forumService.addSection(section.getText());
    }

    @PostMapping("Section/{id}/SubSection")
    public ForumSubSection addForumSubSection(@PathVariable("id") long id, @RequestBody NameDTO subSection) {
        return forumService.addForumSubSection(id, subSection.getText());
    }

    @PostMapping("SubSection/{id}/Topic")
    public ForumTopic addForumTopic(@PathVariable("id") long id, @RequestBody NameDTO topic) {
        return forumService.addForumTopic(id, topic.getText());
    }

    @PostMapping("Topic/{id}/TopicComm")
    public ForumTopicComm addForumTopicComm(@PathVariable("id") long id, @RequestBody NameDTO topicComm) {
        return forumService.addForumTopicComm(id, topicComm.getText());
    }

    @PostMapping("SubSection/Topic")
    public ForumTopicComm createNewTopic(@RequestBody TopicDTO topicDTO) {
        return  forumService.createNewTopic(topicDTO);
    }
//endregion

    //region Delete
    @DeleteMapping("/Section/{id}")
    public ResponseEntity<Object> deleteSection(@PathVariable("id") long id) {
        forumService.deleteSection(id);
        return ResponseEntity.ok(stateOk);
    }

    @DeleteMapping("/SubSection/{id}")
    public ResponseEntity<Object> deleteSubSection(@PathVariable("id") long id) {
        forumService.deleteSubSection(id);
        return ResponseEntity.ok(stateOk);
    }

    @DeleteMapping("/Topic/{id}")
    public ResponseEntity<Object> deleteTopics(@PathVariable("id") long id) {
        forumService.deleteTopics(id);
        return ResponseEntity.ok(stateOk);
    }

    @DeleteMapping("/Comments/{id}")
    public ResponseEntity<Object> deleteTopicsComm(@PathVariable("id") long id) {
        forumService.deleteTopicsComm(id);
        return ResponseEntity.ok(stateOk);
    }
    //endregion

    //region Put
    @PutMapping("/Section/{id}")
    public ForumSection updateSection(@PathVariable("id") long id, @RequestBody NameDTO section) {

        return forumService.updateSection(id, section.getText());
    }

    @PutMapping("/SubSection/{id}")
    public ForumSubSection updateSubSection(@PathVariable("id") long id, @RequestBody NameDTO subSection) {
        return forumService.updateSubSection(id, subSection.getText());
    }

    @PutMapping("/ForumTopic/{id}")
    public ForumTopic updateForumTopic(@PathVariable("id") long id, @RequestBody NameDTO topic) {
        return forumService.updateForumTopic(id, topic.getText());
    }

    @PutMapping("/ForumTopicComm/{id}")
    public ForumTopicComm updateForumTopicComm(@PathVariable("id") long id, @RequestBody NameDTO topicComm) {
        return forumService.updateForumTopicComm(id, topicComm.getText());
    }
    //endregion

}
