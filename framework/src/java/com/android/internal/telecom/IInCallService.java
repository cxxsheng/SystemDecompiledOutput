package com.android.internal.telecom;

/* loaded from: classes5.dex */
public interface IInCallService extends android.os.IInterface {
    void addCall(android.telecom.ParcelableCall parcelableCall) throws android.os.RemoteException;

    void bringToForeground(boolean z) throws android.os.RemoteException;

    void onAvailableCallEndpointsChanged(java.util.List<android.telecom.CallEndpoint> list) throws android.os.RemoteException;

    void onCallAudioStateChanged(android.telecom.CallAudioState callAudioState) throws android.os.RemoteException;

    void onCallEndpointChanged(android.telecom.CallEndpoint callEndpoint) throws android.os.RemoteException;

    void onCanAddCallChanged(boolean z) throws android.os.RemoteException;

    void onConnectionEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException;

    void onHandoverComplete(java.lang.String str) throws android.os.RemoteException;

    void onHandoverFailed(java.lang.String str, int i) throws android.os.RemoteException;

    void onMuteStateChanged(boolean z) throws android.os.RemoteException;

    void onRttInitiationFailure(java.lang.String str, int i) throws android.os.RemoteException;

    void onRttUpgradeRequest(java.lang.String str, int i) throws android.os.RemoteException;

    void setInCallAdapter(com.android.internal.telecom.IInCallAdapter iInCallAdapter) throws android.os.RemoteException;

