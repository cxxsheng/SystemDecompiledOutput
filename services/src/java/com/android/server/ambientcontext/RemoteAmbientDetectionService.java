package com.android.server.ambientcontext;

/* loaded from: classes.dex */
interface RemoteAmbientDetectionService {
    void dump(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.io.PrintWriter printWriter);

    void queryServiceStatus(int[] iArr, java.lang.String str, android.os.RemoteCallback remoteCallback);

    void startDetection(@android.annotation.NonNull android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.lang.String str, android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2);

    void stopDetection(java.lang.String str);

    void unbind();
}
