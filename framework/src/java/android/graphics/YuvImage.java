package android.graphics;

/* loaded from: classes.dex */
public class YuvImage {
    private static final int WORKING_COMPRESS_STORAGE = 4096;
    private static final java.lang.String[] sSupportedFormats = {"NV21", "YUY2", "YCBCR_P010", "YUV_420_888"};
    private static final android.graphics.ColorSpace.Named[] sSupportedJpegRHdrColorSpaces = {android.graphics.ColorSpace.Named.BT2020_HLG, android.graphics.ColorSpace.Named.BT2020_PQ};
    private static final android.graphics.ColorSpace.Named[] sSupportedJpegRSdrColorSpaces = {android.graphics.ColorSpace.Named.SRGB, android.graphics.ColorSpace.Named.DISPLAY_P3};
    private android.graphics.ColorSpace mColorSpace;
    private byte[] mData;
    private int mFormat;
    private int mHeight;
    private int[] mStrides;
    private int mWidth;

    private static native boolean nativeCompressToJpeg(byte[] bArr, int i, int i2, int i3, int[] iArr, int[] iArr2, int i4, java.io.OutputStream outputStream, byte[] bArr2);

    private static native boolean nativeCompressToJpegR(byte[] bArr, int i, byte[] bArr2, int i2, int i3, int i4, int i5, java.io.OutputStream outputStream, byte[] bArr3, byte[] bArr4, int[] iArr, int[] iArr2);

