package android.debug;

/* loaded from: classes.dex */
public interface IAdbTransport extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.debug.IAdbTransport";

    void onAdbEnabled(boolean z, byte b) throws android.os.RemoteException;

    public static class Default implements android.debug.IAdbTransport {
        @Override // android.debug.IAdbTransport
        public void onAdbEnabled(boolean z, byte b) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.debug.IAdbTransport {
        static final int TRANSACTION_onAdbEnabled = 1;

        public Stub() {
            attachInterface(this, android.debug.IAdbTransport.DESCRIPTOR);
        }

        public static android.debug.IAdbTransport asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.debug.IAdbTransport.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.debug.IAdbTransport)) {
                return (android.debug.IAdbTransport) queryLocalInterface;
            }
            return new android.debug.IAdbTransport.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onAdbEnabled";
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
                parcel.enforceInterface(android.debug.IAdbTransport.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.debug.IAdbTransport.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    byte readByte = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    onAdbEnabled(readBoolean, readByte);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.debug.IAdbTransport {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.debug.IAdbTransport.DESCRIPTOR;
            }

            @Override // android.debug.IAdbTransport
            public void onAdbEnabled(boolean z, byte b) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.debug.IAdbTransport.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeByte(b);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
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
