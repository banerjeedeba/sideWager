import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
@Component({
  selector: 'app-live-wagers',
  templateUrl: './live-wagers.component.html',
  styleUrls: ['./live-wagers.component.css']
})
export class LiveWagersComponent implements OnInit {

  constructor(private router:Router) { }
accept(){
  this.router.navigate(['home']);
}
  ngOnInit() {
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
