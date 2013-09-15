// Replaces all instances of the given substring.
String.prototype.replaceAll = function(
	strTarget, // The substring you want to replace
	strSubString // The string you want to replace in.
	){
	var strText = this;
	var intIndexOfMatch = strText.indexOf( strTarget );
	// Keep looping while an instance of the target string
	// still exists in the string.
	while (intIndexOfMatch != -1){
		// Relace out the current instance.
		strText = strText.replace( strTarget, strSubString )
		// Get the index of any next matching substring.
		intIndexOfMatch = strText.indexOf( strTarget );
	}
	// Return the updated string with ALL the target strings
	// replaced out with the new substring.
	return( strText );
}

//returns the number of occurences of "mach" within the current string
String.prototype.count = function(match) {
	var res = this.match(new RegExp(match,"g"));
	if (res==null) { return 0; }
	return res.length;
}

//Reverts the letter cases 
String.prototype.swapcase = function(){
	return this.replace(/([a-z])|([A-Z])/g,function($0,$1,$2){
		return ($1) ? $0.toUpperCase() : $0.toLowerCase()
	})
}
//Reverts the letter cases 
String.prototype.trim = function(){
	
	return this.replace(/\s/g, "");
}

//Checks if this string starts with the given string
String.prototype.startsWith = function(str){
	return !this.indexOf(str);
}

//Checks if this string ends with the given string
String.prototype.endsWith = function(str) {
	var offset = this.length - str.length;
	return offset >=0 && this.lastIndexOf(str) === offset;
};
