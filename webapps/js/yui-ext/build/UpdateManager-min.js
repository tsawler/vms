/*
 * yui-ext 0.33
 * Copyright(c) 2006, Jack Slocum.
 */


YAHOO.ext.UpdateManager=function(el,forceNew){el=YAHOO.ext.Element.get(el);if(!forceNew&&el.updateManager){return el.updateManager;}
this.el=el;this.defaultUrl=null;this.beforeUpdate=new YAHOO.util.CustomEvent('UpdateManager.beforeUpdate');this.onUpdate=new YAHOO.util.CustomEvent('UpdateManager.onUpdate');this.onFailure=new YAHOO.util.CustomEvent('UpdateManager.onFailure');this.events={'beforeupdate':this.beforeUpdate,'update':this.onUpdate,'failure':this.onFailure};this.sslBlankUrl=YAHOO.ext.UpdateManager.defaults.sslBlankUrl;this.disableCaching=YAHOO.ext.UpdateManager.defaults.disableCaching;this.indicatorText=YAHOO.ext.UpdateManager.defaults.indicatorText;this.showLoadIndicator=YAHOO.ext.UpdateManager.defaults.showLoadIndicator;this.timeout=YAHOO.ext.UpdateManager.defaults.timeout;this.loadScripts=YAHOO.ext.UpdateManager.defaults.loadScripts;this.transaction=null;this.autoRefreshProcId=null;this.refreshDelegate=this.refresh.createDelegate(this);this.updateDelegate=this.update.createDelegate(this);this.formUpdateDelegate=this.formUpdate.createDelegate(this);this.successDelegate=this.processSuccess.createDelegate(this);this.failureDelegate=this.processFailure.createDelegate(this);this.renderer=new YAHOO.ext.UpdateManager.BasicRenderer();};YAHOO.ext.UpdateManager.prototype={fireEvent:YAHOO.ext.util.Observable.prototype.fireEvent,on:YAHOO.ext.util.Observable.prototype.on,addListener:YAHOO.ext.util.Observable.prototype.addListener,delayedListener:YAHOO.ext.util.Observable.prototype.delayedListener,removeListener:YAHOO.ext.util.Observable.prototype.removeListener,purgeListeners:YAHOO.ext.util.Observable.prototype.purgeListeners,bufferedListener:YAHOO.ext.util.Observable.prototype.bufferedListener,getEl:function(){return this.el;},update:function(url,params,callback,discardUrl){if(this.beforeUpdate.fireDirect(this.el,url,params)!==false){if(typeof url=='object'){var cfg=url;url=cfg.url;params=params||cfg.params;callback=callback||cfg.callback;discardUrl=discardUrl||cfg.discardUrl;if(callback&&cfg.scope){callback=callback.createDelegate(cfg.scope);}
if(typeof cfg.nocache!='undefined'){this.disableCaching=cfg.nocache};if(typeof cfg.text!='undefined'){this.indicatorText='<div class="loading-indicator">'+cfg.text+'</div>'};if(typeof cfg.scripts!='undefined'){this.loadScripts=cfg.scripts};if(typeof cfg.timeout!='undefined'){this.timeout=cfg.timeout};}
this.showLoading();if(!discardUrl){this.defaultUrl=url;}
if(typeof url=='function'){url=url();}
if(typeof params=='function'){params=params();}
if(params&&typeof params!='string'){var buf=[];for(var key in params){if(typeof params[key]!='function'){buf.push(encodeURIComponent(key),'=',encodeURIComponent(params[key]),'&');}}
delete buf[buf.length-1];params=buf.join('');}
var callback={success:this.successDelegate,failure:this.failureDelegate,timeout:(this.timeout*1000),argument:{'url':url,'form':null,'callback':callback,'params':params}};var method=params?'POST':'GET';if(method=='GET'){url=this.prepareUrl(url);}
this.transaction=YAHOO.util.Connect.asyncRequest(method,url,callback,params);}},formUpdate:function(form,url,reset,callback){if(this.beforeUpdate.fireDirect(this.el,form,url)!==false){this.showLoading();formEl=YAHOO.util.Dom.get(form);if(typeof url=='function'){url=url();}
if(typeof params=='function'){params=params();}
url=url||formEl.action;var callback={success:this.successDelegate,failure:this.failureDelegate,timeout:(this.timeout*1000),argument:{'url':url,'form':form,'callback':callback,'reset':reset}};var isUpload=false;var enctype=formEl.getAttribute('enctype');if(enctype&&enctype.toLowerCase()=='multipart/form-data'){isUpload=true;}
YAHOO.util.Connect.setForm(form,isUpload,this.sslBlankUrl);this.transaction=YAHOO.util.Connect.asyncRequest('POST',url,callback);}},refresh:function(callback){if(this.defaultUrl==null){return;}
this.update(this.defaultUrl,null,callback,true);},startAutoRefresh:function(interval,url,params,callback,refreshNow){if(refreshNow){this.update(url||this.defaultUrl,params,callback,true);}
if(this.autoRefreshProcId){clearInterval(this.autoRefreshProcId);}
this.autoRefreshProcId=setInterval(this.update.createDelegate(this,[url||this.defaultUrl,params,callback,true]),interval*1000);},stopAutoRefresh:function(){if(this.autoRefreshProcId){clearInterval(this.autoRefreshProcId);}},showLoading:function(){if(this.showLoadIndicator){this.el.update(this.indicatorText);}},prepareUrl:function(url){if(this.disableCaching){var append='_dc='+(new Date().getTime());if(url.indexOf('?')!==-1){url+='&'+append;}else{url+='?'+append;}}
return url;},processSuccess:function(response){this.transaction=null;if(response.argument.form&&response.argument.reset){try{response.argument.form.reset();}catch(e){}}
if(this.loadScripts){this.renderer.render(this.el,response,this,this.updateComplete.createDelegate(this,[response]));}else{this.renderer.render(this.el,response,this);this.updateComplete(response);}},updateComplete:function(response){this.fireEvent('update',this.el,response);if(typeof response.argument.callback=='function'){response.argument.callback(this.el,true);}},processFailure:function(response){this.transaction=null;this.onFailure.fireDirect(this.el,response);if(typeof response.argument.callback=='function'){response.argument.callback(this.el,false);}},setRenderer:function(renderer){this.renderer=renderer;},getRenderer:function(){return this.renderer;},setDefaultUrl:function(defaultUrl){this.defaultUrl=defaultUrl;},abort:function(){if(this.transaction){YAHOO.util.Connect.abort(this.transaction);}},isUpdating:function(){if(this.transaction){return YAHOO.util.Connect.isCallInProgress(this.transaction);}
return false;}};YAHOO.ext.UpdateManager.defaults={timeout:30,loadScripts:false,sslBlankUrl:(YAHOO.ext.SSL_SECURE_URL||'javascript:false'),disableCaching:false,showLoadIndicator:true,indicatorText:'<div class="loading-indicator">Loading...</div>'};YAHOO.ext.UpdateManager.updateElement=function(el,url,params,options){var um=getEl(el,true).getUpdateManager();YAHOO.ext.util.Config.apply(um,options);um.update(url,params,options.callback);}
YAHOO.ext.UpdateManager.update=YAHOO.ext.UpdateManager.updateElement;YAHOO.ext.UpdateManager.BasicRenderer=function(){};YAHOO.ext.UpdateManager.BasicRenderer.prototype={render:function(el,response,updateManager,callback){el.update(response.responseText,updateManager.loadScripts,callback);}};