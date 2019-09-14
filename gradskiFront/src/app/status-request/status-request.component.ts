import { Component, OnInit, Input} from '@angular/core';
import { StatusRequest } from '../model/status-request';
import { StatusRequestService } from '../services/status-request.service';
import { NotificationService } from '../services/notification.service';
import { HttpClient } from 'selenium-webdriver/http';

@Component({
  selector: 'app-status-request',
  templateUrl: './status-request.component.html',
  styleUrls: ['./status-request.component.css']
})
export class StatusRequestComponent implements OnInit {
  @Input() request;
  printType: string;
  img: File;
  dateTimeNow : string; 
  visible : boolean;
  constructor(
    private statusService : StatusRequestService,
    private notificationService : NotificationService) { }

  ngOnInit() {
    this.visible = true;
    this.dateTimeNow = Date.now().toString();
    if(this.request.type=="STUDENT"){
      this.printType = "Zahtev za studentski nalog."
    }else{
      this.printType = "Zahtev za penzionerski nalog"
    }
    //sliku nabaviti 
  }

  initImg(){
    
  }

  approve(){
    this.statusService.approveStatusRequest(this.request);
    this.request.approved = true;
    
  }

  delete(){
    this.statusService.deleteStatusRequest(this.request.id);
    this.request.approved = true;
  }


}
