package Timer;

import Backup.Copy;
import FileHandler.FileHandler;
import Window.Window;

import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;

public class TimeHandler {
FileHandler fileHandler;
Copy copy;
Window window;
    public TimeHandler(FileHandler fileHandler, Copy copy, Window window){
        this.fileHandler = fileHandler;
        this.copy = copy;
        this.window = window;

        scheduler();
    }

    private void scheduler(){

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if(fileHandler.getAutoBackup()) {

                    LocalDate localDate = LocalDate.now();
                    int nowYear = localDate.getYear();
                    int nowMonth = localDate.getMonthValue();
                    int nowDay = localDate.getDayOfMonth();

                    int lastBackupYear = fileHandler.getLastBackup().getYear();
                    int lastBackupMonth = fileHandler.getLastBackup().getMonthValue();
                    int lastBackupDay = fileHandler.getLastBackup().getDayOfMonth();

                    if (nowYear != lastBackupYear){
                        fileHandler.setLastBackup(LocalDate.now());
                        copy.backupFiles(window.trayIcon);
                    }

                    else if (nowMonth != lastBackupMonth){
                        fileHandler.setLastBackup(LocalDate.now());
                        copy.backupFiles(window.trayIcon);
                    }

                    else if (nowDay != lastBackupDay){
                        fileHandler.setLastBackup(LocalDate.now());
                        copy.backupFiles(window.trayIcon);
                    }

                }
            }
        },0,3600000);

    }
}
