package com.android.server.wallpaper;

/* loaded from: classes.dex */
public class WallpaperCropper {
    private static final int ADD = 1;
    private static final int BALANCE = 3;
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_CROP = true;
    private static final float MAX_PARALLAX = 1.0f;
    private static final int REMOVE = 2;
    private static final java.lang.String TAG = com.android.server.wallpaper.WallpaperCropper.class.getSimpleName();
    private final com.android.server.wallpaper.WallpaperDisplayHelper mWallpaperDisplayHelper;

    public interface WallpaperCropUtils {
        android.graphics.Rect getCrop(android.graphics.Point point, android.graphics.Point point2, android.util.SparseArray<android.graphics.Rect> sparseArray, boolean z);
    }

    WallpaperCropper(com.android.server.wallpaper.WallpaperDisplayHelper wallpaperDisplayHelper) {
        this.mWallpaperDisplayHelper = wallpaperDisplayHelper;
    }

    public android.graphics.Rect getCrop(android.graphics.Point point, android.graphics.Point point2, android.util.SparseArray<android.graphics.Rect> sparseArray, boolean z) {
        if (sparseArray == null || sparseArray.size() == 0) {
            android.graphics.Rect rect = new android.graphics.Rect(0, 0, point.x, point.y);
            rect.scale(java.lang.Math.min(point2.x / point.x, point2.y / point.y));
            rect.offset((point2.x - rect.width()) / 2, (point2.y - rect.height()) / 2);
            return rect;
        }
        int orientation = android.app.WallpaperManager.getOrientation(point);
        android.graphics.Rect rect2 = sparseArray.get(orientation);
        if (rect2 != null) {
            if (rect2.left < 0 || rect2.top < 0 || rect2.right > point2.x || rect2.bottom > point2.y) {
                android.util.Slog.w(TAG, "invalid suggested crop: " + rect2);
                return getAdjustedCrop(new android.graphics.Rect(0, 0, point2.x, point2.y), point2, point, true, z, 1);
            }
            return getAdjustedCrop(rect2, point2, point, true, z, 1);
        }
        android.util.SparseArray<android.graphics.Point> defaultDisplaySizes = this.mWallpaperDisplayHelper.getDefaultDisplaySizes();
        int rotatedOrientation = android.app.WallpaperManager.getRotatedOrientation(orientation);
        android.graphics.Rect rect3 = sparseArray.get(rotatedOrientation);
        android.graphics.Point point3 = defaultDisplaySizes.get(rotatedOrientation);
        if (rect3 != null) {
            return getAdjustedCrop(noParallax(rect3, point3, point2, z), point2, point, false, z, 3);
        }
        int unfoldedOrientation = this.mWallpaperDisplayHelper.getUnfoldedOrientation(orientation);
        android.graphics.Rect rect4 = sparseArray.get(unfoldedOrientation);
        android.graphics.Point point4 = defaultDisplaySizes.get(unfoldedOrientation);
        if (rect4 != null) {
            return getAdjustedCrop(noParallax(rect4, point4, point2, z), point2, point, false, z, 2);
        }
        int foldedOrientation = this.mWallpaperDisplayHelper.getFoldedOrientation(orientation);
        android.graphics.Rect rect5 = sparseArray.get(foldedOrientation);
        android.graphics.Point point5 = defaultDisplaySizes.get(foldedOrientation);
        if (rect5 != null) {
            return getAdjustedCrop(noParallax(rect5, point5, point2, z), point2, point, false, z, 1);
        }
        android.graphics.Point point6 = defaultDisplaySizes.get(rotatedOrientation);
        if (point6 != null) {
            int[] iArr = {this.mWallpaperDisplayHelper.getFoldedOrientation(rotatedOrientation), this.mWallpaperDisplayHelper.getUnfoldedOrientation(rotatedOrientation)};
            for (int i = 0; i < 2; i++) {
                if (sparseArray.get(iArr[i]) != null) {
                    android.graphics.Rect crop = getCrop(point6, point2, sparseArray, z);
                    android.util.SparseArray<android.graphics.Rect> sparseArray2 = new android.util.SparseArray<>();
                    sparseArray2.put(rotatedOrientation, crop);
                    return getCrop(point, point2, sparseArray2, z);
                }
            }
        }
        android.util.Slog.w(TAG, "Could not find a proper default crop for display: " + point + ", bitmap size: " + point2 + ", suggested crops: " + sparseArray + ", orientation: " + orientation + ", rtl: " + z + ", defaultDisplaySizes: " + defaultDisplaySizes);
        return getCrop(point, point2, new android.util.SparseArray<>(), z);
    }

