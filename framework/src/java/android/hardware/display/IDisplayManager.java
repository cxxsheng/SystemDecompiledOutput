package android.hardware.display;

/* loaded from: classes2.dex */
public interface IDisplayManager extends android.os.IInterface {
    boolean areUserDisabledHdrTypesAllowed() throws android.os.RemoteException;

    void connectWifiDisplay(java.lang.String str) throws android.os.RemoteException;

    int createVirtualDisplay(android.hardware.display.VirtualDisplayConfig virtualDisplayConfig, android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, android.media.projection.IMediaProjection iMediaProjection, java.lang.String str) throws android.os.RemoteException;

    void disableConnectedDisplay(int i) throws android.os.RemoteException;

    void disconnectWifiDisplay() throws android.os.RemoteException;

    void enableConnectedDisplay(int i) throws android.os.RemoteException;

    void forgetWifiDisplay(java.lang.String str) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getAmbientBrightnessStats() throws android.os.RemoteException;

    float getBrightness(int i) throws android.os.RemoteException;

    android.hardware.display.BrightnessConfiguration getBrightnessConfigurationForDisplay(java.lang.String str, int i) throws android.os.RemoteException;

    android.hardware.display.BrightnessConfiguration getBrightnessConfigurationForUser(int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getBrightnessEvents(java.lang.String str) throws android.os.RemoteException;

    android.hardware.display.BrightnessInfo getBrightnessInfo(int i) throws android.os.RemoteException;

    android.hardware.display.BrightnessConfiguration getDefaultBrightnessConfiguration() throws android.os.RemoteException;

    android.hardware.graphics.common.DisplayDecorationSupport getDisplayDecorationSupport(int i) throws android.os.RemoteException;

    int[] getDisplayIds(boolean z) throws android.os.RemoteException;

    android.view.DisplayInfo getDisplayInfo(int i) throws android.os.RemoteException;

    android.hardware.display.HdrConversionMode getHdrConversionMode() throws android.os.RemoteException;

    android.hardware.display.HdrConversionMode getHdrConversionModeSetting() throws android.os.RemoteException;

    android.hardware.display.Curve getMinimumBrightnessCurve() throws android.os.RemoteException;

    android.hardware.OverlayProperties getOverlaySupport() throws android.os.RemoteException;

    int getPreferredWideGamutColorSpaceId() throws android.os.RemoteException;

    int getRefreshRateSwitchingType() throws android.os.RemoteException;

    android.graphics.Point getStableDisplaySize() throws android.os.RemoteException;

    int[] getSupportedHdrOutputTypes() throws android.os.RemoteException;

    android.view.Display.Mode getSystemPreferredDisplayMode(int i) throws android.os.RemoteException;

    int[] getUserDisabledHdrTypes() throws android.os.RemoteException;

    android.view.Display.Mode getUserPreferredDisplayMode(int i) throws android.os.RemoteException;

    android.hardware.display.WifiDisplayStatus getWifiDisplayStatus() throws android.os.RemoteException;

    boolean isMinimalPostProcessingRequested(int i) throws android.os.RemoteException;

    boolean isUidPresentOnDisplay(int i, int i2) throws android.os.RemoteException;

    void overrideHdrTypes(int i, int[] iArr) throws android.os.RemoteException;

    void pauseWifiDisplay() throws android.os.RemoteException;

    void registerCallback(android.hardware.display.IDisplayManagerCallback iDisplayManagerCallback) throws android.os.RemoteException;

    void registerCallbackWithEventMask(android.hardware.display.IDisplayManagerCallback iDisplayManagerCallback, long j) throws android.os.RemoteException;

    void releaseVirtualDisplay(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback) throws android.os.RemoteException;

    void renameWifiDisplay(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void requestColorMode(int i, int i2) throws android.os.RemoteException;

    void resizeVirtualDisplay(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, int i, int i2, int i3) throws android.os.RemoteException;

    void resumeWifiDisplay() throws android.os.RemoteException;

    void setAreUserDisabledHdrTypesAllowed(boolean z) throws android.os.RemoteException;

    void setBrightness(int i, float f) throws android.os.RemoteException;

    void setBrightnessConfigurationForDisplay(android.hardware.display.BrightnessConfiguration brightnessConfiguration, java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    void setBrightnessConfigurationForUser(android.hardware.display.BrightnessConfiguration brightnessConfiguration, int i, java.lang.String str) throws android.os.RemoteException;

    void setDisplayIdToMirror(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void setHdrConversionMode(android.hardware.display.HdrConversionMode hdrConversionMode) throws android.os.RemoteException;

    void setRefreshRateSwitchingType(int i) throws android.os.RemoteException;

    void setShouldAlwaysRespectAppRequestedMode(boolean z) throws android.os.RemoteException;

    void setTemporaryAutoBrightnessAdjustment(float f) throws android.os.RemoteException;

    void setTemporaryBrightness(int i, float f) throws android.os.RemoteException;

    void setUserDisabledHdrTypes(int[] iArr) throws android.os.RemoteException;

    void setUserPreferredDisplayMode(int i, android.view.Display.Mode mode) throws android.os.RemoteException;

    void setVirtualDisplayState(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, boolean z) throws android.os.RemoteException;

    void setVirtualDisplaySurface(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, android.view.Surface surface) throws android.os.RemoteException;

    boolean shouldAlwaysRespectAppRequestedMode() throws android.os.RemoteException;

    void startWifiDisplayScan() throws android.os.RemoteException;

    void stopWifiDisplayScan() throws android.os.RemoteException;

    public static class Default implements android.hardware.display.IDisplayManager {
        @Override // android.hardware.display.IDisplayManager
        public android.view.DisplayInfo getDisplayInfo(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public int[] getDisplayIds(boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean isUidPresentOnDisplay(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public void registerCallback(android.hardware.display.IDisplayManagerCallback iDisplayManagerCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void registerCallbackWithEventMask(android.hardware.display.IDisplayManagerCallback iDisplayManagerCallback, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void startWifiDisplayScan() throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void stopWifiDisplayScan() throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void connectWifiDisplay(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void disconnectWifiDisplay() throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void renameWifiDisplay(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void forgetWifiDisplay(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void pauseWifiDisplay() throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void resumeWifiDisplay() throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public android.hardware.display.WifiDisplayStatus getWifiDisplayStatus() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setUserDisabledHdrTypes(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setAreUserDisabledHdrTypesAllowed(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean areUserDisabledHdrTypesAllowed() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public int[] getUserDisabledHdrTypes() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public void overrideHdrTypes(int i, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void requestColorMode(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public int createVirtualDisplay(android.hardware.display.VirtualDisplayConfig virtualDisplayConfig, android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, android.media.projection.IMediaProjection iMediaProjection, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IDisplayManager
        public void resizeVirtualDisplay(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setVirtualDisplaySurface(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, android.view.Surface surface) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void releaseVirtualDisplay(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setVirtualDisplayState(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public android.graphics.Point getStableDisplaySize() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public android.content.pm.ParceledListSlice getBrightnessEvents(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public android.content.pm.ParceledListSlice getAmbientBrightnessStats() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setBrightnessConfigurationForUser(android.hardware.display.BrightnessConfiguration brightnessConfiguration, int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setBrightnessConfigurationForDisplay(android.hardware.display.BrightnessConfiguration brightnessConfiguration, java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public android.hardware.display.BrightnessConfiguration getBrightnessConfigurationForDisplay(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public android.hardware.display.BrightnessConfiguration getBrightnessConfigurationForUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public android.hardware.display.BrightnessConfiguration getDefaultBrightnessConfiguration() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean isMinimalPostProcessingRequested(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setTemporaryBrightness(int i, float f) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setBrightness(int i, float f) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public float getBrightness(int i) throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setTemporaryAutoBrightnessAdjustment(float f) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public android.hardware.display.Curve getMinimumBrightnessCurve() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public android.hardware.display.BrightnessInfo getBrightnessInfo(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public int getPreferredWideGamutColorSpaceId() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setUserPreferredDisplayMode(int i, android.view.Display.Mode mode) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public android.view.Display.Mode getUserPreferredDisplayMode(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public android.view.Display.Mode getSystemPreferredDisplayMode(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setHdrConversionMode(android.hardware.display.HdrConversionMode hdrConversionMode) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public android.hardware.display.HdrConversionMode getHdrConversionModeSetting() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public android.hardware.display.HdrConversionMode getHdrConversionMode() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public int[] getSupportedHdrOutputTypes() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setShouldAlwaysRespectAppRequestedMode(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean shouldAlwaysRespectAppRequestedMode() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setRefreshRateSwitchingType(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public int getRefreshRateSwitchingType() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IDisplayManager
        public android.hardware.graphics.common.DisplayDecorationSupport getDisplayDecorationSupport(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setDisplayIdToMirror(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public android.hardware.OverlayProperties getOverlaySupport() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public void enableConnectedDisplay(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void disableConnectedDisplay(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.display.IDisplayManager {
        public static final java.lang.String DESCRIPTOR = "android.hardware.display.IDisplayManager";
        static final int TRANSACTION_areUserDisabledHdrTypesAllowed = 17;
        static final int TRANSACTION_connectWifiDisplay = 8;
        static final int TRANSACTION_createVirtualDisplay = 21;
        static final int TRANSACTION_disableConnectedDisplay = 57;
        static final int TRANSACTION_disconnectWifiDisplay = 9;
        static final int TRANSACTION_enableConnectedDisplay = 56;
        static final int TRANSACTION_forgetWifiDisplay = 11;
        static final int TRANSACTION_getAmbientBrightnessStats = 28;
        static final int TRANSACTION_getBrightness = 37;
        static final int TRANSACTION_getBrightnessConfigurationForDisplay = 31;
        static final int TRANSACTION_getBrightnessConfigurationForUser = 32;
        static final int TRANSACTION_getBrightnessEvents = 27;
        static final int TRANSACTION_getBrightnessInfo = 40;
        static final int TRANSACTION_getDefaultBrightnessConfiguration = 33;
        static final int TRANSACTION_getDisplayDecorationSupport = 53;
        static final int TRANSACTION_getDisplayIds = 2;
        static final int TRANSACTION_getDisplayInfo = 1;
        static final int TRANSACTION_getHdrConversionMode = 47;
        static final int TRANSACTION_getHdrConversionModeSetting = 46;
        static final int TRANSACTION_getMinimumBrightnessCurve = 39;
        static final int TRANSACTION_getOverlaySupport = 55;
        static final int TRANSACTION_getPreferredWideGamutColorSpaceId = 41;
        static final int TRANSACTION_getRefreshRateSwitchingType = 52;
        static final int TRANSACTION_getStableDisplaySize = 26;
        static final int TRANSACTION_getSupportedHdrOutputTypes = 48;
        static final int TRANSACTION_getSystemPreferredDisplayMode = 44;
        static final int TRANSACTION_getUserDisabledHdrTypes = 18;
        static final int TRANSACTION_getUserPreferredDisplayMode = 43;
        static final int TRANSACTION_getWifiDisplayStatus = 14;
        static final int TRANSACTION_isMinimalPostProcessingRequested = 34;
        static final int TRANSACTION_isUidPresentOnDisplay = 3;
        static final int TRANSACTION_overrideHdrTypes = 19;
        static final int TRANSACTION_pauseWifiDisplay = 12;
        static final int TRANSACTION_registerCallback = 4;
        static final int TRANSACTION_registerCallbackWithEventMask = 5;
        static final int TRANSACTION_releaseVirtualDisplay = 24;
        static final int TRANSACTION_renameWifiDisplay = 10;
        static final int TRANSACTION_requestColorMode = 20;
        static final int TRANSACTION_resizeVirtualDisplay = 22;
        static final int TRANSACTION_resumeWifiDisplay = 13;
        static final int TRANSACTION_setAreUserDisabledHdrTypesAllowed = 16;
        static final int TRANSACTION_setBrightness = 36;
        static final int TRANSACTION_setBrightnessConfigurationForDisplay = 30;
        static final int TRANSACTION_setBrightnessConfigurationForUser = 29;
        static final int TRANSACTION_setDisplayIdToMirror = 54;
        static final int TRANSACTION_setHdrConversionMode = 45;
        static final int TRANSACTION_setRefreshRateSwitchingType = 51;
        static final int TRANSACTION_setShouldAlwaysRespectAppRequestedMode = 49;
        static final int TRANSACTION_setTemporaryAutoBrightnessAdjustment = 38;
        static final int TRANSACTION_setTemporaryBrightness = 35;
        static final int TRANSACTION_setUserDisabledHdrTypes = 15;
        static final int TRANSACTION_setUserPreferredDisplayMode = 42;
        static final int TRANSACTION_setVirtualDisplayState = 25;
        static final int TRANSACTION_setVirtualDisplaySurface = 23;
        static final int TRANSACTION_shouldAlwaysRespectAppRequestedMode = 50;
        static final int TRANSACTION_startWifiDisplayScan = 6;
        static final int TRANSACTION_stopWifiDisplayScan = 7;
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

        public static android.hardware.display.IDisplayManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.display.IDisplayManager)) {
                return (android.hardware.display.IDisplayManager) queryLocalInterface;
            }
            return new android.hardware.display.IDisplayManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getDisplayInfo";
                case 2:
                    return "getDisplayIds";
                case 3:
                    return "isUidPresentOnDisplay";
                case 4:
                    return "registerCallback";
                case 5:
                    return "registerCallbackWithEventMask";
                case 6:
                    return "startWifiDisplayScan";
                case 7:
                    return "stopWifiDisplayScan";
                case 8:
                    return "connectWifiDisplay";
                case 9:
                    return "disconnectWifiDisplay";
                case 10:
                    return "renameWifiDisplay";
                case 11:
                    return "forgetWifiDisplay";
                case 12:
                    return "pauseWifiDisplay";
                case 13:
                    return "resumeWifiDisplay";
                case 14:
                    return "getWifiDisplayStatus";
                case 15:
                    return "setUserDisabledHdrTypes";
                case 16:
                    return "setAreUserDisabledHdrTypesAllowed";
                case 17:
                    return "areUserDisabledHdrTypesAllowed";
                case 18:
                    return "getUserDisabledHdrTypes";
                case 19:
                    return "overrideHdrTypes";
                case 20:
                    return "requestColorMode";
                case 21:
                    return "createVirtualDisplay";
                case 22:
                    return "resizeVirtualDisplay";
                case 23:
                    return "setVirtualDisplaySurface";
                case 24:
                    return "releaseVirtualDisplay";
                case 25:
                    return "setVirtualDisplayState";
                case 26:
                    return "getStableDisplaySize";
                case 27:
                    return "getBrightnessEvents";
                case 28:
                    return "getAmbientBrightnessStats";
                case 29:
                    return "setBrightnessConfigurationForUser";
                case 30:
                    return "setBrightnessConfigurationForDisplay";
                case 31:
                    return "getBrightnessConfigurationForDisplay";
                case 32:
                    return "getBrightnessConfigurationForUser";
                case 33:
                    return "getDefaultBrightnessConfiguration";
                case 34:
                    return "isMinimalPostProcessingRequested";
                case 35:
                    return "setTemporaryBrightness";
                case 36:
                    return "setBrightness";
                case 37:
                    return "getBrightness";
                case 38:
                    return "setTemporaryAutoBrightnessAdjustment";
                case 39:
                    return "getMinimumBrightnessCurve";
                case 40:
                    return "getBrightnessInfo";
                case 41:
                    return "getPreferredWideGamutColorSpaceId";
                case 42:
                    return "setUserPreferredDisplayMode";
                case 43:
                    return "getUserPreferredDisplayMode";
                case 44:
                    return "getSystemPreferredDisplayMode";
                case 45:
                    return "setHdrConversionMode";
                case 46:
                    return "getHdrConversionModeSetting";
                case 47:
                    return "getHdrConversionMode";
                case 48:
                    return "getSupportedHdrOutputTypes";
                case 49:
                    return "setShouldAlwaysRespectAppRequestedMode";
                case 50:
                    return "shouldAlwaysRespectAppRequestedMode";
                case 51:
                    return "setRefreshRateSwitchingType";
                case 52:
                    return "getRefreshRateSwitchingType";
                case 53:
                    return "getDisplayDecorationSupport";
                case 54:
                    return "setDisplayIdToMirror";
                case 55:
                    return "getOverlaySupport";
                case 56:
                    return "enableConnectedDisplay";
                case 57:
                    return "disableConnectedDisplay";
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
                    android.view.DisplayInfo displayInfo = getDisplayInfo(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(displayInfo, 1);
                    return true;
                case 2:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int[] displayIds = getDisplayIds(readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(displayIds);
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUidPresentOnDisplay = isUidPresentOnDisplay(readInt2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUidPresentOnDisplay);
                    return true;
                case 4:
                    android.hardware.display.IDisplayManagerCallback asInterface = android.hardware.display.IDisplayManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.hardware.display.IDisplayManagerCallback asInterface2 = android.hardware.display.IDisplayManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    registerCallbackWithEventMask(asInterface2, readLong);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    startWifiDisplayScan();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    stopWifiDisplayScan();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    connectWifiDisplay(readString);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    disconnectWifiDisplay();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    renameWifiDisplay(readString2, readString3);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    forgetWifiDisplay(readString4);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    pauseWifiDisplay();
                    parcel2.writeNoException();
                    return true;
                case 13:
                    resumeWifiDisplay();
                    parcel2.writeNoException();
                    return true;
                case 14:
                    android.hardware.display.WifiDisplayStatus wifiDisplayStatus = getWifiDisplayStatus();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wifiDisplayStatus, 1);
                    return true;
                case 15:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setUserDisabledHdrTypes(createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAreUserDisabledHdrTypesAllowed(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    boolean areUserDisabledHdrTypesAllowed = areUserDisabledHdrTypesAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(areUserDisabledHdrTypesAllowed);
                    return true;
                case 18:
                    int[] userDisabledHdrTypes = getUserDisabledHdrTypes();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(userDisabledHdrTypes);
                    return true;
                case 19:
                    int readInt4 = parcel.readInt();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    overrideHdrTypes(readInt4, createIntArray2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestColorMode(readInt5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    android.hardware.display.VirtualDisplayConfig virtualDisplayConfig = (android.hardware.display.VirtualDisplayConfig) parcel.readTypedObject(android.hardware.display.VirtualDisplayConfig.CREATOR);
                    android.hardware.display.IVirtualDisplayCallback asInterface3 = android.hardware.display.IVirtualDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.media.projection.IMediaProjection asInterface4 = android.media.projection.IMediaProjection.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int createVirtualDisplay = createVirtualDisplay(virtualDisplayConfig, asInterface3, asInterface4, readString5);
                    parcel2.writeNoException();
                    parcel2.writeInt(createVirtualDisplay);
                    return true;
                case 22:
                    android.hardware.display.IVirtualDisplayCallback asInterface5 = android.hardware.display.IVirtualDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resizeVirtualDisplay(asInterface5, readInt7, readInt8, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    android.hardware.display.IVirtualDisplayCallback asInterface6 = android.hardware.display.IVirtualDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.view.Surface surface = (android.view.Surface) parcel.readTypedObject(android.view.Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    setVirtualDisplaySurface(asInterface6, surface);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    android.hardware.display.IVirtualDisplayCallback asInterface7 = android.hardware.display.IVirtualDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    releaseVirtualDisplay(asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    android.hardware.display.IVirtualDisplayCallback asInterface8 = android.hardware.display.IVirtualDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVirtualDisplayState(asInterface8, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    android.graphics.Point stableDisplaySize = getStableDisplaySize();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(stableDisplaySize, 1);
                    return true;
                case 27:
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice brightnessEvents = getBrightnessEvents(readString6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(brightnessEvents, 1);
                    return true;
                case 28:
                    android.content.pm.ParceledListSlice ambientBrightnessStats = getAmbientBrightnessStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ambientBrightnessStats, 1);
                    return true;
                case 29:
                    android.hardware.display.BrightnessConfiguration brightnessConfiguration = (android.hardware.display.BrightnessConfiguration) parcel.readTypedObject(android.hardware.display.BrightnessConfiguration.CREATOR);
                    int readInt10 = parcel.readInt();
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBrightnessConfigurationForUser(brightnessConfiguration, readInt10, readString7);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    android.hardware.display.BrightnessConfiguration brightnessConfiguration2 = (android.hardware.display.BrightnessConfiguration) parcel.readTypedObject(android.hardware.display.BrightnessConfiguration.CREATOR);
                    java.lang.String readString8 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBrightnessConfigurationForDisplay(brightnessConfiguration2, readString8, readInt11, readString9);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    java.lang.String readString10 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.display.BrightnessConfiguration brightnessConfigurationForDisplay = getBrightnessConfigurationForDisplay(readString10, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(brightnessConfigurationForDisplay, 1);
                    return true;
                case 32:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.display.BrightnessConfiguration brightnessConfigurationForUser = getBrightnessConfigurationForUser(readInt13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(brightnessConfigurationForUser, 1);
                    return true;
                case 33:
                    android.hardware.display.BrightnessConfiguration defaultBrightnessConfiguration = getDefaultBrightnessConfiguration();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultBrightnessConfiguration, 1);
                    return true;
                case 34:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isMinimalPostProcessingRequested = isMinimalPostProcessingRequested(readInt14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMinimalPostProcessingRequested);
                    return true;
                case 35:
                    int readInt15 = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setTemporaryBrightness(readInt15, readFloat);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int readInt16 = parcel.readInt();
                    float readFloat2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setBrightness(readInt16, readFloat2);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float brightness = getBrightness(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeFloat(brightness);
                    return true;
                case 38:
                    float readFloat3 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setTemporaryAutoBrightnessAdjustment(readFloat3);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    android.hardware.display.Curve minimumBrightnessCurve = getMinimumBrightnessCurve();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(minimumBrightnessCurve, 1);
                    return true;
                case 40:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.display.BrightnessInfo brightnessInfo = getBrightnessInfo(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(brightnessInfo, 1);
                    return true;
                case 41:
                    int preferredWideGamutColorSpaceId = getPreferredWideGamutColorSpaceId();
                    parcel2.writeNoException();
                    parcel2.writeInt(preferredWideGamutColorSpaceId);
                    return true;
                case 42:
                    int readInt19 = parcel.readInt();
                    android.view.Display.Mode mode = (android.view.Display.Mode) parcel.readTypedObject(android.view.Display.Mode.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUserPreferredDisplayMode(readInt19, mode);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.view.Display.Mode userPreferredDisplayMode = getUserPreferredDisplayMode(readInt20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userPreferredDisplayMode, 1);
                    return true;
                case 44:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.view.Display.Mode systemPreferredDisplayMode = getSystemPreferredDisplayMode(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(systemPreferredDisplayMode, 1);
                    return true;
                case 45:
                    android.hardware.display.HdrConversionMode hdrConversionMode = (android.hardware.display.HdrConversionMode) parcel.readTypedObject(android.hardware.display.HdrConversionMode.CREATOR);
                    parcel.enforceNoDataAvail();
                    setHdrConversionMode(hdrConversionMode);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    android.hardware.display.HdrConversionMode hdrConversionModeSetting = getHdrConversionModeSetting();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(hdrConversionModeSetting, 1);
                    return true;
                case 47:
                    android.hardware.display.HdrConversionMode hdrConversionMode2 = getHdrConversionMode();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(hdrConversionMode2, 1);
                    return true;
                case 48:
                    int[] supportedHdrOutputTypes = getSupportedHdrOutputTypes();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedHdrOutputTypes);
                    return true;
                case 49:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShouldAlwaysRespectAppRequestedMode(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    boolean shouldAlwaysRespectAppRequestedMode = shouldAlwaysRespectAppRequestedMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldAlwaysRespectAppRequestedMode);
                    return true;
                case 51:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRefreshRateSwitchingType(readInt22);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int refreshRateSwitchingType = getRefreshRateSwitchingType();
                    parcel2.writeNoException();
                    parcel2.writeInt(refreshRateSwitchingType);
                    return true;
                case 53:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.graphics.common.DisplayDecorationSupport displayDecorationSupport = getDisplayDecorationSupport(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(displayDecorationSupport, 1);
                    return true;
                case 54:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisplayIdToMirror(readStrongBinder, readInt24);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    android.hardware.OverlayProperties overlaySupport = getOverlaySupport();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(overlaySupport, 1);
                    return true;
                case 56:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableConnectedDisplay(readInt25);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableConnectedDisplay(readInt26);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.display.IDisplayManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.display.IDisplayManager.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.display.IDisplayManager
            public android.view.DisplayInfo getDisplayInfo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.DisplayInfo) obtain2.readTypedObject(android.view.DisplayInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int[] getDisplayIds(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean isUidPresentOnDisplay(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void registerCallback(android.hardware.display.IDisplayManagerCallback iDisplayManagerCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayManagerCallback);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void registerCallbackWithEventMask(android.hardware.display.IDisplayManagerCallback iDisplayManagerCallback, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayManagerCallback);
                    obtain.writeLong(j);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void startWifiDisplayScan() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void stopWifiDisplayScan() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void connectWifiDisplay(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void disconnectWifiDisplay() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void renameWifiDisplay(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void forgetWifiDisplay(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void pauseWifiDisplay() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void resumeWifiDisplay() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public android.hardware.display.WifiDisplayStatus getWifiDisplayStatus() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.display.WifiDisplayStatus) obtain2.readTypedObject(android.hardware.display.WifiDisplayStatus.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setUserDisabledHdrTypes(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setAreUserDisabledHdrTypesAllowed(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean areUserDisabledHdrTypesAllowed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int[] getUserDisabledHdrTypes() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void overrideHdrTypes(int i, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void requestColorMode(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int createVirtualDisplay(android.hardware.display.VirtualDisplayConfig virtualDisplayConfig, android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, android.media.projection.IMediaProjection iMediaProjection, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(virtualDisplayConfig, 0);
                    obtain.writeStrongInterface(iVirtualDisplayCallback);
                    obtain.writeStrongInterface(iMediaProjection);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void resizeVirtualDisplay(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVirtualDisplayCallback);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setVirtualDisplaySurface(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, android.view.Surface surface) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVirtualDisplayCallback);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void releaseVirtualDisplay(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVirtualDisplayCallback);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setVirtualDisplayState(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVirtualDisplayCallback);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public android.graphics.Point getStableDisplaySize() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.graphics.Point) obtain2.readTypedObject(android.graphics.Point.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public android.content.pm.ParceledListSlice getBrightnessEvents(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public android.content.pm.ParceledListSlice getAmbientBrightnessStats() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setBrightnessConfigurationForUser(android.hardware.display.BrightnessConfiguration brightnessConfiguration, int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(brightnessConfiguration, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setBrightnessConfigurationForDisplay(android.hardware.display.BrightnessConfiguration brightnessConfiguration, java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(brightnessConfiguration, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public android.hardware.display.BrightnessConfiguration getBrightnessConfigurationForDisplay(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.display.BrightnessConfiguration) obtain2.readTypedObject(android.hardware.display.BrightnessConfiguration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public android.hardware.display.BrightnessConfiguration getBrightnessConfigurationForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.display.BrightnessConfiguration) obtain2.readTypedObject(android.hardware.display.BrightnessConfiguration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public android.hardware.display.BrightnessConfiguration getDefaultBrightnessConfiguration() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.display.BrightnessConfiguration) obtain2.readTypedObject(android.hardware.display.BrightnessConfiguration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean isMinimalPostProcessingRequested(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setTemporaryBrightness(int i, float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setBrightness(int i, float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public float getBrightness(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setTemporaryAutoBrightnessAdjustment(float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public android.hardware.display.Curve getMinimumBrightnessCurve() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.display.Curve) obtain2.readTypedObject(android.hardware.display.Curve.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public android.hardware.display.BrightnessInfo getBrightnessInfo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.display.BrightnessInfo) obtain2.readTypedObject(android.hardware.display.BrightnessInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int getPreferredWideGamutColorSpaceId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setUserPreferredDisplayMode(int i, android.view.Display.Mode mode) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(mode, 0);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public android.view.Display.Mode getUserPreferredDisplayMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.Display.Mode) obtain2.readTypedObject(android.view.Display.Mode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public android.view.Display.Mode getSystemPreferredDisplayMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.Display.Mode) obtain2.readTypedObject(android.view.Display.Mode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setHdrConversionMode(android.hardware.display.HdrConversionMode hdrConversionMode) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(hdrConversionMode, 0);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public android.hardware.display.HdrConversionMode getHdrConversionModeSetting() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.display.HdrConversionMode) obtain2.readTypedObject(android.hardware.display.HdrConversionMode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public android.hardware.display.HdrConversionMode getHdrConversionMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.display.HdrConversionMode) obtain2.readTypedObject(android.hardware.display.HdrConversionMode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int[] getSupportedHdrOutputTypes() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setShouldAlwaysRespectAppRequestedMode(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean shouldAlwaysRespectAppRequestedMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setRefreshRateSwitchingType(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int getRefreshRateSwitchingType() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public android.hardware.graphics.common.DisplayDecorationSupport getDisplayDecorationSupport(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.graphics.common.DisplayDecorationSupport) obtain2.readTypedObject(android.hardware.graphics.common.DisplayDecorationSupport.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setDisplayIdToMirror(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public android.hardware.OverlayProperties getOverlaySupport() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.OverlayProperties) obtain2.readTypedObject(android.hardware.OverlayProperties.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void enableConnectedDisplay(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void disableConnectedDisplay(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IDisplayManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void startWifiDisplayScan_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONFIGURE_WIFI_DISPLAY, getCallingPid(), getCallingUid());
        }

        protected void stopWifiDisplayScan_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONFIGURE_WIFI_DISPLAY, getCallingPid(), getCallingUid());
        }

        protected void pauseWifiDisplay_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONFIGURE_WIFI_DISPLAY, getCallingPid(), getCallingUid());
        }

        protected void resumeWifiDisplay_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONFIGURE_WIFI_DISPLAY, getCallingPid(), getCallingUid());
        }

        protected void setUserDisabledHdrTypes_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void setAreUserDisabledHdrTypesAllowed_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void requestColorMode_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONFIGURE_DISPLAY_COLOR_MODE, getCallingPid(), getCallingUid());
        }

        protected void getBrightnessEvents_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.BRIGHTNESS_SLIDER_USAGE, getCallingPid(), getCallingUid());
        }

        protected void getAmbientBrightnessStats_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_AMBIENT_LIGHT_STATS, getCallingPid(), getCallingUid());
        }

        protected void setBrightnessConfigurationForUser_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONFIGURE_DISPLAY_BRIGHTNESS, getCallingPid(), getCallingUid());
        }

        protected void setBrightnessConfigurationForDisplay_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONFIGURE_DISPLAY_BRIGHTNESS, getCallingPid(), getCallingUid());
        }

        protected void getBrightnessConfigurationForDisplay_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONFIGURE_DISPLAY_BRIGHTNESS, getCallingPid(), getCallingUid());
        }

        protected void getDefaultBrightnessConfiguration_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONFIGURE_DISPLAY_BRIGHTNESS, getCallingPid(), getCallingUid());
        }

        protected void setTemporaryBrightness_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DISPLAY_BRIGHTNESS, getCallingPid(), getCallingUid());
        }

        protected void setBrightness_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DISPLAY_BRIGHTNESS, getCallingPid(), getCallingUid());
        }

        protected void setTemporaryAutoBrightnessAdjustment_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DISPLAY_BRIGHTNESS, getCallingPid(), getCallingUid());
        }

        protected void getBrightnessInfo_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_DISPLAY_BRIGHTNESS, getCallingPid(), getCallingUid());
        }

        protected void setUserPreferredDisplayMode_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_USER_PREFERRED_DISPLAY_MODE, getCallingPid(), getCallingUid());
        }

        protected void setShouldAlwaysRespectAppRequestedMode_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.OVERRIDE_DISPLAY_MODE_REQUESTS, getCallingPid(), getCallingUid());
        }

        protected void shouldAlwaysRespectAppRequestedMode_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.OVERRIDE_DISPLAY_MODE_REQUESTS, getCallingPid(), getCallingUid());
        }

        protected void setRefreshRateSwitchingType_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_REFRESH_RATE_SWITCHING_TYPE, getCallingPid(), getCallingUid());
        }

        protected void enableConnectedDisplay_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_DISPLAYS, getCallingPid(), getCallingUid());
        }

        protected void disableConnectedDisplay_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_DISPLAYS, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 56;
        }
    }
}
