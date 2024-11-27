package android.telephony.ims.compat.stub;

/* loaded from: classes3.dex */
public class ImsCallSessionImplBase extends com.android.ims.internal.IImsCallSession.Stub {
    @Override // com.android.ims.internal.IImsCallSession
    public final void setListener(android.telephony.ims.aidl.IImsCallSessionListener iImsCallSessionListener) throws android.os.RemoteException {
        setListener(new android.telephony.ims.compat.stub.ImsCallSessionImplBase.ImsCallSessionListenerConverter(iImsCallSessionListener));
    }

    public void setListener(com.android.ims.internal.IImsCallSessionListener iImsCallSessionListener) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void close() {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public java.lang.String getCallId() {
        return null;
    }

    @Override // com.android.ims.internal.IImsCallSession
    public android.telephony.ims.ImsCallProfile getCallProfile() {
        return null;
    }

    @Override // com.android.ims.internal.IImsCallSession
    public android.telephony.ims.ImsCallProfile getLocalCallProfile() {
        return null;
    }

    @Override // com.android.ims.internal.IImsCallSession
    public android.telephony.ims.ImsCallProfile getRemoteCallProfile() {
        return null;
    }

    @Override // com.android.ims.internal.IImsCallSession
    public java.lang.String getProperty(java.lang.String str) {
        return null;
    }

    @Override // com.android.ims.internal.IImsCallSession
    public int getState() {
        return -1;
    }

    @Override // com.android.ims.internal.IImsCallSession
    public boolean isInCall() {
        return false;
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void setMute(boolean z) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void start(java.lang.String str, android.telephony.ims.ImsCallProfile imsCallProfile) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void startConference(java.lang.String[] strArr, android.telephony.ims.ImsCallProfile imsCallProfile) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void accept(int i, android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void deflect(java.lang.String str) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void transfer(java.lang.String str, boolean z) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void consultativeTransfer(com.android.ims.internal.IImsCallSession iImsCallSession) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void reject(int i) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void terminate(int i) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void hold(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void resume(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void merge() {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void update(int i, android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void extendToConference(java.lang.String[] strArr) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void inviteParticipants(java.lang.String[] strArr) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void removeParticipants(java.lang.String[] strArr) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void sendDtmf(char c, android.os.Message message) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void startDtmf(char c) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void stopDtmf() {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void sendUssd(java.lang.String str) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public com.android.ims.internal.IImsVideoCallProvider getVideoCallProvider() {
        return null;
    }

    @Override // com.android.ims.internal.IImsCallSession
    public boolean isMultiparty() {
        return false;
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void sendRttModifyRequest(android.telephony.ims.ImsCallProfile imsCallProfile) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void sendRttModifyResponse(boolean z) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void sendRttMessage(java.lang.String str) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void sendRtpHeaderExtensions(java.util.List<android.telephony.ims.RtpHeaderExtension> list) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void callSessionNotifyAnbr(int i, int i2, int i3) {
    }

    private class ImsCallSessionListenerConverter extends com.android.ims.internal.IImsCallSessionListener.Stub {
        private final android.telephony.ims.aidl.IImsCallSessionListener mNewListener;

        public ImsCallSessionListenerConverter(android.telephony.ims.aidl.IImsCallSessionListener iImsCallSessionListener) {
            this.mNewListener = iImsCallSessionListener;
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionProgressing(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException {
            this.mNewListener.callSessionProgressing(imsStreamMediaProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionStarted(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
            this.mNewListener.callSessionInitiated(imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionStartFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
            this.mNewListener.callSessionInitiatedFailed(imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionTerminated(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
            this.mNewListener.callSessionTerminated(imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHeld(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
            this.mNewListener.callSessionHeld(imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHoldFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
            this.mNewListener.callSessionHoldFailed(imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHoldReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
            this.mNewListener.callSessionHoldReceived(imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionResumed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
            this.mNewListener.callSessionResumed(imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionResumeFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
            this.mNewListener.callSessionResumeFailed(imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionResumeReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
            this.mNewListener.callSessionResumeReceived(imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMergeStarted(com.android.ims.internal.IImsCallSession iImsCallSession, com.android.ims.internal.IImsCallSession iImsCallSession2, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
            this.mNewListener.callSessionMergeStarted(iImsCallSession2, imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMergeComplete(com.android.ims.internal.IImsCallSession iImsCallSession) throws android.os.RemoteException {
            this.mNewListener.callSessionMergeComplete(iImsCallSession);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMergeFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
            this.mNewListener.callSessionMergeFailed(imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionUpdated(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
            this.mNewListener.callSessionUpdated(imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionUpdateFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
            this.mNewListener.callSessionUpdateFailed(imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionUpdateReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
            this.mNewListener.callSessionUpdateReceived(imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionConferenceExtended(com.android.ims.internal.IImsCallSession iImsCallSession, com.android.ims.internal.IImsCallSession iImsCallSession2, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
            this.mNewListener.callSessionConferenceExtended(iImsCallSession2, imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionConferenceExtendFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
            this.mNewListener.callSessionConferenceExtendFailed(imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionConferenceExtendReceived(com.android.ims.internal.IImsCallSession iImsCallSession, com.android.ims.internal.IImsCallSession iImsCallSession2, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
            this.mNewListener.callSessionConferenceExtendReceived(iImsCallSession2, imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionInviteParticipantsRequestDelivered(com.android.ims.internal.IImsCallSession iImsCallSession) throws android.os.RemoteException {
            this.mNewListener.callSessionInviteParticipantsRequestDelivered();
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionInviteParticipantsRequestFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
            this.mNewListener.callSessionInviteParticipantsRequestFailed(imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRemoveParticipantsRequestDelivered(com.android.ims.internal.IImsCallSession iImsCallSession) throws android.os.RemoteException {
            this.mNewListener.callSessionRemoveParticipantsRequestDelivered();
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRemoveParticipantsRequestFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
            this.mNewListener.callSessionRemoveParticipantsRequestFailed(imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionConferenceStateUpdated(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsConferenceState imsConferenceState) throws android.os.RemoteException {
            this.mNewListener.callSessionConferenceStateUpdated(imsConferenceState);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionUssdMessageReceived(com.android.ims.internal.IImsCallSession iImsCallSession, int i, java.lang.String str) throws android.os.RemoteException {
            this.mNewListener.callSessionUssdMessageReceived(i, str);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHandover(com.android.ims.internal.IImsCallSession iImsCallSession, int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
            this.mNewListener.callSessionHandover(android.telephony.ServiceState.rilRadioTechnologyToNetworkType(i), android.telephony.ServiceState.rilRadioTechnologyToNetworkType(i2), imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHandoverFailed(com.android.ims.internal.IImsCallSession iImsCallSession, int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
            this.mNewListener.callSessionHandoverFailed(android.telephony.ServiceState.rilRadioTechnologyToNetworkType(i), android.telephony.ServiceState.rilRadioTechnologyToNetworkType(i2), imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMayHandover(com.android.ims.internal.IImsCallSession iImsCallSession, int i, int i2) throws android.os.RemoteException {
            this.mNewListener.callSessionMayHandover(android.telephony.ServiceState.rilRadioTechnologyToNetworkType(i), android.telephony.ServiceState.rilRadioTechnologyToNetworkType(i2));
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionTtyModeReceived(com.android.ims.internal.IImsCallSession iImsCallSession, int i) throws android.os.RemoteException {
            this.mNewListener.callSessionTtyModeReceived(i);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMultipartyStateChanged(com.android.ims.internal.IImsCallSession iImsCallSession, boolean z) throws android.os.RemoteException {
            this.mNewListener.callSessionMultipartyStateChanged(z);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionSuppServiceReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsSuppServiceNotification imsSuppServiceNotification) throws android.os.RemoteException {
            this.mNewListener.callSessionSuppServiceReceived(imsSuppServiceNotification);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRttModifyRequestReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
            this.mNewListener.callSessionRttModifyRequestReceived(imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRttModifyResponseReceived(int i) throws android.os.RemoteException {
            this.mNewListener.callSessionRttModifyResponseReceived(i);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRttMessageReceived(java.lang.String str) throws android.os.RemoteException {
            this.mNewListener.callSessionRttMessageReceived(str);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRttAudioIndicatorChanged(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException {
            this.mNewListener.callSessionRttAudioIndicatorChanged(imsStreamMediaProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionTransferred() throws android.os.RemoteException {
            this.mNewListener.callSessionTransferred();
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionTransferFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
            this.mNewListener.callSessionTransferFailed(imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callQualityChanged(android.telephony.CallQuality callQuality) throws android.os.RemoteException {
            this.mNewListener.callQualityChanged(callQuality);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionSendAnbrQuery(int i, int i2, int i3) throws android.os.RemoteException {
            this.mNewListener.callSessionSendAnbrQuery(i, i2, i3);
        }
    }
}
