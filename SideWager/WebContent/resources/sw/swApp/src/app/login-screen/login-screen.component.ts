import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../provider/auth.service';
@Component({
  selector: 'app-login-screen',
  templateUrl: './login-screen.component.html',
  styleUrls: ['./login-screen.component.css']
})
export class LoginScreenComponent implements OnInit {

  public email: string;
  public password: string;
  constructor(public authService: AuthService,private router:Router) { }

  ngOnInit() {
  }
  loginWithGoogle(){
  this.authService.LoginWithGoogle().then((data)=>{
    this.router.navigate(['home']);
  })
  }

  loginWithFaceBook(){
  this.authService.LoginWithFaceBook().then((data)=>{
    this.router.navigate(['home']);
  })
  }

  loginWithEmail(){
  this.authService.LoginWithEmail(this.email,this.password).then((data)=>{
    //this.router.navigate(['']);
  })
  }

  registerWithEmail(){
  this.authService.RegisterWithEmail(this.email,this.password).then((data)=>{
    //this.router.navigate(['']);
  })
  }
  
}
