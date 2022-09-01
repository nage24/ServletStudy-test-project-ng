load(); // db에서 user information을 가져와서 로딩해줄 것. -> js 실행되자 마자 로드됨. 

function load() {
	
	$.ajax({
		async: false,
		type: "get", // 가져올거다
		url: "/api/v1/auth/management/users/test", // 요청 URL ; 여기서 Ajax를 통해 받아온 데이터를 처리할 것임. 
		dataType: "json", // json으로 가져올거다
		success: (response) => {
			console.log(response);
			getUserList(response); // 가져온 응답을 userlist에 jsp에서 차곡차곡 띄워줄거다. 
		},
		error: (error) => {
			console.log(error);
		}
	});
}

function getUserList(userList) {
	const tbody = document.querySelector("tbody");	// tbody 에 user를 띄워줄거임
	
	tbody.innerHTML = ``;
	for(let user of userList) {
		tbody.innerHTML += `
					<tr>
						<td>${user.user_code}</td>
						<td>${user.user_id}</td>
						<td>${user.user_password}</td>
						<td>${user.user_name}</td>
						<td>${user.user_email}</td>
						<td>${user.user_phone}</td>
						<td>${user.user_address}</td>
					</tr>
		`;
	}
}