package android.media.soundtrigger;

/* loaded from: classes2.dex */
public interface ISoundTriggerDetectionService extends android.os.IInterface {
    void onError(android.os.ParcelUuid parcelUuid, int i, int i2) throws android.os.RemoteException;

    void onGenericRecognitionEvent(android.os.ParcelUuid parcelUuid, int i, android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws android.os.RemoteException;

    void onStopOperation(android.os.ParcelUuid parcelUuid, int i) throws android.os.RemoteException;

    void removeClient(android.os.ParcelUuid parcelUuid) throws android.os.RemoteException;

    void setClient(android.os.ParcelUuid parcelUuid, android.os.Bundle bundle, android.media.soundtrigger.ISoundTriggerDetectionServiceClient iSoundTriggerDetectionServiceClient) throws android.os.RemoteException;

    public static class Default implements android.media.soundtrigger.ISoundTriggerDetectionService {
        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void setClient(android.os.ParcelUuid parcelUuid, android.os.Bundle bundle, android.media.soundtrigger.ISoundTriggerDetectionServiceClient iSoundTriggerDetectionServiceClient) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void removeClient(android.os.ParcelUuid parcelUuid) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void onGenericRecognitionEvent(android.os.ParcelUuid parcelUuid, int i, android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void onError(android.os.ParcelUuid parcelUuid, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void onStopOperation(android.os.ParcelUuid parcelUuid, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.soundtrigger.ISoundTriggerDetectionService {
        public static final java.lang.String DESCRIPTOR = "android.media.soundtrigger.ISoundTriggerDetectionService";
        static final int TRANSACTION_onError = 4;
        static final int TRANSACTION_onGenericRecognitionEvent = 3;
        static final int TRANSACTION_onStopOperation = 5;
        static final int TRANSACTION_removeClient = 2;
        static final int TRANSACTION_setClient = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.soundtrigger.ISoundTriggerDetectionService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.soundtrigger.ISoundTriggerDetectionService)) {
                return (android.media.soundtrigger.ISoundTriggerDetectionService) queryLocalInterface;
            }
            return new android.media.soundtrigger.ISoundTriggerDetectionService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setClient";
                case 2:
                    return "removeClient";
                case 3:
                    return "onGenericRecognitionEvent";
                case 4:
                    return "onError";
                case 5:
                    return "onStopOperation";
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
                    android.os.ParcelUuid parcelUuid = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.media.soundtrigger.ISoundTriggerDetectionServiceClient asInterface = android.media.soundtrigger.ISoundTriggerDetectionServiceClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setClient(parcelUuid, bundle, asInterface);
                    return true;
                case 2:
                    android.os.ParcelUuid parcelUuid2 = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeClient(parcelUuid2);
                    return true;
                case 3:
                    android.os.ParcelUuid parcelUuid3 = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    int readInt = parcel.readInt();
                    android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent = (android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent) parcel.readTypedObject(android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onGenericRecognitionEvent(parcelUuid3, readInt, genericRecognitionEvent);
                    return true;
                case 4:
                    android.os.ParcelUuid parcelUuid4 = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(parcelUuid4, readInt2, readInt3);
                    return true;
                case 5:
                    android.os.ParcelUuid parcelUuid5 = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStopOperation(parcelUuid5, readInt4);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.soundtrigger.ISoundTriggerDetectionService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.soundtrigger.ISoundTriggerDetectionService.Stub.DESCRIPTOR;
            }

            @Override // android.media.soundtrigger.ISoundTriggerDetectionService
            public void setClient(android.os.ParcelUuid parcelUuid, android.os.Bundle bundle, android.media.soundtrigger.ISoundTriggerDetectionServiceClient iSoundTriggerDetectionServiceClient) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger.ISoundTriggerDetectionService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iSoundTriggerDetectionServiceClient);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger.ISoundTriggerDetectionService
            public void removeClient(android.os.ParcelUuid parcelUuid) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger.ISoundTriggerDetectionService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger.ISoundTriggerDetectionService
            public void onGenericRecognitionEvent(android.os.ParcelUuid parcelUuid, int i, android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger.ISoundTriggerDetectionService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(genericRecognitionEvent, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger.ISoundTriggerDetectionService
            public void onError(android.os.ParcelUuid parcelUuid, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger.ISoundTriggerDetectionService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger.ISoundTriggerDetectionService
            public void onStopOperation(android.os.ParcelUuid parcelUuid, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger.ISoundTriggerDetectionService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
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
