package android.app;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SystemServiceRegistry {
    private static final java.lang.String TAG = "SystemServiceRegistry";
    private static volatile boolean sInitializing;
    private static int sServiceCacheSize;
    public static boolean sEnableServiceNotFoundWtf = false;
    private static final java.util.Map<java.lang.Class<?>, java.lang.String> SYSTEM_SERVICE_NAMES = new android.util.ArrayMap();
    private static final java.util.Map<java.lang.String, android.app.SystemServiceRegistry.ServiceFetcher<?>> SYSTEM_SERVICE_FETCHERS = new android.util.ArrayMap();
    private static final java.util.Map<java.lang.String, java.lang.String> SYSTEM_SERVICE_CLASS_NAMES = new android.util.ArrayMap();

    @android.annotation.SystemApi
    public interface ContextAwareServiceProducerWithBinder<TServiceClass> {
        TServiceClass createService(android.content.Context context, android.os.IBinder iBinder);
    }

    @android.annotation.SystemApi
    public interface ContextAwareServiceProducerWithoutBinder<TServiceClass> {
        TServiceClass createService(android.content.Context context);
    }

    @android.annotation.SystemApi
    public interface StaticServiceProducerWithBinder<TServiceClass> {
        TServiceClass createService(android.os.IBinder iBinder);
    }

    @android.annotation.SystemApi
    public interface StaticServiceProducerWithoutBinder<TServiceClass> {
        TServiceClass createService();
    }

    static {
        registerService(android.content.Context.ACCESSIBILITY_SERVICE, android.view.accessibility.AccessibilityManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.view.accessibility.AccessibilityManager>() { // from class: android.app.SystemServiceRegistry.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.view.accessibility.AccessibilityManager createService(android.app.ContextImpl contextImpl) {
                return android.view.accessibility.AccessibilityManager.getInstance(contextImpl);
            }
        });
        registerService(android.content.Context.CAPTIONING_SERVICE, android.view.accessibility.CaptioningManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.view.accessibility.CaptioningManager>() { // from class: android.app.SystemServiceRegistry.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.view.accessibility.CaptioningManager createService(android.app.ContextImpl contextImpl) {
                return new android.view.accessibility.CaptioningManager(contextImpl);
            }
        });
        registerService("account", android.accounts.AccountManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.accounts.AccountManager>() { // from class: android.app.SystemServiceRegistry.3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.accounts.AccountManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.accounts.AccountManager(contextImpl, android.accounts.IAccountManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("account")));
            }
        });
        registerService("activity", android.app.ActivityManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.ActivityManager>() { // from class: android.app.SystemServiceRegistry.4
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.ActivityManager createService(android.app.ContextImpl contextImpl) {
                return new android.app.ActivityManager(contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler());
            }
        });
        registerService(android.content.Context.ACTIVITY_TASK_SERVICE, android.app.ActivityTaskManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.ActivityTaskManager>() { // from class: android.app.SystemServiceRegistry.5
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.ActivityTaskManager createService(android.app.ContextImpl contextImpl) {
                return android.app.ActivityTaskManager.getInstance();
            }
        });
        registerService(android.content.Context.URI_GRANTS_SERVICE, android.app.UriGrantsManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.UriGrantsManager>() { // from class: android.app.SystemServiceRegistry.6
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.UriGrantsManager createService(android.app.ContextImpl contextImpl) {
                return new android.app.UriGrantsManager(contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler());
            }
        });
        registerService("alarm", android.app.AlarmManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.AlarmManager>() { // from class: android.app.SystemServiceRegistry.7
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.AlarmManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.app.AlarmManager(android.app.IAlarmManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("alarm")), contextImpl);
            }
        });
        registerService("audio", android.media.AudioManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.media.AudioManager>() { // from class: android.app.SystemServiceRegistry.8
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.media.AudioManager createService(android.app.ContextImpl contextImpl) {
                return new android.media.AudioManager(contextImpl);
            }
        });
        registerService(android.content.Context.AUDIO_DEVICE_VOLUME_SERVICE, android.media.AudioDeviceVolumeManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.media.AudioDeviceVolumeManager>() { // from class: android.app.SystemServiceRegistry.9
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.media.AudioDeviceVolumeManager createService(android.app.ContextImpl contextImpl) {
                return new android.media.AudioDeviceVolumeManager(contextImpl);
            }
        });
        registerService(android.content.Context.MEDIA_ROUTER_SERVICE, android.media.MediaRouter.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.media.MediaRouter>() { // from class: android.app.SystemServiceRegistry.10
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.media.MediaRouter createService(android.app.ContextImpl contextImpl) {
                return new android.media.MediaRouter(contextImpl);
            }
        });
        registerService(android.content.Context.HDMI_CONTROL_SERVICE, android.hardware.hdmi.HdmiControlManager.class, new android.app.SystemServiceRegistry.StaticServiceFetcher<android.hardware.hdmi.HdmiControlManager>() { // from class: android.app.SystemServiceRegistry.11
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
            public android.hardware.hdmi.HdmiControlManager createService() throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.hardware.hdmi.HdmiControlManager(android.hardware.hdmi.IHdmiControlService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.HDMI_CONTROL_SERVICE)));
            }
        });
        registerService(android.content.Context.TEXT_CLASSIFICATION_SERVICE, android.view.textclassifier.TextClassificationManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.view.textclassifier.TextClassificationManager>() { // from class: android.app.SystemServiceRegistry.12
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.view.textclassifier.TextClassificationManager createService(android.app.ContextImpl contextImpl) {
                return new android.view.textclassifier.TextClassificationManager(contextImpl);
            }
        });
        registerService(android.content.Context.FONT_SERVICE, android.graphics.fonts.FontManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.graphics.fonts.FontManager>() { // from class: android.app.SystemServiceRegistry.13
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.graphics.fonts.FontManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return android.graphics.fonts.FontManager.create(com.android.internal.graphics.fonts.IFontManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.FONT_SERVICE)));
            }
        });
        registerService("clipboard", android.content.ClipboardManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.content.ClipboardManager>() { // from class: android.app.SystemServiceRegistry.14
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.content.ClipboardManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.content.ClipboardManager(contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler());
            }
        });
        SYSTEM_SERVICE_NAMES.put(android.text.ClipboardManager.class, "clipboard");
        registerService(android.content.Context.PAC_PROXY_SERVICE, android.net.PacProxyManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.net.PacProxyManager>() { // from class: android.app.SystemServiceRegistry.15
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.net.PacProxyManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.net.PacProxyManager(contextImpl.getOuterContext(), android.net.IPacProxyManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.PAC_PROXY_SERVICE)));
            }
        });
        registerService(android.content.Context.NETD_SERVICE, android.os.IBinder.class, new android.app.SystemServiceRegistry.StaticServiceFetcher<android.os.IBinder>() { // from class: android.app.SystemServiceRegistry.16
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
            public android.os.IBinder createService() throws android.os.ServiceManager.ServiceNotFoundException {
                return android.os.ServiceManager.getServiceOrThrow(android.content.Context.NETD_SERVICE);
            }
        });
        registerService(android.content.Context.TETHERING_SERVICE, android.net.TetheringManager.class, new android.app.SystemServiceRegistry.AnonymousClass17());
        registerService(android.content.Context.VPN_MANAGEMENT_SERVICE, android.net.VpnManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.net.VpnManager>() { // from class: android.app.SystemServiceRegistry.18
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.net.VpnManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.net.VpnManager(contextImpl, android.net.IVpnManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.VPN_MANAGEMENT_SERVICE)));
            }
        });
        registerService(android.content.Context.VCN_MANAGEMENT_SERVICE, android.net.vcn.VcnManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.net.vcn.VcnManager>() { // from class: android.app.SystemServiceRegistry.19
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.net.vcn.VcnManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.net.vcn.VcnManager(contextImpl, android.net.vcn.IVcnManagementService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.VCN_MANAGEMENT_SERVICE)));
            }
        });
        registerService(android.content.Context.COUNTRY_DETECTOR, android.location.CountryDetector.class, new android.app.SystemServiceRegistry.StaticServiceFetcher<android.location.CountryDetector>() { // from class: android.app.SystemServiceRegistry.20
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
            public android.location.CountryDetector createService() throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.location.CountryDetector(android.location.ICountryDetector.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.COUNTRY_DETECTOR)));
            }
        });
        registerService(android.content.Context.DEVICE_POLICY_SERVICE, android.app.admin.DevicePolicyManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.admin.DevicePolicyManager>() { // from class: android.app.SystemServiceRegistry.21
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.admin.DevicePolicyManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.app.admin.DevicePolicyManager(contextImpl, android.app.admin.IDevicePolicyManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.DEVICE_POLICY_SERVICE)));
            }
        });
        registerService("download", android.app.DownloadManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.DownloadManager>() { // from class: android.app.SystemServiceRegistry.22
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.DownloadManager createService(android.app.ContextImpl contextImpl) {
                return new android.app.DownloadManager(contextImpl);
            }
        });
        registerService(android.content.Context.BATTERY_SERVICE, android.os.BatteryManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.os.BatteryManager>() { // from class: android.app.SystemServiceRegistry.23
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.os.BatteryManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.os.BatteryManager(contextImpl, com.android.internal.app.IBatteryStats.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("batterystats")), android.os.IBatteryPropertiesRegistrar.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("batteryproperties")));
            }
        });
        registerService(android.content.Context.DROPBOX_SERVICE, android.os.DropBoxManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.os.DropBoxManager>() { // from class: android.app.SystemServiceRegistry.24
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.os.DropBoxManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.os.DropBoxManager(contextImpl, com.android.internal.os.IDropBoxManagerService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.DROPBOX_SERVICE)));
            }
        });
        registerService(android.content.Context.BINARY_TRANSPARENCY_SERVICE, android.transparency.BinaryTransparencyManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.transparency.BinaryTransparencyManager>() { // from class: android.app.SystemServiceRegistry.25
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.transparency.BinaryTransparencyManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.transparency.BinaryTransparencyManager(contextImpl, com.android.internal.os.IBinaryTransparencyService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.BINARY_TRANSPARENCY_SERVICE)));
            }
        });
        registerService("input", android.hardware.input.InputManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.hardware.input.InputManager>() { // from class: android.app.SystemServiceRegistry.26
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.hardware.input.InputManager createService(android.app.ContextImpl contextImpl) {
                return new android.hardware.input.InputManager(contextImpl.getOuterContext());
            }
        });
        registerService(android.content.Context.DISPLAY_SERVICE, android.hardware.display.DisplayManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.hardware.display.DisplayManager>() { // from class: android.app.SystemServiceRegistry.27
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.hardware.display.DisplayManager createService(android.app.ContextImpl contextImpl) {
                return new android.hardware.display.DisplayManager(contextImpl.getOuterContext());
            }
        });
        registerService(android.content.Context.COLOR_DISPLAY_SERVICE, android.hardware.display.ColorDisplayManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.hardware.display.ColorDisplayManager>() { // from class: android.app.SystemServiceRegistry.28
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.hardware.display.ColorDisplayManager createService(android.app.ContextImpl contextImpl) {
                return new android.hardware.display.ColorDisplayManager();
            }
        });
        registerService(android.content.Context.INPUT_METHOD_SERVICE, android.view.inputmethod.InputMethodManager.class, new android.app.SystemServiceRegistry.ServiceFetcher<android.view.inputmethod.InputMethodManager>() { // from class: android.app.SystemServiceRegistry.29
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.ServiceFetcher
            public android.view.inputmethod.InputMethodManager getService(android.app.ContextImpl contextImpl) {
                return android.view.inputmethod.InputMethodManager.forContext(contextImpl.getOuterContext());
            }
        });
        registerService(android.content.Context.TEXT_SERVICES_MANAGER_SERVICE, android.view.textservice.TextServicesManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.view.textservice.TextServicesManager>() { // from class: android.app.SystemServiceRegistry.30
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.view.textservice.TextServicesManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return android.view.textservice.TextServicesManager.createInstance(contextImpl);
            }
        });
        registerService(android.content.Context.KEYGUARD_SERVICE, android.app.KeyguardManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.KeyguardManager>() { // from class: android.app.SystemServiceRegistry.31
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.KeyguardManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.app.KeyguardManager(contextImpl);
            }
        });
        registerService(android.content.Context.LAYOUT_INFLATER_SERVICE, android.view.LayoutInflater.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.view.LayoutInflater>() { // from class: android.app.SystemServiceRegistry.32
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.view.LayoutInflater createService(android.app.ContextImpl contextImpl) {
                return new com.android.internal.policy.PhoneLayoutInflater(contextImpl.getOuterContext());
            }
        });
        registerService("location", android.location.LocationManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.location.LocationManager>() { // from class: android.app.SystemServiceRegistry.33
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.location.LocationManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.location.LocationManager(contextImpl, android.location.ILocationManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("location")));
            }
        });
        registerService(android.content.Context.NETWORK_POLICY_SERVICE, android.net.NetworkPolicyManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.net.NetworkPolicyManager>() { // from class: android.app.SystemServiceRegistry.34
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.net.NetworkPolicyManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.net.NetworkPolicyManager(contextImpl, android.net.INetworkPolicyManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.NETWORK_POLICY_SERVICE)));
            }
        });
        registerService("notification", android.app.NotificationManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.NotificationManager>() { // from class: android.app.SystemServiceRegistry.35
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.NotificationManager createService(android.app.ContextImpl contextImpl) {
                android.content.Context outerContext = contextImpl.getOuterContext();
                return new android.app.NotificationManager(new android.view.ContextThemeWrapper(outerContext, android.content.res.Resources.selectSystemTheme(0, outerContext.getApplicationInfo().targetSdkVersion, 16973835, 16973935, 16974126, 16974130)), contextImpl.mMainThread.getHandler());
            }
        });
        registerService(android.content.Context.PEOPLE_SERVICE, android.app.people.PeopleManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.people.PeopleManager>() { // from class: android.app.SystemServiceRegistry.36
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.people.PeopleManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.app.people.PeopleManager(contextImpl);
            }
        });
        registerService(android.content.Context.POWER_SERVICE, android.os.PowerManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.os.PowerManager>() { // from class: android.app.SystemServiceRegistry.37
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.os.PowerManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.os.PowerManager(contextImpl.getOuterContext(), android.os.IPowerManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.POWER_SERVICE)), android.os.IThermalService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.THERMAL_SERVICE)), contextImpl.mMainThread.getHandler());
            }
        });
        registerService(android.content.Context.PERFORMANCE_HINT_SERVICE, android.os.PerformanceHintManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.os.PerformanceHintManager>() { // from class: android.app.SystemServiceRegistry.38
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.os.PerformanceHintManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return android.os.PerformanceHintManager.create();
            }
        });
        registerService("recovery", android.os.RecoverySystem.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.os.RecoverySystem>() { // from class: android.app.SystemServiceRegistry.39
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.os.RecoverySystem createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.os.RecoverySystem(android.os.IRecoverySystem.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("recovery")));
            }
        });
        registerService("search", android.app.SearchManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.SearchManager>() { // from class: android.app.SystemServiceRegistry.40
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.SearchManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.app.SearchManager(contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler());
            }
        });
        registerService(android.content.Context.SECURITY_STATE_SERVICE, android.os.SecurityStateManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.os.SecurityStateManager>() { // from class: android.app.SystemServiceRegistry.41
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.os.SecurityStateManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.os.SecurityStateManager(android.os.ISecurityStateManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.SECURITY_STATE_SERVICE)));
            }
        });
        registerService(android.content.Context.SENSOR_SERVICE, android.hardware.SensorManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.hardware.SensorManager>() { // from class: android.app.SystemServiceRegistry.42
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.hardware.SensorManager createService(android.app.ContextImpl contextImpl) {
                return new android.hardware.SystemSensorManager(contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler().getLooper());
            }
        });
        registerService(android.content.Context.SENSOR_PRIVACY_SERVICE, android.hardware.SensorPrivacyManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.hardware.SensorPrivacyManager>() { // from class: android.app.SystemServiceRegistry.43
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.hardware.SensorPrivacyManager createService(android.app.ContextImpl contextImpl) {
                return android.hardware.SensorPrivacyManager.getInstance(contextImpl);
            }
        });
        registerService(android.content.Context.STATUS_BAR_SERVICE, android.app.StatusBarManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.StatusBarManager>() { // from class: android.app.SystemServiceRegistry.44
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.StatusBarManager createService(android.app.ContextImpl contextImpl) {
                return new android.app.StatusBarManager(contextImpl.getOuterContext());
            }
        });
        registerService(android.content.Context.STORAGE_SERVICE, android.os.storage.StorageManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.os.storage.StorageManager>() { // from class: android.app.SystemServiceRegistry.45
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.os.storage.StorageManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.os.storage.StorageManager(contextImpl, contextImpl.mMainThread.getHandler().getLooper());
            }
        });
        registerService(android.content.Context.STORAGE_STATS_SERVICE, android.app.usage.StorageStatsManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.usage.StorageStatsManager>() { // from class: android.app.SystemServiceRegistry.46
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.usage.StorageStatsManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.app.usage.StorageStatsManager(contextImpl, android.app.usage.IStorageStatsManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.STORAGE_STATS_SERVICE)));
            }
        });
        registerService(android.content.Context.SYSTEM_UPDATE_SERVICE, android.os.SystemUpdateManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.os.SystemUpdateManager>() { // from class: android.app.SystemServiceRegistry.47
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.os.SystemUpdateManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.os.SystemUpdateManager(android.os.ISystemUpdateManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.SYSTEM_UPDATE_SERVICE)));
            }
        });
        registerService(android.content.Context.SYSTEM_CONFIG_SERVICE, android.os.SystemConfigManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.os.SystemConfigManager>() { // from class: android.app.SystemServiceRegistry.48
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.os.SystemConfigManager createService(android.app.ContextImpl contextImpl) {
                return new android.os.SystemConfigManager();
            }
        });
        registerService(android.content.Context.TELEPHONY_REGISTRY_SERVICE, android.telephony.TelephonyRegistryManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.telephony.TelephonyRegistryManager>() { // from class: android.app.SystemServiceRegistry.49
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.telephony.TelephonyRegistryManager createService(android.app.ContextImpl contextImpl) {
                return new android.telephony.TelephonyRegistryManager(contextImpl);
            }
        });
        registerService(android.content.Context.TELECOM_SERVICE, android.telecom.TelecomManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.telecom.TelecomManager>() { // from class: android.app.SystemServiceRegistry.50
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.telecom.TelecomManager createService(android.app.ContextImpl contextImpl) {
                return new android.telecom.TelecomManager(contextImpl.getOuterContext());
            }
        });
        registerService("mms", android.telephony.MmsManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.telephony.MmsManager>() { // from class: android.app.SystemServiceRegistry.51
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.telephony.MmsManager createService(android.app.ContextImpl contextImpl) {
                return new android.telephony.MmsManager(contextImpl.getOuterContext());
            }
        });
        registerService(android.content.Context.UI_MODE_SERVICE, android.app.UiModeManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.UiModeManager>() { // from class: android.app.SystemServiceRegistry.52
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.UiModeManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.app.UiModeManager(contextImpl.getOuterContext());
            }
        });
        registerService("usb", android.hardware.usb.UsbManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.hardware.usb.UsbManager>() { // from class: android.app.SystemServiceRegistry.53
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.hardware.usb.UsbManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.hardware.usb.UsbManager(contextImpl, android.hardware.usb.IUsbManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("usb")));
            }
        });
        registerService("adb", android.debug.AdbManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.debug.AdbManager>() { // from class: android.app.SystemServiceRegistry.54
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.debug.AdbManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.debug.AdbManager(contextImpl, android.debug.IAdbManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("adb")));
            }
        });
        registerService(android.content.Context.SERIAL_SERVICE, android.hardware.SerialManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.hardware.SerialManager>() { // from class: android.app.SystemServiceRegistry.55
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.hardware.SerialManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.hardware.SerialManager(contextImpl, android.hardware.ISerialManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.SERIAL_SERVICE)));
            }
        });
        registerService(android.content.Context.VIBRATOR_MANAGER_SERVICE, android.os.VibratorManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.os.VibratorManager>() { // from class: android.app.SystemServiceRegistry.56
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.os.VibratorManager createService(android.app.ContextImpl contextImpl) {
                return new android.os.SystemVibratorManager(contextImpl);
            }
        });
        registerService(android.content.Context.VIBRATOR_SERVICE, android.os.Vibrator.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.os.Vibrator>() { // from class: android.app.SystemServiceRegistry.57
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.os.Vibrator createService(android.app.ContextImpl contextImpl) {
                return new android.os.SystemVibrator(contextImpl);
            }
        });
        registerService(android.content.Context.WALLPAPER_SERVICE, android.app.WallpaperManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.WallpaperManager>() { // from class: android.app.SystemServiceRegistry.58
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.WallpaperManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                android.os.IBinder service = android.os.ServiceManager.getService(android.content.Context.WALLPAPER_SERVICE);
                if (service == null) {
                    android.content.pm.ApplicationInfo applicationInfo = contextImpl.getApplicationInfo();
                    if (applicationInfo.targetSdkVersion >= 28 && applicationInfo.isInstantApp()) {
                        throw new android.os.ServiceManager.ServiceNotFoundException(android.content.Context.WALLPAPER_SERVICE);
                    }
                    if (!android.content.res.Resources.getSystem().getBoolean(com.android.internal.R.bool.config_enableWallpaperService)) {
                        return android.app.DisabledWallpaperManager.getInstance();
                    }
                    android.util.Log.e(android.app.SystemServiceRegistry.TAG, "No wallpaper service");
                }
                return new android.app.WallpaperManager(android.app.IWallpaperManager.Stub.asInterface(service), contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler());
            }
        });
        registerService(android.content.Context.WIFI_NL80211_SERVICE, android.net.wifi.nl80211.WifiNl80211Manager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.net.wifi.nl80211.WifiNl80211Manager>() { // from class: android.app.SystemServiceRegistry.59
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.net.wifi.nl80211.WifiNl80211Manager createService(android.app.ContextImpl contextImpl) {
                return new android.net.wifi.nl80211.WifiNl80211Manager(contextImpl.getOuterContext());
            }
        });
        registerService(android.content.Context.WINDOW_SERVICE, android.view.WindowManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.view.WindowManager>() { // from class: android.app.SystemServiceRegistry.60
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.view.WindowManager createService(android.app.ContextImpl contextImpl) {
                return new android.view.WindowManagerImpl(contextImpl);
            }
        });
        registerService("user", android.os.UserManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.os.UserManager>() { // from class: android.app.SystemServiceRegistry.61
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.os.UserManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.os.UserManager(contextImpl, android.os.IUserManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("user")));
            }
        });
        registerService(android.content.Context.APP_OPS_SERVICE, android.app.AppOpsManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.AppOpsManager>() { // from class: android.app.SystemServiceRegistry.62
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.AppOpsManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.app.AppOpsManager(contextImpl, com.android.internal.app.IAppOpsService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.APP_OPS_SERVICE)));
            }
        });
        registerService(android.content.Context.CAMERA_SERVICE, android.hardware.camera2.CameraManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.hardware.camera2.CameraManager>() { // from class: android.app.SystemServiceRegistry.63
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.hardware.camera2.CameraManager createService(android.app.ContextImpl contextImpl) {
                return new android.hardware.camera2.CameraManager(contextImpl);
            }
        });
        registerService(android.content.Context.LAUNCHER_APPS_SERVICE, android.content.pm.LauncherApps.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.content.pm.LauncherApps>() { // from class: android.app.SystemServiceRegistry.64
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.content.pm.LauncherApps createService(android.app.ContextImpl contextImpl) {
                return new android.content.pm.LauncherApps(contextImpl);
            }
        });
        registerService(android.content.Context.RESTRICTIONS_SERVICE, android.content.RestrictionsManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.content.RestrictionsManager>() { // from class: android.app.SystemServiceRegistry.65
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.content.RestrictionsManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.content.RestrictionsManager(contextImpl, android.content.IRestrictionsManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.RESTRICTIONS_SERVICE)));
            }
        });
        registerService(android.content.Context.PRINT_SERVICE, android.print.PrintManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.print.PrintManager>() { // from class: android.app.SystemServiceRegistry.66
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.print.PrintManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                android.print.IPrintManager iPrintManager;
                if (!contextImpl.getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_PRINTING)) {
                    iPrintManager = null;
                } else {
                    iPrintManager = android.print.IPrintManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.PRINT_SERVICE));
                }
                return new android.print.PrintManager(contextImpl.getOuterContext(), iPrintManager, contextImpl.getUserId(), android.os.UserHandle.getAppId(contextImpl.getApplicationInfo().uid));
            }
        });
        registerService(android.content.Context.COMPANION_DEVICE_SERVICE, android.companion.CompanionDeviceManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.companion.CompanionDeviceManager>() { // from class: android.app.SystemServiceRegistry.67
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.companion.CompanionDeviceManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                android.companion.ICompanionDeviceManager iCompanionDeviceManager;
                if (!contextImpl.getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_COMPANION_DEVICE_SETUP)) {
                    iCompanionDeviceManager = null;
                } else {
                    iCompanionDeviceManager = android.companion.ICompanionDeviceManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.COMPANION_DEVICE_SERVICE));
                }
                return new android.companion.CompanionDeviceManager(iCompanionDeviceManager, contextImpl.getOuterContext());
            }
        });
        registerService(android.content.Context.VIRTUAL_DEVICE_SERVICE, android.companion.virtual.VirtualDeviceManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.companion.virtual.VirtualDeviceManager>() { // from class: android.app.SystemServiceRegistry.68
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.companion.virtual.VirtualDeviceManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                if (!contextImpl.getResources().getBoolean(com.android.internal.R.bool.config_enableVirtualDeviceManager)) {
                    return null;
                }
                return new android.companion.virtual.VirtualDeviceManager(android.companion.virtual.IVirtualDeviceManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.VIRTUAL_DEVICE_SERVICE)), contextImpl.getOuterContext());
            }
        });
        registerService(android.content.Context.CONSUMER_IR_SERVICE, android.hardware.ConsumerIrManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.hardware.ConsumerIrManager>() { // from class: android.app.SystemServiceRegistry.69
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.hardware.ConsumerIrManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.hardware.ConsumerIrManager(contextImpl);
            }
        });
        registerService(android.content.Context.TRUST_SERVICE, android.app.trust.TrustManager.class, new android.app.SystemServiceRegistry.StaticServiceFetcher<android.app.trust.TrustManager>() { // from class: android.app.SystemServiceRegistry.70
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
            public android.app.trust.TrustManager createService() throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.app.trust.TrustManager(android.os.ServiceManager.getServiceOrThrow(android.content.Context.TRUST_SERVICE));
            }
        });
        registerService(android.content.Context.FINGERPRINT_SERVICE, android.hardware.fingerprint.FingerprintManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.hardware.fingerprint.FingerprintManager>() { // from class: android.app.SystemServiceRegistry.71
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.hardware.fingerprint.FingerprintManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                android.os.IBinder service;
                if (contextImpl.getApplicationInfo().targetSdkVersion >= 26) {
                    service = android.os.ServiceManager.getServiceOrThrow(android.content.Context.FINGERPRINT_SERVICE);
                } else {
                    service = android.os.ServiceManager.getService(android.content.Context.FINGERPRINT_SERVICE);
                }
                return new android.hardware.fingerprint.FingerprintManager(contextImpl.getOuterContext(), android.hardware.fingerprint.IFingerprintService.Stub.asInterface(service));
            }
        });
        registerService(android.content.Context.FACE_SERVICE, android.hardware.face.FaceManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.hardware.face.FaceManager>() { // from class: android.app.SystemServiceRegistry.72
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.hardware.face.FaceManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                android.os.IBinder service;
                if (contextImpl.getApplicationInfo().targetSdkVersion >= 26) {
                    service = android.os.ServiceManager.getServiceOrThrow(android.content.Context.FACE_SERVICE);
                } else {
                    service = android.os.ServiceManager.getService(android.content.Context.FACE_SERVICE);
                }
                return new android.hardware.face.FaceManager(contextImpl.getOuterContext(), android.hardware.face.IFaceService.Stub.asInterface(service));
            }
        });
        registerService(android.content.Context.IRIS_SERVICE, android.hardware.iris.IrisManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.hardware.iris.IrisManager>() { // from class: android.app.SystemServiceRegistry.73
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.hardware.iris.IrisManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.hardware.iris.IrisManager(contextImpl.getOuterContext(), android.hardware.iris.IIrisService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.IRIS_SERVICE)));
            }
        });
        registerService(android.content.Context.BIOMETRIC_SERVICE, android.hardware.biometrics.BiometricManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.hardware.biometrics.BiometricManager>() { // from class: android.app.SystemServiceRegistry.74
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.hardware.biometrics.BiometricManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.hardware.biometrics.BiometricManager(contextImpl.getOuterContext(), android.hardware.biometrics.IAuthService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.AUTH_SERVICE)));
            }
        });
        registerService(android.content.Context.TV_INTERACTIVE_APP_SERVICE, android.media.tv.interactive.TvInteractiveAppManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.media.tv.interactive.TvInteractiveAppManager>() { // from class: android.app.SystemServiceRegistry.75
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.media.tv.interactive.TvInteractiveAppManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.media.tv.interactive.TvInteractiveAppManager(android.media.tv.interactive.ITvInteractiveAppManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.TV_INTERACTIVE_APP_SERVICE)), contextImpl.getUserId());
            }
        });
        registerService(android.content.Context.TV_AD_SERVICE, android.media.tv.ad.TvAdManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.media.tv.ad.TvAdManager>() { // from class: android.app.SystemServiceRegistry.76
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.media.tv.ad.TvAdManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.media.tv.ad.TvAdManager(android.media.tv.ad.ITvAdManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.TV_AD_SERVICE)), contextImpl.getUserId());
            }
        });
        registerService(android.content.Context.TV_INPUT_SERVICE, android.media.tv.TvInputManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.media.tv.TvInputManager>() { // from class: android.app.SystemServiceRegistry.77
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.media.tv.TvInputManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.media.tv.TvInputManager(android.media.tv.ITvInputManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.TV_INPUT_SERVICE)), contextImpl.getUserId());
            }
        });
        registerService(android.content.Context.TV_TUNER_RESOURCE_MGR_SERVICE, android.media.tv.tunerresourcemanager.TunerResourceManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.media.tv.tunerresourcemanager.TunerResourceManager>() { // from class: android.app.SystemServiceRegistry.78
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.media.tv.tunerresourcemanager.TunerResourceManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.media.tv.tunerresourcemanager.TunerResourceManager(android.media.tv.tunerresourcemanager.ITunerResourceManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.TV_TUNER_RESOURCE_MGR_SERVICE)), contextImpl.getUserId());
            }
        });
        registerService(android.content.Context.NETWORK_SCORE_SERVICE, android.net.NetworkScoreManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.net.NetworkScoreManager>() { // from class: android.app.SystemServiceRegistry.79
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.net.NetworkScoreManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.net.NetworkScoreManager(contextImpl);
            }
        });
        registerService(android.content.Context.USAGE_STATS_SERVICE, android.app.usage.UsageStatsManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.usage.UsageStatsManager>() { // from class: android.app.SystemServiceRegistry.80
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.usage.UsageStatsManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.app.usage.UsageStatsManager(contextImpl.getOuterContext(), android.app.usage.IUsageStatsManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.USAGE_STATS_SERVICE)));
            }
        });
        registerService(android.content.Context.PERSISTENT_DATA_BLOCK_SERVICE, android.service.persistentdata.PersistentDataBlockManager.class, new android.app.SystemServiceRegistry.StaticServiceFetcher<android.service.persistentdata.PersistentDataBlockManager>() { // from class: android.app.SystemServiceRegistry.81
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
            public android.service.persistentdata.PersistentDataBlockManager createService() throws android.os.ServiceManager.ServiceNotFoundException {
                android.service.persistentdata.IPersistentDataBlockService asInterface = android.service.persistentdata.IPersistentDataBlockService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.PERSISTENT_DATA_BLOCK_SERVICE));
                if (asInterface != null) {
                    return new android.service.persistentdata.PersistentDataBlockManager(asInterface);
                }
                return null;
            }
        });
        registerService(android.content.Context.OEM_LOCK_SERVICE, android.service.oemlock.OemLockManager.class, new android.app.SystemServiceRegistry.StaticServiceFetcher<android.service.oemlock.OemLockManager>() { // from class: android.app.SystemServiceRegistry.82
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
            public android.service.oemlock.OemLockManager createService() throws android.os.ServiceManager.ServiceNotFoundException {
                android.service.oemlock.IOemLockService asInterface = android.service.oemlock.IOemLockService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.OEM_LOCK_SERVICE));
                if (asInterface != null) {
                    return new android.service.oemlock.OemLockManager(asInterface);
                }
                return null;
            }
        });
        registerService(android.content.Context.MEDIA_PROJECTION_SERVICE, android.media.projection.MediaProjectionManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.media.projection.MediaProjectionManager>() { // from class: android.app.SystemServiceRegistry.83
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.media.projection.MediaProjectionManager createService(android.app.ContextImpl contextImpl) {
                return new android.media.projection.MediaProjectionManager(contextImpl);
            }
        });
        registerService(android.content.Context.APPWIDGET_SERVICE, android.appwidget.AppWidgetManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.appwidget.AppWidgetManager>() { // from class: android.app.SystemServiceRegistry.84
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.appwidget.AppWidgetManager createService(android.app.ContextImpl contextImpl) {
                android.os.IBinder service = android.os.ServiceManager.getService(android.content.Context.APPWIDGET_SERVICE);
                if (service == null) {
                    return null;
                }
                return new android.appwidget.AppWidgetManager(contextImpl, com.android.internal.appwidget.IAppWidgetService.Stub.asInterface(service));
            }
        });
        registerService("midi", android.media.midi.MidiManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.media.midi.MidiManager>() { // from class: android.app.SystemServiceRegistry.85
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.media.midi.MidiManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.media.midi.MidiManager(android.media.midi.IMidiManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("midi")));
            }
        });
        registerService(android.content.Context.RADIO_SERVICE, android.hardware.radio.RadioManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.hardware.radio.RadioManager>() { // from class: android.app.SystemServiceRegistry.86
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.hardware.radio.RadioManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.hardware.radio.RadioManager(contextImpl);
            }
        });
        registerService(android.content.Context.HARDWARE_PROPERTIES_SERVICE, android.os.HardwarePropertiesManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.os.HardwarePropertiesManager>() { // from class: android.app.SystemServiceRegistry.87
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.os.HardwarePropertiesManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.os.HardwarePropertiesManager(contextImpl, android.os.IHardwarePropertiesManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.HARDWARE_PROPERTIES_SERVICE)));
            }
        });
        registerService(android.content.Context.SOUND_TRIGGER_SERVICE, android.media.soundtrigger.SoundTriggerManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.media.soundtrigger.SoundTriggerManager>() { // from class: android.app.SystemServiceRegistry.88
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.media.soundtrigger.SoundTriggerManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.media.soundtrigger.SoundTriggerManager(contextImpl, com.android.internal.app.ISoundTriggerService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.SOUND_TRIGGER_SERVICE)));
            }
        });
        registerService("shortcut", android.content.pm.ShortcutManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.content.pm.ShortcutManager>() { // from class: android.app.SystemServiceRegistry.89
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.content.pm.ShortcutManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.content.pm.ShortcutManager(contextImpl, android.content.pm.IShortcutService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("shortcut")));
            }
        });
        registerService("overlay", android.content.om.OverlayManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.content.om.OverlayManager>() { // from class: android.app.SystemServiceRegistry.90
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.content.om.OverlayManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                android.os.IBinder serviceOrThrow;
                if (android.compat.Compatibility.isChangeEnabled(android.content.om.OverlayManager.SELF_TARGETING_OVERLAY)) {
                    serviceOrThrow = android.os.ServiceManager.getService("overlay");
                } else {
                    serviceOrThrow = android.os.ServiceManager.getServiceOrThrow("overlay");
                }
                return new android.content.om.OverlayManager(contextImpl, android.content.om.IOverlayManager.Stub.asInterface(serviceOrThrow));
            }
        });
        registerService(android.content.Context.NETWORK_WATCHLIST_SERVICE, android.net.NetworkWatchlistManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.net.NetworkWatchlistManager>() { // from class: android.app.SystemServiceRegistry.91
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.net.NetworkWatchlistManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.net.NetworkWatchlistManager(contextImpl, com.android.internal.net.INetworkWatchlistManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.NETWORK_WATCHLIST_SERVICE)));
            }
        });
        registerService(android.content.Context.SYSTEM_HEALTH_SERVICE, android.os.health.SystemHealthManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.os.health.SystemHealthManager>() { // from class: android.app.SystemServiceRegistry.92
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.os.health.SystemHealthManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.os.health.SystemHealthManager(com.android.internal.app.IBatteryStats.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("batterystats")), android.os.IPowerStatsService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.POWER_STATS_SERVICE)));
            }
        });
        registerService(android.content.Context.CONTEXTHUB_SERVICE, android.hardware.location.ContextHubManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.hardware.location.ContextHubManager>() { // from class: android.app.SystemServiceRegistry.93
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.hardware.location.ContextHubManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                android.os.IBinder service = android.os.ServiceManager.getService(android.content.Context.CONTEXTHUB_SERVICE);
                if (service == null) {
                    return null;
                }
                return new android.hardware.location.ContextHubManager(android.hardware.location.IContextHubService.Stub.asInterface(service), contextImpl.mMainThread.getHandler().getLooper());
            }
        });
        registerService(android.content.Context.INCIDENT_SERVICE, android.os.IncidentManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.os.IncidentManager>() { // from class: android.app.SystemServiceRegistry.94
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.os.IncidentManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.os.IncidentManager(contextImpl);
            }
        });
        registerService(android.content.Context.BUGREPORT_SERVICE, android.os.BugreportManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.os.BugreportManager>() { // from class: android.app.SystemServiceRegistry.95
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.os.BugreportManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.os.BugreportManager(contextImpl.getOuterContext(), android.os.IDumpstate.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.BUGREPORT_SERVICE)));
            }
        });
        registerService(android.content.Context.AUTOFILL_MANAGER_SERVICE, android.view.autofill.AutofillManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.view.autofill.AutofillManager>() { // from class: android.app.SystemServiceRegistry.96
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.view.autofill.AutofillManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.view.autofill.AutofillManager(contextImpl.getOuterContext(), android.view.autofill.IAutoFillManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.AUTOFILL_MANAGER_SERVICE)));
            }
        });
        registerService("credential", android.credentials.CredentialManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.credentials.CredentialManager>() { // from class: android.app.SystemServiceRegistry.97
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.credentials.CredentialManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                android.credentials.ICredentialManager asInterface = android.credentials.ICredentialManager.Stub.asInterface(android.os.ServiceManager.getService("credential"));
                if (asInterface != null) {
                    return new android.credentials.CredentialManager(contextImpl.getOuterContext(), asInterface);
                }
                return null;
            }
        });
        registerService(android.content.Context.MUSIC_RECOGNITION_SERVICE, android.media.musicrecognition.MusicRecognitionManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.media.musicrecognition.MusicRecognitionManager>() { // from class: android.app.SystemServiceRegistry.98
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.media.musicrecognition.MusicRecognitionManager createService(android.app.ContextImpl contextImpl) {
                return new android.media.musicrecognition.MusicRecognitionManager(android.media.musicrecognition.IMusicRecognitionManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.MUSIC_RECOGNITION_SERVICE)));
            }
        });
        registerService(android.content.Context.CONTENT_CAPTURE_MANAGER_SERVICE, android.view.contentcapture.ContentCaptureManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.view.contentcapture.ContentCaptureManager>() { // from class: android.app.SystemServiceRegistry.99
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.view.contentcapture.ContentCaptureManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                android.view.contentcapture.IContentCaptureManager asInterface;
                android.content.Context outerContext = contextImpl.getOuterContext();
                android.content.ContentCaptureOptions contentCaptureOptions = outerContext.getContentCaptureOptions();
                if (contentCaptureOptions != null) {
                    if ((contentCaptureOptions.lite || contentCaptureOptions.isWhitelisted(outerContext)) && (asInterface = android.view.contentcapture.IContentCaptureManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.CONTENT_CAPTURE_MANAGER_SERVICE))) != null) {
                        return new android.view.contentcapture.ContentCaptureManager(outerContext, asInterface, contentCaptureOptions);
                    }
                    return null;
                }
                return null;
            }
        });
        registerService(android.content.Context.TRANSLATION_MANAGER_SERVICE, android.view.translation.TranslationManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.view.translation.TranslationManager>() { // from class: android.app.SystemServiceRegistry.100
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.view.translation.TranslationManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                android.view.translation.ITranslationManager asInterface = android.view.translation.ITranslationManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.TRANSLATION_MANAGER_SERVICE));
                if (asInterface != null) {
                    return new android.view.translation.TranslationManager(contextImpl.getOuterContext(), asInterface);
                }
                return null;
            }
        });
        registerService(android.content.Context.UI_TRANSLATION_SERVICE, android.view.translation.UiTranslationManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.view.translation.UiTranslationManager>() { // from class: android.app.SystemServiceRegistry.101
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.view.translation.UiTranslationManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                android.view.translation.ITranslationManager asInterface = android.view.translation.ITranslationManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.TRANSLATION_MANAGER_SERVICE));
                if (asInterface != null) {
                    return new android.view.translation.UiTranslationManager(contextImpl.getOuterContext(), asInterface);
                }
                return null;
            }
        });
        registerService(android.content.Context.SEARCH_UI_SERVICE, android.app.search.SearchUiManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.search.SearchUiManager>() { // from class: android.app.SystemServiceRegistry.102
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.search.SearchUiManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                if (android.os.ServiceManager.getService(android.content.Context.SEARCH_UI_SERVICE) == null) {
                    return null;
                }
                return new android.app.search.SearchUiManager(contextImpl);
            }
        });
        registerService(android.content.Context.SMARTSPACE_SERVICE, android.app.smartspace.SmartspaceManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.smartspace.SmartspaceManager>() { // from class: android.app.SystemServiceRegistry.103
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.smartspace.SmartspaceManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                if (android.os.ServiceManager.getService(android.content.Context.SMARTSPACE_SERVICE) == null) {
                    return null;
                }
                return new android.app.smartspace.SmartspaceManager(contextImpl);
            }
        });
        registerService(android.content.Context.APP_PREDICTION_SERVICE, android.app.prediction.AppPredictionManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.prediction.AppPredictionManager>() { // from class: android.app.SystemServiceRegistry.104
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.prediction.AppPredictionManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                if (android.os.ServiceManager.getService(android.content.Context.APP_PREDICTION_SERVICE) == null) {
                    return null;
                }
                return new android.app.prediction.AppPredictionManager(contextImpl);
            }
        });
        registerService(android.content.Context.CONTENT_SUGGESTIONS_SERVICE, android.app.contentsuggestions.ContentSuggestionsManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.contentsuggestions.ContentSuggestionsManager>() { // from class: android.app.SystemServiceRegistry.105
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.contentsuggestions.ContentSuggestionsManager createService(android.app.ContextImpl contextImpl) {
                return new android.app.contentsuggestions.ContentSuggestionsManager(contextImpl.getUserId(), android.app.contentsuggestions.IContentSuggestionsManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.CONTENT_SUGGESTIONS_SERVICE)));
            }
        });
        registerService(android.content.Context.WALLPAPER_EFFECTS_GENERATION_SERVICE, android.app.wallpapereffectsgeneration.WallpaperEffectsGenerationManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.wallpapereffectsgeneration.WallpaperEffectsGenerationManager>() { // from class: android.app.SystemServiceRegistry.106
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.wallpapereffectsgeneration.WallpaperEffectsGenerationManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                android.os.IBinder service = android.os.ServiceManager.getService(android.content.Context.WALLPAPER_EFFECTS_GENERATION_SERVICE);
                if (service == null) {
                    return null;
                }
                return new android.app.wallpapereffectsgeneration.WallpaperEffectsGenerationManager(android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager.Stub.asInterface(service));
            }
        });
        registerService(android.content.Context.VR_SERVICE, android.app.VrManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.VrManager>() { // from class: android.app.SystemServiceRegistry.107
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.VrManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.app.VrManager(android.service.vr.IVrManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.VR_SERVICE)));
            }
        });
        registerService(android.content.Context.CROSS_PROFILE_APPS_SERVICE, android.content.pm.CrossProfileApps.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.content.pm.CrossProfileApps>() { // from class: android.app.SystemServiceRegistry.108
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.content.pm.CrossProfileApps createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.content.pm.CrossProfileApps(contextImpl.getOuterContext(), android.content.pm.ICrossProfileApps.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.CROSS_PROFILE_APPS_SERVICE)));
            }
        });
        registerService("slice", android.app.slice.SliceManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.slice.SliceManager>() { // from class: android.app.SystemServiceRegistry.109
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.slice.SliceManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.app.slice.SliceManager(contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler());
            }
        });
        registerService("time_detector", android.app.timedetector.TimeDetector.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.timedetector.TimeDetector>() { // from class: android.app.SystemServiceRegistry.110
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.timedetector.TimeDetector createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.app.timedetector.TimeDetectorImpl();
            }
        });
        registerService("time_zone_detector", android.app.timezonedetector.TimeZoneDetector.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.timezonedetector.TimeZoneDetector>() { // from class: android.app.SystemServiceRegistry.111
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.timezonedetector.TimeZoneDetector createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.app.timezonedetector.TimeZoneDetectorImpl();
            }
        });
        registerService(android.content.Context.TIME_MANAGER_SERVICE, android.app.time.TimeManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.time.TimeManager>() { // from class: android.app.SystemServiceRegistry.112
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.time.TimeManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.app.time.TimeManager();
            }
        });
        registerService("permission", android.permission.PermissionManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.permission.PermissionManager>() { // from class: android.app.SystemServiceRegistry.113
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.permission.PermissionManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.permission.PermissionManager(contextImpl.getOuterContext());
            }
        });
        registerService(android.content.Context.LEGACY_PERMISSION_SERVICE, android.permission.LegacyPermissionManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.permission.LegacyPermissionManager>() { // from class: android.app.SystemServiceRegistry.114
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.permission.LegacyPermissionManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.permission.LegacyPermissionManager();
            }
        });
        registerService(android.content.Context.PERMISSION_CONTROLLER_SERVICE, android.permission.PermissionControllerManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.permission.PermissionControllerManager>() { // from class: android.app.SystemServiceRegistry.115
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.permission.PermissionControllerManager createService(android.app.ContextImpl contextImpl) {
                return new android.permission.PermissionControllerManager(contextImpl.getOuterContext(), contextImpl.getMainThreadHandler());
            }
        });
        registerService(android.content.Context.PERMISSION_CHECKER_SERVICE, android.permission.PermissionCheckerManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.permission.PermissionCheckerManager>() { // from class: android.app.SystemServiceRegistry.116
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.permission.PermissionCheckerManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.permission.PermissionCheckerManager(contextImpl.getOuterContext());
            }
        });
        registerService(android.content.Context.PERMISSION_ENFORCER_SERVICE, android.os.PermissionEnforcer.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.os.PermissionEnforcer>() { // from class: android.app.SystemServiceRegistry.117
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.os.PermissionEnforcer createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.os.PermissionEnforcer(contextImpl.getOuterContext());
            }
        });
        registerService(android.content.Context.DYNAMIC_SYSTEM_SERVICE, android.os.image.DynamicSystemManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.os.image.DynamicSystemManager>() { // from class: android.app.SystemServiceRegistry.118
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.os.image.DynamicSystemManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.os.image.DynamicSystemManager(android.os.image.IDynamicSystemService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.DYNAMIC_SYSTEM_SERVICE)));
            }
        });
        registerService("batterystats", android.os.BatteryStatsManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.os.BatteryStatsManager>() { // from class: android.app.SystemServiceRegistry.119
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.os.BatteryStatsManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.os.BatteryStatsManager(com.android.internal.app.IBatteryStats.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("batterystats")));
            }
        });
        registerService(android.content.Context.DATA_LOADER_MANAGER_SERVICE, android.content.pm.DataLoaderManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.content.pm.DataLoaderManager>() { // from class: android.app.SystemServiceRegistry.120
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.content.pm.DataLoaderManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.content.pm.DataLoaderManager(android.content.pm.IDataLoaderManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.DATA_LOADER_MANAGER_SERVICE)));
            }
        });
        registerService(android.content.Context.LIGHTS_SERVICE, android.hardware.lights.LightsManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.hardware.lights.LightsManager>() { // from class: android.app.SystemServiceRegistry.121
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.hardware.lights.LightsManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.hardware.lights.SystemLightsManager(contextImpl);
            }
        });
        registerService("locale", android.app.LocaleManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.LocaleManager>() { // from class: android.app.SystemServiceRegistry.122
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.LocaleManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.app.LocaleManager(contextImpl, android.app.ILocaleManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("locale")));
            }
        });
        registerService(android.content.Context.INCREMENTAL_SERVICE, android.os.incremental.IncrementalManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.os.incremental.IncrementalManager>() { // from class: android.app.SystemServiceRegistry.123
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.os.incremental.IncrementalManager createService(android.app.ContextImpl contextImpl) {
                android.os.IBinder service = android.os.ServiceManager.getService(android.content.Context.INCREMENTAL_SERVICE);
                if (service == null) {
                    return null;
                }
                return new android.os.incremental.IncrementalManager(android.os.incremental.IIncrementalService.Stub.asInterface(service));
            }
        });
        registerService(android.content.Context.FILE_INTEGRITY_SERVICE, android.security.FileIntegrityManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.security.FileIntegrityManager>() { // from class: android.app.SystemServiceRegistry.124
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.security.FileIntegrityManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.security.FileIntegrityManager(contextImpl.getOuterContext(), android.security.IFileIntegrityService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.FILE_INTEGRITY_SERVICE)));
            }
        });
        registerService(android.content.Context.ATTESTATION_VERIFICATION_SERVICE, android.security.attestationverification.AttestationVerificationManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.security.attestationverification.AttestationVerificationManager>() { // from class: android.app.SystemServiceRegistry.125
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.security.attestationverification.AttestationVerificationManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.security.attestationverification.AttestationVerificationManager(contextImpl.getOuterContext(), android.security.attestationverification.IAttestationVerificationManagerService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.ATTESTATION_VERIFICATION_SERVICE)));
            }
        });
        registerService(android.content.Context.APP_INTEGRITY_SERVICE, android.content.integrity.AppIntegrityManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.content.integrity.AppIntegrityManager>() { // from class: android.app.SystemServiceRegistry.126
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.content.integrity.AppIntegrityManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.content.integrity.AppIntegrityManager(android.content.integrity.IAppIntegrityManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.APP_INTEGRITY_SERVICE)));
            }
        });
        registerService(android.content.Context.APP_HIBERNATION_SERVICE, android.apphibernation.AppHibernationManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.apphibernation.AppHibernationManager>() { // from class: android.app.SystemServiceRegistry.127
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.apphibernation.AppHibernationManager createService(android.app.ContextImpl contextImpl) {
                if (android.os.ServiceManager.getService(android.content.Context.APP_HIBERNATION_SERVICE) == null) {
                    return null;
                }
                return new android.apphibernation.AppHibernationManager(contextImpl);
            }
        });
        registerService(android.content.Context.DREAM_SERVICE, android.app.DreamManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.DreamManager>() { // from class: android.app.SystemServiceRegistry.128
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.DreamManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.app.DreamManager(contextImpl);
            }
        });
        registerService(android.content.Context.DEVICE_STATE_SERVICE, android.hardware.devicestate.DeviceStateManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.hardware.devicestate.DeviceStateManager>() { // from class: android.app.SystemServiceRegistry.129
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.hardware.devicestate.DeviceStateManager createService(android.app.ContextImpl contextImpl) {
                return new android.hardware.devicestate.DeviceStateManager();
            }
        });
        registerService(android.content.Context.MEDIA_METRICS_SERVICE, android.media.metrics.MediaMetricsManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.media.metrics.MediaMetricsManager>() { // from class: android.app.SystemServiceRegistry.130
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.media.metrics.MediaMetricsManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.media.metrics.MediaMetricsManager(android.media.metrics.IMediaMetricsManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.MEDIA_METRICS_SERVICE)), contextImpl.getUserId());
            }
        });
        registerService(android.content.Context.GAME_SERVICE, android.app.GameManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.GameManager>() { // from class: android.app.SystemServiceRegistry.131
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.GameManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.app.GameManager(contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler());
            }
        });
        registerService(android.content.Context.DOMAIN_VERIFICATION_SERVICE, android.content.pm.verify.domain.DomainVerificationManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.content.pm.verify.domain.DomainVerificationManager>() { // from class: android.app.SystemServiceRegistry.132
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.content.pm.verify.domain.DomainVerificationManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.content.pm.verify.domain.DomainVerificationManager(contextImpl, android.content.pm.verify.domain.IDomainVerificationManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.DOMAIN_VERIFICATION_SERVICE)));
            }
        });
        registerService(android.content.Context.DISPLAY_HASH_SERVICE, android.view.displayhash.DisplayHashManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.view.displayhash.DisplayHashManager>() { // from class: android.app.SystemServiceRegistry.133
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.view.displayhash.DisplayHashManager createService(android.app.ContextImpl contextImpl) {
                return new android.view.displayhash.DisplayHashManager();
            }
        });
        registerService(android.content.Context.AMBIENT_CONTEXT_SERVICE, android.app.ambientcontext.AmbientContextManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.ambientcontext.AmbientContextManager>() { // from class: android.app.SystemServiceRegistry.134
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.ambientcontext.AmbientContextManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.app.ambientcontext.AmbientContextManager(contextImpl.getOuterContext(), android.app.ambientcontext.IAmbientContextManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.AMBIENT_CONTEXT_SERVICE)));
            }
        });
        registerService(android.content.Context.WEARABLE_SENSING_SERVICE, android.app.wearable.WearableSensingManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.wearable.WearableSensingManager>() { // from class: android.app.SystemServiceRegistry.135
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.wearable.WearableSensingManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.app.wearable.WearableSensingManager(contextImpl.getOuterContext(), android.app.wearable.IWearableSensingManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.WEARABLE_SENSING_SERVICE)));
            }
        });
        registerService(android.content.Context.GRAMMATICAL_INFLECTION_SERVICE, android.app.GrammaticalInflectionManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.GrammaticalInflectionManager>() { // from class: android.app.SystemServiceRegistry.136
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.app.GrammaticalInflectionManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return new android.app.GrammaticalInflectionManager(contextImpl, android.app.IGrammaticalInflectionManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.GRAMMATICAL_INFLECTION_SERVICE)));
            }
        });
        registerService(android.content.Context.SHARED_CONNECTIVITY_SERVICE, android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.net.wifi.sharedconnectivity.app.SharedConnectivityManager>() { // from class: android.app.SystemServiceRegistry.137
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.net.wifi.sharedconnectivity.app.SharedConnectivityManager createService(android.app.ContextImpl contextImpl) {
                return android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.create(contextImpl);
            }
        });
        registerService(android.content.Context.CONTACT_KEYS_SERVICE, android.provider.E2eeContactKeysManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.provider.E2eeContactKeysManager>() { // from class: android.app.SystemServiceRegistry.138
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public android.provider.E2eeContactKeysManager createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                if (!android.provider.Flags.userKeys()) {
                    throw new android.os.ServiceManager.ServiceNotFoundException("ContactKeysManager is not supported");
                }
                return new android.provider.E2eeContactKeysManager(contextImpl);
            }
        });
        if (android.app.Flags.bicClient()) {
            registerService(android.content.Context.BACKGROUND_INSTALL_CONTROL_SERVICE, android.app.BackgroundInstallControlManager.class, new android.app.SystemServiceRegistry.CachedServiceFetcher<android.app.BackgroundInstallControlManager>() { // from class: android.app.SystemServiceRegistry.139
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
                public android.app.BackgroundInstallControlManager createService(android.app.ContextImpl contextImpl) {
                    return new android.app.BackgroundInstallControlManager(contextImpl);
                }
            });
        }
        sInitializing = true;
        try {
            android.net.ConnectivityFrameworkInitializer.registerServiceWrappers();
            android.app.job.JobSchedulerFrameworkInitializer.registerServiceWrappers();
            android.app.blob.BlobStoreManagerFrameworkInitializer.initialize();
            android.bluetooth.BluetoothFrameworkInitializer.registerServiceWrappers();
            android.nfc.NfcFrameworkInitializer.registerServiceWrappers();
            android.telephony.TelephonyFrameworkInitializer.registerServiceWrappers();
            android.app.appsearch.AppSearchManagerFrameworkInitializer.initialize();
            android.health.connect.HealthServicesInitializer.registerServiceWrappers();
            android.net.wifi.WifiFrameworkInitializer.registerServiceWrappers();
            android.os.StatsFrameworkInitializer.registerServiceWrappers();
            android.content.rollback.RollbackManagerFrameworkInitializer.initialize();
            android.media.MediaFrameworkPlatformInitializer.registerServiceWrappers();
            android.media.MediaFrameworkInitializer.registerServiceWrappers();
            android.app.role.RoleFrameworkInitializer.registerServiceWrappers();
            android.scheduling.SchedulingFrameworkInitializer.registerServiceWrappers();
            android.app.sdksandbox.SdkSandboxManagerFrameworkInitializer.registerServiceWrappers();
            android.adservices.AdServicesFrameworkInitializer.registerServiceWrappers();
            android.uwb.UwbFrameworkInitializer.registerServiceWrappers();
            android.safetycenter.SafetyCenterFrameworkInitializer.registerServiceWrappers();
            android.net.ConnectivityFrameworkInitializerTiramisu.registerServiceWrappers();
            android.nearby.NearbyFrameworkInitializer.registerServiceWrappers();
            android.ondevicepersonalization.OnDevicePersonalizationFrameworkInitializer.registerServiceWrappers();
            android.devicelock.DeviceLockFrameworkInitializer.registerServiceWrappers();
            android.system.virtualmachine.VirtualizationFrameworkInitializer.registerServiceWrappers();
            if (android.permission.flags.Flags.enhancedConfirmationModeApisEnabled()) {
                android.app.ecm.EnhancedConfirmationFrameworkInitializer.registerServiceWrappers();
            }
            if (android.server.Flags.telemetryApisService()) {
                android.os.ProfilingFrameworkInitializer.registerServiceWrappers();
            }
            if (android.webkit.Flags.updateServiceIpcWrapper()) {
                android.webkit.WebViewBootstrapFrameworkInitializer.registerServiceWrappers();
            }
        } finally {
            sInitializing = false;
        }
    }

    private SystemServiceRegistry() {
    }

    /* renamed from: android.app.SystemServiceRegistry$17, reason: invalid class name */
    class AnonymousClass17 extends android.app.SystemServiceRegistry.CachedServiceFetcher<android.net.TetheringManager> {
        AnonymousClass17() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
        public android.net.TetheringManager createService(android.app.ContextImpl contextImpl) {
            return new android.net.TetheringManager(contextImpl, new java.util.function.Supplier() { // from class: android.app.SystemServiceRegistry$17$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.os.IBinder service;
                    service = android.os.ServiceManager.getService(android.content.Context.TETHERING_SERVICE);
                    return service;
                }
            });
        }
    }

    private static void ensureInitializing(java.lang.String str) {
        com.android.internal.util.Preconditions.checkState(sInitializing, "Internal error: %s can only be called during class initialization.", str);
    }

    public static java.lang.Object[] createServiceCache() {
        return new java.lang.Object[sServiceCacheSize];
    }

    private static android.app.SystemServiceRegistry.ServiceFetcher<?> getSystemServiceFetcher(java.lang.String str) {
        if (str == null) {
            return null;
        }
        android.app.SystemServiceRegistry.ServiceFetcher<?> serviceFetcher = SYSTEM_SERVICE_FETCHERS.get(str);
        if (serviceFetcher == null) {
            if (sEnableServiceNotFoundWtf) {
                android.util.Slog.wtf(TAG, "Unknown manager requested: " + str);
            }
            return null;
        }
        return serviceFetcher;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static java.lang.Object getSystemService(android.app.ContextImpl contextImpl, java.lang.String str) {
        char c;
        android.app.SystemServiceRegistry.ServiceFetcher<?> systemServiceFetcher = getSystemServiceFetcher(str);
        if (systemServiceFetcher == null) {
            return null;
        }
        java.lang.Object service = systemServiceFetcher.getService(contextImpl);
        if (sEnableServiceNotFoundWtf && service == null) {
            switch (str.hashCode()) {
                case -1419358249:
                    if (str.equals(android.content.Context.ETHERNET_SERVICE)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -906336856:
                    if (str.equals("search")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -769002131:
                    if (str.equals(android.content.Context.APP_PREDICTION_SERVICE)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 56472313:
                    if (str.equals(android.content.Context.VIRTUALIZATION_SERVICE)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 273796326:
                    if (str.equals(android.content.Context.CONTEXTHUB_SERVICE)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 948275489:
                    if (str.equals(android.content.Context.VIRTUAL_DEVICE_SERVICE)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 974854528:
                    if (str.equals(android.content.Context.CONTENT_CAPTURE_MANAGER_SERVICE)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1085372378:
                    if (str.equals(android.content.Context.INCREMENTAL_SERVICE)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    return null;
                case 7:
                    android.content.pm.PackageManager packageManager = contextImpl.getPackageManager();
                    if (packageManager != null && packageManager.hasSystemFeature(android.content.pm.PackageManager.FEATURE_WATCH)) {
                        return null;
                    }
                    break;
            }
            android.util.Slog.wtf(TAG, "Manager wrapper not available: " + str);
            return null;
        }
        return service;
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static java.lang.Object getSystemServiceWithNoContext(java.lang.String str) {
        android.app.SystemServiceRegistry.ServiceFetcher<?> systemServiceFetcher = getSystemServiceFetcher(str);
        if (systemServiceFetcher == null) {
            return null;
        }
        if (!systemServiceFetcher.supportsFetchWithoutContext()) {
            throw new java.lang.IllegalArgumentException("Manager cannot be fetched without a context: " + str);
        }
        return systemServiceFetcher.getService(null);
    }

    public static java.lang.String getSystemServiceName(java.lang.Class<?> cls) {
        if (cls == null) {
            return null;
        }
        java.lang.String str = SYSTEM_SERVICE_NAMES.get(cls);
        if (sEnableServiceNotFoundWtf && str == null) {
            android.util.Slog.wtf(TAG, "Unknown manager requested: " + cls.getCanonicalName());
        }
        return str;
    }

    private static <T> void registerService(java.lang.String str, java.lang.Class<T> cls, android.app.SystemServiceRegistry.ServiceFetcher<T> serviceFetcher) {
        SYSTEM_SERVICE_NAMES.put(cls, str);
        SYSTEM_SERVICE_FETCHERS.put(str, serviceFetcher);
        SYSTEM_SERVICE_CLASS_NAMES.put(str, cls.getSimpleName());
    }

    public static java.lang.String getSystemServiceClassName(java.lang.String str) {
        return SYSTEM_SERVICE_CLASS_NAMES.get(str);
    }

    @android.annotation.SystemApi
    public static <TServiceClass> void registerStaticService(final java.lang.String str, java.lang.Class<TServiceClass> cls, final android.app.SystemServiceRegistry.StaticServiceProducerWithBinder<TServiceClass> staticServiceProducerWithBinder) {
        ensureInitializing("registerStaticService");
        com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        java.util.Objects.requireNonNull(cls);
        java.util.Objects.requireNonNull(staticServiceProducerWithBinder);
        registerService(str, cls, new android.app.SystemServiceRegistry.StaticServiceFetcher<TServiceClass>() { // from class: android.app.SystemServiceRegistry.140
            @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
            public TServiceClass createService() throws android.os.ServiceManager.ServiceNotFoundException {
                return (TServiceClass) android.app.SystemServiceRegistry.StaticServiceProducerWithBinder.this.createService(android.os.ServiceManager.getServiceOrThrow(str));
            }
        });
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static <TServiceClass> void registerForeverStaticService(final java.lang.String str, java.lang.Class<TServiceClass> cls, final android.app.SystemServiceRegistry.StaticServiceProducerWithBinder<TServiceClass> staticServiceProducerWithBinder) {
        ensureInitializing("registerStaticService");
        com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        java.util.Objects.requireNonNull(cls);
        java.util.Objects.requireNonNull(staticServiceProducerWithBinder);
        registerService(str, cls, new android.app.SystemServiceRegistry.StaticServiceFetcher<TServiceClass>() { // from class: android.app.SystemServiceRegistry.141
            @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
            public TServiceClass createService() throws android.os.ServiceManager.ServiceNotFoundException {
                return (TServiceClass) android.app.SystemServiceRegistry.StaticServiceProducerWithBinder.this.createService(android.os.ServiceManager.getServiceOrThrow(str));
            }

            @Override // android.app.SystemServiceRegistry.StaticServiceFetcher, android.app.SystemServiceRegistry.ServiceFetcher
            public boolean supportsFetchWithoutContext() {
                return true;
            }
        });
    }

    @android.annotation.SystemApi
    public static <TServiceClass> void registerStaticService(java.lang.String str, java.lang.Class<TServiceClass> cls, final android.app.SystemServiceRegistry.StaticServiceProducerWithoutBinder<TServiceClass> staticServiceProducerWithoutBinder) {
        ensureInitializing("registerStaticService");
        com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        java.util.Objects.requireNonNull(cls);
        java.util.Objects.requireNonNull(staticServiceProducerWithoutBinder);
        registerService(str, cls, new android.app.SystemServiceRegistry.StaticServiceFetcher<TServiceClass>() { // from class: android.app.SystemServiceRegistry.142
            @Override // android.app.SystemServiceRegistry.StaticServiceFetcher
            public TServiceClass createService() {
                return (TServiceClass) android.app.SystemServiceRegistry.StaticServiceProducerWithoutBinder.this.createService();
            }
        });
    }

    @android.annotation.SystemApi
    public static <TServiceClass> void registerContextAwareService(final java.lang.String str, java.lang.Class<TServiceClass> cls, final android.app.SystemServiceRegistry.ContextAwareServiceProducerWithBinder<TServiceClass> contextAwareServiceProducerWithBinder) {
        ensureInitializing("registerContextAwareService");
        com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        java.util.Objects.requireNonNull(cls);
        java.util.Objects.requireNonNull(contextAwareServiceProducerWithBinder);
        registerService(str, cls, new android.app.SystemServiceRegistry.CachedServiceFetcher<TServiceClass>() { // from class: android.app.SystemServiceRegistry.143
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public TServiceClass createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException {
                return (TServiceClass) android.app.SystemServiceRegistry.ContextAwareServiceProducerWithBinder.this.createService(contextImpl.getOuterContext(), android.os.ServiceManager.getServiceOrThrow(str));
            }
        });
    }

    @android.annotation.SystemApi
    public static <TServiceClass> void registerContextAwareService(java.lang.String str, java.lang.Class<TServiceClass> cls, final android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder<TServiceClass> contextAwareServiceProducerWithoutBinder) {
        ensureInitializing("registerContextAwareService");
        com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        java.util.Objects.requireNonNull(cls);
        java.util.Objects.requireNonNull(contextAwareServiceProducerWithoutBinder);
        registerService(str, cls, new android.app.SystemServiceRegistry.CachedServiceFetcher<TServiceClass>() { // from class: android.app.SystemServiceRegistry.144
            @Override // android.app.SystemServiceRegistry.CachedServiceFetcher
            public TServiceClass createService(android.app.ContextImpl contextImpl) {
                return (TServiceClass) android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder.this.createService(contextImpl.getOuterContext());
            }
        });
    }

    interface ServiceFetcher<T> {
        T getService(android.app.ContextImpl contextImpl);

        default boolean supportsFetchWithoutContext() {
            return false;
        }
    }

    static abstract class CachedServiceFetcher<T> implements android.app.SystemServiceRegistry.ServiceFetcher<T> {
        private final int mCacheIndex;

        public abstract T createService(android.app.ContextImpl contextImpl) throws android.os.ServiceManager.ServiceNotFoundException;

        CachedServiceFetcher() {
            int i = android.app.SystemServiceRegistry.sServiceCacheSize;
            android.app.SystemServiceRegistry.sServiceCacheSize = i + 1;
            this.mCacheIndex = i;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.app.SystemServiceRegistry.ServiceFetcher
        public final T getService(android.app.ContextImpl contextImpl) {
            T t;
            boolean z;
            int i;
            java.lang.Object[] objArr = contextImpl.mServiceCache;
            int[] iArr = contextImpl.mServiceInitializationStateArray;
            boolean z2 = false;
            while (true) {
                synchronized (objArr) {
                    t = (T) objArr[this.mCacheIndex];
                    if (t != null) {
                        break;
                    }
                    if (iArr[this.mCacheIndex] == 2 || iArr[this.mCacheIndex] == 3) {
                        iArr[this.mCacheIndex] = 0;
                    }
                    if (iArr[this.mCacheIndex] == 0) {
                        iArr[this.mCacheIndex] = 1;
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        java.lang.Object obj = null;
                        obj = null;
                        obj = null;
                        try {
                            try {
                                T createService = createService(contextImpl);
                                synchronized (objArr) {
                                    objArr[this.mCacheIndex] = createService;
                                    i = this.mCacheIndex;
                                    iArr[i] = 2;
                                    objArr.notifyAll();
                                }
                                t = createService;
                                obj = i;
                            } catch (android.os.ServiceManager.ServiceNotFoundException e) {
                                android.app.SystemServiceRegistry.onServiceNotFound(e);
                                synchronized (objArr) {
                                    objArr[this.mCacheIndex] = null;
                                    iArr[this.mCacheIndex] = 3;
                                    objArr.notifyAll();
                                    t = null;
                                }
                            }
                            break;
                        } catch (java.lang.Throwable th) {
                            synchronized (objArr) {
                                objArr[this.mCacheIndex] = obj;
                                iArr[this.mCacheIndex] = 3;
                                objArr.notifyAll();
                                throw th;
                            }
                        }
                    }
                    synchronized (objArr) {
                        while (iArr[this.mCacheIndex] < 2) {
                            try {
                                z2 |= java.lang.Thread.interrupted();
                                objArr.wait();
                            } catch (java.lang.InterruptedException e2) {
                                android.util.Slog.w(android.app.SystemServiceRegistry.TAG, "getService() interrupted");
                                z2 = true;
                            }
                        }
                    }
                }
            }
            if (z2) {
                java.lang.Thread.currentThread().interrupt();
            }
            return t;
        }

        @Override // android.app.SystemServiceRegistry.ServiceFetcher
        public final boolean supportsFetchWithoutContext() {
            return false;
        }
    }

    static abstract class StaticServiceFetcher<T> implements android.app.SystemServiceRegistry.ServiceFetcher<T> {
        private T mCachedInstance;

        public abstract T createService() throws android.os.ServiceManager.ServiceNotFoundException;

        StaticServiceFetcher() {
        }

        @Override // android.app.SystemServiceRegistry.ServiceFetcher
        public final T getService(android.app.ContextImpl contextImpl) {
            T t;
            synchronized (this) {
                if (this.mCachedInstance == null) {
                    try {
                        this.mCachedInstance = createService();
                    } catch (android.os.ServiceManager.ServiceNotFoundException e) {
                        android.app.SystemServiceRegistry.onServiceNotFound(e);
                    }
                }
                t = this.mCachedInstance;
            }
            return t;
        }

        @Override // android.app.SystemServiceRegistry.ServiceFetcher
        public boolean supportsFetchWithoutContext() {
            return false;
        }
    }

    public static void onServiceNotFound(android.os.ServiceManager.ServiceNotFoundException serviceNotFoundException) {
        if (android.os.Process.myUid() < 10000) {
            android.util.Log.wtf(TAG, serviceNotFoundException.getMessage(), serviceNotFoundException);
        } else {
            android.util.Log.w(TAG, serviceNotFoundException.getMessage());
        }
    }
}
