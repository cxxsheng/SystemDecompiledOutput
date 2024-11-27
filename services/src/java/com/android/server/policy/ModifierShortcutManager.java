package com.android.server.policy;

/* loaded from: classes2.dex */
public class ModifierShortcutManager {
    private static final java.lang.String ATTRIBUTE_CATEGORY = "category";
    private static final java.lang.String ATTRIBUTE_CLASS = "class";
    private static final java.lang.String ATTRIBUTE_PACKAGE = "package";
    private static final java.lang.String ATTRIBUTE_ROLE = "role";
    private static final java.lang.String ATTRIBUTE_SHIFT = "shift";
    private static final java.lang.String ATTRIBUTE_SHORTCUT = "shortcut";
    public static final java.lang.String EXTRA_ROLE = "com.android.server.policy.ModifierShortcutManager.EXTRA_ROLE";
    private static final java.lang.String TAG = "ModifierShortcutManager";
    private static final java.lang.String TAG_BOOKMARK = "bookmark";
    private static final java.lang.String TAG_BOOKMARKS = "bookmarks";
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private final android.content.pm.PackageManager mPackageManager;
    private final android.app.role.RoleManager mRoleManager;
    static android.util.SparseArray<java.lang.String> sApplicationLaunchKeyRoles = new android.util.SparseArray<>();
    static android.util.SparseArray<java.lang.String> sApplicationLaunchKeyCategories = new android.util.SparseArray<>();
    private final android.util.SparseArray<android.content.Intent> mIntentShortcuts = new android.util.SparseArray<>();
    private final android.util.SparseArray<android.content.Intent> mShiftShortcuts = new android.util.SparseArray<>();
    private final android.util.SparseArray<java.lang.String> mRoleShortcuts = new android.util.SparseArray<>();
    private final android.util.SparseArray<java.lang.String> mShiftRoleShortcuts = new android.util.SparseArray<>();
    private final java.util.Map<java.lang.String, android.content.Intent> mRoleIntents = new java.util.HashMap();
    private android.util.LongSparseArray<com.android.internal.policy.IShortcutService> mShortcutKeyServices = new android.util.LongSparseArray<>();
    private boolean mSearchKeyShortcutPending = false;
    private boolean mConsumeSearchKeyUp = true;

    static {
        sApplicationLaunchKeyRoles.append(64, "android.app.role.BROWSER");
        sApplicationLaunchKeyCategories.append(65, "android.intent.category.APP_EMAIL");
        sApplicationLaunchKeyCategories.append(207, "android.intent.category.APP_CONTACTS");
        sApplicationLaunchKeyCategories.append(208, "android.intent.category.APP_CALENDAR");
        sApplicationLaunchKeyCategories.append(209, "android.intent.category.APP_MUSIC");
        sApplicationLaunchKeyCategories.append(210, "android.intent.category.APP_CALCULATOR");
    }

