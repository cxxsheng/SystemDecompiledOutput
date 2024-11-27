package android.telephony.ims;

/* loaded from: classes3.dex */
public class ImsCallSession {
    private static final java.lang.String TAG = "ImsCallSession";
    private java.lang.String mCallId;
    private boolean mClosed;
    private android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy mIImsCallSessionListenerProxy;
    private android.telephony.ims.ImsCallSession.Listener mListener;
    private java.util.concurrent.Executor mListenerExecutor;
    private final com.android.ims.internal.IImsCallSession miSession;

    public static class State {
        public static final int ESTABLISHED = 4;
        public static final int ESTABLISHING = 3;
        public static final int IDLE = 0;
        public static final int INITIATED = 1;
        public static final int INVALID = -1;
        public static final int NEGOTIATING = 2;
        public static final int REESTABLISHING = 6;
        public static final int RENEGOTIATING = 5;
        public static final int TERMINATED = 8;
        public static final int TERMINATING = 7;

        public static java.lang.String toString(int i) {
            switch (i) {
                case 0:
                    return "IDLE";
                case 1:
                    return "INITIATED";
                case 2:
                    return "NEGOTIATING";
                case 3:
                    return "ESTABLISHING";
                case 4:
                    return "ESTABLISHED";
                case 5:
                    return "RENEGOTIATING";
                case 6:
                    return "REESTABLISHING";
                case 7:
                    return "TERMINATING";
                case 8:
                    return "TERMINATED";
                default:
                    return "UNKNOWN";
            }
        }

        private State() {
        }
    }

    public static class Listener {
        public void callSessionInitiating(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) {
        }

