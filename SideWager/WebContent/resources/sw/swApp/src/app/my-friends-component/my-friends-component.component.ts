import { Component, OnInit, OnDestroy } from '@angular/core';
import { UpdateUser } from '../provider/updateuser.service';
import { User } from '../entities/User';

@Component({
  selector: 'app-my-friends-component',
  templateUrl: './my-friends-component.component.html',
  styleUrls: ['./my-friends-component.component.css']
})
export class MyFriendsComponentComponent implements OnInit, OnDestroy {

  public pendingList : User[] = [];
  public frndList: User[] = null;
  public pendingKeys : string[] =[];

  constructor(private user:UpdateUser) { }

  pendingListSubscribe;
  
  ngOnInit() {
    this.pendingListSubscribe = this.user.getPendingList().subscribe(
      list => {
        this.pendingList = list;
        //for(var item of list){
          //for(var key in item){
            //this.pendingKeys.push(key);
          //}
        //}
      }
    )
  }

  acceptRequest(to:User, toKey:string){
    this.user.acceptRequest(to,toKey);
  }

  rejectRequest(to:User, toKey:string){
    this.user.rejectRequest(to,toKey);
  }

  ngOnDestroy(){
    this.pendingListSubscribe.unsubscribe();
  }

}
