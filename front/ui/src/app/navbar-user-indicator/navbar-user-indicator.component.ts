import { Component, OnInit } from '@angular/core';

import { faUser } from '@fortawesome/free-solid-svg-icons';
import { UserService } from '../user/UserService';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar-user-indicator',
  templateUrl: './navbar-user-indicator.component.html',
  styleUrls: ['./navbar-user-indicator.component.css']
})
export class NavbarUserIndicatorComponent implements OnInit {

  faUser = faUser;

  constructor(private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
  }

  isUserLoggedIn(): boolean {
    return this.userService.isUserLoggedIn();
  }

  getUsername(): string {
    return this.userService.getLoggedInUsername();
  }

  performAction() {
    if (!this.isUserLoggedIn()) {
      this.router.navigate(['/login']).then();
    } else {
      this.router.navigate(['/user-config']).then();
    }
  }
}
