import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'web-ecommerce';
  url: string = "../assets/img1.jpg";
    imageChange(event: any){
        this.url = event.target.src;
    }
}
