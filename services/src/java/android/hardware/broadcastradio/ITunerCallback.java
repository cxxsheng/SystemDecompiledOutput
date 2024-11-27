package android.hardware.broadcastradio;

/* loaded from: classes.dex */
public interface ITunerCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$broadcastradio$ITunerCallback".replace('$', '.');
    public static final java.lang.String HASH = "bff68a8bc8b7cc191ab62bee10f7df8e79494467";
    public static final int VERSION = 2;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void onAntennaStateChange(boolean z) throws android.os.RemoteException;

    void onConfigFlagUpdated(int i, boolean z) throws android.os.RemoteException;

    void onCurrentProgramInfoChanged(android.hardware.broadcastradio.ProgramInfo programInfo) throws android.os.RemoteException;

    void onParametersUpdated(android.hardware.broadcastradio.VendorKeyValue[] vendorKeyValueArr) throws android.os.RemoteException;

    void onProgramListUpdated(android.hardware.broadcastradio.ProgramListChunk programListChunk) throws android.os.RemoteException;

    void onTuneFailed(int i, android.hardware.broadcastradio.ProgramSelector programSelector) throws android.os.RemoteException;

    public static class Default implements android.hardware.broadcastradio.ITunerCallback {
        @Override // android.hardware.broadcastradio.ITunerCallback
        public void onTuneFailed(int i, android.hardware.broadcastradio.ProgramSelector programSelector) throws android.os.RemoteException {
        }

        @Override // android.hardware.broadcastradio.ITunerCallback
        public void onCurrentProgramInfoChanged(android.hardware.broadcastradio.ProgramInfo programInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.broadcastradio.ITunerCallback
        public void onProgramListUpdated(android.hardware.broadcastradio.ProgramListChunk programListChunk) throws android.os.RemoteException {
        }

        @Override // android.hardware.broadcastradio.ITunerCallback
        public void onAntennaStateChange(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.broadcastradio.ITunerCallback
        public void onConfigFlagUpdated(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.broadcastradio.ITunerCallback
        public void onParametersUpdated(android.hardware.broadcastradio.VendorKeyValue[] vendorKeyValueArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.broadcastradio.ITunerCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.broadcastradio.ITunerCallback
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.broadcastradio.ITunerCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onAntennaStateChange = 4;
        static final int TRANSACTION_onConfigFlagUpdated = 5;
        static final int TRANSACTION_onCurrentProgramInfoChanged = 2;
        static final int TRANSACTION_onParametersUpdated = 6;
        static final int TRANSACTION_onProgramListUpdated = 3;
        static final int TRANSACTION_onTuneFailed = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.hardware.broadcastradio.ITunerCallback.DESCRIPTOR);
        }

        public static android.hardware.broadcastradio.ITunerCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.broadcastradio.ITunerCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.broadcastradio.ITunerCallback)) {
                return (android.hardware.broadcastradio.ITunerCallback) queryLocalInterface;
            }
            return new android.hardware.broadcastradio.ITunerCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.hardware.broadcastradio.ITunerCallback.DESCRIPTOR;
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
            if (i == TRANSACTION_getInterfaceHash) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.hardware.broadcastradio.ProgramSelector programSelector = (android.hardware.broadcastradio.ProgramSelector) parcel.readTypedObject(android.hardware.broadcastradio.ProgramSelector.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTuneFailed(readInt, programSelector);
                    return true;
                case 2:
                    android.hardware.broadcastradio.ProgramInfo programInfo = (android.hardware.broadcastradio.ProgramInfo) parcel.readTypedObject(android.hardware.broadcastradio.ProgramInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCurrentProgramInfoChanged(programInfo);
                    return true;
                case 3:
                    android.hardware.broadcastradio.ProgramListChunk programListChunk = (android.hardware.broadcastradio.ProgramListChunk) parcel.readTypedObject(android.hardware.broadcastradio.ProgramListChunk.CREATOR);
                    parcel.enforceNoDataAvail();
                    onProgramListUpdated(programListChunk);
                    return true;
                case 4:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAntennaStateChange(readBoolean);
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onConfigFlagUpdated(readInt2, readBoolean2);
                    return true;
                case 6:
                    android.hardware.broadcastradio.VendorKeyValue[] vendorKeyValueArr = (android.hardware.broadcastradio.VendorKeyValue[]) parcel.createTypedArray(android.hardware.broadcastradio.VendorKeyValue.CREATOR);
                    parcel.enforceNoDataAvail();
                    onParametersUpdated(vendorKeyValueArr);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.broadcastradio.ITunerCallback {
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
                return android.hardware.broadcastradio.ITunerCallback.DESCRIPTOR;
            }

            @Override // android.hardware.broadcastradio.ITunerCallback
            public void onTuneFailed(int i, android.hardware.broadcastradio.ProgramSelector programSelector) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.ITunerCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(programSelector, 0);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onTuneFailed is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.ITunerCallback
            public void onCurrentProgramInfoChanged(android.hardware.broadcastradio.ProgramInfo programInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.ITunerCallback.DESCRIPTOR);
                    obtain.writeTypedObject(programInfo, 0);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onCurrentProgramInfoChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.ITunerCallback
            public void onProgramListUpdated(android.hardware.broadcastradio.ProgramListChunk programListChunk) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.ITunerCallback.DESCRIPTOR);
                    obtain.writeTypedObject(programListChunk, 0);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onProgramListUpdated is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.ITunerCallback
            public void onAntennaStateChange(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.ITunerCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onAntennaStateChange is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.ITunerCallback
            public void onConfigFlagUpdated(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.ITunerCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onConfigFlagUpdated is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.ITunerCallback
            public void onParametersUpdated(android.hardware.broadcastradio.VendorKeyValue[] vendorKeyValueArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.ITunerCallback.DESCRIPTOR);
                    obtain.writeTypedArray(vendorKeyValueArr, 0);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onParametersUpdated is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.ITunerCallback
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.hardware.broadcastradio.ITunerCallback.DESCRIPTOR);
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

            @Override // android.hardware.broadcastradio.ITunerCallback
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.hardware.broadcastradio.ITunerCallback.DESCRIPTOR);
                            this.mRemote.transact(android.hardware.broadcastradio.ITunerCallback.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
                return this.mCachedHash;
            }
        }
    }
}
