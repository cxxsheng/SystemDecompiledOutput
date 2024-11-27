package android.app.ondeviceintelligence;

/* loaded from: classes.dex */
public interface IProcessingSignal extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.ondeviceintelligence.IProcessingSignal";

    void sendSignal(android.os.PersistableBundle persistableBundle) throws android.os.RemoteException;

    public static class Default implements android.app.ondeviceintelligence.IProcessingSignal {
        @Override // android.app.ondeviceintelligence.IProcessingSignal
        public void sendSignal(android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.ondeviceintelligence.IProcessingSignal {
        static final int TRANSACTION_sendSignal = 3;

        public Stub() {
            attachInterface(this, android.app.ondeviceintelligence.IProcessingSignal.DESCRIPTOR);
        }

        public static android.app.ondeviceintelligence.IProcessingSignal asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.ondeviceintelligence.IProcessingSignal.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.ondeviceintelligence.IProcessingSignal)) {
                return (android.app.ondeviceintelligence.IProcessingSignal) queryLocalInterface;
            }
            return new android.app.ondeviceintelligence.IProcessingSignal.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 3:
                    return "sendSignal";
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
                parcel.enforceInterface(android.app.ondeviceintelligence.IProcessingSignal.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.ondeviceintelligence.IProcessingSignal.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 3:
                    android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSignal(persistableBundle);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.ondeviceintelligence.IProcessingSignal {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.ondeviceintelligence.IProcessingSignal.DESCRIPTOR;
            }

            @Override // android.app.ondeviceintelligence.IProcessingSignal
            public void sendSignal(android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ondeviceintelligence.IProcessingSignal.DESCRIPTOR);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
