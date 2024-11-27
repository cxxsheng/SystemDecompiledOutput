package android.app.prediction;

/* loaded from: classes.dex */
public interface IPredictionManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.prediction.IPredictionManager";

    void createPredictionSession(android.app.prediction.AppPredictionContext appPredictionContext, android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.os.IBinder iBinder) throws android.os.RemoteException;

    void notifyAppTargetEvent(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.AppTargetEvent appTargetEvent) throws android.os.RemoteException;

    void notifyLaunchLocationShown(android.app.prediction.AppPredictionSessionId appPredictionSessionId, java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException;

    void onDestroyPredictionSession(android.app.prediction.AppPredictionSessionId appPredictionSessionId) throws android.os.RemoteException;

    void registerPredictionUpdates(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.IPredictionCallback iPredictionCallback) throws android.os.RemoteException;

    void requestPredictionUpdate(android.app.prediction.AppPredictionSessionId appPredictionSessionId) throws android.os.RemoteException;

    void requestServiceFeatures(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException;

    void sortAppTargets(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.content.pm.ParceledListSlice parceledListSlice, android.app.prediction.IPredictionCallback iPredictionCallback) throws android.os.RemoteException;

    void unregisterPredictionUpdates(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.IPredictionCallback iPredictionCallback) throws android.os.RemoteException;

    public static class Default implements android.app.prediction.IPredictionManager {
        @Override // android.app.prediction.IPredictionManager
        public void createPredictionSession(android.app.prediction.AppPredictionContext appPredictionContext, android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.prediction.IPredictionManager
        public void notifyAppTargetEvent(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.AppTargetEvent appTargetEvent) throws android.os.RemoteException {
        }

        @Override // android.app.prediction.IPredictionManager
        public void notifyLaunchLocationShown(android.app.prediction.AppPredictionSessionId appPredictionSessionId, java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
        }

        @Override // android.app.prediction.IPredictionManager
        public void sortAppTargets(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.content.pm.ParceledListSlice parceledListSlice, android.app.prediction.IPredictionCallback iPredictionCallback) throws android.os.RemoteException {
        }

        @Override // android.app.prediction.IPredictionManager
        public void registerPredictionUpdates(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.IPredictionCallback iPredictionCallback) throws android.os.RemoteException {
        }

        @Override // android.app.prediction.IPredictionManager
        public void unregisterPredictionUpdates(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.IPredictionCallback iPredictionCallback) throws android.os.RemoteException {
        }

        @Override // android.app.prediction.IPredictionManager
        public void requestPredictionUpdate(android.app.prediction.AppPredictionSessionId appPredictionSessionId) throws android.os.RemoteException {
        }

        @Override // android.app.prediction.IPredictionManager
        public void onDestroyPredictionSession(android.app.prediction.AppPredictionSessionId appPredictionSessionId) throws android.os.RemoteException {
        }

        @Override // android.app.prediction.IPredictionManager
        public void requestServiceFeatures(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.prediction.IPredictionManager {
        static final int TRANSACTION_createPredictionSession = 1;
        static final int TRANSACTION_notifyAppTargetEvent = 2;
        static final int TRANSACTION_notifyLaunchLocationShown = 3;
        static final int TRANSACTION_onDestroyPredictionSession = 8;
        static final int TRANSACTION_registerPredictionUpdates = 5;
        static final int TRANSACTION_requestPredictionUpdate = 7;
        static final int TRANSACTION_requestServiceFeatures = 9;
        static final int TRANSACTION_sortAppTargets = 4;
        static final int TRANSACTION_unregisterPredictionUpdates = 6;

        public Stub() {
            attachInterface(this, android.app.prediction.IPredictionManager.DESCRIPTOR);
        }

        public static android.app.prediction.IPredictionManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.prediction.IPredictionManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.prediction.IPredictionManager)) {
                return (android.app.prediction.IPredictionManager) queryLocalInterface;
            }
            return new android.app.prediction.IPredictionManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createPredictionSession";
                case 2:
                    return "notifyAppTargetEvent";
                case 3:
                    return "notifyLaunchLocationShown";
                case 4:
                    return "sortAppTargets";
                case 5:
                    return "registerPredictionUpdates";
                case 6:
                    return "unregisterPredictionUpdates";
                case 7:
                    return "requestPredictionUpdate";
                case 8:
                    return "onDestroyPredictionSession";
                case 9:
                    return "requestServiceFeatures";
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
                parcel.enforceInterface(android.app.prediction.IPredictionManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.prediction.IPredictionManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.prediction.AppPredictionContext appPredictionContext = (android.app.prediction.AppPredictionContext) parcel.readTypedObject(android.app.prediction.AppPredictionContext.CREATOR);
                    android.app.prediction.AppPredictionSessionId appPredictionSessionId = (android.app.prediction.AppPredictionSessionId) parcel.readTypedObject(android.app.prediction.AppPredictionSessionId.CREATOR);
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createPredictionSession(appPredictionContext, appPredictionSessionId, readStrongBinder);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.app.prediction.AppPredictionSessionId appPredictionSessionId2 = (android.app.prediction.AppPredictionSessionId) parcel.readTypedObject(android.app.prediction.AppPredictionSessionId.CREATOR);
                    android.app.prediction.AppTargetEvent appTargetEvent = (android.app.prediction.AppTargetEvent) parcel.readTypedObject(android.app.prediction.AppTargetEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyAppTargetEvent(appPredictionSessionId2, appTargetEvent);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.app.prediction.AppPredictionSessionId appPredictionSessionId3 = (android.app.prediction.AppPredictionSessionId) parcel.readTypedObject(android.app.prediction.AppPredictionSessionId.CREATOR);
                    java.lang.String readString = parcel.readString();
                    android.content.pm.ParceledListSlice parceledListSlice = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyLaunchLocationShown(appPredictionSessionId3, readString, parceledListSlice);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.app.prediction.AppPredictionSessionId appPredictionSessionId4 = (android.app.prediction.AppPredictionSessionId) parcel.readTypedObject(android.app.prediction.AppPredictionSessionId.CREATOR);
                    android.content.pm.ParceledListSlice parceledListSlice2 = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    android.app.prediction.IPredictionCallback asInterface = android.app.prediction.IPredictionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sortAppTargets(appPredictionSessionId4, parceledListSlice2, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.app.prediction.AppPredictionSessionId appPredictionSessionId5 = (android.app.prediction.AppPredictionSessionId) parcel.readTypedObject(android.app.prediction.AppPredictionSessionId.CREATOR);
                    android.app.prediction.IPredictionCallback asInterface2 = android.app.prediction.IPredictionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerPredictionUpdates(appPredictionSessionId5, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.app.prediction.AppPredictionSessionId appPredictionSessionId6 = (android.app.prediction.AppPredictionSessionId) parcel.readTypedObject(android.app.prediction.AppPredictionSessionId.CREATOR);
                    android.app.prediction.IPredictionCallback asInterface3 = android.app.prediction.IPredictionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterPredictionUpdates(appPredictionSessionId6, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    android.app.prediction.AppPredictionSessionId appPredictionSessionId7 = (android.app.prediction.AppPredictionSessionId) parcel.readTypedObject(android.app.prediction.AppPredictionSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestPredictionUpdate(appPredictionSessionId7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.app.prediction.AppPredictionSessionId appPredictionSessionId8 = (android.app.prediction.AppPredictionSessionId) parcel.readTypedObject(android.app.prediction.AppPredictionSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDestroyPredictionSession(appPredictionSessionId8);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.app.prediction.AppPredictionSessionId appPredictionSessionId9 = (android.app.prediction.AppPredictionSessionId) parcel.readTypedObject(android.app.prediction.AppPredictionSessionId.CREATOR);
                    android.os.IRemoteCallback asInterface4 = android.os.IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestServiceFeatures(appPredictionSessionId9, asInterface4);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.prediction.IPredictionManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.prediction.IPredictionManager.DESCRIPTOR;
            }

            @Override // android.app.prediction.IPredictionManager
            public void createPredictionSession(android.app.prediction.AppPredictionContext appPredictionContext, android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.prediction.IPredictionManager.DESCRIPTOR);
                    obtain.writeTypedObject(appPredictionContext, 0);
                    obtain.writeTypedObject(appPredictionSessionId, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.prediction.IPredictionManager
            public void notifyAppTargetEvent(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.AppTargetEvent appTargetEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.prediction.IPredictionManager.DESCRIPTOR);
                    obtain.writeTypedObject(appPredictionSessionId, 0);
                    obtain.writeTypedObject(appTargetEvent, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.prediction.IPredictionManager
            public void notifyLaunchLocationShown(android.app.prediction.AppPredictionSessionId appPredictionSessionId, java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.prediction.IPredictionManager.DESCRIPTOR);
                    obtain.writeTypedObject(appPredictionSessionId, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.prediction.IPredictionManager
            public void sortAppTargets(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.content.pm.ParceledListSlice parceledListSlice, android.app.prediction.IPredictionCallback iPredictionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.prediction.IPredictionManager.DESCRIPTOR);
                    obtain.writeTypedObject(appPredictionSessionId, 0);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    obtain.writeStrongInterface(iPredictionCallback);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.prediction.IPredictionManager
            public void registerPredictionUpdates(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.IPredictionCallback iPredictionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.prediction.IPredictionManager.DESCRIPTOR);
                    obtain.writeTypedObject(appPredictionSessionId, 0);
                    obtain.writeStrongInterface(iPredictionCallback);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.prediction.IPredictionManager
            public void unregisterPredictionUpdates(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.IPredictionCallback iPredictionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.prediction.IPredictionManager.DESCRIPTOR);
                    obtain.writeTypedObject(appPredictionSessionId, 0);
                    obtain.writeStrongInterface(iPredictionCallback);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.prediction.IPredictionManager
            public void requestPredictionUpdate(android.app.prediction.AppPredictionSessionId appPredictionSessionId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.prediction.IPredictionManager.DESCRIPTOR);
                    obtain.writeTypedObject(appPredictionSessionId, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.prediction.IPredictionManager
            public void onDestroyPredictionSession(android.app.prediction.AppPredictionSessionId appPredictionSessionId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.prediction.IPredictionManager.DESCRIPTOR);
                    obtain.writeTypedObject(appPredictionSessionId, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.prediction.IPredictionManager
            public void requestServiceFeatures(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.prediction.IPredictionManager.DESCRIPTOR);
                    obtain.writeTypedObject(appPredictionSessionId, 0);
                    obtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}
