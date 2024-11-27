package com.android.internal.telecom;

/* loaded from: classes5.dex */
public interface ICallControl extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telecom.ICallControl";

    void answer(int i, java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void disconnect(java.lang.String str, android.telecom.DisconnectCause disconnectCause, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void requestCallEndpointChange(android.telecom.CallEndpoint callEndpoint, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void requestVideoState(int i, java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void sendEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException;

    void setActive(java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void setInactive(java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void setMuteState(boolean z, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void startCallStreaming(java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telecom.ICallControl {
        @Override // com.android.internal.telecom.ICallControl
        public void setActive(java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void answer(int i, java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void setInactive(java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void disconnect(java.lang.String str, android.telecom.DisconnectCause disconnectCause, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void startCallStreaming(java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void requestCallEndpointChange(android.telecom.CallEndpoint callEndpoint, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void setMuteState(boolean z, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void sendEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void requestVideoState(int i, java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telecom.ICallControl {
        static final int TRANSACTION_answer = 2;
        static final int TRANSACTION_disconnect = 4;
        static final int TRANSACTION_requestCallEndpointChange = 6;
        static final int TRANSACTION_requestVideoState = 9;
        static final int TRANSACTION_sendEvent = 8;
        static final int TRANSACTION_setActive = 1;
        static final int TRANSACTION_setInactive = 3;
        static final int TRANSACTION_setMuteState = 7;
        static final int TRANSACTION_startCallStreaming = 5;

        public Stub() {
            attachInterface(this, com.android.internal.telecom.ICallControl.DESCRIPTOR);
        }

        public static com.android.internal.telecom.ICallControl asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telecom.ICallControl.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telecom.ICallControl)) {
                return (com.android.internal.telecom.ICallControl) queryLocalInterface;
            }
            return new com.android.internal.telecom.ICallControl.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setActive";
                case 2:
                    return "answer";
                case 3:
                    return "setInactive";
                case 4:
                    return android.media.MediaMetrics.Value.DISCONNECT;
                case 5:
                    return "startCallStreaming";
                case 6:
                    return "requestCallEndpointChange";
                case 7:
                    return "setMuteState";
                case 8:
                    return "sendEvent";
                case 9:
                    return "requestVideoState";
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
                parcel.enforceInterface(com.android.internal.telecom.ICallControl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telecom.ICallControl.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    setActive(readString, resultReceiver);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    android.os.ResultReceiver resultReceiver2 = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    answer(readInt, readString2, resultReceiver2);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    android.os.ResultReceiver resultReceiver3 = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    setInactive(readString3, resultReceiver3);
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    android.telecom.DisconnectCause disconnectCause = (android.telecom.DisconnectCause) parcel.readTypedObject(android.telecom.DisconnectCause.CREATOR);
                    android.os.ResultReceiver resultReceiver4 = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    disconnect(readString4, disconnectCause, resultReceiver4);
                    return true;
                case 5:
                    java.lang.String readString5 = parcel.readString();
                    android.os.ResultReceiver resultReceiver5 = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    startCallStreaming(readString5, resultReceiver5);
                    return true;
                case 6:
                    android.telecom.CallEndpoint callEndpoint = (android.telecom.CallEndpoint) parcel.readTypedObject(android.telecom.CallEndpoint.CREATOR);
                    android.os.ResultReceiver resultReceiver6 = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCallEndpointChange(callEndpoint, resultReceiver6);
                    return true;
                case 7:
                    boolean readBoolean = parcel.readBoolean();
                    android.os.ResultReceiver resultReceiver7 = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    setMuteState(readBoolean, resultReceiver7);
                    return true;
                case 8:
                    java.lang.String readString6 = parcel.readString();
                    java.lang.String readString7 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendEvent(readString6, readString7, bundle);
                    return true;
                case 9:
                    int readInt2 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    android.os.ResultReceiver resultReceiver8 = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestVideoState(readInt2, readString8, resultReceiver8);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telecom.ICallControl {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telecom.ICallControl.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.ICallControl
            public void setActive(java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallControl.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void answer(int i, java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallControl.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void setInactive(java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallControl.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void disconnect(java.lang.String str, android.telecom.DisconnectCause disconnectCause, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallControl.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(disconnectCause, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void startCallStreaming(java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallControl.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void requestCallEndpointChange(android.telecom.CallEndpoint callEndpoint, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallControl.DESCRIPTOR);
                    obtain.writeTypedObject(callEndpoint, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void setMuteState(boolean z, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallControl.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void sendEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallControl.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void requestVideoState(int i, java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallControl.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}
