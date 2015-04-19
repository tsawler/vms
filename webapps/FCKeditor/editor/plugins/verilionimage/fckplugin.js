/*
 * FCKeditor - The text editor for Internet - http://www.fckeditor.net
 * Copyright (C) 2003-2007 Frederico Caldeira Knabben
 *
 * == BEGIN LICENSE ==
 *
 * Licensed under the terms of any of the following licenses at your
 * choice:
 *
 *  - GNU General Public License Version 2 or later (the "GPL")
 *    http://www.gnu.org/licenses/gpl.html
 *
 *  - GNU Lesser General Public License Version 2.1 or later (the "LGPL")
 *    http://www.gnu.org/licenses/lgpl.html
 *
 *  - Mozilla Public License Version 1.1 or later (the "MPL")
 *    http://www.mozilla.org/MPL/MPL-1.1.html
 *
 * == END LICENSE ==
 *
 * Plugin to insert "Verilionimages" in the editor.
 */

// Register the related command.
FCKCommands.RegisterCommand( 'Verilionimage', new FCKDialogCommand( 'Quick Image', "Choose Image" , FCKPlugins.Items['verilionimage'].Path + 'fck_verilionimage.html', 750, 600 ) ) ;

// Create the "Plaholder" toolbar button.
var oVerilionimageItem = new FCKToolbarButton( 'Verilionimage', "Choose Image" ) ;
oVerilionimageItem.IconPath = FCKPlugins.Items['verilionimage'].Path + 'verilionimage.gif' ;

FCKToolbarItems.RegisterItem( 'Verilionimage', oVerilionimageItem ) ;


// The object used for all Verilionimage operations.
var FCKVerilionimages = new Object() ;

// Add a new verilionimage at the actual selection.
FCKVerilionimages.Add = function( name )
{
	var oImage = FCK.CreateElement( 'IMG' ) ;
	this.SetupImage( oImage, name ) ;
}

FCKVerilionimages.SetupImage = function( image, name )
{
	image.src = "/" + name ;



	if ( FCKBrowserInfo.IsGecko )
		image.style.cursor = 'default' ;

	image._fckverilionimage = name ;
	image.contentEditable = true ;
}

