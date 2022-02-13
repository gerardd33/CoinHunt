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
import { PlayGameComponent } from './play-game/play-game.component';
import { PlayGameManager } from './PlayGameManager';
import { MazeResource } from './resources/maze/MazeResource';
import { MazeComponent } from './maze/maze.component';
import { MazeFieldComponent } from './maze-field/maze-field.component';
import { GamePersistenceService } from './game-persistence/GamePersistenceService';
import { UserService } from './user/UserService';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NavbarUserIndicatorComponent } from './navbar-user-indicator/navbar-user-indicator.component';
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';
import { UserConfigurationComponent } from './user-configuration/user-configuration.component';
import { RankingComponent } from './ranking/ranking.component';
import { HttpLevelInfoResource } from './resources/level-info/HttpLevelInfoResource';
import { HttpClientModule } from '@angular/common/http';
import { HttpMazeResource } from './resources/maze/HttpMazeResource';
import { HttpGamePersistenceService } from './game-persistence/HttpGamePersistenceService';
import { HttpUserService } from './user/HttpUserService';

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
    FormsModule,
    HttpClientModule
  ],
  providers: [
    { provide: LevelInfoResource, useClass: HttpLevelInfoResource },
    { provide: MazeResource, useClass: HttpMazeResource },
    { provide: GamePersistenceService, useClass: HttpGamePersistenceService },
    { provide: UserService, useClass: HttpUserService },
    PlayGameManager
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
