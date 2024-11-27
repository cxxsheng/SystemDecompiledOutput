package android.flags;

/* loaded from: classes.dex */
public interface IFeatureFlags extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.flags.IFeatureFlags";

    void overrideFlag(android.flags.SyncableFlag syncableFlag) throws android.os.RemoteException;

    java.util.List<android.flags.SyncableFlag> queryFlags(java.util.List<android.flags.SyncableFlag> list) throws android.os.RemoteException;

    void registerCallback(android.flags.IFeatureFlagsCallback iFeatureFlagsCallback) throws android.os.RemoteException;

    void resetFlag(android.flags.SyncableFlag syncableFlag) throws android.os.RemoteException;

    java.util.List<android.flags.SyncableFlag> syncFlags(java.util.List<android.flags.SyncableFlag> list) throws android.os.RemoteException;

    void unregisterCallback(android.flags.IFeatureFlagsCallback iFeatureFlagsCallback) throws android.os.RemoteException;

    public static class Default implements android.flags.IFeatureFlags {
        @Override // android.flags.IFeatureFlags
        public java.util.List<android.flags.SyncableFlag> syncFlags(java.util.List<android.flags.SyncableFlag> list) throws android.os.RemoteException {
            return null;
        }

        @Override // android.flags.IFeatureFlags
        public void registerCallback(android.flags.IFeatureFlagsCallback iFeatureFlagsCallback) throws android.os.RemoteException {
        }

        @Override // android.flags.IFeatureFlags
        public void unregisterCallback(android.flags.IFeatureFlagsCallback iFeatureFlagsCallback) throws android.os.RemoteException {
        }

        @Override // android.flags.IFeatureFlags
        public java.util.List<android.flags.SyncableFlag> queryFlags(java.util.List<android.flags.SyncableFlag> list) throws android.os.RemoteException {
            return null;
        }

        @Override // android.flags.IFeatureFlags
        public void overrideFlag(android.flags.SyncableFlag syncableFlag) throws android.os.RemoteException {
        }

        @Override // android.flags.IFeatureFlags
        public void resetFlag(android.flags.SyncableFlag syncableFlag) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.flags.IFeatureFlags {
        static final int TRANSACTION_overrideFlag = 5;
        static final int TRANSACTION_queryFlags = 4;
        static final int TRANSACTION_registerCallback = 2;
        static final int TRANSACTION_resetFlag = 6;
        static final int TRANSACTION_syncFlags = 1;
        static final int TRANSACTION_unregisterCallback = 3;

        public Stub() {
            attachInterface(this, android.flags.IFeatureFlags.DESCRIPTOR);
        }

        public static android.flags.IFeatureFlags asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.flags.IFeatureFlags.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.flags.IFeatureFlags)) {
                return (android.flags.IFeatureFlags) queryLocalInterface;
            }
            return new android.flags.IFeatureFlags.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "syncFlags";
                case 2:
                    return "registerCallback";
                case 3:
                    return "unregisterCallback";
                case 4:
                    return "queryFlags";
                case 5:
                    return "overrideFlag";
                case 6:
                    return "resetFlag";
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
                parcel.enforceInterface(android.flags.IFeatureFlags.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.flags.IFeatureFlags.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.flags.SyncableFlag.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<android.flags.SyncableFlag> syncFlags = syncFlags(createTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(syncFlags, 1);
                    return true;
                case 2:
                    android.flags.IFeatureFlagsCallback asInterface = android.flags.IFeatureFlagsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.flags.IFeatureFlagsCallback asInterface2 = android.flags.IFeatureFlagsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.flags.SyncableFlag.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<android.flags.SyncableFlag> queryFlags = queryFlags(createTypedArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(queryFlags, 1);
                    return true;
                case 5:
                    android.flags.SyncableFlag syncableFlag = (android.flags.SyncableFlag) parcel.readTypedObject(android.flags.SyncableFlag.CREATOR);
                    parcel.enforceNoDataAvail();
                    overrideFlag(syncableFlag);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.flags.SyncableFlag syncableFlag2 = (android.flags.SyncableFlag) parcel.readTypedObject(android.flags.SyncableFlag.CREATOR);
                    parcel.enforceNoDataAvail();
                    resetFlag(syncableFlag2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.flags.IFeatureFlags {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.flags.IFeatureFlags.DESCRIPTOR;
            }

            @Override // android.flags.IFeatureFlags
            public java.util.List<android.flags.SyncableFlag> syncFlags(java.util.List<android.flags.SyncableFlag> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.flags.IFeatureFlags.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.flags.SyncableFlag.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.flags.IFeatureFlags
            public void registerCallback(android.flags.IFeatureFlagsCallback iFeatureFlagsCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.flags.IFeatureFlags.DESCRIPTOR);
                    obtain.writeStrongInterface(iFeatureFlagsCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.flags.IFeatureFlags
            public void unregisterCallback(android.flags.IFeatureFlagsCallback iFeatureFlagsCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.flags.IFeatureFlags.DESCRIPTOR);
                    obtain.writeStrongInterface(iFeatureFlagsCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.flags.IFeatureFlags
            public java.util.List<android.flags.SyncableFlag> queryFlags(java.util.List<android.flags.SyncableFlag> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.flags.IFeatureFlags.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.flags.SyncableFlag.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.flags.IFeatureFlags
            public void overrideFlag(android.flags.SyncableFlag syncableFlag) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.flags.IFeatureFlags.DESCRIPTOR);
                    obtain.writeTypedObject(syncableFlag, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.flags.IFeatureFlags
            public void resetFlag(android.flags.SyncableFlag syncableFlag) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.flags.IFeatureFlags.DESCRIPTOR);
                    obtain.writeTypedObject(syncableFlag, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
