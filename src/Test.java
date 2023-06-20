import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Test {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // Underline
    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

    // Background
    public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE

    public static void main(String[] args) throws IOException {
        Main.IS_TESTING = true;
        File saveLogCloud = new File("C:\\Users\\apbro\\Documents\\Code\\Cloud Save Provider\\Cloud\\saveLog.txt");
        File saveLogLocal = new File("C:\\Users\\apbro\\Documents\\Code\\Cloud Save Provider\\Local\\saveLog.txt");
        File amongUsLocal = new File("C:\\Users\\apbro\\Documents\\Code\\Cloud Save Provider\\Local\\amongus.txt");
        File amongUsCloud = new File("C:\\Users\\apbro\\Documents\\Code\\Cloud Save Provider\\Cloud\\amongus.txt");
        System.out.println("Test Suite");
        System.out.println("==========");

        // Test 1: Testing Sync Capabilities
        System.out.println("Test 1: Test for Sync");
        FileUtils.writeStringToFile(amongUsCloud, "test", "UTF-8");
        FileUtils.writeStringToFile(saveLogCloud, "2023/06/15 20:15:18\n2023/06/15 20:15:19\n", "UTF-8");
        FileUtils.writeStringToFile(saveLogLocal, "2023/06/15 20:15:18\n", "UTF-8");
        Main.main(new String[]{"sync", "C:\\Users\\apbro\\Documents\\Code\\Cloud Save Provider\\Cloud", "C:\\Users\\apbro\\Documents\\Code\\Cloud Save Provider\\Local"});
        if (FileUtils.readFileToString(amongUsLocal, "UTF-8").equalsIgnoreCase("test"))
            System.out.println("Test 1: " + GREEN_BACKGROUND + "Passed" + RESET);
        else
            System.out.println("Test 1: " + RED_BACKGROUND + "Failed" + RESET);

        // Test 2: Testing if Upload works
        System.out.println("Test 2: Test for Upload");
        FileUtils.writeStringToFile(amongUsLocal, "testbutbetter :)", "UTF-8");
        Main.main(new String[]{"save", "C:\\Users\\apbro\\Documents\\Code\\Cloud Save Provider\\Cloud", "C:\\Users\\apbro\\Documents\\Code\\Cloud Save Provider\\Local"});
        Main.main(new String[]{"upload", "C:\\Users\\apbro\\Documents\\Code\\Cloud Save Provider\\Cloud", "C:\\Users\\apbro\\Documents\\Code\\Cloud Save Provider\\Local"});
        if (FileUtils.readFileToString(amongUsCloud, "UTF-8").equalsIgnoreCase("testbutbetter :)"))
            System.out.println("Test 2: " + GREEN_BACKGROUND + "Passed" + RESET);
        else
            System.out.println("Test 2: " + RED_BACKGROUND + "Failed" + RESET);

        // Test 3: Testing if conflicts are detectable (Syncing)
        System.out.println("Test 3: Test for Conflict Detection when Syncing");
        FileUtils.writeStringToFile(amongUsCloud, "test (cloud)", "UTF-8");
        FileUtils.writeStringToFile(saveLogCloud, "2023/06/15 20:15:18\n", "UTF-8");
        FileUtils.writeStringToFile(amongUsLocal, "test (local)", "UTF-8");
        Main.main(new String[]{"save", "C:\\Users\\apbro\\Documents\\Code\\Cloud Save Provider\\Cloud", "C:\\Users\\apbro\\Documents\\Code\\Cloud Save Provider\\Local"});
        Main.main(new String[]{"sync", "C:\\Users\\apbro\\Documents\\Code\\Cloud Save Provider\\Cloud", "C:\\Users\\apbro\\Documents\\Code\\Cloud Save Provider\\Local"});
        if (FileUtils.readFileToString(amongUsLocal, "UTF-8").equalsIgnoreCase("test (local)"))
            System.out.println("Test 3: " + GREEN_BACKGROUND + "Passed" + RESET);
        else
            System.out.println("Test 3: " + RED_BACKGROUND + "Failed" + RESET);

        // Test 4: Testing if conflicts are detectable (Upload)
        System.out.println("Test 4: Test for Conflict Detection when Uploading");
        FileUtils.writeStringToFile(amongUsCloud, "test (cloud)", "UTF-8");
        FileUtils.writeStringToFile(amongUsLocal, "test (local)", "UTF-8");
        Main.main(new String[]{"upload", "C:\\Users\\apbro\\Documents\\Code\\Cloud Save Provider\\Cloud", "C:\\Users\\apbro\\Documents\\Code\\Cloud Save Provider\\Local"});
        if (FileUtils.readFileToString(amongUsCloud, "UTF-8").equalsIgnoreCase("test (cloud)"))
            System.out.println("Test 4: " + GREEN_BACKGROUND + "Passed" + RESET);
        else
            System.out.println("Test 4: " + RED_BACKGROUND + "Failed" + RESET);

        // Test 5: Testing if conflicts are resolvable (Cloud -> Local)
        System.out.println("Test 5: Test for Conflict Resolution (Cloud -> Local)");
        FileUtils.writeStringToFile(amongUsCloud, "test (cloud)", "UTF-8");
        FileUtils.writeStringToFile(amongUsLocal, "test (local)", "UTF-8");
        Main.main(new String[]{"resolve", "local", "C:\\Users\\apbro\\Documents\\Code\\Cloud Save Provider\\Cloud", "C:\\Users\\apbro\\Documents\\Code\\Cloud Save Provider\\Local"});
        if (FileUtils.readFileToString(amongUsCloud, "UTF-8").equalsIgnoreCase("test (local)"))
            System.out.println("Test 5: " + GREEN_BACKGROUND + "Passed" + RESET);
        else
            System.out.println("Test 5: " + RED_BACKGROUND + "Failed" + RESET);

        // Test 6: Testing if conflicts are resolvable (Local -> Cloud)
        System.out.println("Test 6: Test for Conflict Resolution (Local -> Cloud)");
        FileUtils.writeStringToFile(amongUsCloud, "test (cloud)", "UTF-8");
        FileUtils.writeStringToFile(amongUsLocal, "test (local)", "UTF-8");
        Main.main(new String[]{"resolve", "cloud", "C:\\Users\\apbro\\Documents\\Code\\Cloud Save Provider\\Cloud", "C:\\Users\\apbro\\Documents\\Code\\Cloud Save Provider\\Local"});
        if (FileUtils.readFileToString(amongUsLocal, "UTF-8").equalsIgnoreCase("test (cloud)"))
            System.out.println("Test 6: " + GREEN_BACKGROUND + "Passed" + RESET);
        else
            System.out.println("Test 6: " + RED_BACKGROUND + "Failed" + RESET);

        System.out.println("Test suite completed.");
    }
}
