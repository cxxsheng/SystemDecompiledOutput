package com.android.server.am;

/* loaded from: classes.dex */
public class SameProcessApplicationThread extends android.app.IApplicationThread.Default {
    private final android.os.Handler mHandler;
    private final android.app.IApplicationThread mWrapped;

    public SameProcessApplicationThread(@android.annotation.NonNull android.app.IApplicationThread iApplicationThread, @android.annotation.NonNull android.os.Handler handler) {
        java.util.Objects.requireNonNull(iApplicationThread);
        this.mWrapped = iApplicationThread;
        java.util.Objects.requireNonNull(handler);
        this.mHandler = handler;
    }

    public void scheduleReceiver(final android.content.Intent intent, final android.content.pm.ActivityInfo activityInfo, final android.content.res.CompatibilityInfo compatibilityInfo, final int i, final java.lang.String str, final android.os.Bundle bundle, final boolean z, final boolean z2, final int i2, final int i3, final int i4, final java.lang.String str2) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.SameProcessApplicationThread$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.SameProcessApplicationThread.this.lambda$scheduleReceiver$0(intent, activityInfo, compatibilityInfo, i, str, bundle, z, z2, i2, i3, i4, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleReceiver$0(android.content.Intent intent, android.content.pm.ActivityInfo activityInfo, android.content.res.CompatibilityInfo compatibilityInfo, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, int i2, int i3, int i4, java.lang.String str2) {
        try {
            this.mWrapped.scheduleReceiver(intent, activityInfo, compatibilityInfo, i, str, bundle, z, z2, i2, i3, i4, str2);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public void scheduleRegisteredReceiver(final android.content.IIntentReceiver iIntentReceiver, final android.content.Intent intent, final int i, final java.lang.String str, final android.os.Bundle bundle, final boolean z, final boolean z2, final boolean z3, final int i2, final int i3, final int i4, final java.lang.String str2) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.SameProcessApplicationThread$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.SameProcessApplicationThread.this.lambda$scheduleRegisteredReceiver$1(iIntentReceiver, intent, i, str, bundle, z, z2, z3, i2, i3, i4, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleRegisteredReceiver$1(android.content.IIntentReceiver iIntentReceiver, android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, boolean z3, int i2, int i3, int i4, java.lang.String str2) {
        try {
            this.mWrapped.scheduleRegisteredReceiver(iIntentReceiver, intent, i, str, bundle, z, z2, z3, i2, i3, i4, str2);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public void scheduleReceiverList(final java.util.List<android.app.ReceiverInfo> list) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.SameProcessApplicationThread$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.SameProcessApplicationThread.this.lambda$scheduleReceiverList$2(list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleReceiverList$2(java.util.List list) {
        try {
            this.mWrapped.scheduleReceiverList(list);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public void schedulePing(final android.os.RemoteCallback remoteCallback) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.SameProcessApplicationThread$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.SameProcessApplicationThread.this.lambda$schedulePing$3(remoteCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$schedulePing$3(android.os.RemoteCallback remoteCallback) {
        try {
            this.mWrapped.schedulePing(remoteCallback);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }
}
