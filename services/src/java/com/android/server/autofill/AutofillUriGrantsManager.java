package com.android.server.autofill;

/* loaded from: classes.dex */
final class AutofillUriGrantsManager {
    private static final java.lang.String TAG = com.android.server.autofill.AutofillUriGrantsManager.class.getSimpleName();
    private final int mSourceUid;
    private final int mSourceUserId;

    @android.annotation.NonNull
    private final com.android.server.wm.ActivityTaskManagerInternal mActivityTaskMgrInternal = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);

    @android.annotation.NonNull
    private final android.app.IUriGrantsManager mUgm = android.app.UriGrantsManager.getService();

    AutofillUriGrantsManager(int i) {
        this.mSourceUid = i;
        this.mSourceUserId = android.os.UserHandle.getUserId(this.mSourceUid);
    }

    public void grantUriPermissions(@android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull android.content.ClipData clipData) {
        java.lang.String packageName = componentName.getPackageName();
        android.os.IBinder uriPermissionOwnerForActivity = this.mActivityTaskMgrInternal.getUriPermissionOwnerForActivity(iBinder);
        if (uriPermissionOwnerForActivity == null) {
            android.util.Slog.w(TAG, "Can't grant URI permissions, because the target activity token is invalid: clip=" + clipData + ", targetActivity=" + componentName + ", targetUserId=" + i + ", targetActivityToken=" + java.lang.Integer.toHexString(iBinder.hashCode()));
            return;
        }
        for (int i2 = 0; i2 < clipData.getItemCount(); i2++) {
            android.net.Uri uri = clipData.getItemAt(i2).getUri();
            if (uri != null && com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT.equals(uri.getScheme())) {
                grantUriPermissions(uri, packageName, i, uriPermissionOwnerForActivity);
            }
        }
    }

    private void grantUriPermissions(@android.annotation.NonNull android.net.Uri uri, @android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull android.os.IBinder iBinder) {
        java.lang.String str2;
        java.lang.String str3;
        java.lang.String str4;
        java.lang.String str5;
        int userIdFromUri = android.content.ContentProvider.getUserIdFromUri(uri, this.mSourceUserId);
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "Granting URI permissions: uri=" + uri + ", sourceUid=" + this.mSourceUid + ", sourceUserId=" + userIdFromUri + ", targetPkg=" + str + ", targetUserId=" + i + ", permissionOwner=" + java.lang.Integer.toHexString(iBinder.hashCode()));
        }
        android.net.Uri uriWithoutUserId = android.content.ContentProvider.getUriWithoutUserId(uri);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                str4 = ", permissionOwner=";
                str5 = ", sourceUid=";
                str2 = ", sourceUserId=";
                str3 = ", targetPkg=";
                try {
                    this.mUgm.grantUriPermissionFromOwner(iBinder, this.mSourceUid, str, uriWithoutUserId, 1, userIdFromUri, i);
                } catch (android.os.RemoteException e) {
                    e = e;
                    android.util.Slog.e(TAG, "Granting URI permissions failed: uri=" + uri + str5 + this.mSourceUid + str2 + userIdFromUri + str3 + str + ", targetUserId=" + i + str4 + java.lang.Integer.toHexString(iBinder.hashCode()), e);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (android.os.RemoteException e2) {
            e = e2;
            str2 = ", sourceUserId=";
            str3 = ", targetPkg=";
            str4 = ", permissionOwner=";
            str5 = ", sourceUid=";
        }
    }
}
