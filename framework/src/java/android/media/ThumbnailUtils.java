package android.media;

/* loaded from: classes2.dex */
public class ThumbnailUtils {
    private static final int OPTIONS_NONE = 0;
    public static final int OPTIONS_RECYCLE_INPUT = 2;
    private static final int OPTIONS_SCALE_UP = 1;
    private static final java.lang.String TAG = "ThumbnailUtils";

    @java.lang.Deprecated
    public static final int TARGET_SIZE_MICRO_THUMBNAIL = 96;

    private static android.util.Size convertKind(int i) {
        return android.provider.MediaStore.Images.Thumbnails.getKindSize(i);
    }

    private static class Resizer implements android.graphics.ImageDecoder.OnHeaderDecodedListener {
        private final android.os.CancellationSignal signal;
        private final android.util.Size size;

        public Resizer(android.util.Size size, android.os.CancellationSignal cancellationSignal) {
            this.size = size;
            this.signal = cancellationSignal;
        }

        @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
        public void onHeaderDecoded(android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
            if (this.signal != null) {
                this.signal.throwIfCanceled();
            }
            imageDecoder.setAllocator(1);
            int max = java.lang.Math.max(imageInfo.getSize().getWidth() / this.size.getWidth(), imageInfo.getSize().getHeight() / this.size.getHeight());
            if (max > 1) {
                imageDecoder.setTargetSampleSize(max);
            }
        }
    }

    @java.lang.Deprecated
    public static android.graphics.Bitmap createAudioThumbnail(java.lang.String str, int i) {
        try {
            return createAudioThumbnail(new java.io.File(str), convertKind(i), null);
        } catch (java.io.IOException e) {
            android.util.Log.w(TAG, e);
            return null;
        }
    }

