import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../user/UserService';
import { UserData } from '../data/UserData';
import { Subject, take, takeUntil } from 'rxjs';
import { Difficulty } from '../data/Difficulty';
import { CompletedGame } from '../data/CompletedGame';
import { GamePersistenceService } from '../game-persistence/GamePersistenceService';
import { CompletedGameFilter } from '../data/CompletedGameFilter';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit, OnDestroy {

  userId: string;

  userData: UserData;

  entries: Array<CompletedGame> = [];

  difficulty: Difficulty = Difficulty.EASY;

  private destroyed$: Subject<void> = new Subject<void>();

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private gamePersistenceService: GamePersistenceService
  ) {}

  ngOnInit(): void {
    this.selectParams();
  }

  ngOnDestroy(): void {
    this.destroyed$.next();
  }

  private selectParams() {
    this.route.params
        .pipe(takeUntil(this.destroyed$))
        .subscribe(params => {
          this.userId = params['userId'];

          this.retrieveUserData();
          this.update(Difficulty.EASY);
        });
  }

  private retrieveUserData() {
    this.userService.retrieveUserData(this.userId)
        .pipe(take(1))
        .subscribe(data => {
          if (!data) {
            this.router.navigate(['/main']).then();
            return;
          }

          this.userData = data as UserData;
        });
  }

  update(difficulty: Difficulty): void {
    this.difficulty = difficulty;

    this.gamePersistenceService.retrieveBestGamesForUser(this.userId, this.difficulty, CompletedGameFilter.BEST_ALL_TIME)
      .pipe(take(1))
      .subscribe(entries => {
        this.entries = entries;
      });

  }
}
