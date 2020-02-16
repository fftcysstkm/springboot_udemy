package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Survey;


public interface SurveyDao {

	//データ登録
	void insertSurvey(Survey survey);

	//一覧をリストとして取得
	List<Survey> getAll();

}