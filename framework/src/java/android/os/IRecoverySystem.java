package android.os;

/* loaded from: classes3.dex */
public interface IRecoverySystem extends android.os.IInterface {
    boolean allocateSpaceForUpdate(java.lang.String str) throws android.os.RemoteException;

    boolean clearBcb() throws android.os.RemoteException;

    boolean clearLskf(java.lang.String str) throws android.os.RemoteException;

    boolean isLskfCaptured(java.lang.String str) throws android.os.RemoteException;

    void rebootRecoveryWithCommand(java.lang.String str) throws android.os.RemoteException;

    int rebootWithLskf(java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException;

    int rebootWithLskfAssumeSlotSwitch(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean requestLskf(java.lang.String str, android.content.IntentSender intentSender) throws android.os.RemoteException;

    boolean setupBcb(java.lang.String str) throws android.os.RemoteException;

    boolean uncrypt(java.lang.String str, android.os.IRecoverySystemProgressListener iRecoverySystemProgressListener) throws android.os.RemoteException;

    public static class Default implements android.os.IRecoverySystem {
        @Override // android.os.IRecoverySystem
        public boolean allocateSpaceForUpdate(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IRecoverySystem
        public boolean uncrypt(java.lang.String str, android.os.IRecoverySystemProgressListener iRecoverySystemProgressListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IRecoverySystem
        public boolean setupBcb(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IRecoverySystem
        public boolean clearBcb() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IRecoverySystem
        public void rebootRecoveryWithCommand(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IRecoverySystem
        public boolean requestLskf(java.lang.String str, android.content.IntentSender intentSender) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IRecoverySystem
        public boolean clearLskf(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IRecoverySystem
        public boolean isLskfCaptured(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IRecoverySystem
        public int rebootWithLskfAssumeSlotSwitch(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IRecoverySystem
        public int rebootWithLskf(java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IRecoverySystem {
        public static final java.lang.String DESCRIPTOR = "android.os.IRecoverySystem";
        static final int TRANSACTION_allocateSpaceForUpdate = 1;
        static final int TRANSACTION_clearBcb = 4;
        static final int TRANSACTION_clearLskf = 7;
        static final int TRANSACTION_isLskfCaptured = 8;
        static final int TRANSACTION_rebootRecoveryWithCommand = 5;
        static final int TRANSACTION_rebootWithLskf = 10;
        static final int TRANSACTION_rebootWithLskfAssumeSlotSwitch = 9;
        static final int TRANSACTION_requestLskf = 6;
        static final int TRANSACTION_setupBcb = 3;
        static final int TRANSACTION_uncrypt = 2;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.os.IRecoverySystem asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IRecoverySystem)) {
                return (android.os.IRecoverySystem) queryLocalInterface;
            }
            return new android.os.IRecoverySystem.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "allocateSpaceForUpdate";
                case 2:
                    return "uncrypt";
                case 3:
                    return "setupBcb";
                case 4:
                    return "clearBcb";
                case 5:
                    return "rebootRecoveryWithCommand";
                case 6:
                    return "requestLskf";
                case 7:
                    return "clearLskf";
                case 8:
                    return "isLskfCaptured";
                case 9:
                    return "rebootWithLskfAssumeSlotSwitch";
                case 10:
                    return "rebootWithLskf";
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
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean allocateSpaceForUpdate = allocateSpaceForUpdate(readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allocateSpaceForUpdate);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    android.os.IRecoverySystemProgressListener asInterface = android.os.IRecoverySystemProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean uncrypt = uncrypt(readString2, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(uncrypt);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean z = setupBcb(readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(z);
                    return true;
                case 4:
                    boolean clearBcb = clearBcb();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearBcb);
                    return true;
                case 5:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    rebootRecoveryWithCommand(readString4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    java.lang.String readString5 = parcel.readString();
                    android.content.IntentSender intentSender = (android.content.IntentSender) parcel.readTypedObject(android.content.IntentSender.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean requestLskf = requestLskf(readString5, intentSender);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestLskf);
                    return true;
                case 7:
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean clearLskf = clearLskf(readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearLskf);
                    return true;
                case 8:
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isLskfCaptured = isLskfCaptured(readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLskfCaptured);
                    return true;
                case 9:
                    java.lang.String readString8 = parcel.readString();
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int rebootWithLskfAssumeSlotSwitch = rebootWithLskfAssumeSlotSwitch(readString8, readString9);
                    parcel2.writeNoException();
                    parcel2.writeInt(rebootWithLskfAssumeSlotSwitch);
                    return true;
                case 10:
                    java.lang.String readString10 = parcel.readString();
                    java.lang.String readString11 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int rebootWithLskf = rebootWithLskf(readString10, readString11, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(rebootWithLskf);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IRecoverySystem {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IRecoverySystem.Stub.DESCRIPTOR;
            }

            @Override // android.os.IRecoverySystem
            public boolean allocateSpaceForUpdate(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IRecoverySystem.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public boolean uncrypt(java.lang.String str, android.os.IRecoverySystemProgressListener iRecoverySystemProgressListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IRecoverySystem.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iRecoverySystemProgressListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public boolean setupBcb(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IRecoverySystem.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public boolean clearBcb() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IRecoverySystem.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public void rebootRecoveryWithCommand(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IRecoverySystem.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public boolean requestLskf(java.lang.String str, android.content.IntentSender intentSender) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IRecoverySystem.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intentSender, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public boolean clearLskf(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IRecoverySystem.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public boolean isLskfCaptured(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IRecoverySystem.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public int rebootWithLskfAssumeSlotSwitch(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IRecoverySystem.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public int rebootWithLskf(java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IRecoverySystem.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void allocateSpaceForUpdate_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.RECOVERY, getCallingPid(), getCallingUid());
        }

        protected void rebootWithLskfAssumeSlotSwitch_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.RECOVERY, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }
    }
}
