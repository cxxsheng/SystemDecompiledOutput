package com.android.server.am;

/* loaded from: classes.dex */
final class BroadcastRecord extends android.os.Binder {
    static final int APP_RECEIVE = 1;
    static final int CALL_DONE_RECEIVE = 3;
    static final int CALL_IN_RECEIVE = 2;
    static final int DELIVERY_DEFERRED = 6;
    static final int DELIVERY_DELIVERED = 1;
    static final int DELIVERY_FAILURE = 5;
    static final int DELIVERY_PENDING = 0;
    static final int DELIVERY_SCHEDULED = 4;
    static final int DELIVERY_SKIPPED = 2;
    static final int DELIVERY_TIMEOUT = 3;
    static final int IDLE = 0;
    static final int WAITING_SERVICES = 4;
    final boolean alarm;
    int anrCount;
    final int appOp;
    int beyondCount;
    final int[] blockedUntilBeyondCount;

    @android.annotation.Nullable
    final com.android.server.am.ProcessRecord callerApp;

    @android.annotation.Nullable
    final java.lang.String callerFeatureId;
    final boolean callerInstantApp;
    final boolean callerInstrumented;

    @android.annotation.Nullable
    final java.lang.String callerPackage;
    final int callerProcState;
    final int callingPid;
    final int callingUid;
    com.android.server.am.ProcessRecord curApp;
    int curAppLastProcessState;
    android.content.ComponentName curComponent;
    com.android.server.am.BroadcastFilter curFilter;
    android.os.Bundle curFilteredExtras;
    android.content.pm.ActivityInfo curReceiver;
    final boolean deferUntilActive;
    boolean deferred;
    int deferredCount;
    final int[] delivery;

    @android.annotation.NonNull
    final java.lang.String[] deliveryReasons;
    long dispatchClockTime;
    long dispatchRealTime;
    long dispatchTime;
    long enqueueClockTime;
    long enqueueRealTime;
    long enqueueTime;

    @android.annotation.Nullable
    final java.lang.String[] excludedPackages;

    @android.annotation.Nullable
    final java.lang.String[] excludedPermissions;

    @android.annotation.Nullable
    final java.util.function.BiFunction<java.lang.Integer, android.os.Bundle, android.os.Bundle> filterExtrasForReceiver;
    long finishTime;
    final boolean initialSticky;

    @android.annotation.NonNull
    final android.content.Intent intent;
    final boolean interactive;
    final android.app.BackgroundStartPrivileges mBackgroundStartPrivileges;

    @android.annotation.Nullable
    private java.lang.String mCachedToShortString;

    @android.annotation.Nullable
    private java.lang.String mCachedToString;
    boolean mIsReceiverAppRunning;

    @android.annotation.Nullable
    private android.util.ArrayMap<com.android.server.am.BroadcastRecord, java.lang.Boolean> mMatchingRecordsCache;
    boolean mWasReceiverAppStopped;
    int manifestCount;
    int manifestSkipCount;
    int nextReceiver;

    @android.annotation.Nullable
    final android.app.BroadcastOptions options;
    final boolean ordered;
    long originalEnqueueClockTime;
    final int originalStickyCallingUid;
    final boolean prioritized;
    final boolean pushMessage;
    final boolean pushMessageOverQuota;

    @android.annotation.Nullable
    com.android.server.am.BroadcastQueue queue;
    long receiverTime;

    @android.annotation.NonNull
    final java.util.List<java.lang.Object> receivers;

    @android.annotation.Nullable
    final java.lang.String[] requiredPermissions;

    @android.annotation.Nullable
    final java.lang.String resolvedType;
    boolean resultAbort;
    int resultCode;

    @android.annotation.Nullable
    java.lang.String resultData;

    @android.annotation.Nullable
    android.os.Bundle resultExtras;

    @android.annotation.Nullable
    android.content.IIntentReceiver resultTo;

    @android.annotation.Nullable
    com.android.server.am.ProcessRecord resultToApp;
    final long[] scheduledTime;
    final boolean shareIdentity;
    int splitCount;
    int splitToken;
    int state;
    final boolean sticky;

    @android.annotation.Nullable
    final android.content.ComponentName targetComp;
    int terminalCount;
    final long[] terminalTime;
    final boolean timeoutExempt;
    final boolean urgent;
    final int userId;
    static boolean CORE_DEFER_UNTIL_ACTIVE = false;
    static final java.util.List<java.lang.Object> EMPTY_RECEIVERS = java.util.List.of();
    static java.util.concurrent.atomic.AtomicInteger sNextToken = new java.util.concurrent.atomic.AtomicInteger(1);

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeliveryState {
    }

    @android.annotation.NonNull
    static java.lang.String deliveryStateToString(int i) {
        switch (i) {
            case 0:
                return "PENDING";
            case 1:
                return "DELIVERED";
            case 2:
                return "SKIPPED";
            case 3:
                return "TIMEOUT";
            case 4:
                return "SCHEDULED";
            case 5:
                return "FAILURE";
            case 6:
                return "DEFERRED";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    static boolean isDeliveryStateTerminal(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
                return true;
            case 4:
            default:
                return false;
        }
    }

    static boolean isDeliveryStateBeyond(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
                return true;
            case 4:
            default:
                return false;
        }
    }

    boolean isAssumedDelivered(int i) {
        return (this.receivers.get(i) instanceof com.android.server.am.BroadcastFilter) && !this.ordered && this.resultTo == null;
    }

