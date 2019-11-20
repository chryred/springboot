/**
 * window 객체에 주입할 Function 정의
 */


/*
 * 콤보박스 초기화
 * 
 * type(string): 콤보박스 구분
 * targetRef(object): 콤보박스 jquery객체
 * params(object): 콤보박스 매개변수
 * changeCallback(function): 콤보박스 change 이벤트 function
 * syncFlag(boolean): ajax 비동기 실행 여부
 * successCallback(function): 콜백 function
 */
window.initCombo = function(comboType, targetRef, params, changeCallback, syncFlag, successCallback) {
	var url = null;
	var setRequestUrl = function(type) {
		switch(type) {
			case "team"://운영팀
				url = contextPath + "/common/combo/ajax/team.do"
				break;
			case "system"://시스템
				url = contextPath + "/common/combo/ajax/system.do"
				break;
			case "owner"://시스템
				url = contextPath + "/common/combo/ajax/owner.do"
				break;
			case "saveType"://보관유형
				url = contextPath + "/common/combo/ajax/saveType.do"
				break;
			case "objectType"://Object구분
				url = contextPath + "/common/combo/ajax/objectType.do"
				break;
			case "personalInfoType"://개인정보구분
				url = contextPath + "/common/combo/ajax/personalInfoType.do"
				break;
			case "dataType"://데이터타입
				url = contextPath + "/common/combo/ajax/dataType.do"
				break;
			case "actionType"://조치방향
				url = contextPath + "/common/combo/ajax/actionType.do"
			case "work"://업무명
				url = contextPath + "/common/combo/ajax/work.do"
				break;
			case "effect": // 업무 영향
				url = contextPath + "/common/combo/ajax/effect.do"
				break;
			case "effectRange": // 장애 범위
				url = contextPath + "/common/combo/ajax/effectRange.do"
				break;
			case "targetSystem": // 대상 시스템
				url = contextPath + "/common/combo/ajax/targetSystem.do"
				break;
			case "status": // 상태
				url = contextPath + "/common/combo/ajax/status.do"
				break;
			case "defectGrade" :
				url = contextPath + "/common/combo/ajax/defectGrade.do"
				break;
		}
	};
	
	if(comboType == undefined) {
		console.error("parameter 누락: type");
		return false;
	}
	if(targetRef == undefined) {
		console.error("parameter 누락: targetRef");
		return false;
	}
	
	setRequestUrl(comboType);
	var syncFlag = syncFlag == false ? false : true;
	
	$.ajax({
		url: url,
		type: "POST",
		async: syncFlag,
		dataType: "json",
		data: params,
		success: function(list) {
			targetRef.html("<option value=''>전체</option>");
			_.each(list, function(obj, idx) {
				targetRef.append("<option value='"+obj.KEY+"'>" + obj.NAME + "</option>")
			});
			if(changeCallback && _.isFunction(changeCallback)) {
				targetRef.unbind();
				targetRef.on('change', changeCallback);
			};
			if(successCallback && _.isFunction(successCallback)) {
				successCallback();
			}
		},
		error: function() {
			alert("콤보박스 초기화 실패");

		}
	});
}