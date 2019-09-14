
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {
  MatButtonModule, 
  MatToolbarModule, 
  MatSidenavModule, 
  MatIconModule, 
  MatListModule, 
  MatGridListModule,
  MatCardModule,
  MatTabsModule
  } from '@angular/material';
import { MatInputModule } from '@angular/material/input';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatTooltipModule} from '@angular/material/tooltip';
import {FlexLayoutModule} from "@angular/flex-layout";
import { HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PricelistComponent } from './pricelist/pricelist.component';
import { MyNavComponent } from './my-nav/my-nav.component';
import { LayoutModule } from '@angular/cdk/layout';
import { PricelistsComponent } from './pricelists/pricelists.component';
import {MatTableModule} from '@angular/material';
import { LoginComponent } from './login/login.component';
import {FormsModule} from '@angular/forms';
import {MatDialogModule} from '@angular/material/dialog';
import { ConfirmBuyComponent } from './confirm-buy/confirm-buy.component';
import { PricelistItemComponent } from './pricelist-item/pricelist-item.component';
import {MatSelectModule} from '@angular/material/select';
import { JwtInterceptor } from './interceptors/jwt-interceptor';
import {TicketTypesComponent} from './ticket-types/ticket-types.component';
import { TicketTypeComponent } from './ticket-type/ticket-type.component';
import {TicketTypeService} from './services/ticket-type.service';
import { EditTicketTypeComponent } from './edit-ticket-type/edit-ticket-type.component';
import {ReactiveFormsModule} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import { PricelistDetailComponent } from './pricelist-detail/pricelist-detail.component';
import { EditPricelistComponent } from './edit-pricelist/edit-pricelist.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {DragDropModule} from '@angular/cdk/drag-drop';
import { EditPricelistItemComponent } from './edit-pricelist-item/edit-pricelist-item.component';
import { MatConfirmDialogComponent } from './mat-confirm-dialog/mat-confirm-dialog.component';
import { RegisterPageComponent } from './register-page/register-page.component';
import { StationsComponent } from './stations/stations.component';
import { LoginSmallComponent } from './login-small/login-small.component';
import { HttpErrorInterceptor } from './interceptors/http-error-interceptor';
import { ZonesComponent } from './zones/zones.component';
import { ZoneComponent } from './zone/zone.component';
import { EditZoneComponent } from './edit-zone/edit-zone.component';
import { MapViewComponent } from './map-view/map-view.component';
import { LineTimetableComponent } from './line-timetable/line-timetable.component';
import { LineComponent } from './line/line.component';
import { UserTicketsComponent } from './user-tickets/user-tickets.component';
import { TicketComponent } from './ticket/ticket.component';
import { StatusRequestComponent } from './status-request/status-request.component';
import { TicketCheckComponent } from './ticket-check/ticket-check.component';
import { SendStatusRequestComponent } from './send-status-request/send-status-request.component';
import { StatusRequestsComponent } from './status-requests/status-requests.component';
@NgModule({
  declarations: [
    AppComponent,
    PricelistComponent,
    MyNavComponent,
    PricelistsComponent,
    LoginComponent,
    ConfirmBuyComponent,
    PricelistItemComponent,
    TicketTypesComponent,
    TicketTypeComponent,
    EditTicketTypeComponent,
    PricelistDetailComponent,
    EditPricelistComponent,
    EditPricelistItemComponent,
    MatConfirmDialogComponent,
    RegisterPageComponent,
    StationsComponent,
    LoginSmallComponent,
    MapViewComponent,
    EditZoneComponent,
    ZoneComponent,
    ZonesComponent,
    LineComponent,
    LineTimetableComponent,
    UserTicketsComponent,
    TicketComponent,
    StatusRequestComponent,
    TicketCheckComponent,
    SendStatusRequestComponent,
    StatusRequestsComponent

  ],
  imports: [

    BrowserModule,
    BrowserAnimationsModule,
    MatButtonModule,
    LayoutModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatGridListModule,
    MatCardModule,
    FlexLayoutModule,
    MatIconModule,
    HttpClientModule,
    MatTableModule,
    MatInputModule,
    MatFormFieldModule,
    FormsModule,
    MatDialogModule,
    MatSelectModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSnackBarModule,
    MatDatepickerModule,
    MatTooltipModule,
    DragDropModule,
    AppRoutingModule,
    MatTabsModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true },
  TicketTypeService,
  TicketTypesComponent],
  bootstrap: [AppComponent],
  entryComponents: [ConfirmBuyComponent,
    PricelistItemComponent,
    EditTicketTypeComponent,
    EditPricelistComponent,
    EditPricelistItemComponent,
    MatConfirmDialogComponent,
    EditZoneComponent]
})
export class AppModule { }
