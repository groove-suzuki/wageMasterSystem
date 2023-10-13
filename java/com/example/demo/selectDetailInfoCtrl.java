package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class selectDetailInfoCtrl{
	//　/selectDetailInfoでリクエスト時に子画面を呼び出す
	//■引数
	//  ・/selectDetailInfo:リクエスト時のurl情報
	//■戻り値
	//  ・detailInfoScr:子画面名
	@RequestMapping("/selectDetaileInfo")
	public String searchScr() {
		return "detaileInfoScr";
	}
	
	//　画面の入力情報を取得、画面に情報を返却
		//■引数
		//  ・form:画面とサーバー間の情報のやりとりを行う
		//■戻り値
		//  ・map:画面へ返却したい項目を格納
	@RequestMapping("/detaileSelect")
	@ResponseBody
	public Map selectDetailInfoCtrl(@RequestBody selectFuncForm form) {
		Map<String, Object> map = new HashMap<String, Object>();
		//Modelクラスをインスタンス化
		selectDetailInfoMdl mdl = new selectDetailInfoMdl();
		//画面で選択された社員名の社員Idを取得する
		String syainId=form.getSelectedSyainId();
		List detaileInfo=new ArrayList();
		//modelクラスの詳細検索メソッドを呼び出し
		mdl.selectDetailInfo(detaileInfo,syainId);
		map.put("detaileInfo", detaileInfo);
		return map;
	}
}