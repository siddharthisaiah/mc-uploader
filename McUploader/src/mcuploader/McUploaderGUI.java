package mcuploader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.FileUtils;


/**
 *
 * @author siddharth.isaiah@zocdoc.com
 */
public class McUploaderGUI extends JFrame {
    
    public static JFrame mainFrame;
    private static DefaultListModel modelFiles;
    private static JList fileList;
    private static File[] selectedFiles;
        
    public McUploaderGUI(){
        mainWindow();
    }
       
    public static void mainWindow(){
        //Main Frame
        mainFrame = new JFrame("MC Uploader");
        mainFrame.setSize(400, 400);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        
        //Add mc uploader icon to frame
        try {
            mainFrame.setIconImage(ImageIO.read(new File("icon-uploader.png")));
        } catch (IOException ex) {
            Logger.getLogger(McUploaderGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Position the frame in the center
        Toolkit tk = Toolkit.getDefaultToolkit();   //Get monitor size
        Dimension screenSize = tk.getScreenSize(); //Get screen size
        Dimension frameSize = mainFrame.getSize();
        int width = (screenSize.width - frameSize.width)/2;
        int height = (screenSize.height - frameSize.height)/2;
        mainFrame.setLocation(width, height);
        
        //JPanel and GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(30,30,30,30);
        
                //Add files button to mainPanel
                JButton addFilesButton = new JButton("Add Files");
                c.gridx = 0;
                c.gridy = 10;
                addFilesButton.setPreferredSize(new Dimension(100, 30));
                addFilesButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                fileChooser();
            }
            
        });
        mainPanel.add(addFilesButton,c);
        
        //Add a "clear files" button to mainPanel
        JButton clearButton = new JButton("Clear Files");
        c.gridx = 0;
        c.gridy = 20;
        clearButton.setPreferredSize(new Dimension(100, 30));
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                modelFiles.clear();
                selectedFiles = null;                
            }
        });
        mainPanel.add(clearButton,c);
        
        //Add an "upload files" button to mainPanel
        JButton uploadButton = new JButton("Upload");
        c.gridx = 0;
        c.gridy = 30;
        uploadButton.setPreferredSize(new Dimension(100, 30));
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {                
                    uploadStuff();
                } catch (IOException ex) {
                    Logger.getLogger(McUploaderGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        mainPanel.add(uploadButton,c);
        
        //Another JPanel to hold the JList
        JPanel listPanel = new JPanel();
        mainFrame.add(listPanel,BorderLayout.EAST);
        
        //JList to hold all selected files
        modelFiles = new DefaultListModel();
        fileList = new JList(modelFiles);
        fileList.setPreferredSize(new Dimension(225, 365));
        
        
        
        listPanel.add(fileList);
        
        
        mainFrame.add(mainPanel,BorderLayout.WEST);
        
    }
    
    public static void fileChooser(){
    //JFileChooser object to select files to upload and populate JList with names of files
        JFileChooser chooseFile = new JFileChooser();
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Robots, Types, and Snippets", "robot", "type", "snippet");
        chooseFile.setFileFilter(filter);
        chooseFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooseFile.setMultiSelectionEnabled(true);
        int response = chooseFile.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION){
             selectedFiles = chooseFile.getSelectedFiles();
        
            for(int x = 0; x< selectedFiles.length;x++){
                modelFiles.addElement(selectedFiles[x].getName());
            }
        }
        
    }
    
    public static void copyCat() throws IOException {
    //copies selectedFiles into custom made directory
    String dirname = "C:\\akdoZdkowwkdjfp\\";
    
    for(int x = 0; x < selectedFiles.length; x++) {
        File sourceFile = new File(selectedFiles[x].getAbsolutePath());
        File targetfile = new File(dirname + selectedFiles[x].getName());
        FileUtils.copyFile(sourceFile, targetfile);
    }

        
    }
    
    public static void uploadStuff() throws IOException{
        //copy files to dirname
        try{
            copyCat();
            //upload files
            String dirname = "C:\\akdoZdkowwkdjfp\\";
            File dir = new File(dirname);
            SimpleRepositoryUploader.uploadInitiation(dirname);
            //delete files from disk and remove file names from JList
            FileUtils.deleteDirectory(dir);
            modelFiles.clear();
            //show upload successfull message
            JOptionPane.showMessageDialog(mainFrame, "Files uploaded!");
        
        }catch (NullPointerException nlp){
            JOptionPane.showMessageDialog(mainFrame, "Add Files to Upload First");
        }
        
        
        
    }
    

    
}
