YAHOO.ext.grid.PropsDataModel = function(propertyNames, source){
    YAHOO.ext.grid.PropsDataModel.superclass.constructor.call(this, []);
    if(source){
        this.setSource(source);
    }
    this.names = propertyNames || {};
};
YAHOO.extendX(YAHOO.ext.grid.PropsDataModel, YAHOO.ext.grid.DefaultDataModel, {
    setSource : function(o){
        this.source = o;
        var data = [];
        for(var key in o){
            if(this.isEditableValue(o[key])){
                var vals = [key, o[key]];
                vals.key = key;
                data.push(vals);
            }
        }
        this.removeAll();
        this.addRows(data);
    },
    
    getRowId: function(rowIndex){
        return this.data[rowIndex].key;  
    },
    
    getPropertyName: function(rowIndex){
        return this.data[rowIndex].key;  
    },
    
    isEditableValue: function(val){
        if(val && val instanceof Date){
            return true;
        }else if(typeof val == 'object' || typeof val == 'function'){
            return false;
        }
        return true;
    },
    
    setValueAt : function(value, rowIndex, colIndex){
        var origVal = this.getValueAt(rowIndex, colIndex);
        if(typeof origVal == 'boolean'){
            value = (value == 'true' || value == '1');
        }
        YAHOO.ext.grid.PropsDataModel.superclass.setValueAt.call(this, value, rowIndex, colIndex);
        var key = this.data[rowIndex].key;
        if(key){
            this.source[key] = value;
        }
    },
    
    getName : function(propName){
        if(typeof this.names[propName] != 'undefined'){
            return this.names[propName];
        }
        return propName;
    },
    
    getSource : function(){
        return this.source;
    }
});
YAHOO.ext.grid.PropsColumnModel = function(dataModel, customEditors){
    YAHOO.ext.grid.PropsColumnModel.superclass.constructor.call(this, [
        {header: 'Name', sortable: true},
        {header: 'Value'} 
    ]);
    this.dataModel = dataModel;
    this.bselect = YAHOO.ext.DomHelper.append(document.body, {
        tag: 'select', cls: 'ygrid-editor', children: [
            {tag: 'option', value: 'true', html: 'true'},
            {tag: 'option', value: 'false', html: 'false'}
        ]
    });
    YAHOO.util.Dom.generateId(this.bselect);
    this.editors = {
        'date' : new YAHOO.ext.grid.DateEditor(),
        'string' : new YAHOO.ext.grid.TextEditor(),
        'number' : new YAHOO.ext.grid.NumberEditor(),
        'boolean' : new YAHOO.ext.grid.SelectEditor(this.bselect)
    };
    this.customEditors = customEditors || {};
    this.renderCellDelegate = this.renderCell.createDelegate(this);
};

YAHOO.extendX(YAHOO.ext.grid.PropsColumnModel, YAHOO.ext.grid.DefaultColumnModel, {
    isCellEditable : function(colIndex, rowIndex){
        return colIndex == 1;
    },
    
    getRenderer : function(col){
        if(col == 1){
            return this.renderCellDelegate;
        }
        return YAHOO.ext.grid.DefaultColumnModel.defaultRenderer; 
    },
    
    renderCell : function(val, rowIndex, colIndex){
        if(val instanceof Date){
            return this.renderDate(val);
        }else if(typeof val == 'boolean'){
            return this.renderBool(val);
        }else{
            return val;
        }
    },
    
    getCellEditor : function(colIndex, rowIndex){
        var propName = this.dataModel.getPropertyName(rowIndex);
        if(this.customEditors[propName]){
            return this.customEditors[propName];
        }
        var val = this.dataModel.getValueAt(rowIndex, colIndex);
        if(val instanceof Date){
            return this.editors['date'];
        }else if(typeof val == 'number'){
            return this.editors['number'];
        }else if(typeof val == 'boolean'){
            return this.editors['boolean'];
        }else{
            return this.editors['string'];
        }
    },
    
    getCellEditor : function(colIndex, rowIndex){
        var val = this.dataModel.getValueAt(rowIndex, colIndex);
        if(val instanceof Date){
            return this.editors['date'];
        }else if(typeof val == 'number'){
            return this.editors['number'];
        }else if(typeof val == 'boolean'){
            return this.editors['boolean'];
        }else{
            return this.editors['string'];
        }
    }
});

YAHOO.ext.grid.PropsColumnModel.prototype.renderDate = function(dateVal){
    return dateVal.dateFormat('m/j/Y');
};

YAHOO.ext.grid.PropsColumnModel.prototype.renderBool = function(bVal){
    return bVal ? 'true' : 'false';
};

