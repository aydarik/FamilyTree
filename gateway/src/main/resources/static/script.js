////////////////////////////////////////////////
/////////////// AUTHORIZATION //////////////////
////////////////////////////////////////////////

function addUser() {
	var username = $('#user_login').val();
	var password = $('#user_password').val();

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
			log("OK: " + data.name);
			login();
		},
		error: function (xhr, ajaxOptions, thrownError) {
			log("ERROR: " + xhr.responseText);
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
			success: function () {
				log("OK: user dropped");
				logout();
			},
			error: function (xhr, ajaxOptions, thrownError) {
				log("ERROR: " + xhr.responseText);
			}
		});
	} else {
		log("WARN: not logged in");
	}
}

function login() {
	var username = $('#user_login').val();
	var password = $('#user_password').val();

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
			log("OK: " + data.access_token);
			localStorage.setItem('token', data.access_token);
			getCurrentAccount();
		},
		error: function (xhr, ajaxOptions, thrownError) {
			log("ERROR: " + xhr.responseText);
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
				log("OK: logged in");
				$("#user").html(data.name);
			},
			error: function (xhr, ajaxOptions, thrownError) {
				log("ERROR: " + xhr.responseText);
			}
		});
	} else {
		log("WARN: not logged in");
	}
}

function logout() {
	removeOauthTokenFromStorage();
	$("#user").html("<i>Not logged in</i>");
	log("OK: logged out");
}

function removeOauthTokenFromStorage() {
	localStorage.removeItem('token');
}

function log(message) {
	const field = $("#message");
	field.html(message);

	if (message.startsWith('OK')) {
		field.css('color', 'green');
	} else if (message.startsWith('ERROR')) {
		field.css('color', 'red');
	} else {
		field.css('color', '');
	}
}


////////////////////////////////////////////////
/////////////////// INFURA /////////////////////
////////////////////////////////////////////////

function setEthAddress() {
	var token = localStorage.getItem('token');

	if (token) {
		var ethAddress = $("#user_eth_address").val();

		if (ethAddress) {
			$.ajax({
				url: 'accounts/current',
				datatype: 'json',
				type: "PUT",
				headers: {'Authorization': 'Bearer ' + token},
				async: false,
				contentType: "application/json",
				data: JSON.stringify({
					ethAddress: ethAddress
				}),
				success: function (data) {
					log("OK: set Ethereum Address");
					$("#infura").html(ethAddress);
				},
				error: function (xhr, ajaxOptions, thrownError) {
					log("ERROR: " + xhr.responseText);
				}
			});
		} else {
			log("WARN: not address specified");
		}
	} else {
		log("WARN: not logged in");
	}
}

function getInfuraMethods() {
	var token = localStorage.getItem('token');

	if (token) {
		$.ajax({
			url: 'ethereum/methods',
			datatype: 'json',
			type: 'GET',
			headers: {'Authorization': 'Bearer ' + token},
			async: false,
			success: function (data) {
				log("OK: got Infura methods");
				$("#infura").html(data);
			},
			error: function (xhr, ajaxOptions, thrownError) {
				log("ERROR: " + xhr.responseText);
			}
		});
	} else {
		log("WARN: not logged in");
	}
}

function getInfuraBalance() {
	var token = localStorage.getItem('token');

	if (token) {
		$.ajax({
			url: 'ethereum/balance',
			datatype: 'json',
			type: 'GET',
			headers: {'Authorization': 'Bearer ' + token},
			async: false,
			success: function (data) {
				log("OK: got current user's balance");
				$("#infura").html(data);
			},
			error: function (xhr, ajaxOptions, thrownError) {
				log("ERROR: " + xhr.responseText);
			}
		});
	} else {
		log("WARN: not logged in");
	}
}