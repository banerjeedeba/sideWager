import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { MaterialModule } from '@angular/Material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import 'hammerjs';
import { AngularFireModule } from 'angularfire2';
import { AngularFireDatabaseModule, AngularFireDatabase, FirebaseListObservable } from 'angularfire2/database';
import { AngularFireAuthModule, AngularFireAuth } from 'angularfire2/auth';
import * as firebase from 'firebase/app';
import {RouterModule,Routes} from '@angular/router';

import { AppComponent } from './app.component';
import { SwSideNavComponent } from './sw-side-nav/sw-side-nav.component';
import { SwSideNavCardComponent } from './sw-side-nav-card/sw-side-nav-card.component';
import { SwSideNavListComponent } from './sw-side-nav-list/sw-side-nav-list.component';
import { SwTabListComponent } from './sw-tab-list/sw-tab-list.component';
import { GamesWagerComponent } from './games-wager/games-wager.component';
import { environment } from '../environments/environment';
import {AuthService} from './provider/auth.service';
import { LoginScreenComponent } from './login-screen/login-screen.component';
import { SplashScreenComponent } from './splash-screen/splash-screen.component';

export const routes:Routes=[
  {path:'',component:SwSideNavComponent},
  {path:'login',component:LoginScreenComponent}
];
@NgModule({
  declarations: [
    AppComponent,
    SwSideNavComponent,
    SwSideNavCardComponent,
    SwSideNavListComponent,
    SwTabListComponent,
    GamesWagerComponent,
    LoginScreenComponent,
    SplashScreenComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFireDatabaseModule,
    AngularFireAuthModule,
    MaterialModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(routes)
  ],
  providers: [AuthService],
  bootstrap: [AppComponent]
})
export class AppModule { }
