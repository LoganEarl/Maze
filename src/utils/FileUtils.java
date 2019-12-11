package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtils {
    public static final String DATA_DIRECTORY = System.getenv("APPDATA").replace("\\", "/") + "/Dabuggers Maze/";
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void copyFile(File sourceFile, File destFile) {
        try {
            if (!destFile.exists()) {
                destFile.createNewFile();
            }

            try (FileChannel source = new FileInputStream(sourceFile).getChannel();
                 FileChannel destination = new FileOutputStream(destFile).getChannel()) {
                destination.transferFrom(source, 0, source.size());
            }
        }catch (IOException ignored){}
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteFile(File toDelete){
        try {
            toDelete.delete();
        }catch(Exception ignored){}
    }
}
