package edu.ap.spring.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import edu.ap.spring.redis.RedisService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuoteController {

   @Autowired
   private RedisService service;
	 
   @GetMapping("/listauthors")
   public String listAuthors(Model model) {

	ArrayList<String> authors = new ArrayList<String>();
	for(String a : this.service.keys("author:*")) {
		authors.add(this.service.getKey(a));
	}
	model.addAttribute("authors", authors);

	return "listAuthors";
   }

   @GetMapping("/listquotes/{authorid}")
   public String listQuotesById(@PathVariable("authorid") int authorid, 
   							Model model) {

	ArrayList<String> quotes = new ArrayList<String>();
	for(String a : this.service.keys("quote:" + authorid + ":*")) {
		quotes.add(this.service.getKey(a));
	}

	String author = this.service.getKey(this.service.keys("author:*:" + authorid).iterator().next());
	model.addAttribute("quotes", quotes);
	model.addAttribute("author", author);

	return "listQuotes";
   }

   @GetMapping("/author")
   public String getAuthorForm() {
	return "addAuthor";
   }

   @PostMapping("/author")
   public String addAuthor(@RequestParam("firstName") String firstName, 
						   @RequestParam("lastName") String lastName, 
						   Model model) {
	   
	if(this.service.exists("authorcount")) {
		this.service.incr("authorcount");
	}
	else {
		this.service.setKey("authorcount", "1");
	}   
	
	this.service.setKey("author:" + firstName + lastName + ":" + this.service.getKey("authorcount"),  firstName + " " + lastName);

	return "redirect:listauthors";
   }

   @GetMapping("/quote")
   public String getQuoteForm(Model model) {

	ArrayList<String> authors = new ArrayList<String>();
	for(String a : this.service.keys("author:*")) {
		authors.add(this.service.getKey(a));
	}
	model.addAttribute("authors", authors);

	return "addQuote";
   }

   @PostMapping("/quote")
   public String addQuote(@RequestParam("quote") String quote, 
   						  @RequestParam("author") String author) {

	String[] authorName = author.split(" ");
	String authorKey = this.service.keys("author:" + authorName[0] + authorName[1] + ":*").iterator().next();
	if(this.service.exists("quotecount")) {
		this.service.incr("quotecount");
	}
	else {
		this.service.setKey("quotecount", "1");
	}     

	this.service.setKey("quote:" + authorKey.split(":")[2] + ":" + this.service.getKey("quotecount"), quote);

	return "redirect:listquotes/" + authorKey.split(":")[2];
   }
}
