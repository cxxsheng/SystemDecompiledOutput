package com.android.internal.app;

/* loaded from: classes4.dex */
public class IntentForwarderActivity extends android.app.Activity {
    public static final java.lang.String EXTRA_SKIP_USER_CONFIRMATION = "com.android.internal.app.EXTRA_SKIP_USER_CONFIRMATION";
    private static final java.lang.String TEL_SCHEME = "tel";
    protected java.util.concurrent.ExecutorService mExecutorService;
    private com.android.internal.app.IntentForwarderActivity.Injector mInjector;
    private com.android.internal.logging.MetricsLogger mMetricsLogger;
    public static java.lang.String TAG = "IntentForwarderActivity";
    public static java.lang.String FORWARD_INTENT_TO_PARENT = "com.android.internal.app.ForwardIntentToParent";
    public static java.lang.String FORWARD_INTENT_TO_MANAGED_PROFILE = "com.android.internal.app.ForwardIntentToManagedProfile";
    private static final java.util.Set<java.lang.String> ALLOWED_TEXT_MESSAGE_SCHEMES = new java.util.HashSet(java.util.Arrays.asList(android.content.Context.SMS_SERVICE, "smsto", "mms", "mmsto"));
    private static final android.content.ComponentName RESOLVER_COMPONENT_NAME = new android.content.ComponentName("android", com.android.internal.app.ResolverActivity.class.getName());

    public interface Injector {
        android.content.pm.IPackageManager getIPackageManager();

        android.content.pm.PackageManager getPackageManager();

        android.os.UserManager getUserManager();

        java.util.concurrent.CompletableFuture<android.content.pm.ResolveInfo> resolveActivityAsUser(android.content.Intent intent, int i, int i2);

