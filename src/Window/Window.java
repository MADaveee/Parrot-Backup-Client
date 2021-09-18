package Window;

import Backup.Copy;
import FileHandler.FileHandler;

import javax.swing.*;
import java.awt.*;

public class Window {
Copy copy;
FileHandler fileHandler;
    public Window(Copy copy, FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        this.copy = copy;

        initWindow();
        systemTray();

    }

    private int size = 999;
    private int visibleMax = 0;
    private int visibleMin = 0;
    private int page = 0;
    private JFrame f;
    private JButton add, changeBackupDir, backup, AutoBackup, exit;
    private JButton up, down;
    private JFileChooser fc;
    private JLabel destinationLabel, sourceLabel, background;
    private JLabel destination;
    private JLabel[] source = new JLabel[size];
    private JButton[] deletesource = new JButton[size];

    private ImageIcon backgroundIcon;
    private ImageIcon cross;
    private ImageIcon addDirectoryIcon;
    private ImageIcon changeBackupdirIcon;
    private ImageIcon backupIcon;
    private ImageIcon AutoBackupdisabled;
    private ImageIcon AutoBackupenabled;
    private ImageIcon exitIcon;
    private ImageIcon arrowUp;
    private ImageIcon arrowDown;

    private PopupMenu popup;
    public TrayIcon trayIcon;
    private SystemTray tray;
    private void initWindow() {
        backgroundIcon = new ImageIcon(fileHandler.dirPath+"\\images\\background.png");
        cross = new ImageIcon(fileHandler.dirPath+"\\images\\cross.png");
        addDirectoryIcon = new ImageIcon(fileHandler.dirPath+"\\images\\addDirectory.png");
        changeBackupdirIcon = new ImageIcon(fileHandler.dirPath+"\\images\\changeBackupDir.png");
        backupIcon = new ImageIcon(fileHandler.dirPath+"\\images\\backup.png");
        AutoBackupdisabled = new ImageIcon(fileHandler.dirPath+"\\images\\autoBackupDisabled.png");
        AutoBackupenabled = new ImageIcon(fileHandler.dirPath+"\\images\\autoBackupEnabled.png");
        exitIcon = new ImageIcon(fileHandler.dirPath+"\\images\\exit.png");
        arrowUp = new ImageIcon(fileHandler.dirPath+"\\images\\arrowUp.png");
        arrowDown = new ImageIcon(fileHandler.dirPath+"\\images\\arrowDown.png");

        f = new JFrame("Parrot-Backup-Client");
        f.setSize(800,600);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        f.setResizable(false);

        background = new JLabel(backgroundIcon);
        background.setBounds(0,0,800,600);

        add = new JButton(addDirectoryIcon);
        add.setBounds(5,5,200,30);
        add.setContentAreaFilled(false);
        add.setBorder(null);
        add.addActionListener((e)->{

            fileHandler.setDir(selectDir());
            update();
        });

        changeBackupDir = new JButton(changeBackupdirIcon);
        changeBackupDir.setBounds(5,45,200,30);
        changeBackupDir.setContentAreaFilled(false);
        changeBackupDir.setBorder(null);
        changeBackupDir.addActionListener((e)->{

            fileHandler.setBackupDir(selectDir());
            destination.setText(fileHandler.getBackupFolder());
        });

        backup = new JButton(backupIcon);
        backup.setBounds(5,85,200,30);
        backup.setContentAreaFilled(false);
        backup.setBorder(null);
        backup.addActionListener((e)-> copy.backupFiles(trayIcon));

        AutoBackup = new JButton();
        AutoBackup.setContentAreaFilled(false);
        AutoBackup.setBorder(null);
        AutoBackup.setBounds(5,125,200,30);
        AutoBackup.addActionListener((e)-> checkAutoBackup());

        if(fileHandler.getAutoBackup()){
            AutoBackup.setIcon(AutoBackupenabled);
        }else{
            AutoBackup.setIcon(AutoBackupdisabled);
        }

        exit = new JButton(exitIcon);
        exit.setBounds(5,165,200,30);
        exit.setContentAreaFilled(false);
        exit.setBorder(null);
        exit.addActionListener((e)-> System.exit(0));

        destinationLabel = new JLabel("Your Data is going to be backuped into this directory:");
        destinationLabel.setBounds(280,5,400,30);

        destination = new JLabel(fileHandler.getBackupFolder());
        destination.setBounds(280,35,400,30);

        sourceLabel = new JLabel("These direcotrys are going to be backuped:");
        sourceLabel.setBounds(280,90,400,30);

        for(int i = 0;i<size;i++){
            int finalI = i;
            source[i] = new JLabel();
            source[i].setBounds(280,120+(i*30),400,30);

            deletesource[i] = new JButton(cross);
            deletesource[i].setBounds(740,133+(i*31),21,21);
            deletesource[i].setBorder(null);
            deletesource[i].setContentAreaFilled(false);
            deletesource[i].setVisible(false);
            deletesource[i].addActionListener((e)->{

                fileHandler.deleteBackupDirs(finalI);
                update();
            });

            f.add(source[i]);
            f.add(deletesource[i]);

        }

        up = new JButton(arrowUp);
        up.setBounds(240,95,20,20);
        up.setBorder(null);
        up.setContentAreaFilled(false);
        up.addActionListener((e)-> scrollUp());

        down = new JButton(arrowDown);
        down.setBounds(240,130,20,20);
        down.setBorder(null);
        down.setContentAreaFilled(false);
        down.addActionListener((e)-> scrollDown());

        update();

        f.add(add);
        f.add(changeBackupDir);
        f.add(backup);
        f.add(AutoBackup);
        f.add(exit);
        f.add(destinationLabel);
        f.add(destination);
        f.add(sourceLabel);
        f.add(up);
        f.add(down);
        f.add(background);
        f.setVisible(true);
    }

