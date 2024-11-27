package com.android.server.notification;

/* loaded from: classes2.dex */
public final class NotificationRecord {
    private static final int MAX_SOUND_DELAY_MS = 2000;
    boolean isCanceled;
    public boolean isUpdate;
    private java.lang.String mAdjustmentIssuer;
    private boolean mAllowBubble;
    private boolean mAppDemotedFromConvo;
    private int mAuthoritativeRank;
    private android.app.NotificationChannel mChannel;
    private float mContactAffinity;
    private final android.content.Context mContext;
    private long mCreationTimeMs;
    private boolean mEditChoicesBeforeSending;
    private boolean mFlagBubbleRemoved;
    private java.lang.String mGlobalSortKey;
    private android.util.ArraySet<android.net.Uri> mGrantableUris;
    private boolean mHasSeenSmartReplies;
    private boolean mHasSentValidMsg;
    private boolean mHidden;
    private int mImportance;
    private boolean mImportanceFixed;
    private boolean mIntercept;
    private boolean mInterceptSet;
    private long mInterruptionTimeMs;
    private boolean mIsAppImportanceLocked;
    private boolean mIsInterruptive;
    private boolean mIsNotConversationOverride;
    private android.app.KeyguardManager mKeyguardManager;
    private long mLastAudiblyAlertedMs;
    private long mLastIntrusive;
    private int mNumberOfSmartActionsAdded;
    private int mNumberOfSmartRepliesAdded;
    final int mOriginalFlags;
    private int mPackagePriority;
    private int mPackageVisibility;
    private java.util.ArrayList<java.lang.String> mPeopleOverride;
    private android.util.ArraySet<java.lang.String> mPhoneNumbers;
    private boolean mPkgAllowedAsConvo;
    private boolean mPostSilently;
    private final android.os.PowerManager mPowerManager;
    private boolean mPreChannelsNotification;
    private boolean mRecentlyIntrusive;
    private boolean mRecordedInterruption;
    private android.content.pm.ShortcutInfo mShortcutInfo;
    private boolean mShowBadge;
    private java.util.ArrayList<java.lang.CharSequence> mSmartReplies;
    private java.util.ArrayList<android.service.notification.SnoozeCriterion> mSnoozeCriteria;
    private boolean mSuggestionsGeneratedByAssistant;
    private java.util.ArrayList<android.app.Notification.Action> mSystemGeneratedSmartActions;
    final int mTargetSdkVersion;
    private boolean mTextChanged;

    @com.android.internal.annotations.VisibleForTesting
    final long mUpdateTimeMs;
    private java.lang.String mUserExplanation;
    private int mUserSentiment;
    private long mVisibleSinceMs;
    android.os.IBinder permissionOwner;
    private final android.service.notification.StatusBarNotification sbn;
    static final java.lang.String TAG = "NotificationRecord";
    static final boolean DBG = android.util.Log.isLoggable(TAG, 3);
    private int mSystemImportance = -1000;
    private int mAssistantImportance = -1000;
    private float mRankingScore = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    private int mCriticality = 2;
    private int mImportanceExplanationCode = 0;
    private int mInitialImportanceExplanationCode = 0;
    private int mSuppressedVisualEffects = 0;
    private boolean mPendingLogUpdate = false;
    private int mProposedImportance = -1000;
    private boolean mSensitiveContent = false;
    private final com.android.server.uri.UriGrantsManagerInternal mUgmInternal = (com.android.server.uri.UriGrantsManagerInternal) com.android.server.LocalServices.getService(com.android.server.uri.UriGrantsManagerInternal.class);
    private long mRankingTimeMs = calculateRankingTimeMs(0);
    com.android.server.notification.NotificationUsageStats.SingleNotificationStats stats = new com.android.server.notification.NotificationUsageStats.SingleNotificationStats();
    private android.net.Uri mSound = calculateSound();
    private android.os.VibrationEffect mVibration = calculateVibration();

    @android.annotation.NonNull
    private android.media.AudioAttributes mAttributes = calculateAttributes();
    private com.android.server.notification.NotificationRecord.Light mLight = calculateLights();
    private final java.util.List<android.service.notification.Adjustment> mAdjustments = new java.util.ArrayList();
    private final android.service.notification.NotificationStats mStats = new android.service.notification.NotificationStats();

    public NotificationRecord(android.content.Context context, android.service.notification.StatusBarNotification statusBarNotification, android.app.NotificationChannel notificationChannel) {
        this.mImportance = -1000;
        this.mPreChannelsNotification = true;
        this.sbn = statusBarNotification;
        this.mTargetSdkVersion = ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getPackageTargetSdkVersion(statusBarNotification.getPackageName());
        this.mOriginalFlags = statusBarNotification.getNotification().flags;
        this.mCreationTimeMs = statusBarNotification.getPostTime();
        this.mUpdateTimeMs = this.mCreationTimeMs;
        this.mInterruptionTimeMs = this.mCreationTimeMs;
        this.mContext = context;
        this.mKeyguardManager = (android.app.KeyguardManager) this.mContext.getSystemService(android.app.KeyguardManager.class);
        this.mPowerManager = (android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class);
        this.mChannel = notificationChannel;
        this.mPreChannelsNotification = isPreChannelsNotification();
        this.mImportance = calculateInitialImportance();
        calculateUserSentiment();
        calculateGrantableUris();
    }

    private boolean isPreChannelsNotification() {
        if ("miscellaneous".equals(getChannel().getId()) && this.mTargetSdkVersion < 26) {
            return true;
        }
        return false;
    }

    private android.net.Uri calculateSound() {
        android.app.Notification notification = getSbn().getNotification();
        if (this.mContext.getPackageManager().hasSystemFeature("android.software.leanback")) {
            return null;
        }
        android.net.Uri sound = this.mChannel.getSound();
        if (this.mPreChannelsNotification && (getChannel().getUserLockedFields() & 32) == 0) {
            if ((notification.defaults & 1) != 0) {
                return android.provider.Settings.System.DEFAULT_NOTIFICATION_URI;
            }
            return notification.sound;
        }
        return sound;
    }

