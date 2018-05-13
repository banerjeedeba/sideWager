import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { LiveWager } from '../entities/LiveWager';
import { FirebaseObjectObservable } from 'angularfire2/database';
import { WagerService } from '../provider/wager.service';

@Component({
  selector: 'app-make-wager-step-5-live',
  templateUrl: './make-wager-step-5-live.component.html',
  styleUrls: ['./make-wager-step-5-live.component.css'],
  providers: [WagerService]
})
export class MakeWagerStep5LiveComponent implements OnInit {

  public tempWager:FirebaseObjectObservable<LiveWager>;
  public twager:LiveWager;
  public selectedTeam;
  public selectedShortName;
  public uoValue;
  public opTeam;
  public amount;
  public selectedFrnd;
  constructor(private router:Router,private wagerService:WagerService) { }
  ngOnInit() {
    this.tempWager = this.wagerService.getTempWagers();
    this.tempWager.subscribe(snapshot=>{
      this.twager = snapshot;
    })
    if(this.twager.game.awayTeamShortName==this.twager.selectedTeam){
      this.selectedTeam = this.twager.game.awayTeam;
      this.selectedShortName = this.twager.game.awayTeamShortName;
      this.opTeam = this.twager.game.homeTeam;
    }
    
    if(this.twager.game.homeTeamShortName==this.twager.selectedTeam){
      this.selectedTeam = this.twager.game.homeTeam;
      this.selectedShortName = this.twager.game.homeTeamShortName;
      this.opTeam = this.twager.game.awayTeam;
    }

    if(this.twager.uoValue!=null){
      this.uoValue = this.twager.uoValue;
    }
  }

  gotoTabs()
  {
  this.router.navigate(['home','mkwagerstep4']);
  }
  gotoStep5(){
    this.router.navigate(['home','mkwagerstep5live']);
    this.wagerService.createLiveWager(this.twager.game, this.twager.selectedTeam, this.uoValue, this.twager.amount);
    this.wagerService.removeTempWager();
  }
}
