package android.service.appprediction;

/* loaded from: classes3.dex */
public interface IPredictionService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.appprediction.IPredictionService";

    void notifyAppTargetEvent(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.AppTargetEvent appTargetEvent) throws android.os.RemoteException;

    void notifyLaunchLocationShown(android.app.prediction.AppPredictionSessionId appPredictionSessionId, java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException;

    void onCreatePredictionSession(android.app.prediction.AppPredictionContext appPredictionContext, android.app.prediction.AppPredictionSessionId appPredictionSessionId) throws android.os.RemoteException;

    void onDestroyPredictionSession(android.app.prediction.AppPredictionSessionId appPredictionSessionId) throws android.os.RemoteException;

    void registerPredictionUpdates(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.IPredictionCallback iPredictionCallback) throws android.os.RemoteException;

    void requestPredictionUpdate(android.app.prediction.AppPredictionSessionId appPredictionSessionId) throws android.os.RemoteException;

    void requestServiceFeatures(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException;

    void sortAppTargets(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.content.pm.ParceledListSlice parceledListSlice, android.app.prediction.IPredictionCallback iPredictionCallback) throws android.os.RemoteException;

    void unregisterPredictionUpdates(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.IPredictionCallback iPredictionCallback) throws android.os.RemoteException;

    public static class Default implements android.service.appprediction.IPredictionService {
        @Override // android.service.appprediction.IPredictionService
        public void onCreatePredictionSession(android.app.prediction.AppPredictionContext appPredictionContext, android.app.prediction.AppPredictionSessionId appPredictionSessionId) throws android.os.RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void notifyAppTargetEvent(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.AppTargetEvent appTargetEvent) throws android.os.RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void notifyLaunchLocationShown(android.app.prediction.AppPredictionSessionId appPredictionSessionId, java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void sortAppTargets(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.content.pm.ParceledListSlice parceledListSlice, android.app.prediction.IPredictionCallback iPredictionCallback) throws android.os.RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void registerPredictionUpdates(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.IPredictionCallback iPredictionCallback) throws android.os.RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void unregisterPredictionUpdates(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.IPredictionCallback iPredictionCallback) throws android.os.RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void requestPredictionUpdate(android.app.prediction.AppPredictionSessionId appPredictionSessionId) throws android.os.RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void onDestroyPredictionSession(android.app.prediction.AppPredictionSessionId appPredictionSessionId) throws android.os.RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void requestServiceFeatures(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.appprediction.IPredictionService {
        static final int TRANSACTION_notifyAppTargetEvent = 2;
        static final int TRANSACTION_notifyLaunchLocationShown = 3;
        static final int TRANSACTION_onCreatePredictionSession = 1;
        static final int TRANSACTION_onDestroyPredictionSession = 8;
        static final int TRANSACTION_registerPredictionUpdates = 5;
        static final int TRANSACTION_requestPredictionUpdate = 7;
        static final int TRANSACTION_requestServiceFeatures = 9;
        static final int TRANSACTION_sortAppTargets = 4;
        static final int TRANSACTION_unregisterPredictionUpdates = 6;

        public Stub() {
            attachInterface(this, android.service.appprediction.IPredictionService.DESCRIPTOR);
        }

        public static android.service.appprediction.IPredictionService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.appprediction.IPredictionService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.appprediction.IPredictionService)) {
                return (android.service.appprediction.IPredictionService) queryLocalInterface;
            }
            return new android.service.appprediction.IPredictionService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCreatePredictionSession";
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
                parcel.enforceInterface(android.service.appprediction.IPredictionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.appprediction.IPredictionService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.prediction.AppPredictionContext appPredictionContext = (android.app.prediction.AppPredictionContext) parcel.readTypedObject(android.app.prediction.AppPredictionContext.CREATOR);
                    android.app.prediction.AppPredictionSessionId appPredictionSessionId = (android.app.prediction.AppPredictionSessionId) parcel.readTypedObject(android.app.prediction.AppPredictionSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCreatePredictionSession(appPredictionContext, appPredictionSessionId);
                    return true;
                case 2:
                    android.app.prediction.AppPredictionSessionId appPredictionSessionId2 = (android.app.prediction.AppPredictionSessionId) parcel.readTypedObject(android.app.prediction.AppPredictionSessionId.CREATOR);
                    android.app.prediction.AppTargetEvent appTargetEvent = (android.app.prediction.AppTargetEvent) parcel.readTypedObject(android.app.prediction.AppTargetEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyAppTargetEvent(appPredictionSessionId2, appTargetEvent);
                    return true;
                case 3:
                    android.app.prediction.AppPredictionSessionId appPredictionSessionId3 = (android.app.prediction.AppPredictionSessionId) parcel.readTypedObject(android.app.prediction.AppPredictionSessionId.CREATOR);
                    java.lang.String readString = parcel.readString();
                    android.content.pm.ParceledListSlice parceledListSlice = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyLaunchLocationShown(appPredictionSessionId3, readString, parceledListSlice);
                    return true;
                case 4:
                    android.app.prediction.AppPredictionSessionId appPredictionSessionId4 = (android.app.prediction.AppPredictionSessionId) parcel.readTypedObject(android.app.prediction.AppPredictionSessionId.CREATOR);
                    android.content.pm.ParceledListSlice parceledListSlice2 = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    android.app.prediction.IPredictionCallback asInterface = android.app.prediction.IPredictionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sortAppTargets(appPredictionSessionId4, parceledListSlice2, asInterface);
                    return true;
                case 5:
                    android.app.prediction.AppPredictionSessionId appPredictionSessionId5 = (android.app.prediction.AppPredictionSessionId) parcel.readTypedObject(android.app.prediction.AppPredictionSessionId.CREATOR);
                    android.app.prediction.IPredictionCallback asInterface2 = android.app.prediction.IPredictionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerPredictionUpdates(appPredictionSessionId5, asInterface2);
                    return true;
                case 6:
                    android.app.prediction.AppPredictionSessionId appPredictionSessionId6 = (android.app.prediction.AppPredictionSessionId) parcel.readTypedObject(android.app.prediction.AppPredictionSessionId.CREATOR);
                    android.app.prediction.IPredictionCallback asInterface3 = android.app.prediction.IPredictionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterPredictionUpdates(appPredictionSessionId6, asInterface3);
                    return true;
                case 7:
                    android.app.prediction.AppPredictionSessionId appPredictionSessionId7 = (android.app.prediction.AppPredictionSessionId) parcel.readTypedObject(android.app.prediction.AppPredictionSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestPredictionUpdate(appPredictionSessionId7);
                    return true;
                case 8:
                    android.app.prediction.AppPredictionSessionId appPredictionSessionId8 = (android.app.prediction.AppPredictionSessionId) parcel.readTypedObject(android.app.prediction.AppPredictionSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDestroyPredictionSession(appPredictionSessionId8);
                    return true;
                case 9:
                    android.app.prediction.AppPredictionSessionId appPredictionSessionId9 = (android.app.prediction.AppPredictionSessionId) parcel.readTypedObject(android.app.prediction.AppPredictionSessionId.CREATOR);
                    android.os.IRemoteCallback asInterface4 = android.os.IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestServiceFeatures(appPredictionSessionId9, asInterface4);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.appprediction.IPredictionService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.appprediction.IPredictionService.DESCRIPTOR;
            }

            @Override // android.service.appprediction.IPredictionService
            public void onCreatePredictionSession(android.app.prediction.AppPredictionContext appPredictionContext, android.app.prediction.AppPredictionSessionId appPredictionSessionId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.appprediction.IPredictionService.DESCRIPTOR);
                    obtain.writeTypedObject(appPredictionContext, 0);
                    obtain.writeTypedObject(appPredictionSessionId, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void notifyAppTargetEvent(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.AppTargetEvent appTargetEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.appprediction.IPredictionService.DESCRIPTOR);
                    obtain.writeTypedObject(appPredictionSessionId, 0);
                    obtain.writeTypedObject(appTargetEvent, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void notifyLaunchLocationShown(android.app.prediction.AppPredictionSessionId appPredictionSessionId, java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.appprediction.IPredictionService.DESCRIPTOR);
                    obtain.writeTypedObject(appPredictionSessionId, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void sortAppTargets(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.content.pm.ParceledListSlice parceledListSlice, android.app.prediction.IPredictionCallback iPredictionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.appprediction.IPredictionService.DESCRIPTOR);
                    obtain.writeTypedObject(appPredictionSessionId, 0);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    obtain.writeStrongInterface(iPredictionCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void registerPredictionUpdates(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.IPredictionCallback iPredictionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.appprediction.IPredictionService.DESCRIPTOR);
                    obtain.writeTypedObject(appPredictionSessionId, 0);
                    obtain.writeStrongInterface(iPredictionCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void unregisterPredictionUpdates(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.IPredictionCallback iPredictionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.appprediction.IPredictionService.DESCRIPTOR);
                    obtain.writeTypedObject(appPredictionSessionId, 0);
                    obtain.writeStrongInterface(iPredictionCallback);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void requestPredictionUpdate(android.app.prediction.AppPredictionSessionId appPredictionSessionId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.appprediction.IPredictionService.DESCRIPTOR);
                    obtain.writeTypedObject(appPredictionSessionId, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void onDestroyPredictionSession(android.app.prediction.AppPredictionSessionId appPredictionSessionId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.appprediction.IPredictionService.DESCRIPTOR);
                    obtain.writeTypedObject(appPredictionSessionId, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void requestServiceFeatures(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.appprediction.IPredictionService.DESCRIPTOR);
                    obtain.writeTypedObject(appPredictionSessionId, 0);
                    obtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
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
