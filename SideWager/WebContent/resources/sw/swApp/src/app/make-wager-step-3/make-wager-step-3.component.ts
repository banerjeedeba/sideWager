import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { User } from '../entities/User';
import { UpdateUser } from '../provider/updateuser.service';
import { Subject } from 'rxjs';
import { LiveWager } from '../entities/LiveWager';
import { FirebaseObjectObservable } from 'angularfire2/database';
import { WagerService } from '../provider/wager.service';
@Component({
  selector: 'app-make-wager-step-3',
  templateUrl: './make-wager-step-3.component.html',
  styleUrls: ['./make-wager-step-3.component.css'],
  providers: [WagerService]
})
export class MakeWagerStep3Component implements OnInit {


  public friendList : User[] = [];
  public results : User[] = null;
  public resultStatus :any ={};
  lastKeyPress: number = 0;
  public currentUserEmail:string;
  startAt = new Subject();
  endAt = new Subject();

  friendListSubscribe;
  searchUserListSubscribe;
  isFriendSubscribe;

  constructor(private router:Router, private user:UpdateUser ,private wagerService:WagerService) { }

  public tempWager:FirebaseObjectObservable<LiveWager>;
  public twager:LiveWager;
  public selectedTeam;
  public selectedFullName;
  public uoValue;
  public opTeam;
  public amount;
  public selectedFrnd;
  ngOnInit() {
    this.friendListSubscribe = this.user.getFirstFewFriendList().subscribe(
      list =>{
        this.friendList = list;
      }
    )
    this.searchUserListSubscribe = this.user.getSearchFrndList(this.startAt,this.endAt).subscribe(data => {
      this.results = data;
      
    })

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
  }

  frndSearch($event){
    let searchString = $event.target.value.toLowerCase();
    if($event.timeStamp - this.lastKeyPress > 200 && searchString!=""){
        this.currentUserEmail = this.user.auth.user.email;
        this.startAt.next(searchString);
        this.endAt.next(searchString+"\uf8ff");
    }
    this.lastKeyPress = $event.timeStamp;
  }

  gotoTabs()
  {
  this.router.navigate(['home','mkwagerstep2']);
  }
  gotoStep4(frndKey,frndName){
    this.router.navigate(['home','mkwagerstep4']);
    this.wagerService.createTempOpenWager(this.twager.game,this.twager.selectedTeam,this.twager.selected,this.uoValue,this.amount,frndKey,frndName);
  }

  ngOnDestroy(){
    
    if(this.friendListSubscribe!=undefined){
    this.friendListSubscribe.unsubscribe();
    }

    if(this.searchUserListSubscribe!=undefined){
      this.searchUserListSubscribe.unsubscribe();
    }
    if(this.isFriendSubscribe!=undefined){
      this.isFriendSubscribe.unsubscribe();
    }
  }

  close(){
    this.router.navigate(['home','swtab']);
  }
}
