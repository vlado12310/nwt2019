import { Component, OnInit } from '@angular/core';
import { User } from '../model/user'
import { UserService } from '../services/user.service'
import { FormControl, FormGroupDirective, NgForm, Validators, ValidatorFn, AbstractControl } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Router } from '@angular/router'

import { MatSnackBar } from '@angular/material';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.touched));
  }
}

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent implements OnInit {

  constructor(private userService: UserService, private snackBar : MatSnackBar, private router : Router) { }

  regExpValidator(nameRe: RegExp): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} | null => {
      const forbidden = nameRe.test(control.value);
      return forbidden ? null : {'regexp': {value: control.value}};
    };
  }
  
  passwordFormControl = new FormControl('', [
    Validators.required
  ]);

  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email
  ]);

  passwordRFormControl = new FormControl('', [
    Validators.required
  ]);

  lNameFormControl = new FormControl('', [
    Validators.required
  ]);

  fNameFormControl = new FormControl('', [
    Validators.required
  ]);

  adresaFormControl = new FormControl('', []);
  private onRegisterClick() : void
  {
    let error : boolean = false;
    error = 
       this.fNameFormControl.hasError('required') 
    || this.lNameFormControl.hasError('required') 
    || this.passwordFormControl.hasError('required') 
    || this.passwordRFormControl.hasError('required') 
    || this.emailFormControl.hasError('required')
    || this.emailFormControl.hasError('email');
    
    if (error)
    {
      this.snackBar.open("You must enter all fields correctly!","", {
        duration: 3000,
      });
    } else if (this.passwordFormControl.value!=this.passwordRFormControl.value)
    {
      this.snackBar.open("You must enter matching passwords!","", {
        duration: 3000,
      });
    }  else{

      let user : User =  new User();
      user.idUser = -1;
      user.email = this.emailFormControl.value;
      user.name = this.fNameFormControl.value;
      user.lName = this.lNameFormControl.value;
      user.password = this.passwordFormControl.value;
      user.type = "";
      user.token = "";
      this.userService.register(user).subscribe(
        (data: any) => {
          this.snackBar.open(`Succesfully registered. Please log in.`,"", {
            duration: 3000,
          });
          this.router.navigate(['login'])
        }, 
          error => {console.log(error)} 
      );
    }

  }

  matcher = new MyErrorStateMatcher();

  ngOnInit() {
  }

}
