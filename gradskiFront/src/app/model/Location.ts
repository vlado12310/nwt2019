import { Deserializable } from './deserializable';

export class Location implements Deserializable{
    lat : Number;
    lng : Number;
    deserialize(input: any): this {
        Object.assign(this, input);
        return this;
    }

}