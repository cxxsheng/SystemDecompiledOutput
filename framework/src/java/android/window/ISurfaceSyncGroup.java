package android.window;

/* loaded from: classes4.dex */
public interface ISurfaceSyncGroup extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.ISurfaceSyncGroup";

    boolean addToSync(android.window.ISurfaceSyncGroup iSurfaceSyncGroup, boolean z) throws android.os.RemoteException;

    boolean onAddedToSyncGroup(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    public static class Default implements android.window.ISurfaceSyncGroup {
        @Override // android.window.ISurfaceSyncGroup
        public boolean onAddedToSyncGroup(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.window.ISurfaceSyncGroup
        public boolean addToSync(android.window.ISurfaceSyncGroup iSurfaceSyncGroup, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.ISurfaceSyncGroup {
        static final int TRANSACTION_addToSync = 2;
        static final int TRANSACTION_onAddedToSyncGroup = 1;

        public Stub() {
            attachInterface(this, android.window.ISurfaceSyncGroup.DESCRIPTOR);
        }

        public static android.window.ISurfaceSyncGroup asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.ISurfaceSyncGroup.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.ISurfaceSyncGroup)) {
                return (android.window.ISurfaceSyncGroup) queryLocalInterface;
            }
            return new android.window.ISurfaceSyncGroup.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onAddedToSyncGroup";
                case 2:
                    return "addToSync";
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
                parcel.enforceInterface(android.window.ISurfaceSyncGroup.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.ISurfaceSyncGroup.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean onAddedToSyncGroup = onAddedToSyncGroup(readStrongBinder, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(onAddedToSyncGroup);
                    return true;
                case 2:
                    android.window.ISurfaceSyncGroup asInterface = asInterface(parcel.readStrongBinder());
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean addToSync = addToSync(asInterface, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addToSync);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.ISurfaceSyncGroup {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.ISurfaceSyncGroup.DESCRIPTOR;
            }

            @Override // android.window.ISurfaceSyncGroup
            public boolean onAddedToSyncGroup(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.ISurfaceSyncGroup.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ISurfaceSyncGroup
            public boolean addToSync(android.window.ISurfaceSyncGroup iSurfaceSyncGroup, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.ISurfaceSyncGroup.DESCRIPTOR);
                    obtain.writeStrongInterface(iSurfaceSyncGroup);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
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
