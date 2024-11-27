package com.android.server.power;

/* loaded from: classes2.dex */
public class AmbientDisplaySuppressionController {
    private static final java.lang.String TAG = "AmbientDisplaySuppressionController";
    private final com.android.server.power.AmbientDisplaySuppressionController.AmbientDisplaySuppressionChangedCallback mCallback;
    private com.android.internal.statusbar.IStatusBarService mStatusBarService;
    private final java.util.Set<android.util.Pair<java.lang.String, java.lang.Integer>> mSuppressionTokens = java.util.Collections.synchronizedSet(new android.util.ArraySet());

    interface AmbientDisplaySuppressionChangedCallback {
        void onSuppressionChanged(boolean z);
    }

    AmbientDisplaySuppressionController(@android.annotation.NonNull com.android.server.power.AmbientDisplaySuppressionController.AmbientDisplaySuppressionChangedCallback ambientDisplaySuppressionChangedCallback) {
        java.util.Objects.requireNonNull(ambientDisplaySuppressionChangedCallback);
        this.mCallback = ambientDisplaySuppressionChangedCallback;
    }

    public void suppress(@android.annotation.NonNull java.lang.String str, int i, boolean z) {
        java.util.Objects.requireNonNull(str);
        android.util.Pair<java.lang.String, java.lang.Integer> create = android.util.Pair.create(str, java.lang.Integer.valueOf(i));
        boolean isSuppressed = isSuppressed();
        if (z) {
            this.mSuppressionTokens.add(create);
        } else {
            this.mSuppressionTokens.remove(create);
        }
        boolean isSuppressed2 = isSuppressed();
        if (isSuppressed2 != isSuppressed) {
            this.mCallback.onSuppressionChanged(isSuppressed2);
        }
        try {
            synchronized (this.mSuppressionTokens) {
                getStatusBar().suppressAmbientDisplay(isSuppressed2);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to suppress ambient display", e);
        }
    }

    java.util.List<java.lang.String> getSuppressionTokens(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mSuppressionTokens) {
            try {
                for (android.util.Pair<java.lang.String, java.lang.Integer> pair : this.mSuppressionTokens) {
                    if (((java.lang.Integer) pair.second).intValue() == i) {
                        arrayList.add((java.lang.String) pair.first);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public boolean isSuppressed(@android.annotation.NonNull java.lang.String str, int i) {
        java.util.Set<android.util.Pair<java.lang.String, java.lang.Integer>> set = this.mSuppressionTokens;
        java.util.Objects.requireNonNull(str);
        return set.contains(android.util.Pair.create(str, java.lang.Integer.valueOf(i)));
    }

    public boolean isSuppressed() {
        return !this.mSuppressionTokens.isEmpty();
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("AmbientDisplaySuppressionController:");
        printWriter.println(" ambientDisplaySuppressed=" + isSuppressed());
        printWriter.println(" mSuppressionTokens=" + this.mSuppressionTokens);
    }

    private synchronized com.android.internal.statusbar.IStatusBarService getStatusBar() {
        try {
            if (this.mStatusBarService == null) {
                this.mStatusBarService = com.android.internal.statusbar.IStatusBarService.Stub.asInterface(android.os.ServiceManager.getService("statusbar"));
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
        return this.mStatusBarService;
    }
}
