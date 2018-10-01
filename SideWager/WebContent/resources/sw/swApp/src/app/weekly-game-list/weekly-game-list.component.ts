import { Component, OnInit } from '@angular/core';
import { OpenWager } from "../entities/OpenWager";
import {WagerService} from '../provider/wager.service';
import { FirebaseListObservable } from 'angularfire2/database';
import { WagerRequestConfirmModalComponent } from '../wager-request-confirm-modal/wager-request-confirm-modal.component';
import {OpenWagerTabCountService} from '../provider/open-wager-tab-count.service';

@Component({
  selector: 'app-weekly-game-list',
  templateUrl: './weekly-game-list.component.html',
  styleUrls: ['./weekly-game-list.component.css'],
  providers: [WagerService]
})
export class WeeklyGameListComponent implements OnInit {

  public openWagers :Array<OpenWager> = new Array();
  public openWagersList :OpenWager[];
  openWagerSubscribe;
  challengerOpenWagerSubscribe;

  public openWagerCount: number = 0;
  public openWagerTabCount: string = "0";
  constructor(private wagerService:WagerService,private openWagerTabCountService:OpenWagerTabCountService) { }

  ngOnInit() {
    this.openWagerSubscribe = this.wagerService.getAllOpenWagers().subscribe(openWagersListSnapshot=>{
      this.openWagersList = openWagersListSnapshot;
      this.openWagerTabCount = this.openWagersList.length.toString();
      //this.clearTabcount();
      this.sendTabCount();
    })
  }
  sendTabCount(): void {
    // send message to subscribers via observable subject
    this.openWagerTabCountService.setTabCount(this.openWagerTabCount.toString());
  }
  private todaysDate=new Date(Date.now());
  currentDate = new Date( this.todaysDate.getFullYear(),
                   this.todaysDate.getMonth(),
                   this.todaysDate.getDate());
  nextDate(){
    this.currentDate = new Date( this.currentDate.getFullYear(),
                   this.currentDate.getMonth(),
                   this.currentDate.getDate()+7);
  
  }
  prevDate(){
       this.currentDate = new Date( this.currentDate.getFullYear(),
                   this.currentDate.getMonth(),
                   this.currentDate.getDate()-7);
  }
}
