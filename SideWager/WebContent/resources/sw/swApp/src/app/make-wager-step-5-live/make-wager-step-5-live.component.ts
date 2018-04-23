import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-make-wager-step-5-live',
  templateUrl: './make-wager-step-5-live.component.html',
  styleUrls: ['./make-wager-step-5-live.component.css']
})
export class MakeWagerStep5LiveComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit() {
  }

  gotoTabs()
  {
  this.router.navigate(['home','mkwagerstep4']);
  }
  gotoStep5(){
    this.router.navigate(['home','mkwagerstep5live']);
  }
}
