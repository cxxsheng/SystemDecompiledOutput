package android.hardware.devicestate;

/* loaded from: classes2.dex */
public interface IDeviceStateManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.devicestate.IDeviceStateManager";

    void cancelBaseStateOverride() throws android.os.RemoteException;

    void cancelStateRequest() throws android.os.RemoteException;

    android.hardware.devicestate.DeviceStateInfo getDeviceStateInfo() throws android.os.RemoteException;

    void onStateRequestOverlayDismissed(boolean z) throws android.os.RemoteException;

    void registerCallback(android.hardware.devicestate.IDeviceStateManagerCallback iDeviceStateManagerCallback) throws android.os.RemoteException;

    void requestBaseStateOverride(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException;

    void requestState(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.hardware.devicestate.IDeviceStateManager {
        @Override // android.hardware.devicestate.IDeviceStateManager
        public android.hardware.devicestate.DeviceStateInfo getDeviceStateInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.devicestate.IDeviceStateManager
        public void registerCallback(android.hardware.devicestate.IDeviceStateManagerCallback iDeviceStateManagerCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.devicestate.IDeviceStateManager
        public void requestState(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.devicestate.IDeviceStateManager
        public void cancelStateRequest() throws android.os.RemoteException {
        }

        @Override // android.hardware.devicestate.IDeviceStateManager
        public void requestBaseStateOverride(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.devicestate.IDeviceStateManager
        public void cancelBaseStateOverride() throws android.os.RemoteException {
        }

        @Override // android.hardware.devicestate.IDeviceStateManager
        public void onStateRequestOverlayDismissed(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.devicestate.IDeviceStateManager {
        static final int TRANSACTION_cancelBaseStateOverride = 6;
        static final int TRANSACTION_cancelStateRequest = 4;
        static final int TRANSACTION_getDeviceStateInfo = 1;
        static final int TRANSACTION_onStateRequestOverlayDismissed = 7;
        static final int TRANSACTION_registerCallback = 2;
        static final int TRANSACTION_requestBaseStateOverride = 5;
        static final int TRANSACTION_requestState = 3;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, android.hardware.devicestate.IDeviceStateManager.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.hardware.devicestate.IDeviceStateManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.devicestate.IDeviceStateManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.devicestate.IDeviceStateManager)) {
                return (android.hardware.devicestate.IDeviceStateManager) queryLocalInterface;
            }
            return new android.hardware.devicestate.IDeviceStateManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getDeviceStateInfo";
                case 2:
                    return "registerCallback";
                case 3:
                    return "requestState";
                case 4:
                    return "cancelStateRequest";
                case 5:
                    return "requestBaseStateOverride";
                case 6:
                    return "cancelBaseStateOverride";
                case 7:
                    return "onStateRequestOverlayDismissed";
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
                parcel.enforceInterface(android.hardware.devicestate.IDeviceStateManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.devicestate.IDeviceStateManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.hardware.devicestate.DeviceStateInfo deviceStateInfo = getDeviceStateInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(deviceStateInfo, 1);
                    return true;
                case 2:
                    android.hardware.devicestate.IDeviceStateManagerCallback asInterface = android.hardware.devicestate.IDeviceStateManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestState(readStrongBinder, readInt, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    cancelStateRequest();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestBaseStateOverride(readStrongBinder2, readInt3, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    cancelBaseStateOverride();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onStateRequestOverlayDismissed(readBoolean);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.devicestate.IDeviceStateManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.devicestate.IDeviceStateManager.DESCRIPTOR;
            }

            @Override // android.hardware.devicestate.IDeviceStateManager
            public android.hardware.devicestate.DeviceStateInfo getDeviceStateInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.devicestate.IDeviceStateManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.devicestate.DeviceStateInfo) obtain2.readTypedObject(android.hardware.devicestate.DeviceStateInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.devicestate.IDeviceStateManager
            public void registerCallback(android.hardware.devicestate.IDeviceStateManagerCallback iDeviceStateManagerCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.devicestate.IDeviceStateManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iDeviceStateManagerCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.devicestate.IDeviceStateManager
            public void requestState(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.devicestate.IDeviceStateManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.devicestate.IDeviceStateManager
            public void cancelStateRequest() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.devicestate.IDeviceStateManager.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.devicestate.IDeviceStateManager
            public void requestBaseStateOverride(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.devicestate.IDeviceStateManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.devicestate.IDeviceStateManager
            public void cancelBaseStateOverride() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.devicestate.IDeviceStateManager.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.devicestate.IDeviceStateManager
            public void onStateRequestOverlayDismissed(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.devicestate.IDeviceStateManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void onStateRequestOverlayDismissed_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DEVICE_STATE, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
