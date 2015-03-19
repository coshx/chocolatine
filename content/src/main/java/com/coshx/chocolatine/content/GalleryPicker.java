package com.coshx.chocolatine.content;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import java.io.File;

/**
 * @class GalleryPicker
 * @brief
 */
public class GalleryPicker {
    public interface IGalleryPickerListener {
        public void onPictureSelected(File picture, String mimeType);

        public void onGalleryCancel();
    }

    private final static int PROCESS_ID = 1;
    private IGalleryPickerListener _listener;
    private Activity               _activity;

    public GalleryPicker(Activity activity, IGalleryPickerListener listener) {
        this._activity = activity;
        this._listener = listener;
    }

    public void show(String title) {
        Intent intent = new Intent();

        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        // Allow only local pictures, not ones from remote platforms
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        _activity.startActivityForResult(Intent.createChooser(intent, title), PROCESS_ID);
    }

    public void process(int requestCode, int resultCode, Intent data) {
        if (requestCode != PROCESS_ID) {
            return;
        }

        if (resultCode == Activity.RESULT_OK) {
            Cursor cursor;
            String pictureField = MediaStore.Images.Media.DATA;

            cursor = _activity.getContentResolver().query(
                data.getData(),
                new String[] { pictureField },
                null, null, null
            );

            if (!cursor.moveToFirst()) {
                cursor.close();
                _listener.onGalleryCancel();
            } else {
                File picture;
                MimeTypeMap map = MimeTypeMap.getSingleton();
                String mimeType;

                picture = new File(cursor.getString(cursor.getColumnIndex(pictureField)));
                cursor.close();

                mimeType = map.getMimeTypeFromExtension(
                    MimeTypeMap.getFileExtensionFromUrl(picture.getAbsolutePath())
                );

                _listener.onPictureSelected(picture, mimeType);
            }

        } else {
            _listener.onGalleryCancel();
        }
    }
}
