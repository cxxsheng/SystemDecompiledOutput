package android.service.notification;

/* loaded from: classes3.dex */
public interface IStatusBarNotificationHolder extends android.os.IInterface {
    android.service.notification.StatusBarNotification get() throws android.os.RemoteException;

    public static class Default implements android.service.notification.IStatusBarNotificationHolder {
        @Override // android.service.notification.IStatusBarNotificationHolder
        public android.service.notification.StatusBarNotification get() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.notification.IStatusBarNotificationHolder {
        public static final java.lang.String DESCRIPTOR = "android.service.notification.IStatusBarNotificationHolder";
        static final int TRANSACTION_get = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.notification.IStatusBarNotificationHolder asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.notification.IStatusBarNotificationHolder)) {
                return (android.service.notification.IStatusBarNotificationHolder) queryLocalInterface;
            }
            return new android.service.notification.IStatusBarNotificationHolder.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "get";
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
                    android.service.notification.StatusBarNotification statusBarNotification = get();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(statusBarNotification, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.notification.IStatusBarNotificationHolder {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.notification.IStatusBarNotificationHolder.Stub.DESCRIPTOR;
            }

            @Override // android.service.notification.IStatusBarNotificationHolder
            public android.service.notification.StatusBarNotification get() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.notification.IStatusBarNotificationHolder.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.service.notification.StatusBarNotification) obtain2.readTypedObject(android.service.notification.StatusBarNotification.CREATOR);
                } finally {
                    obtain2.recycle();
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
