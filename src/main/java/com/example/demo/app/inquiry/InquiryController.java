package com.example.demo.app.inquiry;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

	@GetMapping("/form")//inqury/formにGetメソッドでアクセスされると以下実行される
	public String form(InquiryForm inquiryForm,
			Model model,
			@ModelAttribute("complete")String complete) {
		//@ModelAttributeでフラッシュスコープのデータ引き継げる
		model.addAttribute("title", "Inquiry Form");

		//inqyiryフォルダのform.htmlを返す。
		return "inquiry/form";
	}

	//戻るボタンで戻った先の処理
	@PostMapping("/form")
	public String formGoBack(InquiryForm inquiryForm, Model model) {
		model.addAttribute("title", "Inquiry Form");
		//inqyiryフォルダのform.htmlを返す。
		return "inquiry/form";
	}

	//入力フォームからPOSTメソッドでアクセスがあったら、下記実行される。
	//BindingResultにバリデーション結果が入っている。
	@PostMapping("/confirm")
	public String confirm(@Validated InquiryForm inquiryForm,
			BindingResult result, Model model) {
		//エラーがあったらフォームのページに戻す。
		if(result.hasErrors()) {
			model.addAttribute("title", "Inquiry Form");
			return "inquiry/form";
		}
		//エラーがなければ確認ページへ。
		model.addAttribute("title", "Confirm Page");
		return "inquiry/confirm";
	}

	//（完了ページボタンを押したあと）完了ページ遷移後の処理
	@PostMapping("/complete")
	public String complete(@Validated InquiryForm inquiryForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		//エラーがあったらフォームのページに戻す。
		if(result.hasErrors()) {
			model.addAttribute("title","InquiryForm");
			return "inquiry/form";
		}

		//フラッシュスコープ
		redirectAttributes.addFlashAttribute("complete", "Registered!");
		return "redirect:/inquiry/form";
	}

}
