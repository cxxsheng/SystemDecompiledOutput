package com.android.internal.telecom;

/* loaded from: classes5.dex */
public interface IConnectionServiceAdapter extends android.os.IInterface {
    void addConferenceCall(java.lang.String str, android.telecom.ParcelableConference parcelableConference, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void addExistingConnection(java.lang.String str, android.telecom.ParcelableConnection parcelableConnection, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void handleCreateConferenceComplete(java.lang.String str, android.telecom.ConnectionRequest connectionRequest, android.telecom.ParcelableConference parcelableConference, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void handleCreateConnectionComplete(java.lang.String str, android.telecom.ConnectionRequest connectionRequest, android.telecom.ParcelableConnection parcelableConnection, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void onConnectionEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void onConnectionServiceFocusReleased(android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void onPhoneAccountChanged(java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void onPostDialChar(java.lang.String str, char c, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void onPostDialWait(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void onRemoteRttRequest(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void onRttInitiationFailure(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void onRttInitiationSuccess(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void onRttSessionRemotelyTerminated(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void putExtras(java.lang.String str, android.os.Bundle bundle, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void queryLocation(java.lang.String str, long j, java.lang.String str2, android.os.ResultReceiver resultReceiver, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void queryRemoteConnectionServices(com.android.internal.telecom.RemoteServiceCallback remoteServiceCallback, java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void removeCall(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void removeExtras(java.lang.String str, java.util.List<java.lang.String> list, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void requestCallEndpointChange(java.lang.String str, android.telecom.CallEndpoint callEndpoint, android.os.ResultReceiver resultReceiver, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void resetConnectionTime(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void setActive(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void setAddress(java.lang.String str, android.net.Uri uri, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void setAudioRoute(java.lang.String str, int i, java.lang.String str2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void setCallDirection(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void setCallerDisplayName(java.lang.String str, java.lang.String str2, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void setConferenceMergeFailed(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void setConferenceState(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void setConferenceableConnections(java.lang.String str, java.util.List<java.lang.String> list, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void setConnectionCapabilities(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void setConnectionProperties(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void setDialing(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void setDisconnected(java.lang.String str, android.telecom.DisconnectCause disconnectCause, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void setIsConferenced(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void setIsVoipAudioMode(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void setOnHold(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void setPulling(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void setRingbackRequested(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void setRinging(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void setStatusHints(java.lang.String str, android.telecom.StatusHints statusHints, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void setVideoProvider(java.lang.String str, com.android.internal.telecom.IVideoProvider iVideoProvider, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    void setVideoState(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telecom.IConnectionServiceAdapter {
        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void handleCreateConnectionComplete(java.lang.String str, android.telecom.ConnectionRequest connectionRequest, android.telecom.ParcelableConnection parcelableConnection, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void handleCreateConferenceComplete(java.lang.String str, android.telecom.ConnectionRequest connectionRequest, android.telecom.ParcelableConference parcelableConference, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setActive(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setRinging(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setDialing(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setPulling(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setDisconnected(java.lang.String str, android.telecom.DisconnectCause disconnectCause, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setOnHold(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setRingbackRequested(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setConnectionCapabilities(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setConnectionProperties(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setIsConferenced(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setConferenceMergeFailed(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void addConferenceCall(java.lang.String str, android.telecom.ParcelableConference parcelableConference, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void removeCall(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onPostDialWait(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onPostDialChar(java.lang.String str, char c, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void queryRemoteConnectionServices(com.android.internal.telecom.RemoteServiceCallback remoteServiceCallback, java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setVideoProvider(java.lang.String str, com.android.internal.telecom.IVideoProvider iVideoProvider, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setVideoState(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setIsVoipAudioMode(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setStatusHints(java.lang.String str, android.telecom.StatusHints statusHints, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setAddress(java.lang.String str, android.net.Uri uri, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setCallerDisplayName(java.lang.String str, java.lang.String str2, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setConferenceableConnections(java.lang.String str, java.util.List<java.lang.String> list, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void addExistingConnection(java.lang.String str, android.telecom.ParcelableConnection parcelableConnection, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void putExtras(java.lang.String str, android.os.Bundle bundle, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void removeExtras(java.lang.String str, java.util.List<java.lang.String> list, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setAudioRoute(java.lang.String str, int i, java.lang.String str2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void requestCallEndpointChange(java.lang.String str, android.telecom.CallEndpoint callEndpoint, android.os.ResultReceiver resultReceiver, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onConnectionEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onRttInitiationSuccess(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onRttInitiationFailure(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onRttSessionRemotelyTerminated(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onRemoteRttRequest(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onPhoneAccountChanged(java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onConnectionServiceFocusReleased(android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void resetConnectionTime(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setConferenceState(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setCallDirection(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void queryLocation(java.lang.String str, long j, java.lang.String str2, android.os.ResultReceiver resultReceiver, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telecom.IConnectionServiceAdapter {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.telecom.IConnectionServiceAdapter";
        static final int TRANSACTION_addConferenceCall = 14;
        static final int TRANSACTION_addExistingConnection = 26;
        static final int TRANSACTION_handleCreateConferenceComplete = 2;
        static final int TRANSACTION_handleCreateConnectionComplete = 1;
        static final int TRANSACTION_onConnectionEvent = 31;
        static final int TRANSACTION_onConnectionServiceFocusReleased = 37;
        static final int TRANSACTION_onPhoneAccountChanged = 36;
        static final int TRANSACTION_onPostDialChar = 17;
        static final int TRANSACTION_onPostDialWait = 16;
        static final int TRANSACTION_onRemoteRttRequest = 35;
        static final int TRANSACTION_onRttInitiationFailure = 33;
        static final int TRANSACTION_onRttInitiationSuccess = 32;
        static final int TRANSACTION_onRttSessionRemotelyTerminated = 34;
        static final int TRANSACTION_putExtras = 27;
        static final int TRANSACTION_queryLocation = 41;
        static final int TRANSACTION_queryRemoteConnectionServices = 18;
        static final int TRANSACTION_removeCall = 15;
        static final int TRANSACTION_removeExtras = 28;
        static final int TRANSACTION_requestCallEndpointChange = 30;
        static final int TRANSACTION_resetConnectionTime = 38;
        static final int TRANSACTION_setActive = 3;
        static final int TRANSACTION_setAddress = 23;
        static final int TRANSACTION_setAudioRoute = 29;
        static final int TRANSACTION_setCallDirection = 40;
        static final int TRANSACTION_setCallerDisplayName = 24;
        static final int TRANSACTION_setConferenceMergeFailed = 13;
        static final int TRANSACTION_setConferenceState = 39;
        static final int TRANSACTION_setConferenceableConnections = 25;
        static final int TRANSACTION_setConnectionCapabilities = 10;
        static final int TRANSACTION_setConnectionProperties = 11;
        static final int TRANSACTION_setDialing = 5;
        static final int TRANSACTION_setDisconnected = 7;
        static final int TRANSACTION_setIsConferenced = 12;
        static final int TRANSACTION_setIsVoipAudioMode = 21;
        static final int TRANSACTION_setOnHold = 8;
        static final int TRANSACTION_setPulling = 6;
        static final int TRANSACTION_setRingbackRequested = 9;
        static final int TRANSACTION_setRinging = 4;
        static final int TRANSACTION_setStatusHints = 22;
        static final int TRANSACTION_setVideoProvider = 19;
        static final int TRANSACTION_setVideoState = 20;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.telecom.IConnectionServiceAdapter asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telecom.IConnectionServiceAdapter)) {
                return (com.android.internal.telecom.IConnectionServiceAdapter) queryLocalInterface;
            }
            return new com.android.internal.telecom.IConnectionServiceAdapter.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "handleCreateConnectionComplete";
                case 2:
                    return "handleCreateConferenceComplete";
                case 3:
                    return "setActive";
                case 4:
                    return "setRinging";
                case 5:
                    return "setDialing";
                case 6:
                    return "setPulling";
                case 7:
                    return "setDisconnected";
                case 8:
                    return "setOnHold";
                case 9:
                    return "setRingbackRequested";
                case 10:
                    return "setConnectionCapabilities";
                case 11:
                    return "setConnectionProperties";
                case 12:
                    return "setIsConferenced";
                case 13:
                    return "setConferenceMergeFailed";
                case 14:
                    return "addConferenceCall";
                case 15:
                    return "removeCall";
                case 16:
                    return "onPostDialWait";
                case 17:
                    return "onPostDialChar";
                case 18:
                    return "queryRemoteConnectionServices";
                case 19:
                    return "setVideoProvider";
                case 20:
                    return "setVideoState";
                case 21:
                    return "setIsVoipAudioMode";
                case 22:
                    return "setStatusHints";
                case 23:
                    return "setAddress";
                case 24:
                    return "setCallerDisplayName";
                case 25:
                    return "setConferenceableConnections";
                case 26:
                    return "addExistingConnection";
                case 27:
                    return "putExtras";
                case 28:
                    return "removeExtras";
                case 29:
                    return "setAudioRoute";
                case 30:
                    return "requestCallEndpointChange";
                case 31:
                    return "onConnectionEvent";
                case 32:
                    return "onRttInitiationSuccess";
                case 33:
                    return "onRttInitiationFailure";
                case 34:
                    return "onRttSessionRemotelyTerminated";
                case 35:
                    return "onRemoteRttRequest";
                case 36:
                    return "onPhoneAccountChanged";
                case 37:
                    return "onConnectionServiceFocusReleased";
                case 38:
                    return "resetConnectionTime";
                case 39:
                    return "setConferenceState";
                case 40:
                    return "setCallDirection";
                case 41:
                    return "queryLocation";
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
                    java.lang.String readString = parcel.readString();
                    android.telecom.ConnectionRequest connectionRequest = (android.telecom.ConnectionRequest) parcel.readTypedObject(android.telecom.ConnectionRequest.CREATOR);
                    android.telecom.ParcelableConnection parcelableConnection = (android.telecom.ParcelableConnection) parcel.readTypedObject(android.telecom.ParcelableConnection.CREATOR);
                    android.telecom.Logging.Session.Info info = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleCreateConnectionComplete(readString, connectionRequest, parcelableConnection, info);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    android.telecom.ConnectionRequest connectionRequest2 = (android.telecom.ConnectionRequest) parcel.readTypedObject(android.telecom.ConnectionRequest.CREATOR);
                    android.telecom.ParcelableConference parcelableConference = (android.telecom.ParcelableConference) parcel.readTypedObject(android.telecom.ParcelableConference.CREATOR);
                    android.telecom.Logging.Session.Info info2 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleCreateConferenceComplete(readString2, connectionRequest2, parcelableConference, info2);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    android.telecom.Logging.Session.Info info3 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setActive(readString3, info3);
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    android.telecom.Logging.Session.Info info4 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRinging(readString4, info4);
                    return true;
                case 5:
                    java.lang.String readString5 = parcel.readString();
                    android.telecom.Logging.Session.Info info5 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDialing(readString5, info5);
                    return true;
                case 6:
                    java.lang.String readString6 = parcel.readString();
                    android.telecom.Logging.Session.Info info6 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPulling(readString6, info6);
                    return true;
                case 7:
                    java.lang.String readString7 = parcel.readString();
                    android.telecom.DisconnectCause disconnectCause = (android.telecom.DisconnectCause) parcel.readTypedObject(android.telecom.DisconnectCause.CREATOR);
                    android.telecom.Logging.Session.Info info7 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDisconnected(readString7, disconnectCause, info7);
                    return true;
                case 8:
                    java.lang.String readString8 = parcel.readString();
                    android.telecom.Logging.Session.Info info8 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setOnHold(readString8, info8);
                    return true;
                case 9:
                    java.lang.String readString9 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    android.telecom.Logging.Session.Info info9 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRingbackRequested(readString9, readBoolean, info9);
                    return true;
                case 10:
                    java.lang.String readString10 = parcel.readString();
                    int readInt = parcel.readInt();
                    android.telecom.Logging.Session.Info info10 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setConnectionCapabilities(readString10, readInt, info10);
                    return true;
                case 11:
                    java.lang.String readString11 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    android.telecom.Logging.Session.Info info11 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setConnectionProperties(readString11, readInt2, info11);
                    return true;
                case 12:
                    java.lang.String readString12 = parcel.readString();
                    java.lang.String readString13 = parcel.readString();
                    android.telecom.Logging.Session.Info info12 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setIsConferenced(readString12, readString13, info12);
                    return true;
                case 13:
                    java.lang.String readString14 = parcel.readString();
                    android.telecom.Logging.Session.Info info13 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setConferenceMergeFailed(readString14, info13);
                    return true;
                case 14:
                    java.lang.String readString15 = parcel.readString();
                    android.telecom.ParcelableConference parcelableConference2 = (android.telecom.ParcelableConference) parcel.readTypedObject(android.telecom.ParcelableConference.CREATOR);
                    android.telecom.Logging.Session.Info info14 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    addConferenceCall(readString15, parcelableConference2, info14);
                    return true;
                case 15:
                    java.lang.String readString16 = parcel.readString();
                    android.telecom.Logging.Session.Info info15 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeCall(readString16, info15);
                    return true;
                case 16:
                    java.lang.String readString17 = parcel.readString();
                    java.lang.String readString18 = parcel.readString();
                    android.telecom.Logging.Session.Info info16 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPostDialWait(readString17, readString18, info16);
                    return true;
                case 17:
                    java.lang.String readString19 = parcel.readString();
                    char readInt3 = (char) parcel.readInt();
                    android.telecom.Logging.Session.Info info17 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPostDialChar(readString19, readInt3, info17);
                    return true;
                case 18:
                    com.android.internal.telecom.RemoteServiceCallback asInterface = com.android.internal.telecom.RemoteServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString20 = parcel.readString();
                    android.telecom.Logging.Session.Info info18 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    queryRemoteConnectionServices(asInterface, readString20, info18);
                    return true;
                case 19:
                    java.lang.String readString21 = parcel.readString();
                    com.android.internal.telecom.IVideoProvider asInterface2 = com.android.internal.telecom.IVideoProvider.Stub.asInterface(parcel.readStrongBinder());
                    android.telecom.Logging.Session.Info info19 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setVideoProvider(readString21, asInterface2, info19);
                    return true;
                case 20:
                    java.lang.String readString22 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    android.telecom.Logging.Session.Info info20 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setVideoState(readString22, readInt4, info20);
                    return true;
                case 21:
                    java.lang.String readString23 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    android.telecom.Logging.Session.Info info21 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setIsVoipAudioMode(readString23, readBoolean2, info21);
                    return true;
                case 22:
                    java.lang.String readString24 = parcel.readString();
                    android.telecom.StatusHints statusHints = (android.telecom.StatusHints) parcel.readTypedObject(android.telecom.StatusHints.CREATOR);
                    android.telecom.Logging.Session.Info info22 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setStatusHints(readString24, statusHints, info22);
                    return true;
                case 23:
                    java.lang.String readString25 = parcel.readString();
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt5 = parcel.readInt();
                    android.telecom.Logging.Session.Info info23 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setAddress(readString25, uri, readInt5, info23);
                    return true;
                case 24:
                    java.lang.String readString26 = parcel.readString();
                    java.lang.String readString27 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    android.telecom.Logging.Session.Info info24 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCallerDisplayName(readString26, readString27, readInt6, info24);
                    return true;
                case 25:
                    java.lang.String readString28 = parcel.readString();
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    android.telecom.Logging.Session.Info info25 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setConferenceableConnections(readString28, createStringArrayList, info25);
                    return true;
                case 26:
                    java.lang.String readString29 = parcel.readString();
                    android.telecom.ParcelableConnection parcelableConnection2 = (android.telecom.ParcelableConnection) parcel.readTypedObject(android.telecom.ParcelableConnection.CREATOR);
                    android.telecom.Logging.Session.Info info26 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    addExistingConnection(readString29, parcelableConnection2, info26);
                    return true;
                case 27:
                    java.lang.String readString30 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.telecom.Logging.Session.Info info27 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    putExtras(readString30, bundle, info27);
                    return true;
                case 28:
                    java.lang.String readString31 = parcel.readString();
                    java.util.ArrayList<java.lang.String> createStringArrayList2 = parcel.createStringArrayList();
                    android.telecom.Logging.Session.Info info28 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeExtras(readString31, createStringArrayList2, info28);
                    return true;
                case 29:
                    java.lang.String readString32 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    java.lang.String readString33 = parcel.readString();
                    android.telecom.Logging.Session.Info info29 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setAudioRoute(readString32, readInt7, readString33, info29);
                    return true;
                case 30:
                    java.lang.String readString34 = parcel.readString();
                    android.telecom.CallEndpoint callEndpoint = (android.telecom.CallEndpoint) parcel.readTypedObject(android.telecom.CallEndpoint.CREATOR);
                    android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    android.telecom.Logging.Session.Info info30 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCallEndpointChange(readString34, callEndpoint, resultReceiver, info30);
                    return true;
                case 31:
                    java.lang.String readString35 = parcel.readString();
                    java.lang.String readString36 = parcel.readString();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.telecom.Logging.Session.Info info31 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onConnectionEvent(readString35, readString36, bundle2, info31);
                    return true;
                case 32:
                    java.lang.String readString37 = parcel.readString();
                    android.telecom.Logging.Session.Info info32 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRttInitiationSuccess(readString37, info32);
                    return true;
                case 33:
                    java.lang.String readString38 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    android.telecom.Logging.Session.Info info33 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRttInitiationFailure(readString38, readInt8, info33);
                    return true;
                case 34:
                    java.lang.String readString39 = parcel.readString();
                    android.telecom.Logging.Session.Info info34 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRttSessionRemotelyTerminated(readString39, info34);
                    return true;
                case 35:
                    java.lang.String readString40 = parcel.readString();
                    android.telecom.Logging.Session.Info info35 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRemoteRttRequest(readString40, info35);
                    return true;
                case 36:
                    java.lang.String readString41 = parcel.readString();
                    android.telecom.PhoneAccountHandle phoneAccountHandle = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    android.telecom.Logging.Session.Info info36 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPhoneAccountChanged(readString41, phoneAccountHandle, info36);
                    return true;
                case 37:
                    android.telecom.Logging.Session.Info info37 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onConnectionServiceFocusReleased(info37);
                    return true;
                case 38:
                    java.lang.String readString42 = parcel.readString();
                    android.telecom.Logging.Session.Info info38 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    resetConnectionTime(readString42, info38);
                    return true;
                case 39:
                    java.lang.String readString43 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    android.telecom.Logging.Session.Info info39 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setConferenceState(readString43, readBoolean3, info39);
                    return true;
                case 40:
                    java.lang.String readString44 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    android.telecom.Logging.Session.Info info40 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCallDirection(readString44, readInt9, info40);
                    return true;
                case 41:
                    java.lang.String readString45 = parcel.readString();
                    long readLong = parcel.readLong();
                    java.lang.String readString46 = parcel.readString();
                    android.os.ResultReceiver resultReceiver2 = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    android.telecom.Logging.Session.Info info41 = (android.telecom.Logging.Session.Info) parcel.readTypedObject(android.telecom.Logging.Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    queryLocation(readString45, readLong, readString46, resultReceiver2, info41);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telecom.IConnectionServiceAdapter {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void handleCreateConnectionComplete(java.lang.String str, android.telecom.ConnectionRequest connectionRequest, android.telecom.ParcelableConnection parcelableConnection, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(connectionRequest, 0);
                    obtain.writeTypedObject(parcelableConnection, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void handleCreateConferenceComplete(java.lang.String str, android.telecom.ConnectionRequest connectionRequest, android.telecom.ParcelableConference parcelableConference, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(connectionRequest, 0);
                    obtain.writeTypedObject(parcelableConference, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setActive(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setRinging(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setDialing(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setPulling(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setDisconnected(java.lang.String str, android.telecom.DisconnectCause disconnectCause, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(disconnectCause, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setOnHold(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setRingbackRequested(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setConnectionCapabilities(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setConnectionProperties(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setIsConferenced(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setConferenceMergeFailed(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void addConferenceCall(java.lang.String str, android.telecom.ParcelableConference parcelableConference, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelableConference, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void removeCall(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onPostDialWait(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onPostDialChar(java.lang.String str, char c, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(c);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void queryRemoteConnectionServices(com.android.internal.telecom.RemoteServiceCallback remoteServiceCallback, java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(remoteServiceCallback);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setVideoProvider(java.lang.String str, com.android.internal.telecom.IVideoProvider iVideoProvider, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iVideoProvider);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setVideoState(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setIsVoipAudioMode(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setStatusHints(java.lang.String str, android.telecom.StatusHints statusHints, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(statusHints, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setAddress(java.lang.String str, android.net.Uri uri, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setCallerDisplayName(java.lang.String str, java.lang.String str2, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setConferenceableConnections(java.lang.String str, java.util.List<java.lang.String> list, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void addExistingConnection(java.lang.String str, android.telecom.ParcelableConnection parcelableConnection, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelableConnection, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void putExtras(java.lang.String str, android.os.Bundle bundle, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void removeExtras(java.lang.String str, java.util.List<java.lang.String> list, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setAudioRoute(java.lang.String str, int i, java.lang.String str2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void requestCallEndpointChange(java.lang.String str, android.telecom.CallEndpoint callEndpoint, android.os.ResultReceiver resultReceiver, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(callEndpoint, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onConnectionEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onRttInitiationSuccess(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onRttInitiationFailure(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onRttSessionRemotelyTerminated(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onRemoteRttRequest(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(35, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onPhoneAccountChanged(java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(36, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onConnectionServiceFocusReleased(android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(37, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void resetConnectionTime(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(38, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setConferenceState(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(39, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setCallDirection(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(40, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void queryLocation(java.lang.String str, long j, java.lang.String str2, android.os.ResultReceiver resultReceiver, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IConnectionServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(resultReceiver, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(41, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 40;
        }
    }
}