        public void callSessionInitiatingFailed(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionProgressing(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
        }

        public void callSessionStarted(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) {
        }

        public void callSessionStartFailed(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionTerminated(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionHeld(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) {
        }

        public void callSessionHoldFailed(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionHoldReceived(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) {
        }

        public void callSessionResumed(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) {
        }

        public void callSessionResumeFailed(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionResumeReceived(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) {
        }

        public void callSessionMergeStarted(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsCallSession imsCallSession2, android.telephony.ims.ImsCallProfile imsCallProfile) {
        }

        public void callSessionMergeComplete(android.telephony.ims.ImsCallSession imsCallSession) {
        }

        public void callSessionMergeFailed(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionUpdated(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) {
        }

        public void callSessionUpdateFailed(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionUpdateReceived(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) {
        }

        public void callSessionConferenceExtended(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsCallSession imsCallSession2, android.telephony.ims.ImsCallProfile imsCallProfile) {
        }

        public void callSessionConferenceExtendFailed(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionConferenceExtendReceived(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsCallSession imsCallSession2, android.telephony.ims.ImsCallProfile imsCallProfile) {
        }

        public void callSessionInviteParticipantsRequestDelivered(android.telephony.ims.ImsCallSession imsCallSession) {
        }

        public void callSessionInviteParticipantsRequestFailed(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionRemoveParticipantsRequestDelivered(android.telephony.ims.ImsCallSession imsCallSession) {
        }

        public void callSessionRemoveParticipantsRequestFailed(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionConferenceStateUpdated(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsConferenceState imsConferenceState) {
        }

        public void callSessionUssdMessageReceived(android.telephony.ims.ImsCallSession imsCallSession, int i, java.lang.String str) {
        }

        public void callSessionMayHandover(android.telephony.ims.ImsCallSession imsCallSession, int i, int i2) {
        }

        public void callSessionHandover(android.telephony.ims.ImsCallSession imsCallSession, int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionHandoverFailed(android.telephony.ims.ImsCallSession imsCallSession, int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionTtyModeReceived(android.telephony.ims.ImsCallSession imsCallSession, int i) {
        }

        public void callSessionMultipartyStateChanged(android.telephony.ims.ImsCallSession imsCallSession, boolean z) {
        }

        public void callSessionSuppServiceReceived(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsSuppServiceNotification imsSuppServiceNotification) {
        }

        public void callSessionRttModifyRequestReceived(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) {
        }

        public void callSessionRttModifyResponseReceived(int i) {
        }

        public void callSessionRttMessageReceived(java.lang.String str) {
        }

        public void callSessionRttAudioIndicatorChanged(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
        }

        public void callSessionTransferred(android.telephony.ims.ImsCallSession imsCallSession) {
        }

        public void callSessionTransferFailed(android.telephony.ims.ImsCallSession imsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionDtmfReceived(char c) {
        }

        public void callQualityChanged(android.telephony.CallQuality callQuality) {
        }

        public void callSessionRtpHeaderExtensionsReceived(java.util.Set<android.telephony.ims.RtpHeaderExtension> set) {
        }

        public void callSessionSendAnbrQuery(int i, int i2, int i3) {
        }
    }

    public ImsCallSession(com.android.ims.internal.IImsCallSession iImsCallSession) {
        this.mClosed = false;
        this.mCallId = null;
        this.mListenerExecutor = new android.app.PendingIntent$$ExternalSyntheticLambda0();
        this.mIImsCallSessionListenerProxy = null;
        this.miSession = iImsCallSession;
        this.mIImsCallSessionListenerProxy = new android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy();
        if (iImsCallSession != null) {
            try {
                iImsCallSession.setListener(this.mIImsCallSessionListenerProxy);
                return;
            } catch (android.os.RemoteException e) {
                if (com.android.internal.telephony.flags.Flags.ignoreAlreadyTerminatedIncomingCallBeforeRegisteringListener()) {
                    android.util.Log.e(TAG, "ImsCallSession : " + e);
                    this.mClosed = true;
                    return;
                }
                return;
            }
        }
        this.mClosed = true;
    }

    public ImsCallSession(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallSession.Listener listener, java.util.concurrent.Executor executor) {
        this(iImsCallSession);
        setListener(listener, executor);
    }

    public final android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy getIImsCallSessionListenerProxy() {
        return this.mIImsCallSessionListenerProxy;
    }

    public void close() {
        synchronized (this) {
            if (this.mClosed) {
                return;
            }
            try {
                this.miSession.close();
                this.mClosed = true;
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public java.lang.String getCallId() {
        if (this.mClosed) {
            return null;
        }
        if (this.mCallId != null) {
            return this.mCallId;
        }
        try {
            java.lang.String callId = this.miSession.getCallId();
            this.mCallId = callId;
            return callId;
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public void setCallId(java.lang.String str) {
        if (str != null) {
            this.mCallId = str;
        }
    }

    public android.telephony.ims.ImsCallProfile getCallProfile() {
        if (this.mClosed) {
            return null;
        }
        try {
            return this.miSession.getCallProfile();
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public android.telephony.ims.ImsCallProfile getLocalCallProfile() {
        if (this.mClosed) {
            return null;
        }
        try {
            return this.miSession.getLocalCallProfile();
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public android.telephony.ims.ImsCallProfile getRemoteCallProfile() {
        if (this.mClosed) {
            return null;
        }
        try {
            return this.miSession.getRemoteCallProfile();
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public com.android.ims.internal.IImsVideoCallProvider getVideoCallProvider() {
        if (this.mClosed) {
            return null;
        }
        try {
            return this.miSession.getVideoCallProvider();
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public java.lang.String getProperty(java.lang.String str) {
        if (this.mClosed) {
            return null;
        }
        try {
            return this.miSession.getProperty(str);
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public int getState() {
        if (this.mClosed) {
            return -1;
        }
        try {
            return this.miSession.getState();
        } catch (android.os.RemoteException e) {
            return -1;
        }
    }

    public boolean isAlive() {
        if (this.mClosed) {
            return false;
        }
        switch (getState()) {
        }
        return false;
    }

    public com.android.ims.internal.IImsCallSession getSession() {
        return this.miSession;
    }

    public boolean isInCall() {
        if (this.mClosed) {
            return false;
        }
        try {
            return this.miSession.isInCall();
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public void setListener(android.telephony.ims.ImsCallSession.Listener listener, java.util.concurrent.Executor executor) {
        this.mListener = listener;
        if (executor != null) {
            this.mListenerExecutor = executor;
        }
    }

    public void setMute(boolean z) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.setMute(z);
        } catch (android.os.RemoteException e) {
        }
    }

    public void start(java.lang.String str, android.telephony.ims.ImsCallProfile imsCallProfile) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.start(str, imsCallProfile);
        } catch (android.os.RemoteException e) {
        }
    }

    public void start(java.lang.String[] strArr, android.telephony.ims.ImsCallProfile imsCallProfile) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.startConference(strArr, imsCallProfile);
        } catch (android.os.RemoteException e) {
        }
    }

    public void accept(int i, android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.accept(i, imsStreamMediaProfile);
        } catch (android.os.RemoteException e) {
        }
    }

    public void deflect(java.lang.String str) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.deflect(str);
        } catch (android.os.RemoteException e) {
        }
    }

    public void reject(int i) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.reject(i);
        } catch (android.os.RemoteException e) {
        }
    }

    public void transfer(java.lang.String str, boolean z) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.transfer(str, z);
        } catch (android.os.RemoteException e) {
        }
    }

    public void transfer(android.telephony.ims.ImsCallSession imsCallSession) {
        if (!this.mClosed && imsCallSession != null) {
            try {
                this.miSession.consultativeTransfer(imsCallSession.getSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void terminate(int i) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.terminate(i);
        } catch (android.os.RemoteException e) {
        }
    }

    public void hold(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.hold(imsStreamMediaProfile);
        } catch (android.os.RemoteException e) {
        }
    }

    public void resume(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.resume(imsStreamMediaProfile);
        } catch (android.os.RemoteException e) {
        }
    }

    public void merge() {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.merge();
        } catch (android.os.RemoteException e) {
        }
    }

    public void update(int i, android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.update(i, imsStreamMediaProfile);
        } catch (android.os.RemoteException e) {
        }
    }

    public void extendToConference(java.lang.String[] strArr) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.extendToConference(strArr);
        } catch (android.os.RemoteException e) {
        }
    }

    public void inviteParticipants(java.lang.String[] strArr) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.inviteParticipants(strArr);
        } catch (android.os.RemoteException e) {
        }
    }

    public void removeParticipants(java.lang.String[] strArr) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.removeParticipants(strArr);
        } catch (android.os.RemoteException e) {
        }
    }

    public void sendDtmf(char c, android.os.Message message) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.sendDtmf(c, message);
        } catch (android.os.RemoteException e) {
        }
    }

    public void startDtmf(char c) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.startDtmf(c);
        } catch (android.os.RemoteException e) {
        }
    }

    public void stopDtmf() {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.stopDtmf();
        } catch (android.os.RemoteException e) {
        }
    }

    public void sendUssd(java.lang.String str) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.sendUssd(str);
        } catch (android.os.RemoteException e) {
        }
    }

    public boolean isMultiparty() {
        if (this.mClosed) {
            return false;
        }
        try {
            return this.miSession.isMultiparty();
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public void sendRttMessage(java.lang.String str) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.sendRttMessage(str);
        } catch (android.os.RemoteException e) {
        }
    }

    public void sendRttModifyRequest(android.telephony.ims.ImsCallProfile imsCallProfile) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.sendRttModifyRequest(imsCallProfile);
        } catch (android.os.RemoteException e) {
        }
    }

