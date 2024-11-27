package android.app.pinner;

/* loaded from: classes.dex */
public interface IPinnerService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.pinner.IPinnerService";

    java.util.List<android.app.pinner.PinnedFileStat> getPinnerStats() throws android.os.RemoteException;

    public static class Default implements android.app.pinner.IPinnerService {
        @Override // android.app.pinner.IPinnerService
        public java.util.List<android.app.pinner.PinnedFileStat> getPinnerStats() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.pinner.IPinnerService {
        static final int TRANSACTION_getPinnerStats = 1;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, android.app.pinner.IPinnerService.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.app.pinner.IPinnerService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.pinner.IPinnerService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.pinner.IPinnerService)) {
                return (android.app.pinner.IPinnerService) queryLocalInterface;
            }
            return new android.app.pinner.IPinnerService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getPinnerStats";
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
                parcel.enforceInterface(android.app.pinner.IPinnerService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.pinner.IPinnerService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.util.List<android.app.pinner.PinnedFileStat> pinnerStats = getPinnerStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(pinnerStats, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.pinner.IPinnerService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.pinner.IPinnerService.DESCRIPTOR;
            }

            @Override // android.app.pinner.IPinnerService
            public java.util.List<android.app.pinner.PinnedFileStat> getPinnerStats() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.pinner.IPinnerService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.pinner.PinnedFileStat.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void getPinnerStats_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.DUMP, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
