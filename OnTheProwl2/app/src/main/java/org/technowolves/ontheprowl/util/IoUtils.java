package org.technowolves.ontheprowl.util;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Useful methods to store
 * and read data from files
 */
public class IoUtils {

    /**
     * Checks whether application storage is
     * read-only
     *
     * @return Whether or not storage is read-only
     */
    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether application storage is
     * available to write to or read from
     *
     * @return Whether or not storage is available
     */
    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param context
     * @param fileDir
     * @param fileName
     * @param out
     */
    public static void writeBytestoFile(Context context, String fileDir, String fileName,
                                        byte[] out) {
        try {
            String path = getStorageDir(context, fileDir).getPath()
                    + File.separator + fileName;

            FileOutputStream fos = new FileOutputStream(path);
            fos.write(out);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the lines from the file
     *
     * @param context Context of the application
     * @param fileDir The directory of the file
     * @param fileName The name of the file
     * @return All of the lines from the file
     */
    public static String readLinesFromFile(Context context, String fileDir, String fileName) {
        // init variables
        String string = "";
        String line;

        // try reading lines from the file
        try {
            String path = getStorageDir(context, fileDir).getPath()
                    + File.separator + fileName;

            // read the lines and append them
            FileInputStream fin = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
            while ((line = reader.readLine()) != null) {
                string += line + System.getProperty("line.separator");
            }
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return string;
    }

    /**
     * Checks whether file exists
     *
     * @param context Context of application
     * @param fileDir Directory where file is stored
     * @param fileName Name of file
     * @return Whether or not file exists
     */
    public static boolean doesFileExist(Context context, String fileDir, String fileName) {
        String path = getStorageDir(context, fileDir).getPath()
                + File.separator + fileName;
        File file = new File(path);
        return file.exists();
    }

    /**
     * Get the File of the specified directory
     *
     * @param context Context of application
     * @param fileDir Directory where file is stored
     * @return The file of the directory
     */
    private static File getStorageDir(Context context, String fileDir) {
        // Get the directory for the user's public pictures directory.
        File file = new File(context.getExternalFilesDir(null),
                fileDir);
        file.mkdirs();

        return file;
    }

}
