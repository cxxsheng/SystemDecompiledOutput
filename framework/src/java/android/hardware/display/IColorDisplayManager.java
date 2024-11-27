package android.hardware.display;

/* loaded from: classes2.dex */
public interface IColorDisplayManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.display.IColorDisplayManager";

    int getColorMode() throws android.os.RemoteException;

    int getNightDisplayAutoMode() throws android.os.RemoteException;

    int getNightDisplayAutoModeRaw() throws android.os.RemoteException;

    int getNightDisplayColorTemperature() throws android.os.RemoteException;

    android.hardware.display.Time getNightDisplayCustomEndTime() throws android.os.RemoteException;

    android.hardware.display.Time getNightDisplayCustomStartTime() throws android.os.RemoteException;

    float getReduceBrightColorsOffsetFactor() throws android.os.RemoteException;

    int getReduceBrightColorsStrength() throws android.os.RemoteException;

    int getTransformCapabilities() throws android.os.RemoteException;

    boolean isDeviceColorManaged() throws android.os.RemoteException;

    boolean isDisplayWhiteBalanceEnabled() throws android.os.RemoteException;

    boolean isNightDisplayActivated() throws android.os.RemoteException;

    boolean isReduceBrightColorsActivated() throws android.os.RemoteException;

    boolean isSaturationActivated() throws android.os.RemoteException;

    boolean setAppSaturationLevel(java.lang.String str, int i) throws android.os.RemoteException;

    void setColorMode(int i) throws android.os.RemoteException;

    boolean setDisplayWhiteBalanceEnabled(boolean z) throws android.os.RemoteException;

    boolean setNightDisplayActivated(boolean z) throws android.os.RemoteException;

    boolean setNightDisplayAutoMode(int i) throws android.os.RemoteException;

    boolean setNightDisplayColorTemperature(int i) throws android.os.RemoteException;

    boolean setNightDisplayCustomEndTime(android.hardware.display.Time time) throws android.os.RemoteException;

    boolean setNightDisplayCustomStartTime(android.hardware.display.Time time) throws android.os.RemoteException;

    boolean setReduceBrightColorsActivated(boolean z) throws android.os.RemoteException;

    boolean setReduceBrightColorsStrength(int i) throws android.os.RemoteException;

    boolean setSaturationLevel(int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.display.IColorDisplayManager {
        @Override // android.hardware.display.IColorDisplayManager
        public boolean isDeviceColorManaged() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean setSaturationLevel(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean setAppSaturationLevel(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean isSaturationActivated() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public int getTransformCapabilities() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean isNightDisplayActivated() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean setNightDisplayActivated(boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public int getNightDisplayColorTemperature() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean setNightDisplayColorTemperature(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public int getNightDisplayAutoMode() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public int getNightDisplayAutoModeRaw() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean setNightDisplayAutoMode(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public android.hardware.display.Time getNightDisplayCustomStartTime() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean setNightDisplayCustomStartTime(android.hardware.display.Time time) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public android.hardware.display.Time getNightDisplayCustomEndTime() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean setNightDisplayCustomEndTime(android.hardware.display.Time time) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public int getColorMode() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public void setColorMode(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean isDisplayWhiteBalanceEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean setDisplayWhiteBalanceEnabled(boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean isReduceBrightColorsActivated() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean setReduceBrightColorsActivated(boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public int getReduceBrightColorsStrength() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean setReduceBrightColorsStrength(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public float getReduceBrightColorsOffsetFactor() throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.display.IColorDisplayManager {
        static final int TRANSACTION_getColorMode = 17;
        static final int TRANSACTION_getNightDisplayAutoMode = 10;
        static final int TRANSACTION_getNightDisplayAutoModeRaw = 11;
        static final int TRANSACTION_getNightDisplayColorTemperature = 8;
        static final int TRANSACTION_getNightDisplayCustomEndTime = 15;
        static final int TRANSACTION_getNightDisplayCustomStartTime = 13;
        static final int TRANSACTION_getReduceBrightColorsOffsetFactor = 25;
        static final int TRANSACTION_getReduceBrightColorsStrength = 23;
        static final int TRANSACTION_getTransformCapabilities = 5;
        static final int TRANSACTION_isDeviceColorManaged = 1;
        static final int TRANSACTION_isDisplayWhiteBalanceEnabled = 19;
        static final int TRANSACTION_isNightDisplayActivated = 6;
        static final int TRANSACTION_isReduceBrightColorsActivated = 21;
        static final int TRANSACTION_isSaturationActivated = 4;
        static final int TRANSACTION_setAppSaturationLevel = 3;
        static final int TRANSACTION_setColorMode = 18;
        static final int TRANSACTION_setDisplayWhiteBalanceEnabled = 20;
        static final int TRANSACTION_setNightDisplayActivated = 7;
        static final int TRANSACTION_setNightDisplayAutoMode = 12;
        static final int TRANSACTION_setNightDisplayColorTemperature = 9;
        static final int TRANSACTION_setNightDisplayCustomEndTime = 16;
        static final int TRANSACTION_setNightDisplayCustomStartTime = 14;
        static final int TRANSACTION_setReduceBrightColorsActivated = 22;
        static final int TRANSACTION_setReduceBrightColorsStrength = 24;
        static final int TRANSACTION_setSaturationLevel = 2;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, android.hardware.display.IColorDisplayManager.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.hardware.display.IColorDisplayManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.display.IColorDisplayManager)) {
                return (android.hardware.display.IColorDisplayManager) queryLocalInterface;
            }
            return new android.hardware.display.IColorDisplayManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isDeviceColorManaged";
                case 2:
                    return "setSaturationLevel";
                case 3:
                    return "setAppSaturationLevel";
                case 4:
                    return "isSaturationActivated";
                case 5:
                    return "getTransformCapabilities";
                case 6:
                    return "isNightDisplayActivated";
                case 7:
                    return "setNightDisplayActivated";
                case 8:
                    return "getNightDisplayColorTemperature";
                case 9:
                    return "setNightDisplayColorTemperature";
                case 10:
                    return "getNightDisplayAutoMode";
                case 11:
                    return "getNightDisplayAutoModeRaw";
                case 12:
                    return "setNightDisplayAutoMode";
                case 13:
                    return "getNightDisplayCustomStartTime";
                case 14:
                    return "setNightDisplayCustomStartTime";
                case 15:
                    return "getNightDisplayCustomEndTime";
                case 16:
                    return "setNightDisplayCustomEndTime";
                case 17:
                    return "getColorMode";
                case 18:
                    return "setColorMode";
                case 19:
                    return "isDisplayWhiteBalanceEnabled";
                case 20:
                    return "setDisplayWhiteBalanceEnabled";
                case 21:
                    return "isReduceBrightColorsActivated";
                case 22:
                    return "setReduceBrightColorsActivated";
                case 23:
                    return "getReduceBrightColorsStrength";
                case 24:
                    return "setReduceBrightColorsStrength";
                case 25:
                    return "getReduceBrightColorsOffsetFactor";
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
                parcel.enforceInterface(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean isDeviceColorManaged = isDeviceColorManaged();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceColorManaged);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean saturationLevel = setSaturationLevel(readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(saturationLevel);
                    return true;
                case 3:
                    java.lang.String readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean appSaturationLevel = setAppSaturationLevel(readString, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appSaturationLevel);
                    return true;
                case 4:
                    boolean isSaturationActivated = isSaturationActivated();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSaturationActivated);
                    return true;
                case 5:
                    int transformCapabilities = getTransformCapabilities();
                    parcel2.writeNoException();
                    parcel2.writeInt(transformCapabilities);
                    return true;
                case 6:
                    boolean isNightDisplayActivated = isNightDisplayActivated();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNightDisplayActivated);
                    return true;
                case 7:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean nightDisplayActivated = setNightDisplayActivated(readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightDisplayActivated);
                    return true;
                case 8:
                    int nightDisplayColorTemperature = getNightDisplayColorTemperature();
                    parcel2.writeNoException();
                    parcel2.writeInt(nightDisplayColorTemperature);
                    return true;
                case 9:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean nightDisplayColorTemperature2 = setNightDisplayColorTemperature(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightDisplayColorTemperature2);
                    return true;
                case 10:
                    int nightDisplayAutoMode = getNightDisplayAutoMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(nightDisplayAutoMode);
                    return true;
                case 11:
                    int nightDisplayAutoModeRaw = getNightDisplayAutoModeRaw();
                    parcel2.writeNoException();
                    parcel2.writeInt(nightDisplayAutoModeRaw);
                    return true;
                case 12:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean nightDisplayAutoMode2 = setNightDisplayAutoMode(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightDisplayAutoMode2);
                    return true;
                case 13:
                    android.hardware.display.Time nightDisplayCustomStartTime = getNightDisplayCustomStartTime();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(nightDisplayCustomStartTime, 1);
                    return true;
                case 14:
                    android.hardware.display.Time time = (android.hardware.display.Time) parcel.readTypedObject(android.hardware.display.Time.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean nightDisplayCustomStartTime2 = setNightDisplayCustomStartTime(time);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightDisplayCustomStartTime2);
                    return true;
                case 15:
                    android.hardware.display.Time nightDisplayCustomEndTime = getNightDisplayCustomEndTime();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(nightDisplayCustomEndTime, 1);
                    return true;
                case 16:
                    android.hardware.display.Time time2 = (android.hardware.display.Time) parcel.readTypedObject(android.hardware.display.Time.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean nightDisplayCustomEndTime2 = setNightDisplayCustomEndTime(time2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightDisplayCustomEndTime2);
                    return true;
                case 17:
                    int colorMode = getColorMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(colorMode);
                    return true;
                case 18:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setColorMode(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    boolean isDisplayWhiteBalanceEnabled = isDisplayWhiteBalanceEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDisplayWhiteBalanceEnabled);
                    return true;
                case 20:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean displayWhiteBalanceEnabled = setDisplayWhiteBalanceEnabled(readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(displayWhiteBalanceEnabled);
                    return true;
                case 21:
                    boolean isReduceBrightColorsActivated = isReduceBrightColorsActivated();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isReduceBrightColorsActivated);
                    return true;
                case 22:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean reduceBrightColorsActivated = setReduceBrightColorsActivated(readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(reduceBrightColorsActivated);
                    return true;
                case 23:
                    int reduceBrightColorsStrength = getReduceBrightColorsStrength();
                    parcel2.writeNoException();
                    parcel2.writeInt(reduceBrightColorsStrength);
                    return true;
                case 24:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean reduceBrightColorsStrength2 = setReduceBrightColorsStrength(readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(reduceBrightColorsStrength2);
                    return true;
                case 25:
                    float reduceBrightColorsOffsetFactor = getReduceBrightColorsOffsetFactor();
                    parcel2.writeNoException();
                    parcel2.writeFloat(reduceBrightColorsOffsetFactor);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.display.IColorDisplayManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.display.IColorDisplayManager.DESCRIPTOR;
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean isDeviceColorManaged() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setSaturationLevel(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setAppSaturationLevel(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean isSaturationActivated() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public int getTransformCapabilities() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean isNightDisplayActivated() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setNightDisplayActivated(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public int getNightDisplayColorTemperature() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setNightDisplayColorTemperature(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public int getNightDisplayAutoMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public int getNightDisplayAutoModeRaw() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setNightDisplayAutoMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public android.hardware.display.Time getNightDisplayCustomStartTime() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.display.Time) obtain2.readTypedObject(android.hardware.display.Time.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setNightDisplayCustomStartTime(android.hardware.display.Time time) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    obtain.writeTypedObject(time, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public android.hardware.display.Time getNightDisplayCustomEndTime() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.display.Time) obtain2.readTypedObject(android.hardware.display.Time.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setNightDisplayCustomEndTime(android.hardware.display.Time time) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    obtain.writeTypedObject(time, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public int getColorMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public void setColorMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean isDisplayWhiteBalanceEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setDisplayWhiteBalanceEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean isReduceBrightColorsActivated() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setReduceBrightColorsActivated(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public int getReduceBrightColorsStrength() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setReduceBrightColorsStrength(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public float getReduceBrightColorsOffsetFactor() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void setAppSaturationLevel_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void isSaturationActivated_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void getTransformCapabilities_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void setNightDisplayActivated_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void setNightDisplayColorTemperature_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void getNightDisplayAutoMode_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void setNightDisplayAutoMode_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void setNightDisplayCustomStartTime_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void setNightDisplayCustomEndTime_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void setColorMode_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void setDisplayWhiteBalanceEnabled_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void setReduceBrightColorsActivated_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void setReduceBrightColorsStrength_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 24;
        }
    }
}
