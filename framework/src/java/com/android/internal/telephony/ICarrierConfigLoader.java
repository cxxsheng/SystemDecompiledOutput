package com.android.internal.telephony;

/* loaded from: classes5.dex */
public interface ICarrierConfigLoader extends android.os.IInterface {
    @java.lang.Deprecated
    android.os.PersistableBundle getConfigForSubId(int i, java.lang.String str) throws android.os.RemoteException;

    android.os.PersistableBundle getConfigForSubIdWithFeature(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.os.PersistableBundle getConfigSubsetForSubIdWithFeature(int i, java.lang.String str, java.lang.String str2, java.lang.String[] strArr) throws android.os.RemoteException;

    java.lang.String getDefaultCarrierServicePackageName() throws android.os.RemoteException;

    void notifyConfigChangedForSubId(int i) throws android.os.RemoteException;

    void overrideConfig(int i, android.os.PersistableBundle persistableBundle, boolean z) throws android.os.RemoteException;

    void updateConfigForPhoneId(int i, java.lang.String str) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.ICarrierConfigLoader {
        @Override // com.android.internal.telephony.ICarrierConfigLoader
        public android.os.PersistableBundle getConfigForSubId(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ICarrierConfigLoader
        public android.os.PersistableBundle getConfigForSubIdWithFeature(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ICarrierConfigLoader
        public void overrideConfig(int i, android.os.PersistableBundle persistableBundle, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ICarrierConfigLoader
        public void notifyConfigChangedForSubId(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ICarrierConfigLoader
        public void updateConfigForPhoneId(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ICarrierConfigLoader
        public java.lang.String getDefaultCarrierServicePackageName() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ICarrierConfigLoader
        public android.os.PersistableBundle getConfigSubsetForSubIdWithFeature(int i, java.lang.String str, java.lang.String str2, java.lang.String[] strArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.ICarrierConfigLoader {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.ICarrierConfigLoader";
        static final int TRANSACTION_getConfigForSubId = 1;
        static final int TRANSACTION_getConfigForSubIdWithFeature = 2;
        static final int TRANSACTION_getConfigSubsetForSubIdWithFeature = 7;
        static final int TRANSACTION_getDefaultCarrierServicePackageName = 6;
        static final int TRANSACTION_notifyConfigChangedForSubId = 4;
        static final int TRANSACTION_overrideConfig = 3;
        static final int TRANSACTION_updateConfigForPhoneId = 5;
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

        public static com.android.internal.telephony.ICarrierConfigLoader asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.ICarrierConfigLoader)) {
                return (com.android.internal.telephony.ICarrierConfigLoader) queryLocalInterface;
            }
            return new com.android.internal.telephony.ICarrierConfigLoader.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getConfigForSubId";
                case 2:
                    return "getConfigForSubIdWithFeature";
                case 3:
                    return "overrideConfig";
                case 4:
                    return "notifyConfigChangedForSubId";
                case 5:
                    return "updateConfigForPhoneId";
                case 6:
                    return "getDefaultCarrierServicePackageName";
                case 7:
                    return "getConfigSubsetForSubIdWithFeature";
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
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.PersistableBundle configForSubId = getConfigForSubId(readInt, readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configForSubId, 1);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.PersistableBundle configForSubIdWithFeature = getConfigForSubIdWithFeature(readInt2, readString2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configForSubIdWithFeature, 1);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    overrideConfig(readInt3, persistableBundle, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyConfigChangedForSubId(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateConfigForPhoneId(readInt5, readString4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    java.lang.String defaultCarrierServicePackageName = getDefaultCarrierServicePackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(defaultCarrierServicePackageName);
                    return true;
                case 7:
                    int readInt6 = parcel.readInt();
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    android.os.PersistableBundle configSubsetForSubIdWithFeature = getConfigSubsetForSubIdWithFeature(readInt6, readString5, readString6, createStringArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configSubsetForSubIdWithFeature, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.ICarrierConfigLoader {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.ICarrierConfigLoader.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ICarrierConfigLoader
            public android.os.PersistableBundle getConfigForSubId(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ICarrierConfigLoader.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.PersistableBundle) obtain2.readTypedObject(android.os.PersistableBundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierConfigLoader
            public android.os.PersistableBundle getConfigForSubIdWithFeature(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ICarrierConfigLoader.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.PersistableBundle) obtain2.readTypedObject(android.os.PersistableBundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierConfigLoader
            public void overrideConfig(int i, android.os.PersistableBundle persistableBundle, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ICarrierConfigLoader.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierConfigLoader
            public void notifyConfigChangedForSubId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ICarrierConfigLoader.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierConfigLoader
            public void updateConfigForPhoneId(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ICarrierConfigLoader.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierConfigLoader
            public java.lang.String getDefaultCarrierServicePackageName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ICarrierConfigLoader.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierConfigLoader
            public android.os.PersistableBundle getConfigSubsetForSubIdWithFeature(int i, java.lang.String str, java.lang.String str2, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ICarrierConfigLoader.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.PersistableBundle) obtain2.readTypedObject(android.os.PersistableBundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void overrideConfig_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_PHONE_STATE, getCallingPid(), getCallingUid());
        }

        protected void updateConfigForPhoneId_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_PHONE_STATE, getCallingPid(), getCallingUid());
        }

        protected void getDefaultCarrierServicePackageName_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.READ_PRIVILEGED_PHONE_STATE, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
