package space.learning.baselib.useCompress.picture;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;



public class PhotoUtils {


    /**
     * 把相册的content://图片路径转换成绝对路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getImagePathFromURI(Context context, Uri uri) {

        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        String path = null;
        if (cursor != null) {
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
            cursor.close();

            cursor = context.getContentResolver().query(
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
            if (cursor != null) {
                cursor.moveToFirst();
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();
            }
        }
        return path;
    }

//    public static void ucrop(Uri uri, Fragment fragment) {
//
//        UCrop.Options options = new UCrop.Options();
//
//        UCrop.of(uri, null)
//                .withAspectRatio(16f, 9f)
////                .withOptions(options)
//                .withMaxResultSize(1080, 1920)
//                .start(fragment.getActivity(), fragment);
//    }


}
