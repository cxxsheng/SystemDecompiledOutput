package com.android.internal.accessibility;

/* loaded from: classes4.dex */
public class AccessibilityShortcutController {
    public static final java.lang.String MAGNIFICATION_CONTROLLER_NAME = "com.android.server.accessibility.MagnificationController";
    private static final java.lang.String TAG = "AccessibilityShortcutController";
    private static java.util.Map<android.content.ComponentName, com.android.internal.accessibility.AccessibilityShortcutController.FrameworkFeatureInfo> sFrameworkShortcutFeaturesMap;
    private android.app.AlertDialog mAlertDialog;
    private final android.content.Context mContext;
    private boolean mEnabledOnLockScreen;
    public com.android.internal.accessibility.AccessibilityShortcutController.FrameworkObjectProvider mFrameworkObjectProvider = new com.android.internal.accessibility.AccessibilityShortcutController.FrameworkObjectProvider();
    private final android.os.Handler mHandler;
    private boolean mIsShortcutEnabled;
    private int mUserId;
    private final com.android.internal.accessibility.AccessibilityShortcutController.UserSetupCompleteObserver mUserSetupCompleteObserver;
    public static final android.content.ComponentName COLOR_INVERSION_COMPONENT_NAME = new android.content.ComponentName("com.android.server.accessibility", "ColorInversion");
    public static final android.content.ComponentName DALTONIZER_COMPONENT_NAME = new android.content.ComponentName("com.android.server.accessibility", "Daltonizer");
    public static final android.content.ComponentName MAGNIFICATION_COMPONENT_NAME = new android.content.ComponentName("com.android.server.accessibility", "Magnification");
    public static final android.content.ComponentName ONE_HANDED_COMPONENT_NAME = new android.content.ComponentName("com.android.server.accessibility", "OneHandedMode");
    public static final android.content.ComponentName REDUCE_BRIGHT_COLORS_COMPONENT_NAME = new android.content.ComponentName("com.android.server.accessibility", "ReduceBrightColors");
    public static final android.content.ComponentName FONT_SIZE_COMPONENT_NAME = new android.content.ComponentName("com.android.server.accessibility", "FontSize");
    public static final android.content.ComponentName ACCESSIBILITY_BUTTON_COMPONENT_NAME = new android.content.ComponentName("com.android.server.accessibility", "AccessibilityButton");
    public static final android.content.ComponentName ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME = new android.content.ComponentName("com.android.server.accessibility", "HearingAids");
    public static final android.content.ComponentName COLOR_INVERSION_TILE_COMPONENT_NAME = new android.content.ComponentName("com.android.server.accessibility", "ColorInversionTile");
    public static final android.content.ComponentName DALTONIZER_TILE_COMPONENT_NAME = new android.content.ComponentName("com.android.server.accessibility", "ColorCorrectionTile");
    public static final android.content.ComponentName ONE_HANDED_TILE_COMPONENT_NAME = new android.content.ComponentName("com.android.server.accessibility", "OneHandedModeTile");
    public static final android.content.ComponentName REDUCE_BRIGHT_COLORS_TILE_SERVICE_COMPONENT_NAME = new android.content.ComponentName("com.android.server.accessibility", "ReduceBrightColorsTile");
    public static final android.content.ComponentName FONT_SIZE_TILE_COMPONENT_NAME = new android.content.ComponentName("com.android.server.accessibility", "FontSizeTile");
    private static final android.media.AudioAttributes VIBRATION_ATTRIBUTES = new android.media.AudioAttributes.Builder().setContentType(4).setUsage(11).build();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface DialogStatus {
        public static final int NOT_SHOWN = 0;
        public static final int SHOWN = 1;
    }

