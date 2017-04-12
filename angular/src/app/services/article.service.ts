import {Injectable, OnInit} from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import 'rxjs/Rx';

import { Category } from '../entity/category.entity';
import { URL_API } from "../shared/config.service";
import {User} from "../entity/user.entity";
import {SessionService} from "./session.service";

@Injectable()
export class ArticleService {

  private categories:Category[] = [
    { id:"madrid",     name:"Madrid" },
    { id:"espana",     name:"España" },
    { id:"mundo",      name:"Mundo" },
    { id:"deportes",   name:"Deportes" },
    { id:"tecnologia", name:"Tecnologia" },
    { id:"cultura",    name:"Cultura" }
  ];

  constructor(private http:Http, private sessionService:SessionService) {
    console.log("# Init ArticleService")
  }

  public getCategories() {
    return this.categories;
  }

  public getCategoryById(id:string):Category {
    for(let i = 0; i < this.categories.length; i++) {
      if(this.categories[i].id == id)
        return this.categories[i];
    }

    return null;
  }

  // Ultimos articulos publicados
  public getLastArticles(number:number) {
    let url = URL_API+"/articulos?number="+number;
    return this.http.get(url).map(
      response => response.json()
    );
  }

    // Ultimos articulos publicados en una categoria determinada. Las paginas empiezan en 1
  public getArticlesFromCategory(id:string, page:number, number:number) {
    let url = URL_API+"/articulos/"+id+"?page="+page+"&number="+number;
    return this.http.get(url).map(
      response => response.json().content.map( article => article )
    );
  }

  // Articulos para el carrousel
  public carrousel() {
    let url = URL_API+"/articulos/carrousel";
    return this.http.get(url).map(
      response => response.json()
    );
  }

  // Devuelve ultimas noticias en categoria de favoritos
  public favourites() {
    let headers, options;

    // Verificar si esta logeado
    if(this.sessionService.isUserLogged()) {
      headers = new Headers({ 'Authorization': this.sessionService.getAuthHeader() });
    } else {
      headers = new Headers({});
    }

    options = new RequestOptions({ headers: headers });

    let url = URL_API+"/articulos/favoritos";
    return this.http.get(url, options).map(
      response => response.json()
    );
  }
}