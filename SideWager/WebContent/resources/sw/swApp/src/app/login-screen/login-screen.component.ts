import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../provider/auth.service';
@Component({
  selector: 'app-login-screen',
  templateUrl: './login-screen.component.html',
  styleUrls: ['./login-screen.component.css']
})
export class LoginScreenComponent implements OnInit {

  constructor(public authService: AuthService,private router:Router) { }

  ngOnInit() {
  }
  login(){
  this.authService.LoginWithGoogle().then((data)=>{
    this.router.navigate(['']);
  })
  }
}
