package space.learning.baselibrary.photoCrop.conf;

public class CropConfigure {

    //裁剪图片尺寸最大宽
    private int maxWidth;

    //裁剪图片尺寸最大高
    private int maxHeight;

    //裁剪图片质量压缩比例
    private int compressQuality;

    private CropConfigure(Builder builder) {
        maxWidth = builder.maxWidth;
        maxHeight = builder.maxHeight;
        compressQuality = builder.compressQuality;
    }

    public static final class Builder {
        private int maxWidth;
        private int maxHeight;
        private int compressQuality;

        public Builder() {
        }

        public Builder maxWidth(int val) {
            maxWidth = val;
            return this;
        }

        public Builder maxHeight(int val) {
            maxHeight = val;
            return this;
        }

        public Builder compressQuality(int val) {
            compressQuality = val;
            return this;
        }


        public CropConfigure build() {
            return new CropConfigure(this);
        }
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getCompressQuality() {
        return compressQuality;
    }

    public static CropConfigure getDefault() {
        return new Builder()
                .maxWidth(1080)
                .maxHeight(1920)
                .compressQuality(90)
                .build();
    }
}
