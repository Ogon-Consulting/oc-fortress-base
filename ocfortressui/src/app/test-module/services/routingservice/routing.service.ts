import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RoutingService {

  constructor(private router:Router) { }

  goToHome() {
    this.router.navigate(['/home']);
  }

  goTologin() {
    this.router.navigate(['/login']);
  }

  routeToHistory() {
    this.router.navigate(['/history']);
  }

  routeToJobLog() {
    this.router.navigate(['/joblog']);
  }

  goToScheduleHistory() {
    this.router.navigate(['/schedules']);
  }

  goToBundlesList() {
    this.router.navigate(['/bundles']);
  }

  routeToUserManagement(){
    this.router.navigate(['/usersearch']);
  }

  routeToScheduleHistories() {
    this.router.navigate(['/schedules']);
  }
  routeToEditUser() {
    this.router.navigate(['/edituser']);
  }
}
