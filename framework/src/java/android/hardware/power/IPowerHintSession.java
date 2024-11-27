package android.hardware.power;

/* loaded from: classes2.dex */
public interface IPowerHintSession extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$power$IPowerHintSession".replace('$', '.');
    public static final java.lang.String HASH = "d111735ed2b89b6c32443aac9b162b1afbbea3f2";
    public static final int VERSION = 5;

    void close() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    android.hardware.power.SessionConfig getSessionConfig() throws android.os.RemoteException;

    void pause() throws android.os.RemoteException;

    void reportActualWorkDuration(android.hardware.power.WorkDuration[] workDurationArr) throws android.os.RemoteException;

    void resume() throws android.os.RemoteException;

    void sendHint(int i) throws android.os.RemoteException;

    void setMode(int i, boolean z) throws android.os.RemoteException;

    void setThreads(int[] iArr) throws android.os.RemoteException;

    void updateTargetWorkDuration(long j) throws android.os.RemoteException;

    public static class Default implements android.hardware.power.IPowerHintSession {
        @Override // android.hardware.power.IPowerHintSession
        public void updateTargetWorkDuration(long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.power.IPowerHintSession
        public void reportActualWorkDuration(android.hardware.power.WorkDuration[] workDurationArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.power.IPowerHintSession
        public void pause() throws android.os.RemoteException {
        }

        @Override // android.hardware.power.IPowerHintSession
        public void resume() throws android.os.RemoteException {
        }

        @Override // android.hardware.power.IPowerHintSession
        public void close() throws android.os.RemoteException {
        }

        @Override // android.hardware.power.IPowerHintSession
        public void sendHint(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.power.IPowerHintSession
        public void setThreads(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.power.IPowerHintSession
        public void setMode(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.power.IPowerHintSession
        public android.hardware.power.SessionConfig getSessionConfig() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.power.IPowerHintSession
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.power.IPowerHintSession
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.power.IPowerHintSession {
        static final int TRANSACTION_close = 5;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getSessionConfig = 9;
        static final int TRANSACTION_pause = 3;
        static final int TRANSACTION_reportActualWorkDuration = 2;
        static final int TRANSACTION_resume = 4;
        static final int TRANSACTION_sendHint = 6;
        static final int TRANSACTION_setMode = 8;
        static final int TRANSACTION_setThreads = 7;
        static final int TRANSACTION_updateTargetWorkDuration = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.power.IPowerHintSession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.power.IPowerHintSession)) {
                return (android.hardware.power.IPowerHintSession) queryLocalInterface;
            }
            return new android.hardware.power.IPowerHintSession.Stub.Proxy(iBinder);
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
                    return android.media.tv.interactive.TvInteractiveAppService.TIME_SHIFT_COMMAND_TYPE_PAUSE;
                case 4:
                    return android.media.tv.interactive.TvInteractiveAppService.TIME_SHIFT_COMMAND_TYPE_RESUME;
                case 5:
                    return "close";
                case 6:
                    return "sendHint";
                case 7:
                    return "setThreads";
                case 8:
                    return "setMode";
                case 9:
                    return "getSessionConfig";
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
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
            java.lang.String str = DESCRIPTOR;
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
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    updateTargetWorkDuration(readLong);
                    return true;
                case 2:
                    android.hardware.power.WorkDuration[] workDurationArr = (android.hardware.power.WorkDuration[]) parcel.createTypedArray(android.hardware.power.WorkDuration.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportActualWorkDuration(workDurationArr);
                    return true;
                case 3:
                    pause();
                    return true;
                case 4:
                    resume();
                    return true;
                case 5:
                    close();
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendHint(readInt);
                    return true;
                case 7:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setThreads(createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMode(readInt2, readBoolean);
                    return true;
                case 9:
                    android.hardware.power.SessionConfig sessionConfig = getSessionConfig();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sessionConfig, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.power.IPowerHintSession {
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
                return DESCRIPTOR;
            }

            @Override // android.hardware.power.IPowerHintSession
            public void updateTargetWorkDuration(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method updateTargetWorkDuration is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPowerHintSession
            public void reportActualWorkDuration(android.hardware.power.WorkDuration[] workDurationArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedArray(workDurationArr, 0);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method reportActualWorkDuration is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPowerHintSession
            public void pause() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method pause is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPowerHintSession
            public void resume() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method resume is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPowerHintSession
            public void close() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method close is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPowerHintSession
            public void sendHint(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendHint is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPowerHintSession
            public void setThreads(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setThreads is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPowerHintSession
            public void setMode(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setMode is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPowerHintSession
            public android.hardware.power.SessionConfig getSessionConfig() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getSessionConfig is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.power.SessionConfig) obtain2.readTypedObject(android.hardware.power.SessionConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPowerHintSession
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
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

            @Override // android.hardware.power.IPowerHintSession
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
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
                return this.mCachedHash;
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }
    }
}
