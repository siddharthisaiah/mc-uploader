package mcuploader;

import java.util.List;


/**
 *
 * @author siddharth.isaiah@zocdoc.com
 * No JPanels were harmed in the making of this Application
 */
public class McUploader {
    
    public static List Addresses = IpRetrieval.sqlFu();

    public static void main(String[] args) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                McUploaderGUI mcUploaderGUI = new McUploaderGUI();
             }
        });
        
    }
    
}