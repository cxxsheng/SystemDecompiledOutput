package android.media.soundtrigger_middleware;

/* loaded from: classes2.dex */
public interface ISoundTriggerMiddlewareService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService";

    android.media.soundtrigger_middleware.ISoundTriggerModule attachAsMiddleman(int i, android.media.permission.Identity identity, android.media.permission.Identity identity2, android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback, boolean z) throws android.os.RemoteException;

    android.media.soundtrigger_middleware.ISoundTriggerModule attachAsOriginator(int i, android.media.permission.Identity identity, android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback) throws android.os.RemoteException;

    void attachFakeHalInjection(android.media.soundtrigger_middleware.ISoundTriggerInjection iSoundTriggerInjection) throws android.os.RemoteException;

    android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] listModulesAsMiddleman(android.media.permission.Identity identity, android.media.permission.Identity identity2) throws android.os.RemoteException;

    android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] listModulesAsOriginator(android.media.permission.Identity identity) throws android.os.RemoteException;

    public static class Default implements android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService {
        @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
        public android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] listModulesAsOriginator(android.media.permission.Identity identity) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
        public android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] listModulesAsMiddleman(android.media.permission.Identity identity, android.media.permission.Identity identity2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
        public android.media.soundtrigger_middleware.ISoundTriggerModule attachAsOriginator(int i, android.media.permission.Identity identity, android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
        public android.media.soundtrigger_middleware.ISoundTriggerModule attachAsMiddleman(int i, android.media.permission.Identity identity, android.media.permission.Identity identity2, android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
        public void attachFakeHalInjection(android.media.soundtrigger_middleware.ISoundTriggerInjection iSoundTriggerInjection) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService {
        static final int TRANSACTION_attachAsMiddleman = 4;
        static final int TRANSACTION_attachAsOriginator = 3;
        static final int TRANSACTION_attachFakeHalInjection = 5;
        static final int TRANSACTION_listModulesAsMiddleman = 2;
        static final int TRANSACTION_listModulesAsOriginator = 1;

        public Stub() {
            attachInterface(this, android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService.DESCRIPTOR);
        }

        public static android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService)) {
                return (android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService) queryLocalInterface;
            }
            return new android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.media.permission.Identity identity = (android.media.permission.Identity) parcel.readTypedObject(android.media.permission.Identity.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] listModulesAsOriginator = listModulesAsOriginator(identity);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(listModulesAsOriginator, 1);
                    return true;
                case 2:
                    android.media.permission.Identity identity2 = (android.media.permission.Identity) parcel.readTypedObject(android.media.permission.Identity.CREATOR);
                    android.media.permission.Identity identity3 = (android.media.permission.Identity) parcel.readTypedObject(android.media.permission.Identity.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] listModulesAsMiddleman = listModulesAsMiddleman(identity2, identity3);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(listModulesAsMiddleman, 1);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    android.media.permission.Identity identity4 = (android.media.permission.Identity) parcel.readTypedObject(android.media.permission.Identity.CREATOR);
                    android.media.soundtrigger_middleware.ISoundTriggerCallback asInterface = android.media.soundtrigger_middleware.ISoundTriggerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    android.media.soundtrigger_middleware.ISoundTriggerModule attachAsOriginator = attachAsOriginator(readInt, identity4, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(attachAsOriginator);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    android.media.permission.Identity identity5 = (android.media.permission.Identity) parcel.readTypedObject(android.media.permission.Identity.CREATOR);
                    android.media.permission.Identity identity6 = (android.media.permission.Identity) parcel.readTypedObject(android.media.permission.Identity.CREATOR);
                    android.media.soundtrigger_middleware.ISoundTriggerCallback asInterface2 = android.media.soundtrigger_middleware.ISoundTriggerCallback.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.media.soundtrigger_middleware.ISoundTriggerModule attachAsMiddleman = attachAsMiddleman(readInt2, identity5, identity6, asInterface2, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(attachAsMiddleman);
                    return true;
                case 5:
                    android.media.soundtrigger_middleware.ISoundTriggerInjection asInterface3 = android.media.soundtrigger_middleware.ISoundTriggerInjection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    attachFakeHalInjection(asInterface3);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService.DESCRIPTOR;
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
            public android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] listModulesAsOriginator(android.media.permission.Identity identity) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService.DESCRIPTOR);
                    obtain.writeTypedObject(identity, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[]) obtain2.createTypedArray(android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
            public android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] listModulesAsMiddleman(android.media.permission.Identity identity, android.media.permission.Identity identity2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService.DESCRIPTOR);
                    obtain.writeTypedObject(identity, 0);
                    obtain.writeTypedObject(identity2, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[]) obtain2.createTypedArray(android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
            public android.media.soundtrigger_middleware.ISoundTriggerModule attachAsOriginator(int i, android.media.permission.Identity identity, android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(identity, 0);
                    obtain.writeStrongInterface(iSoundTriggerCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.media.soundtrigger_middleware.ISoundTriggerModule.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
            public android.media.soundtrigger_middleware.ISoundTriggerModule attachAsMiddleman(int i, android.media.permission.Identity identity, android.media.permission.Identity identity2, android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(identity, 0);
                    obtain.writeTypedObject(identity2, 0);
                    obtain.writeStrongInterface(iSoundTriggerCallback);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.media.soundtrigger_middleware.ISoundTriggerModule.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
            public void attachFakeHalInjection(android.media.soundtrigger_middleware.ISoundTriggerInjection iSoundTriggerInjection) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService.DESCRIPTOR);
                    obtain.writeStrongInterface(iSoundTriggerInjection);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
