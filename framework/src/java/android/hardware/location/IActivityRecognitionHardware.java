package android.hardware.location;

/* loaded from: classes2.dex */
public interface IActivityRecognitionHardware extends android.os.IInterface {
    boolean disableActivityEvent(java.lang.String str, int i) throws android.os.RemoteException;

    boolean enableActivityEvent(java.lang.String str, int i, long j) throws android.os.RemoteException;

    boolean flush() throws android.os.RemoteException;

    java.lang.String[] getSupportedActivities() throws android.os.RemoteException;

    boolean isActivitySupported(java.lang.String str) throws android.os.RemoteException;

    boolean registerSink(android.hardware.location.IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) throws android.os.RemoteException;

    boolean unregisterSink(android.hardware.location.IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) throws android.os.RemoteException;

    public static class Default implements android.hardware.location.IActivityRecognitionHardware {
        @Override // android.hardware.location.IActivityRecognitionHardware
        public java.lang.String[] getSupportedActivities() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.location.IActivityRecognitionHardware
        public boolean isActivitySupported(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.location.IActivityRecognitionHardware
        public boolean registerSink(android.hardware.location.IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.location.IActivityRecognitionHardware
        public boolean unregisterSink(android.hardware.location.IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.location.IActivityRecognitionHardware
        public boolean enableActivityEvent(java.lang.String str, int i, long j) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.location.IActivityRecognitionHardware
        public boolean disableActivityEvent(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.location.IActivityRecognitionHardware
        public boolean flush() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.location.IActivityRecognitionHardware {
        public static final java.lang.String DESCRIPTOR = "android.hardware.location.IActivityRecognitionHardware";
        static final int TRANSACTION_disableActivityEvent = 6;
        static final int TRANSACTION_enableActivityEvent = 5;
        static final int TRANSACTION_flush = 7;
        static final int TRANSACTION_getSupportedActivities = 1;
        static final int TRANSACTION_isActivitySupported = 2;
        static final int TRANSACTION_registerSink = 3;
        static final int TRANSACTION_unregisterSink = 4;
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

        public static android.hardware.location.IActivityRecognitionHardware asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.location.IActivityRecognitionHardware)) {
                return (android.hardware.location.IActivityRecognitionHardware) queryLocalInterface;
            }
            return new android.hardware.location.IActivityRecognitionHardware.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getSupportedActivities";
                case 2:
                    return "isActivitySupported";
                case 3:
                    return "registerSink";
                case 4:
                    return "unregisterSink";
                case 5:
                    return "enableActivityEvent";
                case 6:
                    return "disableActivityEvent";
                case 7:
                    return "flush";
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
                    java.lang.String[] supportedActivities = getSupportedActivities();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(supportedActivities);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isActivitySupported = isActivitySupported(readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isActivitySupported);
                    return true;
                case 3:
                    android.hardware.location.IActivityRecognitionHardwareSink asInterface = android.hardware.location.IActivityRecognitionHardwareSink.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerSink = registerSink(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerSink);
                    return true;
                case 4:
                    android.hardware.location.IActivityRecognitionHardwareSink asInterface2 = android.hardware.location.IActivityRecognitionHardwareSink.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterSink = unregisterSink(asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterSink);
                    return true;
                case 5:
                    java.lang.String readString2 = parcel.readString();
                    int readInt = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean enableActivityEvent = enableActivityEvent(readString2, readInt, readLong);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableActivityEvent);
                    return true;
                case 6:
                    java.lang.String readString3 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean disableActivityEvent = disableActivityEvent(readString3, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disableActivityEvent);
                    return true;
                case 7:
                    boolean flush = flush();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(flush);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.location.IActivityRecognitionHardware {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.location.IActivityRecognitionHardware.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.location.IActivityRecognitionHardware
            public java.lang.String[] getSupportedActivities() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IActivityRecognitionHardware.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IActivityRecognitionHardware
            public boolean isActivitySupported(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IActivityRecognitionHardware.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IActivityRecognitionHardware
            public boolean registerSink(android.hardware.location.IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IActivityRecognitionHardware.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iActivityRecognitionHardwareSink);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IActivityRecognitionHardware
            public boolean unregisterSink(android.hardware.location.IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IActivityRecognitionHardware.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iActivityRecognitionHardwareSink);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IActivityRecognitionHardware
            public boolean enableActivityEvent(java.lang.String str, int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IActivityRecognitionHardware.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IActivityRecognitionHardware
            public boolean disableActivityEvent(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IActivityRecognitionHardware.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IActivityRecognitionHardware
            public boolean flush() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IActivityRecognitionHardware.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void getSupportedActivities_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }

        protected void isActivitySupported_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }

        protected void registerSink_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }

        protected void unregisterSink_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }

        protected void enableActivityEvent_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }

        protected void disableActivityEvent_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }

        protected void flush_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
