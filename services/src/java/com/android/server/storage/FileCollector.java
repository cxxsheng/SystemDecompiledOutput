package com.android.server.storage;

/* loaded from: classes2.dex */
public class FileCollector {
    private static final int AUDIO = 2;
    private static final java.util.Map<java.lang.String, java.lang.Integer> EXTENSION_MAP = new android.util.ArrayMap();
    private static final int IMAGES = 0;
    private static final int UNRECOGNIZED = -1;
    private static final int VIDEO = 1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface FileTypes {
    }

    static {
        EXTENSION_MAP.put("aac", 2);
        EXTENSION_MAP.put("amr", 2);
        EXTENSION_MAP.put("awb", 2);
        EXTENSION_MAP.put("snd", 2);
        EXTENSION_MAP.put("flac", 2);
        EXTENSION_MAP.put("mp3", 2);
        EXTENSION_MAP.put("mpga", 2);
        EXTENSION_MAP.put("mpega", 2);
        EXTENSION_MAP.put("mp2", 2);
        EXTENSION_MAP.put("m4a", 2);
        EXTENSION_MAP.put("aif", 2);
        EXTENSION_MAP.put("aiff", 2);
        EXTENSION_MAP.put("aifc", 2);
        EXTENSION_MAP.put("gsm", 2);
        EXTENSION_MAP.put("mka", 2);
        EXTENSION_MAP.put("m3u", 2);
        EXTENSION_MAP.put("wma", 2);
        EXTENSION_MAP.put("wax", 2);
        EXTENSION_MAP.put("ra", 2);
        EXTENSION_MAP.put("rm", 2);
        EXTENSION_MAP.put("ram", 2);
        EXTENSION_MAP.put("pls", 2);
        EXTENSION_MAP.put("sd2", 2);
        EXTENSION_MAP.put("wav", 2);
        EXTENSION_MAP.put("ogg", 2);
        EXTENSION_MAP.put("oga", 2);
        EXTENSION_MAP.put("3gpp", 1);
        EXTENSION_MAP.put("3gp", 1);
        EXTENSION_MAP.put("3gpp2", 1);
        EXTENSION_MAP.put("3g2", 1);
        EXTENSION_MAP.put("avi", 1);
        EXTENSION_MAP.put("dl", 1);
        EXTENSION_MAP.put("dif", 1);
        EXTENSION_MAP.put("dv", 1);
        EXTENSION_MAP.put("fli", 1);
        EXTENSION_MAP.put("m4v", 1);
        EXTENSION_MAP.put("ts", 1);
        EXTENSION_MAP.put("mpeg", 1);
        EXTENSION_MAP.put("mpg", 1);
        EXTENSION_MAP.put("mpe", 1);
        EXTENSION_MAP.put("mp4", 1);
        EXTENSION_MAP.put("vob", 1);
        EXTENSION_MAP.put("qt", 1);
        EXTENSION_MAP.put("mov", 1);
        EXTENSION_MAP.put("mxu", 1);
        EXTENSION_MAP.put("webm", 1);
        EXTENSION_MAP.put("lsf", 1);
        EXTENSION_MAP.put("lsx", 1);
        EXTENSION_MAP.put("mkv", 1);
        EXTENSION_MAP.put("mng", 1);
        EXTENSION_MAP.put("asf", 1);
        EXTENSION_MAP.put("asx", 1);
        EXTENSION_MAP.put("wm", 1);
        EXTENSION_MAP.put("wmv", 1);
        EXTENSION_MAP.put("wmx", 1);
        EXTENSION_MAP.put("wvx", 1);
        EXTENSION_MAP.put("movie", 1);
        EXTENSION_MAP.put("wrf", 1);
        EXTENSION_MAP.put("bmp", 0);
        EXTENSION_MAP.put("gif", 0);
        EXTENSION_MAP.put("jpg", 0);
        EXTENSION_MAP.put("jpeg", 0);
        EXTENSION_MAP.put("jpe", 0);
        EXTENSION_MAP.put("pcx", 0);
        EXTENSION_MAP.put("png", 0);
        EXTENSION_MAP.put("svg", 0);
        EXTENSION_MAP.put("svgz", 0);
        EXTENSION_MAP.put("tiff", 0);
        EXTENSION_MAP.put("tif", 0);
        EXTENSION_MAP.put("wbmp", 0);
        EXTENSION_MAP.put("webp", 0);
        EXTENSION_MAP.put("dng", 0);
        EXTENSION_MAP.put("cr2", 0);
        EXTENSION_MAP.put("ras", 0);
        EXTENSION_MAP.put("art", 0);
        EXTENSION_MAP.put("jng", 0);
        EXTENSION_MAP.put("nef", 0);
        EXTENSION_MAP.put("nrw", 0);
        EXTENSION_MAP.put("orf", 0);
        EXTENSION_MAP.put("rw2", 0);
        EXTENSION_MAP.put("pef", 0);
        EXTENSION_MAP.put("psd", 0);
        EXTENSION_MAP.put("pnm", 0);
        EXTENSION_MAP.put("pbm", 0);
        EXTENSION_MAP.put("pgm", 0);
        EXTENSION_MAP.put("ppm", 0);
        EXTENSION_MAP.put("srw", 0);
        EXTENSION_MAP.put("arw", 0);
        EXTENSION_MAP.put("rgb", 0);
        EXTENSION_MAP.put("xbm", 0);
        EXTENSION_MAP.put("xpm", 0);
        EXTENSION_MAP.put("xwd", 0);
    }

