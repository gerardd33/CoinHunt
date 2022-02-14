import { Component, OnDestroy, OnInit } from '@angular/core';
import { Maze } from '../data/FieldContent';
import { ReplayGameManager } from '../ReplayGameManager';
import { Subject, takeUntil } from 'rxjs';
import { ReplayGameStatus } from '../ReplayGameStatus';
import { Router } from '@angular/router';

@Component({
  selector: 'app-game-replay',
  templateUrl: './replay-game.component.html',
  styleUrls: ['./replay-game.component.css']
})
export class ReplayGameComponent implements OnInit, OnDestroy {

  maze: Maze;

  status: ReplayGameStatus;

  destroyed$: Subject<void> = new Subject<void>();

  constructor(private replayGameManager: ReplayGameManager,
              private router: Router) { }

  ngOnInit(): void {
    if (this.replayGameManager.getStatus() === ReplayGameStatus.NONE) {
      this.router.navigate(['/main']);
    }

    this.selectMaze();
    this.selectStatus();
  }

  selectMaze() {
    this.replayGameManager.selectMaze()
      .pipe(takeUntil(this.destroyed$))
      .subscribe(maze => {
        this.maze = maze;
      });
  }

  selectStatus() {
    this.replayGameManager.selectStatus()
        .pipe(takeUntil(this.destroyed$))
        .subscribe(status => {
          this.status = status;
        });
  }

  ngOnDestroy(): void {
    this.destroyed$.next();
  }

  isFinished(): boolean {
    return this.status === ReplayGameStatus.FINISHED;
  }

  finishReplay() {
    this.replayGameManager.finishReplay();
  }
}
