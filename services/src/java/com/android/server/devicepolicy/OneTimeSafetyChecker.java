package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class OneTimeSafetyChecker implements android.app.admin.DevicePolicySafetyChecker {
    private static final long SELF_DESTRUCT_TIMEOUT_MS = 10000;
    private static final java.lang.String TAG = com.android.server.devicepolicy.OneTimeSafetyChecker.class.getSimpleName();
    private boolean mDone;
    private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
    private final int mOperation;
    private final android.app.admin.DevicePolicySafetyChecker mRealSafetyChecker;
    private final int mReason;
    private final com.android.server.devicepolicy.DevicePolicyManagerService mService;

    OneTimeSafetyChecker(com.android.server.devicepolicy.DevicePolicyManagerService devicePolicyManagerService, int i, int i2) {
        java.util.Objects.requireNonNull(devicePolicyManagerService);
        this.mService = devicePolicyManagerService;
        this.mOperation = i;
        this.mReason = i2;
        this.mRealSafetyChecker = devicePolicyManagerService.getDevicePolicySafetyChecker();
        android.util.Slog.i(TAG, "OneTimeSafetyChecker constructor: operation=" + android.app.admin.DevicePolicyManager.operationToString(i) + ", reason=" + android.app.admin.DevicePolicyManager.operationSafetyReasonToString(i2) + ", realChecker=" + this.mRealSafetyChecker + ", maxDuration=10000ms");
        this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.devicepolicy.OneTimeSafetyChecker$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.devicepolicy.OneTimeSafetyChecker.this.lambda$new$0();
            }
        }, 10000L);
    }

    public int getUnsafeOperationReason(int i) {
        int i2;
        java.lang.String operationToString = android.app.admin.DevicePolicyManager.operationToString(i);
        android.util.Slog.i(TAG, "getUnsafeOperationReason(" + operationToString + ")");
        if (i == this.mOperation) {
            i2 = this.mReason;
        } else {
            android.util.Slog.wtf(TAG, "invalid call to isDevicePolicyOperationSafe(): asked for " + operationToString + ", should be " + android.app.admin.DevicePolicyManager.operationToString(this.mOperation));
            i2 = -1;
        }
        java.lang.String operationSafetyReasonToString = android.app.admin.DevicePolicyManager.operationSafetyReasonToString(i2);
        android.app.admin.DevicePolicyManagerLiteInternal devicePolicyManagerLiteInternal = (android.app.admin.DevicePolicyManagerLiteInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerLiteInternal.class);
        android.util.Slog.i(TAG, "notifying " + operationSafetyReasonToString + " is UNSAFE");
        devicePolicyManagerLiteInternal.notifyUnsafeOperationStateChanged(this, i2, false);
        android.util.Slog.i(TAG, "notifying " + operationSafetyReasonToString + " is SAFE");
        devicePolicyManagerLiteInternal.notifyUnsafeOperationStateChanged(this, i2, true);
        android.util.Slog.i(TAG, "returning " + operationSafetyReasonToString);
        disableSelf();
        return i2;
    }

    public boolean isSafeOperation(int i) {
        boolean z = this.mReason != i;
        android.util.Slog.i(TAG, "isSafeOperation(" + android.app.admin.DevicePolicyManager.operationSafetyReasonToString(i) + "): " + z);
        disableSelf();
        return z;
    }

    public void onFactoryReset(com.android.internal.os.IResultReceiver iResultReceiver) {
        throw new java.lang.UnsupportedOperationException();
    }

    private void disableSelf() {
        if (this.mDone) {
            android.util.Slog.w(TAG, "disableSelf(): already disabled");
            return;
        }
        android.util.Slog.i(TAG, "restoring DevicePolicySafetyChecker to " + this.mRealSafetyChecker);
        this.mService.setDevicePolicySafetyCheckerUnchecked(this.mRealSafetyChecker);
        this.mDone = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: selfDestruct, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0() {
        if (this.mDone) {
            return;
        }
        android.util.Slog.e(TAG, "Self destructing " + this + ", as it was not automatically disabled");
        disableSelf();
    }

    public java.lang.String toString() {
        return "OneTimeSafetyChecker[id=" + java.lang.System.identityHashCode(this) + ", reason=" + android.app.admin.DevicePolicyManager.operationSafetyReasonToString(this.mReason) + ", operation=" + android.app.admin.DevicePolicyManager.operationToString(this.mOperation) + ']';
    }
}
