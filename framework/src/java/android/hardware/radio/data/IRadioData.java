package android.hardware.radio.data;

/* loaded from: classes2.dex */
public interface IRadioData extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$radio$data$IRadioData".replace('$', '.');
    public static final java.lang.String HASH = "cd8913a3f9d39f1cc0a5fcf9e90257be94ec38df";
    public static final int VERSION = 3;

    void allocatePduSessionId(int i) throws android.os.RemoteException;

    void cancelHandover(int i, int i2) throws android.os.RemoteException;

    void deactivateDataCall(int i, int i2, int i3) throws android.os.RemoteException;

    void getDataCallList(int i) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void getSlicingConfig(int i) throws android.os.RemoteException;

    void releasePduSessionId(int i, int i2) throws android.os.RemoteException;

    void responseAcknowledgement() throws android.os.RemoteException;

    void setDataAllowed(int i, boolean z) throws android.os.RemoteException;

    void setDataProfile(int i, android.hardware.radio.data.DataProfileInfo[] dataProfileInfoArr) throws android.os.RemoteException;

    void setDataThrottling(int i, byte b, long j) throws android.os.RemoteException;

    void setInitialAttachApn(int i, android.hardware.radio.data.DataProfileInfo dataProfileInfo) throws android.os.RemoteException;

    void setResponseFunctions(android.hardware.radio.data.IRadioDataResponse iRadioDataResponse, android.hardware.radio.data.IRadioDataIndication iRadioDataIndication) throws android.os.RemoteException;

    void setupDataCall(int i, int i2, android.hardware.radio.data.DataProfileInfo dataProfileInfo, boolean z, int i3, android.hardware.radio.data.LinkAddress[] linkAddressArr, java.lang.String[] strArr, int i4, android.hardware.radio.data.SliceInfo sliceInfo, boolean z2) throws android.os.RemoteException;

    void startHandover(int i, int i2) throws android.os.RemoteException;

    void startKeepalive(int i, android.hardware.radio.data.KeepaliveRequest keepaliveRequest) throws android.os.RemoteException;

    void stopKeepalive(int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.data.IRadioData {
        @Override // android.hardware.radio.data.IRadioData
        public void allocatePduSessionId(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void cancelHandover(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void deactivateDataCall(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void getDataCallList(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void getSlicingConfig(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void releasePduSessionId(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void responseAcknowledgement() throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void setDataAllowed(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void setDataProfile(int i, android.hardware.radio.data.DataProfileInfo[] dataProfileInfoArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void setDataThrottling(int i, byte b, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void setInitialAttachApn(int i, android.hardware.radio.data.DataProfileInfo dataProfileInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void setResponseFunctions(android.hardware.radio.data.IRadioDataResponse iRadioDataResponse, android.hardware.radio.data.IRadioDataIndication iRadioDataIndication) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void setupDataCall(int i, int i2, android.hardware.radio.data.DataProfileInfo dataProfileInfo, boolean z, int i3, android.hardware.radio.data.LinkAddress[] linkAddressArr, java.lang.String[] strArr, int i4, android.hardware.radio.data.SliceInfo sliceInfo, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void startHandover(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void startKeepalive(int i, android.hardware.radio.data.KeepaliveRequest keepaliveRequest) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void stopKeepalive(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.data.IRadioData
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.data.IRadioData {
        static final int TRANSACTION_allocatePduSessionId = 1;
        static final int TRANSACTION_cancelHandover = 2;
        static final int TRANSACTION_deactivateDataCall = 3;
        static final int TRANSACTION_getDataCallList = 4;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getSlicingConfig = 5;
        static final int TRANSACTION_releasePduSessionId = 6;
        static final int TRANSACTION_responseAcknowledgement = 7;
        static final int TRANSACTION_setDataAllowed = 8;
        static final int TRANSACTION_setDataProfile = 9;
        static final int TRANSACTION_setDataThrottling = 10;
        static final int TRANSACTION_setInitialAttachApn = 11;
        static final int TRANSACTION_setResponseFunctions = 12;
        static final int TRANSACTION_setupDataCall = 13;
        static final int TRANSACTION_startHandover = 14;
        static final int TRANSACTION_startKeepalive = 15;
        static final int TRANSACTION_stopKeepalive = 16;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.data.IRadioData asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.data.IRadioData)) {
                return (android.hardware.radio.data.IRadioData) queryLocalInterface;
            }
            return new android.hardware.radio.data.IRadioData.Stub.Proxy(iBinder);
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
                    allocatePduSessionId(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelHandover(readInt2, readInt3);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deactivateDataCall(readInt4, readInt5, readInt6);
                    return true;
                case 4:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getDataCallList(readInt7);
                    return true;
                case 5:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSlicingConfig(readInt8);
                    return true;
                case 6:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releasePduSessionId(readInt9, readInt10);
                    return true;
                case 7:
                    responseAcknowledgement();
                    return true;
                case 8:
                    int readInt11 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDataAllowed(readInt11, readBoolean);
                    return true;
                case 9:
                    int readInt12 = parcel.readInt();
                    android.hardware.radio.data.DataProfileInfo[] dataProfileInfoArr = (android.hardware.radio.data.DataProfileInfo[]) parcel.createTypedArray(android.hardware.radio.data.DataProfileInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDataProfile(readInt12, dataProfileInfoArr);
                    return true;
                case 10:
                    int readInt13 = parcel.readInt();
                    byte readByte = parcel.readByte();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setDataThrottling(readInt13, readByte, readLong);
                    return true;
                case 11:
                    int readInt14 = parcel.readInt();
                    android.hardware.radio.data.DataProfileInfo dataProfileInfo = (android.hardware.radio.data.DataProfileInfo) parcel.readTypedObject(android.hardware.radio.data.DataProfileInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setInitialAttachApn(readInt14, dataProfileInfo);
                    return true;
                case 12:
                    android.hardware.radio.data.IRadioDataResponse asInterface = android.hardware.radio.data.IRadioDataResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.hardware.radio.data.IRadioDataIndication asInterface2 = android.hardware.radio.data.IRadioDataIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(asInterface, asInterface2);
                    return true;
                case 13:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    android.hardware.radio.data.DataProfileInfo dataProfileInfo2 = (android.hardware.radio.data.DataProfileInfo) parcel.readTypedObject(android.hardware.radio.data.DataProfileInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt17 = parcel.readInt();
                    android.hardware.radio.data.LinkAddress[] linkAddressArr = (android.hardware.radio.data.LinkAddress[]) parcel.createTypedArray(android.hardware.radio.data.LinkAddress.CREATOR);
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    int readInt18 = parcel.readInt();
                    android.hardware.radio.data.SliceInfo sliceInfo = (android.hardware.radio.data.SliceInfo) parcel.readTypedObject(android.hardware.radio.data.SliceInfo.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setupDataCall(readInt15, readInt16, dataProfileInfo2, readBoolean2, readInt17, linkAddressArr, createStringArray, readInt18, sliceInfo, readBoolean3);
                    return true;
                case 14:
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startHandover(readInt19, readInt20);
                    return true;
                case 15:
                    int readInt21 = parcel.readInt();
                    android.hardware.radio.data.KeepaliveRequest keepaliveRequest = (android.hardware.radio.data.KeepaliveRequest) parcel.readTypedObject(android.hardware.radio.data.KeepaliveRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    startKeepalive(readInt21, keepaliveRequest);
                    return true;
                case 16:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopKeepalive(readInt22, readInt23);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.radio.data.IRadioData {
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

            @Override // android.hardware.radio.data.IRadioData
            public void allocatePduSessionId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method allocatePduSessionId is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void cancelHandover(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method cancelHandover is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void deactivateDataCall(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method deactivateDataCall is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void getDataCallList(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getDataCallList is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void getSlicingConfig(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getSlicingConfig is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void releasePduSessionId(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method releasePduSessionId is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void responseAcknowledgement() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method responseAcknowledgement is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void setDataAllowed(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setDataAllowed is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void setDataProfile(int i, android.hardware.radio.data.DataProfileInfo[] dataProfileInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(dataProfileInfoArr, 0);
                    if (!this.mRemote.transact(9, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setDataProfile is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void setDataThrottling(int i, byte b, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByte(b);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(10, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setDataThrottling is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void setInitialAttachApn(int i, android.hardware.radio.data.DataProfileInfo dataProfileInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(dataProfileInfo, 0);
                    if (!this.mRemote.transact(11, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setInitialAttachApn is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void setResponseFunctions(android.hardware.radio.data.IRadioDataResponse iRadioDataResponse, android.hardware.radio.data.IRadioDataIndication iRadioDataIndication) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iRadioDataResponse);
                    obtain.writeStrongInterface(iRadioDataIndication);
                    if (!this.mRemote.transact(12, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void setupDataCall(int i, int i2, android.hardware.radio.data.DataProfileInfo dataProfileInfo, boolean z, int i3, android.hardware.radio.data.LinkAddress[] linkAddressArr, java.lang.String[] strArr, int i4, android.hardware.radio.data.SliceInfo sliceInfo, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(dataProfileInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i3);
                    obtain.writeTypedArray(linkAddressArr, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i4);
                    obtain.writeTypedObject(sliceInfo, 0);
                    obtain.writeBoolean(z2);
                    if (!this.mRemote.transact(13, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setupDataCall is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void startHandover(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(14, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method startHandover is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void startKeepalive(int i, android.hardware.radio.data.KeepaliveRequest keepaliveRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(keepaliveRequest, 0);
                    if (!this.mRemote.transact(15, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method startKeepalive is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void stopKeepalive(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(16, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method stopKeepalive is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
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

            @Override // android.hardware.radio.data.IRadioData
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
