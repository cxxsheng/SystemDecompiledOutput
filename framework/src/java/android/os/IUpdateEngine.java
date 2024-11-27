package android.os;

/* loaded from: classes3.dex */
public interface IUpdateEngine extends android.os.IInterface {
    long allocateSpaceForPayload(java.lang.String str, java.lang.String[] strArr) throws android.os.RemoteException;

    void applyPayload(java.lang.String str, long j, long j2, java.lang.String[] strArr) throws android.os.RemoteException;

    void applyPayloadFd(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, long j2, java.lang.String[] strArr) throws android.os.RemoteException;

    boolean bind(android.os.IUpdateEngineCallback iUpdateEngineCallback) throws android.os.RemoteException;

    void cancel() throws android.os.RemoteException;

    void cleanupSuccessfulUpdate(android.os.IUpdateEngineCallback iUpdateEngineCallback) throws android.os.RemoteException;

    void resetShouldSwitchSlotOnReboot() throws android.os.RemoteException;

    void resetStatus() throws android.os.RemoteException;

    void resume() throws android.os.RemoteException;

    void setPerformanceMode(boolean z) throws android.os.RemoteException;

    void setShouldSwitchSlotOnReboot(java.lang.String str) throws android.os.RemoteException;

    void suspend() throws android.os.RemoteException;

    boolean unbind(android.os.IUpdateEngineCallback iUpdateEngineCallback) throws android.os.RemoteException;

    boolean verifyPayloadApplicable(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.os.IUpdateEngine {
        @Override // android.os.IUpdateEngine
        public void applyPayload(java.lang.String str, long j, long j2, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.os.IUpdateEngine
        public void applyPayloadFd(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, long j2, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.os.IUpdateEngine
        public boolean bind(android.os.IUpdateEngineCallback iUpdateEngineCallback) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUpdateEngine
        public boolean unbind(android.os.IUpdateEngineCallback iUpdateEngineCallback) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUpdateEngine
        public void suspend() throws android.os.RemoteException {
        }

        @Override // android.os.IUpdateEngine
        public void resume() throws android.os.RemoteException {
        }

        @Override // android.os.IUpdateEngine
        public void cancel() throws android.os.RemoteException {
        }

        @Override // android.os.IUpdateEngine
        public void resetStatus() throws android.os.RemoteException {
        }

        @Override // android.os.IUpdateEngine
        public void setShouldSwitchSlotOnReboot(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IUpdateEngine
        public void resetShouldSwitchSlotOnReboot() throws android.os.RemoteException {
        }

        @Override // android.os.IUpdateEngine
        public boolean verifyPayloadApplicable(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUpdateEngine
        public long allocateSpaceForPayload(java.lang.String str, java.lang.String[] strArr) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.os.IUpdateEngine
        public void cleanupSuccessfulUpdate(android.os.IUpdateEngineCallback iUpdateEngineCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IUpdateEngine
        public void setPerformanceMode(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IUpdateEngine {
        public static final java.lang.String DESCRIPTOR = "android.os.IUpdateEngine";
        static final int TRANSACTION_allocateSpaceForPayload = 12;
        static final int TRANSACTION_applyPayload = 1;
        static final int TRANSACTION_applyPayloadFd = 2;
        static final int TRANSACTION_bind = 3;
        static final int TRANSACTION_cancel = 7;
        static final int TRANSACTION_cleanupSuccessfulUpdate = 13;
        static final int TRANSACTION_resetShouldSwitchSlotOnReboot = 10;
        static final int TRANSACTION_resetStatus = 8;
        static final int TRANSACTION_resume = 6;
        static final int TRANSACTION_setPerformanceMode = 14;
        static final int TRANSACTION_setShouldSwitchSlotOnReboot = 9;
        static final int TRANSACTION_suspend = 5;
        static final int TRANSACTION_unbind = 4;
        static final int TRANSACTION_verifyPayloadApplicable = 11;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.os.IUpdateEngine asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IUpdateEngine)) {
                return (android.os.IUpdateEngine) queryLocalInterface;
            }
            return new android.os.IUpdateEngine.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "applyPayload";
                case 2:
                    return "applyPayloadFd";
                case 3:
                    return "bind";
                case 4:
                    return "unbind";
                case 5:
                    return "suspend";
                case 6:
                    return android.media.tv.interactive.TvInteractiveAppService.TIME_SHIFT_COMMAND_TYPE_RESUME;
                case 7:
                    return "cancel";
                case 8:
                    return "resetStatus";
                case 9:
                    return "setShouldSwitchSlotOnReboot";
                case 10:
                    return "resetShouldSwitchSlotOnReboot";
                case 11:
                    return "verifyPayloadApplicable";
                case 12:
                    return "allocateSpaceForPayload";
                case 13:
                    return "cleanupSuccessfulUpdate";
                case 14:
                    return "setPerformanceMode";
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
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    applyPayload(readString, readLong, readLong2, createStringArray);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    long readLong3 = parcel.readLong();
                    long readLong4 = parcel.readLong();
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    applyPayloadFd(parcelFileDescriptor, readLong3, readLong4, createStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.os.IUpdateEngineCallback asInterface = android.os.IUpdateEngineCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean bind = bind(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bind);
                    return true;
                case 4:
                    android.os.IUpdateEngineCallback asInterface2 = android.os.IUpdateEngineCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unbind = unbind(asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unbind);
                    return true;
                case 5:
                    suspend();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    resume();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    cancel();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    resetStatus();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setShouldSwitchSlotOnReboot(readString2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    resetShouldSwitchSlotOnReboot();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean verifyPayloadApplicable = verifyPayloadApplicable(readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(verifyPayloadApplicable);
                    return true;
                case 12:
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String[] createStringArray3 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    long allocateSpaceForPayload = allocateSpaceForPayload(readString4, createStringArray3);
                    parcel2.writeNoException();
                    parcel2.writeLong(allocateSpaceForPayload);
                    return true;
                case 13:
                    android.os.IUpdateEngineCallback asInterface3 = android.os.IUpdateEngineCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cleanupSuccessfulUpdate(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPerformanceMode(readBoolean);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IUpdateEngine {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IUpdateEngine.Stub.DESCRIPTOR;
            }

            @Override // android.os.IUpdateEngine
            public void applyPayload(java.lang.String str, long j, long j2, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUpdateEngine.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void applyPayloadFd(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, long j2, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUpdateEngine.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public boolean bind(android.os.IUpdateEngineCallback iUpdateEngineCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUpdateEngine.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUpdateEngineCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public boolean unbind(android.os.IUpdateEngineCallback iUpdateEngineCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUpdateEngine.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUpdateEngineCallback);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void suspend() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUpdateEngine.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void resume() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUpdateEngine.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void cancel() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUpdateEngine.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void resetStatus() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUpdateEngine.Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void setShouldSwitchSlotOnReboot(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUpdateEngine.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void resetShouldSwitchSlotOnReboot() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUpdateEngine.Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public boolean verifyPayloadApplicable(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUpdateEngine.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public long allocateSpaceForPayload(java.lang.String str, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUpdateEngine.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void cleanupSuccessfulUpdate(android.os.IUpdateEngineCallback iUpdateEngineCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUpdateEngine.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUpdateEngineCallback);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngine
            public void setPerformanceMode(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUpdateEngine.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }
    }
}
