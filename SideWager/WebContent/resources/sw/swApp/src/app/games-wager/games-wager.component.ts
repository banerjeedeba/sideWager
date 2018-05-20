import { Component, OnInit, OnDestroy } from '@angular/core';
import {GamelistService} from '../provider/gamelist.service';
import {AuthService} from '../provider/auth.service';
import {Router} from '@angular/router';
import {UpdateUser} from '../provider/updateuser.service';
import {LoadingSpinnerComponent} from '../loading-spinner/loading-spinner.component';
import {WagerService} from '../provider/wager.service';
import {Sports} from '../entities/Sports'
import { Game } from "../entities/Game";
@Component({
  selector: 'app-games-wager',
  templateUrl: './games-wager.component.html',
  styleUrls: ['./games-wager.component.css'],
  providers: [GamelistService, WagerService]
})
export class GamesWagerComponent implements OnInit, OnDestroy {

  //Local : 
 // public sportsList:Array<any> = new Array();
  public sportsList:Array<Sports> = new Array();
  public isLoading=true;
  constructor(public gls:GamelistService,public authService: AuthService,private router:Router
    , private user : UpdateUser, private wagerService:WagerService) {
    //this.dialog.open(LoadingSpinnerComponent);
  }

  gameListSubscribe;
  wagerSservieSubscribe;

  ngOnInit() {
    //Local : 
    //this.sportsList = this.sportsLists;
    this.gameListSubscribe = this.gls.getListData(this.user.user.ckey)
    .subscribe(data => {
      //Local : 
      //this.sportsList = this.sportsLists;
      this.sportsList = data;
      this.isLoading=false;
      
    },
    error => {
      //this.sportsList = this.sportsLists;
      this.isLoading=false;
    })

    //this.dialog.closeAll();
    /*this.authService.af.authState.subscribe(
     (user)=>{
       if(user==null){
         console.log("no user")
       }
       else{
          user.getIdToken().then(token =>{
            console.log("latest token: "+ token);
            this.gls.getListData(token).subscribe(data => {
              this.sportsList = data;
            })
          })
       }
     }
   )*/
  }
  makeWager(game: Game){
    this.router.navigate(['home','mkwagerstep1']);
    //this.makeWagerService.setGame(game);
    this.wagerService.createTempWager(game,null,null,null);
    //this.wagerService.createLiveWager(game, game.homeTeamShortName, 500);
    //this.wagerService.createOpenWager(game, game.homeTeamShortName, 500, 'Debanjan Banerjee', '3yCzgU8VjFVX87s0EchIGZhFNpi1')
  }

private todaysDate=new Date(Date.now());
currentDate = new Date( this.todaysDate.getFullYear(),
                 this.todaysDate.getMonth(),
                 this.todaysDate.getDate());
nextDate(){
  this.currentDate = new Date( this.currentDate.getFullYear(),
                 this.currentDate.getMonth(),
                 this.currentDate.getDate()+1);

}
prevDate(){
     this.currentDate = new Date( this.currentDate.getFullYear(),
                 this.currentDate.getMonth(),
                 this.currentDate.getDate()-1);
}

ngOnDestroy(){
  this.gameListSubscribe.unsubscribe();
}
public sportsLists=[{
"shortName":"ABC",
"gameList":[{
  "homeTeam":"Toranto",
  "awayTeam":"Chicago",
  "homeTeamShortName":"TOR",
  "awayTeamShortName":"CHI",
  "pointSpread":"TOR-6.5",
  "underLine":"102.5",
  "matchDate":"Oct 15, 2017",
  "matchTime":"8:00PM",
}]},
{"shortName":"PQR",
"gameList":[{
  "homeTeam":"Cavaliers",
  "awayTeam":"Minnesota",
  "homeTeamShortName":"CLE",
  "awayTeamShortName":"MIN",
  "pointSpread":"CLE-4.5",
  "underLine":"105.5",
  "matchDate":"Oct 15, 2017",
  "matchTime":" 8:00PM",
}]},
{"shortName":"XYZ",
"gameList":[{
  "homeTeam":"Sacremento",
  "awayTeam":"Los Angeles",
  "homeTeamShortName":"SAC",
  "awayTeamShortName":"LAL",
  "pointSpread":"TOR-6.5",
  "underLine":"101",
  "matchDate":"Oct 15, 2017",
  "matchTime":"10:30PM",
}]
}
];
public gamesList=[{
  "Team1FullName":"Toranto",
  "Team2FullName":"Chicago",
  "Team1Name":"TOR",
  "Team2Name":"CHI",
  "Team1Ind":"TOR-6.5",
  "Team2Ind":"u 102.5",
  "Time":"8:00PM",
},
{
  "Team1FullName":"Cavaliers",
  "Team2FullName":"Minnesota",
  "Team1Name":"CLE",
  "Team2Name":"MIN",
  "Team1Ind":"CLE-4.5",
  "Team2Ind":"u 105.5",
  "Time":"8:00PM",
},
{
  "Team1FullName":"Sacremento",
  "Team2FullName":"Los Angeles",
  "Team1Name":"SAC",
  "Team2Name":"LAL",
  "Team1Ind":"TOR-6.5",
  "Team2Ind":"u 101",
  "Time":"10:30PM",
}];
}
