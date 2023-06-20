import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static boolean IS_TESTING = false;
    public static boolean updateSaveLog(File mcSavePath) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        File saveLog = new File(mcSavePath + "/saveLog.txt");
        if (!saveLog.exists()) {
            saveLog.createNewFile();
        }
        String saveLogContents = FileUtils.readFileToString(saveLog, "UTF-8");
        assert saveLogContents != null;
        saveLogContents += "U: " + dateFormat.format(date) + "\n";
        FileUtils.writeStringToFile(saveLog, saveLogContents, "UTF-8");
        return true;
    }

    public static boolean approveUpstream(File mcSavePath) throws IOException {
        Date date = new Date();
        File saveLog = new File(mcSavePath + "/saveLog.txt");
        if (!saveLog.exists()) {
            saveLog.createNewFile();
        }
        String saveLogContents = FileUtils.readFileToString(saveLog, "UTF-8");
        assert saveLogContents != null;
        saveLogContents = saveLogContents.replaceAll("U: ", "");
        System.out.println(saveLogContents);
        System.out.println("Successfully approved upstream save.");
        FileUtils.writeStringToFile(saveLog, saveLogContents, "UTF-8");
        return true;
    }

    public static Object[] isThereSaveConflict(String saveLogLocal, String saveLogCloud, boolean isSync) {
        if (saveLogLocal.isEmpty() && saveLogCloud.isEmpty())
            return null;
        LinkedList<String> saveLogLinesLocal = new LinkedList<>(Arrays.asList(saveLogLocal.split("\n")));
        // reverse the list so that the newest save is at the top
        LinkedList<String> saveLogLinesCloud = new LinkedList<>(Arrays.asList(saveLogCloud.split("\n")));
        // reverse the list so that the newest save is at the top
        for (String saveLogLineCloud : saveLogLinesCloud) {
            // if true, then cloud save is newer than the local save
            if (saveLogLinesCloud.indexOf(saveLogLineCloud) + 1 <= saveLogLinesLocal.size() && !saveLogLinesLocal.get(saveLogLinesCloud.indexOf(saveLogLineCloud)).equalsIgnoreCase(saveLogLineCloud)) {
                return new Object[]{ true, saveLogLineCloud, saveLogLinesLocal.get(saveLogLinesCloud.indexOf(saveLogLineCloud)), "cloud" };
            }
            else if (saveLogLinesCloud.indexOf(saveLogLineCloud) + 1 > saveLogLinesLocal.size()) {
                if (isSync) continue;
                return new Object[]{ true, saveLogLineCloud, "???", "cloud" };
            }
        }
        for (String saveLogLineLocal : saveLogLinesLocal) {
            // if true, then local save is newer than the cloud save
            if (saveLogLineLocal.startsWith("U: ") && !isSync) continue;
            if (saveLogLinesLocal.indexOf(saveLogLineLocal) + 1 <= saveLogLinesCloud.size() && !saveLogLinesCloud.get(saveLogLinesLocal.indexOf(saveLogLineLocal)).equalsIgnoreCase(saveLogLineLocal)) {
                return new Object[]{ true, saveLogLinesCloud.get(saveLogLinesLocal.indexOf(saveLogLineLocal)), saveLogLineLocal, "local" };
            }
            else if (saveLogLinesLocal.indexOf(saveLogLineLocal) + 1 > saveLogLinesCloud.size()) {
                return new Object[]{ true, "????", saveLogLineLocal, "local" };
            }
        }


        return null;
    }

    public static void main(String[] args) throws IOException {
        // either a "sync" or "upload"
        String typeOfCloudSave = args[0];
        if (typeOfCloudSave.equalsIgnoreCase("help")) {
            System.out.println("Usage: java -jar cloud-save.jar <sync|upload|resolve|save> <?cloud|?local> <cloud save path> <minecraft save path>");
            System.out.println("? = Only if \"resolve\" is the command.");
            return;
        }
        if (typeOfCloudSave.equalsIgnoreCase("resolve")) {
            switch (args[1]) {
                case "cloud":
                    System.out.println("Overriding local save and pulling from cloud save...");
                    FileUtils.copyDirectory(new File(args[2]), new File(args[3]));
                    System.out.println("Successfully copied cloud save to local save.");
                    return;
                case "local":
                    System.out.println("Overriding cloud save and pulling from local save...");
                    approveUpstream(new File(args[2]));
                    FileUtils.copyDirectory(new File(args[3]), new File(args[2]));
                    System.out.println("Successfully copied local save to cloud save.");
                    return;
                case "help":
                    System.out.println("Usage: java -jar cloud-save.jar resolve <cloud|local> <cloud save path> <minecraft save path>");
                    return;
                default:
                    System.out.println("Invalid argument for resolve.");
                    return;
            }
        }
        // example: G:\Minecraft\CloudSaves\
        String cloudSavePath = args[1];
        // example: %appdata%\.minecraft\saves\world\
        String mcSavePath = args[2];

        File minecraftWorld = new File(mcSavePath);
        File cloudSave = new File(cloudSavePath);
        if (!minecraftWorld.exists()) {
            System.out.println("Minecraft world does not exist.");
            if (!IS_TESTING)
                System.exit(1);
            else return;
        }
        if (!cloudSave.exists()) {
            System.out.println("Cloud save does not exist.");
            if (!IS_TESTING)
                System.exit(1);
            else return;
        }
        File localSaveLog = new File(mcSavePath + "/saveLog.txt");
        File cloudSaveLog = new File(cloudSavePath + "/saveLog.txt");
        if (!localSaveLog.exists()) {
            // first sync, so we need to copy the local save to the cloud save
            System.out.println("Initializing Cloud Save...");
            localSaveLog.createNewFile();
            FileUtils.writeStringToFile(localSaveLog, "U: " + new Date().toString() + " -- Initial Creation\n", "UTF-8");
            approveUpstream(minecraftWorld);
            FileUtils.copyDirectory(minecraftWorld, cloudSave);
        }
        if (!cloudSaveLog.exists()) {
            // first sync, so we need to copy the local save to the cloud save
            System.out.println("Reinitializing Cloud Save...");
            localSaveLog.createNewFile();
            FileUtils.copyDirectory(minecraftWorld, cloudSave);
        }

        String saveLogLocal = FileUtils.readFileToString(localSaveLog, "UTF-8");
        String saveLogCloud = FileUtils.readFileToString(cloudSaveLog, "UTF-8");
        assert saveLogCloud != null;
        assert saveLogLocal != null;

        if (typeOfCloudSave.equals("sync")) {
            Object[] saveConflict = isThereSaveConflict(saveLogLocal, saveLogCloud, true);
            shouldResolveConflict(cloudSave, minecraftWorld, saveConflict, false);
            System.out.println("Successfully synced save.");
        }
        else if (typeOfCloudSave.equals("upload")) {
            Object[] saveConflict = isThereSaveConflict(saveLogLocal, saveLogCloud, false);
            shouldResolveConflict(minecraftWorld, cloudSave, saveConflict, true);
            System.out.println("Successfully uploaded save.");
        }
        else if (typeOfCloudSave.equals("save")) {
            updateSaveLog(minecraftWorld);
            System.out.println("Successfully saved save log.");
        }
        else {
            System.out.println("Invalid type of cloud save.");
            if (!IS_TESTING) {
                System.exit(1);
            }
        }
    }

    private static void shouldResolveConflict(File minecraftWorld, File cloudSave, Object[] saveConflict, boolean shouldApproveUpstream) throws IOException {
        if (saveConflict != null) {
            String saveConflictCloud = (String) saveConflict[1];
            String saveConflictLocal = (String) saveConflict[2];

            String whereConflictOriginates = (String) saveConflict[3];
            System.out.println("Save conflict detected.");
            System.out.println("Local Date: " + saveConflictLocal.replace("U: ", "(Unapproved) "));
            System.out.println("Cloud Date: " + saveConflictCloud);
            if (!IS_TESTING) {
                System.exit(1);
            }
            else {
                return;
            }
        }
        approveUpstream(minecraftWorld);
        FileUtils.copyDirectory(minecraftWorld, cloudSave);
    }
}