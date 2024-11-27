package android.accessibilityservice;

/* loaded from: classes.dex */
public interface IAccessibilityServiceConnection extends android.os.IInterface {
    void attachAccessibilityOverlayToDisplay(int i, int i2, android.view.SurfaceControl surfaceControl, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws android.os.RemoteException;

    void attachAccessibilityOverlayToWindow(int i, int i2, android.view.SurfaceControl surfaceControl, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws android.os.RemoteException;

    void connectBluetoothBrailleDisplay(java.lang.String str, android.accessibilityservice.IBrailleDisplayController iBrailleDisplayController) throws android.os.RemoteException;

    void connectUsbBrailleDisplay(android.hardware.usb.UsbDevice usbDevice, android.accessibilityservice.IBrailleDisplayController iBrailleDisplayController) throws android.os.RemoteException;

    void disableSelf() throws android.os.RemoteException;

    void dispatchGesture(int i, android.content.pm.ParceledListSlice parceledListSlice, int i2) throws android.os.RemoteException;

    java.lang.String[] findAccessibilityNodeInfoByAccessibilityId(int i, long j, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, long j2, android.os.Bundle bundle) throws android.os.RemoteException;

    java.lang.String[] findAccessibilityNodeInfosByText(int i, long j, java.lang.String str, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException;

    java.lang.String[] findAccessibilityNodeInfosByViewId(int i, long j, java.lang.String str, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException;

    java.lang.String[] findFocus(int i, long j, int i2, int i3, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException;

    java.lang.String[] focusSearch(int i, long j, int i2, int i3, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException;

    android.graphics.Region getCurrentMagnificationRegion(int i) throws android.os.RemoteException;

    java.util.List<android.accessibilityservice.AccessibilityServiceInfo> getInstalledAndEnabledServices() throws android.os.RemoteException;

    float getMagnificationCenterX(int i) throws android.os.RemoteException;

    float getMagnificationCenterY(int i) throws android.os.RemoteException;

    android.accessibilityservice.MagnificationConfig getMagnificationConfig(int i) throws android.os.RemoteException;

    android.graphics.Region getMagnificationRegion(int i) throws android.os.RemoteException;

    float getMagnificationScale(int i) throws android.os.RemoteException;

    android.os.IBinder getOverlayWindowToken(int i) throws android.os.RemoteException;

    android.accessibilityservice.AccessibilityServiceInfo getServiceInfo() throws android.os.RemoteException;

    int getSoftKeyboardShowMode() throws android.os.RemoteException;

    java.util.List<android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction> getSystemActions() throws android.os.RemoteException;

    android.view.accessibility.AccessibilityWindowInfo getWindow(int i) throws android.os.RemoteException;

    int getWindowIdForLeashToken(android.os.IBinder iBinder) throws android.os.RemoteException;

    android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray getWindows() throws android.os.RemoteException;

    boolean isAccessibilityButtonAvailable() throws android.os.RemoteException;

    boolean isFingerprintGestureDetectionAvailable() throws android.os.RemoteException;

    void logTrace(long j, java.lang.String str, long j2, java.lang.String str2, int i, long j3, int i2, android.os.Bundle bundle) throws android.os.RemoteException;

    void onDoubleTap(int i) throws android.os.RemoteException;

    void onDoubleTapAndHold(int i) throws android.os.RemoteException;

    boolean performAccessibilityAction(int i, long j, int i2, android.os.Bundle bundle, int i3, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException;

    boolean performGlobalAction(int i) throws android.os.RemoteException;

    void requestDelegating(int i) throws android.os.RemoteException;

    void requestDragging(int i, int i2) throws android.os.RemoteException;

    void requestTouchExploration(int i) throws android.os.RemoteException;

    boolean resetCurrentMagnification(int i, boolean z) throws android.os.RemoteException;

    boolean resetMagnification(int i, boolean z) throws android.os.RemoteException;

    void sendGesture(int i, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException;

    void setAnimationScale(float f) throws android.os.RemoteException;

    void setAttributionTag(java.lang.String str) throws android.os.RemoteException;

    void setCacheEnabled(boolean z) throws android.os.RemoteException;

    void setFocusAppearance(int i, int i2) throws android.os.RemoteException;

    void setGestureDetectionPassthroughRegion(int i, android.graphics.Region region) throws android.os.RemoteException;

    int setInputMethodEnabled(java.lang.String str, boolean z) throws android.os.RemoteException;

    void setInstalledAndEnabledServices(java.util.List<android.accessibilityservice.AccessibilityServiceInfo> list) throws android.os.RemoteException;

    void setMagnificationCallbackEnabled(int i, boolean z) throws android.os.RemoteException;

    boolean setMagnificationConfig(int i, android.accessibilityservice.MagnificationConfig magnificationConfig, boolean z) throws android.os.RemoteException;

    void setOnKeyEventResult(boolean z, int i) throws android.os.RemoteException;

    void setServiceDetectsGesturesEnabled(int i, boolean z) throws android.os.RemoteException;

    void setServiceInfo(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) throws android.os.RemoteException;

    void setSoftKeyboardCallbackEnabled(boolean z) throws android.os.RemoteException;

    boolean setSoftKeyboardShowMode(int i) throws android.os.RemoteException;

    void setTestBrailleDisplayData(java.util.List<android.os.Bundle> list) throws android.os.RemoteException;

    void setTouchExplorationPassthroughRegion(int i, android.graphics.Region region) throws android.os.RemoteException;

    boolean switchToInputMethod(java.lang.String str) throws android.os.RemoteException;

    void takeScreenshot(int i, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void takeScreenshotOfWindow(int i, int i2, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws android.os.RemoteException;

    public static class Default implements android.accessibilityservice.IAccessibilityServiceConnection {
        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setServiceInfo(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setAttributionTag(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public java.lang.String[] findAccessibilityNodeInfoByAccessibilityId(int i, long j, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, long j2, android.os.Bundle bundle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public java.lang.String[] findAccessibilityNodeInfosByText(int i, long j, java.lang.String str, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public java.lang.String[] findAccessibilityNodeInfosByViewId(int i, long j, java.lang.String str, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public java.lang.String[] findFocus(int i, long j, int i2, int i3, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public java.lang.String[] focusSearch(int i, long j, int i2, int i3, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public boolean performAccessibilityAction(int i, long j, int i2, android.os.Bundle bundle, int i3, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public android.view.accessibility.AccessibilityWindowInfo getWindow(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray getWindows() throws android.os.RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public android.accessibilityservice.AccessibilityServiceInfo getServiceInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public boolean performGlobalAction(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public java.util.List<android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction> getSystemActions() throws android.os.RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void disableSelf() throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setOnKeyEventResult(boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public android.accessibilityservice.MagnificationConfig getMagnificationConfig(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public float getMagnificationScale(int i) throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public float getMagnificationCenterX(int i) throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public float getMagnificationCenterY(int i) throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public android.graphics.Region getMagnificationRegion(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public android.graphics.Region getCurrentMagnificationRegion(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public boolean resetMagnification(int i, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public boolean resetCurrentMagnification(int i, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public boolean setMagnificationConfig(int i, android.accessibilityservice.MagnificationConfig magnificationConfig, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setMagnificationCallbackEnabled(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public boolean setSoftKeyboardShowMode(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public int getSoftKeyboardShowMode() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setSoftKeyboardCallbackEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public boolean switchToInputMethod(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public int setInputMethodEnabled(java.lang.String str, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public boolean isAccessibilityButtonAvailable() throws android.os.RemoteException {
            return false;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void sendGesture(int i, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void dispatchGesture(int i, android.content.pm.ParceledListSlice parceledListSlice, int i2) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public boolean isFingerprintGestureDetectionAvailable() throws android.os.RemoteException {
            return false;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public android.os.IBinder getOverlayWindowToken(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public int getWindowIdForLeashToken(android.os.IBinder iBinder) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void takeScreenshot(int i, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void takeScreenshotOfWindow(int i, int i2, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setGestureDetectionPassthroughRegion(int i, android.graphics.Region region) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setTouchExplorationPassthroughRegion(int i, android.graphics.Region region) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setFocusAppearance(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setCacheEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void logTrace(long j, java.lang.String str, long j2, java.lang.String str2, int i, long j3, int i2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setServiceDetectsGesturesEnabled(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void requestTouchExploration(int i) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void requestDragging(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void requestDelegating(int i) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void onDoubleTap(int i) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void onDoubleTapAndHold(int i) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setAnimationScale(float f) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setInstalledAndEnabledServices(java.util.List<android.accessibilityservice.AccessibilityServiceInfo> list) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public java.util.List<android.accessibilityservice.AccessibilityServiceInfo> getInstalledAndEnabledServices() throws android.os.RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void attachAccessibilityOverlayToDisplay(int i, int i2, android.view.SurfaceControl surfaceControl, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void attachAccessibilityOverlayToWindow(int i, int i2, android.view.SurfaceControl surfaceControl, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void connectBluetoothBrailleDisplay(java.lang.String str, android.accessibilityservice.IBrailleDisplayController iBrailleDisplayController) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void connectUsbBrailleDisplay(android.hardware.usb.UsbDevice usbDevice, android.accessibilityservice.IBrailleDisplayController iBrailleDisplayController) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setTestBrailleDisplayData(java.util.List<android.os.Bundle> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.accessibilityservice.IAccessibilityServiceConnection {
        public static final java.lang.String DESCRIPTOR = "android.accessibilityservice.IAccessibilityServiceConnection";
        static final int TRANSACTION_attachAccessibilityOverlayToDisplay = 53;
        static final int TRANSACTION_attachAccessibilityOverlayToWindow = 54;
        static final int TRANSACTION_connectBluetoothBrailleDisplay = 55;
        static final int TRANSACTION_connectUsbBrailleDisplay = 56;
        static final int TRANSACTION_disableSelf = 14;
        static final int TRANSACTION_dispatchGesture = 33;
        static final int TRANSACTION_findAccessibilityNodeInfoByAccessibilityId = 3;
        static final int TRANSACTION_findAccessibilityNodeInfosByText = 4;
        static final int TRANSACTION_findAccessibilityNodeInfosByViewId = 5;
        static final int TRANSACTION_findFocus = 6;
        static final int TRANSACTION_focusSearch = 7;
        static final int TRANSACTION_getCurrentMagnificationRegion = 21;
        static final int TRANSACTION_getInstalledAndEnabledServices = 52;
        static final int TRANSACTION_getMagnificationCenterX = 18;
        static final int TRANSACTION_getMagnificationCenterY = 19;
        static final int TRANSACTION_getMagnificationConfig = 16;
        static final int TRANSACTION_getMagnificationRegion = 20;
        static final int TRANSACTION_getMagnificationScale = 17;
        static final int TRANSACTION_getOverlayWindowToken = 35;
        static final int TRANSACTION_getServiceInfo = 11;
        static final int TRANSACTION_getSoftKeyboardShowMode = 27;
        static final int TRANSACTION_getSystemActions = 13;
        static final int TRANSACTION_getWindow = 9;
        static final int TRANSACTION_getWindowIdForLeashToken = 36;
        static final int TRANSACTION_getWindows = 10;
        static final int TRANSACTION_isAccessibilityButtonAvailable = 31;
        static final int TRANSACTION_isFingerprintGestureDetectionAvailable = 34;
        static final int TRANSACTION_logTrace = 43;
        static final int TRANSACTION_onDoubleTap = 48;
        static final int TRANSACTION_onDoubleTapAndHold = 49;
        static final int TRANSACTION_performAccessibilityAction = 8;
        static final int TRANSACTION_performGlobalAction = 12;
        static final int TRANSACTION_requestDelegating = 47;
        static final int TRANSACTION_requestDragging = 46;
        static final int TRANSACTION_requestTouchExploration = 45;
        static final int TRANSACTION_resetCurrentMagnification = 23;
        static final int TRANSACTION_resetMagnification = 22;
        static final int TRANSACTION_sendGesture = 32;
        static final int TRANSACTION_setAnimationScale = 50;
        static final int TRANSACTION_setAttributionTag = 2;
        static final int TRANSACTION_setCacheEnabled = 42;
        static final int TRANSACTION_setFocusAppearance = 41;
        static final int TRANSACTION_setGestureDetectionPassthroughRegion = 39;
        static final int TRANSACTION_setInputMethodEnabled = 30;
        static final int TRANSACTION_setInstalledAndEnabledServices = 51;
        static final int TRANSACTION_setMagnificationCallbackEnabled = 25;
        static final int TRANSACTION_setMagnificationConfig = 24;
        static final int TRANSACTION_setOnKeyEventResult = 15;
        static final int TRANSACTION_setServiceDetectsGesturesEnabled = 44;
        static final int TRANSACTION_setServiceInfo = 1;
        static final int TRANSACTION_setSoftKeyboardCallbackEnabled = 28;
        static final int TRANSACTION_setSoftKeyboardShowMode = 26;
        static final int TRANSACTION_setTestBrailleDisplayData = 57;
        static final int TRANSACTION_setTouchExplorationPassthroughRegion = 40;
        static final int TRANSACTION_switchToInputMethod = 29;
        static final int TRANSACTION_takeScreenshot = 37;
        static final int TRANSACTION_takeScreenshotOfWindow = 38;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.accessibilityservice.IAccessibilityServiceConnection asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.accessibilityservice.IAccessibilityServiceConnection)) {
                return (android.accessibilityservice.IAccessibilityServiceConnection) queryLocalInterface;
            }
            return new android.accessibilityservice.IAccessibilityServiceConnection.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setServiceInfo";
                case 2:
                    return "setAttributionTag";
                case 3:
                    return "findAccessibilityNodeInfoByAccessibilityId";
                case 4:
                    return "findAccessibilityNodeInfosByText";
                case 5:
                    return "findAccessibilityNodeInfosByViewId";
                case 6:
                    return "findFocus";
                case 7:
                    return "focusSearch";
                case 8:
                    return "performAccessibilityAction";
                case 9:
                    return "getWindow";
                case 10:
                    return "getWindows";
                case 11:
                    return "getServiceInfo";
                case 12:
                    return "performGlobalAction";
                case 13:
                    return "getSystemActions";
                case 14:
                    return "disableSelf";
                case 15:
                    return "setOnKeyEventResult";
                case 16:
                    return "getMagnificationConfig";
                case 17:
                    return "getMagnificationScale";
                case 18:
                    return "getMagnificationCenterX";
                case 19:
                    return "getMagnificationCenterY";
                case 20:
                    return "getMagnificationRegion";
                case 21:
                    return "getCurrentMagnificationRegion";
                case 22:
                    return "resetMagnification";
                case 23:
                    return "resetCurrentMagnification";
                case 24:
                    return "setMagnificationConfig";
                case 25:
                    return "setMagnificationCallbackEnabled";
                case 26:
                    return "setSoftKeyboardShowMode";
                case 27:
                    return "getSoftKeyboardShowMode";
                case 28:
                    return "setSoftKeyboardCallbackEnabled";
                case 29:
                    return "switchToInputMethod";
                case 30:
                    return "setInputMethodEnabled";
                case 31:
                    return "isAccessibilityButtonAvailable";
                case 32:
                    return "sendGesture";
                case 33:
                    return "dispatchGesture";
                case 34:
                    return "isFingerprintGestureDetectionAvailable";
                case 35:
                    return "getOverlayWindowToken";
                case 36:
                    return "getWindowIdForLeashToken";
                case 37:
                    return "takeScreenshot";
                case 38:
                    return "takeScreenshotOfWindow";
                case 39:
                    return "setGestureDetectionPassthroughRegion";
                case 40:
                    return "setTouchExplorationPassthroughRegion";
                case 41:
                    return "setFocusAppearance";
                case 42:
                    return "setCacheEnabled";
                case 43:
                    return "logTrace";
                case 44:
                    return "setServiceDetectsGesturesEnabled";
                case 45:
                    return "requestTouchExploration";
                case 46:
                    return "requestDragging";
                case 47:
                    return "requestDelegating";
                case 48:
                    return "onDoubleTap";
                case 49:
                    return "onDoubleTapAndHold";
                case 50:
                    return "setAnimationScale";
                case 51:
                    return "setInstalledAndEnabledServices";
                case 52:
                    return "getInstalledAndEnabledServices";
                case 53:
                    return "attachAccessibilityOverlayToDisplay";
                case 54:
                    return "attachAccessibilityOverlayToWindow";
                case 55:
                    return "connectBluetoothBrailleDisplay";
                case 56:
                    return "connectUsbBrailleDisplay";
                case 57:
                    return "setTestBrailleDisplayData";
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
                    android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo = (android.accessibilityservice.AccessibilityServiceInfo) parcel.readTypedObject(android.accessibilityservice.AccessibilityServiceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setServiceInfo(accessibilityServiceInfo);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAttributionTag(readString);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    long readLong = parcel.readLong();
                    int readInt2 = parcel.readInt();
                    android.view.accessibility.IAccessibilityInteractionConnectionCallback asInterface = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt3 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.lang.String[] findAccessibilityNodeInfoByAccessibilityId = findAccessibilityNodeInfoByAccessibilityId(readInt, readLong, readInt2, asInterface, readInt3, readLong2, bundle);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(findAccessibilityNodeInfoByAccessibilityId);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    java.lang.String readString2 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    android.view.accessibility.IAccessibilityInteractionConnectionCallback asInterface2 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] findAccessibilityNodeInfosByText = findAccessibilityNodeInfosByText(readInt4, readLong3, readString2, readInt5, asInterface2, readLong4);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(findAccessibilityNodeInfosByText);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    long readLong5 = parcel.readLong();
                    java.lang.String readString3 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    android.view.accessibility.IAccessibilityInteractionConnectionCallback asInterface3 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    long readLong6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] findAccessibilityNodeInfosByViewId = findAccessibilityNodeInfosByViewId(readInt6, readLong5, readString3, readInt7, asInterface3, readLong6);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(findAccessibilityNodeInfosByViewId);
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    long readLong7 = parcel.readLong();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    android.view.accessibility.IAccessibilityInteractionConnectionCallback asInterface4 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    long readLong8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] findFocus = findFocus(readInt8, readLong7, readInt9, readInt10, asInterface4, readLong8);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(findFocus);
                    return true;
                case 7:
                    int readInt11 = parcel.readInt();
                    long readLong9 = parcel.readLong();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    android.view.accessibility.IAccessibilityInteractionConnectionCallback asInterface5 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    long readLong10 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] focusSearch = focusSearch(readInt11, readLong9, readInt12, readInt13, asInterface5, readLong10);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(focusSearch);
                    return true;
                case 8:
                    int readInt14 = parcel.readInt();
                    long readLong11 = parcel.readLong();
                    int readInt15 = parcel.readInt();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt16 = parcel.readInt();
                    android.view.accessibility.IAccessibilityInteractionConnectionCallback asInterface6 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    long readLong12 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean performAccessibilityAction = performAccessibilityAction(readInt14, readLong11, readInt15, bundle2, readInt16, asInterface6, readLong12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(performAccessibilityAction);
                    return true;
                case 9:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.view.accessibility.AccessibilityWindowInfo window = getWindow(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(window, 1);
                    return true;
                case 10:
                    android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray windows = getWindows();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(windows, 1);
                    return true;
                case 11:
                    android.accessibilityservice.AccessibilityServiceInfo serviceInfo = getServiceInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(serviceInfo, 1);
                    return true;
                case 12:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean performGlobalAction = performGlobalAction(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(performGlobalAction);
                    return true;
                case 13:
                    java.util.List<android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction> systemActions = getSystemActions();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(systemActions, 1);
                    return true;
                case 14:
                    disableSelf();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean readBoolean = parcel.readBoolean();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setOnKeyEventResult(readBoolean, readInt19);
                    return true;
                case 16:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.accessibilityservice.MagnificationConfig magnificationConfig = getMagnificationConfig(readInt20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(magnificationConfig, 1);
                    return true;
                case 17:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float magnificationScale = getMagnificationScale(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeFloat(magnificationScale);
                    return true;
                case 18:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float magnificationCenterX = getMagnificationCenterX(readInt22);
                    parcel2.writeNoException();
                    parcel2.writeFloat(magnificationCenterX);
                    return true;
                case 19:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float magnificationCenterY = getMagnificationCenterY(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeFloat(magnificationCenterY);
                    return true;
                case 20:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.graphics.Region magnificationRegion = getMagnificationRegion(readInt24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(magnificationRegion, 1);
                    return true;
                case 21:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.graphics.Region currentMagnificationRegion = getCurrentMagnificationRegion(readInt25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentMagnificationRegion, 1);
                    return true;
                case 22:
                    int readInt26 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean resetMagnification = resetMagnification(readInt26, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetMagnification);
                    return true;
                case 23:
                    int readInt27 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean resetCurrentMagnification = resetCurrentMagnification(readInt27, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetCurrentMagnification);
                    return true;
                case 24:
                    int readInt28 = parcel.readInt();
                    android.accessibilityservice.MagnificationConfig magnificationConfig2 = (android.accessibilityservice.MagnificationConfig) parcel.readTypedObject(android.accessibilityservice.MagnificationConfig.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean magnificationConfig3 = setMagnificationConfig(readInt28, magnificationConfig2, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(magnificationConfig3);
                    return true;
                case 25:
                    int readInt29 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMagnificationCallbackEnabled(readInt29, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean softKeyboardShowMode = setSoftKeyboardShowMode(readInt30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(softKeyboardShowMode);
                    return true;
                case 27:
                    int softKeyboardShowMode2 = getSoftKeyboardShowMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(softKeyboardShowMode2);
                    return true;
                case 28:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSoftKeyboardCallbackEnabled(readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean switchToInputMethod = switchToInputMethod(readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(switchToInputMethod);
                    return true;
                case 30:
                    java.lang.String readString5 = parcel.readString();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int inputMethodEnabled = setInputMethodEnabled(readString5, readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeInt(inputMethodEnabled);
                    return true;
                case 31:
                    boolean isAccessibilityButtonAvailable = isAccessibilityButtonAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAccessibilityButtonAvailable);
                    return true;
                case 32:
                    int readInt31 = parcel.readInt();
                    android.content.pm.ParceledListSlice parceledListSlice = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendGesture(readInt31, parceledListSlice);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int readInt32 = parcel.readInt();
                    android.content.pm.ParceledListSlice parceledListSlice2 = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchGesture(readInt32, parceledListSlice2, readInt33);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    boolean isFingerprintGestureDetectionAvailable = isFingerprintGestureDetectionAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFingerprintGestureDetectionAvailable);
                    return true;
                case 35:
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.IBinder overlayWindowToken = getOverlayWindowToken(readInt34);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(overlayWindowToken);
                    return true;
                case 36:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int windowIdForLeashToken = getWindowIdForLeashToken(readStrongBinder);
                    parcel2.writeNoException();
                    parcel2.writeInt(windowIdForLeashToken);
                    return true;
                case 37:
                    int readInt35 = parcel.readInt();
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    takeScreenshot(readInt35, remoteCallback);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int readInt36 = parcel.readInt();
                    int readInt37 = parcel.readInt();
                    android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener = (android.window.ScreenCapture.ScreenCaptureListener) parcel.readTypedObject(android.window.ScreenCapture.ScreenCaptureListener.CREATOR);
                    android.view.accessibility.IAccessibilityInteractionConnectionCallback asInterface7 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    takeScreenshotOfWindow(readInt36, readInt37, screenCaptureListener, asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int readInt38 = parcel.readInt();
                    android.graphics.Region region = (android.graphics.Region) parcel.readTypedObject(android.graphics.Region.CREATOR);
                    parcel.enforceNoDataAvail();
                    setGestureDetectionPassthroughRegion(readInt38, region);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int readInt39 = parcel.readInt();
                    android.graphics.Region region2 = (android.graphics.Region) parcel.readTypedObject(android.graphics.Region.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTouchExplorationPassthroughRegion(readInt39, region2);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int readInt40 = parcel.readInt();
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFocusAppearance(readInt40, readInt41);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCacheEnabled(readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    long readLong13 = parcel.readLong();
                    java.lang.String readString6 = parcel.readString();
                    long readLong14 = parcel.readLong();
                    java.lang.String readString7 = parcel.readString();
                    int readInt42 = parcel.readInt();
                    long readLong15 = parcel.readLong();
                    int readInt43 = parcel.readInt();
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    logTrace(readLong13, readString6, readLong14, readString7, readInt42, readLong15, readInt43, bundle3);
                    return true;
                case 44:
                    int readInt44 = parcel.readInt();
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setServiceDetectsGesturesEnabled(readInt44, readBoolean9);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestTouchExploration(readInt45);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int readInt46 = parcel.readInt();
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestDragging(readInt46, readInt47);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestDelegating(readInt48);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDoubleTap(readInt49);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDoubleTapAndHold(readInt50);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setAnimationScale(readFloat);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.accessibilityservice.AccessibilityServiceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setInstalledAndEnabledServices(createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    java.util.List<android.accessibilityservice.AccessibilityServiceInfo> installedAndEnabledServices = getInstalledAndEnabledServices();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(installedAndEnabledServices, 1);
                    return true;
                case 53:
                    int readInt51 = parcel.readInt();
                    int readInt52 = parcel.readInt();
                    android.view.SurfaceControl surfaceControl = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
                    android.view.accessibility.IAccessibilityInteractionConnectionCallback asInterface8 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    attachAccessibilityOverlayToDisplay(readInt51, readInt52, surfaceControl, asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    int readInt53 = parcel.readInt();
                    int readInt54 = parcel.readInt();
                    android.view.SurfaceControl surfaceControl2 = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
                    android.view.accessibility.IAccessibilityInteractionConnectionCallback asInterface9 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    attachAccessibilityOverlayToWindow(readInt53, readInt54, surfaceControl2, asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    java.lang.String readString8 = parcel.readString();
                    android.accessibilityservice.IBrailleDisplayController asInterface10 = android.accessibilityservice.IBrailleDisplayController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    connectBluetoothBrailleDisplay(readString8, asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    android.hardware.usb.UsbDevice usbDevice = (android.hardware.usb.UsbDevice) parcel.readTypedObject(android.hardware.usb.UsbDevice.CREATOR);
                    android.accessibilityservice.IBrailleDisplayController asInterface11 = android.accessibilityservice.IBrailleDisplayController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    connectUsbBrailleDisplay(usbDevice, asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTestBrailleDisplayData(createTypedArrayList2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.accessibilityservice.IAccessibilityServiceConnection {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR;
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setServiceInfo(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(accessibilityServiceInfo, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setAttributionTag(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public java.lang.String[] findAccessibilityNodeInfoByAccessibilityId(int i, long j, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, long j2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeInt(i3);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public java.lang.String[] findAccessibilityNodeInfosByText(int i, long j, java.lang.String str, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeLong(j2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public java.lang.String[] findAccessibilityNodeInfosByViewId(int i, long j, java.lang.String str, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeLong(j2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public java.lang.String[] findFocus(int i, long j, int i2, int i3, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeLong(j2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public java.lang.String[] focusSearch(int i, long j, int i2, int i3, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeLong(j2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean performAccessibilityAction(int i, long j, int i2, android.os.Bundle bundle, int i3, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i3);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeLong(j2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public android.view.accessibility.AccessibilityWindowInfo getWindow(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.accessibility.AccessibilityWindowInfo) obtain2.readTypedObject(android.view.accessibility.AccessibilityWindowInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray getWindows() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray) obtain2.readTypedObject(android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public android.accessibilityservice.AccessibilityServiceInfo getServiceInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.accessibilityservice.AccessibilityServiceInfo) obtain2.readTypedObject(android.accessibilityservice.AccessibilityServiceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean performGlobalAction(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public java.util.List<android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction> getSystemActions() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void disableSelf() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setOnKeyEventResult(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public android.accessibilityservice.MagnificationConfig getMagnificationConfig(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.accessibilityservice.MagnificationConfig) obtain2.readTypedObject(android.accessibilityservice.MagnificationConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public float getMagnificationScale(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public float getMagnificationCenterX(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public float getMagnificationCenterY(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public android.graphics.Region getMagnificationRegion(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.graphics.Region) obtain2.readTypedObject(android.graphics.Region.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public android.graphics.Region getCurrentMagnificationRegion(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.graphics.Region) obtain2.readTypedObject(android.graphics.Region.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean resetMagnification(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean resetCurrentMagnification(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean setMagnificationConfig(int i, android.accessibilityservice.MagnificationConfig magnificationConfig, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(magnificationConfig, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setMagnificationCallbackEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean setSoftKeyboardShowMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public int getSoftKeyboardShowMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setSoftKeyboardCallbackEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean switchToInputMethod(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public int setInputMethodEnabled(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean isAccessibilityButtonAvailable() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void sendGesture(int i, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void dispatchGesture(int i, android.content.pm.ParceledListSlice parceledListSlice, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean isFingerprintGestureDetectionAvailable() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public android.os.IBinder getOverlayWindowToken(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public int getWindowIdForLeashToken(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void takeScreenshot(int i, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void takeScreenshotOfWindow(int i, int i2, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(screenCaptureListener, 0);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setGestureDetectionPassthroughRegion(int i, android.graphics.Region region) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(region, 0);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setTouchExplorationPassthroughRegion(int i, android.graphics.Region region) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(region, 0);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setFocusAppearance(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setCacheEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void logTrace(long j, java.lang.String str, long j2, java.lang.String str2, int i, long j3, int i2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeLong(j2);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeLong(j3);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(43, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setServiceDetectsGesturesEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void requestTouchExploration(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void requestDragging(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void requestDelegating(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void onDoubleTap(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void onDoubleTapAndHold(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setAnimationScale(float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setInstalledAndEnabledServices(java.util.List<android.accessibilityservice.AccessibilityServiceInfo> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public java.util.List<android.accessibilityservice.AccessibilityServiceInfo> getInstalledAndEnabledServices() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.accessibilityservice.AccessibilityServiceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void attachAccessibilityOverlayToDisplay(int i, int i2, android.view.SurfaceControl surfaceControl, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(surfaceControl, 0);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void attachAccessibilityOverlayToWindow(int i, int i2, android.view.SurfaceControl surfaceControl, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(surfaceControl, 0);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void connectBluetoothBrailleDisplay(java.lang.String str, android.accessibilityservice.IBrailleDisplayController iBrailleDisplayController) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iBrailleDisplayController);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void connectUsbBrailleDisplay(android.hardware.usb.UsbDevice usbDevice, android.accessibilityservice.IBrailleDisplayController iBrailleDisplayController) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbDevice, 0);
                    obtain.writeStrongInterface(iBrailleDisplayController);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setTestBrailleDisplayData(java.util.List<android.os.Bundle> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceConnection.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 56;
        }
    }
}
