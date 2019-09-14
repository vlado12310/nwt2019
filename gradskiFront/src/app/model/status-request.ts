import { User } from './user';

export class StatusRequest {
    id : number;
    approved : boolean;
    user : User;
    type : string;
    imgUrl : string;
}