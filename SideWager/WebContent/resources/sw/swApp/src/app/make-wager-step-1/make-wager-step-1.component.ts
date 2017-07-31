import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
@Component({
  selector: 'app-make-wager-step-1',
  templateUrl: './make-wager-step-1.component.html',
  styleUrls: ['./make-wager-step-1.component.css']
})
export class MakeWagerStep1Component implements OnInit {

  constructor(private router:Router) { }

  ngOnInit() {
  }
gotoTabs()
{
this.router.navigate(['home','swtab']);
}
}
