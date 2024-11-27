package com.android.server.devicepolicy;

/* loaded from: classes.dex */
public class CryptoTestHelper {
    private static native int runSelfTest();

    public static void runAndLogSelfTest() {
        android.app.admin.SecurityLog.writeEvent(210031, new java.lang.Object[]{java.lang.Integer.valueOf(runSelfTest())});
    }
}
