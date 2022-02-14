import { Component, Input, OnInit } from '@angular/core';
import { Difficulty } from '../data/Difficulty';
import { LevelInfoResource } from '../resources/level-info/LevelInfoResource';
import { LevelInfo } from '../data/LevelInfo';
import { take } from 'rxjs';
import { PlayGameManager } from '../PlayGameManager';
import { UserService } from '../user/UserService';

@Component({
  selector: 'app-game-initiator',
  templateUrl: './game-initiator.component.html',
  styleUrls: ['./game-initiator.component.css']
})
export class GameInitiatorComponent implements OnInit {

  @Input()
  difficulty: Difficulty;

  levelInfoLoaded: boolean = false;

  levelInfo: LevelInfo;

  isUserLoggedIn: boolean = false;

  constructor(private levelInfoResource: LevelInfoResource,
              private playGameManager: PlayGameManager,
              private userService: UserService) { }

  ngOnInit(): void {
    this.retrieveLevelInfo();
    this.retrieveIsUserLoggedIn();
  }

  getBackgroundClass(): string {
    switch (this.difficulty) {
      case Difficulty.EASY:
        return 'easy-background';
      case Difficulty.MEDIUM:
        return 'medium-background';
      case Difficulty.HARD:
        return 'hard-background';
    }
  }

  initializeGame(): void {
    this.playGameManager.initializeGame(this.difficulty);
  }

  retrieveIsUserLoggedIn(): void {
    this.userService.isUserLoggedIn()
      .pipe(take(1))
      .subscribe(loggedIn => {
        this.isUserLoggedIn = loggedIn;
      });
  }

  private retrieveLevelInfo(): void {
    this.levelInfoResource.retrieveLevelInfo(this.difficulty)
      .pipe(take(1))
      .subscribe(info => {
        this.levelInfo = info;
        this.levelInfoLoaded = true;
      });
  }
}
