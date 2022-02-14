import { Component, OnInit } from '@angular/core';
import { UserService } from '../user/UserService';
import { Router } from '@angular/router';
import { take } from 'rxjs';

@Component({
  selector: 'app-user-configuration',
  templateUrl: './user-configuration.component.html',
  styleUrls: ['./user-configuration.component.css']
})
export class UserConfigurationComponent implements OnInit {

  constructor(private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
    this.userService.isUserLoggedIn()
      .pipe(take(1))
      .subscribe(loggedIn => {
        if (!loggedIn) {
          this.router.navigate(['/main']).then();
        }
      });
  }

  logOut(): void {
    this.userService.logOut();
    this.router.navigate(['/main']).then();
  }

}
