package com.android.server;

/* loaded from: classes5.dex */
public class DropboxLogTags {
    public static final int DROPBOX_FILE_COPY = 81002;

    private DropboxLogTags() {
    }

    public static void writeDropboxFileCopy(java.lang.String str, int i, java.lang.String str2) {
        android.util.EventLog.writeEvent(DROPBOX_FILE_COPY, str, java.lang.Integer.valueOf(i), str2);
    }
}
