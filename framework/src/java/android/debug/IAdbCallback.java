package android.debug;

/* loaded from: classes.dex */
public interface IAdbCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.debug.IAdbCallback";

    void onDebuggingChanged(boolean z, byte b) throws android.os.RemoteException;

    public static class Default implements android.debug.IAdbCallback {
        @Override // android.debug.IAdbCallback
        public void onDebuggingChanged(boolean z, byte b) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.debug.IAdbCallback {
        static final int TRANSACTION_onDebuggingChanged = 1;

        public Stub() {
            attachInterface(this, android.debug.IAdbCallback.DESCRIPTOR);
        }

        public static android.debug.IAdbCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.debug.IAdbCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.debug.IAdbCallback)) {
                return (android.debug.IAdbCallback) queryLocalInterface;
            }
            return new android.debug.IAdbCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDebuggingChanged";
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
                parcel.enforceInterface(android.debug.IAdbCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.debug.IAdbCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    byte readByte = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    onDebuggingChanged(readBoolean, readByte);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.debug.IAdbCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.debug.IAdbCallback.DESCRIPTOR;
            }

            @Override // android.debug.IAdbCallback
            public void onDebuggingChanged(boolean z, byte b) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.debug.IAdbCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeByte(b);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
