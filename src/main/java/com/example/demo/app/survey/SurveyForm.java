package com.example.demo.app.survey;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SurveyForm {
	//年齢
	@Max(150)
	@Min(1)
	private int age;

	//満足度
	@NotNull
	@Min(1)
	@Max(5)
	private int satisfaction;

	//コメント
	@NotNull
	private String comment;


	//？？コンストラクタ。
	public SurveyForm() {
	}


	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getSatisfaction() {
		return satisfaction;
	}
	public void setSatisfaction(int satisfaction) {
		this.satisfaction = satisfaction;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}


}