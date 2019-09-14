import { Component, OnInit } from '@angular/core';
import { StatusRequestService } from '../services/status-request.service';
import { NotificationService } from '../services/notification.service';
import { Router } from '@angular/router';
import { StatusRequest } from '../model/status-request';

@Component({
  selector: 'app-status-requests',
  templateUrl: './status-requests.component.html',
  styleUrls: ['./status-requests.component.css']
})
export class StatusRequestsComponent implements OnInit {
  statusRequests : StatusRequest[];
  constructor(
              private statusService : StatusRequestService,
              private notificationService : NotificationService,
              private router : Router)
     { }

  ngOnInit() {
    this.statusService.statusRequestObservable.subscribe(statusRequests => {
      this.statusRequests = statusRequests;
      console.log(this.statusRequests);
    });
    this.statusService.getStatusRequests();
  }
  


}