    @dalvik.annotation.optimization.NeverCompile
    void dump(java.io.PrintWriter printWriter, java.lang.String str, java.text.SimpleDateFormat simpleDateFormat) {
        java.lang.String str2;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        printWriter.print(str);
        printWriter.print(this);
        printWriter.print(" to user ");
        printWriter.println(this.userId);
        printWriter.print(str);
        printWriter.println(this.intent.toInsecureString());
        if (this.targetComp != null && this.targetComp != this.intent.getComponent()) {
            printWriter.print(str);
            printWriter.print("  targetComp: ");
            printWriter.println(this.targetComp.toShortString());
        }
        android.os.Bundle extras = this.intent.getExtras();
        if (extras != null) {
            printWriter.print(str);
            printWriter.print("  extras: ");
            printWriter.println(extras.toString());
        }
        printWriter.print(str);
        printWriter.print("caller=");
        printWriter.print(this.callerPackage);
        printWriter.print(" ");
        printWriter.print(this.callerApp != null ? this.callerApp.toShortString() : "null");
        printWriter.print(" pid=");
        printWriter.print(this.callingPid);
        printWriter.print(" uid=");
        printWriter.println(this.callingUid);
        if ((this.requiredPermissions != null && this.requiredPermissions.length > 0) || this.appOp != -1) {
            printWriter.print(str);
            printWriter.print("requiredPermissions=");
            printWriter.print(java.util.Arrays.toString(this.requiredPermissions));
            printWriter.print("  appOp=");
            printWriter.println(this.appOp);
        }
        if (this.excludedPermissions != null && this.excludedPermissions.length > 0) {
            printWriter.print(str);
            printWriter.print("excludedPermissions=");
            printWriter.print(java.util.Arrays.toString(this.excludedPermissions));
        }
        if (this.excludedPackages != null && this.excludedPackages.length > 0) {
            printWriter.print(str);
            printWriter.print("excludedPackages=");
            printWriter.print(java.util.Arrays.toString(this.excludedPackages));
        }
        if (this.options != null) {
            printWriter.print(str);
            printWriter.print("options=");
            printWriter.println(this.options.toBundle());
        }
        printWriter.print(str);
        printWriter.print("enqueueClockTime=");
        printWriter.print(simpleDateFormat.format(new java.util.Date(this.enqueueClockTime)));
        printWriter.print(" dispatchClockTime=");
        printWriter.print(simpleDateFormat.format(new java.util.Date(this.dispatchClockTime)));
        if (this.originalEnqueueClockTime > 0) {
            printWriter.print(" originalEnqueueClockTime=");
            printWriter.print(simpleDateFormat.format(new java.util.Date(this.originalEnqueueClockTime)));
        }
        printWriter.println();
        printWriter.print(str);
        printWriter.print("dispatchTime=");
        android.util.TimeUtils.formatDuration(this.dispatchTime, uptimeMillis, printWriter);
        printWriter.print(" (");
        android.util.TimeUtils.formatDuration(this.dispatchTime - this.enqueueTime, printWriter);
        printWriter.print(" since enq)");
        if (this.finishTime != 0) {
            printWriter.print(" finishTime=");
            android.util.TimeUtils.formatDuration(this.finishTime, uptimeMillis, printWriter);
            printWriter.print(" (");
            android.util.TimeUtils.formatDuration(this.finishTime - this.dispatchTime, printWriter);
            printWriter.print(" since disp)");
        } else {
            printWriter.print(" receiverTime=");
            android.util.TimeUtils.formatDuration(this.receiverTime, uptimeMillis, printWriter);
        }
        printWriter.println("");
        if (this.anrCount != 0) {
            printWriter.print(str);
            printWriter.print("anrCount=");
            printWriter.println(this.anrCount);
        }
        if (this.resultTo != null || this.resultCode != -1 || this.resultData != null) {
            printWriter.print(str);
            printWriter.print("resultTo=");
            printWriter.print(this.resultTo);
            printWriter.print(" resultCode=");
            printWriter.print(this.resultCode);
            printWriter.print(" resultData=");
            printWriter.println(this.resultData);
        }
        if (this.resultExtras != null) {
            printWriter.print(str);
            printWriter.print("resultExtras=");
            printWriter.println(this.resultExtras);
        }
        if (this.resultAbort || this.ordered || this.sticky || this.initialSticky) {
            printWriter.print(str);
            printWriter.print("resultAbort=");
            printWriter.print(this.resultAbort);
            printWriter.print(" ordered=");
            printWriter.print(this.ordered);
            printWriter.print(" sticky=");
            printWriter.print(this.sticky);
            printWriter.print(" initialSticky=");
            printWriter.print(this.initialSticky);
            printWriter.print(" originalStickyCallingUid=");
            printWriter.println(this.originalStickyCallingUid);
        }
        if (this.nextReceiver != 0) {
            printWriter.print(str);
            printWriter.print("nextReceiver=");
            printWriter.println(this.nextReceiver);
        }
        if (this.curFilter != null) {
            printWriter.print(str);
            printWriter.print("curFilter=");
            printWriter.println(this.curFilter);
        }
        if (this.curReceiver != null) {
            printWriter.print(str);
            printWriter.print("curReceiver=");
            printWriter.println(this.curReceiver);
        }
        if (this.curApp != null) {
            printWriter.print(str);
            printWriter.print("curApp=");
            printWriter.println(this.curApp);
            printWriter.print(str);
            printWriter.print("curComponent=");
            printWriter.println(this.curComponent != null ? this.curComponent.toShortString() : "--");
            if (this.curReceiver != null && this.curReceiver.applicationInfo != null) {
                printWriter.print(str);
                printWriter.print("curSourceDir=");
                printWriter.println(this.curReceiver.applicationInfo.sourceDir);
            }
        }
        if (this.curFilteredExtras != null) {
            printWriter.print(" filtered extras: ");
            printWriter.println(this.curFilteredExtras);
        }
        if (this.state != 0) {
            switch (this.state) {
                case 1:
                    str2 = " (APP_RECEIVE)";
                    break;
                case 2:
                    str2 = " (CALL_IN_RECEIVE)";
                    break;
                case 3:
                    str2 = " (CALL_DONE_RECEIVE)";
                    break;
                case 4:
                    str2 = " (WAITING_SERVICES)";
                    break;
                default:
                    str2 = " (?)";
                    break;
            }
            printWriter.print(str);
            printWriter.print("state=");
            printWriter.print(this.state);
            printWriter.println(str2);
        }
        printWriter.print(str);
        printWriter.print("terminalCount=");
        printWriter.println(this.terminalCount);
        int size = this.receivers != null ? this.receivers.size() : 0;
        java.lang.String str3 = str + "  ";
        android.util.PrintWriterPrinter printWriterPrinter = new android.util.PrintWriterPrinter(printWriter);
        for (int i = 0; i < size; i++) {
            java.lang.Object obj = this.receivers.get(i);
            printWriter.print(str);
            printWriter.print(deliveryStateToString(this.delivery[i]));
            printWriter.print(' ');
            if (this.scheduledTime[i] != 0) {
                printWriter.print("scheduled ");
                android.util.TimeUtils.formatDuration(this.scheduledTime[i] - this.enqueueTime, printWriter);
                printWriter.print(' ');
            }
            if (this.terminalTime[i] != 0) {
                printWriter.print("terminal ");
                android.util.TimeUtils.formatDuration(this.terminalTime[i] - this.scheduledTime[i], printWriter);
                printWriter.print(' ');
            }
            printWriter.print("(");
            printWriter.print(this.blockedUntilBeyondCount[i]);
            printWriter.print(") ");
            printWriter.print("#");
            printWriter.print(i);
            printWriter.print(": ");
            if (obj instanceof com.android.server.am.BroadcastFilter) {
                printWriter.println(obj);
                ((com.android.server.am.BroadcastFilter) obj).dumpBrief(printWriter, str3);
            } else if (obj instanceof android.content.pm.ResolveInfo) {
                printWriter.println("(manifest)");
                ((android.content.pm.ResolveInfo) obj).dump(printWriterPrinter, str3, 0);
            } else {
                printWriter.println(obj);
            }
            if (this.deliveryReasons[i] != null) {
                printWriter.print(str3);
                printWriter.print("reason: ");
                printWriter.println(this.deliveryReasons[i]);
            }
        }
    }

