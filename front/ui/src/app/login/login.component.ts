import { Component, OnInit } from '@angular/core';
import { UserService } from '../user/UserService';
import { Router } from '@angular/router';
import { take } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginUsername: string;
  loginPassword: string;

  registerUsername: string;
  registerEmail: string;
  registerPassword: string;
  registerRepeatedPassword: string;

  constructor(private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
  }

  logIn() {
    this.userService.logIn(this.loginUsername, this.loginPassword)
        .pipe(take(1))
        .subscribe(loggedIn => {
          if (loggedIn) {
            this.router.navigate(['/main']).then();
          } else {
            this.loginUsername = '';
            this.loginPassword = '';
          }
      });
  }

  register() {
    if (this.registerPassword !== this.registerRepeatedPassword) {
      this.registerPassword = '';
      this.registerEmail = '';
      this.registerUsername = '';
      this.registerRepeatedPassword = '';

      return;
    }

    this.userService.register(this.registerUsername, this.registerEmail, this.registerPassword)
        .pipe(take(1))
        .subscribe(registered => {
          if (registered) {
            this.router.navigate(['/main']).then();
          } else {
            this.registerPassword = '';
            this.registerEmail = '';
            this.registerUsername = '';
            this.registerRepeatedPassword = '';
          }
        })
  }
}
