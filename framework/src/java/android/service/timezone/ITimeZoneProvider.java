package android.service.timezone;

/* loaded from: classes3.dex */
public interface ITimeZoneProvider extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.timezone.ITimeZoneProvider";

    void startUpdates(android.service.timezone.ITimeZoneProviderManager iTimeZoneProviderManager, long j, long j2) throws android.os.RemoteException;

    void stopUpdates() throws android.os.RemoteException;

    public static class Default implements android.service.timezone.ITimeZoneProvider {
        @Override // android.service.timezone.ITimeZoneProvider
        public void startUpdates(android.service.timezone.ITimeZoneProviderManager iTimeZoneProviderManager, long j, long j2) throws android.os.RemoteException {
        }

        @Override // android.service.timezone.ITimeZoneProvider
        public void stopUpdates() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.timezone.ITimeZoneProvider {
        static final int TRANSACTION_startUpdates = 1;
        static final int TRANSACTION_stopUpdates = 2;

        public Stub() {
            attachInterface(this, android.service.timezone.ITimeZoneProvider.DESCRIPTOR);
        }

        public static android.service.timezone.ITimeZoneProvider asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.timezone.ITimeZoneProvider.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.timezone.ITimeZoneProvider)) {
                return (android.service.timezone.ITimeZoneProvider) queryLocalInterface;
            }
            return new android.service.timezone.ITimeZoneProvider.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startUpdates";
                case 2:
                    return "stopUpdates";
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
                parcel.enforceInterface(android.service.timezone.ITimeZoneProvider.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.timezone.ITimeZoneProvider.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.service.timezone.ITimeZoneProviderManager asInterface = android.service.timezone.ITimeZoneProviderManager.Stub.asInterface(parcel.readStrongBinder());
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    startUpdates(asInterface, readLong, readLong2);
                    return true;
                case 2:
                    stopUpdates();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.timezone.ITimeZoneProvider {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.timezone.ITimeZoneProvider.DESCRIPTOR;
            }

            @Override // android.service.timezone.ITimeZoneProvider
            public void startUpdates(android.service.timezone.ITimeZoneProviderManager iTimeZoneProviderManager, long j, long j2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.timezone.ITimeZoneProvider.DESCRIPTOR);
                    obtain.writeStrongInterface(iTimeZoneProviderManager);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.timezone.ITimeZoneProvider
            public void stopUpdates() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.timezone.ITimeZoneProvider.DESCRIPTOR);
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