    BroadcastRecord(com.android.server.am.BroadcastQueue broadcastQueue, android.content.Intent intent, com.android.server.am.ProcessRecord processRecord, java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i, int i2, boolean z, java.lang.String str3, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String[] strArr3, int i3, android.app.BroadcastOptions broadcastOptions, java.util.List list, com.android.server.am.ProcessRecord processRecord2, android.content.IIntentReceiver iIntentReceiver, int i4, java.lang.String str4, android.os.Bundle bundle, boolean z2, boolean z3, boolean z4, int i5, @android.annotation.NonNull android.app.BackgroundStartPrivileges backgroundStartPrivileges, boolean z5, @android.annotation.Nullable java.util.function.BiFunction<java.lang.Integer, android.os.Bundle, android.os.Bundle> biFunction, int i6) {
        this(broadcastQueue, intent, processRecord, str, str2, i, i2, z, str3, strArr, strArr2, strArr3, i3, broadcastOptions, list, processRecord2, iIntentReceiver, i4, str4, bundle, z2, z3, z4, i5, -1, backgroundStartPrivileges, z5, biFunction, i6);
    }

    BroadcastRecord(com.android.server.am.BroadcastQueue broadcastQueue, android.content.Intent intent, com.android.server.am.ProcessRecord processRecord, java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i, int i2, boolean z, java.lang.String str3, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String[] strArr3, int i3, android.app.BroadcastOptions broadcastOptions, java.util.List list, com.android.server.am.ProcessRecord processRecord2, android.content.IIntentReceiver iIntentReceiver, int i4, java.lang.String str4, android.os.Bundle bundle, boolean z2, boolean z3, boolean z4, int i5, int i6, @android.annotation.NonNull android.app.BackgroundStartPrivileges backgroundStartPrivileges, boolean z5, @android.annotation.Nullable java.util.function.BiFunction<java.lang.Integer, android.os.Bundle, android.os.Bundle> biFunction, int i7) {
        if (intent != null) {
            this.queue = broadcastQueue;
            java.util.Objects.requireNonNull(intent);
            this.intent = intent;
            this.targetComp = intent.getComponent();
            this.callerApp = processRecord;
            this.callerPackage = str;
            this.callerFeatureId = str2;
            this.callingPid = i;
            this.callingUid = i2;
            this.callerProcState = i7;
            this.callerInstantApp = z;
            this.callerInstrumented = isCallerInstrumented(processRecord, i2);
            this.resolvedType = str3;
            this.requiredPermissions = strArr;
            this.excludedPermissions = strArr2;
            this.excludedPackages = strArr3;
            this.appOp = i3;
            this.options = broadcastOptions;
            this.receivers = list != null ? list : EMPTY_RECEIVERS;
            boolean z6 = false;
            this.delivery = new int[list != null ? list.size() : 0];
            this.deliveryReasons = new java.lang.String[this.delivery.length];
            this.urgent = calculateUrgent(intent, broadcastOptions);
            this.deferUntilActive = calculateDeferUntilActive(i2, broadcastOptions, iIntentReceiver, z2, this.urgent);
            this.blockedUntilBeyondCount = calculateBlockedUntilBeyondCount(this.receivers, z2);
            this.scheduledTime = new long[this.delivery.length];
            this.terminalTime = new long[this.delivery.length];
            this.resultToApp = processRecord2;
            this.resultTo = iIntentReceiver;
            this.resultCode = i4;
            this.resultData = str4;
            this.resultExtras = bundle;
            this.ordered = z2;
            this.sticky = z3;
            this.initialSticky = z4;
            this.prioritized = isPrioritized(this.blockedUntilBeyondCount, z2);
            this.userId = i5;
            this.nextReceiver = 0;
            this.state = 0;
            this.mBackgroundStartPrivileges = backgroundStartPrivileges;
            this.timeoutExempt = z5;
            this.alarm = this.options != null && this.options.isAlarmBroadcast();
            this.pushMessage = this.options != null && this.options.isPushMessagingBroadcast();
            this.pushMessageOverQuota = this.options != null && this.options.isPushMessagingOverQuotaBroadcast();
            this.interactive = this.options != null && this.options.isInteractive();
            if (this.options != null && this.options.isShareIdentityEnabled()) {
                z6 = true;
            }
            this.shareIdentity = z6;
            this.filterExtrasForReceiver = biFunction;
            this.originalStickyCallingUid = i6;
            return;
        }
        throw new java.lang.NullPointerException("Can't construct with a null intent");
    }

