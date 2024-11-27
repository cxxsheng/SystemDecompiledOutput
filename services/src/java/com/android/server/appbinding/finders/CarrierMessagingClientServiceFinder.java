package com.android.server.appbinding.finders;

/* loaded from: classes.dex */
public class CarrierMessagingClientServiceFinder extends com.android.server.appbinding.finders.AppServiceFinder<android.service.carrier.CarrierMessagingClientService, android.service.carrier.ICarrierMessagingClientService> {
    private final android.app.role.OnRoleHoldersChangedListener mRoleHolderChangedListener;
    private final android.app.role.RoleManager mRoleManager;

    public CarrierMessagingClientServiceFinder(android.content.Context context, java.util.function.BiConsumer<com.android.server.appbinding.finders.AppServiceFinder, java.lang.Integer> biConsumer, android.os.Handler handler) {
        super(context, biConsumer, handler);
        this.mRoleHolderChangedListener = new android.app.role.OnRoleHoldersChangedListener() { // from class: com.android.server.appbinding.finders.CarrierMessagingClientServiceFinder$$ExternalSyntheticLambda0
            public final void onRoleHoldersChanged(java.lang.String str, android.os.UserHandle userHandle) {
                com.android.server.appbinding.finders.CarrierMessagingClientServiceFinder.this.lambda$new$0(str, userHandle);
            }
        };
        this.mRoleManager = (android.app.role.RoleManager) context.getSystemService(android.app.role.RoleManager.class);
    }

    @Override // com.android.server.appbinding.finders.AppServiceFinder
    protected boolean isEnabled(com.android.server.appbinding.AppBindingConstants appBindingConstants) {
        return appBindingConstants.SMS_SERVICE_ENABLED && this.mContext.getResources().getBoolean(android.R.bool.config_useFixedVolume);
    }

    @Override // com.android.server.appbinding.finders.AppServiceFinder
    public java.lang.String getAppDescription() {
        return "[Default SMS app]";
    }

    @Override // com.android.server.appbinding.finders.AppServiceFinder
    protected java.lang.Class<android.service.carrier.CarrierMessagingClientService> getServiceClass() {
        return android.service.carrier.CarrierMessagingClientService.class;
    }

    @Override // com.android.server.appbinding.finders.AppServiceFinder
    public android.service.carrier.ICarrierMessagingClientService asInterface(android.os.IBinder iBinder) {
        return android.service.carrier.ICarrierMessagingClientService.Stub.asInterface(iBinder);
    }

    @Override // com.android.server.appbinding.finders.AppServiceFinder
    protected java.lang.String getServiceAction() {
        return "android.telephony.action.CARRIER_MESSAGING_CLIENT_SERVICE";
    }

    @Override // com.android.server.appbinding.finders.AppServiceFinder
    protected java.lang.String getServicePermission() {
        return "android.permission.BIND_CARRIER_MESSAGING_CLIENT_SERVICE";
    }

    @Override // com.android.server.appbinding.finders.AppServiceFinder
    public java.lang.String getTargetPackage(int i) {
        return (java.lang.String) com.android.internal.util.CollectionUtils.firstOrNull(this.mRoleManager.getRoleHoldersAsUser("android.app.role.SMS", android.os.UserHandle.of(i)));
    }

    @Override // com.android.server.appbinding.finders.AppServiceFinder
    public void startMonitoring() {
        this.mRoleManager.addOnRoleHoldersChangedListenerAsUser(com.android.internal.os.BackgroundThread.getExecutor(), this.mRoleHolderChangedListener, android.os.UserHandle.ALL);
    }

    @Override // com.android.server.appbinding.finders.AppServiceFinder
    protected java.lang.String validateService(android.content.pm.ServiceInfo serviceInfo) {
        java.lang.String str = serviceInfo.packageName;
        java.lang.String str2 = serviceInfo.processName;
        if (str2 == null || android.text.TextUtils.equals(str, str2)) {
            return "Service must not run on the main process";
        }
        return null;
    }

    @Override // com.android.server.appbinding.finders.AppServiceFinder
    public int getBindFlags(com.android.server.appbinding.AppBindingConstants appBindingConstants) {
        return appBindingConstants.SMS_APP_BIND_FLAGS;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(java.lang.String str, android.os.UserHandle userHandle) {
        if ("android.app.role.SMS".equals(str)) {
            this.mListener.accept(this, java.lang.Integer.valueOf(userHandle.getIdentifier()));
        }
    }
}
