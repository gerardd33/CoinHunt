import { Component, Input, OnInit } from '@angular/core';
import { Difficulty } from '../data/Difficulty';
import { LevelInfoResource } from '../resources/level-info/LevelInfoResource';
import { LevelInfo } from '../data/LevelInfo';
import { take } from 'rxjs';

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

  constructor(private levelInfoResource: LevelInfoResource) { }

  ngOnInit(): void {
    this.retrieveLevelInfo();
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

  private retrieveLevelInfo(): void {
    this.levelInfoResource.retrieveLevelInfo(this.difficulty)
      .pipe(
        take(1)
      )
      .subscribe(info => {
        this.levelInfo = info;
        this.levelInfoLoaded = true;
      });
  }

}