    private com.android.server.notification.NotificationRecord.Light calculateLights() {
        int integer = this.mContext.getResources().getInteger(android.R.integer.config_defaultNightMode);
        int integer2 = this.mContext.getResources().getInteger(android.R.integer.config_defaultNightDisplayCustomStartTime);
        com.android.server.notification.NotificationRecord.Light light = getChannel().shouldShowLights() ? new com.android.server.notification.NotificationRecord.Light(getChannel().getLightColor() != 0 ? getChannel().getLightColor() : 0, integer, integer2) : null;
        if (this.mPreChannelsNotification && (getChannel().getUserLockedFields() & 8) == 0) {
            android.app.Notification notification = getSbn().getNotification();
            if ((notification.flags & 1) != 0) {
                return (notification.defaults & 4) != 0 ? new com.android.server.notification.NotificationRecord.Light(0, integer, integer2) : new com.android.server.notification.NotificationRecord.Light(notification.ledARGB, notification.ledOnMS, notification.ledOffMS);
            }
            return null;
        }
        return light;
    }

    private android.os.VibrationEffect getVibrationForChannel(android.app.NotificationChannel notificationChannel, com.android.server.notification.VibratorHelper vibratorHelper, boolean z) {
        android.os.VibrationEffect vibrationEffect;
        if (!notificationChannel.shouldVibrate()) {
            return null;
        }
        if (android.app.Flags.notificationChannelVibrationEffectApi() && (vibrationEffect = notificationChannel.getVibrationEffect()) != null && vibratorHelper.areEffectComponentsSupported(vibrationEffect)) {
            return vibrationEffect.applyRepeatingIndefinitely(z, 0);
        }
        long[] vibrationPattern = notificationChannel.getVibrationPattern();
        if (vibrationPattern == null) {
            return vibratorHelper.createDefaultVibration(z);
        }
        return com.android.server.notification.VibratorHelper.createWaveformVibration(vibrationPattern, z);
    }

    private android.os.VibrationEffect calculateVibration() {
        com.android.server.notification.VibratorHelper vibratorHelper = new com.android.server.notification.VibratorHelper(this.mContext);
        android.app.Notification notification = getSbn().getNotification();
        boolean z = (notification.flags & 4) != 0;
        if (this.mPreChannelsNotification && (getChannel().getUserLockedFields() & 16) == 0) {
            if ((notification.defaults & 2) != 0) {
                return vibratorHelper.createDefaultVibration(z);
            }
            return com.android.server.notification.VibratorHelper.createWaveformVibration(notification.vibrate, z);
        }
        return getVibrationForChannel(getChannel(), vibratorHelper, z);
    }

    @android.annotation.NonNull
    private android.media.AudioAttributes calculateAttributes() {
        android.app.Notification notification = getSbn().getNotification();
        android.media.AudioAttributes audioAttributes = getChannel().getAudioAttributes();
        if (audioAttributes == null) {
            audioAttributes = android.app.Notification.AUDIO_ATTRIBUTES_DEFAULT;
        }
        if (this.mPreChannelsNotification && (getChannel().getUserLockedFields() & 32) == 0) {
            if (notification.audioAttributes != null) {
                return notification.audioAttributes;
            }
            if (notification.audioStreamType >= 0 && notification.audioStreamType < android.media.AudioSystem.getNumStreamTypes()) {
                return new android.media.AudioAttributes.Builder().setInternalLegacyStreamType(notification.audioStreamType).build();
            }
            if (notification.audioStreamType != -1) {
                android.util.Log.w(TAG, java.lang.String.format("Invalid stream type: %d", java.lang.Integer.valueOf(notification.audioStreamType)));
                return audioAttributes;
            }
            return audioAttributes;
        }
        return audioAttributes;
    }

    private int calculateInitialImportance() {
        int i;
        int i2;
        android.app.Notification notification = getSbn().getNotification();
        int importance = getChannel().getImportance();
        boolean z = true;
        if (getChannel().hasUserSetImportance()) {
            i = 2;
        } else {
            i = 1;
        }
        this.mInitialImportanceExplanationCode = i;
        if ((notification.flags & 128) != 0) {
            notification.priority = 2;
        }
        notification.priority = com.android.server.notification.NotificationManagerService.clamp(notification.priority, -2, 2);
        switch (notification.priority) {
            case -2:
                i2 = 1;
                break;
            case -1:
                i2 = 2;
                break;
            case 0:
                i2 = 3;
                break;
            case 1:
            case 2:
                i2 = 4;
                break;
            default:
                i2 = 3;
                break;
        }
        this.stats.requestedImportance = i2;
        com.android.server.notification.NotificationUsageStats.SingleNotificationStats singleNotificationStats = this.stats;
        if (this.mSound == null && this.mVibration == null) {
            z = false;
        }
        singleNotificationStats.isNoisy = z;
        if (this.mPreChannelsNotification && (importance == -1000 || !getChannel().hasUserSetImportance())) {
            int i3 = (this.stats.isNoisy || i2 <= 2) ? i2 : 2;
            int i4 = (!this.stats.isNoisy || i3 >= 3) ? i3 : 3;
            if (notification.fullScreenIntent == null) {
                importance = i4;
            } else {
                importance = 4;
            }
            this.mInitialImportanceExplanationCode = 5;
        }
        this.stats.naturalImportance = importance;
        return importance;
    }

    public void copyRankingInformation(com.android.server.notification.NotificationRecord notificationRecord) {
        this.mContactAffinity = notificationRecord.mContactAffinity;
        this.mRecentlyIntrusive = notificationRecord.mRecentlyIntrusive;
        this.mPackagePriority = notificationRecord.mPackagePriority;
        this.mPackageVisibility = notificationRecord.mPackageVisibility;
        this.mIntercept = notificationRecord.mIntercept;
        this.mHidden = notificationRecord.mHidden;
        this.mRankingTimeMs = calculateRankingTimeMs(notificationRecord.getRankingTimeMs());
        this.mCreationTimeMs = notificationRecord.mCreationTimeMs;
        this.mVisibleSinceMs = notificationRecord.mVisibleSinceMs;
        if (notificationRecord.getSbn().getOverrideGroupKey() != null && !getSbn().isAppGroup()) {
            getSbn().setOverrideGroupKey(notificationRecord.getSbn().getOverrideGroupKey());
        }
    }

    public android.app.Notification getNotification() {
        return getSbn().getNotification();
    }

    public int getFlags() {
        return getSbn().getNotification().flags;
    }

    public android.os.UserHandle getUser() {
        return getSbn().getUser();
    }

    public java.lang.String getKey() {
        return getSbn().getKey();
    }

    public int getUserId() {
        return getSbn().getUserId();
    }

    public int getUid() {
        return getSbn().getUid();
    }

