import { UsersDTO } from './../../InterfacesPortal';
import { HttpServiceUsers } from './../../http.service.users';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, Inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

export interface DialogData {
  email: string;
}

@Component({
  selector: 'app-lost-password',
  templateUrl: './lost-password.component.html',
  styleUrls: ['./lost-password.component.css']
})
export class LostPasswordComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<LostPasswordComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private httpServiceUsers: HttpServiceUsers
  ) { }

  email: string;

  ngOnInit(): void {
  }

  lostPassword() {
    let email = <UsersDTO>{};
    email.email = this.email;
    this.httpServiceUsers.resetPassword(email).subscribe(res => {
      console.log(res);
    });
    this.dialogRef.close();

  }



}
