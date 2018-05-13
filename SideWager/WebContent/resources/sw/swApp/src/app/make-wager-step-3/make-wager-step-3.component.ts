import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
@Component({
  selector: 'app-make-wager-step-3',
  templateUrl: './make-wager-step-3.component.html',
  styleUrls: ['./make-wager-step-3.component.css']
})
export class MakeWagerStep3Component implements OnInit {

  constructor(private router:Router) { }

  ngOnInit() {
  }
  gotoTabs()
  {
  this.router.navigate(['home','mkwagerstep2']);
  }
  gotoStep5(){
    this.router.navigate(['home','mkwagerstep4']);
  }
}
