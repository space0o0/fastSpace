package space.learning.baselibrary.compress.listener;


import java.util.ArrayList;

import space.learning.baselibrary.compress.bean.Photo;

/**
 * 图片集合的压缩返回监听
 */
public interface CompressImage {

    // 开始压缩
    void compress();

    // 图片集合的压缩结果返回
    interface CompressListener {

        // 成功
        void onCompressSuccess(ArrayList<Photo> images);

        // 失败
        void onCompressFailed(ArrayList<Photo> images, String error);
    }
}
