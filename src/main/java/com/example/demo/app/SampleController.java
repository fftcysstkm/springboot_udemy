package com.example.demo.app;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * Add annotations here
 */
@Controller
@RequestMapping("/sample")//ドメイン以降のURLになる。
public class SampleController {

 	private final JdbcTemplate jdbcTemplate;

 	//コンストラクタにjdbcTemplateを引数として渡す。
 	@Autowired
 	public SampleController(JdbcTemplate jdbcTemplate) {
 		this.jdbcTemplate = jdbcTemplate;
 	}


	@GetMapping("/test")//sample/testにアクセスがあると下記が実行される。
	public String test(Model model) {

		String sql = "SELECT id, name, email "
				+ "FROM inquiry WHERE id = 1";
		//DBのカラムの型がintだったり、Stringだったりする。まとめてObject型にしておく。
		Map<String, Object> map = jdbcTemplate.queryForMap(sql);


		//htmlに渡したいデータがある　→　modelに渡す。
		model.addAttribute("title", "Inquiry Form");//第一引数がThymeleafと対応。
		model.addAttribute("name", map.get("name"));
		model.addAttribute("email", map.get("email"));

		return "test";
	}

}