    public static java.util.Map<android.content.ComponentName, com.android.internal.accessibility.AccessibilityShortcutController.FrameworkFeatureInfo> getFrameworkShortcutFeaturesMap() {
        if (sFrameworkShortcutFeaturesMap == null) {
            android.util.ArrayMap arrayMap = new android.util.ArrayMap(4);
            arrayMap.put(COLOR_INVERSION_COMPONENT_NAME, new com.android.internal.accessibility.AccessibilityShortcutController.ToggleableFrameworkFeatureInfo(android.provider.Settings.Secure.ACCESSIBILITY_DISPLAY_INVERSION_ENABLED, "1", android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS, com.android.internal.R.string.color_inversion_feature_name));
            arrayMap.put(DALTONIZER_COMPONENT_NAME, new com.android.internal.accessibility.AccessibilityShortcutController.ToggleableFrameworkFeatureInfo(android.provider.Settings.Secure.ACCESSIBILITY_DISPLAY_DALTONIZER_ENABLED, "1", android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS, com.android.internal.R.string.color_correction_feature_name));
            if (com.android.internal.os.RoSystemProperties.SUPPORT_ONE_HANDED_MODE) {
                arrayMap.put(ONE_HANDED_COMPONENT_NAME, new com.android.internal.accessibility.AccessibilityShortcutController.ToggleableFrameworkFeatureInfo(android.provider.Settings.Secure.ONE_HANDED_MODE_ACTIVATED, "1", android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS, com.android.internal.R.string.one_handed_mode_feature_name));
            }
            arrayMap.put(REDUCE_BRIGHT_COLORS_COMPONENT_NAME, new com.android.internal.accessibility.AccessibilityShortcutController.ToggleableFrameworkFeatureInfo(android.provider.Settings.Secure.REDUCE_BRIGHT_COLORS_ACTIVATED, "1", android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS, com.android.internal.R.string.reduce_bright_colors_feature_name));
            arrayMap.put(ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME, new com.android.internal.accessibility.AccessibilityShortcutController.LaunchableFrameworkFeatureInfo(com.android.internal.R.string.hearing_aids_feature_name));
            sFrameworkShortcutFeaturesMap = java.util.Collections.unmodifiableMap(arrayMap);
        }
        return sFrameworkShortcutFeaturesMap;
    }

