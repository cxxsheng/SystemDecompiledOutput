package com.android.server.am;

/* loaded from: classes.dex */
public final class PendingIntentRecord extends android.content.IIntentSender.Stub {
    private static final long DEFAULT_RESCIND_BAL_PRIVILEGES_FROM_PENDING_INTENT_SENDER = 244637991;
    public static final int FLAG_ACTIVITY_SENDER = 1;
    public static final int FLAG_BROADCAST_SENDER = 2;
    public static final int FLAG_SERVICE_SENDER = 4;
    private static final java.lang.String TAG = "ActivityManager";
    final com.android.server.am.PendingIntentController controller;
    final com.android.server.am.PendingIntentRecord.Key key;
    java.lang.String lastTag;
    java.lang.String lastTagPrefix;
    private android.util.ArrayMap<android.os.IBinder, com.android.server.am.PendingIntentRecord.TempAllowListDuration> mAllowlistDuration;
    private android.os.RemoteCallbackList<com.android.internal.os.IResultReceiver> mCancelCallbacks;
    java.lang.String stringName;
    final int uid;
    boolean sent = false;
    boolean canceled = false;
    private android.util.ArraySet<android.os.IBinder> mAllowBgActivityStartsForActivitySender = new android.util.ArraySet<>();
    private android.util.ArraySet<android.os.IBinder> mAllowBgActivityStartsForBroadcastSender = new android.util.ArraySet<>();
    private android.util.ArraySet<android.os.IBinder> mAllowBgActivityStartsForServiceSender = new android.util.ArraySet<>();
    public final java.lang.ref.WeakReference<com.android.server.am.PendingIntentRecord> ref = new java.lang.ref.WeakReference<>(this);

    static final class Key {
        private static final int ODD_PRIME_NUMBER = 37;
        final android.os.IBinder activity;
        android.content.Intent[] allIntents;
        java.lang.String[] allResolvedTypes;
        final java.lang.String featureId;
        final int flags;
        final int hashCode;
        final com.android.server.wm.SafeActivityOptions options;
        final java.lang.String packageName;
        final int requestCode;
        final android.content.Intent requestIntent;
        final java.lang.String requestResolvedType;
        final int type;
        final int userId;
        final java.lang.String who;

        Key(int i, java.lang.String str, @android.annotation.Nullable java.lang.String str2, android.os.IBinder iBinder, java.lang.String str3, int i2, android.content.Intent[] intentArr, java.lang.String[] strArr, int i3, com.android.server.wm.SafeActivityOptions safeActivityOptions, int i4) {
            this.type = i;
            this.packageName = str;
            this.featureId = str2;
            this.activity = iBinder;
            this.who = str3;
            this.requestCode = i2;
            this.requestIntent = intentArr != null ? intentArr[intentArr.length - 1] : null;
            this.requestResolvedType = strArr != null ? strArr[strArr.length - 1] : null;
            this.allIntents = intentArr;
            this.allResolvedTypes = strArr;
            this.flags = i3;
            this.options = safeActivityOptions;
            this.userId = i4;
            int i5 = ((((851 + i3) * 37) + i2) * 37) + i4;
            i5 = str3 != null ? (i5 * 37) + str3.hashCode() : i5;
            i5 = iBinder != null ? (i5 * 37) + iBinder.hashCode() : i5;
            i5 = this.requestIntent != null ? (i5 * 37) + this.requestIntent.filterHashCode() : i5;
            this.hashCode = ((((this.requestResolvedType != null ? (i5 * 37) + this.requestResolvedType.hashCode() : i5) * 37) + (str != null ? str.hashCode() : 0)) * 37) + i;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null) {
                return false;
            }
            try {
                com.android.server.am.PendingIntentRecord.Key key = (com.android.server.am.PendingIntentRecord.Key) obj;
                if (this.type != key.type || this.userId != key.userId || !java.util.Objects.equals(this.packageName, key.packageName) || !java.util.Objects.equals(this.featureId, key.featureId) || this.activity != key.activity || !java.util.Objects.equals(this.who, key.who) || this.requestCode != key.requestCode) {
                    return false;
                }
                if (this.requestIntent != key.requestIntent) {
                    if (this.requestIntent != null) {
                        if (!this.requestIntent.filterEquals(key.requestIntent)) {
                            return false;
                        }
                    } else if (key.requestIntent != null) {
                        return false;
                    }
                }
                if (!java.util.Objects.equals(this.requestResolvedType, key.requestResolvedType)) {
                    return false;
                }
                if (this.flags != key.flags) {
                    return false;
                }
                return true;
            } catch (java.lang.ClassCastException e) {
                return false;
            }
        }

