import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { PlayGameManager } from '../PlayGameManager';
import { Router } from '@angular/router';
import { PlayGameStatus } from '../PlayGameStatus';
import { Subject, takeUntil } from 'rxjs';
import { Maze } from '../data/FieldContent';
import { StepDirection } from '../data/StepDirection';
import { Difficulty } from '../data/Difficulty';

@Component({
  selector: 'app-play-game',
  templateUrl: './play-game.component.html',
  styleUrls: ['./play-game.component.css']
})
export class PlayGameComponent implements OnInit, OnDestroy {

  maze: Maze;

  status: PlayGameStatus;

  difficulty: Difficulty;

  destroyed$: Subject<void> = new Subject<void>();

  constructor(private route: Router,
              private playGameManager: PlayGameManager) {}

  ngOnInit(): void {
    if (this.playGameManager.getStatus() == PlayGameStatus.NONE) {
      this.route.navigate(['/main']).then();
    }

    this.selectMaze();
    this.selectStatus();
    this.difficulty = this.playGameManager.getDifficulty();
  }

  ngOnDestroy() {
    this.destroyed$.next();
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent): void {
    if (this.status != PlayGameStatus.IN_PROGRESS) return;

    switch (event.key) {
      case 'w':
        this.playGameManager.move(StepDirection.UP);
        break;
      case 's':
        this.playGameManager.move(StepDirection.DOWN);
        break;
      case 'd':
        this.playGameManager.move(StepDirection.RIGHT);
        break;
      case 'a':
        this.playGameManager.move(StepDirection.LEFT);
        break;
    }
  }

  isNotStarted(): boolean {
    return this.status === PlayGameStatus.NOT_STARTED;
  }

  startGame(): void {
    this.playGameManager.startGame();
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

  private selectMaze() {
    this.playGameManager.selectMaze()
      .pipe(takeUntil(this.destroyed$))
      .subscribe(maze => {
        this.maze = maze;
      })
  }

  private selectStatus() {
    this.playGameManager.selectStatus()
        .pipe(takeUntil(this.destroyed$))
        .subscribe(status => {
          this.status = status;
        })
  }
}
