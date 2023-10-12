package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.LinkedCaseInsensitiveMap;

public class filterSearchMdl {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//絞り込み検索メソッド
	public void searchInfo(String syainName, List syainInfo, List errorMsg) {
		//DBに接続する
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		
		//入力された社員名に該当する情報を取得する
		List selectResult = jdbcTemplate.queryForList(
				"SELECT a.syainId,a.syainName,a.affiliation,b.position FROM syainmst a LEFT JOIN positionmst b ON a.positionNum = b.positionNum WHERE syainName Like ?",
				"%"+syainName+"%");
		
		if (selectResult.size() > 0) {
			//社員情報をLinkedCaseInsensitiveMapに代入し、syainInfoで保持する
			for (int count = 0; count < selectResult.size(); count++) {
				LinkedCaseInsensitiveMap infoRecord = (LinkedCaseInsensitiveMap) selectResult.get(0);
				syainInfo.add(infoRecord);
			}
		}else {
			//検索結果が無い場合、エラーメッセージを設定する
			errorMsg.add("入力された社員名に該当する情報はありません");
		}
	}
}