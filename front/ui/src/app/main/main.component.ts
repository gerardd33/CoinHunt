import { Component, OnInit } from '@angular/core';
import { Difficulty } from '../data/Difficulty';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  difficultyEnum = Difficulty;

  constructor() { }

  ngOnInit(): void {
  }

}
