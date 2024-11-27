package com.android.server.accessibility.magnification;

/* loaded from: classes.dex */
public class MagnificationScaleProvider {

    @com.android.internal.annotations.VisibleForTesting
    protected static final float DEFAULT_MAGNIFICATION_SCALE = 2.0f;
    public static final float MAX_SCALE = 8.0f;
    public static final float MIN_SCALE = 1.0f;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<android.util.SparseArray<java.lang.Float>> mUsersScales = new android.util.SparseArray<>();
    private int mCurrentUserId = 0;
    private final java.lang.Object mLock = new java.lang.Object();

    public MagnificationScaleProvider(android.content.Context context) {
        this.mContext = context;
    }

    void putScale(final float f, int i) {
        if (i == 0) {
            com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.accessibility.magnification.MagnificationScaleProvider$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.accessibility.magnification.MagnificationScaleProvider.this.lambda$putScale$0(f);
                }
            });
            return;
        }
        synchronized (this.mLock) {
            getScalesWithCurrentUser().put(i, java.lang.Float.valueOf(f));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putScale$0(float f) {
        android.provider.Settings.Secure.putFloatForUser(this.mContext.getContentResolver(), "accessibility_display_magnification_scale", f, this.mCurrentUserId);
    }

    float getScale(int i) {
        float floatValue;
        if (i == 0) {
            return android.provider.Settings.Secure.getFloatForUser(this.mContext.getContentResolver(), "accessibility_display_magnification_scale", DEFAULT_MAGNIFICATION_SCALE, this.mCurrentUserId);
        }
        synchronized (this.mLock) {
            floatValue = getScalesWithCurrentUser().get(i, java.lang.Float.valueOf(DEFAULT_MAGNIFICATION_SCALE)).floatValue();
        }
        return floatValue;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.SparseArray<java.lang.Float> getScalesWithCurrentUser() {
        android.util.SparseArray<java.lang.Float> sparseArray = this.mUsersScales.get(this.mCurrentUserId);
        if (sparseArray == null) {
            android.util.SparseArray<java.lang.Float> sparseArray2 = new android.util.SparseArray<>();
            this.mUsersScales.put(this.mCurrentUserId, sparseArray2);
            return sparseArray2;
        }
        return sparseArray;
    }

    void onUserChanged(int i) {
        synchronized (this.mLock) {
            this.mCurrentUserId = i;
        }
    }

    void onUserRemoved(int i) {
        synchronized (this.mLock) {
            this.mUsersScales.remove(i);
        }
    }

    void onDisplayRemoved(int i) {
        synchronized (this.mLock) {
            try {
                for (int size = this.mUsersScales.size() - 1; size >= 0; size--) {
                    this.mUsersScales.get(size).remove(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public java.lang.String toString() {
        java.lang.String str;
        synchronized (this.mLock) {
            str = "MagnificationScaleProvider{mCurrentUserId=" + this.mCurrentUserId + "Scale on the default display=" + getScale(0) + "Scales on non-default displays=" + getScalesWithCurrentUser() + '}';
        }
        return str;
    }

    static float constrainScale(float f) {
        return android.util.MathUtils.constrain(f, 1.0f, 8.0f);
    }
}
