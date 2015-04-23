//------------------------------------------------------------------------------
//Copyright (c) 2004-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-11
//Revisions
//------------------------------------------------------------------------------
//$Log: MessageHandlerScheduler.java,v $
//Revision 1.1.2.3.4.1.2.1  2007/03/15 17:49:41  tcs
//Updated to 5 min intervals for emailer
//
//Revision 1.1.2.3.4.1  2006/08/30 10:32:29  tcs
//Fixed comments
//
//Revision 1.1.2.3  2005/11/09 18:12:38  tcs
//Initial entry
//
//------------------------------------------------------------------------------

package com.verilion.scheduler;

import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import com.verilion.object.mail.MessageHandler;

/**
 * Schedule a task that executes at regular intervals.
 */

public class MessageHandlerScheduler {
	Toolkit toolkit;
	Timer timer;

	public MessageHandlerScheduler() {
		toolkit = Toolkit.getDefaultToolkit();
		timer = new Timer(true);
		timer.schedule(new RemindTask(), 0, // initial delay
				1000 * 60 * 5); // subsequent rate every 1 min
	}

	public void Stop() {
		if (timer != null) {
			timer.cancel();
		}
	}

	class RemindTask extends TimerTask {
		public void run() {
			// System.out.println("Sending email");
			MessageHandler myHandler = new MessageHandler();
			try {
				myHandler.SendMessagesInQueue();
			} catch (Exception e) {
				System.out.println("Error sending email: " + e.toString());
				e.printStackTrace();
			}
		}
	}

	public static void main() {
		System.out.println("About to schedule task.");
		new MessageHandlerScheduler();
		System.out.println("Task scheduled.");
	}

}