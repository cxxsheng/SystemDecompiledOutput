package com.android.server.contentcapture;

/* loaded from: classes.dex */
final class ContentCaptureServerSession {
    private static final java.lang.String TAG = com.android.server.contentcapture.ContentCaptureServerSession.class.getSimpleName();
    public final android.content.ComponentName appComponentName;
    final android.os.IBinder mActivityToken;
    private final android.view.contentcapture.ContentCaptureContext mContentCaptureContext;
    private final int mId;
    private final java.lang.Object mLock;
    private final com.android.server.contentcapture.ContentCapturePerUserService mService;

    @android.annotation.NonNull
    private final com.android.internal.os.IResultReceiver mSessionStateReceiver;
    private final int mUid;

    ContentCaptureServerSession(@android.annotation.NonNull java.lang.Object obj, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.app.assist.ActivityId activityId, @android.annotation.NonNull com.android.server.contentcapture.ContentCapturePerUserService contentCapturePerUserService, @android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver, int i, int i2, int i3, int i4, int i5) {
        com.android.internal.util.Preconditions.checkArgument(i3 != 0);
        this.mLock = obj;
        this.mActivityToken = iBinder;
        this.appComponentName = componentName;
        this.mService = contentCapturePerUserService;
        this.mId = i3;
        this.mUid = i4;
        this.mContentCaptureContext = new android.view.contentcapture.ContentCaptureContext(null, activityId, componentName, i2, iBinder, i5);
        this.mSessionStateReceiver = iResultReceiver;
        try {
            iResultReceiver.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.contentcapture.ContentCaptureServerSession$$ExternalSyntheticLambda0
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    com.android.server.contentcapture.ContentCaptureServerSession.this.lambda$new$0();
                }
            }, 0);
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "could not register DeathRecipient for " + iBinder);
        }
    }

    boolean isActivitySession(@android.annotation.NonNull android.os.IBinder iBinder) {
        return this.mActivityToken.equals(iBinder);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void notifySessionStartedLocked(@android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver) {
        if (this.mService.mRemoteService == null) {
            android.util.Slog.w(TAG, "notifySessionStartedLocked(): no remote service");
        } else {
            this.mService.mRemoteService.onSessionStarted(this.mContentCaptureContext, this.mId, this.mUid, iResultReceiver, 2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void setContentCaptureEnabledLocked(boolean z) {
        try {
            android.os.Bundle bundle = new android.os.Bundle();
            int i = 1;
            bundle.putBoolean(com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED, true);
            com.android.internal.os.IResultReceiver iResultReceiver = this.mSessionStateReceiver;
            if (!z) {
                i = 2;
            }
            iResultReceiver.send(i, bundle);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Error async reporting result to client: " + e);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void sendActivitySnapshotLocked(@android.annotation.NonNull android.service.contentcapture.SnapshotData snapshotData) {
        android.util.LocalLog localLog = this.mService.getMaster().mRequestsHistory;
        if (localLog != null) {
            localLog.log("snapshot: id=" + this.mId);
        }
        if (this.mService.mRemoteService == null) {
            android.util.Slog.w(TAG, "sendActivitySnapshotLocked(): no remote service");
        } else {
            this.mService.mRemoteService.onActivitySnapshotRequest(this.mId, snapshotData);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void removeSelfLocked(boolean z) {
        try {
            destroyLocked(z);
        } finally {
            this.mService.removeSessionLocked(this.mId);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void destroyLocked(boolean z) {
        if (this.mService.isVerbose()) {
            android.util.Slog.v(TAG, "destroy(notifyRemoteService=" + z + ")");
        }
        if (z) {
            if (this.mService.mRemoteService == null) {
                android.util.Slog.w(TAG, "destroyLocked(): no remote service");
            } else {
                this.mService.mRemoteService.onSessionFinished(this.mId);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void resurrectLocked() {
        com.android.server.contentcapture.RemoteContentCaptureService remoteContentCaptureService = this.mService.mRemoteService;
        if (remoteContentCaptureService == null) {
            android.util.Slog.w(TAG, "destroyLocked(: no remote service");
            return;
        }
        if (this.mService.isVerbose()) {
            android.util.Slog.v(TAG, "resurrecting " + this.mActivityToken + " on " + remoteContentCaptureService);
        }
        remoteContentCaptureService.onSessionStarted(new android.view.contentcapture.ContentCaptureContext(this.mContentCaptureContext, 4), this.mId, this.mUid, this.mSessionStateReceiver, com.android.server.usb.descriptors.UsbACInterface.FORMAT_II_AC3);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void pauseLocked() {
        if (this.mService.isVerbose()) {
            android.util.Slog.v(TAG, "pausing " + this.mActivityToken);
        }
        android.service.contentcapture.ContentCaptureService.setClientState(this.mSessionStateReceiver, 2052, (android.os.IBinder) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onClientDeath, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0() {
        if (this.mService.isVerbose()) {
            android.util.Slog.v(TAG, "onClientDeath(" + this.mActivityToken + "): removing session " + this.mId);
        }
        synchronized (this.mLock) {
            removeSelfLocked(true);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void dumpLocked(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("id: ");
        printWriter.print(this.mId);
        printWriter.println();
        printWriter.print(str);
        printWriter.print("uid: ");
        printWriter.print(this.mUid);
        printWriter.println();
        printWriter.print(str);
        printWriter.print("context: ");
        this.mContentCaptureContext.dump(printWriter);
        printWriter.println();
        printWriter.print(str);
        printWriter.print("activity token: ");
        printWriter.println(this.mActivityToken);
        printWriter.print(str);
        printWriter.print("app component: ");
        printWriter.println(this.appComponentName);
        printWriter.print(str);
        printWriter.print("has autofill callback: ");
    }

    java.lang.String toShortString() {
        return this.mId + ":" + this.mActivityToken;
    }

    public java.lang.String toString() {
        return "ContentCaptureSession[id=" + this.mId + ", act=" + this.mActivityToken + "]";
    }
}
