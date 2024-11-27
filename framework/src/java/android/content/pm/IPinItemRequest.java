package android.content.pm;

/* loaded from: classes.dex */
public interface IPinItemRequest extends android.os.IInterface {
    boolean accept(android.os.Bundle bundle) throws android.os.RemoteException;

    android.appwidget.AppWidgetProviderInfo getAppWidgetProviderInfo() throws android.os.RemoteException;

    android.os.Bundle getExtras() throws android.os.RemoteException;

    android.content.pm.ShortcutInfo getShortcutInfo() throws android.os.RemoteException;

    boolean isValid() throws android.os.RemoteException;

    public static class Default implements android.content.pm.IPinItemRequest {
        @Override // android.content.pm.IPinItemRequest
        public boolean isValid() throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPinItemRequest
        public boolean accept(android.os.Bundle bundle) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPinItemRequest
        public android.content.pm.ShortcutInfo getShortcutInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPinItemRequest
        public android.appwidget.AppWidgetProviderInfo getAppWidgetProviderInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPinItemRequest
        public android.os.Bundle getExtras() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.IPinItemRequest {
        public static final java.lang.String DESCRIPTOR = "android.content.pm.IPinItemRequest";
        static final int TRANSACTION_accept = 2;
        static final int TRANSACTION_getAppWidgetProviderInfo = 4;
        static final int TRANSACTION_getExtras = 5;
        static final int TRANSACTION_getShortcutInfo = 3;
        static final int TRANSACTION_isValid = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.content.pm.IPinItemRequest asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.IPinItemRequest)) {
                return (android.content.pm.IPinItemRequest) queryLocalInterface;
            }
            return new android.content.pm.IPinItemRequest.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isValid";
                case 2:
                    return "accept";
                case 3:
                    return "getShortcutInfo";
                case 4:
                    return "getAppWidgetProviderInfo";
                case 5:
                    return "getExtras";
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
                    boolean isValid = isValid();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isValid);
                    return true;
                case 2:
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean accept = accept(bundle);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(accept);
                    return true;
                case 3:
                    android.content.pm.ShortcutInfo shortcutInfo = getShortcutInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(shortcutInfo, 1);
                    return true;
                case 4:
                    android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo = getAppWidgetProviderInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appWidgetProviderInfo, 1);
                    return true;
                case 5:
                    android.os.Bundle extras = getExtras();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(extras, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.pm.IPinItemRequest {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.IPinItemRequest.Stub.DESCRIPTOR;
            }

            @Override // android.content.pm.IPinItemRequest
            public boolean isValid() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPinItemRequest.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPinItemRequest
            public boolean accept(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPinItemRequest.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPinItemRequest
            public android.content.pm.ShortcutInfo getShortcutInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPinItemRequest.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ShortcutInfo) obtain2.readTypedObject(android.content.pm.ShortcutInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPinItemRequest
            public android.appwidget.AppWidgetProviderInfo getAppWidgetProviderInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPinItemRequest.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.appwidget.AppWidgetProviderInfo) obtain2.readTypedObject(android.appwidget.AppWidgetProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPinItemRequest
            public android.os.Bundle getExtras() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPinItemRequest.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Bundle) obtain2.readTypedObject(android.os.Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
