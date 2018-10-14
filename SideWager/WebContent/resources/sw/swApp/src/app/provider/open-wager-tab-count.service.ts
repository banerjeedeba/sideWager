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
      return this.subject.asObservable();
  }
  setTabCount(message: string) {
      this.subject.next(message);
  }

  getRequestTabCount(): Observable<any> {
      return this.subjectRequest.asObservable();
  }
  setRequestTabCount(message: string) {
      this.subjectRequest.next(message);
  }

  getResultTabCount(): Observable<any> {
      return this.subjectResult.asObservable();
  }
  setResultTabCount(message: string) {
      this.subjectResult.next(message);
  }

}

