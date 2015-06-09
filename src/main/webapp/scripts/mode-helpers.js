/**
 * Pencil click - switch to edit mode
 */
function switchToEditMode(thisElement) {
	// we expect two spans within each li (one span is for the viewMode and one is for the editMode)
	var e = $(thisElement).parents('li').children('span')
	
	// show / hide the view and edit spans
	e.toggle();  
	
	// set focus on the first input element within the first form and 
	// select its contents so we are ready for user to edit the value
	e.children('form').children('input').focus().select(); 
	
}

/**
 * Switch back to view mode. Any changes during editing are not reset.
 * 
 */
function switchToViewMode(thisElement) {
	// we expect two spans within each li (one span is for the viewMode and one is for the editMode)
	var e = $(thisElement).parents('li').children('span')
	
	// show / hide the view and edit spans
	e.toggle();
	
}

/**
 * Cancel editing - sets the mode back to view mode but intentionally does not reset 
 * any data entry so user can continue with edits if desired
 * 
 */
function cancelEditing(thisElement) {
	
	switchToViewMode(thisElement)
}

/**
 * Reset text for the elements related to the given reset element
 *  - TODO: enhance this function to support resetting of more than one text field per form
 */
function resetText(thisElement) {
	// we expect two spans within each li (one span is for the viewMode and one is for the editMode)
	var e = $(thisElement).parents('li').children('span')
	
	// get the saved text from the non-editable link text
	var savedText = e.find('a').text()
	
	// reset the editable text element
	e.find('input.resetable').val(savedText)
	
}

/**
 * Toggle AddMode 
 * Use this from the Add Icon and it will toggle the addMode div
 * within the same kibaliCard 
 * 
 */
function toggleAddMode(thisElement) {
	// we expect a separate div for addMode which is hidden when we are not in addMode
	//var e = $(thisElement).parents('div.kibaliCardHead').next('div').children('div.addMode')
	var e = $(thisElement).parents('div.kibaliCard').children('div.kibaliCardBody').children('div.addMode')
	
	// show / hide the add div
	e.toggle();
	
	// set focus on the textarea and select its contents, if any
	e.children('form').find('textarea').focus().select(); 
	
}

/**
 * showAddButtonsAjaxLoader
 * This method is called by the Snippet when the ajax create button is clicked
 * 
 * @id - the id of the parent div (e.g. tenantProjects)
 * @textareaClass - the class of the textarea used for data entry (e.g. newProjects)
 * 
 * We expect the shadow textarea to have the same name as textareaClass with "Shadow" 
 * appended to it.
 * 
 */
function showAddButtonsAjaxLoader(id, textareaClass) {
	
	var idSelector = "#" + id
	var textareaSelector = "textarea." + textareaClass
	
	var newProjectsText = $(idSelector).find(textareaSelector).val()
	
	$(idSelector).find(textareaSelector + "Shadow").val(newProjectsText).prop('disabled', true)
	
	$(idSelector).find(".addButtons").toggle()
	
}

/**
 * Start initialization when the document is ready
 */
$(document).ready(function() {
	// put code you want to run on page load here
});
