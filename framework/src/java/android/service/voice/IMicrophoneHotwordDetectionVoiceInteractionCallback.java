package android.service.voice;

/* loaded from: classes3.dex */
public interface IMicrophoneHotwordDetectionVoiceInteractionCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback";

    void onDetected(android.service.voice.HotwordDetectedResult hotwordDetectedResult, android.media.AudioFormat audioFormat, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException;

    void onHotwordDetectionServiceFailure(android.service.voice.HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws android.os.RemoteException;

    void onRejected(android.service.voice.HotwordRejectedResult hotwordRejectedResult) throws android.os.RemoteException;

    public static class Default implements android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback {
        @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
        public void onDetected(android.service.voice.HotwordDetectedResult hotwordDetectedResult, android.media.AudioFormat audioFormat, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
        }

        @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
        public void onHotwordDetectionServiceFailure(android.service.voice.HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws android.os.RemoteException {
        }

        @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
        public void onRejected(android.service.voice.HotwordRejectedResult hotwordRejectedResult) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback {
        static final int TRANSACTION_onDetected = 1;
        static final int TRANSACTION_onHotwordDetectionServiceFailure = 2;
        static final int TRANSACTION_onRejected = 3;

        public Stub() {
            attachInterface(this, android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback.DESCRIPTOR);
        }

        public static android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback)) {
                return (android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback) queryLocalInterface;
            }
            return new android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDetected";
                case 2:
                    return "onHotwordDetectionServiceFailure";
                case 3:
                    return "onRejected";
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
                parcel.enforceInterface(android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.service.voice.HotwordDetectedResult hotwordDetectedResult = (android.service.voice.HotwordDetectedResult) parcel.readTypedObject(android.service.voice.HotwordDetectedResult.CREATOR);
                    android.media.AudioFormat audioFormat = (android.media.AudioFormat) parcel.readTypedObject(android.media.AudioFormat.CREATOR);
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDetected(hotwordDetectedResult, audioFormat, parcelFileDescriptor);
                    return true;
                case 2:
                    android.service.voice.HotwordDetectionServiceFailure hotwordDetectionServiceFailure = (android.service.voice.HotwordDetectionServiceFailure) parcel.readTypedObject(android.service.voice.HotwordDetectionServiceFailure.CREATOR);
                    parcel.enforceNoDataAvail();
                    onHotwordDetectionServiceFailure(hotwordDetectionServiceFailure);
                    return true;
                case 3:
                    android.service.voice.HotwordRejectedResult hotwordRejectedResult = (android.service.voice.HotwordRejectedResult) parcel.readTypedObject(android.service.voice.HotwordRejectedResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRejected(hotwordRejectedResult);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback.DESCRIPTOR;
            }

            @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
            public void onDetected(android.service.voice.HotwordDetectedResult hotwordDetectedResult, android.media.AudioFormat audioFormat, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(hotwordDetectedResult, 0);
                    obtain.writeTypedObject(audioFormat, 0);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
            public void onHotwordDetectionServiceFailure(android.service.voice.HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(hotwordDetectionServiceFailure, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
            public void onRejected(android.service.voice.HotwordRejectedResult hotwordRejectedResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(hotwordRejectedResult, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
