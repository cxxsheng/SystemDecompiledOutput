package android.hardware.camera2;

/* loaded from: classes.dex */
public final class DngCreator implements java.lang.AutoCloseable {
    private static final int BYTES_PER_RGB_PIX = 3;
    private static final int DEFAULT_PIXEL_STRIDE = 2;
    private static final java.lang.String GPS_LAT_REF_NORTH = "N";
    private static final java.lang.String GPS_LAT_REF_SOUTH = "S";
    private static final java.lang.String GPS_LONG_REF_EAST = "E";
    private static final java.lang.String GPS_LONG_REF_WEST = "W";
    public static final int MAX_THUMBNAIL_DIMENSION = 256;
    private static final java.lang.String TAG = "DngCreator";
    private static final int TAG_ORIENTATION_UNKNOWN = 9;
    private static final java.lang.String TIFF_DATETIME_FORMAT = "yyyy:MM:dd HH:mm:ss";
    private final java.util.Calendar mGPSTimeStampCalendar = java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone(android.text.format.Time.TIMEZONE_UTC));
    private long mNativeContext;
    private static final java.lang.String GPS_DATE_FORMAT_STR = "yyyy:MM:dd";
    private static final java.text.DateFormat sExifGPSDateStamp = new java.text.SimpleDateFormat(GPS_DATE_FORMAT_STR, java.util.Locale.US);

    private static native void nativeClassInit();

    private native synchronized void nativeDestroy();

    private native synchronized void nativeInit(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative2, java.lang.String str);

    private native synchronized void nativeSetDescription(java.lang.String str);

    private native synchronized void nativeSetGpsTags(int[] iArr, java.lang.String str, int[] iArr2, java.lang.String str2, java.lang.String str3, int[] iArr3);

    private native synchronized void nativeSetOrientation(int i);

    private native synchronized void nativeSetThumbnail(java.nio.ByteBuffer byteBuffer, int i, int i2);

    private native synchronized void nativeWriteImage(java.io.OutputStream outputStream, int i, int i2, java.nio.ByteBuffer byteBuffer, int i3, int i4, long j, boolean z) throws java.io.IOException;

    private native synchronized void nativeWriteInputStream(java.io.OutputStream outputStream, java.io.InputStream inputStream, int i, int i2, long j) throws java.io.IOException;

    public DngCreator(android.hardware.camera2.CameraCharacteristics cameraCharacteristics, android.hardware.camera2.CaptureResult captureResult) {
        long uptimeMillis;
        if (cameraCharacteristics == null || captureResult == null) {
            throw new java.lang.IllegalArgumentException("Null argument to DngCreator constructor");
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        int intValue = ((java.lang.Integer) cameraCharacteristics.get(android.hardware.camera2.CameraCharacteristics.SENSOR_INFO_TIMESTAMP_SOURCE)).intValue();
        if (intValue == 1) {
            uptimeMillis = currentTimeMillis - android.os.SystemClock.elapsedRealtime();
        } else if (intValue == 0) {
            uptimeMillis = currentTimeMillis - android.os.SystemClock.uptimeMillis();
        } else {
            android.util.Log.w(TAG, "Sensor timestamp source is unexpected: " + intValue);
            uptimeMillis = currentTimeMillis - android.os.SystemClock.uptimeMillis();
        }
        java.lang.Long l = (java.lang.Long) captureResult.get(android.hardware.camera2.CaptureResult.SENSOR_TIMESTAMP);
        currentTimeMillis = l != null ? (l.longValue() / 1000000) + uptimeMillis : currentTimeMillis;
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(TIFF_DATETIME_FORMAT, java.util.Locale.US);
        simpleDateFormat.setTimeZone(java.util.TimeZone.getDefault());
        nativeInit(cameraCharacteristics.getNativeCopy(), captureResult.getNativeCopy(), simpleDateFormat.format(java.lang.Long.valueOf(currentTimeMillis)));
    }

    public android.hardware.camera2.DngCreator setOrientation(int i) {
        if (i < 0 || i > 8) {
            throw new java.lang.IllegalArgumentException("Orientation " + i + " is not a valid EXIF orientation value");
        }
        if (i == 0) {
            i = 9;
        }
        nativeSetOrientation(i);
        return this;
    }

    public android.hardware.camera2.DngCreator setThumbnail(android.graphics.Bitmap bitmap) {
        if (bitmap == null) {
            throw new java.lang.IllegalArgumentException("Null argument to setThumbnail");
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width > 256 || height > 256) {
            throw new java.lang.IllegalArgumentException("Thumbnail dimensions width,height (" + width + "," + height + ") too large, dimensions must be smaller than 256");
        }
        nativeSetThumbnail(convertToRGB(bitmap), width, height);
        return this;
    }

    public android.hardware.camera2.DngCreator setThumbnail(android.media.Image image) {
        if (image == null) {
            throw new java.lang.IllegalArgumentException("Null argument to setThumbnail");
        }
        int format = image.getFormat();
        if (format != 35) {
            throw new java.lang.IllegalArgumentException("Unsupported Image format " + format);
        }
        int width = image.getWidth();
        int height = image.getHeight();
        if (width > 256 || height > 256) {
            throw new java.lang.IllegalArgumentException("Thumbnail dimensions width,height (" + width + "," + height + ") too large, dimensions must be smaller than 256");
        }
        nativeSetThumbnail(convertToRGB(image), width, height);
        return this;
    }

    public android.hardware.camera2.DngCreator setLocation(android.location.Location location) {
        if (location == null) {
            throw new java.lang.IllegalArgumentException("Null location passed to setLocation");
        }
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        long time = location.getTime();
        int[] exifLatLong = toExifLatLong(latitude);
        int[] exifLatLong2 = toExifLatLong(longitude);
        java.lang.String str = latitude >= 0.0d ? "N" : "S";
        java.lang.String str2 = longitude >= 0.0d ? GPS_LONG_REF_EAST : "W";
        java.lang.String format = sExifGPSDateStamp.format(java.lang.Long.valueOf(time));
        this.mGPSTimeStampCalendar.setTimeInMillis(time);
        nativeSetGpsTags(exifLatLong, str, exifLatLong2, str2, format, new int[]{this.mGPSTimeStampCalendar.get(11), 1, this.mGPSTimeStampCalendar.get(12), 1, this.mGPSTimeStampCalendar.get(13), 1});
        return this;
    }

    public android.hardware.camera2.DngCreator setDescription(java.lang.String str) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("Null description passed to setDescription.");
        }
        nativeSetDescription(str);
        return this;
    }

    public void writeInputStream(java.io.OutputStream outputStream, android.util.Size size, java.io.InputStream inputStream, long j) throws java.io.IOException {
        if (outputStream == null) {
            throw new java.lang.IllegalArgumentException("Null dngOutput passed to writeInputStream");
        }
        if (size == null) {
            throw new java.lang.IllegalArgumentException("Null size passed to writeInputStream");
        }
        if (inputStream == null) {
            throw new java.lang.IllegalArgumentException("Null pixels passed to writeInputStream");
        }
        if (j < 0) {
            throw new java.lang.IllegalArgumentException("Negative offset passed to writeInputStream");
        }
        int width = size.getWidth();
        int height = size.getHeight();
        if (width <= 0 || height <= 0) {
            throw new java.lang.IllegalArgumentException("Size with invalid width, height: (" + width + "," + height + ") passed to writeInputStream");
        }
        nativeWriteInputStream(outputStream, inputStream, width, height, j);
    }

    public void writeByteBuffer(java.io.OutputStream outputStream, android.util.Size size, java.nio.ByteBuffer byteBuffer, long j) throws java.io.IOException {
        if (outputStream == null) {
            throw new java.lang.IllegalArgumentException("Null dngOutput passed to writeByteBuffer");
        }
        if (size == null) {
            throw new java.lang.IllegalArgumentException("Null size passed to writeByteBuffer");
        }
        if (byteBuffer == null) {
            throw new java.lang.IllegalArgumentException("Null pixels passed to writeByteBuffer");
        }
        if (j < 0) {
            throw new java.lang.IllegalArgumentException("Negative offset passed to writeByteBuffer");
        }
        int width = size.getWidth();
        writeByteBuffer(width, size.getHeight(), byteBuffer, outputStream, 2, width * 2, j);
    }

    public void writeImage(java.io.OutputStream outputStream, android.media.Image image) throws java.io.IOException {
        if (outputStream == null) {
            throw new java.lang.IllegalArgumentException("Null dngOutput to writeImage");
        }
        if (image == null) {
            throw new java.lang.IllegalArgumentException("Null pixels to writeImage");
        }
        int format = image.getFormat();
        if (format != 32) {
            throw new java.lang.IllegalArgumentException("Unsupported image format " + format);
        }
        android.media.Image.Plane[] planes = image.getPlanes();
        if (planes == null || planes.length <= 0) {
            throw new java.lang.IllegalArgumentException("Image with no planes passed to writeImage");
        }
        writeByteBuffer(image.getWidth(), image.getHeight(), planes[0].getBuffer(), outputStream, planes[0].getPixelStride(), planes[0].getRowStride(), 0L);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        nativeDestroy();
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    static {
        sExifGPSDateStamp.setTimeZone(java.util.TimeZone.getTimeZone(android.text.format.Time.TIMEZONE_UTC));
        nativeClassInit();
    }

    private void writeByteBuffer(int i, int i2, java.nio.ByteBuffer byteBuffer, java.io.OutputStream outputStream, int i3, int i4, long j) throws java.io.IOException {
        if (i <= 0 || i2 <= 0) {
            throw new java.lang.IllegalArgumentException("Image with invalid width, height: (" + i + "," + i2 + ") passed to write");
        }
        long capacity = byteBuffer.capacity();
        long j2 = (i4 * i2) + j;
        if (capacity < j2) {
            throw new java.lang.IllegalArgumentException("Image size " + capacity + " is too small (must be larger than " + j2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        int i5 = i3 * i;
        if (i5 > i4) {
            throw new java.lang.IllegalArgumentException("Invalid image pixel stride, row byte width " + i5 + " is too large, expecting " + i4);
        }
        byteBuffer.clear();
        nativeWriteImage(outputStream, i, i2, byteBuffer, i4, i3, j, byteBuffer.isDirect());
        byteBuffer.clear();
    }

    private static void yuvToRgb(byte[] bArr, int i, byte[] bArr2) {
        float f = bArr[0] & 255;
        float f2 = bArr[1] & 255;
        float f3 = (bArr[2] & 255) - 128.0f;
        float f4 = (1.402f * f3) + f;
        float f5 = (f - (0.34414f * (f2 - 128.0f))) - (f3 * 0.71414f);
        bArr2[i] = (byte) java.lang.Math.max(0.0f, java.lang.Math.min(255.0f, f4));
        bArr2[i + 1] = (byte) java.lang.Math.max(0.0f, java.lang.Math.min(255.0f, f5));
        bArr2[i + 2] = (byte) java.lang.Math.max(0.0f, java.lang.Math.min(255.0f, f + (r1 * 1.772f)));
    }

    private static void colorToRgb(int i, int i2, byte[] bArr) {
        bArr[i2] = (byte) android.graphics.Color.red(i);
        bArr[i2 + 1] = (byte) android.graphics.Color.green(i);
        bArr[i2 + 2] = (byte) android.graphics.Color.blue(i);
    }

    private static java.nio.ByteBuffer convertToRGB(android.media.Image image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int i = width * 3;
        java.nio.ByteBuffer allocateDirect = java.nio.ByteBuffer.allocateDirect(i * height);
        android.media.Image.Plane plane = image.getPlanes()[0];
        android.media.Image.Plane plane2 = image.getPlanes()[1];
        android.media.Image.Plane plane3 = image.getPlanes()[2];
        java.nio.ByteBuffer buffer = plane.getBuffer();
        java.nio.ByteBuffer buffer2 = plane2.getBuffer();
        java.nio.ByteBuffer buffer3 = plane3.getBuffer();
        buffer.rewind();
        buffer2.rewind();
        buffer3.rewind();
        int rowStride = plane.getRowStride();
        int rowStride2 = plane3.getRowStride();
        int rowStride3 = plane2.getRowStride();
        int pixelStride = plane.getPixelStride();
        int pixelStride2 = plane3.getPixelStride();
        int pixelStride3 = plane2.getPixelStride();
        byte[] bArr = {0, 0, 0};
        byte[] bArr2 = new byte[((width - 1) * pixelStride) + 1];
        int i2 = (width / 2) - 1;
        byte[] bArr3 = new byte[(pixelStride3 * i2) + 1];
        byte[] bArr4 = new byte[(i2 * pixelStride2) + 1];
        byte[] bArr5 = new byte[i];
        int i3 = 0;
        while (i3 < height) {
            int i4 = i3 / 2;
            int i5 = height;
            buffer.position(rowStride * i3);
            buffer.get(bArr2);
            buffer2.position(rowStride3 * i4);
            buffer2.get(bArr3);
            buffer3.position(rowStride2 * i4);
            buffer3.get(bArr4);
            int i6 = 0;
            while (i6 < width) {
                int i7 = i6 / 2;
                bArr[0] = bArr2[pixelStride * i6];
                bArr[1] = bArr3[pixelStride3 * i7];
                bArr[2] = bArr4[i7 * pixelStride2];
                yuvToRgb(bArr, i6 * 3, bArr5);
                i6++;
                width = width;
            }
            allocateDirect.put(bArr5);
            i3++;
            width = width;
            height = i5;
        }
        buffer.rewind();
        buffer2.rewind();
        buffer3.rewind();
        allocateDirect.rewind();
        return allocateDirect;
    }

    private static java.nio.ByteBuffer convertToRGB(android.graphics.Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = width * 3;
        java.nio.ByteBuffer allocateDirect = java.nio.ByteBuffer.allocateDirect(i * height);
        int[] iArr = new int[width];
        byte[] bArr = new byte[i];
        for (int i2 = 0; i2 < height; i2++) {
            bitmap.getPixels(iArr, 0, width, 0, i2, width, 1);
            for (int i3 = 0; i3 < width; i3++) {
                colorToRgb(iArr[i3], i3 * 3, bArr);
            }
            allocateDirect.put(bArr);
        }
        allocateDirect.rewind();
        return allocateDirect;
    }

    private static int[] toExifLatLong(double d) {
        double abs = java.lang.Math.abs(d);
        int i = (int) abs;
        double d2 = (abs - i) * 60.0d;
        int i2 = (int) d2;
        return new int[]{i, 1, i2, 1, (int) ((d2 - i2) * 6000.0d), 100};
    }
}
