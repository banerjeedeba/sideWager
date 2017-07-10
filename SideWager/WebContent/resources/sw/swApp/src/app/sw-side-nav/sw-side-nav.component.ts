import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../provider/auth.service';

@Component({
  selector: 'app-sw-side-nav',
  templateUrl: './sw-side-nav.component.html',
  styleUrls: ['./sw-side-nav.component.css']
})
export class SwSideNavComponent implements OnInit {

 constructor(public authService: AuthService,private router:Router) { }

  ngOnInit() {
  }
  logout(){
    console.log('signout worked sidenav');
    this.authService.Logout();
    this.router.navigate(['login']);
    console.log('signout worked sidenav end');
  }
}
