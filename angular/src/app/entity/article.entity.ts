import {User} from "./user.entity";
export class Article {
  public id:number;
  public title:string;
  public dateInsert:Date;
  public nComments:number;
  public author:User;
}
