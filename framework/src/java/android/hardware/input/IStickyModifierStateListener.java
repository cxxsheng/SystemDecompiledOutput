package android.hardware.input;

/* loaded from: classes2.dex */
public interface IStickyModifierStateListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.input.IStickyModifierStateListener";

    void onStickyModifierStateChanged(int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.hardware.input.IStickyModifierStateListener {
        @Override // android.hardware.input.IStickyModifierStateListener
        public void onStickyModifierStateChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.input.IStickyModifierStateListener {
        static final int TRANSACTION_onStickyModifierStateChanged = 1;

        public Stub() {
            attachInterface(this, android.hardware.input.IStickyModifierStateListener.DESCRIPTOR);
        }

        public static android.hardware.input.IStickyModifierStateListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.input.IStickyModifierStateListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.input.IStickyModifierStateListener)) {
                return (android.hardware.input.IStickyModifierStateListener) queryLocalInterface;
            }
            return new android.hardware.input.IStickyModifierStateListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onStickyModifierStateChanged";
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
                parcel.enforceInterface(android.hardware.input.IStickyModifierStateListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.input.IStickyModifierStateListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStickyModifierStateChanged(readInt, readInt2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.input.IStickyModifierStateListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.input.IStickyModifierStateListener.DESCRIPTOR;
            }

            @Override // android.hardware.input.IStickyModifierStateListener
            public void onStickyModifierStateChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IStickyModifierStateListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
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
