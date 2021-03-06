// (1) 회원정보 수정
function update(userId, event) {
	
	event.preventDefault(); //form 태그 액션을 막기 !!(더이상 진행되지 않게)
	
	//JQuery 를 사용할 수 있는 이유 : header.jsp 에 JQuery 를 이미 정의해놓음
	//#profileUpdate : update.jsp 의  form 태그 id
	let data=$("#profileUpdate").serialize();	// profileUpdate 명의 <form> 태그안에 있는 모든 info 값을 담음
	
	console.log(data);
	
	$.ajax({
		//javascript Object 공간
		type:"put",
		url:`/api/user/${userId}`,
		data:data,
		contentType:"application/x-www-form-urlencoded; charset=utf-8", // data가 무엇인지 설명하는 곳=> key:value 형식의 마임타입 =>application/x-www-form-urlencoded
		dataType:"json"
	}).done(res=>{//HttpStatus 상태코드 200 번대
		//res에 json을 javascript 로 parsing 해서 받음 : res 는 javascript 오브젝트임
		console.log("update 성공",res);
		location.href=`/user/${userId}`;
	}).fail(error=>{//HttpStatus 상태코드 200번대가 아닐 때
		if(error.data==null){//500번대 에러(데이터가 넘어오지 않았을 때)
			alert(error.responseJSON.message);
		}else{//400번대 에러(요청을 잘 못했을 때)
			alert(JSON.stringify(error.responseJSON.data));
		}
	});
}