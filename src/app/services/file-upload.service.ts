import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';



@Injectable({
  providedIn: 'root'
})

export class FileUploadService {
  getFilesById(id: String) : Observable<any> {
    return this.http.get(`${this.baseUrl}/filesbyidproduct/${id}`);
  }

  //private baseUrl = 'http://localhost:8091/api/fmanager';
  private baseUrl = 'http://localhost:8080/services/photodb/api/fmanager';
  constructor(private http: HttpClient) { }

  upload(file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    
    const oldName = file.name;
    const fileExtension = oldName.slice(oldName.lastIndexOf('.') - oldName.length);
    const newname = Date.now() + fileExtension;



    formData.append('files', file, newname);

    const req = new HttpRequest('POST', `${this.baseUrl}/upload`, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.http.request(req);
  }

  getFiles(): Observable<any> {
    return this.http.get(`${this.baseUrl}/files`);
  }
}
