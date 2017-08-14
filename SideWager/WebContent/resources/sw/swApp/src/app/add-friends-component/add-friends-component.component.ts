import { Component, OnInit } from '@angular/core';
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
export class AddFriendsComponentComponent implements OnInit {

  private searchTerms :string ;
  public results : User[] = null;
  constructor(private user : UpdateUser,
              private db: AngularFireDatabase) { }

  ngOnInit() {
  }

  frndSearch(searchString : string){
    console.log("search string"+searchString);
    const query= {
        orderByChild: 'email',
        equalTo: searchString
    };
    this.user.getSearchUserList(query).subscribe(data => {
      this.results = data;
      console.log("result");
      console.log(this.results);
    })
  }
}
