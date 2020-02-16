package com.example.demo.app.inquiry;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Inquiry;
import com.example.demo.service.InquiryService;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

	//型はインターフェース型にする。
	private final InquiryService inquiryService;

	//このInquiryServiceからRepositoryクラスのCRUD関係メソッドを実行
	//型はインターフェースだが、@ServiceをつけているServiceImplがinjectされる。
	@Autowired
	public InquiryController(InquiryService inquiryService) {
		this.inquiryService = inquiryService;
	}

	//お問い合わせの一覧
	@GetMapping//引数なしアノテーションで、（"/inquiry"）と同じ意味
	public String index(Model model) {
		List<Inquiry> list = inquiryService.getAll();
		model.addAttribute("inquiryList", list);
		model.addAttribute("title", "Inquiry Index");

		return "inquiry/index";
	}


	@GetMapping("/form")
	public String form(InquiryForm inquiryForm,
			Model model,
			@ModelAttribute("complete")String complete) {
		//@ModelAttributeでフラッシュスコープのデータ引き継げる
		model.addAttribute("title", "Inquiry Form");

		//inqyiryフォルダのform.htmlを返す。
		return "inquiry/form";
	}

	//戻るボタンで戻った後の処理
	@PostMapping("/form")
	public String formGoBack(InquiryForm inquiryForm, Model model) {
		model.addAttribute("title", "Inquiry Form");
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

		//Inquiry（Entityクラス）にFormクラスの値をを詰める。これをサービスクラスを介してDaoに渡す。
		Inquiry inquiry = new Inquiry();
		inquiry.setName(inquiryForm.getName());
		inquiry.setEmail(inquiryForm.getEmail());
		inquiry.setContents(inquiryForm.getContents());
		inquiry.setCreated(LocalDateTime.now());

		//上で値をつめたinquiryをサービスクラスに渡す。
		//新規登録のsaveメソッド。
		inquiryService.save(inquiry);

		//フラッシュスコープ
		redirectAttributes.addFlashAttribute("complete", "Registered!");
		return "redirect:/inquiry/form";
	}

}
