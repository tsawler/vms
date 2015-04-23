//------------------------------------------------------------------------------
//Copyright (c) 2003-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-05-28
//Revisions
//------------------------------------------------------------------------------
//$Log: Effects.java,v $
//Revision 1.1.2.1  2007/02/04 00:58:48  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.object.jsremoting;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.scriptaculous.Effect;

/**
 * Use DWR to invoke scriptaculous effects on a single browser's web page
 * 
 * @author Trevor
 * 
 */
public class Effects {

	public Effects() {

	}

	public void initSession() {
		WebContext wctx = WebContextFactory.get();
		wctx.getScriptSession();
	}

	public void fadeEffect(String id) {
		WebContext wctx = WebContextFactory.get();
		Effect effect = new Effect(wctx.getScriptSession());
		effect.fade(id);
	}

	public void fadeEffect(String id, String options) {
		WebContext wctx = WebContextFactory.get();
		Effect effect = new Effect(wctx.getScriptSession());
		effect.fade(id, "{" + options + "}");
	}

	public void blindUpEffect(String id) {
		WebContext wctx = WebContextFactory.get();
		Effect effect = new Effect(wctx.getScriptSession());
		effect.blindUp(id);
	}

	public void blindUpEffect(String id, String options) {
		WebContext wctx = WebContextFactory.get();
		Effect effect = new Effect(wctx.getScriptSession());
		effect.blindUp(id, "{" + options + "}");
	}

	public void blindDownEffect(String id) {
		WebContext wctx = WebContextFactory.get();
		Effect effect = new Effect(wctx.getScriptSession());
		effect.blindDown(id);
	}

	public void blindDownEffect(String id, String options) {
		WebContext wctx = WebContextFactory.get();
		Effect effect = new Effect(wctx.getScriptSession());
		effect.blindDown(id, "{" + options + "}");
	}

	public void appearEffect(String id) {
		WebContext wctx = WebContextFactory.get();
		Effect effect = new Effect(wctx.getScriptSession());
		effect.appear(id);
	}

	public void appearDownEffect(String id, String options) {
		WebContext wctx = WebContextFactory.get();
		Effect effect = new Effect(wctx.getScriptSession());
		effect.appear(id, "{" + options + "}");
	}

	public void dropOutEffect(String id) {
		WebContext wctx = WebContextFactory.get();
		Effect effect = new Effect(wctx.getScriptSession());
		effect.dropOut(id);
	}

	public void dropOutEffect(String id, String options) {
		WebContext wctx = WebContextFactory.get();
		Effect effect = new Effect(wctx.getScriptSession());
		effect.dropOut(id, "{" + options + "}");
	}

	public void foldEffect(String id) {
		WebContext wctx = WebContextFactory.get();
		Effect effect = new Effect(wctx.getScriptSession());
		effect.fold(id);
	}

	public void foldDownEffect(String id, String options) {
		WebContext wctx = WebContextFactory.get();
		Effect effect = new Effect(wctx.getScriptSession());
		effect.fold(id, "{" + options + "}");
	}

	public void growEffect(String id) {
		WebContext wctx = WebContextFactory.get();
		Effect effect = new Effect(wctx.getScriptSession());
		effect.grow(id);
	}

	public void growEffect(String id, String options) {
		WebContext wctx = WebContextFactory.get();
		Effect effect = new Effect(wctx.getScriptSession());
		effect.grow(id, "{" + options + "}");
	}

	public void highlightEffect(String id) {
		WebContext wctx = WebContextFactory.get();
		Effect effect = new Effect(wctx.getScriptSession());
		effect.highlight(id);
	}

	public void hightlightEffect(String id, String options) {
		WebContext wctx = WebContextFactory.get();
		Effect effect = new Effect(wctx.getScriptSession());
		effect.highlight(id, "{" + options + "}");
	}

	public void puffEffect(String id) {
		WebContext wctx = WebContextFactory.get();
		Effect effect = new Effect(wctx.getScriptSession());
		effect.puff(id);
	}

	public void puffEffect(String id, String options) {
		WebContext wctx = WebContextFactory.get();
		Effect effect = new Effect(wctx.getScriptSession());
		effect.puff(id, "{" + options + "}");
	}

	public void pulsateEffect(String id) {
		WebContext wctx = WebContextFactory.get();
		Effect effect = new Effect(wctx.getScriptSession());
		effect.pulsate(id);
	}

	public void pulsateEffect(String id, String options) {
		WebContext wctx = WebContextFactory.get();
		Effect effect = new Effect(wctx.getScriptSession());
		effect.pulsate(id, "{" + options + "}");
	}
}