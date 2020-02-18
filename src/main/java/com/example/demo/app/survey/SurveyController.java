package com.example.demo.app.survey;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

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

import com.example.demo.entity.Survey;
import com.example.demo.service.SurveyService;

@Controller
@RequestMapping("/survey")
public class SurveyController{

	private final SurveyService surveyService;

	@Autowired
	public SurveyController(SurveyService surveyService) {
		this.surveyService = surveyService;
	}

	//初回のアクセス時の画面
	//ModelAttributeはリダイレクトで飛んできたときのデータを受け取る。
	@GetMapping("/form")
	public String form(SurveyForm surveyForm,
			Model model,
			@ModelAttribute("complete")String complete) {
		model.addAttribute("title", "Survey Form");
		return "survey/form";
	}

	//アンケート一覧。Serveceクラスを使って一覧のリストをmodelへ。
	@GetMapping
	public String index(Model model) {
		List<Survey> list = surveyService.getAll();
		model.addAttribute("surveyList", list);
		model.addAttribute("title", "Survey Index");
		return  "survey/index";
	}

	//確認ページ。入力チェック結果がBindingResultに格納される。
	@PostMapping("/confirm")
	public String confirm(@Validated SurveyForm surveyForm,
			BindingResult result,
			Model model) {
		//入力に間違いがあった場合→エラーメッセージとともにformへ戻す。
		if(result.hasErrors()) {
			model.addAttribute("title", "Survey Form");
			return "survey/form";
		}
		//OKならconfirm.htmlを返す。
		model.addAttribute("title", "Confirm Page");
		return "survey/confirm";
	}

	//確認画面から、戻るボタンでもどったときの画面
	@PostMapping("/form")
	public String formGoBack(SurveyForm surveyForm, Model model) {
		model.addAttribute("title", "Survey Form");
		return "survey/form";
	}

	//完了ページ
	@PostMapping("/complete")
	public String complete(
			@Valid @ModelAttribute SurveyForm surveyForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			model.addAttribute("title", "Survey Form");
			return "survey/form";
		}

		//Surveyクラス(Entity)にsurveyFormの値を詰め直す。
		Survey survey = new Survey();
		survey.setAge(surveyForm.getAge());
		survey.setSatisfaction(surveyForm.getSatisfaction());
		survey.setComment(surveyForm.getComment());
		survey.setCreated(LocalDateTime.now());

		//DB登録
		surveyService.insertSurvey(survey);

		redirectAttributes.addFlashAttribute("complete", "completed!");
		return "redirect:/survey/form";
	}


}