    private static android.graphics.Rect noParallax(android.graphics.Rect rect, android.graphics.Point point, android.graphics.Point point2, boolean z) {
        if (point == null) {
            return rect;
        }
        android.graphics.Rect adjustedCrop = getAdjustedCrop(rect, point2, point, true, z, 1);
        int width = (int) ((adjustedCrop.width() - (adjustedCrop.height() * ((point.x * 1.0f) / point.y))) + 0.5f);
        if (z) {
            adjustedCrop.left += width;
        } else {
            adjustedCrop.right -= width;
        }
        return adjustedCrop;
    }

    private static android.graphics.Rect getAdjustedCrop(android.graphics.Rect rect, android.graphics.Point point, android.graphics.Point point2, boolean z, boolean z2, int i) {
        int height;
        android.graphics.Rect rect2 = new android.graphics.Rect(rect);
        float width = rect.width() / rect.height();
        float f = point2.x / point2.y;
        if (width >= f) {
            if (!z) {
                int i2 = point.y - rect.bottom;
                int height2 = rect.height() + i2;
                int i3 = rect.left;
                android.graphics.Rect rect3 = new android.graphics.Rect(i2, i3, height2, rect.width() + i3);
                android.graphics.Point point3 = new android.graphics.Point(point.y, point.x);
                android.graphics.Rect adjustedCrop = getAdjustedCrop(rect3, point3, new android.graphics.Point(point2.y, point2.x), false, z2, i);
                int i4 = adjustedCrop.top;
                int height3 = adjustedCrop.height() + i4;
                int i5 = point3.x - adjustedCrop.right;
                return new android.graphics.Rect(i4, i5, height3, adjustedCrop.width() + i5);
            }
            if ((width / f) - 1.0f > 1.0f) {
                int ceil = (int) java.lang.Math.ceil((r1 - 1.0f) * f * rect.height());
                if (z2) {
                    rect2.left += ceil;
                } else {
                    rect2.right -= ceil;
                }
            }
        } else {
            if (i == 2) {
                height = 0;
            } else {
                height = i == 1 ? (int) (((rect.height() * f) + 0.5d) - rect.width()) : (int) ((rect.height() + 0.5d) - rect.width());
            }
            if (point.x - rect.width() >= height) {
                int i6 = height / 2;
                int i7 = (height % 2) + i6;
                if (rect.left < i6) {
                    i7 += i6 - rect.left;
                    i6 = rect.left;
                } else if (point.x - rect.right < i7) {
                    i6 += i7 - (point.x - rect.right);
                    i7 = point.x - rect.right;
                }
                rect2.left -= i6;
                rect2.right += i7;
            } else {
                rect2.left = 0;
                rect2.right = point.x;
            }
            int height4 = (int) (rect.height() - (rect2.width() / f));
            int i8 = height4 / 2;
            rect2.top += (height4 % 2) + i8;
            rect2.bottom -= i8;
        }
        return rect2;
    }

    public static android.graphics.Rect getTotalCrop(android.util.SparseArray<android.graphics.Rect> sparseArray) {
        int i = Integer.MAX_VALUE;
        int i2 = Integer.MIN_VALUE;
        int i3 = Integer.MAX_VALUE;
        int i4 = Integer.MIN_VALUE;
        for (int i5 = 0; i5 < sparseArray.size(); i5++) {
            android.graphics.Rect valueAt = sparseArray.valueAt(i5);
            i = java.lang.Math.min(i, valueAt.left);
            i3 = java.lang.Math.min(i3, valueAt.top);
            i4 = java.lang.Math.max(i4, valueAt.right);
            i2 = java.lang.Math.max(i2, valueAt.bottom);
        }
        return new android.graphics.Rect(i, i3, i4, i2);
    }

    android.util.SparseArray<android.graphics.Rect> getRelativeCropHints(com.android.server.wallpaper.WallpaperData wallpaperData) {
        android.util.SparseArray<android.graphics.Rect> sparseArray = new android.util.SparseArray<>();
        for (int i = 0; i < wallpaperData.mCropHints.size(); i++) {
            android.graphics.Rect rect = new android.graphics.Rect(wallpaperData.mCropHints.valueAt(i));
            rect.offset(-wallpaperData.cropHint.left, -wallpaperData.cropHint.top);
            rect.scale(1.0f / wallpaperData.mSampleSize);
            sparseArray.put(wallpaperData.mCropHints.keyAt(i), rect);
        }
        return sparseArray;
    }

