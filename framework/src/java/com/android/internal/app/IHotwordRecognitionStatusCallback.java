package com.android.internal.app;

/* loaded from: classes4.dex */
public interface IHotwordRecognitionStatusCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.app.IHotwordRecognitionStatusCallback";

    void onGenericSoundTriggerDetected(android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws android.os.RemoteException;

    void onHotwordDetectionServiceFailure(android.service.voice.HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws android.os.RemoteException;

    void onKeyphraseDetected(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, android.service.voice.HotwordDetectedResult hotwordDetectedResult) throws android.os.RemoteException;

    void onKeyphraseDetectedFromExternalSource(android.service.voice.HotwordDetectedResult hotwordDetectedResult) throws android.os.RemoteException;

    void onOpenFile(java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void onProcessRestarted() throws android.os.RemoteException;

    void onRecognitionPaused() throws android.os.RemoteException;

    void onRecognitionResumed() throws android.os.RemoteException;

    void onRejected(android.service.voice.HotwordRejectedResult hotwordRejectedResult) throws android.os.RemoteException;

    void onSoundTriggerFailure(android.service.voice.SoundTriggerFailure soundTriggerFailure) throws android.os.RemoteException;

    void onStatusReported(int i) throws android.os.RemoteException;

    void onUnknownFailure(java.lang.String str) throws android.os.RemoteException;

    void onVisualQueryDetectionServiceFailure(android.service.voice.VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws android.os.RemoteException;

    public static class Default implements com.android.internal.app.IHotwordRecognitionStatusCallback {
        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onKeyphraseDetected(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, android.service.voice.HotwordDetectedResult hotwordDetectedResult) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onKeyphraseDetectedFromExternalSource(android.service.voice.HotwordDetectedResult hotwordDetectedResult) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onGenericSoundTriggerDetected(android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRejected(android.service.voice.HotwordRejectedResult hotwordRejectedResult) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onHotwordDetectionServiceFailure(android.service.voice.HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onVisualQueryDetectionServiceFailure(android.service.voice.VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onSoundTriggerFailure(android.service.voice.SoundTriggerFailure soundTriggerFailure) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onUnknownFailure(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRecognitionPaused() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRecognitionResumed() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onStatusReported(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onProcessRestarted() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onOpenFile(java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.app.IHotwordRecognitionStatusCallback {
        static final int TRANSACTION_onGenericSoundTriggerDetected = 3;
        static final int TRANSACTION_onHotwordDetectionServiceFailure = 5;
        static final int TRANSACTION_onKeyphraseDetected = 1;
        static final int TRANSACTION_onKeyphraseDetectedFromExternalSource = 2;
        static final int TRANSACTION_onOpenFile = 13;
        static final int TRANSACTION_onProcessRestarted = 12;
        static final int TRANSACTION_onRecognitionPaused = 9;
        static final int TRANSACTION_onRecognitionResumed = 10;
        static final int TRANSACTION_onRejected = 4;
        static final int TRANSACTION_onSoundTriggerFailure = 7;
        static final int TRANSACTION_onStatusReported = 11;
        static final int TRANSACTION_onUnknownFailure = 8;
        static final int TRANSACTION_onVisualQueryDetectionServiceFailure = 6;

        public Stub() {
            attachInterface(this, com.android.internal.app.IHotwordRecognitionStatusCallback.DESCRIPTOR);
        }

        public static com.android.internal.app.IHotwordRecognitionStatusCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.app.IHotwordRecognitionStatusCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.app.IHotwordRecognitionStatusCallback)) {
                return (com.android.internal.app.IHotwordRecognitionStatusCallback) queryLocalInterface;
            }
            return new com.android.internal.app.IHotwordRecognitionStatusCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onKeyphraseDetected";
                case 2:
                    return "onKeyphraseDetectedFromExternalSource";
                case 3:
                    return "onGenericSoundTriggerDetected";
                case 4:
                    return "onRejected";
                case 5:
                    return "onHotwordDetectionServiceFailure";
                case 6:
                    return "onVisualQueryDetectionServiceFailure";
                case 7:
                    return "onSoundTriggerFailure";
                case 8:
                    return "onUnknownFailure";
                case 9:
                    return "onRecognitionPaused";
                case 10:
                    return "onRecognitionResumed";
                case 11:
                    return "onStatusReported";
                case 12:
                    return "onProcessRestarted";
                case 13:
                    return "onOpenFile";
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
                parcel.enforceInterface(com.android.internal.app.IHotwordRecognitionStatusCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.app.IHotwordRecognitionStatusCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent = (android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent) parcel.readTypedObject(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent.CREATOR);
                    android.service.voice.HotwordDetectedResult hotwordDetectedResult = (android.service.voice.HotwordDetectedResult) parcel.readTypedObject(android.service.voice.HotwordDetectedResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onKeyphraseDetected(keyphraseRecognitionEvent, hotwordDetectedResult);
                    return true;
                case 2:
                    android.service.voice.HotwordDetectedResult hotwordDetectedResult2 = (android.service.voice.HotwordDetectedResult) parcel.readTypedObject(android.service.voice.HotwordDetectedResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onKeyphraseDetectedFromExternalSource(hotwordDetectedResult2);
                    return true;
                case 3:
                    android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent = (android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent) parcel.readTypedObject(android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onGenericSoundTriggerDetected(genericRecognitionEvent);
                    return true;
                case 4:
                    android.service.voice.HotwordRejectedResult hotwordRejectedResult = (android.service.voice.HotwordRejectedResult) parcel.readTypedObject(android.service.voice.HotwordRejectedResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRejected(hotwordRejectedResult);
                    return true;
                case 5:
                    android.service.voice.HotwordDetectionServiceFailure hotwordDetectionServiceFailure = (android.service.voice.HotwordDetectionServiceFailure) parcel.readTypedObject(android.service.voice.HotwordDetectionServiceFailure.CREATOR);
                    parcel.enforceNoDataAvail();
                    onHotwordDetectionServiceFailure(hotwordDetectionServiceFailure);
                    return true;
                case 6:
                    android.service.voice.VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure = (android.service.voice.VisualQueryDetectionServiceFailure) parcel.readTypedObject(android.service.voice.VisualQueryDetectionServiceFailure.CREATOR);
                    parcel.enforceNoDataAvail();
                    onVisualQueryDetectionServiceFailure(visualQueryDetectionServiceFailure);
                    return true;
                case 7:
                    android.service.voice.SoundTriggerFailure soundTriggerFailure = (android.service.voice.SoundTriggerFailure) parcel.readTypedObject(android.service.voice.SoundTriggerFailure.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSoundTriggerFailure(soundTriggerFailure);
                    return true;
                case 8:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onUnknownFailure(readString);
                    return true;
                case 9:
                    onRecognitionPaused();
                    return true;
                case 10:
                    onRecognitionResumed();
                    return true;
                case 11:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStatusReported(readInt);
                    return true;
                case 12:
                    onProcessRestarted();
                    return true;
                case 13:
                    java.lang.String readString2 = parcel.readString();
                    com.android.internal.infra.AndroidFuture androidFuture = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    onOpenFile(readString2, androidFuture);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.app.IHotwordRecognitionStatusCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.app.IHotwordRecognitionStatusCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onKeyphraseDetected(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, android.service.voice.HotwordDetectedResult hotwordDetectedResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    obtain.writeTypedObject(keyphraseRecognitionEvent, 0);
                    obtain.writeTypedObject(hotwordDetectedResult, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onKeyphraseDetectedFromExternalSource(android.service.voice.HotwordDetectedResult hotwordDetectedResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    obtain.writeTypedObject(hotwordDetectedResult, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onGenericSoundTriggerDetected(android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    obtain.writeTypedObject(genericRecognitionEvent, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onRejected(android.service.voice.HotwordRejectedResult hotwordRejectedResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    obtain.writeTypedObject(hotwordRejectedResult, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onHotwordDetectionServiceFailure(android.service.voice.HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    obtain.writeTypedObject(hotwordDetectionServiceFailure, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onVisualQueryDetectionServiceFailure(android.service.voice.VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    obtain.writeTypedObject(visualQueryDetectionServiceFailure, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onSoundTriggerFailure(android.service.voice.SoundTriggerFailure soundTriggerFailure) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    obtain.writeTypedObject(soundTriggerFailure, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onUnknownFailure(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onRecognitionPaused() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onRecognitionResumed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onStatusReported(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onProcessRestarted() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onOpenFile(java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }
    }
}
