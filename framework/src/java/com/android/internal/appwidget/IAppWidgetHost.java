package com.android.internal.appwidget;

/* loaded from: classes4.dex */
public interface IAppWidgetHost extends android.os.IInterface {
    void appWidgetRemoved(int i) throws android.os.RemoteException;

    void providerChanged(int i, android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo) throws android.os.RemoteException;

    void providersChanged() throws android.os.RemoteException;

    void updateAppWidget(int i, android.widget.RemoteViews remoteViews) throws android.os.RemoteException;

    void viewDataChanged(int i, int i2) throws android.os.RemoteException;

    public static class Default implements com.android.internal.appwidget.IAppWidgetHost {
        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void updateAppWidget(int i, android.widget.RemoteViews remoteViews) throws android.os.RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void providerChanged(int i, android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo) throws android.os.RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void providersChanged() throws android.os.RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void viewDataChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetHost
        public void appWidgetRemoved(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.appwidget.IAppWidgetHost {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.appwidget.IAppWidgetHost";
        static final int TRANSACTION_appWidgetRemoved = 5;
        static final int TRANSACTION_providerChanged = 2;
        static final int TRANSACTION_providersChanged = 3;
        static final int TRANSACTION_updateAppWidget = 1;
        static final int TRANSACTION_viewDataChanged = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.appwidget.IAppWidgetHost asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.appwidget.IAppWidgetHost)) {
                return (com.android.internal.appwidget.IAppWidgetHost) queryLocalInterface;
            }
            return new com.android.internal.appwidget.IAppWidgetHost.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "updateAppWidget";
                case 2:
                    return "providerChanged";
                case 3:
                    return "providersChanged";
                case 4:
                    return "viewDataChanged";
                case 5:
                    return "appWidgetRemoved";
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
                    int readInt = parcel.readInt();
                    android.widget.RemoteViews remoteViews = (android.widget.RemoteViews) parcel.readTypedObject(android.widget.RemoteViews.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateAppWidget(readInt, remoteViews);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo = (android.appwidget.AppWidgetProviderInfo) parcel.readTypedObject(android.appwidget.AppWidgetProviderInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    providerChanged(readInt2, appWidgetProviderInfo);
                    return true;
                case 3:
                    providersChanged();
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    viewDataChanged(readInt3, readInt4);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    appWidgetRemoved(readInt5);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.appwidget.IAppWidgetHost {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.appwidget.IAppWidgetHost.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.appwidget.IAppWidgetHost
            public void updateAppWidget(int i, android.widget.RemoteViews remoteViews) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetHost.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(remoteViews, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetHost
            public void providerChanged(int i, android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetHost.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(appWidgetProviderInfo, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetHost
            public void providersChanged() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetHost.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetHost
            public void viewDataChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetHost.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetHost
            public void appWidgetRemoved(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.appwidget.IAppWidgetHost.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
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
