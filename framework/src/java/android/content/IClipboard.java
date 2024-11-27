package android.content;

/* loaded from: classes.dex */
public interface IClipboard extends android.os.IInterface {
    void addPrimaryClipChangedListener(android.content.IOnPrimaryClipChangedListener iOnPrimaryClipChangedListener, java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    boolean areClipboardAccessNotificationsEnabledForUser(int i) throws android.os.RemoteException;

    void clearPrimaryClip(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    android.content.ClipData getPrimaryClip(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    android.content.ClipDescription getPrimaryClipDescription(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    java.lang.String getPrimaryClipSource(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    boolean hasClipboardText(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    boolean hasPrimaryClip(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    void removePrimaryClipChangedListener(android.content.IOnPrimaryClipChangedListener iOnPrimaryClipChangedListener, java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    void setClipboardAccessNotificationsEnabledForUser(boolean z, int i) throws android.os.RemoteException;

    void setPrimaryClip(android.content.ClipData clipData, java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    void setPrimaryClipAsPackage(android.content.ClipData clipData, java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3) throws android.os.RemoteException;

    public static class Default implements android.content.IClipboard {
        @Override // android.content.IClipboard
        public void setPrimaryClip(android.content.ClipData clipData, java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.content.IClipboard
        public void setPrimaryClipAsPackage(android.content.ClipData clipData, java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // android.content.IClipboard
        public void clearPrimaryClip(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.content.IClipboard
        public android.content.ClipData getPrimaryClip(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.IClipboard
        public android.content.ClipDescription getPrimaryClipDescription(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.IClipboard
        public boolean hasPrimaryClip(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.IClipboard
        public void addPrimaryClipChangedListener(android.content.IOnPrimaryClipChangedListener iOnPrimaryClipChangedListener, java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.content.IClipboard
        public void removePrimaryClipChangedListener(android.content.IOnPrimaryClipChangedListener iOnPrimaryClipChangedListener, java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.content.IClipboard
        public boolean hasClipboardText(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.IClipboard
        public java.lang.String getPrimaryClipSource(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.IClipboard
        public boolean areClipboardAccessNotificationsEnabledForUser(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.IClipboard
        public void setClipboardAccessNotificationsEnabledForUser(boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.IClipboard {
        public static final java.lang.String DESCRIPTOR = "android.content.IClipboard";
        static final int TRANSACTION_addPrimaryClipChangedListener = 7;
        static final int TRANSACTION_areClipboardAccessNotificationsEnabledForUser = 11;
        static final int TRANSACTION_clearPrimaryClip = 3;
        static final int TRANSACTION_getPrimaryClip = 4;
        static final int TRANSACTION_getPrimaryClipDescription = 5;
        static final int TRANSACTION_getPrimaryClipSource = 10;
        static final int TRANSACTION_hasClipboardText = 9;
        static final int TRANSACTION_hasPrimaryClip = 6;
        static final int TRANSACTION_removePrimaryClipChangedListener = 8;
        static final int TRANSACTION_setClipboardAccessNotificationsEnabledForUser = 12;
        static final int TRANSACTION_setPrimaryClip = 1;
        static final int TRANSACTION_setPrimaryClipAsPackage = 2;
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

        public static android.content.IClipboard asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.IClipboard)) {
                return (android.content.IClipboard) queryLocalInterface;
            }
            return new android.content.IClipboard.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setPrimaryClip";
                case 2:
                    return "setPrimaryClipAsPackage";
                case 3:
                    return "clearPrimaryClip";
                case 4:
                    return "getPrimaryClip";
                case 5:
                    return "getPrimaryClipDescription";
                case 6:
                    return "hasPrimaryClip";
                case 7:
                    return "addPrimaryClipChangedListener";
                case 8:
                    return "removePrimaryClipChangedListener";
                case 9:
                    return "hasClipboardText";
                case 10:
                    return "getPrimaryClipSource";
                case 11:
                    return "areClipboardAccessNotificationsEnabledForUser";
                case 12:
                    return "setClipboardAccessNotificationsEnabledForUser";
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
                    android.content.ClipData clipData = (android.content.ClipData) parcel.readTypedObject(android.content.ClipData.CREATOR);
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPrimaryClip(clipData, readString, readString2, readInt, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.content.ClipData clipData2 = (android.content.ClipData) parcel.readTypedObject(android.content.ClipData.CREATOR);
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setPrimaryClipAsPackage(clipData2, readString3, readString4, readInt3, readInt4, readString5);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String readString6 = parcel.readString();
                    java.lang.String readString7 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearPrimaryClip(readString6, readString7, readInt5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    java.lang.String readString8 = parcel.readString();
                    java.lang.String readString9 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.ClipData primaryClip = getPrimaryClip(readString8, readString9, readInt7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(primaryClip, 1);
                    return true;
                case 5:
                    java.lang.String readString10 = parcel.readString();
                    java.lang.String readString11 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.ClipDescription primaryClipDescription = getPrimaryClipDescription(readString10, readString11, readInt9, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(primaryClipDescription, 1);
                    return true;
                case 6:
                    java.lang.String readString12 = parcel.readString();
                    java.lang.String readString13 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasPrimaryClip = hasPrimaryClip(readString12, readString13, readInt11, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasPrimaryClip);
                    return true;
                case 7:
                    android.content.IOnPrimaryClipChangedListener asInterface = android.content.IOnPrimaryClipChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString14 = parcel.readString();
                    java.lang.String readString15 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addPrimaryClipChangedListener(asInterface, readString14, readString15, readInt13, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.content.IOnPrimaryClipChangedListener asInterface2 = android.content.IOnPrimaryClipChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString16 = parcel.readString();
                    java.lang.String readString17 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removePrimaryClipChangedListener(asInterface2, readString16, readString17, readInt15, readInt16);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    java.lang.String readString18 = parcel.readString();
                    java.lang.String readString19 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasClipboardText = hasClipboardText(readString18, readString19, readInt17, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasClipboardText);
                    return true;
                case 10:
                    java.lang.String readString20 = parcel.readString();
                    java.lang.String readString21 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String primaryClipSource = getPrimaryClipSource(readString20, readString21, readInt19, readInt20);
                    parcel2.writeNoException();
                    parcel2.writeString(primaryClipSource);
                    return true;
                case 11:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean areClipboardAccessNotificationsEnabledForUser = areClipboardAccessNotificationsEnabledForUser(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(areClipboardAccessNotificationsEnabledForUser);
                    return true;
                case 12:
                    boolean readBoolean = parcel.readBoolean();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setClipboardAccessNotificationsEnabledForUser(readBoolean, readInt22);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.IClipboard {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.IClipboard.Stub.DESCRIPTOR;
            }

            @Override // android.content.IClipboard
            public void setPrimaryClip(android.content.ClipData clipData, java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IClipboard.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(clipData, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public void setPrimaryClipAsPackage(android.content.ClipData clipData, java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IClipboard.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(clipData, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str3);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public void clearPrimaryClip(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IClipboard.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public android.content.ClipData getPrimaryClip(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IClipboard.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ClipData) obtain2.readTypedObject(android.content.ClipData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public android.content.ClipDescription getPrimaryClipDescription(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IClipboard.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ClipDescription) obtain2.readTypedObject(android.content.ClipDescription.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public boolean hasPrimaryClip(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IClipboard.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public void addPrimaryClipChangedListener(android.content.IOnPrimaryClipChangedListener iOnPrimaryClipChangedListener, java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IClipboard.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnPrimaryClipChangedListener);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public void removePrimaryClipChangedListener(android.content.IOnPrimaryClipChangedListener iOnPrimaryClipChangedListener, java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IClipboard.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnPrimaryClipChangedListener);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public boolean hasClipboardText(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IClipboard.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public java.lang.String getPrimaryClipSource(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IClipboard.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public boolean areClipboardAccessNotificationsEnabledForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IClipboard.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public void setClipboardAccessNotificationsEnabledForUser(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IClipboard.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void setPrimaryClipAsPackage_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.SET_CLIP_SOURCE, getCallingPid(), getCallingUid());
        }

        protected void getPrimaryClipSource_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.SET_CLIP_SOURCE, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }
    }
}
