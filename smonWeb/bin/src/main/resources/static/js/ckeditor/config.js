/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function (config) {
    // Define changes to default configuration here. For example:
    config.language = 'ko';
    config.uiColor = '#FFFFFF';
    config.height = 320;
    config.resize_enabled = true;
    config.extraPlugins = 'codesnippet';
    config.codeSnippet_theme = 'pojoaque';
    //config.removePlugins= 'toolbar';
    /*config.toolbar = [
        { name: 'basicstyles', items: [ 'Bold', 'Italic', 'Underline', 'Strike' ] },
        { name: 'styles', items: [ 'FontSize' ] },
        { name: 'insert', items: [ 'Table', 'Smiley', 'TextColor', 'BGColor' ] },
        { name: 'paragraph', items: [ 'NumberedList','BulletedList','Outdent', 'Indent', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ] }
    ];*/
    config.toolbarGroups = [
		/*{ name: 'clipboard', groups: [ 'clipboard', 'undo' ] },*/
		{ name: 'links', groups: [ 'links' ] },
		{ name: 'insert', groups: [ 'insert' ] },
		/*{ name: 'editing', groups: [ 'find', 'selection', 'editing' ] },*/
		/*{ name: 'forms', groups: [ 'forms' ] },*/
		/*{ name: 'tools', groups: [ 'tools' ] },*/
		{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
		{ name: 'others', groups: [ 'others' ] },
		'/',
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
		{ name: 'styles', groups: [ 'styles' ] },
		{ name: 'colors', groups: [ 'colors' ] },
		/*{ name: 'about', groups: [ 'about' ] }*/
	];
    config.allowedContent = true;
};