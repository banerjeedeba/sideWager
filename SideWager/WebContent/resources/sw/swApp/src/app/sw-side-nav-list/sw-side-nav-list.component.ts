import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-sw-side-nav-list',
  templateUrl: './sw-side-nav-list.component.html',
  styleUrls: ['./sw-side-nav-list.component.css']
})
export class SwSideNavListComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit() {
  }
 getFriends(){
    this.router.navigate(['home','friends']);
  }
}
