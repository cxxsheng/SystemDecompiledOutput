package com.android.internal.telecom;

/* loaded from: classes5.dex */
public interface IConnectionService extends android.os.IInterface {
    void abort(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void addConferenceParticipants(java.lang.String str, java.util.List<android.net.Uri> list, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void addConnectionServiceAdapter(com.android.internal.telecom.IConnectionServiceAdapter iConnectionServiceAdapter, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void answer(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void answerVideo(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void conference(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void connectionServiceFocusGained(android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void connectionServiceFocusLost(android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void consultativeTransfer(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void createConference(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, android.telecom.ConnectionRequest connectionRequest, boolean z, boolean z2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void createConferenceComplete(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void createConferenceFailed(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, android.telecom.ConnectionRequest connectionRequest, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void createConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, android.telecom.ConnectionRequest connectionRequest, boolean z, boolean z2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void createConnectionComplete(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void createConnectionFailed(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, android.telecom.ConnectionRequest connectionRequest, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void deflect(java.lang.String str, android.net.Uri uri, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void disconnect(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void handoverComplete(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void handoverFailed(java.lang.String str, android.telecom.ConnectionRequest connectionRequest, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void hold(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void mergeConference(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void onAvailableCallEndpointsChanged(java.lang.String str, java.util.List<android.telecom.CallEndpoint> list, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void onCallAudioStateChanged(java.lang.String str, android.telecom.CallAudioState callAudioState, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void onCallEndpointChanged(java.lang.String str, android.telecom.CallEndpoint callEndpoint, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void onCallFilteringCompleted(java.lang.String str, android.telecom.Connection.CallFilteringCompletionInfo callFilteringCompletionInfo, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void onExtrasChanged(java.lang.String str, android.os.Bundle bundle, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void onMuteStateChanged(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void onPostDialContinue(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void onTrackedByNonUiService(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void onUsingAlternativeUi(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void playDtmfTone(java.lang.String str, char c, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void pullExternalCall(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void reject(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void rejectWithMessage(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void rejectWithReason(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void removeConnectionServiceAdapter(com.android.internal.telecom.IConnectionServiceAdapter iConnectionServiceAdapter, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void respondToRttUpgradeRequest(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void sendCallEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void silence(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void splitFromConference(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void startRtt(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void stopDtmfTone(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void stopRtt(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void swapConference(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void transfer(java.lang.String str, android.net.Uri uri, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void unhold(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telecom.IConnectionService {
        @Override // com.android.internal.telecom.IConnectionService
        public void addConnectionServiceAdapter(com.android.internal.telecom.IConnectionServiceAdapter iConnectionServiceAdapter, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void removeConnectionServiceAdapter(com.android.internal.telecom.IConnectionServiceAdapter iConnectionServiceAdapter, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, android.telecom.ConnectionRequest connectionRequest, boolean z, boolean z2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConnectionComplete(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConnectionFailed(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, android.telecom.ConnectionRequest connectionRequest, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConference(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, android.telecom.ConnectionRequest connectionRequest, boolean z, boolean z2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConferenceComplete(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConferenceFailed(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, android.telecom.ConnectionRequest connectionRequest, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void abort(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void answerVideo(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void answer(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void deflect(java.lang.String str, android.net.Uri uri, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void reject(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void rejectWithReason(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void rejectWithMessage(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void transfer(java.lang.String str, android.net.Uri uri, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void consultativeTransfer(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void disconnect(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void silence(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void hold(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void unhold(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onCallAudioStateChanged(java.lang.String str, android.telecom.CallAudioState callAudioState, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onCallEndpointChanged(java.lang.String str, android.telecom.CallEndpoint callEndpoint, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onAvailableCallEndpointsChanged(java.lang.String str, java.util.List<android.telecom.CallEndpoint> list, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onMuteStateChanged(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void playDtmfTone(java.lang.String str, char c, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void stopDtmfTone(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void conference(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void splitFromConference(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void mergeConference(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void swapConference(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void addConferenceParticipants(java.lang.String str, java.util.List<android.net.Uri> list, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onPostDialContinue(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void pullExternalCall(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void sendCallEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onCallFilteringCompleted(java.lang.String str, android.telecom.Connection.CallFilteringCompletionInfo callFilteringCompletionInfo, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onExtrasChanged(java.lang.String str, android.os.Bundle bundle, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void startRtt(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void stopRtt(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void respondToRttUpgradeRequest(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void connectionServiceFocusLost(android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void connectionServiceFocusGained(android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void handoverFailed(java.lang.String str, android.telecom.ConnectionRequest connectionRequest, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void handoverComplete(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onUsingAlternativeUi(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onTrackedByNonUiService(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telecom.IConnectionService {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.telecom.IConnectionService";
        static final int TRANSACTION_abort = 9;
        static final int TRANSACTION_addConferenceParticipants = 32;
        static final int TRANSACTION_addConnectionServiceAdapter = 1;
        static final int TRANSACTION_answer = 11;
        static final int TRANSACTION_answerVideo = 10;
        static final int TRANSACTION_conference = 28;
        static final int TRANSACTION_connectionServiceFocusGained = 42;
        static final int TRANSACTION_connectionServiceFocusLost = 41;
        static final int TRANSACTION_consultativeTransfer = 17;
        static final int TRANSACTION_createConference = 6;
        static final int TRANSACTION_createConferenceComplete = 7;
        static final int TRANSACTION_createConferenceFailed = 8;
        static final int TRANSACTION_createConnection = 3;
        static final int TRANSACTION_createConnectionComplete = 4;
        static final int TRANSACTION_createConnectionFailed = 5;
        static final int TRANSACTION_deflect = 12;
        static final int TRANSACTION_disconnect = 18;
        static final int TRANSACTION_handoverComplete = 44;
        static final int TRANSACTION_handoverFailed = 43;
        static final int TRANSACTION_hold = 20;
        static final int TRANSACTION_mergeConference = 30;
        static final int TRANSACTION_onAvailableCallEndpointsChanged = 24;
        static final int TRANSACTION_onCallAudioStateChanged = 22;
        static final int TRANSACTION_onCallEndpointChanged = 23;
        static final int TRANSACTION_onCallFilteringCompleted = 36;
        static final int TRANSACTION_onExtrasChanged = 37;
        static final int TRANSACTION_onMuteStateChanged = 25;
        static final int TRANSACTION_onPostDialContinue = 33;
        static final int TRANSACTION_onTrackedByNonUiService = 46;
        static final int TRANSACTION_onUsingAlternativeUi = 45;
        static final int TRANSACTION_playDtmfTone = 26;
        static final int TRANSACTION_pullExternalCall = 34;
        static final int TRANSACTION_reject = 13;
        static final int TRANSACTION_rejectWithMessage = 15;
        static final int TRANSACTION_rejectWithReason = 14;
        static final int TRANSACTION_removeConnectionServiceAdapter = 2;
        static final int TRANSACTION_respondToRttUpgradeRequest = 40;
        static final int TRANSACTION_sendCallEvent = 35;
        static final int TRANSACTION_silence = 19;
        static final int TRANSACTION_splitFromConference = 29;
        static final int TRANSACTION_startRtt = 38;
        static final int TRANSACTION_stopDtmfTone = 27;
        static final int TRANSACTION_stopRtt = 39;
        static final int TRANSACTION_swapConference = 31;
        static final int TRANSACTION_transfer = 16;
        static final int TRANSACTION_unhold = 21;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.telecom.IConnectionService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telecom.IConnectionService)) {
                return (com.android.internal.telecom.IConnectionService) queryLocalInterface;
            }
            return new com.android.internal.telecom.IConnectionService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addConnectionServiceAdapter";
                case 2:
                    return "removeConnectionServiceAdapter";
                case 3:
                    return "createConnection";
                case 4:
                    return "createConnectionComplete";
                case 5:
                    return "createConnectionFailed";
                case 6:
                    return "createConference";
                case 7:
                    return "createConferenceComplete";
                case 8:
                    return "createConferenceFailed";
                case 9:
                    return "abort";
                case 10:
                    return "answerVideo";
                case 11:
                    return "answer";
                case 12:
                    return "deflect";
                case 13:
                    return "reject";
                case 14:
                    return "rejectWithReason";
                case 15:
                    return "rejectWithMessage";
                case 16:
                    return "transfer";
                case 17:
                    return "consultativeTransfer";
                case 18:
                    return android.media.MediaMetrics.Value.DISCONNECT;
                case 19:
                    return "silence";
                case 20:
                    return "hold";
                case 21:
                    return "unhold";
                case 22:
                    return "onCallAudioStateChanged";
                case 23:
                    return "onCallEndpointChanged";
                case 24:
                    return "onAvailableCallEndpointsChanged";
                case 25:
                    return "onMuteStateChanged";
                case 26:
                    return "playDtmfTone";
                case 27:
                    return "stopDtmfTone";
                case 28:
                    return android.telephony.ims.ImsCallProfile.EXTRA_CONFERENCE_DEPRECATED;
                case 29:
                    return "splitFromConference";
                case 30:
                    return "mergeConference";
                case 31:
                    return "swapConference";
                case 32:
                    return "addConferenceParticipants";
                case 33:
                    return "onPostDialContinue";
                case 34:
                    return "pullExternalCall";
                case 35:
                    return "sendCallEvent";
                case 36:
                    return "onCallFilteringCompleted";
                case 37:
                    return "onExtrasChanged";
                case 38:
                    return "startRtt";
                case 39:
                    return "stopRtt";
                case 40:
                    return "respondToRttUpgradeRequest";
                case 41:
                    return "connectionServiceFocusLost";
                case 42:
                    return "connectionServiceFocusGained";
                case 43:
                    return "handoverFailed";
                case 44:
                    return "handoverComplete";
                case 45:
                    return "onUsingAlternativeUi";
                case 46:
                    return "onTrackedByNonUiService";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    com.android.internal.telecom.IConnectionServiceAdapter asInterface = com.android.internal.telecom.IConnectionServiceAdapter.Stub.asInterface(parcel.readStrongBinder());
                    android.telecom.Logging.Session.Info info = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    addConnectionServiceAdapter(asInterface, info);
                    return true;
                case 2:
                    com.android.internal.telecom.IConnectionServiceAdapter asInterface2 = com.android.internal.telecom.IConnectionServiceAdapter.Stub.asInterface(parcel.readStrongBinder());
                    android.telecom.Logging.Session.Info info2 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeConnectionServiceAdapter(asInterface2, info2);
                    return true;
                case 3:
                    android.telecom.PhoneAccountHandle phoneAccountHandle = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    java.lang.String readString = parcel.readString();
                    android.telecom.ConnectionRequest connectionRequest = (android.telecom.ConnectionRequest) parcel.readTypedObject(android.telecom.ConnectionRequest.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    android.telecom.Logging.Session.Info info3 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    createConnection(phoneAccountHandle, readString, connectionRequest, readBoolean, readBoolean2, info3);
                    return true;
                case 4:
                    java.lang.String readString2 = parcel.readString();
                    android.telecom.Logging.Session.Info info4 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    createConnectionComplete(readString2, info4);
                    return true;
                case 5:
                    android.telecom.PhoneAccountHandle phoneAccountHandle2 = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    java.lang.String readString3 = parcel.readString();
                    android.telecom.ConnectionRequest connectionRequest2 = (android.telecom.ConnectionRequest) parcel.readTypedObject(android.telecom.ConnectionRequest.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    android.telecom.Logging.Session.Info info5 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    createConnectionFailed(phoneAccountHandle2, readString3, connectionRequest2, readBoolean3, info5);
                    return true;
                case 6:
                    android.telecom.PhoneAccountHandle phoneAccountHandle3 = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    java.lang.String readString4 = parcel.readString();
                    android.telecom.ConnectionRequest connectionRequest3 = (android.telecom.ConnectionRequest) parcel.readTypedObject(android.telecom.ConnectionRequest.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    boolean readBoolean5 = parcel.readBoolean();
                    android.telecom.Logging.Session.Info info6 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    createConference(phoneAccountHandle3, readString4, connectionRequest3, readBoolean4, readBoolean5, info6);
                    return true;
                case 7:
                    java.lang.String readString5 = parcel.readString();
                    android.telecom.Logging.Session.Info info7 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    createConferenceComplete(readString5, info7);
                    return true;
                case 8:
                    android.telecom.PhoneAccountHandle phoneAccountHandle4 = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    java.lang.String readString6 = parcel.readString();
                    android.telecom.ConnectionRequest connectionRequest4 = (android.telecom.ConnectionRequest) parcel.readTypedObject(android.telecom.ConnectionRequest.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    android.telecom.Logging.Session.Info info8 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    createConferenceFailed(phoneAccountHandle4, readString6, connectionRequest4, readBoolean6, info8);
                    return true;
                case 9:
                    java.lang.String readString7 = parcel.readString();
                    android.telecom.Logging.Session.Info info9 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    abort(readString7, info9);
                    return true;
                case 10:
                    java.lang.String readString8 = parcel.readString();
                    int readInt = parcel.readInt();
                    android.telecom.Logging.Session.Info info10 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    answerVideo(readString8, readInt, info10);
                    return true;
                case 11:
                    java.lang.String readString9 = parcel.readString();
                    android.telecom.Logging.Session.Info info11 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    answer(readString9, info11);
                    return true;
                case 12:
                    java.lang.String readString10 = parcel.readString();
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.telecom.Logging.Session.Info info12 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    deflect(readString10, uri, info12);
                    return true;
                case 13:
                    java.lang.String readString11 = parcel.readString();
                    android.telecom.Logging.Session.Info info13 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    reject(readString11, info13);
                    return true;
                case 14:
                    java.lang.String readString12 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    android.telecom.Logging.Session.Info info14 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    rejectWithReason(readString12, readInt2, info14);
                    return true;
                case 15:
                    java.lang.String readString13 = parcel.readString();
                    java.lang.String readString14 = parcel.readString();
                    android.telecom.Logging.Session.Info info15 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    rejectWithMessage(readString13, readString14, info15);
                    return true;
                case 16:
                    java.lang.String readString15 = parcel.readString();
                    android.net.Uri uri2 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    boolean readBoolean7 = parcel.readBoolean();
                    android.telecom.Logging.Session.Info info16 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    transfer(readString15, uri2, readBoolean7, info16);
                    return true;
                case 17:
                    java.lang.String readString16 = parcel.readString();
                    java.lang.String readString17 = parcel.readString();
                    android.telecom.Logging.Session.Info info17 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    consultativeTransfer(readString16, readString17, info17);
                    return true;
                case 18:
                    java.lang.String readString18 = parcel.readString();
                    android.telecom.Logging.Session.Info info18 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    disconnect(readString18, info18);
                    return true;
                case 19:
                    java.lang.String readString19 = parcel.readString();
                    android.telecom.Logging.Session.Info info19 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    silence(readString19, info19);
                    return true;
                case 20:
                    java.lang.String readString20 = parcel.readString();
                    android.telecom.Logging.Session.Info info20 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    hold(readString20, info20);
                    return true;
                case 21:
                    java.lang.String readString21 = parcel.readString();
                    android.telecom.Logging.Session.Info info21 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    unhold(readString21, info21);
                    return true;
                case 22:
                    java.lang.String readString22 = parcel.readString();
                    android.telecom.CallAudioState callAudioState = (android.telecom.CallAudioState) parcel.readTypedObject(android.telecom.CallAudioState.CREATOR);
                    android.telecom.Logging.Session.Info info22 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallAudioStateChanged(readString22, callAudioState, info22);
                    return true;
                case 23:
                    java.lang.String readString23 = parcel.readString();
                    android.telecom.CallEndpoint callEndpoint = (android.telecom.CallEndpoint) parcel.readTypedObject(android.telecom.CallEndpoint.CREATOR);
                    android.telecom.Logging.Session.Info info23 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallEndpointChanged(readString23, callEndpoint, info23);
                    return true;
                case 24:
                    java.lang.String readString24 = parcel.readString();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telecom.CallEndpoint.CREATOR);
                    android.telecom.Logging.Session.Info info24 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAvailableCallEndpointsChanged(readString24, createTypedArrayList, info24);
                    return true;
                case 25:
                    java.lang.String readString25 = parcel.readString();
                    boolean readBoolean8 = parcel.readBoolean();
                    android.telecom.Logging.Session.Info info25 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMuteStateChanged(readString25, readBoolean8, info25);
                    return true;
                case 26:
                    java.lang.String readString26 = parcel.readString();
                    char readInt3 = (char) parcel.readInt();
                    android.telecom.Logging.Session.Info info26 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    playDtmfTone(readString26, readInt3, info26);
                    return true;
                case 27:
                    java.lang.String readString27 = parcel.readString();
                    android.telecom.Logging.Session.Info info27 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopDtmfTone(readString27, info27);
                    return true;
                case 28:
                    java.lang.String readString28 = parcel.readString();
                    java.lang.String readString29 = parcel.readString();
                    android.telecom.Logging.Session.Info info28 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    conference(readString28, readString29, info28);
                    return true;
                case 29:
                    java.lang.String readString30 = parcel.readString();
                    android.telecom.Logging.Session.Info info29 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    splitFromConference(readString30, info29);
                    return true;
                case 30:
                    java.lang.String readString31 = parcel.readString();
                    android.telecom.Logging.Session.Info info30 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    mergeConference(readString31, info30);
                    return true;
                case 31:
                    java.lang.String readString32 = parcel.readString();
                    android.telecom.Logging.Session.Info info31 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    swapConference(readString32, info31);
                    return true;
                case 32:
                    java.lang.String readString33 = parcel.readString();
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.net.Uri.CREATOR);
                    android.telecom.Logging.Session.Info info32 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    addConferenceParticipants(readString33, createTypedArrayList2, info32);
                    return true;
                case 33:
                    java.lang.String readString34 = parcel.readString();
                    boolean readBoolean9 = parcel.readBoolean();
                    android.telecom.Logging.Session.Info info33 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPostDialContinue(readString34, readBoolean9, info33);
                    return true;
                case 34:
                    java.lang.String readString35 = parcel.readString();
                    android.telecom.Logging.Session.Info info34 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    pullExternalCall(readString35, info34);
                    return true;
                case 35:
                    java.lang.String readString36 = parcel.readString();
                    java.lang.String readString37 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.telecom.Logging.Session.Info info35 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCallEvent(readString36, readString37, bundle, info35);
                    return true;
                case 36:
                    java.lang.String readString38 = parcel.readString();
                    android.telecom.Connection.CallFilteringCompletionInfo callFilteringCompletionInfo = (android.telecom.Connection.CallFilteringCompletionInfo) parcel.readTypedObject(android.telecom.Connection.CallFilteringCompletionInfo.CREATOR);
                    android.telecom.Logging.Session.Info info36 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallFilteringCompleted(readString38, callFilteringCompletionInfo, info36);
                    return true;
                case 37:
                    java.lang.String readString39 = parcel.readString();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.telecom.Logging.Session.Info info37 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onExtrasChanged(readString39, bundle2, info37);
                    return true;
                case 38:
                    java.lang.String readString40 = parcel.readString();
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.os.ParcelFileDescriptor parcelFileDescriptor2 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.telecom.Logging.Session.Info info38 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    startRtt(readString40, parcelFileDescriptor, parcelFileDescriptor2, info38);
                    return true;
                case 39:
                    java.lang.String readString41 = parcel.readString();
                    android.telecom.Logging.Session.Info info39 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopRtt(readString41, info39);
                    return true;
                case 40:
                    java.lang.String readString42 = parcel.readString();
                    android.os.ParcelFileDescriptor parcelFileDescriptor3 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.os.ParcelFileDescriptor parcelFileDescriptor4 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.telecom.Logging.Session.Info info40 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    respondToRttUpgradeRequest(readString42, parcelFileDescriptor3, parcelFileDescriptor4, info40);
                    return true;
                case 41:
                    android.telecom.Logging.Session.Info info41 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    connectionServiceFocusLost(info41);
                    return true;
                case 42:
                    android.telecom.Logging.Session.Info info42 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    connectionServiceFocusGained(info42);
                    return true;
                case 43:
                    java.lang.String readString43 = parcel.readString();
                    android.telecom.ConnectionRequest connectionRequest5 = (android.telecom.ConnectionRequest) parcel.readTypedObject(android.telecom.ConnectionRequest.CREATOR);
                    int readInt4 = parcel.readInt();
                    android.telecom.Logging.Session.Info info43 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    handoverFailed(readString43, connectionRequest5, readInt4, info43);
                    return true;
                case 44:
                    java.lang.String readString44 = parcel.readString();
                    android.telecom.Logging.Session.Info info44 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    handoverComplete(readString44, info44);
                    return true;
                case 45:
                    java.lang.String readString45 = parcel.readString();
                    boolean readBoolean10 = parcel.readBoolean();
                    android.telecom.Logging.Session.Info info45 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onUsingAlternativeUi(readString45, readBoolean10, info45);
                    return true;
                case 46:
                    java.lang.String readString46 = parcel.readString();
                    boolean readBoolean11 = parcel.readBoolean();
                    android.telecom.Logging.Session.Info info46 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTrackedByNonUiService(readString46, readBoolean11, info46);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telecom.IConnectionService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void addConnectionServiceAdapter(com.android.internal.telecom.IConnectionServiceAdapter iConnectionServiceAdapter, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iConnectionServiceAdapter);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void removeConnectionServiceAdapter(com.android.internal.telecom.IConnectionServiceAdapter iConnectionServiceAdapter, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iConnectionServiceAdapter);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void createConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, android.telecom.ConnectionRequest connectionRequest, boolean z, boolean z2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(connectionRequest, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void createConnectionComplete(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void createConnectionFailed(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, android.telecom.ConnectionRequest connectionRequest, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(connectionRequest, 0);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void createConference(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, android.telecom.ConnectionRequest connectionRequest, boolean z, boolean z2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(connectionRequest, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void createConferenceComplete(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void createConferenceFailed(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, android.telecom.ConnectionRequest connectionRequest, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(connectionRequest, 0);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void abort(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void answerVideo(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void answer(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void deflect(java.lang.String str, android.net.Uri uri, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void reject(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void rejectWithReason(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void rejectWithMessage(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void transfer(java.lang.String str, android.net.Uri uri, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void consultativeTransfer(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void disconnect(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void silence(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void hold(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void unhold(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onCallAudioStateChanged(java.lang.String str, android.telecom.CallAudioState callAudioState, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(callAudioState, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onCallEndpointChanged(java.lang.String str, android.telecom.CallEndpoint callEndpoint, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(callEndpoint, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onAvailableCallEndpointsChanged(java.lang.String str, java.util.List<android.telecom.CallEndpoint> list, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onMuteStateChanged(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void playDtmfTone(java.lang.String str, char c, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(c);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void stopDtmfTone(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void conference(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void splitFromConference(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void mergeConference(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void swapConference(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void addConferenceParticipants(java.lang.String str, java.util.List<android.net.Uri> list, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onPostDialContinue(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void pullExternalCall(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void sendCallEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(35, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onCallFilteringCompleted(java.lang.String str, android.telecom.Connection.CallFilteringCompletionInfo callFilteringCompletionInfo, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(callFilteringCompletionInfo, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(36, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onExtrasChanged(java.lang.String str, android.os.Bundle bundle, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(37, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void startRtt(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(parcelFileDescriptor2, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(38, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void stopRtt(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(39, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void respondToRttUpgradeRequest(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(parcelFileDescriptor2, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(40, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void connectionServiceFocusLost(android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(41, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void connectionServiceFocusGained(android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(42, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void handoverFailed(java.lang.String str, android.telecom.ConnectionRequest connectionRequest, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(connectionRequest, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(43, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void handoverComplete(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(44, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onUsingAlternativeUi(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(45, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onTrackedByNonUiService(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(46, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 45;
        }
    }
}