    void setPostDial(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void setPostDialWait(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void silenceRinger() throws android.os.RemoteException;

    void updateCall(android.telecom.ParcelableCall parcelableCall) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telecom.IInCallService {
        @Override // com.android.internal.telecom.IInCallService
        public void setInCallAdapter(com.android.internal.telecom.IInCallAdapter iInCallAdapter) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void addCall(android.telecom.ParcelableCall parcelableCall) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void updateCall(android.telecom.ParcelableCall parcelableCall) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void setPostDial(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void setPostDialWait(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onCallAudioStateChanged(android.telecom.CallAudioState callAudioState) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onCallEndpointChanged(android.telecom.CallEndpoint callEndpoint) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onAvailableCallEndpointsChanged(java.util.List<android.telecom.CallEndpoint> list) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onMuteStateChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void bringToForeground(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onCanAddCallChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void silenceRinger() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onConnectionEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onRttUpgradeRequest(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onRttInitiationFailure(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onHandoverFailed(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onHandoverComplete(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telecom.IInCallService {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.telecom.IInCallService";
        static final int TRANSACTION_addCall = 2;
        static final int TRANSACTION_bringToForeground = 10;
        static final int TRANSACTION_onAvailableCallEndpointsChanged = 8;
        static final int TRANSACTION_onCallAudioStateChanged = 6;
        static final int TRANSACTION_onCallEndpointChanged = 7;
        static final int TRANSACTION_onCanAddCallChanged = 11;
        static final int TRANSACTION_onConnectionEvent = 13;
        static final int TRANSACTION_onHandoverComplete = 17;
        static final int TRANSACTION_onHandoverFailed = 16;
        static final int TRANSACTION_onMuteStateChanged = 9;
        static final int TRANSACTION_onRttInitiationFailure = 15;
        static final int TRANSACTION_onRttUpgradeRequest = 14;
        static final int TRANSACTION_setInCallAdapter = 1;
        static final int TRANSACTION_setPostDial = 4;
        static final int TRANSACTION_setPostDialWait = 5;
        static final int TRANSACTION_silenceRinger = 12;
        static final int TRANSACTION_updateCall = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.telecom.IInCallService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telecom.IInCallService)) {
                return (com.android.internal.telecom.IInCallService) queryLocalInterface;
            }
            return new com.android.internal.telecom.IInCallService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setInCallAdapter";
                case 2:
                    return "addCall";
                case 3:
                    return "updateCall";
                case 4:
                    return "setPostDial";
                case 5:
                    return "setPostDialWait";
                case 6:
                    return "onCallAudioStateChanged";
                case 7:
                    return "onCallEndpointChanged";
                case 8:
                    return "onAvailableCallEndpointsChanged";
                case 9:
                    return "onMuteStateChanged";
                case 10:
                    return "bringToForeground";
                case 11:
                    return "onCanAddCallChanged";
                case 12:
                    return "silenceRinger";
                case 13:
                    return "onConnectionEvent";
                case 14:
                    return "onRttUpgradeRequest";
                case 15:
                    return "onRttInitiationFailure";
                case 16:
                    return "onHandoverFailed";
                case 17:
                    return "onHandoverComplete";
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
                    com.android.internal.telecom.IInCallAdapter asInterface = com.android.internal.telecom.IInCallAdapter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setInCallAdapter(asInterface);
                    return true;
                case 2:
                    android.telecom.ParcelableCall parcelableCall = (android.telecom.ParcelableCall) parcel.readTypedObject(android.telecom.ParcelableCall.CREATOR);
                    parcel.enforceNoDataAvail();
                    addCall(parcelableCall);
                    return true;
                case 3:
                    android.telecom.ParcelableCall parcelableCall2 = (android.telecom.ParcelableCall) parcel.readTypedObject(android.telecom.ParcelableCall.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateCall(parcelableCall2);
                    return true;
                case 4:
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setPostDial(readString, readString2);
                    return true;
                case 5:
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setPostDialWait(readString3, readString4);
                    return true;
                case 6:
                    android.telecom.CallAudioState callAudioState = (android.telecom.CallAudioState) parcel.readTypedObject(android.telecom.CallAudioState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallAudioStateChanged(callAudioState);
                    return true;
                case 7:
                    android.telecom.CallEndpoint callEndpoint = (android.telecom.CallEndpoint) parcel.readTypedObject(android.telecom.CallEndpoint.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallEndpointChanged(callEndpoint);
                    return true;
                case 8:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telecom.CallEndpoint.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAvailableCallEndpointsChanged(createTypedArrayList);
                    return true;
                case 9:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onMuteStateChanged(readBoolean);
                    return true;
                case 10:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    bringToForeground(readBoolean2);
                    return true;
                case 11:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCanAddCallChanged(readBoolean3);
                    return true;
                case 12:
                    silenceRinger();
                    return true;
                case 13:
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onConnectionEvent(readString5, readString6, bundle);
                    return true;
                case 14:
                    java.lang.String readString7 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRttUpgradeRequest(readString7, readInt);
                    return true;
                case 15:
                    java.lang.String readString8 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRttInitiationFailure(readString8, readInt2);
                    return true;
                case 16:
                    java.lang.String readString9 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onHandoverFailed(readString9, readInt3);
                    return true;
                case 17:
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onHandoverComplete(readString10);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telecom.IInCallService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telecom.IInCallService.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.IInCallService
            public void setInCallAdapter(com.android.internal.telecom.IInCallAdapter iInCallAdapter) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInCallAdapter);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void addCall(android.telecom.ParcelableCall parcelableCall) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelableCall, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void updateCall(android.telecom.ParcelableCall parcelableCall) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelableCall, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void setPostDial(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void setPostDialWait(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void onCallAudioStateChanged(android.telecom.CallAudioState callAudioState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(callAudioState, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void onCallEndpointChanged(android.telecom.CallEndpoint callEndpoint) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(callEndpoint, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void onAvailableCallEndpointsChanged(java.util.List<android.telecom.CallEndpoint> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallService.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void onMuteStateChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void bringToForeground(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void onCanAddCallChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void silenceRinger() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallService.Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void onConnectionEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void onRttUpgradeRequest(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void onRttInitiationFailure(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void onHandoverFailed(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void onHandoverComplete(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInCallService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16;
        }
    }
}
