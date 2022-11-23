import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators ,FormGroup} from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
    form:any={
        username:null,
        email:null,
        password:null
    };

      isSuccessful = false;
      isSignUpFailed = false;
      errorMessage = '';

  constructor(private authService: AuthService,private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
        username: ['',Validators.required],
        email: ['',Validators.required],
        password: ['',Validators.required],
       
      });
  }
  
  onSubmit(): void {
    const { username, email, password } = this.form;

    this.authService.register(username, email, password).subscribe({
      next: (data: any) => {
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

}