function addUser() {
	var username = 'test-1';
	var password = 'password';

	$.ajax({
		url: 'accounts/',
		datatype: 'json',
		type: "POST",
		async: false,
		contentType: "application/json",
		data: JSON.stringify({
			username: username,
			password: password
		}),
		success: function (data) {
			log("OK - addUser: " + data);
			login();
		},
		error: function (xhr, ajaxOptions, thrownError) {
			log("ERROR - addUser: " + xhr.responseText);
		}
	});
}

function dropUser() {
	var token = localStorage.getItem('token');

	if (token) {
		$.ajax({
			url: 'accounts/current',
			datatype: 'json',
			type: 'DELETE',
			headers: {'Authorization': 'Bearer ' + token},
			async: false,
			success: function (data) {
				log("OK - dropUser: " + data);
				logout();
			},
			error: function (xhr, ajaxOptions, thrownError) {
				log("ERROR - dropUser: " + xhr.responseText);
			}
		});
	} else {
		log("ERROR - Not logged in");
	}
}

function login() {
	var username = 'test-1';
	var password = 'password';

	$.ajax({
		url: 'uaa/oauth/token',
		datatype: 'json',
		type: 'POST',
		async: false,
		headers: {'Authorization': 'Basic YnJvd3Nlcjo='},
		// headers: {'Authorization': 'Basic ' + btoa("browser:")},
		data: {
			scope: 'ui',
			username: username,
			password: password,
			grant_type: 'password'
		},
		success: function (data) {
			log("OK - requestOauthToken: " + data.access_token);
			localStorage.setItem('token', data.access_token);
			getCurrentAccount();
		},
		error: function (xhr, ajaxOptions, thrownError) {
			log("ERROR - requestOauthToken: " + xhr.responseText);
			removeOauthTokenFromStorage();
		}
	});
}


function getCurrentAccount() {
	var token = localStorage.getItem('token');

	if (token) {
		$.ajax({
			url: 'accounts/current',
			datatype: 'json',
			type: 'GET',
			headers: {'Authorization': 'Bearer ' + token},
			async: false,
			success: function (data) {
				log("OK - getCurrentAccount: " + data.name);
				localStorage.setItem('account', data.name);
				$("#user").html(data.name);
			},
			error: function () {
				log("ERROR - getCurrentAccount: " + xhr.responseText);
				removeOauthTokenFromStorage();
			}
		});
	} else {
		log("ERROR - Not logged in");
	}
}

function logout() {
	removeOauthTokenFromStorage();
	location.reload();
}

function removeOauthTokenFromStorage() {
	return localStorage.removeItem('token');
}

function log(message) {
	$("#message").html(message);

}