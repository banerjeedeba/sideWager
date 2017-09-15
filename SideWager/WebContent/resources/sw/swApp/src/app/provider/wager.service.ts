import { Injectable } from '@angular/core';
import { AngularFireModule } from 'angularfire2';
import { AngularFireDatabaseModule, AngularFireDatabase, FirebaseObjectObservable, FirebaseListObservable } from 'angularfire2/database';
import { AngularFireAuthModule, AngularFireAuth } from 'angularfire2/auth';
import {User} from '../entities/user';
import {AuthService} from '../provider/auth.service';
import { Game } from "../entities/Game";
import { LiveWager } from "../entities/LiveWager";
@Injectable()
export class WagerService{

    private liveWagerPath : string = '/livewager';

    constructor(public auth: AuthService,
        private db: AngularFireDatabase) { }

    createLiveWager(game: Game, selectedTeam: string, amount: number): void{
        let userKey =  this.auth.user.uid;
        let liveWagerObject:LiveWager = new LiveWager();
        liveWagerObject.userKey = userKey;
        liveWagerObject.userName = this.auth.user.displayName;
        liveWagerObject.selectedTeam = selectedTeam;
        liveWagerObject.game = game;
        liveWagerObject.amount = amount;
        const liveWagerUserPath =  `${this.liveWagerPath}/${userKey}`;
        const liveWagerUserList = this.db.list(liveWagerUserPath);
        liveWagerUserList.push(liveWagerObject);
        return;
    }

    getLiveWagers(friendKey) : FirebaseListObservable<LiveWager[]>{
        const liveWagerUserPath =  `${this.liveWagerPath}/${friendKey}`;
        let liveWagerUserList = this.db.list(liveWagerUserPath);
        return liveWagerUserList;
    }

    // Default error handling for all actions
    private handleError(error) {
        console.log(" Wager service error");    
        console.log(error);
    }
}