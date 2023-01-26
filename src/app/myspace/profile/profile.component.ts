import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/user';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  public user : User 
    = {
      id :'',
      username :'',
      email:'',
      password:'',
    };

    public user1 : User 
    = {
      id :'',
      username :'ali baba',
      email:'coucou',
      password:'',
    };

    public email:string='';

  constructor(private authService: AuthService,) { 
    
  }

  ngOnInit(): void {
    this.loadprofile();
  }

  loadprofile():void{
    this.authService.account().subscribe((data) => {
      this.user = data;

      this.email = data.email ;
      console.log(this.email);
    });    
  }

}
