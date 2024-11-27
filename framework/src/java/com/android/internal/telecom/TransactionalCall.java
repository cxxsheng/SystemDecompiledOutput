package com.android.internal.telecom;

/* loaded from: classes5.dex */
public class TransactionalCall {
    private final android.telecom.CallAttributes mCallAttributes;
    private android.telecom.CallControl mCallControl;
    private final android.telecom.CallControlCallback mCallControlCallback;
    private final java.lang.String mCallId;
    private final android.telecom.CallEventCallback mCallStateCallback;
    private final java.util.concurrent.Executor mExecutor;
    private final android.os.OutcomeReceiver<android.telecom.CallControl, android.telecom.CallException> mPendingControl;

    public TransactionalCall(java.lang.String str, android.telecom.CallAttributes callAttributes, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<android.telecom.CallControl, android.telecom.CallException> outcomeReceiver, android.telecom.CallControlCallback callControlCallback, android.telecom.CallEventCallback callEventCallback) {
        this.mCallId = str;
        this.mCallAttributes = callAttributes;
        this.mExecutor = executor;
        this.mPendingControl = outcomeReceiver;
        this.mCallControlCallback = callControlCallback;
        this.mCallStateCallback = callEventCallback;
    }

    public void setCallControl(android.telecom.CallControl callControl) {
        this.mCallControl = callControl;
    }

    public android.telecom.CallControl getCallControl() {
        return this.mCallControl;
    }

    public java.lang.String getCallId() {
        return this.mCallId;
    }

    public android.telecom.CallAttributes getCallAttributes() {
        return this.mCallAttributes;
    }

    public java.util.concurrent.Executor getExecutor() {
        return this.mExecutor;
    }

    public android.os.OutcomeReceiver<android.telecom.CallControl, android.telecom.CallException> getPendingControl() {
        return this.mPendingControl;
    }

    public android.telecom.CallControlCallback getCallControlCallback() {
        return this.mCallControlCallback;
    }

    public android.telecom.CallEventCallback getCallStateCallback() {
        return this.mCallStateCallback;
    }
}
