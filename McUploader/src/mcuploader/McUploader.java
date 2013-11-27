/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mcuploader;

/**
 *
 * @author siddharth.isaiah
 */
public class McUploader {

    /**
     * @param args the command line arguments
     */
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
