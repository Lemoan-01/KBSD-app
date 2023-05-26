import java.net.MalformedURLException;
import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        try {
            new GuiLooks();

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }


    }
//        new saveMonitoring(98.78, "Werkt het vandaag ook?");
}