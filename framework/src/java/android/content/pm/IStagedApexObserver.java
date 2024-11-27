package android.content.pm;

/* loaded from: classes.dex */
public interface IStagedApexObserver extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.content.pm.IStagedApexObserver";

    void onApexStaged(android.content.pm.ApexStagedEvent apexStagedEvent) throws android.os.RemoteException;

    public static class Default implements android.content.pm.IStagedApexObserver {
        @Override // android.content.pm.IStagedApexObserver
        public void onApexStaged(android.content.pm.ApexStagedEvent apexStagedEvent) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.IStagedApexObserver {
        static final int TRANSACTION_onApexStaged = 1;

        public Stub() {
            attachInterface(this, android.content.pm.IStagedApexObserver.DESCRIPTOR);
        }

        public static android.content.pm.IStagedApexObserver asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.content.pm.IStagedApexObserver.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.IStagedApexObserver)) {
                return (android.content.pm.IStagedApexObserver) queryLocalInterface;
            }
            return new android.content.pm.IStagedApexObserver.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.content.pm.IStagedApexObserver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.content.pm.IStagedApexObserver.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.content.pm.ApexStagedEvent apexStagedEvent = (android.content.pm.ApexStagedEvent) parcel.readTypedObject(android.content.pm.ApexStagedEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onApexStaged(apexStagedEvent);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.pm.IStagedApexObserver {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.IStagedApexObserver.DESCRIPTOR;
            }

            @Override // android.content.pm.IStagedApexObserver
            public void onApexStaged(android.content.pm.ApexStagedEvent apexStagedEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IStagedApexObserver.DESCRIPTOR);
                    obtain.writeTypedObject(apexStagedEvent, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