    public AccessibilityShortcutController(android.content.Context context, android.os.Handler handler, int i) {
        this.mContext = context;
        this.mHandler = handler;
        this.mUserId = i;
        this.mUserSetupCompleteObserver = new com.android.internal.accessibility.AccessibilityShortcutController.UserSetupCompleteObserver(handler, i);
        android.database.ContentObserver contentObserver = new android.database.ContentObserver(handler) { // from class: com.android.internal.accessibility.AccessibilityShortcutController.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z, java.util.Collection<android.net.Uri> collection, int i2, int i3) {
                if (i3 == com.android.internal.accessibility.AccessibilityShortcutController.this.mUserId) {
                    com.android.internal.accessibility.AccessibilityShortcutController.this.onSettingsChanged();
                }
            }
        };
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor(android.provider.Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE), false, contentObserver, -1);
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor(android.provider.Settings.Secure.ACCESSIBILITY_SHORTCUT_ON_LOCK_SCREEN), false, contentObserver, -1);
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor(android.provider.Settings.Secure.ACCESSIBILITY_SHORTCUT_DIALOG_SHOWN), false, contentObserver, -1);
        setCurrentUser(this.mUserId);
    }

    public void setCurrentUser(int i) {
        this.mUserId = i;
        onSettingsChanged();
        this.mUserSetupCompleteObserver.onUserSwitched(i);
    }

    public boolean isAccessibilityShortcutAvailable(boolean z) {
        return this.mIsShortcutEnabled && (!z || this.mEnabledOnLockScreen);
    }

    public void onSettingsChanged() {
        boolean hasShortcutTarget = hasShortcutTarget();
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mEnabledOnLockScreen = android.provider.Settings.Secure.getIntForUser(contentResolver, android.provider.Settings.Secure.ACCESSIBILITY_SHORTCUT_ON_LOCK_SCREEN, android.provider.Settings.Secure.getIntForUser(contentResolver, android.provider.Settings.Secure.ACCESSIBILITY_SHORTCUT_DIALOG_SHOWN, 0, this.mUserId), this.mUserId) == 1;
        this.mIsShortcutEnabled = hasShortcutTarget;
    }

    public void performAccessibilityShortcut() {
        android.util.Slog.d(TAG, "Accessibility shortcut activated");
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        int currentUser = android.app.ActivityManager.getCurrentUser();
        android.os.Vibrator vibrator = (android.os.Vibrator) this.mContext.getSystemService(android.content.Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(com.android.internal.util.ArrayUtils.convertToLongArray(this.mContext.getResources().getIntArray(com.android.internal.R.array.config_longPressVibePattern)), -1, VIBRATION_ATTRIBUTES);
        }
        if (shouldShowDialog()) {
            this.mAlertDialog = createShortcutWarningDialog(currentUser);
            if (this.mAlertDialog == null) {
                return;
            }
            if (!performTtsPrompt(this.mAlertDialog)) {
                playNotificationTone();
            }
            android.view.Window window = this.mAlertDialog.getWindow();
            android.view.WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.type = 2009;
            window.setAttributes(attributes);
            this.mAlertDialog.show();
            android.provider.Settings.Secure.putIntForUser(contentResolver, android.provider.Settings.Secure.ACCESSIBILITY_SHORTCUT_DIALOG_SHOWN, 1, currentUser);
            return;
        }
        playNotificationTone();
        if (this.mAlertDialog != null) {
            this.mAlertDialog.dismiss();
            this.mAlertDialog = null;
        }
        showToast();
        this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext).performAccessibilityShortcut();
    }

    private boolean shouldShowDialog() {
        return !hasFeatureLeanback() && android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_SHORTCUT_DIALOG_SHOWN, 0, android.app.ActivityManager.getCurrentUser()) == 0;
    }

    private void showToast() {
        int i;
        android.accessibilityservice.AccessibilityServiceInfo infoForTargetService = getInfoForTargetService();
        if (infoForTargetService == null) {
            return;
        }
        java.lang.String shortcutFeatureDescription = getShortcutFeatureDescription(false);
        if (shortcutFeatureDescription == null) {
            return;
        }
        boolean z = (infoForTargetService.flags & 256) != 0;
        boolean isServiceEnabled = isServiceEnabled(infoForTargetService);
        if (infoForTargetService.getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion > 29 && z && isServiceEnabled) {
            return;
        }
        android.content.Context context = this.mContext;
        if (isServiceEnabled) {
            i = com.android.internal.R.string.accessibility_shortcut_disabling_service;
        } else {
            i = com.android.internal.R.string.accessibility_shortcut_enabling_service;
        }
        this.mFrameworkObjectProvider.makeToastFromText(this.mContext, java.lang.String.format(context.getString(i), shortcutFeatureDescription), 1).show();
    }

    private android.app.AlertDialog createShortcutWarningDialog(final int i) {
        java.util.List<com.android.internal.accessibility.dialog.AccessibilityTarget> targets = com.android.internal.accessibility.dialog.AccessibilityTargetHelper.getTargets(this.mContext, 1);
        if (targets.size() == 0) {
            return null;
        }
        return this.mFrameworkObjectProvider.getAlertDialogBuilder(this.mFrameworkObjectProvider.getSystemUiContext()).setTitle(getShortcutWarningTitle(targets)).setMessage(getShortcutWarningMessage(targets)).setCancelable(false).setNegativeButton(com.android.internal.R.string.accessibility_shortcut_on, new android.content.DialogInterface.OnClickListener() { // from class: com.android.internal.accessibility.AccessibilityShortcutController$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(android.content.DialogInterface dialogInterface, int i2) {
                com.android.internal.accessibility.AccessibilityShortcutController.this.lambda$createShortcutWarningDialog$0(i, dialogInterface, i2);
            }
        }).setPositiveButton(com.android.internal.R.string.accessibility_shortcut_off, new android.content.DialogInterface.OnClickListener() { // from class: com.android.internal.accessibility.AccessibilityShortcutController$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(android.content.DialogInterface dialogInterface, int i2) {
                com.android.internal.accessibility.AccessibilityShortcutController.this.lambda$createShortcutWarningDialog$1(i, dialogInterface, i2);
            }
        }).setOnCancelListener(new android.content.DialogInterface.OnCancelListener() { // from class: com.android.internal.accessibility.AccessibilityShortcutController$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(android.content.DialogInterface dialogInterface) {
                com.android.internal.accessibility.AccessibilityShortcutController.this.lambda$createShortcutWarningDialog$2(i, dialogInterface);
            }
        }).create();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createShortcutWarningDialog$0(int i, android.content.DialogInterface dialogInterface, int i2) {
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(this.mContext.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE, i);
        java.lang.String string = this.mContext.getString(com.android.internal.R.string.config_defaultAccessibilityService);
        if (stringForUser == null && !android.text.TextUtils.isEmpty(string)) {
            android.provider.Settings.Secure.putStringForUser(this.mContext.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE, android.content.ComponentName.unflattenFromString(string).flattenToString(), i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createShortcutWarningDialog$1(int i, android.content.DialogInterface dialogInterface, int i2) {
        if (android.view.accessibility.Flags.updateAlwaysOnA11yService()) {
            java.util.Set<java.lang.String> shortcutTargetsFromSettings = com.android.internal.accessibility.util.ShortcutUtils.getShortcutTargetsFromSettings(this.mContext, 2, i);
            android.provider.Settings.Secure.putStringForUser(this.mContext.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE, "", i);
            com.android.internal.accessibility.util.ShortcutUtils.updateInvisibleToggleAccessibilityServiceEnableState(this.mContext, shortcutTargetsFromSettings, i);
        } else {
            android.provider.Settings.Secure.putStringForUser(this.mContext.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE, "", i);
        }
        android.provider.Settings.Secure.putIntForUser(this.mContext.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_SHORTCUT_DIALOG_SHOWN, 0, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createShortcutWarningDialog$2(int i, android.content.DialogInterface dialogInterface) {
        android.provider.Settings.Secure.putIntForUser(this.mContext.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_SHORTCUT_DIALOG_SHOWN, 0, i);
    }

    private java.lang.String getShortcutWarningTitle(java.util.List<com.android.internal.accessibility.dialog.AccessibilityTarget> list) {
        if (list.size() == 1) {
            return this.mContext.getString(com.android.internal.R.string.accessibility_shortcut_single_service_warning_title, list.get(0).getLabel());
        }
        return this.mContext.getString(com.android.internal.R.string.accessibility_shortcut_multiple_service_warning_title);
    }

    private java.lang.String getShortcutWarningMessage(java.util.List<com.android.internal.accessibility.dialog.AccessibilityTarget> list) {
        if (list.size() == 1) {
            return this.mContext.getString(com.android.internal.R.string.accessibility_shortcut_single_service_warning, list.get(0).getLabel());
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        java.util.Iterator<com.android.internal.accessibility.dialog.AccessibilityTarget> it = list.iterator();
        while (it.hasNext()) {
            sb.append(this.mContext.getString(com.android.internal.R.string.accessibility_shortcut_multiple_service_list, it.next().getLabel()));
        }
        return this.mContext.getString(com.android.internal.R.string.accessibility_shortcut_multiple_service_warning, sb.toString());
    }

    private android.accessibilityservice.AccessibilityServiceInfo getInfoForTargetService() {
        android.content.ComponentName shortcutTargetComponentName = getShortcutTargetComponentName();
        if (shortcutTargetComponentName == null) {
            return null;
        }
        return this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext).getInstalledServiceInfoWithComponentName(shortcutTargetComponentName);
    }

    private java.lang.String getShortcutFeatureDescription(boolean z) {
        android.content.ComponentName shortcutTargetComponentName = getShortcutTargetComponentName();
        if (shortcutTargetComponentName == null) {
            return null;
        }
        com.android.internal.accessibility.AccessibilityShortcutController.FrameworkFeatureInfo frameworkFeatureInfo = getFrameworkShortcutFeaturesMap().get(shortcutTargetComponentName);
        if (frameworkFeatureInfo != null) {
            return frameworkFeatureInfo.getLabel(this.mContext);
        }
        android.accessibilityservice.AccessibilityServiceInfo installedServiceInfoWithComponentName = this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext).getInstalledServiceInfoWithComponentName(shortcutTargetComponentName);
        if (installedServiceInfoWithComponentName == null) {
            return null;
        }
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        java.lang.String charSequence = installedServiceInfoWithComponentName.getResolveInfo().loadLabel(packageManager).toString();
        java.lang.CharSequence loadSummary = installedServiceInfoWithComponentName.loadSummary(packageManager);
        if (!z || android.text.TextUtils.isEmpty(loadSummary)) {
            return charSequence;
        }
        return java.lang.String.format("%s\n%s", charSequence, loadSummary);
    }

    private boolean isServiceEnabled(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        return this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext).getEnabledAccessibilityServiceList(-1).contains(accessibilityServiceInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasFeatureLeanback() {
        return this.mContext.getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_LEANBACK);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playNotificationTone() {
        int i;
        if (hasFeatureLeanback()) {
            i = 11;
        } else {
            i = 10;
        }
        android.media.Ringtone ringtone = this.mFrameworkObjectProvider.getRingtone(this.mContext, android.net.Uri.parse("file://" + this.mContext.getString(com.android.internal.R.string.config_defaultAccessibilityNotificationSound)));
        if (ringtone == null) {
            ringtone = this.mFrameworkObjectProvider.getRingtone(this.mContext, android.provider.Settings.System.DEFAULT_NOTIFICATION_URI);
        }
        if (ringtone != null) {
            ringtone.setAudioAttributes(new android.media.AudioAttributes.Builder().setUsage(i).build());
            ringtone.play();
        }
    }

    private boolean performTtsPrompt(android.app.AlertDialog alertDialog) {
        java.lang.String shortcutFeatureDescription = getShortcutFeatureDescription(false);
        android.accessibilityservice.AccessibilityServiceInfo infoForTargetService = getInfoForTargetService();
        if (android.text.TextUtils.isEmpty(shortcutFeatureDescription) || infoForTargetService == null || (infoForTargetService.flags & 1024) == 0) {
            return false;
        }
        final com.android.internal.accessibility.AccessibilityShortcutController.TtsPrompt ttsPrompt = new com.android.internal.accessibility.AccessibilityShortcutController.TtsPrompt(shortcutFeatureDescription);
        alertDialog.setOnDismissListener(new android.content.DialogInterface.OnDismissListener() { // from class: com.android.internal.accessibility.AccessibilityShortcutController$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(android.content.DialogInterface dialogInterface) {
                com.android.internal.accessibility.AccessibilityShortcutController.TtsPrompt.this.dismiss();
            }
        });
        return true;
    }

    private boolean hasShortcutTarget() {
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(this.mContext.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE, this.mUserId);
        if (stringForUser == null) {
            stringForUser = this.mContext.getString(com.android.internal.R.string.config_defaultAccessibilityService);
        }
        return !android.text.TextUtils.isEmpty(stringForUser);
    }

    private android.content.ComponentName getShortcutTargetComponentName() {
        java.util.List<java.lang.String> accessibilityShortcutTargets = this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext).getAccessibilityShortcutTargets(1);
        if (accessibilityShortcutTargets.size() != 1) {
            return null;
        }
        return android.content.ComponentName.unflattenFromString(accessibilityShortcutTargets.get(0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    class TtsPrompt implements android.speech.tts.TextToSpeech.OnInitListener {
        private static final int RETRY_MILLIS = 1000;
        private boolean mDismiss;
        private final java.lang.CharSequence mText;
        private android.speech.tts.TextToSpeech mTts;
        private int mRetryCount = 3;
        private boolean mLanguageReady = false;

        TtsPrompt(java.lang.String str) {
            this.mText = com.android.internal.accessibility.AccessibilityShortcutController.this.mContext.getString(com.android.internal.R.string.accessibility_shortcut_spoken_feedback, str);
            this.mTts = com.android.internal.accessibility.AccessibilityShortcutController.this.mFrameworkObjectProvider.getTextToSpeech(com.android.internal.accessibility.AccessibilityShortcutController.this.mContext, this);
        }

        public void dismiss() {
            this.mDismiss = true;
            com.android.internal.accessibility.AccessibilityShortcutController.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.internal.accessibility.AccessibilityShortcutController$TtsPrompt$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.speech.tts.TextToSpeech) obj).shutdown();
                }
            }, this.mTts));
        }

        @Override // android.speech.tts.TextToSpeech.OnInitListener
        public void onInit(int i) {
            if (i != 0) {
                android.util.Slog.d(com.android.internal.accessibility.AccessibilityShortcutController.TAG, "Tts init fail, status=" + java.lang.Integer.toString(i));
                com.android.internal.accessibility.AccessibilityShortcutController.this.playNotificationTone();
            } else {
                com.android.internal.accessibility.AccessibilityShortcutController.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.accessibility.AccessibilityShortcutController$TtsPrompt$$ExternalSyntheticLambda1(), this));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void play() {
            if (!this.mDismiss && this.mTts.speak(this.mText, 0, null, null) != 0) {
                android.util.Slog.d(com.android.internal.accessibility.AccessibilityShortcutController.TAG, "Tts play fail");
                com.android.internal.accessibility.AccessibilityShortcutController.this.playNotificationTone();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void waitForTtsReady() {
            if (this.mDismiss) {
                return;
            }
            boolean z = false;
            if (!this.mLanguageReady) {
                int language = this.mTts.setLanguage(java.util.Locale.getDefault());
                this.mLanguageReady = (language == -1 || language == -2) ? false : true;
            }
            if (this.mLanguageReady) {
                android.speech.tts.Voice voice = this.mTts.getVoice();
                if (voice != null && voice.getFeatures() != null && !voice.getFeatures().contains(android.speech.tts.TextToSpeech.Engine.KEY_FEATURE_NOT_INSTALLED)) {
                    z = true;
                }
                if (z) {
                    com.android.internal.accessibility.AccessibilityShortcutController.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.internal.accessibility.AccessibilityShortcutController$TtsPrompt$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            ((com.android.internal.accessibility.AccessibilityShortcutController.TtsPrompt) obj).play();
                        }
                    }, this));
                    return;
                }
            }
            if (this.mRetryCount == 0) {
                android.util.Slog.d(com.android.internal.accessibility.AccessibilityShortcutController.TAG, "Tts not ready to speak.");
                com.android.internal.accessibility.AccessibilityShortcutController.this.playNotificationTone();
            } else {
                this.mRetryCount--;
                com.android.internal.accessibility.AccessibilityShortcutController.this.mHandler.sendMessageDelayed(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.accessibility.AccessibilityShortcutController$TtsPrompt$$ExternalSyntheticLambda1(), this), 1000L);
            }
        }
    }

    private class UserSetupCompleteObserver extends android.database.ContentObserver {
        private boolean mIsRegistered;
        private int mUserId;

        UserSetupCompleteObserver(android.os.Handler handler, int i) {
            super(handler);
            this.mIsRegistered = false;
            this.mUserId = i;
            if (!isUserSetupComplete()) {
                registerObserver();
            }
        }

        private boolean isUserSetupComplete() {
            return android.provider.Settings.Secure.getIntForUser(com.android.internal.accessibility.AccessibilityShortcutController.this.mContext.getContentResolver(), android.provider.Settings.Secure.USER_SETUP_COMPLETE, 0, this.mUserId) == 1;
        }

        private void registerObserver() {
            if (this.mIsRegistered) {
                return;
            }
            com.android.internal.accessibility.AccessibilityShortcutController.this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor(android.provider.Settings.Secure.USER_SETUP_COMPLETE), false, this, this.mUserId);
            this.mIsRegistered = true;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            if (isUserSetupComplete()) {
                unregisterObserver();
                setEmptyShortcutTargetIfNeeded();
            }
        }

        private void unregisterObserver() {
            if (!this.mIsRegistered) {
                return;
            }
            com.android.internal.accessibility.AccessibilityShortcutController.this.mContext.getContentResolver().unregisterContentObserver(this);
            this.mIsRegistered = false;
        }

        private void setEmptyShortcutTargetIfNeeded() {
            if (com.android.internal.accessibility.AccessibilityShortcutController.this.hasFeatureLeanback()) {
                return;
            }
            android.content.ContentResolver contentResolver = com.android.internal.accessibility.AccessibilityShortcutController.this.mContext.getContentResolver();
            if (android.provider.Settings.Secure.getStringForUser(contentResolver, android.provider.Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE, this.mUserId) != null) {
                return;
            }
            java.lang.String string = com.android.internal.accessibility.AccessibilityShortcutController.this.mContext.getString(com.android.internal.R.string.config_defaultAccessibilityService);
            java.util.List<android.accessibilityservice.AccessibilityServiceInfo> enabledAccessibilityServiceList = com.android.internal.accessibility.AccessibilityShortcutController.this.mFrameworkObjectProvider.getAccessibilityManagerInstance(com.android.internal.accessibility.AccessibilityShortcutController.this.mContext).getEnabledAccessibilityServiceList(-1);
            for (int size = enabledAccessibilityServiceList.size() - 1; size >= 0; size--) {
                if (android.text.TextUtils.equals(string, enabledAccessibilityServiceList.get(size).getId())) {
                    return;
                }
            }
            android.provider.Settings.Secure.putStringForUser(contentResolver, android.provider.Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE, "", this.mUserId);
        }

        void onUserSwitched(int i) {
            if (this.mUserId == i) {
                return;
            }
            unregisterObserver();
            this.mUserId = i;
            if (!isUserSetupComplete()) {
                registerObserver();
            }
        }
    }

    public static abstract class FrameworkFeatureInfo {
        private final int mLabelStringResourceId;
        private final java.lang.String mSettingKey;
        private final java.lang.String mSettingOffValue;
        private final java.lang.String mSettingOnValue;

        FrameworkFeatureInfo(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
            this.mSettingKey = str;
            this.mSettingOnValue = str2;
            this.mSettingOffValue = str3;
            this.mLabelStringResourceId = i;
        }

        public java.lang.String getSettingKey() {
            return this.mSettingKey;
        }

        public java.lang.String getSettingOnValue() {
            return this.mSettingOnValue;
        }

        public java.lang.String getSettingOffValue() {
            return this.mSettingOffValue;
        }

        public java.lang.String getLabel(android.content.Context context) {
            return context.getString(this.mLabelStringResourceId);
        }
    }

    public static class ToggleableFrameworkFeatureInfo extends com.android.internal.accessibility.AccessibilityShortcutController.FrameworkFeatureInfo {
        ToggleableFrameworkFeatureInfo(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
            super(str, str2, str3, i);
        }
    }

    public static class LaunchableFrameworkFeatureInfo extends com.android.internal.accessibility.AccessibilityShortcutController.FrameworkFeatureInfo {
        LaunchableFrameworkFeatureInfo(int i) {
            super(null, null, null, i);
        }
    }

    public static class FrameworkObjectProvider {
        public android.view.accessibility.AccessibilityManager getAccessibilityManagerInstance(android.content.Context context) {
            return android.view.accessibility.AccessibilityManager.getInstance(context);
        }

        public android.app.AlertDialog.Builder getAlertDialogBuilder(android.content.Context context) {
            return new android.app.AlertDialog.Builder(context, (context.getResources().getConfiguration().uiMode & 48) == 32 ? 16974545 : 16974546);
        }

        public android.widget.Toast makeToastFromText(android.content.Context context, java.lang.CharSequence charSequence, int i) {
            return android.widget.Toast.makeText(context, charSequence, i);
        }

        public android.content.Context getSystemUiContext() {
            return android.app.ActivityThread.currentActivityThread().getSystemUiContext();
        }

        public android.speech.tts.TextToSpeech getTextToSpeech(android.content.Context context, android.speech.tts.TextToSpeech.OnInitListener onInitListener) {
            return new android.speech.tts.TextToSpeech(context, onInitListener);
        }

        public android.media.Ringtone getRingtone(android.content.Context context, android.net.Uri uri) {
            return android.media.RingtoneManager.getRingtone(context, uri);
        }
    }
}