    ModifierShortcutManager(android.content.Context context, android.os.Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
        this.mPackageManager = this.mContext.getPackageManager();
        this.mRoleManager = (android.app.role.RoleManager) this.mContext.getSystemService(android.app.role.RoleManager.class);
        this.mRoleManager.addOnRoleHoldersChangedListenerAsUser(this.mContext.getMainExecutor(), new android.app.role.OnRoleHoldersChangedListener() { // from class: com.android.server.policy.ModifierShortcutManager$$ExternalSyntheticLambda1
            public final void onRoleHoldersChanged(java.lang.String str, android.os.UserHandle userHandle) {
                com.android.server.policy.ModifierShortcutManager.this.lambda$new$0(str, userHandle);
            }
        }, android.os.UserHandle.ALL);
        loadShortcuts();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(java.lang.String str, android.os.UserHandle userHandle) {
        this.mRoleIntents.remove(str);
    }

    private android.content.Intent getIntent(android.view.KeyCharacterMap keyCharacterMap, int i, int i2) {
        char lowerCase;
        boolean metaStateHasModifiers = android.view.KeyEvent.metaStateHasModifiers(i2, 1);
        android.content.Intent intent = null;
        if (!metaStateHasModifiers && !android.view.KeyEvent.metaStateHasNoModifiers(i2)) {
            return null;
        }
        android.util.SparseArray<android.content.Intent> sparseArray = metaStateHasModifiers ? this.mShiftShortcuts : this.mIntentShortcuts;
        int i3 = keyCharacterMap.get(i, i2);
        if (i3 != 0) {
            intent = sparseArray.get(i3);
        }
        if (intent == null && (lowerCase = java.lang.Character.toLowerCase(keyCharacterMap.getDisplayLabel(i))) != 0) {
            android.content.Intent intent2 = sparseArray.get(lowerCase);
            if (intent2 == null) {
                java.lang.String str = metaStateHasModifiers ? this.mShiftRoleShortcuts.get(lowerCase) : this.mRoleShortcuts.get(lowerCase);
                if (str != null) {
                    return getRoleLaunchIntent(str);
                }
                return intent2;
            }
            return intent2;
        }
        return intent;
    }

    private android.content.Intent getRoleLaunchIntent(java.lang.String str) {
        android.content.Intent intent = this.mRoleIntents.get(str);
        if (intent == null) {
            if (this.mRoleManager.isRoleAvailable(str)) {
                java.lang.String defaultApplication = this.mRoleManager.getDefaultApplication(str);
                if (defaultApplication != null) {
                    android.content.Intent launchIntentForPackage = this.mPackageManager.getLaunchIntentForPackage(defaultApplication);
                    launchIntentForPackage.putExtra(EXTRA_ROLE, str);
                    this.mRoleIntents.put(str, launchIntentForPackage);
                    return launchIntentForPackage;
                }
                android.util.Log.w(TAG, "No default application for role " + str);
                return intent;
            }
            android.util.Log.w(TAG, "Role " + str + " is not available.");
            return intent;
        }
        return intent;
    }

    private void loadShortcuts() {
        android.content.Intent intent;
        try {
            android.content.res.XmlResourceParser xml = this.mContext.getResources().getXml(android.R.xml.bookmarks);
            com.android.internal.util.XmlUtils.beginDocument(xml, TAG_BOOKMARKS);
            while (true) {
                com.android.internal.util.XmlUtils.nextElement(xml);
                if (xml.getEventType() == 1 || !TAG_BOOKMARK.equals(xml.getName())) {
                    break;
                }
                java.lang.String attributeValue = xml.getAttributeValue(null, "package");
                java.lang.String attributeValue2 = xml.getAttributeValue(null, ATTRIBUTE_CLASS);
                java.lang.String attributeValue3 = xml.getAttributeValue(null, ATTRIBUTE_SHORTCUT);
                java.lang.String attributeValue4 = xml.getAttributeValue(null, ATTRIBUTE_CATEGORY);
                java.lang.String attributeValue5 = xml.getAttributeValue(null, ATTRIBUTE_SHIFT);
                java.lang.String attributeValue6 = xml.getAttributeValue(null, ATTRIBUTE_ROLE);
                if (android.text.TextUtils.isEmpty(attributeValue3)) {
                    android.util.Log.w(TAG, "Shortcut required for bookmark with category=" + attributeValue4 + " packageName=" + attributeValue + " className=" + attributeValue2 + " role=" + attributeValue6 + "shiftName=" + attributeValue5);
                } else {
                    char charAt = attributeValue3.charAt(0);
                    boolean z = attributeValue5 != null && attributeValue5.equals("true");
                    if (attributeValue != null && attributeValue2 != null) {
                        if (attributeValue6 != null || attributeValue4 != null) {
                            android.util.Log.w(TAG, "Cannot specify role or category when package and class are present for bookmark packageName=" + attributeValue + " className=" + attributeValue2 + " shortcutChar=" + ((int) charAt));
                        } else {
                            android.content.ComponentName componentName = new android.content.ComponentName(attributeValue, attributeValue2);
                            try {
                                this.mPackageManager.getActivityInfo(componentName, 794624);
                            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                                android.content.ComponentName componentName2 = new android.content.ComponentName(this.mPackageManager.canonicalToCurrentPackageNames(new java.lang.String[]{attributeValue})[0], attributeValue2);
                                try {
                                    this.mPackageManager.getActivityInfo(componentName2, 794624);
                                    componentName = componentName2;
                                } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                                    android.util.Log.w(TAG, "Unable to add bookmark: " + attributeValue + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + attributeValue2 + " not found.");
                                }
                            }
                            intent = new android.content.Intent("android.intent.action.MAIN");
                            intent.addCategory("android.intent.category.LAUNCHER");
                            intent.setComponent(componentName);
                        }
                    } else if (attributeValue4 != null) {
                        if (attributeValue6 != null) {
                            android.util.Log.w(TAG, "Cannot specify role bookmark when category is present for bookmark shortcutChar=" + ((int) charAt) + " category= " + attributeValue4);
                        } else {
                            intent = android.content.Intent.makeMainSelectorActivity("android.intent.action.MAIN", attributeValue4);
                        }
                    } else if (attributeValue6 != null) {
                        if (z) {
                            this.mShiftRoleShortcuts.put(charAt, attributeValue6);
                        } else {
                            this.mRoleShortcuts.put(charAt, attributeValue6);
                        }
                    } else {
                        android.util.Log.w(TAG, "Unable to add bookmark for shortcut " + attributeValue3 + ": missing package/class, category or role attributes");
                    }
                    if (z) {
                        this.mShiftShortcuts.put(charAt, intent);
                    } else {
                        this.mIntentShortcuts.put(charAt, intent);
                    }
                }
            }
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e3) {
            android.util.Log.e(TAG, "Got exception parsing bookmarks.", e3);
        }
    }

