import { MazeResource } from './MazeResource';
import { Observable } from 'rxjs';
import { Difficulty } from '../../data/Difficulty';
import { Maze } from '../../data/FieldContent';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class HttpMazeResource extends MazeResource {

  constructor(private http: HttpClient) {
    super();
  }

  retrieveMaze(difficulty: Difficulty): Observable<Maze> {
    return this.http.get<Maze>(`${this.baseUrl}/api/game/new/`, {
      headers: {
        Authorization: "xd" // TODO
      },
      params: {
        difficulty: difficulty.toString()
      }
    });
  }

}
