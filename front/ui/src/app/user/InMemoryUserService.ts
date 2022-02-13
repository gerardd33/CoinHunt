import { UserService } from './UserService';
import { Injectable } from '@angular/core';

interface UserData {
  userId: string,
  username: string,
  password: string
}

@Injectable()
export class InMemoryUserService extends UserService {

  userDataByUsername: Map<string, UserData> = new Map<string, UserData>();
  userDataByUserId: Map<string, UserData> = new Map<string, UserData>();

  userLoggedIn: boolean = false;

  loggedInUserData: UserData;

  nextUserId: string = "xd";

  constructor() {
    super();

    this.userDataByUsername.set('user1', {
      userId: "user1id",
      username: 'user1',
      password: 'password1'
    });
    this.userDataByUsername.set('user2', {
      userId: "user2id",
      username: 'user2',
      password: 'password2'
    });

    this.userDataByUserId.set("user1id", {
      userId: "user1id",
      username: 'user1',
      password: 'password1'
    });
    this.userDataByUserId.set("user2id", {
      userId: "user2id",
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

  getLoggedInUserId(): string {
    if (!this.isUserLoggedIn()) throw new Error("No user logged in");

    return this.loggedInUserData.userId;
  }

  getLoggedInUsername(): string {
    if (!this.isUserLoggedIn()) throw new Error("No user logged in");

    return this.loggedInUserData.username;
  }

}
