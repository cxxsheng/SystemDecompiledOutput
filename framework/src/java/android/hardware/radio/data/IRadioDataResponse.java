package android.hardware.radio.data;

/* loaded from: classes2.dex */
public interface IRadioDataResponse extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$radio$data$IRadioDataResponse".replace('$', '.');
    public static final java.lang.String HASH = "cd8913a3f9d39f1cc0a5fcf9e90257be94ec38df";
    public static final int VERSION = 3;

    void acknowledgeRequest(int i) throws android.os.RemoteException;

    void allocatePduSessionIdResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException;

    void cancelHandoverResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void deactivateDataCallResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void getDataCallListResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.data.SetupDataCallResult[] setupDataCallResultArr) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void getSlicingConfigResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.data.SlicingConfig slicingConfig) throws android.os.RemoteException;

    void releasePduSessionIdResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setDataAllowedResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setDataProfileResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setDataThrottlingResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setInitialAttachApnResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setupDataCallResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.data.SetupDataCallResult setupDataCallResult) throws android.os.RemoteException;

    void startHandoverResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void startKeepaliveResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.data.KeepaliveStatus keepaliveStatus) throws android.os.RemoteException;

    void stopKeepaliveResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.data.IRadioDataResponse {
        @Override // android.hardware.radio.data.IRadioDataResponse
        public void acknowledgeRequest(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void allocatePduSessionIdResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void cancelHandoverResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void deactivateDataCallResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void getDataCallListResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.data.SetupDataCallResult[] setupDataCallResultArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void getSlicingConfigResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.data.SlicingConfig slicingConfig) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void releasePduSessionIdResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void setDataAllowedResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void setDataProfileResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void setDataThrottlingResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void setInitialAttachApnResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void setupDataCallResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.data.SetupDataCallResult setupDataCallResult) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void startHandoverResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void startKeepaliveResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.data.KeepaliveStatus keepaliveStatus) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void stopKeepaliveResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.data.IRadioDataResponse {
        static final int TRANSACTION_acknowledgeRequest = 1;
        static final int TRANSACTION_allocatePduSessionIdResponse = 2;
        static final int TRANSACTION_cancelHandoverResponse = 3;
        static final int TRANSACTION_deactivateDataCallResponse = 4;
        static final int TRANSACTION_getDataCallListResponse = 5;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getSlicingConfigResponse = 6;
        static final int TRANSACTION_releasePduSessionIdResponse = 7;
        static final int TRANSACTION_setDataAllowedResponse = 8;
        static final int TRANSACTION_setDataProfileResponse = 9;
        static final int TRANSACTION_setDataThrottlingResponse = 10;
        static final int TRANSACTION_setInitialAttachApnResponse = 11;
        static final int TRANSACTION_setupDataCallResponse = 12;
        static final int TRANSACTION_startHandoverResponse = 13;
        static final int TRANSACTION_startKeepaliveResponse = 14;
        static final int TRANSACTION_stopKeepaliveResponse = 15;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.data.IRadioDataResponse asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.data.IRadioDataResponse)) {
                return (android.hardware.radio.data.IRadioDataResponse) queryLocalInterface;
            }
            return new android.hardware.radio.data.IRadioDataResponse.Stub.Proxy(iBinder);
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeRequest(readInt);
                    return true;
                case 2:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    allocatePduSessionIdResponse(radioResponseInfo, readInt2);
                    return true;
                case 3:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo2 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    cancelHandoverResponse(radioResponseInfo2);
                    return true;
                case 4:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo3 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    deactivateDataCallResponse(radioResponseInfo3);
                    return true;
                case 5:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo4 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.data.SetupDataCallResult[] setupDataCallResultArr = (android.hardware.radio.data.SetupDataCallResult[]) parcel.createTypedArray(android.hardware.radio.data.SetupDataCallResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    getDataCallListResponse(radioResponseInfo4, setupDataCallResultArr);
                    return true;
                case 6:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo5 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.data.SlicingConfig slicingConfig = (android.hardware.radio.data.SlicingConfig) parcel.readTypedObject(android.hardware.radio.data.SlicingConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSlicingConfigResponse(radioResponseInfo5, slicingConfig);
                    return true;
                case 7:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo6 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    releasePduSessionIdResponse(radioResponseInfo6);
                    return true;
                case 8:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo7 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDataAllowedResponse(radioResponseInfo7);
                    return true;
                case 9:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo8 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDataProfileResponse(radioResponseInfo8);
                    return true;
                case 10:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo9 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDataThrottlingResponse(radioResponseInfo9);
                    return true;
                case 11:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo10 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setInitialAttachApnResponse(radioResponseInfo10);
                    return true;
                case 12:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo11 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.data.SetupDataCallResult setupDataCallResult = (android.hardware.radio.data.SetupDataCallResult) parcel.readTypedObject(android.hardware.radio.data.SetupDataCallResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    setupDataCallResponse(radioResponseInfo11, setupDataCallResult);
                    return true;
                case 13:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo12 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    startHandoverResponse(radioResponseInfo12);
                    return true;
                case 14:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo13 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.data.KeepaliveStatus keepaliveStatus = (android.hardware.radio.data.KeepaliveStatus) parcel.readTypedObject(android.hardware.radio.data.KeepaliveStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    startKeepaliveResponse(radioResponseInfo13, keepaliveStatus);
                    return true;
                case 15:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo14 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopKeepaliveResponse(radioResponseInfo14);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.radio.data.IRadioDataResponse {
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

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void acknowledgeRequest(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method acknowledgeRequest is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void allocatePduSessionIdResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method allocatePduSessionIdResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void cancelHandoverResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method cancelHandoverResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void deactivateDataCallResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method deactivateDataCallResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void getDataCallListResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.data.SetupDataCallResult[] setupDataCallResultArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedArray(setupDataCallResultArr, 0);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getDataCallListResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void getSlicingConfigResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.data.SlicingConfig slicingConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(slicingConfig, 0);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getSlicingConfigResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void releasePduSessionIdResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method releasePduSessionIdResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void setDataAllowedResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setDataAllowedResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void setDataProfileResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(9, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setDataProfileResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void setDataThrottlingResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(10, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setDataThrottlingResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void setInitialAttachApnResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(11, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setInitialAttachApnResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void setupDataCallResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.data.SetupDataCallResult setupDataCallResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(setupDataCallResult, 0);
                    if (!this.mRemote.transact(12, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setupDataCallResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void startHandoverResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(13, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method startHandoverResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void startKeepaliveResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.data.KeepaliveStatus keepaliveStatus) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(keepaliveStatus, 0);
                    if (!this.mRemote.transact(14, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method startKeepaliveResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void stopKeepaliveResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(15, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method stopKeepaliveResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
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

            @Override // android.hardware.radio.data.IRadioDataResponse
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
