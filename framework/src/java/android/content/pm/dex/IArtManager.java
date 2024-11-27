package android.content.pm.dex;

/* loaded from: classes.dex */
public interface IArtManager extends android.os.IInterface {
    boolean isRuntimeProfilingEnabled(int i, java.lang.String str) throws android.os.RemoteException;

    void snapshotRuntimeProfile(int i, java.lang.String str, java.lang.String str2, android.content.pm.dex.ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback, java.lang.String str3) throws android.os.RemoteException;

    public static class Default implements android.content.pm.dex.IArtManager {
        @Override // android.content.pm.dex.IArtManager
        public void snapshotRuntimeProfile(int i, java.lang.String str, java.lang.String str2, android.content.pm.dex.ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // android.content.pm.dex.IArtManager
        public boolean isRuntimeProfilingEnabled(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.dex.IArtManager {
        public static final java.lang.String DESCRIPTOR = "android.content.pm.dex.IArtManager";
        static final int TRANSACTION_isRuntimeProfilingEnabled = 2;
        static final int TRANSACTION_snapshotRuntimeProfile = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.content.pm.dex.IArtManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.dex.IArtManager)) {
                return (android.content.pm.dex.IArtManager) queryLocalInterface;
            }
            return new android.content.pm.dex.IArtManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "snapshotRuntimeProfile";
                case 2:
                    return "isRuntimeProfilingEnabled";
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
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    android.content.pm.dex.ISnapshotRuntimeProfileCallback asInterface = android.content.pm.dex.ISnapshotRuntimeProfileCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    snapshotRuntimeProfile(readInt, readString, readString2, asInterface, readString3);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isRuntimeProfilingEnabled = isRuntimeProfilingEnabled(readInt2, readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRuntimeProfilingEnabled);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.pm.dex.IArtManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.dex.IArtManager.Stub.DESCRIPTOR;
            }

            @Override // android.content.pm.dex.IArtManager
            public void snapshotRuntimeProfile(int i, java.lang.String str, java.lang.String str2, android.content.pm.dex.ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.dex.IArtManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iSnapshotRuntimeProfileCallback);
                    obtain.writeString(str3);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.dex.IArtManager
            public boolean isRuntimeProfilingEnabled(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.dex.IArtManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
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
