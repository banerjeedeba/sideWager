import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
@Component({
  selector: 'app-make-wager-step-6',
  templateUrl: './make-wager-step-6.component.html',
  styleUrls: ['./make-wager-step-6.component.css']
})
export class MakeWagerStep6Component implements OnInit {

  constructor(private router:Router) { }

  ngOnInit() {
  }

  gotoTabs()
  {
  this.router.navigate(['home','mkwagerstep5live']);
  }
  gotoStep7(){
    this.router.navigate(['home','mkwagerstep7']);
  }
}
