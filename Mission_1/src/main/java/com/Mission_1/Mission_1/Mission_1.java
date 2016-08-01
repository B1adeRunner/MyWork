package com.Mission_1.Mission_1;

import javax.swing.SwingUtilities;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Mission_1 {
    public static void main( String[] args ) {
    	ApplicationContext ctx = new ClassPathXmlApplicationContext("file:config.xml");
    	Controller controller = (Controller)ctx.getBean("controller");
    	SwingUtilities.invokeLater(new Runnable() {
    		public void run ( ) {
    			controller.openGui();
    		}
    	});
    }
}