        public int hashCode() {
            return this.hashCode;
        }

        public java.lang.String toString() {
            java.lang.String str;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Key{");
            sb.append(typeName());
            sb.append(" pkg=");
            sb.append(this.packageName);
            if (this.featureId != null) {
                str = com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + this.featureId;
            } else {
                str = "";
            }
            sb.append(str);
            sb.append(" intent=");
            sb.append(this.requestIntent != null ? this.requestIntent.toShortString(false, true, false, false) : "<null>");
            sb.append(" flags=0x");
            sb.append(java.lang.Integer.toHexString(this.flags));
            sb.append(" u=");
            sb.append(this.userId);
            sb.append("} requestCode=");
            sb.append(this.requestCode);
            return sb.toString();
        }

        java.lang.String typeName() {
            switch (this.type) {
                case 1:
                    return "broadcastIntent";
                case 2:
                    return "startActivity";
                case 3:
                    return "activityResult";
                case 4:
                    return "startService";
                case 5:
                    return "startForegroundService";
                default:
                    return java.lang.Integer.toString(this.type);
            }
        }
    }

    static final class TempAllowListDuration {
        long duration;

        @android.annotation.Nullable
        java.lang.String reason;
        int reasonCode;
        int type;

        TempAllowListDuration(long j, int i, int i2, java.lang.String str) {
            this.duration = j;
            this.type = i;
            this.reasonCode = i2;
            this.reason = str;
        }
    }

    PendingIntentRecord(com.android.server.am.PendingIntentController pendingIntentController, com.android.server.am.PendingIntentRecord.Key key, int i) {
        this.controller = pendingIntentController;
        this.key = key;
        this.uid = i;
    }

    void setAllowlistDurationLocked(android.os.IBinder iBinder, long j, int i, int i2, @android.annotation.Nullable java.lang.String str) {
        if (j > 0) {
            if (this.mAllowlistDuration == null) {
                this.mAllowlistDuration = new android.util.ArrayMap<>();
            }
            this.mAllowlistDuration.put(iBinder, new com.android.server.am.PendingIntentRecord.TempAllowListDuration(j, i, i2, str));
        } else if (this.mAllowlistDuration != null) {
            this.mAllowlistDuration.remove(iBinder);
            if (this.mAllowlistDuration.size() <= 0) {
                this.mAllowlistDuration = null;
            }
        }
        this.stringName = null;
    }

    void setAllowBgActivityStarts(android.os.IBinder iBinder, int i) {
        if (iBinder == null) {
            return;
        }
        if ((i & 1) != 0) {
            this.mAllowBgActivityStartsForActivitySender.add(iBinder);
        }
        if ((i & 2) != 0) {
            this.mAllowBgActivityStartsForBroadcastSender.add(iBinder);
        }
        if ((i & 4) != 0) {
            this.mAllowBgActivityStartsForServiceSender.add(iBinder);
        }
    }

    void clearAllowBgActivityStarts(android.os.IBinder iBinder) {
        if (iBinder == null) {
            return;
        }
        this.mAllowBgActivityStartsForActivitySender.remove(iBinder);
        this.mAllowBgActivityStartsForBroadcastSender.remove(iBinder);
        this.mAllowBgActivityStartsForServiceSender.remove(iBinder);
    }

