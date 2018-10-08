import { Component, OnInit, Inject } from '@angular/core';
import { MdDialog, MdDialogRef, MD_DIALOG_DATA,MdDialogConfig, MdDialogClose, MdDialogActions, MdButton} from '@angular/material';

@Component({
  selector: 'app-wager-request-confirm-modal',
  templateUrl: './wager-request-confirm-modal.component.html',
  styleUrls: ['./wager-request-confirm-modal.component.css']
})
export class WagerRequestConfirmModalComponent implements OnInit {

  constructor(public dialogRef: MdDialogRef<WagerRequestConfirmModalComponent>,
    @Inject(MD_DIALOG_DATA) public data: any) { }

  ngOnInit() {
  }

  confirm(){
   this.dialogRef.close( true);
  }
  cancel(){
    this.dialogRef.close( false);
  } 

}