    void registerShortcutKey(long j, com.android.internal.policy.IShortcutService iShortcutService) throws android.os.RemoteException {
        com.android.internal.policy.IShortcutService iShortcutService2 = this.mShortcutKeyServices.get(j);
        if (iShortcutService2 != null && iShortcutService2.asBinder().pingBinder()) {
            throw new android.os.RemoteException("Key already exists.");
        }
        this.mShortcutKeyServices.put(j, iShortcutService);
    }

    private boolean handleShortcutService(int i, int i2) {
        long j = i;
        if ((i2 & 4096) != 0) {
            j |= 17592186044416L;
        }
        if ((i2 & 2) != 0) {
            j |= 8589934592L;
        }
        if ((i2 & 1) != 0) {
            j |= 4294967296L;
        }
        if ((65536 & i2) != 0) {
            j |= 281474976710656L;
        }
        com.android.internal.policy.IShortcutService iShortcutService = this.mShortcutKeyServices.get(j);
        if (iShortcutService != null) {
            try {
                iShortcutService.notifyShortcutKeyPressed(j);
                return true;
            } catch (android.os.RemoteException e) {
                this.mShortcutKeyServices.delete(j);
                return true;
            }
        }
        return false;
    }

    @android.annotation.SuppressLint({"MissingPermission"})
    private boolean handleIntentShortcut(android.view.KeyCharacterMap keyCharacterMap, android.view.KeyEvent keyEvent, int i) {
        android.content.Intent intent;
        int keyCode = keyEvent.getKeyCode();
        if (this.mSearchKeyShortcutPending) {
            if (!keyCharacterMap.isPrintingKey(keyCode)) {
                return false;
            }
            this.mConsumeSearchKeyUp = true;
            this.mSearchKeyShortcutPending = false;
        } else if ((458752 & i) != 0) {
            i &= -458753;
        } else {
            java.lang.String str = sApplicationLaunchKeyRoles.get(keyCode);
            java.lang.String str2 = sApplicationLaunchKeyCategories.get(keyCode);
            if (str != null) {
                intent = getRoleLaunchIntent(str);
            } else if (str2 == null) {
                intent = null;
            } else {
                intent = android.content.Intent.makeMainSelectorActivity("android.intent.action.MAIN", str2);
            }
            if (intent == null) {
                return false;
            }
            intent.setFlags(268435456);
            try {
                this.mContext.startActivityAsUser(intent, android.os.UserHandle.CURRENT);
            } catch (android.content.ActivityNotFoundException e) {
                android.util.Slog.w(TAG, "Dropping application launch key because the activity to which it is registered was not found: keyCode=" + android.view.KeyEvent.keyCodeToString(keyCode) + ", category=" + str2 + " role=" + str);
            }
            logKeyboardShortcut(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.getLogEventFromIntent(intent));
            return true;
        }
        android.content.Intent intent2 = getIntent(keyCharacterMap, keyCode, i);
        if (intent2 == null) {
            return false;
        }
        intent2.addFlags(268435456);
        try {
            this.mContext.startActivityAsUser(intent2, android.os.UserHandle.CURRENT);
        } catch (android.content.ActivityNotFoundException e2) {
            android.util.Slog.w(TAG, "Dropping shortcut key combination because the activity to which it is registered was not found: META+ or SEARCH" + android.view.KeyEvent.keyCodeToString(keyCode));
        }
        logKeyboardShortcut(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.getLogEventFromIntent(intent2));
        return true;
    }

    private void logKeyboardShortcut(final android.view.KeyEvent keyEvent, final com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent keyboardLogEvent) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.policy.ModifierShortcutManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.policy.ModifierShortcutManager.this.lambda$logKeyboardShortcut$1(keyEvent, keyboardLogEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleKeyboardLogging, reason: merged with bridge method [inline-methods] */
    public void lambda$logKeyboardShortcut$1(android.view.KeyEvent keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent keyboardLogEvent) {
        android.hardware.input.InputManager inputManager = (android.hardware.input.InputManager) this.mContext.getSystemService(android.hardware.input.InputManager.class);
        com.android.server.input.KeyboardMetricsCollector.logKeyboardSystemsEventReportedAtom(inputManager != null ? inputManager.getInputDevice(keyEvent.getDeviceId()) : null, keyboardLogEvent, keyEvent.getMetaState(), keyEvent.getKeyCode());
    }

    boolean interceptKey(android.view.KeyEvent keyEvent) {
        if (keyEvent.getRepeatCount() != 0) {
            return false;
        }
        int modifiers = keyEvent.getModifiers();
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 84) {
            if (keyEvent.getAction() == 0) {
                this.mSearchKeyShortcutPending = true;
                this.mConsumeSearchKeyUp = false;
            } else {
                this.mSearchKeyShortcutPending = false;
                if (this.mConsumeSearchKeyUp) {
                    this.mConsumeSearchKeyUp = false;
                    return true;
                }
            }
            return false;
        }
        if (keyEvent.getAction() != 0) {
            return false;
        }
        return handleIntentShortcut(keyEvent.getKeyCharacterMap(), keyEvent, modifiers) || handleShortcutService(keyCode, modifiers);
    }
}
