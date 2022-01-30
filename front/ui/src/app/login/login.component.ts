import { Component, OnInit } from '@angular/core';
import { UserService } from '../user/UserService';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginUsername: string;
  loginPassword: string;

  registerUsername: string;
  registerPassword: string;
  registerRepeatedPassword: string;

  constructor(private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
  }

  logIn() {
    if (this.userService.logIn(this.loginUsername, this.loginPassword)) {
      this.router.navigate(['/main']).then();
    } else {
      this.loginUsername = '';
      this.loginPassword = '';
    }
  }

  register() {
    if (this.registerPassword === this.registerRepeatedPassword && this.userService.register(this.registerUsername, this.registerPassword)) {
      this.router.navigate(['/main']).then();
    } else {
      this.registerPassword = '';
      this.registerUsername = '';
      this.registerRepeatedPassword = '';
    }
  }
}
