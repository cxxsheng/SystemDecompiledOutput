package com.android.internal.protolog;

/* loaded from: classes5.dex */
public class ProtoLogViewerConfigReader {
    private java.util.Map<java.lang.Long, java.lang.String> mLogMessageMap = null;
    private final com.android.internal.protolog.ViewerConfigInputStreamProvider mViewerConfigInputStreamProvider;

    public ProtoLogViewerConfigReader(com.android.internal.protolog.ViewerConfigInputStreamProvider viewerConfigInputStreamProvider) {
        this.mViewerConfigInputStreamProvider = viewerConfigInputStreamProvider;
    }

    public synchronized java.lang.String getViewerString(long j) {
        if (this.mLogMessageMap == null) {
            return null;
        }
        return this.mLogMessageMap.get(java.lang.Long.valueOf(j));
    }

    public synchronized void loadViewerConfig(com.android.internal.protolog.common.ILogger iLogger) {
        if (this.mLogMessageMap != null) {
            return;
        }
        try {
            doLoadViewerConfig();
            iLogger.log("Loaded " + this.mLogMessageMap.size() + " log definitions");
        } catch (java.io.IOException e) {
            iLogger.log("Unable to load log definitions: IOException while processing viewer config" + e);
        }
    }

    public synchronized void unloadViewerConfig() {
        this.mLogMessageMap = null;
    }

    private void doLoadViewerConfig() throws java.io.IOException {
        android.util.proto.ProtoInputStream inputStream = this.mViewerConfigInputStreamProvider.getInputStream();
        while (inputStream.nextField() != -1) {
            if (inputStream.getFieldNumber() == 1) {
                long start = inputStream.start(2246267895809L);
                java.lang.String str = null;
                long j = 0;
                while (inputStream.nextField() != -1) {
                    switch (inputStream.getFieldNumber()) {
                        case 1:
                            j = inputStream.readLong(1125281431553L);
                            break;
                        case 2:
                            str = inputStream.readString(1138166333442L);
                            break;
                    }
                }
                if (j == 0) {
                    throw new java.io.IOException("Failed to get message id");
                }
                if (str == null) {
                    throw new java.io.IOException("Failed to get message string");
                }
                this.mLogMessageMap.put(java.lang.Long.valueOf(j), str);
                inputStream.end(start);
            }
        }
    }
}
