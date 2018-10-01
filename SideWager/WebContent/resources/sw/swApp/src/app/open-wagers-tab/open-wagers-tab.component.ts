import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import {OpenWagerTabCountService} from '../provider/open-wager-tab-count.service';
@Component({
  selector: 'app-open-wagers-tab',
  templateUrl: './open-wagers-tab.component.html',
  styleUrls: ['./open-wagers-tab.component.css'],
  providers: []
  //encapsulation: ViewEncapsulation.Native
})
export class OpenWagersTabComponent implements  OnDestroy  {

  constructor(private openWagerTabCountService:OpenWagerTabCountService) {
   }

  openWagerRequestTabCountsubscription: Subscription;
  openWagerResultTabCountsubscription: Subscription;
  openWagerTabCountsubscription: Subscription;
  openWagersRequestTabCount:string;
  openWagersResultTabCount:string;
  openWagersTabCount:string;

  ngOnInit() {
    this.openWagerRequestTabCountsubscription = this.openWagerTabCountService.getRequestTabCount().subscribe((data) => 
    { 
      this.openWagersRequestTabCount = data; 
    });
    
    this.openWagerResultTabCountsubscription = this.openWagerTabCountService.getResultTabCount().subscribe((data) => 
    { 
        this.openWagersResultTabCount = data; 
    });

    this.openWagerTabCountsubscription = this.openWagerTabCountService.getTabCount().subscribe((data) => 
    { 
        this.openWagersTabCount = data; 
    });
  }
  
  ngOnDestroy(){
    this.openWagerRequestTabCountsubscription.unsubscribe();
    this.openWagerResultTabCountsubscription.unsubscribe();
    this.openWagerTabCountsubscription.unsubscribe();
  }
}
