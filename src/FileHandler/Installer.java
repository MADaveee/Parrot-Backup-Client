package FileHandler;

import java.io.File;

public class Installer {
FileHandler fileHandler;
    public Installer(FileHandler fileHandler){
        this.fileHandler = fileHandler;

        initResources();
    }
    private final String username = "david";
    private final String dirPath = "C:\\Users\\"+username+"\\Downloads\\Parrot-Backup-Client-Installer";

    private final String dest = "C:\\Users\\"+username+"\\Documents\\Parrot-Backup-Client";

    private final File systemTray = new File(dirPath+"\\images\\systemTray.png");
    private final File background = new File(dirPath+"\\images\\background.png");
    private final File cross = new File(dirPath+"\\images\\cross.png");
    private final File addDirectory = new File(dirPath+"\\images\\addDirectory.png");
    private final File changeBackupDir = new File(dirPath+"\\images\\changeBackupDir.png");
    private final File backup = new File(dirPath+"\\images\\backup.png");
    private final File autoBackupDisabled = new File(dirPath+"\\images\\autoBackupDisabled.png");
    private final File autoBackupEnabled = new File(dirPath+"\\images\\autoBackupEnabled.png");
    private final File exit = new File(dirPath+"\\images\\exit.png");
    private final File arrowUp = new File(dirPath+"\\images\\arrowUp.png");
    private final File arrowDown = new File(dirPath+"\\images\\arrowDown.png");

    private final File destsystemTray = new File(dest+"\\images\\systemTray.png");
    private final File destbackground = new File(dest+"\\images\\background.png");
    private final File destcross = new File(dest+"\\images\\cross.png");
    private final File destaddDirectory = new File(dest+"\\images\\addDirectory.png");
    private final File destchangeBackupDir = new File(dest+"\\images\\changeBackupDir.png");
    private final File destbackup = new File(dest+"\\images\\backup.png");
    private final File destautoBackupDisabled = new File(dest+"\\images\\autoBackupDisabled.png");
    private final File destautoBackupEnabled = new File(dest+"\\images\\autoBackupEnabled.png");
    private final File destexit = new File(dest+"\\images\\exit.png");
    private final File destarrowUp = new File(dest+"\\images\\arrowUp.png");
    private final File destarrowDown = new File(dest+"\\images\\arrowDown.png");
    private void initResources(){

        if(fileHandler.getInstalled()==null){
            systemTray.renameTo(destsystemTray);
            background.renameTo(destbackground);
            cross.renameTo(destcross);
            addDirectory.renameTo(destaddDirectory);
            changeBackupDir.renameTo(destchangeBackupDir);
            backup.renameTo(destbackup);
            autoBackupDisabled.renameTo(destautoBackupDisabled);
            autoBackupEnabled.renameTo(destautoBackupEnabled);
            exit.renameTo(destexit);
            arrowUp.renameTo(destarrowUp);
            arrowDown.renameTo(destarrowDown);
            fileHandler.setInstalled();
        }


    }
}
