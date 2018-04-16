import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
@Component({
  selector: 'app-make-wager-step-2',
  templateUrl: './make-wager-step-2.component.html',
  styleUrls: ['./make-wager-step-2.component.css']
})
export class MakeWagerStep2Component implements OnInit {

  constructor(private router:Router) { }

  ngOnInit() {
  }
gotoTabs()
{
this.router.navigate(['home','swtab']);
}
gotoStep4(){
  this.router.navigate(['home','mkwagerstep4']);
}
}
