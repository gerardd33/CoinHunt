import { Resource } from '../Resource';
import { Observable } from 'rxjs';
import { UserData } from '../data/UserData';

export abstract class UserService extends Resource {

  abstract logIn(username: string, password: string): Observable<boolean>;

  abstract register(username: string, email: string, password: string): Observable<boolean>;

  abstract logOut(): void;

  abstract isUserLoggedIn(): Observable<boolean>;

  abstract getLoggedInUserId(): Observable<string>;

  abstract getToken(): string | null;

  abstract selectLoginChange(): Observable<void>;

  abstract retrieveUserData(userId: string): Observable<UserData | null>;
}
