import { Component, OnInit } from '@angular/core';
import { LineService } from '../services/line.service';


export interface HoursMinutes {
  hours: number;
  minutes: string;

}

const ELEMENT_DATA: HoursMinutes[] = [
  {hours: 5, minutes: '00, 10, 20, 30, 40'},
  {hours: 6, minutes: '15, 30, 45' },
  {hours: 7, minutes: '15, 30, 45'},
  {hours: 8, minutes: '00, 10, 20, 30, 40'},
  {hours: 9, minutes: '15, 30, 45'},
  {hours: 10, minutes: '15, 30, 45'},
  {hours: 11, minutes: '00, 10, 20, 30, 40'},
  {hours: 12, minutes: '15, 30, 45'},
  {hours: 13, minutes: '15, 30, 45'},
  {hours: 14, minutes: '15, 30, 45'},
];
@Component({
  selector: 'app-line-timetable',
  templateUrl: './line-timetable.component.html',
  styleUrls: ['./line-timetable.component.css']
})

export class LineTimetableComponent implements OnInit {

  constructor(
    private lineService : LineService
  ) { }

  private line;
  private lines = [];
  private lineView;

  ngOnInit() {
    this.lineService.findOne(1)
    .subscribe(line =>{

        this.line = line;
        this.lineView = line.concat();
        console.log(this.lineView);
    })
  }

  displayedColumns: string[] = ['hours', 'minutes'];
  dataSource = ELEMENT_DATA;
}