    static java.util.List<android.graphics.Rect> getOriginalCropHints(com.android.server.wallpaper.WallpaperData wallpaperData, java.util.List<android.graphics.Rect> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<android.graphics.Rect> it = list.iterator();
        while (it.hasNext()) {
            android.graphics.Rect rect = new android.graphics.Rect(it.next());
            rect.scale(wallpaperData.mSampleSize);
            rect.offset(wallpaperData.cropHint.left, wallpaperData.cropHint.top);
            arrayList.add(rect);
        }
        return arrayList;
    }

    android.util.SparseArray<android.graphics.Rect> getDefaultCrops(android.util.SparseArray<android.graphics.Rect> sparseArray, android.graphics.Point point) {
        android.util.SparseArray<android.graphics.Point> defaultDisplaySizes = this.mWallpaperDisplayHelper.getDefaultDisplaySizes();
        boolean z = android.text.TextUtils.getLayoutDirectionFromLocale(java.util.Locale.getDefault()) == 1;
        android.util.SparseArray<android.graphics.Rect> sparseArray2 = new android.util.SparseArray<>();
        for (int i = 0; i < defaultDisplaySizes.size(); i++) {
            int keyAt = defaultDisplaySizes.keyAt(i);
            android.graphics.Point valueAt = defaultDisplaySizes.valueAt(i);
            if (sparseArray.get(keyAt) != null) {
                sparseArray2.put(keyAt, getCrop(valueAt, point, sparseArray, z));
            }
        }
        android.util.SparseArray<android.graphics.Rect> clone = sparseArray2.clone();
        for (int i2 = 0; i2 < defaultDisplaySizes.size(); i2++) {
            int keyAt2 = defaultDisplaySizes.keyAt(i2);
            if (!clone.contains(keyAt2)) {
                clone.put(keyAt2, getCrop(defaultDisplaySizes.valueAt(i2), point, sparseArray2, z));
            }
        }
        return clone;
    }

