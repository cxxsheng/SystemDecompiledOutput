package android.companion;

/* loaded from: classes.dex */
public interface ICompanionDeviceManager extends android.os.IInterface {
    void addOnAssociationsChangedListener(android.companion.IOnAssociationsChangedListener iOnAssociationsChangedListener, int i) throws android.os.RemoteException;

    void addOnMessageReceivedListener(int i, android.companion.IOnMessageReceivedListener iOnMessageReceivedListener) throws android.os.RemoteException;

    void addOnTransportsChangedListener(android.companion.IOnTransportsChangedListener iOnTransportsChangedListener) throws android.os.RemoteException;

    void applyRestoredPayload(byte[] bArr, int i) throws android.os.RemoteException;

    void associate(android.companion.AssociationRequest associationRequest, android.companion.IAssociationRequestCallback iAssociationRequestCallback, java.lang.String str, int i) throws android.os.RemoteException;

    void attachSystemDataTransport(java.lang.String str, int i, int i2, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException;

    android.app.PendingIntent buildAssociationCancellationIntent(java.lang.String str, int i) throws android.os.RemoteException;

    android.app.PendingIntent buildPermissionTransferUserConsentIntent(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    boolean canPairWithoutPrompt(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    void clearAssociationTag(int i) throws android.os.RemoteException;

    void createAssociation(java.lang.String str, java.lang.String str2, int i, byte[] bArr) throws android.os.RemoteException;

    void detachSystemDataTransport(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void disablePermissionsSync(int i) throws android.os.RemoteException;

    void disableSystemDataSync(int i, int i2) throws android.os.RemoteException;

    void disassociate(int i) throws android.os.RemoteException;

    void enablePermissionsSync(int i) throws android.os.RemoteException;

    void enableSecureTransport(boolean z) throws android.os.RemoteException;

    void enableSystemDataSync(int i, int i2) throws android.os.RemoteException;

    java.util.List<android.companion.AssociationInfo> getAllAssociationsForUser(int i) throws android.os.RemoteException;

    java.util.List<android.companion.AssociationInfo> getAssociations(java.lang.String str, int i) throws android.os.RemoteException;

    byte[] getBackupPayload(int i) throws android.os.RemoteException;

    android.companion.datatransfer.PermissionSyncRequest getPermissionSyncRequest(int i) throws android.os.RemoteException;

    @java.lang.Deprecated
    boolean hasNotificationAccess(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean isCompanionApplicationBound(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isDeviceAssociatedForWifiConnection(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    boolean isPermissionTransferUserConsented(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    @java.lang.Deprecated
    void legacyDisassociate(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    void notifyDeviceAppeared(int i) throws android.os.RemoteException;

    void notifyDeviceDisappeared(int i) throws android.os.RemoteException;

    void registerDevicePresenceListenerService(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    void removeOnAssociationsChangedListener(android.companion.IOnAssociationsChangedListener iOnAssociationsChangedListener, int i) throws android.os.RemoteException;

    void removeOnMessageReceivedListener(int i, android.companion.IOnMessageReceivedListener iOnMessageReceivedListener) throws android.os.RemoteException;

    void removeOnTransportsChangedListener(android.companion.IOnTransportsChangedListener iOnTransportsChangedListener) throws android.os.RemoteException;

    android.app.PendingIntent requestNotificationAccess(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    void sendMessage(int i, byte[] bArr, int[] iArr) throws android.os.RemoteException;

    void setAssociationTag(int i, java.lang.String str) throws android.os.RemoteException;

    void startObservingDevicePresence(android.companion.ObservingDevicePresenceRequest observingDevicePresenceRequest, java.lang.String str, int i) throws android.os.RemoteException;

    void startSystemDataTransfer(java.lang.String str, int i, int i2, android.companion.ISystemDataTransferCallback iSystemDataTransferCallback) throws android.os.RemoteException;

    void stopObservingDevicePresence(android.companion.ObservingDevicePresenceRequest observingDevicePresenceRequest, java.lang.String str, int i) throws android.os.RemoteException;

    void unregisterDevicePresenceListenerService(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    public static class Default implements android.companion.ICompanionDeviceManager {
        @Override // android.companion.ICompanionDeviceManager
        public void associate(android.companion.AssociationRequest associationRequest, android.companion.IAssociationRequestCallback iAssociationRequestCallback, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public java.util.List<android.companion.AssociationInfo> getAssociations(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public java.util.List<android.companion.AssociationInfo> getAllAssociationsForUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public void legacyDisassociate(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void disassociate(int i) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public boolean hasNotificationAccess(android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // android.companion.ICompanionDeviceManager
        public android.app.PendingIntent requestNotificationAccess(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public boolean isDeviceAssociatedForWifiConnection(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.companion.ICompanionDeviceManager
        public void registerDevicePresenceListenerService(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void unregisterDevicePresenceListenerService(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public boolean canPairWithoutPrompt(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.companion.ICompanionDeviceManager
        public void createAssociation(java.lang.String str, java.lang.String str2, int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void addOnAssociationsChangedListener(android.companion.IOnAssociationsChangedListener iOnAssociationsChangedListener, int i) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void removeOnAssociationsChangedListener(android.companion.IOnAssociationsChangedListener iOnAssociationsChangedListener, int i) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void addOnTransportsChangedListener(android.companion.IOnTransportsChangedListener iOnTransportsChangedListener) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void removeOnTransportsChangedListener(android.companion.IOnTransportsChangedListener iOnTransportsChangedListener) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void sendMessage(int i, byte[] bArr, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void addOnMessageReceivedListener(int i, android.companion.IOnMessageReceivedListener iOnMessageReceivedListener) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void removeOnMessageReceivedListener(int i, android.companion.IOnMessageReceivedListener iOnMessageReceivedListener) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void notifyDeviceAppeared(int i) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void notifyDeviceDisappeared(int i) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public android.app.PendingIntent buildPermissionTransferUserConsentIntent(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public boolean isPermissionTransferUserConsented(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.companion.ICompanionDeviceManager
        public void startSystemDataTransfer(java.lang.String str, int i, int i2, android.companion.ISystemDataTransferCallback iSystemDataTransferCallback) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void attachSystemDataTransport(java.lang.String str, int i, int i2, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void detachSystemDataTransport(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public boolean isCompanionApplicationBound(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.companion.ICompanionDeviceManager
        public android.app.PendingIntent buildAssociationCancellationIntent(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public void enableSystemDataSync(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void disableSystemDataSync(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void enablePermissionsSync(int i) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void disablePermissionsSync(int i) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public android.companion.datatransfer.PermissionSyncRequest getPermissionSyncRequest(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public void enableSecureTransport(boolean z) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void setAssociationTag(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void clearAssociationTag(int i) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public byte[] getBackupPayload(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public void applyRestoredPayload(byte[] bArr, int i) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void startObservingDevicePresence(android.companion.ObservingDevicePresenceRequest observingDevicePresenceRequest, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void stopObservingDevicePresence(android.companion.ObservingDevicePresenceRequest observingDevicePresenceRequest, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.companion.ICompanionDeviceManager {
        public static final java.lang.String DESCRIPTOR = "android.companion.ICompanionDeviceManager";
        static final int TRANSACTION_addOnAssociationsChangedListener = 13;
        static final int TRANSACTION_addOnMessageReceivedListener = 18;
        static final int TRANSACTION_addOnTransportsChangedListener = 15;
        static final int TRANSACTION_applyRestoredPayload = 38;
        static final int TRANSACTION_associate = 1;
        static final int TRANSACTION_attachSystemDataTransport = 25;
        static final int TRANSACTION_buildAssociationCancellationIntent = 28;
        static final int TRANSACTION_buildPermissionTransferUserConsentIntent = 22;
        static final int TRANSACTION_canPairWithoutPrompt = 11;
        static final int TRANSACTION_clearAssociationTag = 36;
        static final int TRANSACTION_createAssociation = 12;
        static final int TRANSACTION_detachSystemDataTransport = 26;
        static final int TRANSACTION_disablePermissionsSync = 32;
        static final int TRANSACTION_disableSystemDataSync = 30;
        static final int TRANSACTION_disassociate = 5;
        static final int TRANSACTION_enablePermissionsSync = 31;
        static final int TRANSACTION_enableSecureTransport = 34;
        static final int TRANSACTION_enableSystemDataSync = 29;
        static final int TRANSACTION_getAllAssociationsForUser = 3;
        static final int TRANSACTION_getAssociations = 2;
        static final int TRANSACTION_getBackupPayload = 37;
        static final int TRANSACTION_getPermissionSyncRequest = 33;
        static final int TRANSACTION_hasNotificationAccess = 6;
        static final int TRANSACTION_isCompanionApplicationBound = 27;
        static final int TRANSACTION_isDeviceAssociatedForWifiConnection = 8;
        static final int TRANSACTION_isPermissionTransferUserConsented = 23;
        static final int TRANSACTION_legacyDisassociate = 4;
        static final int TRANSACTION_notifyDeviceAppeared = 20;
        static final int TRANSACTION_notifyDeviceDisappeared = 21;
        static final int TRANSACTION_registerDevicePresenceListenerService = 9;
        static final int TRANSACTION_removeOnAssociationsChangedListener = 14;
        static final int TRANSACTION_removeOnMessageReceivedListener = 19;
        static final int TRANSACTION_removeOnTransportsChangedListener = 16;
        static final int TRANSACTION_requestNotificationAccess = 7;
        static final int TRANSACTION_sendMessage = 17;
        static final int TRANSACTION_setAssociationTag = 35;
        static final int TRANSACTION_startObservingDevicePresence = 39;
        static final int TRANSACTION_startSystemDataTransfer = 24;
        static final int TRANSACTION_stopObservingDevicePresence = 40;
        static final int TRANSACTION_unregisterDevicePresenceListenerService = 10;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.companion.ICompanionDeviceManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.companion.ICompanionDeviceManager)) {
                return (android.companion.ICompanionDeviceManager) queryLocalInterface;
            }
            return new android.companion.ICompanionDeviceManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "associate";
                case 2:
                    return "getAssociations";
                case 3:
                    return "getAllAssociationsForUser";
                case 4:
                    return "legacyDisassociate";
                case 5:
                    return "disassociate";
                case 6:
                    return "hasNotificationAccess";
                case 7:
                    return "requestNotificationAccess";
                case 8:
                    return "isDeviceAssociatedForWifiConnection";
                case 9:
                    return "registerDevicePresenceListenerService";
                case 10:
                    return "unregisterDevicePresenceListenerService";
                case 11:
                    return "canPairWithoutPrompt";
                case 12:
                    return "createAssociation";
                case 13:
                    return "addOnAssociationsChangedListener";
                case 14:
                    return "removeOnAssociationsChangedListener";
                case 15:
                    return "addOnTransportsChangedListener";
                case 16:
                    return "removeOnTransportsChangedListener";
                case 17:
                    return "sendMessage";
                case 18:
                    return "addOnMessageReceivedListener";
                case 19:
                    return "removeOnMessageReceivedListener";
                case 20:
                    return "notifyDeviceAppeared";
                case 21:
                    return "notifyDeviceDisappeared";
                case 22:
                    return "buildPermissionTransferUserConsentIntent";
                case 23:
                    return "isPermissionTransferUserConsented";
                case 24:
                    return "startSystemDataTransfer";
                case 25:
                    return "attachSystemDataTransport";
                case 26:
                    return "detachSystemDataTransport";
                case 27:
                    return "isCompanionApplicationBound";
                case 28:
                    return "buildAssociationCancellationIntent";
                case 29:
                    return "enableSystemDataSync";
                case 30:
                    return "disableSystemDataSync";
                case 31:
                    return "enablePermissionsSync";
                case 32:
                    return "disablePermissionsSync";
                case 33:
                    return "getPermissionSyncRequest";
                case 34:
                    return "enableSecureTransport";
                case 35:
                    return "setAssociationTag";
                case 36:
                    return "clearAssociationTag";
                case 37:
                    return "getBackupPayload";
                case 38:
                    return "applyRestoredPayload";
                case 39:
                    return "startObservingDevicePresence";
                case 40:
                    return "stopObservingDevicePresence";
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
                    android.companion.AssociationRequest associationRequest = (android.companion.AssociationRequest) parcel.readTypedObject(android.companion.AssociationRequest.CREATOR);
                    android.companion.IAssociationRequestCallback asInterface = android.companion.IAssociationRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    associate(associationRequest, asInterface, readString, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.companion.AssociationInfo> associations = getAssociations(readString2, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(associations, 1);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.companion.AssociationInfo> allAssociationsForUser = getAllAssociationsForUser(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allAssociationsForUser, 1);
                    return true;
                case 4:
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    legacyDisassociate(readString3, readString4, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disassociate(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean hasNotificationAccess = hasNotificationAccess(componentName);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasNotificationAccess);
                    return true;
                case 7:
                    android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.PendingIntent requestNotificationAccess = requestNotificationAccess(componentName2, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(requestNotificationAccess, 1);
                    return true;
                case 8:
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDeviceAssociatedForWifiConnection = isDeviceAssociatedForWifiConnection(readString5, readString6, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceAssociatedForWifiConnection);
                    return true;
                case 9:
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerDevicePresenceListenerService(readString7, readString8, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    java.lang.String readString9 = parcel.readString();
                    java.lang.String readString10 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterDevicePresenceListenerService(readString9, readString10, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    java.lang.String readString11 = parcel.readString();
                    java.lang.String readString12 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean canPairWithoutPrompt = canPairWithoutPrompt(readString11, readString12, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canPairWithoutPrompt);
                    return true;
                case 12:
                    java.lang.String readString13 = parcel.readString();
                    java.lang.String readString14 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    createAssociation(readString13, readString14, readInt11, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    android.companion.IOnAssociationsChangedListener asInterface2 = android.companion.IOnAssociationsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addOnAssociationsChangedListener(asInterface2, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    android.companion.IOnAssociationsChangedListener asInterface3 = android.companion.IOnAssociationsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeOnAssociationsChangedListener(asInterface3, readInt13);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    android.companion.IOnTransportsChangedListener asInterface4 = android.companion.IOnTransportsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnTransportsChangedListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    android.companion.IOnTransportsChangedListener asInterface5 = android.companion.IOnTransportsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnTransportsChangedListener(asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt14 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    sendMessage(readInt14, createByteArray2, createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt15 = parcel.readInt();
                    android.companion.IOnMessageReceivedListener asInterface6 = android.companion.IOnMessageReceivedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnMessageReceivedListener(readInt15, asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt16 = parcel.readInt();
                    android.companion.IOnMessageReceivedListener asInterface7 = android.companion.IOnMessageReceivedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnMessageReceivedListener(readInt16, asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyDeviceAppeared(readInt17);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyDeviceDisappeared(readInt18);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    java.lang.String readString15 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.PendingIntent buildPermissionTransferUserConsentIntent = buildPermissionTransferUserConsentIntent(readString15, readInt19, readInt20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(buildPermissionTransferUserConsentIntent, 1);
                    return true;
                case 23:
                    java.lang.String readString16 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPermissionTransferUserConsented = isPermissionTransferUserConsented(readString16, readInt21, readInt22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPermissionTransferUserConsented);
                    return true;
                case 24:
                    java.lang.String readString17 = parcel.readString();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    android.companion.ISystemDataTransferCallback asInterface8 = android.companion.ISystemDataTransferCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startSystemDataTransfer(readString17, readInt23, readInt24, asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    java.lang.String readString18 = parcel.readString();
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    attachSystemDataTransport(readString18, readInt25, readInt26, parcelFileDescriptor);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    java.lang.String readString19 = parcel.readString();
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    detachSystemDataTransport(readString19, readInt27, readInt28);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    java.lang.String readString20 = parcel.readString();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCompanionApplicationBound = isCompanionApplicationBound(readString20, readInt29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCompanionApplicationBound);
                    return true;
                case 28:
                    java.lang.String readString21 = parcel.readString();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.PendingIntent buildAssociationCancellationIntent = buildAssociationCancellationIntent(readString21, readInt30);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(buildAssociationCancellationIntent, 1);
                    return true;
                case 29:
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableSystemDataSync(readInt31, readInt32);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int readInt33 = parcel.readInt();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableSystemDataSync(readInt33, readInt34);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enablePermissionsSync(readInt35);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disablePermissionsSync(readInt36);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.companion.datatransfer.PermissionSyncRequest permissionSyncRequest = getPermissionSyncRequest(readInt37);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(permissionSyncRequest, 1);
                    return true;
                case 34:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableSecureTransport(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int readInt38 = parcel.readInt();
                    java.lang.String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAssociationTag(readInt38, readString22);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearAssociationTag(readInt39);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] backupPayload = getBackupPayload(readInt40);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(backupPayload);
                    return true;
                case 38:
                    byte[] createByteArray3 = parcel.createByteArray();
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    applyRestoredPayload(createByteArray3, readInt41);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    android.companion.ObservingDevicePresenceRequest observingDevicePresenceRequest = (android.companion.ObservingDevicePresenceRequest) parcel.readTypedObject(android.companion.ObservingDevicePresenceRequest.CREATOR);
                    java.lang.String readString23 = parcel.readString();
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startObservingDevicePresence(observingDevicePresenceRequest, readString23, readInt42);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    android.companion.ObservingDevicePresenceRequest observingDevicePresenceRequest2 = (android.companion.ObservingDevicePresenceRequest) parcel.readTypedObject(android.companion.ObservingDevicePresenceRequest.CREATOR);
                    java.lang.String readString24 = parcel.readString();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopObservingDevicePresence(observingDevicePresenceRequest2, readString24, readInt43);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.companion.ICompanionDeviceManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR;
            }

            @Override // android.companion.ICompanionDeviceManager
            public void associate(android.companion.AssociationRequest associationRequest, android.companion.IAssociationRequestCallback iAssociationRequestCallback, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(associationRequest, 0);
                    obtain.writeStrongInterface(iAssociationRequestCallback);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public java.util.List<android.companion.AssociationInfo> getAssociations(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.companion.AssociationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public java.util.List<android.companion.AssociationInfo> getAllAssociationsForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.companion.AssociationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void legacyDisassociate(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void disassociate(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean hasNotificationAccess(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public android.app.PendingIntent requestNotificationAccess(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.PendingIntent) obtain2.readTypedObject(android.app.PendingIntent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean isDeviceAssociatedForWifiConnection(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void registerDevicePresenceListenerService(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void unregisterDevicePresenceListenerService(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean canPairWithoutPrompt(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void createAssociation(java.lang.String str, java.lang.String str2, int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void addOnAssociationsChangedListener(android.companion.IOnAssociationsChangedListener iOnAssociationsChangedListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnAssociationsChangedListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void removeOnAssociationsChangedListener(android.companion.IOnAssociationsChangedListener iOnAssociationsChangedListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnAssociationsChangedListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void addOnTransportsChangedListener(android.companion.IOnTransportsChangedListener iOnTransportsChangedListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnTransportsChangedListener);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void removeOnTransportsChangedListener(android.companion.IOnTransportsChangedListener iOnTransportsChangedListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnTransportsChangedListener);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void sendMessage(int i, byte[] bArr, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void addOnMessageReceivedListener(int i, android.companion.IOnMessageReceivedListener iOnMessageReceivedListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iOnMessageReceivedListener);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void removeOnMessageReceivedListener(int i, android.companion.IOnMessageReceivedListener iOnMessageReceivedListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iOnMessageReceivedListener);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void notifyDeviceAppeared(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void notifyDeviceDisappeared(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public android.app.PendingIntent buildPermissionTransferUserConsentIntent(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.PendingIntent) obtain2.readTypedObject(android.app.PendingIntent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean isPermissionTransferUserConsented(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void startSystemDataTransfer(java.lang.String str, int i, int i2, android.companion.ISystemDataTransferCallback iSystemDataTransferCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iSystemDataTransferCallback);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void attachSystemDataTransport(java.lang.String str, int i, int i2, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void detachSystemDataTransport(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean isCompanionApplicationBound(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public android.app.PendingIntent buildAssociationCancellationIntent(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.PendingIntent) obtain2.readTypedObject(android.app.PendingIntent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void enableSystemDataSync(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void disableSystemDataSync(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void enablePermissionsSync(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void disablePermissionsSync(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public android.companion.datatransfer.PermissionSyncRequest getPermissionSyncRequest(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.companion.datatransfer.PermissionSyncRequest) obtain2.readTypedObject(android.companion.datatransfer.PermissionSyncRequest.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void enableSecureTransport(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void setAssociationTag(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void clearAssociationTag(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public byte[] getBackupPayload(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void applyRestoredPayload(byte[] bArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void startObservingDevicePresence(android.companion.ObservingDevicePresenceRequest observingDevicePresenceRequest, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(observingDevicePresenceRequest, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void stopObservingDevicePresence(android.companion.ObservingDevicePresenceRequest observingDevicePresenceRequest, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(observingDevicePresenceRequest, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void getAllAssociationsForUser_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_COMPANION_DEVICES, getCallingPid(), getCallingUid());
        }

        protected void isDeviceAssociatedForWifiConnection_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_COMPANION_DEVICES, getCallingPid(), getCallingUid());
        }

        protected void registerDevicePresenceListenerService_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.REQUEST_OBSERVE_COMPANION_DEVICE_PRESENCE, getCallingPid(), getCallingUid());
        }

        protected void unregisterDevicePresenceListenerService_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.REQUEST_OBSERVE_COMPANION_DEVICE_PRESENCE, getCallingPid(), getCallingUid());
        }

        protected void createAssociation_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ASSOCIATE_COMPANION_DEVICES, getCallingPid(), getCallingUid());
        }

        protected void addOnAssociationsChangedListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_COMPANION_DEVICES, getCallingPid(), getCallingUid());
        }

        protected void removeOnAssociationsChangedListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_COMPANION_DEVICES, getCallingPid(), getCallingUid());
        }

        protected void addOnTransportsChangedListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_COMPANION_TRANSPORTS, getCallingPid(), getCallingUid());
        }

        protected void removeOnTransportsChangedListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_COMPANION_TRANSPORTS, getCallingPid(), getCallingUid());
        }

        protected void sendMessage_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_COMPANION_TRANSPORTS, getCallingPid(), getCallingUid());
        }

        protected void addOnMessageReceivedListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_COMPANION_TRANSPORTS, getCallingPid(), getCallingUid());
        }

        protected void removeOnMessageReceivedListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_COMPANION_TRANSPORTS, getCallingPid(), getCallingUid());
        }

        protected void attachSystemDataTransport_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.DELIVER_COMPANION_MESSAGES, getCallingPid(), getCallingUid());
        }

        protected void detachSystemDataTransport_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.DELIVER_COMPANION_MESSAGES, getCallingPid(), getCallingUid());
        }

        protected void enableSecureTransport_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_COMPANION_DEVICES, getCallingPid(), getCallingUid());
        }

        protected void startObservingDevicePresence_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.REQUEST_OBSERVE_COMPANION_DEVICE_PRESENCE, getCallingPid(), getCallingUid());
        }

        protected void stopObservingDevicePresence_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.REQUEST_OBSERVE_COMPANION_DEVICE_PRESENCE, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 39;
        }
    }
}
