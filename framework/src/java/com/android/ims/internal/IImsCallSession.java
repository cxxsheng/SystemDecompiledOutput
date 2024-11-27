package com.android.ims.internal;

/* loaded from: classes4.dex */
public interface IImsCallSession extends android.os.IInterface {
    void accept(int i, android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException;

    void callSessionNotifyAnbr(int i, int i2, int i3) throws android.os.RemoteException;

    void close() throws android.os.RemoteException;

    void consultativeTransfer(com.android.ims.internal.IImsCallSession iImsCallSession) throws android.os.RemoteException;

    void deflect(java.lang.String str) throws android.os.RemoteException;

    void extendToConference(java.lang.String[] strArr) throws android.os.RemoteException;

    java.lang.String getCallId() throws android.os.RemoteException;

    android.telephony.ims.ImsCallProfile getCallProfile() throws android.os.RemoteException;

    android.telephony.ims.ImsCallProfile getLocalCallProfile() throws android.os.RemoteException;

    java.lang.String getProperty(java.lang.String str) throws android.os.RemoteException;

    android.telephony.ims.ImsCallProfile getRemoteCallProfile() throws android.os.RemoteException;

    int getState() throws android.os.RemoteException;

    com.android.ims.internal.IImsVideoCallProvider getVideoCallProvider() throws android.os.RemoteException;

    void hold(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException;

    void inviteParticipants(java.lang.String[] strArr) throws android.os.RemoteException;

    boolean isInCall() throws android.os.RemoteException;

    boolean isMultiparty() throws android.os.RemoteException;

    void merge() throws android.os.RemoteException;

    void reject(int i) throws android.os.RemoteException;

    void removeParticipants(java.lang.String[] strArr) throws android.os.RemoteException;

    void resume(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException;

    void sendDtmf(char c, android.os.Message message) throws android.os.RemoteException;

    void sendRtpHeaderExtensions(java.util.List<android.telephony.ims.RtpHeaderExtension> list) throws android.os.RemoteException;

    void sendRttMessage(java.lang.String str) throws android.os.RemoteException;

    void sendRttModifyRequest(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void sendRttModifyResponse(boolean z) throws android.os.RemoteException;

    void sendUssd(java.lang.String str) throws android.os.RemoteException;

    @java.lang.Deprecated
    void setListener(android.telephony.ims.aidl.IImsCallSessionListener iImsCallSessionListener) throws android.os.RemoteException;

    void setMute(boolean z) throws android.os.RemoteException;

    void start(java.lang.String str, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void startConference(java.lang.String[] strArr, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void startDtmf(char c) throws android.os.RemoteException;

    void stopDtmf() throws android.os.RemoteException;

    void terminate(int i) throws android.os.RemoteException;

    void transfer(java.lang.String str, boolean z) throws android.os.RemoteException;

    void update(int i, android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException;

    public static class Default implements com.android.ims.internal.IImsCallSession {
        @Override // com.android.ims.internal.IImsCallSession
        public void close() throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public java.lang.String getCallId() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public android.telephony.ims.ImsCallProfile getCallProfile() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public android.telephony.ims.ImsCallProfile getLocalCallProfile() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public android.telephony.ims.ImsCallProfile getRemoteCallProfile() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public java.lang.String getProperty(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public int getState() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public boolean isInCall() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void setListener(android.telephony.ims.aidl.IImsCallSessionListener iImsCallSessionListener) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void setMute(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void start(java.lang.String str, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void startConference(java.lang.String[] strArr, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void accept(int i, android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void deflect(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void reject(int i) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void transfer(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void consultativeTransfer(com.android.ims.internal.IImsCallSession iImsCallSession) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void terminate(int i) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void hold(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void resume(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void merge() throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void update(int i, android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void extendToConference(java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void inviteParticipants(java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void removeParticipants(java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendDtmf(char c, android.os.Message message) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void startDtmf(char c) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void stopDtmf() throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendUssd(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public com.android.ims.internal.IImsVideoCallProvider getVideoCallProvider() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public boolean isMultiparty() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendRttModifyRequest(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendRttModifyResponse(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendRttMessage(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendRtpHeaderExtensions(java.util.List<android.telephony.ims.RtpHeaderExtension> list) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void callSessionNotifyAnbr(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.ims.internal.IImsCallSession {
        public static final java.lang.String DESCRIPTOR = "com.android.ims.internal.IImsCallSession";
        static final int TRANSACTION_accept = 13;
        static final int TRANSACTION_callSessionNotifyAnbr = 36;
        static final int TRANSACTION_close = 1;
        static final int TRANSACTION_consultativeTransfer = 17;
        static final int TRANSACTION_deflect = 14;
        static final int TRANSACTION_extendToConference = 23;
        static final int TRANSACTION_getCallId = 2;
        static final int TRANSACTION_getCallProfile = 3;
        static final int TRANSACTION_getLocalCallProfile = 4;
        static final int TRANSACTION_getProperty = 6;
        static final int TRANSACTION_getRemoteCallProfile = 5;
        static final int TRANSACTION_getState = 7;
        static final int TRANSACTION_getVideoCallProvider = 30;
        static final int TRANSACTION_hold = 19;
        static final int TRANSACTION_inviteParticipants = 24;
        static final int TRANSACTION_isInCall = 8;
        static final int TRANSACTION_isMultiparty = 31;
        static final int TRANSACTION_merge = 21;
        static final int TRANSACTION_reject = 15;
        static final int TRANSACTION_removeParticipants = 25;
        static final int TRANSACTION_resume = 20;
        static final int TRANSACTION_sendDtmf = 26;
        static final int TRANSACTION_sendRtpHeaderExtensions = 35;
        static final int TRANSACTION_sendRttMessage = 34;
        static final int TRANSACTION_sendRttModifyRequest = 32;
        static final int TRANSACTION_sendRttModifyResponse = 33;
        static final int TRANSACTION_sendUssd = 29;
        static final int TRANSACTION_setListener = 9;
        static final int TRANSACTION_setMute = 10;
        static final int TRANSACTION_start = 11;
        static final int TRANSACTION_startConference = 12;
        static final int TRANSACTION_startDtmf = 27;
        static final int TRANSACTION_stopDtmf = 28;
        static final int TRANSACTION_terminate = 18;
        static final int TRANSACTION_transfer = 16;
        static final int TRANSACTION_update = 22;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.ims.internal.IImsCallSession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.ims.internal.IImsCallSession)) {
                return (com.android.ims.internal.IImsCallSession) queryLocalInterface;
            }
            return new com.android.ims.internal.IImsCallSession.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "close";
                case 2:
                    return "getCallId";
                case 3:
                    return "getCallProfile";
                case 4:
                    return "getLocalCallProfile";
                case 5:
                    return "getRemoteCallProfile";
                case 6:
                    return "getProperty";
                case 7:
                    return "getState";
                case 8:
                    return "isInCall";
                case 9:
                    return "setListener";
                case 10:
                    return "setMute";
                case 11:
                    return "start";
                case 12:
                    return "startConference";
                case 13:
                    return "accept";
                case 14:
                    return "deflect";
                case 15:
                    return "reject";
                case 16:
                    return "transfer";
                case 17:
                    return "consultativeTransfer";
                case 18:
                    return "terminate";
                case 19:
                    return "hold";
                case 20:
                    return android.media.tv.interactive.TvInteractiveAppService.TIME_SHIFT_COMMAND_TYPE_RESUME;
                case 21:
                    return "merge";
                case 22:
                    return "update";
                case 23:
                    return "extendToConference";
                case 24:
                    return "inviteParticipants";
                case 25:
                    return "removeParticipants";
                case 26:
                    return "sendDtmf";
                case 27:
                    return "startDtmf";
                case 28:
                    return "stopDtmf";
                case 29:
                    return "sendUssd";
                case 30:
                    return "getVideoCallProvider";
                case 31:
                    return "isMultiparty";
                case 32:
                    return "sendRttModifyRequest";
                case 33:
                    return "sendRttModifyResponse";
                case 34:
                    return "sendRttMessage";
                case 35:
                    return "sendRtpHeaderExtensions";
                case 36:
                    return "callSessionNotifyAnbr";
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
                    close();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    java.lang.String callId = getCallId();
                    parcel2.writeNoException();
                    parcel2.writeString(callId);
                    return true;
                case 3:
                    android.telephony.ims.ImsCallProfile callProfile = getCallProfile();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(callProfile, 1);
                    return true;
                case 4:
                    android.telephony.ims.ImsCallProfile localCallProfile = getLocalCallProfile();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(localCallProfile, 1);
                    return true;
                case 5:
                    android.telephony.ims.ImsCallProfile remoteCallProfile = getRemoteCallProfile();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(remoteCallProfile, 1);
                    return true;
                case 6:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String property = getProperty(readString);
                    parcel2.writeNoException();
                    parcel2.writeString(property);
                    return true;
                case 7:
                    int state = getState();
                    parcel2.writeNoException();
                    parcel2.writeInt(state);
                    return true;
                case 8:
                    boolean isInCall = isInCall();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInCall);
                    return true;
                case 9:
                    android.telephony.ims.aidl.IImsCallSessionListener asInterface = android.telephony.ims.aidl.IImsCallSessionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMute(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    java.lang.String readString2 = parcel.readString();
                    android.telephony.ims.ImsCallProfile imsCallProfile = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    start(readString2, imsCallProfile);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    android.telephony.ims.ImsCallProfile imsCallProfile2 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    startConference(createStringArray, imsCallProfile2);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt = parcel.readInt();
                    android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile = (android.telephony.ims.ImsStreamMediaProfile) parcel.readTypedObject(android.telephony.ims.ImsStreamMediaProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    accept(readInt, imsStreamMediaProfile);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deflect(readString3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reject(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    java.lang.String readString4 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    transfer(readString4, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    com.android.ims.internal.IImsCallSession asInterface2 = asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    consultativeTransfer(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    terminate(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile2 = (android.telephony.ims.ImsStreamMediaProfile) parcel.readTypedObject(android.telephony.ims.ImsStreamMediaProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    hold(imsStreamMediaProfile2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile3 = (android.telephony.ims.ImsStreamMediaProfile) parcel.readTypedObject(android.telephony.ims.ImsStreamMediaProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    resume(imsStreamMediaProfile3);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    merge();
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt4 = parcel.readInt();
                    android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile4 = (android.telephony.ims.ImsStreamMediaProfile) parcel.readTypedObject(android.telephony.ims.ImsStreamMediaProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    update(readInt4, imsStreamMediaProfile4);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    extendToConference(createStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    java.lang.String[] createStringArray3 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    inviteParticipants(createStringArray3);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    java.lang.String[] createStringArray4 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    removeParticipants(createStringArray4);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    char readInt5 = (char) parcel.readInt();
                    android.os.Message message = (android.os.Message) parcel.readTypedObject(android.os.Message.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendDtmf(readInt5, message);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    char readInt6 = (char) parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startDtmf(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    stopDtmf();
                    parcel2.writeNoException();
                    return true;
                case 29:
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendUssd(readString5);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    com.android.ims.internal.IImsVideoCallProvider videoCallProvider = getVideoCallProvider();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(videoCallProvider);
                    return true;
                case 31:
                    boolean isMultiparty = isMultiparty();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMultiparty);
                    return true;
                case 32:
                    android.telephony.ims.ImsCallProfile imsCallProfile3 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendRttModifyRequest(imsCallProfile3);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendRttModifyResponse(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendRttMessage(readString6);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telephony.ims.RtpHeaderExtension.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendRtpHeaderExtensions(createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callSessionNotifyAnbr(readInt7, readInt8, readInt9);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.ims.internal.IImsCallSession {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void close() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public java.lang.String getCallId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public android.telephony.ims.ImsCallProfile getCallProfile() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.ims.ImsCallProfile) obtain2.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public android.telephony.ims.ImsCallProfile getLocalCallProfile() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.ims.ImsCallProfile) obtain2.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public android.telephony.ims.ImsCallProfile getRemoteCallProfile() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.ims.ImsCallProfile) obtain2.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public java.lang.String getProperty(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public int getState() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public boolean isInCall() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void setListener(android.telephony.ims.aidl.IImsCallSessionListener iImsCallSessionListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSessionListener);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void setMute(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void start(java.lang.String str, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void startConference(java.lang.String[] strArr, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void accept(int i, android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(imsStreamMediaProfile, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void deflect(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void reject(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void transfer(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void consultativeTransfer(com.android.ims.internal.IImsCallSession iImsCallSession) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void terminate(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void hold(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(imsStreamMediaProfile, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void resume(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(imsStreamMediaProfile, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void merge() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void update(int i, android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(imsStreamMediaProfile, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void extendToConference(java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void inviteParticipants(java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void removeParticipants(java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void sendDtmf(char c, android.os.Message message) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeInt(c);
                    obtain.writeTypedObject(message, 0);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void startDtmf(char c) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeInt(c);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void stopDtmf() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void sendUssd(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public com.android.ims.internal.IImsVideoCallProvider getVideoCallProvider() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.IImsVideoCallProvider.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public boolean isMultiparty() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void sendRttModifyRequest(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void sendRttModifyResponse(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void sendRttMessage(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void sendRtpHeaderExtensions(java.util.List<android.telephony.ims.RtpHeaderExtension> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void callSessionNotifyAnbr(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 35;
        }
    }
}
