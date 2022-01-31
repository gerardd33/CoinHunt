import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CommonModule } from '@angular/common';
import { MainComponent } from './main/main.component';
import { LeaderboardsComponent } from './leaderboards/leaderboards.component';
import { NavbarComponent } from './navbar/navbar.component';
import { GameInitiatorComponent } from './game-initiator/game-initiator.component';
import { LevelInfoResource } from './resources/level-info/LevelInfoResource';
import { MockLevelInfoResource } from './resources/level-info/MockLevelInfoResource';
import { PlayGameComponent } from './play-game/play-game.component';
import { PlayGameManager } from './PlayGameManager';
import { MazeResource } from './resources/maze/MazeResource';
import { MockMazeResource } from './resources/maze/MockMazeResource';
import { MazeComponent } from './maze/maze.component';
import { MazeFieldComponent } from './maze-field/maze-field.component';
import { GamePersistenceService } from './game-persistence/GamePersistenceService';
import { MockGamePersistenceService } from './game-persistence/MockGamePersistenceService';
import { UserService } from './user/UserService';
import { InMemoryUserService } from './user/InMemoryUserService';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NavbarUserIndicatorComponent } from './navbar-user-indicator/navbar-user-indicator.component';
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';
import { UserConfigurationComponent } from './user-configuration/user-configuration.component';
import { RankingComponent } from './ranking/ranking.component';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    LeaderboardsComponent,
    NavbarComponent,
    GameInitiatorComponent,
    PlayGameComponent,
    MazeComponent,
    MazeFieldComponent,
    NavbarUserIndicatorComponent,
    LoginComponent,
    UserConfigurationComponent,
    RankingComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    FontAwesomeModule,
    FormsModule
  ],
  providers: [
    { provide: LevelInfoResource, useClass: MockLevelInfoResource },
    { provide: MazeResource, useClass: MockMazeResource },
    { provide: GamePersistenceService, useClass: MockGamePersistenceService },
    { provide: GamePersistenceService, useClass: MockGamePersistenceService },
    { provide: UserService, useClass: InMemoryUserService },
    PlayGameManager
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
