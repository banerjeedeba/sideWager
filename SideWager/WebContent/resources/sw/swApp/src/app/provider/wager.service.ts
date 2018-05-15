import { Injectable } from '@angular/core';
import { AngularFireModule } from 'angularfire2';
import { AngularFireDatabaseModule, AngularFireDatabase, FirebaseObjectObservable, FirebaseListObservable } from 'angularfire2/database';
import { AngularFireAuthModule, AngularFireAuth } from 'angularfire2/auth';
import {User} from '../entities/user';
import {AuthService} from '../provider/auth.service';
import { Game } from "../entities/Game";
import { LiveWager } from "../entities/LiveWager";
import { OpenWager } from "../entities/OpenWager";
@Injectable()
export class WagerService{

    private liveWagerPath : string = '/livewager';
    private tempWagerPath : string = '/tempwager';
    private openWagerPath : string = '/openwager';

    constructor(public auth: AuthService,
        private db: AngularFireDatabase) { }

    createLiveWager(game: Game, selectedTeam: string, uovalue:string, amount: number): void{
        let userKey =  this.auth.user.uid;
        let liveWagerObject:LiveWager = new LiveWager();
        liveWagerObject.userKey = userKey;
        liveWagerObject.userName = this.auth.user.displayName;
        if(selectedTeam != null)
            liveWagerObject.selectedTeam = selectedTeam;
        if(uovalue != null)
            liveWagerObject.uoValue = uovalue;
        liveWagerObject.game = game;
        liveWagerObject.amount = amount;
        const liveWagerUserPath =  `${this.liveWagerPath}/${userKey}`;
        const liveWagerUserList = this.db.list(liveWagerUserPath);
        liveWagerUserList.push(liveWagerObject);
        return;
    }

    createTempWager(game: Game, selectedTeam: string, uovalue:string, amount: number) :void{
        this.createTempOpenWager(game,selectedTeam,uovalue,amount,null,null);
    }

    createTempOpenWager(game: Game, selectedTeam: string, uovalue:string, amount: number, opKey: string, opName: string): void{
        let userKey =  this.auth.user.uid;
        let tempWagerObject:LiveWager = new LiveWager();
        tempWagerObject.userKey = userKey;
        tempWagerObject.userName = this.auth.user.displayName;
        if(selectedTeam != null)
            tempWagerObject.selectedTeam = selectedTeam;
        if(uovalue != null)
            tempWagerObject.uoValue = uovalue;
        tempWagerObject.game = game;
        if(amount != null)
            tempWagerObject.amount = amount;
        if(opKey != null){
            tempWagerObject.opKey = opKey;
        }
        if(opName != null){
            tempWagerObject.opName = opName;
        }
        console.log(userKey);
        const tempWagerUserPath =  `${this.tempWagerPath}/${userKey}`;
        //const tempWagerUserList = this.db.list(tempWagerUserPath);
        //tempWagerUserList.push(tempWagerObject);
        this.db.object(tempWagerUserPath).set(tempWagerObject);
        return;
    }

    createOpenWager(game: Game, selectedTeam: string, uoValue: string, amount: number, opUserName: string, opUserKey: string ): void{
        let userKey =  this.auth.user.uid;
        let challengerOpenWager:OpenWager = new OpenWager();
        challengerOpenWager.userKey = userKey;
        challengerOpenWager.userName = this.auth.user.displayName;
        challengerOpenWager.game = game;
        challengerOpenWager.amount = amount;
        if(uoValue!= null){
            challengerOpenWager.uoValue = uoValue;
        } 
        if(selectedTeam!= null){
            challengerOpenWager.selectedTeam = selectedTeam;
        }
        challengerOpenWager.status = "Challenged "+opUserName;
        challengerOpenWager.opUserKey = opUserKey;

        let opponentOpenWager:OpenWager = new OpenWager();
        opponentOpenWager.userKey = userKey;
        opponentOpenWager.userName = this.auth.user.displayName;
        if(uoValue!= null){
            opponentOpenWager.uoValue = uoValue;
        } 
        if(selectedTeam!= null){
            opponentOpenWager.selectedTeam = selectedTeam;
        }
        opponentOpenWager.game = game;
        opponentOpenWager.amount = amount;
        opponentOpenWager.status = "Pending";
        opponentOpenWager.opUserKey = opUserKey;


        const challengerOpenWagerPath = `${this.openWagerPath}/${userKey}`;
        const challengerOpenWagerList = this.db.list(challengerOpenWagerPath);
        challengerOpenWagerList.push(challengerOpenWager);

        const opponentOpenWagerPath = `${this.openWagerPath}/${opUserKey}`;
        const opponentOpenWagerList = this.db.list(opponentOpenWagerPath);
        opponentOpenWagerList.push(opponentOpenWager);
        return;
    }

    getLiveWagers(friendKey) : FirebaseListObservable<LiveWager[]>{
        const liveWagerUserPath =  `${this.liveWagerPath}/${friendKey}`;
        let liveWagerUserList = this.db.list(liveWagerUserPath);
        return liveWagerUserList;
    }

    getTempWagers() : FirebaseObjectObservable<LiveWager>{
        let userKey =  this.auth.user.uid;
        const tempWagerUserPath =  `${this.tempWagerPath}/${userKey}`;
        let tempWagerUserObject = this.db.object(tempWagerUserPath);
        return tempWagerUserObject;
    }

    removeTempWager() : any{
        let userKey =  this.auth.user.uid;
        const tempWagerUserPath =  `${this.tempWagerPath}/${userKey}`;
        let tempWagerUserObject = this.db.object(tempWagerUserPath).remove();
    }

    getPendingOpenWagers() : FirebaseListObservable<OpenWager[]>{
        let userKey =  this.auth.user.uid;
        const openWagerUserPath =  `${this.openWagerPath}/${userKey}`;
        let openWagerUserList = this.db.list(openWagerUserPath, {
            query: {
                orderByChild: 'status',
                equalTo: 'Pending'
            }
        });
        return openWagerUserList;
    }

    // Default error handling for all actions
    private handleError(error) {
        console.log(" Wager service error");    
        console.log(error);
    }
}