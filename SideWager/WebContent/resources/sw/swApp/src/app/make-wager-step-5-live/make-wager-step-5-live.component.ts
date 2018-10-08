import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { LiveWager } from '../entities/LiveWager';
import { FirebaseObjectObservable } from 'angularfire2/database';
import { WagerService } from '../provider/wager.service';
import { MdDialog, MdDialogRef, MD_DIALOG_DATA} from '@angular/material';
import { WagerRequestConfirmModalComponent } from '../wager-request-confirm-modal/wager-request-confirm-modal.component';

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
  constructor(private router:Router,private wagerService:WagerService, public dialog: MdDialog) { }
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

    if(this.twager.opName != null){
      this.selectedFrnd = this.twager.opName;
    }
    
  }

  gotoTabs()
  {
  this.router.navigate(['home','mkwagerstep4']);
  }
  gotoStep5(){
    
    this.router.navigate(['home','swtab']);
    if(this.twager.opName != null){
      this.wagerService.createOpenWager(this.twager.game, this.twager.selectedTeam,this.twager.selected,this.uoValue,this.twager.amount,this.twager.opName,this.twager.opKey)
    } else {
      this.wagerService.createLiveWager(this.twager.game, this.twager.selectedTeam,this.twager.selected, this.uoValue, this.twager.amount);
    }
    this.wagerService.removeTempWager();
  }

  isConfirm: boolean;

  openDialog(): void {
    let dialogRef = this.dialog.open(WagerRequestConfirmModalComponent, {
      width: '250px',
      data: { isConfirm: this.isConfirm }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.isConfirm = result;
      console.log('The dialog was closed : '+this.isConfirm);
      if(this.isConfirm){
        this.gotoStep5();
      }
    });
  }

  close(){
    this.router.navigate(['home','swtab']);
  }
}
