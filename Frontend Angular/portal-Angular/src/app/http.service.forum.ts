import { environment } from './../environments/environment';
import { HttpServiceUsers } from './http.service.users';
import { ForumTopic, Comments } from './InterfacesPortal';
import { Observable } from 'rxjs';
import { ForumSection, ForumSubSections } from 'src/app/InterfacesPortal';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class HttpServiceForum {



    constructor(private httpClient: HttpClient, private httpServiceUsers: HttpServiceUsers) {
    }

    forumApiUrl = environment.baseURL+'api/v1/Forum/';
    httpOptions = this.httpServiceUsers.currenthttpOptions();


    //#region Get Data
    getMainForum(): Observable<Array<ForumSection>> {
        return this.httpClient.get<Array<ForumSection>>(this.forumApiUrl + 'Generate');
    }

    getSubSection(id: number): Observable<ForumSubSections> {
        return this.httpClient.get<ForumSubSections>(this.forumApiUrl + 'SubSection/' + id);
    }
    getTopic(id: number): Observable<ForumTopic> {
        return this.httpClient.get<ForumTopic>(this.forumApiUrl + 'Topics/' + id);
    }

    getPost(commentId: number): Observable<Comments> {
        return this.httpClient.get<Comments>(this.forumApiUrl + 'Comments/' + commentId);
    }
    //#endregion

    //#region Add Data
    addMainForum(id: number, data: any) { }
    addTopic(id: number, data: any) { }

    addPost(id: number, data: any) {
        return this.httpClient.post(this.forumApiUrl + 'Topic/' + id + '/TopicComm', data, this.httpOptions);
    }

    //#endregion

    //#region Delete Data
    deleteMainForum(id: number) { }
    deleteSubSection(id: number) { }
    deletTopic(Topicid: number) {
        return this.httpClient.delete(this.forumApiUrl + 'Topic/' + Topicid);
    }


    deletePost(commentId: number) {
        return this.httpClient.delete(this.forumApiUrl + 'Comments/' + commentId);
    }
    //#endregion

    //#region Update Data
    updateMainForum(id: number, data: any) { }

    updateSubSection(id: number, data: any) { }

    updateTopic(id: number, data: any) {
        return this.httpClient.put(this.forumApiUrl + 'ForumTopic/' + id, data);
    }
    updatePost(editObjectId: number, data: any) {
        return this.httpClient.put(this.forumApiUrl + 'ForumTopicComm/' + editObjectId, data);
    }

    getLastTopic(): Observable<Array<ForumTopic>> {
        return this.httpClient.get<Array<ForumTopic>>(this.forumApiUrl + 'ForumLastTopic/2');
    }

    getAllTopic(): Observable<Array<ForumTopic>> {
        return this.httpClient.get<Array<ForumTopic>>(this.forumApiUrl + 'Topics');
    }
    createTopic(data: any) {
        console.log(data)
        return this.httpClient.post(this.forumApiUrl + 'SubSection/Topic', data);
    }
    //#endregion













}
