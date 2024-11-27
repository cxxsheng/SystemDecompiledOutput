package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public interface ISipDelegateStateCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.ims.aidl.ISipDelegateStateCallback";

    void onConfigurationChanged(android.telephony.ims.SipDelegateConfiguration sipDelegateConfiguration) throws android.os.RemoteException;

    void onCreated(android.telephony.ims.aidl.ISipDelegate iSipDelegate, java.util.List<android.telephony.ims.FeatureTagState> list) throws android.os.RemoteException;

    void onDestroyed(int i) throws android.os.RemoteException;

    void onFeatureTagRegistrationChanged(android.telephony.ims.DelegateRegistrationState delegateRegistrationState) throws android.os.RemoteException;

    void onImsConfigurationChanged(android.telephony.ims.SipDelegateImsConfiguration sipDelegateImsConfiguration) throws android.os.RemoteException;

    public static class Default implements android.telephony.ims.aidl.ISipDelegateStateCallback {
        @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
        public void onCreated(android.telephony.ims.aidl.ISipDelegate iSipDelegate, java.util.List<android.telephony.ims.FeatureTagState> list) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
        public void onFeatureTagRegistrationChanged(android.telephony.ims.DelegateRegistrationState delegateRegistrationState) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
        public void onImsConfigurationChanged(android.telephony.ims.SipDelegateImsConfiguration sipDelegateImsConfiguration) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
        public void onConfigurationChanged(android.telephony.ims.SipDelegateConfiguration sipDelegateConfiguration) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
        public void onDestroyed(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.ims.aidl.ISipDelegateStateCallback {
        static final int TRANSACTION_onConfigurationChanged = 4;
        static final int TRANSACTION_onCreated = 1;
        static final int TRANSACTION_onDestroyed = 5;
        static final int TRANSACTION_onFeatureTagRegistrationChanged = 2;
        static final int TRANSACTION_onImsConfigurationChanged = 3;

        public Stub() {
            attachInterface(this, android.telephony.ims.aidl.ISipDelegateStateCallback.DESCRIPTOR);
        }

        public static android.telephony.ims.aidl.ISipDelegateStateCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.ims.aidl.ISipDelegateStateCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.ims.aidl.ISipDelegateStateCallback)) {
                return (android.telephony.ims.aidl.ISipDelegateStateCallback) queryLocalInterface;
            }
            return new android.telephony.ims.aidl.ISipDelegateStateCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCreated";
                case 2:
                    return "onFeatureTagRegistrationChanged";
                case 3:
                    return "onImsConfigurationChanged";
                case 4:
                    return "onConfigurationChanged";
                case 5:
                    return "onDestroyed";
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
                parcel.enforceInterface(android.telephony.ims.aidl.ISipDelegateStateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.ims.aidl.ISipDelegateStateCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.telephony.ims.aidl.ISipDelegate asInterface = android.telephony.ims.aidl.ISipDelegate.Stub.asInterface(parcel.readStrongBinder());
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telephony.ims.FeatureTagState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCreated(asInterface, createTypedArrayList);
                    return true;
                case 2:
                    android.telephony.ims.DelegateRegistrationState delegateRegistrationState = (android.telephony.ims.DelegateRegistrationState) parcel.readTypedObject(android.telephony.ims.DelegateRegistrationState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onFeatureTagRegistrationChanged(delegateRegistrationState);
                    return true;
                case 3:
                    android.telephony.ims.SipDelegateImsConfiguration sipDelegateImsConfiguration = (android.telephony.ims.SipDelegateImsConfiguration) parcel.readTypedObject(android.telephony.ims.SipDelegateImsConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    onImsConfigurationChanged(sipDelegateImsConfiguration);
                    return true;
                case 4:
                    android.telephony.ims.SipDelegateConfiguration sipDelegateConfiguration = (android.telephony.ims.SipDelegateConfiguration) parcel.readTypedObject(android.telephony.ims.SipDelegateConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    onConfigurationChanged(sipDelegateConfiguration);
                    return true;
                case 5:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDestroyed(readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.ims.aidl.ISipDelegateStateCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.ims.aidl.ISipDelegateStateCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
            public void onCreated(android.telephony.ims.aidl.ISipDelegate iSipDelegate, java.util.List<android.telephony.ims.FeatureTagState> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.ISipDelegateStateCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iSipDelegate);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
            public void onFeatureTagRegistrationChanged(android.telephony.ims.DelegateRegistrationState delegateRegistrationState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.ISipDelegateStateCallback.DESCRIPTOR);
                    obtain.writeTypedObject(delegateRegistrationState, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
            public void onImsConfigurationChanged(android.telephony.ims.SipDelegateImsConfiguration sipDelegateImsConfiguration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.ISipDelegateStateCallback.DESCRIPTOR);
                    obtain.writeTypedObject(sipDelegateImsConfiguration, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
            public void onConfigurationChanged(android.telephony.ims.SipDelegateConfiguration sipDelegateConfiguration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.ISipDelegateStateCallback.DESCRIPTOR);
                    obtain.writeTypedObject(sipDelegateConfiguration, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
            public void onDestroyed(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.ISipDelegateStateCallback.DESCRIPTOR);
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
