package android.app.admin;

/* loaded from: classes.dex */
public interface IAuditLogEventsCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.admin.IAuditLogEventsCallback";

    void onNewAuditLogEvents(java.util.List<android.app.admin.SecurityLog.SecurityEvent> list) throws android.os.RemoteException;

    public static class Default implements android.app.admin.IAuditLogEventsCallback {
        @Override // android.app.admin.IAuditLogEventsCallback
        public void onNewAuditLogEvents(java.util.List<android.app.admin.SecurityLog.SecurityEvent> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.admin.IAuditLogEventsCallback {
        static final int TRANSACTION_onNewAuditLogEvents = 1;

        public Stub() {
            attachInterface(this, android.app.admin.IAuditLogEventsCallback.DESCRIPTOR);
        }

        public static android.app.admin.IAuditLogEventsCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.admin.IAuditLogEventsCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.admin.IAuditLogEventsCallback)) {
                return (android.app.admin.IAuditLogEventsCallback) queryLocalInterface;
            }
            return new android.app.admin.IAuditLogEventsCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onNewAuditLogEvents";
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
                parcel.enforceInterface(android.app.admin.IAuditLogEventsCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.admin.IAuditLogEventsCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.app.admin.SecurityLog.SecurityEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNewAuditLogEvents(createTypedArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.admin.IAuditLogEventsCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.admin.IAuditLogEventsCallback.DESCRIPTOR;
            }

            @Override // android.app.admin.IAuditLogEventsCallback
            public void onNewAuditLogEvents(java.util.List<android.app.admin.SecurityLog.SecurityEvent> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.admin.IAuditLogEventsCallback.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
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