    private BroadcastRecord(com.android.server.am.BroadcastRecord broadcastRecord, android.content.Intent intent) {
        java.util.Objects.requireNonNull(intent);
        this.intent = intent;
        this.targetComp = intent.getComponent();
        this.callerApp = broadcastRecord.callerApp;
        this.callerPackage = broadcastRecord.callerPackage;
        this.callerFeatureId = broadcastRecord.callerFeatureId;
        this.callingPid = broadcastRecord.callingPid;
        this.callingUid = broadcastRecord.callingUid;
        this.callerProcState = broadcastRecord.callerProcState;
        this.callerInstantApp = broadcastRecord.callerInstantApp;
        this.callerInstrumented = broadcastRecord.callerInstrumented;
        this.ordered = broadcastRecord.ordered;
        this.sticky = broadcastRecord.sticky;
        this.initialSticky = broadcastRecord.initialSticky;
        this.prioritized = broadcastRecord.prioritized;
        this.userId = broadcastRecord.userId;
        this.resolvedType = broadcastRecord.resolvedType;
        this.requiredPermissions = broadcastRecord.requiredPermissions;
        this.excludedPermissions = broadcastRecord.excludedPermissions;
        this.excludedPackages = broadcastRecord.excludedPackages;
        this.appOp = broadcastRecord.appOp;
        this.options = broadcastRecord.options;
        this.receivers = broadcastRecord.receivers;
        this.delivery = broadcastRecord.delivery;
        this.deliveryReasons = broadcastRecord.deliveryReasons;
        this.deferUntilActive = broadcastRecord.deferUntilActive;
        this.blockedUntilBeyondCount = broadcastRecord.blockedUntilBeyondCount;
        this.scheduledTime = broadcastRecord.scheduledTime;
        this.terminalTime = broadcastRecord.terminalTime;
        this.resultToApp = broadcastRecord.resultToApp;
        this.resultTo = broadcastRecord.resultTo;
        this.enqueueTime = broadcastRecord.enqueueTime;
        this.enqueueRealTime = broadcastRecord.enqueueRealTime;
        this.enqueueClockTime = broadcastRecord.enqueueClockTime;
        this.dispatchTime = broadcastRecord.dispatchTime;
        this.dispatchRealTime = broadcastRecord.dispatchRealTime;
        this.dispatchClockTime = broadcastRecord.dispatchClockTime;
        this.receiverTime = broadcastRecord.receiverTime;
        this.finishTime = broadcastRecord.finishTime;
        this.resultCode = broadcastRecord.resultCode;
        this.resultData = broadcastRecord.resultData;
        this.resultExtras = broadcastRecord.resultExtras;
        this.resultAbort = broadcastRecord.resultAbort;
        this.nextReceiver = broadcastRecord.nextReceiver;
        this.state = broadcastRecord.state;
        this.anrCount = broadcastRecord.anrCount;
        this.manifestCount = broadcastRecord.manifestCount;
        this.manifestSkipCount = broadcastRecord.manifestSkipCount;
        this.queue = broadcastRecord.queue;
        this.mBackgroundStartPrivileges = broadcastRecord.mBackgroundStartPrivileges;
        this.timeoutExempt = broadcastRecord.timeoutExempt;
        this.alarm = broadcastRecord.alarm;
        this.pushMessage = broadcastRecord.pushMessage;
        this.pushMessageOverQuota = broadcastRecord.pushMessageOverQuota;
        this.interactive = broadcastRecord.interactive;
        this.shareIdentity = broadcastRecord.shareIdentity;
        this.urgent = broadcastRecord.urgent;
        this.filterExtrasForReceiver = broadcastRecord.filterExtrasForReceiver;
        this.originalStickyCallingUid = broadcastRecord.originalStickyCallingUid;
    }

