import { Component, OnInit } from '@angular/core';
import { MdDialog, MdDialogRef, MD_DIALOG_DATA} from '@angular/material';
import { OpenWager } from "../entities/OpenWager";
import {WagerService} from '../provider/wager.service';
import { FirebaseListObservable } from 'angularfire2/database';
import { WagerRequestConfirmModalComponent } from '../wager-request-confirm-modal/wager-request-confirm-modal.component';

@Component({
  selector: 'app-open-wagers-requests',
  templateUrl: './open-wagers-requests.component.html',
  styleUrls: ['./open-wagers-requests.component.css'],
  providers: [WagerService]
})
export class OpenWagersRequestsComponent implements OnInit {

  public openWagers :Array<OpenWager> = new Array();
  public openWagersList :OpenWager[];
  openWagerSubscribe;
  challengerOpenWagerSubscribe;

  public openWagerCount: number = 0;

  constructor(private wagerService:WagerService,public dialog: MdDialog) { }

  ngOnInit() {
    this.openWagerSubscribe = this.wagerService.getPendingOpenWagers().subscribe(openWagersListSnapshot=>{
      this.openWagersList = openWagersListSnapshot;
      
      /*for(let openWager of openWagersListSnapshot){
        this.openWagers.push(openWager);
        this.openWagerCount++;
      }*/
      
    })
  }

  
  isConfirmValue: boolean;

  openDialog(wager:OpenWager , key:string): void {
    let dialogRef = this.dialog.open(WagerRequestConfirmModalComponent, {
      width: '250px',
      data: { isConfirm: this.isConfirmValue }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.isConfirmValue = result;
      console.log('The dialog was closed : '+this.isConfirmValue);
      if(this.isConfirmValue){
        this.accept(wager,key);
      }
    });
  }

accept(wager:OpenWager , key:string){
  this.challengerOpenWagerSubscribe = this.wagerService.getChallengerOpenWagers(wager.userKey,wager.game.matchDate).subscribe(openWagersList=>{
    openWagersList.forEach(challengerOpenWager=>{
      if(wager.selectedTeam==challengerOpenWager.selectedTeam
        && wager.uoValue == challengerOpenWager.uoValue
        && wager.game.matchDate == challengerOpenWager.game.matchDate){
          let challengerwager:any=challengerOpenWager;
          this.wagerService.acceptOpenWager(wager,key,challengerOpenWager,challengerwager.$key);
          this.challengerOpenWagerSubscribe.unsubscribe();
      }
    })  
  })
}

reject(wager:OpenWager , key:string){
  this.challengerOpenWagerSubscribe = this.wagerService.getChallengerOpenWagers(wager.userKey,wager.game.matchDate).subscribe(openWagersList=>{
    openWagersList.forEach(challengerOpenWager=>{
      if(wager.selectedTeam==challengerOpenWager.selectedTeam
        && wager.uoValue == challengerOpenWager.uoValue
        && wager.game.matchDate == challengerOpenWager.game.matchDate){
          let challengerwager:any=challengerOpenWager;
          this.wagerService.rejectOpenWager(wager,key,challengerOpenWager,challengerwager.$key);
          this.challengerOpenWagerSubscribe.unsubscribe();
      }
    })  
  })
}

ngOnDestroy(){
  if(this.openWagerSubscribe!=undefined){
    this.openWagerSubscribe.unsubscribe();
  }
}
  
public listFrinds=[
  {
    "Name":"Brent Boner",
    "imgSource":"..\\assets\\img\\user.png",
    "HTP":"56",
    "HTPind":"Red",
    "Amount":"250"
  },
  {
    "Name":"Kyle diPretoro",
    "imgSource":"..\\assets\\img\\user.png",
    "HTP":"25",
    "HTPind":"Amber",
    "Amount":"150"
  },
  {
    "Name":"Michael Adams John",
    "imgSource":"..\\assets\\img\\user.png",
    "HTP":"5",
    "HTPind":"Green",
    "Amount":"650"
  },
];

public gamesList=[{
  "Team1FullName":"Toranto",
  "Team2FullName":"Chicago",
  "Team1Name":"TOR",
  "Team2Name":"CHI",
  "Team1Ind":"TOR-6.5",
  "Team2Ind":"u 102.5",
  "Time":"Aug 15 8:00PM",
},
{
  "Team1FullName":"Cavaliers",
  "Team2FullName":"Minnesota",
  "Team1Name":"CLE",
  "Team2Name":"MIN",
  "Team1Ind":"CLE-4.5",
  "Team2Ind":"u 105.5",
  "Time":"Sep 10 8:00PM",
},
{
  "Team1FullName":"Sacremento",
  "Team2FullName":"Los Angeles",
  "Team1Name":"SAC",
  "Team2Name":"LAL",
  "Team1Ind":"TOR-6.5",
  "Team2Ind":"u 101",
  "Time":"Oct 17 10:30PM",
}];
}
