import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import {OpenWagerTabCountService} from '../provider/open-wager-tab-count.service';
@Component({
  selector: 'app-sw-tab-list',
  templateUrl: './sw-tab-list.component.html',
  styleUrls: ['./sw-tab-list.component.css']
})
export class SwTabListComponent implements OnInit {

  constructor(private openWagerTabCountService:OpenWagerTabCountService) { }
  openWagerRequestTabCountsubscription: Subscription;
  openWagersRequestTabCount:string;
  ngOnInit() {
    this.openWagerRequestTabCountsubscription = this.openWagerTabCountService.getRequestTabCount().subscribe((data) => 
    { 
      this.openWagersRequestTabCount = data; 
    });
  }
  ngOnDestroy(){
    this.openWagerRequestTabCountsubscription.unsubscribe();
  }

}
