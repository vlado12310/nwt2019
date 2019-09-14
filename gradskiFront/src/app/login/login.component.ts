import { Component, OnInit } from '@angular/core';
import { LoginService } from '../services/login.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username : string;
  password : string;

  constructor(private loginService : LoginService) { }

  login() : void{
    this.loginService.login(this.username, this.password);
  }
  ngOnInit() {
    this.username = "";
    this.password = "";
  }

}
