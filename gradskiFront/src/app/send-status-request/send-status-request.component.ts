import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import {StatusRequestService} from '../services/status-request.service';
import { StatusRequest } from '../model/status-request';
import { EventManager } from '@angular/platform-browser';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-send-status-request',
  templateUrl: './send-status-request.component.html',
  styleUrls: ['./send-status-request.component.css']
})
export class SendStatusRequestComponent implements OnInit {

  constructor(
    private statusService: StatusRequestService,
    private formBuilder: FormBuilder, 
    private http: HttpClient
  ) { }

  file: File;
  uploadForm: FormGroup;
  public selectedType:string;

  ngOnInit() {
    this.selectedType = "STUDENT";
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });
  }

  valuechangeIMG(event) {
    if(event.target.files.length>0){
      const f: FileList = event.target.files[0];
      this.uploadForm.get('profile').setValue(f);
      this.file = f[0];
    }
  }

  sendStatusRequest(){
    let requestForSend = new StatusRequest;
    requestForSend.type = this.selectedType;
    this.statusService.addImage(this.uploadForm.get('profile').value);
    this.statusService.addStatusRequest(requestForSend);
  }

}
