package android.service.trust;

/* loaded from: classes3.dex */
public interface ITrustAgentService extends android.os.IInterface {
    void onConfigure(java.util.List<android.os.PersistableBundle> list, android.os.IBinder iBinder) throws android.os.RemoteException;

    void onDeviceLocked() throws android.os.RemoteException;

    void onDeviceUnlocked() throws android.os.RemoteException;

    void onEscrowTokenAdded(byte[] bArr, long j, android.os.UserHandle userHandle) throws android.os.RemoteException;

    void onEscrowTokenRemoved(long j, boolean z) throws android.os.RemoteException;

    void onTokenStateReceived(long j, int i) throws android.os.RemoteException;

    void onTrustTimeout() throws android.os.RemoteException;

    void onUnlockAttempt(boolean z) throws android.os.RemoteException;

    void onUnlockLockout(int i) throws android.os.RemoteException;

    void onUserMayRequestUnlock() throws android.os.RemoteException;

    void onUserRequestedUnlock(boolean z) throws android.os.RemoteException;

    void setCallback(android.service.trust.ITrustAgentServiceCallback iTrustAgentServiceCallback) throws android.os.RemoteException;

    public static class Default implements android.service.trust.ITrustAgentService {
        @Override // android.service.trust.ITrustAgentService
        public void onUnlockAttempt(boolean z) throws android.os.RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void onUserRequestedUnlock(boolean z) throws android.os.RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void onUserMayRequestUnlock() throws android.os.RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void onUnlockLockout(int i) throws android.os.RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void onTrustTimeout() throws android.os.RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void onDeviceLocked() throws android.os.RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void onDeviceUnlocked() throws android.os.RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void onConfigure(java.util.List<android.os.PersistableBundle> list, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void setCallback(android.service.trust.ITrustAgentServiceCallback iTrustAgentServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void onEscrowTokenAdded(byte[] bArr, long j, android.os.UserHandle userHandle) throws android.os.RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void onTokenStateReceived(long j, int i) throws android.os.RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void onEscrowTokenRemoved(long j, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.trust.ITrustAgentService {
        public static final java.lang.String DESCRIPTOR = "android.service.trust.ITrustAgentService";
        static final int TRANSACTION_onConfigure = 8;
        static final int TRANSACTION_onDeviceLocked = 6;
        static final int TRANSACTION_onDeviceUnlocked = 7;
        static final int TRANSACTION_onEscrowTokenAdded = 10;
        static final int TRANSACTION_onEscrowTokenRemoved = 12;
        static final int TRANSACTION_onTokenStateReceived = 11;
        static final int TRANSACTION_onTrustTimeout = 5;
        static final int TRANSACTION_onUnlockAttempt = 1;
        static final int TRANSACTION_onUnlockLockout = 4;
        static final int TRANSACTION_onUserMayRequestUnlock = 3;
        static final int TRANSACTION_onUserRequestedUnlock = 2;
        static final int TRANSACTION_setCallback = 9;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.trust.ITrustAgentService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.trust.ITrustAgentService)) {
                return (android.service.trust.ITrustAgentService) queryLocalInterface;
            }
            return new android.service.trust.ITrustAgentService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onUnlockAttempt";
                case 2:
                    return "onUserRequestedUnlock";
                case 3:
                    return "onUserMayRequestUnlock";
                case 4:
                    return "onUnlockLockout";
                case 5:
                    return "onTrustTimeout";
                case 6:
                    return "onDeviceLocked";
                case 7:
                    return "onDeviceUnlocked";
                case 8:
                    return "onConfigure";
                case 9:
                    return "setCallback";
                case 10:
                    return "onEscrowTokenAdded";
                case 11:
                    return "onTokenStateReceived";
                case 12:
                    return "onEscrowTokenRemoved";
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
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onUnlockAttempt(readBoolean);
                    return true;
                case 2:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onUserRequestedUnlock(readBoolean2);
                    return true;
                case 3:
                    onUserMayRequestUnlock();
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUnlockLockout(readInt);
                    return true;
                case 5:
                    onTrustTimeout();
                    return true;
                case 6:
                    onDeviceLocked();
                    return true;
                case 7:
                    onDeviceUnlocked();
                    return true;
                case 8:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.os.PersistableBundle.CREATOR);
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onConfigure(createTypedArrayList, readStrongBinder);
                    return true;
                case 9:
                    android.service.trust.ITrustAgentServiceCallback asInterface = android.service.trust.ITrustAgentServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(asInterface);
                    return true;
                case 10:
                    byte[] createByteArray = parcel.createByteArray();
                    long readLong = parcel.readLong();
                    android.os.UserHandle userHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onEscrowTokenAdded(createByteArray, readLong, userHandle);
                    return true;
                case 11:
                    long readLong2 = parcel.readLong();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTokenStateReceived(readLong2, readInt2);
                    return true;
                case 12:
                    long readLong3 = parcel.readLong();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onEscrowTokenRemoved(readLong3, readBoolean3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.trust.ITrustAgentService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.trust.ITrustAgentService.Stub.DESCRIPTOR;
            }

            @Override // android.service.trust.ITrustAgentService
            public void onUnlockAttempt(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void onUserRequestedUnlock(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void onUserMayRequestUnlock() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentService.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void onUnlockLockout(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void onTrustTimeout() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentService.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void onDeviceLocked() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentService.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void onDeviceUnlocked() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentService.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void onConfigure(java.util.List<android.os.PersistableBundle> list, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentService.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void setCallback(android.service.trust.ITrustAgentServiceCallback iTrustAgentServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTrustAgentServiceCallback);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void onEscrowTokenAdded(byte[] bArr, long j, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentService.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void onTokenStateReceived(long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentService.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void onEscrowTokenRemoved(long j, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentService.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }
    }
}