    private void systemTray(){

        update();
        //Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        popup = new PopupMenu();
        trayIcon = new TrayIcon(new ImageIcon(fileHandler.getSystemTray()).getImage());
        tray = SystemTray.getSystemTray();

        // Create a pop-up menu components
        MenuItem openItem = new MenuItem("open");
        openItem.addActionListener((e)->{
            update();
            f.setVisible(true);
        });
        MenuItem addDirectoryItem = new MenuItem("Add directory");
        addDirectoryItem.addActionListener((e)-> fileHandler.setDir(selectDir()));

        MenuItem changeBackupfolderItem = new MenuItem("Change Backupfolder");
        changeBackupfolderItem.addActionListener((e)->{
            fileHandler.setBackupDir(selectDir());
            destination.setText(fileHandler.getBackupFolder());
        });
        MenuItem backupNowItem = new MenuItem("Backup now");
        backupNowItem.addActionListener((e)-> copy.backupFiles(trayIcon));

        MenuItem autobackupItem = new MenuItem();
        if(fileHandler.getAutoBackup())
            autobackupItem.setLabel("Autobackup enabled");
        else if(!fileHandler.getAutoBackup())
            autobackupItem.setLabel("Autobackup disabled");
        autobackupItem.addActionListener((e)->{
            if(fileHandler.getAutoBackup()){
                AutoBackup.setIcon(AutoBackupdisabled);
                fileHandler.setAutoBackup(false);
                autobackupItem.setLabel("Autobackup disabled");
            } else if(!fileHandler.getAutoBackup()){
                AutoBackup.setIcon(AutoBackupenabled);
                fileHandler.setAutoBackup(true);
                autobackupItem.setLabel("Autobackup enabled");
            }

        });
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener((e)-> System.exit(0));

        //Add components to pop-up menu
        popup.add(openItem);
        popup.addSeparator();
        popup.add(addDirectoryItem);
        popup.add(changeBackupfolderItem);
        popup.addSeparator();
        popup.add(backupNowItem);
        popup.addSeparator();
        popup.add(autobackupItem);
        popup.addSeparator();
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
    }

    private String selectDir(){

        fc = new JFileChooser();
        fc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        fc.showOpenDialog(null);
        return fc.getSelectedFile().toString();
    }

