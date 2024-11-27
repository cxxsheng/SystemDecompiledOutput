package android.hardware;

/* loaded from: classes.dex */
public interface ISensorPrivacyListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.ISensorPrivacyListener";

    void onSensorPrivacyChanged(int i, int i2, boolean z) throws android.os.RemoteException;

    void onSensorPrivacyStateChanged(int i, int i2, int i3) throws android.os.RemoteException;

    public static class Default implements android.hardware.ISensorPrivacyListener {
        @Override // android.hardware.ISensorPrivacyListener
        public void onSensorPrivacyChanged(int i, int i2, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyListener
        public void onSensorPrivacyStateChanged(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.ISensorPrivacyListener {
        static final int TRANSACTION_onSensorPrivacyChanged = 1;
        static final int TRANSACTION_onSensorPrivacyStateChanged = 2;

        public Stub() {
            attachInterface(this, android.hardware.ISensorPrivacyListener.DESCRIPTOR);
        }

        public static android.hardware.ISensorPrivacyListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.ISensorPrivacyListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.ISensorPrivacyListener)) {
                return (android.hardware.ISensorPrivacyListener) queryLocalInterface;
            }
            return new android.hardware.ISensorPrivacyListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSensorPrivacyChanged";
                case 2:
                    return "onSensorPrivacyStateChanged";
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
                parcel.enforceInterface(android.hardware.ISensorPrivacyListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.ISensorPrivacyListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onSensorPrivacyChanged(readInt, readInt2, readBoolean);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSensorPrivacyStateChanged(readInt3, readInt4, readInt5);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.ISensorPrivacyListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.ISensorPrivacyListener.DESCRIPTOR;
            }

            @Override // android.hardware.ISensorPrivacyListener
            public void onSensorPrivacyChanged(int i, int i2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.ISensorPrivacyListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyListener
            public void onSensorPrivacyStateChanged(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.ISensorPrivacyListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
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
