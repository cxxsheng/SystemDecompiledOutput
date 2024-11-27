package android.media.tv.interactive;

/* loaded from: classes2.dex */
public interface ITvInteractiveAppService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.tv.interactive.ITvInteractiveAppService";

    void createSession(android.view.InputChannel inputChannel, android.media.tv.interactive.ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback, java.lang.String str, int i) throws android.os.RemoteException;

    void registerAppLinkInfo(android.media.tv.interactive.AppLinkInfo appLinkInfo) throws android.os.RemoteException;

    void registerCallback(android.media.tv.interactive.ITvInteractiveAppServiceCallback iTvInteractiveAppServiceCallback) throws android.os.RemoteException;

    void sendAppLinkCommand(android.os.Bundle bundle) throws android.os.RemoteException;

    void unregisterAppLinkInfo(android.media.tv.interactive.AppLinkInfo appLinkInfo) throws android.os.RemoteException;

    void unregisterCallback(android.media.tv.interactive.ITvInteractiveAppServiceCallback iTvInteractiveAppServiceCallback) throws android.os.RemoteException;

    public static class Default implements android.media.tv.interactive.ITvInteractiveAppService {
        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void registerCallback(android.media.tv.interactive.ITvInteractiveAppServiceCallback iTvInteractiveAppServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void unregisterCallback(android.media.tv.interactive.ITvInteractiveAppServiceCallback iTvInteractiveAppServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void createSession(android.view.InputChannel inputChannel, android.media.tv.interactive.ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void registerAppLinkInfo(android.media.tv.interactive.AppLinkInfo appLinkInfo) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void unregisterAppLinkInfo(android.media.tv.interactive.AppLinkInfo appLinkInfo) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void sendAppLinkCommand(android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.interactive.ITvInteractiveAppService {
        static final int TRANSACTION_createSession = 3;
        static final int TRANSACTION_registerAppLinkInfo = 4;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_sendAppLinkCommand = 6;
        static final int TRANSACTION_unregisterAppLinkInfo = 5;
        static final int TRANSACTION_unregisterCallback = 2;

        public Stub() {
            attachInterface(this, android.media.tv.interactive.ITvInteractiveAppService.DESCRIPTOR);
        }

        public static android.media.tv.interactive.ITvInteractiveAppService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.tv.interactive.ITvInteractiveAppService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.interactive.ITvInteractiveAppService)) {
                return (android.media.tv.interactive.ITvInteractiveAppService) queryLocalInterface;
            }
            return new android.media.tv.interactive.ITvInteractiveAppService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerCallback";
                case 2:
                    return "unregisterCallback";
                case 3:
                    return "createSession";
                case 4:
                    return "registerAppLinkInfo";
                case 5:
                    return "unregisterAppLinkInfo";
                case 6:
                    return "sendAppLinkCommand";
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
                parcel.enforceInterface(android.media.tv.interactive.ITvInteractiveAppService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.tv.interactive.ITvInteractiveAppService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.media.tv.interactive.ITvInteractiveAppServiceCallback asInterface = android.media.tv.interactive.ITvInteractiveAppServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface);
                    return true;
                case 2:
                    android.media.tv.interactive.ITvInteractiveAppServiceCallback asInterface2 = android.media.tv.interactive.ITvInteractiveAppServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(asInterface2);
                    return true;
                case 3:
                    android.view.InputChannel inputChannel = (android.view.InputChannel) parcel.readTypedObject(android.view.InputChannel.CREATOR);
                    android.media.tv.interactive.ITvInteractiveAppSessionCallback asInterface3 = android.media.tv.interactive.ITvInteractiveAppSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createSession(inputChannel, asInterface3, readString, readInt);
                    return true;
                case 4:
                    android.media.tv.interactive.AppLinkInfo appLinkInfo = (android.media.tv.interactive.AppLinkInfo) parcel.readTypedObject(android.media.tv.interactive.AppLinkInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerAppLinkInfo(appLinkInfo);
                    return true;
                case 5:
                    android.media.tv.interactive.AppLinkInfo appLinkInfo2 = (android.media.tv.interactive.AppLinkInfo) parcel.readTypedObject(android.media.tv.interactive.AppLinkInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    unregisterAppLinkInfo(appLinkInfo2);
                    return true;
                case 6:
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendAppLinkCommand(bundle);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.interactive.ITvInteractiveAppService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.interactive.ITvInteractiveAppService.DESCRIPTOR;
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void registerCallback(android.media.tv.interactive.ITvInteractiveAppServiceCallback iTvInteractiveAppServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppService.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvInteractiveAppServiceCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void unregisterCallback(android.media.tv.interactive.ITvInteractiveAppServiceCallback iTvInteractiveAppServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppService.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvInteractiveAppServiceCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void createSession(android.view.InputChannel inputChannel, android.media.tv.interactive.ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppService.DESCRIPTOR);
                    obtain.writeTypedObject(inputChannel, 0);
                    obtain.writeStrongInterface(iTvInteractiveAppSessionCallback);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void registerAppLinkInfo(android.media.tv.interactive.AppLinkInfo appLinkInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppService.DESCRIPTOR);
                    obtain.writeTypedObject(appLinkInfo, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void unregisterAppLinkInfo(android.media.tv.interactive.AppLinkInfo appLinkInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppService.DESCRIPTOR);
                    obtain.writeTypedObject(appLinkInfo, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void sendAppLinkCommand(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppService.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
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
