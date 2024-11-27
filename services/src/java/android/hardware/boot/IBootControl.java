package android.hardware.boot;

/* loaded from: classes.dex */
public interface IBootControl extends android.os.IInterface {
    public static final int COMMAND_FAILED = -2;
    public static final java.lang.String DESCRIPTOR = "android$hardware$boot$IBootControl".replace('$', '.');
    public static final java.lang.String HASH = "2400346954240a5de495a1debc81429dd012d7b7";
    public static final int INVALID_SLOT = -1;
    public static final int VERSION = 1;

    int getActiveBootSlot() throws android.os.RemoteException;

    int getCurrentSlot() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    int getNumberSlots() throws android.os.RemoteException;

    int getSnapshotMergeStatus() throws android.os.RemoteException;

    java.lang.String getSuffix(int i) throws android.os.RemoteException;

    boolean isSlotBootable(int i) throws android.os.RemoteException;

    boolean isSlotMarkedSuccessful(int i) throws android.os.RemoteException;

    void markBootSuccessful() throws android.os.RemoteException;

    void setActiveBootSlot(int i) throws android.os.RemoteException;

    void setSlotAsUnbootable(int i) throws android.os.RemoteException;

    void setSnapshotMergeStatus(int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.boot.IBootControl {
        @Override // android.hardware.boot.IBootControl
        public int getActiveBootSlot() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.boot.IBootControl
        public int getCurrentSlot() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.boot.IBootControl
        public int getNumberSlots() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.boot.IBootControl
        public int getSnapshotMergeStatus() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.boot.IBootControl
        public java.lang.String getSuffix(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.boot.IBootControl
        public boolean isSlotBootable(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.boot.IBootControl
        public boolean isSlotMarkedSuccessful(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.boot.IBootControl
        public void markBootSuccessful() throws android.os.RemoteException {
        }

        @Override // android.hardware.boot.IBootControl
        public void setActiveBootSlot(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.boot.IBootControl
        public void setSlotAsUnbootable(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.boot.IBootControl
        public void setSnapshotMergeStatus(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.boot.IBootControl
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.boot.IBootControl
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.boot.IBootControl {
        static final int TRANSACTION_getActiveBootSlot = 1;
        static final int TRANSACTION_getCurrentSlot = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getNumberSlots = 3;
        static final int TRANSACTION_getSnapshotMergeStatus = 4;
        static final int TRANSACTION_getSuffix = 5;
        static final int TRANSACTION_isSlotBootable = 6;
        static final int TRANSACTION_isSlotMarkedSuccessful = 7;
        static final int TRANSACTION_markBootSuccessful = 8;
        static final int TRANSACTION_setActiveBootSlot = 9;
        static final int TRANSACTION_setSlotAsUnbootable = 10;
        static final int TRANSACTION_setSnapshotMergeStatus = 11;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.hardware.boot.IBootControl.DESCRIPTOR);
        }

        public static android.hardware.boot.IBootControl asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.boot.IBootControl.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.boot.IBootControl)) {
                return (android.hardware.boot.IBootControl) queryLocalInterface;
            }
            return new android.hardware.boot.IBootControl.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.hardware.boot.IBootControl.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == TRANSACTION_getInterfaceHash) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    int activeBootSlot = getActiveBootSlot();
                    parcel2.writeNoException();
                    parcel2.writeInt(activeBootSlot);
                    return true;
                case 2:
                    int currentSlot = getCurrentSlot();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentSlot);
                    return true;
                case 3:
                    int numberSlots = getNumberSlots();
                    parcel2.writeNoException();
                    parcel2.writeInt(numberSlots);
                    return true;
                case 4:
                    int snapshotMergeStatus = getSnapshotMergeStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(snapshotMergeStatus);
                    return true;
                case 5:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String suffix = getSuffix(readInt);
                    parcel2.writeNoException();
                    parcel2.writeString(suffix);
                    return true;
                case 6:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSlotBootable = isSlotBootable(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSlotBootable);
                    return true;
                case 7:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSlotMarkedSuccessful = isSlotMarkedSuccessful(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSlotMarkedSuccessful);
                    return true;
                case 8:
                    markBootSuccessful();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setActiveBootSlot(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSlotAsUnbootable(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSnapshotMergeStatus(readInt6);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.boot.IBootControl {
            private android.os.IBinder mRemote;
            private int mCachedVersion = -1;
            private java.lang.String mCachedHash = "-1";

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.boot.IBootControl.DESCRIPTOR;
            }

            @Override // android.hardware.boot.IBootControl
            public int getActiveBootSlot() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.boot.IBootControl.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getActiveBootSlot is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.boot.IBootControl
            public int getCurrentSlot() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.boot.IBootControl.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getCurrentSlot is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.boot.IBootControl
            public int getNumberSlots() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.boot.IBootControl.DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getNumberSlots is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.boot.IBootControl
            public int getSnapshotMergeStatus() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.boot.IBootControl.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getSnapshotMergeStatus is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.boot.IBootControl
            public java.lang.String getSuffix(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.boot.IBootControl.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getSuffix is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.boot.IBootControl
            public boolean isSlotBootable(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.boot.IBootControl.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method isSlotBootable is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.boot.IBootControl
            public boolean isSlotMarkedSuccessful(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.boot.IBootControl.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method isSlotMarkedSuccessful is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.boot.IBootControl
            public void markBootSuccessful() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.boot.IBootControl.DESCRIPTOR);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method markBootSuccessful is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.boot.IBootControl
            public void setActiveBootSlot(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.boot.IBootControl.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setActiveBootSlot is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.boot.IBootControl
            public void setSlotAsUnbootable(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.boot.IBootControl.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setSlotAsUnbootable is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.boot.IBootControl
            public void setSnapshotMergeStatus(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.boot.IBootControl.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setSnapshotMergeStatus is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.boot.IBootControl
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.hardware.boot.IBootControl.DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.boot.IBootControl
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.hardware.boot.IBootControl.DESCRIPTOR);
                            this.mRemote.transact(android.hardware.boot.IBootControl.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
                            obtain2.readException();
                            this.mCachedHash = obtain2.readString();
                            obtain2.recycle();
                            obtain.recycle();
                        } catch (java.lang.Throwable th) {
                            obtain2.recycle();
                            obtain.recycle();
                            throw th;
                        }
                    }
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
                return this.mCachedHash;
            }
        }
    }
}
