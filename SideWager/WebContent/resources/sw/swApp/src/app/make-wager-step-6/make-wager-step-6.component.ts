import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { FirebaseObjectObservable } from 'angularfire2/database';
import { LiveWager } from '../entities/LiveWager';
import { WagerService } from '../provider/wager.service';
@Component({
  selector: 'app-make-wager-step-6',
  templateUrl: './make-wager-step-6.component.html',
  styleUrls: ['./make-wager-step-6.component.css'],
  providers: [WagerService]
})
export class MakeWagerStep6Component implements OnInit {

  public tempWager:FirebaseObjectObservable<LiveWager>;
  public twager:LiveWager;
  public selectedTeam;
  public selectedFullName;
  public uoValue;
  public opTeam;
  public amount;
  public betAmount;
  public selectedFrnd;
  public errorMessage;
  constructor(private router:Router,private wagerService:WagerService) { }
  ngOnInit() {
    this.tempWager = this.wagerService.getTempAcceptLiveWagers();
    this.tempWager.subscribe(snapshot=>{
      this.twager = snapshot;
    })
    if(this.twager.game.homeTeamShortName==this.twager.selectedTeam){
      this.selectedTeam = this.twager.game.awayTeamShortName;
    } else if(this.twager.game.awayTeamShortName==this.twager.selectedTeam){
      this.selectedTeam = this.twager.game.homeTeamShortName
    }
    if(this.twager.game.awayTeamShortName==this.selectedTeam){
      this.selectedFullName = this.twager.game.awayTeam;
      this.opTeam = this.twager.game.homeTeam;
    }
    
    if(this.twager.game.homeTeamShortName==this.selectedTeam){
      this.selectedFullName = this.twager.game.homeTeam;
      this.opTeam = this.twager.game.awayTeam;
    }

    if(this.twager.uoValue!=null){
      if(this.twager.uoValue.startsWith('o'))
      {
        this.uoValue = this.twager.uoValue;
        this.uoValue = this.uoValue.replace("o","u");
      } else if(this.twager.uoValue.startsWith('u'))
      {
        this.uoValue = this.twager.uoValue;
        this.uoValue = this.uoValue.replace("u","o");
      }
    }

  }

  gotoTabs()
  {
  this.router.navigate(['home','swtab']);
  }
  gotoStep7(wager:LiveWager){
    if (this.betAmount==null ||this.betAmount =='' || this.betAmount==0) {
      this.errorMessage='Please enter a valid amount!';
      return;
    }
    this.router.navigate(['home','mkwagerstep7']);
    wager.betamount=this.betAmount;
    this.wagerService.createTempOpenWagerFromLiveWager(wager);
  }

  close(){
    this.router.navigate(['home','swtab']);
  }
}
