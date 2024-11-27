package com.android.server.notification;

/* loaded from: classes2.dex */
public class PreferencesHelper implements com.android.server.notification.RankingConfig {
    private static final java.lang.String ATT_ALLOW_BUBBLE = "allow_bubble";
    private static final java.lang.String ATT_APP_USER_LOCKED_FIELDS = "app_user_locked_fields";
    private static final java.lang.String ATT_ENABLED = "enabled";
    private static final java.lang.String ATT_HIDE_SILENT = "hide_gentle";
    private static final java.lang.String ATT_ID = "id";
    private static final java.lang.String ATT_IMPORTANCE = "importance";
    private static final java.lang.String ATT_NAME = "name";
    private static final java.lang.String ATT_PRIORITY = "priority";
    private static final java.lang.String ATT_SENT_INVALID_MESSAGE = "sent_invalid_msg";
    private static final java.lang.String ATT_SENT_VALID_BUBBLE = "sent_valid_bubble";
    private static final java.lang.String ATT_SENT_VALID_MESSAGE = "sent_valid_msg";
    private static final java.lang.String ATT_SHOW_BADGE = "show_badge";
    private static final java.lang.String ATT_SOUND_TIMEOUT = "sound-timeout";
    private static final java.lang.String ATT_UID = "uid";
    private static final java.lang.String ATT_USER_DEMOTED_INVALID_MSG_APP = "user_demote_msg_app";
    private static final java.lang.String ATT_VERSION = "version";
    private static final java.lang.String ATT_VISIBILITY = "visibility";
    private static final boolean DEFAULT_APP_LOCKED_IMPORTANCE = false;
    static final boolean DEFAULT_BUBBLES_ENABLED = true;

    @com.android.internal.annotations.VisibleForTesting
    static final int DEFAULT_BUBBLE_PREFERENCE = 0;

    @com.android.internal.annotations.VisibleForTesting
    static final boolean DEFAULT_HIDE_SILENT_STATUS_BAR_ICONS = false;
    private static final int DEFAULT_IMPORTANCE = -1000;
    private static final int DEFAULT_LOCKED_APP_FIELDS = 0;
    private static final int DEFAULT_PRIORITY = 0;
    private static final boolean DEFAULT_SHOW_BADGE = true;
    private static final int DEFAULT_SOUND_TIMEOUT = 0;
    private static final int DEFAULT_VISIBILITY = -1000;

    @com.android.internal.annotations.VisibleForTesting
    static final int NOTIFICATION_CHANNEL_COUNT_LIMIT = 5000;
    private static final int NOTIFICATION_CHANNEL_DELETION_RETENTION_DAYS = 30;

    @com.android.internal.annotations.VisibleForTesting
    static final int NOTIFICATION_CHANNEL_GROUP_COUNT_LIMIT = 6000;
    private static final int NOTIFICATION_CHANNEL_GROUP_PULL_LIMIT = 1000;
    private static final int NOTIFICATION_CHANNEL_PULL_LIMIT = 2000;
    private static final int NOTIFICATION_PREFERENCES_PULL_LIMIT = 1000;
    private static final int NOTIFICATION_UPDATE_LOG_SUBTYPE_FROM_APP = 0;
    private static final int NOTIFICATION_UPDATE_LOG_SUBTYPE_FROM_USER = 1;
    private static final java.lang.String TAG = "NotificationPrefHelper";
    private static final java.lang.String TAG_CHANNEL = "channel";
    private static final java.lang.String TAG_DELEGATE = "delegate";
    private static final java.lang.String TAG_GROUP = "channelGroup";
    private static final java.lang.String TAG_PACKAGE = "package";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String TAG_RANKING = "ranking";
    private static final java.lang.String TAG_STATUS_ICONS = "silent_status_icons";

    @com.android.internal.annotations.VisibleForTesting
    static final int UNKNOWN_UID = -10000;
    private static final int XML_VERSION_BUBBLES_UPGRADE = 1;
    private static final int XML_VERSION_NOTIF_PERMISSION = 3;
    private static final int XML_VERSION_REVIEW_PERMISSIONS_NOTIFICATION = 4;
    private final android.app.AppOpsManager mAppOps;
    private android.util.SparseBooleanArray mBadgingEnabled;
    private android.util.SparseBooleanArray mBubblesEnabled;
    private final android.content.Context mContext;
    private boolean mCurrentUserHasChannelsBypassingDnd;
    private boolean mIsMediaNotificationFilteringEnabled;
    private android.util.SparseBooleanArray mLockScreenPrivateNotifications;
    private android.util.SparseBooleanArray mLockScreenShowNotifications;
    private final com.android.server.notification.NotificationChannelLogger mNotificationChannelLogger;
    private final com.android.server.notification.PermissionHelper mPermissionHelper;
    private final android.permission.PermissionManager mPermissionManager;
    private final android.content.pm.PackageManager mPm;
    private final com.android.server.notification.RankingHandler mRankingHandler;
    private final boolean mShowReviewPermissionsNotification;
    private final com.android.server.notification.ManagedServices.UserProfiles mUserProfiles;
    private final com.android.server.notification.ZenModeHelper mZenModeHelper;
    private final android.util.ArrayMap<java.lang.String, com.android.server.notification.PreferencesHelper.PackagePreferences> mPackagePreferences = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<java.lang.String, com.android.server.notification.PreferencesHelper.PackagePreferences> mRestoredWithoutUids = new android.util.ArrayMap<>();
    private boolean mHideSilentStatusBarIcons = false;
    private final int XML_VERSION = 4;

    public @interface LockableAppFields {
        public static final int USER_LOCKED_BUBBLE = 2;
        public static final int USER_LOCKED_IMPORTANCE = 1;
    }

    public PreferencesHelper(android.content.Context context, android.content.pm.PackageManager packageManager, com.android.server.notification.RankingHandler rankingHandler, com.android.server.notification.ZenModeHelper zenModeHelper, com.android.server.notification.PermissionHelper permissionHelper, android.permission.PermissionManager permissionManager, com.android.server.notification.NotificationChannelLogger notificationChannelLogger, android.app.AppOpsManager appOpsManager, com.android.server.notification.ManagedServices.UserProfiles userProfiles, boolean z) {
        this.mContext = context;
        this.mZenModeHelper = zenModeHelper;
        this.mRankingHandler = rankingHandler;
        this.mPermissionHelper = permissionHelper;
        this.mPermissionManager = permissionManager;
        this.mPm = packageManager;
        this.mNotificationChannelLogger = notificationChannelLogger;
        this.mAppOps = appOpsManager;
        this.mUserProfiles = userProfiles;
        this.mShowReviewPermissionsNotification = z;
        this.mIsMediaNotificationFilteringEnabled = context.getResources().getBoolean(android.R.bool.config_preferKeepClearForFocus);
        updateBadgingEnabled();
        updateBubblesEnabled();
        updateMediaNotificationFilteringEnabled();
    }

