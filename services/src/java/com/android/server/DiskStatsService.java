package com.android.server;

/* loaded from: classes.dex */
public class DiskStatsService extends android.os.Binder {
    private static final java.lang.String DISKSTATS_DUMP_FILE = "/data/system/diskstats_cache.json";
    private static final java.lang.String TAG = "DiskStatsService";
    private final android.content.Context mContext;

    public DiskStatsService(android.content.Context context) {
        this.mContext = context;
        com.android.server.storage.DiskStatsLoggingService.schedule(context);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00a4  */
    @Override // android.os.Binder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        java.lang.Throwable th;
        java.io.IOException iOException;
        java.io.FileOutputStream fileOutputStream;
        boolean hasOption;
        android.util.proto.ProtoOutputStream protoOutputStream;
        if (com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(this.mContext, TAG, printWriter)) {
            byte[] bArr = new byte[512];
            for (int i = 0; i < 512; i++) {
                bArr[i] = (byte) i;
            }
            java.io.File file = new java.io.File(android.os.Environment.getDataDirectory(), "system/perftest.tmp");
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            java.io.FileOutputStream fileOutputStream2 = null;
            java.io.PrintWriter printWriter2 = null;
            try {
                fileOutputStream = new java.io.FileOutputStream(file);
                try {
                    fileOutputStream.write(bArr);
                    try {
                        fileOutputStream.close();
                    } catch (java.io.IOException e) {
                    }
                    iOException = null;
                } catch (java.io.IOException e2) {
                    iOException = e2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (java.io.IOException e3) {
                        }
                    }
                    long uptimeMillis2 = android.os.SystemClock.uptimeMillis();
                    if (file.exists()) {
                    }
                    hasOption = hasOption(strArr, "--proto");
                    if (hasOption) {
                    }
                    if (hasOption) {
                    }
                    java.io.PrintWriter printWriter3 = printWriter2;
                    android.util.proto.ProtoOutputStream protoOutputStream2 = protoOutputStream;
                    reportFreeSpace(android.os.Environment.getDataDirectory(), "Data", printWriter3, protoOutputStream2, 0);
                    reportFreeSpace(android.os.Environment.getDownloadCacheDirectory(), "Cache", printWriter3, protoOutputStream2, 1);
                    reportFreeSpace(new java.io.File("/system"), "System", printWriter3, protoOutputStream2, 2);
                    reportFreeSpace(android.os.Environment.getMetadataDirectory(), "Metadata", printWriter3, protoOutputStream2, 3);
                    boolean isFileEncrypted = android.os.storage.StorageManager.isFileEncrypted();
                    if (hasOption) {
                    }
                    if (hasOption) {
                    }
                    if (hasOption) {
                    }
                } catch (java.lang.Throwable th2) {
                    th = th2;
                    fileOutputStream2 = fileOutputStream;
                    if (fileOutputStream2 == null) {
                        throw th;
                    }
                    try {
                        fileOutputStream2.close();
                        throw th;
                    } catch (java.io.IOException e4) {
                        throw th;
                    }
                }
            } catch (java.io.IOException e5) {
                iOException = e5;
                fileOutputStream = null;
            } catch (java.lang.Throwable th3) {
                th = th3;
            }
            long uptimeMillis22 = android.os.SystemClock.uptimeMillis();
            if (file.exists()) {
                file.delete();
            }
            hasOption = hasOption(strArr, "--proto");
            if (hasOption) {
                if (iOException != null) {
                    printWriter.print("Test-Error: ");
                    printWriter.println(iOException.toString());
                } else {
                    printWriter.print("Latency: ");
                    printWriter.print(uptimeMillis22 - uptimeMillis);
                    printWriter.println("ms [512B Data Write]");
                }
                protoOutputStream = null;
                printWriter2 = printWriter;
            } else {
                android.util.proto.ProtoOutputStream protoOutputStream3 = new android.util.proto.ProtoOutputStream(fileDescriptor);
                protoOutputStream3.write(1133871366145L, iOException != null);
                if (iOException != null) {
                    protoOutputStream3.write(1138166333442L, iOException.toString());
                } else {
                    protoOutputStream3.write(1120986464259L, uptimeMillis22 - uptimeMillis);
                }
                protoOutputStream = protoOutputStream3;
            }
            if (hasOption) {
                reportDiskWriteSpeed(printWriter2);
            } else {
                reportDiskWriteSpeedProto(protoOutputStream);
            }
            java.io.PrintWriter printWriter32 = printWriter2;
            android.util.proto.ProtoOutputStream protoOutputStream22 = protoOutputStream;
            reportFreeSpace(android.os.Environment.getDataDirectory(), "Data", printWriter32, protoOutputStream22, 0);
            reportFreeSpace(android.os.Environment.getDownloadCacheDirectory(), "Cache", printWriter32, protoOutputStream22, 1);
            reportFreeSpace(new java.io.File("/system"), "System", printWriter32, protoOutputStream22, 2);
            reportFreeSpace(android.os.Environment.getMetadataDirectory(), "Metadata", printWriter32, protoOutputStream22, 3);
            boolean isFileEncrypted2 = android.os.storage.StorageManager.isFileEncrypted();
            if (hasOption) {
                if (isFileEncrypted2) {
                    printWriter2.println("File-based Encryption: true");
                }
            } else if (isFileEncrypted2) {
                protoOutputStream.write(1159641169925L, 3);
            } else {
                protoOutputStream.write(1159641169925L, 1);
            }
            if (hasOption) {
                reportCachedValues(printWriter2);
            } else {
                reportCachedValuesProto(protoOutputStream);
            }
            if (hasOption) {
                return;
            }
            protoOutputStream.flush();
        }
    }

    private void reportFreeSpace(java.io.File file, java.lang.String str, java.io.PrintWriter printWriter, android.util.proto.ProtoOutputStream protoOutputStream, int i) {
        try {
            android.os.StatFs statFs = new android.os.StatFs(file.getPath());
            long blockSize = statFs.getBlockSize();
            long availableBlocks = statFs.getAvailableBlocks();
            long blockCount = statFs.getBlockCount();
            if (blockSize <= 0 || blockCount <= 0) {
                throw new java.lang.IllegalArgumentException("Invalid stat: bsize=" + blockSize + " avail=" + availableBlocks + " total=" + blockCount);
            }
            if (protoOutputStream != null) {
                long start = protoOutputStream.start(2246267895812L);
                protoOutputStream.write(1159641169921L, i);
                protoOutputStream.write(1112396529666L, (availableBlocks * blockSize) / 1024);
                protoOutputStream.write(1112396529667L, (blockCount * blockSize) / 1024);
                protoOutputStream.end(start);
                return;
            }
            printWriter.print(str);
            printWriter.print("-Free: ");
            printWriter.print((availableBlocks * blockSize) / 1024);
            printWriter.print("K / ");
            printWriter.print((blockSize * blockCount) / 1024);
            printWriter.print("K total = ");
            printWriter.print((availableBlocks * 100) / blockCount);
            printWriter.println("% free");
        } catch (java.lang.IllegalArgumentException e) {
            if (protoOutputStream == null) {
                printWriter.print(str);
                printWriter.print("-Error: ");
                printWriter.println(e.toString());
            }
        }
    }

    private boolean hasOption(java.lang.String[] strArr, java.lang.String str) {
        for (java.lang.String str2 : strArr) {
            if (str.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    private void reportCachedValues(java.io.PrintWriter printWriter) {
        try {
            org.json.JSONObject jSONObject = new org.json.JSONObject(libcore.io.IoUtils.readFileAsString("/data/system/diskstats_cache.json"));
            printWriter.print("App Size: ");
            printWriter.println(jSONObject.getLong(com.android.server.storage.DiskStatsFileLogger.APP_SIZE_AGG_KEY));
            printWriter.print("App Data Size: ");
            printWriter.println(jSONObject.getLong(com.android.server.storage.DiskStatsFileLogger.APP_DATA_SIZE_AGG_KEY));
            printWriter.print("App Cache Size: ");
            printWriter.println(jSONObject.getLong(com.android.server.storage.DiskStatsFileLogger.APP_CACHE_AGG_KEY));
            printWriter.print("Photos Size: ");
            printWriter.println(jSONObject.getLong(com.android.server.storage.DiskStatsFileLogger.PHOTOS_KEY));
            printWriter.print("Videos Size: ");
            printWriter.println(jSONObject.getLong(com.android.server.storage.DiskStatsFileLogger.VIDEOS_KEY));
            printWriter.print("Audio Size: ");
            printWriter.println(jSONObject.getLong(com.android.server.storage.DiskStatsFileLogger.AUDIO_KEY));
            printWriter.print("Downloads Size: ");
            printWriter.println(jSONObject.getLong(com.android.server.storage.DiskStatsFileLogger.DOWNLOADS_KEY));
            printWriter.print("System Size: ");
            printWriter.println(jSONObject.getLong(com.android.server.storage.DiskStatsFileLogger.SYSTEM_KEY));
            printWriter.print("Other Size: ");
            printWriter.println(jSONObject.getLong(com.android.server.storage.DiskStatsFileLogger.MISC_KEY));
            printWriter.print("Package Names: ");
            printWriter.println(jSONObject.getJSONArray(com.android.server.storage.DiskStatsFileLogger.PACKAGE_NAMES_KEY));
            printWriter.print("App Sizes: ");
            printWriter.println(jSONObject.getJSONArray(com.android.server.storage.DiskStatsFileLogger.APP_SIZES_KEY));
            printWriter.print("App Data Sizes: ");
            printWriter.println(jSONObject.getJSONArray(com.android.server.storage.DiskStatsFileLogger.APP_DATA_KEY));
            printWriter.print("Cache Sizes: ");
            printWriter.println(jSONObject.getJSONArray(com.android.server.storage.DiskStatsFileLogger.APP_CACHES_KEY));
        } catch (java.io.IOException | org.json.JSONException e) {
            android.util.Log.w(TAG, "exception reading diskstats cache file", e);
        }
    }

    private void reportCachedValuesProto(android.util.proto.ProtoOutputStream protoOutputStream) {
        try {
            org.json.JSONObject jSONObject = new org.json.JSONObject(libcore.io.IoUtils.readFileAsString("/data/system/diskstats_cache.json"));
            long start = protoOutputStream.start(1146756268038L);
            protoOutputStream.write(1112396529665L, jSONObject.getLong(com.android.server.storage.DiskStatsFileLogger.APP_SIZE_AGG_KEY));
            protoOutputStream.write(1112396529674L, jSONObject.getLong(com.android.server.storage.DiskStatsFileLogger.APP_DATA_SIZE_AGG_KEY));
            protoOutputStream.write(1112396529666L, jSONObject.getLong(com.android.server.storage.DiskStatsFileLogger.APP_CACHE_AGG_KEY));
            protoOutputStream.write(1112396529667L, jSONObject.getLong(com.android.server.storage.DiskStatsFileLogger.PHOTOS_KEY));
            protoOutputStream.write(1112396529668L, jSONObject.getLong(com.android.server.storage.DiskStatsFileLogger.VIDEOS_KEY));
            protoOutputStream.write(1112396529669L, jSONObject.getLong(com.android.server.storage.DiskStatsFileLogger.AUDIO_KEY));
            protoOutputStream.write(1112396529670L, jSONObject.getLong(com.android.server.storage.DiskStatsFileLogger.DOWNLOADS_KEY));
            protoOutputStream.write(1112396529671L, jSONObject.getLong(com.android.server.storage.DiskStatsFileLogger.SYSTEM_KEY));
            protoOutputStream.write(1112396529672L, jSONObject.getLong(com.android.server.storage.DiskStatsFileLogger.MISC_KEY));
            org.json.JSONArray jSONArray = jSONObject.getJSONArray(com.android.server.storage.DiskStatsFileLogger.PACKAGE_NAMES_KEY);
            org.json.JSONArray jSONArray2 = jSONObject.getJSONArray(com.android.server.storage.DiskStatsFileLogger.APP_SIZES_KEY);
            org.json.JSONArray jSONArray3 = jSONObject.getJSONArray(com.android.server.storage.DiskStatsFileLogger.APP_DATA_KEY);
            org.json.JSONArray jSONArray4 = jSONObject.getJSONArray(com.android.server.storage.DiskStatsFileLogger.APP_CACHES_KEY);
            int length = jSONArray.length();
            if (length == jSONArray2.length() && length == jSONArray3.length() && length == jSONArray4.length()) {
                for (int i = 0; i < length; i++) {
                    long start2 = protoOutputStream.start(2246267895817L);
                    protoOutputStream.write(1138166333441L, jSONArray.getString(i));
                    protoOutputStream.write(1112396529666L, jSONArray2.getLong(i));
                    protoOutputStream.write(1112396529668L, jSONArray3.getLong(i));
                    protoOutputStream.write(1112396529667L, jSONArray4.getLong(i));
                    protoOutputStream.end(start2);
                }
            } else {
                android.util.Slog.wtf(TAG, "Sizes of packageNamesArray, appSizesArray, appDataSizesArray  and cacheSizesArray are not the same");
            }
            protoOutputStream.end(start);
        } catch (java.io.IOException | org.json.JSONException e) {
            android.util.Log.w(TAG, "exception reading diskstats cache file", e);
        }
    }

    private int getRecentPerf() throws android.os.RemoteException, java.lang.IllegalStateException {
        android.os.IBinder service = android.os.ServiceManager.getService("storaged");
        if (service == null) {
            throw new java.lang.IllegalStateException("storaged not found");
        }
        return android.os.IStoraged.Stub.asInterface(service).getRecentPerf();
    }

    private void reportDiskWriteSpeed(java.io.PrintWriter printWriter) {
        try {
            long recentPerf = getRecentPerf();
            if (recentPerf != 0) {
                printWriter.print("Recent Disk Write Speed (kB/s) = ");
                printWriter.println(recentPerf);
            } else {
                printWriter.println("Recent Disk Write Speed data unavailable");
                android.util.Log.w(TAG, "Recent Disk Write Speed data unavailable!");
            }
        } catch (android.os.RemoteException | java.lang.IllegalStateException e) {
            printWriter.println(e.toString());
            android.util.Log.e(TAG, e.toString());
        }
    }

    private void reportDiskWriteSpeedProto(android.util.proto.ProtoOutputStream protoOutputStream) {
        try {
            long recentPerf = getRecentPerf();
            if (recentPerf == 0) {
                android.util.Log.w(TAG, "Recent Disk Write Speed data unavailable!");
            } else {
                protoOutputStream.write(1120986464263L, recentPerf);
            }
        } catch (android.os.RemoteException | java.lang.IllegalStateException e) {
            android.util.Log.e(TAG, e.toString());
        }
    }
}
