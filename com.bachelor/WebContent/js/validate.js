function validate() {
	var username = document.registerform.username;
	var password = document.registerform.password;

	if (username.value == null || username.value == "") {
		alert("Please enter username");
		username.focus();
		return false;
	}
	if (password.value == null || password.value == "") {
		alert("Please enter password.");
		password.focus();
		return false;
	}
}