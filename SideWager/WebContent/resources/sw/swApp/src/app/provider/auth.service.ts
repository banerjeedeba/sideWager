import { Injectable } from '@angular/core';
import { AngularFireModule } from 'angularfire2';

import { AngularFireDatabaseModule, AngularFireDatabase, FirebaseListObservable } from 'angularfire2/database';
import { AngularFireAuthModule, AngularFireAuth } from 'angularfire2/auth';
import * as firebase from 'firebase/app';
@Injectable()
export class AuthService {

 
  constructor(public af:AngularFireAuth) { }
  LoginWithGoogle(){
   return this.af.auth.signInWithPopup(new firebase.auth.GoogleAuthProvider());
  }

  Logout(){
    return this.af.auth.signOut();
   
  }
}
