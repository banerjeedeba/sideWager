import { Component, OnInit } from '@angular/core';
import {GamelistService} from '../provider/gamelist.service';
import {AuthService} from '../provider/auth.service';
import {Router} from '@angular/router';
@Component({
  selector: 'app-games-wager',
  templateUrl: './games-wager.component.html',
  styleUrls: ['./games-wager.component.css'],
  providers: [GamelistService]
})
export class GamesWagerComponent implements OnInit {

  public gameList = [];
 
  constructor(public gls:GamelistService,public authService: AuthService,private router:Router) {
    
  }

  ngOnInit() {
    
    console.log("init test: "+this.gameList);   
    this.authService.af.authState.subscribe(
     (user)=>{
       if(user==null){
         console.log("no user")
       }
       else{
          user.getIdToken().then(token =>{
            this.gls.getListData(token).subscribe(data => {
              this.gameList = data;
            })
          })
       }
     }
   )
  }
  makeWager(){
 this.router.navigate(['home','mkwagerstep1']);
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
