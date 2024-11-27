package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class InputContentUriTokenHandler extends com.android.internal.inputmethod.IInputContentUriToken.Stub {
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.os.IBinder mPermissionOwnerToken = null;
    private final int mSourceUid;
    private final int mSourceUserId;

    @android.annotation.NonNull
    private final java.lang.String mTargetPackage;
    private final int mTargetUserId;

    @android.annotation.NonNull
    private final android.net.Uri mUri;

    InputContentUriTokenHandler(@android.annotation.NonNull android.net.Uri uri, int i, @android.annotation.NonNull java.lang.String str, int i2, int i3) {
        this.mUri = uri;
        this.mSourceUid = i;
        this.mTargetPackage = str;
        this.mSourceUserId = i2;
        this.mTargetUserId = i3;
    }

    public void take() {
        synchronized (this.mLock) {
            try {
                if (this.mPermissionOwnerToken != null) {
                    return;
                }
                this.mPermissionOwnerToken = ((com.android.server.uri.UriGrantsManagerInternal) com.android.server.LocalServices.getService(com.android.server.uri.UriGrantsManagerInternal.class)).newUriPermissionOwner("InputContentUriTokenHandler");
                doTakeLocked(this.mPermissionOwnerToken);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void doTakeLocked(@android.annotation.NonNull android.os.IBinder iBinder) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                android.app.UriGrantsManager.getService().grantUriPermissionFromOwner(iBinder, this.mSourceUid, this.mTargetPackage, this.mUri, 1, this.mSourceUserId, this.mTargetUserId);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void release() {
        synchronized (this.mLock) {
            if (this.mPermissionOwnerToken == null) {
                return;
            }
            try {
                ((com.android.server.uri.UriGrantsManagerInternal) com.android.server.LocalServices.getService(com.android.server.uri.UriGrantsManagerInternal.class)).revokeUriPermissionFromOwner(this.mPermissionOwnerToken, this.mUri, 1, this.mSourceUserId);
            } finally {
                this.mPermissionOwnerToken = null;
            }
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            release();
        } finally {
            super/*java.lang.Object*/.finalize();
        }
    }
}
