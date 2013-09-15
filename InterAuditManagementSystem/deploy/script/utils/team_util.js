function copySelectionCombo2Combo(sourceComboId, targetComboId) {
	var sourceComboHandler = new ComboHandler(document.getElementById(sourceComboId));
	var targetComboHandler = new ComboHandler(document.getElementById(targetComboId));
	// copySelection(new ComboHandler(document.getElementById(sourceComboId)), new ComboHandler(document.getElementById(targetComboId)));
	copySelection(sourceComboHandler, targetComboHandler);
	sourceComboHandler = null;
	targetComboHandler = null;
}
function copySelectionCombo2Input(sourceComboId, valueFieldId, labelFieldId) {
	// var sourceCombo = document.getElementById(sourceComboId);
	var sourceComboHandler = new ComboHandler(document.getElementById(sourceComboId));
	var targetInputFieldHandler = new InputFieldHandler(document.getElementById(valueFieldId), document.getElementById(labelFieldId));
	try {
		copySelection(sourceComboHandler, targetInputFieldHandler);
	} catch(e) {
		alert(e);
	}
	sourceComboHandler = null;
	targetInputFieldHandler = null;
}
function removeSelectedValuesFromCombo(comboId) {
	var comboHandler = new ComboHandler(document.getElementById(comboId));
	comboHandler.removeSelectedValues();
	comboHandler = null;
}
function removeValueFromInput(valueFieldId, labelFieldId) {
	var inputFieldHandler = new InputFieldHandler(document.getElementById(valueFieldId), document.getElementById(labelFieldId));
	inputFieldHandler.removeSelectedValues();
	inputFieldHandler = null;
}

function containsOption(targetCombo, option) {
	var options = targetCombo.options;
	
	for (var i = options.length - 1; i > -1; i--) {
		if (options[i].value == option.value) {
			return true;
		}
	}
	return false;
}

function copySelection(sourceHandler, targetHandler) {
	targetHandler.addValues(sourceHandler.getSelectedValues());	
}

function CompositeValue(value, label) {
	this.value = value;
	this.label = label;
}
/**
 * ComboHandler is an object used to retrieve the selected value or to set values to the given combo.
 * @param combo
 */
function ComboHandler(combo) {
	this.combo=combo;
}
/**
 * @param compositeValues CompositeValue objects array. 
 */
ComboHandler.prototype.addValues = function (compositeValues) {
	var newOption = null;
	for (var i = 0; i < compositeValues.length; i++) {
		newOption = document.createElement("OPTION");
		newOption.value = compositeValues[i].value;
		newOption.text = compositeValues[i].label;
		if ( containsOption(this.combo, newOption) ) {
			continue;
		}
		try {
			this.combo.add(newOption, null); // standards compliant; doesn't work in IE
		} catch(ex) {
			this.combo.add(newOption); // IE only
		}
	}
}
/**
 * @return A CompositeValue objects array. 
 */
ComboHandler.prototype.getSelectedValues = function() {
	var options = this.combo.options;
	var compositeValues = new Array();
	for (var i = 0; i < options.length; i++) {
		if (options[i].selected) {
			compositeValues.push(new CompositeValue(options[i].value, options[i].text));
			options[i].selected = null;
		}
	}
	return compositeValues;
}
/**
 * Removes all selected options from its combo.
 */
ComboHandler.prototype.removeSelectedValues = function() {
	var options = this.combo.options;
	for (i = options.length - 1; i >= 0; i--) {
		if (options[i].selected) {
			this.combo.remove(i);
		}
	}
}
/**
 * @param valueField a input field that contains the value.
 * @param labelField a input field that displays the label associated with given valueField.
 */
function InputFieldHandler(valueField, labelField) {
	this.valueField = valueField;
	this.labelField = labelField;
}
/**
 * @param compositeValues CompositeValue objects array. 
 */
InputFieldHandler.prototype.addValues = function(compositeValues) {
	if ( ( ! compositeValues) || compositeValues.length > 1) {
		throw errorTooManyUsersSelectedSelectOne;
	}
	if(compositeValues.length > 0) {
		this.valueField.value = compositeValues[0].value;
		this.labelField.value = compositeValues[0].label;
	}
}
/**
 * @return A CompositeValue objects array. 
 */
InputFieldHandler.prototype.getSelectedValues = function() {
	var compositeValues = new Array();
	compositeValues.push(new CompositeValue(this.valueField.value, this.labelField.text));
	return compositeValues;
}
/**
 * Sets empty string as value for value and for label.
 */
InputFieldHandler.prototype.removeSelectedValues = function() {
	this.valueField.value = "";
	this.labelField.value = "";
}