        void showToast(java.lang.String str, int i);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.mExecutorService.shutdown();
    }

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        final int i;
        java.lang.String str;
        android.content.pm.UserInfo userInfo;
        super.onCreate(bundle);
        this.mInjector = createInjector();
        this.mExecutorService = java.util.concurrent.Executors.newSingleThreadExecutor();
        final android.content.Intent intent = getIntent();
        final java.lang.String className = intent.getComponent().getClassName();
        if (className.equals(FORWARD_INTENT_TO_PARENT)) {
            java.lang.String forwardToPersonalMessage = getForwardToPersonalMessage();
            int profileParent = getProfileParent();
            getMetricsLogger().write(new android.metrics.LogMaker(com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_SWITCH_SHARE_PROFILE).setSubtype(1));
            str = forwardToPersonalMessage;
            userInfo = null;
            i = profileParent;
        } else if (className.equals(FORWARD_INTENT_TO_MANAGED_PROFILE)) {
            java.lang.String forwardToWorkMessage = getForwardToWorkMessage();
            android.content.pm.UserInfo managedProfile = getManagedProfile();
            int i2 = managedProfile == null ? -10000 : managedProfile.id;
            getMetricsLogger().write(new android.metrics.LogMaker(com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_SWITCH_SHARE_PROFILE).setSubtype(2));
            str = forwardToWorkMessage;
            userInfo = managedProfile;
            i = i2;
        } else {
            android.util.Slog.wtf(TAG, com.android.internal.app.IntentForwarderActivity.class.getName() + " cannot be called directly");
            i = -10000;
            str = null;
            userInfo = null;
        }
        if (i == -10000) {
            finish();
            return;
        }
        if (android.content.Intent.ACTION_CHOOSER.equals(intent.getAction())) {
            launchChooserActivityWithCorrectTab(intent, className);
            return;
        }
        final int userId = getUserId();
        final android.content.Intent canForward = canForward(intent, getUserId(), i, this.mInjector.getIPackageManager(), getContentResolver());
        if (canForward == null) {
            android.util.Slog.wtf(TAG, "the intent: " + intent + " cannot be forwarded from user " + userId + " to user " + i);
            finish();
            return;
        }
        canForward.prepareToLeaveUser(userId);
        java.util.concurrent.CompletableFuture<U> thenApplyAsync = this.mInjector.resolveActivityAsUser(canForward, 65536, i).thenApplyAsync(new java.util.function.Function() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.content.pm.ResolveInfo lambda$onCreate$0;
                lambda$onCreate$0 = com.android.internal.app.IntentForwarderActivity.this.lambda$onCreate$0(intent, className, canForward, userId, i, (android.content.pm.ResolveInfo) obj);
                return lambda$onCreate$0;
            }
        }, (java.util.concurrent.Executor) this.mExecutorService);
        final java.lang.String str2 = str;
        final android.content.pm.UserInfo userInfo2 = userInfo;
        thenApplyAsync.thenAcceptAsync((java.util.function.Consumer<? super U>) new java.util.function.Consumer() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.internal.app.IntentForwarderActivity.this.lambda$onCreate$1(className, intent, str2, canForward, userInfo2, (android.content.pm.ResolveInfo) obj);
            }
        }, getApplicationContext().getMainExecutor());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.pm.ResolveInfo lambda$onCreate$0(android.content.Intent intent, java.lang.String str, android.content.Intent intent2, int i, int i2, android.content.pm.ResolveInfo resolveInfo) {
        if (isResolverActivityResolveInfo(resolveInfo)) {
            launchResolverActivityWithCorrectTab(intent, str, intent2, i, i2);
        } else if (str.equals(FORWARD_INTENT_TO_PARENT)) {
            startActivityAsCaller(intent2, i2);
        }
        return resolveInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(java.lang.String str, android.content.Intent intent, java.lang.String str2, android.content.Intent intent2, android.content.pm.UserInfo userInfo, android.content.pm.ResolveInfo resolveInfo) {
        if (str.equals(FORWARD_INTENT_TO_PARENT)) {
            maybeShowDisclosure(intent, resolveInfo, str2);
            finish();
        } else if (str.equals(FORWARD_INTENT_TO_MANAGED_PROFILE)) {
            maybeShowUserConsentMiniResolver(resolveInfo, intent2, userInfo);
        }
    }

    private void maybeShowUserConsentMiniResolver(android.content.pm.ResolveInfo resolveInfo, final android.content.Intent intent, android.content.pm.UserInfo userInfo) {
        if (resolveInfo == null || isIntentForwarderResolveInfo(resolveInfo) || !isDeviceProvisioned()) {
            finish();
            return;
        }
        final int i = userInfo == null ? -10000 : userInfo.id;
        java.lang.String callingPackage = getCallingPackage();
        boolean z = intent.getBooleanExtra(EXTRA_SKIP_USER_CONFIRMATION, false) && callingPackage != null && getPackageManager().checkPermission(android.Manifest.permission.INTERACT_ACROSS_USERS, callingPackage) == 0;
        android.app.admin.DevicePolicyManager devicePolicyManager = (android.app.admin.DevicePolicyManager) getSystemService(android.app.admin.DevicePolicyManager.class);
        android.content.ComponentName profileOwnerAsUser = devicePolicyManager.getProfileOwnerAsUser(i);
        boolean z2 = profileOwnerAsUser != null && profileOwnerAsUser.getPackageName().equals(resolveInfo.getComponentInfo().packageName);
        if (z || z2) {
            android.util.Log.i("IntentForwarderActivity", java.lang.String.format("Skipping user consent for redirection into the managed profile for intent [%s], privilegedCallerAskedToSkipUserConsent=[%s], intentToLaunchProfileOwner=[%s]", intent, java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(z2)));
            startActivityAsCaller(intent, i);
            finish();
            return;
        }
        android.util.Log.i("IntentForwarderActivity", java.lang.String.format("Showing user consent for redirection into the managed profile for intent [%s] and  calling package [%s]", intent, callingPackage));
        setContentView(com.android.internal.R.layout.miniresolver);
        findViewById(com.android.internal.R.id.title_container).setElevation(0.0f);
        android.content.pm.PackageManager packageManager = createContextAsUser(android.os.UserHandle.of(i), 0).getPackageManager();
        ((android.widget.ImageView) findViewById(16908294)).lambda$setImageURIAsync$2(getAppIcon(resolveInfo, intent, i, packageManager));
        android.view.View findViewById = findViewById(com.android.internal.R.id.button_bar_container);
        findViewById.setPadding(0, 0, 0, findViewById.getPaddingBottom());
        ((android.widget.TextView) findViewById(com.android.internal.R.id.open_cross_profile)).lambda$setTextAsync$0(getOpenInWorkMessage(intent, resolveInfo.loadLabel(packageManager)));
        ((android.widget.Button) findViewById(com.android.internal.R.id.use_same_profile_browser)).setText(17039360);
        findViewById(com.android.internal.R.id.use_same_profile_browser).setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(android.view.View view) {
                com.android.internal.app.IntentForwarderActivity.this.lambda$maybeShowUserConsentMiniResolver$2(view);
            }
        });
        ((android.widget.Button) findViewById(com.android.internal.R.id.button_open)).setText(getOpenInWorkButtonString(intent));
        findViewById(com.android.internal.R.id.button_open).setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(android.view.View view) {
                com.android.internal.app.IntentForwarderActivity.this.lambda$maybeShowUserConsentMiniResolver$3(intent, i, view);
            }
        });
        android.view.View findViewById2 = findViewById(com.android.internal.R.id.miniresolver_info_section);
        if ((isDialerIntent(intent) || isTextMessageIntent(intent)) && devicePolicyManager.getManagedSubscriptionsPolicy().getPolicyType() == 1) {
            findViewById2.setVisibility(0);
            ((android.widget.TextView) findViewById(com.android.internal.R.id.miniresolver_info_section_text)).lambda$setTextAsync$0(getWorkTelephonyInfoSectionMessage(intent));
        } else {
            findViewById2.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$maybeShowUserConsentMiniResolver$2(android.view.View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$maybeShowUserConsentMiniResolver$3(android.content.Intent intent, int i, android.view.View view) {
        startActivityAsCaller(intent, android.app.ActivityOptions.makeCustomAnimation(getApplicationContext(), com.android.internal.R.anim.activity_open_enter, com.android.internal.R.anim.push_down_out).toBundle(), false, i);
        finish();
    }

    private android.graphics.drawable.Drawable getAppIcon(android.content.pm.ResolveInfo resolveInfo, android.content.Intent intent, int i, android.content.pm.PackageManager packageManager) {
        if (isDialerIntent(intent)) {
            try {
                return packageManager.getApplicationInfo(((android.telecom.TelecomManager) getApplicationContext().getSystemService(android.telecom.TelecomManager.class)).getDefaultDialerPackage(android.os.UserHandle.of(i)), 0).loadIcon(packageManager);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Slog.w(TAG, "Cannot load icon for default dialer package");
            }
        }
        return resolveInfo.loadIcon(packageManager);
    }

    private int getOpenInWorkButtonString(android.content.Intent intent) {
        if (isDialerIntent(intent)) {
            return com.android.internal.R.string.miniresolver_call;
        }
        if (isTextMessageIntent(intent)) {
            return com.android.internal.R.string.miniresolver_switch;
        }
        return com.android.internal.R.string.whichViewApplicationLabel;
    }

    private java.lang.String getOpenInWorkMessage(android.content.Intent intent, final java.lang.CharSequence charSequence) {
        if (isDialerIntent(intent)) {
            return ((android.app.admin.DevicePolicyManager) getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(android.app.admin.DevicePolicyResources.Strings.Core.MINIRESOLVER_CALL_FROM_WORK, new java.util.function.Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda5
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.String lambda$getOpenInWorkMessage$4;
                    lambda$getOpenInWorkMessage$4 = com.android.internal.app.IntentForwarderActivity.this.lambda$getOpenInWorkMessage$4();
                    return lambda$getOpenInWorkMessage$4;
                }
            });
        }
        if (isTextMessageIntent(intent)) {
            return ((android.app.admin.DevicePolicyManager) getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(android.app.admin.DevicePolicyResources.Strings.Core.MINIRESOLVER_SWITCH_TO_WORK, new java.util.function.Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.String lambda$getOpenInWorkMessage$5;
                    lambda$getOpenInWorkMessage$5 = com.android.internal.app.IntentForwarderActivity.this.lambda$getOpenInWorkMessage$5();
                    return lambda$getOpenInWorkMessage$5;
                }
            });
        }
        return ((android.app.admin.DevicePolicyManager) getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(android.app.admin.DevicePolicyResources.Strings.Core.MINIRESOLVER_OPEN_WORK, new java.util.function.Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String lambda$getOpenInWorkMessage$6;
                lambda$getOpenInWorkMessage$6 = com.android.internal.app.IntentForwarderActivity.this.lambda$getOpenInWorkMessage$6(charSequence);
                return lambda$getOpenInWorkMessage$6;
            }
        }, charSequence);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getOpenInWorkMessage$4() {
        return getString(com.android.internal.R.string.miniresolver_call_in_work);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getOpenInWorkMessage$5() {
        return getString(com.android.internal.R.string.miniresolver_switch_to_work);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getOpenInWorkMessage$6(java.lang.CharSequence charSequence) {
        return getString(com.android.internal.R.string.miniresolver_open_work, charSequence);
    }

    private java.lang.String getWorkTelephonyInfoSectionMessage(android.content.Intent intent) {
        if (isDialerIntent(intent)) {
            return ((android.app.admin.DevicePolicyManager) getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString("Core.MINIRESOLVER_WORK_TELEPHONY_INFORMATION", new java.util.function.Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.String lambda$getWorkTelephonyInfoSectionMessage$7;
                    lambda$getWorkTelephonyInfoSectionMessage$7 = com.android.internal.app.IntentForwarderActivity.this.lambda$getWorkTelephonyInfoSectionMessage$7();
                    return lambda$getWorkTelephonyInfoSectionMessage$7;
                }
            });
        }
        if (isTextMessageIntent(intent)) {
            return ((android.app.admin.DevicePolicyManager) getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString("Core.MINIRESOLVER_WORK_TELEPHONY_INFORMATION", new java.util.function.Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.String lambda$getWorkTelephonyInfoSectionMessage$8;
                    lambda$getWorkTelephonyInfoSectionMessage$8 = com.android.internal.app.IntentForwarderActivity.this.lambda$getWorkTelephonyInfoSectionMessage$8();
                    return lambda$getWorkTelephonyInfoSectionMessage$8;
                }
            });
        }
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getWorkTelephonyInfoSectionMessage$7() {
        return getString(com.android.internal.R.string.miniresolver_call_information);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getWorkTelephonyInfoSectionMessage$8() {
        return getString(com.android.internal.R.string.miniresolver_sms_information);
    }

    private java.lang.String getForwardToPersonalMessage() {
        return ((android.app.admin.DevicePolicyManager) getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(android.app.admin.DevicePolicyResources.Strings.Core.FORWARD_INTENT_TO_PERSONAL, new java.util.function.Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String lambda$getForwardToPersonalMessage$9;
                lambda$getForwardToPersonalMessage$9 = com.android.internal.app.IntentForwarderActivity.this.lambda$getForwardToPersonalMessage$9();
                return lambda$getForwardToPersonalMessage$9;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getForwardToPersonalMessage$9() {
        return getString(com.android.internal.R.string.forward_intent_to_owner);
    }

    private java.lang.String getForwardToWorkMessage() {
        return ((android.app.admin.DevicePolicyManager) getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(android.app.admin.DevicePolicyResources.Strings.Core.FORWARD_INTENT_TO_WORK, new java.util.function.Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String lambda$getForwardToWorkMessage$10;
                lambda$getForwardToWorkMessage$10 = com.android.internal.app.IntentForwarderActivity.this.lambda$getForwardToWorkMessage$10();
                return lambda$getForwardToWorkMessage$10;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getForwardToWorkMessage$10() {
        return getString(com.android.internal.R.string.forward_intent_to_work);
    }

    private boolean isIntentForwarderResolveInfo(android.content.pm.ResolveInfo resolveInfo) {
        android.content.pm.ActivityInfo activityInfo;
        if (resolveInfo == null || (activityInfo = resolveInfo.activityInfo) == null || !"android".equals(activityInfo.packageName)) {
            return false;
        }
        return activityInfo.name.equals(FORWARD_INTENT_TO_PARENT) || activityInfo.name.equals(FORWARD_INTENT_TO_MANAGED_PROFILE);
    }

    private boolean isResolverActivityResolveInfo(android.content.pm.ResolveInfo resolveInfo) {
        return (resolveInfo == null || resolveInfo.activityInfo == null || !RESOLVER_COMPONENT_NAME.equals(resolveInfo.activityInfo.getComponentName())) ? false : true;
    }

    private void maybeShowDisclosure(android.content.Intent intent, android.content.pm.ResolveInfo resolveInfo, java.lang.String str) {
        if (shouldShowDisclosure(resolveInfo, intent) && str != null) {
            this.mInjector.showToast(str, 1);
        }
    }

    private void startActivityAsCaller(android.content.Intent intent, int i) {
        try {
            startActivityAsCaller(intent, null, false, i);
        } catch (java.lang.RuntimeException e) {
            android.util.Slog.wtf(TAG, "Unable to launch as UID " + getLaunchedFromUid() + " package " + getLaunchedFromPackage() + ", while running in " + android.app.ActivityThread.currentProcessName(), e);
        }
    }

    private void launchChooserActivityWithCorrectTab(android.content.Intent intent, java.lang.String str) {
        int findSelectedProfile = findSelectedProfile(str);
        sanitizeIntent(intent);
        intent.putExtra("com.android.internal.app.ResolverActivity.EXTRA_SELECTED_PROFILE", findSelectedProfile);
        android.content.Intent intent2 = (android.content.Intent) intent.getParcelableExtra(android.content.Intent.EXTRA_INTENT, android.content.Intent.class);
        if (intent2 == null) {
            android.util.Slog.wtf(TAG, "Cannot start a chooser intent with no extra android.intent.extra.INTENT");
            return;
        }
        sanitizeIntent(intent2);
        startActivityAsCaller(intent, null, false, getUserId());
        finish();
    }

    private void launchResolverActivityWithCorrectTab(android.content.Intent intent, java.lang.String str, android.content.Intent intent2, int i, int i2) {
        if (!isIntentForwarderResolveInfo(this.mInjector.resolveActivityAsUser(intent2, 65536, i).join())) {
            i2 = i;
        }
        int findSelectedProfile = findSelectedProfile(str);
        sanitizeIntent(intent);
        intent.putExtra("com.android.internal.app.ResolverActivity.EXTRA_SELECTED_PROFILE", findSelectedProfile);
        intent.putExtra("com.android.internal.app.ResolverActivity.EXTRA_CALLING_USER", android.os.UserHandle.of(i));
        startActivityAsCaller(intent, null, false, i2);
        finish();
    }

    private int findSelectedProfile(java.lang.String str) {
        if (str.equals(FORWARD_INTENT_TO_PARENT)) {
            return 0;
        }
        if (str.equals(FORWARD_INTENT_TO_MANAGED_PROFILE)) {
            return 1;
        }
        return -1;
    }

    private boolean shouldShowDisclosure(android.content.pm.ResolveInfo resolveInfo, android.content.Intent intent) {
        if (!isDeviceProvisioned()) {
            return false;
        }
        if (resolveInfo == null || resolveInfo.activityInfo == null) {
            return true;
        }
        if (resolveInfo.activityInfo.applicationInfo.isSystemApp() && (isDialerIntent(intent) || isTextMessageIntent(intent))) {
            return false;
        }
        return !isTargetResolverOrChooserActivity(resolveInfo.activityInfo);
    }

    private boolean isDeviceProvisioned() {
        return android.provider.Settings.Global.getInt(getContentResolver(), "device_provisioned", 0) != 0;
    }

    private boolean isTextMessageIntent(android.content.Intent intent) {
        return (android.content.Intent.ACTION_SENDTO.equals(intent.getAction()) || isViewActionIntent(intent)) && ALLOWED_TEXT_MESSAGE_SCHEMES.contains(intent.getScheme());
    }

    private boolean isDialerIntent(android.content.Intent intent) {
        return android.content.Intent.ACTION_DIAL.equals(intent.getAction()) || android.content.Intent.ACTION_CALL.equals(intent.getAction()) || android.content.Intent.ACTION_CALL_PRIVILEGED.equals(intent.getAction()) || android.content.Intent.ACTION_CALL_EMERGENCY.equals(intent.getAction()) || (isViewActionIntent(intent) && "tel".equals(intent.getScheme()));
    }

    private boolean isViewActionIntent(android.content.Intent intent) {
        return "android.intent.action.VIEW".equals(intent.getAction()) && intent.hasCategory(android.content.Intent.CATEGORY_BROWSABLE);
    }

    private boolean isTargetResolverOrChooserActivity(android.content.pm.ActivityInfo activityInfo) {
        if ("android".equals(activityInfo.packageName)) {
            return com.android.internal.app.ResolverActivity.class.getName().equals(activityInfo.name) || com.android.internal.app.ChooserActivity.class.getName().equals(activityInfo.name);
        }
        return false;
    }

    static android.content.Intent canForward(android.content.Intent intent, int i, int i2, android.content.pm.IPackageManager iPackageManager, android.content.ContentResolver contentResolver) {
        android.content.Intent intent2;
        android.content.Intent intent3 = new android.content.Intent(intent);
        intent3.addFlags(50331648);
        sanitizeIntent(intent3);
        if (android.content.Intent.ACTION_CHOOSER.equals(intent3.getAction())) {
            return null;
        }
        if (intent3.getSelector() == null) {
            intent2 = intent3;
        } else {
            intent2 = intent3.getSelector();
        }
        java.lang.String resolveTypeIfNeeded = intent2.resolveTypeIfNeeded(contentResolver);
        sanitizeIntent(intent2);
        try {
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "PackageManagerService is dead?");
        }
        if (iPackageManager.canForwardTo(intent2, resolveTypeIfNeeded, i, i2)) {
            return intent3;
        }
        return null;
    }

    private android.content.pm.UserInfo getManagedProfile() {
        for (android.content.pm.UserInfo userInfo : this.mInjector.getUserManager().getProfiles(android.os.UserHandle.myUserId())) {
            if (userInfo.isManagedProfile()) {
                return userInfo;
            }
        }
        android.util.Slog.wtf(TAG, FORWARD_INTENT_TO_MANAGED_PROFILE + " has been called, but there is no managed profile");
        return null;
    }

    private int getProfileParent() {
        android.content.pm.UserInfo profileParent = this.mInjector.getUserManager().getProfileParent(android.os.UserHandle.myUserId());
        if (profileParent == null) {
            android.util.Slog.wtf(TAG, FORWARD_INTENT_TO_PARENT + " has been called, but there is no parent");
            return -10000;
        }
        return profileParent.id;
    }

    private static void sanitizeIntent(android.content.Intent intent) {
        intent.setPackage(null);
        intent.setComponent(null);
    }

    protected com.android.internal.logging.MetricsLogger getMetricsLogger() {
        if (this.mMetricsLogger == null) {
            this.mMetricsLogger = new com.android.internal.logging.MetricsLogger();
        }
        return this.mMetricsLogger;
    }

    protected com.android.internal.app.IntentForwarderActivity.Injector createInjector() {
        return new com.android.internal.app.IntentForwarderActivity.InjectorImpl();
    }

    /* JADX INFO: Access modifiers changed from: private */
    class InjectorImpl implements com.android.internal.app.IntentForwarderActivity.Injector {
        private InjectorImpl() {
        }

        @Override // com.android.internal.app.IntentForwarderActivity.Injector
        public android.content.pm.IPackageManager getIPackageManager() {
            return android.app.AppGlobals.getPackageManager();
        }

        @Override // com.android.internal.app.IntentForwarderActivity.Injector
        public android.os.UserManager getUserManager() {
            return (android.os.UserManager) com.android.internal.app.IntentForwarderActivity.this.getSystemService(android.os.UserManager.class);
        }

        @Override // com.android.internal.app.IntentForwarderActivity.Injector
        public android.content.pm.PackageManager getPackageManager() {
            return com.android.internal.app.IntentForwarderActivity.this.getPackageManager();
        }

        @Override // com.android.internal.app.IntentForwarderActivity.Injector
        public java.util.concurrent.CompletableFuture<android.content.pm.ResolveInfo> resolveActivityAsUser(final android.content.Intent intent, final int i, final int i2) {
            return java.util.concurrent.CompletableFuture.supplyAsync(new java.util.function.Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$InjectorImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.content.pm.ResolveInfo lambda$resolveActivityAsUser$0;
                    lambda$resolveActivityAsUser$0 = com.android.internal.app.IntentForwarderActivity.InjectorImpl.this.lambda$resolveActivityAsUser$0(intent, i, i2);
                    return lambda$resolveActivityAsUser$0;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ android.content.pm.ResolveInfo lambda$resolveActivityAsUser$0(android.content.Intent intent, int i, int i2) {
            return getPackageManager().resolveActivityAsUser(intent, i, i2);
        }

        @Override // com.android.internal.app.IntentForwarderActivity.Injector
        public void showToast(java.lang.String str, int i) {
            android.widget.Toast.makeText(com.android.internal.app.IntentForwarderActivity.this, str, i).show();
        }
    }
}
