import { UserService } from './UserService';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, of, Subject, take } from 'rxjs';
import { UserIdResponse } from '../data/UserIdResponse';
import { AuthTokenResponse } from '../data/AuthTokenResponse';

@Injectable()
export class HttpUserService extends UserService {

  private static TOKEN_LOCALSTORAGE_KEY: string = 'auth-token';

  userLoggedIn: boolean = false;
  loggedInUserId: string;

  loginChange$: Subject<void> = new Subject<void>();

  constructor(private http: HttpClient) {
    super();
  }

  getLoggedInUserId(): Observable<string> {
    if (this.userLoggedIn) return of(this.loggedInUserId);

    return this.http.get<UserIdResponse>(`${this.baseUrl}/api/account/auth`, {
      headers: {
        Authorization: this.getToken() as string
      }
    }).pipe(
      map(userIdResponse => userIdResponse.userId)
    );
  }

  isUserLoggedIn(): Observable<boolean> {
    if (!this.isTokenPresent()) return of(false);
    if (this.userLoggedIn) return of(true);

    return this.http.get<UserIdResponse>(`${this.baseUrl}/api/account/auth`, {
      headers: {
        Authorization: this.getToken() as string
      },
      observe: 'response'
    }).pipe(
      map(response => {
        if (response.status == 200) {
          this.userLoggedIn = true;
          this.loggedInUserId = response.body?.userId as string;
          return true;
        }

        return false;
      })
    );
  }

  logIn(username: string, password: string): Observable<boolean> {
    const body = { userId: username, password: password };

    return this.http.post<AuthTokenResponse>(`${this.baseUrl}/api/account/login`, body, {
      observe: 'response'
    }).pipe(
      map(response => {
        if (response.status === 200) {
          localStorage.setItem(HttpUserService.TOKEN_LOCALSTORAGE_KEY, response.body?.token as string);
          this.loginChange$.next();
          return true;
        }

        return false;
      })
    );
  }

  logOut(): void {
    this.userLoggedIn = false;
    localStorage.removeItem(HttpUserService.TOKEN_LOCALSTORAGE_KEY);
    this.loginChange$.next();
  }

  register(username: string, password: string): Observable<boolean> {
    const body = { userId: username, password: password };

    return this.http.post<AuthTokenResponse>(`${this.baseUrl}/api/account/register`, body, {
      observe: 'response'
    }).pipe(
      map(response => {
        if (response.status === 200) {
          localStorage.setItem(HttpUserService.TOKEN_LOCALSTORAGE_KEY, response.body?.token as string);
          this.loginChange$.next();
          return true;
        }

        return false;
      })
    );
  }

  getToken(): string | null {
    return localStorage.getItem(HttpUserService.TOKEN_LOCALSTORAGE_KEY);
  }

  selectLoginChange(): Observable<void> {
    return this.loginChange$.asObservable();
  }

  private isTokenPresent(): boolean {
    return !!localStorage.getItem(HttpUserService.TOKEN_LOCALSTORAGE_KEY);
  }

}
