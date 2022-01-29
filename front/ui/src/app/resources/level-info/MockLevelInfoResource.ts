import { LevelInfoResource } from './LevelInfoResource';
import { Difficulty } from '../../data/Difficulty';
import { delay, Observable, of } from 'rxjs';
import { LevelInfo } from '../../data/LevelInfo';
import { Injectable } from '@angular/core';

@Injectable()
export class MockLevelInfoResource extends LevelInfoResource {

  levelInfoByDifficulty: Map<Difficulty, LevelInfo> = new Map<Difficulty, LevelInfo>();

  constructor() {
    super();

    this.levelInfoByDifficulty.set(Difficulty.EASY, {
      description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ",
      mazeDimension: 5,
      numberOfCoins: 2
    });

    this.levelInfoByDifficulty.set(Difficulty.MEDIUM, {
      description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ",
      mazeDimension: 10,
      numberOfCoins: 5
    });

    this.levelInfoByDifficulty.set(Difficulty.HARD, {
      description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ",
      mazeDimension: 20,
      numberOfCoins: 7
    });
  }

  retrieveLevelInfo(difficulty: Difficulty): Observable<LevelInfo> {
    return of(this.levelInfoByDifficulty.get(difficulty) as LevelInfo);
  }

}
