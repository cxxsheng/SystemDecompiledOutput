package android.view.translation;

/* loaded from: classes4.dex */
public interface ITranslationManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.translation.ITranslationManager";

    void getServiceSettingsActivity(com.android.internal.os.IResultReceiver iResultReceiver, int i) throws android.os.RemoteException;

    void onSessionCreated(android.view.translation.TranslationContext translationContext, int i, com.android.internal.os.IResultReceiver iResultReceiver, int i2) throws android.os.RemoteException;

    void onTranslationCapabilitiesRequest(int i, int i2, android.os.ResultReceiver resultReceiver, int i3) throws android.os.RemoteException;

    void onTranslationFinished(boolean z, android.os.IBinder iBinder, android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    void registerTranslationCapabilityCallback(android.os.IRemoteCallback iRemoteCallback, int i) throws android.os.RemoteException;

    void registerUiTranslationStateCallback(android.os.IRemoteCallback iRemoteCallback, int i) throws android.os.RemoteException;

    void unregisterTranslationCapabilityCallback(android.os.IRemoteCallback iRemoteCallback, int i) throws android.os.RemoteException;

    void unregisterUiTranslationStateCallback(android.os.IRemoteCallback iRemoteCallback, int i) throws android.os.RemoteException;

    void updateUiTranslationState(int i, android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, java.util.List<android.view.autofill.AutofillId> list, android.os.IBinder iBinder, int i2, android.view.translation.UiTranslationSpec uiTranslationSpec, int i3) throws android.os.RemoteException;

    public static class Default implements android.view.translation.ITranslationManager {
        @Override // android.view.translation.ITranslationManager
        public void onTranslationCapabilitiesRequest(int i, int i2, android.os.ResultReceiver resultReceiver, int i3) throws android.os.RemoteException {
        }

        @Override // android.view.translation.ITranslationManager
        public void registerTranslationCapabilityCallback(android.os.IRemoteCallback iRemoteCallback, int i) throws android.os.RemoteException {
        }

        @Override // android.view.translation.ITranslationManager
        public void unregisterTranslationCapabilityCallback(android.os.IRemoteCallback iRemoteCallback, int i) throws android.os.RemoteException {
        }

        @Override // android.view.translation.ITranslationManager
        public void onSessionCreated(android.view.translation.TranslationContext translationContext, int i, com.android.internal.os.IResultReceiver iResultReceiver, int i2) throws android.os.RemoteException {
        }

        @Override // android.view.translation.ITranslationManager
        public void updateUiTranslationState(int i, android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, java.util.List<android.view.autofill.AutofillId> list, android.os.IBinder iBinder, int i2, android.view.translation.UiTranslationSpec uiTranslationSpec, int i3) throws android.os.RemoteException {
        }

        @Override // android.view.translation.ITranslationManager
        public void registerUiTranslationStateCallback(android.os.IRemoteCallback iRemoteCallback, int i) throws android.os.RemoteException {
        }

        @Override // android.view.translation.ITranslationManager
        public void unregisterUiTranslationStateCallback(android.os.IRemoteCallback iRemoteCallback, int i) throws android.os.RemoteException {
        }

        @Override // android.view.translation.ITranslationManager
        public void getServiceSettingsActivity(com.android.internal.os.IResultReceiver iResultReceiver, int i) throws android.os.RemoteException {
        }

        @Override // android.view.translation.ITranslationManager
        public void onTranslationFinished(boolean z, android.os.IBinder iBinder, android.content.ComponentName componentName, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.translation.ITranslationManager {
        static final int TRANSACTION_getServiceSettingsActivity = 8;
        static final int TRANSACTION_onSessionCreated = 4;
        static final int TRANSACTION_onTranslationCapabilitiesRequest = 1;
        static final int TRANSACTION_onTranslationFinished = 9;
        static final int TRANSACTION_registerTranslationCapabilityCallback = 2;
        static final int TRANSACTION_registerUiTranslationStateCallback = 6;
        static final int TRANSACTION_unregisterTranslationCapabilityCallback = 3;
        static final int TRANSACTION_unregisterUiTranslationStateCallback = 7;
        static final int TRANSACTION_updateUiTranslationState = 5;

        public Stub() {
            attachInterface(this, android.view.translation.ITranslationManager.DESCRIPTOR);
        }

        public static android.view.translation.ITranslationManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.translation.ITranslationManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.translation.ITranslationManager)) {
                return (android.view.translation.ITranslationManager) queryLocalInterface;
            }
            return new android.view.translation.ITranslationManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onTranslationCapabilitiesRequest";
                case 2:
                    return "registerTranslationCapabilityCallback";
                case 3:
                    return "unregisterTranslationCapabilityCallback";
                case 4:
                    return "onSessionCreated";
                case 5:
                    return "updateUiTranslationState";
                case 6:
                    return "registerUiTranslationStateCallback";
                case 7:
                    return "unregisterUiTranslationStateCallback";
                case 8:
                    return "getServiceSettingsActivity";
                case 9:
                    return "onTranslationFinished";
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
                parcel.enforceInterface(android.view.translation.ITranslationManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.translation.ITranslationManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTranslationCapabilitiesRequest(readInt, readInt2, resultReceiver, readInt3);
                    return true;
                case 2:
                    android.os.IRemoteCallback asInterface = android.os.IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerTranslationCapabilityCallback(asInterface, readInt4);
                    return true;
                case 3:
                    android.os.IRemoteCallback asInterface2 = android.os.IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterTranslationCapabilityCallback(asInterface2, readInt5);
                    return true;
                case 4:
                    android.view.translation.TranslationContext translationContext = (android.view.translation.TranslationContext) parcel.readTypedObject(android.view.translation.TranslationContext.CREATOR);
                    int readInt6 = parcel.readInt();
                    com.android.internal.os.IResultReceiver asInterface3 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionCreated(translationContext, readInt6, asInterface3, readInt7);
                    return true;
                case 5:
                    int readInt8 = parcel.readInt();
                    android.view.translation.TranslationSpec translationSpec = (android.view.translation.TranslationSpec) parcel.readTypedObject(android.view.translation.TranslationSpec.CREATOR);
                    android.view.translation.TranslationSpec translationSpec2 = (android.view.translation.TranslationSpec) parcel.readTypedObject(android.view.translation.TranslationSpec.CREATOR);
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.view.autofill.AutofillId.CREATOR);
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt9 = parcel.readInt();
                    android.view.translation.UiTranslationSpec uiTranslationSpec = (android.view.translation.UiTranslationSpec) parcel.readTypedObject(android.view.translation.UiTranslationSpec.CREATOR);
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateUiTranslationState(readInt8, translationSpec, translationSpec2, createTypedArrayList, readStrongBinder, readInt9, uiTranslationSpec, readInt10);
                    return true;
                case 6:
                    android.os.IRemoteCallback asInterface4 = android.os.IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerUiTranslationStateCallback(asInterface4, readInt11);
                    return true;
                case 7:
                    android.os.IRemoteCallback asInterface5 = android.os.IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterUiTranslationStateCallback(asInterface5, readInt12);
                    return true;
                case 8:
                    com.android.internal.os.IResultReceiver asInterface6 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getServiceSettingsActivity(asInterface6, readInt13);
                    return true;
                case 9:
                    boolean readBoolean = parcel.readBoolean();
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTranslationFinished(readBoolean, readStrongBinder2, componentName, readInt14);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.translation.ITranslationManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.translation.ITranslationManager.DESCRIPTOR;
            }

            @Override // android.view.translation.ITranslationManager
            public void onTranslationCapabilitiesRequest(int i, int i2, android.os.ResultReceiver resultReceiver, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.translation.ITranslationManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(resultReceiver, 0);
                    obtain.writeInt(i3);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void registerTranslationCapabilityCallback(android.os.IRemoteCallback iRemoteCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.translation.ITranslationManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void unregisterTranslationCapabilityCallback(android.os.IRemoteCallback iRemoteCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.translation.ITranslationManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void onSessionCreated(android.view.translation.TranslationContext translationContext, int i, com.android.internal.os.IResultReceiver iResultReceiver, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.translation.ITranslationManager.DESCRIPTOR);
                    obtain.writeTypedObject(translationContext, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iResultReceiver);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void updateUiTranslationState(int i, android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, java.util.List<android.view.autofill.AutofillId> list, android.os.IBinder iBinder, int i2, android.view.translation.UiTranslationSpec uiTranslationSpec, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.translation.ITranslationManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(translationSpec, 0);
                    obtain.writeTypedObject(translationSpec2, 0);
                    obtain.writeTypedList(list, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(uiTranslationSpec, 0);
                    obtain.writeInt(i3);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void registerUiTranslationStateCallback(android.os.IRemoteCallback iRemoteCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.translation.ITranslationManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void unregisterUiTranslationStateCallback(android.os.IRemoteCallback iRemoteCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.translation.ITranslationManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void getServiceSettingsActivity(com.android.internal.os.IResultReceiver iResultReceiver, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.translation.ITranslationManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void onTranslationFinished(boolean z, android.os.IBinder iBinder, android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.translation.ITranslationManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}
