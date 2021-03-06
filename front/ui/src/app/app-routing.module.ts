import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './main/main.component';
import { LeaderboardsComponent } from './leaderboards/leaderboards.component';
import { PlayGameComponent } from './play-game/play-game.component';
import { LoginComponent } from './login/login.component';
import { UserConfigurationComponent } from './user-configuration/user-configuration.component';
import { ReplayGameComponent } from './game-replay/replay-game.component';
import { UserPageComponent } from './user-page/user-page.component';

const routes: Routes = [
  { path: 'main', component: MainComponent },
  { path: 'leaderboards', component: LeaderboardsComponent },
  { path: 'play', component: PlayGameComponent },
  { path: 'replay', component: ReplayGameComponent },
  { path: 'login', component: LoginComponent },
  { path: 'user-config', component: UserConfigurationComponent },
  { path: 'user/:userId', component: UserPageComponent },
  { path: '**', component: MainComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
