import { Component, OnInit } from '@angular/core';

import { faUser } from '@fortawesome/free-solid-svg-icons';
import { UserService } from '../user/UserService';
import { Router } from '@angular/router';
import { take } from 'rxjs';

@Component({
  selector: 'app-navbar-user-indicator',
  templateUrl: './navbar-user-indicator.component.html',
  styleUrls: ['./navbar-user-indicator.component.css']
})
export class NavbarUserIndicatorComponent implements OnInit {

  faUser = faUser;

  isUserLoggedIn: boolean = false;

  userId: string;

  constructor(private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
    this.retrieveIsUserLoggedIn();
    this.selectLoginChange();
  }

  retrieveIsUserLoggedIn(): void {
    this.userService.isUserLoggedIn()
      .pipe(take(1))
      .subscribe(loggedIn => {
        this.isUserLoggedIn = loggedIn;

        if (this.isUserLoggedIn) this.retrieveLoggedInUserId();
      });
  }

  retrieveLoggedInUserId(): void {
    this.userService.getLoggedInUserId()
      .pipe(take(1))
      .subscribe(id => {
        this.userId = id;
      });
  }

  selectLoginChange(): void {
    this.userService.selectLoginChange()
      .subscribe(() => {
        this.retrieveIsUserLoggedIn();
      });
  }

  performAction() {
    if (!this.isUserLoggedIn) {
      this.router.navigate(['/login']).then();
    } else {
      this.router.navigate(['/user-config']).then();
    }
  }
}
