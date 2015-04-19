//------------------------------------------------------------------------------
//Copyright (c) 2004-2009 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2009-04-29
//Revisions
//------------------------------------------------------------------------------
//$Log: ClearChatScheduler.java,v $
//Revision 1.1.2.1  2009/07/22 16:29:35  tcs
//*** empty log message ***
//
//------------------------------------------------------------------------------

package com.verilion.scheduler;

import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import com.verilion.components.Chat;


/**
 * Schedule a task that executes at regular intervals.
 */

public class ClearChatScheduler {
   Toolkit toolkit;
   Timer timer;

   public ClearChatScheduler() {
      toolkit = Toolkit.getDefaultToolkit();
      timer = new Timer(true);
      timer.schedule(new RemindTask(), 0, // initial delay
            1000 * 60 * 3); // subsequent rate every 10 min
   }
   
   public void Stop() {
      if (timer != null) {
         timer.cancel();
      }
   }

   class RemindTask extends TimerTask {
      public void run() {
    	  Chat myChat = new Chat();
    	  myChat.removeInactiveUsers();
      }
   }

   public static void main() {
      new ClearChatScheduler();
   }
}