    private void checkAutoBackup(){

        if(fileHandler.getAutoBackup()){

            AutoBackup.setIcon(AutoBackupdisabled);
            fileHandler.setAutoBackup(false);
        }else{

            AutoBackup.setIcon(AutoBackupenabled);
            fileHandler.setAutoBackup(true);
        }
    }

    private int realSize(){
        return fileHandler.getSource().length;
    }

    private void update(){

        int k = 0;
        int n = realSize();
        int c = 0;
        boolean b = fileHandler.selectedDirFileIsEmpty();

        do{
            if(!b){
                if(!(k>=n)){
                    source[k].setText(fileHandler.getSource()[k]);
                }
                if(k>=n){
                    source[k].setText("");
                }
            }
            else{
                    source[k].setText("");
            }

            k++;
        }while(k<size);

        for(int i = 0;i<size;i++){
               if(i<page*6){
                   source[i].setVisible(false);
                   deletesource[i].setVisible(false);
               } else if(i<page*6+6){
                        if(source[i].getText().contains("\\")){
                            source[i].setBounds(280,120+(c*30),400,30);
                            deletesource[i].setBounds(740,127+(c*31),21,21);
                            source[i].setVisible(true);
                            deletesource[i].setVisible(true);
                            c++;
                        }else{
                            source[i].setVisible(false);
                            deletesource[i].setVisible(false);
                        }
                }else{
                    source[i].setVisible(false);
                    deletesource[i].setVisible(false);
                }

        }
    }

    private void scrollUp() {

        visibleMin = 0;
        visibleMax = 0;
        int n = 0;

        for (int i = 0; i < size; i++) {
            if (source[i].isVisible()) {
                if (visibleMin == 0)
                    visibleMin = i;

                if (visibleMax < i) {
                    visibleMax = i;
                }
            }
        }
        visibleMin--;

        if (page != 0){
            page--;

            for (int i = 0; i < size; i++) {
                if (!(visibleMin > 5)) {

                    if (i <= 5) {
                        source[i].setBounds(280, 120 + (i * 30), 400, 30);
                        deletesource[i].setBounds(740, 127 + (i * 31), 21, 21);
                        source[i].setVisible(true);
                        deletesource[i].setVisible(true);
                    }
                    if (i > 5) {
                        source[i].setVisible(false);
                        deletesource[i].setVisible(false);
                    }

                } else if (visibleMin > 5) {

                    if (i <= visibleMin-6) {
                        source[i].setVisible(false);
                        deletesource[i].setVisible(false);
                    } else if (i <= visibleMin) {
                        source[i].setBounds(280, 120 + (n * 30), 400, 30);
                        deletesource[i].setBounds(740, 127 + (n * 31), 21, 21);
                        source[i].setVisible(true);
                        deletesource[i].setVisible(true);
                        n++;
                    } else {
                        source[i].setVisible(false);
                        deletesource[i].setVisible(false);
                    }
                }
            }
        }
    }

    private void scrollDown() {

        visibleMin = 0;
        visibleMax = 0;
        int n = 0;

        for (int i = 0; i < size; i++) {
            if (source[i].isVisible()) {
                if (visibleMin == 0)
                    visibleMin = i;

                if (visibleMax < i) {
                    visibleMax = i;
                }
            }
        }
        visibleMin--;

        if(visibleMax+1!=realSize()&&realSize()!=0) {
            page++;

            for (int i = 0; i < size; i++) {

                if (i <= visibleMax) {
                    source[i].setVisible(false);
                    deletesource[i].setVisible(false);
                } else if (i <= visibleMax+6) {
                    if (source[i].getText().contains("\\")) {
                        source[i].setBounds(280, 120 + (n * 30), 400, 30);
                        deletesource[i].setBounds(740, 127 + (n * 31), 21, 21);
                        source[i].setVisible(true);
                        deletesource[i].setVisible(true);
                        n++;
                    } else {
                        source[i].setVisible(false);
                        deletesource[i].setVisible(false);
                    }
                } else {
                    source[i].setVisible(false);
                    deletesource[i].setVisible(false);
                }

            }
        }
    }
}