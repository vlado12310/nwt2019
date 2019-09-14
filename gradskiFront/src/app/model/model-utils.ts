import { Deserializable } from './deserializable';

export class ModelUtils
{
    static deserializeObjects<T extends Deserializable>(objects : any[], clasz: { new(): T ;} ) : T[]
    {
        let ret : T[] = [];
        for(let object of objects){
            ret.push(new clasz().deserialize(object));
        }
        return ret;
    }
}