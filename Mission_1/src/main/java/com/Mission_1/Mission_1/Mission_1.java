package com.Mission_1.Mission_1;

import javax.swing.SwingUtilities;

public class Mission_1 {
    public static void main( String[] args ) {
    	Controller controller = new Controller();
    	SwingUtilities.invokeLater(new Runnable() {
    		public void run ( )  {
    			controller.openGui();
    		}
    	});
    }
}
