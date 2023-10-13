package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class selectTopCtrl {

	// /systemTopでリクエスト時に画面を呼び出す
	//■引数
	//  ・/systemTop:リクエスト時のurl情報
	//■戻り値
	//  ・systemTopScr:初期画面名
	@RequestMapping("/systemTop")
	public String searchScr() {
		return "systemTopScr";
	}

	//画面の入力情報を取得、処理結果を返却
	//■引数
	//  ・form:画面とサーバー間の情報のやりとりを行う
	//■戻り値
	//  ・mv:画面へ返却したい項目を格納
	@RequestMapping("/selectTop")
	public ModelAndView selectTopCtrl() {
		//ModelAndViewをインスタンス化
		ModelAndView mv = new ModelAndView();
		//モデルクラスをインスタンス化
		selectTopMdl mdl = new selectTopMdl();
		List syainInfo = new ArrayList();
		//モデルクラスの社員情報検索メソッドを呼び出し
		mdl.selectSyainInfo(syainInfo);
		//画面へ返却する項目を設定
		mv.addObject("syainInfo", syainInfo);
		mv.setViewName("syainListScr");
		return mv;
	}
}