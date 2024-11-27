package android.os;

/* loaded from: classes3.dex */
public interface ILogd extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.ILogd";

    void approve(int i, int i2, int i3, int i4) throws android.os.RemoteException;

    void decline(int i, int i2, int i3, int i4) throws android.os.RemoteException;

    public static class Default implements android.os.ILogd {
        @Override // android.os.ILogd
        public void approve(int i, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.os.ILogd
        public void decline(int i, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.ILogd {
        static final int TRANSACTION_approve = 1;
        static final int TRANSACTION_decline = 2;

        public Stub() {
            attachInterface(this, android.os.ILogd.DESCRIPTOR);
        }

        public static android.os.ILogd asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.ILogd.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.ILogd)) {
                return (android.os.ILogd) queryLocalInterface;
            }
            return new android.os.ILogd.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "approve";
                case 2:
                    return "decline";
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
                parcel.enforceInterface(android.os.ILogd.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.ILogd.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    approve(readInt, readInt2, readInt3, readInt4);
                    return true;
                case 2:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    decline(readInt5, readInt6, readInt7, readInt8);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.ILogd {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.ILogd.DESCRIPTOR;
            }

            @Override // android.os.ILogd
            public void approve(int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.ILogd.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.ILogd
            public void decline(int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.ILogd.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
