package android.service.attention;

/* loaded from: classes3.dex */
public interface IProximityUpdateCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.attention.IProximityUpdateCallback";

    void onProximityUpdate(double d) throws android.os.RemoteException;

    public static class Default implements android.service.attention.IProximityUpdateCallback {
        @Override // android.service.attention.IProximityUpdateCallback
        public void onProximityUpdate(double d) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.attention.IProximityUpdateCallback {
        static final int TRANSACTION_onProximityUpdate = 1;

        public Stub() {
            attachInterface(this, android.service.attention.IProximityUpdateCallback.DESCRIPTOR);
        }

        public static android.service.attention.IProximityUpdateCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.attention.IProximityUpdateCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.attention.IProximityUpdateCallback)) {
                return (android.service.attention.IProximityUpdateCallback) queryLocalInterface;
            }
            return new android.service.attention.IProximityUpdateCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onProximityUpdate";
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
                parcel.enforceInterface(android.service.attention.IProximityUpdateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.attention.IProximityUpdateCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    double readDouble = parcel.readDouble();
                    parcel.enforceNoDataAvail();
                    onProximityUpdate(readDouble);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.attention.IProximityUpdateCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.attention.IProximityUpdateCallback.DESCRIPTOR;
            }

            @Override // android.service.attention.IProximityUpdateCallback
            public void onProximityUpdate(double d) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.attention.IProximityUpdateCallback.DESCRIPTOR);
                    obtain.writeDouble(d);
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
