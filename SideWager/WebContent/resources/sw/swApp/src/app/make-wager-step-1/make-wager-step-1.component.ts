import { Component, OnInit } from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';
import { Game } from '../entities/Game';
import { WagerService } from '../provider/wager.service';
import { LiveWager } from '../entities/LiveWager';
import { FirebaseObjectObservable } from 'angularfire2/database';
@Component({
  selector: 'app-make-wager-step-1',
  templateUrl: './make-wager-step-1.component.html',
  styleUrls: ['./make-wager-step-1.component.css'],
  providers: [WagerService]
})
export class MakeWagerStep1Component implements OnInit {

  public tempWager:FirebaseObjectObservable<LiveWager>;
  public twager:LiveWager;
  constructor(private router:Router,private wagerService:WagerService ) { 
    window.scrollTo(1058, 1707);

  }

  ngOnInit() {
    this.tempWager = this.wagerService.getTempWagers();
    this.tempWager.subscribe(snapshot=>{
      this.twager = snapshot;
    })
  }
gotoTabs()
{
this.router.navigate(['home','swtab']);
}
gotoStep2(teamName){
  this.router.navigate(['home','mkwagerstep2']);
  this.wagerService.createTempWager(this.twager.game,teamName, null,null);
}
gotouoStep2(uoValue:string, game:Game){
  this.router.navigate(['home','mkwagerstep2']);
  this.wagerService.createTempWager(this.twager.game,null,uoValue+game.underLine,null);
}
}
