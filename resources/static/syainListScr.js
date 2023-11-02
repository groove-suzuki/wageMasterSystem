$(function() {
	//社員名入力欄の入力チェックを行う関数
	$("#nameInput").on("input",function(){
		var inputName=$("#nameInput").val();
		if(inputName==""){
			$("#selectButton").prop("disabled",true);
		}else{
			$("#selectButton").prop("disabled",false);
		}
	})
	
	//全件検索ボタン押下時に呼び出す関数
	$("#allSelectButton").on("click", function() {
		//表示するテーブルを切り替える関数を呼び出し
		toggleTableBody("#allInfoTable", "#filterInfoTable");
		$("#selectErrorMsg").empty();
	})
	
	//検索ボタン押下時に呼び出す関数
	$("#selectButton").on("click", function() {
		//社員名入力値をチェックする
		var inputName = $("#nameInput").val();
		var regex = /^[ぁ-んァ-ン一-龯]{1,10}$/;
		var checkResult = regex.test(inputName);

		if (checkResult) {
			//入力エラーメッセージを非表示にする
			$("#inputErrorMsg").empty();
			//入力値を送信、検索結果を取得する関数を呼び出し
			filterSearch(inputName)
		} else {
			//入力エラーメッセージを表示する
			var errorMsgHtml = "<div style='color: red;'>" + "社員名は10字以内の漢字、ひらがなで入力してください。" + "</div>";
			$("#inputErrorMsg").html(errorMsgHtml);
		}
	})
	//戻るボタン押下時に呼び出す関数
	$("#backButton").on("click",function(){
		//初期画面に戻る
		window.history.back();
	})
})

//入力値を送信、検索結果を取得する関数
function filterSearch(inputName) {
	//入力値をajaxで送信する
	$.ajax({
		url: "http://localhost:8080/filterSearch",
		type: "POST",
		contentType: "application/json; charset=UTF-8",
		data: JSON.stringify({
			inputValue: inputName
		})
	}).done(function(data) {
		var errorMsg = data.errorMsg;
		if (errorMsg.length == 0) {
			//絞り込み検索結果、入力エラーメッセージをリセットにする
			$("#filterInfoTable,#selectErrorMsg").empty();
			var syainInfo = data.syainInfo;
			//検索結果を従業員一覧表に設定する
			for (var i = 0; i < syainInfo.length; i++) {
				var infoRecord = syainInfo[i];
				$("#filterInfoTable").append(
					"<tr>"
					+ "<td id='syainId-" + i + "'>" + (infoRecord.syainId || "") + "</td>"
					+ "<td><a href='' id='syainName-" + i + "' class='syainNameLink' onclick='handleClick(" + i + ")'>"
					+ "<div>" + (infoRecord.syainName||"") + "</div></a></td>"
					+ "<td>" + (infoRecord.affiliation||"") + "</td>"
					+ "<td>" + (infoRecord.position || "") + "</td>"
					+ "</tr>"
				);
			}
			//テーブル内容の表示を切り替える関数を呼び出し
			toggleTableBody("#filterInfoTable", "#allInfoTable");
		} else {
			//社員情報一覧表を非表示にする
			$("#filterInfoTable,#allInfoTable").hide();
			//検索エラーメッセージを表示する
			var errorMsgHtml = "<div style='color: red;'>" + errorMsg + "</div>";
			$("#selectErrorMsg").html(errorMsgHtml);
		}
	}).fail(function() {
		console.log("failが実行された");
	})
}

//社員名のリンクがクリックされた時に呼び出す関数
function handleClick(index){
	//クリックされた社員名に該当する社員IDを画面に設定し、保持する
	$("#selectedSyainId").val($("#syainId-" + index).text());
		//子画面を表示する
		window.open('selectDetaileInfo', 'selectWindow', 'popup');
}
//テーブル内容の表示を切り替える関数
function toggleTableBody(tbodyToShow, tbodyToHide) {
	$(tbodyToShow).show();
	$(tbodyToHide).hide();
}