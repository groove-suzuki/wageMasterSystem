package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.LinkedCaseInsensitiveMap;

public class selectDetailInfoMdl {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//詳細検索メソッドを呼び出し
	public void selectDetailInfo(List detailInfo, String syainId) {
		//DBに接続する
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		//社員マスタ、役職マスタ、住所マスタ、家族マスタ、入社日マスタで検索を行う
		List selectResult = jdbcTemplate.queryForList(
				"SELECT a.syainId,a.syainName,a.affiliation,a.baseSalary,b.position,b.position_Allowance,c.address,c.residentTax,d.familyStructure,d.family_Allowance, CEIL(DATEDIFF(CURDATE(), e.hire_date) / 365) as serviceLength FROM "
						+ "syainmst a LEFT JOIN positionmst b ON a.positionNum = b.positionNum "
						+ "INNER JOIN addressmst c ON a.addressNum = c.addressNum "
						+ "LEFT JOIN familymst d ON a.familyNum = d.familyNum "
						+ "INNER JOIN hiredatemst e ON a.syainId = e.syainId WHERE a.syainId = ?",
				syainId);
		//詳細情報をLinkedCaseInsensitiveMapに代入し、detaiInfoで保持する
		LinkedCaseInsensitiveMap infoRecord = (LinkedCaseInsensitiveMap) selectResult.get(0);
		detailInfo.add(infoRecord);
	}
}