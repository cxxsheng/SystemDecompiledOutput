package android.hardware.camera2;

/* loaded from: classes.dex */
public interface ICameraInjectionSession extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.camera2.ICameraInjectionSession";

    void stopInjection() throws android.os.RemoteException;

    public static class Default implements android.hardware.camera2.ICameraInjectionSession {
        @Override // android.hardware.camera2.ICameraInjectionSession
        public void stopInjection() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.camera2.ICameraInjectionSession {
        static final int TRANSACTION_stopInjection = 1;

        public Stub() {
            attachInterface(this, android.hardware.camera2.ICameraInjectionSession.DESCRIPTOR);
        }

        public static android.hardware.camera2.ICameraInjectionSession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.camera2.ICameraInjectionSession.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.camera2.ICameraInjectionSession)) {
                return (android.hardware.camera2.ICameraInjectionSession) queryLocalInterface;
            }
            return new android.hardware.camera2.ICameraInjectionSession.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "stopInjection";
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
                parcel.enforceInterface(android.hardware.camera2.ICameraInjectionSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.camera2.ICameraInjectionSession.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    stopInjection();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.camera2.ICameraInjectionSession {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.camera2.ICameraInjectionSession.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.ICameraInjectionSession
            public void stopInjection() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraInjectionSession.DESCRIPTOR);
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
