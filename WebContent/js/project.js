(function($, document, window) {
	function noBack() {
		window.history.forward()
	}
	noBack();
	window.onload = noBack();
	window.onpageshow = function(evt) {
		if (evt.persisted)
			noBack()
	}
	window.onunload = function() {
		void (0)
	}

	//validateNumberAllowDecimal=function(event, isDecimal) {
	function validateNumberAllowDecimal(event, isDecimal) {
		var keysWithDecimal;
		if (isDecimal) {
			// keycode 190 dot (.) without number pad
			// keycode 110 dot (.) on number pad
			keysWithDecimal = [ 46, 8, 9, 27, 13, 190, 110 ];
		} else {
			keysWithDecimal = [ 46, 8, 9, 27, 13 ];
		}

		// Allow: backspace, delete, tab, escape, enter and .
		if ($.inArray(event.keyCode, keysWithDecimal) !== -1 ||
		// Allow: Ctrl+A
		(event.keyCode == 65 && event.ctrlKey === true) ||
		// Allow: home, end, left, right
		(event.keyCode >= 35 && event.keyCode <= 39)) {
			// let it happen, don't do anything
			return;
		} else {
			// Ensure that it is a number and stop the keypress
			if (event.shiftKey || (event.keyCode < 48 || event.keyCode > 57)
					&& (event.keyCode < 96 || event.keyCode > 105)) {
				event.preventDefault();
			}
		}
	}

})(jQuery, document, window);
