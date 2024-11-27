package android.hardware.radio.data;

/* loaded from: classes2.dex */
public interface IRadioDataIndication extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$radio$data$IRadioDataIndication".replace('$', '.');
    public static final java.lang.String HASH = "cd8913a3f9d39f1cc0a5fcf9e90257be94ec38df";
    public static final int VERSION = 3;

    void dataCallListChanged(int i, android.hardware.radio.data.SetupDataCallResult[] setupDataCallResultArr) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void keepaliveStatus(int i, android.hardware.radio.data.KeepaliveStatus keepaliveStatus) throws android.os.RemoteException;

    void pcoData(int i, android.hardware.radio.data.PcoDataInfo pcoDataInfo) throws android.os.RemoteException;

    void slicingConfigChanged(int i, android.hardware.radio.data.SlicingConfig slicingConfig) throws android.os.RemoteException;

    void unthrottleApn(int i, android.hardware.radio.data.DataProfileInfo dataProfileInfo) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.data.IRadioDataIndication {
        @Override // android.hardware.radio.data.IRadioDataIndication
        public void dataCallListChanged(int i, android.hardware.radio.data.SetupDataCallResult[] setupDataCallResultArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataIndication
        public void keepaliveStatus(int i, android.hardware.radio.data.KeepaliveStatus keepaliveStatus) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataIndication
        public void pcoData(int i, android.hardware.radio.data.PcoDataInfo pcoDataInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataIndication
        public void unthrottleApn(int i, android.hardware.radio.data.DataProfileInfo dataProfileInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataIndication
        public void slicingConfigChanged(int i, android.hardware.radio.data.SlicingConfig slicingConfig) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.data.IRadioDataIndication
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.data.IRadioDataIndication {
        static final int TRANSACTION_dataCallListChanged = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_keepaliveStatus = 2;
        static final int TRANSACTION_pcoData = 3;
        static final int TRANSACTION_slicingConfigChanged = 5;
        static final int TRANSACTION_unthrottleApn = 4;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.data.IRadioDataIndication asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.data.IRadioDataIndication)) {
                return (android.hardware.radio.data.IRadioDataIndication) queryLocalInterface;
            }
            return new android.hardware.radio.data.IRadioDataIndication.Stub.Proxy(iBinder);
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
                    android.hardware.radio.data.SetupDataCallResult[] setupDataCallResultArr = (android.hardware.radio.data.SetupDataCallResult[]) parcel.createTypedArray(android.hardware.radio.data.SetupDataCallResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    dataCallListChanged(readInt, setupDataCallResultArr);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    android.hardware.radio.data.KeepaliveStatus keepaliveStatus = (android.hardware.radio.data.KeepaliveStatus) parcel.readTypedObject(android.hardware.radio.data.KeepaliveStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    keepaliveStatus(readInt2, keepaliveStatus);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    android.hardware.radio.data.PcoDataInfo pcoDataInfo = (android.hardware.radio.data.PcoDataInfo) parcel.readTypedObject(android.hardware.radio.data.PcoDataInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    pcoData(readInt3, pcoDataInfo);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    android.hardware.radio.data.DataProfileInfo dataProfileInfo = (android.hardware.radio.data.DataProfileInfo) parcel.readTypedObject(android.hardware.radio.data.DataProfileInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    unthrottleApn(readInt4, dataProfileInfo);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    android.hardware.radio.data.SlicingConfig slicingConfig = (android.hardware.radio.data.SlicingConfig) parcel.readTypedObject(android.hardware.radio.data.SlicingConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    slicingConfigChanged(readInt5, slicingConfig);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.radio.data.IRadioDataIndication {
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

            @Override // android.hardware.radio.data.IRadioDataIndication
            public void dataCallListChanged(int i, android.hardware.radio.data.SetupDataCallResult[] setupDataCallResultArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(setupDataCallResultArr, 0);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method dataCallListChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataIndication
            public void keepaliveStatus(int i, android.hardware.radio.data.KeepaliveStatus keepaliveStatus) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(keepaliveStatus, 0);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method keepaliveStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataIndication
            public void pcoData(int i, android.hardware.radio.data.PcoDataInfo pcoDataInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(pcoDataInfo, 0);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method pcoData is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataIndication
            public void unthrottleApn(int i, android.hardware.radio.data.DataProfileInfo dataProfileInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(dataProfileInfo, 0);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method unthrottleApn is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataIndication
            public void slicingConfigChanged(int i, android.hardware.radio.data.SlicingConfig slicingConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(slicingConfig, 0);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method slicingConfigChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataIndication
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

            @Override // android.hardware.radio.data.IRadioDataIndication
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
