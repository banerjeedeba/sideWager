import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { FirebaseObjectObservable } from 'angularfire2/database';
import { LiveWager } from '../entities/LiveWager';
import { WagerService } from '../provider/wager.service';
import { MdDialog, MdDialogRef, MD_DIALOG_DATA} from '@angular/material';
import { WagerRequestConfirmModalComponent } from '../wager-request-confirm-modal/wager-request-confirm-modal.component';
@Component({
  selector: 'app-make-wager-step-7',
  templateUrl: './make-wager-step-7.component.html',
  styleUrls: ['./make-wager-step-7.component.css'],
  providers: [WagerService]
})
export class MakeWagerStep7Component implements OnInit {

  public tempWager:FirebaseObjectObservable<LiveWager>;
  public twager:LiveWager;
  public selectedTeam;
  public selectedShortName;
  public selectedFullName;
  public uoValue;
  public opTeam;
  public amount;
  public selectedFrnd;
  public result;
  constructor(public router:Router,public wagerService:WagerService, public dialog: MdDialog) { }

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
    this.router.navigate(['home','mkwagerstep6']);
    }
    gotoStep5(){
      
      //update live wager amount
      let liveWagerObject = this.wagerService.getLiveWager(this.twager.userKey, this.twager.wagerKey);
        let liveWagersubscribe = liveWagerObject.subscribe(liveWagerSnapshot=>{
            setTimeout( () => {
              liveWagersubscribe.unsubscribe();
              let liveWager:any = liveWagerSnapshot;
            
              if(liveWager.$value===null){
                  this.result = "Live wager is not available!!";
                  console.log(this.result);
                  return;
              }
              if(liveWager.amount > this.twager.betamount){
                  //update live wager amount
                this.wagerService.updateLiveWagerAmount(this.twager.game, this.twager.selectedTeam,this.uoValue,this.twager.betamount,this.twager.opName,this.twager.opKey,this.twager.userName, this.twager.userKey, this.twager.wagerKey, liveWager);
                if(this.wagerService.createAcceptLiveOpenWager(this.twager.game, this.twager.selectedTeam,this.twager.selected,this.uoValue,this.twager.betamount,this.twager.opName,this.twager.opKey,this.twager.userName, this.twager.userKey))
                {
                  this.wagerService.removeTempAcceptLiveWager();
                  this.router.navigate(['home','swtab']);
                }
                  
              } else if(liveWager.amount==this.twager.betamount){
                  //delete live wager
                  this.wagerService.removeLiveWager(this.twager.userKey, this.twager.wagerKey);
                  if(this.wagerService.createAcceptLiveOpenWager(this.twager.game, this.twager.selectedTeam,this.twager.selected,this.uoValue,this.twager.betamount,this.twager.opName,this.twager.opKey,this.twager.userName, this.twager.userKey)){
                    this.wagerService.removeTempAcceptLiveWager();
                    this.router.navigate(['home','swtab']);
                  }
              } else if(liveWager.amount < this.twager.betamount){
                this.result = "Live wager current available balance is "+liveWager.amount+" . Please select an amount within "+liveWager.amount;
                console.log(this.result);
              }
            }, 10 );
          })
   }

   isConfirmVal: boolean;

  openDialog(): void {
    let dialogRef = this.dialog.open(WagerRequestConfirmModalComponent, {
      width: '250px',
      data: { isConfirm: this.isConfirmVal }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.isConfirmVal = result;
      console.log('The dialog was closed : '+this.isConfirmVal);
      if(this.isConfirmVal){
        this.gotoStep5();
      }
    });
  }

  close(){
    this.router.navigate(['home','swtab']);
  }
}
