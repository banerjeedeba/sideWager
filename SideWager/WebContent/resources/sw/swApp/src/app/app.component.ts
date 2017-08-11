import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from './provider/auth.service';
import {User} from './entities/User';
import {UpdateUser} from './provider/updateuser.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [UpdateUser]
})
export class AppComponent {
  private isLoggedin:Boolean;
  public userObj: User;
   constructor(public authService: AuthService,private router:Router, private updateUser: UpdateUser) { 
   this.authService.af.authState.subscribe(
     (usertemp)=>{
       if(usertemp==null){
         this.isLoggedin=false;
         this.router.navigate(['']);
       }
       else{
         this.userObj = new User;
         const key: string = usertemp.uid;
         this.userObj.ckey = usertemp["na"].Da;
         this.userObj.expTime = usertemp["na"].Ja;
         this.userObj.displayName = usertemp.displayName;
         this.userObj.email = usertemp.email;
         this.userObj.photoUrl = usertemp.photoURL;
         this.updateUser.setItem(this.userObj, key);
         this.isLoggedin=true;
         this.router.navigate(['home','swtab']);
       }
     }
   )
   }
}
