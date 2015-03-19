package com.coshx.chocolatine.media;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @class PictureEngine
 * @brief
 */
public class PictureEngine {
    public final static int ORIENTATION_DEFAULT_VALUE = -1;

    public enum Compression {
        NO_COMPRESSION(100),
        LOW(80),
        MEDIUM(60),
        HIGH(40);

        private int _value;

        Compression(int value) {
            this._value = value;
        }

        public int toInt() {
            return this._value;
        }

        @Override
        public String toString() {
            return Integer.toString(toInt());
        }
    }

    private static File _compress(Context context, Bitmap bitmap, Compression compression) {
        File output = null;

        try {
            FileOutputStream stream;

            output = File.createTempFile(
                "picture_engine_rotate", ".jpg",
                context.getCacheDir()
            );
            stream = new FileOutputStream(output);
            bitmap.compress(Bitmap.CompressFormat.JPEG, compression.toInt(), stream);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            output = null;
        } finally {
            return output;
        }
    }

    private static Bitmap _rotate(Context context, File picture) {
        int orientation, rotation;
        Bitmap bitmap = BitmapFactory.decodeFile(picture.getAbsolutePath());

        orientation = getOrientation(picture);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotation = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotation = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotation = 270;
                break;
            default:
                rotation = 0;
                break;
        }

        if (rotation != 0) {
            Matrix matrix = new Matrix();

            matrix.postRotate(rotation);
            bitmap = Bitmap.createBitmap(
                bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true
            );
        }

        return bitmap;
    }

    public static int getOrientation(File picture) {
        int value = ORIENTATION_DEFAULT_VALUE;

        try {
            ExifInterface exif = new ExifInterface(picture.getAbsolutePath());
            value = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ORIENTATION_DEFAULT_VALUE
            );
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return value;
        }
    }

    public static File rotate(Context context, File picture) {
        return _compress(
            context,
            _rotate(context, picture),
            Compression.NO_COMPRESSION
        );
    }

    public static File compress(Context context, File picture, Compression value) {
        return _compress(
            context,
            BitmapFactory.decodeFile(picture.getAbsolutePath()),
            value
        );
    }

    public static File rotateAndCompress(Context context, File picture,
                                         Compression value) {
        return _compress(
            context,
            _rotate(context, picture),
            value
        );
    }
}