    com.android.server.am.BroadcastRecord splitRecipientsLocked(int i, int i2) {
        int i3 = i2;
        java.util.ArrayList arrayList = null;
        while (i3 < this.receivers.size()) {
            java.lang.Object obj = this.receivers.get(i3);
            if (getReceiverUid(obj) == i) {
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList();
                }
                java.util.ArrayList arrayList2 = arrayList;
                arrayList2.add(obj);
                this.receivers.remove(i3);
                arrayList = arrayList2;
            } else {
                i3++;
            }
        }
        if (arrayList == null) {
            return null;
        }
        com.android.server.am.BroadcastRecord broadcastRecord = new com.android.server.am.BroadcastRecord(this.queue, this.intent, this.callerApp, this.callerPackage, this.callerFeatureId, this.callingPid, this.callingUid, this.callerInstantApp, this.resolvedType, this.requiredPermissions, this.excludedPermissions, this.excludedPackages, this.appOp, this.options, arrayList, this.resultToApp, this.resultTo, this.resultCode, this.resultData, this.resultExtras, this.ordered, this.sticky, this.initialSticky, this.userId, this.mBackgroundStartPrivileges, this.timeoutExempt, this.filterExtrasForReceiver, this.callerProcState);
        broadcastRecord.enqueueTime = this.enqueueTime;
        broadcastRecord.enqueueRealTime = this.enqueueRealTime;
        broadcastRecord.enqueueClockTime = this.enqueueClockTime;
        broadcastRecord.splitToken = this.splitToken;
        return broadcastRecord;
    }

    @android.annotation.NonNull
    android.util.SparseArray<com.android.server.am.BroadcastRecord> splitDeferredBootCompletedBroadcastLocked(android.app.ActivityManagerInternal activityManagerInternal, int i) {
        int i2;
        android.util.SparseArray<com.android.server.am.BroadcastRecord> sparseArray = new android.util.SparseArray<>();
        if (i == 0) {
            return sparseArray;
        }
        if (this.receivers == null) {
            return sparseArray;
        }
        java.lang.String action = this.intent.getAction();
        if (!"android.intent.action.LOCKED_BOOT_COMPLETED".equals(action) && !"android.intent.action.BOOT_COMPLETED".equals(action)) {
            return sparseArray;
        }
        android.util.SparseArray sparseArray2 = new android.util.SparseArray();
        int size = this.receivers.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            java.lang.Object obj = this.receivers.get(size);
            int receiverUid = getReceiverUid(obj);
            if (i != 1) {
                if ((i & 2) != 0 && activityManagerInternal.getRestrictionLevel(receiverUid) < 50) {
                    size--;
                }
                if ((i & 4) != 0 && !android.app.compat.CompatChanges.isChangeEnabled(203704822L, receiverUid)) {
                    size--;
                }
            }
            this.receivers.remove(size);
            java.util.List list = (java.util.List) sparseArray2.get(receiverUid);
            if (list != null) {
                list.add(0, obj);
            } else {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                arrayList.add(0, obj);
                sparseArray2.put(receiverUid, arrayList);
            }
            size--;
        }
        int size2 = sparseArray2.size();
        for (i2 = 0; i2 < size2; i2++) {
            com.android.server.am.BroadcastRecord broadcastRecord = new com.android.server.am.BroadcastRecord(this.queue, this.intent, this.callerApp, this.callerPackage, this.callerFeatureId, this.callingPid, this.callingUid, this.callerInstantApp, this.resolvedType, this.requiredPermissions, this.excludedPermissions, this.excludedPackages, this.appOp, this.options, (java.util.List) sparseArray2.valueAt(i2), null, null, this.resultCode, this.resultData, this.resultExtras, this.ordered, this.sticky, this.initialSticky, this.userId, this.mBackgroundStartPrivileges, this.timeoutExempt, this.filterExtrasForReceiver, this.callerProcState);
            broadcastRecord.enqueueTime = this.enqueueTime;
            broadcastRecord.enqueueRealTime = this.enqueueRealTime;
            broadcastRecord.enqueueClockTime = this.enqueueClockTime;
            sparseArray.put(sparseArray2.keyAt(i2), broadcastRecord);
        }
        return sparseArray;
    }

    boolean setDeliveryState(int i, int i2, @android.annotation.NonNull java.lang.String str) {
        int i3 = this.delivery[i];
        if (isDeliveryStateTerminal(i3) || i2 == i3) {
            return false;
        }
        switch (i3) {
            case 6:
                this.deferredCount--;
                break;
        }
        switch (i2) {
            case 0:
                this.scheduledTime[i] = 0;
                break;
            case 1:
            case 2:
            case 3:
            case 5:
                this.terminalTime[i] = android.os.SystemClock.uptimeMillis();
                this.terminalCount++;
                break;
            case 4:
                this.scheduledTime[i] = android.os.SystemClock.uptimeMillis();
                break;
            case 6:
                this.deferredCount++;
                break;
        }
        this.delivery[i] = i2;
        this.deliveryReasons[i] = str;
        int i4 = this.beyondCount;
        if (i >= this.beyondCount) {
            int i5 = this.beyondCount;
            while (i5 < this.delivery.length && isDeliveryStateBeyond(getDeliveryState(i5))) {
                i5++;
                this.beyondCount = i5;
            }
        }
        return this.beyondCount != i4;
    }

    int getDeliveryState(int i) {
        return this.delivery[i];
    }

    boolean isBlocked(int i) {
        return this.beyondCount < this.blockedUntilBeyondCount[i];
    }

    boolean wasDeliveryAttempted(int i) {
        switch (getDeliveryState(i)) {
            case 1:
            case 3:
            case 5:
                return true;
            case 2:
            case 4:
            default:
                return false;
        }
    }

    void copyEnqueueTimeFrom(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord) {
        this.originalEnqueueClockTime = this.enqueueClockTime;
        this.enqueueTime = broadcastRecord.enqueueTime;
        this.enqueueRealTime = broadcastRecord.enqueueRealTime;
        this.enqueueClockTime = broadcastRecord.enqueueClockTime;
    }

    boolean isForeground() {
        return (this.intent.getFlags() & 268435456) != 0;
    }

    boolean isReplacePending() {
        return (this.intent.getFlags() & 536870912) != 0;
    }

    boolean isNoAbort() {
        return (this.intent.getFlags() & 134217728) != 0;
    }

    boolean isOffload() {
        return (this.intent.getFlags() & Integer.MIN_VALUE) != 0;
    }

    boolean isDeferUntilActive() {
        return this.deferUntilActive;
    }

    boolean isUrgent() {
        return this.urgent;
    }

    @android.annotation.NonNull
    java.lang.String getHostingRecordTriggerType() {
        if (this.alarm) {
            return com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM;
        }
        if (this.pushMessage) {
            return com.android.server.am.HostingRecord.TRIGGER_TYPE_PUSH_MESSAGE;
        }
        if (this.pushMessageOverQuota) {
            return com.android.server.am.HostingRecord.TRIGGER_TYPE_PUSH_MESSAGE_OVER_QUOTA;
        }
        return "unknown";
    }

    @android.annotation.Nullable
    android.content.Intent getReceiverIntent(@android.annotation.NonNull java.lang.Object obj) {
        android.os.Bundle extras;
        android.content.Intent intent = null;
        if (this.filterExtrasForReceiver != null && (extras = this.intent.getExtras()) != null) {
            android.os.Bundle apply = this.filterExtrasForReceiver.apply(java.lang.Integer.valueOf(getReceiverUid(obj)), extras);
            if (apply == null) {
                return null;
            }
            intent = new android.content.Intent(this.intent);
            intent.replaceExtras(apply);
        }
        if (obj instanceof android.content.pm.ResolveInfo) {
            if (intent == null) {
                intent = new android.content.Intent(this.intent);
            }
            intent.setComponent(((android.content.pm.ResolveInfo) obj).activityInfo.getComponentName());
        }
        return intent != null ? intent : this.intent;
    }

    static boolean isCallerInstrumented(@android.annotation.Nullable com.android.server.am.ProcessRecord processRecord, int i) {
        switch (android.os.UserHandle.getAppId(i)) {
            case 0:
            case 2000:
                return true;
            default:
                return (processRecord == null || processRecord.getActiveInstrumentation() == null) ? false : true;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static boolean isPrioritized(@android.annotation.NonNull int[] iArr, boolean z) {
        return (z || iArr.length <= 0 || iArr[0] == -1) ? false : true;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    static int[] calculateBlockedUntilBeyondCount(@android.annotation.NonNull java.util.List<java.lang.Object> list, boolean z) {
        int size = list.size();
        int[] iArr = new int[size];
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            if (z) {
                iArr[i3] = i3;
            } else {
                int receiverPriority = getReceiverPriority(list.get(i3));
                if (i3 == 0 || receiverPriority != i) {
                    iArr[i3] = i3;
                    i2 = i3;
                    i = receiverPriority;
                } else {
                    iArr[i3] = i2;
                }
            }
        }
        if (size > 0 && iArr[size - 1] == 0) {
            java.util.Arrays.fill(iArr, -1);
        }
        return iArr;
    }

    static int getReceiverUid(@android.annotation.NonNull java.lang.Object obj) {
        if (obj instanceof com.android.server.am.BroadcastFilter) {
            return ((com.android.server.am.BroadcastFilter) obj).owningUid;
        }
        return ((android.content.pm.ResolveInfo) obj).activityInfo.applicationInfo.uid;
    }

    @android.annotation.NonNull
    static java.lang.String getReceiverProcessName(@android.annotation.NonNull java.lang.Object obj) {
        if (obj instanceof com.android.server.am.BroadcastFilter) {
            return ((com.android.server.am.BroadcastFilter) obj).receiverList.app.processName;
        }
        return ((android.content.pm.ResolveInfo) obj).activityInfo.processName;
    }

    @android.annotation.NonNull
    static java.lang.String getReceiverPackageName(@android.annotation.NonNull java.lang.Object obj) {
        if (obj instanceof com.android.server.am.BroadcastFilter) {
            return ((com.android.server.am.BroadcastFilter) obj).receiverList.app.info.packageName;
        }
        return ((android.content.pm.ResolveInfo) obj).activityInfo.packageName;
    }

    @android.annotation.Nullable
    static java.lang.String getReceiverClassName(@android.annotation.NonNull java.lang.Object obj) {
        if (obj instanceof com.android.server.am.BroadcastFilter) {
            return ((com.android.server.am.BroadcastFilter) obj).getReceiverClassName();
        }
        return ((android.content.pm.ResolveInfo) obj).activityInfo.name;
    }

    static int getReceiverPriority(@android.annotation.NonNull java.lang.Object obj) {
        if (obj instanceof com.android.server.am.BroadcastFilter) {
            return ((com.android.server.am.BroadcastFilter) obj).getPriority();
        }
        return ((android.content.pm.ResolveInfo) obj).priority;
    }

    static boolean isReceiverEquals(@android.annotation.NonNull java.lang.Object obj, @android.annotation.NonNull java.lang.Object obj2) {
        if (obj == obj2) {
            return true;
        }
        if (!(obj instanceof android.content.pm.ResolveInfo) || !(obj2 instanceof android.content.pm.ResolveInfo)) {
            return false;
        }
        android.content.pm.ResolveInfo resolveInfo = (android.content.pm.ResolveInfo) obj;
        android.content.pm.ResolveInfo resolveInfo2 = (android.content.pm.ResolveInfo) obj2;
        return java.util.Objects.equals(resolveInfo.activityInfo.packageName, resolveInfo2.activityInfo.packageName) && java.util.Objects.equals(resolveInfo.activityInfo.name, resolveInfo2.activityInfo.name);
    }

    @com.android.internal.annotations.VisibleForTesting
    static boolean calculateUrgent(@android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable android.app.BroadcastOptions broadcastOptions) {
        if ((intent.getFlags() & 268435456) != 0) {
            return true;
        }
        if (broadcastOptions != null) {
            return broadcastOptions.isInteractive() || broadcastOptions.isAlarmBroadcast();
        }
        return false;
    }

    @com.android.internal.annotations.VisibleForTesting
    static boolean calculateDeferUntilActive(int i, @android.annotation.Nullable android.app.BroadcastOptions broadcastOptions, @android.annotation.Nullable android.content.IIntentReceiver iIntentReceiver, boolean z, boolean z2) {
        if (z) {
            return false;
        }
        if (!z && iIntentReceiver != null) {
            return true;
        }
        if (broadcastOptions != null) {
            switch (broadcastOptions.getDeferralPolicy()) {
                case 1:
                    return false;
                case 2:
                    return true;
            }
        }
        if (z2 || !CORE_DEFER_UNTIL_ACTIVE || !android.os.UserHandle.isCore(i)) {
            return false;
        }
        return true;
    }

    int calculateTypeForLogging() {
        int i;
        if (isForeground()) {
            i = 2;
        } else {
            i = 1;
        }
        if (this.alarm) {
            i |= 4;
        }
        if (this.interactive) {
            i |= 8;
        }
        if (this.ordered) {
            i |= 16;
        }
        if (this.prioritized) {
            i |= 32;
        }
        if (this.resultTo != null) {
            i |= 64;
        }
        if (this.deferUntilActive) {
            i |= 128;
        }
        if (this.pushMessage) {
            i |= 256;
        }
        if (this.pushMessageOverQuota) {
            i |= 512;
        }
        if (this.sticky) {
            i |= 1024;
        }
        if (this.initialSticky) {
            return i | 2048;
        }
        return i;
    }

    public com.android.server.am.BroadcastRecord maybeStripForHistory() {
        if (!this.intent.canStripForHistory()) {
            return this;
        }
        return new com.android.server.am.BroadcastRecord(this, this.intent.maybeStripForHistory());
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean cleanupDisabledPackageReceiversLocked(java.lang.String str, java.util.Set<java.lang.String> set, int i, boolean z) {
        if (this.receivers == null) {
            return false;
        }
        boolean z2 = i == -1;
        boolean z3 = this.userId == -1;
        if (this.userId != i && !z2 && !z3) {
            return false;
        }
        boolean z4 = false;
        for (int size = this.receivers.size() - 1; size >= 0; size--) {
            java.lang.Object obj = this.receivers.get(size);
            if (obj instanceof android.content.pm.ResolveInfo) {
                android.content.pm.ActivityInfo activityInfo = ((android.content.pm.ResolveInfo) obj).activityInfo;
                if ((str == null || (activityInfo.applicationInfo.packageName.equals(str) && (set == null || set.contains(activityInfo.name)))) && (z2 || android.os.UserHandle.getUserId(activityInfo.applicationInfo.uid) == i)) {
                    if (!z) {
                        return true;
                    }
                    this.receivers.remove(size);
                    if (size < this.nextReceiver) {
                        this.nextReceiver--;
                    }
                    z4 = true;
                }
            }
        }
        this.nextReceiver = java.lang.Math.min(this.nextReceiver, this.receivers.size());
        return z4;
    }

    void applySingletonPolicy(@android.annotation.NonNull com.android.server.am.ActivityManagerService activityManagerService) {
        boolean z;
        if (this.receivers == null) {
            return;
        }
        for (int i = 0; i < this.receivers.size(); i++) {
            java.lang.Object obj = this.receivers.get(i);
            if (obj instanceof android.content.pm.ResolveInfo) {
                android.content.pm.ResolveInfo resolveInfo = (android.content.pm.ResolveInfo) obj;
                try {
                    z = activityManagerService.isSingleton(resolveInfo.activityInfo.processName, resolveInfo.activityInfo.applicationInfo, resolveInfo.activityInfo.name, resolveInfo.activityInfo.flags);
                } catch (java.lang.SecurityException e) {
                    com.android.server.am.BroadcastQueue.logw(e.getMessage());
                    z = false;
                }
                int i2 = resolveInfo.activityInfo.applicationInfo.uid;
                if (this.callingUid != 1000 && z && activityManagerService.isValidSingletonCall(this.callingUid, i2)) {
                    resolveInfo.activityInfo = activityManagerService.getActivityInfoForUser(resolveInfo.activityInfo, 0);
                }
            }
        }
    }

    boolean containsReceiver(@android.annotation.NonNull java.lang.Object obj) {
        for (int size = this.receivers.size() - 1; size >= 0; size--) {
            if (isReceiverEquals(obj, this.receivers.get(size))) {
                return true;
            }
        }
        return false;
    }

    boolean containsAllReceivers(@android.annotation.NonNull java.util.List<java.lang.Object> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            if (!containsReceiver(list.get(size))) {
                return false;
            }
        }
        return true;
    }

    int getDeliveryGroupPolicy() {
        if (this.options != null) {
            return this.options.getDeliveryGroupPolicy();
        }
        return 0;
    }

    boolean matchesDeliveryGroup(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord) {
        return matchesDeliveryGroup(this, broadcastRecord);
    }

    private static boolean matchesDeliveryGroup(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, @android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord2) {
        android.content.IntentFilter deliveryGroupMatchingFilter = getDeliveryGroupMatchingFilter(broadcastRecord);
        if (isMatchingKeyNull(broadcastRecord) && isMatchingKeyNull(broadcastRecord2) && deliveryGroupMatchingFilter == null) {
            return broadcastRecord.intent.filterEquals(broadcastRecord2.intent);
        }
        if (deliveryGroupMatchingFilter != null && !deliveryGroupMatchingFilter.asPredicate().test(broadcastRecord2.intent)) {
            return false;
        }
        return areMatchingKeysEqual(broadcastRecord, broadcastRecord2);
    }

    private static boolean isMatchingKeyNull(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord) {
        return getDeliveryGroupMatchingNamespaceFragment(broadcastRecord) == null || getDeliveryGroupMatchingKeyFragment(broadcastRecord) == null;
    }

    private static boolean areMatchingKeysEqual(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, @android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord2) {
        if (!java.util.Objects.equals(getDeliveryGroupMatchingNamespaceFragment(broadcastRecord), getDeliveryGroupMatchingNamespaceFragment(broadcastRecord2))) {
            return false;
        }
        return java.util.Objects.equals(getDeliveryGroupMatchingKeyFragment(broadcastRecord), getDeliveryGroupMatchingKeyFragment(broadcastRecord2));
    }

    @android.annotation.Nullable
    private static java.lang.String getDeliveryGroupMatchingNamespaceFragment(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord) {
        if (broadcastRecord.options == null) {
            return null;
        }
        return broadcastRecord.options.getDeliveryGroupMatchingNamespaceFragment();
    }

    @android.annotation.Nullable
    private static java.lang.String getDeliveryGroupMatchingKeyFragment(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord) {
        if (broadcastRecord.options == null) {
            return null;
        }
        return broadcastRecord.options.getDeliveryGroupMatchingKeyFragment();
    }

    @android.annotation.Nullable
    private static android.content.IntentFilter getDeliveryGroupMatchingFilter(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord) {
        if (broadcastRecord.options == null) {
            return null;
        }
        return broadcastRecord.options.getDeliveryGroupMatchingFilter();
    }

    boolean allReceiversPending() {
        return this.terminalCount == 0 && this.dispatchTime <= 0;
    }

    boolean isMatchingRecord(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord) {
        int indexOfKey = this.mMatchingRecordsCache.indexOfKey(broadcastRecord);
        if (indexOfKey > 0) {
            return this.mMatchingRecordsCache.valueAt(indexOfKey).booleanValue();
        }
        boolean z = false;
        boolean z2 = this.receivers.size() == broadcastRecord.receivers.size();
        if (z2) {
            for (int size = this.receivers.size() - 1; size >= 0; size--) {
                if (!isReceiverEquals(this.receivers.get(size), broadcastRecord.receivers.get(size))) {
                    break;
                }
            }
        }
        z = z2;
        this.mMatchingRecordsCache.put(broadcastRecord, java.lang.Boolean.valueOf(z));
        return z;
    }

    void setMatchingRecordsCache(@android.annotation.NonNull android.util.ArrayMap<com.android.server.am.BroadcastRecord, java.lang.Boolean> arrayMap) {
        this.mMatchingRecordsCache = arrayMap;
    }

    void clearMatchingRecordsCache() {
        this.mMatchingRecordsCache = null;
    }

    public java.lang.String toString() {
        if (this.mCachedToString == null) {
            if (this.intent.getAction() == null) {
                this.intent.toString();
            }
            this.mCachedToString = "BroadcastRecord{" + toShortString() + "}";
        }
        return this.mCachedToString;
    }

    public java.lang.String toShortString() {
        if (this.mCachedToShortString == null) {
            java.lang.String action = this.intent.getAction();
            if (action == null) {
                action = this.intent.toString();
            }
            this.mCachedToShortString = java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + action + "/u" + this.userId;
        }
        return this.mCachedToShortString;
    }

    @dalvik.annotation.optimization.NeverCompile
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.userId);
        protoOutputStream.write(1138166333442L, this.intent.getAction());
        protoOutputStream.end(start);
    }
}
