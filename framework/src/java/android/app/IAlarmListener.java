package android.app;

/* loaded from: classes.dex */
public interface IAlarmListener extends android.os.IInterface {
    void doAlarm(android.app.IAlarmCompleteListener iAlarmCompleteListener) throws android.os.RemoteException;

    public static class Default implements android.app.IAlarmListener {
        @Override // android.app.IAlarmListener
        public void doAlarm(android.app.IAlarmCompleteListener iAlarmCompleteListener) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IAlarmListener {
        public static final java.lang.String DESCRIPTOR = "android.app.IAlarmListener";
        static final int TRANSACTION_doAlarm = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.IAlarmListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IAlarmListener)) {
                return (android.app.IAlarmListener) queryLocalInterface;
            }
            return new android.app.IAlarmListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "doAlarm";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.IAlarmCompleteListener asInterface = android.app.IAlarmCompleteListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    doAlarm(asInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IAlarmListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IAlarmListener.Stub.DESCRIPTOR;
            }

            @Override // android.app.IAlarmListener
            public void doAlarm(android.app.IAlarmCompleteListener iAlarmCompleteListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IAlarmListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAlarmCompleteListener);
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
