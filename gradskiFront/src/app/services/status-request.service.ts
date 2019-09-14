import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NotificationService } from './notification.service';
import { BehaviorSubject } from 'rxjs';
import { StatusRequest } from '../model/status-request';
import { Config } from 'src/config';

@Injectable({
  providedIn: 'root'
})
export class StatusRequestService {

  constructor(
    private http: HttpClient,
    private notificationService: NotificationService
  ) { }

  //
      private statusRequestSource = new BehaviorSubject<StatusRequest[]>([]);
      statusRequestObservable = this.statusRequestSource.asObservable();
      private statusRequests = [];
      private statusRequest;
  //

  private statusRequestUrl = `${Config.apiUrl}statusRequest`;

  addStatusRequest(request){
    this.http.post<StatusRequest>(this.statusRequestUrl, request)
    .subscribe(
      response => 
      {
        this.notificationService.success("Uspesno ste poslali zahtev"); 
      },
      error =>
      {
        this.notificationService.warn("Neuspesno poslat zahtev");
      }
    )
  }

  getStatusRequests() {
    this.http.get<StatusRequest[]>(this.statusRequestUrl)
    .subscribe(statusRequests => {
        this.statusRequests = statusRequests;
        this.statusRequestSource.next(this.statusRequests);
    });
  }
  approveStatusRequest(request){
    this.http.put<StatusRequest>(this.statusRequestUrl+"/approve", request)
    .subscribe(
      statusRequest =>{
        request.approve = true;
        console.log(statusRequest);
        this.notificationService.success("Uspešno ste promenili status korisnika!");
      }
    )
  }
  deleteStatusRequest(id){
    this.http.delete<void>(this.statusRequestUrl+"/"+id).subscribe(
      x=>{
        this.notificationService.success("Zahtev za promenu statusa odbijen.");
      },
      error =>{
        this.notificationService.warn("Greska pri odbijanju zahteva");
      }
    );
  }

  addImage(file: File){
    const sendData = new FormData();
    sendData.append('file', file);
    this.http.post<any>(this.statusRequestUrl + "/uploadImage", sendData
    ).subscribe(
      data => { 
      },
      error => {
        //this.notificationService.warn("Greska pri slanju slike!");
      }
    );
  }

}
