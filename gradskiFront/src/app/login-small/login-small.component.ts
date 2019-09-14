import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import {Router} from "@angular/router";
import { LoginService } from '../services/login.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-login-small',
  templateUrl: './login-small.component.html',
  styleUrls: ['./login-small.component.css']
})
export class LoginSmallComponent implements OnInit {

  constructor(
    private loginService : LoginService, 
    private router: Router, 
    private userService : UserService) { }
  
  
  passwordFormControl = new FormControl('', [
    Validators.required
  ]);

  usernameFormControl = new FormControl('', [
    Validators.required,
    Validators.email
  ]);

  onClick(){
    let username = this.usernameFormControl.value;
    let password = this.passwordFormControl.value;
    this.passwordFormControl.markAsTouched();
    this.usernameFormControl.markAsTouched(); 
   if(username.trim().length>0 && password.trim().length>0){
    this.loginService.login(username.trim(), password.trim());
   }
      
  }
  onKey(event: any) { // without type info
    if(event.key == "Enter"){
      this.onClick();
    }
  }
  
  ngOnInit() {
    this.userService.getAll().subscribe();
  }

}
