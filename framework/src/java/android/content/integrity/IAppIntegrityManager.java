package android.content.integrity;

/* loaded from: classes.dex */
public interface IAppIntegrityManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.content.integrity.IAppIntegrityManager";

    java.lang.String getCurrentRuleSetProvider() throws android.os.RemoteException;

    java.lang.String getCurrentRuleSetVersion() throws android.os.RemoteException;

    android.content.pm.ParceledListSlice<android.content.integrity.Rule> getCurrentRules() throws android.os.RemoteException;

    java.util.List<java.lang.String> getWhitelistedRuleProviders() throws android.os.RemoteException;

    void updateRuleSet(java.lang.String str, android.content.pm.ParceledListSlice<android.content.integrity.Rule> parceledListSlice, android.content.IntentSender intentSender) throws android.os.RemoteException;

    public static class Default implements android.content.integrity.IAppIntegrityManager {
        @Override // android.content.integrity.IAppIntegrityManager
        public void updateRuleSet(java.lang.String str, android.content.pm.ParceledListSlice<android.content.integrity.Rule> parceledListSlice, android.content.IntentSender intentSender) throws android.os.RemoteException {
        }

        @Override // android.content.integrity.IAppIntegrityManager
        public java.lang.String getCurrentRuleSetVersion() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.integrity.IAppIntegrityManager
        public java.lang.String getCurrentRuleSetProvider() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.integrity.IAppIntegrityManager
        public android.content.pm.ParceledListSlice<android.content.integrity.Rule> getCurrentRules() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.integrity.IAppIntegrityManager
        public java.util.List<java.lang.String> getWhitelistedRuleProviders() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.integrity.IAppIntegrityManager {
        static final int TRANSACTION_getCurrentRuleSetProvider = 3;
        static final int TRANSACTION_getCurrentRuleSetVersion = 2;
        static final int TRANSACTION_getCurrentRules = 4;
        static final int TRANSACTION_getWhitelistedRuleProviders = 5;
        static final int TRANSACTION_updateRuleSet = 1;

        public Stub() {
            attachInterface(this, android.content.integrity.IAppIntegrityManager.DESCRIPTOR);
        }

        public static android.content.integrity.IAppIntegrityManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.content.integrity.IAppIntegrityManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.integrity.IAppIntegrityManager)) {
                return (android.content.integrity.IAppIntegrityManager) queryLocalInterface;
            }
            return new android.content.integrity.IAppIntegrityManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "updateRuleSet";
                case 2:
                    return "getCurrentRuleSetVersion";
                case 3:
                    return "getCurrentRuleSetProvider";
                case 4:
                    return "getCurrentRules";
                case 5:
                    return "getWhitelistedRuleProviders";
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
                parcel.enforceInterface(android.content.integrity.IAppIntegrityManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.content.integrity.IAppIntegrityManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    android.content.pm.ParceledListSlice<android.content.integrity.Rule> parceledListSlice = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    android.content.IntentSender intentSender = (android.content.IntentSender) parcel.readTypedObject(android.content.IntentSender.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateRuleSet(readString, parceledListSlice, intentSender);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    java.lang.String currentRuleSetVersion = getCurrentRuleSetVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(currentRuleSetVersion);
                    return true;
                case 3:
                    java.lang.String currentRuleSetProvider = getCurrentRuleSetProvider();
                    parcel2.writeNoException();
                    parcel2.writeString(currentRuleSetProvider);
                    return true;
                case 4:
                    android.content.pm.ParceledListSlice<android.content.integrity.Rule> currentRules = getCurrentRules();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentRules, 1);
                    return true;
                case 5:
                    java.util.List<java.lang.String> whitelistedRuleProviders = getWhitelistedRuleProviders();
                    parcel2.writeNoException();
                    parcel2.writeStringList(whitelistedRuleProviders);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.integrity.IAppIntegrityManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.integrity.IAppIntegrityManager.DESCRIPTOR;
            }

            @Override // android.content.integrity.IAppIntegrityManager
            public void updateRuleSet(java.lang.String str, android.content.pm.ParceledListSlice<android.content.integrity.Rule> parceledListSlice, android.content.IntentSender intentSender) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.integrity.IAppIntegrityManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    obtain.writeTypedObject(intentSender, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.integrity.IAppIntegrityManager
            public java.lang.String getCurrentRuleSetVersion() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.integrity.IAppIntegrityManager.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.integrity.IAppIntegrityManager
            public java.lang.String getCurrentRuleSetProvider() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.integrity.IAppIntegrityManager.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.integrity.IAppIntegrityManager
            public android.content.pm.ParceledListSlice<android.content.integrity.Rule> getCurrentRules() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.integrity.IAppIntegrityManager.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.integrity.IAppIntegrityManager
            public java.util.List<java.lang.String> getWhitelistedRuleProviders() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.integrity.IAppIntegrityManager.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
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
