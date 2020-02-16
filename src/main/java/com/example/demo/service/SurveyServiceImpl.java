package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Survey;
import com.example.demo.repository.SurveyDao;

@Service
public class SurveyServiceImpl implements SurveyService {

	private final SurveyDao dao;

	@Autowired
	public SurveyServiceImpl(SurveyDao dao) {
		this.dao = dao;
	}

	//データ登録
	@Override
	public void insert(Survey survey) {
		dao.insertSurvey(survey);
	}

	//一覧取得
	@Override
	public List<Survey> getAll() {
		return dao.getAll();
	}

}
