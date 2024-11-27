package android.app.admin;

/* loaded from: classes.dex */
public interface DevicePolicySafetyChecker {
    int getUnsafeOperationReason(int i);

    boolean isSafeOperation(int i);

    void onFactoryReset(com.android.internal.os.IResultReceiver iResultReceiver);

    default android.app.admin.UnsafeStateException newUnsafeStateException(int i, int i2) {
        return new android.app.admin.UnsafeStateException(i, i2);
    }
}
