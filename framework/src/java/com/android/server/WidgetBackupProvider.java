package com.android.server;

/* loaded from: classes5.dex */
public interface WidgetBackupProvider {
    java.util.List<java.lang.String> getWidgetParticipants(int i);

    byte[] getWidgetState(java.lang.String str, int i);

    void restoreWidgetState(java.lang.String str, byte[] bArr, int i);

    void systemRestoreFinished(int i);

    void systemRestoreStarting(int i);
}