    private static java.lang.String printSupportedFormats() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < sSupportedFormats.length; i++) {
            sb.append(sSupportedFormats[i]);
            if (i != sSupportedFormats.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private static java.lang.String printSupportedJpegRColorSpaces(boolean z) {
        android.graphics.ColorSpace.Named[] namedArr = z ? sSupportedJpegRHdrColorSpaces : sSupportedJpegRSdrColorSpaces;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < namedArr.length; i++) {
            sb.append(android.graphics.ColorSpace.get(namedArr[i]).getName());
            if (i != namedArr.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private static boolean isSupportedJpegRColorSpace(boolean z, int i) {
        for (android.graphics.ColorSpace.Named named : z ? sSupportedJpegRHdrColorSpaces : sSupportedJpegRSdrColorSpaces) {
            if (named.ordinal() == i) {
                return true;
            }
        }
        return false;
    }

    public YuvImage(byte[] bArr, int i, int i2, int i3, int[] iArr) {
        this(bArr, i, i2, i3, iArr, android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB));
    }

    public YuvImage(byte[] bArr, int i, int i2, int i3, int[] iArr, android.graphics.ColorSpace colorSpace) {
        if (i != 17 && i != 20 && i != 54 && i != 35) {
            throw new java.lang.IllegalArgumentException("only supports the following ImageFormat:" + printSupportedFormats());
        }
        if (i2 <= 0 || i3 <= 0) {
            throw new java.lang.IllegalArgumentException("width and height must large than 0");
        }
        if (bArr == null) {
            throw new java.lang.IllegalArgumentException("yuv cannot be null");
        }
        if (colorSpace == null) {
            throw new java.lang.IllegalArgumentException("ColorSpace cannot be null");
        }
        if (iArr == null) {
            this.mStrides = calculateStrides(i2, i);
        } else {
            this.mStrides = iArr;
        }
        this.mData = bArr;
        this.mFormat = i;
        this.mWidth = i2;
        this.mHeight = i3;
        this.mColorSpace = colorSpace;
    }

    public boolean compressToJpeg(android.graphics.Rect rect, int i, java.io.OutputStream outputStream) {
        if (this.mFormat != 17 && this.mFormat != 20) {
            throw new java.lang.IllegalArgumentException("Only ImageFormat.NV21 and ImageFormat.YUY2 are supported.");
        }
        if (this.mColorSpace.getId() != android.graphics.ColorSpace.Named.SRGB.ordinal()) {
            throw new java.lang.IllegalArgumentException("Only SRGB color space is supported.");
        }
        if (!new android.graphics.Rect(0, 0, this.mWidth, this.mHeight).contains(rect)) {
            throw new java.lang.IllegalArgumentException("rectangle is not inside the image");
        }
        if (i < 0 || i > 100) {
            throw new java.lang.IllegalArgumentException("quality must be 0..100");
        }
        if (outputStream == null) {
            throw new java.lang.IllegalArgumentException("stream cannot be null");
        }
        adjustRectangle(rect);
        return nativeCompressToJpeg(this.mData, this.mFormat, rect.width(), rect.height(), calculateOffsets(rect.left, rect.top), this.mStrides, i, outputStream, new byte[4096]);
    }

    public boolean compressToJpegR(android.graphics.YuvImage yuvImage, int i, java.io.OutputStream outputStream) {
        return compressToJpegR(yuvImage, i, outputStream, new byte[0]);
    }

    public boolean compressToJpegR(android.graphics.YuvImage yuvImage, int i, java.io.OutputStream outputStream, byte[] bArr) {
        if (yuvImage == null) {
            throw new java.lang.IllegalArgumentException("SDR input cannot be null");
        }
        if (this.mData.length == 0 || yuvImage.getYuvData().length == 0) {
            throw new java.lang.IllegalArgumentException("Input images cannot be empty");
        }
        if (this.mFormat == 54 && yuvImage.getYuvFormat() == 35) {
            if (yuvImage.getWidth() != this.mWidth || yuvImage.getHeight() != this.mHeight) {
                throw new java.lang.IllegalArgumentException("HDR and SDR resolution mismatch");
            }
            if (i < 0 || i > 100) {
                throw new java.lang.IllegalArgumentException("quality must be 0..100");
            }
            if (outputStream == null) {
                throw new java.lang.IllegalArgumentException("stream cannot be null");
            }
            if (!isSupportedJpegRColorSpace(true, this.mColorSpace.getId()) || !isSupportedJpegRColorSpace(false, yuvImage.getColorSpace().getId())) {
                throw new java.lang.IllegalArgumentException("Not supported color space. SDR only supports: " + printSupportedJpegRColorSpaces(false) + "HDR only supports: " + printSupportedJpegRColorSpaces(true));
            }
            return nativeCompressToJpegR(this.mData, this.mColorSpace.getDataSpace(), yuvImage.getYuvData(), yuvImage.getColorSpace().getDataSpace(), this.mWidth, this.mHeight, i, outputStream, new byte[4096], bArr, this.mStrides, yuvImage.getStrides());
        }
        throw new java.lang.IllegalArgumentException("only support ImageFormat.YCBCR_P010 and ImageFormat.YUV_420_888");
    }

    public byte[] getYuvData() {
        return this.mData;
    }

    public int getYuvFormat() {
        return this.mFormat;
    }

    public int[] getStrides() {
        return this.mStrides;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public android.graphics.ColorSpace getColorSpace() {
        return this.mColorSpace;
    }

    int[] calculateOffsets(int i, int i2) {
        if (this.mFormat == 17) {
            return new int[]{(this.mStrides[0] * i2) + i, (this.mHeight * this.mStrides[0]) + ((i2 / 2) * this.mStrides[1]) + ((i / 2) * 2)};
        }
        if (this.mFormat == 20) {
            return new int[]{(i2 * this.mStrides[0]) + ((i / 2) * 4)};
        }
        return null;
    }

    private int[] calculateStrides(int i, int i2) {
        switch (i2) {
            case 17:
                return new int[]{i, i};
            case 20:
                return new int[]{i * 2};
            case 35:
                int i3 = (i + 1) / 2;
                return new int[]{i, i3, i3};
            case 54:
                int i4 = i * 2;
                return new int[]{i4, i4};
            default:
                throw new java.lang.IllegalArgumentException("only supports the following ImageFormat:" + printSupportedFormats());
        }
    }

    private void adjustRectangle(android.graphics.Rect rect) {
        int width = rect.width();
        int height = rect.height();
        if (this.mFormat == 17) {
            width &= -2;
            rect.left &= -2;
            rect.top &= -2;
            rect.right = rect.left + width;
            rect.bottom = rect.top + (height & (-2));
        }
        if (this.mFormat == 20) {
            rect.left &= -2;
            rect.right = rect.left + (width & (-2));
        }
    }
}
