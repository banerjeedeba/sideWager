import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs/Subject';
@Injectable()
export class OpenWagerTabCountService {

  constructor() { }
  private subject = new Subject<any>();
  private subjectRequest = new Subject<any>();
  private subjectResult = new Subject<any>();
  
  resetTabCount() {
      this.subject.next();
  }

  getTabCount(): Observable<any> {
    debugger;
      return this.subject.asObservable();
  }
  setTabCount(message: string) {
    debugger;
      this.subject.next(message);
  }

  getRequestTabCount(): Observable<any> {
    debugger;
      return this.subjectRequest.asObservable();
  }
  setRequestTabCount(message: string) {
    debugger;
      this.subjectRequest.next(message);
  }

  getResultTabCount(): Observable<any> {
    debugger;
      return this.subjectResult.asObservable();
  }
  setResultTabCount(message: string) {
    debugger;
      this.subjectResult.next(message);
  }

}

