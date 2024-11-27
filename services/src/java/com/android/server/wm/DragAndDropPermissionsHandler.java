package com.android.server.wm;

/* loaded from: classes3.dex */
class DragAndDropPermissionsHandler extends com.android.internal.view.IDragAndDropPermissions.Stub {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "DragAndDrop";
    private final com.android.server.wm.WindowManagerGlobalLock mGlobalLock;
    private final int mMode;
    private final int mSourceUid;
    private final int mSourceUserId;
    private final java.lang.String mTargetPackage;
    private final int mTargetUserId;
    private final java.util.ArrayList<android.net.Uri> mUris = new java.util.ArrayList<>();
    private android.os.IBinder mActivityToken = null;
    private android.os.IBinder mPermissionOwnerToken = null;

    DragAndDropPermissionsHandler(com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock, android.content.ClipData clipData, int i, java.lang.String str, int i2, int i3, int i4) {
        this.mGlobalLock = windowManagerGlobalLock;
        this.mSourceUid = i;
        this.mTargetPackage = str;
        this.mMode = i2;
        this.mSourceUserId = i3;
        this.mTargetUserId = i4;
        clipData.collectUris(this.mUris);
    }

    public void take(android.os.IBinder iBinder) throws android.os.RemoteException {
        if (this.mActivityToken != null || this.mPermissionOwnerToken != null) {
            return;
        }
        this.mActivityToken = iBinder;
        doTake(getUriPermissionOwnerForActivity(this.mActivityToken));
    }

    private void doTake(android.os.IBinder iBinder) throws android.os.RemoteException {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        for (int i = 0; i < this.mUris.size(); i++) {
            try {
                android.app.UriGrantsManager.getService().grantUriPermissionFromOwner(iBinder, this.mSourceUid, this.mTargetPackage, this.mUris.get(i), this.mMode, this.mSourceUserId, this.mTargetUserId);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void takeTransient() throws android.os.RemoteException {
        if (this.mActivityToken != null || this.mPermissionOwnerToken != null) {
            return;
        }
        this.mPermissionOwnerToken = ((com.android.server.uri.UriGrantsManagerInternal) com.android.server.LocalServices.getService(com.android.server.uri.UriGrantsManagerInternal.class)).newUriPermissionOwner("drop");
        doTake(this.mPermissionOwnerToken);
    }

    public void release() throws android.os.RemoteException {
        android.os.IBinder uriPermissionOwnerForActivity;
        if (this.mActivityToken == null && this.mPermissionOwnerToken == null) {
            return;
        }
        if (this.mActivityToken != null) {
            try {
                uriPermissionOwnerForActivity = getUriPermissionOwnerForActivity(this.mActivityToken);
            } catch (java.lang.Exception e) {
                return;
            } finally {
                this.mActivityToken = null;
            }
        } else {
            uriPermissionOwnerForActivity = this.mPermissionOwnerToken;
            this.mPermissionOwnerToken = null;
        }
        com.android.server.uri.UriGrantsManagerInternal uriGrantsManagerInternal = (com.android.server.uri.UriGrantsManagerInternal) com.android.server.LocalServices.getService(com.android.server.uri.UriGrantsManagerInternal.class);
        for (int i = 0; i < this.mUris.size(); i++) {
            uriGrantsManagerInternal.revokeUriPermissionFromOwner(uriPermissionOwnerForActivity, this.mUris.get(i), this.mMode, this.mSourceUserId);
        }
    }

    private android.os.IBinder getUriPermissionOwnerForActivity(android.os.IBinder iBinder) {
        android.os.Binder externalToken;
        com.android.server.wm.ActivityTaskManagerService.enforceNotIsolatedCaller("getUriPermissionOwnerForActivity");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked == null) {
                    throw new java.lang.IllegalArgumentException("Activity does not exist; token=" + iBinder);
                }
                externalToken = isInRootTaskLocked.getUriPermissionsLocked().getExternalToken();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return externalToken;
    }

    protected void finalize() throws java.lang.Throwable {
        if (this.mActivityToken != null || this.mPermissionOwnerToken == null) {
            return;
        }
        release();
    }
}
