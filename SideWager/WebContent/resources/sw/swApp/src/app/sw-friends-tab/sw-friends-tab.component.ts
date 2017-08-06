import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-sw-friends-tab',
  templateUrl: './sw-friends-tab.component.html',
  styleUrls: ['./sw-friends-tab.component.css']
})
export class SwFriendsTabComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit() {
  }
gotoWager(){
 this.router.navigate(['home']);
}
}
