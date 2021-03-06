import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { MaterialModule} from '@angular/Material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import 'hammerjs';
import { AngularFireModule } from 'angularfire2';
import { AngularFireDatabaseModule, AngularFireDatabase, FirebaseListObservable } from 'angularfire2/database';
import { AngularFireAuthModule, AngularFireAuth } from 'angularfire2/auth';
import * as firebase from 'firebase/app';
import {RouterModule,Routes} from '@angular/router';
import { MdDialogModule, MdDialogRef, MD_DIALOG_DATA} from '@angular/material'
import { AppComponent } from './app.component';
import { SwSideNavComponent } from './sw-side-nav/sw-side-nav.component';
import { SwSideNavCardComponent } from './sw-side-nav-card/sw-side-nav-card.component';
import { SwSideNavListComponent } from './sw-side-nav-list/sw-side-nav-list.component';
import { SwTabListComponent } from './sw-tab-list/sw-tab-list.component';
import { GamesWagerComponent } from './games-wager/games-wager.component';
import { environment } from '../environments/environment';
import {AuthService} from './provider/auth.service';
import {OpenWagerTabCountService} from './provider/open-wager-tab-count.service';
import { LoginScreenComponent } from './login-screen/login-screen.component';
import { SplashScreenComponent } from './splash-screen/splash-screen.component';
import { LiveWagersComponent } from './live-wagers/live-wagers.component';
import { OpenWagersRequestsComponent } from './open-wagers-requests/open-wagers-requests.component';
import { OpenWagersWinningsComponent } from './open-wagers-winnings/open-wagers-winnings.component';
import { OpenWagersLossesComponent } from './open-wagers-losses/open-wagers-losses.component';
import { OpenWagersTabComponent } from './open-wagers-tab/open-wagers-tab.component';
import { OpenWagersComponent } from './open-wagers/open-wagers.component';
import { MakeWagerStep1Component } from './make-wager-step-1/make-wager-step-1.component';
import { SwFriendsTabComponent } from './sw-friends-tab/sw-friends-tab.component';
import { MyFriendsComponentComponent } from './my-friends-component/my-friends-component.component';
import { AddFriendsComponentComponent } from './add-friends-component/add-friends-component.component';
import { LoadingSpinnerComponent } from './loading-spinner/loading-spinner.component';
import { MakeWagerStep2Component } from './make-wager-step-2/make-wager-step-2.component';
import { MakeWagerStep4Component } from './make-wager-step-4/make-wager-step-4.component';
import { MakeWagerStep5LiveComponent } from './make-wager-step-5-live/make-wager-step-5-live.component';
import { WagerRequestOuConfirm2Component } from './wager-request-ou-confirm2/wager-request-ou-confirm2.component';
import { MakeWagerStep3Component } from './make-wager-step-3/make-wager-step-3.component';
import { WagerRequestConfirmModalComponent } from './wager-request-confirm-modal/wager-request-confirm-modal.component';
import { WeeklyGameListComponent } from './weekly-game-list/weekly-game-list.component';
import { MakeWagerStep6Component } from './make-wager-step-6/make-wager-step-6.component';
import { MakeWagerStep7Component } from './make-wager-step-7/make-wager-step-7.component';

export const routes:Routes=[
  {path:'home',component:SwSideNavComponent,
  children:[
    { path: '', redirectTo: 'swtab', pathMatch: 'full' },
    {path:'mkwagerstep1',component:MakeWagerStep1Component},
    {path:'mkwagerstep2',component:MakeWagerStep2Component},
    {path:'mkwagerstep3',component:MakeWagerStep3Component},
    {path:'mkwagerstep4',component:MakeWagerStep4Component},
    {path:'wagerrequestouconfirm2',component:WagerRequestOuConfirm2Component},
    {path:'mkwagerstep5live',component:MakeWagerStep5LiveComponent},
    {path:'mkwagerstep6',component:MakeWagerStep6Component},
    {path:'mkwagerstep7',component:MakeWagerStep7Component},
    {path:'swtab',component:SwTabListComponent},
    {path:'friends',component:SwFriendsTabComponent},
    {path:'weeklyhistory',component:WeeklyGameListComponent}
  ]
  },
  {path:'',component:LoginScreenComponent}
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
    SplashScreenComponent,
    LiveWagersComponent,
    OpenWagersRequestsComponent,
    OpenWagersWinningsComponent,
    OpenWagersLossesComponent,
    OpenWagersTabComponent,
    OpenWagersComponent,
    MakeWagerStep1Component,
    SwFriendsTabComponent,
    MyFriendsComponentComponent,
    AddFriendsComponentComponent,
    LoadingSpinnerComponent,
    MakeWagerStep2Component,
    MakeWagerStep4Component,
    MakeWagerStep5LiveComponent,
    WagerRequestOuConfirm2Component,
    MakeWagerStep3Component,
    WagerRequestConfirmModalComponent,
    WeeklyGameListComponent,
    MakeWagerStep6Component,
    MakeWagerStep7Component
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    MdDialogModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFireDatabaseModule,
    AngularFireAuthModule,
    MaterialModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(routes)
  ],
  entryComponents:[WagerRequestConfirmModalComponent],
  providers: [AuthService,OpenWagerTabCountService],
  bootstrap: [AppComponent]
})
export class AppModule { }
