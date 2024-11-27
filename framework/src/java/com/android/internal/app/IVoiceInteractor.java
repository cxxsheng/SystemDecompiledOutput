package com.android.internal.app;

/* loaded from: classes4.dex */
public interface IVoiceInteractor extends android.os.IInterface {
    void notifyDirectActionsChanged(int i, android.os.IBinder iBinder) throws android.os.RemoteException;

    void setKillCallback(android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException;

    com.android.internal.app.IVoiceInteractorRequest startAbortVoice(java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.app.VoiceInteractor.Prompt prompt, android.os.Bundle bundle) throws android.os.RemoteException;

    com.android.internal.app.IVoiceInteractorRequest startCommand(java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException;

    com.android.internal.app.IVoiceInteractorRequest startCompleteVoice(java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.app.VoiceInteractor.Prompt prompt, android.os.Bundle bundle) throws android.os.RemoteException;

    com.android.internal.app.IVoiceInteractorRequest startConfirmation(java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.app.VoiceInteractor.Prompt prompt, android.os.Bundle bundle) throws android.os.RemoteException;

    com.android.internal.app.IVoiceInteractorRequest startPickOption(java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.app.VoiceInteractor.Prompt prompt, android.app.VoiceInteractor.PickOptionRequest.Option[] optionArr, android.os.Bundle bundle) throws android.os.RemoteException;

    boolean[] supportsCommands(java.lang.String str, java.lang.String[] strArr) throws android.os.RemoteException;

    public static class Default implements com.android.internal.app.IVoiceInteractor {
        @Override // com.android.internal.app.IVoiceInteractor
        public com.android.internal.app.IVoiceInteractorRequest startConfirmation(java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.app.VoiceInteractor.Prompt prompt, android.os.Bundle bundle) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractor
        public com.android.internal.app.IVoiceInteractorRequest startPickOption(java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.app.VoiceInteractor.Prompt prompt, android.app.VoiceInteractor.PickOptionRequest.Option[] optionArr, android.os.Bundle bundle) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractor
        public com.android.internal.app.IVoiceInteractorRequest startCompleteVoice(java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.app.VoiceInteractor.Prompt prompt, android.os.Bundle bundle) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractor
        public com.android.internal.app.IVoiceInteractorRequest startAbortVoice(java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.app.VoiceInteractor.Prompt prompt, android.os.Bundle bundle) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractor
        public com.android.internal.app.IVoiceInteractorRequest startCommand(java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractor
        public boolean[] supportsCommands(java.lang.String str, java.lang.String[] strArr) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractor
        public void notifyDirectActionsChanged(int i, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractor
        public void setKillCallback(android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.app.IVoiceInteractor {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.app.IVoiceInteractor";
        static final int TRANSACTION_notifyDirectActionsChanged = 7;
        static final int TRANSACTION_setKillCallback = 8;
        static final int TRANSACTION_startAbortVoice = 4;
        static final int TRANSACTION_startCommand = 5;
        static final int TRANSACTION_startCompleteVoice = 3;
        static final int TRANSACTION_startConfirmation = 1;
        static final int TRANSACTION_startPickOption = 2;
        static final int TRANSACTION_supportsCommands = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.app.IVoiceInteractor asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.app.IVoiceInteractor)) {
                return (com.android.internal.app.IVoiceInteractor) queryLocalInterface;
            }
            return new com.android.internal.app.IVoiceInteractor.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startConfirmation";
                case 2:
                    return "startPickOption";
                case 3:
                    return "startCompleteVoice";
                case 4:
                    return "startAbortVoice";
                case 5:
                    return "startCommand";
                case 6:
                    return "supportsCommands";
                case 7:
                    return "notifyDirectActionsChanged";
                case 8:
                    return "setKillCallback";
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
                    java.lang.String readString = parcel.readString();
                    com.android.internal.app.IVoiceInteractorCallback asInterface = com.android.internal.app.IVoiceInteractorCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.app.VoiceInteractor.Prompt prompt = (android.app.VoiceInteractor.Prompt) parcel.readTypedObject(android.app.VoiceInteractor.Prompt.CREATOR);
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    com.android.internal.app.IVoiceInteractorRequest startConfirmation = startConfirmation(readString, asInterface, prompt, bundle);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(startConfirmation);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    com.android.internal.app.IVoiceInteractorCallback asInterface2 = com.android.internal.app.IVoiceInteractorCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.app.VoiceInteractor.Prompt prompt2 = (android.app.VoiceInteractor.Prompt) parcel.readTypedObject(android.app.VoiceInteractor.Prompt.CREATOR);
                    android.app.VoiceInteractor.PickOptionRequest.Option[] optionArr = (android.app.VoiceInteractor.PickOptionRequest.Option[]) parcel.createTypedArray(android.app.VoiceInteractor.PickOptionRequest.Option.CREATOR);
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    com.android.internal.app.IVoiceInteractorRequest startPickOption = startPickOption(readString2, asInterface2, prompt2, optionArr, bundle2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(startPickOption);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    com.android.internal.app.IVoiceInteractorCallback asInterface3 = com.android.internal.app.IVoiceInteractorCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.app.VoiceInteractor.Prompt prompt3 = (android.app.VoiceInteractor.Prompt) parcel.readTypedObject(android.app.VoiceInteractor.Prompt.CREATOR);
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    com.android.internal.app.IVoiceInteractorRequest startCompleteVoice = startCompleteVoice(readString3, asInterface3, prompt3, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(startCompleteVoice);
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    com.android.internal.app.IVoiceInteractorCallback asInterface4 = com.android.internal.app.IVoiceInteractorCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.app.VoiceInteractor.Prompt prompt4 = (android.app.VoiceInteractor.Prompt) parcel.readTypedObject(android.app.VoiceInteractor.Prompt.CREATOR);
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    com.android.internal.app.IVoiceInteractorRequest startAbortVoice = startAbortVoice(readString4, asInterface4, prompt4, bundle4);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(startAbortVoice);
                    return true;
                case 5:
                    java.lang.String readString5 = parcel.readString();
                    com.android.internal.app.IVoiceInteractorCallback asInterface5 = com.android.internal.app.IVoiceInteractorCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString6 = parcel.readString();
                    android.os.Bundle bundle5 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    com.android.internal.app.IVoiceInteractorRequest startCommand = startCommand(readString5, asInterface5, readString6, bundle5);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(startCommand);
                    return true;
                case 6:
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    boolean[] supportsCommands = supportsCommands(readString7, createStringArray);
                    parcel2.writeNoException();
                    parcel2.writeBooleanArray(supportsCommands);
                    return true;
                case 7:
                    int readInt = parcel.readInt();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    notifyDirectActionsChanged(readInt, readStrongBinder);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.os.ICancellationSignal asInterface6 = android.os.ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setKillCallback(asInterface6);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.app.IVoiceInteractor {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.app.IVoiceInteractor.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public com.android.internal.app.IVoiceInteractorRequest startConfirmation(java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.app.VoiceInteractor.Prompt prompt, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractor.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iVoiceInteractorCallback);
                    obtain.writeTypedObject(prompt, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.internal.app.IVoiceInteractorRequest.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public com.android.internal.app.IVoiceInteractorRequest startPickOption(java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.app.VoiceInteractor.Prompt prompt, android.app.VoiceInteractor.PickOptionRequest.Option[] optionArr, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractor.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iVoiceInteractorCallback);
                    obtain.writeTypedObject(prompt, 0);
                    obtain.writeTypedArray(optionArr, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.internal.app.IVoiceInteractorRequest.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public com.android.internal.app.IVoiceInteractorRequest startCompleteVoice(java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.app.VoiceInteractor.Prompt prompt, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractor.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iVoiceInteractorCallback);
                    obtain.writeTypedObject(prompt, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.internal.app.IVoiceInteractorRequest.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public com.android.internal.app.IVoiceInteractorRequest startAbortVoice(java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.app.VoiceInteractor.Prompt prompt, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractor.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iVoiceInteractorCallback);
                    obtain.writeTypedObject(prompt, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.internal.app.IVoiceInteractorRequest.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public com.android.internal.app.IVoiceInteractorRequest startCommand(java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractor.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iVoiceInteractorCallback);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.internal.app.IVoiceInteractorRequest.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public boolean[] supportsCommands(java.lang.String str, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractor.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createBooleanArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public void notifyDirectActionsChanged(int i, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractor.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public void setKillCallback(android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractor.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCancellationSignal);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
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
