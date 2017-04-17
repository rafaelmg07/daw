import {Injectable} from '@angular/core';
import 'rxjs/Rx';
import {MessageObject} from "../shared/message.object";

@Injectable()
export class MessageService {

  constructor() {
    console.log("Init MessageService")
  }

  // Obtener lista de anuncios
  public getMessage(n:number) {
    let message;

    console.log(n);

    if(this.messagesList[n]) {
      message = this.messagesList[n];
    } else {
      message = { "code":-1, "message":"Error desconocido", "isError":true };
    }

    console.log(message);

    return message;
  }

  public getMessageFromApi(message:string) {
    return { "code":1, "message":message, "isError":true };
  }

  /**
   * LISTA DE MENSAJES
   */
  private messagesList = {
    // 1: Errores devueltos por la API

    // 2-99: Reservado

    // 100-199: AdministratorListComponent y AdministratorFormComponent
    '100': { "code":100, "message":"Los datos del usuario han sido guardados correctamente", "isError":false },
    '101': { "code":101, "message":"No se ha podido encontrar el usuario en la base de datos", "isError":true },
    '102': { "code":102, "message":"Ha ocurrido un error cuando se intentaba guardar los datos", "isError":true }
  };
}
