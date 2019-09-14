import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../model/user';
import { map } from 'rxjs/operators';
import { ModelUtils } from '../model/model-utils';
import { Observable } from 'rxjs';
import { Config } from 'src/config';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) { }

  getAll() : Observable<User[]> {
      return this.http.get<any>(`${Config.apiUrl}users`).pipe(map(users => {
        return ModelUtils.deserializeObjects(users, User);
      }));
  }
  
  getById(id: number) : Observable<User> {
      return this.http.get<any>(`${Config.apiUrl}users/${id}`).pipe(
          map(user => {return new User().deserialize(user);}));
  }

  register(user: User) {
      return this.http.post(`${Config.apiUrl}users`, user);
  }

  update(user: User) {
      return this.http.put(`${Config.apiUrl}users/${user.idUser}`, user);
  }

  delete(id: number) {
      return this.http.delete(`${Config.apiUrl}users/${id}`);
  }
}