    public void readXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, boolean z, int i) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.util.ArrayMap<java.lang.String, com.android.server.notification.PreferencesHelper.PackagePreferences> arrayMap;
        android.util.ArrayMap<java.lang.String, com.android.server.notification.PreferencesHelper.PackagePreferences> arrayMap2;
        if (typedXmlPullParser.getEventType() == 2 && TAG_RANKING.equals(typedXmlPullParser.getName())) {
            int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATT_VERSION, -1);
            boolean z2 = attributeInt == 1;
            boolean z3 = attributeInt < 3;
            if (this.mShowReviewPermissionsNotification && attributeInt < 4) {
                android.provider.Settings.Global.putInt(this.mContext.getContentResolver(), "review_permissions_notification_state", 0);
            }
            android.util.ArrayMap<java.lang.String, com.android.server.notification.PreferencesHelper.PackagePreferences> arrayMap3 = this.mPackagePreferences;
            synchronized (arrayMap3) {
                while (true) {
                    try {
                        int next = typedXmlPullParser.next();
                        if (next == 1) {
                            arrayMap2 = arrayMap3;
                            break;
                        }
                        java.lang.String name = typedXmlPullParser.getName();
                        if (next == 3 && TAG_RANKING.equals(name)) {
                            arrayMap2 = arrayMap3;
                            break;
                        }
                        if (next == 2) {
                            if (TAG_STATUS_ICONS.equals(name)) {
                                if (!z || i == 0) {
                                    this.mHideSilentStatusBarIcons = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATT_HIDE_SILENT, false);
                                }
                            } else if ("package".equals(name)) {
                                java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
                                if (android.text.TextUtils.isEmpty(attributeValue)) {
                                    arrayMap = arrayMap3;
                                } else {
                                    arrayMap = arrayMap3;
                                    try {
                                        restorePackage(typedXmlPullParser, z, i, attributeValue, z2, z3);
                                    } catch (java.lang.Throwable th) {
                                        th = th;
                                        throw th;
                                    }
                                }
                                arrayMap3 = arrayMap;
                            }
                        }
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                        arrayMap = arrayMap3;
                    }
                }
            }
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(7:(17:108|109|110|111|112|113|7|(1:107)(2:10|(1:12)(1:106))|(1:14)(1:105)|15|16|17|18|(2:19|(7:21|(2:26|27)|97|98|67|68|64)(2:99|100))|28|29|(2:31|33)(1:35))(1:5)|17|18|(3:19|(0)(0)|64)|28|29|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x020b, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x020d, code lost:
    
        android.util.Slog.e(r6, "deleteDefaultChannelIfNeededLocked - Exception: " + r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0150, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x022a, code lost:
    
        android.util.Slog.w(r6, "Failed to restore pkg", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0230, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x010c, code lost:
    
        if (r3 != 4) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0119, code lost:
    
        r3 = r25.getName();
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0126, code lost:
    
        if (com.android.server.notification.PreferencesHelper.TAG_GROUP.equals(r3) == false) goto L111;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0130, code lost:
    
        if (r1.groups.size() < 6000) goto L114;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x015e, code lost:
    
        r6 = r22;
        r8 = r25.getAttributeValue((java.lang.String) null, com.android.server.notification.PreferencesHelper.ATT_ID);
        r9 = r25.getAttributeValue((java.lang.String) null, "name");
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x016e, code lost:
    
        if (android.text.TextUtils.isEmpty(r8) != false) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0170, code lost:
    
        r13 = new android.app.NotificationChannelGroup(r8, r9);
        r13.populateFromXml(r25);
        r1.groups.put(r8, r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0186, code lost:
    
        if (com.android.server.notification.PreferencesHelper.TAG_CHANNEL.equals(r3) == false) goto L116;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0190, code lost:
    
        if (r1.channels.size() < 5000) goto L117;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0192, code lost:
    
        if (r0 != false) goto L118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0194, code lost:
    
        android.util.Slog.w(r6, "Skipping further channels for " + r1.pkg);
        r0 = true;
        r22 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x01af, code lost:
    
        r3 = r23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x01b7, code lost:
    
        r8 = true;
        restoreChannel(r25, r26, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x01c7, code lost:
    
        if (com.android.server.notification.PreferencesHelper.TAG_DELEGATE.equals(r3) == false) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x01c9, code lost:
    
        r3 = r23;
        r13 = r25.getAttributeInt((java.lang.String) null, r3, -10000);
        r7 = com.android.internal.util.XmlUtils.readStringAttribute(r25, "name");
        r14 = r25.getAttributeBoolean((java.lang.String) null, "enabled", r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x01dc, code lost:
    
        if (r13 == (-10000)) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x01e2, code lost:
    
        if (android.text.TextUtils.isEmpty(r7) != false) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01e4, code lost:
    
        r15 = new com.android.server.notification.PreferencesHelper.Delegate(r7, r13, r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01eb, code lost:
    
        r1.delegate = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x01ea, code lost:
    
        r15 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x01ee, code lost:
    
        r3 = r23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x01be, code lost:
    
        r8 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0132, code lost:
    
        if (r5 != false) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0147, code lost:
    
        r6 = r22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0149, code lost:
    
        android.util.Slog.w(r6, "Skipping further groups for " + r1.pkg);
        r5 = true;
        r22 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0153, code lost:
    
        r6 = r22;
        r3 = r23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x017e, code lost:
    
        r6 = r22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x010e, code lost:
    
        r6 = r22;
        r3 = r23;
     */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0060 A[Catch: Exception -> 0x0029, TryCatch #1 {Exception -> 0x0029, blocks: (B:3:0x0017, B:109:0x001d, B:112:0x0023, B:10:0x0042, B:15:0x0068, B:105:0x0060), top: B:2:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0223 A[Catch: Exception -> 0x0150, TRY_LEAVE, TryCatch #0 {Exception -> 0x0150, blocks: (B:29:0x0207, B:31:0x0223, B:39:0x020d, B:89:0x0149, B:53:0x015e, B:55:0x0170, B:56:0x0180, B:58:0x0188, B:62:0x0194, B:70:0x01b7, B:71:0x01c1, B:73:0x01c9, B:75:0x01de, B:77:0x01e4, B:78:0x01eb), top: B:88:0x0149, inners: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0202 A[SYNTHETIC] */
    @com.android.internal.annotations.GuardedBy({"mPackagePreferences"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void restorePackage(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, boolean z, int i, java.lang.String str, boolean z2, boolean z3) {
        java.lang.String str2;
        int packageUidAsUser;
        java.lang.String str3;
        int next;
        boolean z4;
        com.android.server.notification.PreferencesHelper preferencesHelper;
        try {
            int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, "uid", -10000);
            try {
                if (z) {
                    try {
                        try {
                            packageUidAsUser = this.mPm.getPackageUidAsUser(str, i);
                        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        }
                    } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                    }
                    boolean z5 = false;
                    int attributeInt2 = !((z2 || packageUidAsUser == -10000) ? false : this.mAppOps.noteOpNoThrow(24, packageUidAsUser, str, (java.lang.String) null, "check-notif-bubble") == 0) ? 1 : typedXmlPullParser.getAttributeInt((java.lang.String) null, ATT_ALLOW_BUBBLE, 0);
                    int attributeInt3 = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATT_IMPORTANCE, -1000);
                    int attributeInt4 = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATT_PRIORITY, 0);
                    int attributeInt5 = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATT_VISIBILITY, -1000);
                    boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATT_SHOW_BADGE, true);
                    str3 = TAG;
                    java.lang.String str4 = "uid";
                    com.android.server.notification.PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = getOrCreatePackagePreferencesLocked(str, i, packageUidAsUser, attributeInt3, attributeInt4, attributeInt5, attributeBoolean, attributeInt2);
                    orCreatePackagePreferencesLocked.priority = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATT_PRIORITY, 0);
                    orCreatePackagePreferencesLocked.visibility = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATT_VISIBILITY, -1000);
                    orCreatePackagePreferencesLocked.showBadge = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATT_SHOW_BADGE, true);
                    orCreatePackagePreferencesLocked.lockedAppFields = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATT_APP_USER_LOCKED_FIELDS, 0);
                    orCreatePackagePreferencesLocked.hasSentInvalidMessage = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATT_SENT_INVALID_MESSAGE, false);
                    orCreatePackagePreferencesLocked.hasSentValidMessage = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATT_SENT_VALID_MESSAGE, false);
                    orCreatePackagePreferencesLocked.userDemotedMsgApp = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATT_USER_DEMOTED_INVALID_MSG_APP, false);
                    orCreatePackagePreferencesLocked.hasSentValidBubble = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATT_SENT_VALID_BUBBLE, false);
                    orCreatePackagePreferencesLocked.soundTimeout = typedXmlPullParser.getAttributeLong((java.lang.String) null, ATT_SOUND_TIMEOUT, 0L);
                    int depth = typedXmlPullParser.getDepth();
                    boolean z6 = false;
                    while (true) {
                        next = typedXmlPullParser.next();
                        if (next != 1) {
                            z4 = true;
                            str2 = str3;
                            preferencesHelper = this;
                            break;
                        }
                        if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                            z4 = true;
                            str2 = str3;
                            preferencesHelper = this;
                            break;
                        }
                        java.lang.String str5 = str3;
                        java.lang.String str6 = str4;
                        str4 = str6;
                        str3 = str5;
                    }
                    preferencesHelper.deleteDefaultChannelIfNeededLocked(orCreatePackagePreferencesLocked);
                    if (z3) {
                        return;
                    }
                    orCreatePackagePreferencesLocked.importance = attributeInt3;
                    orCreatePackagePreferencesLocked.migrateToPm = z4;
                    return;
                }
                com.android.server.notification.PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked2 = getOrCreatePackagePreferencesLocked(str, i, packageUidAsUser, attributeInt3, attributeInt4, attributeInt5, attributeBoolean, attributeInt2);
                orCreatePackagePreferencesLocked2.priority = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATT_PRIORITY, 0);
                orCreatePackagePreferencesLocked2.visibility = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATT_VISIBILITY, -1000);
                orCreatePackagePreferencesLocked2.showBadge = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATT_SHOW_BADGE, true);
                orCreatePackagePreferencesLocked2.lockedAppFields = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATT_APP_USER_LOCKED_FIELDS, 0);
                orCreatePackagePreferencesLocked2.hasSentInvalidMessage = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATT_SENT_INVALID_MESSAGE, false);
                orCreatePackagePreferencesLocked2.hasSentValidMessage = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATT_SENT_VALID_MESSAGE, false);
                orCreatePackagePreferencesLocked2.userDemotedMsgApp = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATT_USER_DEMOTED_INVALID_MSG_APP, false);
                orCreatePackagePreferencesLocked2.hasSentValidBubble = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATT_SENT_VALID_BUBBLE, false);
                orCreatePackagePreferencesLocked2.soundTimeout = typedXmlPullParser.getAttributeLong((java.lang.String) null, ATT_SOUND_TIMEOUT, 0L);
                int depth2 = typedXmlPullParser.getDepth();
                boolean z62 = false;
                while (true) {
                    next = typedXmlPullParser.next();
                    if (next != 1) {
                    }
                }
                preferencesHelper.deleteDefaultChannelIfNeededLocked(orCreatePackagePreferencesLocked2);
                if (z3) {
                }
            } catch (java.lang.Exception e3) {
                e = e3;
                str2 = str3;
            }
            packageUidAsUser = attributeInt;
            boolean z52 = false;
            if (!((z2 || packageUidAsUser == -10000) ? false : this.mAppOps.noteOpNoThrow(24, packageUidAsUser, str, (java.lang.String) null, "check-notif-bubble") == 0)) {
            }
            int attributeInt32 = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATT_IMPORTANCE, -1000);
            int attributeInt42 = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATT_PRIORITY, 0);
            int attributeInt52 = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATT_VISIBILITY, -1000);
            boolean attributeBoolean2 = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATT_SHOW_BADGE, true);
            str3 = TAG;
            java.lang.String str42 = "uid";
        } catch (java.lang.Exception e4) {
            e = e4;
            str2 = TAG;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPackagePreferences"})
    private void restoreChannel(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, boolean z, com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferences) {
        try {
            java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATT_ID);
            java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
            int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATT_IMPORTANCE, -1000);
            if (!android.text.TextUtils.isEmpty(attributeValue) && !android.text.TextUtils.isEmpty(attributeValue2)) {
                android.app.NotificationChannel notificationChannel = new android.app.NotificationChannel(attributeValue, attributeValue2, attributeInt);
                if (z) {
                    notificationChannel.populateFromXmlForRestore(typedXmlPullParser, packagePreferences.uid != -10000, this.mContext);
                } else {
                    notificationChannel.populateFromXml(typedXmlPullParser);
                }
                notificationChannel.setImportanceLockedByCriticalDeviceFunction(packagePreferences.defaultAppLockedImportance || packagePreferences.fixedImportance);
                if (isShortcutOk(notificationChannel) && isDeletionOk(notificationChannel)) {
                    packagePreferences.channels.put(attributeValue, notificationChannel);
                }
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "could not restore channel for " + packagePreferences.pkg, e);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPackagePreferences"})
    private boolean hasUserConfiguredSettings(com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferences) {
        boolean z;
        java.util.Iterator<android.app.NotificationChannel> it = packagePreferences.channels.values().iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            if (it.next().getUserLockedFields() != 0) {
                z = true;
                break;
            }
        }
        return z || packagePreferences.importance == 0;
    }

    private boolean isShortcutOk(android.app.NotificationChannel notificationChannel) {
        return !(notificationChannel.getConversationId() != null && notificationChannel.getConversationId().contains(":placeholder_id"));
    }

    private boolean isDeletionOk(android.app.NotificationChannel notificationChannel) {
        if (notificationChannel.isDeleted()) {
            return notificationChannel.getDeletedTimeMs() > java.lang.System.currentTimeMillis() - com.android.server.usage.UnixCalendar.MONTH_IN_MILLIS;
        }
        return true;
    }

    private com.android.server.notification.PreferencesHelper.PackagePreferences getPackagePreferencesLocked(java.lang.String str, int i) {
        return this.mPackagePreferences.get(packagePreferencesKey(str, i));
    }

    private com.android.server.notification.PreferencesHelper.PackagePreferences getOrCreatePackagePreferencesLocked(java.lang.String str, int i) {
        return getOrCreatePackagePreferencesLocked(str, android.os.UserHandle.getUserId(i), i, -1000, 0, -1000, true, 0);
    }

    private com.android.server.notification.PreferencesHelper.PackagePreferences getOrCreatePackagePreferencesLocked(java.lang.String str, int i, int i2, int i3, int i4, int i5, boolean z, int i6) {
        com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferences;
        java.lang.String packagePreferencesKey = packagePreferencesKey(str, i2);
        if (i2 == -10000) {
            packagePreferences = this.mRestoredWithoutUids.get(unrestoredPackageKey(str, i));
        } else {
            packagePreferences = this.mPackagePreferences.get(packagePreferencesKey);
        }
        if (packagePreferences == null) {
            packagePreferences = new com.android.server.notification.PreferencesHelper.PackagePreferences();
            packagePreferences.pkg = str;
            packagePreferences.uid = i2;
            packagePreferences.importance = i3;
            packagePreferences.priority = i4;
            packagePreferences.visibility = i5;
            packagePreferences.showBadge = z;
            packagePreferences.bubblePreference = i6;
            try {
                createDefaultChannelIfNeededLocked(packagePreferences);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Slog.e(TAG, "createDefaultChannelIfNeededLocked - Exception: " + e);
            }
            if (packagePreferences.uid == -10000) {
                this.mRestoredWithoutUids.put(unrestoredPackageKey(str, i), packagePreferences);
            } else {
                this.mPackagePreferences.put(packagePreferencesKey, packagePreferences);
            }
        }
        return packagePreferences;
    }

    private boolean shouldHaveDefaultChannel(com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferences) throws android.content.pm.PackageManager.NameNotFoundException {
        if (this.mPm.getApplicationInfoAsUser(packagePreferences.pkg, 0, android.os.UserHandle.getUserId(packagePreferences.uid)).targetSdkVersion >= 26) {
            return false;
        }
        return true;
    }

    private boolean deleteDefaultChannelIfNeededLocked(com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferences) throws android.content.pm.PackageManager.NameNotFoundException {
        if (!packagePreferences.channels.containsKey("miscellaneous") || shouldHaveDefaultChannel(packagePreferences)) {
            return false;
        }
        packagePreferences.channels.remove("miscellaneous");
        return true;
    }

    private boolean createDefaultChannelIfNeededLocked(com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferences) throws android.content.pm.PackageManager.NameNotFoundException {
        if (packagePreferences.uid == -10000) {
            return false;
        }
        if (packagePreferences.channels.containsKey("miscellaneous")) {
            packagePreferences.channels.get("miscellaneous").setName(this.mContext.getString(android.R.string.db_default_sync_mode));
            return false;
        }
        if (!shouldHaveDefaultChannel(packagePreferences)) {
            return false;
        }
        android.app.NotificationChannel notificationChannel = new android.app.NotificationChannel("miscellaneous", this.mContext.getString(android.R.string.db_default_sync_mode), packagePreferences.importance);
        notificationChannel.setBypassDnd(packagePreferences.priority == 2);
        notificationChannel.setLockscreenVisibility(packagePreferences.visibility);
        if (packagePreferences.importance != -1000) {
            notificationChannel.lockFields(4);
        }
        if (packagePreferences.priority != 0) {
            notificationChannel.lockFields(1);
        }
        if (packagePreferences.visibility != -1000) {
            notificationChannel.lockFields(2);
        }
        packagePreferences.channels.put(notificationChannel.getId(), notificationChannel);
        return true;
    }

    public void writeXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, boolean z, int i) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, TAG_RANKING);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATT_VERSION, this.XML_VERSION);
        if (this.mHideSilentStatusBarIcons) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_STATUS_ICONS);
            typedXmlSerializer.attributeBoolean((java.lang.String) null, ATT_HIDE_SILENT, this.mHideSilentStatusBarIcons);
            typedXmlSerializer.endTag((java.lang.String) null, TAG_STATUS_ICONS);
        }
        android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.Pair<java.lang.Boolean, java.lang.Boolean>> arrayMap = new android.util.ArrayMap<>();
        if (z) {
            arrayMap = this.mPermissionHelper.getNotificationPermissionValues(i);
        }
        synchronized (this.mPackagePreferences) {
            try {
                int size = this.mPackagePreferences.size();
                int i2 = 0;
                while (true) {
                    int i3 = 3;
                    if (i2 >= size) {
                        break;
                    }
                    com.android.server.notification.PreferencesHelper.PackagePreferences valueAt = this.mPackagePreferences.valueAt(i2);
                    if (!z || android.os.UserHandle.getUserId(valueAt.uid) == i) {
                        typedXmlSerializer.startTag((java.lang.String) null, "package");
                        typedXmlSerializer.attribute((java.lang.String) null, "name", valueAt.pkg);
                        if (!arrayMap.isEmpty()) {
                            android.util.Pair pair = new android.util.Pair(java.lang.Integer.valueOf(valueAt.uid), valueAt.pkg);
                            android.util.Pair<java.lang.Boolean, java.lang.Boolean> pair2 = arrayMap.get(pair);
                            if (pair2 == null || !((java.lang.Boolean) pair2.first).booleanValue()) {
                                i3 = 0;
                            }
                            typedXmlSerializer.attributeInt((java.lang.String) null, ATT_IMPORTANCE, i3);
                            arrayMap.remove(pair);
                        } else if (valueAt.importance != -1000) {
                            typedXmlSerializer.attributeInt((java.lang.String) null, ATT_IMPORTANCE, valueAt.importance);
                        }
                        if (valueAt.priority != 0) {
                            typedXmlSerializer.attributeInt((java.lang.String) null, ATT_PRIORITY, valueAt.priority);
                        }
                        if (valueAt.visibility != -1000) {
                            typedXmlSerializer.attributeInt((java.lang.String) null, ATT_VISIBILITY, valueAt.visibility);
                        }
                        if (valueAt.soundTimeout != 0) {
                            typedXmlSerializer.attributeLong((java.lang.String) null, ATT_SOUND_TIMEOUT, valueAt.soundTimeout);
                        }
                        if (valueAt.bubblePreference != 0) {
                            typedXmlSerializer.attributeInt((java.lang.String) null, ATT_ALLOW_BUBBLE, valueAt.bubblePreference);
                        }
                        typedXmlSerializer.attributeBoolean((java.lang.String) null, ATT_SHOW_BADGE, valueAt.showBadge);
                        typedXmlSerializer.attributeInt((java.lang.String) null, ATT_APP_USER_LOCKED_FIELDS, valueAt.lockedAppFields);
                        typedXmlSerializer.attributeBoolean((java.lang.String) null, ATT_SENT_INVALID_MESSAGE, valueAt.hasSentInvalidMessage);
                        typedXmlSerializer.attributeBoolean((java.lang.String) null, ATT_SENT_VALID_MESSAGE, valueAt.hasSentValidMessage);
                        typedXmlSerializer.attributeBoolean((java.lang.String) null, ATT_USER_DEMOTED_INVALID_MSG_APP, valueAt.userDemotedMsgApp);
                        typedXmlSerializer.attributeBoolean((java.lang.String) null, ATT_SENT_VALID_BUBBLE, valueAt.hasSentValidBubble);
                        if (!z) {
                            typedXmlSerializer.attributeInt((java.lang.String) null, "uid", valueAt.uid);
                        }
                        if (valueAt.delegate != null) {
                            typedXmlSerializer.startTag((java.lang.String) null, TAG_DELEGATE);
                            typedXmlSerializer.attribute((java.lang.String) null, "name", valueAt.delegate.mPkg);
                            typedXmlSerializer.attributeInt((java.lang.String) null, "uid", valueAt.delegate.mUid);
                            if (!valueAt.delegate.mEnabled) {
                                typedXmlSerializer.attributeBoolean((java.lang.String) null, "enabled", valueAt.delegate.mEnabled);
                            }
                            typedXmlSerializer.endTag((java.lang.String) null, TAG_DELEGATE);
                        }
                        java.util.Iterator<android.app.NotificationChannelGroup> it = valueAt.groups.values().iterator();
                        while (it.hasNext()) {
                            it.next().writeXml(typedXmlSerializer);
                        }
                        for (android.app.NotificationChannel notificationChannel : valueAt.channels.values()) {
                            if (z) {
                                if (!notificationChannel.isDeleted()) {
                                    notificationChannel.writeXmlForBackup(typedXmlSerializer, this.mContext);
                                }
                            } else {
                                notificationChannel.writeXml(typedXmlSerializer);
                            }
                        }
                        typedXmlSerializer.endTag((java.lang.String) null, "package");
                    }
                    i2++;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (!arrayMap.isEmpty()) {
            for (android.util.Pair<java.lang.Integer, java.lang.String> pair3 : arrayMap.keySet()) {
                typedXmlSerializer.startTag((java.lang.String) null, "package");
                typedXmlSerializer.attribute((java.lang.String) null, "name", (java.lang.String) pair3.second);
                typedXmlSerializer.attributeInt((java.lang.String) null, ATT_IMPORTANCE, ((java.lang.Boolean) arrayMap.get(pair3).first).booleanValue() ? 3 : 0);
                typedXmlSerializer.endTag((java.lang.String) null, "package");
            }
        }
        typedXmlSerializer.endTag((java.lang.String) null, TAG_RANKING);
    }

    public void setBubblesAllowed(java.lang.String str, int i, int i2) {
        boolean z;
        synchronized (this.mPackagePreferences) {
            com.android.server.notification.PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = getOrCreatePackagePreferencesLocked(str, i);
            z = orCreatePackagePreferencesLocked.bubblePreference != i2;
            orCreatePackagePreferencesLocked.bubblePreference = i2;
            orCreatePackagePreferencesLocked.lockedAppFields |= 2;
        }
        if (z) {
            updateConfig();
        }
    }

    @Override // com.android.server.notification.RankingConfig
    public int getBubblePreference(java.lang.String str, int i) {
        int i2;
        synchronized (this.mPackagePreferences) {
            i2 = getOrCreatePackagePreferencesLocked(str, i).bubblePreference;
        }
        return i2;
    }

    public int getAppLockedFields(java.lang.String str, int i) {
        int i2;
        synchronized (this.mPackagePreferences) {
            i2 = getOrCreatePackagePreferencesLocked(str, i).lockedAppFields;
        }
        return i2;
    }

    @Override // com.android.server.notification.RankingConfig
    public boolean canShowBadge(java.lang.String str, int i) {
        boolean z;
        synchronized (this.mPackagePreferences) {
            z = getOrCreatePackagePreferencesLocked(str, i).showBadge;
        }
        return z;
    }

    @Override // com.android.server.notification.RankingConfig
    public void setShowBadge(java.lang.String str, int i, boolean z) {
        boolean z2;
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = getOrCreatePackagePreferencesLocked(str, i);
                if (orCreatePackagePreferencesLocked.showBadge == z) {
                    z2 = false;
                } else {
                    orCreatePackagePreferencesLocked.showBadge = z;
                    z2 = true;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z2) {
            updateConfig();
        }
    }

    public boolean isInInvalidMsgState(java.lang.String str, int i) {
        boolean z;
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = getOrCreatePackagePreferencesLocked(str, i);
                z = orCreatePackagePreferencesLocked.hasSentInvalidMessage && !orCreatePackagePreferencesLocked.hasSentValidMessage;
            } finally {
            }
        }
        return z;
    }

    public boolean hasUserDemotedInvalidMsgApp(java.lang.String str, int i) {
        boolean z;
        synchronized (this.mPackagePreferences) {
            try {
                z = isInInvalidMsgState(str, i) ? getOrCreatePackagePreferencesLocked(str, i).userDemotedMsgApp : false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public void setInvalidMsgAppDemoted(java.lang.String str, int i, boolean z) {
        synchronized (this.mPackagePreferences) {
            getOrCreatePackagePreferencesLocked(str, i).userDemotedMsgApp = z;
        }
    }

    public boolean setInvalidMessageSent(java.lang.String str, int i) {
        boolean z;
        synchronized (this.mPackagePreferences) {
            com.android.server.notification.PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = getOrCreatePackagePreferencesLocked(str, i);
            z = !orCreatePackagePreferencesLocked.hasSentInvalidMessage;
            orCreatePackagePreferencesLocked.hasSentInvalidMessage = true;
        }
        return z;
    }

    public boolean setValidMessageSent(java.lang.String str, int i) {
        boolean z;
        synchronized (this.mPackagePreferences) {
            com.android.server.notification.PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = getOrCreatePackagePreferencesLocked(str, i);
            z = !orCreatePackagePreferencesLocked.hasSentValidMessage;
            orCreatePackagePreferencesLocked.hasSentValidMessage = true;
        }
        return z;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean hasSentInvalidMsg(java.lang.String str, int i) {
        boolean z;
        synchronized (this.mPackagePreferences) {
            z = getOrCreatePackagePreferencesLocked(str, i).hasSentInvalidMessage;
        }
        return z;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean hasSentValidMsg(java.lang.String str, int i) {
        boolean z;
        synchronized (this.mPackagePreferences) {
            z = getOrCreatePackagePreferencesLocked(str, i).hasSentValidMessage;
        }
        return z;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean didUserEverDemoteInvalidMsgApp(java.lang.String str, int i) {
        boolean z;
        synchronized (this.mPackagePreferences) {
            z = getOrCreatePackagePreferencesLocked(str, i).userDemotedMsgApp;
        }
        return z;
    }

    public boolean setValidBubbleSent(java.lang.String str, int i) {
        boolean z;
        synchronized (this.mPackagePreferences) {
            com.android.server.notification.PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = getOrCreatePackagePreferencesLocked(str, i);
            z = !orCreatePackagePreferencesLocked.hasSentValidBubble;
            orCreatePackagePreferencesLocked.hasSentValidBubble = true;
        }
        return z;
    }

    boolean hasSentValidBubble(java.lang.String str, int i) {
        boolean z;
        synchronized (this.mPackagePreferences) {
            z = getOrCreatePackagePreferencesLocked(str, i).hasSentValidBubble;
        }
        return z;
    }

    boolean isImportanceLocked(java.lang.String str, int i) {
        boolean z;
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = getOrCreatePackagePreferencesLocked(str, i);
                z = orCreatePackagePreferencesLocked.fixedImportance || orCreatePackagePreferencesLocked.defaultAppLockedImportance;
            } finally {
            }
        }
        return z;
    }

    @Override // com.android.server.notification.RankingConfig
    public boolean isGroupBlocked(java.lang.String str, int i, java.lang.String str2) {
        if (str2 == null) {
            return false;
        }
        synchronized (this.mPackagePreferences) {
            try {
                android.app.NotificationChannelGroup notificationChannelGroup = getOrCreatePackagePreferencesLocked(str, i).groups.get(str2);
                if (notificationChannelGroup == null) {
                    return false;
                }
                return notificationChannelGroup.isBlocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    int getPackagePriority(java.lang.String str, int i) {
        int i2;
        synchronized (this.mPackagePreferences) {
            i2 = getOrCreatePackagePreferencesLocked(str, i).priority;
        }
        return i2;
    }

    int getPackageVisibility(java.lang.String str, int i) {
        int i2;
        synchronized (this.mPackagePreferences) {
            i2 = getOrCreatePackagePreferencesLocked(str, i).visibility;
        }
        return i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x007b A[Catch: all -> 0x0034, TryCatch #0 {all -> 0x0034, blocks: (B:6:0x001d, B:8:0x0023, B:12:0x0030, B:13:0x0037, B:15:0x0046, B:17:0x004f, B:18:0x0065, B:20:0x006f, B:21:0x0075, B:23:0x007b, B:27:0x0090, B:30:0x0099, B:33:0x00a2, B:34:0x00ab, B:41:0x00b6, B:42:0x00bd, B:43:0x00be, B:44:0x00c5), top: B:5:0x001d }] */
    @Override // com.android.server.notification.RankingConfig
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void createNotificationChannelGroup(java.lang.String str, int i, android.app.NotificationChannelGroup notificationChannelGroup, boolean z, int i2, boolean z2) {
        boolean z3;
        boolean z4;
        boolean z5;
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(notificationChannelGroup);
        java.util.Objects.requireNonNull(notificationChannelGroup.getId());
        if (android.text.TextUtils.isEmpty(notificationChannelGroup.getName())) {
            throw new java.lang.IllegalArgumentException("group.getName() can't be empty");
        }
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = getOrCreatePackagePreferencesLocked(str, i);
                if (orCreatePackagePreferencesLocked == null) {
                    throw new java.lang.IllegalArgumentException("Invalid package");
                }
                if (orCreatePackagePreferencesLocked.groups.size() >= 6000) {
                    throw new java.lang.IllegalStateException("Limit exceed; cannot create more groups");
                }
                if (z) {
                    notificationChannelGroup.setBlocked(false);
                }
                android.app.NotificationChannelGroup notificationChannelGroup2 = orCreatePackagePreferencesLocked.groups.get(notificationChannelGroup.getId());
                if (notificationChannelGroup2 != null) {
                    notificationChannelGroup.setChannels(notificationChannelGroup2.getChannels());
                    if (z) {
                        notificationChannelGroup.setBlocked(notificationChannelGroup2.isBlocked());
                        notificationChannelGroup.unlockFields(notificationChannelGroup.getUserLockedFields());
                        notificationChannelGroup.lockFields(notificationChannelGroup2.getUserLockedFields());
                    } else if (notificationChannelGroup.isBlocked() != notificationChannelGroup2.isBlocked()) {
                        notificationChannelGroup.lockFields(1);
                        z3 = true;
                        if (!notificationChannelGroup.equals(notificationChannelGroup2)) {
                            com.android.internal.logging.MetricsLogger.action(getChannelGroupLog(notificationChannelGroup.getId(), str));
                            com.android.server.notification.NotificationChannelLogger notificationChannelLogger = this.mNotificationChannelLogger;
                            if (notificationChannelGroup2 != null) {
                                z4 = false;
                            } else {
                                z4 = true;
                            }
                            if (notificationChannelGroup2 == null || !notificationChannelGroup2.isBlocked()) {
                                z5 = false;
                            } else {
                                z5 = true;
                            }
                            notificationChannelLogger.logNotificationChannelGroup(notificationChannelGroup, i, str, z4, z5);
                        }
                        orCreatePackagePreferencesLocked.groups.put(notificationChannelGroup.getId(), notificationChannelGroup);
                    }
                }
                z3 = false;
                if (!notificationChannelGroup.equals(notificationChannelGroup2)) {
                }
                orCreatePackagePreferencesLocked.groups.put(notificationChannelGroup.getId(), notificationChannelGroup);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z3) {
            updateCurrentUserHasChannelsBypassingDnd(i2, z2);
        }
    }

    @Override // com.android.server.notification.RankingConfig
    public boolean createNotificationChannel(java.lang.String str, int i, android.app.NotificationChannel notificationChannel, boolean z, boolean z2, int i2, boolean z3) {
        boolean z4;
        int i3;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean canBypassDnd;
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(notificationChannel);
        java.util.Objects.requireNonNull(notificationChannel.getId());
        boolean z9 = true;
        com.android.internal.util.Preconditions.checkArgument(!android.text.TextUtils.isEmpty(notificationChannel.getName()));
        com.android.internal.util.Preconditions.checkArgument(notificationChannel.getImportance() >= 0 && notificationChannel.getImportance() <= 5, "Invalid importance level");
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = getOrCreatePackagePreferencesLocked(str, i);
                if (orCreatePackagePreferencesLocked == null) {
                    throw new java.lang.IllegalArgumentException("Invalid package");
                }
                if (notificationChannel.getGroup() != null && !orCreatePackagePreferencesLocked.groups.containsKey(notificationChannel.getGroup())) {
                    throw new java.lang.IllegalArgumentException("NotificationChannelGroup doesn't exist");
                }
                if ("miscellaneous".equals(notificationChannel.getId())) {
                    throw new java.lang.IllegalArgumentException("Reserved id");
                }
                android.app.NotificationChannel notificationChannel2 = orCreatePackagePreferencesLocked.channels.get(notificationChannel.getId());
                if (notificationChannel2 != null && z) {
                    if (!notificationChannel2.isDeleted()) {
                        z5 = false;
                        z6 = false;
                    } else {
                        notificationChannel2.setDeleted(false);
                        notificationChannel2.setDeletedTimeMs(-1L);
                        com.android.internal.logging.MetricsLogger.action(getChannelLog(notificationChannel, str).setType(1));
                        this.mNotificationChannelLogger.logNotificationChannelCreated(notificationChannel, i, str);
                        z5 = true;
                        z6 = true;
                    }
                    if (!java.util.Objects.equals(notificationChannel.getName().toString(), notificationChannel2.getName().toString())) {
                        notificationChannel2.setName(notificationChannel.getName().toString());
                        z6 = true;
                    }
                    if (!java.util.Objects.equals(notificationChannel.getDescription(), notificationChannel2.getDescription())) {
                        notificationChannel2.setDescription(notificationChannel.getDescription());
                        z6 = true;
                    }
                    if (notificationChannel.isBlockable() != notificationChannel2.isBlockable()) {
                        notificationChannel2.setBlockable(notificationChannel.isBlockable());
                        z6 = true;
                    }
                    if (notificationChannel.getGroup() != null && notificationChannel2.getGroup() == null) {
                        notificationChannel2.setGroup(notificationChannel.getGroup());
                        z6 = true;
                    }
                    int importance = notificationChannel2.getImportance();
                    int loggingImportance = com.android.server.notification.NotificationChannelLogger.getLoggingImportance(notificationChannel2);
                    if (notificationChannel2.getUserLockedFields() == 0 && notificationChannel.getImportance() < notificationChannel2.getImportance()) {
                        notificationChannel2.setImportance(notificationChannel.getImportance());
                        z6 = true;
                    }
                    if (notificationChannel2.getUserLockedFields() == 0 && z2 && ((canBypassDnd = notificationChannel.canBypassDnd()) != notificationChannel2.canBypassDnd() || z5)) {
                        notificationChannel2.setBypassDnd(canBypassDnd);
                        if (canBypassDnd == this.mCurrentUserHasChannelsBypassingDnd && importance == notificationChannel2.getImportance()) {
                            z4 = false;
                            z7 = true;
                        }
                        z7 = true;
                        z4 = true;
                    } else {
                        boolean z10 = z6;
                        z4 = false;
                        z7 = z10;
                    }
                    if (notificationChannel2.getOriginalImportance() != -1000) {
                        z8 = z7;
                    } else {
                        notificationChannel2.setOriginalImportance(notificationChannel.getImportance());
                        z8 = true;
                    }
                    if (z8) {
                        updateConfig();
                    }
                    if (z8 && !z5) {
                        this.mNotificationChannelLogger.logNotificationChannelModified(notificationChannel2, i, str, loggingImportance, false);
                    }
                    z9 = z8;
                } else {
                    if (orCreatePackagePreferencesLocked.channels.size() >= 5000) {
                        throw new java.lang.IllegalStateException("Limit exceed; cannot create more channels");
                    }
                    if (z && !z2) {
                        notificationChannel.setBypassDnd(orCreatePackagePreferencesLocked.priority == 2);
                    }
                    if (z) {
                        notificationChannel.setLockscreenVisibility(orCreatePackagePreferencesLocked.visibility);
                        if (notificationChannel2 != null) {
                            i3 = notificationChannel2.getAllowBubbles();
                        } else {
                            i3 = -1;
                        }
                        notificationChannel.setAllowBubbles(i3);
                        notificationChannel.setImportantConversation(false);
                    }
                    clearLockedFieldsLocked(notificationChannel);
                    notificationChannel.setImportanceLockedByCriticalDeviceFunction(orCreatePackagePreferencesLocked.defaultAppLockedImportance || orCreatePackagePreferencesLocked.fixedImportance);
                    if (notificationChannel.getLockscreenVisibility() == 1) {
                        notificationChannel.setLockscreenVisibility(-1000);
                    }
                    if (!orCreatePackagePreferencesLocked.showBadge) {
                        notificationChannel.setShowBadge(false);
                    }
                    notificationChannel.setOriginalImportance(notificationChannel.getImportance());
                    if (notificationChannel.getParentChannelId() != null) {
                        com.android.internal.util.Preconditions.checkArgument(orCreatePackagePreferencesLocked.channels.containsKey(notificationChannel.getParentChannelId()), "Tried to create a conversation channel without a preexisting parent");
                    }
                    orCreatePackagePreferencesLocked.channels.put(notificationChannel.getId(), notificationChannel);
                    boolean z11 = notificationChannel.canBypassDnd() != this.mCurrentUserHasChannelsBypassingDnd;
                    com.android.internal.logging.MetricsLogger.action(getChannelLog(notificationChannel, str).setType(1));
                    this.mNotificationChannelLogger.logNotificationChannelCreated(notificationChannel, i, str);
                    z4 = z11;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z4) {
            updateCurrentUserHasChannelsBypassingDnd(i2, z3);
        }
        return z9;
    }

    void clearLockedFieldsLocked(android.app.NotificationChannel notificationChannel) {
        notificationChannel.unlockFields(notificationChannel.getUserLockedFields());
    }

    void unlockNotificationChannelImportance(java.lang.String str, int i, java.lang.String str2) {
        java.util.Objects.requireNonNull(str2);
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = getOrCreatePackagePreferencesLocked(str, i);
                if (orCreatePackagePreferencesLocked == null) {
                    throw new java.lang.IllegalArgumentException("Invalid package");
                }
                android.app.NotificationChannel notificationChannel = orCreatePackagePreferencesLocked.channels.get(str2);
                if (notificationChannel == null || notificationChannel.isDeleted()) {
                    throw new java.lang.IllegalArgumentException("Channel does not exist");
                }
                notificationChannel.unlockFields(4);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.notification.RankingConfig
    public void updateNotificationChannel(java.lang.String str, int i, android.app.NotificationChannel notificationChannel, boolean z, int i2, boolean z2) {
        boolean z3;
        boolean z4;
        java.util.Objects.requireNonNull(notificationChannel);
        java.util.Objects.requireNonNull(notificationChannel.getId());
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = getOrCreatePackagePreferencesLocked(str, i);
                if (orCreatePackagePreferencesLocked == null) {
                    throw new java.lang.IllegalArgumentException("Invalid package");
                }
                android.app.NotificationChannel notificationChannel2 = orCreatePackagePreferencesLocked.channels.get(notificationChannel.getId());
                if (notificationChannel2 == null || notificationChannel2.isDeleted()) {
                    throw new java.lang.IllegalArgumentException("Channel does not exist");
                }
                if (notificationChannel.getLockscreenVisibility() == 1) {
                    notificationChannel.setLockscreenVisibility(-1000);
                }
                if (z) {
                    notificationChannel.lockFields(notificationChannel2.getUserLockedFields());
                    lockFieldsForUpdateLocked(notificationChannel2, notificationChannel);
                } else {
                    notificationChannel.unlockFields(notificationChannel.getUserLockedFields());
                }
                if (notificationChannel2.isImportanceLockedByCriticalDeviceFunction() && !notificationChannel2.isBlockable() && notificationChannel2.getImportance() != 0) {
                    notificationChannel.setImportance(notificationChannel2.getImportance());
                }
                orCreatePackagePreferencesLocked.channels.put(notificationChannel.getId(), notificationChannel);
                if (!onlyHasDefaultChannel(str, i)) {
                    z3 = false;
                } else {
                    orCreatePackagePreferencesLocked.priority = notificationChannel.canBypassDnd() ? 2 : 0;
                    orCreatePackagePreferencesLocked.visibility = notificationChannel.getLockscreenVisibility();
                    orCreatePackagePreferencesLocked.showBadge = notificationChannel.canShowBadge();
                    z3 = true;
                }
                if (!notificationChannel2.equals(notificationChannel)) {
                    com.android.internal.logging.MetricsLogger.action(getChannelLog(notificationChannel, str).setSubtype(z ? 1 : 0));
                    this.mNotificationChannelLogger.logNotificationChannelModified(notificationChannel, i, str, com.android.server.notification.NotificationChannelLogger.getLoggingImportance(notificationChannel2), z);
                    z3 = true;
                }
                if (z && com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.getResolver().isEnabled(com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.NotificationFlags.PROPAGATE_CHANNEL_UPDATES_TO_CONVERSATIONS)) {
                    updateChildrenConversationChannels(orCreatePackagePreferencesLocked, notificationChannel2, notificationChannel);
                }
                z4 = (notificationChannel.canBypassDnd() == this.mCurrentUserHasChannelsBypassingDnd && notificationChannel2.getImportance() == notificationChannel.getImportance()) ? false : true;
                z3 = true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z4) {
            updateCurrentUserHasChannelsBypassingDnd(i2, z2);
        }
        if (z3) {
            updateConfig();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPackagePreferences"})
    private void updateChildrenConversationChannels(@android.annotation.NonNull com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferences, @android.annotation.NonNull android.app.NotificationChannel notificationChannel, @android.annotation.NonNull android.app.NotificationChannel notificationChannel2) {
        if (notificationChannel.equals(notificationChannel2) || notificationChannel.isConversation()) {
            return;
        }
        for (android.app.NotificationChannel notificationChannel3 : packagePreferences.channels.values()) {
            if (notificationChannel3.isConversation() && notificationChannel.getId().equals(notificationChannel3.getParentChannelId())) {
                maybeUpdateChildConversationChannel(packagePreferences.pkg, packagePreferences.uid, notificationChannel3, notificationChannel, notificationChannel2);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPackagePreferences"})
    private void maybeUpdateChildConversationChannel(java.lang.String str, int i, @android.annotation.NonNull android.app.NotificationChannel notificationChannel, @android.annotation.NonNull android.app.NotificationChannel notificationChannel2, @android.annotation.NonNull android.app.NotificationChannel notificationChannel3) {
        boolean z;
        int loggingImportance = com.android.server.notification.NotificationChannelLogger.getLoggingImportance(notificationChannel);
        if ((notificationChannel.getUserLockedFields() & 1) == 0 && notificationChannel2.canBypassDnd() != notificationChannel3.canBypassDnd()) {
            notificationChannel.setBypassDnd(notificationChannel3.canBypassDnd());
            z = true;
        } else {
            z = false;
        }
        if ((notificationChannel.getUserLockedFields() & 2) == 0 && notificationChannel2.getLockscreenVisibility() != notificationChannel3.getLockscreenVisibility()) {
            notificationChannel.setLockscreenVisibility(notificationChannel3.getLockscreenVisibility());
            z = true;
        }
        if ((notificationChannel.getUserLockedFields() & 4) == 0 && notificationChannel2.getImportance() != notificationChannel3.getImportance()) {
            notificationChannel.setImportance(notificationChannel3.getImportance());
            z = true;
        }
        if ((notificationChannel.getUserLockedFields() & 8) == 0 && (notificationChannel2.shouldShowLights() != notificationChannel3.shouldShowLights() || notificationChannel2.getLightColor() != notificationChannel3.getLightColor())) {
            notificationChannel.enableLights(notificationChannel3.shouldShowLights());
            notificationChannel.setLightColor(notificationChannel3.getLightColor());
            z = true;
        }
        if ((notificationChannel.getUserLockedFields() & 32) == 0 && !java.util.Objects.equals(notificationChannel2.getSound(), notificationChannel3.getSound())) {
            notificationChannel.setSound(notificationChannel3.getSound(), notificationChannel3.getAudioAttributes());
            z = true;
        }
        if ((notificationChannel.getUserLockedFields() & 16) == 0 && (!java.util.Arrays.equals(notificationChannel2.getVibrationPattern(), notificationChannel3.getVibrationPattern()) || !java.util.Objects.equals(notificationChannel2.getVibrationEffect(), notificationChannel3.getVibrationEffect()) || notificationChannel2.shouldVibrate() != notificationChannel3.shouldVibrate())) {
            notificationChannel.setVibrationPattern(notificationChannel3.getVibrationPattern());
            notificationChannel.enableVibration(notificationChannel3.shouldVibrate());
            z = true;
        }
        if ((notificationChannel.getUserLockedFields() & 128) == 0 && notificationChannel2.canShowBadge() != notificationChannel3.canShowBadge()) {
            notificationChannel.setShowBadge(notificationChannel3.canShowBadge());
            z = true;
        }
        if ((notificationChannel.getUserLockedFields() & 256) == 0 && notificationChannel2.getAllowBubbles() != notificationChannel3.getAllowBubbles()) {
            notificationChannel.setAllowBubbles(notificationChannel3.getAllowBubbles());
            z = true;
        }
        if (z) {
            com.android.internal.logging.MetricsLogger.action(getChannelLog(notificationChannel, str).setSubtype(1));
            this.mNotificationChannelLogger.logNotificationChannelModified(notificationChannel, i, str, loggingImportance, true);
        }
    }

    @Override // com.android.server.notification.RankingConfig
    public android.app.NotificationChannel getNotificationChannel(java.lang.String str, int i, java.lang.String str2, boolean z) {
        java.util.Objects.requireNonNull(str);
        return getConversationNotificationChannel(str, i, str2, null, true, z);
    }

    @Override // com.android.server.notification.RankingConfig
    public android.app.NotificationChannel getConversationNotificationChannel(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, boolean z, boolean z2) {
        android.app.NotificationChannel notificationChannel;
        com.android.internal.util.Preconditions.checkNotNull(str);
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = getOrCreatePackagePreferencesLocked(str, i);
                android.app.NotificationChannel notificationChannel2 = null;
                if (orCreatePackagePreferencesLocked == null) {
                    return null;
                }
                if (str2 == null) {
                    str2 = "miscellaneous";
                }
                if (str3 != null) {
                    notificationChannel2 = findConversationChannel(orCreatePackagePreferencesLocked, str2, str3, z2);
                }
                return (notificationChannel2 != null || !z || (notificationChannel = orCreatePackagePreferencesLocked.channels.get(str2)) == null || (!z2 && notificationChannel.isDeleted())) ? notificationChannel2 : notificationChannel;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private android.app.NotificationChannel findConversationChannel(com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferences, java.lang.String str, java.lang.String str2, boolean z) {
        for (android.app.NotificationChannel notificationChannel : packagePreferences.channels.values()) {
            if (str2.equals(notificationChannel.getConversationId()) && str.equals(notificationChannel.getParentChannelId()) && (z || !notificationChannel.isDeleted())) {
                return notificationChannel;
            }
        }
        return null;
    }

    public java.util.List<android.app.NotificationChannel> getNotificationChannelsByConversationId(java.lang.String str, int i, java.lang.String str2) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        com.android.internal.util.Preconditions.checkNotNull(str2);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = getOrCreatePackagePreferencesLocked(str, i);
                if (orCreatePackagePreferencesLocked == null) {
                    return arrayList;
                }
                for (android.app.NotificationChannel notificationChannel : orCreatePackagePreferencesLocked.channels.values()) {
                    if (str2.equals(notificationChannel.getConversationId()) && !notificationChannel.isDeleted()) {
                        arrayList.add(notificationChannel);
                    }
                }
                return arrayList;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.notification.RankingConfig
    public boolean deleteNotificationChannel(java.lang.String str, int i, java.lang.String str2, int i2, boolean z) {
        boolean z2;
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
                boolean z3 = false;
                if (packagePreferencesLocked == null) {
                    return false;
                }
                android.app.NotificationChannel notificationChannel = packagePreferencesLocked.channels.get(str2);
                if (notificationChannel == null) {
                    z2 = false;
                } else {
                    z3 = notificationChannel.canBypassDnd();
                    z2 = deleteNotificationChannelLocked(notificationChannel, str, i);
                }
                if (z3) {
                    updateCurrentUserHasChannelsBypassingDnd(i2, z);
                }
                return z2;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean deleteNotificationChannelLocked(android.app.NotificationChannel notificationChannel, java.lang.String str, int i) {
        if (!notificationChannel.isDeleted()) {
            notificationChannel.setDeleted(true);
            notificationChannel.setDeletedTimeMs(java.lang.System.currentTimeMillis());
            android.metrics.LogMaker channelLog = getChannelLog(notificationChannel, str);
            channelLog.setType(2);
            com.android.internal.logging.MetricsLogger.action(channelLog);
            this.mNotificationChannelLogger.logNotificationChannelDeleted(notificationChannel, i, str);
            return true;
        }
        return false;
    }

    @Override // com.android.server.notification.RankingConfig
    @com.android.internal.annotations.VisibleForTesting
    public void permanentlyDeleteNotificationChannel(java.lang.String str, int i, java.lang.String str2) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(str2);
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
                if (packagePreferencesLocked == null) {
                    return;
                }
                packagePreferencesLocked.channels.remove(str2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.notification.RankingConfig
    public void permanentlyDeleteNotificationChannels(java.lang.String str, int i) {
        java.util.Objects.requireNonNull(str);
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
                if (packagePreferencesLocked == null) {
                    return;
                }
                for (int size = packagePreferencesLocked.channels.size() - 1; size >= 0; size--) {
                    java.lang.String keyAt = packagePreferencesLocked.channels.keyAt(size);
                    if (!"miscellaneous".equals(keyAt)) {
                        packagePreferencesLocked.channels.remove(keyAt);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean shouldHideSilentStatusIcons() {
        return this.mHideSilentStatusBarIcons;
    }

    public void setHideSilentStatusIcons(boolean z) {
        this.mHideSilentStatusBarIcons = z;
    }

    public void updateFixedImportance(java.util.List<android.content.pm.UserInfo> list) {
        for (android.content.pm.UserInfo userInfo : list) {
            for (android.content.pm.PackageInfo packageInfo : this.mPm.getInstalledPackagesAsUser(android.content.pm.PackageManager.PackageInfoFlags.of(1048576L), userInfo.getUserHandle().getIdentifier())) {
                if (this.mPermissionHelper.isPermissionFixed(packageInfo.packageName, userInfo.getUserHandle().getIdentifier())) {
                    synchronized (this.mPackagePreferences) {
                        try {
                            com.android.server.notification.PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = getOrCreatePackagePreferencesLocked(packageInfo.packageName, packageInfo.applicationInfo.uid);
                            orCreatePackagePreferencesLocked.fixedImportance = true;
                            java.util.Iterator<android.app.NotificationChannel> it = orCreatePackagePreferencesLocked.channels.values().iterator();
                            while (it.hasNext()) {
                                it.next().setImportanceLockedByCriticalDeviceFunction(true);
                            }
                        } finally {
                        }
                    }
                }
            }
        }
    }

    public void updateDefaultApps(int i, android.util.ArraySet<java.lang.String> arraySet, android.util.ArraySet<android.util.Pair<java.lang.String, java.lang.Integer>> arraySet2) {
        synchronized (this.mPackagePreferences) {
            try {
                for (com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferences : this.mPackagePreferences.values()) {
                    if (i == android.os.UserHandle.getUserId(packagePreferences.uid) && arraySet != null && arraySet.contains(packagePreferences.pkg)) {
                        packagePreferences.defaultAppLockedImportance = false;
                        if (!packagePreferences.fixedImportance) {
                            java.util.Iterator<android.app.NotificationChannel> it = packagePreferences.channels.values().iterator();
                            while (it.hasNext()) {
                                it.next().setImportanceLockedByCriticalDeviceFunction(false);
                            }
                        }
                    }
                }
                if (arraySet2 != null) {
                    java.util.Iterator<android.util.Pair<java.lang.String, java.lang.Integer>> it2 = arraySet2.iterator();
                    while (it2.hasNext()) {
                        android.util.Pair<java.lang.String, java.lang.Integer> next = it2.next();
                        com.android.server.notification.PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = getOrCreatePackagePreferencesLocked((java.lang.String) next.first, ((java.lang.Integer) next.second).intValue());
                        orCreatePackagePreferencesLocked.defaultAppLockedImportance = true;
                        java.util.Iterator<android.app.NotificationChannel> it3 = orCreatePackagePreferencesLocked.channels.values().iterator();
                        while (it3.hasNext()) {
                            it3.next().setImportanceLockedByCriticalDeviceFunction(true);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.app.NotificationChannelGroup getNotificationChannelGroupWithChannels(java.lang.String str, int i, java.lang.String str2, boolean z) {
        java.util.Objects.requireNonNull(str);
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
                if (packagePreferencesLocked == null || str2 == null || !packagePreferencesLocked.groups.containsKey(str2)) {
                    return null;
                }
                android.app.NotificationChannelGroup clone = packagePreferencesLocked.groups.get(str2).clone();
                clone.setChannels(new java.util.ArrayList());
                int size = packagePreferencesLocked.channels.size();
                for (int i2 = 0; i2 < size; i2++) {
                    android.app.NotificationChannel valueAt = packagePreferencesLocked.channels.valueAt(i2);
                    if ((z || !valueAt.isDeleted()) && str2.equals(valueAt.getGroup())) {
                        clone.addChannel(valueAt);
                    }
                }
                return clone;
            } finally {
            }
        }
    }

    public android.app.NotificationChannelGroup getNotificationChannelGroup(java.lang.String str, java.lang.String str2, int i) {
        java.util.Objects.requireNonNull(str2);
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str2, i);
                if (packagePreferencesLocked == null) {
                    return null;
                }
                return packagePreferencesLocked.groups.get(str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0059 A[Catch: all -> 0x001a, TryCatch #0 {all -> 0x001a, blocks: (B:4:0x000e, B:6:0x0014, B:7:0x0018, B:10:0x001d, B:12:0x002d, B:14:0x0037, B:18:0x0059, B:20:0x005f, B:22:0x006b, B:24:0x0077, B:25:0x0096, B:29:0x009a, B:27:0x009d, B:34:0x0043, B:36:0x0049, B:42:0x00a2, B:44:0x00ac, B:46:0x00b1, B:47:0x00bb, B:49:0x00c1, B:51:0x00d1, B:56:0x00d9, B:57:0x00e7), top: B:3:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x009d A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.content.pm.ParceledListSlice<android.app.NotificationChannelGroup> getNotificationChannelGroups(java.lang.String str, int i, boolean z, boolean z2, boolean z3, boolean z4, java.util.Set<java.lang.String> set) {
        boolean z5;
        java.util.Objects.requireNonNull(str);
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
                if (packagePreferencesLocked == null) {
                    return android.content.pm.ParceledListSlice.emptyList();
                }
                android.app.NotificationChannelGroup notificationChannelGroup = new android.app.NotificationChannelGroup(null, null);
                int size = packagePreferencesLocked.channels.size();
                for (int i2 = 0; i2 < size; i2++) {
                    android.app.NotificationChannel valueAt = packagePreferencesLocked.channels.valueAt(i2);
                    if ((!z && valueAt.isDeleted()) || (set != null && ((!z4 || valueAt.getImportance() != 0) && !set.contains(valueAt.getId())))) {
                        z5 = false;
                        if (z5) {
                            if (valueAt.getGroup() != null) {
                                if (packagePreferencesLocked.groups.get(valueAt.getGroup()) != null) {
                                    android.app.NotificationChannelGroup notificationChannelGroup2 = (android.app.NotificationChannelGroup) arrayMap.get(valueAt.getGroup());
                                    if (notificationChannelGroup2 == null) {
                                        notificationChannelGroup2 = packagePreferencesLocked.groups.get(valueAt.getGroup()).clone();
                                        notificationChannelGroup2.setChannels(new java.util.ArrayList());
                                        arrayMap.put(valueAt.getGroup(), notificationChannelGroup2);
                                    }
                                    notificationChannelGroup2.addChannel(valueAt);
                                }
                            } else {
                                notificationChannelGroup.addChannel(valueAt);
                            }
                        }
                    }
                    z5 = true;
                    if (z5) {
                    }
                }
                if (z2 && notificationChannelGroup.getChannels().size() > 0) {
                    arrayMap.put(null, notificationChannelGroup);
                }
                if (z3) {
                    for (android.app.NotificationChannelGroup notificationChannelGroup3 : packagePreferencesLocked.groups.values()) {
                        if (!arrayMap.containsKey(notificationChannelGroup3.getId())) {
                            arrayMap.put(notificationChannelGroup3.getId(), notificationChannelGroup3);
                        }
                    }
                }
                return new android.content.pm.ParceledListSlice<>(new java.util.ArrayList(arrayMap.values()));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public java.util.List<android.app.NotificationChannel> deleteNotificationChannelGroup(java.lang.String str, int i, java.lang.String str2, int i2, boolean z) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
                if (packagePreferencesLocked == null || android.text.TextUtils.isEmpty(str2)) {
                    return arrayList;
                }
                android.app.NotificationChannelGroup remove = packagePreferencesLocked.groups.remove(str2);
                if (remove != null) {
                    this.mNotificationChannelLogger.logNotificationChannelGroupDeleted(remove, i, str);
                }
                int size = packagePreferencesLocked.channels.size();
                boolean z2 = false;
                for (int i3 = 0; i3 < size; i3++) {
                    android.app.NotificationChannel valueAt = packagePreferencesLocked.channels.valueAt(i3);
                    if (str2.equals(valueAt.getGroup())) {
                        z2 |= valueAt.canBypassDnd();
                        deleteNotificationChannelLocked(valueAt, str, i);
                        arrayList.add(valueAt);
                    }
                }
                if (z2) {
                    updateCurrentUserHasChannelsBypassingDnd(i2, z);
                }
                return arrayList;
            } finally {
            }
        }
    }

    @Override // com.android.server.notification.RankingConfig
    public java.util.Collection<android.app.NotificationChannelGroup> getNotificationChannelGroups(java.lang.String str, int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
                if (packagePreferencesLocked == null) {
                    return arrayList;
                }
                arrayList.addAll(packagePreferencesLocked.groups.values());
                return arrayList;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.app.NotificationChannelGroup getGroupForChannel(java.lang.String str, int i, java.lang.String str2) {
        android.app.NotificationChannel notificationChannel;
        android.app.NotificationChannelGroup notificationChannelGroup;
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
                if (packagePreferencesLocked == null || (notificationChannel = packagePreferencesLocked.channels.get(str2)) == null || notificationChannel.isDeleted() || notificationChannel.getGroup() == null || (notificationChannelGroup = packagePreferencesLocked.groups.get(notificationChannel.getGroup())) == null) {
                    return null;
                }
                return notificationChannelGroup;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00ad A[Catch: all -> 0x00a8, TryCatch #0 {all -> 0x00a8, blocks: (B:4:0x0003, B:5:0x0012, B:7:0x0018, B:9:0x002a, B:11:0x0034, B:13:0x0046, B:15:0x004c, B:17:0x0052, B:22:0x005a, B:25:0x0080, B:27:0x008a, B:29:0x0098, B:33:0x00ad, B:36:0x00a0, B:38:0x007c, B:21:0x00b0, B:46:0x00b6), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00b0 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public java.util.ArrayList<android.service.notification.ConversationChannelWrapper> getConversations(android.util.IntArray intArray, boolean z) {
        java.util.ArrayList<android.service.notification.ConversationChannelWrapper> arrayList;
        java.lang.CharSequence name;
        boolean z2;
        android.app.NotificationChannelGroup notificationChannelGroup;
        synchronized (this.mPackagePreferences) {
            try {
                arrayList = new java.util.ArrayList<>();
                for (com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferences : this.mPackagePreferences.values()) {
                    if (intArray.binarySearch(android.os.UserHandle.getUserId(packagePreferences.uid)) >= 0) {
                        int size = packagePreferences.channels.size();
                        for (int i = 0; i < size; i++) {
                            android.app.NotificationChannel valueAt = packagePreferences.channels.valueAt(i);
                            if (!android.text.TextUtils.isEmpty(valueAt.getConversationId()) && !valueAt.isDeleted() && !valueAt.isDemoted() && (valueAt.isImportantConversation() || !z)) {
                                android.service.notification.ConversationChannelWrapper conversationChannelWrapper = new android.service.notification.ConversationChannelWrapper();
                                conversationChannelWrapper.setPkg(packagePreferences.pkg);
                                conversationChannelWrapper.setUid(packagePreferences.uid);
                                conversationChannelWrapper.setNotificationChannel(valueAt);
                                android.app.NotificationChannel notificationChannel = packagePreferences.channels.get(valueAt.getParentChannelId());
                                if (notificationChannel == null) {
                                    name = null;
                                } else {
                                    name = notificationChannel.getName();
                                }
                                conversationChannelWrapper.setParentChannelLabel(name);
                                if (valueAt.getGroup() != null && (notificationChannelGroup = packagePreferences.groups.get(valueAt.getGroup())) != null) {
                                    if (notificationChannelGroup.isBlocked()) {
                                        z2 = true;
                                        if (z2) {
                                            arrayList.add(conversationChannelWrapper);
                                        }
                                    } else {
                                        conversationChannelWrapper.setGroupLabel(notificationChannelGroup.getName());
                                    }
                                }
                                z2 = false;
                                if (z2) {
                                }
                            }
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x008f A[Catch: all -> 0x0013, TryCatch #0 {all -> 0x0013, blocks: (B:4:0x0006, B:6:0x000c, B:7:0x0011, B:10:0x0016, B:12:0x0025, B:14:0x0037, B:16:0x003d, B:18:0x0043, B:20:0x006f, B:22:0x007d, B:26:0x008f, B:30:0x0085, B:28:0x0092, B:36:0x0095), top: B:3:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0092 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public java.util.ArrayList<android.service.notification.ConversationChannelWrapper> getConversations(java.lang.String str, int i) {
        boolean z;
        android.app.NotificationChannelGroup notificationChannelGroup;
        java.util.Objects.requireNonNull(str);
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
                if (packagePreferencesLocked == null) {
                    return new java.util.ArrayList<>();
                }
                java.util.ArrayList<android.service.notification.ConversationChannelWrapper> arrayList = new java.util.ArrayList<>();
                int size = packagePreferencesLocked.channels.size();
                for (int i2 = 0; i2 < size; i2++) {
                    android.app.NotificationChannel valueAt = packagePreferencesLocked.channels.valueAt(i2);
                    if (!android.text.TextUtils.isEmpty(valueAt.getConversationId()) && !valueAt.isDeleted() && !valueAt.isDemoted()) {
                        android.service.notification.ConversationChannelWrapper conversationChannelWrapper = new android.service.notification.ConversationChannelWrapper();
                        conversationChannelWrapper.setPkg(packagePreferencesLocked.pkg);
                        conversationChannelWrapper.setUid(packagePreferencesLocked.uid);
                        conversationChannelWrapper.setNotificationChannel(valueAt);
                        conversationChannelWrapper.setParentChannelLabel(packagePreferencesLocked.channels.get(valueAt.getParentChannelId()).getName());
                        if (valueAt.getGroup() != null && (notificationChannelGroup = packagePreferencesLocked.groups.get(valueAt.getGroup())) != null) {
                            if (notificationChannelGroup.isBlocked()) {
                                z = true;
                                if (z) {
                                    arrayList.add(conversationChannelWrapper);
                                }
                            } else {
                                conversationChannelWrapper.setGroupLabel(notificationChannelGroup.getName());
                            }
                        }
                        z = false;
                        if (z) {
                        }
                    }
                }
                return arrayList;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    public java.util.List<java.lang.String> deleteConversations(java.lang.String str, int i, java.util.Set<java.lang.String> set, int i2, boolean z) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
                if (packagePreferencesLocked == null) {
                    return arrayList;
                }
                int size = packagePreferencesLocked.channels.size();
                for (int i3 = 0; i3 < size; i3++) {
                    android.app.NotificationChannel valueAt = packagePreferencesLocked.channels.valueAt(i3);
                    if (valueAt.getConversationId() != null && set.contains(valueAt.getConversationId())) {
                        valueAt.setDeleted(true);
                        valueAt.setDeletedTimeMs(java.lang.System.currentTimeMillis());
                        android.metrics.LogMaker channelLog = getChannelLog(valueAt, str);
                        channelLog.setType(2);
                        com.android.internal.logging.MetricsLogger.action(channelLog);
                        this.mNotificationChannelLogger.logNotificationChannelDeleted(valueAt, i, str);
                        arrayList.add(valueAt.getId());
                    }
                }
                if (!arrayList.isEmpty() && this.mCurrentUserHasChannelsBypassingDnd) {
                    updateCurrentUserHasChannelsBypassingDnd(i2, z);
                }
                return arrayList;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.notification.RankingConfig
    public android.content.pm.ParceledListSlice<android.app.NotificationChannel> getNotificationChannels(java.lang.String str, int i, boolean z) {
        java.util.Objects.requireNonNull(str);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
                if (packagePreferencesLocked == null) {
                    return android.content.pm.ParceledListSlice.emptyList();
                }
                int size = packagePreferencesLocked.channels.size();
                for (int i2 = 0; i2 < size; i2++) {
                    android.app.NotificationChannel valueAt = packagePreferencesLocked.channels.valueAt(i2);
                    if (z || !valueAt.isDeleted()) {
                        arrayList.add(valueAt);
                    }
                }
                return new android.content.pm.ParceledListSlice<>(arrayList);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.content.pm.ParceledListSlice<android.app.NotificationChannel> getNotificationChannelsBypassingDnd(java.lang.String str, int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferences = this.mPackagePreferences.get(packagePreferencesKey(str, i));
                if (packagePreferences != null) {
                    for (android.app.NotificationChannel notificationChannel : packagePreferences.channels.values()) {
                        if (channelIsLiveLocked(packagePreferences, notificationChannel) && notificationChannel.canBypassDnd()) {
                            arrayList.add(notificationChannel);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return new android.content.pm.ParceledListSlice<>(arrayList);
    }

    public boolean onlyHasDefaultChannel(java.lang.String str, int i) {
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = getOrCreatePackagePreferencesLocked(str, i);
                return orCreatePackagePreferencesLocked.channels.size() == 1 && orCreatePackagePreferencesLocked.channels.containsKey("miscellaneous");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getDeletedChannelCount(java.lang.String str, int i) {
        java.util.Objects.requireNonNull(str);
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
                if (packagePreferencesLocked == null) {
                    return 0;
                }
                int size = packagePreferencesLocked.channels.size();
                int i2 = 0;
                for (int i3 = 0; i3 < size; i3++) {
                    if (packagePreferencesLocked.channels.valueAt(i3).isDeleted()) {
                        i2++;
                    }
                }
                return i2;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getBlockedChannelCount(java.lang.String str, int i) {
        java.util.Objects.requireNonNull(str);
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
                if (packagePreferencesLocked == null) {
                    return 0;
                }
                int size = packagePreferencesLocked.channels.size();
                int i2 = 0;
                for (int i3 = 0; i3 < size; i3++) {
                    android.app.NotificationChannel valueAt = packagePreferencesLocked.channels.valueAt(i3);
                    if (!valueAt.isDeleted() && valueAt.getImportance() == 0) {
                        i2++;
                    }
                }
                return i2;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void syncChannelsBypassingDnd() {
        this.mCurrentUserHasChannelsBypassingDnd = (this.mZenModeHelper.getNotificationPolicy().state & 1) != 0;
        updateCurrentUserHasChannelsBypassingDnd(1000, true);
    }

    private void updateCurrentUserHasChannelsBypassingDnd(int i, boolean z) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        android.util.IntArray currentProfileIds = this.mUserProfiles.getCurrentProfileIds();
        synchronized (this.mPackagePreferences) {
            try {
                int size = this.mPackagePreferences.size();
                for (int i2 = 0; i2 < size; i2++) {
                    com.android.server.notification.PreferencesHelper.PackagePreferences valueAt = this.mPackagePreferences.valueAt(i2);
                    if (currentProfileIds.contains(android.os.UserHandle.getUserId(valueAt.uid))) {
                        java.util.Iterator<android.app.NotificationChannel> it = valueAt.channels.values().iterator();
                        while (true) {
                            if (it.hasNext()) {
                                android.app.NotificationChannel next = it.next();
                                if (channelIsLiveLocked(valueAt, next) && next.canBypassDnd()) {
                                    arraySet.add(new android.util.Pair(valueAt.pkg, java.lang.Integer.valueOf(valueAt.uid)));
                                    break;
                                }
                            }
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
            if (!this.mPermissionHelper.hasPermission(((java.lang.Integer) ((android.util.Pair) arraySet.valueAt(size2)).second).intValue())) {
                arraySet.removeAt(size2);
            }
        }
        boolean z2 = arraySet.size() > 0;
        if (this.mCurrentUserHasChannelsBypassingDnd != z2) {
            this.mCurrentUserHasChannelsBypassingDnd = z2;
            updateZenPolicy(this.mCurrentUserHasChannelsBypassingDnd, i, z);
        }
    }

    private boolean channelIsLiveLocked(com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferences, android.app.NotificationChannel notificationChannel) {
        return (isGroupBlocked(packagePreferences.pkg, packagePreferences.uid, notificationChannel.getGroup()) || notificationChannel.isDeleted() || notificationChannel.getImportance() == 0) ? false : true;
    }

    public void updateZenPolicy(boolean z, int i, boolean z2) {
        android.app.NotificationManager.Policy notificationPolicy = this.mZenModeHelper.getNotificationPolicy();
        this.mZenModeHelper.setNotificationPolicy(new android.app.NotificationManager.Policy(notificationPolicy.priorityCategories, notificationPolicy.priorityCallSenders, notificationPolicy.priorityMessageSenders, notificationPolicy.suppressedVisualEffects, z ? 1 : 0, notificationPolicy.priorityConversationSenders), z2 ? 5 : 4, i);
    }

    public boolean areChannelsBypassingDnd() {
        return this.mCurrentUserHasChannelsBypassingDnd;
    }

    public void setAppImportanceLocked(java.lang.String str, int i) {
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = getOrCreatePackagePreferencesLocked(str, i);
                if ((orCreatePackagePreferencesLocked.lockedAppFields & 1) != 0) {
                    return;
                }
                orCreatePackagePreferencesLocked.lockedAppFields |= 1;
                updateConfig();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public long getNotificationSoundTimeout(java.lang.String str, int i) {
        return getOrCreatePackagePreferencesLocked(str, i).soundTimeout;
    }

    public void setNotificationSoundTimeout(java.lang.String str, int i, long j) {
        getOrCreatePackagePreferencesLocked(str, i).soundTimeout = j;
        updateConfig();
    }

    @android.annotation.Nullable
    public java.lang.String getNotificationDelegate(java.lang.String str, int i) {
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
                if (packagePreferencesLocked == null || packagePreferencesLocked.delegate == null) {
                    return null;
                }
                if (!packagePreferencesLocked.delegate.mEnabled) {
                    return null;
                }
                return packagePreferencesLocked.delegate.mPkg;
            } finally {
            }
        }
    }

    public void setNotificationDelegate(java.lang.String str, int i, java.lang.String str2, int i2) {
        synchronized (this.mPackagePreferences) {
            getOrCreatePackagePreferencesLocked(str, i).delegate = new com.android.server.notification.PreferencesHelper.Delegate(str2, i2, true);
        }
    }

    public void revokeNotificationDelegate(java.lang.String str, int i) {
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
                if (packagePreferencesLocked != null && packagePreferencesLocked.delegate != null) {
                    packagePreferencesLocked.delegate.mEnabled = false;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isDelegateAllowed(java.lang.String str, int i, java.lang.String str2, int i2) {
        boolean z;
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
                z = packagePreferencesLocked != null && packagePreferencesLocked.isValidDelegate(str2, i2);
            } finally {
            }
        }
        return z;
    }

    private void lockFieldsForUpdateLocked(android.app.NotificationChannel notificationChannel, android.app.NotificationChannel notificationChannel2) {
        if (notificationChannel.canBypassDnd() != notificationChannel2.canBypassDnd()) {
            notificationChannel2.lockFields(1);
        }
        if (notificationChannel.getLockscreenVisibility() != notificationChannel2.getLockscreenVisibility()) {
            notificationChannel2.lockFields(2);
        }
        if (notificationChannel.getImportance() != notificationChannel2.getImportance()) {
            notificationChannel2.lockFields(4);
        }
        if (notificationChannel.shouldShowLights() != notificationChannel2.shouldShowLights() || notificationChannel.getLightColor() != notificationChannel2.getLightColor()) {
            notificationChannel2.lockFields(8);
        }
        if (!java.util.Objects.equals(notificationChannel.getSound(), notificationChannel2.getSound())) {
            notificationChannel2.lockFields(32);
        }
        if (!java.util.Arrays.equals(notificationChannel.getVibrationPattern(), notificationChannel2.getVibrationPattern()) || !java.util.Objects.equals(notificationChannel.getVibrationEffect(), notificationChannel2.getVibrationEffect()) || notificationChannel.shouldVibrate() != notificationChannel2.shouldVibrate()) {
            notificationChannel2.lockFields(16);
        }
        if (notificationChannel.canShowBadge() != notificationChannel2.canShowBadge()) {
            notificationChannel2.lockFields(128);
        }
        if (notificationChannel.getAllowBubbles() != notificationChannel2.getAllowBubbles()) {
            notificationChannel2.lockFields(256);
        }
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str, @android.annotation.NonNull com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter, android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.Pair<java.lang.Boolean, java.lang.Boolean>> arrayMap) {
        printWriter.print(str);
        printWriter.println("per-package config version: " + this.XML_VERSION);
        printWriter.println("PackagePreferences:");
        synchronized (this.mPackagePreferences) {
            dumpPackagePreferencesLocked(printWriter, str, dumpFilter, this.mPackagePreferences, arrayMap);
        }
        printWriter.println("Restored without uid:");
        dumpPackagePreferencesLocked(printWriter, str, dumpFilter, this.mRestoredWithoutUids, (android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.Pair<java.lang.Boolean, java.lang.Boolean>>) null);
    }

    public void dump(android.util.proto.ProtoOutputStream protoOutputStream, @android.annotation.NonNull com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter, android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.Pair<java.lang.Boolean, java.lang.Boolean>> arrayMap) {
        synchronized (this.mPackagePreferences) {
            dumpPackagePreferencesLocked(protoOutputStream, 2246267895810L, dumpFilter, this.mPackagePreferences, arrayMap);
        }
        dumpPackagePreferencesLocked(protoOutputStream, 2246267895811L, dumpFilter, this.mRestoredWithoutUids, (android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.Pair<java.lang.Boolean, java.lang.Boolean>>) null);
    }

    private void dumpPackagePreferencesLocked(java.io.PrintWriter printWriter, java.lang.String str, @android.annotation.NonNull com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter, android.util.ArrayMap<java.lang.String, com.android.server.notification.PreferencesHelper.PackagePreferences> arrayMap, android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.Pair<java.lang.Boolean, java.lang.Boolean>> arrayMap2) {
        java.util.Set<android.util.Pair<java.lang.Integer, java.lang.String>> set;
        if (arrayMap2 == null) {
            set = null;
        } else {
            set = arrayMap2.keySet();
        }
        int size = arrayMap.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            com.android.server.notification.PreferencesHelper.PackagePreferences valueAt = arrayMap.valueAt(i);
            if (dumpFilter.matches(valueAt.pkg)) {
                printWriter.print(str);
                printWriter.print("  AppSettings: ");
                printWriter.print(valueAt.pkg);
                printWriter.print(" (");
                printWriter.print(valueAt.uid != -10000 ? java.lang.Integer.toString(valueAt.uid) : "UNKNOWN_UID");
                printWriter.print(')');
                android.util.Pair pair = new android.util.Pair(java.lang.Integer.valueOf(valueAt.uid), valueAt.pkg);
                if (arrayMap2 != null && set.contains(pair)) {
                    printWriter.print(" importance=");
                    printWriter.print(android.service.notification.NotificationListenerService.Ranking.importanceToString(((java.lang.Boolean) arrayMap2.get(pair).first).booleanValue() ? 3 : 0));
                    printWriter.print(" userSet=");
                    printWriter.print(arrayMap2.get(pair).second);
                    set.remove(pair);
                }
                if (valueAt.priority != 0) {
                    printWriter.print(" priority=");
                    printWriter.print(android.app.Notification.priorityToString(valueAt.priority));
                }
                if (valueAt.visibility != -1000) {
                    printWriter.print(" visibility=");
                    printWriter.print(android.app.Notification.visibilityToString(valueAt.visibility));
                }
                if (!valueAt.showBadge) {
                    printWriter.print(" showBadge=");
                    printWriter.print(valueAt.showBadge);
                }
                if (valueAt.defaultAppLockedImportance) {
                    printWriter.print(" defaultAppLocked=");
                    printWriter.print(valueAt.defaultAppLockedImportance);
                }
                if (valueAt.fixedImportance) {
                    printWriter.print(" fixedImportance=");
                    printWriter.print(valueAt.fixedImportance);
                }
                printWriter.println();
                for (android.app.NotificationChannel notificationChannel : valueAt.channels.values()) {
                    printWriter.print(str);
                    notificationChannel.dump(printWriter, "    ", dumpFilter.redact);
                }
                for (android.app.NotificationChannelGroup notificationChannelGroup : valueAt.groups.values()) {
                    printWriter.print(str);
                    printWriter.print("  ");
                    printWriter.print("  ");
                    printWriter.println(notificationChannelGroup);
                }
            }
            i++;
        }
        if (set != null) {
            for (android.util.Pair<java.lang.Integer, java.lang.String> pair2 : set) {
                if (dumpFilter.matches((java.lang.String) pair2.second)) {
                    printWriter.print(str);
                    printWriter.print("  AppSettings: ");
                    printWriter.print((java.lang.String) pair2.second);
                    printWriter.print(" (");
                    printWriter.print(((java.lang.Integer) pair2.first).intValue() == -10000 ? "UNKNOWN_UID" : java.lang.Integer.toString(((java.lang.Integer) pair2.first).intValue()));
                    printWriter.print(')');
                    printWriter.print(" importance=");
                    printWriter.print(android.service.notification.NotificationListenerService.Ranking.importanceToString(((java.lang.Boolean) arrayMap2.get(pair2).first).booleanValue() ? 3 : 0));
                    printWriter.print(" userSet=");
                    printWriter.print(arrayMap2.get(pair2).second);
                    printWriter.println();
                }
            }
        }
    }

    private void dumpPackagePreferencesLocked(android.util.proto.ProtoOutputStream protoOutputStream, long j, @android.annotation.NonNull com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter, android.util.ArrayMap<java.lang.String, com.android.server.notification.PreferencesHelper.PackagePreferences> arrayMap, android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.Pair<java.lang.Boolean, java.lang.Boolean>> arrayMap2) {
        java.util.Set<android.util.Pair<java.lang.Integer, java.lang.String>> set;
        if (arrayMap2 == null) {
            set = null;
        } else {
            set = arrayMap2.keySet();
        }
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            com.android.server.notification.PreferencesHelper.PackagePreferences valueAt = arrayMap.valueAt(i);
            if (dumpFilter.matches(valueAt.pkg)) {
                long start = protoOutputStream.start(j);
                protoOutputStream.write(1138166333441L, valueAt.pkg);
                protoOutputStream.write(1120986464258L, valueAt.uid);
                android.util.Pair pair = new android.util.Pair(java.lang.Integer.valueOf(valueAt.uid), valueAt.pkg);
                if (arrayMap2 != null && set.contains(pair)) {
                    protoOutputStream.write(1172526071811L, ((java.lang.Boolean) arrayMap2.get(pair).first).booleanValue() ? 3 : 0);
                    set.remove(pair);
                }
                protoOutputStream.write(1120986464260L, valueAt.priority);
                protoOutputStream.write(1172526071813L, valueAt.visibility);
                protoOutputStream.write(1133871366150L, valueAt.showBadge);
                java.util.Iterator<android.app.NotificationChannel> it = valueAt.channels.values().iterator();
                while (it.hasNext()) {
                    it.next().dumpDebug(protoOutputStream, 2246267895815L);
                }
                java.util.Iterator<android.app.NotificationChannelGroup> it2 = valueAt.groups.values().iterator();
                while (it2.hasNext()) {
                    it2.next().dumpDebug(protoOutputStream, 2246267895816L);
                }
                protoOutputStream.end(start);
            }
        }
        if (set != null) {
            for (android.util.Pair<java.lang.Integer, java.lang.String> pair2 : set) {
                if (dumpFilter.matches((java.lang.String) pair2.second)) {
                    long start2 = protoOutputStream.start(j);
                    protoOutputStream.write(1138166333441L, (java.lang.String) pair2.second);
                    protoOutputStream.write(1120986464258L, ((java.lang.Integer) pair2.first).intValue());
                    protoOutputStream.write(1172526071811L, ((java.lang.Boolean) arrayMap2.get(pair2).first).booleanValue() ? 3 : 0);
                    protoOutputStream.end(start2);
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    int getFsiState(java.lang.String str, int i, boolean z) {
        if (!z) {
            return 0;
        }
        if (this.mPermissionManager.checkPermissionForPreflight("android.permission.USE_FULL_SCREEN_INTENT", new android.content.AttributionSource.Builder(i).setPackageName(str).build()) == 0) {
            return 1;
        }
        return 2;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isFsiPermissionUserSet(java.lang.String str, int i, int i2, int i3) {
        if (i2 == 0 || (i3 & 1) == 0) {
            return false;
        }
        return true;
    }

    public void pullPackagePreferencesStats(java.util.List<android.util.StatsEvent> list, android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.Pair<java.lang.Boolean, java.lang.Boolean>> arrayMap) {
        java.util.Set<android.util.Pair<java.lang.Integer, java.lang.String>> set;
        int i;
        int i2;
        boolean z;
        int i3;
        if (arrayMap == null) {
            set = null;
        } else {
            set = arrayMap.keySet();
        }
        synchronized (this.mPackagePreferences) {
            int i4 = 0;
            i = 0;
            while (true) {
                try {
                    int i5 = 3;
                    if (i4 >= this.mPackagePreferences.size() || i > 1000) {
                        break;
                    }
                    i++;
                    com.android.server.notification.PreferencesHelper.PackagePreferences valueAt = this.mPackagePreferences.valueAt(i4);
                    android.util.Pair pair = new android.util.Pair(java.lang.Integer.valueOf(valueAt.uid), valueAt.pkg);
                    if (arrayMap == null || !set.contains(pair)) {
                        z = false;
                        i3 = -1000;
                    } else {
                        android.util.Pair<java.lang.Boolean, java.lang.Boolean> pair2 = arrayMap.get(pair);
                        if (!((java.lang.Boolean) pair2.first).booleanValue()) {
                            i5 = 0;
                        }
                        boolean booleanValue = ((java.lang.Boolean) pair2.second).booleanValue();
                        set.remove(pair);
                        i3 = i5;
                        z = booleanValue;
                    }
                    int fsiState = getFsiState(valueAt.pkg, valueAt.uid, this.mPermissionHelper.hasRequestedPermission("android.permission.USE_FULL_SCREEN_INTENT", valueAt.pkg, valueAt.uid));
                    list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.PACKAGE_NOTIFICATION_PREFERENCES, valueAt.uid, i3, valueAt.visibility, valueAt.lockedAppFields, z, fsiState, isFsiPermissionUserSet(valueAt.pkg, valueAt.uid, fsiState, this.mPm.getPermissionFlags("android.permission.USE_FULL_SCREEN_INTENT", valueAt.pkg, android.os.UserHandle.getUserHandleForUid(valueAt.uid)))));
                    i4++;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        if (arrayMap != null) {
            for (android.util.Pair<java.lang.Integer, java.lang.String> pair3 : set) {
                if (i <= 1000) {
                    i++;
                    int intValue = ((java.lang.Integer) pair3.first).intValue();
                    if (!((java.lang.Boolean) arrayMap.get(pair3).first).booleanValue()) {
                        i2 = 0;
                    } else {
                        i2 = 3;
                    }
                    list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.PACKAGE_NOTIFICATION_PREFERENCES, intValue, i2, -1000, 0, ((java.lang.Boolean) arrayMap.get(pair3).second).booleanValue(), 0, false));
                } else {
                    return;
                }
            }
        }
    }

    public void pullPackageChannelPreferencesStats(java.util.List<android.util.StatsEvent> list) {
        synchronized (this.mPackagePreferences) {
            int i = 0;
            for (int i2 = 0; i2 < this.mPackagePreferences.size() && i <= 2000; i2++) {
                try {
                    com.android.server.notification.PreferencesHelper.PackagePreferences valueAt = this.mPackagePreferences.valueAt(i2);
                    for (android.app.NotificationChannel notificationChannel : valueAt.channels.values()) {
                        i++;
                        if (i > 2000) {
                            break;
                        } else {
                            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_PREFERENCES, valueAt.uid, notificationChannel.getId(), notificationChannel.getName().toString(), notificationChannel.getDescription(), notificationChannel.getImportance(), notificationChannel.getUserLockedFields(), notificationChannel.isDeleted(), notificationChannel.getConversationId() != null, notificationChannel.isDemoted(), notificationChannel.isImportantConversation()));
                        }
                    }
                } finally {
                }
            }
        }
    }

    public void pullPackageChannelGroupPreferencesStats(java.util.List<android.util.StatsEvent> list) {
        synchronized (this.mPackagePreferences) {
            int i = 0;
            for (int i2 = 0; i2 < this.mPackagePreferences.size() && i <= 1000; i2++) {
                try {
                    com.android.server.notification.PreferencesHelper.PackagePreferences valueAt = this.mPackagePreferences.valueAt(i2);
                    for (android.app.NotificationChannelGroup notificationChannelGroup : valueAt.groups.values()) {
                        i++;
                        if (i > 1000) {
                            break;
                        } else {
                            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_GROUP_PREFERENCES, valueAt.uid, notificationChannelGroup.getId(), notificationChannelGroup.getName().toString(), notificationChannelGroup.getDescription(), notificationChannelGroup.isBlocked(), notificationChannelGroup.getUserLockedFields()));
                        }
                    }
                } finally {
                }
            }
        }
    }

    public org.json.JSONObject dumpJson(com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter, android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.Pair<java.lang.Boolean, java.lang.Boolean>> arrayMap) {
        java.util.Set<android.util.Pair<java.lang.Integer, java.lang.String>> set;
        int i;
        org.json.JSONObject jSONObject = new org.json.JSONObject();
        org.json.JSONArray jSONArray = new org.json.JSONArray();
        try {
            jSONObject.put("noUid", this.mRestoredWithoutUids.size());
        } catch (org.json.JSONException e) {
        }
        if (arrayMap == null) {
            set = null;
        } else {
            set = arrayMap.keySet();
        }
        synchronized (this.mPackagePreferences) {
            try {
                int size = this.mPackagePreferences.size();
                int i2 = 0;
                while (true) {
                    int i3 = 3;
                    if (i2 >= size) {
                        break;
                    }
                    com.android.server.notification.PreferencesHelper.PackagePreferences valueAt = this.mPackagePreferences.valueAt(i2);
                    if (dumpFilter == null || dumpFilter.matches(valueAt.pkg)) {
                        org.json.JSONObject jSONObject2 = new org.json.JSONObject();
                        try {
                            jSONObject2.put("userId", android.os.UserHandle.getUserId(valueAt.uid));
                            jSONObject2.put(com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME, valueAt.pkg);
                            android.util.Pair pair = new android.util.Pair(java.lang.Integer.valueOf(valueAt.uid), valueAt.pkg);
                            if (arrayMap != null && set.contains(pair)) {
                                if (!((java.lang.Boolean) arrayMap.get(pair).first).booleanValue()) {
                                    i3 = 0;
                                }
                                jSONObject2.put(ATT_IMPORTANCE, android.service.notification.NotificationListenerService.Ranking.importanceToString(i3));
                                set.remove(pair);
                            }
                            if (valueAt.priority != 0) {
                                jSONObject2.put(ATT_PRIORITY, android.app.Notification.priorityToString(valueAt.priority));
                            }
                            if (valueAt.visibility != -1000) {
                                jSONObject2.put(ATT_VISIBILITY, android.app.Notification.visibilityToString(valueAt.visibility));
                            }
                            if (!valueAt.showBadge) {
                                jSONObject2.put("showBadge", java.lang.Boolean.valueOf(valueAt.showBadge));
                            }
                            if (valueAt.soundTimeout != 0) {
                                jSONObject2.put("soundTimeout", valueAt.soundTimeout);
                            }
                            org.json.JSONArray jSONArray2 = new org.json.JSONArray();
                            java.util.Iterator<android.app.NotificationChannel> it = valueAt.channels.values().iterator();
                            while (it.hasNext()) {
                                jSONArray2.put(it.next().toJson());
                            }
                            jSONObject2.put("channels", jSONArray2);
                            org.json.JSONArray jSONArray3 = new org.json.JSONArray();
                            java.util.Iterator<android.app.NotificationChannelGroup> it2 = valueAt.groups.values().iterator();
                            while (it2.hasNext()) {
                                jSONArray3.put(it2.next().toJson());
                            }
                            jSONObject2.put("groups", jSONArray3);
                        } catch (org.json.JSONException e2) {
                        }
                        jSONArray.put(jSONObject2);
                    }
                    i2++;
                }
            } finally {
            }
        }
        if (set != null) {
            for (android.util.Pair<java.lang.Integer, java.lang.String> pair2 : set) {
                if (dumpFilter == null || dumpFilter.matches((java.lang.String) pair2.second)) {
                    org.json.JSONObject jSONObject3 = new org.json.JSONObject();
                    try {
                        jSONObject3.put("userId", android.os.UserHandle.getUserId(((java.lang.Integer) pair2.first).intValue()));
                        jSONObject3.put(com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME, pair2.second);
                        if (!((java.lang.Boolean) arrayMap.get(pair2).first).booleanValue()) {
                            i = 0;
                        } else {
                            i = 3;
                        }
                        jSONObject3.put(ATT_IMPORTANCE, android.service.notification.NotificationListenerService.Ranking.importanceToString(i));
                    } catch (org.json.JSONException e3) {
                    }
                    jSONArray.put(jSONObject3);
                }
            }
        }
        try {
            jSONObject.put("PackagePreferencess", jSONArray);
        } catch (org.json.JSONException e4) {
        }
        return jSONObject;
    }

    public org.json.JSONArray dumpBansJson(com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter, android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.Pair<java.lang.Boolean, java.lang.Boolean>> arrayMap) {
        org.json.JSONArray jSONArray = new org.json.JSONArray();
        for (java.util.Map.Entry<java.lang.Integer, java.lang.String> entry : getPermissionBasedPackageBans(arrayMap).entrySet()) {
            int userId = android.os.UserHandle.getUserId(entry.getKey().intValue());
            java.lang.String value = entry.getValue();
            if (dumpFilter == null || dumpFilter.matches(value)) {
                org.json.JSONObject jSONObject = new org.json.JSONObject();
                try {
                    jSONObject.put("userId", userId);
                    jSONObject.put(com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME, value);
                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                }
                jSONArray.put(jSONObject);
            }
        }
        return jSONArray;
    }

    public java.util.Map<java.lang.Integer, java.lang.String> getPackageBans() {
        android.util.ArrayMap arrayMap;
        synchronized (this.mPackagePreferences) {
            try {
                int size = this.mPackagePreferences.size();
                arrayMap = new android.util.ArrayMap(size);
                for (int i = 0; i < size; i++) {
                    com.android.server.notification.PreferencesHelper.PackagePreferences valueAt = this.mPackagePreferences.valueAt(i);
                    if (valueAt.importance == 0) {
                        arrayMap.put(java.lang.Integer.valueOf(valueAt.uid), valueAt.pkg);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayMap;
    }

    protected java.util.Map<java.lang.Integer, java.lang.String> getPermissionBasedPackageBans(android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.Pair<java.lang.Boolean, java.lang.Boolean>> arrayMap) {
        android.util.ArrayMap arrayMap2 = new android.util.ArrayMap();
        if (arrayMap != null) {
            for (android.util.Pair<java.lang.Integer, java.lang.String> pair : arrayMap.keySet()) {
                if (!((java.lang.Boolean) arrayMap.get(pair).first).booleanValue()) {
                    arrayMap2.put((java.lang.Integer) pair.first, (java.lang.String) pair.second);
                }
            }
        }
        return arrayMap2;
    }

    public org.json.JSONArray dumpChannelsJson(com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter) {
        org.json.JSONArray jSONArray = new org.json.JSONArray();
        for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry : getPackageChannels().entrySet()) {
            java.lang.String key = entry.getKey();
            if (dumpFilter == null || dumpFilter.matches(key)) {
                org.json.JSONObject jSONObject = new org.json.JSONObject();
                try {
                    jSONObject.put(com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME, key);
                    jSONObject.put("channelCount", entry.getValue());
                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                }
                jSONArray.put(jSONObject);
            }
        }
        return jSONArray;
    }

    private java.util.Map<java.lang.String, java.lang.Integer> getPackageChannels() {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        synchronized (this.mPackagePreferences) {
            for (int i = 0; i < this.mPackagePreferences.size(); i++) {
                try {
                    com.android.server.notification.PreferencesHelper.PackagePreferences valueAt = this.mPackagePreferences.valueAt(i);
                    int i2 = 0;
                    for (int i3 = 0; i3 < valueAt.channels.size(); i3++) {
                        if (!valueAt.channels.valueAt(i3).isDeleted()) {
                            i2++;
                        }
                    }
                    arrayMap.put(valueAt.pkg, java.lang.Integer.valueOf(i2));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        return arrayMap;
    }

    public void onUserRemoved(int i) {
        synchronized (this.mPackagePreferences) {
            try {
                for (int size = this.mPackagePreferences.size() - 1; size >= 0; size--) {
                    if (android.os.UserHandle.getUserId(this.mPackagePreferences.valueAt(size).uid) == i) {
                        this.mPackagePreferences.removeAt(size);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected void onLocaleChanged(android.content.Context context, int i) {
        synchronized (this.mPackagePreferences) {
            try {
                int size = this.mPackagePreferences.size();
                for (int i2 = 0; i2 < size; i2++) {
                    com.android.server.notification.PreferencesHelper.PackagePreferences valueAt = this.mPackagePreferences.valueAt(i2);
                    if (android.os.UserHandle.getUserId(valueAt.uid) == i && valueAt.channels.containsKey("miscellaneous")) {
                        valueAt.channels.get("miscellaneous").setName(context.getResources().getString(android.R.string.db_default_sync_mode));
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean onPackagesChanged(boolean z, int i, java.lang.String[] strArr, int[] iArr) {
        boolean z2;
        if (strArr == null || strArr.length == 0) {
            return false;
        }
        boolean z3 = true;
        if (z) {
            int min = java.lang.Math.min(strArr.length, iArr.length);
            int i2 = 0;
            z2 = false;
            while (i2 < min) {
                java.lang.String str = strArr[i2];
                int i3 = iArr[i2];
                synchronized (this.mPackagePreferences) {
                    this.mPackagePreferences.remove(packagePreferencesKey(str, i3));
                }
                this.mRestoredWithoutUids.remove(unrestoredPackageKey(str, i));
                i2++;
                z2 = true;
            }
        } else {
            int length = strArr.length;
            z2 = false;
            int i4 = 0;
            while (i4 < length) {
                java.lang.String str2 = strArr[i4];
                com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferences = this.mRestoredWithoutUids.get(unrestoredPackageKey(str2, i));
                if (packagePreferences != null) {
                    try {
                        packagePreferences.uid = this.mPm.getPackageUidAsUser(packagePreferences.pkg, i);
                        this.mRestoredWithoutUids.remove(unrestoredPackageKey(str2, i));
                        synchronized (this.mPackagePreferences) {
                            try {
                                this.mPackagePreferences.put(packagePreferencesKey(packagePreferences.pkg, packagePreferences.uid), packagePreferences);
                                for (android.app.NotificationChannel notificationChannel : packagePreferences.channels.values()) {
                                    if (!notificationChannel.isSoundRestored()) {
                                        android.net.Uri sound = notificationChannel.getSound();
                                        android.net.Uri restoreSoundUri = notificationChannel.restoreSoundUri(this.mContext, sound, z3, notificationChannel.getAudioAttributes().getUsage());
                                        if (android.provider.Settings.System.DEFAULT_NOTIFICATION_URI.equals(restoreSoundUri)) {
                                            android.util.Log.w(TAG, "Could not restore sound: " + sound + " for channel: " + notificationChannel);
                                        }
                                        notificationChannel.setSound(restoreSoundUri, notificationChannel.getAudioAttributes());
                                    }
                                    z3 = true;
                                }
                            } catch (java.lang.Throwable th) {
                                throw th;
                            }
                        }
                        if (packagePreferences.migrateToPm) {
                            try {
                                this.mPermissionHelper.setNotificationPermission(new com.android.server.notification.PermissionHelper.PackagePermission(packagePreferences.pkg, android.os.UserHandle.getUserId(packagePreferences.uid), packagePreferences.importance != 0, hasUserConfiguredSettings(packagePreferences)));
                            } catch (java.lang.Exception e) {
                                android.util.Slog.e(TAG, "could not migrate setting for " + packagePreferences.pkg, e);
                            }
                        }
                        z2 = true;
                    } catch (java.lang.Exception e2) {
                        android.util.Slog.e(TAG, "could not restore " + packagePreferences.pkg, e2);
                    }
                }
                try {
                    synchronized (this.mPackagePreferences) {
                        try {
                            com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str2, this.mPm.getPackageUidAsUser(str2, i));
                            if (packagePreferencesLocked != null) {
                                z2 = z2 | createDefaultChannelIfNeededLocked(packagePreferencesLocked) | deleteDefaultChannelIfNeededLocked(packagePreferencesLocked);
                            }
                        } catch (java.lang.Throwable th2) {
                            throw th2;
                        }
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e3) {
                }
                i4++;
                z3 = true;
            }
        }
        if (z2) {
            updateConfig();
        }
        return z2;
    }

    public void clearData(java.lang.String str, int i) {
        synchronized (this.mPackagePreferences) {
            try {
                com.android.server.notification.PreferencesHelper.PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
                if (packagePreferencesLocked != null) {
                    packagePreferencesLocked.channels = new android.util.ArrayMap<>();
                    packagePreferencesLocked.groups = new android.util.ArrayMap();
                    packagePreferencesLocked.delegate = null;
                    packagePreferencesLocked.lockedAppFields = 0;
                    packagePreferencesLocked.soundTimeout = 0L;
                    packagePreferencesLocked.bubblePreference = 0;
                    packagePreferencesLocked.importance = -1000;
                    packagePreferencesLocked.priority = 0;
                    packagePreferencesLocked.visibility = -1000;
                    packagePreferencesLocked.showBadge = true;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private android.metrics.LogMaker getChannelLog(android.app.NotificationChannel notificationChannel, java.lang.String str) {
        return new android.metrics.LogMaker(856).setType(6).setPackageName(str).addTaggedData(857, notificationChannel.getId()).addTaggedData(858, java.lang.Integer.valueOf(notificationChannel.getImportance()));
    }

    private android.metrics.LogMaker getChannelGroupLog(java.lang.String str, java.lang.String str2) {
        return new android.metrics.LogMaker(859).setType(6).addTaggedData(860, str).setPackageName(str2);
    }

    public void updateMediaNotificationFilteringEnabled() {
        boolean z = android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "qs_media_controls", 1) > 0 && this.mContext.getResources().getBoolean(android.R.bool.config_preferKeepClearForFocus);
        if (z != this.mIsMediaNotificationFilteringEnabled) {
            this.mIsMediaNotificationFilteringEnabled = z;
            updateConfig();
        }
    }

    @Override // com.android.server.notification.RankingConfig
    public boolean isMediaNotificationFilteringEnabled() {
        return this.mIsMediaNotificationFilteringEnabled;
    }

    public void updateBadgingEnabled() {
        if (this.mBadgingEnabled == null) {
            this.mBadgingEnabled = new android.util.SparseBooleanArray();
        }
        boolean z = false;
        for (int i = 0; i < this.mBadgingEnabled.size(); i++) {
            int keyAt = this.mBadgingEnabled.keyAt(i);
            boolean z2 = this.mBadgingEnabled.get(keyAt);
            boolean z3 = true;
            boolean z4 = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "notification_badging", 1, keyAt) != 0;
            this.mBadgingEnabled.put(keyAt, z4);
            if (z2 == z4) {
                z3 = false;
            }
            z |= z3;
        }
        if (z) {
            updateConfig();
        }
    }

    @Override // com.android.server.notification.RankingConfig
    public boolean badgingEnabled(android.os.UserHandle userHandle) {
        int identifier = userHandle.getIdentifier();
        if (identifier == -1) {
            return false;
        }
        if (this.mBadgingEnabled.indexOfKey(identifier) < 0) {
            this.mBadgingEnabled.put(identifier, android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "notification_badging", 1, identifier) != 0);
        }
        return this.mBadgingEnabled.get(identifier, true);
    }

    public void updateBubblesEnabled() {
        if (this.mBubblesEnabled == null) {
            this.mBubblesEnabled = new android.util.SparseBooleanArray();
        }
        boolean z = false;
        for (int i = 0; i < this.mBubblesEnabled.size(); i++) {
            int keyAt = this.mBubblesEnabled.keyAt(i);
            boolean z2 = this.mBubblesEnabled.get(keyAt);
            boolean z3 = true;
            boolean z4 = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "notification_bubbles", 1, keyAt) != 0;
            this.mBubblesEnabled.put(keyAt, z4);
            if (z2 == z4) {
                z3 = false;
            }
            z |= z3;
        }
        if (z) {
            updateConfig();
        }
    }

    @Override // com.android.server.notification.RankingConfig
    public boolean bubblesEnabled(android.os.UserHandle userHandle) {
        int identifier = userHandle.getIdentifier();
        if (identifier == -1) {
            return false;
        }
        if (this.mBubblesEnabled.indexOfKey(identifier) < 0) {
            this.mBubblesEnabled.put(identifier, android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "notification_bubbles", 1, identifier) != 0);
        }
        return this.mBubblesEnabled.get(identifier, true);
    }

    public void updateLockScreenPrivateNotifications() {
        if (this.mLockScreenPrivateNotifications == null) {
            this.mLockScreenPrivateNotifications = new android.util.SparseBooleanArray();
        }
        boolean z = false;
        for (int i = 0; i < this.mLockScreenPrivateNotifications.size(); i++) {
            int keyAt = this.mLockScreenPrivateNotifications.keyAt(i);
            boolean z2 = this.mLockScreenPrivateNotifications.get(keyAt);
            boolean z3 = true;
            boolean z4 = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "lock_screen_allow_private_notifications", 1, keyAt) != 0;
            this.mLockScreenPrivateNotifications.put(keyAt, z4);
            if (z2 == z4) {
                z3 = false;
            }
            z |= z3;
        }
        if (z) {
            updateConfig();
        }
    }

    public void updateLockScreenShowNotifications() {
        if (this.mLockScreenShowNotifications == null) {
            this.mLockScreenShowNotifications = new android.util.SparseBooleanArray();
        }
        boolean z = false;
        for (int i = 0; i < this.mLockScreenShowNotifications.size(); i++) {
            int keyAt = this.mLockScreenShowNotifications.keyAt(i);
            boolean z2 = this.mLockScreenShowNotifications.get(keyAt);
            boolean z3 = true;
            boolean z4 = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "lock_screen_show_notifications", 1, keyAt) != 0;
            this.mLockScreenShowNotifications.put(keyAt, z4);
            if (z2 == z4) {
                z3 = false;
            }
            z |= z3;
        }
        if (z) {
            updateConfig();
        }
    }

    @Override // com.android.server.notification.RankingConfig
    public boolean canShowNotificationsOnLockscreen(int i) {
        if (this.mLockScreenShowNotifications == null) {
            this.mLockScreenShowNotifications = new android.util.SparseBooleanArray();
        }
        return this.mLockScreenShowNotifications.get(i, true);
    }

    @Override // com.android.server.notification.RankingConfig
    public boolean canShowPrivateNotificationsOnLockScreen(int i) {
        if (this.mLockScreenPrivateNotifications == null) {
            this.mLockScreenPrivateNotifications = new android.util.SparseBooleanArray();
        }
        return this.mLockScreenPrivateNotifications.get(i, true);
    }

    public void unlockAllNotificationChannels() {
        synchronized (this.mPackagePreferences) {
            try {
                int size = this.mPackagePreferences.size();
                for (int i = 0; i < size; i++) {
                    java.util.Iterator<android.app.NotificationChannel> it = this.mPackagePreferences.valueAt(i).channels.values().iterator();
                    while (it.hasNext()) {
                        it.next().unlockFields(4);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void migrateNotificationPermissions(java.util.List<android.content.pm.UserInfo> list) {
        java.util.Iterator<android.content.pm.UserInfo> it = list.iterator();
        while (it.hasNext()) {
            for (android.content.pm.PackageInfo packageInfo : this.mPm.getInstalledPackagesAsUser(android.content.pm.PackageManager.PackageInfoFlags.of(131072L), it.next().getUserHandle().getIdentifier())) {
                synchronized (this.mPackagePreferences) {
                    com.android.server.notification.PreferencesHelper.PackagePreferences orCreatePackagePreferencesLocked = getOrCreatePackagePreferencesLocked(packageInfo.packageName, packageInfo.applicationInfo.uid);
                    if (orCreatePackagePreferencesLocked.migrateToPm && orCreatePackagePreferencesLocked.uid != -10000) {
                        try {
                            this.mPermissionHelper.setNotificationPermission(new com.android.server.notification.PermissionHelper.PackagePermission(orCreatePackagePreferencesLocked.pkg, android.os.UserHandle.getUserId(orCreatePackagePreferencesLocked.uid), orCreatePackagePreferencesLocked.importance != 0, hasUserConfiguredSettings(orCreatePackagePreferencesLocked)));
                        } catch (java.lang.Exception e) {
                            android.util.Slog.e(TAG, "could not migrate setting for " + orCreatePackagePreferencesLocked.pkg, e);
                        }
                    }
                }
            }
        }
    }

    private void updateConfig() {
        this.mRankingHandler.requestSort();
    }

    private static java.lang.String packagePreferencesKey(java.lang.String str, int i) {
        return str + "|" + i;
    }

    private static java.lang.String unrestoredPackageKey(java.lang.String str, int i) {
        return str + "|" + i;
    }

    private static class PackagePreferences {
        int bubblePreference;
        android.util.ArrayMap<java.lang.String, android.app.NotificationChannel> channels;
        boolean defaultAppLockedImportance;
        com.android.server.notification.PreferencesHelper.Delegate delegate;
        boolean fixedImportance;
        java.util.Map<java.lang.String, android.app.NotificationChannelGroup> groups;
        boolean hasSentInvalidMessage;
        boolean hasSentValidBubble;
        boolean hasSentValidMessage;
        int importance;
        int lockedAppFields;
        boolean migrateToPm;
        java.lang.String pkg;
        int priority;
        boolean showBadge;
        long soundTimeout;
        int uid;
        boolean userDemotedMsgApp;
        int visibility;

        private PackagePreferences() {
            this.uid = -10000;
            this.importance = -1000;
            this.priority = 0;
            this.visibility = -1000;
            this.showBadge = true;
            this.bubblePreference = 0;
            this.lockedAppFields = 0;
            this.soundTimeout = 0L;
            this.defaultAppLockedImportance = false;
            this.fixedImportance = false;
            this.hasSentInvalidMessage = false;
            this.hasSentValidMessage = false;
            this.userDemotedMsgApp = false;
            this.hasSentValidBubble = false;
            this.migrateToPm = false;
            this.delegate = null;
            this.channels = new android.util.ArrayMap<>();
            this.groups = new java.util.concurrent.ConcurrentHashMap();
        }

        public boolean isValidDelegate(java.lang.String str, int i) {
            return this.delegate != null && this.delegate.isAllowed(str, i);
        }
    }

    private static class Delegate {
        static final boolean DEFAULT_ENABLED = true;
        boolean mEnabled;
        final java.lang.String mPkg;
        final int mUid;

        Delegate(java.lang.String str, int i, boolean z) {
            this.mPkg = str;
            this.mUid = i;
            this.mEnabled = z;
        }

        public boolean isAllowed(java.lang.String str, int i) {
            return str != null && i != -10000 && str.equals(this.mPkg) && i == this.mUid && this.mEnabled;
        }
    }
}
