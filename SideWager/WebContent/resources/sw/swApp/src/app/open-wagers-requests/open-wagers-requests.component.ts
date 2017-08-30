import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-open-wagers-requests',
  templateUrl: './open-wagers-requests.component.html',
  styleUrls: ['./open-wagers-requests.component.css']
})
export class OpenWagersRequestsComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }
accept(){

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
