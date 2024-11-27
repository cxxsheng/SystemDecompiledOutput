package android.hardware.radio.config;

/* loaded from: classes2.dex */
public interface IRadioConfig extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$radio$config$IRadioConfig".replace('$', '.');
    public static final java.lang.String HASH = "1e3dcfffc1e90fc886cf5a22ecaa94601b115710";
    public static final int VERSION = 3;

    void getHalDeviceCapabilities(int i) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void getNumOfLiveModems(int i) throws android.os.RemoteException;

    void getPhoneCapability(int i) throws android.os.RemoteException;

    void getSimSlotsStatus(int i) throws android.os.RemoteException;

    void getSimultaneousCallingSupport(int i) throws android.os.RemoteException;

    void setNumOfLiveModems(int i, byte b) throws android.os.RemoteException;

    void setPreferredDataModem(int i, byte b) throws android.os.RemoteException;

    void setResponseFunctions(android.hardware.radio.config.IRadioConfigResponse iRadioConfigResponse, android.hardware.radio.config.IRadioConfigIndication iRadioConfigIndication) throws android.os.RemoteException;

    void setSimSlotsMapping(int i, android.hardware.radio.config.SlotPortMapping[] slotPortMappingArr) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.config.IRadioConfig {
        @Override // android.hardware.radio.config.IRadioConfig
        public void getHalDeviceCapabilities(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public void getNumOfLiveModems(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public void getPhoneCapability(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public void getSimSlotsStatus(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public void setNumOfLiveModems(int i, byte b) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public void setPreferredDataModem(int i, byte b) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public void setResponseFunctions(android.hardware.radio.config.IRadioConfigResponse iRadioConfigResponse, android.hardware.radio.config.IRadioConfigIndication iRadioConfigIndication) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public void setSimSlotsMapping(int i, android.hardware.radio.config.SlotPortMapping[] slotPortMappingArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public void getSimultaneousCallingSupport(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.config.IRadioConfig {
        static final int TRANSACTION_getHalDeviceCapabilities = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getNumOfLiveModems = 2;
        static final int TRANSACTION_getPhoneCapability = 3;
        static final int TRANSACTION_getSimSlotsStatus = 4;
        static final int TRANSACTION_getSimultaneousCallingSupport = 9;
        static final int TRANSACTION_setNumOfLiveModems = 5;
        static final int TRANSACTION_setPreferredDataModem = 6;
        static final int TRANSACTION_setResponseFunctions = 7;
        static final int TRANSACTION_setSimSlotsMapping = 8;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.config.IRadioConfig asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.config.IRadioConfig)) {
                return (android.hardware.radio.config.IRadioConfig) queryLocalInterface;
            }
            return new android.hardware.radio.config.IRadioConfig.Stub.Proxy(iBinder);
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
                    getHalDeviceCapabilities(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getNumOfLiveModems(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getPhoneCapability(readInt3);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSimSlotsStatus(readInt4);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    byte readByte = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    setNumOfLiveModems(readInt5, readByte);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    byte readByte2 = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    setPreferredDataModem(readInt6, readByte2);
                    return true;
                case 7:
                    android.hardware.radio.config.IRadioConfigResponse asInterface = android.hardware.radio.config.IRadioConfigResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.hardware.radio.config.IRadioConfigIndication asInterface2 = android.hardware.radio.config.IRadioConfigIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(asInterface, asInterface2);
                    return true;
                case 8:
                    int readInt7 = parcel.readInt();
                    android.hardware.radio.config.SlotPortMapping[] slotPortMappingArr = (android.hardware.radio.config.SlotPortMapping[]) parcel.createTypedArray(android.hardware.radio.config.SlotPortMapping.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSimSlotsMapping(readInt7, slotPortMappingArr);
                    return true;
                case 9:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSimultaneousCallingSupport(readInt8);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.radio.config.IRadioConfig {
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

            @Override // android.hardware.radio.config.IRadioConfig
            public void getHalDeviceCapabilities(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getHalDeviceCapabilities is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfig
            public void getNumOfLiveModems(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getNumOfLiveModems is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfig
            public void getPhoneCapability(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getPhoneCapability is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfig
            public void getSimSlotsStatus(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getSimSlotsStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfig
            public void setNumOfLiveModems(int i, byte b) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByte(b);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setNumOfLiveModems is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfig
            public void setPreferredDataModem(int i, byte b) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByte(b);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setPreferredDataModem is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfig
            public void setResponseFunctions(android.hardware.radio.config.IRadioConfigResponse iRadioConfigResponse, android.hardware.radio.config.IRadioConfigIndication iRadioConfigIndication) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iRadioConfigResponse);
                    obtain.writeStrongInterface(iRadioConfigIndication);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfig
            public void setSimSlotsMapping(int i, android.hardware.radio.config.SlotPortMapping[] slotPortMappingArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(slotPortMappingArr, 0);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setSimSlotsMapping is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfig
            public void getSimultaneousCallingSupport(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(9, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getSimultaneousCallingSupport is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfig
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

            @Override // android.hardware.radio.config.IRadioConfig
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