    void generateCrop(com.android.server.wallpaper.WallpaperData wallpaperData) {
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog(TAG);
        timingsTraceAndSlog.traceBegin("WPMS.generateCrop");
        generateCropInternal(wallpaperData);
        timingsTraceAndSlog.traceEnd();
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x03f8  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0410  */
    /* JADX WARN: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void generateCropInternal(com.android.server.wallpaper.WallpaperData wallpaperData) {
        boolean z;
        android.graphics.Rect rect;
        boolean z2;
        java.io.FileOutputStream fileOutputStream;
        java.io.FileOutputStream fileOutputStream2;
        java.io.BufferedOutputStream bufferedOutputStream;
        java.io.FileOutputStream fileOutputStream3;
        java.io.BufferedOutputStream bufferedOutputStream2;
        com.android.server.wallpaper.WallpaperDisplayHelper.DisplayData displayDataOrCreate = this.mWallpaperDisplayHelper.getDisplayDataOrCreate(0);
        android.view.DisplayInfo displayInfo = this.mWallpaperDisplayHelper.getDisplayInfo(0);
        android.graphics.BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        android.graphics.BitmapFactory.decodeFile(wallpaperData.getWallpaperFile().getAbsolutePath(), options);
        if (options.outWidth > 0 && options.outHeight > 0) {
            boolean z3 = com.android.window.flags.Flags.multiCrop() && wallpaperData.mSupportsMultiCrop;
            android.graphics.Point point = new android.graphics.Point(options.outWidth, options.outHeight);
            if (z3) {
                android.util.SparseArray<android.graphics.Rect> defaultCrops = getDefaultCrops(wallpaperData.mCropHints, point);
                android.util.SparseArray<android.graphics.Rect> sparseArray = new android.util.SparseArray<>();
                for (int i = 0; i < wallpaperData.mCropHints.size(); i++) {
                    int keyAt = wallpaperData.mCropHints.keyAt(i);
                    android.graphics.Rect rect2 = defaultCrops.get(keyAt);
                    if (rect2 != null) {
                        sparseArray.put(keyAt, rect2);
                    }
                }
                wallpaperData.mCropHints = sparseArray;
                rect = getTotalCrop(defaultCrops);
                wallpaperData.cropHint.set(rect);
            } else {
                rect = new android.graphics.Rect(wallpaperData.cropHint);
            }
            if (rect.isEmpty()) {
                rect.top = 0;
                rect.left = 0;
                rect.right = options.outWidth;
                rect.bottom = options.outHeight;
                z2 = false;
            } else {
                rect.offset(rect.right > options.outWidth ? options.outWidth - rect.right : 0, rect.bottom > options.outHeight ? options.outHeight - rect.bottom : 0);
                if (rect.left < 0) {
                    rect.left = 0;
                }
                if (rect.top < 0) {
                    rect.top = 0;
                }
                z2 = options.outHeight > rect.height() || options.outWidth > rect.width();
            }
            boolean z4 = rect.height() > displayDataOrCreate.mHeight || rect.height() > com.android.server.wallpaper.GLHelper.getMaxTextureSize() || rect.width() > com.android.server.wallpaper.GLHelper.getMaxTextureSize();
            if (z4 && !z3) {
                if (((int) (rect.width() * (displayDataOrCreate.mHeight / rect.height()))) < displayInfo.logicalWidth) {
                    rect.bottom = (int) (rect.width() * (displayInfo.logicalHeight / displayInfo.logicalWidth));
                    z2 = true;
                }
            }
            android.util.Slog.v(TAG, "crop: w=" + rect.width() + " h=" + rect.height());
            android.util.Slog.v(TAG, "dims: w=" + displayDataOrCreate.mWidth + " h=" + displayDataOrCreate.mHeight);
            android.util.Slog.v(TAG, "meas: w=" + options.outWidth + " h=" + options.outHeight);
            android.util.Slog.v(TAG, "crop?=" + z2 + " scale?=" + z4);
            if (z2 || z4) {
                java.io.BufferedOutputStream bufferedOutputStream3 = null;
                try {
                    final int i2 = 1;
                    while (true) {
                        int i3 = i2 * 2;
                        if (i3 > rect.height() / displayDataOrCreate.mHeight) {
                            break;
                        } else {
                            i2 = i3;
                        }
                    }
                    options.inSampleSize = i2;
                    options.inJustDecodeBounds = false;
                    final android.graphics.Rect rect3 = new android.graphics.Rect(rect);
                    rect3.scale(1.0f / options.inSampleSize);
                    float height = displayDataOrCreate.mHeight / rect3.height();
                    if (z3) {
                        try {
                            height = java.lang.Math.min(this.mWallpaperDisplayHelper.getDefaultDisplayLargestDimension() / rect3.height(), 1.0f);
                        } catch (java.lang.Exception e) {
                            fileOutputStream2 = null;
                            libcore.io.IoUtils.closeQuietly(bufferedOutputStream3);
                            libcore.io.IoUtils.closeQuietly(fileOutputStream2);
                            z = false;
                            if (!z) {
                            }
                            if (wallpaperData.getCropFile().exists()) {
                            }
                        } catch (java.lang.Throwable th) {
                            th = th;
                            fileOutputStream = null;
                            libcore.io.IoUtils.closeQuietly(bufferedOutputStream3);
                            libcore.io.IoUtils.closeQuietly(fileOutputStream);
                            throw th;
                        }
                    }
                    int height2 = (int) (rect3.height() * height);
                    int width = (int) (rect3.width() * height);
                    if (width > com.android.server.wallpaper.GLHelper.getMaxTextureSize()) {
                        if (z3) {
                            wallpaperData.mCropHints.clear();
                            generateCropInternal(wallpaperData);
                            libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) null);
                            libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) null);
                            return;
                        }
                        int i4 = (int) (displayDataOrCreate.mHeight / height);
                        int i5 = (int) (displayDataOrCreate.mWidth / height);
                        rect3.set(rect);
                        rect3.left += (rect.width() - i5) / 2;
                        rect3.top += (rect.height() - i4) / 2;
                        rect3.right = rect3.left + i5;
                        rect3.bottom = rect3.top + i4;
                        rect.set(rect3);
                        rect3.scale(1.0f / options.inSampleSize);
                    }
                    int height3 = (int) ((rect3.height() * height) + 0.5f);
                    int width2 = (int) ((rect3.width() * height) + 0.5f);
                    android.util.Slog.v(TAG, "Decode parameters:");
                    android.util.Slog.v(TAG, "  cropHint=" + rect + ", estimateCrop=" + rect3);
                    android.util.Slog.v(TAG, "  down sampling=" + options.inSampleSize + ", hRatio=" + height);
                    android.util.Slog.v(TAG, "  dest=" + width + "x" + height2);
                    android.util.Slog.v(TAG, "  safe=" + width2 + "x" + height3);
                    java.lang.String str = TAG;
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("  maxTextureSize=");
                    sb.append(com.android.server.wallpaper.GLHelper.getMaxTextureSize());
                    android.util.Slog.v(str, sb.toString());
                    java.io.File file = new java.io.File(com.android.server.wallpaper.WallpaperUtils.getWallpaperDir(wallpaperData.userId), wallpaperData.getWallpaperFile().getName().equals("wallpaper_orig") ? "decode_record" : "decode_lock_record");
                    file.createNewFile();
                    android.util.Slog.v(TAG, "record path =" + file.getPath() + ", record name =" + file.getName());
                    android.graphics.Bitmap decodeBitmap = android.graphics.ImageDecoder.decodeBitmap(android.graphics.ImageDecoder.createSource(wallpaperData.getWallpaperFile()), new android.graphics.ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.server.wallpaper.WallpaperCropper$$ExternalSyntheticLambda0
                        @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                        public final void onHeaderDecoded(android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
                            com.android.server.wallpaper.WallpaperCropper.lambda$generateCropInternal$0(i2, rect3, imageDecoder, imageInfo, source);
                        }
                    });
                    file.delete();
                    if (decodeBitmap == null) {
                        android.util.Slog.e(TAG, "Could not decode new wallpaper");
                        z = false;
                        bufferedOutputStream2 = null;
                        fileOutputStream3 = null;
                    } else {
                        android.graphics.Bitmap createScaledBitmap = android.graphics.Bitmap.createScaledBitmap(decodeBitmap, width2, height3, true);
                        if (z3) {
                            wallpaperData.mSampleSize = rect.height() / createScaledBitmap.getHeight();
                        }
                        java.io.FileOutputStream fileOutputStream4 = new java.io.FileOutputStream(wallpaperData.getCropFile());
                        try {
                            bufferedOutputStream = new java.io.BufferedOutputStream(fileOutputStream4, 32768);
                        } catch (java.lang.Exception e2) {
                            fileOutputStream2 = fileOutputStream4;
                            bufferedOutputStream3 = null;
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                            fileOutputStream = fileOutputStream4;
                            bufferedOutputStream3 = null;
                        }
                        try {
                            createScaledBitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, bufferedOutputStream);
                            bufferedOutputStream.flush();
                            fileOutputStream3 = fileOutputStream4;
                            bufferedOutputStream2 = bufferedOutputStream;
                            z = true;
                        } catch (java.lang.Exception e3) {
                            fileOutputStream2 = fileOutputStream4;
                            bufferedOutputStream3 = bufferedOutputStream;
                            libcore.io.IoUtils.closeQuietly(bufferedOutputStream3);
                            libcore.io.IoUtils.closeQuietly(fileOutputStream2);
                            z = false;
                            if (!z) {
                            }
                            if (wallpaperData.getCropFile().exists()) {
                            }
                        } catch (java.lang.Throwable th3) {
                            th = th3;
                            fileOutputStream = fileOutputStream4;
                            bufferedOutputStream3 = bufferedOutputStream;
                            libcore.io.IoUtils.closeQuietly(bufferedOutputStream3);
                            libcore.io.IoUtils.closeQuietly(fileOutputStream);
                            throw th;
                        }
                    }
                    libcore.io.IoUtils.closeQuietly(bufferedOutputStream2);
                    libcore.io.IoUtils.closeQuietly(fileOutputStream3);
                } catch (java.lang.Exception e4) {
                    bufferedOutputStream3 = null;
                    fileOutputStream2 = null;
                } catch (java.lang.Throwable th4) {
                    th = th4;
                    bufferedOutputStream3 = null;
                    fileOutputStream = null;
                }
            } else {
                z = android.os.FileUtils.copyFile(wallpaperData.getWallpaperFile(), wallpaperData.getCropFile());
                if (!z) {
                    wallpaperData.getCropFile().delete();
                }
            }
            if (!z) {
                android.util.Slog.e(TAG, "Unable to apply new wallpaper");
                wallpaperData.getCropFile().delete();
            }
            if (wallpaperData.getCropFile().exists()) {
                return;
            }
            android.os.SELinux.restorecon(wallpaperData.getCropFile().getAbsoluteFile());
            return;
        }
        android.util.Slog.w(TAG, "Invalid wallpaper data");
        z = false;
        if (!z) {
        }
        if (wallpaperData.getCropFile().exists()) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$generateCropInternal$0(int i, android.graphics.Rect rect, android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
        imageDecoder.setTargetSampleSize(i);
        imageDecoder.setCrop(rect);
    }
}
