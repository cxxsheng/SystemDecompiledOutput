package com.android.server.wm;

/* loaded from: classes3.dex */
class AssistDataReceiverProxy implements com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks, android.os.IBinder.DeathRecipient {
    private static final java.lang.String TAG = "ActivityTaskManager";
    private java.lang.String mCallerPackage;
    private android.app.IAssistDataReceiver mReceiver;

    public AssistDataReceiverProxy(android.app.IAssistDataReceiver iAssistDataReceiver, java.lang.String str) {
        this.mReceiver = iAssistDataReceiver;
        this.mCallerPackage = str;
        linkToDeath();
    }

    @Override // com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks
    public boolean canHandleReceivedAssistDataLocked() {
        return true;
    }

    @Override // com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks
    public void onAssistDataReceivedLocked(android.os.Bundle bundle, int i, int i2) {
        if (this.mReceiver != null) {
            try {
                this.mReceiver.onHandleAssistData(bundle);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Failed to proxy assist data to receiver in package=" + this.mCallerPackage, e);
            }
        }
    }

    @Override // com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks
    public void onAssistScreenshotReceivedLocked(android.graphics.Bitmap bitmap) {
        if (this.mReceiver != null) {
            try {
                this.mReceiver.onHandleAssistScreenshot(bitmap);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Failed to proxy assist screenshot to receiver in package=" + this.mCallerPackage, e);
            }
        }
    }

    @Override // com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks
    public void onAssistRequestCompleted() {
        unlinkToDeath();
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        unlinkToDeath();
    }

    private void linkToDeath() {
        try {
            this.mReceiver.asBinder().linkToDeath(this, 0);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Could not link to client death", e);
        }
    }

    private void unlinkToDeath() {
        if (this.mReceiver != null) {
            this.mReceiver.asBinder().unlinkToDeath(this, 0);
        }
        this.mReceiver = null;
    }
}
