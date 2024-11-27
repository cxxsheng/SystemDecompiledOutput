package com.android.internal.protolog;

/* loaded from: classes5.dex */
public class LegacyProtoLogViewerConfigReader {
    private static final java.lang.String TAG = "ProtoLogViewerConfigReader";
    private java.util.Map<java.lang.Long, java.lang.String> mLogMessageMap = null;

    public synchronized java.lang.String getViewerString(long j) {
        if (this.mLogMessageMap == null) {
            return null;
        }
        return this.mLogMessageMap.get(java.lang.Long.valueOf(j));
    }

    public synchronized void loadViewerConfig(com.android.internal.protolog.common.ILogger iLogger, java.lang.String str) {
        try {
            try {
                loadViewerConfig(new java.util.zip.GZIPInputStream(new java.io.FileInputStream(str)));
                iLogger.log("Loaded " + this.mLogMessageMap.size() + " log definitions from " + str);
            } catch (java.io.IOException e) {
                iLogger.log("Unable to load log definitions: IOException while reading " + str + ". " + e);
            } catch (org.json.JSONException e2) {
                iLogger.log("Unable to load log definitions: JSON parsing exception while reading " + str + ". " + e2);
            }
        } catch (java.io.FileNotFoundException e3) {
            iLogger.log("Unable to load log definitions: File " + str + " not found." + e3);
        }
    }

    public synchronized void loadViewerConfig(java.io.InputStream inputStream) throws java.io.IOException, org.json.JSONException {
        if (this.mLogMessageMap != null) {
            return;
        }
        java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(inputStream));
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        while (true) {
            java.lang.String readLine = bufferedReader.readLine();
            if (readLine == null) {
                break;
            } else {
                sb.append(readLine).append('\n');
            }
        }
        bufferedReader.close();
        org.json.JSONObject jSONObject = new org.json.JSONObject(sb.toString()).getJSONObject("messages");
        this.mLogMessageMap = new java.util.TreeMap();
        java.util.Iterator<java.lang.String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            java.lang.String next = keys.next();
            try {
                long parseLong = java.lang.Long.parseLong(next);
                this.mLogMessageMap.put(java.lang.Long.valueOf(parseLong), jSONObject.getJSONObject(next).getString(android.app.slice.Slice.SUBTYPE_MESSAGE));
            } catch (java.lang.NumberFormatException e) {
            }
        }
    }

    public synchronized int knownViewerStringsNumber() {
        if (this.mLogMessageMap == null) {
            return 0;
        }
        return this.mLogMessageMap.size();
    }
}
