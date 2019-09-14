import { Deserializable } from './deserializable';
import {Location } from './Location';

export class Station implements Deserializable {

    id : number;
    name : String;
    location : Location;
    

    deserialize(input: any): this {
        Object.assign(this, input);
        this.location = new Location().deserialize(input.location);
        return this;
    }
    toListItemString(): String {
        return this.name;
    }
    
}