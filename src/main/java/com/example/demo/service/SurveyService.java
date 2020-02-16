package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Survey;

public interface SurveyService {

	void insert(Survey survey);

	List<Survey> getAll();

}