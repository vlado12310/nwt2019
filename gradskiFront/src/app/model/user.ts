import { Deserializable } from './deserializable';

export class User implements Deserializable{

    idUser : number;
    email : string;
    type : string;
    token : string;
    name : string;
    lName : string;
    status : string;
    password : string;
    
    deserialize(input: any): this {
        Object.assign(this, input);
        return this;
    }
}