package com.twitter.automation.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.twitter.automation.testscript.TwitterAutomation;

public class ApplicationListener implements ServletContextListener {
	TwitterAutomation twitterAutomationObj = new TwitterAutomation();
	static int i = 0;

	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Context Destroyed");
	}

	public void contextInitialized(ServletContextEvent ctx) {
		WebApplicationContextUtils.getRequiredWebApplicationContext(ctx.getServletContext())
				.getAutowireCapableBeanFactory().autowireBean(this);

		Runnable runnable = new Runnable() {
			
			public void run() {
				try {
					System.out.println("hi");
					twitterAutomationObj.runTwitter();
					if (i == 1) {

						System.out.println(i);

					}
					i++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		};
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(runnable, 0, 2000, TimeUnit.DAYS);

	}

}
