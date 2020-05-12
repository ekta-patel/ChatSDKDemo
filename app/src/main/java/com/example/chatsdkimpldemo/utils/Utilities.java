package com.example.chatsdkimpldemo.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

    private static final int BUFFER_SIZE = 1024 * 4;

    public static File createImageFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        return image;
    }

    public static File createAudioFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String audioFileName = "3GP_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File audio = File.createTempFile(
                audioFileName,
                ".3gp",
                storageDir
        );
        return audio;
    }

    public static File createFileForExtension(Context context, String extension) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = timeStamp + "_";
        File storageDir = context.getExternalFilesDir(null);
        File f = File.createTempFile(
                fileName,
                "." + extension,
                storageDir
        );
        return f;
    }

    public static long copy(InputStream in, OutputStream out)
            throws IOException {
        final byte[] buf = new byte[BUFFER_SIZE];
        long count = 0;
        int n = 0;
        while ((n = in.read(buf)) > -1) {
            out.write(buf, 0, n);
            count += n;
        }
        return count;
    }
}