    void dump(android.util.proto.ProtoOutputStream protoOutputStream, long j, boolean z, int i) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, getSbn().getKey());
        protoOutputStream.write(1159641169922L, i);
        if (getChannel() != null) {
            protoOutputStream.write(1138166333444L, getChannel().getId());
        }
        protoOutputStream.write(1133871366152L, getLight() != null);
        protoOutputStream.write(1133871366151L, getVibration() != null);
        protoOutputStream.write(1120986464259L, getSbn().getNotification().flags);
        protoOutputStream.write(1138166333449L, getGroupKey());
        protoOutputStream.write(1172526071818L, getImportance());
        if (getSound() != null) {
            protoOutputStream.write(1138166333445L, getSound().toString());
        }
        if (getAudioAttributes() != null) {
            getAudioAttributes().dumpDebug(protoOutputStream, 1146756268038L);
        }
        protoOutputStream.write(1138166333451L, getSbn().getPackageName());
        protoOutputStream.write(1138166333452L, getSbn().getOpPkg());
        protoOutputStream.end(start);
    }

    java.lang.String formatRemoteViews(android.widget.RemoteViews remoteViews) {
        return remoteViews == null ? "null" : java.lang.String.format("%s/0x%08x (%d bytes): %s", remoteViews.getPackage(), java.lang.Integer.valueOf(remoteViews.getLayoutId()), java.lang.Integer.valueOf(remoteViews.estimateMemoryUsage()), remoteViews.toString());
    }

    @dalvik.annotation.optimization.NeverCompile
    void dump(java.io.PrintWriter printWriter, java.lang.String str, android.content.Context context, boolean z) {
        android.app.Notification notification = getSbn().getNotification();
        printWriter.println(str + this);
        java.lang.String str2 = str + "  ";
        printWriter.println(str2 + "uid=" + getSbn().getUid() + " userId=" + getSbn().getUserId());
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str2);
        sb.append("opPkg=");
        sb.append(getSbn().getOpPkg());
        printWriter.println(sb.toString());
        printWriter.println(str2 + "icon=" + notification.getSmallIcon());
        printWriter.println(str2 + "flags=0x" + java.lang.Integer.toHexString(notification.flags));
        printWriter.println(str2 + "originalFlags=0x" + java.lang.Integer.toHexString(this.mOriginalFlags));
        printWriter.println(str2 + "pri=" + notification.priority);
        printWriter.println(str2 + "key=" + getSbn().getKey());
        printWriter.println(str2 + "seen=" + this.mStats.hasSeen());
        printWriter.println(str2 + "groupKey=" + getGroupKey());
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
        sb2.append(str2);
        sb2.append("notification=");
        printWriter.println(sb2.toString());
        dumpNotification(printWriter, str2 + str2, notification, z);
        printWriter.println(str2 + "publicNotification=");
        dumpNotification(printWriter, str2 + str2, notification.publicVersion, z);
        printWriter.println(str2 + "stats=" + this.stats.toString());
        printWriter.println(str2 + "mContactAffinity=" + this.mContactAffinity);
        printWriter.println(str2 + "mRecentlyIntrusive=" + this.mRecentlyIntrusive);
        printWriter.println(str2 + "mPackagePriority=" + this.mPackagePriority);
        printWriter.println(str2 + "mPackageVisibility=" + this.mPackageVisibility);
        printWriter.println(str2 + "mSystemImportance=" + android.service.notification.NotificationListenerService.Ranking.importanceToString(this.mSystemImportance));
        printWriter.println(str2 + "mAsstImportance=" + android.service.notification.NotificationListenerService.Ranking.importanceToString(this.mAssistantImportance));
        printWriter.println(str2 + "mImportance=" + android.service.notification.NotificationListenerService.Ranking.importanceToString(this.mImportance));
        printWriter.println(str2 + "mImportanceExplanation=" + ((java.lang.Object) getImportanceExplanation()));
        printWriter.println(str2 + "mProposedImportance=" + android.service.notification.NotificationListenerService.Ranking.importanceToString(this.mProposedImportance));
        printWriter.println(str2 + "mIsAppImportanceLocked=" + this.mIsAppImportanceLocked);
        printWriter.println(str2 + "mSensitiveContent=" + this.mSensitiveContent);
        printWriter.println(str2 + "mIntercept=" + this.mIntercept);
        printWriter.println(str2 + "mHidden==" + this.mHidden);
        printWriter.println(str2 + "mGlobalSortKey=" + this.mGlobalSortKey);
        printWriter.println(str2 + "mRankingTimeMs=" + this.mRankingTimeMs);
        printWriter.println(str2 + "mCreationTimeMs=" + this.mCreationTimeMs);
        printWriter.println(str2 + "mVisibleSinceMs=" + this.mVisibleSinceMs);
        printWriter.println(str2 + "mUpdateTimeMs=" + this.mUpdateTimeMs);
        printWriter.println(str2 + "mInterruptionTimeMs=" + this.mInterruptionTimeMs);
        printWriter.println(str2 + "mSuppressedVisualEffects= " + this.mSuppressedVisualEffects);
        if (this.mPreChannelsNotification) {
            printWriter.println(str2 + java.lang.String.format("defaults=0x%08x flags=0x%08x", java.lang.Integer.valueOf(notification.defaults), java.lang.Integer.valueOf(notification.flags)));
            printWriter.println(str2 + "n.sound=" + notification.sound);
            printWriter.println(str2 + "n.audioStreamType=" + notification.audioStreamType);
            printWriter.println(str2 + "n.audioAttributes=" + notification.audioAttributes);
            java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
            sb3.append(str2);
            sb3.append(java.lang.String.format("  led=0x%08x onMs=%d offMs=%d", java.lang.Integer.valueOf(notification.ledARGB), java.lang.Integer.valueOf(notification.ledOnMS), java.lang.Integer.valueOf(notification.ledOffMS)));
            printWriter.println(sb3.toString());
            printWriter.println(str2 + "vibrate=" + java.util.Arrays.toString(notification.vibrate));
        }
        printWriter.println(str2 + "mSound= " + this.mSound);
        printWriter.println(str2 + "mVibration= " + this.mVibration);
        printWriter.println(str2 + "mAttributes= " + this.mAttributes);
        printWriter.println(str2 + "mLight= " + this.mLight);
        printWriter.println(str2 + "mShowBadge=" + this.mShowBadge);
        printWriter.println(str2 + "mColorized=" + notification.isColorized());
        printWriter.println(str2 + "mAllowBubble=" + this.mAllowBubble);
        printWriter.println(str2 + "isBubble=" + notification.isBubbleNotification());
        printWriter.println(str2 + "mIsInterruptive=" + this.mIsInterruptive);
        printWriter.println(str2 + "effectiveNotificationChannel=" + getChannel());
        if (getPeopleOverride() != null) {
            printWriter.println(str2 + "overridePeople= " + android.text.TextUtils.join(",", getPeopleOverride()));
        }
        if (getSnoozeCriteria() != null) {
            printWriter.println(str2 + "snoozeCriteria=" + android.text.TextUtils.join(",", getSnoozeCriteria()));
        }
        printWriter.println(str2 + "mAdjustments=" + this.mAdjustments);
        java.lang.StringBuilder sb4 = new java.lang.StringBuilder();
        sb4.append(str2);
        sb4.append("shortcut=");
        sb4.append(notification.getShortcutId());
        sb4.append(" found valid? ");
        sb4.append(this.mShortcutInfo != null);
        printWriter.println(sb4.toString());
        printWriter.println(str2 + "mUserVisOverride=" + getPackageVisibilityOverride());
    }

    private void dumpNotification(java.io.PrintWriter printWriter, java.lang.String str, android.app.Notification notification, boolean z) {
        if (notification == null) {
            printWriter.println(str + com.android.server.input.KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
            return;
        }
        printWriter.println(str + "fullscreenIntent=" + notification.fullScreenIntent);
        printWriter.println(str + "contentIntent=" + notification.contentIntent);
        printWriter.println(str + "deleteIntent=" + notification.deleteIntent);
        printWriter.println(str + "number=" + notification.number);
        printWriter.println(str + "groupAlertBehavior=" + notification.getGroupAlertBehavior());
        printWriter.println(str + "when=" + notification.when);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str);
        sb.append("tickerText=");
        printWriter.print(sb.toString());
        if (!android.text.TextUtils.isEmpty(notification.tickerText)) {
            java.lang.String charSequence = notification.tickerText.toString();
            if (z) {
                printWriter.print(charSequence.length() > 16 ? charSequence.substring(0, 8) : "");
                printWriter.println("...");
            } else {
                printWriter.println(charSequence);
            }
        } else {
            printWriter.println("null");
        }
        printWriter.println(str + "vis=" + notification.visibility);
        printWriter.println(str + "contentView=" + formatRemoteViews(notification.contentView));
        printWriter.println(str + "bigContentView=" + formatRemoteViews(notification.bigContentView));
        printWriter.println(str + "headsUpContentView=" + formatRemoteViews(notification.headsUpContentView));
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
        sb2.append(str);
        sb2.append(java.lang.String.format("color=0x%08x", java.lang.Integer.valueOf(notification.color)));
        printWriter.println(sb2.toString());
        printWriter.println(str + "timeout=" + android.util.TimeUtils.formatForLogging(notification.getTimeoutAfter()));
        if (notification.actions != null && notification.actions.length > 0) {
            printWriter.println(str + "actions={");
            int length = notification.actions.length;
            for (int i = 0; i < length; i++) {
                android.app.Notification.Action action = notification.actions[i];
                if (action != null) {
                    printWriter.println(java.lang.String.format("%s    [%d] \"%s\" -> %s", str, java.lang.Integer.valueOf(i), action.title, action.actionIntent == null ? "null" : action.actionIntent.toString()));
                }
            }
            printWriter.println(str + "  }");
        }
        if (notification.extras != null && notification.extras.size() > 0) {
            printWriter.println(str + "extras={");
            for (java.lang.String str2 : notification.extras.keySet()) {
                printWriter.print(str + "    " + str2 + "=");
                java.lang.Object obj = notification.extras.get(str2);
                if (obj == null) {
                    printWriter.println("null");
                } else {
                    printWriter.print(obj.getClass().getSimpleName());
                    if (z && (obj instanceof java.lang.CharSequence) && shouldRedactStringExtra(str2)) {
                        printWriter.print(java.lang.String.format(" [length=%d]", java.lang.Integer.valueOf(((java.lang.CharSequence) obj).length())));
                    } else if (obj instanceof android.graphics.Bitmap) {
                        android.graphics.Bitmap bitmap = (android.graphics.Bitmap) obj;
                        printWriter.print(java.lang.String.format(" (%dx%d)", java.lang.Integer.valueOf(bitmap.getWidth()), java.lang.Integer.valueOf(bitmap.getHeight())));
                    } else if (obj.getClass().isArray()) {
                        int length2 = java.lang.reflect.Array.getLength(obj);
                        printWriter.print(" (" + length2 + ")");
                        if (!z) {
                            for (int i2 = 0; i2 < length2; i2++) {
                                printWriter.println();
                                printWriter.print(java.lang.String.format("%s      [%d] %s", str, java.lang.Integer.valueOf(i2), java.lang.String.valueOf(java.lang.reflect.Array.get(obj, i2))));
                            }
                        }
                    } else {
                        printWriter.print(" (" + java.lang.String.valueOf(obj) + ")");
                    }
                    printWriter.println();
                }
            }
            printWriter.println(str + "}");
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean shouldRedactStringExtra(java.lang.String str) {
        char c;
        if (str == null) {
            return true;
        }
        switch (str.hashCode()) {
            case -1349298919:
                if (str.equals("android.template")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -330858995:
                if (str.equals("android.substName")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1258919194:
                if (str.equals("android.support.v4.app.extra.COMPAT_TEMPLATE")) {
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
        }
        return true;
    }

    public final java.lang.String toString() {
        return java.lang.String.format("NotificationRecord(0x%08x: pkg=%s user=%s id=%d tag=%s importance=%d key=%s: %s)", java.lang.Integer.valueOf(java.lang.System.identityHashCode(this)), getSbn().getPackageName(), getSbn().getUser(), java.lang.Integer.valueOf(getSbn().getId()), getSbn().getTag(), java.lang.Integer.valueOf(this.mImportance), getSbn().getKey(), getSbn().getNotification());
    }

    public boolean hasAdjustment(java.lang.String str) {
        synchronized (this.mAdjustments) {
            try {
                java.util.Iterator<android.service.notification.Adjustment> it = this.mAdjustments.iterator();
                while (it.hasNext()) {
                    if (it.next().getSignals().containsKey(str)) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void addAdjustment(android.service.notification.Adjustment adjustment) {
        synchronized (this.mAdjustments) {
            this.mAdjustments.add(adjustment);
        }
    }

    public void applyAdjustments() {
        java.lang.System.currentTimeMillis();
        synchronized (this.mAdjustments) {
            try {
                for (android.service.notification.Adjustment adjustment : this.mAdjustments) {
                    android.os.Bundle signals = adjustment.getSignals();
                    if (signals.containsKey("key_people")) {
                        java.util.ArrayList<java.lang.String> stringArrayList = adjustment.getSignals().getStringArrayList("key_people");
                        setPeopleOverride(stringArrayList);
                        com.android.server.EventLogTags.writeNotificationAdjusted(getKey(), "key_people", stringArrayList.toString());
                    }
                    if (signals.containsKey("key_snooze_criteria")) {
                        java.util.ArrayList<android.service.notification.SnoozeCriterion> parcelableArrayList = adjustment.getSignals().getParcelableArrayList("key_snooze_criteria", android.service.notification.SnoozeCriterion.class);
                        setSnoozeCriteria(parcelableArrayList);
                        com.android.server.EventLogTags.writeNotificationAdjusted(getKey(), "key_snooze_criteria", parcelableArrayList.toString());
                    }
                    if (signals.containsKey("key_group_key")) {
                        java.lang.String string = adjustment.getSignals().getString("key_group_key");
                        setOverrideGroupKey(string);
                        com.android.server.EventLogTags.writeNotificationAdjusted(getKey(), "key_group_key", string);
                    }
                    if (signals.containsKey("key_user_sentiment") && !this.mIsAppImportanceLocked && (getChannel().getUserLockedFields() & 4) == 0) {
                        setUserSentiment(adjustment.getSignals().getInt("key_user_sentiment", 0));
                        com.android.server.EventLogTags.writeNotificationAdjusted(getKey(), "key_user_sentiment", java.lang.Integer.toString(getUserSentiment()));
                    }
                    if (signals.containsKey("key_contextual_actions")) {
                        setSystemGeneratedSmartActions(signals.getParcelableArrayList("key_contextual_actions", android.app.Notification.Action.class));
                        com.android.server.EventLogTags.writeNotificationAdjusted(getKey(), "key_contextual_actions", getSystemGeneratedSmartActions().toString());
                    }
                    if (signals.containsKey("key_text_replies")) {
                        setSmartReplies(signals.getCharSequenceArrayList("key_text_replies"));
                        com.android.server.EventLogTags.writeNotificationAdjusted(getKey(), "key_text_replies", getSmartReplies().toString());
                    }
                    if (signals.containsKey("key_importance")) {
                        int min = java.lang.Math.min(4, java.lang.Math.max(-1000, signals.getInt("key_importance")));
                        setAssistantImportance(min);
                        com.android.server.EventLogTags.writeNotificationAdjusted(getKey(), "key_importance", java.lang.Integer.toString(min));
                    }
                    if (signals.containsKey("key_ranking_score")) {
                        this.mRankingScore = signals.getFloat("key_ranking_score");
                        com.android.server.EventLogTags.writeNotificationAdjusted(getKey(), "key_ranking_score", java.lang.Float.toString(this.mRankingScore));
                    }
                    if (signals.containsKey("key_not_conversation")) {
                        this.mIsNotConversationOverride = signals.getBoolean("key_not_conversation");
                        com.android.server.EventLogTags.writeNotificationAdjusted(getKey(), "key_not_conversation", java.lang.Boolean.toString(this.mIsNotConversationOverride));
                    }
                    if (signals.containsKey("key_importance_proposal")) {
                        this.mProposedImportance = signals.getInt("key_importance_proposal");
                        com.android.server.EventLogTags.writeNotificationAdjusted(getKey(), "key_importance_proposal", java.lang.Integer.toString(this.mProposedImportance));
                    }
                    if (signals.containsKey("key_sensitive_content")) {
                        this.mSensitiveContent = signals.getBoolean("key_sensitive_content");
                        com.android.server.EventLogTags.writeNotificationAdjusted(getKey(), "key_sensitive_content", java.lang.Boolean.toString(this.mSensitiveContent));
                    }
                    if (!signals.isEmpty() && adjustment.getIssuer() != null) {
                        this.mAdjustmentIssuer = adjustment.getIssuer();
                    }
                }
                this.mAdjustments.clear();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    java.lang.String getAdjustmentIssuer() {
        return this.mAdjustmentIssuer;
    }

    public void setIsAppImportanceLocked(boolean z) {
        this.mIsAppImportanceLocked = z;
        calculateUserSentiment();
    }

    public void setContactAffinity(float f) {
        this.mContactAffinity = f;
    }

    public float getContactAffinity() {
        return this.mContactAffinity;
    }

    public void setRecentlyIntrusive(boolean z) {
        this.mRecentlyIntrusive = z;
        if (z) {
            this.mLastIntrusive = java.lang.System.currentTimeMillis();
        }
    }

    public boolean isRecentlyIntrusive() {
        return this.mRecentlyIntrusive;
    }

    public long getLastIntrusive() {
        return this.mLastIntrusive;
    }

    public void setPackagePriority(int i) {
        this.mPackagePriority = i;
    }

    public int getPackagePriority() {
        return this.mPackagePriority;
    }

    public void setPackageVisibilityOverride(int i) {
        this.mPackageVisibility = i;
    }

    public int getPackageVisibilityOverride() {
        return this.mPackageVisibility;
    }

    private java.lang.String getUserExplanation() {
        if (this.mUserExplanation == null) {
            this.mUserExplanation = this.mContext.getResources().getString(android.R.string.imProtocolGoogleTalk);
        }
        return this.mUserExplanation;
    }

    public void setSystemImportance(int i) {
        this.mSystemImportance = i;
        calculateImportance();
    }

    public void setAssistantImportance(int i) {
        this.mAssistantImportance = i;
    }

    public int getAssistantImportance() {
        return this.mAssistantImportance;
    }

    public void setImportanceFixed(boolean z) {
        this.mImportanceFixed = z;
    }

    public boolean isImportanceFixed() {
        return this.mImportanceFixed;
    }

    protected void calculateImportance() {
        this.mImportance = calculateInitialImportance();
        this.mImportanceExplanationCode = this.mInitialImportanceExplanationCode;
        if (!getChannel().hasUserSetImportance() && this.mAssistantImportance != -1000 && !this.mImportanceFixed) {
            this.mImportance = this.mAssistantImportance;
            this.mImportanceExplanationCode = 3;
        }
        if (this.mSystemImportance != -1000) {
            this.mImportance = this.mSystemImportance;
            this.mImportanceExplanationCode = 4;
        }
    }

    public int getImportance() {
        return this.mImportance;
    }

    int getInitialImportance() {
        return this.stats.naturalImportance;
    }

    public int getProposedImportance() {
        return this.mProposedImportance;
    }

    public boolean hasSensitiveContent() {
        return this.mSensitiveContent;
    }

    public float getRankingScore() {
        return this.mRankingScore;
    }

    int getImportanceExplanationCode() {
        return this.mImportanceExplanationCode;
    }

    int getInitialImportanceExplanationCode() {
        return this.mInitialImportanceExplanationCode;
    }

    public java.lang.CharSequence getImportanceExplanation() {
        switch (this.mImportanceExplanationCode) {
        }
        return null;
    }

    public boolean setIntercepted(boolean z) {
        this.mIntercept = z;
        this.mInterceptSet = true;
        return this.mIntercept;
    }

    public void setCriticality(int i) {
        this.mCriticality = i;
    }

    public int getCriticality() {
        return this.mCriticality;
    }

    public boolean isIntercepted() {
        return this.mIntercept;
    }

    public boolean hasInterceptBeenSet() {
        return this.mInterceptSet;
    }

    public boolean isNewEnoughForAlerting(long j) {
        return getFreshnessMs(j) <= 2000;
    }

    public void setHidden(boolean z) {
        this.mHidden = z;
    }

    public boolean isHidden() {
        return this.mHidden;
    }

    public boolean isForegroundService() {
        return (getFlags() & 64) != 0;
    }

    public void setPostSilently(boolean z) {
        this.mPostSilently = z;
    }

    public boolean shouldPostSilently() {
        return this.mPostSilently;
    }

    public void setSuppressedVisualEffects(int i) {
        this.mSuppressedVisualEffects = i;
    }

    public int getSuppressedVisualEffects() {
        return this.mSuppressedVisualEffects;
    }

    public boolean isCategory(java.lang.String str) {
        return java.util.Objects.equals(getNotification().category, str);
    }

    public boolean isAudioAttributesUsage(int i) {
        return this.mAttributes.getUsage() == i;
    }

    public long getRankingTimeMs() {
        return this.mRankingTimeMs;
    }

    public int getFreshnessMs(long j) {
        return (int) (j - this.mUpdateTimeMs);
    }

    public int getLifespanMs(long j) {
        return (int) (j - this.mCreationTimeMs);
    }

    public int getExposureMs(long j) {
        if (this.mVisibleSinceMs == 0) {
            return 0;
        }
        return (int) (j - this.mVisibleSinceMs);
    }

    public int getInterruptionMs(long j) {
        return (int) (j - this.mInterruptionTimeMs);
    }

    public long getUpdateTimeMs() {
        return this.mUpdateTimeMs;
    }

    public void setVisibility(boolean z, int i, int i2, com.android.server.notification.NotificationRecordLogger notificationRecordLogger) {
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        this.mVisibleSinceMs = z ? currentTimeMillis : this.mVisibleSinceMs;
        this.stats.onVisibilityChanged(z);
        com.android.internal.logging.MetricsLogger.action(getLogMaker(currentTimeMillis).setCategory(128).setType(z ? 1 : 2).addTaggedData(798, java.lang.Integer.valueOf(i)).addTaggedData(1395, java.lang.Integer.valueOf(i2)));
        if (z) {
            setSeen();
            com.android.internal.logging.MetricsLogger.histogram(this.mContext, "note_freshness", getFreshnessMs(currentTimeMillis));
        }
        com.android.server.EventLogTags.writeNotificationVisibility(getKey(), z ? 1 : 0, getLifespanMs(currentTimeMillis), getFreshnessMs(currentTimeMillis), 0, i);
        notificationRecordLogger.logNotificationVisibility(this, z);
    }

    private long calculateRankingTimeMs(long j) {
        android.app.Notification notification = getNotification();
        if (notification.when != 0 && notification.when <= getSbn().getPostTime()) {
            return notification.when;
        }
        if (j > 0) {
            return j;
        }
        return getSbn().getPostTime();
    }

    public void setGlobalSortKey(java.lang.String str) {
        this.mGlobalSortKey = str;
    }

    public java.lang.String getGlobalSortKey() {
        return this.mGlobalSortKey;
    }

    public boolean isSeen() {
        return this.mStats.hasSeen();
    }

    public void setSeen() {
        this.mStats.setSeen();
        if (this.mTextChanged) {
            setInterruptive(true);
        }
    }

    public void setAuthoritativeRank(int i) {
        this.mAuthoritativeRank = i;
    }

    public int getAuthoritativeRank() {
        return this.mAuthoritativeRank;
    }

    public java.lang.String getGroupKey() {
        return getSbn().getGroupKey();
    }

    public void setOverrideGroupKey(java.lang.String str) {
        getSbn().setOverrideGroupKey(str);
    }

    public android.app.NotificationChannel getChannel() {
        return this.mChannel;
    }

    public boolean getIsAppImportanceLocked() {
        return this.mIsAppImportanceLocked;
    }

    protected void updateNotificationChannel(android.app.NotificationChannel notificationChannel) {
        if (notificationChannel != null) {
            this.mChannel = notificationChannel;
            calculateImportance();
            calculateUserSentiment();
        }
    }

    public void setShowBadge(boolean z) {
        this.mShowBadge = z;
    }

    public boolean canBubble() {
        return this.mAllowBubble;
    }

    public void setAllowBubble(boolean z) {
        this.mAllowBubble = z;
    }

    public boolean canShowBadge() {
        return this.mShowBadge;
    }

    public com.android.server.notification.NotificationRecord.Light getLight() {
        return this.mLight;
    }

    public android.net.Uri getSound() {
        return this.mSound;
    }

    public android.os.VibrationEffect getVibration() {
        return this.mVibration;
    }

    @android.annotation.NonNull
    public android.media.AudioAttributes getAudioAttributes() {
        return this.mAttributes;
    }

    public java.util.ArrayList<java.lang.String> getPeopleOverride() {
        return this.mPeopleOverride;
    }

    public void setInterruptive(boolean z) {
        this.mIsInterruptive = z;
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        this.mInterruptionTimeMs = z ? currentTimeMillis : this.mInterruptionTimeMs;
        if (z) {
            com.android.internal.logging.MetricsLogger.action(getLogMaker().setCategory(1501).setType(1).addTaggedData(android.net.util.NetworkConstants.ETHER_MTU, java.lang.Integer.valueOf(getInterruptionMs(currentTimeMillis))));
            com.android.internal.logging.MetricsLogger.histogram(this.mContext, "note_interruptive", getInterruptionMs(currentTimeMillis));
        }
    }

    public void setAudiblyAlerted(boolean z) {
        this.mLastAudiblyAlertedMs = z ? java.lang.System.currentTimeMillis() : -1L;
    }

    public void setTextChanged(boolean z) {
        this.mTextChanged = z;
    }

    public void setRecordedInterruption(boolean z) {
        this.mRecordedInterruption = z;
    }

    public boolean hasRecordedInterruption() {
        return this.mRecordedInterruption;
    }

    public boolean isInterruptive() {
        return this.mIsInterruptive;
    }

    public boolean isTextChanged() {
        return this.mTextChanged;
    }

    public long getLastAudiblyAlertedMs() {
        return this.mLastAudiblyAlertedMs;
    }

    protected void setPeopleOverride(java.util.ArrayList<java.lang.String> arrayList) {
        this.mPeopleOverride = arrayList;
    }

    public java.util.ArrayList<android.service.notification.SnoozeCriterion> getSnoozeCriteria() {
        return this.mSnoozeCriteria;
    }

    protected void setSnoozeCriteria(java.util.ArrayList<android.service.notification.SnoozeCriterion> arrayList) {
        this.mSnoozeCriteria = arrayList;
    }

    private void calculateUserSentiment() {
        if ((getChannel().getUserLockedFields() & 4) != 0 || this.mIsAppImportanceLocked) {
            this.mUserSentiment = 1;
        }
    }

    private void setUserSentiment(int i) {
        this.mUserSentiment = i;
    }

    public int getUserSentiment() {
        return this.mUserSentiment;
    }

    public android.service.notification.NotificationStats getStats() {
        return this.mStats;
    }

    public void recordExpanded() {
        this.mStats.setExpanded();
    }

    public void recordDirectReplied() {
        if (android.app.Flags.lifetimeExtensionRefactor()) {
            getSbn().getNotification().flags |= 65536;
        }
        this.mStats.setDirectReplied();
    }

    public void recordSmartReplied() {
        getSbn().getNotification().flags |= 65536;
        this.mStats.setSmartReplied();
    }

    public void recordDismissalSurface(int i) {
        this.mStats.setDismissalSurface(i);
    }

    public void recordDismissalSentiment(int i) {
        this.mStats.setDismissalSentiment(i);
    }

    public void recordSnoozed() {
        this.mStats.setSnoozed();
    }

    public void recordViewedSettings() {
        this.mStats.setViewedSettings();
    }

    public void setNumSmartRepliesAdded(int i) {
        this.mNumberOfSmartRepliesAdded = i;
    }

    public int getNumSmartRepliesAdded() {
        return this.mNumberOfSmartRepliesAdded;
    }

    public void setNumSmartActionsAdded(int i) {
        this.mNumberOfSmartActionsAdded = i;
    }

    public int getNumSmartActionsAdded() {
        return this.mNumberOfSmartActionsAdded;
    }

    public void setSuggestionsGeneratedByAssistant(boolean z) {
        this.mSuggestionsGeneratedByAssistant = z;
    }

    public boolean getSuggestionsGeneratedByAssistant() {
        return this.mSuggestionsGeneratedByAssistant;
    }

    public boolean getEditChoicesBeforeSending() {
        return this.mEditChoicesBeforeSending;
    }

    public void setEditChoicesBeforeSending(boolean z) {
        this.mEditChoicesBeforeSending = z;
    }

    public boolean hasSeenSmartReplies() {
        return this.mHasSeenSmartReplies;
    }

    public void setSeenSmartReplies(boolean z) {
        this.mHasSeenSmartReplies = z;
    }

    public boolean hasBeenVisiblyExpanded() {
        return this.stats.hasBeenVisiblyExpanded();
    }

    public boolean isFlagBubbleRemoved() {
        return this.mFlagBubbleRemoved;
    }

    public void setFlagBubbleRemoved(boolean z) {
        this.mFlagBubbleRemoved = z;
    }

    public void setSystemGeneratedSmartActions(java.util.ArrayList<android.app.Notification.Action> arrayList) {
        this.mSystemGeneratedSmartActions = arrayList;
    }

    public java.util.ArrayList<android.app.Notification.Action> getSystemGeneratedSmartActions() {
        return this.mSystemGeneratedSmartActions;
    }

    public void setSmartReplies(java.util.ArrayList<java.lang.CharSequence> arrayList) {
        this.mSmartReplies = arrayList;
    }

    public java.util.ArrayList<java.lang.CharSequence> getSmartReplies() {
        return this.mSmartReplies;
    }

    public boolean isProxied() {
        return !java.util.Objects.equals(getSbn().getPackageName(), getSbn().getOpPkg());
    }

    public int getNotificationType() {
        if (isConversation()) {
            return 1;
        }
        if (getImportance() >= 3) {
            return 2;
        }
        return 4;
    }

    @android.annotation.Nullable
    public android.util.ArraySet<android.net.Uri> getGrantableUris() {
        return this.mGrantableUris;
    }

    private void calculateGrantableUris() {
        android.app.NotificationChannel channel;
        android.os.Trace.beginSection("NotificationRecord.calculateGrantableUris");
        try {
            if (getSbn().getUid() == 1000) {
                return;
            }
            android.app.Notification notification = getNotification();
            notification.visitUris(new java.util.function.Consumer() { // from class: com.android.server.notification.NotificationRecord$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.notification.NotificationRecord.this.lambda$calculateGrantableUris$0((android.net.Uri) obj);
                }
            });
            if (notification.getChannelId() != null && (channel = getChannel()) != null) {
                visitGrantableUri(channel.getSound(), (channel.getUserLockedFields() & 32) != 0, true);
            }
        } finally {
            android.os.Trace.endSection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$calculateGrantableUris$0(android.net.Uri uri) {
        visitGrantableUri(uri, false, false);
    }

    private void visitGrantableUri(android.net.Uri uri, boolean z, boolean z2) {
        if (uri == null || !com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT.equals(uri.getScheme())) {
            return;
        }
        if (this.mGrantableUris != null && this.mGrantableUris.contains(uri)) {
            return;
        }
        int uid = getSbn().getUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mUgmInternal.checkGrantUriPermission(uid, null, android.content.ContentProvider.getUriWithoutUserId(uri), 1, android.content.ContentProvider.getUserIdFromUri(uri, android.os.UserHandle.getUserId(uid)));
                if (this.mGrantableUris == null) {
                    this.mGrantableUris = new android.util.ArraySet<>();
                }
                this.mGrantableUris.add(uri);
            } catch (java.lang.SecurityException e) {
                if (!z) {
                    if (z2) {
                        this.mSound = android.provider.Settings.System.DEFAULT_NOTIFICATION_URI;
                        android.util.Log.w(TAG, "Replacing " + uri + " from " + uid + ": " + e.getMessage());
                    } else {
                        if (this.mTargetSdkVersion >= 28) {
                            throw e;
                        }
                        android.util.Log.w(TAG, "Ignoring " + uri + " from " + uid + ": " + e.getMessage());
                    }
                }
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public android.metrics.LogMaker getLogMaker(long j) {
        android.metrics.LogMaker addTaggedData = getSbn().getLogMaker().addTaggedData(858, java.lang.Integer.valueOf(this.mImportance)).addTaggedData(793, java.lang.Integer.valueOf(getLifespanMs(j))).addTaggedData(795, java.lang.Integer.valueOf(getFreshnessMs(j))).addTaggedData(794, java.lang.Integer.valueOf(getExposureMs(j))).addTaggedData(android.net.util.NetworkConstants.ETHER_MTU, java.lang.Integer.valueOf(getInterruptionMs(j)));
        if (this.mImportanceExplanationCode != 0) {
            addTaggedData.addTaggedData(1688, java.lang.Integer.valueOf(this.mImportanceExplanationCode));
            if ((this.mImportanceExplanationCode == 3 || this.mImportanceExplanationCode == 4) && this.stats.naturalImportance != -1000) {
                addTaggedData.addTaggedData(1690, java.lang.Integer.valueOf(this.mInitialImportanceExplanationCode));
                addTaggedData.addTaggedData(1689, java.lang.Integer.valueOf(this.stats.naturalImportance));
            }
        }
        if (this.mAssistantImportance != -1000) {
            addTaggedData.addTaggedData(1691, java.lang.Integer.valueOf(this.mAssistantImportance));
        }
        if (this.mAdjustmentIssuer != null) {
            addTaggedData.addTaggedData(1742, java.lang.Integer.valueOf(this.mAdjustmentIssuer.hashCode()));
        }
        return addTaggedData;
    }

    public android.metrics.LogMaker getLogMaker() {
        return getLogMaker(java.lang.System.currentTimeMillis());
    }

    public android.metrics.LogMaker getItemLogMaker() {
        return getLogMaker().setCategory(128);
    }

    public boolean hasUndecoratedRemoteView() {
        android.app.Notification notification = getNotification();
        return (notification.contentView != null || notification.bigContentView != null || notification.headsUpContentView != null) && !(notification.isStyle(android.app.Notification.DecoratedCustomViewStyle.class) || notification.isStyle(android.app.Notification.DecoratedMediaCustomViewStyle.class));
    }

    public void setShortcutInfo(android.content.pm.ShortcutInfo shortcutInfo) {
        this.mShortcutInfo = shortcutInfo;
    }

    public android.content.pm.ShortcutInfo getShortcutInfo() {
        return this.mShortcutInfo;
    }

    public void setHasSentValidMsg(boolean z) {
        this.mHasSentValidMsg = z;
    }

    public void userDemotedAppFromConvoSpace(boolean z) {
        this.mAppDemotedFromConvo = z;
    }

    public void setPkgAllowedAsConvo(boolean z) {
        this.mPkgAllowedAsConvo = z;
    }

    public boolean isConversation() {
        android.app.Notification notification = getNotification();
        if (this.mChannel.isDemoted() || this.mAppDemotedFromConvo || this.mIsNotConversationOverride) {
            return false;
        }
        if (!notification.isStyle(android.app.Notification.MessagingStyle.class)) {
            return this.mPkgAllowedAsConvo && this.mTargetSdkVersion < 30 && "msg".equals(getNotification().category);
        }
        if (this.mTargetSdkVersion >= 30 && notification.isStyle(android.app.Notification.MessagingStyle.class) && (this.mShortcutInfo == null || isOnlyBots(this.mShortcutInfo.getPersons()))) {
            return false;
        }
        return (this.mHasSentValidMsg && this.mShortcutInfo == null) ? false : true;
    }

    private boolean isOnlyBots(android.app.Person[] personArr) {
        if (personArr == null || personArr.length == 0) {
            return false;
        }
        for (android.app.Person person : personArr) {
            if (!person.isBot()) {
                return false;
            }
        }
        return true;
    }

    android.service.notification.StatusBarNotification getSbn() {
        return this.sbn;
    }

    public boolean rankingScoreMatches(float f) {
        return ((double) java.lang.Math.abs(this.mRankingScore - f)) < 1.0E-4d;
    }

    protected void setPendingLogUpdate(boolean z) {
        this.mPendingLogUpdate = z;
    }

    protected boolean hasPendingLogUpdate() {
        return this.mPendingLogUpdate;
    }

    public void mergePhoneNumbers(android.util.ArraySet<java.lang.String> arraySet) {
        if (arraySet == null || arraySet.size() == 0) {
            return;
        }
        if (this.mPhoneNumbers == null) {
            this.mPhoneNumbers = new android.util.ArraySet<>();
        }
        this.mPhoneNumbers.addAll((android.util.ArraySet<? extends java.lang.String>) arraySet);
    }

    public android.util.ArraySet<java.lang.String> getPhoneNumbers() {
        return this.mPhoneNumbers;
    }

    boolean isLocked() {
        return getKeyguardManager().isKeyguardLocked() || !this.mPowerManager.isInteractive();
    }

    private android.app.KeyguardManager getKeyguardManager() {
        if (this.mKeyguardManager == null) {
            this.mKeyguardManager = (android.app.KeyguardManager) this.mContext.getSystemService(android.app.KeyguardManager.class);
        }
        return this.mKeyguardManager;
    }

    @com.android.internal.annotations.VisibleForTesting
    static final class Light {
        public final int color;
        public final int offMs;
        public final int onMs;

        public Light(int i, int i2, int i3) {
            this.color = i;
            this.onMs = i2;
            this.offMs = i3;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || com.android.server.notification.NotificationRecord.Light.class != obj.getClass()) {
                return false;
            }
            com.android.server.notification.NotificationRecord.Light light = (com.android.server.notification.NotificationRecord.Light) obj;
            if (this.color == light.color && this.onMs == light.onMs && this.offMs == light.offMs) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (((this.color * 31) + this.onMs) * 31) + this.offMs;
        }

        public java.lang.String toString() {
            return "Light{color=" + this.color + ", onMs=" + this.onMs + ", offMs=" + this.offMs + '}';
        }
    }
}
