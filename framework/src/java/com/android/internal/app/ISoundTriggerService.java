package com.android.internal.app;

/* loaded from: classes4.dex */
public interface ISoundTriggerService extends android.os.IInterface {
    com.android.internal.app.ISoundTriggerSession attachAsMiddleman(android.media.permission.Identity identity, android.media.permission.Identity identity2, android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties, android.os.IBinder iBinder) throws android.os.RemoteException;

    com.android.internal.app.ISoundTriggerSession attachAsOriginator(android.media.permission.Identity identity, android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties, android.os.IBinder iBinder) throws android.os.RemoteException;

    void attachInjection(android.media.soundtrigger_middleware.ISoundTriggerInjection iSoundTriggerInjection) throws android.os.RemoteException;

    java.util.List<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> listModuleProperties(android.media.permission.Identity identity) throws android.os.RemoteException;

    void setInPhoneCallState(boolean z) throws android.os.RemoteException;

    public static class Default implements com.android.internal.app.ISoundTriggerService {
        @Override // com.android.internal.app.ISoundTriggerService
        public com.android.internal.app.ISoundTriggerSession attachAsOriginator(android.media.permission.Identity identity, android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties, android.os.IBinder iBinder) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.ISoundTriggerService
        public com.android.internal.app.ISoundTriggerSession attachAsMiddleman(android.media.permission.Identity identity, android.media.permission.Identity identity2, android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties, android.os.IBinder iBinder) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.ISoundTriggerService
        public java.util.List<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> listModuleProperties(android.media.permission.Identity identity) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.ISoundTriggerService
        public void attachInjection(android.media.soundtrigger_middleware.ISoundTriggerInjection iSoundTriggerInjection) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.ISoundTriggerService
        public void setInPhoneCallState(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.app.ISoundTriggerService {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.app.ISoundTriggerService";
        static final int TRANSACTION_attachAsMiddleman = 2;
        static final int TRANSACTION_attachAsOriginator = 1;
        static final int TRANSACTION_attachInjection = 4;
        static final int TRANSACTION_listModuleProperties = 3;
        static final int TRANSACTION_setInPhoneCallState = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.app.ISoundTriggerService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.app.ISoundTriggerService)) {
                return (com.android.internal.app.ISoundTriggerService) queryLocalInterface;
            }
            return new com.android.internal.app.ISoundTriggerService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "attachAsOriginator";
                case 2:
                    return "attachAsMiddleman";
                case 3:
                    return "listModuleProperties";
                case 4:
                    return "attachInjection";
                case 5:
                    return "setInPhoneCallState";
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
                    android.media.permission.Identity identity = (android.media.permission.Identity) parcel.readTypedObject(android.media.permission.Identity.CREATOR);
                    android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties = (android.hardware.soundtrigger.SoundTrigger.ModuleProperties) parcel.readTypedObject(android.hardware.soundtrigger.SoundTrigger.ModuleProperties.CREATOR);
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    com.android.internal.app.ISoundTriggerSession attachAsOriginator = attachAsOriginator(identity, moduleProperties, readStrongBinder);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(attachAsOriginator);
                    return true;
                case 2:
                    android.media.permission.Identity identity2 = (android.media.permission.Identity) parcel.readTypedObject(android.media.permission.Identity.CREATOR);
                    android.media.permission.Identity identity3 = (android.media.permission.Identity) parcel.readTypedObject(android.media.permission.Identity.CREATOR);
                    android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties2 = (android.hardware.soundtrigger.SoundTrigger.ModuleProperties) parcel.readTypedObject(android.hardware.soundtrigger.SoundTrigger.ModuleProperties.CREATOR);
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    com.android.internal.app.ISoundTriggerSession attachAsMiddleman = attachAsMiddleman(identity2, identity3, moduleProperties2, readStrongBinder2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(attachAsMiddleman);
                    return true;
                case 3:
                    android.media.permission.Identity identity4 = (android.media.permission.Identity) parcel.readTypedObject(android.media.permission.Identity.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> listModuleProperties = listModuleProperties(identity4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(listModuleProperties, 1);
                    return true;
                case 4:
                    android.media.soundtrigger_middleware.ISoundTriggerInjection asInterface = android.media.soundtrigger_middleware.ISoundTriggerInjection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    attachInjection(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInPhoneCallState(readBoolean);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.app.ISoundTriggerService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.app.ISoundTriggerService.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.app.ISoundTriggerService
            public com.android.internal.app.ISoundTriggerSession attachAsOriginator(android.media.permission.Identity identity, android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ISoundTriggerService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(identity, 0);
                    obtain.writeTypedObject(moduleProperties, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.internal.app.ISoundTriggerSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerService
            public com.android.internal.app.ISoundTriggerSession attachAsMiddleman(android.media.permission.Identity identity, android.media.permission.Identity identity2, android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ISoundTriggerService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(identity, 0);
                    obtain.writeTypedObject(identity2, 0);
                    obtain.writeTypedObject(moduleProperties, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.internal.app.ISoundTriggerSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerService
            public java.util.List<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> listModuleProperties(android.media.permission.Identity identity) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ISoundTriggerService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(identity, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.soundtrigger.SoundTrigger.ModuleProperties.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerService
            public void attachInjection(android.media.soundtrigger_middleware.ISoundTriggerInjection iSoundTriggerInjection) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ISoundTriggerService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSoundTriggerInjection);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerService
            public void setInPhoneCallState(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ISoundTriggerService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
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