    public void registerCancelListenerLocked(com.android.internal.os.IResultReceiver iResultReceiver) {
        if (this.mCancelCallbacks == null) {
            this.mCancelCallbacks = new android.os.RemoteCallbackList<>();
        }
        this.mCancelCallbacks.register(iResultReceiver);
    }

    public void unregisterCancelListenerLocked(com.android.internal.os.IResultReceiver iResultReceiver) {
        if (this.mCancelCallbacks == null) {
            return;
        }
        this.mCancelCallbacks.unregister(iResultReceiver);
        if (this.mCancelCallbacks.getRegisteredCallbackCount() <= 0) {
            this.mCancelCallbacks = null;
        }
    }

    public android.os.RemoteCallbackList<com.android.internal.os.IResultReceiver> detachCancelListenersLocked() {
        android.os.RemoteCallbackList<com.android.internal.os.IResultReceiver> remoteCallbackList = this.mCancelCallbacks;
        this.mCancelCallbacks = null;
        return remoteCallbackList;
    }

    public void send(int i, android.content.Intent intent, java.lang.String str, android.os.IBinder iBinder, android.content.IIntentReceiver iIntentReceiver, java.lang.String str2, android.os.Bundle bundle) {
        sendInner(null, i, intent, str, iBinder, iIntentReceiver, str2, null, null, 0, 0, 0, bundle);
    }

    public void send(android.app.IApplicationThread iApplicationThread, int i, android.content.Intent intent, java.lang.String str, android.os.IBinder iBinder, android.content.IIntentReceiver iIntentReceiver, java.lang.String str2, android.os.Bundle bundle) {
        sendInner(iApplicationThread, i, intent, str, iBinder, iIntentReceiver, str2, null, null, 0, 0, 0, bundle);
    }

    public int sendWithResult(android.app.IApplicationThread iApplicationThread, int i, android.content.Intent intent, java.lang.String str, android.os.IBinder iBinder, android.content.IIntentReceiver iIntentReceiver, java.lang.String str2, android.os.Bundle bundle) {
        return sendInner(iApplicationThread, i, intent, str, iBinder, iIntentReceiver, str2, null, null, 0, 0, 0, bundle);
    }

    public static boolean isPendingIntentBalAllowedByPermission(@android.annotation.Nullable android.app.ActivityOptions activityOptions) {
        if (activityOptions == null) {
            return false;
        }
        return activityOptions.isPendingIntentBackgroundActivityLaunchAllowedByPermission();
    }

    public static android.app.BackgroundStartPrivileges getBackgroundStartPrivilegesAllowedByCaller(@android.annotation.Nullable android.app.ActivityOptions activityOptions, int i, @android.annotation.Nullable java.lang.String str) {
        if (activityOptions == null) {
            return getDefaultBackgroundStartPrivileges(i, str);
        }
        return getBackgroundStartPrivilegesAllowedByCaller(activityOptions.toBundle(), i, str);
    }

    private static android.app.BackgroundStartPrivileges getBackgroundStartPrivilegesAllowedByCaller(@android.annotation.Nullable android.os.Bundle bundle, int i, @android.annotation.Nullable java.lang.String str) {
        if (bundle == null || !bundle.containsKey("android.pendingIntent.backgroundActivityAllowed")) {
            return getDefaultBackgroundStartPrivileges(i, str);
        }
        if (bundle.getBoolean("android.pendingIntent.backgroundActivityAllowed")) {
            return android.app.BackgroundStartPrivileges.ALLOW_BAL;
        }
        return android.app.BackgroundStartPrivileges.NONE;
    }

