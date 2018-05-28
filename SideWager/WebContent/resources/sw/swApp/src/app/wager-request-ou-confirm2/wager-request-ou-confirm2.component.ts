import { Component, OnInit } from '@angular/core';
import { AuthService } from '../provider/auth.service';
import { WagerService } from '../provider/wager.service';
import { FirebaseObjectObservable } from 'angularfire2/database';
import { LiveWager } from '../entities/LiveWager';
import { Router } from '@angular/router';
import { MdDialog, MdDialogRef, MD_DIALOG_DATA} from '@angular/material';
import { WagerRequestConfirmModalComponent } from '../wager-request-confirm-modal/wager-request-confirm-modal.component';

@Component({
  selector: 'app-wager-request-ou-confirm2',
  templateUrl: './wager-request-ou-confirm2.component.html',
  styleUrls: ['./wager-request-ou-confirm2.component.css'],
  providers: [WagerService]
})
export class WagerRequestOuConfirm2Component implements OnInit {

  public tempWager:FirebaseObjectObservable<LiveWager>;
  public twager:LiveWager;
  public selectedTeam;
  public uoValue;
  public opTeam;
  constructor(private wagerService:WagerService, private auth:AuthService,private router:Router, public dialog: MdDialog) {
    
   }

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

    if(this.twager.uoValue!=null){
      this.uoValue= this.twager.uoValue;
    }
  }
  animal: string;
  name: string;

  openDialog(): void {
    let dialogRef = this.dialog.open(WagerRequestConfirmModalComponent, {
      width: '250px',
      data: { name: this.name, animal: this.animal }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.animal = result;
    });
  }

  gotoConfirm(){

    // this.router.navigate(['home','swtab']);
    // this.wagerService.createLiveWager(this.twager.game, this.twager.selectedTeam, this.uoValue, this.twager.amount);
    // this.wagerService.removeTempWager();
  }

  gotoTabs(){
    
  }

}
