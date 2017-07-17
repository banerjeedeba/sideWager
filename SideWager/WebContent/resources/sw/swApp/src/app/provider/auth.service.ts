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

  LoginWithFaceBook(){
   return this.af.auth.signInWithPopup(new firebase.auth.FacebookAuthProvider());
  }

  LoginWithEmail(email: string, password: string){
   return this.af.auth.signInWithEmailAndPassword(email,password);
  }

  RegisterWithEmail(email: string, password: string){
   return this.af.auth.createUserWithEmailAndPassword(email,password);
  }

  Logout(){
    return this.af.auth.signOut();
   
  }
}
