package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.LinkedCaseInsensitiveMap;

public class selectTopMdl {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	//全社員情報の検索を行うメソッド
	// ■引数
	//   ・syainInfo:社員情報検索結果を格納
	public void selectSyainInfo(List syainInfo) {
		//DBに接続する
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		//社員マスタ、役職マスタで全件検索を行う
		List selectResult = jdbcTemplate.queryForList(
				"SELECT a.syainId,a.syainName,a.affiliation,b.position FROM syainmst a LEFT JOIN positionmst b ON a.positionNum = b.positionNum");
		//書籍情報をLinkedCaseInsensitiveMapに代入し、syainInfoで保持する
		for (int count = 0; count < selectResult.size(); count++) {
			LinkedCaseInsensitiveMap resultCount = (LinkedCaseInsensitiveMap) selectResult.get(count);
			syainInfo.add(resultCount);
		}
	}
}