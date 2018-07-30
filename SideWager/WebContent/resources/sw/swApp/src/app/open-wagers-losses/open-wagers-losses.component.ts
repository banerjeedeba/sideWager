import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-open-wagers-losses',
  templateUrl: './open-wagers-losses.component.html',
  styleUrls: ['./open-wagers-losses.component.css']
})
export class OpenWagersLossesComponent implements OnInit {

  constructor() { }

  ngOnInit() {


    
  }
public listWinnings=[
  {
    "Name":"Brent Boner",
    "imgSource":"..\\assets\\img\\user.png",
    "weeklyHTP":"15",
    "HTPind":"Red",
    "Amount":"-250",
    "paymentStatus":"N"
  },
  {
    "Name":"Kyle diPretoro",
    "imgSource":"..\\assets\\img\\user.png",
    "weeklyHTP":"56",
    "HTPind":"Amber",
    "Amount":"-500",
    "paymentStatus":"W"
  },
  {
    "Name":"Michael Adams",
    "imgSource":"..\\assets\\img\\user.png",
    "weeklyHTP":"12",
    "HTPind":"Green",
    "Amount":"-650",
    "paymentStatus":"Y"
  },
];
}
