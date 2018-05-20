import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-weekly-game-list',
  templateUrl: './weekly-game-list.component.html',
  styleUrls: ['./weekly-game-list.component.css']
})
export class WeeklyGameListComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }
  private todaysDate=new Date(Date.now());
  currentDate = new Date( this.todaysDate.getFullYear(),
                   this.todaysDate.getMonth(),
                   this.todaysDate.getDate());
  nextDate(){
    this.currentDate = new Date( this.currentDate.getFullYear(),
                   this.currentDate.getMonth(),
                   this.currentDate.getDate()+7);
  
  }
  prevDate(){
       this.currentDate = new Date( this.currentDate.getFullYear(),
                   this.currentDate.getMonth(),
                   this.currentDate.getDate()-7);
  }
}
