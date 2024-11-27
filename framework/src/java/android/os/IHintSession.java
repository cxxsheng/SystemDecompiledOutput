package android.os;

/* loaded from: classes3.dex */
public interface IHintSession extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.IHintSession";

    void close() throws android.os.RemoteException;

    void reportActualWorkDuration(long[] jArr, long[] jArr2) throws android.os.RemoteException;

    void reportActualWorkDuration2(android.os.WorkDuration[] workDurationArr) throws android.os.RemoteException;

    void sendHint(int i) throws android.os.RemoteException;

    void setMode(int i, boolean z) throws android.os.RemoteException;

    void updateTargetWorkDuration(long j) throws android.os.RemoteException;

    public static class Default implements android.os.IHintSession {
        @Override // android.os.IHintSession
        public void updateTargetWorkDuration(long j) throws android.os.RemoteException {
        }

        @Override // android.os.IHintSession
        public void reportActualWorkDuration(long[] jArr, long[] jArr2) throws android.os.RemoteException {
        }

        @Override // android.os.IHintSession
        public void close() throws android.os.RemoteException {
        }

        @Override // android.os.IHintSession
        public void sendHint(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IHintSession
        public void setMode(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IHintSession
        public void reportActualWorkDuration2(android.os.WorkDuration[] workDurationArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IHintSession {
        static final int TRANSACTION_close = 3;
        static final int TRANSACTION_reportActualWorkDuration = 2;
        static final int TRANSACTION_reportActualWorkDuration2 = 6;
        static final int TRANSACTION_sendHint = 4;
        static final int TRANSACTION_setMode = 5;
        static final int TRANSACTION_updateTargetWorkDuration = 1;

        public Stub() {
            attachInterface(this, android.os.IHintSession.DESCRIPTOR);
        }

        public static android.os.IHintSession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.IHintSession.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IHintSession)) {
                return (android.os.IHintSession) queryLocalInterface;
            }
            return new android.os.IHintSession.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "updateTargetWorkDuration";
                case 2:
                    return "reportActualWorkDuration";
                case 3:
                    return "close";
                case 4:
                    return "sendHint";
                case 5:
                    return "setMode";
                case 6:
                    return "reportActualWorkDuration2";
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
                parcel.enforceInterface(android.os.IHintSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.IHintSession.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    updateTargetWorkDuration(readLong);
                    return true;
                case 2:
                    long[] createLongArray = parcel.createLongArray();
                    long[] createLongArray2 = parcel.createLongArray();
                    parcel.enforceNoDataAvail();
                    reportActualWorkDuration(createLongArray, createLongArray2);
                    return true;
                case 3:
                    close();
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendHint(readInt);
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMode(readInt2, readBoolean);
                    return true;
                case 6:
                    android.os.WorkDuration[] workDurationArr = (android.os.WorkDuration[]) parcel.createTypedArray(android.os.WorkDuration.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportActualWorkDuration2(workDurationArr);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IHintSession {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IHintSession.DESCRIPTOR;
            }

            @Override // android.os.IHintSession
            public void updateTargetWorkDuration(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IHintSession.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IHintSession
            public void reportActualWorkDuration(long[] jArr, long[] jArr2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IHintSession.DESCRIPTOR);
                    obtain.writeLongArray(jArr);
                    obtain.writeLongArray(jArr2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IHintSession
            public void close() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IHintSession.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IHintSession
            public void sendHint(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IHintSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IHintSession
            public void setMode(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IHintSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IHintSession
            public void reportActualWorkDuration2(android.os.WorkDuration[] workDurationArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IHintSession.DESCRIPTOR);
                    obtain.writeTypedArray(workDurationArr, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
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
