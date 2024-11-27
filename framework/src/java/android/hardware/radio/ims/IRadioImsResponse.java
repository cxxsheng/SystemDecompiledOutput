package android.hardware.radio.ims;

/* loaded from: classes2.dex */
public interface IRadioImsResponse extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$radio$ims$IRadioImsResponse".replace('$', '.');
    public static final java.lang.String HASH = "ec0dfedf764f3916783848c540ad312a74fa755d";
    public static final int VERSION = 2;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void sendAnbrQueryResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setSrvccCallInfoResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void startImsTrafficResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.ims.ConnectionFailureInfo connectionFailureInfo) throws android.os.RemoteException;

    void stopImsTrafficResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void triggerEpsFallbackResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void updateImsCallStatusResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void updateImsRegistrationInfoResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.ims.IRadioImsResponse {
        @Override // android.hardware.radio.ims.IRadioImsResponse
        public void setSrvccCallInfoResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioImsResponse
        public void updateImsRegistrationInfoResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioImsResponse
        public void startImsTrafficResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.ims.ConnectionFailureInfo connectionFailureInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioImsResponse
        public void stopImsTrafficResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioImsResponse
        public void triggerEpsFallbackResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioImsResponse
        public void sendAnbrQueryResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioImsResponse
        public void updateImsCallStatusResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioImsResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.ims.IRadioImsResponse
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.ims.IRadioImsResponse {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_sendAnbrQueryResponse = 6;
        static final int TRANSACTION_setSrvccCallInfoResponse = 1;
        static final int TRANSACTION_startImsTrafficResponse = 3;
        static final int TRANSACTION_stopImsTrafficResponse = 4;
        static final int TRANSACTION_triggerEpsFallbackResponse = 5;
        static final int TRANSACTION_updateImsCallStatusResponse = 7;
        static final int TRANSACTION_updateImsRegistrationInfoResponse = 2;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.ims.IRadioImsResponse asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.ims.IRadioImsResponse)) {
                return (android.hardware.radio.ims.IRadioImsResponse) queryLocalInterface;
            }
            return new android.hardware.radio.ims.IRadioImsResponse.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSrvccCallInfoResponse(radioResponseInfo);
                    return true;
                case 2:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo2 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateImsRegistrationInfoResponse(radioResponseInfo2);
                    return true;
                case 3:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo3 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.ims.ConnectionFailureInfo connectionFailureInfo = (android.hardware.radio.ims.ConnectionFailureInfo) parcel.readTypedObject(android.hardware.radio.ims.ConnectionFailureInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    startImsTrafficResponse(radioResponseInfo3, connectionFailureInfo);
                    return true;
                case 4:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo4 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopImsTrafficResponse(radioResponseInfo4);
                    return true;
                case 5:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo5 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    triggerEpsFallbackResponse(radioResponseInfo5);
                    return true;
                case 6:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo6 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendAnbrQueryResponse(radioResponseInfo6);
                    return true;
                case 7:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo7 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateImsCallStatusResponse(radioResponseInfo7);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.radio.ims.IRadioImsResponse {
            private android.os.IBinder mRemote;
            private int mCachedVersion = -1;
            private java.lang.String mCachedHash = "-1";

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.radio.ims.IRadioImsResponse
            public void setSrvccCallInfoResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setSrvccCallInfoResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioImsResponse
            public void updateImsRegistrationInfoResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method updateImsRegistrationInfoResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioImsResponse
            public void startImsTrafficResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.ims.ConnectionFailureInfo connectionFailureInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(connectionFailureInfo, 0);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method startImsTrafficResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioImsResponse
            public void stopImsTrafficResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method stopImsTrafficResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioImsResponse
            public void triggerEpsFallbackResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method triggerEpsFallbackResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioImsResponse
            public void sendAnbrQueryResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendAnbrQueryResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioImsResponse
            public void updateImsCallStatusResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method updateImsCallStatusResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioImsResponse
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.radio.ims.IRadioImsResponse
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (java.lang.Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
