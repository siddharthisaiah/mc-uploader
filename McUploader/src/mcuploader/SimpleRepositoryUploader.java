package mcuploader;

// The following libraries must be on the CLASSPATH.    All are installed under the Kapow installation directory.
// robosuite-api.jar
// commons-logging-1.1.1.jar
// commons-codec-1.4.jar
// commons-ssl-0.3.8.jar
// httpclient-4.1.jar
// httpcore-4.1.jar

import java.io.*;
import java.util.Date;
import java.text.*;
import com.kapowtech.robosuite.api.java.repository.engine.*;
import com.kapowtech.robosuite.api.java.util.*;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static mcuploader.McUploaderGUI.mainFrame;

/**
 * @author ben.breakstone@kapowsoftware.com
 */

public class SimpleRepositoryUploader {
    // Uploads all robots, types, and snippets from C:\KapowFiles\UploadToMC\ to a management console at localhost:50080.
    // TODO: Parse and validate upload dir, credentials, MC host info from command-line args
    // TODO: Probably more efficient to create File from dir string once, pass around resulting File
    // TODO: Don't use hard-coded Default project; verify project exists before upload
    // TODO: More complete Javadoc comments
    /**
     *
     * @param uploadDirectory
     * @param user
     * @param password
     * @param mgmtConsoleHost
     * @param mgmtConsolePort
     */
    public static void uploadRobotFolderToRepository(
    // Upload all .robot files in a directory to the specified MC; log results to file in the directory
            String uploadDirectory, String user, String password, String mgmtConsoleHost, int mgmtConsolePort ){
            
            
        
            RepositoryClient client = null;
            try {
                client = RepositoryClientFactory.createRepositoryClient("http://" + mgmtConsoleHost + ":" + mgmtConsolePort, user, password);
                //client = new RepositoryClient("http://" + mgmtConsoleHost + ":" + mgmtConsolePort, user, password);
            } catch (RepositoryClientException e) {
                // an error connecting to the repository
                e.printStackTrace();
                writeToLog("ERROR: " + e.getMessage(), uploadDirectory);
            }
            File[] robotFiles = filterFiles(uploadDirectory, ".robot");
            for (File robotFile : robotFiles) {
                try {
                FileInputStream robotStream = new FileInputStream(robotFile);
                byte[] robotBytes = StreamUtil.readStream(robotStream).toByteArray();
                client.deployRobot("Default project", robotFile.getName(), robotBytes, false);
                // Always uploads to Default project, might be good to check if that still exists first
                writeToLog("Uploaded robot " + robotFile.getName(), uploadDirectory);
                } catch (FileNotFoundException e) {
                    System.out.println("Could not load robot file from disk " + e.getMessage());
                    writeToLog("ERROR: Could not load robot file from disk " + e.getMessage(), uploadDirectory);
                } catch (IOException e) {
                    System.out.println("Could not read bytes from stream " + e.getMessage());
                    writeToLog("ERROR: Could not read bytes from stream " + e.getMessage(), uploadDirectory);
                } catch (FileAlreadyExistsException e) {
                    // the file already exists in the project
                    System.out.println(e.getMessage());
                    writeToLog("ERROR: " + e.getMessage(), uploadDirectory);
                } catch (RepositoryClientException e) {
                    // an error connecting to the repository
                    e.printStackTrace();
                    writeToLog("ERROR: " + e.getMessage(), uploadDirectory);
                    JOptionPane.showMessageDialog(mainFrame, "ERROR: " + e.getMessage() + ". Check Roboserver status");
                }
            }
        }

    public static void uploadTypeFolderToRepository(
    // Upload all .type files in a directory to the specified MC; log results to file in the directory
        String uploadDirectory, String user, String password, String mgmtConsoleHost, int mgmtConsolePort ){
        RepositoryClient client = null;
        try {
            client = RepositoryClientFactory.createRepositoryClient("http://" + mgmtConsoleHost + ":" + mgmtConsolePort, user, password);
        } catch (RepositoryClientException e) {
            // an error connecting to the repository
            e.printStackTrace();
            writeToLog("ERROR: " + e.getMessage(), uploadDirectory);
        }
        File[] typeFiles = filterFiles(uploadDirectory, ".type");
        for (File typeFile : typeFiles) {
            try {
            FileInputStream typeStream = new FileInputStream(typeFile);
            byte[] typeBytes = StreamUtil.readStream(typeStream).toByteArray();
            client.deployType("Default project", typeFile.getName(), typeBytes, false);
            // Always uploads to Default project, might be good to check if that still exists first
            writeToLog("Uploaded type " + typeFile.getName(), uploadDirectory);
            } catch (FileNotFoundException e) {
                System.out.println("Could not load type file from disk " + e.getMessage());
                writeToLog("ERROR: Could not load type file from disk " + e.getMessage(), uploadDirectory);
            } catch (IOException e) {
                System.out.println("Could not read bytes from stream " + e.getMessage());
                writeToLog("ERROR: Could not read bytes from stream " + e.getMessage(), uploadDirectory);
            } catch (FileAlreadyExistsException e) {
                // the file already exists in the project
                System.out.println(e.getMessage());
                writeToLog("ERROR: " + e.getMessage(), uploadDirectory);
            } catch (RepositoryClientException e) {
                // an error connecting to the repository
                e.printStackTrace();
                writeToLog("ERROR: " + e.getMessage(), uploadDirectory);
                JOptionPane.showMessageDialog(mainFrame, "ERROR: " + e.getMessage() + ". Check Roboserver status");
            }
        }
    }

