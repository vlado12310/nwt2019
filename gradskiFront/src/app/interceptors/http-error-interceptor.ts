import {
    HttpEvent,
    HttpInterceptor,
    HttpHandler,
    HttpRequest,
    HttpResponse,
    HttpErrorResponse
   } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { MatSnackBar } from '@angular/material';
import { Injectable } from '@angular/core';
@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {
constructor(private snackBar: MatSnackBar) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      return next.handle(request)
        .pipe(
          retry(1),
          catchError((error: HttpErrorResponse) => {
            let errorMessage = '';
            if (error.error instanceof ErrorEvent) {
              // client-side error
            } else {
              // server-side error
              if(error.error){
                if(error.error.message){
                  errorMessage=error.error.message;
                }
              }else{
                errorMessage=`Something went wrong. :(`;
              }
            }
            this.snackBar.open(errorMessage,"", {
                duration: 3000,
              });
            return throwError(errorMessage);
          })
        )
    }
   }
