import Backup.Copy;
import FileHandler.FileHandler;
import Timer.TimeHandler;
import Window.Window;
import FileHandler.Installer;

public class Main {

    public static void main(String[] args) {

        FileHandler fileHandler = new FileHandler();

        Installer installer = new Installer(fileHandler);

        Copy copy = new Copy(fileHandler);

        Window window = new Window(copy, fileHandler);

        TimeHandler timeHandler = new TimeHandler(fileHandler, copy, window);
    }

}
