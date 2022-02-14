import { LevelInfoResource } from './LevelInfoResource';
import { Difficulty } from '../../data/Difficulty';
import { Observable, of } from 'rxjs';
import { LevelInfo } from '../../data/LevelInfo';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class HttpLevelInfoResource extends LevelInfoResource {

  constructor(private http: HttpClient) {
    super();
  }

  retrieveLevelInfo(difficulty: Difficulty): Observable<LevelInfo> {
    return this.http.get<LevelInfo>(`${this.baseUrl}/api/difficulty/info/`, {
      headers: {
        "Access-Control-Allow-Origin": "*"
      },
      params: {
        difficulty: difficulty.toString()
      }
    });
  }

}
