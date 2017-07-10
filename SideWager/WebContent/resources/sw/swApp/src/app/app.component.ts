import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from './provider/auth.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  private isLoggedin:Boolean;
   constructor(public authService: AuthService,private router:Router) { 
   this.authService.af.authState.subscribe(
     (user)=>{
       if(user==null){
         this.isLoggedin=false;
         this.router.navigate(['login']);
       }
       else{
          this.isLoggedin=true;
          this.router.navigate(['']);
       }
     }
   )
   }
}
