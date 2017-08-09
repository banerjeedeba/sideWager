import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-open-wagers-winnings',
  templateUrl: './open-wagers-winnings.component.html',
  styleUrls: ['./open-wagers-winnings.component.css']
})
export class OpenWagersWinningsComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }
public listWinnings=[
  {
    "Name":"Brent Boner",
    "imgSource":"..\\assets\\img\\user.png",
    "weeklyHTP":"15",
    "HTPind":"Red",
    "Amount":"250 sc",
    "paymentStatus":"N"
  },
  {
    "Name":"Kyle diPretoro",
    "imgSource":"..\\assets\\img\\user.png",
    "weeklyHTP":"56",
    "HTPind":"Amber",
    "Amount":"500 sc",
    "paymentStatus":"N"
  },
  {
    "Name":"Michael Adams",
    "imgSource":"..\\assets\\img\\user.png",
    "weeklyHTP":"12",
    "HTPind":"Green",
    "Amount":"650 sc",
    "paymentStatus":"N"
  },
];
}
