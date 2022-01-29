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

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    LeaderboardsComponent,
    NavbarComponent,
    GameInitiatorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule
  ],
  providers: [
    { provide: LevelInfoResource, useClass: MockLevelInfoResource }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
