import { Component, OnInit } from '@angular/core';
import {OpenWagerTabCountService} from '../provider/open-wager-tab-count.service';

@Component({
  selector: 'app-open-wagers-losses',
  templateUrl: './open-wagers-losses.component.html',
  styleUrls: ['./open-wagers-losses.component.css'],
  providers: []
})
export class OpenWagersLossesComponent implements OnInit {

  constructor(private openWagerTabCountService:OpenWagerTabCountService) { }

  ngOnInit() {
    this.openWagersResultListTabCount = this.listWinnings.length.toString();
    this.sendTabCount();
  }
  openWagersResultListTabCount:string="0";
  sendTabCount(): void {
    // send message to subscribers via observable subject
    this.openWagerTabCountService.setResultTabCount(this.openWagersResultListTabCount.toString());
  }

  clearTabcount(): void {
      // clear message
      this.openWagerTabCountService.resetTabCount();
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
