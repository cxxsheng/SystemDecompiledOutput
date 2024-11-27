package android.hardware.contexthub;

/* loaded from: classes.dex */
public interface IContextHubCallback extends android.os.IInterface {
    public static final int CONTEXTHUB_NAN_TRANSACTION_TIMEOUT_MS = 10000;
    public static final java.lang.String DESCRIPTOR = "android$hardware$contexthub$IContextHubCallback".replace('$', '.');
    public static final java.lang.String HASH = "03f1982c8e20e58494a4ff8c9736b1c257dfeb6c";
    public static final int VERSION = 3;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    java.lang.String getName() throws android.os.RemoteException;

    byte[] getUuid() throws android.os.RemoteException;

    void handleContextHubAsyncEvent(int i) throws android.os.RemoteException;

    void handleContextHubMessage(android.hardware.contexthub.ContextHubMessage contextHubMessage, java.lang.String[] strArr) throws android.os.RemoteException;

    void handleMessageDeliveryStatus(char c, android.hardware.contexthub.MessageDeliveryStatus messageDeliveryStatus) throws android.os.RemoteException;

    void handleNanSessionRequest(android.hardware.contexthub.NanSessionRequest nanSessionRequest) throws android.os.RemoteException;

    void handleNanoappInfo(android.hardware.contexthub.NanoappInfo[] nanoappInfoArr) throws android.os.RemoteException;

    void handleTransactionResult(int i, boolean z) throws android.os.RemoteException;

    public static class Default implements android.hardware.contexthub.IContextHubCallback {
        @Override // android.hardware.contexthub.IContextHubCallback
        public void handleNanoappInfo(android.hardware.contexthub.NanoappInfo[] nanoappInfoArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHubCallback
        public void handleContextHubMessage(android.hardware.contexthub.ContextHubMessage contextHubMessage, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHubCallback
        public void handleContextHubAsyncEvent(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHubCallback
        public void handleTransactionResult(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHubCallback
        public void handleNanSessionRequest(android.hardware.contexthub.NanSessionRequest nanSessionRequest) throws android.os.RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHubCallback
        public void handleMessageDeliveryStatus(char c, android.hardware.contexthub.MessageDeliveryStatus messageDeliveryStatus) throws android.os.RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHubCallback
        public byte[] getUuid() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.contexthub.IContextHubCallback
        public java.lang.String getName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.contexthub.IContextHubCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.contexthub.IContextHubCallback
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.contexthub.IContextHubCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getName = 8;
        static final int TRANSACTION_getUuid = 7;
        static final int TRANSACTION_handleContextHubAsyncEvent = 3;
        static final int TRANSACTION_handleContextHubMessage = 2;
        static final int TRANSACTION_handleMessageDeliveryStatus = 6;
        static final int TRANSACTION_handleNanSessionRequest = 5;
        static final int TRANSACTION_handleNanoappInfo = 1;
        static final int TRANSACTION_handleTransactionResult = 4;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.contexthub.IContextHubCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.contexthub.IContextHubCallback)) {
                return (android.hardware.contexthub.IContextHubCallback) queryLocalInterface;
            }
            return new android.hardware.contexthub.IContextHubCallback.Stub.Proxy(iBinder);
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
                    android.hardware.contexthub.NanoappInfo[] nanoappInfoArr = (android.hardware.contexthub.NanoappInfo[]) parcel.createTypedArray(android.hardware.contexthub.NanoappInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleNanoappInfo(nanoappInfoArr);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.hardware.contexthub.ContextHubMessage contextHubMessage = (android.hardware.contexthub.ContextHubMessage) parcel.readTypedObject(android.hardware.contexthub.ContextHubMessage.CREATOR);
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    handleContextHubMessage(contextHubMessage, createStringArray);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    handleContextHubAsyncEvent(readInt);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    handleTransactionResult(readInt2, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.hardware.contexthub.NanSessionRequest nanSessionRequest = (android.hardware.contexthub.NanSessionRequest) parcel.readTypedObject(android.hardware.contexthub.NanSessionRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleNanSessionRequest(nanSessionRequest);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    char readInt3 = (char) parcel.readInt();
                    android.hardware.contexthub.MessageDeliveryStatus messageDeliveryStatus = (android.hardware.contexthub.MessageDeliveryStatus) parcel.readTypedObject(android.hardware.contexthub.MessageDeliveryStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleMessageDeliveryStatus(readInt3, messageDeliveryStatus);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    byte[] uuid = getUuid();
                    parcel2.writeNoException();
                    parcel2.writeFixedArray(uuid, 1, 16);
                    return true;
                case 8:
                    java.lang.String name = getName();
                    parcel2.writeNoException();
                    parcel2.writeString(name);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.contexthub.IContextHubCallback {
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

            @Override // android.hardware.contexthub.IContextHubCallback
            public void handleNanoappInfo(android.hardware.contexthub.NanoappInfo[] nanoappInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedArray(nanoappInfoArr, 0);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method handleNanoappInfo is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
            public void handleContextHubMessage(android.hardware.contexthub.ContextHubMessage contextHubMessage, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(contextHubMessage, 0);
                    obtain.writeStringArray(strArr);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method handleContextHubMessage is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
            public void handleContextHubAsyncEvent(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method handleContextHubAsyncEvent is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
            public void handleTransactionResult(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method handleTransactionResult is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
            public void handleNanSessionRequest(android.hardware.contexthub.NanSessionRequest nanSessionRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(nanSessionRequest, 0);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method handleNanSessionRequest is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
            public void handleMessageDeliveryStatus(char c, android.hardware.contexthub.MessageDeliveryStatus messageDeliveryStatus) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(c);
                    obtain.writeTypedObject(messageDeliveryStatus, 0);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method handleMessageDeliveryStatus is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
            public byte[] getUuid() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getUuid is unimplemented.");
                    }
                    obtain2.readException();
                    return (byte[]) obtain2.createFixedArray(byte[].class, 16);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
            public java.lang.String getName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getName is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubCallback
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

            @Override // android.hardware.contexthub.IContextHubCallback
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
