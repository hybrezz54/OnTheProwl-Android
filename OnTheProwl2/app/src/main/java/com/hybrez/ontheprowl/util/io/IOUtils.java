package com.hybrez.ontheprowl.util.io;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Class that includes helper methods
 * for input and output file operations
 *
 * @author Hamzah Aslam
 */
public class IOUtils {

    /**
     * Enumerator that specifies whether to
     * read/write to the external or internal app
     * directory
     */
    public enum Directory {
        kInternalOnly,
        kExternalOnly,
        kInternalExternal,
        kExternalInternal,
        kCache
    }

    /**
     * Checks whether application storage is
     * read-only
     *
     * @return Whether or not storage is read-only
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);

    }

    /**
     * Checks whether application storage is
     * available to write to or read from
     *
     * @return Whether or not storage is available
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * Checks whether file exists
     *
     * @param file The path of the file
     * @return True if the file exists and false otherwise
     */
    public static boolean doesFileExist(String file) {
        return doesFileExist(new File(file));
    }

    /**
     * Checks whether file exists
     *
     * @param file The object of the file to check
     * @return True if the file exists and false otherwise
     */
    public static boolean doesFileExist(File file) {
        return file.exists();
    }

    /**
     * Get the specified internal or external storage
     * directory of the application
     *
     * @param context The context of the application
     * @param dir The location of the directory
     * @return The file object of the directory
     */
    public static File getStorageDirectory(Context context, Directory dir) {
        switch(dir) {
            case kInternalOnly:
                return context.getFilesDir();
            case kInternalExternal:
                if (checkAvailableInternalMemory()) return context.getFilesDir();
            case kExternalOnly:
                return context.getExternalFilesDir(null);
            case kExternalInternal:
                if (isExternalStorageWritable()) return context.getExternalFilesDir(null);
                else return context.getFilesDir();
            case kCache:
                return context.getCacheDir();
        }

        return null;
    }

    /**
     * Create a directory in a specified directory
     *
     * @param file The location of the directory
     * @param dir The name of the directory to create
     * @return True if the directory was successfully created
     *         and false otherwise
     */
    public static boolean makeDirectory(File file, String dir) {
        File f = new File(file, dir);
        return f.mkdirs();
    }

    /**
     * Write data out to a file
     *
     * @param file The object of the file to write
     * @param data The data to write in a String representation
     */
    public static boolean write(File file, String data) {
        return writeBytes(file, data.getBytes());
    }

    /**
     * Write data out to a file
     *
     * @param file The path of the file to write to
     * @param data The bytes of the data to write
     */
    public static boolean write(String file, String data) {
        return writeBytes(file, data.getBytes());
    }

    /**
     * Write data out to a file
     *
     * @param context The context of the application
     * @param dir The location of the directory
     * @param file The path of the file to write to
     * @param data The bytes of the data to write
     */
    public static boolean write(Context context, Directory dir, String file, String data) {
        return writeBytes(new File(getStorageDirectory(context, dir), file),
                data.getBytes());
    }

    /**
     * Write bytes of data out to a file
     *
     * @param file The object of the file to write
     * @param data The bytes of the data to write
     */
    public static boolean writeBytes(File file, byte[] data) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Write bytes of data out to a file
     *
     * @param file The path of the file to write to
     * @param data The bytes of the data to write
     */
    public static boolean writeBytes(String file, byte[] data) {
            return writeBytes(new File(file), data);
    }

    /**
     * Read data from a file by returning its contents as a string
     *
     * @param file The object of the file to read
     * @return All of the lines from the file as a string
     */
    public static String read(File file) {
        // init variables
        String string = "";

        try {
            // init variables
            FileInputStream fin = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
            String line;

            // read the lines and append them
            while ((line = reader.readLine()) != null) {
                string += line + System.getProperty("line.separator");
            }

            // close the reader
            reader.close();
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // return the string
        return string;
    }

    /**
     * Read data from a file by returning its contents as a string
     *
     * @param file The path of the file to read
     * @return All of the lines from the file as a string
     */
    public static String read(String file) {
        return read(new File(file));
    }

    /**
     * Read data from a file by returning its contents as a string
     *
     * @param context The context of the application
     * @param dir The location of the directory
     * @param file The path of the file to write to
     * @return All of the lines from the file as a string
     */
    public static String read(Context context, Directory dir, String file) {
        return read(new File(getStorageDirectory(context, dir), file));
    }

    /**
     * Check if at least 1 MB of internal memory is available
     *
     * @return True if internal storage available and
     *         false otherwise
     */
    public static boolean checkAvailableInternalMemory() {
        // init variables
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize, availableBlocks;

        // get available space
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            blockSize = stat.getBlockSize();
            availableBlocks = stat.getAvailableBlocks();
        }

        // compute available space
        long bytes = availableBlocks * blockSize;

        return bytes >= (1000000);
    }

}