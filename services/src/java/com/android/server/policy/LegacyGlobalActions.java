package com.android.server.policy;

/* loaded from: classes2.dex */
class LegacyGlobalActions implements android.content.DialogInterface.OnDismissListener, android.content.DialogInterface.OnClickListener {
    private static final int DIALOG_DISMISS_DELAY = 300;
    private static final java.lang.String GLOBAL_ACTION_KEY_AIRPLANE = "airplane";
    private static final java.lang.String GLOBAL_ACTION_KEY_ASSIST = "assist";
    private static final java.lang.String GLOBAL_ACTION_KEY_BUGREPORT = "bugreport";
    private static final java.lang.String GLOBAL_ACTION_KEY_LOCKDOWN = "lockdown";
    private static final java.lang.String GLOBAL_ACTION_KEY_POWER = "power";
    private static final java.lang.String GLOBAL_ACTION_KEY_RESTART = "restart";
    private static final java.lang.String GLOBAL_ACTION_KEY_SETTINGS = "settings";
    private static final java.lang.String GLOBAL_ACTION_KEY_SILENT = "silent";
    private static final java.lang.String GLOBAL_ACTION_KEY_USERS = "users";
    private static final java.lang.String GLOBAL_ACTION_KEY_VOICEASSIST = "voiceassist";
    private static final int MESSAGE_DISMISS = 0;
    private static final int MESSAGE_REFRESH = 1;
    private static final int MESSAGE_SHOW = 2;
    private static final boolean SHOW_SILENT_TOGGLE = true;
    private static final java.lang.String TAG = "LegacyGlobalActions";
    private com.android.internal.globalactions.ActionsAdapter mAdapter;
    private com.android.internal.globalactions.ToggleAction mAirplaneModeOn;
    private final android.media.AudioManager mAudioManager;
    private final android.content.Context mContext;
    private com.android.internal.globalactions.ActionsDialog mDialog;
    private final com.android.internal.util.EmergencyAffordanceManager mEmergencyAffordanceManager;
    private final boolean mHasTelephony;
    private boolean mHasVibrator;
    private java.util.ArrayList<com.android.internal.globalactions.Action> mItems;
    private final java.lang.Runnable mOnDismiss;
    private final boolean mShowSilentToggle;
    private com.android.internal.globalactions.Action mSilentModeAction;
    private final com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs mWindowManagerFuncs;
    private boolean mKeyguardShowing = false;
    private boolean mDeviceProvisioned = false;
    private com.android.internal.globalactions.ToggleAction.State mAirplaneState = com.android.internal.globalactions.ToggleAction.State.Off;
    private boolean mIsWaitingForEcmExit = false;
    private android.content.BroadcastReceiver mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.policy.LegacyGlobalActions.9
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            java.lang.String action = intent.getAction();
            if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(action) || "android.intent.action.SCREEN_OFF".equals(action)) {
                if (!com.android.server.policy.PhoneWindowManager.SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS.equals(intent.getStringExtra(com.android.server.policy.PhoneWindowManager.SYSTEM_DIALOG_REASON_KEY))) {
                    com.android.server.policy.LegacyGlobalActions.this.mHandler.sendEmptyMessage(0);
                }
            } else if ("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED".equals(action) && !intent.getBooleanExtra("android.telephony.extra.PHONE_IN_ECM_STATE", false) && com.android.server.policy.LegacyGlobalActions.this.mIsWaitingForEcmExit) {
                com.android.server.policy.LegacyGlobalActions.this.mIsWaitingForEcmExit = false;
                com.android.server.policy.LegacyGlobalActions.this.changeAirplaneModeSystemSetting(true);
            }
        }
    };
    android.telephony.PhoneStateListener mPhoneStateListener = new android.telephony.PhoneStateListener() { // from class: com.android.server.policy.LegacyGlobalActions.10
        @Override // android.telephony.PhoneStateListener
        public void onServiceStateChanged(android.telephony.ServiceState serviceState) {
            if (com.android.server.policy.LegacyGlobalActions.this.mHasTelephony) {
                boolean z = serviceState.getState() == 3;
                com.android.server.policy.LegacyGlobalActions.this.mAirplaneState = z ? com.android.internal.globalactions.ToggleAction.State.On : com.android.internal.globalactions.ToggleAction.State.Off;
                com.android.server.policy.LegacyGlobalActions.this.mAirplaneModeOn.updateState(com.android.server.policy.LegacyGlobalActions.this.mAirplaneState);
                com.android.server.policy.LegacyGlobalActions.this.mAdapter.notifyDataSetChanged();
            }
        }
    };
    private android.content.BroadcastReceiver mRingerModeReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.policy.LegacyGlobalActions.11
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (intent.getAction().equals("android.media.RINGER_MODE_CHANGED")) {
                com.android.server.policy.LegacyGlobalActions.this.mHandler.sendEmptyMessage(1);
            }
        }
    };
    private android.database.ContentObserver mAirplaneModeObserver = new android.database.ContentObserver(new android.os.Handler()) { // from class: com.android.server.policy.LegacyGlobalActions.12
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            com.android.server.policy.LegacyGlobalActions.this.onAirplaneModeChanged();
        }
    };
    private android.os.Handler mHandler = new android.os.Handler() { // from class: com.android.server.policy.LegacyGlobalActions.13
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    if (com.android.server.policy.LegacyGlobalActions.this.mDialog != null) {
                        com.android.server.policy.LegacyGlobalActions.this.mDialog.dismiss();
                        com.android.server.policy.LegacyGlobalActions.this.mDialog = null;
                        break;
                    }
                    break;
                case 1:
                    com.android.server.policy.LegacyGlobalActions.this.refreshSilentMode();
                    com.android.server.policy.LegacyGlobalActions.this.mAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    com.android.server.policy.LegacyGlobalActions.this.handleShow();
                    break;
            }
        }
    };
    private final android.service.dreams.IDreamManager mDreamManager = android.service.dreams.IDreamManager.Stub.asInterface(android.os.ServiceManager.getService("dreams"));

    public LegacyGlobalActions(android.content.Context context, com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs windowManagerFuncs, java.lang.Runnable runnable) {
        boolean z = false;
        this.mContext = context;
        this.mWindowManagerFuncs = windowManagerFuncs;
        this.mOnDismiss = runnable;
        this.mAudioManager = (android.media.AudioManager) this.mContext.getSystemService("audio");
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED");
        context.registerReceiverAsUser(this.mBroadcastReceiver, android.os.UserHandle.ALL, intentFilter, null, null, 2);
        this.mHasTelephony = context.getPackageManager().hasSystemFeature("android.hardware.telephony");
        ((android.telephony.TelephonyManager) context.getSystemService(com.android.server.autofill.HintsHelper.AUTOFILL_HINT_PHONE)).listen(this.mPhoneStateListener, 1);
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("airplane_mode_on"), true, this.mAirplaneModeObserver);
        android.os.Vibrator vibrator = (android.os.Vibrator) this.mContext.getSystemService("vibrator");
        if (vibrator != null && vibrator.hasVibrator()) {
            z = true;
        }
        this.mHasVibrator = z;
        this.mShowSilentToggle = !this.mContext.getResources().getBoolean(android.R.bool.config_useAutoSuspend);
        this.mEmergencyAffordanceManager = new com.android.internal.util.EmergencyAffordanceManager(context);
    }

    public void showDialog(boolean z, boolean z2) {
        this.mKeyguardShowing = z;
        this.mDeviceProvisioned = z2;
        if (this.mDialog != null) {
            this.mDialog.dismiss();
            this.mDialog = null;
            this.mHandler.sendEmptyMessage(2);
            return;
        }
        handleShow();
    }

    private void awakenIfNecessary() {
        if (this.mDreamManager != null) {
            try {
                if (this.mDreamManager.isDreaming()) {
                    this.mDreamManager.awaken();
                }
            } catch (android.os.RemoteException e) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleShow() {
        awakenIfNecessary();
        this.mDialog = createDialog();
        prepareDialog();
        if (this.mAdapter.getCount() == 1 && (this.mAdapter.getItem(0) instanceof com.android.internal.globalactions.SinglePressAction) && !(this.mAdapter.getItem(0) instanceof com.android.internal.globalactions.LongPressAction)) {
            this.mAdapter.getItem(0).onPress();
            return;
        }
        if (this.mDialog != null) {
            android.view.WindowManager.LayoutParams attributes = this.mDialog.getWindow().getAttributes();
            attributes.setTitle(TAG);
            this.mDialog.getWindow().setAttributes(attributes);
            this.mDialog.show();
            this.mDialog.getWindow().getDecorView().setSystemUiVisibility(65536);
        }
    }

    private com.android.internal.globalactions.ActionsDialog createDialog() {
        if (!this.mHasVibrator) {
            this.mSilentModeAction = new com.android.server.policy.LegacyGlobalActions.SilentModeToggleAction();
        } else {
            this.mSilentModeAction = new com.android.server.policy.LegacyGlobalActions.SilentModeTriStateAction(this.mContext, this.mAudioManager, this.mHandler);
        }
        this.mAirplaneModeOn = new com.android.internal.globalactions.ToggleAction(android.R.drawable.ic_launcher_android, android.R.drawable.ic_lock_airplane_mode, android.R.string.fp_power_button_bp_positive_button, android.R.string.fp_power_button_bp_negative_button, android.R.string.fp_power_button_bp_message) { // from class: com.android.server.policy.LegacyGlobalActions.1
            public void onToggle(boolean z) {
                if (com.android.server.policy.LegacyGlobalActions.this.mHasTelephony && ((java.lang.Boolean) android.sysprop.TelephonyProperties.in_ecm_mode().orElse(false)).booleanValue()) {
                    com.android.server.policy.LegacyGlobalActions.this.mIsWaitingForEcmExit = true;
                    android.content.Intent intent = new android.content.Intent("android.telephony.action.SHOW_NOTICE_ECM_BLOCK_OTHERS", (android.net.Uri) null);
                    intent.addFlags(268435456);
                    com.android.server.policy.LegacyGlobalActions.this.mContext.startActivity(intent);
                    return;
                }
                com.android.server.policy.LegacyGlobalActions.this.changeAirplaneModeSystemSetting(z);
            }

            protected void changeStateFromPress(boolean z) {
                if (com.android.server.policy.LegacyGlobalActions.this.mHasTelephony && !((java.lang.Boolean) android.sysprop.TelephonyProperties.in_ecm_mode().orElse(false)).booleanValue()) {
                    ((com.android.internal.globalactions.ToggleAction) this).mState = z ? com.android.internal.globalactions.ToggleAction.State.TurningOn : com.android.internal.globalactions.ToggleAction.State.TurningOff;
                    com.android.server.policy.LegacyGlobalActions.this.mAirplaneState = ((com.android.internal.globalactions.ToggleAction) this).mState;
                }
            }

            public boolean showDuringKeyguard() {
                return true;
            }

            public boolean showBeforeProvisioning() {
                return false;
            }
        };
        onAirplaneModeChanged();
        this.mItems = new java.util.ArrayList<>();
        java.lang.String[] stringArray = this.mContext.getResources().getStringArray(android.R.array.config_forceQueryablePackages);
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (java.lang.String str : stringArray) {
            if (!arraySet.contains(str)) {
                if (GLOBAL_ACTION_KEY_POWER.equals(str)) {
                    this.mItems.add(new com.android.server.policy.PowerAction(this.mContext, this.mWindowManagerFuncs));
                } else if (GLOBAL_ACTION_KEY_AIRPLANE.equals(str)) {
                    this.mItems.add(this.mAirplaneModeOn);
                } else if (GLOBAL_ACTION_KEY_BUGREPORT.equals(str)) {
                    if (android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "bugreport_in_power_menu", 0, this.mContext.getUserId()) != 0 && isCurrentUserAdmin()) {
                        this.mItems.add(new com.android.server.policy.LegacyGlobalActions.BugReportAction());
                    }
                } else if (GLOBAL_ACTION_KEY_SILENT.equals(str)) {
                    if (this.mShowSilentToggle) {
                        this.mItems.add(this.mSilentModeAction);
                    }
                } else if ("users".equals(str)) {
                    if (android.os.SystemProperties.getBoolean("fw.power_user_switcher", false)) {
                        addUsersToMenu(this.mItems);
                    }
                } else if (GLOBAL_ACTION_KEY_SETTINGS.equals(str)) {
                    this.mItems.add(getSettingsAction());
                } else if (GLOBAL_ACTION_KEY_LOCKDOWN.equals(str)) {
                    this.mItems.add(getLockdownAction());
                } else if (GLOBAL_ACTION_KEY_VOICEASSIST.equals(str)) {
                    this.mItems.add(getVoiceAssistAction());
                } else if ("assist".equals(str)) {
                    this.mItems.add(getAssistAction());
                } else if ("restart".equals(str)) {
                    this.mItems.add(new com.android.server.policy.RestartAction(this.mContext, this.mWindowManagerFuncs));
                } else {
                    android.util.Log.e(TAG, "Invalid global action key " + str);
                }
                arraySet.add(str);
            }
        }
        if (this.mEmergencyAffordanceManager.needsEmergencyAffordance()) {
            this.mItems.add(getEmergencyAction());
        }
        this.mAdapter = new com.android.internal.globalactions.ActionsAdapter(this.mContext, this.mItems, new java.util.function.BooleanSupplier() { // from class: com.android.server.policy.LegacyGlobalActions$$ExternalSyntheticLambda0
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$createDialog$0;
                lambda$createDialog$0 = com.android.server.policy.LegacyGlobalActions.this.lambda$createDialog$0();
                return lambda$createDialog$0;
            }
        }, new java.util.function.BooleanSupplier() { // from class: com.android.server.policy.LegacyGlobalActions$$ExternalSyntheticLambda1
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$createDialog$1;
                lambda$createDialog$1 = com.android.server.policy.LegacyGlobalActions.this.lambda$createDialog$1();
                return lambda$createDialog$1;
            }
        });
        com.android.internal.app.AlertController.AlertParams alertParams = new com.android.internal.app.AlertController.AlertParams(this.mContext);
        alertParams.mAdapter = this.mAdapter;
        alertParams.mOnClickListener = this;
        alertParams.mForceInverseBackground = true;
        com.android.internal.globalactions.ActionsDialog actionsDialog = new com.android.internal.globalactions.ActionsDialog(this.mContext, alertParams);
        actionsDialog.setCanceledOnTouchOutside(false);
        actionsDialog.getListView().setItemsCanFocus(true);
        actionsDialog.getListView().setLongClickable(true);
        actionsDialog.getListView().setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener() { // from class: com.android.server.policy.LegacyGlobalActions.2
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public boolean onItemLongClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
                com.android.internal.globalactions.LongPressAction item = com.android.server.policy.LegacyGlobalActions.this.mAdapter.getItem(i);
                if (item instanceof com.android.internal.globalactions.LongPressAction) {
                    return item.onLongPress();
                }
                return false;
            }
        });
        actionsDialog.getWindow().setType(2009);
        actionsDialog.getWindow().setFlags(131072, 131072);
        actionsDialog.setOnDismissListener(this);
        return actionsDialog;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$createDialog$0() {
        return this.mDeviceProvisioned;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$createDialog$1() {
        return this.mKeyguardShowing;
    }

    private class BugReportAction extends com.android.internal.globalactions.SinglePressAction implements com.android.internal.globalactions.LongPressAction {
        public BugReportAction() {
            super(android.R.drawable.ic_lock_airplane_mode_off, android.R.string.bugreport_message);
        }

        public void onPress() {
            if (android.app.ActivityManager.isUserAMonkey()) {
                return;
            }
            com.android.server.policy.LegacyGlobalActions.this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.policy.LegacyGlobalActions.BugReportAction.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        com.android.internal.logging.MetricsLogger.action(com.android.server.policy.LegacyGlobalActions.this.mContext, 292);
                        android.app.ActivityManager.getService().requestInteractiveBugReport();
                    } catch (android.os.RemoteException e) {
                    }
                }
            }, 500L);
        }

        public boolean onLongPress() {
            if (android.app.ActivityManager.isUserAMonkey()) {
                return false;
            }
            try {
                com.android.internal.logging.MetricsLogger.action(com.android.server.policy.LegacyGlobalActions.this.mContext, 293);
                android.app.ActivityManager.getService().requestFullBugReport();
            } catch (android.os.RemoteException e) {
            }
            return false;
        }

        public boolean showDuringKeyguard() {
            return true;
        }

        public boolean showBeforeProvisioning() {
            return false;
        }

        public java.lang.String getStatus() {
            return com.android.server.policy.LegacyGlobalActions.this.mContext.getString(android.R.string.bugreport_countdown, android.os.Build.VERSION.RELEASE_OR_CODENAME, android.os.Build.ID);
        }
    }

    private com.android.internal.globalactions.Action getSettingsAction() {
        return new com.android.internal.globalactions.SinglePressAction(android.R.drawable.ic_search_api_holo_light, android.R.string.foreground_service_app_in_background) { // from class: com.android.server.policy.LegacyGlobalActions.3
            public void onPress() {
                android.content.Intent intent = new android.content.Intent("android.settings.SETTINGS");
                intent.addFlags(android.hardware.audio.common.V2_0.AudioFormat.AAC_ADIF);
                com.android.server.policy.LegacyGlobalActions.this.mContext.startActivity(intent);
            }

            public boolean showDuringKeyguard() {
                return true;
            }

            public boolean showBeforeProvisioning() {
                return true;
            }
        };
    }

    private com.android.internal.globalactions.Action getEmergencyAction() {
        return new com.android.internal.globalactions.SinglePressAction(android.R.drawable.editbox_dropdown_background_dark, android.R.string.font_family_display_2_material) { // from class: com.android.server.policy.LegacyGlobalActions.4
            public void onPress() {
                com.android.server.policy.LegacyGlobalActions.this.mEmergencyAffordanceManager.performEmergencyCall();
            }

            public boolean showDuringKeyguard() {
                return true;
            }

            public boolean showBeforeProvisioning() {
                return true;
            }
        };
    }

    private com.android.internal.globalactions.Action getAssistAction() {
        return new com.android.internal.globalactions.SinglePressAction(android.R.drawable.ic_account_circle, android.R.string.font_family_caption_material) { // from class: com.android.server.policy.LegacyGlobalActions.5
            public void onPress() {
                android.content.Intent intent = new android.content.Intent("android.intent.action.ASSIST");
                intent.addFlags(android.hardware.audio.common.V2_0.AudioFormat.AAC_ADIF);
                com.android.server.policy.LegacyGlobalActions.this.mContext.startActivity(intent);
            }

            public boolean showDuringKeyguard() {
                return true;
            }

            public boolean showBeforeProvisioning() {
                return true;
            }
        };
    }

    private com.android.internal.globalactions.Action getVoiceAssistAction() {
        return new com.android.internal.globalactions.SinglePressAction(android.R.drawable.ic_vibrate, android.R.string.forward_intent_to_owner) { // from class: com.android.server.policy.LegacyGlobalActions.6
            public void onPress() {
                android.content.Intent intent = new android.content.Intent("android.intent.action.VOICE_ASSIST");
                intent.addFlags(android.hardware.audio.common.V2_0.AudioFormat.AAC_ADIF);
                com.android.server.policy.LegacyGlobalActions.this.mContext.startActivity(intent);
            }

            public boolean showDuringKeyguard() {
                return true;
            }

            public boolean showBeforeProvisioning() {
                return true;
            }
        };
    }

    private com.android.internal.globalactions.Action getLockdownAction() {
        return new com.android.internal.globalactions.SinglePressAction(android.R.drawable.ic_lock_lock, android.R.string.font_family_display_4_material) { // from class: com.android.server.policy.LegacyGlobalActions.7
            public void onPress() {
                new com.android.internal.widget.LockPatternUtils(com.android.server.policy.LegacyGlobalActions.this.mContext).requireCredentialEntry(-1);
                try {
                    android.view.WindowManagerGlobal.getWindowManagerService().lockNow((android.os.Bundle) null);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.server.policy.LegacyGlobalActions.TAG, "Error while trying to lock device.", e);
                }
            }

            public boolean showDuringKeyguard() {
                return true;
            }

            public boolean showBeforeProvisioning() {
                return false;
            }
        };
    }

    private android.content.pm.UserInfo getCurrentUser() {
        try {
            return android.app.ActivityManager.getService().getCurrentUser();
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    private boolean isCurrentUserAdmin() {
        android.content.pm.UserInfo currentUser = getCurrentUser();
        return currentUser != null && currentUser.isAdmin();
    }

    private void addUsersToMenu(java.util.ArrayList<com.android.internal.globalactions.Action> arrayList) {
        android.os.UserManager userManager = (android.os.UserManager) this.mContext.getSystemService("user");
        if (userManager.isUserSwitcherEnabled()) {
            java.util.List<android.content.pm.UserInfo> users = userManager.getUsers();
            android.content.pm.UserInfo currentUser = getCurrentUser();
            for (final android.content.pm.UserInfo userInfo : users) {
                if (userInfo.supportsSwitchToByUser()) {
                    boolean z = false;
                    if (currentUser != null ? currentUser.id == userInfo.id : userInfo.id == 0) {
                        z = true;
                    }
                    android.graphics.drawable.Drawable createFromPath = userInfo.iconPath != null ? android.graphics.drawable.Drawable.createFromPath(userInfo.iconPath) : null;
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append(userInfo.name != null ? userInfo.name : "Primary");
                    sb.append(z ? " ✔" : "");
                    arrayList.add(new com.android.internal.globalactions.SinglePressAction(android.R.drawable.ic_menu_blocked_user, createFromPath, sb.toString()) { // from class: com.android.server.policy.LegacyGlobalActions.8
                        public void onPress() {
                            try {
                                android.app.ActivityManager.getService().switchUser(userInfo.id);
                            } catch (android.os.RemoteException e) {
                                android.util.Log.e(com.android.server.policy.LegacyGlobalActions.TAG, "Couldn't switch user " + e);
                            }
                        }

                        public boolean showDuringKeyguard() {
                            return true;
                        }

                        public boolean showBeforeProvisioning() {
                            return false;
                        }
                    });
                }
            }
        }
    }

    private void prepareDialog() {
        refreshSilentMode();
        this.mAirplaneModeOn.updateState(this.mAirplaneState);
        this.mAdapter.notifyDataSetChanged();
        this.mDialog.getWindow().setType(2009);
        if (this.mShowSilentToggle) {
            this.mContext.registerReceiver(this.mRingerModeReceiver, new android.content.IntentFilter("android.media.RINGER_MODE_CHANGED"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshSilentMode() {
        if (!this.mHasVibrator) {
            this.mSilentModeAction.updateState(this.mAudioManager.getRingerMode() != 2 ? com.android.internal.globalactions.ToggleAction.State.On : com.android.internal.globalactions.ToggleAction.State.Off);
        }
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(android.content.DialogInterface dialogInterface) {
        if (this.mOnDismiss != null) {
            this.mOnDismiss.run();
        }
        if (this.mShowSilentToggle) {
            try {
                this.mContext.unregisterReceiver(this.mRingerModeReceiver);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Log.w(TAG, e);
            }
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(android.content.DialogInterface dialogInterface, int i) {
        if (!(this.mAdapter.getItem(i) instanceof com.android.server.policy.LegacyGlobalActions.SilentModeTriStateAction)) {
            dialogInterface.dismiss();
        }
        this.mAdapter.getItem(i).onPress();
    }

    private class SilentModeToggleAction extends com.android.internal.globalactions.ToggleAction {
        public SilentModeToggleAction() {
            super(android.R.drawable.ic_audio_ring_notif_vibrate, android.R.drawable.ic_audio_ring_notif_mute, android.R.string.foreground_service_tap_for_details, android.R.string.foreground_service_multiple_separator, android.R.string.foreground_service_apps_in_background);
        }

        public void onToggle(boolean z) {
            if (z) {
                com.android.server.policy.LegacyGlobalActions.this.mAudioManager.setRingerMode(0);
            } else {
                com.android.server.policy.LegacyGlobalActions.this.mAudioManager.setRingerMode(2);
            }
        }

        public boolean showDuringKeyguard() {
            return true;
        }

        public boolean showBeforeProvisioning() {
            return false;
        }
    }

    private static class SilentModeTriStateAction implements com.android.internal.globalactions.Action, android.view.View.OnClickListener {
        private final int[] ITEM_IDS = {android.R.id.open_cross_profile, android.R.id.opticalBounds, android.R.id.option1};
        private final android.media.AudioManager mAudioManager;
        private final android.content.Context mContext;
        private final android.os.Handler mHandler;

        SilentModeTriStateAction(android.content.Context context, android.media.AudioManager audioManager, android.os.Handler handler) {
            this.mAudioManager = audioManager;
            this.mHandler = handler;
            this.mContext = context;
        }

        private int ringerModeToIndex(int i) {
            return i;
        }

        private int indexToRingerMode(int i) {
            return i;
        }

        public java.lang.CharSequence getLabelForAccessibility(android.content.Context context) {
            return null;
        }

        public android.view.View create(android.content.Context context, android.view.View view, android.view.ViewGroup viewGroup, android.view.LayoutInflater layoutInflater) {
            android.view.View inflate = layoutInflater.inflate(android.R.layout.global_actions_silent_mode, viewGroup, false);
            int ringerModeToIndex = ringerModeToIndex(this.mAudioManager.getRingerMode());
            int i = 0;
            while (i < 3) {
                android.view.View findViewById = inflate.findViewById(this.ITEM_IDS[i]);
                findViewById.setSelected(ringerModeToIndex == i);
                findViewById.setTag(java.lang.Integer.valueOf(i));
                findViewById.setOnClickListener(this);
                i++;
            }
            return inflate;
        }

        public void onPress() {
        }

        public boolean showDuringKeyguard() {
            return true;
        }

        public boolean showBeforeProvisioning() {
            return false;
        }

        public boolean isEnabled() {
            return true;
        }

        void willCreate() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            if (view.getTag() instanceof java.lang.Integer) {
                this.mAudioManager.setRingerMode(indexToRingerMode(((java.lang.Integer) view.getTag()).intValue()));
                this.mHandler.sendEmptyMessageDelayed(0, 300L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAirplaneModeChanged() {
        if (this.mHasTelephony) {
            return;
        }
        this.mAirplaneState = android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0) == 1 ? com.android.internal.globalactions.ToggleAction.State.On : com.android.internal.globalactions.ToggleAction.State.Off;
        this.mAirplaneModeOn.updateState(this.mAirplaneState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeAirplaneModeSystemSetting(boolean z) {
        android.provider.Settings.Global.putInt(this.mContext.getContentResolver(), "airplane_mode_on", z ? 1 : 0);
        android.content.Intent intent = new android.content.Intent("android.intent.action.AIRPLANE_MODE");
        intent.addFlags(536870912);
        intent.putExtra("state", z);
        this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.ALL);
        if (!this.mHasTelephony) {
            this.mAirplaneState = z ? com.android.internal.globalactions.ToggleAction.State.On : com.android.internal.globalactions.ToggleAction.State.Off;
        }
    }
}
