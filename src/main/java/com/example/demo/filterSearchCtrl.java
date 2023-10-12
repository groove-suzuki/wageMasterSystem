package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class filterSearchCtrl {

	//　画面の入力情報を取得、画面に情報を返却
	//■引数
	//  ・form:画面とサーバー間の情報のやりとりを行う
	//■戻り値
	//  ・map:画面へ返却したい項目を格納
	@RequestMapping(value = "/filterSearch", method = RequestMethod.POST)
	@ResponseBody
	public Map filterSearchCtrl(@RequestBody selectFuncForm form) {
		Map<String, Object> map = new HashMap<String, Object>();
		//Modelクラスをインスタンス化
		filterSearchMdl mdl = new filterSearchMdl();
		//画面での入力値を取得する
		String syainName = form.getInputValue();
		List syainInfo = new ArrayList();
		List errorMsg = new ArrayList();
		//modelクラスの絞り込み検索メソッドを呼び出し
		mdl.searchInfo(syainName, syainInfo, errorMsg);
		//画面へ返却する項目を設定
		map.put("syainInfo", syainInfo);
		map.put("errorMsg", errorMsg);
		return map;
	}
}