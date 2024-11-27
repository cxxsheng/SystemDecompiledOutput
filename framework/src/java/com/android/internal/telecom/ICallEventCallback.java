package com.android.internal.telecom;

/* loaded from: classes5.dex */
public interface ICallEventCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telecom.ICallEventCallback";

    void onAddCallControl(java.lang.String str, int i, com.android.internal.telecom.ICallControl iCallControl, android.telecom.CallException callException) throws android.os.RemoteException;

    void onAnswer(java.lang.String str, int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void onAvailableCallEndpointsChanged(java.lang.String str, java.util.List<android.telecom.CallEndpoint> list) throws android.os.RemoteException;

    void onCallEndpointChanged(java.lang.String str, android.telecom.CallEndpoint callEndpoint) throws android.os.RemoteException;

    void onCallStreamingFailed(java.lang.String str, int i) throws android.os.RemoteException;

    void onCallStreamingStarted(java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void onDisconnect(java.lang.String str, android.telecom.DisconnectCause disconnectCause, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void onEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException;

    void onMuteStateChanged(java.lang.String str, boolean z) throws android.os.RemoteException;

    void onSetActive(java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void onSetInactive(java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void onVideoStateChanged(java.lang.String str, int i) throws android.os.RemoteException;

    void removeCallFromTransactionalServiceWrapper(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telecom.ICallEventCallback {
        @Override // com.android.internal.telecom.ICallEventCallback
        public void onAddCallControl(java.lang.String str, int i, com.android.internal.telecom.ICallControl iCallControl, android.telecom.CallException callException) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onSetActive(java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onSetInactive(java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onAnswer(java.lang.String str, int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onDisconnect(java.lang.String str, android.telecom.DisconnectCause disconnectCause, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onCallStreamingStarted(java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onCallStreamingFailed(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onCallEndpointChanged(java.lang.String str, android.telecom.CallEndpoint callEndpoint) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onAvailableCallEndpointsChanged(java.lang.String str, java.util.List<android.telecom.CallEndpoint> list) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onMuteStateChanged(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onVideoStateChanged(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void removeCallFromTransactionalServiceWrapper(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telecom.ICallEventCallback {
        static final int TRANSACTION_onAddCallControl = 1;
        static final int TRANSACTION_onAnswer = 4;
        static final int TRANSACTION_onAvailableCallEndpointsChanged = 9;
        static final int TRANSACTION_onCallEndpointChanged = 8;
        static final int TRANSACTION_onCallStreamingFailed = 7;
        static final int TRANSACTION_onCallStreamingStarted = 6;
        static final int TRANSACTION_onDisconnect = 5;
        static final int TRANSACTION_onEvent = 12;
        static final int TRANSACTION_onMuteStateChanged = 10;
        static final int TRANSACTION_onSetActive = 2;
        static final int TRANSACTION_onSetInactive = 3;
        static final int TRANSACTION_onVideoStateChanged = 11;
        static final int TRANSACTION_removeCallFromTransactionalServiceWrapper = 13;

        public Stub() {
            attachInterface(this, com.android.internal.telecom.ICallEventCallback.DESCRIPTOR);
        }

        public static com.android.internal.telecom.ICallEventCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telecom.ICallEventCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telecom.ICallEventCallback)) {
                return (com.android.internal.telecom.ICallEventCallback) queryLocalInterface;
            }
            return new com.android.internal.telecom.ICallEventCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onAddCallControl";
                case 2:
                    return "onSetActive";
                case 3:
                    return "onSetInactive";
                case 4:
                    return "onAnswer";
                case 5:
                    return "onDisconnect";
                case 6:
                    return "onCallStreamingStarted";
                case 7:
                    return "onCallStreamingFailed";
                case 8:
                    return "onCallEndpointChanged";
                case 9:
                    return "onAvailableCallEndpointsChanged";
                case 10:
                    return "onMuteStateChanged";
                case 11:
                    return "onVideoStateChanged";
                case 12:
                    return "onEvent";
                case 13:
                    return "removeCallFromTransactionalServiceWrapper";
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
                parcel.enforceInterface(com.android.internal.telecom.ICallEventCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telecom.ICallEventCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    com.android.internal.telecom.ICallControl asInterface = com.android.internal.telecom.ICallControl.Stub.asInterface(parcel.readStrongBinder());
                    android.telecom.CallException callException = (android.telecom.CallException) parcel.readTypedObject(android.telecom.CallException.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAddCallControl(readString, readInt, asInterface, callException);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSetActive(readString2, resultReceiver);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    android.os.ResultReceiver resultReceiver2 = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSetInactive(readString3, resultReceiver2);
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    android.os.ResultReceiver resultReceiver3 = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAnswer(readString4, readInt2, resultReceiver3);
                    return true;
                case 5:
                    java.lang.String readString5 = parcel.readString();
                    android.telecom.DisconnectCause disconnectCause = (android.telecom.DisconnectCause) parcel.readTypedObject(android.telecom.DisconnectCause.CREATOR);
                    android.os.ResultReceiver resultReceiver4 = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDisconnect(readString5, disconnectCause, resultReceiver4);
                    return true;
                case 6:
                    java.lang.String readString6 = parcel.readString();
                    android.os.ResultReceiver resultReceiver5 = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallStreamingStarted(readString6, resultReceiver5);
                    return true;
                case 7:
                    java.lang.String readString7 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCallStreamingFailed(readString7, readInt3);
                    return true;
                case 8:
                    java.lang.String readString8 = parcel.readString();
                    android.telecom.CallEndpoint callEndpoint = (android.telecom.CallEndpoint) parcel.readTypedObject(android.telecom.CallEndpoint.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallEndpointChanged(readString8, callEndpoint);
                    return true;
                case 9:
                    java.lang.String readString9 = parcel.readString();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telecom.CallEndpoint.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAvailableCallEndpointsChanged(readString9, createTypedArrayList);
                    return true;
                case 10:
                    java.lang.String readString10 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onMuteStateChanged(readString10, readBoolean);
                    return true;
                case 11:
                    java.lang.String readString11 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoStateChanged(readString11, readInt4);
                    return true;
                case 12:
                    java.lang.String readString12 = parcel.readString();
                    java.lang.String readString13 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onEvent(readString12, readString13, bundle);
                    return true;
                case 13:
                    java.lang.String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeCallFromTransactionalServiceWrapper(readString14);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telecom.ICallEventCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telecom.ICallEventCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onAddCallControl(java.lang.String str, int i, com.android.internal.telecom.ICallControl iCallControl, android.telecom.CallException callException) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iCallControl);
                    obtain.writeTypedObject(callException, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onSetActive(java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onSetInactive(java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onAnswer(java.lang.String str, int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onDisconnect(java.lang.String str, android.telecom.DisconnectCause disconnectCause, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(disconnectCause, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onCallStreamingStarted(java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onCallStreamingFailed(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onCallEndpointChanged(java.lang.String str, android.telecom.CallEndpoint callEndpoint) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(callEndpoint, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onAvailableCallEndpointsChanged(java.lang.String str, java.util.List<android.telecom.CallEndpoint> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onMuteStateChanged(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onVideoStateChanged(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void removeCallFromTransactionalServiceWrapper(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }
    }
}
