import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './main/main.component';
import { LeaderboardsComponent } from './leaderboards/leaderboards.component';
import { PlayGameComponent } from './play-game/play-game.component';
import { LoginComponent } from './login/login.component';
import { UserConfigurationComponent } from './user-configuration/user-configuration.component';

const routes: Routes = [
  { path: 'main', component: MainComponent },
  { path: 'leaderboards', component: LeaderboardsComponent },
  { path: 'play', component: PlayGameComponent },
  { path: 'login', component: LoginComponent },
  { path: 'user-config', component: UserConfigurationComponent },
  { path: '**', component: MainComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
