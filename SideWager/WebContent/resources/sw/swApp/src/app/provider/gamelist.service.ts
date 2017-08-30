import { Injectable } from '@angular/core';
import {Http, Response} from '@angular/http';
import { AngularFireModule } from 'angularfire2';
import { Observable } from 'rxjs';
import { AngularFireDatabaseModule, AngularFireDatabase, FirebaseListObservable } from 'angularfire2/database';
import * as firebase from 'firebase/app';
@Injectable()
export class GamelistService {

  items: any = {};

  constructor(public afdb: AngularFireDatabase, public http:Http) { 
  }

  getListData(token){
    //console.log("getdata");
    //'/SideWager/rest/data/gamelist?ckey='+token
    //'https://www.sidewagerapp.com/rest/data/gamelist?ckey='+token
    
    return this.http.get('https://www.sidewagerapp.com/rest/data/gamelist?ckey='+token)
    .map((res : Response) => res.json())
    .catch(this.handleError);
  }

    handleError(error: Response) {
    console.log(" GameList service error"+error);    
    return Observable.throw(error || "GameList service error");
    }
}
