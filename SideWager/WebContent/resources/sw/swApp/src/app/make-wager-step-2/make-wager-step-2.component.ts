import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { WagerService } from '../provider/wager.service';
import { FirebaseObjectObservable } from 'angularfire2/database';
import { LiveWager } from '../entities/LiveWager';
@Component({
  selector: 'app-make-wager-step-2',
  templateUrl: './make-wager-step-2.component.html',
  styleUrls: ['./make-wager-step-2.component.css'],
  providers: [WagerService]
})
export class MakeWagerStep2Component implements OnInit {

  public tempWager:FirebaseObjectObservable<LiveWager>;
  public twager:LiveWager;
  public selectedTeam;
  public uoValue;
  public opTeam;
  constructor(private router:Router,private wagerService:WagerService ) { }

  ngOnInit() {
    this.tempWager = this.wagerService.getTempWagers();
    this.tempWager.subscribe(snapshot=>{
      this.twager = snapshot;
    })
    if(this.twager.game.awayTeamShortName==this.twager.selectedTeam){
      this.selectedTeam = this.twager.game.awayTeam;
      this.opTeam = this.twager.game.homeTeam;
    }
    
    if(this.twager.game.homeTeamShortName==this.twager.selectedTeam){
      this.selectedTeam = this.twager.game.homeTeam;
      this.opTeam = this.twager.game.awayTeam;
    }

    if(this.twager.uoValue !=null){
      this.uoValue = this.twager.uoValue;
    }
  }
gotoTabs()
{
this.router.navigate(['home','mkwagerstep1']);
}
gotoStep4(){
  this.router.navigate(['home','mkwagerstep4']);
  
}
gotoStep3(){
  this.router.navigate(['home','mkwagerstep3']);
  
}
}
