import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { WagerService } from '../provider/wager.service';
import { FirebaseObjectObservable } from 'angularfire2/database';
import { LiveWager } from '../entities/LiveWager';
import {FormControl, FormGroupDirective, NgForm, Validators} from '@angular/forms';

@Component({
  selector: 'app-make-wager-step-4',
  templateUrl: './make-wager-step-4.component.html',
  styleUrls: ['./make-wager-step-4.component.css'],
  providers: [WagerService]
})
export class MakeWagerStep4Component implements OnInit {

  public tempWager:FirebaseObjectObservable<LiveWager>;
  public twager:LiveWager;
  public selectedTeam;
  public selectedFullName;
  public uoValue;
  public opTeam;
  public amount;
  public selectedFrnd;
  public errorMessage; 

  constructor(private router:Router,private wagerService:WagerService) { }

  ngOnInit() {
    this.tempWager = this.wagerService.getTempWagers();
    this.tempWager.subscribe(snapshot=>{
      this.twager = snapshot;
    })

    this.selectedTeam = this.twager.selectedTeam;
    if(this.twager.game.awayTeamShortName==this.twager.selectedTeam){
      this.selectedFullName = this.twager.game.awayTeam;
      this.opTeam = this.twager.game.homeTeam;
    }
    
    if(this.twager.game.homeTeamShortName==this.twager.selectedTeam){
      this.selectedFullName = this.twager.game.homeTeam;
      this.opTeam = this.twager.game.awayTeam;
    }

    if(this.twager.uoValue!=null){
      this.uoValue = this.twager.uoValue;
    }

    if(this.twager.opName != null){
      this.selectedFrnd = this.twager.opName;
    }

    if(this.twager.amount!=null){
      this.amount = this.twager.amount;
    }
  }
  gotoTabs()
  {
  this.router.navigate(['home','mkwagerstep2']);
  }
  gotoStep5(){
    if (this.amount==null ||this.amount =='' || this.amount==0) {
      this.errorMessage='Please enter the amount!';
      return;
    }
    this.router.navigate(['home','mkwagerstep5live']);
    if(this.twager.opName != null){
      this.wagerService.createTempOpenWager(this.twager.game,this.twager.selectedTeam,this.twager.selected,this.uoValue,this.amount,this.twager.opKey,this.twager.opName);
    } else {
      this.wagerService.createTempWager(this.twager.game,this.twager.selectedTeam,this.twager.selected,this.uoValue,this.amount);
    }
    
  }

  close(){
    this.router.navigate(['home','swtab']);
  }
}