    public static void uploadSnippetFolderToRepository(
    // Upload all .snippet files in a directory to the specified MC; log results to file in the directory
        String uploadDirectory, String user, String password, String mgmtConsoleHost, int mgmtConsolePort ){
        RepositoryClient client = null;
        try {
            client = RepositoryClientFactory.createRepositoryClient("http://" + mgmtConsoleHost + ":" + mgmtConsolePort, user, password);
        } catch (RepositoryClientException e) {
            // an error connecting to the repository
            e.printStackTrace();
            writeToLog("ERROR: " + e.getMessage(), uploadDirectory);
        }
        File[] snippetFiles = filterFiles(uploadDirectory, ".snippet");
        for (File snippetFile : snippetFiles) {
            try {
            FileInputStream snippetStream = new FileInputStream(snippetFile);
            byte[] snippetBytes = StreamUtil.readStream(snippetStream).toByteArray();
            client.deploySnippet("Default project", snippetFile.getName(), snippetBytes, false);
            // Always uploads to Default project, might be good to check if that still exists first
            writeToLog("Uploaded snippet " + snippetFile.getName(), uploadDirectory);
            } catch (FileNotFoundException e) {
                System.out.println("Could not load snippet file from disk " + e.getMessage());
                writeToLog("ERROR: Could not load snippet file from disk " + e.getMessage(), uploadDirectory);
            } catch (IOException e) {
                System.out.println("Could not read bytes from stream " + e.getMessage());
                writeToLog("ERROR: Could not read bytes from stream " + e.getMessage(), uploadDirectory);
            } catch (FileAlreadyExistsException e) {
                // the snippet file already exists in the project
                System.out.println(e.getMessage());
                writeToLog("ERROR: " + e.getMessage(), uploadDirectory);
            } catch (RepositoryClientException e) {
                // an error connecting to the repository
                e.printStackTrace();
                writeToLog("ERROR: " + e.getMessage(), uploadDirectory);
                JOptionPane.showMessageDialog(mainFrame, "ERROR: " + e.getMessage() + ". Check Roboserver status");
            }
        }
    }

    public static void writeToLog(String logMessage, String uploadDirectory){
    // Creates or opens RepositoryAPILog.txt in the specified directory, appends a line with timestamp
        try {
            File logFile = new File(uploadDirectory + "RepositoryAPILog.txt");
            FileWriter fw = new FileWriter(logFile,true);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            Date logTime = new Date();
            String newLine = String.format("%n"); // Platform-specific newline

            fw.write(df.format(logTime) + " " + logMessage + newLine);
            fw.flush();
            fw.close();
        } catch (Exception e) {
            System.exit(0);
        }
    }

    public static File[] filterFiles(String dirName, final String extension){
    // Returns an array of all Files in directory dirName that have names ending in extension
        File dir = new File(dirName);

        return dir.listFiles(new FilenameFilter() {
            @Override
                 public boolean accept(File dir, String filename)
                      { return filename.endsWith(extension); }
        } );
    }

    
    public static void uploadInitiation(String uploadDirectory){
        
            //Starts the upload process from the uploadDirectory
            //nulls for MC configured not to require authentication
                String user = null;
                String password = null;
                List address = McUploader.Addresses;
                int mgmtConsolePort = 50080;
                
                for( int i = 0; i < address.size(); i++) {
                    String mgmtConsoleHost = (String) address.get(i);     
                    uploadRobotFolderToRepository(uploadDirectory, user, password, mgmtConsoleHost, mgmtConsolePort);
                    uploadSnippetFolderToRepository(uploadDirectory, user, password, mgmtConsoleHost, mgmtConsolePort);
                    uploadSnippetFolderToRepository(uploadDirectory, user, password, mgmtConsoleHost, mgmtConsolePort);
                }
        }
    
}