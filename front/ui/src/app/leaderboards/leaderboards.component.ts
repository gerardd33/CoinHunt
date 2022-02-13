import { Component, OnInit } from '@angular/core';
import { CompletedGameEntry } from '../data/CompletedGameEntry';
import { GamePersistenceService } from '../game-persistence/GamePersistenceService';
import { Difficulty } from '../data/Difficulty';
import { CompletedGameFilter } from '../data/CompletedGameFilter';
import { take } from 'rxjs';

@Component({
  selector: 'app-leaderboards',
  templateUrl: './leaderboards.component.html',
  styleUrls: ['./leaderboards.component.css']
})
export class LeaderboardsComponent implements OnInit {

  lastWeekEntries: Array<CompletedGameEntry>;
  lastWeekDifficulty: Difficulty = Difficulty.HARD;

  allTimeEntries: Array<CompletedGameEntry>;
  allTimeDifficulty: Difficulty = Difficulty.HARD;

  constructor(private gamePersistenceService: GamePersistenceService) { }

  ngOnInit(): void {
    this.updateLastWeekLeaderboard(Difficulty.HARD);
    this.updateAllTimeLeaderboard(Difficulty.HARD);
  }

  updateLastWeekLeaderboard(newDifficulty: Difficulty) {
    this.lastWeekDifficulty = newDifficulty;
    this.gamePersistenceService.retrieveBestGamesEntries(this.lastWeekDifficulty, CompletedGameFilter.BEST_WEEK)
      .pipe(take(1))
      .subscribe(entries => {
        this.lastWeekEntries = entries;
      });
  }

  updateAllTimeLeaderboard(newDifficulty: Difficulty) {
    this.allTimeDifficulty = newDifficulty;
    this.gamePersistenceService.retrieveBestGamesEntries(this.allTimeDifficulty, CompletedGameFilter.BEST_ALL_TIME)
      .pipe(take(1))
      .subscribe(entries => {
        this.allTimeEntries = entries;
      });
  }

}
