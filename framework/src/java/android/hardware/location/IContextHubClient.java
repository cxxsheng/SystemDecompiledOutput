package android.hardware.location;

/* loaded from: classes2.dex */
public interface IContextHubClient extends android.os.IInterface {
    void callbackFinished() throws android.os.RemoteException;

    void close() throws android.os.RemoteException;

    int getId() throws android.os.RemoteException;

    void reliableMessageCallbackFinished(int i, byte b) throws android.os.RemoteException;

    int sendMessageToNanoApp(android.hardware.location.NanoAppMessage nanoAppMessage) throws android.os.RemoteException;

    int sendReliableMessageToNanoApp(android.hardware.location.NanoAppMessage nanoAppMessage, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback) throws android.os.RemoteException;

    public static class Default implements android.hardware.location.IContextHubClient {
        @Override // android.hardware.location.IContextHubClient
        public int sendMessageToNanoApp(android.hardware.location.NanoAppMessage nanoAppMessage) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.location.IContextHubClient
        public void close() throws android.os.RemoteException {
        }

        @Override // android.hardware.location.IContextHubClient
        public int getId() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.location.IContextHubClient
        public void callbackFinished() throws android.os.RemoteException {
        }

        @Override // android.hardware.location.IContextHubClient
        public void reliableMessageCallbackFinished(int i, byte b) throws android.os.RemoteException {
        }

        @Override // android.hardware.location.IContextHubClient
        public int sendReliableMessageToNanoApp(android.hardware.location.NanoAppMessage nanoAppMessage, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.location.IContextHubClient {
        public static final java.lang.String DESCRIPTOR = "android.hardware.location.IContextHubClient";
        static final int TRANSACTION_callbackFinished = 4;
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_getId = 3;
        static final int TRANSACTION_reliableMessageCallbackFinished = 5;
        static final int TRANSACTION_sendMessageToNanoApp = 1;
        static final int TRANSACTION_sendReliableMessageToNanoApp = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.location.IContextHubClient asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.location.IContextHubClient)) {
                return (android.hardware.location.IContextHubClient) queryLocalInterface;
            }
            return new android.hardware.location.IContextHubClient.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "sendMessageToNanoApp";
                case 2:
                    return "close";
                case 3:
                    return "getId";
                case 4:
                    return "callbackFinished";
                case 5:
                    return "reliableMessageCallbackFinished";
                case 6:
                    return "sendReliableMessageToNanoApp";
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
                    android.hardware.location.NanoAppMessage nanoAppMessage = (android.hardware.location.NanoAppMessage) parcel.readTypedObject(android.hardware.location.NanoAppMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    int sendMessageToNanoApp = sendMessageToNanoApp(nanoAppMessage);
                    parcel2.writeNoException();
                    parcel2.writeInt(sendMessageToNanoApp);
                    return true;
                case 2:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int id = getId();
                    parcel2.writeNoException();
                    parcel2.writeInt(id);
                    return true;
                case 4:
                    callbackFinished();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt = parcel.readInt();
                    byte readByte = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    reliableMessageCallbackFinished(readInt, readByte);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.hardware.location.NanoAppMessage nanoAppMessage2 = (android.hardware.location.NanoAppMessage) parcel.readTypedObject(android.hardware.location.NanoAppMessage.CREATOR);
                    android.hardware.location.IContextHubTransactionCallback asInterface = android.hardware.location.IContextHubTransactionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int sendReliableMessageToNanoApp = sendReliableMessageToNanoApp(nanoAppMessage2, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(sendReliableMessageToNanoApp);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.location.IContextHubClient {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.location.IContextHubClient.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.location.IContextHubClient
            public int sendMessageToNanoApp(android.hardware.location.NanoAppMessage nanoAppMessage) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(nanoAppMessage, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClient
            public void close() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubClient.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClient
            public int getId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubClient.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClient
            public void callbackFinished() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubClient.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClient
            public void reliableMessageCallbackFinished(int i, byte b) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByte(b);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClient
            public int sendReliableMessageToNanoApp(android.hardware.location.NanoAppMessage nanoAppMessage, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(nanoAppMessage, 0);
                    obtain.writeStrongInterface(iContextHubTransactionCallback);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
