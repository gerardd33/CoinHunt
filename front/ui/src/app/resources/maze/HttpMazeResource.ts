import { MazeResource } from './MazeResource';
import { map, Observable } from 'rxjs';
import { Difficulty } from '../../data/Difficulty';
import { Maze, MazeResponse } from '../../data/FieldContent';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserService } from '../../user/UserService';

@Injectable()
export class HttpMazeResource extends MazeResource {

  constructor(private http: HttpClient,
              private userService: UserService) {
    super();
  }

  retrieveMaze(difficulty: Difficulty): Observable<Maze> {
    return this.http.get<MazeResponse>(`${this.baseUrl}/api/game/new/`, {
      headers: {
        Authorization: this.userService.getToken() as string
      },
      params: {
        difficulty: difficulty.toString()
      }
    }).pipe(
      map(response => response.grid)
    );
  }

}
