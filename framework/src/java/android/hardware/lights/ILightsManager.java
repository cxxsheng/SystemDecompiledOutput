package android.hardware.lights;

/* loaded from: classes2.dex */
public interface ILightsManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.lights.ILightsManager";

    void closeSession(android.os.IBinder iBinder) throws android.os.RemoteException;

    android.hardware.lights.LightState getLightState(int i) throws android.os.RemoteException;

    java.util.List<android.hardware.lights.Light> getLights() throws android.os.RemoteException;

    void openSession(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void setLightStates(android.os.IBinder iBinder, int[] iArr, android.hardware.lights.LightState[] lightStateArr) throws android.os.RemoteException;

    public static class Default implements android.hardware.lights.ILightsManager {
        @Override // android.hardware.lights.ILightsManager
        public java.util.List<android.hardware.lights.Light> getLights() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.lights.ILightsManager
        public android.hardware.lights.LightState getLightState(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.lights.ILightsManager
        public void openSession(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.lights.ILightsManager
        public void closeSession(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.hardware.lights.ILightsManager
        public void setLightStates(android.os.IBinder iBinder, int[] iArr, android.hardware.lights.LightState[] lightStateArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.lights.ILightsManager {
        static final int TRANSACTION_closeSession = 4;
        static final int TRANSACTION_getLightState = 2;
        static final int TRANSACTION_getLights = 1;
        static final int TRANSACTION_openSession = 3;
        static final int TRANSACTION_setLightStates = 5;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, android.hardware.lights.ILightsManager.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.hardware.lights.ILightsManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.lights.ILightsManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.lights.ILightsManager)) {
                return (android.hardware.lights.ILightsManager) queryLocalInterface;
            }
            return new android.hardware.lights.ILightsManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getLights";
                case 2:
                    return "getLightState";
                case 3:
                    return "openSession";
                case 4:
                    return "closeSession";
                case 5:
                    return "setLightStates";
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
                parcel.enforceInterface(android.hardware.lights.ILightsManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.lights.ILightsManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.util.List<android.hardware.lights.Light> lights = getLights();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(lights, 1);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.lights.LightState lightState = getLightState(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lightState, 1);
                    return true;
                case 3:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    openSession(readStrongBinder, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    closeSession(readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    int[] createIntArray = parcel.createIntArray();
                    android.hardware.lights.LightState[] lightStateArr = (android.hardware.lights.LightState[]) parcel.createTypedArray(android.hardware.lights.LightState.CREATOR);
                    parcel.enforceNoDataAvail();
                    setLightStates(readStrongBinder3, createIntArray, lightStateArr);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.lights.ILightsManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.lights.ILightsManager.DESCRIPTOR;
            }

            @Override // android.hardware.lights.ILightsManager
            public java.util.List<android.hardware.lights.Light> getLights() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.lights.ILightsManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.lights.Light.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.lights.ILightsManager
            public android.hardware.lights.LightState getLightState(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.lights.ILightsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.lights.LightState) obtain2.readTypedObject(android.hardware.lights.LightState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.lights.ILightsManager
            public void openSession(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.lights.ILightsManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.lights.ILightsManager
            public void closeSession(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.lights.ILightsManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.lights.ILightsManager
            public void setLightStates(android.os.IBinder iBinder, int[] iArr, android.hardware.lights.LightState[] lightStateArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.lights.ILightsManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedArray(lightStateArr, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void getLights_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DEVICE_LIGHTS, getCallingPid(), getCallingUid());
        }

        protected void getLightState_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DEVICE_LIGHTS, getCallingPid(), getCallingUid());
        }

        protected void openSession_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DEVICE_LIGHTS, getCallingPid(), getCallingUid());
        }

        protected void closeSession_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DEVICE_LIGHTS, getCallingPid(), getCallingUid());
        }

        protected void setLightStates_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DEVICE_LIGHTS, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
