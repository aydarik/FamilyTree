////////////////////////////////////////////////
/////////////// AUTHORIZATION //////////////////
////////////////////////////////////////////////

function addUser() {
	var username = document.getElementById('user_login').value;
	var password = document.getElementById('user_password').value;

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
	var username = document.getElementById('user_login').value;
	var password = document.getElementById('user_password').value;

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

				getCurrentNetwork();
				getGethClient();
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
	var id = Math.floor(Math.random() * 10000).toString();

	var style;
	if (message.startsWith('OK')) {
		style = "alert-success";
	} else if (message.startsWith('WARN')) {
		style = "alert-warning";
	} else if (message.startsWith('ERROR')) {
		style = "alert-danger";
	} else {
		style = "alert-info";
	}

	$("#messages").prepend("<div class=\"alert " + style + " alert-dismissable fade in\" id=\"" + id + "\">" +
		"<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>" +
		message +
		"</div>");

	setTimeout(function () {
		$("#" + id).alert("close")
	}, 30000);
}

////////////////////////////////////////////////
/////////////////// INFURA /////////////////////
////////////////////////////////////////////////

function getCurrentNetwork() {
	var token = localStorage.getItem('token');

	if (token) {
		$.ajax({
			url: 'ethereum/network',
			datatype: 'json',
			type: 'GET',
			headers: {'Authorization': 'Bearer ' + token},
			async: true,
			success: function (data) {
				log("OK: network received");
				$("#network").html(data);
			},
			error: function (xhr, ajaxOptions, thrownError) {
				log("ERROR: " + xhr.responseText);
			}
		});
	}
}

function getGethClient() {
	var token = localStorage.getItem('token');

	if (token) {
		$.ajax({
			url: 'ethereum/geth',
			datatype: 'json',
			type: 'GET',
			headers: {'Authorization': 'Bearer ' + token},
			async: true,
			success: function (data) {
				log("OK: Geth client received");
				$("#geth_client").html(data);
			},
			error: function (xhr, ajaxOptions, thrownError) {
				log("ERROR: " + xhr.responseText);
			}
		});
	}
}

function setEthAddress() {
	var token = localStorage.getItem('token');

	if (token) {
		var ethAddress = document.getElementById('user_eth_address').value;

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

function generateEthAddress() {
	var token = localStorage.getItem('token');

	if (token) {
		$.ajax({
			url: 'ethereum/generate',
			datatype: 'json',
			type: 'GET',
			headers: {'Authorization': 'Bearer ' + token},
			async: false,
			success: function (data) {
				log("OK: generated");
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