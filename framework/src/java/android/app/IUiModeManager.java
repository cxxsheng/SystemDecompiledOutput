package android.app;

/* loaded from: classes.dex */
public interface IUiModeManager extends android.os.IInterface {
    void addCallback(android.app.IUiModeManagerCallback iUiModeManagerCallback) throws android.os.RemoteException;

    void addOnProjectionStateChangedListener(android.app.IOnProjectionStateChangedListener iOnProjectionStateChangedListener, int i) throws android.os.RemoteException;

    void disableCarMode(int i) throws android.os.RemoteException;

    void disableCarModeByCallingPackage(int i, java.lang.String str) throws android.os.RemoteException;

    void enableCarMode(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    int getActiveProjectionTypes() throws android.os.RemoteException;

    int getAttentionModeThemeOverlay() throws android.os.RemoteException;

    float getContrast() throws android.os.RemoteException;

    int getCurrentModeType() throws android.os.RemoteException;

    long getCustomNightModeEnd() throws android.os.RemoteException;

    long getCustomNightModeStart() throws android.os.RemoteException;

    int getNightMode() throws android.os.RemoteException;

    int getNightModeCustomType() throws android.os.RemoteException;

    java.util.List<java.lang.String> getProjectingPackages(int i) throws android.os.RemoteException;

    boolean isNightModeLocked() throws android.os.RemoteException;

    boolean isUiModeLocked() throws android.os.RemoteException;

    boolean releaseProjection(int i, java.lang.String str) throws android.os.RemoteException;

    void removeOnProjectionStateChangedListener(android.app.IOnProjectionStateChangedListener iOnProjectionStateChangedListener) throws android.os.RemoteException;

    boolean requestProjection(android.os.IBinder iBinder, int i, java.lang.String str) throws android.os.RemoteException;

    void setApplicationNightMode(int i) throws android.os.RemoteException;

    void setAttentionModeThemeOverlay(int i) throws android.os.RemoteException;

    void setCustomNightModeEnd(long j) throws android.os.RemoteException;

    void setCustomNightModeStart(long j) throws android.os.RemoteException;

    void setNightMode(int i) throws android.os.RemoteException;

    boolean setNightModeActivated(boolean z) throws android.os.RemoteException;

    boolean setNightModeActivatedForCustomMode(int i, boolean z) throws android.os.RemoteException;

    void setNightModeCustomType(int i) throws android.os.RemoteException;

    public static class Default implements android.app.IUiModeManager {
        @Override // android.app.IUiModeManager
        public void addCallback(android.app.IUiModeManagerCallback iUiModeManagerCallback) throws android.os.RemoteException {
        }

        @Override // android.app.IUiModeManager
        public void enableCarMode(int i, int i2, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.IUiModeManager
        public void disableCarMode(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IUiModeManager
        public void disableCarModeByCallingPackage(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.IUiModeManager
        public int getCurrentModeType() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IUiModeManager
        public void setNightMode(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IUiModeManager
        public int getNightMode() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IUiModeManager
        public void setNightModeCustomType(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IUiModeManager
        public int getNightModeCustomType() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IUiModeManager
        public void setAttentionModeThemeOverlay(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IUiModeManager
        public int getAttentionModeThemeOverlay() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IUiModeManager
        public void setApplicationNightMode(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IUiModeManager
        public boolean isUiModeLocked() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IUiModeManager
        public boolean isNightModeLocked() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IUiModeManager
        public boolean setNightModeActivatedForCustomMode(int i, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IUiModeManager
        public boolean setNightModeActivated(boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IUiModeManager
        public long getCustomNightModeStart() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.IUiModeManager
        public void setCustomNightModeStart(long j) throws android.os.RemoteException {
        }

        @Override // android.app.IUiModeManager
        public long getCustomNightModeEnd() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.IUiModeManager
        public void setCustomNightModeEnd(long j) throws android.os.RemoteException {
        }

        @Override // android.app.IUiModeManager
        public boolean requestProjection(android.os.IBinder iBinder, int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IUiModeManager
        public boolean releaseProjection(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IUiModeManager
        public void addOnProjectionStateChangedListener(android.app.IOnProjectionStateChangedListener iOnProjectionStateChangedListener, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IUiModeManager
        public void removeOnProjectionStateChangedListener(android.app.IOnProjectionStateChangedListener iOnProjectionStateChangedListener) throws android.os.RemoteException {
        }

        @Override // android.app.IUiModeManager
        public java.util.List<java.lang.String> getProjectingPackages(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IUiModeManager
        public int getActiveProjectionTypes() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IUiModeManager
        public float getContrast() throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IUiModeManager {
        public static final java.lang.String DESCRIPTOR = "android.app.IUiModeManager";
        static final int TRANSACTION_addCallback = 1;
        static final int TRANSACTION_addOnProjectionStateChangedListener = 23;
        static final int TRANSACTION_disableCarMode = 3;
        static final int TRANSACTION_disableCarModeByCallingPackage = 4;
        static final int TRANSACTION_enableCarMode = 2;
        static final int TRANSACTION_getActiveProjectionTypes = 26;
        static final int TRANSACTION_getAttentionModeThemeOverlay = 11;
        static final int TRANSACTION_getContrast = 27;
        static final int TRANSACTION_getCurrentModeType = 5;
        static final int TRANSACTION_getCustomNightModeEnd = 19;
        static final int TRANSACTION_getCustomNightModeStart = 17;
        static final int TRANSACTION_getNightMode = 7;
        static final int TRANSACTION_getNightModeCustomType = 9;
        static final int TRANSACTION_getProjectingPackages = 25;
        static final int TRANSACTION_isNightModeLocked = 14;
        static final int TRANSACTION_isUiModeLocked = 13;
        static final int TRANSACTION_releaseProjection = 22;
        static final int TRANSACTION_removeOnProjectionStateChangedListener = 24;
        static final int TRANSACTION_requestProjection = 21;
        static final int TRANSACTION_setApplicationNightMode = 12;
        static final int TRANSACTION_setAttentionModeThemeOverlay = 10;
        static final int TRANSACTION_setCustomNightModeEnd = 20;
        static final int TRANSACTION_setCustomNightModeStart = 18;
        static final int TRANSACTION_setNightMode = 6;
        static final int TRANSACTION_setNightModeActivated = 16;
        static final int TRANSACTION_setNightModeActivatedForCustomMode = 15;
        static final int TRANSACTION_setNightModeCustomType = 8;
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

        public static android.app.IUiModeManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IUiModeManager)) {
                return (android.app.IUiModeManager) queryLocalInterface;
            }
            return new android.app.IUiModeManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addCallback";
                case 2:
                    return "enableCarMode";
                case 3:
                    return "disableCarMode";
                case 4:
                    return "disableCarModeByCallingPackage";
                case 5:
                    return "getCurrentModeType";
                case 6:
                    return "setNightMode";
                case 7:
                    return "getNightMode";
                case 8:
                    return "setNightModeCustomType";
                case 9:
                    return "getNightModeCustomType";
                case 10:
                    return "setAttentionModeThemeOverlay";
                case 11:
                    return "getAttentionModeThemeOverlay";
                case 12:
                    return "setApplicationNightMode";
                case 13:
                    return "isUiModeLocked";
                case 14:
                    return "isNightModeLocked";
                case 15:
                    return "setNightModeActivatedForCustomMode";
                case 16:
                    return "setNightModeActivated";
                case 17:
                    return "getCustomNightModeStart";
                case 18:
                    return "setCustomNightModeStart";
                case 19:
                    return "getCustomNightModeEnd";
                case 20:
                    return "setCustomNightModeEnd";
                case 21:
                    return "requestProjection";
                case 22:
                    return "releaseProjection";
                case 23:
                    return "addOnProjectionStateChangedListener";
                case 24:
                    return "removeOnProjectionStateChangedListener";
                case 25:
                    return "getProjectingPackages";
                case 26:
                    return "getActiveProjectionTypes";
                case 27:
                    return "getContrast";
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
                    android.app.IUiModeManagerCallback asInterface = android.app.IUiModeManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enableCarMode(readInt, readInt2, readString);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableCarMode(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disableCarModeByCallingPackage(readInt4, readString2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int currentModeType = getCurrentModeType();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentModeType);
                    return true;
                case 6:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNightMode(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int nightMode = getNightMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(nightMode);
                    return true;
                case 8:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNightModeCustomType(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int nightModeCustomType = getNightModeCustomType();
                    parcel2.writeNoException();
                    parcel2.writeInt(nightModeCustomType);
                    return true;
                case 10:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAttentionModeThemeOverlay(readInt7);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int attentionModeThemeOverlay = getAttentionModeThemeOverlay();
                    parcel2.writeNoException();
                    parcel2.writeInt(attentionModeThemeOverlay);
                    return true;
                case 12:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setApplicationNightMode(readInt8);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    boolean isUiModeLocked = isUiModeLocked();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUiModeLocked);
                    return true;
                case 14:
                    boolean isNightModeLocked = isNightModeLocked();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNightModeLocked);
                    return true;
                case 15:
                    int readInt9 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean nightModeActivatedForCustomMode = setNightModeActivatedForCustomMode(readInt9, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightModeActivatedForCustomMode);
                    return true;
                case 16:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean nightModeActivated = setNightModeActivated(readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightModeActivated);
                    return true;
                case 17:
                    long customNightModeStart = getCustomNightModeStart();
                    parcel2.writeNoException();
                    parcel2.writeLong(customNightModeStart);
                    return true;
                case 18:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setCustomNightModeStart(readLong);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    long customNightModeEnd = getCustomNightModeEnd();
                    parcel2.writeNoException();
                    parcel2.writeLong(customNightModeEnd);
                    return true;
                case 20:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setCustomNightModeEnd(readLong2);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt10 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean requestProjection = requestProjection(readStrongBinder, readInt10, readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestProjection);
                    return true;
                case 22:
                    int readInt11 = parcel.readInt();
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean releaseProjection = releaseProjection(readInt11, readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(releaseProjection);
                    return true;
                case 23:
                    android.app.IOnProjectionStateChangedListener asInterface2 = android.app.IOnProjectionStateChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addOnProjectionStateChangedListener(asInterface2, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    android.app.IOnProjectionStateChangedListener asInterface3 = android.app.IOnProjectionStateChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnProjectionStateChangedListener(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> projectingPackages = getProjectingPackages(readInt13);
                    parcel2.writeNoException();
                    parcel2.writeStringList(projectingPackages);
                    return true;
                case 26:
                    int activeProjectionTypes = getActiveProjectionTypes();
                    parcel2.writeNoException();
                    parcel2.writeInt(activeProjectionTypes);
                    return true;
                case 27:
                    float contrast = getContrast();
                    parcel2.writeNoException();
                    parcel2.writeFloat(contrast);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IUiModeManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IUiModeManager.Stub.DESCRIPTOR;
            }

            @Override // android.app.IUiModeManager
            public void addCallback(android.app.IUiModeManagerCallback iUiModeManagerCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUiModeManagerCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void enableCarMode(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void disableCarMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void disableCarModeByCallingPackage(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public int getCurrentModeType() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setNightMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public int getNightMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setNightModeCustomType(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public int getNightModeCustomType() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setAttentionModeThemeOverlay(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public int getAttentionModeThemeOverlay() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setApplicationNightMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public boolean isUiModeLocked() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public boolean isNightModeLocked() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public boolean setNightModeActivatedForCustomMode(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public boolean setNightModeActivated(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public long getCustomNightModeStart() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setCustomNightModeStart(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public long getCustomNightModeEnd() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setCustomNightModeEnd(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public boolean requestProjection(android.os.IBinder iBinder, int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public boolean releaseProjection(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void addOnProjectionStateChangedListener(android.app.IOnProjectionStateChangedListener iOnProjectionStateChangedListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnProjectionStateChangedListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void removeOnProjectionStateChangedListener(android.app.IOnProjectionStateChangedListener iOnProjectionStateChangedListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnProjectionStateChangedListener);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public java.util.List<java.lang.String> getProjectingPackages(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public int getActiveProjectionTypes() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public float getContrast() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUiModeManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void setNightModeCustomType_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DAY_NIGHT_MODE, getCallingPid(), getCallingUid());
        }

        protected void getNightModeCustomType_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DAY_NIGHT_MODE, getCallingPid(), getCallingUid());
        }

        protected void setAttentionModeThemeOverlay_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DAY_NIGHT_MODE, getCallingPid(), getCallingUid());
        }

        protected void getAttentionModeThemeOverlay_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DAY_NIGHT_MODE, getCallingPid(), getCallingUid());
        }

        protected void addOnProjectionStateChangedListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.READ_PROJECTION_STATE, getCallingPid(), getCallingUid());
        }

        protected void removeOnProjectionStateChangedListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.READ_PROJECTION_STATE, getCallingPid(), getCallingUid());
        }

        protected void getProjectingPackages_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.READ_PROJECTION_STATE, getCallingPid(), getCallingUid());
        }

        protected void getActiveProjectionTypes_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.READ_PROJECTION_STATE, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 26;
        }
    }
}
