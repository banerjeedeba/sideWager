import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import {UpdateUser} from '../provider/updateuser.service';
import { AngularFireDatabaseModule, AngularFireDatabase, FirebaseObjectObservable, FirebaseListObservable } from 'angularfire2/database';
import { AngularFireAuthModule, AngularFireAuth } from 'angularfire2/auth';
import {User} from '../entities/user';

@Component({
  selector: 'app-add-friends-component',
  templateUrl: './add-friends-component.component.html',
  styleUrls: ['./add-friends-component.component.css']
})
export class AddFriendsComponentComponent implements OnInit, OnDestroy {

  private searchTerms :string ;
  startAt = new Subject();
  endAt = new Subject();
  lastKeyPress: number = 0;

  public results : User[] = null;
  public currentUserEmail:string;
  public resultStatus :any ={};
  constructor(private user : UpdateUser,
              private db: AngularFireDatabase) { }

  searchUserListSubscribe;
  isFriendSubscribe;
  isPendingSubscribe;
  isRequestedSubscribe;

  ngOnInit() {
    this.searchUserListSubscribe = this.user.getSearchUserList(this.startAt,this.endAt).subscribe(data => {
      this.results = data;
      this.resultStatus = {};
      for( let result of this.results){
        let originalKey = result['$key'];
        this.isFriendSubscribe = this.user.isFriend(result['$key']).subscribe(friends =>{
          let key = friends['$key'];
          if(friends.ckey==undefined){

            //Not a frnd check if pending
              this.isPendingSubscribe = this.user.isPending(result['$key']).subscribe(pendingfriends =>{
                let key = pendingfriends['$key'];
                if(pendingfriends.ckey==undefined){

                  //not pending check if requested
                    this.isRequestedSubscribe = this.user.isRequested(result['$key']).subscribe(requestedfriends =>{
                      let key = requestedfriends['$key'];
                      if(requestedfriends.ckey==undefined){
                        this.resultStatus[key] = false;
                      }else{
                        this.resultStatus[key] = "Requested!!";
                      }
                    });
                }else{
                  this.resultStatus[key] = "Pending Request!!";
                }
              });
          }else{
            this.resultStatus[key] = "Friends!!";
          }
        });
        
      }
    })
  }

  frndSearch($event){
    let searchString = $event.target.value.toLowerCase();
    if($event.timeStamp - this.lastKeyPress > 200 && searchString!=""){
        this.currentUserEmail = this.user.auth.user.email;
        this.startAt.next(searchString);
        this.endAt.next(searchString+"\uf8ff");
    }
    this.lastKeyPress = $event.timeStamp;
  }

  sendRequest(to : User, toKey : string){
    this.user.sendRequest(to,toKey);
  }

  ngOnDestroy(){
    if(this.searchUserListSubscribe!=undefined){
      this.searchUserListSubscribe.unsubscribe();
    }
    if(this.isFriendSubscribe!=undefined){
      this.isFriendSubscribe.unsubscribe();
    }
    if(this.isPendingSubscribe!=undefined){
      this.isPendingSubscribe.unsubscribe();
    }
    if(this.isRequestedSubscribe!=undefined){
      this.isRequestedSubscribe.unsubscribe();
    }
  }
}
