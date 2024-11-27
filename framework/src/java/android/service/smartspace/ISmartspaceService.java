package android.service.smartspace;

/* loaded from: classes3.dex */
public interface ISmartspaceService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.smartspace.ISmartspaceService";

    void notifySmartspaceEvent(android.app.smartspace.SmartspaceSessionId smartspaceSessionId, android.app.smartspace.SmartspaceTargetEvent smartspaceTargetEvent) throws android.os.RemoteException;

    void onCreateSmartspaceSession(android.app.smartspace.SmartspaceConfig smartspaceConfig, android.app.smartspace.SmartspaceSessionId smartspaceSessionId) throws android.os.RemoteException;

    void onDestroySmartspaceSession(android.app.smartspace.SmartspaceSessionId smartspaceSessionId) throws android.os.RemoteException;

    void registerSmartspaceUpdates(android.app.smartspace.SmartspaceSessionId smartspaceSessionId, android.app.smartspace.ISmartspaceCallback iSmartspaceCallback) throws android.os.RemoteException;

    void requestSmartspaceUpdate(android.app.smartspace.SmartspaceSessionId smartspaceSessionId) throws android.os.RemoteException;

    void unregisterSmartspaceUpdates(android.app.smartspace.SmartspaceSessionId smartspaceSessionId, android.app.smartspace.ISmartspaceCallback iSmartspaceCallback) throws android.os.RemoteException;

    public static class Default implements android.service.smartspace.ISmartspaceService {
        @Override // android.service.smartspace.ISmartspaceService
        public void onCreateSmartspaceSession(android.app.smartspace.SmartspaceConfig smartspaceConfig, android.app.smartspace.SmartspaceSessionId smartspaceSessionId) throws android.os.RemoteException {
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void notifySmartspaceEvent(android.app.smartspace.SmartspaceSessionId smartspaceSessionId, android.app.smartspace.SmartspaceTargetEvent smartspaceTargetEvent) throws android.os.RemoteException {
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void requestSmartspaceUpdate(android.app.smartspace.SmartspaceSessionId smartspaceSessionId) throws android.os.RemoteException {
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void registerSmartspaceUpdates(android.app.smartspace.SmartspaceSessionId smartspaceSessionId, android.app.smartspace.ISmartspaceCallback iSmartspaceCallback) throws android.os.RemoteException {
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void unregisterSmartspaceUpdates(android.app.smartspace.SmartspaceSessionId smartspaceSessionId, android.app.smartspace.ISmartspaceCallback iSmartspaceCallback) throws android.os.RemoteException {
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void onDestroySmartspaceSession(android.app.smartspace.SmartspaceSessionId smartspaceSessionId) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.smartspace.ISmartspaceService {
        static final int TRANSACTION_notifySmartspaceEvent = 2;
        static final int TRANSACTION_onCreateSmartspaceSession = 1;
        static final int TRANSACTION_onDestroySmartspaceSession = 6;
        static final int TRANSACTION_registerSmartspaceUpdates = 4;
        static final int TRANSACTION_requestSmartspaceUpdate = 3;
        static final int TRANSACTION_unregisterSmartspaceUpdates = 5;

        public Stub() {
            attachInterface(this, android.service.smartspace.ISmartspaceService.DESCRIPTOR);
        }

        public static android.service.smartspace.ISmartspaceService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.smartspace.ISmartspaceService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.smartspace.ISmartspaceService)) {
                return (android.service.smartspace.ISmartspaceService) queryLocalInterface;
            }
            return new android.service.smartspace.ISmartspaceService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCreateSmartspaceSession";
                case 2:
                    return "notifySmartspaceEvent";
                case 3:
                    return "requestSmartspaceUpdate";
                case 4:
                    return "registerSmartspaceUpdates";
                case 5:
                    return "unregisterSmartspaceUpdates";
                case 6:
                    return "onDestroySmartspaceSession";
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
                parcel.enforceInterface(android.service.smartspace.ISmartspaceService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.smartspace.ISmartspaceService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.smartspace.SmartspaceConfig smartspaceConfig = (android.app.smartspace.SmartspaceConfig) parcel.readTypedObject(android.app.smartspace.SmartspaceConfig.CREATOR);
                    android.app.smartspace.SmartspaceSessionId smartspaceSessionId = (android.app.smartspace.SmartspaceSessionId) parcel.readTypedObject(android.app.smartspace.SmartspaceSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCreateSmartspaceSession(smartspaceConfig, smartspaceSessionId);
                    return true;
                case 2:
                    android.app.smartspace.SmartspaceSessionId smartspaceSessionId2 = (android.app.smartspace.SmartspaceSessionId) parcel.readTypedObject(android.app.smartspace.SmartspaceSessionId.CREATOR);
                    android.app.smartspace.SmartspaceTargetEvent smartspaceTargetEvent = (android.app.smartspace.SmartspaceTargetEvent) parcel.readTypedObject(android.app.smartspace.SmartspaceTargetEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySmartspaceEvent(smartspaceSessionId2, smartspaceTargetEvent);
                    return true;
                case 3:
                    android.app.smartspace.SmartspaceSessionId smartspaceSessionId3 = (android.app.smartspace.SmartspaceSessionId) parcel.readTypedObject(android.app.smartspace.SmartspaceSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestSmartspaceUpdate(smartspaceSessionId3);
                    return true;
                case 4:
                    android.app.smartspace.SmartspaceSessionId smartspaceSessionId4 = (android.app.smartspace.SmartspaceSessionId) parcel.readTypedObject(android.app.smartspace.SmartspaceSessionId.CREATOR);
                    android.app.smartspace.ISmartspaceCallback asInterface = android.app.smartspace.ISmartspaceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSmartspaceUpdates(smartspaceSessionId4, asInterface);
                    return true;
                case 5:
                    android.app.smartspace.SmartspaceSessionId smartspaceSessionId5 = (android.app.smartspace.SmartspaceSessionId) parcel.readTypedObject(android.app.smartspace.SmartspaceSessionId.CREATOR);
                    android.app.smartspace.ISmartspaceCallback asInterface2 = android.app.smartspace.ISmartspaceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterSmartspaceUpdates(smartspaceSessionId5, asInterface2);
                    return true;
                case 6:
                    android.app.smartspace.SmartspaceSessionId smartspaceSessionId6 = (android.app.smartspace.SmartspaceSessionId) parcel.readTypedObject(android.app.smartspace.SmartspaceSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDestroySmartspaceSession(smartspaceSessionId6);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.smartspace.ISmartspaceService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.smartspace.ISmartspaceService.DESCRIPTOR;
            }

            @Override // android.service.smartspace.ISmartspaceService
            public void onCreateSmartspaceSession(android.app.smartspace.SmartspaceConfig smartspaceConfig, android.app.smartspace.SmartspaceSessionId smartspaceSessionId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.smartspace.ISmartspaceService.DESCRIPTOR);
                    obtain.writeTypedObject(smartspaceConfig, 0);
                    obtain.writeTypedObject(smartspaceSessionId, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.smartspace.ISmartspaceService
            public void notifySmartspaceEvent(android.app.smartspace.SmartspaceSessionId smartspaceSessionId, android.app.smartspace.SmartspaceTargetEvent smartspaceTargetEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.smartspace.ISmartspaceService.DESCRIPTOR);
                    obtain.writeTypedObject(smartspaceSessionId, 0);
                    obtain.writeTypedObject(smartspaceTargetEvent, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.smartspace.ISmartspaceService
            public void requestSmartspaceUpdate(android.app.smartspace.SmartspaceSessionId smartspaceSessionId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.smartspace.ISmartspaceService.DESCRIPTOR);
                    obtain.writeTypedObject(smartspaceSessionId, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.smartspace.ISmartspaceService
            public void registerSmartspaceUpdates(android.app.smartspace.SmartspaceSessionId smartspaceSessionId, android.app.smartspace.ISmartspaceCallback iSmartspaceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.smartspace.ISmartspaceService.DESCRIPTOR);
                    obtain.writeTypedObject(smartspaceSessionId, 0);
                    obtain.writeStrongInterface(iSmartspaceCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.smartspace.ISmartspaceService
            public void unregisterSmartspaceUpdates(android.app.smartspace.SmartspaceSessionId smartspaceSessionId, android.app.smartspace.ISmartspaceCallback iSmartspaceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.smartspace.ISmartspaceService.DESCRIPTOR);
                    obtain.writeTypedObject(smartspaceSessionId, 0);
                    obtain.writeStrongInterface(iSmartspaceCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.smartspace.ISmartspaceService
            public void onDestroySmartspaceSession(android.app.smartspace.SmartspaceSessionId smartspaceSessionId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.smartspace.ISmartspaceService.DESCRIPTOR);
                    obtain.writeTypedObject(smartspaceSessionId, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}