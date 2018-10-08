import { Component, OnInit } from '@angular/core';
import {OpenWagerTabCountService} from '../provider/open-wager-tab-count.service';
import { OpenWager } from '../entities/OpenWager';
import { WagerService } from '../provider/wager.service';

@Component({
  selector: 'app-open-wagers-losses',
  templateUrl: './open-wagers-losses.component.html',
  styleUrls: ['./open-wagers-losses.component.css'],
  providers: [WagerService]
})
export class OpenWagersLossesComponent implements OnInit {

  public openWagers :Array<OpenWager> = new Array();
  public openWagersList :OpenWager[];
  openWagerSubscribe;
  challengerOpenWagerSubscribe;
  constructor(private wagerService:WagerService,private openWagerTabCountService:OpenWagerTabCountService) { }

  ngOnInit() {
    this.openWagerSubscribe = this.wagerService.getAllOpenWagers().subscribe(openWagersListSnapshot=>{
      this.openWagersList = openWagersListSnapshot;
    })
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
