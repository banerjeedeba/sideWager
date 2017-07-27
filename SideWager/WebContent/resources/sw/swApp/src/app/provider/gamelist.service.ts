import { Injectable } from '@angular/core';
import {Http, Response} from '@angular/http';
import { AngularFireModule } from 'angularfire2';

import { AngularFireDatabaseModule, AngularFireDatabase, FirebaseListObservable } from 'angularfire2/database';
import * as firebase from 'firebase/app';
@Injectable()
export class GamelistService {

  items: any = {};

  constructor(public afdb: AngularFireDatabase, public http:Http) { 
    this.getGameList();
  }

  getGameList(){
    this.getData().subscribe(data => {
      console.log(data);
      this.items = data;
    })
  }

  getData(){
    return this.http.get('https://www.sidewagerapp.com/rest/notifications/gameList').map((res : Response) => res.json());
  }
}
