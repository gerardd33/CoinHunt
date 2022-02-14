import { Component, OnInit } from '@angular/core';
import { GamePersistenceService } from '../game-persistence/GamePersistenceService';
import { Difficulty } from '../data/Difficulty';
import { CompletedGameFilter } from '../data/CompletedGameFilter';
import { take } from 'rxjs';
import { CompletedGame } from '../data/CompletedGame';

@Component({
  selector: 'app-leaderboards',
  templateUrl: './leaderboards.component.html',
  styleUrls: ['./leaderboards.component.css']
})
export class LeaderboardsComponent implements OnInit {

  lastWeekEntries: Array<CompletedGame>;
  lastWeekDifficulty: Difficulty = Difficulty.HARD;

  allTimeEntries: Array<CompletedGame>;
  allTimeDifficulty: Difficulty = Difficulty.HARD;

  constructor(private gamePersistenceService: GamePersistenceService) { }

  ngOnInit(): void {
    this.updateLastWeekLeaderboard(Difficulty.HARD);
    this.updateAllTimeLeaderboard(Difficulty.HARD);
  }

  updateLastWeekLeaderboard(newDifficulty: Difficulty) {
    this.lastWeekDifficulty = newDifficulty;
    this.gamePersistenceService.retrieveBestGames(this.lastWeekDifficulty, CompletedGameFilter.BEST_WEEK)
      .pipe(take(1))
      .subscribe(entries => {
        this.lastWeekEntries = entries;
      });
  }

  updateAllTimeLeaderboard(newDifficulty: Difficulty) {
    this.allTimeDifficulty = newDifficulty;
    this.gamePersistenceService.retrieveBestGames(this.allTimeDifficulty, CompletedGameFilter.BEST_ALL_TIME)
      .pipe(take(1))
      .subscribe(entries => {
        this.allTimeEntries = entries;
      });
  }

}
