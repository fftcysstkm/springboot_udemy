package com.example.demo.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Inquiry;

@Repository
public class InquiryDaoImpl implements InquiryDao{

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public InquiryDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	//新規登録
	@Override
	public void insertInquiry(Inquiry inquiry) {
		jdbcTemplate.update("INSERT INTO inquiry(name, email, contents, created) VALUES(?, ?, ?, ?)",
				inquiry.getName(),inquiry.getEmail(),inquiry.getContents(),inquiry.getCreated());
	}
	//データベースから取り出し。Map<String, Object>が1レコード。これがまとまったListになって返ってくる。
	@Override
	public List<Inquiry> getAll(){
		String sql = "SELECT id, name, email, contents, created FROM inquiry";
		//データベースから戻ってきた結果は、MapのListになっている。
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);

		//Inquiry（Entity）にListの各要素であるMap（レコード）を詰め直す。
		List<Inquiry> list = new ArrayList<Inquiry>();
		for(Map<String, Object> result : resultList) {
			Inquiry inquiry = new Inquiry();
			inquiry.setId((int)result.get("id"));
			inquiry.setName((String)result.get("name"));
			inquiry.setEmail((String)result.get("email"));
			inquiry.setContents((String)result.get("contents"));
			inquiry.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
			list.add(inquiry);
		}
		return list;
	}

}
