package eightball.spring.redis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import aj.org.objectweb.asm.Handle;
import eightball.spring.redis.service.RedisService;

@Controller
public class EightballController {

	private RedisService service;
	
	@Autowired
	public void setService(RedisService service) {
		this.service = service;
	}
	
	@RequestMapping("/")
	public String index() {
		return "Eightball";
	}
	
	@PostMapping("/questionAction")
	public String setQuestion(@RequestParam("question") String question, Model model) {
		
		String questionString = question;
		String answer;
		if (service.getKey(questionString) != null) {
			answer = service.getKey(questionString);
		}
		else {
			if (service.scard("answer") >= 1) {
				answer = service.spop("answer");
			}
			else {
				byte[] answer1 = "It is certain".getBytes();
				byte[] answer2 = "It is decidedly so".getBytes();
				byte[] answer3 = "Without a doubt".getBytes();
				byte[] answer4 = "Yes definitely".getBytes();
				byte[] answer5 = "You may rely on it".getBytes();
				byte[] answer6 = "As I see it, yes".getBytes();
				byte[] answer7 = "Most likely".getBytes();
				byte[] answer8 = "Outlook good".getBytes();
				byte[] answer9 = "Yes".getBytes();
				byte[] answer10 = "Signs point to yes".getBytes();
				byte[] answer11 = "Reply hazy try again".getBytes();
				byte[] answer12 = "Ask again later".getBytes();
				byte[] answer13 = "Better not tell you now".getBytes();
				byte[] answer14 = "Cannot predict now".getBytes();
				byte[] answer15 = "Concentrate and ask again".getBytes();
				byte[] answer16 = "My reply is no".getBytes();
				byte[] answer17 = "Don't count on it".getBytes();
				byte[] answer18 = "My sources say no".getBytes();
				byte[] answer19 = "Outlook not so good".getBytes();
				byte[] answer20 = "Very doubtful".getBytes();
				service.sadd("answers",answer1,answer2,answer3,answer4,answer5,answer6,answer7,answer8,answer9,answer10,answer11,answer12,answer13,answer14,answer15,answer16,answer17,answer18,answer19,answer20);
				answer = service.spop("answers");
			}
			service.setKey(question, answer);
		}
		
		model.addAttribute("answer",answer);
		return "Validation";
	}
}
