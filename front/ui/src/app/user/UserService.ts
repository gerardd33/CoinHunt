export abstract class UserService {

  abstract logIn(username: string, password: string): boolean;

  abstract register(username: string, password: string): boolean;

  abstract logOut(): void;

  abstract isUserLoggedIn(): boolean;

  abstract getLoggedInUserId(): number;

  abstract getLoggedInUsername(): string;
}
