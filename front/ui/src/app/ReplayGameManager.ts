import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CompletedGame } from './data/CompletedGame';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { FieldContent, Maze } from './data/FieldContent';
import { GameStep } from './data/GameStep';
import { StepDirection } from './data/StepDirection';
import { ReplayGameStatus } from './ReplayGameStatus';

type Location = { x: number, y: number };

@Injectable()
export class ReplayGameManager {

  private maze: Maze;

  private status: ReplayGameStatus = ReplayGameStatus.NONE;

  private playerLocation: Location;

  private maze$: Subject<Maze> = new ReplaySubject<Maze>(1);

  private status$: Subject<ReplayGameStatus> = new ReplaySubject<ReplayGameStatus>(1);

  private nextTimeout: any;

  constructor(private router: Router) {

  }

  startReplay(completedGame: CompletedGame): void {
    clearTimeout(this.nextTimeout);

    this.nextStatus(ReplayGameStatus.IN_PROGRESS);
    this.router.navigate(['/replay']).then();

    this.maze = completedGame.maze.grid;
    this.playerLocation = this.findPlayer(this.maze);

    this.notifyMazeChange();
    this.startSimulation(completedGame.steps);
  }

  finishReplay() {
    this.nextStatus(ReplayGameStatus.NONE);
    this.router.navigate(['/leaderboards']).then();
  }

  selectMaze(): Observable<Maze> {
    return this.maze$.asObservable();
  }

  selectStatus(): Observable<ReplayGameStatus> {
    return this.status$.asObservable();
  }

  getStatus(): ReplayGameStatus {
    return this.status;
  }

  private startSimulation(steps: Array<GameStep>) {
    this.scheduleStep(0, steps);
  }

  private scheduleStep(index: number, steps: Array<GameStep>): void {
    if (index >= steps.length) {
      this.nextStatus(ReplayGameStatus.FINISHED);
      return;
    }

    const step: GameStep = steps[index];

    this.nextTimeout = setTimeout(() => {
      this.move(step.direction);
      this.scheduleStep(index + 1, steps);
    }, step.millisecondsSinceLastStep);
  }

  private move(direction: StepDirection) {
    let newPlayerLocation: Location = this.getNewPlayerLocation(direction);

    this.changeFieldOnLocation(newPlayerLocation, FieldContent.PLAYER);
    this.changeFieldOnLocation(this.playerLocation, FieldContent.EMPTY);
    this.playerLocation = newPlayerLocation;

    this.notifyMazeChange();
  }

  private changeFieldOnLocation(location: Location, field: FieldContent): void {
    this.maze[location.x][location.y] = field;
  }

  private getNewPlayerLocation(direction: StepDirection): Location {
    switch (direction) {
      case StepDirection.DOWN:
        return {x: this.playerLocation.x + 1, y: this.playerLocation.y};

      case StepDirection.UP:
        return {x: this.playerLocation.x - 1, y: this.playerLocation.y};

      case StepDirection.LEFT:
        return {x: this.playerLocation.x, y: this.playerLocation.y - 1};

      case StepDirection.RIGHT:
        return {x: this.playerLocation.x, y: this.playerLocation.y + 1};
    }
  }

  private findPlayer(maze: Maze): Location {
    for (let row = 0; row < this.getMazeDimension(); row++) {
      for (let col = 0; col < this.getMazeDimension(); col++) {
        let field: FieldContent = maze[row][col];

        if (field === FieldContent.PLAYER) {
          return { x: row, y: col };
        }
      }
    }

    throw new Error("No player found on the maze.");
  }

  private getMazeDimension(): number {
    return this.maze.length;
  }

  private notifyMazeChange(): void {
    this.maze$.next(this.maze);
  }

  private nextStatus(status: ReplayGameStatus): void {
    this.status = status;
    this.status$.next(status);
  }
}
