package com.android.internal.telecom;

/* loaded from: classes5.dex */
public interface IInCallAdapter extends android.os.IInterface {
    void addConferenceParticipants(java.lang.String str, java.util.List<android.net.Uri> list) throws android.os.RemoteException;

    void answerCall(java.lang.String str, int i) throws android.os.RemoteException;

    void conference(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void consultativeTransfer(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void deflectCall(java.lang.String str, android.net.Uri uri) throws android.os.RemoteException;

    void disconnectCall(java.lang.String str) throws android.os.RemoteException;

    void enterBackgroundAudioProcessing(java.lang.String str) throws android.os.RemoteException;

    void exitBackgroundAudioProcessing(java.lang.String str, boolean z) throws android.os.RemoteException;

    void handoverTo(java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle, int i, android.os.Bundle bundle) throws android.os.RemoteException;

    void holdCall(java.lang.String str) throws android.os.RemoteException;

    void mergeConference(java.lang.String str) throws android.os.RemoteException;

    void mute(boolean z) throws android.os.RemoteException;

    void phoneAccountSelected(java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle, boolean z) throws android.os.RemoteException;

    void playDtmfTone(java.lang.String str, char c) throws android.os.RemoteException;

    void postDialContinue(java.lang.String str, boolean z) throws android.os.RemoteException;

    void pullExternalCall(java.lang.String str) throws android.os.RemoteException;

    void putExtras(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    void rejectCall(java.lang.String str, boolean z, java.lang.String str2) throws android.os.RemoteException;

    void rejectCallWithReason(java.lang.String str, int i) throws android.os.RemoteException;

    void removeExtras(java.lang.String str, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    void requestCallEndpointChange(android.telecom.CallEndpoint callEndpoint, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void respondToRttRequest(java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    void sendCallEvent(java.lang.String str, java.lang.String str2, int i, android.os.Bundle bundle) throws android.os.RemoteException;

    void sendRttRequest(java.lang.String str) throws android.os.RemoteException;

    void setAudioRoute(int i, java.lang.String str) throws android.os.RemoteException;

    void setRttMode(java.lang.String str, int i) throws android.os.RemoteException;

    void splitFromConference(java.lang.String str) throws android.os.RemoteException;

    void stopDtmfTone(java.lang.String str) throws android.os.RemoteException;

    void stopRtt(java.lang.String str) throws android.os.RemoteException;

    void swapConference(java.lang.String str) throws android.os.RemoteException;

    void transferCall(java.lang.String str, android.net.Uri uri, boolean z) throws android.os.RemoteException;

    void turnOffProximitySensor(boolean z) throws android.os.RemoteException;

    void turnOnProximitySensor() throws android.os.RemoteException;

    void unholdCall(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telecom.IInCallAdapter {
        @Override // com.android.internal.telecom.IInCallAdapter
        public void answerCall(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void deflectCall(java.lang.String str, android.net.Uri uri) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void rejectCall(java.lang.String str, boolean z, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void rejectCallWithReason(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void transferCall(java.lang.String str, android.net.Uri uri, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void consultativeTransfer(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void disconnectCall(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void holdCall(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void unholdCall(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void mute(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void setAudioRoute(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void requestCallEndpointChange(android.telecom.CallEndpoint callEndpoint, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void enterBackgroundAudioProcessing(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void exitBackgroundAudioProcessing(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void playDtmfTone(java.lang.String str, char c) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void stopDtmfTone(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void postDialContinue(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void phoneAccountSelected(java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void conference(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void splitFromConference(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void mergeConference(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void swapConference(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void addConferenceParticipants(java.lang.String str, java.util.List<android.net.Uri> list) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void turnOnProximitySensor() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void turnOffProximitySensor(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void pullExternalCall(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void sendCallEvent(java.lang.String str, java.lang.String str2, int i, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void putExtras(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void removeExtras(java.lang.String str, java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void sendRttRequest(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void respondToRttRequest(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void stopRtt(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void setRttMode(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void handoverTo(java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle, int i, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telecom.IInCallAdapter {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.telecom.IInCallAdapter";
        static final int TRANSACTION_addConferenceParticipants = 23;
        static final int TRANSACTION_answerCall = 1;
        static final int TRANSACTION_conference = 19;
        static final int TRANSACTION_consultativeTransfer = 6;
        static final int TRANSACTION_deflectCall = 2;
        static final int TRANSACTION_disconnectCall = 7;
        static final int TRANSACTION_enterBackgroundAudioProcessing = 13;
        static final int TRANSACTION_exitBackgroundAudioProcessing = 14;
        static final int TRANSACTION_handoverTo = 34;
        static final int TRANSACTION_holdCall = 8;
        static final int TRANSACTION_mergeConference = 21;
        static final int TRANSACTION_mute = 10;
        static final int TRANSACTION_phoneAccountSelected = 18;
        static final int TRANSACTION_playDtmfTone = 15;
        static final int TRANSACTION_postDialContinue = 17;
        static final int TRANSACTION_pullExternalCall = 26;
        static final int TRANSACTION_putExtras = 28;
        static final int TRANSACTION_rejectCall = 3;
        static final int TRANSACTION_rejectCallWithReason = 4;
        static final int TRANSACTION_removeExtras = 29;
        static final int TRANSACTION_requestCallEndpointChange = 12;
        static final int TRANSACTION_respondToRttRequest = 31;
        static final int TRANSACTION_sendCallEvent = 27;
        static final int TRANSACTION_sendRttRequest = 30;
        static final int TRANSACTION_setAudioRoute = 11;
        static final int TRANSACTION_setRttMode = 33;
        static final int TRANSACTION_splitFromConference = 20;
        static final int TRANSACTION_stopDtmfTone = 16;
        static final int TRANSACTION_stopRtt = 32;
        static final int TRANSACTION_swapConference = 22;
        static final int TRANSACTION_transferCall = 5;
        static final int TRANSACTION_turnOffProximitySensor = 25;
        static final int TRANSACTION_turnOnProximitySensor = 24;
        static final int TRANSACTION_unholdCall = 9;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.telecom.IInCallAdapter asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telecom.IInCallAdapter)) {
                return (com.android.internal.telecom.IInCallAdapter) queryLocalInterface;
            }
            return new com.android.internal.telecom.IInCallAdapter.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "answerCall";
                case 2:
                    return "deflectCall";
                case 3:
                    return "rejectCall";
                case 4:
                    return "rejectCallWithReason";
                case 5:
                    return "transferCall";
                case 6:
                    return "consultativeTransfer";
                case 7:
                    return "disconnectCall";
                case 8:
                    return "holdCall";
                case 9:
                    return "unholdCall";
                case 10:
                    return android.media.MediaMetrics.Value.MUTE;
                case 11:
                    return "setAudioRoute";
                case 12:
                    return "requestCallEndpointChange";
                case 13:
                    return "enterBackgroundAudioProcessing";
                case 14:
                    return "exitBackgroundAudioProcessing";
                case 15:
                    return "playDtmfTone";
                case 16:
                    return "stopDtmfTone";
                case 17:
                    return "postDialContinue";
                case 18:
                    return "phoneAccountSelected";
                case 19:
                    return android.telephony.ims.ImsCallProfile.EXTRA_CONFERENCE_DEPRECATED;
                case 20:
                    return "splitFromConference";
                case 21:
                    return "mergeConference";
                case 22:
                    return "swapConference";
                case 23:
                    return "addConferenceParticipants";
                case 24:
                    return "turnOnProximitySensor";
                case 25:
                    return "turnOffProximitySensor";
                case 26:
                    return "pullExternalCall";
                case 27:
                    return "sendCallEvent";
                case 28:
                    return "putExtras";
                case 29:
                    return "removeExtras";
                case 30:
                    return "sendRttRequest";
                case 31:
                    return "respondToRttRequest";
                case 32:
                    return "stopRtt";
                case 33:
                    return "setRttMode";
                case 34:
                    return "handoverTo";
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    answerCall(readString, readInt);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    deflectCall(readString2, uri);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    rejectCall(readString3, readBoolean, readString4);
                    return true;
                case 4:
                    java.lang.String readString5 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    rejectCallWithReason(readString5, readInt2);
                    return true;
                case 5:
                    java.lang.String readString6 = parcel.readString();
                    android.net.Uri uri2 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    transferCall(readString6, uri2, readBoolean2);
                    return true;
                case 6:
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    consultativeTransfer(readString7, readString8);
                    return true;
                case 7:
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disconnectCall(readString9);
                    return true;
                case 8:
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    holdCall(readString10);
                    return true;
                case 9:
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unholdCall(readString11);
                    return true;
                case 10:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    mute(readBoolean3);
                    return true;
                case 11:
                    int readInt3 = parcel.readInt();
                    java.lang.String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAudioRoute(readInt3, readString12);
                    return true;
                case 12:
                    android.telecom.CallEndpoint callEndpoint = (android.telecom.CallEndpoint) parcel.readTypedObject(android.telecom.CallEndpoint.CREATOR);
                    android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCallEndpointChange(callEndpoint, resultReceiver);
                    return true;
                case 13:
                    java.lang.String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enterBackgroundAudioProcessing(readString13);
                    return true;
                case 14:
                    java.lang.String readString14 = parcel.readString();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    exitBackgroundAudioProcessing(readString14, readBoolean4);
                    return true;
                case 15:
                    java.lang.String readString15 = parcel.readString();
                    char readInt4 = (char) parcel.readInt();
                    parcel.enforceNoDataAvail();
                    playDtmfTone(readString15, readInt4);
                    return true;
                case 16:
                    java.lang.String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stopDtmfTone(readString16);
                    return true;
                case 17:
                    java.lang.String readString17 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    postDialContinue(readString17, readBoolean5);
                    return true;
                case 18:
                    java.lang.String readString18 = parcel.readString();
                    android.telecom.PhoneAccountHandle phoneAccountHandle = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    phoneAccountSelected(readString18, phoneAccountHandle, readBoolean6);
                    return true;
                case 19:
                    java.lang.String readString19 = parcel.readString();
                    java.lang.String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    conference(readString19, readString20);
                    return true;
                case 20:
                    java.lang.String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    splitFromConference(readString21);
                    return true;
                case 21:
                    java.lang.String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    mergeConference(readString22);
                    return true;
                case 22:
                    java.lang.String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    swapConference(readString23);
                    return true;
                case 23:
                    java.lang.String readString24 = parcel.readString();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.net.Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    addConferenceParticipants(readString24, createTypedArrayList);
                    return true;
                case 24:
                    turnOnProximitySensor();
                    return true;
                case 25:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    turnOffProximitySensor(readBoolean7);
                    return true;
                case 26:
                    java.lang.String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    pullExternalCall(readString25);
                    return true;
                case 27:
                    java.lang.String readString26 = parcel.readString();
                    java.lang.String readString27 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCallEvent(readString26, readString27, readInt5, bundle);
                    return true;
                case 28:
                    java.lang.String readString28 = parcel.readString();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    putExtras(readString28, bundle2);
                    return true;
                case 29:
                    java.lang.String readString29 = parcel.readString();
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    removeExtras(readString29, createStringArrayList);
                    return true;
                case 30:
                    java.lang.String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendRttRequest(readString30);
                    return true;
                case 31:
                    java.lang.String readString31 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    respondToRttRequest(readString31, readInt6, readBoolean8);
                    return true;
                case 32:
                    java.lang.String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stopRtt(readString32);
                    return true;
                case 33:
                    java.lang.String readString33 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRttMode(readString33, readInt7);
                    return true;
                case 34:
                    java.lang.String readString34 = parcel.readString();
                    android.telecom.PhoneAccountHandle phoneAccountHandle2 = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    int readInt8 = parcel.readInt();
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    handoverTo(readString34, phoneAccountHandle2, readInt8, bundle3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telecom.IInCallAdapter {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void answerCall(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void deflectCall(java.lang.String str, android.net.Uri uri) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void rejectCall(java.lang.String str, boolean z, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void rejectCallWithReason(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void transferCall(java.lang.String str, android.net.Uri uri, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void consultativeTransfer(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void disconnectCall(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void holdCall(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void unholdCall(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void mute(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void setAudioRoute(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void requestCallEndpointChange(android.telecom.CallEndpoint callEndpoint, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(callEndpoint, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void enterBackgroundAudioProcessing(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void exitBackgroundAudioProcessing(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void playDtmfTone(java.lang.String str, char c) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(c);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void stopDtmfTone(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void postDialContinue(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void phoneAccountSelected(java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void conference(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void splitFromConference(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void mergeConference(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void swapConference(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void addConferenceParticipants(java.lang.String str, java.util.List<android.net.Uri> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void turnOnProximitySensor() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void turnOffProximitySensor(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void pullExternalCall(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void sendCallEvent(java.lang.String str, java.lang.String str2, int i, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void putExtras(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void removeExtras(java.lang.String str, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void sendRttRequest(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void respondToRttRequest(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void stopRtt(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void setRttMode(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void handoverTo(java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle, int i, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 33;
        }
    }
}
