import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Difficulty } from '../data/Difficulty';
import { CompletedGame } from '../data/CompletedGame';
import { ReplayGameManager } from '../ReplayGameManager';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ranking',
  templateUrl: './ranking.component.html',
  styleUrls: ['./ranking.component.css']
})
export class RankingComponent implements OnInit {

  difficultyEnum = Difficulty;

  @Input()
  entries: Array<CompletedGame>;

  @Output()
  difficultyChanged: EventEmitter<Difficulty> = new EventEmitter<Difficulty>();

  difficulty: Difficulty = Difficulty.HARD;

  constructor(private replayGameManager: ReplayGameManager,
              private router: Router) { }

  ngOnInit(): void {
  }

  changeDifficulty(difficulty: Difficulty) {
    this.difficulty = difficulty;
    this.difficultyChanged.emit(difficulty);
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

  getClassForActiveDifficultySwitcher(difficulty: Difficulty): string {
    if (this.difficulty !== difficulty) return '';
    return this.getBackgroundClass();
  }

  startReplay(completedGame: CompletedGame): void {
    this.replayGameManager.startReplay(completedGame);
  }

  navigateToUserPage(userId: string) {
    this.router.navigate(['/user', userId]).then();
  }
}
