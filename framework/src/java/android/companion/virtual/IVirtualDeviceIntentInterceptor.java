package android.companion.virtual;

/* loaded from: classes.dex */
public interface IVirtualDeviceIntentInterceptor extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.companion.virtual.IVirtualDeviceIntentInterceptor";

    void onIntentIntercepted(android.content.Intent intent) throws android.os.RemoteException;

    public static class Default implements android.companion.virtual.IVirtualDeviceIntentInterceptor {
        @Override // android.companion.virtual.IVirtualDeviceIntentInterceptor
        public void onIntentIntercepted(android.content.Intent intent) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.companion.virtual.IVirtualDeviceIntentInterceptor {
        static final int TRANSACTION_onIntentIntercepted = 1;

        public Stub() {
            attachInterface(this, android.companion.virtual.IVirtualDeviceIntentInterceptor.DESCRIPTOR);
        }

        public static android.companion.virtual.IVirtualDeviceIntentInterceptor asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.companion.virtual.IVirtualDeviceIntentInterceptor.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.companion.virtual.IVirtualDeviceIntentInterceptor)) {
                return (android.companion.virtual.IVirtualDeviceIntentInterceptor) queryLocalInterface;
            }
            return new android.companion.virtual.IVirtualDeviceIntentInterceptor.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onIntentIntercepted";
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
                parcel.enforceInterface(android.companion.virtual.IVirtualDeviceIntentInterceptor.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.companion.virtual.IVirtualDeviceIntentInterceptor.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onIntentIntercepted(intent);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.companion.virtual.IVirtualDeviceIntentInterceptor {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.companion.virtual.IVirtualDeviceIntentInterceptor.DESCRIPTOR;
            }

            @Override // android.companion.virtual.IVirtualDeviceIntentInterceptor
            public void onIntentIntercepted(android.content.Intent intent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDeviceIntentInterceptor.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
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
