package com.example.demo.app.inquiry;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

	@GetMapping("/form")//inqury/formにGetメソッドでアクセスされると以下実行される
	public String form(Model model) {
		model.addAttribute("title", "Inquiry Form");

		//inqyiryフォルダのform.htmlを返す。
		return "inquiry/form";
	}
}
