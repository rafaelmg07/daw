package com.daw.themadridnews.search;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.daw.themadridnews.Config;
import com.daw.themadridnews.article.*;
import com.daw.themadridnews.comment.CommentRepository;


@Controller
public class SearchController {
	
	@Autowired
	protected ArticleRepository articleRepository;
	
	@Autowired
	protected CommentRepository commentRepository;
	
	@Autowired
	protected Config config;
	
	
	@RequestMapping("/buscar")
	public String categories(Model model, HttpServletRequest request){
		Page<Article> articles = articleRepository.findByCategory("madrid", new PageRequest(0,10));
		
		//model.addAttribute("searchItem",searchItem);
		
		model.addAttribute("articulos",ArticleView.castList(articles.getContent()));
		
		model.addAttribute("showNext",!articles.isLast());
		model.addAttribute("showPrev",!articles.isFirst());
		model.addAttribute("nextPage",articles.getNumber()+1);
		model.addAttribute("prevPage",articles.getNumber()-1);
		model.addAttribute("currentPage",articles.getNumber()+1);
		model.addAttribute("totalPages",articles.getTotalPages());

		config.setPageParams(model, request);
						
		return "search";
	}
	
}
