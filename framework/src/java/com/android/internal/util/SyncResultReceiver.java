package com.android.internal.util;

/* loaded from: classes5.dex */
public final class SyncResultReceiver extends com.android.internal.os.IResultReceiver.Stub {
    private static final java.lang.String EXTRA = "EXTRA";
    private android.os.Bundle mBundle;
    private final java.util.concurrent.CountDownLatch mLatch = new java.util.concurrent.CountDownLatch(1);
    private int mResult;
    private final int mTimeoutMs;

    public SyncResultReceiver(int i) {
        this.mTimeoutMs = i;
    }

    private void waitResult() throws com.android.internal.util.SyncResultReceiver.TimeoutException {
        try {
            if (!this.mLatch.await(this.mTimeoutMs, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                throw new com.android.internal.util.SyncResultReceiver.TimeoutException("Not called in " + this.mTimeoutMs + "ms");
            }
        } catch (java.lang.InterruptedException e) {
            java.lang.Thread.currentThread().interrupt();
            throw new com.android.internal.util.SyncResultReceiver.TimeoutException("Interrupted");
        }
    }

    public int getIntResult() throws com.android.internal.util.SyncResultReceiver.TimeoutException {
        waitResult();
        return this.mResult;
    }

    public java.lang.String getStringResult() throws com.android.internal.util.SyncResultReceiver.TimeoutException {
        waitResult();
        if (this.mBundle == null) {
            return null;
        }
        return this.mBundle.getString("EXTRA");
    }

    public java.lang.String[] getStringArrayResult() throws com.android.internal.util.SyncResultReceiver.TimeoutException {
        waitResult();
        if (this.mBundle == null) {
            return null;
        }
        return this.mBundle.getStringArray("EXTRA");
    }

    public <P extends android.os.Parcelable> P getParcelableResult() throws com.android.internal.util.SyncResultReceiver.TimeoutException {
        waitResult();
        if (this.mBundle == null) {
            return null;
        }
        return (P) this.mBundle.getParcelable("EXTRA");
    }

    public <P extends android.os.Parcelable> java.util.ArrayList<P> getParcelableListResult() throws com.android.internal.util.SyncResultReceiver.TimeoutException {
        waitResult();
        if (this.mBundle == null) {
            return null;
        }
        return this.mBundle.getParcelableArrayList("EXTRA");
    }

    public int getOptionalExtraIntResult(int i) throws com.android.internal.util.SyncResultReceiver.TimeoutException {
        waitResult();
        if (this.mBundle == null || !this.mBundle.containsKey("EXTRA")) {
            return i;
        }
        return this.mBundle.getInt("EXTRA");
    }

    @Override // com.android.internal.os.IResultReceiver
    public void send(int i, android.os.Bundle bundle) {
        this.mResult = i;
        this.mBundle = bundle;
        this.mLatch.countDown();
    }

    public static android.os.Bundle bundleFor(java.lang.String str) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("EXTRA", str);
        return bundle;
    }

    public static android.os.Bundle bundleFor(java.lang.String[] strArr) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putStringArray("EXTRA", strArr);
        return bundle;
    }

    public static android.os.Bundle bundleFor(android.os.Parcelable parcelable) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelable("EXTRA", parcelable);
        return bundle;
    }

    public static android.os.Bundle bundleFor(java.util.ArrayList<? extends android.os.Parcelable> arrayList) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelableArrayList("EXTRA", arrayList);
        return bundle;
    }

    public static android.os.Bundle bundleFor(int i) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putInt("EXTRA", i);
        return bundle;
    }

    public static final class TimeoutException extends java.lang.Exception {
        private TimeoutException(java.lang.String str) {
            super(str);
        }
    }
}
