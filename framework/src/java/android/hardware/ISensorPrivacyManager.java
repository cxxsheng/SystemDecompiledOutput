package android.hardware;

/* loaded from: classes.dex */
public interface ISensorPrivacyManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.ISensorPrivacyManager";

    void addSensorPrivacyListener(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) throws android.os.RemoteException;

    void addToggleSensorPrivacyListener(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) throws android.os.RemoteException;

    java.util.List<android.hardware.CameraPrivacyAllowlistEntry> getCameraPrivacyAllowlist() throws android.os.RemoteException;

    int getToggleSensorPrivacyState(int i, int i2) throws android.os.RemoteException;

    boolean isCameraPrivacyEnabled(java.lang.String str) throws android.os.RemoteException;

    boolean isCombinedToggleSensorPrivacyEnabled(int i) throws android.os.RemoteException;

    boolean isSensorPrivacyEnabled() throws android.os.RemoteException;

    boolean isToggleSensorPrivacyEnabled(int i, int i2) throws android.os.RemoteException;

    void removeSensorPrivacyListener(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) throws android.os.RemoteException;

    void removeToggleSensorPrivacyListener(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) throws android.os.RemoteException;

    boolean requiresAuthentication() throws android.os.RemoteException;

    void setSensorPrivacy(boolean z) throws android.os.RemoteException;

    void setToggleSensorPrivacy(int i, int i2, int i3, boolean z) throws android.os.RemoteException;

    void setToggleSensorPrivacyForProfileGroup(int i, int i2, int i3, boolean z) throws android.os.RemoteException;

    void setToggleSensorPrivacyState(int i, int i2, int i3, int i4) throws android.os.RemoteException;

    void setToggleSensorPrivacyStateForProfileGroup(int i, int i2, int i3, int i4) throws android.os.RemoteException;

    void showSensorUseDialog(int i) throws android.os.RemoteException;

    boolean supportsSensorToggle(int i, int i2) throws android.os.RemoteException;

    void suppressToggleSensorPrivacyReminders(int i, int i2, android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    public static class Default implements android.hardware.ISensorPrivacyManager {
        @Override // android.hardware.ISensorPrivacyManager
        public boolean supportsSensorToggle(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void addSensorPrivacyListener(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void addToggleSensorPrivacyListener(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void removeSensorPrivacyListener(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void removeToggleSensorPrivacyListener(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyManager
        public boolean isSensorPrivacyEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public boolean isCombinedToggleSensorPrivacyEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public boolean isToggleSensorPrivacyEnabled(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void setSensorPrivacy(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void setToggleSensorPrivacy(int i, int i2, int i3, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void setToggleSensorPrivacyForProfileGroup(int i, int i2, int i3, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyManager
        public java.util.List<android.hardware.CameraPrivacyAllowlistEntry> getCameraPrivacyAllowlist() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public int getToggleSensorPrivacyState(int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void setToggleSensorPrivacyState(int i, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void setToggleSensorPrivacyStateForProfileGroup(int i, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyManager
        public boolean isCameraPrivacyEnabled(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void suppressToggleSensorPrivacyReminders(int i, int i2, android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyManager
        public boolean requiresAuthentication() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void showSensorUseDialog(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.ISensorPrivacyManager {
        static final int TRANSACTION_addSensorPrivacyListener = 2;
        static final int TRANSACTION_addToggleSensorPrivacyListener = 3;
        static final int TRANSACTION_getCameraPrivacyAllowlist = 12;
        static final int TRANSACTION_getToggleSensorPrivacyState = 13;
        static final int TRANSACTION_isCameraPrivacyEnabled = 16;
        static final int TRANSACTION_isCombinedToggleSensorPrivacyEnabled = 7;
        static final int TRANSACTION_isSensorPrivacyEnabled = 6;
        static final int TRANSACTION_isToggleSensorPrivacyEnabled = 8;
        static final int TRANSACTION_removeSensorPrivacyListener = 4;
        static final int TRANSACTION_removeToggleSensorPrivacyListener = 5;
        static final int TRANSACTION_requiresAuthentication = 18;
        static final int TRANSACTION_setSensorPrivacy = 9;
        static final int TRANSACTION_setToggleSensorPrivacy = 10;
        static final int TRANSACTION_setToggleSensorPrivacyForProfileGroup = 11;
        static final int TRANSACTION_setToggleSensorPrivacyState = 14;
        static final int TRANSACTION_setToggleSensorPrivacyStateForProfileGroup = 15;
        static final int TRANSACTION_showSensorUseDialog = 19;
        static final int TRANSACTION_supportsSensorToggle = 1;
        static final int TRANSACTION_suppressToggleSensorPrivacyReminders = 17;

        public Stub() {
            attachInterface(this, android.hardware.ISensorPrivacyManager.DESCRIPTOR);
        }

        public static android.hardware.ISensorPrivacyManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.ISensorPrivacyManager)) {
                return (android.hardware.ISensorPrivacyManager) queryLocalInterface;
            }
            return new android.hardware.ISensorPrivacyManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "supportsSensorToggle";
                case 2:
                    return "addSensorPrivacyListener";
                case 3:
                    return "addToggleSensorPrivacyListener";
                case 4:
                    return "removeSensorPrivacyListener";
                case 5:
                    return "removeToggleSensorPrivacyListener";
                case 6:
                    return "isSensorPrivacyEnabled";
                case 7:
                    return "isCombinedToggleSensorPrivacyEnabled";
                case 8:
                    return "isToggleSensorPrivacyEnabled";
                case 9:
                    return "setSensorPrivacy";
                case 10:
                    return "setToggleSensorPrivacy";
                case 11:
                    return "setToggleSensorPrivacyForProfileGroup";
                case 12:
                    return "getCameraPrivacyAllowlist";
                case 13:
                    return "getToggleSensorPrivacyState";
                case 14:
                    return "setToggleSensorPrivacyState";
                case 15:
                    return "setToggleSensorPrivacyStateForProfileGroup";
                case 16:
                    return "isCameraPrivacyEnabled";
                case 17:
                    return "suppressToggleSensorPrivacyReminders";
                case 18:
                    return "requiresAuthentication";
                case 19:
                    return "showSensorUseDialog";
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
                parcel.enforceInterface(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean supportsSensorToggle = supportsSensorToggle(readInt, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportsSensorToggle);
                    return true;
                case 2:
                    android.hardware.ISensorPrivacyListener asInterface = android.hardware.ISensorPrivacyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addSensorPrivacyListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.hardware.ISensorPrivacyListener asInterface2 = android.hardware.ISensorPrivacyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addToggleSensorPrivacyListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.hardware.ISensorPrivacyListener asInterface3 = android.hardware.ISensorPrivacyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeSensorPrivacyListener(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.hardware.ISensorPrivacyListener asInterface4 = android.hardware.ISensorPrivacyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeToggleSensorPrivacyListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    boolean isSensorPrivacyEnabled = isSensorPrivacyEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSensorPrivacyEnabled);
                    return true;
                case 7:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCombinedToggleSensorPrivacyEnabled = isCombinedToggleSensorPrivacyEnabled(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCombinedToggleSensorPrivacyEnabled);
                    return true;
                case 8:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isToggleSensorPrivacyEnabled = isToggleSensorPrivacyEnabled(readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isToggleSensorPrivacyEnabled);
                    return true;
                case 9:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSensorPrivacy(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setToggleSensorPrivacy(readInt6, readInt7, readInt8, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setToggleSensorPrivacyForProfileGroup(readInt9, readInt10, readInt11, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    java.util.List<android.hardware.CameraPrivacyAllowlistEntry> cameraPrivacyAllowlist = getCameraPrivacyAllowlist();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(cameraPrivacyAllowlist, 1);
                    return true;
                case 13:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int toggleSensorPrivacyState = getToggleSensorPrivacyState(readInt12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeInt(toggleSensorPrivacyState);
                    return true;
                case 14:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setToggleSensorPrivacyState(readInt14, readInt15, readInt16, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setToggleSensorPrivacyStateForProfileGroup(readInt18, readInt19, readInt20, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isCameraPrivacyEnabled = isCameraPrivacyEnabled(readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCameraPrivacyEnabled);
                    return true;
                case 17:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    suppressToggleSensorPrivacyReminders(readInt22, readInt23, readStrongBinder, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    boolean requiresAuthentication = requiresAuthentication();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requiresAuthentication);
                    return true;
                case 19:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showSensorUseDialog(readInt24);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.ISensorPrivacyManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.ISensorPrivacyManager.DESCRIPTOR;
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean supportsSensorToggle(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void addSensorPrivacyListener(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iSensorPrivacyListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void addToggleSensorPrivacyListener(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iSensorPrivacyListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void removeSensorPrivacyListener(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iSensorPrivacyListener);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void removeToggleSensorPrivacyListener(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iSensorPrivacyListener);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean isSensorPrivacyEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean isCombinedToggleSensorPrivacyEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean isToggleSensorPrivacyEnabled(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setSensorPrivacy(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setToggleSensorPrivacy(int i, int i2, int i3, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setToggleSensorPrivacyForProfileGroup(int i, int i2, int i3, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public java.util.List<android.hardware.CameraPrivacyAllowlistEntry> getCameraPrivacyAllowlist() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.CameraPrivacyAllowlistEntry.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public int getToggleSensorPrivacyState(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setToggleSensorPrivacyState(int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setToggleSensorPrivacyStateForProfileGroup(int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean isCameraPrivacyEnabled(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void suppressToggleSensorPrivacyReminders(int i, int i2, android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean requiresAuthentication() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void showSensorUseDialog(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 18;
        }
    }
}
