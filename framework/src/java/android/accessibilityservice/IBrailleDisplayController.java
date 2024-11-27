package android.accessibilityservice;

/* loaded from: classes.dex */
public interface IBrailleDisplayController extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.accessibilityservice.IBrailleDisplayController";

    void onConnected(android.accessibilityservice.IBrailleDisplayConnection iBrailleDisplayConnection, byte[] bArr) throws android.os.RemoteException;

    void onConnectionFailed(int i) throws android.os.RemoteException;

    void onDisconnected() throws android.os.RemoteException;

    void onInput(byte[] bArr) throws android.os.RemoteException;

    public static class Default implements android.accessibilityservice.IBrailleDisplayController {
        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onConnected(android.accessibilityservice.IBrailleDisplayConnection iBrailleDisplayConnection, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onConnectionFailed(int i) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onInput(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onDisconnected() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.accessibilityservice.IBrailleDisplayController {
        static final int TRANSACTION_onConnected = 1;
        static final int TRANSACTION_onConnectionFailed = 2;
        static final int TRANSACTION_onDisconnected = 4;
        static final int TRANSACTION_onInput = 3;

        public Stub() {
            attachInterface(this, android.accessibilityservice.IBrailleDisplayController.DESCRIPTOR);
        }

        public static android.accessibilityservice.IBrailleDisplayController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.accessibilityservice.IBrailleDisplayController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.accessibilityservice.IBrailleDisplayController)) {
                return (android.accessibilityservice.IBrailleDisplayController) queryLocalInterface;
            }
            return new android.accessibilityservice.IBrailleDisplayController.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onConnected";
                case 2:
                    return "onConnectionFailed";
                case 3:
                    return "onInput";
                case 4:
                    return "onDisconnected";
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
                parcel.enforceInterface(android.accessibilityservice.IBrailleDisplayController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.accessibilityservice.IBrailleDisplayController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.accessibilityservice.IBrailleDisplayConnection asInterface = android.accessibilityservice.IBrailleDisplayConnection.Stub.asInterface(parcel.readStrongBinder());
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onConnected(asInterface, createByteArray);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onConnectionFailed(readInt);
                    return true;
                case 3:
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onInput(createByteArray2);
                    return true;
                case 4:
                    onDisconnected();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.accessibilityservice.IBrailleDisplayController {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.accessibilityservice.IBrailleDisplayController.DESCRIPTOR;
            }

            @Override // android.accessibilityservice.IBrailleDisplayController
            public void onConnected(android.accessibilityservice.IBrailleDisplayConnection iBrailleDisplayConnection, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IBrailleDisplayController.DESCRIPTOR);
                    obtain.writeStrongInterface(iBrailleDisplayConnection);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IBrailleDisplayController
            public void onConnectionFailed(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IBrailleDisplayController.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IBrailleDisplayController
            public void onInput(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IBrailleDisplayController.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IBrailleDisplayController
            public void onDisconnected() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IBrailleDisplayController.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
