import { NgModule } from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {PricelistComponent} from './pricelist/pricelist.component';
import {PricelistsComponent} from './pricelists/pricelists.component';
import {LoginComponent} from './login/login.component';
import {ConfirmBuyComponent} from './confirm-buy/confirm-buy.component';
import {TicketTypesComponent} from './ticket-types/ticket-types.component';
import {PricelistDetailComponent} from './pricelist-detail/pricelist-detail.component';
import {RegisterPageComponent} from './register-page/register-page.component';
import {ZonesComponent} from './zones/zones.component';
import {AuthGuard} from 'src/app/guards/auth-guard';
import {MapViewComponent} from './map-view/map-view.component';
import {UserTicketsComponent} from './user-tickets/user-tickets.component';
import { TicketCheckComponent } from './ticket-check/ticket-check.component';
import {StatusRequestsComponent} from './status-requests/status-requests.component'
import { SendStatusRequestComponent } from './send-status-request/send-status-request.component';

const routes: Routes = [
  {path: 'pricelist/:id', component : PricelistDetailComponent},
  {path: 'pricelists', component : PricelistsComponent, canActivate: [AuthGuard], data: { roles: ['USER', 'ADMIN'] }  },
  {path: 'pricelists/active', component : PricelistDetailComponent},
  {path: 'login', component : LoginComponent},
  {path: 'ticketTypes', component: TicketTypesComponent},
  {path: 'mapaProba', component: ConfirmBuyComponent},
  {path: 'mapa', component: MapViewComponent},
  {path: 'register', component: RegisterPageComponent},
  {path: 'zones', component: ZonesComponent},
  {path: 'myTickets', component: UserTicketsComponent},
  {path: 'checkTicket', component: TicketCheckComponent},
  {path: 'sendRequest', component: SendStatusRequestComponent},
  {path: 'checkRequests', component: StatusRequestsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
