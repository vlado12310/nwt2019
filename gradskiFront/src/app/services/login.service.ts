import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of, BehaviorSubject} from 'rxjs';
import { Config } from "../../config";
import { User } from '../model/user';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';
@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private http: HttpClient, private router: Router) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    let user : User = new User();
    if(localStorage.getItem('currentUser')){
      user.deserialize(JSON.parse(localStorage.getItem('currentUser')));
      if(user.idUser!=this.currentUserSubject.value.idUser){
        this.currentUserSubject.next(user);
      }
    }
    return this.currentUserSubject.value;
  }

  public get currentUserStatus(): String {
    if(this.currentUserValue){
      return this.currentUserValue.status;
    }
    return "ADMIN";
  }

  login(username: string, password: string) {
    return this.http.post<any>(`${Config.apiUrl}login`, { username, password })
        .pipe(map(userDTO => {
            if (userDTO && userDTO.token) {
                // store user details and jwt token in local storage to keep user logged in between page refreshes
                let user : User = new User().deserialize(userDTO);
                localStorage.setItem('currentUser', JSON.stringify(user));
                this.currentUserSubject.next(user);
            }
            return userDTO;
        })).subscribe();

}
  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }

}
