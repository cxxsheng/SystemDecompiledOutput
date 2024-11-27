package android.media.tv.ad;

/* loaded from: classes2.dex */
public interface ITvAdService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.tv.ad.ITvAdService";

    void createSession(android.view.InputChannel inputChannel, android.media.tv.ad.ITvAdSessionCallback iTvAdSessionCallback, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void registerCallback(android.media.tv.ad.ITvAdServiceCallback iTvAdServiceCallback) throws android.os.RemoteException;

    void sendAppLinkCommand(android.os.Bundle bundle) throws android.os.RemoteException;

    void unregisterCallback(android.media.tv.ad.ITvAdServiceCallback iTvAdServiceCallback) throws android.os.RemoteException;

    public static class Default implements android.media.tv.ad.ITvAdService {
        @Override // android.media.tv.ad.ITvAdService
        public void registerCallback(android.media.tv.ad.ITvAdServiceCallback iTvAdServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdService
        public void unregisterCallback(android.media.tv.ad.ITvAdServiceCallback iTvAdServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdService
        public void createSession(android.view.InputChannel inputChannel, android.media.tv.ad.ITvAdSessionCallback iTvAdSessionCallback, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdService
        public void sendAppLinkCommand(android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.ad.ITvAdService {
        static final int TRANSACTION_createSession = 3;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_sendAppLinkCommand = 4;
        static final int TRANSACTION_unregisterCallback = 2;

        public Stub() {
            attachInterface(this, android.media.tv.ad.ITvAdService.DESCRIPTOR);
        }

        public static android.media.tv.ad.ITvAdService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.tv.ad.ITvAdService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.ad.ITvAdService)) {
                return (android.media.tv.ad.ITvAdService) queryLocalInterface;
            }
            return new android.media.tv.ad.ITvAdService.Stub.Proxy(iBinder);
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
                parcel.enforceInterface(android.media.tv.ad.ITvAdService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.tv.ad.ITvAdService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.media.tv.ad.ITvAdServiceCallback asInterface = android.media.tv.ad.ITvAdServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface);
                    return true;
                case 2:
                    android.media.tv.ad.ITvAdServiceCallback asInterface2 = android.media.tv.ad.ITvAdServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(asInterface2);
                    return true;
                case 3:
                    android.view.InputChannel inputChannel = (android.view.InputChannel) parcel.readTypedObject(android.view.InputChannel.CREATOR);
                    android.media.tv.ad.ITvAdSessionCallback asInterface3 = android.media.tv.ad.ITvAdSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    createSession(inputChannel, asInterface3, readString, readString2);
                    return true;
                case 4:
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendAppLinkCommand(bundle);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.ad.ITvAdService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.ad.ITvAdService.DESCRIPTOR;
            }

            @Override // android.media.tv.ad.ITvAdService
            public void registerCallback(android.media.tv.ad.ITvAdServiceCallback iTvAdServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdService.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvAdServiceCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdService
            public void unregisterCallback(android.media.tv.ad.ITvAdServiceCallback iTvAdServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdService.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvAdServiceCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdService
            public void createSession(android.view.InputChannel inputChannel, android.media.tv.ad.ITvAdSessionCallback iTvAdSessionCallback, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdService.DESCRIPTOR);
                    obtain.writeTypedObject(inputChannel, 0);
                    obtain.writeStrongInterface(iTvAdSessionCallback);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdService
            public void sendAppLinkCommand(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdService.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
