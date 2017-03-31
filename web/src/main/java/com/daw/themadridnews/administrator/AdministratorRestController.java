package com.daw.themadridnews.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.daw.themadridnews.requests.ApiDataUser;
import com.daw.themadridnews.user.User;
import com.daw.themadridnews.user.UserService;
import com.daw.themadridnews.utils.Message;
import com.fasterxml.jackson.annotation.JsonView;


@RestController
@RequestMapping("/api")
public class AdministratorRestController {
	
	@Autowired
	protected UserService userService;
	
	
	/**
	 * Obtener datos detallados de un usuario
	 */
	@JsonView(UserService.UserDetails.class)
	@RequestMapping(value="/administrador/usuario/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> showFormModify(Model model, @PathVariable long id) {
		User u = userService.get(id);
		if(u == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(u, HttpStatus.OK);
	}
	
	/**
	 * Modificar usuario existente
	 */
	@JsonView(UserService.UserDetails.class)
	@RequestMapping(value="/administrador/usuario/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Object> showFormModify(ApiDataUser r, @PathVariable long id) {
		Message message;
		User u = userService.get(id);
		if(u == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		// Validacion
		message = r.validation();
		if(message.getCode() != 0) return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		
		// Guardar datos
		u.setAlias(r.getAlias());
		u.setName(r.getName());
		u.setLastname(r.getLastname());
		u.setEmail(r.getEmail());
		
		u.setRoles(r.getRoles());
		u.setFavourites(u.getFavourites());
		
		u.setSex(r.getSex());
		u.setCity(r.getCity());
		u.setCountry(r.getCountry());
		u.setPhoneNumber(r.getPhoneNumber());
		u.setDescription(r.getDescription());
		u.setPersonalWeb(r.getPersonalWeb());
		
		u = userService.save(u);
		
		return new ResponseEntity<>(u, HttpStatus.OK);
	}
	
	/**
	 * Eliminar usuario
	 */
	@RequestMapping(value="/administrador/usuario/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable long id) {
		User u = userService.get(id);
		userService.delete(u);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * Lista de usuarios
	 * La pagina comienza en 1
	 */
	@RequestMapping(value="/administrador/usuario/lista/{nPage}", method=RequestMethod.GET)
	public ResponseEntity<Object> list(@PathVariable int nPage) {
		Page<User> page = userService.listWhenPermission(nPage-1);
		return new ResponseEntity<>(page, HttpStatus.OK);
	}
}