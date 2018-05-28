import { Component, OnInit, OnDestroy } from '@angular/core';
import {Router} from '@angular/router';
import {WagerService} from '../provider/wager.service';
import {UpdateUser} from '../provider/updateuser.service';
import { LiveWager } from "../entities/LiveWager";
@Component({
  selector: 'app-live-wagers',
  templateUrl: './live-wagers.component.html',
  styleUrls: ['./live-wagers.component.css'],
  providers: [WagerService]
})
export class LiveWagersComponent implements OnInit, OnDestroy {

  public liveWagers :Array<LiveWager> = new Array();

  friendListSubscribe;
  liveWagersSubscribe;
  constructor(private router:Router, private wagerService:WagerService,private user : UpdateUser) { }
  accept(wager:LiveWager, wagerKey:string){
    this.router.navigate(['home','mkwagerstep6']);
    wager.wagerKey = wagerKey;
    this.wagerService.createTempOpenWagerFromLiveWager(wager);
  }
  ngOnInit() {
    this.friendListSubscribe = this.user.getFriendList().subscribe(friends => {
      for(let friend of friends){
        let friendKey = friend['$key'];
        this.liveWagersSubscribe = this.wagerService.getLiveWagers(friendKey).subscribe(friendsLiveWagers=>{
          for(let friendsLiveWager of friendsLiveWagers){
            this.liveWagers.push(friendsLiveWager); 
          }
          this.liveWagersSubscribe.unsubscribe();
        })
      }
      this.friendListSubscribe.unsubscribe();
    })

  }

  ngOnDestroy(){
    if(this.friendListSubscribe!=undefined){
      this.friendListSubscribe.unsubscribe();
    }
    if(this.liveWagersSubscribe!=undefined){
      this.liveWagersSubscribe.unsubscribe();
    }
  }
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