    public static com.android.server.storage.FileCollector.MeasurementResult getMeasurementResult(java.io.File file) {
        return collectFiles(android.os.storage.StorageManager.maybeTranslateEmulatedPathToInternal(file), new com.android.server.storage.FileCollector.MeasurementResult());
    }

    public static com.android.server.storage.FileCollector.MeasurementResult getMeasurementResult(android.content.Context context) {
        com.android.server.storage.FileCollector.MeasurementResult measurementResult = new com.android.server.storage.FileCollector.MeasurementResult();
        try {
            android.app.usage.ExternalStorageStats queryExternalStatsForUser = ((android.app.usage.StorageStatsManager) context.getSystemService("storagestats")).queryExternalStatsForUser(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL, android.os.UserHandle.of(context.getUserId()));
            measurementResult.imagesSize = queryExternalStatsForUser.getImageBytes();
            measurementResult.videosSize = queryExternalStatsForUser.getVideoBytes();
            measurementResult.audioSize = queryExternalStatsForUser.getAudioBytes();
            measurementResult.miscSize = ((queryExternalStatsForUser.getTotalBytes() - measurementResult.imagesSize) - measurementResult.videosSize) - measurementResult.audioSize;
            return measurementResult;
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("Could not query storage");
        }
    }

    public static long getSystemSize(android.content.Context context) {
        java.io.File path;
        android.os.storage.VolumeInfo primaryStorageCurrentVolume = context.getPackageManager().getPrimaryStorageCurrentVolume();
        android.os.storage.StorageManager storageManager = (android.os.storage.StorageManager) context.getSystemService("storage");
        android.os.storage.VolumeInfo findEmulatedForPrivate = storageManager.findEmulatedForPrivate(primaryStorageCurrentVolume);
        if (findEmulatedForPrivate == null || (path = findEmulatedForPrivate.getPath()) == null) {
            return 0L;
        }
        long primaryStorageSize = storageManager.getPrimaryStorageSize() - path.getTotalSpace();
        if (primaryStorageSize <= 0) {
            return 0L;
        }
        return primaryStorageSize;
    }

    private static com.android.server.storage.FileCollector.MeasurementResult collectFiles(java.io.File file, com.android.server.storage.FileCollector.MeasurementResult measurementResult) {
        java.io.File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return measurementResult;
        }
        for (java.io.File file2 : listFiles) {
            if (file2.isDirectory()) {
                try {
                    collectFiles(file2, measurementResult);
                } catch (java.lang.StackOverflowError e) {
                    return measurementResult;
                }
            } else {
                handleFile(measurementResult, file2);
            }
        }
        return measurementResult;
    }

    private static void handleFile(com.android.server.storage.FileCollector.MeasurementResult measurementResult, java.io.File file) {
        long length = file.length();
        switch (EXTENSION_MAP.getOrDefault(getExtensionForFile(file), -1).intValue()) {
            case 0:
                measurementResult.imagesSize += length;
                break;
            case 1:
                measurementResult.videosSize += length;
                break;
            case 2:
                measurementResult.audioSize += length;
                break;
            default:
                measurementResult.miscSize += length;
                break;
        }
    }

    private static java.lang.String getExtensionForFile(java.io.File file) {
        java.lang.String name = file.getName();
        int lastIndexOf = name.lastIndexOf(46);
        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf + 1).toLowerCase();
    }

    public static class MeasurementResult {
        public long audioSize;
        public long imagesSize;
        public long miscSize;
        public long videosSize;

        public long totalAccountedSize() {
            return this.imagesSize + this.videosSize + this.miscSize + this.audioSize;
        }
    }
}
