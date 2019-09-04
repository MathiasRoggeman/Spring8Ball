function validate() {

	var question = document.getElementById('question').value;
	
	if (question == '') {
		alert('Please enter a valid first name.');
		return false;
	}
}