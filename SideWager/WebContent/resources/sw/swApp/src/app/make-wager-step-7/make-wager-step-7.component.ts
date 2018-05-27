import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
@Component({
  selector: 'app-make-wager-step-7',
  templateUrl: './make-wager-step-7.component.html',
  styleUrls: ['./make-wager-step-7.component.css']
})
export class MakeWagerStep7Component implements OnInit {

  constructor(private router:Router)  { }

  ngOnInit() {

  }
  
  gotoTabs()
    {
    this.router.navigate(['home','swtab']);
    }
    gotoStep5(){
      this.router.navigate(['home','mkwagerstep7']);
   }
}
