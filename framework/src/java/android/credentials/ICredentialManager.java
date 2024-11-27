package android.credentials;

/* loaded from: classes.dex */
public interface ICredentialManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.credentials.ICredentialManager";

    android.os.ICancellationSignal clearCredentialState(android.credentials.ClearCredentialStateRequest clearCredentialStateRequest, android.credentials.IClearCredentialStateCallback iClearCredentialStateCallback, java.lang.String str) throws android.os.RemoteException;

    android.os.ICancellationSignal executeCreateCredential(android.credentials.CreateCredentialRequest createCredentialRequest, android.credentials.ICreateCredentialCallback iCreateCredentialCallback, java.lang.String str) throws android.os.RemoteException;

    android.os.ICancellationSignal executeGetCredential(android.credentials.GetCredentialRequest getCredentialRequest, android.credentials.IGetCredentialCallback iGetCredentialCallback, java.lang.String str) throws android.os.RemoteException;

    android.os.ICancellationSignal executePrepareGetCredential(android.credentials.GetCredentialRequest getCredentialRequest, android.credentials.IPrepareGetCredentialCallback iPrepareGetCredentialCallback, android.credentials.IGetCredentialCallback iGetCredentialCallback, java.lang.String str) throws android.os.RemoteException;

    android.os.ICancellationSignal getCandidateCredentials(android.credentials.GetCredentialRequest getCredentialRequest, android.credentials.IGetCandidateCredentialsCallback iGetCandidateCredentialsCallback, android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException;

    java.util.List<android.credentials.CredentialProviderInfo> getCredentialProviderServices(int i, int i2) throws android.os.RemoteException;

    java.util.List<android.credentials.CredentialProviderInfo> getCredentialProviderServicesForTesting(int i) throws android.os.RemoteException;

    boolean isEnabledCredentialProviderService(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    boolean isServiceEnabled() throws android.os.RemoteException;

    void registerCredentialDescription(android.credentials.RegisterCredentialDescriptionRequest registerCredentialDescriptionRequest, java.lang.String str) throws android.os.RemoteException;

    void setEnabledProviders(java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2, int i, android.credentials.ISetEnabledProvidersCallback iSetEnabledProvidersCallback) throws android.os.RemoteException;

    void unregisterCredentialDescription(android.credentials.UnregisterCredentialDescriptionRequest unregisterCredentialDescriptionRequest, java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.credentials.ICredentialManager {
        @Override // android.credentials.ICredentialManager
        public android.os.ICancellationSignal executeGetCredential(android.credentials.GetCredentialRequest getCredentialRequest, android.credentials.IGetCredentialCallback iGetCredentialCallback, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public android.os.ICancellationSignal executePrepareGetCredential(android.credentials.GetCredentialRequest getCredentialRequest, android.credentials.IPrepareGetCredentialCallback iPrepareGetCredentialCallback, android.credentials.IGetCredentialCallback iGetCredentialCallback, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public android.os.ICancellationSignal executeCreateCredential(android.credentials.CreateCredentialRequest createCredentialRequest, android.credentials.ICreateCredentialCallback iCreateCredentialCallback, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public android.os.ICancellationSignal getCandidateCredentials(android.credentials.GetCredentialRequest getCredentialRequest, android.credentials.IGetCandidateCredentialsCallback iGetCandidateCredentialsCallback, android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public android.os.ICancellationSignal clearCredentialState(android.credentials.ClearCredentialStateRequest clearCredentialStateRequest, android.credentials.IClearCredentialStateCallback iClearCredentialStateCallback, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public void setEnabledProviders(java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2, int i, android.credentials.ISetEnabledProvidersCallback iSetEnabledProvidersCallback) throws android.os.RemoteException {
        }

        @Override // android.credentials.ICredentialManager
        public void registerCredentialDescription(android.credentials.RegisterCredentialDescriptionRequest registerCredentialDescriptionRequest, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.credentials.ICredentialManager
        public void unregisterCredentialDescription(android.credentials.UnregisterCredentialDescriptionRequest unregisterCredentialDescriptionRequest, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.credentials.ICredentialManager
        public boolean isEnabledCredentialProviderService(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.credentials.ICredentialManager
        public java.util.List<android.credentials.CredentialProviderInfo> getCredentialProviderServices(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public java.util.List<android.credentials.CredentialProviderInfo> getCredentialProviderServicesForTesting(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public boolean isServiceEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.credentials.ICredentialManager {
        static final int TRANSACTION_clearCredentialState = 5;
        static final int TRANSACTION_executeCreateCredential = 3;
        static final int TRANSACTION_executeGetCredential = 1;
        static final int TRANSACTION_executePrepareGetCredential = 2;
        static final int TRANSACTION_getCandidateCredentials = 4;
        static final int TRANSACTION_getCredentialProviderServices = 10;
        static final int TRANSACTION_getCredentialProviderServicesForTesting = 11;
        static final int TRANSACTION_isEnabledCredentialProviderService = 9;
        static final int TRANSACTION_isServiceEnabled = 12;
        static final int TRANSACTION_registerCredentialDescription = 7;
        static final int TRANSACTION_setEnabledProviders = 6;
        static final int TRANSACTION_unregisterCredentialDescription = 8;

        public Stub() {
            attachInterface(this, android.credentials.ICredentialManager.DESCRIPTOR);
        }

        public static android.credentials.ICredentialManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.credentials.ICredentialManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.credentials.ICredentialManager)) {
                return (android.credentials.ICredentialManager) queryLocalInterface;
            }
            return new android.credentials.ICredentialManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "executeGetCredential";
                case 2:
                    return "executePrepareGetCredential";
                case 3:
                    return "executeCreateCredential";
                case 4:
                    return "getCandidateCredentials";
                case 5:
                    return "clearCredentialState";
                case 6:
                    return "setEnabledProviders";
                case 7:
                    return "registerCredentialDescription";
                case 8:
                    return "unregisterCredentialDescription";
                case 9:
                    return "isEnabledCredentialProviderService";
                case 10:
                    return "getCredentialProviderServices";
                case 11:
                    return "getCredentialProviderServicesForTesting";
                case 12:
                    return "isServiceEnabled";
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
                parcel.enforceInterface(android.credentials.ICredentialManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.credentials.ICredentialManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.credentials.GetCredentialRequest getCredentialRequest = (android.credentials.GetCredentialRequest) parcel.readTypedObject(android.credentials.GetCredentialRequest.CREATOR);
                    android.credentials.IGetCredentialCallback asInterface = android.credentials.IGetCredentialCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.ICancellationSignal executeGetCredential = executeGetCredential(getCredentialRequest, asInterface, readString);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(executeGetCredential);
                    return true;
                case 2:
                    android.credentials.GetCredentialRequest getCredentialRequest2 = (android.credentials.GetCredentialRequest) parcel.readTypedObject(android.credentials.GetCredentialRequest.CREATOR);
                    android.credentials.IPrepareGetCredentialCallback asInterface2 = android.credentials.IPrepareGetCredentialCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.credentials.IGetCredentialCallback asInterface3 = android.credentials.IGetCredentialCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.ICancellationSignal executePrepareGetCredential = executePrepareGetCredential(getCredentialRequest2, asInterface2, asInterface3, readString2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(executePrepareGetCredential);
                    return true;
                case 3:
                    android.credentials.CreateCredentialRequest createCredentialRequest = (android.credentials.CreateCredentialRequest) parcel.readTypedObject(android.credentials.CreateCredentialRequest.CREATOR);
                    android.credentials.ICreateCredentialCallback asInterface4 = android.credentials.ICreateCredentialCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.ICancellationSignal executeCreateCredential = executeCreateCredential(createCredentialRequest, asInterface4, readString3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(executeCreateCredential);
                    return true;
                case 4:
                    android.credentials.GetCredentialRequest getCredentialRequest3 = (android.credentials.GetCredentialRequest) parcel.readTypedObject(android.credentials.GetCredentialRequest.CREATOR);
                    android.credentials.IGetCandidateCredentialsCallback asInterface5 = android.credentials.IGetCandidateCredentialsCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.ICancellationSignal candidateCredentials = getCandidateCredentials(getCredentialRequest3, asInterface5, readStrongBinder, readString4);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(candidateCredentials);
                    return true;
                case 5:
                    android.credentials.ClearCredentialStateRequest clearCredentialStateRequest = (android.credentials.ClearCredentialStateRequest) parcel.readTypedObject(android.credentials.ClearCredentialStateRequest.CREATOR);
                    android.credentials.IClearCredentialStateCallback asInterface6 = android.credentials.IClearCredentialStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.ICancellationSignal clearCredentialState = clearCredentialState(clearCredentialStateRequest, asInterface6, readString5);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(clearCredentialState);
                    return true;
                case 6:
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    java.util.ArrayList<java.lang.String> createStringArrayList2 = parcel.createStringArrayList();
                    int readInt = parcel.readInt();
                    android.credentials.ISetEnabledProvidersCallback asInterface7 = android.credentials.ISetEnabledProvidersCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setEnabledProviders(createStringArrayList, createStringArrayList2, readInt, asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    android.credentials.RegisterCredentialDescriptionRequest registerCredentialDescriptionRequest = (android.credentials.RegisterCredentialDescriptionRequest) parcel.readTypedObject(android.credentials.RegisterCredentialDescriptionRequest.CREATOR);
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerCredentialDescription(registerCredentialDescriptionRequest, readString6);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.credentials.UnregisterCredentialDescriptionRequest unregisterCredentialDescriptionRequest = (android.credentials.UnregisterCredentialDescriptionRequest) parcel.readTypedObject(android.credentials.UnregisterCredentialDescriptionRequest.CREATOR);
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterCredentialDescription(unregisterCredentialDescriptionRequest, readString7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isEnabledCredentialProviderService = isEnabledCredentialProviderService(componentName, readString8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEnabledCredentialProviderService);
                    return true;
                case 10:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.credentials.CredentialProviderInfo> credentialProviderServices = getCredentialProviderServices(readInt2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(credentialProviderServices, 1);
                    return true;
                case 11:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.credentials.CredentialProviderInfo> credentialProviderServicesForTesting = getCredentialProviderServicesForTesting(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(credentialProviderServicesForTesting, 1);
                    return true;
                case 12:
                    boolean isServiceEnabled = isServiceEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isServiceEnabled);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.credentials.ICredentialManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.credentials.ICredentialManager.DESCRIPTOR;
            }

            @Override // android.credentials.ICredentialManager
            public android.os.ICancellationSignal executeGetCredential(android.credentials.GetCredentialRequest getCredentialRequest, android.credentials.IGetCredentialCallback iGetCredentialCallback, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.credentials.ICredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(getCredentialRequest, 0);
                    obtain.writeStrongInterface(iGetCredentialCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.os.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public android.os.ICancellationSignal executePrepareGetCredential(android.credentials.GetCredentialRequest getCredentialRequest, android.credentials.IPrepareGetCredentialCallback iPrepareGetCredentialCallback, android.credentials.IGetCredentialCallback iGetCredentialCallback, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.credentials.ICredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(getCredentialRequest, 0);
                    obtain.writeStrongInterface(iPrepareGetCredentialCallback);
                    obtain.writeStrongInterface(iGetCredentialCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.os.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public android.os.ICancellationSignal executeCreateCredential(android.credentials.CreateCredentialRequest createCredentialRequest, android.credentials.ICreateCredentialCallback iCreateCredentialCallback, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.credentials.ICredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(createCredentialRequest, 0);
                    obtain.writeStrongInterface(iCreateCredentialCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.os.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public android.os.ICancellationSignal getCandidateCredentials(android.credentials.GetCredentialRequest getCredentialRequest, android.credentials.IGetCandidateCredentialsCallback iGetCandidateCredentialsCallback, android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.credentials.ICredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(getCredentialRequest, 0);
                    obtain.writeStrongInterface(iGetCandidateCredentialsCallback);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.os.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public android.os.ICancellationSignal clearCredentialState(android.credentials.ClearCredentialStateRequest clearCredentialStateRequest, android.credentials.IClearCredentialStateCallback iClearCredentialStateCallback, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.credentials.ICredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(clearCredentialStateRequest, 0);
                    obtain.writeStrongInterface(iClearCredentialStateCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.os.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public void setEnabledProviders(java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2, int i, android.credentials.ISetEnabledProvidersCallback iSetEnabledProvidersCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.credentials.ICredentialManager.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeStringList(list2);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSetEnabledProvidersCallback);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public void registerCredentialDescription(android.credentials.RegisterCredentialDescriptionRequest registerCredentialDescriptionRequest, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.credentials.ICredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(registerCredentialDescriptionRequest, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public void unregisterCredentialDescription(android.credentials.UnregisterCredentialDescriptionRequest unregisterCredentialDescriptionRequest, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.credentials.ICredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(unregisterCredentialDescriptionRequest, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public boolean isEnabledCredentialProviderService(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.credentials.ICredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public java.util.List<android.credentials.CredentialProviderInfo> getCredentialProviderServices(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.credentials.ICredentialManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.credentials.CredentialProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public java.util.List<android.credentials.CredentialProviderInfo> getCredentialProviderServicesForTesting(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.credentials.ICredentialManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.credentials.CredentialProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public boolean isServiceEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.credentials.ICredentialManager.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }
    }
}
