import { UserService } from './UserService';
import { Injectable } from '@angular/core';

interface UserData {
  userId: number,
  username: string,
  password: string
}

@Injectable()
export class InMemoryUserService extends UserService {

  userDataByUsername: Map<string, UserData> = new Map<string, UserData>();
  userDataByUserId: Map<number, UserData> = new Map<number, UserData>();

  userLoggedIn: boolean = false;

  loggedInUserData: UserData;

  nextUserId: number = 3;

  constructor() {
    super();

    this.userDataByUsername.set('user1', {
      userId: 1,
      username: 'user1',
      password: 'password1'
    });
    this.userDataByUsername.set('user2', {
      userId: 2,
      username: 'user2',
      password: 'password2'
    });

    this.userDataByUserId.set(1, {
      userId: 1,
      username: 'user1',
      password: 'password1'
    });
    this.userDataByUserId.set(2, {
      userId: 2,
      username: 'user2',
      password: 'password2'
    });

  }

  logIn(username: string, password: string): boolean {
    let userData: UserData = this.userDataByUsername.get(username) as UserData;

    if (userData && userData.password === password) {
      this.userLoggedIn = true;
      this.loggedInUserData = userData;
      return true;
    }

    return false;
  }

  register(username: string, password: string): boolean {
    if (username in this.userDataByUsername.keys()) {
      return false;
    }

    let newUserData: UserData = {
      userId: this.nextUserId,
      username: username,
      password: password
    };

    this.nextUserId++;

    this.userDataByUsername.set(username, newUserData);
    this.userDataByUserId.set(newUserData.userId, newUserData);

    this.loggedInUserData = newUserData;
    this.userLoggedIn = true;

    return true;
  }

  logOut(): void {
    this.userLoggedIn = false;
  }

  isUserLoggedIn(): boolean {
    return this.userLoggedIn;
  }

  getLoggedInUserId(): number {
    if (!this.isUserLoggedIn()) throw new Error("No user logged in");

    return this.loggedInUserData.userId;
  }

  getLoggedInUsername(): string {
    if (!this.isUserLoggedIn()) throw new Error("No user logged in");

    return this.loggedInUserData.username;
  }

}
