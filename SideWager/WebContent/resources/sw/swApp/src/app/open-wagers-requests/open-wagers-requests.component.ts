import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-open-wagers-requests',
  templateUrl: './open-wagers-requests.component.html',
  styleUrls: ['./open-wagers-requests.component.css']
})
export class OpenWagersRequestsComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }
public listFrinds=[
  {
    "Name":"Brent Boner",
    "imgSource":"..\\assets\\img\\user.png",
    "HTP":"56",
    "HTPind":"Red",
    "Amount":"250"
  },
  {
    "Name":"Kyle diPretoro",
    "imgSource":"..\\assets\\img\\user.png",
    "HTP":"25",
    "HTPind":"Amber",
    "Amount":"150"
  },
  {
    "Name":"Michael Adams John",
    "imgSource":"..\\assets\\img\\user.png",
    "HTP":"5",
    "HTPind":"Green",
    "Amount":"650"
  },
];
}
