import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
@Component({
  selector: 'app-splash-screen',
  templateUrl: './splash-screen.component.html',
  styleUrls: ['./splash-screen.component.css']
})
export class SplashScreenComponent implements OnInit {
private timeSlash;
  constructor(private router:Router) { }

  ngOnInit() {
    this.timeSlash= setInterval(()=>{
      window.clearInterval(this.timeSlash);
      this.router.navigate(['login']);

    },3000);
    
  }

}
