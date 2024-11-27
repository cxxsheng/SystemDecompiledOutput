package android.system.suspend.internal;

/* loaded from: classes3.dex */
public interface ISuspendControlServiceInternal extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.system.suspend.internal.ISuspendControlServiceInternal";

    boolean enableAutosuspend(android.os.IBinder iBinder) throws android.os.RemoteException;

    boolean forceSuspend() throws android.os.RemoteException;

    android.system.suspend.internal.SuspendInfo getSuspendStats() throws android.os.RemoteException;

    android.system.suspend.internal.WakeLockInfo[] getWakeLockStats() throws android.os.RemoteException;

    android.system.suspend.internal.WakeupInfo[] getWakeupStats() throws android.os.RemoteException;

    public static class Default implements android.system.suspend.internal.ISuspendControlServiceInternal {
        @Override // android.system.suspend.internal.ISuspendControlServiceInternal
        public boolean enableAutosuspend(android.os.IBinder iBinder) throws android.os.RemoteException {
            return false;
        }

        @Override // android.system.suspend.internal.ISuspendControlServiceInternal
        public boolean forceSuspend() throws android.os.RemoteException {
            return false;
        }

        @Override // android.system.suspend.internal.ISuspendControlServiceInternal
        public android.system.suspend.internal.WakeLockInfo[] getWakeLockStats() throws android.os.RemoteException {
            return null;
        }

        @Override // android.system.suspend.internal.ISuspendControlServiceInternal
        public android.system.suspend.internal.WakeupInfo[] getWakeupStats() throws android.os.RemoteException {
            return null;
        }

        @Override // android.system.suspend.internal.ISuspendControlServiceInternal
        public android.system.suspend.internal.SuspendInfo getSuspendStats() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.system.suspend.internal.ISuspendControlServiceInternal {
        static final int TRANSACTION_enableAutosuspend = 1;
        static final int TRANSACTION_forceSuspend = 2;
        static final int TRANSACTION_getSuspendStats = 5;
        static final int TRANSACTION_getWakeLockStats = 3;
        static final int TRANSACTION_getWakeupStats = 4;

        public Stub() {
            attachInterface(this, android.system.suspend.internal.ISuspendControlServiceInternal.DESCRIPTOR);
        }

        public static android.system.suspend.internal.ISuspendControlServiceInternal asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.system.suspend.internal.ISuspendControlServiceInternal.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.system.suspend.internal.ISuspendControlServiceInternal)) {
                return (android.system.suspend.internal.ISuspendControlServiceInternal) queryLocalInterface;
            }
            return new android.system.suspend.internal.ISuspendControlServiceInternal.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.system.suspend.internal.ISuspendControlServiceInternal.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.system.suspend.internal.ISuspendControlServiceInternal.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean enableAutosuspend = enableAutosuspend(parcel.readStrongBinder());
                    parcel2.writeNoException();
                    parcel2.writeInt(enableAutosuspend ? 1 : 0);
                    return true;
                case 2:
                    boolean forceSuspend = forceSuspend();
                    parcel2.writeNoException();
                    parcel2.writeInt(forceSuspend ? 1 : 0);
                    return true;
                case 3:
                    android.system.suspend.internal.WakeLockInfo[] wakeLockStats = getWakeLockStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(wakeLockStats, 1);
                    return true;
                case 4:
                    android.system.suspend.internal.WakeupInfo[] wakeupStats = getWakeupStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(wakeupStats, 1);
                    return true;
                case 5:
                    android.system.suspend.internal.SuspendInfo suspendStats = getSuspendStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(suspendStats, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.system.suspend.internal.ISuspendControlServiceInternal {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.system.suspend.internal.ISuspendControlServiceInternal.DESCRIPTOR;
            }

            @Override // android.system.suspend.internal.ISuspendControlServiceInternal
            public boolean enableAutosuspend(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.system.suspend.internal.ISuspendControlServiceInternal.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.suspend.internal.ISuspendControlServiceInternal
            public boolean forceSuspend() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.system.suspend.internal.ISuspendControlServiceInternal.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.suspend.internal.ISuspendControlServiceInternal
            public android.system.suspend.internal.WakeLockInfo[] getWakeLockStats() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.system.suspend.internal.ISuspendControlServiceInternal.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.system.suspend.internal.WakeLockInfo[]) obtain2.createTypedArray(android.system.suspend.internal.WakeLockInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.suspend.internal.ISuspendControlServiceInternal
            public android.system.suspend.internal.WakeupInfo[] getWakeupStats() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.system.suspend.internal.ISuspendControlServiceInternal.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.system.suspend.internal.WakeupInfo[]) obtain2.createTypedArray(android.system.suspend.internal.WakeupInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.suspend.internal.ISuspendControlServiceInternal
            public android.system.suspend.internal.SuspendInfo getSuspendStats() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.system.suspend.internal.ISuspendControlServiceInternal.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.system.suspend.internal.SuspendInfo) obtain2.readTypedObject(android.system.suspend.internal.SuspendInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