YAHOO.ext.grid.PropsGrid = function(container, propNames){
    var dm = new YAHOO.ext.grid.PropsDataModel(propNames);
    var cm =new YAHOO.ext.grid.PropsColumnModel(dm);
    dm.sort(cm, 0, 'ASC');
    YAHOO.ext.grid.PropsGrid.superclass.constructor.call(this, container, dm, cm);
    this.container.addClass('yprops-grid');
    this.lastEditRow = null;
    this.on('cellclick', this.onCellClick, this, true);
    this.on('beforeedit', this.beforeEdit, this, true); 
    this.on('columnresize', this.onColumnResize, this, true); 
};
YAHOO.extendX(YAHOO.ext.grid.PropsGrid, YAHOO.ext.grid.EditorGrid, {
    onCellClick : function(grid, rowIndex, colIndex, e){
        if(colIndex == 0){
            this.startEditing(rowIndex, 1);
        }
    },
    
    render : function(){
        YAHOO.ext.grid.PropsGrid.superclass.render.call(this);
        this.getView().fitColumnsToContainer();
    },
    
    autoSize : function(){
        YAHOO.ext.grid.PropsGrid.superclass.autoSize.call(this);
        this.getView().fitColumnsToContainer();
    },
    
    onColumnResize : function(){
        this.colModel.setColumnWidth(1, this.getView().wrap.clientWidth - this.colModel.getColumnWidth(0));
    },
    
    beforeEdit : function(grid, rowIndex, colIndex){
        if(this.lastEditRow && rowIndex != this.lastEditRow.rowIndex){
            YAHOO.util.Dom.removeClass(this.lastEditRow, 'ygrid-prop-edting');
        }
        this.lastEditRow = this.getRow(rowIndex);
        YAHOO.util.Dom.addClass(this.lastEditRow, 'ygrid-prop-edting');
    },
    
    setSource : function(source){
        this.dataModel.setSource(source);
    },
    
    getSource : function(){
        return this.dataModel.getSource();
    }
});

	Example = function(){
	        var layout;
	        return {
	            init : function(){
	               layout = new YAHOO.ext.BorderLayout(document.body, {
	                    hideOnLayout: true,
	                    north: {
	                        split:false,
	                        initialSize: 25,
	                        titlebar: false
	                    },
	                    west: {
	                        split:true,
	                        initialSize: 200,
	                        minSize: 175,
	                        maxSize: 400,
	                        titlebar: true,
	                        collapsible: true,
                          animate: true
	                    },
	                    east: {
	                        split:true,
	                        initialSize: 202,
	                        minSize: 175,
	                        maxSize: 400,
	                        titlebar: true,
	                        collapsible: true,
                          animate: true
	                    },
	                    south: {
	                        split:true,
	                        initialSize: 100,
	                        minSize: 100,
	                        maxSize: 200,
	                        titlebar: true,
	                        collapsible: true,
                          animate: true
	                    },
	                    center: {
	                        titlebar: true,
	                        autoScroll:true
	                    }
	                });
	                
	                var propsGrid = new YAHOO.ext.grid.PropsGrid('props-panel');
	                // bogus object as a data source
                    
                    propsGrid.render();
                    layout.beginUpdate();
		                layout.add('north', new YAHOO.ext.ContentPanel('north', 'North'));
		                layout.add('south', new YAHOO.ext.ContentPanel('south', {title: '', closable: true}));
		                layout.add('west', new YAHOO.ext.ContentPanel('west', {title: 'Menu'}));
		                layout.add('east', new YAHOO.ext.ContentPanel('east', {title: 'Details', closable: false}));
		                layout.add('center', new YAHOO.ext.ContentPanel('center2', {title: 'Work Area', closable: false}));
		                layout.getRegion('center').showPanel('center1');
		                layout.getRegion('east').collapse(true);
		                layout.endUpdate();
	           },
	           
	           contractMe : function(it){
	                layout.getRegion(it).collapse(false);
	           },
	           
	           expandMe : function(it){
	                layout.getRegion(it).expand(false);
	           }
	     }
	       
	}();
	YAHOO.ext.EventManager.onDocumentReady(Example.init, Example, true);

	var persistmenu="yes" 
	var persisttype="sitewide"
	
	if (document.getElementById){ //DynamicDrive.com change
	document.write('<style type="text/css">\n')
	document.write('.submenu{display: none;}\n')
	document.write('</style>\n')
	}
	
	function SwitchMenu(obj){
		if(document.getElementById){
		var el = document.getElementById(obj);
		var ar = document.getElementById("masterdiv").getElementsByTagName("span"); 
			if(el.style.display != "block"){ 
				for (var i=0; i<ar.length; i++){
					if (ar[i].className=="submenu") 
					ar[i].style.display = "none";
				}
				el.style.display = "block";
			}else{
				el.style.display = "none";
			}
		}
	}
	
	function get_cookie(Name) { 
	var search = Name + "="
	var returnvalue = "";
	if (document.cookie.length > 0) {
	offset = document.cookie.indexOf(search)
	if (offset != -1) { 
	offset += search.length
	end = document.cookie.indexOf(";", offset);
	if (end == -1) end = document.cookie.length;
	returnvalue=unescape(document.cookie.substring(offset, end))
	}
	}
	return returnvalue;
	}
	
	function onloadfunction(){
	if (persistmenu=="yes"){
	var cookiename=(persisttype=="sitewide")? "switchmenu" : window.location.pathname
	var cookievalue=get_cookie(cookiename)
	if (cookievalue!="")
	document.getElementById(cookievalue).style.display="block"
	}
	}
	
	function savemenustate(){
	var inc=1, blockid=""
	while (document.getElementById("sub"+inc)){
	if (document.getElementById("sub"+inc).style.display=="block"){
	blockid="sub"+inc
	break
	}
	inc++
	}
	var cookiename=(persisttype=="sitewide")? "switchmenu" : window.location.pathname
	var cookievalue=(persisttype=="sitewide")? blockid+";path=/" : blockid
	document.cookie=cookiename+"="+cookievalue
	}
	
	if (window.addEventListener)
	window.addEventListener("load", onloadfunction, false)
	else if (window.attachEvent)
	window.attachEvent("onload", onloadfunction)
	else if (document.getElementById)
	window.onload=onloadfunction
	
	if (persistmenu=="yes" && document.getElementById)
	window.onunload=savemenustate