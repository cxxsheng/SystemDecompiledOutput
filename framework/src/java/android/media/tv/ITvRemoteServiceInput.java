package android.media.tv;

/* loaded from: classes2.dex */
public interface ITvRemoteServiceInput extends android.os.IInterface {
    void clearInputBridge(android.os.IBinder iBinder) throws android.os.RemoteException;

    void closeInputBridge(android.os.IBinder iBinder) throws android.os.RemoteException;

    void openGamepadBridge(android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException;

    void openInputBridge(android.os.IBinder iBinder, java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException;

    void sendGamepadAxisValue(android.os.IBinder iBinder, int i, float f) throws android.os.RemoteException;

    void sendGamepadKeyDown(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void sendGamepadKeyUp(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void sendKeyDown(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void sendKeyUp(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void sendPointerDown(android.os.IBinder iBinder, int i, int i2, int i3) throws android.os.RemoteException;

    void sendPointerSync(android.os.IBinder iBinder) throws android.os.RemoteException;

    void sendPointerUp(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void sendTimestamp(android.os.IBinder iBinder, long j) throws android.os.RemoteException;

    public static class Default implements android.media.tv.ITvRemoteServiceInput {
        @Override // android.media.tv.ITvRemoteServiceInput
        public void openInputBridge(android.os.IBinder iBinder, java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void closeInputBridge(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void clearInputBridge(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void sendTimestamp(android.os.IBinder iBinder, long j) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void sendKeyDown(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void sendKeyUp(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void sendPointerDown(android.os.IBinder iBinder, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void sendPointerUp(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void sendPointerSync(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void openGamepadBridge(android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void sendGamepadKeyDown(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void sendGamepadKeyUp(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvRemoteServiceInput
        public void sendGamepadAxisValue(android.os.IBinder iBinder, int i, float f) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.ITvRemoteServiceInput {
        public static final java.lang.String DESCRIPTOR = "android.media.tv.ITvRemoteServiceInput";
        static final int TRANSACTION_clearInputBridge = 3;
        static final int TRANSACTION_closeInputBridge = 2;
        static final int TRANSACTION_openGamepadBridge = 10;
        static final int TRANSACTION_openInputBridge = 1;
        static final int TRANSACTION_sendGamepadAxisValue = 13;
        static final int TRANSACTION_sendGamepadKeyDown = 11;
        static final int TRANSACTION_sendGamepadKeyUp = 12;
        static final int TRANSACTION_sendKeyDown = 5;
        static final int TRANSACTION_sendKeyUp = 6;
        static final int TRANSACTION_sendPointerDown = 7;
        static final int TRANSACTION_sendPointerSync = 9;
        static final int TRANSACTION_sendPointerUp = 8;
        static final int TRANSACTION_sendTimestamp = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.tv.ITvRemoteServiceInput asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.ITvRemoteServiceInput)) {
                return (android.media.tv.ITvRemoteServiceInput) queryLocalInterface;
            }
            return new android.media.tv.ITvRemoteServiceInput.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "openInputBridge";
                case 2:
                    return "closeInputBridge";
                case 3:
                    return "clearInputBridge";
                case 4:
                    return "sendTimestamp";
                case 5:
                    return "sendKeyDown";
                case 6:
                    return "sendKeyUp";
                case 7:
                    return "sendPointerDown";
                case 8:
                    return "sendPointerUp";
                case 9:
                    return "sendPointerSync";
                case 10:
                    return "openGamepadBridge";
                case 11:
                    return "sendGamepadKeyDown";
                case 12:
                    return "sendGamepadKeyUp";
                case 13:
                    return "sendGamepadAxisValue";
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
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    openInputBridge(readStrongBinder, readString, readInt, readInt2, readInt3);
                    return true;
                case 2:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    closeInputBridge(readStrongBinder2);
                    return true;
                case 3:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    clearInputBridge(readStrongBinder3);
                    return true;
                case 4:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    sendTimestamp(readStrongBinder4, readLong);
                    return true;
                case 5:
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendKeyDown(readStrongBinder5, readInt4);
                    return true;
                case 6:
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendKeyUp(readStrongBinder6, readInt5);
                    return true;
                case 7:
                    android.os.IBinder readStrongBinder7 = parcel.readStrongBinder();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendPointerDown(readStrongBinder7, readInt6, readInt7, readInt8);
                    return true;
                case 8:
                    android.os.IBinder readStrongBinder8 = parcel.readStrongBinder();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendPointerUp(readStrongBinder8, readInt9);
                    return true;
                case 9:
                    android.os.IBinder readStrongBinder9 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    sendPointerSync(readStrongBinder9);
                    return true;
                case 10:
                    android.os.IBinder readStrongBinder10 = parcel.readStrongBinder();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    openGamepadBridge(readStrongBinder10, readString2);
                    return true;
                case 11:
                    android.os.IBinder readStrongBinder11 = parcel.readStrongBinder();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendGamepadKeyDown(readStrongBinder11, readInt10);
                    return true;
                case 12:
                    android.os.IBinder readStrongBinder12 = parcel.readStrongBinder();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendGamepadKeyUp(readStrongBinder12, readInt11);
                    return true;
                case 13:
                    android.os.IBinder readStrongBinder13 = parcel.readStrongBinder();
                    int readInt12 = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    sendGamepadAxisValue(readStrongBinder13, readInt12, readFloat);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.ITvRemoteServiceInput {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.ITvRemoteServiceInput.Stub.DESCRIPTOR;
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void openInputBridge(android.os.IBinder iBinder, java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvRemoteServiceInput.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void closeInputBridge(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvRemoteServiceInput.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void clearInputBridge(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvRemoteServiceInput.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void sendTimestamp(android.os.IBinder iBinder, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvRemoteServiceInput.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void sendKeyDown(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvRemoteServiceInput.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void sendKeyUp(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvRemoteServiceInput.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void sendPointerDown(android.os.IBinder iBinder, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvRemoteServiceInput.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void sendPointerUp(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvRemoteServiceInput.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void sendPointerSync(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvRemoteServiceInput.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void openGamepadBridge(android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvRemoteServiceInput.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void sendGamepadKeyDown(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvRemoteServiceInput.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void sendGamepadKeyUp(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvRemoteServiceInput.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteServiceInput
            public void sendGamepadAxisValue(android.os.IBinder iBinder, int i, float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvRemoteServiceInput.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }
    }
}
