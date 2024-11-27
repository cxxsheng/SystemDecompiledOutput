package android.service.voice;

/* loaded from: classes3.dex */
public interface IVoiceInteractionService extends android.os.IInterface {
    void detectorRemoteExceptionOccurred(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void getActiveServiceSupportedActions(java.util.List<java.lang.String> list, com.android.internal.app.IVoiceActionCheckCallback iVoiceActionCheckCallback) throws android.os.RemoteException;

    void launchVoiceAssistFromKeyguard() throws android.os.RemoteException;

    void prepareToShowSession(android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void ready() throws android.os.RemoteException;

    void showSessionFailed(android.os.Bundle bundle) throws android.os.RemoteException;

    void shutdown() throws android.os.RemoteException;

    void soundModelsChanged() throws android.os.RemoteException;

    public static class Default implements android.service.voice.IVoiceInteractionService {
        @Override // android.service.voice.IVoiceInteractionService
        public void ready() throws android.os.RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void soundModelsChanged() throws android.os.RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void shutdown() throws android.os.RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void launchVoiceAssistFromKeyguard() throws android.os.RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void getActiveServiceSupportedActions(java.util.List<java.lang.String> list, com.android.internal.app.IVoiceActionCheckCallback iVoiceActionCheckCallback) throws android.os.RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void prepareToShowSession(android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void showSessionFailed(android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void detectorRemoteExceptionOccurred(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.voice.IVoiceInteractionService {
        public static final java.lang.String DESCRIPTOR = "android.service.voice.IVoiceInteractionService";
        static final int TRANSACTION_detectorRemoteExceptionOccurred = 8;
        static final int TRANSACTION_getActiveServiceSupportedActions = 5;
        static final int TRANSACTION_launchVoiceAssistFromKeyguard = 4;
        static final int TRANSACTION_prepareToShowSession = 6;
        static final int TRANSACTION_ready = 1;
        static final int TRANSACTION_showSessionFailed = 7;
        static final int TRANSACTION_shutdown = 3;
        static final int TRANSACTION_soundModelsChanged = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.voice.IVoiceInteractionService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.voice.IVoiceInteractionService)) {
                return (android.service.voice.IVoiceInteractionService) queryLocalInterface;
            }
            return new android.service.voice.IVoiceInteractionService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "ready";
                case 2:
                    return "soundModelsChanged";
                case 3:
                    return "shutdown";
                case 4:
                    return "launchVoiceAssistFromKeyguard";
                case 5:
                    return "getActiveServiceSupportedActions";
                case 6:
                    return "prepareToShowSession";
                case 7:
                    return "showSessionFailed";
                case 8:
                    return "detectorRemoteExceptionOccurred";
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
                    ready();
                    return true;
                case 2:
                    soundModelsChanged();
                    return true;
                case 3:
                    shutdown();
                    return true;
                case 4:
                    launchVoiceAssistFromKeyguard();
                    return true;
                case 5:
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    com.android.internal.app.IVoiceActionCheckCallback asInterface = com.android.internal.app.IVoiceActionCheckCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getActiveServiceSupportedActions(createStringArrayList, asInterface);
                    return true;
                case 6:
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    prepareToShowSession(bundle, readInt);
                    return true;
                case 7:
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    showSessionFailed(bundle2);
                    return true;
                case 8:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    detectorRemoteExceptionOccurred(readStrongBinder, readInt2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.voice.IVoiceInteractionService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.voice.IVoiceInteractionService.Stub.DESCRIPTOR;
            }

            @Override // android.service.voice.IVoiceInteractionService
            public void ready() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVoiceInteractionService.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionService
            public void soundModelsChanged() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVoiceInteractionService.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionService
            public void shutdown() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVoiceInteractionService.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionService
            public void launchVoiceAssistFromKeyguard() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVoiceInteractionService.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionService
            public void getActiveServiceSupportedActions(java.util.List<java.lang.String> list, com.android.internal.app.IVoiceActionCheckCallback iVoiceActionCheckCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVoiceInteractionService.Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeStrongInterface(iVoiceActionCheckCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionService
            public void prepareToShowSession(android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVoiceInteractionService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionService
            public void showSessionFailed(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVoiceInteractionService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionService
            public void detectorRemoteExceptionOccurred(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IVoiceInteractionService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }
    }
}
