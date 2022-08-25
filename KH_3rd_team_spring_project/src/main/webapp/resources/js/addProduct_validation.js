function CheckAddProduct(){
	var productId = document.getElementById("productId");
	var productName = document.getElementById("productName");
	var unitPrice = document.getElementById("unitPrice");
	var unitsInStock = document.getElementById("unitsInStock");
	
	// 상품 아이디 체크
	if (!check(/^P[0-9]{4,11}$/, productId, "[상품 코드]\nP와 숫자를 조합하여 5~12자까지 입력하세요\n첫 글자는 반드시 P로 시작하세요"))
		return false;
	
	// 상품명 체크
	if (productName.value.length <4 || productName.value.length > 12){
		alert("[상품명]\n최소 4자에서 최대 12자까지 입력하세요");
		productName.select();
		productName.focus();
		return false;
	}
	
	// 상품 가격 체크
	if (unitPrice.value.length ==0 || isNaN(unitPrice.value)){
		alert("[가격]\n숫자만 입력하세요");
		unitPrice.select();
		unitPrice.focus();
		return false;
	}
	
	if (unitPrice.value<0){ // 가격이 음수일 때
		alert("[가격]\n음수를 입력할 수 없습니다.");
		unitPrice.select();
		unitPrice.focus();
		return false;
	} else if(!check(/^\d+(?:[.]?[\d]?[\d])?$/, unitPrice, "[가격]\n소수점 둘째 자리까지만 입력하세요"))
		return false;
		
	// 재고 수 체크
	if (isNaN(unitsInStock.value)){
		alert("[재고 수]\n숫자만 입력하세요");
		unitsInStock.select();
		unitsInStock.focus();
		return false;
	}
	
	function check(regExp, e, msg){
		if(regExp.test(e.value)){
			return true;
		}
		alert(msg);
		e.select();
		e.focus();
		return false;
	}
	
	document.newProduct.submit();
}