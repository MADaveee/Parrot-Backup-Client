package Backup;

import FileHandler.FileHandler;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class Copy {
FileHandler fileHandler;

    public Copy(FileHandler fileHandler){
        this.fileHandler = fileHandler;

    }
    
    public void backupFiles(TrayIcon trayIcon){

        new Thread(() -> {

            try {

                trayIcon.displayMessage("Parrot-Backup-Client", "The backuping process has started", TrayIcon.MessageType.INFO);

                List<String> backupDirlist = Files.readAllLines(fileHandler.getBackupDir().toPath());
                List<String> selectedDirlist = Files.readAllLines(fileHandler.getSelectedDirs().toPath());

                int finalLetters = 0;
                int slashcounter = 0;
                int checkcounter = 0;

                for(int i = 0;i<selectedDirlist.size();i++){

                    for(int k = 0;k<selectedDirlist.get(i).length();k++){

                        if(selectedDirlist.get(i).charAt(k)=='\\')
                            slashcounter++;
                    }

                    for(int n = 0;n<selectedDirlist.get(i).length();n++){

                        if(selectedDirlist.get(i).charAt(n)=='\\')
                            checkcounter++;

                        if(checkcounter==slashcounter)
                            finalLetters++;
                    }

                    System.out.println(backupDirlist.get(0)+selectedDirlist.get(i).substring(selectedDirlist.get(i).length()-finalLetters));

                    File dest = new File(backupDirlist.get(0)+selectedDirlist.get(i).substring(selectedDirlist.get(i).length()-finalLetters));
                    File src = new File(selectedDirlist.get(i));

                    FileUtils.copyDirectory(src, dest);

                    finalLetters = 0;
                    slashcounter = 0;
                    checkcounter = 0;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            trayIcon.displayMessage("Parrot-Backup-Client", "The backuping process has finished", TrayIcon.MessageType.INFO);
        }).start();

        
    }

}
