package android.webkit;

/* loaded from: classes4.dex */
public interface IWebViewUpdateService extends android.os.IInterface {
    java.lang.String changeProviderAndSetting(java.lang.String str) throws android.os.RemoteException;

    void enableMultiProcess(boolean z) throws android.os.RemoteException;

    android.webkit.WebViewProviderInfo[] getAllWebViewPackages() throws android.os.RemoteException;

    android.content.pm.PackageInfo getCurrentWebViewPackage() throws android.os.RemoteException;

    java.lang.String getCurrentWebViewPackageName() throws android.os.RemoteException;

    android.webkit.WebViewProviderInfo getDefaultWebViewPackage() throws android.os.RemoteException;

    android.webkit.WebViewProviderInfo[] getValidWebViewPackages() throws android.os.RemoteException;

    boolean isMultiProcessEnabled() throws android.os.RemoteException;

    void notifyRelroCreationCompleted() throws android.os.RemoteException;

    android.webkit.WebViewProviderResponse waitForAndGetProvider() throws android.os.RemoteException;

    public static class Default implements android.webkit.IWebViewUpdateService {
        @Override // android.webkit.IWebViewUpdateService
        public void notifyRelroCreationCompleted() throws android.os.RemoteException {
        }

        @Override // android.webkit.IWebViewUpdateService
        public android.webkit.WebViewProviderResponse waitForAndGetProvider() throws android.os.RemoteException {
            return null;
        }

        @Override // android.webkit.IWebViewUpdateService
        public java.lang.String changeProviderAndSetting(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.webkit.IWebViewUpdateService
        public android.webkit.WebViewProviderInfo[] getValidWebViewPackages() throws android.os.RemoteException {
            return null;
        }

        @Override // android.webkit.IWebViewUpdateService
        public android.webkit.WebViewProviderInfo[] getAllWebViewPackages() throws android.os.RemoteException {
            return null;
        }

        @Override // android.webkit.IWebViewUpdateService
        public java.lang.String getCurrentWebViewPackageName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.webkit.IWebViewUpdateService
        public android.content.pm.PackageInfo getCurrentWebViewPackage() throws android.os.RemoteException {
            return null;
        }

        @Override // android.webkit.IWebViewUpdateService
        public boolean isMultiProcessEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.webkit.IWebViewUpdateService
        public void enableMultiProcess(boolean z) throws android.os.RemoteException {
        }

        @Override // android.webkit.IWebViewUpdateService
        public android.webkit.WebViewProviderInfo getDefaultWebViewPackage() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.webkit.IWebViewUpdateService {
        public static final java.lang.String DESCRIPTOR = "android.webkit.IWebViewUpdateService";
        static final int TRANSACTION_changeProviderAndSetting = 3;
        static final int TRANSACTION_enableMultiProcess = 9;
        static final int TRANSACTION_getAllWebViewPackages = 5;
        static final int TRANSACTION_getCurrentWebViewPackage = 7;
        static final int TRANSACTION_getCurrentWebViewPackageName = 6;
        static final int TRANSACTION_getDefaultWebViewPackage = 10;
        static final int TRANSACTION_getValidWebViewPackages = 4;
        static final int TRANSACTION_isMultiProcessEnabled = 8;
        static final int TRANSACTION_notifyRelroCreationCompleted = 1;
        static final int TRANSACTION_waitForAndGetProvider = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.webkit.IWebViewUpdateService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.webkit.IWebViewUpdateService)) {
                return (android.webkit.IWebViewUpdateService) queryLocalInterface;
            }
            return new android.webkit.IWebViewUpdateService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "notifyRelroCreationCompleted";
                case 2:
                    return "waitForAndGetProvider";
                case 3:
                    return "changeProviderAndSetting";
                case 4:
                    return "getValidWebViewPackages";
                case 5:
                    return "getAllWebViewPackages";
                case 6:
                    return "getCurrentWebViewPackageName";
                case 7:
                    return "getCurrentWebViewPackage";
                case 8:
                    return "isMultiProcessEnabled";
                case 9:
                    return "enableMultiProcess";
                case 10:
                    return "getDefaultWebViewPackage";
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
                    notifyRelroCreationCompleted();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.webkit.WebViewProviderResponse waitForAndGetProvider = waitForAndGetProvider();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(waitForAndGetProvider, 1);
                    return true;
                case 3:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String changeProviderAndSetting = changeProviderAndSetting(readString);
                    parcel2.writeNoException();
                    parcel2.writeString(changeProviderAndSetting);
                    return true;
                case 4:
                    android.webkit.WebViewProviderInfo[] validWebViewPackages = getValidWebViewPackages();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(validWebViewPackages, 1);
                    return true;
                case 5:
                    android.webkit.WebViewProviderInfo[] allWebViewPackages = getAllWebViewPackages();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(allWebViewPackages, 1);
                    return true;
                case 6:
                    java.lang.String currentWebViewPackageName = getCurrentWebViewPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(currentWebViewPackageName);
                    return true;
                case 7:
                    android.content.pm.PackageInfo currentWebViewPackage = getCurrentWebViewPackage();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentWebViewPackage, 1);
                    return true;
                case 8:
                    boolean isMultiProcessEnabled = isMultiProcessEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMultiProcessEnabled);
                    return true;
                case 9:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableMultiProcess(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.webkit.WebViewProviderInfo defaultWebViewPackage = getDefaultWebViewPackage();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultWebViewPackage, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.webkit.IWebViewUpdateService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.webkit.IWebViewUpdateService.Stub.DESCRIPTOR;
            }

            @Override // android.webkit.IWebViewUpdateService
            public void notifyRelroCreationCompleted() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.webkit.IWebViewUpdateService.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.webkit.IWebViewUpdateService
            public android.webkit.WebViewProviderResponse waitForAndGetProvider() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.webkit.IWebViewUpdateService.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.webkit.WebViewProviderResponse) obtain2.readTypedObject(android.webkit.WebViewProviderResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.webkit.IWebViewUpdateService
            public java.lang.String changeProviderAndSetting(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.webkit.IWebViewUpdateService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.webkit.IWebViewUpdateService
            public android.webkit.WebViewProviderInfo[] getValidWebViewPackages() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.webkit.IWebViewUpdateService.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.webkit.WebViewProviderInfo[]) obtain2.createTypedArray(android.webkit.WebViewProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.webkit.IWebViewUpdateService
            public android.webkit.WebViewProviderInfo[] getAllWebViewPackages() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.webkit.IWebViewUpdateService.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.webkit.WebViewProviderInfo[]) obtain2.createTypedArray(android.webkit.WebViewProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.webkit.IWebViewUpdateService
            public java.lang.String getCurrentWebViewPackageName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.webkit.IWebViewUpdateService.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.webkit.IWebViewUpdateService
            public android.content.pm.PackageInfo getCurrentWebViewPackage() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.webkit.IWebViewUpdateService.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.PackageInfo) obtain2.readTypedObject(android.content.pm.PackageInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.webkit.IWebViewUpdateService
            public boolean isMultiProcessEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.webkit.IWebViewUpdateService.Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.webkit.IWebViewUpdateService
            public void enableMultiProcess(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.webkit.IWebViewUpdateService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.webkit.IWebViewUpdateService
            public android.webkit.WebViewProviderInfo getDefaultWebViewPackage() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.webkit.IWebViewUpdateService.Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.webkit.WebViewProviderInfo) obtain2.readTypedObject(android.webkit.WebViewProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }
    }
}
