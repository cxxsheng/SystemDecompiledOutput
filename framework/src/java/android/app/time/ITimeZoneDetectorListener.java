package android.app.time;

/* loaded from: classes.dex */
public interface ITimeZoneDetectorListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.time.ITimeZoneDetectorListener";

    void onChange() throws android.os.RemoteException;

    public static class Default implements android.app.time.ITimeZoneDetectorListener {
        @Override // android.app.time.ITimeZoneDetectorListener
        public void onChange() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.time.ITimeZoneDetectorListener {
        static final int TRANSACTION_onChange = 1;

        public Stub() {
            attachInterface(this, android.app.time.ITimeZoneDetectorListener.DESCRIPTOR);
        }

        public static android.app.time.ITimeZoneDetectorListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.time.ITimeZoneDetectorListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.time.ITimeZoneDetectorListener)) {
                return (android.app.time.ITimeZoneDetectorListener) queryLocalInterface;
            }
            return new android.app.time.ITimeZoneDetectorListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onChange";
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
                parcel.enforceInterface(android.app.time.ITimeZoneDetectorListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.time.ITimeZoneDetectorListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onChange();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.time.ITimeZoneDetectorListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.time.ITimeZoneDetectorListener.DESCRIPTOR;
            }

            @Override // android.app.time.ITimeZoneDetectorListener
            public void onChange() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.time.ITimeZoneDetectorListener.DESCRIPTOR);
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
