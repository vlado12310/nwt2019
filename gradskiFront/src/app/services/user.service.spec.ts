import { TestBed } from '@angular/core/testing';

import { UserService } from './user.service';
import { of } from 'rxjs';
import { User } from '../model/user';
import { ModelUtils } from '../model/model-utils';
import { HttpClientModule } from '@angular/common/http';

describe('UserService', () => {
  let usersService: UserService; // Add this
  const userResponse = [
    {
      idUser: 1,
      email: 'pera@gmail.com',
      status: 'STUDENT',
      name: 'Petar',
      lName: 'Petrovic',
      token: '$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK'
    },
    {
      idUser: 2,
      email: 'steva@gmail.com',
      status: 'ADMIN',
      name: 'Stevan',
      lName: 'Stevanovic',
      token: '$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK'
    }
  ];

  const peraUser : User =  new User().deserialize( {
    email: 'pera@gmail.com',
    status: 'USER',
    name: 'PETAR',
    lName: 'PErkanovic',
  });
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientModule],

      providers: [UserService ]
    });

    usersService = TestBed.get(UserService); // Add this
  });



  it('should be created', () => {
    expect(usersService).toBeTruthy();
  });

  describe('all', () => {
    it('should return a collection of users', () => {
      let response;
      spyOn(usersService, 'getAll').and.returnValue(of(ModelUtils.deserializeObjects(userResponse, User)));

      usersService.getAll().subscribe(res => {
        response = res;
      });

      expect(response).toEqual(ModelUtils.deserializeObjects(userResponse, User));
    });
  });

  describe('byID', () => {
    it('user by id', () => {
      let response;
      spyOn(usersService, 'getById').and.callFake((id : Number)=>{
         return of(new User().deserialize(userResponse.find(user=>user.idUser==id)));
      });

      usersService.getById(1).subscribe(res => {
        response = res;
      });

      expect(response).toEqual(new User().deserialize(userResponse[0]));
    });
  });

  describe('register', () => {
    it('register user', () => {
      let response;
      spyOn(usersService, 'register').and.callFake((user : User)=>{
         return of(user);
      });

      usersService.register(peraUser).subscribe(res => {
        response = res;
      });

      expect(response).toEqual(peraUser);
    });
  });

  describe('register', () => {
    it('register user', () => {
      let response;
      spyOn(usersService, 'register').and.callFake((user : User)=>{
         return of(user);
      });

      usersService.register(peraUser).subscribe(res => {
        response = res;
      });

      expect(response).toEqual(peraUser);
    });
  });


  describe('update', () => {
    it('update user', () => {
      let response;
      spyOn(usersService, 'register').and.callFake((user : User)=>{
         return of(user);
      });
      peraUser.lName="Zika";
      usersService.register(peraUser).subscribe(res => {
        response = res;
      });

      expect(response).toEqual(peraUser);
    });
  });


  describe('delete', () => {
    it('delete user', () => {
      let response;
      spyOn(usersService, 'delete').and.callFake((user : User)=>{
         return of(user);
      });

      usersService.register(peraUser).subscribe(res => {
        response = res;
      });

      expect(response).toEqual(peraUser);
    });
  });
});

