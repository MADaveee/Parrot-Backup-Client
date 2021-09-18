package FileHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private final String username = System.getProperty("user.name");
    public final String dirPath = "C:\\Users\\"+username+"\\Documents\\Parrot-Backup-Client";

    private final File dir = new File(dirPath);
    private final File dirImage = new File(dirPath+"\\images");

    private final File backupDir = new File(dir+"\\backupDir.txt");
    private final File selectedDirs = new File(dir+"\\selectedDirs.txt");
    private final File lastBackup = new File(dir+"\\lastBackup.txt");
    private final File autoBackup = new File(dir+"\\autoBackup.txt");
    private final File installed = new File(dir+"\\installed.txt");

    private final File systemTray = new File(dirPath+"\\images\\systemTray.png");
    private final File backgroundIcon = new File(dirPath+"\\images\\background.png");
    private final File cross = new File(dirPath+"\\images\\Cross.png");
    private final File addDirectoryIcon = new File(dirPath+"\\images\\AddDirectory.png");
    private final File changeBackupdirIcon = new File(dirPath+"\\images\\changeBackupFolder.png");
    private final File backupIcon = new File(dirPath+"\\images\\backupNow.png");
    private final File AutoBackupdisabled = new File(dirPath+"\\images\\autobackupdisabled.png");
    private final File AutoBackupenabled = new File(dirPath+"\\images\\autobackupenabled.png");
    private final File exitIcon = new File(dirPath+"\\images\\exit.png");
    private final File arrowUp = new File(dirPath+"\\images\\Arrowup.png");
    private final File arrowDown = new File(dirPath+"\\images\\Arrowdown.png");
    public FileHandler(){

        initDir();
        initFiles();
    }

    private void initDir(){

        if(dir.exists())
            System.out.println("dir exists");
        else
            dir.mkdir();


        if(dirImage.exists())
            System.out.println("dirImage exists");
        else
            dirImage.mkdir();
    }

    private void initFiles(){

        if(backupDir.exists())
            System.out.println("backupDir exists");
        else {
            try {
                backupDir.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if(selectedDirs.exists())
            System.out.println("selectedDirs exists");
        else{
            try {
                selectedDirs.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if(lastBackup.exists())
            System.out.println("lastBackup exists");
        else{
            try {
                lastBackup.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if(autoBackup.exists())
            System.out.println("autoBackup exists");
        else{
            try {
                autoBackup.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if(installed.exists())
            System.out.println("installed exists");
        else{
            try {
                installed.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDir(String dirPath){

        try {
            List<String> list = Files.readAllLines(selectedDirs.toPath());
            if(!list.contains(dirPath)){
                list.add(dirPath);
                Files.write(selectedDirs.toPath(), list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBackupDir(String dirPath){

        try {
            List<String> list = new ArrayList<>();
            if(!list.contains(dirPath)){
                list.add(dirPath);
                Files.write(backupDir.toPath(), list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBackupFolder(){

        try {
            List<String> list = Files.readAllLines(backupDir.toPath());
            if(list.isEmpty())
                return null;
            return list.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String[] getSource(){

        try {
            List<String> list = Files.readAllLines(selectedDirs.toPath());
            String[] ret = new String[list.size()];

            for(int i = 0;i<list.size();i++)
                ret[i] = list.get(i);
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setLastBackup(LocalDate localDate){

        try {
            List<String> list = new ArrayList<>();
            list.add(localDate.toString());
            Files.write(lastBackup.toPath(), list);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public LocalDate getLastBackup(){

        try {
            List<String> list = Files.readAllLines(lastBackup.toPath());
            if(list.isEmpty()){

                list.add(LocalDate.now().minusDays(1).toString());
                Files.write(lastBackup.toPath(), list);
                getLastBackup();
            }

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(list.get(0), dateTimeFormatter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean getAutoBackup(){
        try {
            List<String> list = Files.readAllLines(autoBackup.toPath());
            if(list.isEmpty()){
                list.add("true");
                Files.write(autoBackup.toPath(), list);
                getLastBackup();
            }
            return Boolean.parseBoolean(list.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setAutoBackup(Boolean b){

        try {
            List<String> list = new ArrayList<>();
            list.add(b.toString());
            Files.write(autoBackup.toPath(), list);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Boolean selectedDirFileIsEmpty(){
        try {
            List<String> list = Files.readAllLines(selectedDirs.toPath());
            return list.isEmpty();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteBackupDirs(int n){
        try {
            List<String> list = Files.readAllLines(selectedDirs.toPath());
            list.remove(n);
            Files.write(selectedDirs.toPath(), list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInstalled(){

        try {
            List<String> list = new ArrayList<>();
            list.add("is Installed");
            Files.write(installed.toPath(), list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getInstalled(){
        try {
            List<String> list = Files.readAllLines(installed.toPath());
            if(list.isEmpty())
                return null;

            return list.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getSystemTray(){
        return systemTray.toString();
    }

    public File getBackupDir() {
        return backupDir;
    }

    public File getSelectedDirs() {
        return selectedDirs;
    }
}
