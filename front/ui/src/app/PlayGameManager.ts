import { Injectable } from '@angular/core';
import { Difficulty } from './data/Difficulty';
import { Router } from '@angular/router';
import { MazeResource } from './resources/maze/MazeResource';
import { Observable, ReplaySubject, Subject, take } from 'rxjs';
import { FieldContent, Maze } from './data/FieldContent';
import { PlayGameStatus } from './PlayGameStatus';
import { StepDirection } from './data/StepDirection';

type Location = { x: number, y: number };

@Injectable()
export class PlayGameManager {

  private status: PlayGameStatus = PlayGameStatus.NONE;

  private maze: Maze;

  private difficulty: Difficulty;

  private playerLocation: Location;

  private remainingCoins: number;

  private status$: Subject<PlayGameStatus> = new ReplaySubject<PlayGameStatus>(1);

  private maze$: Subject<Maze> = new ReplaySubject<Maze>(1);

  constructor(private router: Router,
              private mazeResource: MazeResource) {
  }

  initializeGame(difficulty: Difficulty): void {
    this.mazeResource.retrieveMaze(difficulty)
      .pipe(take(1))
      .subscribe(maze => {
        this.difficulty = difficulty;
        this.maze = maze;
        this.playerLocation = this.findPlayer(maze);
        this.remainingCoins = this.countCoins(maze);

        this.notifyMazeChange();
        this.nextStatus(PlayGameStatus.NOT_STARTED);

        this.router.navigate(['/play']).then();
      })
  }

  startGame(): void {
    this.nextStatus(PlayGameStatus.IN_PROGRESS);
  }

  move(direction: StepDirection): void {
    let newPlayerLocation: Location = this.getNewPlayerLocation(direction);
    if (!this.isInsideMaze(newPlayerLocation) || this.getFieldOnLocation(newPlayerLocation) == FieldContent.WALL) return;

    if (this.getFieldOnLocation(newPlayerLocation) == FieldContent.COIN) {
      this.remainingCoins--;
    }

    this.changeFieldOnLocation(newPlayerLocation, FieldContent.PLAYER);
    this.changeFieldOnLocation(this.playerLocation, FieldContent.EMPTY);
    this.playerLocation = newPlayerLocation;

    this.notifyMazeChange();
    if (this.remainingCoins == 0) this.finishGame();
  }

  getStatus(): PlayGameStatus {
    return this.status;
  }

  getDifficulty(): Difficulty {
    return this.difficulty;
  }

  selectStatus(): Observable<PlayGameStatus> {
    return this.status$.asObservable();
  }

  selectMaze(): Observable<Maze> {
    return this.maze$.asObservable();
  }

  private finishGame(): void {
    this.nextStatus(PlayGameStatus.FINISHED);
  }

  private notifyMazeChange(): void {
    this.maze$.next(this.maze);
  }

  private nextStatus(status: PlayGameStatus): void {
    this.status = status;
    this.status$.next(status);
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

  private getMazeDimension(): number {
    return this.maze.length;
  }

  private isInsideMaze(location: Location): boolean {
    return location.x >= 0 && location.x < this.getMazeDimension() && location.y >= 0 && location.y < this.getMazeDimension();
  }

  private getFieldOnLocation(location: Location): FieldContent {
    return this.maze[location.x][location.y];
  }

  private changeFieldOnLocation(location: Location, field: FieldContent): void {
    this.maze[location.x][location.y] = field;
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

  private countCoins(maze: Maze): number {
    let result: number = 0;

    for (let row = 0; row < this.getMazeDimension(); row++) {
      for (let col = 0; col < this.getMazeDimension(); col++) {
        let field: FieldContent = maze[row][col];

        if (field === FieldContent.COIN) {
          result++;
        }
      }
    }

    return result;
  }

}
