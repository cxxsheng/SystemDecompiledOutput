package android.media;

/* loaded from: classes2.dex */
class ImageUtils {
    private static final java.lang.String IMAGEUTILS_LOG_TAG = "ImageUtils";

    ImageUtils() {
    }

    public static int getNumPlanesForFormat(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 20:
            case 32:
            case 36:
            case 37:
            case 38:
            case 256:
            case 257:
            case 4098:
            case 4099:
            case 4101:
            case 538982489:
            case 540422489:
            case android.graphics.ImageFormat.DEPTH16 /* 1144402265 */:
            case android.graphics.ImageFormat.HEIC /* 1212500294 */:
            case android.graphics.ImageFormat.DEPTH_JPEG /* 1768253795 */:
                return 1;
            case 16:
                return 2;
            case 17:
            case 35:
            case 54:
            case 842094169:
                return 3;
            case 34:
                return 0;
            default:
                throw new java.lang.UnsupportedOperationException(java.lang.String.format("Invalid format specified %d", java.lang.Integer.valueOf(i)));
        }
    }

    public static int getNumPlanesForHardwareBufferFormat(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 22:
            case 33:
            case 43:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
                return 1;
            case 35:
            case 54:
                return 3;
            default:
                throw new java.lang.UnsupportedOperationException(java.lang.String.format("Invalid hardwareBuffer format specified %d", java.lang.Integer.valueOf(i)));
        }
    }

    public static void imageCopy(android.media.Image image, android.media.Image image2) {
        int remaining;
        if (image == null || image2 == null) {
            throw new java.lang.IllegalArgumentException("Images should be non-null");
        }
        if (image.getFormat() != image2.getFormat()) {
            throw new java.lang.IllegalArgumentException("Src and dst images should have the same format");
        }
        if (image.getFormat() == 34 || image2.getFormat() == 34) {
            throw new java.lang.IllegalArgumentException("PRIVATE format images are not copyable");
        }
        if (image.getFormat() == 36) {
            throw new java.lang.IllegalArgumentException("Copy of RAW_OPAQUE format has not been implemented");
        }
        if (image.getFormat() == 4098) {
            throw new java.lang.IllegalArgumentException("Copy of RAW_DEPTH format has not been implemented");
        }
        if (image.getFormat() == 4099) {
            throw new java.lang.IllegalArgumentException("Copy of RAW_DEPTH10 format has not been implemented");
        }
        if (!(image2.getOwner() instanceof android.media.ImageWriter)) {
            throw new java.lang.IllegalArgumentException("Destination image is not from ImageWriter. Only the images from ImageWriter are writable");
        }
        android.util.Size size = new android.util.Size(image.getWidth(), image.getHeight());
        android.util.Size size2 = new android.util.Size(image2.getWidth(), image2.getHeight());
        if (!size.equals(size2)) {
            throw new java.lang.IllegalArgumentException("source image size " + size + " is different with destination image size " + size2);
        }
        android.media.Image.Plane[] planes = image.getPlanes();
        android.media.Image.Plane[] planes2 = image2.getPlanes();
        for (int i = 0; i < planes.length; i++) {
            int rowStride = planes[i].getRowStride();
            int rowStride2 = planes2[i].getRowStride();
            java.nio.ByteBuffer buffer = planes[i].getBuffer();
            java.nio.ByteBuffer buffer2 = planes2[i].getBuffer();
            if (!buffer.isDirect() || !buffer2.isDirect()) {
                throw new java.lang.IllegalArgumentException("Source and destination ByteBuffers must be direct byteBuffer!");
            }
            if (planes[i].getPixelStride() != planes2[i].getPixelStride()) {
                throw new java.lang.IllegalArgumentException("Source plane image pixel stride " + planes[i].getPixelStride() + " must be same as destination image pixel stride " + planes2[i].getPixelStride());
            }
            int position = buffer.position();
            buffer.rewind();
            buffer2.rewind();
            if (rowStride == rowStride2) {
                buffer2.put(buffer);
            } else {
                int position2 = buffer.position();
                int position3 = buffer2.position();
                android.util.Size effectivePlaneSizeForImage = getEffectivePlaneSizeForImage(image, i);
                int width = effectivePlaneSizeForImage.getWidth() * planes[i].getPixelStride();
                for (int i2 = 0; i2 < effectivePlaneSizeForImage.getHeight(); i2++) {
                    if (i2 == effectivePlaneSizeForImage.getHeight() - 1 && width > (remaining = buffer.remaining() - position2)) {
                        width = remaining;
                    }
                    directByteBufferCopy(buffer, position2, buffer2, position3, width);
                    position2 += rowStride;
                    position3 += rowStride2;
                }
            }
            buffer.position(position);
            buffer2.rewind();
        }
    }

    public static int getEstimatedNativeAllocBytes(int i, int i2, int i3, int i4) {
        double d = 1.0d;
        switch (i3) {
            case 1:
            case 2:
            case 43:
                d = 4.0d;
                break;
            case 3:
            case 54:
                d = 3.0d;
                break;
            case 4:
            case 16:
            case 20:
            case 32:
            case 36:
            case 4098:
            case 540422489:
            case android.graphics.ImageFormat.DEPTH16 /* 1144402265 */:
                d = 2.0d;
                break;
            case 17:
            case 34:
            case 35:
            case 38:
            case 842094169:
                d = 1.5d;
                break;
            case 37:
            case 4099:
                d = 1.25d;
                break;
            case 256:
            case 257:
            case 4101:
            case android.graphics.ImageFormat.HEIC /* 1212500294 */:
            case android.graphics.ImageFormat.DEPTH_JPEG /* 1768253795 */:
                d = 0.3d;
                break;
            case 538982489:
                break;
            default:
                if (android.util.Log.isLoggable(IMAGEUTILS_LOG_TAG, 2)) {
                    android.util.Log.v(IMAGEUTILS_LOG_TAG, "getEstimatedNativeAllocBytes() uses defaultestimated native allocation size.");
                    break;
                }
                break;
        }
        return (int) (i * i2 * d * i4);
    }

    private static android.util.Size getEffectivePlaneSizeForImage(android.media.Image image, int i) {
        switch (image.getFormat()) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 20:
            case 32:
            case 37:
            case 38:
            case 43:
            case 256:
            case 4098:
            case 4099:
            case 4101:
            case 538982489:
            case 540422489:
            case android.graphics.ImageFormat.HEIC /* 1212500294 */:
                return new android.util.Size(image.getWidth(), image.getHeight());
            case 16:
                if (i == 0) {
                    return new android.util.Size(image.getWidth(), image.getHeight());
                }
                return new android.util.Size(image.getWidth(), image.getHeight() / 2);
            case 17:
            case 35:
            case 54:
            case 842094169:
                if (i == 0) {
                    return new android.util.Size(image.getWidth(), image.getHeight());
                }
                return new android.util.Size(image.getWidth() / 2, image.getHeight() / 2);
            case 34:
                return new android.util.Size(0, 0);
            default:
                if (android.util.Log.isLoggable(IMAGEUTILS_LOG_TAG, 2)) {
                    android.util.Log.v(IMAGEUTILS_LOG_TAG, "getEffectivePlaneSizeForImage() usesimage's width and height for plane size.");
                }
                return new android.util.Size(image.getWidth(), image.getHeight());
        }
    }

    private static void directByteBufferCopy(java.nio.ByteBuffer byteBuffer, int i, java.nio.ByteBuffer byteBuffer2, int i2, int i3) {
        libcore.io.Memory.memmove(byteBuffer2, i2, byteBuffer, i, i3);
    }
}
