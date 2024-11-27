package android.service.timezone;

/* loaded from: classes3.dex */
public interface ITimeZoneProviderManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.timezone.ITimeZoneProviderManager";

    void onTimeZoneProviderEvent(android.service.timezone.TimeZoneProviderEvent timeZoneProviderEvent) throws android.os.RemoteException;

    public static class Default implements android.service.timezone.ITimeZoneProviderManager {
        @Override // android.service.timezone.ITimeZoneProviderManager
        public void onTimeZoneProviderEvent(android.service.timezone.TimeZoneProviderEvent timeZoneProviderEvent) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.timezone.ITimeZoneProviderManager {
        static final int TRANSACTION_onTimeZoneProviderEvent = 1;

        public Stub() {
            attachInterface(this, android.service.timezone.ITimeZoneProviderManager.DESCRIPTOR);
        }

        public static android.service.timezone.ITimeZoneProviderManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.timezone.ITimeZoneProviderManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.timezone.ITimeZoneProviderManager)) {
                return (android.service.timezone.ITimeZoneProviderManager) queryLocalInterface;
            }
            return new android.service.timezone.ITimeZoneProviderManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onTimeZoneProviderEvent";
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
                parcel.enforceInterface(android.service.timezone.ITimeZoneProviderManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.timezone.ITimeZoneProviderManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.service.timezone.TimeZoneProviderEvent timeZoneProviderEvent = (android.service.timezone.TimeZoneProviderEvent) parcel.readTypedObject(android.service.timezone.TimeZoneProviderEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTimeZoneProviderEvent(timeZoneProviderEvent);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.timezone.ITimeZoneProviderManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.timezone.ITimeZoneProviderManager.DESCRIPTOR;
            }

            @Override // android.service.timezone.ITimeZoneProviderManager
            public void onTimeZoneProviderEvent(android.service.timezone.TimeZoneProviderEvent timeZoneProviderEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.timezone.ITimeZoneProviderManager.DESCRIPTOR);
                    obtain.writeTypedObject(timeZoneProviderEvent, 0);
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
