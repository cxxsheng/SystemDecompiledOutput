package android.view.contentcapture;

/* loaded from: classes4.dex */
public interface IContentCaptureManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.contentcapture.IContentCaptureManager";

    void finishSession(int i) throws android.os.RemoteException;

    void getContentCaptureConditions(java.lang.String str, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void getServiceComponentName(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void getServiceSettingsActivity(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void isContentCaptureFeatureEnabled(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void onLoginDetected(android.content.pm.ParceledListSlice<android.view.contentcapture.ContentCaptureEvent> parceledListSlice) throws android.os.RemoteException;

    void registerContentCaptureOptionsCallback(java.lang.String str, android.view.contentcapture.IContentCaptureOptionsCallback iContentCaptureOptionsCallback) throws android.os.RemoteException;

    void removeData(android.view.contentcapture.DataRemovalRequest dataRemovalRequest) throws android.os.RemoteException;

    void resetTemporaryService(int i) throws android.os.RemoteException;

    void setDefaultServiceEnabled(int i, boolean z) throws android.os.RemoteException;

    void setTemporaryService(int i, java.lang.String str, int i2) throws android.os.RemoteException;

    void shareData(android.view.contentcapture.DataShareRequest dataShareRequest, android.view.contentcapture.IDataShareWriteAdapter iDataShareWriteAdapter) throws android.os.RemoteException;

    void startSession(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.content.ComponentName componentName, int i, int i2, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    public static class Default implements android.view.contentcapture.IContentCaptureManager {
        @Override // android.view.contentcapture.IContentCaptureManager
        public void startSession(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.content.ComponentName componentName, int i, int i2, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void finishSession(int i) throws android.os.RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void getServiceComponentName(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void removeData(android.view.contentcapture.DataRemovalRequest dataRemovalRequest) throws android.os.RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void shareData(android.view.contentcapture.DataShareRequest dataShareRequest, android.view.contentcapture.IDataShareWriteAdapter iDataShareWriteAdapter) throws android.os.RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void isContentCaptureFeatureEnabled(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void getServiceSettingsActivity(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void getContentCaptureConditions(java.lang.String str, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void resetTemporaryService(int i) throws android.os.RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void setTemporaryService(int i, java.lang.String str, int i2) throws android.os.RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void setDefaultServiceEnabled(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void registerContentCaptureOptionsCallback(java.lang.String str, android.view.contentcapture.IContentCaptureOptionsCallback iContentCaptureOptionsCallback) throws android.os.RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void onLoginDetected(android.content.pm.ParceledListSlice<android.view.contentcapture.ContentCaptureEvent> parceledListSlice) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.contentcapture.IContentCaptureManager {
        static final int TRANSACTION_finishSession = 2;
        static final int TRANSACTION_getContentCaptureConditions = 8;
        static final int TRANSACTION_getServiceComponentName = 3;
        static final int TRANSACTION_getServiceSettingsActivity = 7;
        static final int TRANSACTION_isContentCaptureFeatureEnabled = 6;
        static final int TRANSACTION_onLoginDetected = 13;
        static final int TRANSACTION_registerContentCaptureOptionsCallback = 12;
        static final int TRANSACTION_removeData = 4;
        static final int TRANSACTION_resetTemporaryService = 9;
        static final int TRANSACTION_setDefaultServiceEnabled = 11;
        static final int TRANSACTION_setTemporaryService = 10;
        static final int TRANSACTION_shareData = 5;
        static final int TRANSACTION_startSession = 1;

        public Stub() {
            attachInterface(this, android.view.contentcapture.IContentCaptureManager.DESCRIPTOR);
        }

        public static android.view.contentcapture.IContentCaptureManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.contentcapture.IContentCaptureManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.contentcapture.IContentCaptureManager)) {
                return (android.view.contentcapture.IContentCaptureManager) queryLocalInterface;
            }
            return new android.view.contentcapture.IContentCaptureManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startSession";
                case 2:
                    return "finishSession";
                case 3:
                    return "getServiceComponentName";
                case 4:
                    return "removeData";
                case 5:
                    return "shareData";
                case 6:
                    return "isContentCaptureFeatureEnabled";
                case 7:
                    return "getServiceSettingsActivity";
                case 8:
                    return "getContentCaptureConditions";
                case 9:
                    return "resetTemporaryService";
                case 10:
                    return "setTemporaryService";
                case 11:
                    return "setDefaultServiceEnabled";
                case 12:
                    return "registerContentCaptureOptionsCallback";
                case 13:
                    return "onLoginDetected";
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
                parcel.enforceInterface(android.view.contentcapture.IContentCaptureManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.contentcapture.IContentCaptureManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    com.android.internal.os.IResultReceiver asInterface = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startSession(readStrongBinder, readStrongBinder2, componentName, readInt, readInt2, asInterface);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishSession(readInt3);
                    return true;
                case 3:
                    com.android.internal.os.IResultReceiver asInterface2 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getServiceComponentName(asInterface2);
                    return true;
                case 4:
                    android.view.contentcapture.DataRemovalRequest dataRemovalRequest = (android.view.contentcapture.DataRemovalRequest) parcel.readTypedObject(android.view.contentcapture.DataRemovalRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeData(dataRemovalRequest);
                    return true;
                case 5:
                    android.view.contentcapture.DataShareRequest dataShareRequest = (android.view.contentcapture.DataShareRequest) parcel.readTypedObject(android.view.contentcapture.DataShareRequest.CREATOR);
                    android.view.contentcapture.IDataShareWriteAdapter asInterface3 = android.view.contentcapture.IDataShareWriteAdapter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    shareData(dataShareRequest, asInterface3);
                    return true;
                case 6:
                    com.android.internal.os.IResultReceiver asInterface4 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    isContentCaptureFeatureEnabled(asInterface4);
                    return true;
                case 7:
                    com.android.internal.os.IResultReceiver asInterface5 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getServiceSettingsActivity(asInterface5);
                    return true;
                case 8:
                    java.lang.String readString = parcel.readString();
                    com.android.internal.os.IResultReceiver asInterface6 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getContentCaptureConditions(readString, asInterface6);
                    return true;
                case 9:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetTemporaryService(readInt4);
                    return true;
                case 10:
                    int readInt5 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTemporaryService(readInt5, readString2, readInt6);
                    return true;
                case 11:
                    int readInt7 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDefaultServiceEnabled(readInt7, readBoolean);
                    return true;
                case 12:
                    java.lang.String readString3 = parcel.readString();
                    android.view.contentcapture.IContentCaptureOptionsCallback asInterface7 = android.view.contentcapture.IContentCaptureOptionsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerContentCaptureOptionsCallback(readString3, asInterface7);
                    return true;
                case 13:
                    android.content.pm.ParceledListSlice<android.view.contentcapture.ContentCaptureEvent> parceledListSlice = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    onLoginDetected(parceledListSlice);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.contentcapture.IContentCaptureManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.contentcapture.IContentCaptureManager.DESCRIPTOR;
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void startSession(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.content.ComponentName componentName, int i, int i2, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.contentcapture.IContentCaptureManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void finishSession(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.contentcapture.IContentCaptureManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void getServiceComponentName(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.contentcapture.IContentCaptureManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void removeData(android.view.contentcapture.DataRemovalRequest dataRemovalRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.contentcapture.IContentCaptureManager.DESCRIPTOR);
                    obtain.writeTypedObject(dataRemovalRequest, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void shareData(android.view.contentcapture.DataShareRequest dataShareRequest, android.view.contentcapture.IDataShareWriteAdapter iDataShareWriteAdapter) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.contentcapture.IContentCaptureManager.DESCRIPTOR);
                    obtain.writeTypedObject(dataShareRequest, 0);
                    obtain.writeStrongInterface(iDataShareWriteAdapter);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void isContentCaptureFeatureEnabled(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.contentcapture.IContentCaptureManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void getServiceSettingsActivity(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.contentcapture.IContentCaptureManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void getContentCaptureConditions(java.lang.String str, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.contentcapture.IContentCaptureManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void resetTemporaryService(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.contentcapture.IContentCaptureManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void setTemporaryService(int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.contentcapture.IContentCaptureManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void setDefaultServiceEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.contentcapture.IContentCaptureManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void registerContentCaptureOptionsCallback(java.lang.String str, android.view.contentcapture.IContentCaptureOptionsCallback iContentCaptureOptionsCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.contentcapture.IContentCaptureManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iContentCaptureOptionsCallback);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void onLoginDetected(android.content.pm.ParceledListSlice<android.view.contentcapture.ContentCaptureEvent> parceledListSlice) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.contentcapture.IContentCaptureManager.DESCRIPTOR);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }
    }
}
