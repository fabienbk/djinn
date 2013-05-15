package com.scramcode.djinn.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class BufferedScrollableWindow {
	
	final JTextArea statusmessageArea = new JTextArea();
	final JScrollPane scroll = new JScrollPane(statusmessageArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	JFrame frame = new JFrame("This is a Buffered Scrollable Window");
	int BUFFERSIZE = 10; 
	//BUFFERSIZE specifies maximum the no of lines in the window which can be printed 
	//before the topmost entry is replaced by the second line and all lines shifted 
	//one line above making way for the latest line to be printed out at the bottom of the
	//window or buffer
	
	BufferedScrollableWindow () {
		statusmessageArea.setBorder(BorderFactory.createEtchedBorder());
		statusmessageArea.setBorder(BorderFactory.createEtchedBorder());
    	statusmessageArea.setWrapStyleWord(true);  
    	
    	scroll.getViewport().setPreferredSize(new Dimension(400, 400));
    	
    	frame.getContentPane().setLayout(new BorderLayout());
    	frame.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    	frame.getContentPane().add(scroll, BorderLayout.CENTER);          
    	frame.pack();
    	frame.setLocationRelativeTo(null);
    	frame.setVisible(true);      		   
	}  	
	
	public void writeIndexStatusLine(String status) {
            
            //When the line count in the text window > size of the buffer, replace the first line in buffer
            //to give way to the last line, all lines in buffer shift one line up
            if (statusmessageArea.getLineCount() > BUFFERSIZE) {
                try {
                    statusmessageArea.replaceRange(null, statusmessageArea.getLineStartOffset(0), statusmessageArea.getLineEndOffset(0));
                } catch (Exception e) {                
                    statusmessageArea.append("An exception occured in replacing the topmost entry in the buffer");
                    e.printStackTrace();
                    return;
                }
            }
            
            statusmessageArea.append(status + "\n");
            
            //Update the text window with the latest line to be printed
            try {
                statusmessageArea.setCaretPosition(statusmessageArea.getLineEndOffset(statusmessageArea.getLineCount() - 1));
                statusmessageArea.repaint();
            } catch (Exception e1) {
                statusmessageArea.append("An exception occured writing lines to buffer");
                e1.printStackTrace();
            }
    }      
    	
    public static void main(String[] args) throws Exception {
    	
       	BufferedScrollableWindow BW = new BufferedScrollableWindow();
       	
    	for (int i = 1; i<=50; i++) {
    		BW.writeIndexStatusLine("This is line " + i);
    		Thread.sleep(1000);
    	}
    		
    }   
}