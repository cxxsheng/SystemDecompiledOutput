package android.graphics;

/* loaded from: classes.dex */
public class PixelFormat {

    @java.lang.Deprecated
    public static final int A_8 = 8;
    public static final int HSV_888 = 55;

    @java.lang.Deprecated
    public static final int JPEG = 256;

    @java.lang.Deprecated
    public static final int LA_88 = 10;

    @java.lang.Deprecated
    public static final int L_8 = 9;
    public static final int OPAQUE = -1;
    public static final int RGBA_1010102 = 43;

    @java.lang.Deprecated
    public static final int RGBA_4444 = 7;

    @java.lang.Deprecated
    public static final int RGBA_5551 = 6;
    public static final int RGBA_8888 = 1;
    public static final int RGBA_F16 = 22;
    public static final int RGBX_8888 = 2;

    @java.lang.Deprecated
    public static final int RGB_332 = 11;
    public static final int RGB_565 = 4;
    public static final int RGB_888 = 3;
    public static final int R_8 = 56;
    public static final int TRANSLUCENT = -3;
    public static final int TRANSPARENT = -2;
    public static final int UNKNOWN = 0;

    @java.lang.Deprecated
    public static final int YCbCr_420_SP = 17;

    @java.lang.Deprecated
    public static final int YCbCr_422_I = 20;

    @java.lang.Deprecated
    public static final int YCbCr_422_SP = 16;
    public int bitsPerPixel;
    public int bytesPerPixel;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Format {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Opacity {
    }

    public static void getPixelFormatInfo(int i, android.graphics.PixelFormat pixelFormat) {
        switch (i) {
            case 1:
            case 2:
            case 43:
                pixelFormat.bitsPerPixel = 32;
                pixelFormat.bytesPerPixel = 4;
                return;
            case 3:
            case 55:
                pixelFormat.bitsPerPixel = 24;
                pixelFormat.bytesPerPixel = 3;
                return;
            case 4:
            case 6:
            case 7:
            case 10:
                pixelFormat.bitsPerPixel = 16;
                pixelFormat.bytesPerPixel = 2;
                return;
            case 8:
            case 9:
            case 11:
                pixelFormat.bitsPerPixel = 8;
                pixelFormat.bytesPerPixel = 1;
                return;
            case 16:
            case 20:
                pixelFormat.bitsPerPixel = 16;
                pixelFormat.bytesPerPixel = 1;
                return;
            case 17:
                pixelFormat.bitsPerPixel = 12;
                pixelFormat.bytesPerPixel = 1;
                return;
            case 22:
                pixelFormat.bitsPerPixel = 64;
                pixelFormat.bytesPerPixel = 8;
                return;
            case 56:
                pixelFormat.bitsPerPixel = 8;
                pixelFormat.bytesPerPixel = 1;
                return;
            default:
                throw new java.lang.IllegalArgumentException("unknown pixel format " + i);
        }
    }

    public static boolean formatHasAlpha(int i) {
        switch (i) {
            case -3:
            case -2:
            case 1:
            case 6:
            case 7:
            case 8:
            case 10:
            case 22:
            case 43:
                return true;
            default:
                return false;
        }
    }

    public static boolean isPublicFormat(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 22:
            case 43:
                return true;
            default:
                return false;
        }
    }

    public static java.lang.String formatToString(int i) {
        switch (i) {
            case -3:
                return "TRANSLUCENT";
            case -2:
                return "TRANSPARENT";
            case 0:
                return "UNKNOWN";
            case 1:
                return "RGBA_8888";
            case 2:
                return "RGBX_8888";
            case 3:
                return "RGB_888";
            case 4:
                return "RGB_565";
            case 6:
                return "RGBA_5551";
            case 7:
                return "RGBA_4444";
            case 8:
                return "A_8";
            case 9:
                return "L_8";
            case 10:
                return "LA_88";
            case 11:
                return "RGB_332";
            case 16:
                return "YCbCr_422_SP";
            case 17:
                return "YCbCr_420_SP";
            case 20:
                return "YCbCr_422_I";
            case 22:
                return "RGBA_F16";
            case 43:
                return "RGBA_1010102";
            case 55:
                return "HSV_888";
            case 56:
                return "R_8";
            case 256:
                return "JPEG";
            default:
                return java.lang.Integer.toString(i);
        }
    }
}
