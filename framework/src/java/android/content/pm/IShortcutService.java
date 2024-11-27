package android.content.pm;

/* loaded from: classes.dex */
public interface IShortcutService extends android.os.IInterface {
    boolean addDynamicShortcuts(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice, int i) throws android.os.RemoteException;

    void applyRestore(byte[] bArr, int i) throws android.os.RemoteException;

    void createShortcutResultIntent(java.lang.String str, android.content.pm.ShortcutInfo shortcutInfo, int i, com.android.internal.infra.AndroidFuture<android.content.Intent> androidFuture) throws android.os.RemoteException;

    void disableShortcuts(java.lang.String str, java.util.List<java.lang.String> list, java.lang.CharSequence charSequence, int i, int i2) throws android.os.RemoteException;

    void enableShortcuts(java.lang.String str, java.util.List<java.lang.String> list, int i) throws android.os.RemoteException;

    byte[] getBackupPayload(int i) throws android.os.RemoteException;

    int getIconMaxDimensions(java.lang.String str, int i) throws android.os.RemoteException;

    int getMaxShortcutCountPerActivity(java.lang.String str, int i) throws android.os.RemoteException;

    long getRateLimitResetTime(java.lang.String str, int i) throws android.os.RemoteException;

    int getRemainingCallCount(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getShareTargets(java.lang.String str, android.content.IntentFilter intentFilter, int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getShortcuts(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    boolean hasShareTargets(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    boolean isRequestPinItemSupported(int i, int i2) throws android.os.RemoteException;

    void onApplicationActive(java.lang.String str, int i) throws android.os.RemoteException;

    void pushDynamicShortcut(java.lang.String str, android.content.pm.ShortcutInfo shortcutInfo, int i) throws android.os.RemoteException;

    void removeAllDynamicShortcuts(java.lang.String str, int i) throws android.os.RemoteException;

    void removeDynamicShortcuts(java.lang.String str, java.util.List<java.lang.String> list, int i) throws android.os.RemoteException;

    void removeLongLivedShortcuts(java.lang.String str, java.util.List<java.lang.String> list, int i) throws android.os.RemoteException;

    void reportShortcutUsed(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    void requestPinShortcut(java.lang.String str, android.content.pm.ShortcutInfo shortcutInfo, android.content.IntentSender intentSender, int i, com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException;

    void resetThrottling() throws android.os.RemoteException;

    boolean setDynamicShortcuts(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice, int i) throws android.os.RemoteException;

    boolean updateShortcuts(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice, int i) throws android.os.RemoteException;

    public static class Default implements android.content.pm.IShortcutService {
        @Override // android.content.pm.IShortcutService
        public boolean setDynamicShortcuts(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IShortcutService
        public boolean addDynamicShortcuts(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IShortcutService
        public void removeDynamicShortcuts(java.lang.String str, java.util.List<java.lang.String> list, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public void removeAllDynamicShortcuts(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public boolean updateShortcuts(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IShortcutService
        public void requestPinShortcut(java.lang.String str, android.content.pm.ShortcutInfo shortcutInfo, android.content.IntentSender intentSender, int i, com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public void createShortcutResultIntent(java.lang.String str, android.content.pm.ShortcutInfo shortcutInfo, int i, com.android.internal.infra.AndroidFuture<android.content.Intent> androidFuture) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public void disableShortcuts(java.lang.String str, java.util.List<java.lang.String> list, java.lang.CharSequence charSequence, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public void enableShortcuts(java.lang.String str, java.util.List<java.lang.String> list, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public int getMaxShortcutCountPerActivity(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IShortcutService
        public int getRemainingCallCount(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IShortcutService
        public long getRateLimitResetTime(java.lang.String str, int i) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.content.pm.IShortcutService
        public int getIconMaxDimensions(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IShortcutService
        public void reportShortcutUsed(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public void resetThrottling() throws android.os.RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public void onApplicationActive(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public byte[] getBackupPayload(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IShortcutService
        public void applyRestore(byte[] bArr, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public boolean isRequestPinItemSupported(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IShortcutService
        public android.content.pm.ParceledListSlice getShareTargets(java.lang.String str, android.content.IntentFilter intentFilter, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IShortcutService
        public boolean hasShareTargets(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IShortcutService
        public void removeLongLivedShortcuts(java.lang.String str, java.util.List<java.lang.String> list, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public android.content.pm.ParceledListSlice getShortcuts(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IShortcutService
        public void pushDynamicShortcut(java.lang.String str, android.content.pm.ShortcutInfo shortcutInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.IShortcutService {
        public static final java.lang.String DESCRIPTOR = "android.content.pm.IShortcutService";
        static final int TRANSACTION_addDynamicShortcuts = 2;
        static final int TRANSACTION_applyRestore = 18;
        static final int TRANSACTION_createShortcutResultIntent = 7;
        static final int TRANSACTION_disableShortcuts = 8;
        static final int TRANSACTION_enableShortcuts = 9;
        static final int TRANSACTION_getBackupPayload = 17;
        static final int TRANSACTION_getIconMaxDimensions = 13;
        static final int TRANSACTION_getMaxShortcutCountPerActivity = 10;
        static final int TRANSACTION_getRateLimitResetTime = 12;
        static final int TRANSACTION_getRemainingCallCount = 11;
        static final int TRANSACTION_getShareTargets = 20;
        static final int TRANSACTION_getShortcuts = 23;
        static final int TRANSACTION_hasShareTargets = 21;
        static final int TRANSACTION_isRequestPinItemSupported = 19;
        static final int TRANSACTION_onApplicationActive = 16;
        static final int TRANSACTION_pushDynamicShortcut = 24;
        static final int TRANSACTION_removeAllDynamicShortcuts = 4;
        static final int TRANSACTION_removeDynamicShortcuts = 3;
        static final int TRANSACTION_removeLongLivedShortcuts = 22;
        static final int TRANSACTION_reportShortcutUsed = 14;
        static final int TRANSACTION_requestPinShortcut = 6;
        static final int TRANSACTION_resetThrottling = 15;
        static final int TRANSACTION_setDynamicShortcuts = 1;
        static final int TRANSACTION_updateShortcuts = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.content.pm.IShortcutService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.IShortcutService)) {
                return (android.content.pm.IShortcutService) queryLocalInterface;
            }
            return new android.content.pm.IShortcutService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setDynamicShortcuts";
                case 2:
                    return "addDynamicShortcuts";
                case 3:
                    return "removeDynamicShortcuts";
                case 4:
                    return "removeAllDynamicShortcuts";
                case 5:
                    return "updateShortcuts";
                case 6:
                    return "requestPinShortcut";
                case 7:
                    return "createShortcutResultIntent";
                case 8:
                    return "disableShortcuts";
                case 9:
                    return "enableShortcuts";
                case 10:
                    return "getMaxShortcutCountPerActivity";
                case 11:
                    return "getRemainingCallCount";
                case 12:
                    return "getRateLimitResetTime";
                case 13:
                    return "getIconMaxDimensions";
                case 14:
                    return "reportShortcutUsed";
                case 15:
                    return "resetThrottling";
                case 16:
                    return "onApplicationActive";
                case 17:
                    return "getBackupPayload";
                case 18:
                    return "applyRestore";
                case 19:
                    return "isRequestPinItemSupported";
                case 20:
                    return "getShareTargets";
                case 21:
                    return "hasShareTargets";
                case 22:
                    return "removeLongLivedShortcuts";
                case 23:
                    return "getShortcuts";
                case 24:
                    return "pushDynamicShortcut";
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
                    android.content.pm.ParceledListSlice parceledListSlice = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean dynamicShortcuts = setDynamicShortcuts(readString, parceledListSlice, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dynamicShortcuts);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    android.content.pm.ParceledListSlice parceledListSlice2 = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean addDynamicShortcuts = addDynamicShortcuts(readString2, parceledListSlice2, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addDynamicShortcuts);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeDynamicShortcuts(readString3, createStringArrayList, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeAllDynamicShortcuts(readString4, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    java.lang.String readString5 = parcel.readString();
                    android.content.pm.ParceledListSlice parceledListSlice3 = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean updateShortcuts = updateShortcuts(readString5, parceledListSlice3, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateShortcuts);
                    return true;
                case 6:
                    java.lang.String readString6 = parcel.readString();
                    android.content.pm.ShortcutInfo shortcutInfo = (android.content.pm.ShortcutInfo) parcel.readTypedObject(android.content.pm.ShortcutInfo.CREATOR);
                    android.content.IntentSender intentSender = (android.content.IntentSender) parcel.readTypedObject(android.content.IntentSender.CREATOR);
                    int readInt6 = parcel.readInt();
                    com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestPinShortcut(readString6, shortcutInfo, intentSender, readInt6, androidFuture);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.lang.String readString7 = parcel.readString();
                    android.content.pm.ShortcutInfo shortcutInfo2 = (android.content.pm.ShortcutInfo) parcel.readTypedObject(android.content.pm.ShortcutInfo.CREATOR);
                    int readInt7 = parcel.readInt();
                    com.android.internal.infra.AndroidFuture<android.content.Intent> androidFuture2 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    createShortcutResultIntent(readString7, shortcutInfo2, readInt7, androidFuture2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    java.lang.String readString8 = parcel.readString();
                    java.util.ArrayList<java.lang.String> createStringArrayList2 = parcel.createStringArrayList();
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableShortcuts(readString8, createStringArrayList2, charSequence, readInt8, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    java.lang.String readString9 = parcel.readString();
                    java.util.ArrayList<java.lang.String> createStringArrayList3 = parcel.createStringArrayList();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableShortcuts(readString9, createStringArrayList3, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    java.lang.String readString10 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int maxShortcutCountPerActivity = getMaxShortcutCountPerActivity(readString10, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxShortcutCountPerActivity);
                    return true;
                case 11:
                    java.lang.String readString11 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int remainingCallCount = getRemainingCallCount(readString11, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeInt(remainingCallCount);
                    return true;
                case 12:
                    java.lang.String readString12 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long rateLimitResetTime = getRateLimitResetTime(readString12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeLong(rateLimitResetTime);
                    return true;
                case 13:
                    java.lang.String readString13 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iconMaxDimensions = getIconMaxDimensions(readString13, readInt14);
                    parcel2.writeNoException();
                    parcel2.writeInt(iconMaxDimensions);
                    return true;
                case 14:
                    java.lang.String readString14 = parcel.readString();
                    java.lang.String readString15 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportShortcutUsed(readString14, readString15, readInt15);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    resetThrottling();
                    parcel2.writeNoException();
                    return true;
                case 16:
                    java.lang.String readString16 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onApplicationActive(readString16, readInt16);
                    return true;
                case 17:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] backupPayload = getBackupPayload(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(backupPayload);
                    return true;
                case 18:
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    applyRestore(createByteArray, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isRequestPinItemSupported = isRequestPinItemSupported(readInt19, readInt20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRequestPinItemSupported);
                    return true;
                case 20:
                    java.lang.String readString17 = parcel.readString();
                    android.content.IntentFilter intentFilter = (android.content.IntentFilter) parcel.readTypedObject(android.content.IntentFilter.CREATOR);
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice shareTargets = getShareTargets(readString17, intentFilter, readInt21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(shareTargets, 1);
                    return true;
                case 21:
                    java.lang.String readString18 = parcel.readString();
                    java.lang.String readString19 = parcel.readString();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasShareTargets = hasShareTargets(readString18, readString19, readInt22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasShareTargets);
                    return true;
                case 22:
                    java.lang.String readString20 = parcel.readString();
                    java.util.ArrayList<java.lang.String> createStringArrayList4 = parcel.createStringArrayList();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeLongLivedShortcuts(readString20, createStringArrayList4, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    java.lang.String readString21 = parcel.readString();
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice shortcuts = getShortcuts(readString21, readInt24, readInt25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(shortcuts, 1);
                    return true;
                case 24:
                    java.lang.String readString22 = parcel.readString();
                    android.content.pm.ShortcutInfo shortcutInfo3 = (android.content.pm.ShortcutInfo) parcel.readTypedObject(android.content.pm.ShortcutInfo.CREATOR);
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    pushDynamicShortcut(readString22, shortcutInfo3, readInt26);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.pm.IShortcutService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.IShortcutService.Stub.DESCRIPTOR;
            }

            @Override // android.content.pm.IShortcutService
            public boolean setDynamicShortcuts(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public boolean addDynamicShortcuts(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void removeDynamicShortcuts(java.lang.String str, java.util.List<java.lang.String> list, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void removeAllDynamicShortcuts(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public boolean updateShortcuts(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void requestPinShortcut(java.lang.String str, android.content.pm.ShortcutInfo shortcutInfo, android.content.IntentSender intentSender, int i, com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(shortcutInfo, 0);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void createShortcutResultIntent(java.lang.String str, android.content.pm.ShortcutInfo shortcutInfo, int i, com.android.internal.infra.AndroidFuture<android.content.Intent> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(shortcutInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void disableShortcuts(java.lang.String str, java.util.List<java.lang.String> list, java.lang.CharSequence charSequence, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void enableShortcuts(java.lang.String str, java.util.List<java.lang.String> list, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public int getMaxShortcutCountPerActivity(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public int getRemainingCallCount(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public long getRateLimitResetTime(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public int getIconMaxDimensions(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void reportShortcutUsed(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void resetThrottling() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void onApplicationActive(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public byte[] getBackupPayload(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void applyRestore(byte[] bArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public boolean isRequestPinItemSupported(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public android.content.pm.ParceledListSlice getShareTargets(java.lang.String str, android.content.IntentFilter intentFilter, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public boolean hasShareTargets(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void removeLongLivedShortcuts(java.lang.String str, java.util.List<java.lang.String> list, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public android.content.pm.ParceledListSlice getShortcuts(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void pushDynamicShortcut(java.lang.String str, android.content.pm.ShortcutInfo shortcutInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(shortcutInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 23;
        }
    }
}
