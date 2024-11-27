package android.hardware.contexthub;

/* loaded from: classes.dex */
public interface IContextHub extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$contexthub$IContextHub".replace('$', '.');
    public static final int EX_CONTEXT_HUB_UNSPECIFIED = -1;
    public static final java.lang.String HASH = "03f1982c8e20e58494a4ff8c9736b1c257dfeb6c";
    public static final int VERSION = 3;

    void disableNanoapp(int i, long j, int i2) throws android.os.RemoteException;

    void enableNanoapp(int i, long j, int i2) throws android.os.RemoteException;

    java.util.List<android.hardware.contexthub.ContextHubInfo> getContextHubs() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    long[] getPreloadedNanoappIds(int i) throws android.os.RemoteException;

    void loadNanoapp(int i, android.hardware.contexthub.NanoappBinary nanoappBinary, int i2) throws android.os.RemoteException;

    void onHostEndpointConnected(android.hardware.contexthub.HostEndpointInfo hostEndpointInfo) throws android.os.RemoteException;

    void onHostEndpointDisconnected(char c) throws android.os.RemoteException;

    void onNanSessionStateChanged(android.hardware.contexthub.NanSessionStateUpdate nanSessionStateUpdate) throws android.os.RemoteException;

    void onSettingChanged(byte b, boolean z) throws android.os.RemoteException;

    void queryNanoapps(int i) throws android.os.RemoteException;

    void registerCallback(int i, android.hardware.contexthub.IContextHubCallback iContextHubCallback) throws android.os.RemoteException;

    void sendMessageDeliveryStatusToHub(int i, android.hardware.contexthub.MessageDeliveryStatus messageDeliveryStatus) throws android.os.RemoteException;

    void sendMessageToHub(int i, android.hardware.contexthub.ContextHubMessage contextHubMessage) throws android.os.RemoteException;

    void setTestMode(boolean z) throws android.os.RemoteException;

    void unloadNanoapp(int i, long j, int i2) throws android.os.RemoteException;

    public static class Default implements android.hardware.contexthub.IContextHub {
        @Override // android.hardware.contexthub.IContextHub
        public java.util.List<android.hardware.contexthub.ContextHubInfo> getContextHubs() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.contexthub.IContextHub
        public void loadNanoapp(int i, android.hardware.contexthub.NanoappBinary nanoappBinary, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public void unloadNanoapp(int i, long j, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public void disableNanoapp(int i, long j, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public void enableNanoapp(int i, long j, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public void onSettingChanged(byte b, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public void queryNanoapps(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public void registerCallback(int i, android.hardware.contexthub.IContextHubCallback iContextHubCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public void sendMessageToHub(int i, android.hardware.contexthub.ContextHubMessage contextHubMessage) throws android.os.RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public void onHostEndpointConnected(android.hardware.contexthub.HostEndpointInfo hostEndpointInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public void onHostEndpointDisconnected(char c) throws android.os.RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public long[] getPreloadedNanoappIds(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.contexthub.IContextHub
        public void onNanSessionStateChanged(android.hardware.contexthub.NanSessionStateUpdate nanSessionStateUpdate) throws android.os.RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public void setTestMode(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public void sendMessageDeliveryStatusToHub(int i, android.hardware.contexthub.MessageDeliveryStatus messageDeliveryStatus) throws android.os.RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHub
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.contexthub.IContextHub
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.contexthub.IContextHub {
        static final int TRANSACTION_disableNanoapp = 4;
        static final int TRANSACTION_enableNanoapp = 5;
        static final int TRANSACTION_getContextHubs = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getPreloadedNanoappIds = 12;
        static final int TRANSACTION_loadNanoapp = 2;
        static final int TRANSACTION_onHostEndpointConnected = 10;
        static final int TRANSACTION_onHostEndpointDisconnected = 11;
        static final int TRANSACTION_onNanSessionStateChanged = 13;
        static final int TRANSACTION_onSettingChanged = 6;
        static final int TRANSACTION_queryNanoapps = 7;
        static final int TRANSACTION_registerCallback = 8;
        static final int TRANSACTION_sendMessageDeliveryStatusToHub = 15;
        static final int TRANSACTION_sendMessageToHub = 9;
        static final int TRANSACTION_setTestMode = 14;
        static final int TRANSACTION_unloadNanoapp = 3;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.contexthub.IContextHub asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.contexthub.IContextHub)) {
                return (android.hardware.contexthub.IContextHub) queryLocalInterface;
            }
            return new android.hardware.contexthub.IContextHub.Stub.Proxy(iBinder);
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
                    java.util.List<android.hardware.contexthub.ContextHubInfo> contextHubs = getContextHubs();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(contextHubs, 1);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    android.hardware.contexthub.NanoappBinary nanoappBinary = (android.hardware.contexthub.NanoappBinary) parcel.readTypedObject(android.hardware.contexthub.NanoappBinary.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    loadNanoapp(readInt, nanoappBinary, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    long readLong = parcel.readLong();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unloadNanoapp(readInt3, readLong, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableNanoapp(readInt5, readLong2, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt7 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableNanoapp(readInt7, readLong3, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    byte readByte = parcel.readByte();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onSettingChanged(readByte, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    queryNanoapps(readInt9);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt10 = parcel.readInt();
                    android.hardware.contexthub.IContextHubCallback asInterface = android.hardware.contexthub.IContextHubCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(readInt10, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt11 = parcel.readInt();
                    android.hardware.contexthub.ContextHubMessage contextHubMessage = (android.hardware.contexthub.ContextHubMessage) parcel.readTypedObject(android.hardware.contexthub.ContextHubMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendMessageToHub(readInt11, contextHubMessage);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.hardware.contexthub.HostEndpointInfo hostEndpointInfo = (android.hardware.contexthub.HostEndpointInfo) parcel.readTypedObject(android.hardware.contexthub.HostEndpointInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onHostEndpointConnected(hostEndpointInfo);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    char readInt12 = (char) parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onHostEndpointDisconnected(readInt12);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long[] preloadedNanoappIds = getPreloadedNanoappIds(readInt13);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(preloadedNanoappIds);
                    return true;
                case 13:
                    android.hardware.contexthub.NanSessionStateUpdate nanSessionStateUpdate = (android.hardware.contexthub.NanSessionStateUpdate) parcel.readTypedObject(android.hardware.contexthub.NanSessionStateUpdate.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNanSessionStateChanged(nanSessionStateUpdate);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTestMode(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt14 = parcel.readInt();
                    android.hardware.contexthub.MessageDeliveryStatus messageDeliveryStatus = (android.hardware.contexthub.MessageDeliveryStatus) parcel.readTypedObject(android.hardware.contexthub.MessageDeliveryStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendMessageDeliveryStatusToHub(readInt14, messageDeliveryStatus);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.contexthub.IContextHub {
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

            @Override // android.hardware.contexthub.IContextHub
            public java.util.List<android.hardware.contexthub.ContextHubInfo> getContextHubs() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getContextHubs is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.contexthub.ContextHubInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void loadNanoapp(int i, android.hardware.contexthub.NanoappBinary nanoappBinary, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(nanoappBinary, 0);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method loadNanoapp is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void unloadNanoapp(int i, long j, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method unloadNanoapp is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void disableNanoapp(int i, long j, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method disableNanoapp is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void enableNanoapp(int i, long j, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method enableNanoapp is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void onSettingChanged(byte b, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onSettingChanged is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void queryNanoapps(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method queryNanoapps is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void registerCallback(int i, android.hardware.contexthub.IContextHubCallback iContextHubCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iContextHubCallback);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method registerCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void sendMessageToHub(int i, android.hardware.contexthub.ContextHubMessage contextHubMessage) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(contextHubMessage, 0);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method sendMessageToHub is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void onHostEndpointConnected(android.hardware.contexthub.HostEndpointInfo hostEndpointInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(hostEndpointInfo, 0);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onHostEndpointConnected is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void onHostEndpointDisconnected(char c) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(c);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onHostEndpointDisconnected is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public long[] getPreloadedNanoappIds(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getPreloadedNanoappIds is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void onNanSessionStateChanged(android.hardware.contexthub.NanSessionStateUpdate nanSessionStateUpdate) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(nanSessionStateUpdate, 0);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onNanSessionStateChanged is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void setTestMode(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setTestMode is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
            public void sendMessageDeliveryStatusToHub(int i, android.hardware.contexthub.MessageDeliveryStatus messageDeliveryStatus) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(messageDeliveryStatus, 0);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method sendMessageDeliveryStatusToHub is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHub
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

            @Override // android.hardware.contexthub.IContextHub
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