    public void sendRttModifyResponse(boolean z) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.sendRttModifyResponse(z);
        } catch (android.os.RemoteException e) {
        }
    }

    public void sendRtpHeaderExtensions(java.util.Set<android.telephony.ims.RtpHeaderExtension> set) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.sendRtpHeaderExtensions(new java.util.ArrayList(set));
        } catch (android.os.RemoteException e) {
        }
    }

    public void callSessionNotifyAnbr(int i, int i2, int i3) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.callSessionNotifyAnbr(i, i2, i3);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "callSessionNotifyAnbr" + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class IImsCallSessionListenerProxy extends android.telephony.ims.aidl.IImsCallSessionListener.Stub {
        private IImsCallSessionListenerProxy() {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInitiating(final android.telephony.ims.ImsCallProfile imsCallProfile) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda38
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionInitiating$0(imsCallProfile);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionInitiating$0(android.telephony.ims.ImsCallProfile imsCallProfile) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionInitiating(android.telephony.ims.ImsCallSession.this, imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionProgressing(final android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda35
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionProgressing$1(imsStreamMediaProfile);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionProgressing$1(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionProgressing(android.telephony.ims.ImsCallSession.this, imsStreamMediaProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInitiated(final android.telephony.ims.ImsCallProfile imsCallProfile) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda26
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionInitiated$2(imsCallProfile);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionInitiated$2(android.telephony.ims.ImsCallProfile imsCallProfile) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionStarted(android.telephony.ims.ImsCallSession.this, imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInitiatingFailed(final android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionInitiatingFailed$3(imsReasonInfo);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionInitiatingFailed$3(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionStartFailed(android.telephony.ims.ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInitiatedFailed(final android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionInitiatedFailed$4(imsReasonInfo);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionInitiatedFailed$4(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionStartFailed(android.telephony.ims.ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionTerminated(final android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionTerminated$5(imsReasonInfo);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionTerminated$5(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionTerminated(android.telephony.ims.ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHeld(final android.telephony.ims.ImsCallProfile imsCallProfile) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda40
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionHeld$6(imsCallProfile);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionHeld$6(android.telephony.ims.ImsCallProfile imsCallProfile) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionHeld(android.telephony.ims.ImsCallSession.this, imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHoldFailed(final android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionHoldFailed$7(imsReasonInfo);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionHoldFailed$7(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionHoldFailed(android.telephony.ims.ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHoldReceived(final android.telephony.ims.ImsCallProfile imsCallProfile) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda31
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionHoldReceived$8(imsCallProfile);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionHoldReceived$8(android.telephony.ims.ImsCallProfile imsCallProfile) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionHoldReceived(android.telephony.ims.ImsCallSession.this, imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionResumed(final android.telephony.ims.ImsCallProfile imsCallProfile) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda22
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionResumed$9(imsCallProfile);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionResumed$9(android.telephony.ims.ImsCallProfile imsCallProfile) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionResumed(android.telephony.ims.ImsCallSession.this, imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionResumeFailed(final android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda36
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionResumeFailed$10(imsReasonInfo);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionResumeFailed$10(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionResumeFailed(android.telephony.ims.ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionResumeReceived(final android.telephony.ims.ImsCallProfile imsCallProfile) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionResumeReceived$11(imsCallProfile);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionResumeReceived$11(android.telephony.ims.ImsCallProfile imsCallProfile) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionResumeReceived(android.telephony.ims.ImsCallSession.this, imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMergeStarted(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) {
            android.util.Log.d(android.telephony.ims.ImsCallSession.TAG, "callSessionMergeStarted");
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMergeComplete(final com.android.ims.internal.IImsCallSession iImsCallSession) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionMergeComplete$12(iImsCallSession);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionMergeComplete$12(com.android.ims.internal.IImsCallSession iImsCallSession) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                if (iImsCallSession != null) {
                    android.telephony.ims.ImsCallSession.this.mListener.callSessionMergeComplete(new android.telephony.ims.ImsCallSession(iImsCallSession));
                } else {
                    android.telephony.ims.ImsCallSession.this.mListener.callSessionMergeComplete(null);
                }
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMergeFailed(final android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda32
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionMergeFailed$13(imsReasonInfo);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionMergeFailed$13(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionMergeFailed(android.telephony.ims.ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionUpdated(final android.telephony.ims.ImsCallProfile imsCallProfile) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionUpdated$14(imsCallProfile);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionUpdated$14(android.telephony.ims.ImsCallProfile imsCallProfile) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionUpdated(android.telephony.ims.ImsCallSession.this, imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionUpdateFailed(final android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionUpdateFailed$15(imsReasonInfo);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionUpdateFailed$15(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionUpdateFailed(android.telephony.ims.ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionUpdateReceived(final android.telephony.ims.ImsCallProfile imsCallProfile) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda21
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionUpdateReceived$16(imsCallProfile);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionUpdateReceived$16(android.telephony.ims.ImsCallProfile imsCallProfile) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionUpdateReceived(android.telephony.ims.ImsCallSession.this, imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionConferenceExtended(final com.android.ims.internal.IImsCallSession iImsCallSession, final android.telephony.ims.ImsCallProfile imsCallProfile) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda41
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionConferenceExtended$17(iImsCallSession, imsCallProfile);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionConferenceExtended$17(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionConferenceExtended(android.telephony.ims.ImsCallSession.this, new android.telephony.ims.ImsCallSession(iImsCallSession), imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionConferenceExtendFailed(final android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionConferenceExtendFailed$18(imsReasonInfo);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionConferenceExtendFailed$18(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionConferenceExtendFailed(android.telephony.ims.ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionConferenceExtendReceived(final com.android.ims.internal.IImsCallSession iImsCallSession, final android.telephony.ims.ImsCallProfile imsCallProfile) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionConferenceExtendReceived$19(iImsCallSession, imsCallProfile);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionConferenceExtendReceived$19(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionConferenceExtendReceived(android.telephony.ims.ImsCallSession.this, new android.telephony.ims.ImsCallSession(iImsCallSession), imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInviteParticipantsRequestDelivered() {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda39
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionInviteParticipantsRequestDelivered$20();
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionInviteParticipantsRequestDelivered$20() {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionInviteParticipantsRequestDelivered(android.telephony.ims.ImsCallSession.this);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInviteParticipantsRequestFailed(final android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda29
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionInviteParticipantsRequestFailed$21(imsReasonInfo);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionInviteParticipantsRequestFailed$21(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionInviteParticipantsRequestFailed(android.telephony.ims.ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRemoveParticipantsRequestDelivered() {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionRemoveParticipantsRequestDelivered$22();
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionRemoveParticipantsRequestDelivered$22() {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionRemoveParticipantsRequestDelivered(android.telephony.ims.ImsCallSession.this);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRemoveParticipantsRequestFailed(final android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionRemoveParticipantsRequestFailed$23(imsReasonInfo);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionRemoveParticipantsRequestFailed$23(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionRemoveParticipantsRequestFailed(android.telephony.ims.ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionConferenceStateUpdated(final android.telephony.ims.ImsConferenceState imsConferenceState) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda37
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionConferenceStateUpdated$24(imsConferenceState);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionConferenceStateUpdated$24(android.telephony.ims.ImsConferenceState imsConferenceState) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionConferenceStateUpdated(android.telephony.ims.ImsCallSession.this, imsConferenceState);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionUssdMessageReceived(final int i, final java.lang.String str) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda28
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionUssdMessageReceived$25(i, str);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionUssdMessageReceived$25(int i, java.lang.String str) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionUssdMessageReceived(android.telephony.ims.ImsCallSession.this, i, str);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMayHandover(final int i, final int i2) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionMayHandover$26(i, i2);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionMayHandover$26(int i, int i2) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionMayHandover(android.telephony.ims.ImsCallSession.this, i, i2);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHandover(final int i, final int i2, final android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionHandover$27(i, i2, imsReasonInfo);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionHandover$27(int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionHandover(android.telephony.ims.ImsCallSession.this, i, i2, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHandoverFailed(final int i, final int i2, final android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionHandoverFailed$28(i, i2, imsReasonInfo);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionHandoverFailed$28(int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionHandoverFailed(android.telephony.ims.ImsCallSession.this, i, i2, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionTtyModeReceived(final int i) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda30
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionTtyModeReceived$29(i);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionTtyModeReceived$29(int i) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionTtyModeReceived(android.telephony.ims.ImsCallSession.this, i);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMultipartyStateChanged(final boolean z) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionMultipartyStateChanged$30(z);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionMultipartyStateChanged$30(boolean z) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionMultipartyStateChanged(android.telephony.ims.ImsCallSession.this, z);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionSuppServiceReceived(final android.telephony.ims.ImsSuppServiceNotification imsSuppServiceNotification) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionSuppServiceReceived$31(imsSuppServiceNotification);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionSuppServiceReceived$31(android.telephony.ims.ImsSuppServiceNotification imsSuppServiceNotification) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionSuppServiceReceived(android.telephony.ims.ImsCallSession.this, imsSuppServiceNotification);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRttModifyRequestReceived(final android.telephony.ims.ImsCallProfile imsCallProfile) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda34
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionRttModifyRequestReceived$32(imsCallProfile);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionRttModifyRequestReceived$32(android.telephony.ims.ImsCallProfile imsCallProfile) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionRttModifyRequestReceived(android.telephony.ims.ImsCallSession.this, imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRttModifyResponseReceived(final int i) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda27
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionRttModifyResponseReceived$33(i);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionRttModifyResponseReceived$33(int i) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionRttModifyResponseReceived(i);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRttMessageReceived(final java.lang.String str) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionRttMessageReceived$34(str);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionRttMessageReceived$34(java.lang.String str) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionRttMessageReceived(str);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRttAudioIndicatorChanged(final android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionRttAudioIndicatorChanged$35(imsStreamMediaProfile);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionRttAudioIndicatorChanged$35(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionRttAudioIndicatorChanged(imsStreamMediaProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionTransferred() {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda33
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionTransferred$36();
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionTransferred$36() {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionTransferred(android.telephony.ims.ImsCallSession.this);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionTransferFailed(final android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionTransferFailed$37(imsReasonInfo);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionTransferFailed$37(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionTransferFailed(android.telephony.ims.ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionDtmfReceived(final char c) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda24
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionDtmfReceived$38(c);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionDtmfReceived$38(char c) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionDtmfReceived(c);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callQualityChanged(final android.telephony.CallQuality callQuality) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callQualityChanged$39(callQuality);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callQualityChanged$39(android.telephony.CallQuality callQuality) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callQualityChanged(callQuality);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRtpHeaderExtensionsReceived(final java.util.List<android.telephony.ims.RtpHeaderExtension> list) {
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda25
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionRtpHeaderExtensionsReceived$40(list);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionRtpHeaderExtensionsReceived$40(java.util.List list) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionRtpHeaderExtensionsReceived(new android.util.ArraySet(list));
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionSendAnbrQuery(final int i, final int i2, final int i3) {
            android.util.Log.d(android.telephony.ims.ImsCallSession.TAG, "callSessionSendAnbrQuery in ImsCallSession");
            com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(new java.lang.Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsCallSession.IImsCallSessionListenerProxy.this.lambda$callSessionSendAnbrQuery$41(i, i2, i3);
                }
            }, android.telephony.ims.ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionSendAnbrQuery$41(int i, int i2, int i3) {
            if (android.telephony.ims.ImsCallSession.this.mListener != null) {
                android.telephony.ims.ImsCallSession.this.mListener.callSessionSendAnbrQuery(i, i2, i3);
            }
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("[ImsCallSession objId:");
        sb.append(java.lang.System.identityHashCode(this));
        sb.append(" callId:");
        sb.append(this.mCallId != null ? this.mCallId : "[UNINITIALIZED]");
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }
}
