import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FileUploadService } from 'src/app/services/file-upload.service';

@Component({
  selector: 'app-upload-images',
  templateUrl: './upload-images.component.html',
  styleUrls: ['./upload-images.component.css']
})

export class UploadImagesComponent implements OnInit {
  @Input() index!: String;
  @Output() imageInfosChanged: EventEmitter<any> = new EventEmitter();
  selectedFiles?: FileList;
  progressInfos: any[] = [];
  message: string[] = [];

  previews: string[] = [];
  imageInfos?: Observable<any>;
  

  constructor(private uploadService: FileUploadService) {
    console.log("Hello !!!");
   }

  ngOnInit(): void {
    //this.imageInfos =    this.uploadService.getFiles();
    console.log("Hiiiiiiiiiiiiiiiiiiiiiiiiiiii");
    console.log("Hiiiiiiii ---- this.index : ", this.index);
    
    this.getById(this.index);
  }

  getById(id: String) {
    //this.imageInfos =    this.uploadService.getFiles();
    this.uploadService.getFilesById(id).subscribe((data) => {
      this.imageInfos = data;
    });
  }


  
  selectFiles(event: any): void {
    this.message = [];
    this.progressInfos = [];
    this.selectedFiles = event.target.files;

    this.previews = [];
    if (this.selectedFiles && this.selectedFiles[0]) {
      const numberOfFiles = this.selectedFiles.length;
      for (let i = 0; i < numberOfFiles; i++) {
        const reader = new FileReader();

        reader.onload = (e: any) => {
          console.log(e.target.result);
          this.previews.push(e.target.result);
        };

        reader.readAsDataURL(this.selectedFiles[i]);
      }
    }
  }

  upload(idx: number, file: File): void {
  
    this.progressInfos[idx] = { value: 0, fileName: file.name };
    if (file) {
      this.uploadService.upload(file).subscribe({
        next: (event: any) => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progressInfos[idx].value = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            const msg = 'Uploaded the file successfully: ' + file.name;
            this.message.push(msg);
            this.imageInfos = this.uploadService.getFiles();
            this.imageInfos.forEach(element => console.log("element : -----------",element) );
            this.imageInfosChanged.emit(this.imageInfos);
            console.log("Emit ok ......................",file.name);
          }
        },
        error: (err: any) => {
          this.progressInfos[idx].value = 0;
          const msg = 'Could not upload the file: ' + file.name;
          this.message.push(msg);
        }});
    }
  }

  uploadFiles(): void {
    this.message = [];

    if (this.selectedFiles) {
      for (let i = 0; i < this.selectedFiles.length; i++) {
        this.upload(i, this.selectedFiles[i]);
      }
    }
  }

}
