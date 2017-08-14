import { Injectable } from '@angular/core';
import { AngularFireModule } from 'angularfire2';
import { AngularFireDatabaseModule, AngularFireDatabase, FirebaseObjectObservable, FirebaseListObservable } from 'angularfire2/database';
import { AngularFireAuthModule, AngularFireAuth } from 'angularfire2/auth';
import {User} from '../entities/user';
@Injectable()
export class UpdateUser{
    private basePath: string = '/users';
    items: FirebaseListObservable<User[]> = null; //  list of objects
    item: FirebaseObjectObservable<User> = null; //   single object
    public user : User;

    constructor(private af: AngularFireModule,
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

    getSearchUserList(query): FirebaseListObservable<User[]> {
        console.log("search sservice"+query);
    this.items = this.db.list(this.basePath, {
        query: query
    });
    return this.items
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

