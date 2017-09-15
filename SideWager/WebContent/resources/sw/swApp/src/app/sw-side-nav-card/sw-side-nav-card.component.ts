import { Component, OnInit } from '@angular/core';
import {UpdateUser} from '../provider/updateuser.service';

@Component({
  selector: 'app-sw-side-nav-card',
  templateUrl: './sw-side-nav-card.component.html',
  styleUrls: ['./sw-side-nav-card.component.css']
})
export class SwSideNavCardComponent implements OnInit {

  public displayName : string;
  public photoUrl: string;
  constructor(private user : UpdateUser) {
    if(this.user!=undefined && this.user.user!=undefined){
      this.displayName = this.user.user.displayName;
      this.photoUrl = this.user.user.photoUrl;
    }
   }
   
  ngOnInit() {
  }

}