    @android.annotation.RequiresPermission(allOf = {"android.permission.READ_COMPAT_CHANGE_CONFIG", "android.permission.LOG_COMPAT_CHANGE"})
    public static android.app.BackgroundStartPrivileges getDefaultBackgroundStartPrivileges(int i, @android.annotation.Nullable java.lang.String str) {
        boolean isChangeEnabled;
        if (str != null) {
            isChangeEnabled = android.app.compat.CompatChanges.isChangeEnabled(DEFAULT_RESCIND_BAL_PRIVILEGES_FROM_PENDING_INTENT_SENDER, str, android.os.UserHandle.getUserHandleForUid(i));
        } else {
            isChangeEnabled = android.app.compat.CompatChanges.isChangeEnabled(DEFAULT_RESCIND_BAL_PRIVILEGES_FROM_PENDING_INTENT_SENDER, i);
        }
        if (isChangeEnabled) {
            return android.app.BackgroundStartPrivileges.ALLOW_FGS;
        }
        return android.app.BackgroundStartPrivileges.ALLOW_BAL;
    }

    @java.lang.Deprecated
    public int sendInner(int i, android.content.Intent intent, java.lang.String str, android.os.IBinder iBinder, android.content.IIntentReceiver iIntentReceiver, java.lang.String str2, android.os.IBinder iBinder2, java.lang.String str3, int i2, int i3, int i4, android.os.Bundle bundle) {
        return sendInner(null, i, intent, str, iBinder, iIntentReceiver, str2, iBinder2, str3, i2, i3, i4, bundle);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:107:0x03b3  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x02dd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0279 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:132:0x026e  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0227  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0263 A[Catch: all -> 0x0186, TryCatch #3 {all -> 0x0186, blocks: (B:65:0x0171, B:68:0x018b, B:70:0x01a1, B:71:0x01c8, B:77:0x0230, B:78:0x025c, B:80:0x0263, B:81:0x0270, B:122:0x0279, B:125:0x0290, B:83:0x02b9, B:109:0x02dd, B:111:0x02e3, B:113:0x02eb, B:97:0x03bb, B:116:0x031a, B:86:0x035f, B:89:0x037a, B:106:0x03ab, B:131:0x02b1, B:120:0x0354, B:134:0x01a9, B:136:0x01af, B:137:0x01b7, B:139:0x01bd, B:140:0x0189, B:141:0x01eb, B:144:0x01f8, B:146:0x0207), top: B:63:0x016f, inners: #2, #4, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x02b9 A[Catch: all -> 0x0186, TRY_LEAVE, TryCatch #3 {all -> 0x0186, blocks: (B:65:0x0171, B:68:0x018b, B:70:0x01a1, B:71:0x01c8, B:77:0x0230, B:78:0x025c, B:80:0x0263, B:81:0x0270, B:122:0x0279, B:125:0x0290, B:83:0x02b9, B:109:0x02dd, B:111:0x02e3, B:113:0x02eb, B:97:0x03bb, B:116:0x031a, B:86:0x035f, B:89:0x037a, B:106:0x03ab, B:131:0x02b1, B:120:0x0354, B:134:0x01a9, B:136:0x01af, B:137:0x01b7, B:139:0x01bd, B:140:0x0189, B:141:0x01eb, B:144:0x01f8, B:146:0x0207), top: B:63:0x016f, inners: #2, #4, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x035c  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x03b7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int sendInner(android.app.IApplicationThread iApplicationThread, int i, android.content.Intent intent, java.lang.String str, android.os.IBinder iBinder, android.content.IIntentReceiver iIntentReceiver, java.lang.String str2, android.os.IBinder iBinder2, java.lang.String str3, int i2, int i3, int i4, android.os.Bundle bundle) {
        java.lang.String str4;
        com.android.server.wm.SafeActivityOptions safeActivityOptions;
        com.android.server.am.PendingIntentRecord.TempAllowListDuration tempAllowListDuration;
        android.content.Intent[] intentArr;
        java.lang.String[] strArr;
        int i5;
        android.content.Intent intent2;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        java.lang.String str5;
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        if (intent != null) {
            intent.setDefusable(true);
        }
        if (bundle != null) {
            bundle.setDefusable(true);
        }
        synchronized (this.controller.mLock) {
            try {
                if (this.canceled) {
                    return -96;
                }
                this.sent = true;
                if ((this.key.flags & 1073741824) != 0) {
                    this.controller.cancelIntentSender(this, true);
                }
                android.content.Intent intent3 = this.key.requestIntent != null ? new android.content.Intent(this.key.requestIntent) : new android.content.Intent();
                if (!((this.key.flags & 67108864) != 0)) {
                    if (intent != null) {
                        if ((intent3.fillIn(intent, this.key.flags) & 2) != 0) {
                            str5 = str;
                        } else {
                            str5 = this.key.requestResolvedType;
                        }
                    } else {
                        str5 = this.key.requestResolvedType;
                    }
                    int i11 = i3 & (-196);
                    intent3.setFlags(((~i11) & intent3.getFlags()) | (i4 & i11));
                    str4 = str5;
                } else {
                    str4 = this.key.requestResolvedType;
                }
                android.app.ActivityOptions fromBundle = android.app.ActivityOptions.fromBundle(bundle);
                if (fromBundle != null) {
                    if (fromBundle.getPendingIntentCreatorBackgroundActivityStartMode() != 0) {
                        android.util.Slog.wtf(TAG, "Resetting option setPendingIntentCreatorBackgroundActivityStartMode(" + fromBundle.getPendingIntentCreatorBackgroundActivityStartMode() + ") to SYSTEM_DEFINED from the options provided by the pending intent sender (" + this.key.packageName + ") because this option is meant for the pending intent creator");
                        if (android.app.compat.CompatChanges.isChangeEnabled(320664730L, callingUid)) {
                            throw new java.lang.IllegalArgumentException("pendingIntentCreatorBackgroundActivityStartMode must not be set when sending a PendingIntent");
                        }
                        fromBundle.setPendingIntentCreatorBackgroundActivityStartMode(0);
                    }
                    intent3.addFlags(fromBundle.getPendingIntentLaunchFlags());
                }
                com.android.server.wm.SafeActivityOptions safeActivityOptions2 = this.key.options;
                if (safeActivityOptions2 == null) {
                    safeActivityOptions = new com.android.server.wm.SafeActivityOptions(fromBundle);
                } else {
                    safeActivityOptions2.setCallerOptions(fromBundle);
                    safeActivityOptions = safeActivityOptions2;
                }
                if (this.mAllowlistDuration == null) {
                    tempAllowListDuration = null;
                } else {
                    tempAllowListDuration = this.mAllowlistDuration.get(iBinder);
                }
                if (this.key.type == 2 && this.key.allIntents != null && this.key.allIntents.length > 1) {
                    int length = this.key.allIntents.length;
                    android.content.Intent[] intentArr2 = new android.content.Intent[length];
                    int length2 = this.key.allIntents.length;
                    java.lang.String[] strArr2 = new java.lang.String[length2];
                    java.lang.System.arraycopy(this.key.allIntents, 0, intentArr2, 0, this.key.allIntents.length);
                    if (this.key.allResolvedTypes != null) {
                        java.lang.System.arraycopy(this.key.allResolvedTypes, 0, strArr2, 0, this.key.allResolvedTypes.length);
                    }
                    intentArr2[length - 1] = intent3;
                    strArr2[length2 - 1] = str4;
                    intentArr = intentArr2;
                    strArr = strArr2;
                } else {
                    intentArr = null;
                    strArr = null;
                }
                if (this.key.type == 1) {
                    this.controller.mAmInternal.enforceBroadcastOptionsPermissions(bundle, callingUid);
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    if (tempAllowListDuration == null) {
                        i5 = 0;
                        intent2 = intent3;
                        i6 = 5;
                        if (this.key.type == 5 && bundle != null) {
                            android.app.BroadcastOptions broadcastOptions = new android.app.BroadcastOptions(bundle);
                            if (broadcastOptions.getTemporaryAppAllowlistDuration() > 0) {
                                this.controller.mAmInternal.tempAllowlistForPendingIntent(callingPid, callingUid, this.uid, broadcastOptions.getTemporaryAppAllowlistDuration(), broadcastOptions.getTemporaryAppAllowlistType(), broadcastOptions.getTemporaryAppAllowlistReasonCode(), broadcastOptions.getTemporaryAppAllowlistReason());
                            }
                            int i12 = iIntentReceiver == null ? 1 : i5;
                            if (iIntentReceiver != null && iApplicationThread == null) {
                                android.util.Slog.w(TAG, "Sending of " + intent + " from " + android.os.Binder.getCallingUid() + " requested resultTo without an IApplicationThread!", new java.lang.Throwable());
                            }
                            i7 = this.key.userId;
                            if (i7 == -2) {
                                i8 = i7;
                            } else {
                                i8 = this.controller.mUserController.getCurrentOrTargetUserId();
                            }
                            switch (this.key.type) {
                                case 1:
                                    try {
                                        if (this.controller.mAmInternal.broadcastIntentInPackage(this.key.packageName, this.key.featureId, this.uid, callingUid, callingPid, intent2, str4, iApplicationThread, iIntentReceiver, i, (java.lang.String) null, (android.os.Bundle) null, str2, bundle, iIntentReceiver != null ? 1 : i5, false, i8, getBackgroundStartPrivilegesForActivitySender(this.mAllowBgActivityStartsForBroadcastSender, iBinder, bundle, callingUid), (int[]) null) != 0) {
                                            i10 = i12;
                                        } else {
                                            i10 = i5;
                                        }
                                        i12 = i10;
                                        i9 = i5;
                                    } catch (java.lang.RuntimeException e) {
                                        android.util.Slog.w(TAG, "Unable to send startActivity intent", e);
                                        break;
                                    }
                                    if (i12 != 0 && i9 != -96) {
                                        try {
                                            iIntentReceiver.performReceive(new android.content.Intent(intent2), 0, (java.lang.String) null, (android.os.Bundle) null, false, false, this.key.userId);
                                        } catch (android.os.RemoteException e2) {
                                        }
                                    }
                                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                    return i9;
                                case 2:
                                    try {
                                        if (this.key.allIntents != null && this.key.allIntents.length > 1) {
                                            i9 = this.controller.mAtmInternal.startActivitiesInPackage(this.uid, callingPid, callingUid, this.key.packageName, this.key.featureId, intentArr, strArr, iBinder2, safeActivityOptions, i8, false, this, getBackgroundStartPrivilegesForActivitySender(iBinder));
                                        } else {
                                            i9 = this.controller.mAtmInternal.startActivityInPackage(this.uid, callingPid, callingUid, this.key.packageName, this.key.featureId, intent2, str4, iBinder2, str3, i2, 0, safeActivityOptions, i8, null, "PendingIntentRecord", false, this, getBackgroundStartPrivilegesForActivitySender(iBinder));
                                        }
                                    } catch (java.lang.RuntimeException e3) {
                                        android.util.Slog.w(TAG, "Unable to send startActivity intent", e3);
                                        break;
                                    }
                                    if (i12 != 0) {
                                        iIntentReceiver.performReceive(new android.content.Intent(intent2), 0, (java.lang.String) null, (android.os.Bundle) null, false, false, this.key.userId);
                                        break;
                                    }
                                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                    return i9;
                                case 3:
                                    this.controller.mAtmInternal.sendActivityResult(-1, this.key.activity, this.key.who, this.key.requestCode, i, intent2);
                                    i9 = i5;
                                    if (i12 != 0) {
                                    }
                                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                    return i9;
                                case 4:
                                case 5:
                                    try {
                                        this.controller.mAmInternal.startServiceInPackage(this.uid, intent2, str4, this.key.type == i6 ? 1 : i5, this.key.packageName, this.key.featureId, i8, getBackgroundStartPrivilegesForActivitySender(this.mAllowBgActivityStartsForServiceSender, iBinder, bundle, callingUid));
                                    } catch (android.os.TransactionTooLargeException e4) {
                                        i9 = -96;
                                    } catch (java.lang.RuntimeException e5) {
                                        android.util.Slog.w(TAG, "Unable to send startService intent", e5);
                                    }
                                    i9 = i5;
                                    if (i12 != 0) {
                                    }
                                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                    return i9;
                                default:
                                    i9 = i5;
                                    if (i12 != 0) {
                                    }
                                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                    return i9;
                            }
                        }
                    } else {
                        java.lang.StringBuilder sb = new java.lang.StringBuilder(64);
                        sb.append("setPendingIntentAllowlistDuration,reason:");
                        sb.append(tempAllowListDuration.reason == null ? "" : tempAllowListDuration.reason);
                        sb.append(",pendingintent:");
                        android.os.UserHandle.formatUid(sb, callingUid);
                        sb.append(":");
                        if (intent3.getAction() != null) {
                            sb.append(intent3.getAction());
                        } else if (intent3.getComponent() != null) {
                            intent3.getComponent().appendShortString(sb);
                        } else if (intent3.getData() != null) {
                            sb.append(intent3.getData().toSafeString());
                        }
                        i6 = 5;
                        i5 = 0;
                        intent2 = intent3;
                        this.controller.mAmInternal.tempAllowlistForPendingIntent(callingPid, callingUid, this.uid, tempAllowListDuration.duration, tempAllowListDuration.type, tempAllowListDuration.reasonCode, sb.toString());
                    }
                    if (iIntentReceiver == null) {
                    }
                    if (iIntentReceiver != null) {
                        android.util.Slog.w(TAG, "Sending of " + intent + " from " + android.os.Binder.getCallingUid() + " requested resultTo without an IApplicationThread!", new java.lang.Throwable());
                    }
                    i7 = this.key.userId;
                    if (i7 == -2) {
                    }
                    switch (this.key.type) {
                    }
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } finally {
            }
        }
    }

    private android.app.BackgroundStartPrivileges getBackgroundStartPrivilegesForActivitySender(android.os.IBinder iBinder) {
        if (this.mAllowBgActivityStartsForActivitySender.contains(iBinder)) {
            return android.app.BackgroundStartPrivileges.allowBackgroundActivityStarts(iBinder);
        }
        return android.app.BackgroundStartPrivileges.NONE;
    }

    private android.app.BackgroundStartPrivileges getBackgroundStartPrivilegesForActivitySender(android.util.ArraySet<android.os.IBinder> arraySet, android.os.IBinder iBinder, android.os.Bundle bundle, int i) {
        if (arraySet.contains(iBinder)) {
            return android.app.BackgroundStartPrivileges.allowBackgroundActivityStarts(iBinder);
        }
        if (this.uid != i && this.controller.mAtmInternal.isUidForeground(i)) {
            return getBackgroundStartPrivilegesAllowedByCaller(bundle, i, (java.lang.String) null);
        }
        return android.app.BackgroundStartPrivileges.NONE;
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (!this.canceled) {
                this.controller.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.am.PendingIntentRecord$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.am.PendingIntentRecord) obj).completeFinalize();
                    }
                }, this));
            }
        } finally {
            super/*java.lang.Object*/.finalize();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void completeFinalize() {
        synchronized (this.controller.mLock) {
            try {
                if (this.controller.mIntentSenderRecords.get(this.key) == this.ref) {
                    this.controller.mIntentSenderRecords.remove(this.key);
                    this.controller.decrementUidStatLocked(this);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("uid=");
        printWriter.print(this.uid);
        printWriter.print(" packageName=");
        printWriter.print(this.key.packageName);
        printWriter.print(" featureId=");
        printWriter.print(this.key.featureId);
        printWriter.print(" type=");
        printWriter.print(this.key.typeName());
        printWriter.print(" flags=0x");
        printWriter.println(java.lang.Integer.toHexString(this.key.flags));
        if (this.key.activity != null || this.key.who != null) {
            printWriter.print(str);
            printWriter.print("activity=");
            printWriter.print(this.key.activity);
            printWriter.print(" who=");
            printWriter.println(this.key.who);
        }
        if (this.key.requestCode != 0 || this.key.requestResolvedType != null) {
            printWriter.print(str);
            printWriter.print("requestCode=");
            printWriter.print(this.key.requestCode);
            printWriter.print(" requestResolvedType=");
            printWriter.println(this.key.requestResolvedType);
        }
        if (this.key.requestIntent != null) {
            printWriter.print(str);
            printWriter.print("requestIntent=");
            printWriter.println(this.key.requestIntent.toShortString(false, true, true, false));
        }
        if (this.sent || this.canceled) {
            printWriter.print(str);
            printWriter.print("sent=");
            printWriter.print(this.sent);
            printWriter.print(" canceled=");
            printWriter.println(this.canceled);
        }
        if (this.mAllowlistDuration != null) {
            printWriter.print(str);
            printWriter.print("allowlistDuration=");
            for (int i = 0; i < this.mAllowlistDuration.size(); i++) {
                if (i != 0) {
                    printWriter.print(", ");
                }
                com.android.server.am.PendingIntentRecord.TempAllowListDuration valueAt = this.mAllowlistDuration.valueAt(i);
                printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.mAllowlistDuration.keyAt(i))));
                printWriter.print(":");
                android.util.TimeUtils.formatDuration(valueAt.duration, printWriter);
                printWriter.print(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                printWriter.print(valueAt.type);
                printWriter.print(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                printWriter.print(android.os.PowerWhitelistManager.reasonCodeToString(valueAt.reasonCode));
                printWriter.print(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                printWriter.print(valueAt.reason);
            }
            printWriter.println();
        }
        if (this.mCancelCallbacks != null) {
            printWriter.print(str);
            printWriter.println("mCancelCallbacks:");
            for (int i2 = 0; i2 < this.mCancelCallbacks.getRegisteredCallbackCount(); i2++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(this.mCancelCallbacks.getRegisteredCallbackItem(i2));
            }
        }
    }

    public java.lang.String toString() {
        if (this.stringName != null) {
            return this.stringName;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("PendingIntentRecord{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(' ');
        sb.append(this.key.packageName);
        if (this.key.featureId != null) {
            sb.append('/');
            sb.append(this.key.featureId);
        }
        sb.append(' ');
        sb.append(this.key.typeName());
        if (this.mAllowlistDuration != null) {
            sb.append(" (allowlist: ");
            for (int i = 0; i < this.mAllowlistDuration.size(); i++) {
                if (i != 0) {
                    sb.append(",");
                }
                com.android.server.am.PendingIntentRecord.TempAllowListDuration valueAt = this.mAllowlistDuration.valueAt(i);
                sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.mAllowlistDuration.keyAt(i))));
                sb.append(":");
                android.util.TimeUtils.formatDuration(valueAt.duration, sb);
                sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                sb.append(valueAt.type);
                sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                sb.append(android.os.PowerWhitelistManager.reasonCodeToString(valueAt.reasonCode));
                sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                sb.append(valueAt.reason);
            }
            sb.append(")");
        }
        sb.append('}');
        java.lang.String sb2 = sb.toString();
        this.stringName = sb2;
        return sb2;
    }
}
