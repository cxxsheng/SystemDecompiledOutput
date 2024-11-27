package com.android.server.rollback;

/* loaded from: classes2.dex */
public interface RollbackManagerInternal {
    int notifyStagedSession(int i);

    void snapshotAndRestoreUserData(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.List<android.os.UserHandle> list, int i, long j, @android.annotation.NonNull java.lang.String str2, int i2);
}
