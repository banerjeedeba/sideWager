import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { MaterialModule } from '@angular/Material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import 'hammerjs';
import { AppComponent } from './app.component';
import { SwSideNavComponent } from './sw-side-nav/sw-side-nav.component';
import { SwSideNavCardComponent } from './sw-side-nav-card/sw-side-nav-card.component';
import { SwSideNavListComponent } from './sw-side-nav-list/sw-side-nav-list.component';
import { SwTabListComponent } from './sw-tab-list/sw-tab-list.component';


@NgModule({
  declarations: [
    AppComponent,
    SwSideNavComponent,
    SwSideNavCardComponent,
    SwSideNavListComponent,
    SwTabListComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    MaterialModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
