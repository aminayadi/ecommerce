import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators ,FormGroup} from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
    formRegister:any={
        username:null,
        email:null,
        password:null
    };

      isSuccessful = false;
      isSignUpFailed = false;
      errorMessage = '';

      formLogin: any = {
        username: null,
        password: null
      };
      isLoggedIn = false;
      isLoginFailed = false;
      errorMessage2 = '';
      roles: string[] = []; 

      

  constructor(private router: Router,
    private authService: AuthService,
    private formBuilder: FormBuilder, 
    private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    this.formRegister = this.formBuilder.group({
        username: ['',Validators.required],
        email: ['',Validators.required],
        password: ['',Validators.required],
       
      });

      this.formLogin = this.formBuilder.group({
        username: ['', Validators.required],
        password: ['', Validators.required]
    });
  
  
      if (this.tokenStorage.getToken()) {
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getUser().roles;
      } 
  }
  
  onSubmitRegister(): void {
    const { username, email, password } = this.formRegister;
    console.log("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    this.authService.register(username, email, password).subscribe({
      next: (data: any) => {
        console.log("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      error: (err: { error: { message: any; }; }) => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    });
  }

  onSubmitLogin(): void {
    const { username, password } = this.formLogin;
    console.log("test");

    this.authService.login(username, password).subscribe({
      next: data => {
        console.log("data : ", data); 
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getUser().roles;
        this.router.navigate(['myspace']);
      },
      error: err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    });
  }

  reloadPage(): void {
    window.location.reload();
  }

}