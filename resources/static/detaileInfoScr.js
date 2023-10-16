$(function() {
	//親画面で設定されている社員IDを取得する
	var syainId = window.opener.$("#selectedSyainId").val();
	//ajaxで送信する
	$.ajax({
		url: "http://localhost:8080/detaileSelect",
		type: "POST",
		contentType: "application/json; charset=UTF-8",
		data: JSON.stringify({
			selectedSyainId: syainId
		})
	}).done(function(data) {
		//基本給、勤続年数、住民税、各手当の値を取得する
		var detaileInfo = data.detaileInfo[0];
		var hire_date = new Date(detaileInfo.hire_date);
		var position_Allowance = detaileInfo.position_Allowance
		var family_Allowance = detaileInfo.family_Allowance
		var baseSalary = detaileInfo.baseSalary
		var residentTax = detaileInfo.residentTax;
		//総支給額を計算する
		var totalAmount = baseSalary + position_Allowance + family_Allowance;
		//現在日を取得する
		var currentDate = new Date();
		// 年の差分を計算
		var yearDifference = currentDate.getFullYear() - hire_date.getFullYear();
		//控除額を計算する
		var deductions = yearDifference * residentTax;
		//勤続年数の後ろに"年"をつける
		serviceLength = yearDifference + "年";

		//役職手当を取得したとき、"\"を先頭につける
		if (position_Allowance != null) {
			position_Allowance = addCurrencySign(position_Allowance);
		}
		//家族手当を取得したとき、"\"を先頭につける
		if (family_Allowance != null) {
			family_Allowance = addCurrencySign(family_Allowance);
		}

		var netPay = totalAmount - deductions;
		//"\"を追加する
		baseSalary = addCurrencySign(baseSalary);
		residentTax = addCurrencySign(residentTax);
		totalAmount = addCurrencySign(totalAmount);
		deductions = addCurrencySign(deductions);
		netPay = addCurrencySign(netPay);

		//子画面の詳細情報一覧表に設定する
		$("#detailInfoTable").append(
			"<tr><td>社員ID</td><td>" + detaileInfo.syainId + "</td></tr>"
			+ "<tr><td>氏名</td><td>" + detaileInfo.syainName + "</td></tr>"
			+ "<tr><td>所属</td><td>" + detaileInfo.affiliation + "</td></tr>"
			+ "<tr><td>役職</td><td>" + (detaileInfo.position || "") + "</td></tr>"
			+ "<tr><td>家族構成</td><td>" + (detaileInfo.familyStructure || "") + "</td></tr>"
			+ "<tr><td>住所</td><td>" + detaileInfo.address + "</td></tr>"
			+ "<tr><td>勤続年数</td><td>" + serviceLength + "</td></tr>"
			+ "<tr><td>基本給</td><td>" + baseSalary + "</td></tr>"
			+ "<tr><td>役職手当</td><td>" + (position_Allowance || "") + "</td></tr>"
			+ "<tr><td>家族手当</td><td>" + (family_Allowance || "") + "</td></tr>"
			+ "<tr><td>住民税</td><td>" + residentTax + "</td></tr>"
			+ "<tr><td>総支給額</td><td>" + totalAmount + "</td></tr>"
			+ "<tr><td>控除額</td><td>" + deductions + "</td></tr>"
			+ "<tr><td>差引支給額</td><td>" + netPay + "</td></tr>"
		)
	})
	//子画面の戻るボタン押下時に呼び出す関数
	$("#backButton").on("click", function() {
		//子画面を閉じる
		window.close();
	})
})

// 表示用の通貨記号を追加する関数
function addCurrencySign(value) {
	return '\u00A5' + value;
}