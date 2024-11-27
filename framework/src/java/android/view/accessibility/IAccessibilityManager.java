package android.view.accessibility;

/* loaded from: classes4.dex */
public interface IAccessibilityManager extends android.os.IInterface {
    int addAccessibilityInteractionConnection(android.view.IWindow iWindow, android.os.IBinder iBinder, android.view.accessibility.IAccessibilityInteractionConnection iAccessibilityInteractionConnection, java.lang.String str, int i) throws android.os.RemoteException;

    long addClient(android.view.accessibility.IAccessibilityManagerClient iAccessibilityManagerClient, int i) throws android.os.RemoteException;

    void associateEmbeddedHierarchy(android.os.IBinder iBinder, android.os.IBinder iBinder2) throws android.os.RemoteException;

    void attachAccessibilityOverlayToDisplay(int i, android.view.SurfaceControl surfaceControl) throws android.os.RemoteException;

    void disassociateEmbeddedHierarchy(android.os.IBinder iBinder) throws android.os.RemoteException;

    java.util.List<java.lang.String> getAccessibilityShortcutTargets(int i) throws android.os.RemoteException;

    int getAccessibilityWindowId(android.os.IBinder iBinder) throws android.os.RemoteException;

    java.util.List<android.accessibilityservice.AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int i, int i2) throws android.os.RemoteException;

    int getFocusColor() throws android.os.RemoteException;

    int getFocusStrokeWidth() throws android.os.RemoteException;

    android.content.pm.ParceledListSlice<android.accessibilityservice.AccessibilityServiceInfo> getInstalledAccessibilityServiceList(int i) throws android.os.RemoteException;

    long getRecommendedTimeoutMillis() throws android.os.RemoteException;

    android.os.IBinder getWindowToken(int i, int i2) throws android.os.RemoteException;

    android.view.accessibility.IAccessibilityManager.WindowTransformationSpec getWindowTransformationSpec(int i) throws android.os.RemoteException;

    void injectInputEventToInputFilter(android.view.InputEvent inputEvent) throws android.os.RemoteException;

    void interrupt(int i) throws android.os.RemoteException;

    boolean isAccessibilityServiceWarningRequired(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) throws android.os.RemoteException;

    boolean isAccessibilityTargetAllowed(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    boolean isAudioDescriptionByDefaultEnabled() throws android.os.RemoteException;

    boolean isSystemAudioCaptioningUiEnabled(int i) throws android.os.RemoteException;

    void notifyAccessibilityButtonClicked(int i, java.lang.String str) throws android.os.RemoteException;

    void notifyAccessibilityButtonVisibilityChanged(boolean z) throws android.os.RemoteException;

    void notifyQuickSettingsTilesChanged(int i, java.util.List<android.content.ComponentName> list) throws android.os.RemoteException;

    void performAccessibilityShortcut(java.lang.String str) throws android.os.RemoteException;

    boolean registerProxyForDisplay(android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient, int i) throws android.os.RemoteException;

    void registerSystemAction(android.app.RemoteAction remoteAction, int i) throws android.os.RemoteException;

    void registerUiTestAutomationService(android.os.IBinder iBinder, android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient, android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo, int i, int i2) throws android.os.RemoteException;

    void removeAccessibilityInteractionConnection(android.view.IWindow iWindow) throws android.os.RemoteException;

    boolean removeClient(android.view.accessibility.IAccessibilityManagerClient iAccessibilityManagerClient, int i) throws android.os.RemoteException;

    void sendAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent, int i) throws android.os.RemoteException;

    boolean sendFingerprintGesture(int i) throws android.os.RemoteException;

    boolean sendRestrictedDialogIntent(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void setAccessibilityWindowAttributes(int i, int i2, int i3, android.view.accessibility.AccessibilityWindowAttributes accessibilityWindowAttributes) throws android.os.RemoteException;

    void setMagnificationConnection(android.view.accessibility.IMagnificationConnection iMagnificationConnection) throws android.os.RemoteException;

    void setPictureInPictureActionReplacingConnection(android.view.accessibility.IAccessibilityInteractionConnection iAccessibilityInteractionConnection) throws android.os.RemoteException;

    void setSystemAudioCaptioningEnabled(boolean z, int i) throws android.os.RemoteException;

    void setSystemAudioCaptioningUiEnabled(boolean z, int i) throws android.os.RemoteException;

    boolean startFlashNotificationEvent(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    boolean startFlashNotificationSequence(java.lang.String str, int i, android.os.IBinder iBinder) throws android.os.RemoteException;

    boolean stopFlashNotificationSequence(java.lang.String str) throws android.os.RemoteException;

    boolean unregisterProxyForDisplay(int i) throws android.os.RemoteException;

    void unregisterSystemAction(int i) throws android.os.RemoteException;

    void unregisterUiTestAutomationService(android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient) throws android.os.RemoteException;

    public static class Default implements android.view.accessibility.IAccessibilityManager {
        @Override // android.view.accessibility.IAccessibilityManager
        public void interrupt(int i) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void sendAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent, int i) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public long addClient(android.view.accessibility.IAccessibilityManagerClient iAccessibilityManagerClient, int i) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean removeClient(android.view.accessibility.IAccessibilityManagerClient iAccessibilityManagerClient, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public android.content.pm.ParceledListSlice<android.accessibilityservice.AccessibilityServiceInfo> getInstalledAccessibilityServiceList(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public java.util.List<android.accessibilityservice.AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public int addAccessibilityInteractionConnection(android.view.IWindow iWindow, android.os.IBinder iBinder, android.view.accessibility.IAccessibilityInteractionConnection iAccessibilityInteractionConnection, java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void removeAccessibilityInteractionConnection(android.view.IWindow iWindow) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void setPictureInPictureActionReplacingConnection(android.view.accessibility.IAccessibilityInteractionConnection iAccessibilityInteractionConnection) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void registerUiTestAutomationService(android.os.IBinder iBinder, android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient, android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void unregisterUiTestAutomationService(android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public android.os.IBinder getWindowToken(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void notifyAccessibilityButtonClicked(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void notifyAccessibilityButtonVisibilityChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void performAccessibilityShortcut(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public java.util.List<java.lang.String> getAccessibilityShortcutTargets(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean sendFingerprintGesture(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public int getAccessibilityWindowId(android.os.IBinder iBinder) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public long getRecommendedTimeoutMillis() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void registerSystemAction(android.app.RemoteAction remoteAction, int i) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void unregisterSystemAction(int i) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void setMagnificationConnection(android.view.accessibility.IMagnificationConnection iMagnificationConnection) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void associateEmbeddedHierarchy(android.os.IBinder iBinder, android.os.IBinder iBinder2) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void disassociateEmbeddedHierarchy(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public int getFocusStrokeWidth() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public int getFocusColor() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean isAudioDescriptionByDefaultEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void setSystemAudioCaptioningEnabled(boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean isSystemAudioCaptioningUiEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void setSystemAudioCaptioningUiEnabled(boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void setAccessibilityWindowAttributes(int i, int i2, int i3, android.view.accessibility.AccessibilityWindowAttributes accessibilityWindowAttributes) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean registerProxyForDisplay(android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean unregisterProxyForDisplay(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void injectInputEventToInputFilter(android.view.InputEvent inputEvent) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean startFlashNotificationSequence(java.lang.String str, int i, android.os.IBinder iBinder) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean stopFlashNotificationSequence(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean startFlashNotificationEvent(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean isAccessibilityTargetAllowed(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean sendRestrictedDialogIntent(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean isAccessibilityServiceWarningRequired(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public android.view.accessibility.IAccessibilityManager.WindowTransformationSpec getWindowTransformationSpec(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void attachAccessibilityOverlayToDisplay(int i, android.view.SurfaceControl surfaceControl) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void notifyQuickSettingsTilesChanged(int i, java.util.List<android.content.ComponentName> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.accessibility.IAccessibilityManager {
        public static final java.lang.String DESCRIPTOR = "android.view.accessibility.IAccessibilityManager";
        static final int TRANSACTION_addAccessibilityInteractionConnection = 7;
        static final int TRANSACTION_addClient = 3;
        static final int TRANSACTION_associateEmbeddedHierarchy = 23;
        static final int TRANSACTION_attachAccessibilityOverlayToDisplay = 42;
        static final int TRANSACTION_disassociateEmbeddedHierarchy = 24;
        static final int TRANSACTION_getAccessibilityShortcutTargets = 16;
        static final int TRANSACTION_getAccessibilityWindowId = 18;
        static final int TRANSACTION_getEnabledAccessibilityServiceList = 6;
        static final int TRANSACTION_getFocusColor = 26;
        static final int TRANSACTION_getFocusStrokeWidth = 25;
        static final int TRANSACTION_getInstalledAccessibilityServiceList = 5;
        static final int TRANSACTION_getRecommendedTimeoutMillis = 19;
        static final int TRANSACTION_getWindowToken = 12;
        static final int TRANSACTION_getWindowTransformationSpec = 41;
        static final int TRANSACTION_injectInputEventToInputFilter = 34;
        static final int TRANSACTION_interrupt = 1;
        static final int TRANSACTION_isAccessibilityServiceWarningRequired = 40;
        static final int TRANSACTION_isAccessibilityTargetAllowed = 38;
        static final int TRANSACTION_isAudioDescriptionByDefaultEnabled = 27;
        static final int TRANSACTION_isSystemAudioCaptioningUiEnabled = 29;
        static final int TRANSACTION_notifyAccessibilityButtonClicked = 13;
        static final int TRANSACTION_notifyAccessibilityButtonVisibilityChanged = 14;
        static final int TRANSACTION_notifyQuickSettingsTilesChanged = 43;
        static final int TRANSACTION_performAccessibilityShortcut = 15;
        static final int TRANSACTION_registerProxyForDisplay = 32;
        static final int TRANSACTION_registerSystemAction = 20;
        static final int TRANSACTION_registerUiTestAutomationService = 10;
        static final int TRANSACTION_removeAccessibilityInteractionConnection = 8;
        static final int TRANSACTION_removeClient = 4;
        static final int TRANSACTION_sendAccessibilityEvent = 2;
        static final int TRANSACTION_sendFingerprintGesture = 17;
        static final int TRANSACTION_sendRestrictedDialogIntent = 39;
        static final int TRANSACTION_setAccessibilityWindowAttributes = 31;
        static final int TRANSACTION_setMagnificationConnection = 22;
        static final int TRANSACTION_setPictureInPictureActionReplacingConnection = 9;
        static final int TRANSACTION_setSystemAudioCaptioningEnabled = 28;
        static final int TRANSACTION_setSystemAudioCaptioningUiEnabled = 30;
        static final int TRANSACTION_startFlashNotificationEvent = 37;
        static final int TRANSACTION_startFlashNotificationSequence = 35;
        static final int TRANSACTION_stopFlashNotificationSequence = 36;
        static final int TRANSACTION_unregisterProxyForDisplay = 33;
        static final int TRANSACTION_unregisterSystemAction = 21;
        static final int TRANSACTION_unregisterUiTestAutomationService = 11;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.view.accessibility.IAccessibilityManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.accessibility.IAccessibilityManager)) {
                return (android.view.accessibility.IAccessibilityManager) queryLocalInterface;
            }
            return new android.view.accessibility.IAccessibilityManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "interrupt";
                case 2:
                    return "sendAccessibilityEvent";
                case 3:
                    return "addClient";
                case 4:
                    return "removeClient";
                case 5:
                    return "getInstalledAccessibilityServiceList";
                case 6:
                    return "getEnabledAccessibilityServiceList";
                case 7:
                    return "addAccessibilityInteractionConnection";
                case 8:
                    return "removeAccessibilityInteractionConnection";
                case 9:
                    return "setPictureInPictureActionReplacingConnection";
                case 10:
                    return "registerUiTestAutomationService";
                case 11:
                    return "unregisterUiTestAutomationService";
                case 12:
                    return "getWindowToken";
                case 13:
                    return "notifyAccessibilityButtonClicked";
                case 14:
                    return "notifyAccessibilityButtonVisibilityChanged";
                case 15:
                    return "performAccessibilityShortcut";
                case 16:
                    return "getAccessibilityShortcutTargets";
                case 17:
                    return "sendFingerprintGesture";
                case 18:
                    return "getAccessibilityWindowId";
                case 19:
                    return "getRecommendedTimeoutMillis";
                case 20:
                    return "registerSystemAction";
                case 21:
                    return "unregisterSystemAction";
                case 22:
                    return "setMagnificationConnection";
                case 23:
                    return "associateEmbeddedHierarchy";
                case 24:
                    return "disassociateEmbeddedHierarchy";
                case 25:
                    return "getFocusStrokeWidth";
                case 26:
                    return "getFocusColor";
                case 27:
                    return "isAudioDescriptionByDefaultEnabled";
                case 28:
                    return "setSystemAudioCaptioningEnabled";
                case 29:
                    return "isSystemAudioCaptioningUiEnabled";
                case 30:
                    return "setSystemAudioCaptioningUiEnabled";
                case 31:
                    return "setAccessibilityWindowAttributes";
                case 32:
                    return "registerProxyForDisplay";
                case 33:
                    return "unregisterProxyForDisplay";
                case 34:
                    return "injectInputEventToInputFilter";
                case 35:
                    return "startFlashNotificationSequence";
                case 36:
                    return "stopFlashNotificationSequence";
                case 37:
                    return "startFlashNotificationEvent";
                case 38:
                    return "isAccessibilityTargetAllowed";
                case 39:
                    return "sendRestrictedDialogIntent";
                case 40:
                    return "isAccessibilityServiceWarningRequired";
                case 41:
                    return "getWindowTransformationSpec";
                case 42:
                    return "attachAccessibilityOverlayToDisplay";
                case 43:
                    return "notifyQuickSettingsTilesChanged";
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    interrupt(readInt);
                    return true;
                case 2:
                    android.view.accessibility.AccessibilityEvent accessibilityEvent = (android.view.accessibility.AccessibilityEvent) parcel.readTypedObject(android.view.accessibility.AccessibilityEvent.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendAccessibilityEvent(accessibilityEvent, readInt2);
                    return true;
                case 3:
                    android.view.accessibility.IAccessibilityManagerClient asInterface = android.view.accessibility.IAccessibilityManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long addClient = addClient(asInterface, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeLong(addClient);
                    return true;
                case 4:
                    android.view.accessibility.IAccessibilityManagerClient asInterface2 = android.view.accessibility.IAccessibilityManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeClient = removeClient(asInterface2, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeClient);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice<android.accessibilityservice.AccessibilityServiceInfo> installedAccessibilityServiceList = getInstalledAccessibilityServiceList(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installedAccessibilityServiceList, 1);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.accessibilityservice.AccessibilityServiceInfo> enabledAccessibilityServiceList = getEnabledAccessibilityServiceList(readInt6, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enabledAccessibilityServiceList, 1);
                    return true;
                case 7:
                    android.view.IWindow asInterface3 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.view.accessibility.IAccessibilityInteractionConnection asInterface4 = android.view.accessibility.IAccessibilityInteractionConnection.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int addAccessibilityInteractionConnection = addAccessibilityInteractionConnection(asInterface3, readStrongBinder, asInterface4, readString, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeInt(addAccessibilityInteractionConnection);
                    return true;
                case 8:
                    android.view.IWindow asInterface5 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeAccessibilityInteractionConnection(asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.view.accessibility.IAccessibilityInteractionConnection asInterface6 = android.view.accessibility.IAccessibilityInteractionConnection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setPictureInPictureActionReplacingConnection(asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    android.accessibilityservice.IAccessibilityServiceClient asInterface7 = android.accessibilityservice.IAccessibilityServiceClient.Stub.asInterface(parcel.readStrongBinder());
                    android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo = (android.accessibilityservice.AccessibilityServiceInfo) parcel.readTypedObject(android.accessibilityservice.AccessibilityServiceInfo.CREATOR);
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerUiTestAutomationService(readStrongBinder2, asInterface7, accessibilityServiceInfo, readInt9, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    android.accessibilityservice.IAccessibilityServiceClient asInterface8 = android.accessibilityservice.IAccessibilityServiceClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUiTestAutomationService(asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.IBinder windowToken = getWindowToken(readInt11, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(windowToken);
                    return true;
                case 13:
                    int readInt13 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyAccessibilityButtonClicked(readInt13, readString2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyAccessibilityButtonVisibilityChanged(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    performAccessibilityShortcut(readString3);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> accessibilityShortcutTargets = getAccessibilityShortcutTargets(readInt14);
                    parcel2.writeNoException();
                    parcel2.writeStringList(accessibilityShortcutTargets);
                    return true;
                case 17:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean sendFingerprintGesture = sendFingerprintGesture(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendFingerprintGesture);
                    return true;
                case 18:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int accessibilityWindowId = getAccessibilityWindowId(readStrongBinder3);
                    parcel2.writeNoException();
                    parcel2.writeInt(accessibilityWindowId);
                    return true;
                case 19:
                    long recommendedTimeoutMillis = getRecommendedTimeoutMillis();
                    parcel2.writeNoException();
                    parcel2.writeLong(recommendedTimeoutMillis);
                    return true;
                case 20:
                    android.app.RemoteAction remoteAction = (android.app.RemoteAction) parcel.readTypedObject(android.app.RemoteAction.CREATOR);
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerSystemAction(remoteAction, readInt16);
                    return true;
                case 21:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterSystemAction(readInt17);
                    return true;
                case 22:
                    android.view.accessibility.IMagnificationConnection asInterface9 = android.view.accessibility.IMagnificationConnection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setMagnificationConnection(asInterface9);
                    return true;
                case 23:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    associateEmbeddedHierarchy(readStrongBinder4, readStrongBinder5);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    disassociateEmbeddedHierarchy(readStrongBinder6);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int focusStrokeWidth = getFocusStrokeWidth();
                    parcel2.writeNoException();
                    parcel2.writeInt(focusStrokeWidth);
                    return true;
                case 26:
                    int focusColor = getFocusColor();
                    parcel2.writeNoException();
                    parcel2.writeInt(focusColor);
                    return true;
                case 27:
                    boolean isAudioDescriptionByDefaultEnabled = isAudioDescriptionByDefaultEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAudioDescriptionByDefaultEnabled);
                    return true;
                case 28:
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSystemAudioCaptioningEnabled(readBoolean2, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSystemAudioCaptioningUiEnabled = isSystemAudioCaptioningUiEnabled(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSystemAudioCaptioningUiEnabled);
                    return true;
                case 30:
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSystemAudioCaptioningUiEnabled(readBoolean3, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    android.view.accessibility.AccessibilityWindowAttributes accessibilityWindowAttributes = (android.view.accessibility.AccessibilityWindowAttributes) parcel.readTypedObject(android.view.accessibility.AccessibilityWindowAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    setAccessibilityWindowAttributes(readInt21, readInt22, readInt23, accessibilityWindowAttributes);
                    return true;
                case 32:
                    android.accessibilityservice.IAccessibilityServiceClient asInterface10 = android.accessibilityservice.IAccessibilityServiceClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean registerProxyForDisplay = registerProxyForDisplay(asInterface10, readInt24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerProxyForDisplay);
                    return true;
                case 33:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean unregisterProxyForDisplay = unregisterProxyForDisplay(readInt25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterProxyForDisplay);
                    return true;
                case 34:
                    android.view.InputEvent inputEvent = (android.view.InputEvent) parcel.readTypedObject(android.view.InputEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    injectInputEventToInputFilter(inputEvent);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    java.lang.String readString4 = parcel.readString();
                    int readInt26 = parcel.readInt();
                    android.os.IBinder readStrongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean startFlashNotificationSequence = startFlashNotificationSequence(readString4, readInt26, readStrongBinder7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startFlashNotificationSequence);
                    return true;
                case 36:
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean stopFlashNotificationSequence = stopFlashNotificationSequence(readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(stopFlashNotificationSequence);
                    return true;
                case 37:
                    java.lang.String readString6 = parcel.readString();
                    int readInt27 = parcel.readInt();
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean startFlashNotificationEvent = startFlashNotificationEvent(readString6, readInt27, readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startFlashNotificationEvent);
                    return true;
                case 38:
                    java.lang.String readString8 = parcel.readString();
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAccessibilityTargetAllowed = isAccessibilityTargetAllowed(readString8, readInt28, readInt29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAccessibilityTargetAllowed);
                    return true;
                case 39:
                    java.lang.String readString9 = parcel.readString();
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean sendRestrictedDialogIntent = sendRestrictedDialogIntent(readString9, readInt30, readInt31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendRestrictedDialogIntent);
                    return true;
                case 40:
                    android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo2 = (android.accessibilityservice.AccessibilityServiceInfo) parcel.readTypedObject(android.accessibilityservice.AccessibilityServiceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isAccessibilityServiceWarningRequired = isAccessibilityServiceWarningRequired(accessibilityServiceInfo2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAccessibilityServiceWarningRequired);
                    return true;
                case 41:
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.view.accessibility.IAccessibilityManager.WindowTransformationSpec windowTransformationSpec = getWindowTransformationSpec(readInt32);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(windowTransformationSpec, 1);
                    return true;
                case 42:
                    int readInt33 = parcel.readInt();
                    android.view.SurfaceControl surfaceControl = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
                    parcel.enforceNoDataAvail();
                    attachAccessibilityOverlayToDisplay(readInt33, surfaceControl);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    int readInt34 = parcel.readInt();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyQuickSettingsTilesChanged(readInt34, createTypedArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.accessibility.IAccessibilityManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR;
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void interrupt(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void sendAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(accessibilityEvent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public long addClient(android.view.accessibility.IAccessibilityManagerClient iAccessibilityManagerClient, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccessibilityManagerClient);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean removeClient(android.view.accessibility.IAccessibilityManagerClient iAccessibilityManagerClient, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccessibilityManagerClient);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public android.content.pm.ParceledListSlice<android.accessibilityservice.AccessibilityServiceInfo> getInstalledAccessibilityServiceList(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public java.util.List<android.accessibilityservice.AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.accessibilityservice.AccessibilityServiceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public int addAccessibilityInteractionConnection(android.view.IWindow iWindow, android.os.IBinder iBinder, android.view.accessibility.IAccessibilityInteractionConnection iAccessibilityInteractionConnection, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnection);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void removeAccessibilityInteractionConnection(android.view.IWindow iWindow) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setPictureInPictureActionReplacingConnection(android.view.accessibility.IAccessibilityInteractionConnection iAccessibilityInteractionConnection) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnection);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void registerUiTestAutomationService(android.os.IBinder iBinder, android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient, android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iAccessibilityServiceClient);
                    obtain.writeTypedObject(accessibilityServiceInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void unregisterUiTestAutomationService(android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccessibilityServiceClient);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public android.os.IBinder getWindowToken(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void notifyAccessibilityButtonClicked(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void notifyAccessibilityButtonVisibilityChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void performAccessibilityShortcut(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public java.util.List<java.lang.String> getAccessibilityShortcutTargets(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean sendFingerprintGesture(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public int getAccessibilityWindowId(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public long getRecommendedTimeoutMillis() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void registerSystemAction(android.app.RemoteAction remoteAction, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(remoteAction, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void unregisterSystemAction(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setMagnificationConnection(android.view.accessibility.IMagnificationConnection iMagnificationConnection) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMagnificationConnection);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void associateEmbeddedHierarchy(android.os.IBinder iBinder, android.os.IBinder iBinder2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void disassociateEmbeddedHierarchy(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public int getFocusStrokeWidth() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public int getFocusColor() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isAudioDescriptionByDefaultEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setSystemAudioCaptioningEnabled(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isSystemAudioCaptioningUiEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setSystemAudioCaptioningUiEnabled(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setAccessibilityWindowAttributes(int i, int i2, int i3, android.view.accessibility.AccessibilityWindowAttributes accessibilityWindowAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(accessibilityWindowAttributes, 0);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean registerProxyForDisplay(android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccessibilityServiceClient);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean unregisterProxyForDisplay(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void injectInputEventToInputFilter(android.view.InputEvent inputEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputEvent, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean startFlashNotificationSequence(java.lang.String str, int i, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean stopFlashNotificationSequence(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean startFlashNotificationEvent(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isAccessibilityTargetAllowed(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean sendRestrictedDialogIntent(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isAccessibilityServiceWarningRequired(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(accessibilityServiceInfo, 0);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public android.view.accessibility.IAccessibilityManager.WindowTransformationSpec getWindowTransformationSpec(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.accessibility.IAccessibilityManager.WindowTransformationSpec) obtain2.readTypedObject(android.view.accessibility.IAccessibilityManager.WindowTransformationSpec.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void attachAccessibilityOverlayToDisplay(int i, android.view.SurfaceControl surfaceControl) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(surfaceControl, 0);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void notifyQuickSettingsTilesChanged(int i, java.util.List<android.content.ComponentName> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IAccessibilityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(43, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 42;
        }
    }

    public static class WindowTransformationSpec implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.view.accessibility.IAccessibilityManager.WindowTransformationSpec> CREATOR = new android.os.Parcelable.Creator<android.view.accessibility.IAccessibilityManager.WindowTransformationSpec>() { // from class: android.view.accessibility.IAccessibilityManager.WindowTransformationSpec.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.accessibility.IAccessibilityManager.WindowTransformationSpec createFromParcel(android.os.Parcel parcel) {
                android.view.accessibility.IAccessibilityManager.WindowTransformationSpec windowTransformationSpec = new android.view.accessibility.IAccessibilityManager.WindowTransformationSpec();
                windowTransformationSpec.readFromParcel(parcel);
                return windowTransformationSpec;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.accessibility.IAccessibilityManager.WindowTransformationSpec[] newArray(int i) {
                return new android.view.accessibility.IAccessibilityManager.WindowTransformationSpec[i];
            }
        };
        public android.view.MagnificationSpec magnificationSpec;
        public float[] transformationMatrix;

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeFloatArray(this.transformationMatrix);
            parcel.writeTypedObject(this.magnificationSpec, i);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(android.os.Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new android.os.BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.transformationMatrix = parcel.createFloatArray();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.magnificationSpec = (android.view.MagnificationSpec) parcel.readTypedObject(android.view.MagnificationSpec.CREATOR);
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                }
            } catch (java.lang.Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return describeContents(this.magnificationSpec) | 0;
        }

        private int describeContents(java.lang.Object obj) {
            if (obj == null || !(obj instanceof android.os.Parcelable)) {
                return 0;
            }
            return ((android.os.Parcelable) obj).describeContents();
        }
    }
}
