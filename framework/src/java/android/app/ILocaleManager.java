package android.app;

/* loaded from: classes.dex */
public interface ILocaleManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.ILocaleManager";

    android.os.LocaleList getApplicationLocales(java.lang.String str, int i) throws android.os.RemoteException;

    android.app.LocaleConfig getOverrideLocaleConfig(java.lang.String str, int i) throws android.os.RemoteException;

    android.os.LocaleList getSystemLocales() throws android.os.RemoteException;

    void setApplicationLocales(java.lang.String str, int i, android.os.LocaleList localeList, boolean z) throws android.os.RemoteException;

    void setOverrideLocaleConfig(java.lang.String str, int i, android.app.LocaleConfig localeConfig) throws android.os.RemoteException;

    public static class Default implements android.app.ILocaleManager {
        @Override // android.app.ILocaleManager
        public void setApplicationLocales(java.lang.String str, int i, android.os.LocaleList localeList, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.ILocaleManager
        public android.os.LocaleList getApplicationLocales(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.ILocaleManager
        public android.os.LocaleList getSystemLocales() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.ILocaleManager
        public void setOverrideLocaleConfig(java.lang.String str, int i, android.app.LocaleConfig localeConfig) throws android.os.RemoteException {
        }

        @Override // android.app.ILocaleManager
        public android.app.LocaleConfig getOverrideLocaleConfig(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.ILocaleManager {
        static final int TRANSACTION_getApplicationLocales = 2;
        static final int TRANSACTION_getOverrideLocaleConfig = 5;
        static final int TRANSACTION_getSystemLocales = 3;
        static final int TRANSACTION_setApplicationLocales = 1;
        static final int TRANSACTION_setOverrideLocaleConfig = 4;

        public Stub() {
            attachInterface(this, android.app.ILocaleManager.DESCRIPTOR);
        }

        public static android.app.ILocaleManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.ILocaleManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.ILocaleManager)) {
                return (android.app.ILocaleManager) queryLocalInterface;
            }
            return new android.app.ILocaleManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setApplicationLocales";
                case 2:
                    return "getApplicationLocales";
                case 3:
                    return "getSystemLocales";
                case 4:
                    return "setOverrideLocaleConfig";
                case 5:
                    return "getOverrideLocaleConfig";
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
                parcel.enforceInterface(android.app.ILocaleManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.ILocaleManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    android.os.LocaleList localeList = (android.os.LocaleList) parcel.readTypedObject(android.os.LocaleList.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setApplicationLocales(readString, readInt, localeList, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.LocaleList applicationLocales = getApplicationLocales(readString2, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(applicationLocales, 1);
                    return true;
                case 3:
                    android.os.LocaleList systemLocales = getSystemLocales();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(systemLocales, 1);
                    return true;
                case 4:
                    java.lang.String readString3 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    android.app.LocaleConfig localeConfig = (android.app.LocaleConfig) parcel.readTypedObject(android.app.LocaleConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    setOverrideLocaleConfig(readString3, readInt3, localeConfig);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    java.lang.String readString4 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.LocaleConfig overrideLocaleConfig = getOverrideLocaleConfig(readString4, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(overrideLocaleConfig, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.ILocaleManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.ILocaleManager.DESCRIPTOR;
            }

            @Override // android.app.ILocaleManager
            public void setApplicationLocales(java.lang.String str, int i, android.os.LocaleList localeList, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.ILocaleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(localeList, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.ILocaleManager
            public android.os.LocaleList getApplicationLocales(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.ILocaleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.LocaleList) obtain2.readTypedObject(android.os.LocaleList.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.ILocaleManager
            public android.os.LocaleList getSystemLocales() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.ILocaleManager.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.LocaleList) obtain2.readTypedObject(android.os.LocaleList.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.ILocaleManager
            public void setOverrideLocaleConfig(java.lang.String str, int i, android.app.LocaleConfig localeConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.ILocaleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(localeConfig, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.ILocaleManager
            public android.app.LocaleConfig getOverrideLocaleConfig(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.ILocaleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.LocaleConfig) obtain2.readTypedObject(android.app.LocaleConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
