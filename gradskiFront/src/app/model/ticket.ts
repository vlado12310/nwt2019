import { User } from './user';
import { TicketType } from './TicketType';

export class Ticket {
    id : number;
    User : User;
    timeOfActivation : Date;
    purchaseDate : Date;
    ticketType : TicketType;
}
