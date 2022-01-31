import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CompletedGameEntry } from '../data/CompletedGameEntry';
import { Difficulty } from '../data/Difficulty';


@Component({
  selector: 'app-ranking',
  templateUrl: './ranking.component.html',
  styleUrls: ['./ranking.component.css']
})
export class RankingComponent implements OnInit {

  difficultyEnum = Difficulty;

  @Input()
  entries: Array<CompletedGameEntry>;

  @Output()
  difficultyChanged: EventEmitter<Difficulty> = new EventEmitter<Difficulty>();

  difficulty: Difficulty = Difficulty.HARD;

  constructor() { }

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
}
