import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {ActivatedRoute} from '@angular/router';
import {AuthService} from '../provider/auth.service';

@Component({
  selector: 'app-sw-side-nav',
  templateUrl: './sw-side-nav.component.html',
  styleUrls: ['./sw-side-nav.component.css']
})
export class SwSideNavComponent implements OnInit {
private wager;
 constructor(public authService: AuthService,private router:Router,private activeroute:ActivatedRoute) { }

  ngOnInit() {
    // const wagerType=this.activeroute.snapshot.params['wagerType'];
    // this.wager=wagerType;
  }
  logout(){
    this.authService.Logout();
    this.router.navigate(['login']);
  }
}