import { environment } from './../environments/environment.prod';
import { Observable } from 'rxjs';
import { News, Comments, Cpu, Gpu, NewsDTO } from './InterfacesPortal';
import { HttpServiceUsers } from './http.service.users';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, Component } from '@angular/core';

@Injectable()
export class HttpServicePortal {

    constructor(private httpClient: HttpClient, private httpServiceUsers: HttpServiceUsers) { }

    apiPortalUrl = environment.baseURL+'api/v1/Portal';
    apiUrl = environment.baseURL+'api/v1/Portal/News';

    apiCpuSearchUrl = this.apiPortalUrl + '/Search/Cpu';
    apiGpuSearchUrl = this.apiPortalUrl + '/Search/Gpu';

    httpOptions = this.httpServiceUsers.currenthttpOptions();

    //#region get
    newsItems() {
        return this.httpClient.get(this.apiUrl + '/Size')
    }
    newsList(page, size): Observable<Array<News>> {
        let params =
        {
            page: page,
            size: size
        };
        return this.httpClient.get<Array<News>>(this.apiUrl, { params });
    }
    newsOne(id: number): Observable<News> {
        return this.httpClient.get<News>(this.apiUrl + '/' + id);
    }
    theBestNews(id: number): Observable<Array<NewsDTO>> {
        return this.httpClient.get<Array<NewsDTO>>(this.apiPortalUrl + '/BestArticle/' + id);
    }

    last10News(): Observable<Array<News>> {
        return this.httpClient.get<Array<News>>(this.apiUrl + '/Last10');
    }
    commentsList(): Observable<Array<Comments>> {
        return this.httpClient.get<Array<Comments>>(this.apiPortalUrl + '/Comments');
    }
    loadComment(id: number) {
        return this.httpClient.get<Comments>(this.apiPortalUrl + '/Comment/' + id);
    }

    loadListCpu(): Observable<Array<Cpu>> {
        return this.httpClient.get<Array<Cpu>>(this.apiCpuSearchUrl);
    }

    loadListGpu(): Observable<Array<Gpu>> {
        return this.httpClient.get<Array<Gpu>>(this.apiGpuSearchUrl);
    }

    searchCPUbyGPU(id: number): Observable<Array<Cpu>> {
        return this.httpClient.get<Array<Cpu>>(this.apiGpuSearchUrl + '/' + id + '/FindCpu');
    }
    searchGPUbyCPU(id: number): Observable<Array<Gpu>> {
        return this.httpClient.get<Array<Gpu>>(this.apiCpuSearchUrl + '/' + id + '/FindGpu');
    }
    recomendCPU(): Observable<Array<Cpu>> {
        return this.httpClient.get<Array<Cpu>>(this.apiCpuSearchUrl + '/Top');
    }

    recomendGPU(): Observable<Array<Gpu>> {
        return this.httpClient.get<Array<Gpu>>(this.apiGpuSearchUrl + '/Top');
    }

    //#endregion

    //#region add
    commentPost(id: number, data: object) {
        return this.httpClient.post(this.apiUrl + '/' + id + '/Comment', data);
    }
    uploadNews(image: any) {
        return this.httpClient.post(this.apiUrl, image);
    }
    rateArticle(id: number, rate: number) {
        const data = {
            rating: rate
        };
        console.log('Podane waertosci' + id + ' ' + rate);
        return this.httpClient.post(this.apiPortalUrl + '/RateArticle/News/' + id, data);
    }
    cpuToGpu(idCpu: number, idGpu: number) {
        let data: any;
        return this.httpClient.post(this.apiCpuSearchUrl + '/' + idCpu + '/Gpu/' + idGpu, data);
    }

    saveCPU(data: any) {
        return this.httpClient.post(this.apiCpuSearchUrl, data);
    }
    saveGPU(data: any) {
        return this.httpClient.post(this.apiGpuSearchUrl, data);
    }

    //#endregion

    //#region delete
    deleteNews(id: number) {
        return this.httpClient.delete(this.apiUrl + '/' + id);
    }
    deleteComm(id: number) {
        return this.httpClient.delete(this.apiPortalUrl + '/Comment/' + id);
    }
    deleteCpu(id: number) {
        return this.httpClient.delete(this.apiCpuSearchUrl + '/' + id);
    }
    deleteGpu(id: number) {
        return this.httpClient.delete(this.apiGpuSearchUrl + '/' + id);
    }
    //#endregion

    //#region update
    updateNews(id: number, data: object) {
        return this.httpClient.put(this.apiUrl + '/' + id, data);
    }

    saveEditComments(id: number, data: any) {
        return this.httpClient.put(this.apiPortalUrl + '/Comment/' + id, data);
    }

    editCPU(id: number, cpu: Cpu) {
        return this.httpClient.put(this.apiCpuSearchUrl + '/' + id, cpu);
    }

    editGPU(id: number, gpu: Gpu) {
        return this.httpClient.put(this.apiCpuSearchUrl + '/' + id, gpu);
    }

    //#endregion

}



