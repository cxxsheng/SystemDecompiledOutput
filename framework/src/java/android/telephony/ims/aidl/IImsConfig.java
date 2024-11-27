package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public interface IImsConfig extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.ims.aidl.IImsConfig";

    void addImsConfigCallback(android.telephony.ims.aidl.IImsConfigCallback iImsConfigCallback) throws android.os.RemoteException;

    void addRcsConfigCallback(android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback) throws android.os.RemoteException;

    int getConfigInt(int i) throws android.os.RemoteException;

    java.lang.String getConfigString(int i) throws android.os.RemoteException;

    void notifyIntImsConfigChanged(int i, int i2) throws android.os.RemoteException;

    void notifyRcsAutoConfigurationReceived(byte[] bArr, boolean z) throws android.os.RemoteException;

    void notifyRcsAutoConfigurationRemoved() throws android.os.RemoteException;

    void notifyStringImsConfigChanged(int i, java.lang.String str) throws android.os.RemoteException;

    void removeImsConfigCallback(android.telephony.ims.aidl.IImsConfigCallback iImsConfigCallback) throws android.os.RemoteException;

    void removeRcsConfigCallback(android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback) throws android.os.RemoteException;

    int setConfigInt(int i, int i2) throws android.os.RemoteException;

    int setConfigString(int i, java.lang.String str) throws android.os.RemoteException;

    void setRcsClientConfiguration(android.telephony.ims.RcsClientConfiguration rcsClientConfiguration) throws android.os.RemoteException;

    void triggerRcsReconfiguration() throws android.os.RemoteException;

    void updateImsCarrierConfigs(android.os.PersistableBundle persistableBundle) throws android.os.RemoteException;

    public static class Default implements android.telephony.ims.aidl.IImsConfig {
        @Override // android.telephony.ims.aidl.IImsConfig
        public void addImsConfigCallback(android.telephony.ims.aidl.IImsConfigCallback iImsConfigCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void removeImsConfigCallback(android.telephony.ims.aidl.IImsConfigCallback iImsConfigCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public int getConfigInt(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public java.lang.String getConfigString(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public int setConfigInt(int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public int setConfigString(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void updateImsCarrierConfigs(android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyRcsAutoConfigurationReceived(byte[] bArr, boolean z) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyRcsAutoConfigurationRemoved() throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void addRcsConfigCallback(android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void removeRcsConfigCallback(android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void triggerRcsReconfiguration() throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void setRcsClientConfiguration(android.telephony.ims.RcsClientConfiguration rcsClientConfiguration) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyIntImsConfigChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyStringImsConfigChanged(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.ims.aidl.IImsConfig {
        static final int TRANSACTION_addImsConfigCallback = 1;
        static final int TRANSACTION_addRcsConfigCallback = 10;
        static final int TRANSACTION_getConfigInt = 3;
        static final int TRANSACTION_getConfigString = 4;
        static final int TRANSACTION_notifyIntImsConfigChanged = 14;
        static final int TRANSACTION_notifyRcsAutoConfigurationReceived = 8;
        static final int TRANSACTION_notifyRcsAutoConfigurationRemoved = 9;
        static final int TRANSACTION_notifyStringImsConfigChanged = 15;
        static final int TRANSACTION_removeImsConfigCallback = 2;
        static final int TRANSACTION_removeRcsConfigCallback = 11;
        static final int TRANSACTION_setConfigInt = 5;
        static final int TRANSACTION_setConfigString = 6;
        static final int TRANSACTION_setRcsClientConfiguration = 13;
        static final int TRANSACTION_triggerRcsReconfiguration = 12;
        static final int TRANSACTION_updateImsCarrierConfigs = 7;

        public Stub() {
            attachInterface(this, android.telephony.ims.aidl.IImsConfig.DESCRIPTOR);
        }

        public static android.telephony.ims.aidl.IImsConfig asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.ims.aidl.IImsConfig.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.ims.aidl.IImsConfig)) {
                return (android.telephony.ims.aidl.IImsConfig) queryLocalInterface;
            }
            return new android.telephony.ims.aidl.IImsConfig.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addImsConfigCallback";
                case 2:
                    return "removeImsConfigCallback";
                case 3:
                    return "getConfigInt";
                case 4:
                    return "getConfigString";
                case 5:
                    return "setConfigInt";
                case 6:
                    return "setConfigString";
                case 7:
                    return "updateImsCarrierConfigs";
                case 8:
                    return "notifyRcsAutoConfigurationReceived";
                case 9:
                    return "notifyRcsAutoConfigurationRemoved";
                case 10:
                    return "addRcsConfigCallback";
                case 11:
                    return "removeRcsConfigCallback";
                case 12:
                    return "triggerRcsReconfiguration";
                case 13:
                    return "setRcsClientConfiguration";
                case 14:
                    return "notifyIntImsConfigChanged";
                case 15:
                    return "notifyStringImsConfigChanged";
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
                parcel.enforceInterface(android.telephony.ims.aidl.IImsConfig.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.ims.aidl.IImsConfig.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.telephony.ims.aidl.IImsConfigCallback asInterface = android.telephony.ims.aidl.IImsConfigCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addImsConfigCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.telephony.ims.aidl.IImsConfigCallback asInterface2 = android.telephony.ims.aidl.IImsConfigCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeImsConfigCallback(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int configInt = getConfigInt(readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(configInt);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String configString = getConfigString(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeString(configString);
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int configInt2 = setConfigInt(readInt3, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeInt(configInt2);
                    return true;
                case 6:
                    int readInt5 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int configString2 = setConfigString(readInt5, readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(configString2);
                    return true;
                case 7:
                    android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateImsCarrierConfigs(persistableBundle);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    byte[] createByteArray = parcel.createByteArray();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyRcsAutoConfigurationReceived(createByteArray, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    notifyRcsAutoConfigurationRemoved();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.telephony.ims.aidl.IRcsConfigCallback asInterface3 = android.telephony.ims.aidl.IRcsConfigCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addRcsConfigCallback(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    android.telephony.ims.aidl.IRcsConfigCallback asInterface4 = android.telephony.ims.aidl.IRcsConfigCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeRcsConfigCallback(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    triggerRcsReconfiguration();
                    parcel2.writeNoException();
                    return true;
                case 13:
                    android.telephony.ims.RcsClientConfiguration rcsClientConfiguration = (android.telephony.ims.RcsClientConfiguration) parcel.readTypedObject(android.telephony.ims.RcsClientConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRcsClientConfiguration(rcsClientConfiguration);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyIntImsConfigChanged(readInt6, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt8 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyStringImsConfigChanged(readInt8, readString2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.ims.aidl.IImsConfig {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.ims.aidl.IImsConfig.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void addImsConfigCallback(android.telephony.ims.aidl.IImsConfigCallback iImsConfigCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsConfig.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsConfigCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void removeImsConfigCallback(android.telephony.ims.aidl.IImsConfigCallback iImsConfigCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsConfig.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsConfigCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public int getConfigInt(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsConfig.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public java.lang.String getConfigString(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsConfig.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public int setConfigInt(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsConfig.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public int setConfigString(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsConfig.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void updateImsCarrierConfigs(android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsConfig.DESCRIPTOR);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void notifyRcsAutoConfigurationReceived(byte[] bArr, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsConfig.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void notifyRcsAutoConfigurationRemoved() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsConfig.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void addRcsConfigCallback(android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsConfig.DESCRIPTOR);
                    obtain.writeStrongInterface(iRcsConfigCallback);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void removeRcsConfigCallback(android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsConfig.DESCRIPTOR);
                    obtain.writeStrongInterface(iRcsConfigCallback);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void triggerRcsReconfiguration() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsConfig.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void setRcsClientConfiguration(android.telephony.ims.RcsClientConfiguration rcsClientConfiguration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsConfig.DESCRIPTOR);
                    obtain.writeTypedObject(rcsClientConfiguration, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void notifyIntImsConfigChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsConfig.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void notifyStringImsConfigChanged(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsConfig.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 14;
        }
    }
}
