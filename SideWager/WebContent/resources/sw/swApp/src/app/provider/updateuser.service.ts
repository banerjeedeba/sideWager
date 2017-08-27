import { Injectable } from '@angular/core';
import { AngularFireModule } from 'angularfire2';
import { AngularFireDatabaseModule, AngularFireDatabase, FirebaseObjectObservable, FirebaseListObservable } from 'angularfire2/database';
import { AngularFireAuthModule, AngularFireAuth } from 'angularfire2/auth';
import {User} from '../entities/user';
import {AuthService} from '../provider/auth.service';
@Injectable()
export class UpdateUser{
    private basePath: string = '/users';
    private userListPath: string = '/users';
    private requestedPath: string = '/requested';
    private pendingPath: string = '/pending';
    private friendPath: string = '/friend'; 
    private requestPending
    items: FirebaseListObservable<User[]> = null; //  list of objects
    item: FirebaseObjectObservable<User> = null; //   single object
    pendingList: FirebaseListObservable<User[]> = null;
    friendList: FirebaseListObservable<User[]> = null;
    public user : User;

    constructor(public auth: AuthService,
              private db: AngularFireDatabase) { }

    // Return a single observable item
    getItem(key: string): FirebaseObjectObservable<User> {
    const itemPath =  `${this.basePath}/${key}`;
    this.item = this.db.object(itemPath)
    return this.item
    }

    getItemsList(query={}): FirebaseListObservable<User[]> {
    this.items = this.db.list(this.basePath, {
        query: query
    });
    return this.items
    }

    getSearchUserList(start,end): FirebaseListObservable<User[]> {
    this.items = this.db.list(this.basePath, {
        query: {
            orderByChild: 'email',
            startAt: start,
            endAt: end
        }
    });
    return this.items
    }

    getPendingList(){
        const pendingFullPath = `${this.pendingPath}/${this.auth.user.uid}`;
        this.pendingList = this.db.list(pendingFullPath);
        return this.pendingList;
    }

    getFriendList(){
        const friendFullPath = `${this.friendPath}/${this.auth.user.uid}`;
        this.friendList = this.db.list(friendFullPath);
        return this.friendList;
    }

    sendRequest(to :User, tokey : string) : void{
        let fromkey =  this.auth.user.uid;
        const requestedFullPath =  `${this.requestedPath}/${fromkey}/${tokey}`;
        this.db.object(requestedFullPath).set(to).catch(error => this.handleError(error));

        const pendingFullPath =  `${this.pendingPath}/${tokey}/${fromkey}`;
        this.db.object(pendingFullPath).set(this.user).catch(error => this.handleError(error));
    }

    acceptRequest(to :User, tokey : string) : void{
        console.log(this.auth.user);
        let fromkey =  this.auth.user.uid;
        const requestFriendPath = `${this.friendPath}/${fromkey}/${tokey}`;
        this.db.object(requestFriendPath).set(to).catch(error => this.handleError(error));

        const pendingFriendPath =  `${this.friendPath}/${tokey}/${fromkey}`;
        this.db.object(pendingFriendPath).set(this.user).catch(error => this.handleError(error));

        const requestedFullPath =  `${this.requestedPath}/${tokey}/${fromkey}`;
        this.db.object(requestedFullPath).remove().catch(error => this.handleError(error));

        const pendingFullPath =  `${this.pendingPath}/${fromkey}/${tokey}`;
        this.db.object(pendingFullPath).remove().catch(error => this.handleError(error));
    }

    rejectRequest(to :User, tokey : string) : void{
        let fromkey =  this.auth.user.uid;
        const requestedFullPath =  `${this.requestedPath}/${tokey}/${fromkey}`;
        this.db.object(requestedFullPath).remove().catch(error => this.handleError(error));

        const pendingFullPath =  `${this.pendingPath}/${fromkey}/${tokey}`;
        this.db.object(pendingFullPath).remove().catch(error => this.handleError(error));
    }

    isFriend(tokey : string) : FirebaseObjectObservable<User>{
        let fromkey =  this.auth.user.uid;
        const itemPath =  `${this.friendPath}/${fromkey}/${tokey}`;
        return this.db.object(itemPath);
    }

    isPending(tokey : string) : FirebaseObjectObservable<User>{
        let fromkey =  this.auth.user.uid;
        const itemPath =  `${this.pendingPath}/${fromkey}/${tokey}`;
        return this.db.object(itemPath);
    }

    isRequested(tokey : string) : FirebaseObjectObservable<User>{
        let fromkey =  this.auth.user.uid;
        const itemPath =  `${this.requestedPath}/${fromkey}/${tokey}`;
        return this.db.object(itemPath);
    }

    createItem(item: User): void  {
    this.items.push(item)
        .catch(error => this.handleError(error))
    }
    // Update an existing item
    updateItem(key: string, value: any): void {
    this.items.update(key, value)
        .catch(error => this.handleError(error))
    }
    // Deletes a single item
    deleteItem(key: string): void {
        this.items.remove(key)
        .catch(error => this.handleError(error))
    }
    // Deletes the entire list of items
    deleteAll(): void {
        this.items.remove()
        .catch(error => this.handleError(error))
    }

    setItem(item: User, key: string): void{
        this.user = item;
        const itemPath =  `${this.basePath}/${key}`;
        this.item = this.db.object(itemPath);
        this.item.set(item).catch(error => this.handleError(error));//.then(_ => console.log("success"));
    }
    // Default error handling for all actions
    private handleError(error) {
    console.log(" Updateuser service error");    
    console.log(error);
    }
}

