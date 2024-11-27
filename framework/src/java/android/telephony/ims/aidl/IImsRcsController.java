package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public interface IImsRcsController extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.ims.aidl.IImsRcsController";

    void createSipDelegate(int i, android.telephony.ims.DelegateRequest delegateRequest, java.lang.String str, android.telephony.ims.aidl.ISipDelegateConnectionStateCallback iSipDelegateConnectionStateCallback, android.telephony.ims.aidl.ISipDelegateMessageCallback iSipDelegateMessageCallback) throws android.os.RemoteException;

    void destroySipDelegate(int i, android.telephony.ims.aidl.ISipDelegate iSipDelegate, int i2) throws android.os.RemoteException;

    void getImsRcsRegistrationState(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void getImsRcsRegistrationTransportType(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    int getUcePublishState(int i) throws android.os.RemoteException;

    boolean isAvailable(int i, int i2, int i3) throws android.os.RemoteException;

    boolean isCapable(int i, int i2, int i3) throws android.os.RemoteException;

    boolean isSipDelegateSupported(int i) throws android.os.RemoteException;

    boolean isUceSettingEnabled(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void registerImsRegistrationCallback(int i, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException;

    void registerRcsAvailabilityCallback(int i, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException;

    void registerRcsFeatureCallback(int i, com.android.ims.internal.IImsServiceFeatureCallback iImsServiceFeatureCallback) throws android.os.RemoteException;

    void registerSipDialogStateCallback(int i, com.android.internal.telephony.ISipDialogStateCallback iSipDialogStateCallback) throws android.os.RemoteException;

    void registerUcePublishStateCallback(int i, android.telephony.ims.aidl.IRcsUcePublishStateCallback iRcsUcePublishStateCallback) throws android.os.RemoteException;

    void requestAvailability(int i, java.lang.String str, java.lang.String str2, android.net.Uri uri, android.telephony.ims.aidl.IRcsUceControllerCallback iRcsUceControllerCallback) throws android.os.RemoteException;

    void requestCapabilities(int i, java.lang.String str, java.lang.String str2, java.util.List<android.net.Uri> list, android.telephony.ims.aidl.IRcsUceControllerCallback iRcsUceControllerCallback) throws android.os.RemoteException;

    void setUceSettingEnabled(int i, boolean z) throws android.os.RemoteException;

    void triggerNetworkRegistration(int i, android.telephony.ims.aidl.ISipDelegate iSipDelegate, int i2, java.lang.String str) throws android.os.RemoteException;

    void unregisterImsFeatureCallback(com.android.ims.internal.IImsServiceFeatureCallback iImsServiceFeatureCallback) throws android.os.RemoteException;

    void unregisterImsRegistrationCallback(int i, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException;

    void unregisterRcsAvailabilityCallback(int i, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException;

    void unregisterSipDialogStateCallback(int i, com.android.internal.telephony.ISipDialogStateCallback iSipDialogStateCallback) throws android.os.RemoteException;

    void unregisterUcePublishStateCallback(int i, android.telephony.ims.aidl.IRcsUcePublishStateCallback iRcsUcePublishStateCallback) throws android.os.RemoteException;

    public static class Default implements android.telephony.ims.aidl.IImsRcsController {
        @Override // android.telephony.ims.aidl.IImsRcsController
        public void registerImsRegistrationCallback(int i, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void unregisterImsRegistrationCallback(int i, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void getImsRcsRegistrationState(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void getImsRcsRegistrationTransportType(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void registerRcsAvailabilityCallback(int i, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void unregisterRcsAvailabilityCallback(int i, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public boolean isCapable(int i, int i2, int i3) throws android.os.RemoteException {
            return false;
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public boolean isAvailable(int i, int i2, int i3) throws android.os.RemoteException {
            return false;
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void requestCapabilities(int i, java.lang.String str, java.lang.String str2, java.util.List<android.net.Uri> list, android.telephony.ims.aidl.IRcsUceControllerCallback iRcsUceControllerCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void requestAvailability(int i, java.lang.String str, java.lang.String str2, android.net.Uri uri, android.telephony.ims.aidl.IRcsUceControllerCallback iRcsUceControllerCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public int getUcePublishState(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public boolean isUceSettingEnabled(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void setUceSettingEnabled(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void registerUcePublishStateCallback(int i, android.telephony.ims.aidl.IRcsUcePublishStateCallback iRcsUcePublishStateCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void unregisterUcePublishStateCallback(int i, android.telephony.ims.aidl.IRcsUcePublishStateCallback iRcsUcePublishStateCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public boolean isSipDelegateSupported(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void createSipDelegate(int i, android.telephony.ims.DelegateRequest delegateRequest, java.lang.String str, android.telephony.ims.aidl.ISipDelegateConnectionStateCallback iSipDelegateConnectionStateCallback, android.telephony.ims.aidl.ISipDelegateMessageCallback iSipDelegateMessageCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void destroySipDelegate(int i, android.telephony.ims.aidl.ISipDelegate iSipDelegate, int i2) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void triggerNetworkRegistration(int i, android.telephony.ims.aidl.ISipDelegate iSipDelegate, int i2, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void registerSipDialogStateCallback(int i, com.android.internal.telephony.ISipDialogStateCallback iSipDialogStateCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void unregisterSipDialogStateCallback(int i, com.android.internal.telephony.ISipDialogStateCallback iSipDialogStateCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void registerRcsFeatureCallback(int i, com.android.ims.internal.IImsServiceFeatureCallback iImsServiceFeatureCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void unregisterImsFeatureCallback(com.android.ims.internal.IImsServiceFeatureCallback iImsServiceFeatureCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.ims.aidl.IImsRcsController {
        static final int TRANSACTION_createSipDelegate = 17;
        static final int TRANSACTION_destroySipDelegate = 18;
        static final int TRANSACTION_getImsRcsRegistrationState = 3;
        static final int TRANSACTION_getImsRcsRegistrationTransportType = 4;
        static final int TRANSACTION_getUcePublishState = 11;
        static final int TRANSACTION_isAvailable = 8;
        static final int TRANSACTION_isCapable = 7;
        static final int TRANSACTION_isSipDelegateSupported = 16;
        static final int TRANSACTION_isUceSettingEnabled = 12;
        static final int TRANSACTION_registerImsRegistrationCallback = 1;
        static final int TRANSACTION_registerRcsAvailabilityCallback = 5;
        static final int TRANSACTION_registerRcsFeatureCallback = 22;
        static final int TRANSACTION_registerSipDialogStateCallback = 20;
        static final int TRANSACTION_registerUcePublishStateCallback = 14;
        static final int TRANSACTION_requestAvailability = 10;
        static final int TRANSACTION_requestCapabilities = 9;
        static final int TRANSACTION_setUceSettingEnabled = 13;
        static final int TRANSACTION_triggerNetworkRegistration = 19;
        static final int TRANSACTION_unregisterImsFeatureCallback = 23;
        static final int TRANSACTION_unregisterImsRegistrationCallback = 2;
        static final int TRANSACTION_unregisterRcsAvailabilityCallback = 6;
        static final int TRANSACTION_unregisterSipDialogStateCallback = 21;
        static final int TRANSACTION_unregisterUcePublishStateCallback = 15;

        public Stub() {
            attachInterface(this, android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
        }

        public static android.telephony.ims.aidl.IImsRcsController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.ims.aidl.IImsRcsController)) {
                return (android.telephony.ims.aidl.IImsRcsController) queryLocalInterface;
            }
            return new android.telephony.ims.aidl.IImsRcsController.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerImsRegistrationCallback";
                case 2:
                    return "unregisterImsRegistrationCallback";
                case 3:
                    return "getImsRcsRegistrationState";
                case 4:
                    return "getImsRcsRegistrationTransportType";
                case 5:
                    return "registerRcsAvailabilityCallback";
                case 6:
                    return "unregisterRcsAvailabilityCallback";
                case 7:
                    return "isCapable";
                case 8:
                    return "isAvailable";
                case 9:
                    return "requestCapabilities";
                case 10:
                    return "requestAvailability";
                case 11:
                    return "getUcePublishState";
                case 12:
                    return "isUceSettingEnabled";
                case 13:
                    return "setUceSettingEnabled";
                case 14:
                    return "registerUcePublishStateCallback";
                case 15:
                    return "unregisterUcePublishStateCallback";
                case 16:
                    return "isSipDelegateSupported";
                case 17:
                    return "createSipDelegate";
                case 18:
                    return "destroySipDelegate";
                case 19:
                    return "triggerNetworkRegistration";
                case 20:
                    return "registerSipDialogStateCallback";
                case 21:
                    return "unregisterSipDialogStateCallback";
                case 22:
                    return "registerRcsFeatureCallback";
                case 23:
                    return "unregisterImsFeatureCallback";
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
                parcel.enforceInterface(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.telephony.ims.aidl.IImsRegistrationCallback asInterface = android.telephony.ims.aidl.IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerImsRegistrationCallback(readInt, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    android.telephony.ims.aidl.IImsRegistrationCallback asInterface2 = android.telephony.ims.aidl.IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterImsRegistrationCallback(readInt2, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    com.android.internal.telephony.IIntegerConsumer asInterface3 = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getImsRcsRegistrationState(readInt3, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    com.android.internal.telephony.IIntegerConsumer asInterface4 = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getImsRcsRegistrationTransportType(readInt4, asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    android.telephony.ims.aidl.IImsCapabilityCallback asInterface5 = android.telephony.ims.aidl.IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRcsAvailabilityCallback(readInt5, asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    android.telephony.ims.aidl.IImsCapabilityCallback asInterface6 = android.telephony.ims.aidl.IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterRcsAvailabilityCallback(readInt6, asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCapable = isCapable(readInt7, readInt8, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCapable);
                    return true;
                case 8:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAvailable = isAvailable(readInt10, readInt11, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAvailable);
                    return true;
                case 9:
                    int readInt13 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.net.Uri.CREATOR);
                    android.telephony.ims.aidl.IRcsUceControllerCallback asInterface7 = android.telephony.ims.aidl.IRcsUceControllerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestCapabilities(readInt13, readString, readString2, createTypedArrayList, asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt14 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.telephony.ims.aidl.IRcsUceControllerCallback asInterface8 = android.telephony.ims.aidl.IRcsUceControllerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestAvailability(readInt14, readString3, readString4, uri, asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int ucePublishState = getUcePublishState(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeInt(ucePublishState);
                    return true;
                case 12:
                    int readInt16 = parcel.readInt();
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUceSettingEnabled = isUceSettingEnabled(readInt16, readString5, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUceSettingEnabled);
                    return true;
                case 13:
                    int readInt17 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUceSettingEnabled(readInt17, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt18 = parcel.readInt();
                    android.telephony.ims.aidl.IRcsUcePublishStateCallback asInterface9 = android.telephony.ims.aidl.IRcsUcePublishStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerUcePublishStateCallback(readInt18, asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt19 = parcel.readInt();
                    android.telephony.ims.aidl.IRcsUcePublishStateCallback asInterface10 = android.telephony.ims.aidl.IRcsUcePublishStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUcePublishStateCallback(readInt19, asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSipDelegateSupported = isSipDelegateSupported(readInt20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSipDelegateSupported);
                    return true;
                case 17:
                    int readInt21 = parcel.readInt();
                    android.telephony.ims.DelegateRequest delegateRequest = (android.telephony.ims.DelegateRequest) parcel.readTypedObject(android.telephony.ims.DelegateRequest.CREATOR);
                    java.lang.String readString7 = parcel.readString();
                    android.telephony.ims.aidl.ISipDelegateConnectionStateCallback asInterface11 = android.telephony.ims.aidl.ISipDelegateConnectionStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.aidl.ISipDelegateMessageCallback asInterface12 = android.telephony.ims.aidl.ISipDelegateMessageCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    createSipDelegate(readInt21, delegateRequest, readString7, asInterface11, asInterface12);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt22 = parcel.readInt();
                    android.telephony.ims.aidl.ISipDelegate asInterface13 = android.telephony.ims.aidl.ISipDelegate.Stub.asInterface(parcel.readStrongBinder());
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroySipDelegate(readInt22, asInterface13, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt24 = parcel.readInt();
                    android.telephony.ims.aidl.ISipDelegate asInterface14 = android.telephony.ims.aidl.ISipDelegate.Stub.asInterface(parcel.readStrongBinder());
                    int readInt25 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    triggerNetworkRegistration(readInt24, asInterface14, readInt25, readString8);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt26 = parcel.readInt();
                    com.android.internal.telephony.ISipDialogStateCallback asInterface15 = com.android.internal.telephony.ISipDialogStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSipDialogStateCallback(readInt26, asInterface15);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt27 = parcel.readInt();
                    com.android.internal.telephony.ISipDialogStateCallback asInterface16 = com.android.internal.telephony.ISipDialogStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterSipDialogStateCallback(readInt27, asInterface16);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt28 = parcel.readInt();
                    com.android.ims.internal.IImsServiceFeatureCallback asInterface17 = com.android.ims.internal.IImsServiceFeatureCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRcsFeatureCallback(readInt28, asInterface17);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    com.android.ims.internal.IImsServiceFeatureCallback asInterface18 = com.android.ims.internal.IImsServiceFeatureCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterImsFeatureCallback(asInterface18);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.ims.aidl.IImsRcsController {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void registerImsRegistrationCallback(int i, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void unregisterImsRegistrationCallback(int i, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void getImsRcsRegistrationState(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void getImsRcsRegistrationTransportType(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void registerRcsAvailabilityCallback(int i, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void unregisterRcsAvailabilityCallback(int i, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public boolean isCapable(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public boolean isAvailable(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void requestCapabilities(int i, java.lang.String str, java.lang.String str2, java.util.List<android.net.Uri> list, android.telephony.ims.aidl.IRcsUceControllerCallback iRcsUceControllerCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedList(list, 0);
                    obtain.writeStrongInterface(iRcsUceControllerCallback);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void requestAvailability(int i, java.lang.String str, java.lang.String str2, android.net.Uri uri, android.telephony.ims.aidl.IRcsUceControllerCallback iRcsUceControllerCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeStrongInterface(iRcsUceControllerCallback);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public int getUcePublishState(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public boolean isUceSettingEnabled(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void setUceSettingEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void registerUcePublishStateCallback(int i, android.telephony.ims.aidl.IRcsUcePublishStateCallback iRcsUcePublishStateCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRcsUcePublishStateCallback);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void unregisterUcePublishStateCallback(int i, android.telephony.ims.aidl.IRcsUcePublishStateCallback iRcsUcePublishStateCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRcsUcePublishStateCallback);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public boolean isSipDelegateSupported(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void createSipDelegate(int i, android.telephony.ims.DelegateRequest delegateRequest, java.lang.String str, android.telephony.ims.aidl.ISipDelegateConnectionStateCallback iSipDelegateConnectionStateCallback, android.telephony.ims.aidl.ISipDelegateMessageCallback iSipDelegateMessageCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(delegateRequest, 0);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iSipDelegateConnectionStateCallback);
                    obtain.writeStrongInterface(iSipDelegateMessageCallback);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void destroySipDelegate(int i, android.telephony.ims.aidl.ISipDelegate iSipDelegate, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSipDelegate);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void triggerNetworkRegistration(int i, android.telephony.ims.aidl.ISipDelegate iSipDelegate, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSipDelegate);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void registerSipDialogStateCallback(int i, com.android.internal.telephony.ISipDialogStateCallback iSipDialogStateCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSipDialogStateCallback);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void unregisterSipDialogStateCallback(int i, com.android.internal.telephony.ISipDialogStateCallback iSipDialogStateCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSipDialogStateCallback);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void registerRcsFeatureCallback(int i, com.android.ims.internal.IImsServiceFeatureCallback iImsServiceFeatureCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsServiceFeatureCallback);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void unregisterImsFeatureCallback(com.android.ims.internal.IImsServiceFeatureCallback iImsServiceFeatureCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsController.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsServiceFeatureCallback);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 22;
        }
    }
}
