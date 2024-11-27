package com.android.server.storage;

/* loaded from: classes2.dex */
public class DiskStatsFileLogger {
    public static final java.lang.String APP_CACHES_KEY = "cacheSizes";
    public static final java.lang.String APP_CACHE_AGG_KEY = "cacheSize";
    public static final java.lang.String APP_DATA_KEY = "appDataSizes";
    public static final java.lang.String APP_DATA_SIZE_AGG_KEY = "appDataSize";
    public static final java.lang.String APP_SIZES_KEY = "appSizes";
    public static final java.lang.String APP_SIZE_AGG_KEY = "appSize";
    public static final java.lang.String AUDIO_KEY = "audioSize";
    public static final java.lang.String DOWNLOADS_KEY = "downloadsSize";
    public static final java.lang.String LAST_QUERY_TIMESTAMP_KEY = "queryTime";
    public static final java.lang.String MISC_KEY = "otherSize";
    public static final java.lang.String PACKAGE_NAMES_KEY = "packageNames";
    public static final java.lang.String PHOTOS_KEY = "photosSize";
    public static final java.lang.String SYSTEM_KEY = "systemSize";
    private static final java.lang.String TAG = "DiskStatsLogger";
    public static final java.lang.String VIDEOS_KEY = "videosSize";
    private long mDownloadsSize;
    private java.util.List<android.content.pm.PackageStats> mPackageStats;
    private com.android.server.storage.FileCollector.MeasurementResult mResult;
    private long mSystemSize;

    public DiskStatsFileLogger(com.android.server.storage.FileCollector.MeasurementResult measurementResult, com.android.server.storage.FileCollector.MeasurementResult measurementResult2, java.util.List<android.content.pm.PackageStats> list, long j) {
        this.mResult = measurementResult;
        this.mDownloadsSize = measurementResult2.totalAccountedSize();
        this.mSystemSize = j;
        this.mPackageStats = list;
    }

    public void dumpToFile(java.io.File file) throws java.io.FileNotFoundException {
        java.io.PrintWriter printWriter = new java.io.PrintWriter(file);
        org.json.JSONObject jsonRepresentation = getJsonRepresentation();
        if (jsonRepresentation != null) {
            printWriter.println(jsonRepresentation);
        }
        printWriter.close();
    }

    private org.json.JSONObject getJsonRepresentation() {
        org.json.JSONObject jSONObject = new org.json.JSONObject();
        try {
            jSONObject.put(LAST_QUERY_TIMESTAMP_KEY, java.lang.System.currentTimeMillis());
            jSONObject.put(PHOTOS_KEY, this.mResult.imagesSize);
            jSONObject.put(VIDEOS_KEY, this.mResult.videosSize);
            jSONObject.put(AUDIO_KEY, this.mResult.audioSize);
            jSONObject.put(DOWNLOADS_KEY, this.mDownloadsSize);
            jSONObject.put(SYSTEM_KEY, this.mSystemSize);
            jSONObject.put(MISC_KEY, this.mResult.miscSize);
            addAppsToJson(jSONObject);
            return jSONObject;
        } catch (org.json.JSONException e) {
            android.util.Log.e(TAG, e.toString());
            return null;
        }
    }

    private void addAppsToJson(org.json.JSONObject jSONObject) throws org.json.JSONException {
        boolean z;
        java.util.Iterator<java.util.Map.Entry<java.lang.String, android.content.pm.PackageStats>> it;
        org.json.JSONArray jSONArray = new org.json.JSONArray();
        org.json.JSONArray jSONArray2 = new org.json.JSONArray();
        org.json.JSONArray jSONArray3 = new org.json.JSONArray();
        org.json.JSONArray jSONArray4 = new org.json.JSONArray();
        boolean isExternalStorageEmulated = android.os.Environment.isExternalStorageEmulated();
        java.util.Iterator<java.util.Map.Entry<java.lang.String, android.content.pm.PackageStats>> it2 = filterOnlyPrimaryUser().entrySet().iterator();
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        while (it2.hasNext()) {
            android.content.pm.PackageStats value = it2.next().getValue();
            long j4 = value.codeSize;
            org.json.JSONArray jSONArray5 = jSONArray3;
            org.json.JSONArray jSONArray6 = jSONArray4;
            long j5 = value.dataSize;
            org.json.JSONArray jSONArray7 = jSONArray;
            long j6 = value.cacheSize;
            if (!isExternalStorageEmulated) {
                z = isExternalStorageEmulated;
                it = it2;
            } else {
                z = isExternalStorageEmulated;
                it = it2;
                j4 += value.externalCodeSize;
                j5 += value.externalDataSize;
                j6 += value.externalCacheSize;
            }
            j += j4;
            j3 += j5;
            j2 += j6;
            jSONArray7.put(value.packageName);
            jSONArray2.put(j4);
            jSONArray5.put(j5);
            jSONArray6.put(j6);
            jSONArray4 = jSONArray6;
            jSONArray3 = jSONArray5;
            jSONArray = jSONArray7;
            isExternalStorageEmulated = z;
            it2 = it;
        }
        jSONObject.put(PACKAGE_NAMES_KEY, jSONArray);
        jSONObject.put(APP_SIZES_KEY, jSONArray2);
        jSONObject.put(APP_CACHES_KEY, jSONArray4);
        jSONObject.put(APP_DATA_KEY, jSONArray3);
        jSONObject.put(APP_SIZE_AGG_KEY, j);
        jSONObject.put(APP_CACHE_AGG_KEY, j2);
        jSONObject.put(APP_DATA_SIZE_AGG_KEY, j3);
    }

    private android.util.ArrayMap<java.lang.String, android.content.pm.PackageStats> filterOnlyPrimaryUser() {
        android.util.ArrayMap<java.lang.String, android.content.pm.PackageStats> arrayMap = new android.util.ArrayMap<>();
        for (android.content.pm.PackageStats packageStats : this.mPackageStats) {
            if (packageStats.userHandle == 0) {
                android.content.pm.PackageStats packageStats2 = arrayMap.get(packageStats.packageName);
                if (packageStats2 != null) {
                    packageStats2.cacheSize += packageStats.cacheSize;
                    packageStats2.codeSize += packageStats.codeSize;
                    packageStats2.dataSize += packageStats.dataSize;
                    packageStats2.externalCacheSize += packageStats.externalCacheSize;
                    packageStats2.externalCodeSize += packageStats.externalCodeSize;
                    packageStats2.externalDataSize += packageStats.externalDataSize;
                } else {
                    arrayMap.put(packageStats.packageName, new android.content.pm.PackageStats(packageStats));
                }
            }
        }
        return arrayMap;
    }
}
