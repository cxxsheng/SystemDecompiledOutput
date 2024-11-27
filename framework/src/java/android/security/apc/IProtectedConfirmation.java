package android.security.apc;

/* loaded from: classes3.dex */
public interface IProtectedConfirmation extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.security.apc.IProtectedConfirmation";
    public static final int FLAG_UI_OPTION_INVERTED = 1;
    public static final int FLAG_UI_OPTION_MAGNIFIED = 2;

    @java.lang.Deprecated
    void cancelPrompt(android.security.apc.IConfirmationCallback iConfirmationCallback) throws android.os.RemoteException;

    @java.lang.Deprecated
    boolean isSupported() throws android.os.RemoteException;

    @java.lang.Deprecated
    void presentPrompt(android.security.apc.IConfirmationCallback iConfirmationCallback, java.lang.String str, byte[] bArr, java.lang.String str2, int i) throws android.os.RemoteException;

    public static class Default implements android.security.apc.IProtectedConfirmation {
        @Override // android.security.apc.IProtectedConfirmation
        public void presentPrompt(android.security.apc.IConfirmationCallback iConfirmationCallback, java.lang.String str, byte[] bArr, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // android.security.apc.IProtectedConfirmation
        public void cancelPrompt(android.security.apc.IConfirmationCallback iConfirmationCallback) throws android.os.RemoteException {
        }

        @Override // android.security.apc.IProtectedConfirmation
        public boolean isSupported() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.security.apc.IProtectedConfirmation {
        static final int TRANSACTION_cancelPrompt = 2;
        static final int TRANSACTION_isSupported = 3;
        static final int TRANSACTION_presentPrompt = 1;

        public Stub() {
            attachInterface(this, android.security.apc.IProtectedConfirmation.DESCRIPTOR);
        }

        public static android.security.apc.IProtectedConfirmation asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.security.apc.IProtectedConfirmation.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.security.apc.IProtectedConfirmation)) {
                return (android.security.apc.IProtectedConfirmation) queryLocalInterface;
            }
            return new android.security.apc.IProtectedConfirmation.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.security.apc.IProtectedConfirmation.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.security.apc.IProtectedConfirmation.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.security.apc.IConfirmationCallback asInterface = android.security.apc.IConfirmationCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    java.lang.String readString2 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    presentPrompt(asInterface, readString, createByteArray, readString2, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.security.apc.IConfirmationCallback asInterface2 = android.security.apc.IConfirmationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cancelPrompt(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    boolean isSupported = isSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSupported);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.security.apc.IProtectedConfirmation {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.security.apc.IProtectedConfirmation.DESCRIPTOR;
            }

            @Override // android.security.apc.IProtectedConfirmation
            public void presentPrompt(android.security.apc.IConfirmationCallback iConfirmationCallback, java.lang.String str, byte[] bArr, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.apc.IProtectedConfirmation.DESCRIPTOR);
                    obtain.writeStrongInterface(iConfirmationCallback);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.apc.IProtectedConfirmation
            public void cancelPrompt(android.security.apc.IConfirmationCallback iConfirmationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.apc.IProtectedConfirmation.DESCRIPTOR);
                    obtain.writeStrongInterface(iConfirmationCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.apc.IProtectedConfirmation
            public boolean isSupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.apc.IProtectedConfirmation.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