    public static android.graphics.Bitmap createAudioThumbnail(java.io.File file, android.util.Size size, android.os.CancellationSignal cancellationSignal) throws java.io.IOException {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        android.media.ThumbnailUtils.Resizer resizer = new android.media.ThumbnailUtils.Resizer(size, cancellationSignal);
        try {
            android.media.MediaMetadataRetriever mediaMetadataRetriever = new android.media.MediaMetadataRetriever();
            try {
                mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
                byte[] embeddedPicture = mediaMetadataRetriever.getEmbeddedPicture();
                if (embeddedPicture != null) {
                    android.graphics.Bitmap decodeBitmap = android.graphics.ImageDecoder.decodeBitmap(android.graphics.ImageDecoder.createSource(embeddedPicture), resizer);
                    mediaMetadataRetriever.close();
                    return decodeBitmap;
                }
                mediaMetadataRetriever.close();
                if ("unknown".equals(android.os.Environment.getExternalStorageState(file))) {
                    throw new java.io.IOException("No embedded album art found");
                }
                java.io.File parentFile = file.getParentFile();
                java.io.File parentFile2 = parentFile != null ? parentFile.getParentFile() : null;
                if (parentFile != null && parentFile.getName().equals(android.os.Environment.DIRECTORY_DOWNLOADS)) {
                    throw new java.io.IOException("No thumbnails in Downloads directories");
                }
                if (parentFile2 != null && "unknown".equals(android.os.Environment.getExternalStorageState(parentFile2))) {
                    throw new java.io.IOException("No thumbnails in top-level directories");
                }
                java.io.File[] defeatNullable = com.android.internal.util.ArrayUtils.defeatNullable(file.getParentFile().listFiles(new java.io.FilenameFilter() { // from class: android.media.ThumbnailUtils$$ExternalSyntheticLambda0
                    @Override // java.io.FilenameFilter
                    public final boolean accept(java.io.File file2, java.lang.String str) {
                        return android.media.ThumbnailUtils.lambda$createAudioThumbnail$0(file2, str);
                    }
                }));
                final java.util.function.ToIntFunction toIntFunction = new java.util.function.ToIntFunction() { // from class: android.media.ThumbnailUtils$$ExternalSyntheticLambda1
                    @Override // java.util.function.ToIntFunction
                    public final int applyAsInt(java.lang.Object obj) {
                        return android.media.ThumbnailUtils.lambda$createAudioThumbnail$1((java.io.File) obj);
                    }
                };
                java.io.File file2 = (java.io.File) java.util.Arrays.asList(defeatNullable).stream().max(new java.util.Comparator() { // from class: android.media.ThumbnailUtils$$ExternalSyntheticLambda2
                    @Override // java.util.Comparator
                    public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                        return android.media.ThumbnailUtils.lambda$createAudioThumbnail$2(toIntFunction, (java.io.File) obj, (java.io.File) obj2);
                    }
                }).orElse(null);
                if (file2 == null) {
                    throw new java.io.IOException("No album art found");
                }
                if (cancellationSignal != null) {
                    cancellationSignal.throwIfCanceled();
                }
                return android.graphics.ImageDecoder.decodeBitmap(android.graphics.ImageDecoder.createSource(file2), resizer);
            } finally {
            }
        } catch (java.lang.RuntimeException e) {
            throw new java.io.IOException("Failed to create thumbnail", e);
        }
    }

    static /* synthetic */ boolean lambda$createAudioThumbnail$0(java.io.File file, java.lang.String str) {
        java.lang.String lowerCase = str.toLowerCase();
        return lowerCase.endsWith(".jpg") || lowerCase.endsWith(".png");
    }

    static /* synthetic */ int lambda$createAudioThumbnail$1(java.io.File file) {
        java.lang.String lowerCase = file.getName().toLowerCase();
        if (lowerCase.equals("albumart.jpg")) {
            return 4;
        }
        if (lowerCase.startsWith("albumart") && lowerCase.endsWith(".jpg")) {
            return 3;
        }
        if (lowerCase.contains("albumart") && lowerCase.endsWith(".jpg")) {
            return 2;
        }
        return lowerCase.endsWith(".jpg") ? 1 : 0;
    }

    static /* synthetic */ int lambda$createAudioThumbnail$2(java.util.function.ToIntFunction toIntFunction, java.io.File file, java.io.File file2) {
        return toIntFunction.applyAsInt(file) - toIntFunction.applyAsInt(file2);
    }

    @java.lang.Deprecated
    public static android.graphics.Bitmap createImageThumbnail(java.lang.String str, int i) {
        try {
            return createImageThumbnail(new java.io.File(str), convertKind(i), null);
        } catch (java.io.IOException e) {
            android.util.Log.w(TAG, e);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00b2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static android.graphics.Bitmap createImageThumbnail(java.io.File file, android.util.Size size, android.os.CancellationSignal cancellationSignal) throws java.io.IOException {
        android.media.ExifInterface exifInterface;
        android.graphics.Bitmap bitmap;
        byte[] thumbnailBytes;
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        android.media.ThumbnailUtils.Resizer resizer = new android.media.ThumbnailUtils.Resizer(size, cancellationSignal);
        java.lang.String mimeTypeForFile = android.media.MediaFile.getMimeTypeForFile(file.getName());
        int i = 0;
        android.graphics.Bitmap bitmap2 = null;
        if (!android.media.MediaFile.isExifMimeType(mimeTypeForFile)) {
            exifInterface = null;
        } else {
            exifInterface = new android.media.ExifInterface(file);
            switch (exifInterface.getAttributeInt(android.media.ExifInterface.TAG_ORIENTATION, 0)) {
                case 3:
                    i = 180;
                    break;
                case 6:
                    i = 90;
                    break;
                case 8:
                    i = 270;
                    break;
            }
        }
        if (mimeTypeForFile.equals("image/heif") || mimeTypeForFile.equals("image/heif-sequence") || mimeTypeForFile.equals("image/heic") || mimeTypeForFile.equals("image/heic-sequence") || mimeTypeForFile.equals(android.media.MediaFormat.MIMETYPE_IMAGE_AVIF)) {
            try {
                android.media.MediaMetadataRetriever mediaMetadataRetriever = new android.media.MediaMetadataRetriever();
                try {
                    mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
                    bitmap2 = mediaMetadataRetriever.getThumbnailImageAtIndex(-1, new android.media.MediaMetadataRetriever.BitmapParams(), size.getWidth(), size.getWidth() * size.getHeight());
                    mediaMetadataRetriever.close();
                } finally {
                }
            } catch (java.lang.RuntimeException e) {
                throw new java.io.IOException("Failed to create thumbnail", e);
            }
        }
        if (bitmap2 == null && exifInterface != null && (thumbnailBytes = exifInterface.getThumbnailBytes()) != null) {
            try {
                bitmap = android.graphics.ImageDecoder.decodeBitmap(android.graphics.ImageDecoder.createSource(thumbnailBytes), resizer);
            } catch (android.graphics.ImageDecoder.DecodeException e2) {
                android.util.Log.w(TAG, e2);
            }
            if (cancellationSignal != null) {
                cancellationSignal.throwIfCanceled();
            }
            if (bitmap != null) {
                return android.graphics.ImageDecoder.decodeBitmap(android.graphics.ImageDecoder.createSource(file), resizer);
            }
            if (i != 0 && bitmap != null) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                android.graphics.Matrix matrix = new android.graphics.Matrix();
                matrix.setRotate(i, width / 2, height / 2);
                return android.graphics.Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
            }
            return bitmap;
        }
        bitmap = bitmap2;
        if (cancellationSignal != null) {
        }
        if (bitmap != null) {
        }
    }

    @java.lang.Deprecated
    public static android.graphics.Bitmap createVideoThumbnail(java.lang.String str, int i) {
        try {
            return createVideoThumbnail(new java.io.File(str), convertKind(i), null);
        } catch (java.io.IOException e) {
            android.util.Log.w(TAG, e);
            return null;
        }
    }

    public static android.graphics.Bitmap createVideoThumbnail(java.io.File file, android.util.Size size, android.os.CancellationSignal cancellationSignal) throws java.io.IOException {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        android.media.ThumbnailUtils.Resizer resizer = new android.media.ThumbnailUtils.Resizer(size, cancellationSignal);
        try {
            android.media.MediaMetadataRetriever mediaMetadataRetriever = new android.media.MediaMetadataRetriever();
            try {
                mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
                byte[] embeddedPicture = mediaMetadataRetriever.getEmbeddedPicture();
                if (embeddedPicture != null) {
                    android.graphics.Bitmap decodeBitmap = android.graphics.ImageDecoder.decodeBitmap(android.graphics.ImageDecoder.createSource(embeddedPicture), resizer);
                    mediaMetadataRetriever.close();
                    return decodeBitmap;
                }
                android.media.MediaMetadataRetriever.BitmapParams bitmapParams = new android.media.MediaMetadataRetriever.BitmapParams();
                bitmapParams.setPreferredConfig(android.graphics.Bitmap.Config.ARGB_8888);
                int parseInt = java.lang.Integer.parseInt(mediaMetadataRetriever.extractMetadata(18));
                int parseInt2 = java.lang.Integer.parseInt(mediaMetadataRetriever.extractMetadata(19));
                long parseLong = (java.lang.Long.parseLong(mediaMetadataRetriever.extractMetadata(9)) * 1000) / 2;
                if (size.getWidth() <= parseInt || size.getHeight() <= parseInt2) {
                    android.graphics.Bitmap bitmap = (android.graphics.Bitmap) java.util.Objects.requireNonNull(mediaMetadataRetriever.getScaledFrameAtTime(parseLong, 2, size.getWidth(), size.getHeight(), bitmapParams));
                    mediaMetadataRetriever.close();
                    return bitmap;
                }
                android.graphics.Bitmap bitmap2 = (android.graphics.Bitmap) java.util.Objects.requireNonNull(mediaMetadataRetriever.getFrameAtTime(parseLong, 2, bitmapParams));
                mediaMetadataRetriever.close();
                return bitmap2;
            } finally {
            }
        } catch (java.lang.RuntimeException e) {
            throw new java.io.IOException("Failed to create thumbnail", e);
        }
    }

    public static android.graphics.Bitmap extractThumbnail(android.graphics.Bitmap bitmap, int i, int i2) {
        return extractThumbnail(bitmap, i, i2, 0);
    }

    public static android.graphics.Bitmap extractThumbnail(android.graphics.Bitmap bitmap, int i, int i2, int i3) {
        float height;
        if (bitmap == null) {
            return null;
        }
        if (bitmap.getWidth() < bitmap.getHeight()) {
            height = i / bitmap.getWidth();
        } else {
            height = i2 / bitmap.getHeight();
        }
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        matrix.setScale(height, height);
        return transform(matrix, bitmap, i, i2, i3 | 1);
    }

    @java.lang.Deprecated
    private static int computeSampleSize(android.graphics.BitmapFactory.Options options, int i, int i2) {
        return 1;
    }

    @java.lang.Deprecated
    private static int computeInitialSampleSize(android.graphics.BitmapFactory.Options options, int i, int i2) {
        return 1;
    }

    @java.lang.Deprecated
    private static void closeSilently(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
    }

    @java.lang.Deprecated
    private static android.os.ParcelFileDescriptor makeInputStream(android.net.Uri uri, android.content.ContentResolver contentResolver) {
        try {
            return contentResolver.openFileDescriptor(uri, "r");
        } catch (java.io.IOException e) {
            return null;
        }
    }

    @java.lang.Deprecated
    private static android.graphics.Bitmap transform(android.graphics.Matrix matrix, android.graphics.Bitmap bitmap, int i, int i2, int i3) {
        android.graphics.Matrix matrix2;
        android.graphics.Bitmap bitmap2;
        android.graphics.Matrix matrix3 = matrix;
        boolean z = (i3 & 1) != 0;
        boolean z2 = (i3 & 2) != 0;
        int width = bitmap.getWidth() - i;
        int height = bitmap.getHeight() - i2;
        if (!z && (width < 0 || height < 0)) {
            android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(i, i2, android.graphics.Bitmap.Config.ARGB_8888);
            android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
            int max = java.lang.Math.max(0, width / 2);
            int max2 = java.lang.Math.max(0, height / 2);
            android.graphics.Rect rect = new android.graphics.Rect(max, max2, java.lang.Math.min(i, bitmap.getWidth()) + max, java.lang.Math.min(i2, bitmap.getHeight()) + max2);
            int width2 = (i - rect.width()) / 2;
            int height2 = (i2 - rect.height()) / 2;
            canvas.drawBitmap(bitmap, rect, new android.graphics.Rect(width2, height2, i - width2, i2 - height2), (android.graphics.Paint) null);
            if (z2) {
                bitmap.recycle();
            }
            canvas.setBitmap(null);
            return createBitmap;
        }
        float width3 = bitmap.getWidth();
        float height3 = bitmap.getHeight();
        float f = i;
        float f2 = i2;
        if (width3 / height3 > f / f2) {
            float f3 = f2 / height3;
            if (f3 < 0.9f || f3 > 1.0f) {
                matrix.setScale(f3, f3);
            } else {
                matrix3 = null;
            }
            matrix2 = matrix3;
        } else {
            float f4 = f / width3;
            if (f4 < 0.9f || f4 > 1.0f) {
                matrix.setScale(f4, f4);
                matrix2 = matrix3;
            } else {
                matrix2 = null;
            }
        }
        if (matrix2 != null) {
            bitmap2 = android.graphics.Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix2, true);
        } else {
            bitmap2 = bitmap;
        }
        if (z2 && bitmap2 != bitmap) {
            bitmap.recycle();
        }
        android.graphics.Bitmap createBitmap2 = android.graphics.Bitmap.createBitmap(bitmap2, java.lang.Math.max(0, bitmap2.getWidth() - i) / 2, java.lang.Math.max(0, bitmap2.getHeight() - i2) / 2, i, i2);
        if (createBitmap2 != bitmap2 && (z2 || bitmap2 != bitmap)) {
            bitmap2.recycle();
        }
        return createBitmap2;
    }

    @java.lang.Deprecated
    private static class SizedThumbnailBitmap {
        public android.graphics.Bitmap mBitmap;
        public byte[] mThumbnailData;
        public int mThumbnailHeight;
        public int mThumbnailWidth;

        private SizedThumbnailBitmap() {
        }
    }

    @java.lang.Deprecated
    private static void createThumbnailFromEXIF(java.lang.String str, int i, int i2, android.media.ThumbnailUtils.SizedThumbnailBitmap sizedThumbnailBitmap) {
    }
}
