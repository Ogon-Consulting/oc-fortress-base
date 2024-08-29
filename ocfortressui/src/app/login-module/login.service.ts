import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import CryptoJS from 'crypto-js';

@Injectable({
  providedIn: 'root'
})


export class LoginService {

  userVerifyApiUrl: string = 'http://localhost:6080/api/v1/verifyuser';
  #loggedUserName = signal<string>('');

  constructor(private http: HttpClient, private route: Router) { }

  set loggedUserName(loggedUserName: string) {
    this.#loggedUserName.set(loggedUserName);
  }

  get loggedUserName(): string {
    return this.#loggedUserName();
  }

  async verifyUser(userId: string, password: string) {
    let params = new HttpParams();
    params = params.append('userId', userId);
    const hashedPassword = this.hashPassword(password);
    params = params.append('password', password);
    const response = await firstValueFrom(this.http.get<any>(this.userVerifyApiUrl, { params }));
    return response.status;
  }

  hashPassword(password: string): string {
    // Hash the password using SHA-256
    return CryptoJS.SHA256(password).toString(CryptoJS.enc.Hex);
  }
}
