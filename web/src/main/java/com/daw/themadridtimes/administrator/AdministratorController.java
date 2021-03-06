package com.daw.themadridtimes.administrator;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.daw.themadridtimes.requests.FormAdminUser;
import com.daw.themadridtimes.user.User;
import com.daw.themadridtimes.user.UserService;
import com.daw.themadridtimes.user.UserView;
import com.daw.themadridtimes.utils.Message;
import com.daw.themadridtimes.utils.ModPagination;
import com.daw.themadridtimes.utils.ModPagination.ModPageItem;


@Controller
public class AdministratorController {
	
	@Autowired
	protected UserService userService;
	
	
	@RequestMapping(value="/administrador", method=RequestMethod.GET)
	public ModelAndView showAdminIndex(HttpServletResponse response) {
		return new ModelAndView( new RedirectView("/administrador/usuario/lista") );
	}
	
	@RequestMapping(value="/administrador/usuario/{id}", method=RequestMethod.GET)
	public ModelAndView showFormModify(Model model, @PathVariable long id) {
		Message message;
		User user = userService.get(id);
		
		if(user == null) {
			message = new Message(1, "El usuario no existe. Por favor, seleccione uno de la lista.", "danger");
			model.addAttribute("message", message);
			return showList(model, 1);
		}

		UserView uv = new UserView(user);
		
		model.addAttribute("fuser_name", uv.getName());
		model.addAttribute("fuser_lastname", uv.getLastname());
		model.addAttribute("fuser_alias", uv.getAlias());
		model.addAttribute("fuser_role_editor", uv.hasRole("ROLE_EDITOR"));
		model.addAttribute("fuser_role_publicist", uv.hasRole("ROLE_PUBLICIST"));
		model.addAttribute("fuser_role_admin", uv.hasRole("ROLE_ADMIN"));
		
		return new ModelAndView("admin_user_form");
	}
	
	@RequestMapping(value="/administrador/usuario/{id}", method=RequestMethod.POST)
	public ModelAndView showFormModify(Model model, FormAdminUser r, @PathVariable long id) {
		Message message;
		User user = userService.get(id);
		
		if(user == null) {
			message = new Message(1, "El usuario no existe. Por favor, seleccione uno de la lista.", "danger");
			model.addAttribute("message", message);
			return showList(model, 1);
		}
		
		message = r.validation();
		if(message.getCode() != 0) {
			model.addAttribute("message", message);
			return showFormModify(model, id);
		}
		
		List<String> roles = r.getRoles();
		roles.add("ROLE_USER");

		user.setName(r.getName());
		user.setLastname(r.getLastname());
		user.setAlias(r.getAlias());
		user.setRoles(roles);
		userService.save(user);
		
		return new ModelAndView( new RedirectView("/administrador/usuario/lista/guardado") );
	}
	
	@RequestMapping(value="/administrador/usuario/{id}/eliminar", method=RequestMethod.GET)
	public ModelAndView delete(Model model, @PathVariable long id) {
		User user = userService.get(id);
		userService.delete(user);
		return new ModelAndView( new RedirectView("/administrador/usuario/lista/eliminado") );
	}
	
	@RequestMapping(value="/administrador/usuario/lista", method=RequestMethod.GET)
	public ModelAndView showList(Model model, @RequestParam(required=false) Integer page) {
		if(page == null || page.intValue() < 0) {
			page = 0;
		} else {
			page--;
		}
		
		return showListAux(model, 0);
	}
	
	@RequestMapping(value="/administrador/usuario/lista/guardado", method=RequestMethod.GET)
	public ModelAndView showListPublished(Model model) {
		Message message = new Message(0, "El usuario ha sido guardado correctamente", "success");
		model.addAttribute("message", message);
		return showListAux(model, 0);
	}
	
	@RequestMapping(value="/administrador/usuario/lista/eliminado", method=RequestMethod.GET)
	public ModelAndView showListDeleted(Model model) {
		Message message = new Message(0, "El usuario ha sido eliminado correctamente", "success");
		model.addAttribute("message", message);
		return showListAux(model, 0);
	}
	
	private ModelAndView showListAux(Model model, int page) {
		Page<User> p = userService.listWhenPermission(page);
		
		List<User> userList = p.getContent();
		model.addAttribute("fuser_list", UserView.castList(userList) );
		
		ModPagination modPagination = new ModPagination();
		List<ModPageItem> pageList = modPagination.getModPageList(p, "/administrador/usuario/lista?page=");
		model.addAttribute("page_list", pageList);
		
		return new ModelAndView("admin_user_list");
	}
}
