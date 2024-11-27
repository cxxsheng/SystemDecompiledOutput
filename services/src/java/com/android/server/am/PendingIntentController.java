package com.android.server.am;

/* loaded from: classes.dex */
public class PendingIntentController {
    private static final int RECENT_N = 10;
    private static final java.lang.String TAG = "ActivityManager";
    private static final java.lang.String TAG_MU = "ActivityManager_MU";
    android.app.ActivityManagerInternal mAmInternal;
    private final com.android.server.am.ActivityManagerConstants mConstants;
    final android.os.Handler mH;
    final com.android.server.am.UserController mUserController;
    final java.lang.Object mLock = new java.lang.Object();
    final java.util.HashMap<com.android.server.am.PendingIntentRecord.Key, java.lang.ref.WeakReference<com.android.server.am.PendingIntentRecord>> mIntentSenderRecords = new java.util.HashMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseIntArray mIntentsPerUid = new android.util.SparseIntArray();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.internal.util.RingBuffer<java.lang.String>> mRecentIntentsPerUid = new android.util.SparseArray<>();
    final com.android.server.wm.ActivityTaskManagerInternal mAtmInternal = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);

    PendingIntentController(android.os.Looper looper, com.android.server.am.UserController userController, com.android.server.am.ActivityManagerConstants activityManagerConstants) {
        this.mH = new android.os.Handler(looper);
        this.mUserController = userController;
        this.mConstants = activityManagerConstants;
    }

    void onActivityManagerInternalAdded() {
        synchronized (this.mLock) {
            this.mAmInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        }
    }

    public com.android.server.am.PendingIntentRecord getIntentSender(int i, java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i2, int i3, android.os.IBinder iBinder, java.lang.String str3, int i4, android.content.Intent[] intentArr, java.lang.String[] strArr, int i5, android.os.Bundle bundle) {
        android.content.Intent intent;
        synchronized (this.mLock) {
            if (intentArr != null) {
                for (android.content.Intent intent2 : intentArr) {
                    try {
                        intent2.setDefusable(true);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
            android.os.Bundle.setDefusable(bundle, true);
            android.app.ActivityOptions fromBundle = android.app.ActivityOptions.fromBundle(bundle);
            if (fromBundle != null && fromBundle.getPendingIntentBackgroundActivityStartMode() != 0) {
                android.util.Slog.wtf(TAG, "Resetting option setPendingIntentBackgroundActivityStartMode(" + fromBundle.getPendingIntentBackgroundActivityStartMode() + ") to SYSTEM_DEFINED from the options provided by the pending intent creator (" + str + ") because this option is meant for the pending intent sender");
                if (android.app.compat.CompatChanges.isChangeEnabled(320664730L, i2)) {
                    throw new java.lang.IllegalArgumentException("pendingIntentBackgroundActivityStartMode must not be set when creating a PendingIntent");
                }
                fromBundle.setPendingIntentBackgroundActivityStartMode(0);
            }
            boolean z = (i5 & 536870912) != 0;
            boolean z2 = (i5 & 268435456) != 0;
            boolean z3 = (i5 & 134217728) != 0;
            com.android.server.am.PendingIntentRecord.Key key = new com.android.server.am.PendingIntentRecord.Key(i, str, str2, iBinder, str3, i4, intentArr, strArr, i5 & (-939524097), new com.android.server.wm.SafeActivityOptions(fromBundle), i3);
            java.lang.ref.WeakReference<com.android.server.am.PendingIntentRecord> weakReference = this.mIntentSenderRecords.get(key);
            com.android.server.am.PendingIntentRecord pendingIntentRecord = weakReference != null ? weakReference.get() : null;
            if (pendingIntentRecord != null) {
                if (!z2) {
                    if (z3) {
                        if (pendingIntentRecord.key.requestIntent != null) {
                            android.content.Intent intent3 = pendingIntentRecord.key.requestIntent;
                            if (intentArr == null) {
                                intent = null;
                            } else {
                                intent = intentArr[intentArr.length - 1];
                            }
                            intent3.replaceExtras(intent);
                        }
                        if (intentArr == null) {
                            pendingIntentRecord.key.allIntents = null;
                            pendingIntentRecord.key.allResolvedTypes = null;
                        } else {
                            intentArr[intentArr.length - 1] = pendingIntentRecord.key.requestIntent;
                            pendingIntentRecord.key.allIntents = intentArr;
                            pendingIntentRecord.key.allResolvedTypes = strArr;
                        }
                    }
                    return pendingIntentRecord;
                }
                makeIntentSenderCanceled(pendingIntentRecord);
                this.mIntentSenderRecords.remove(key);
                decrementUidStatLocked(pendingIntentRecord);
            }
            if (z) {
                return pendingIntentRecord;
            }
            com.android.server.am.PendingIntentRecord pendingIntentRecord2 = new com.android.server.am.PendingIntentRecord(this, key, i2);
            this.mIntentSenderRecords.put(key, pendingIntentRecord2.ref);
            incrementUidStatLocked(pendingIntentRecord2);
            return pendingIntentRecord2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0066 A[Catch: all -> 0x000f, TryCatch #0 {all -> 0x000f, blocks: (B:4:0x0004, B:6:0x000d, B:9:0x0012, B:10:0x001c, B:12:0x0022, B:63:0x002a, B:15:0x002e, B:60:0x0036, B:53:0x003c, B:49:0x0064, B:43:0x0066, B:45:0x0076, B:20:0x0043, B:29:0x004f, B:35:0x0056, B:66:0x008b), top: B:3:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0064 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    boolean removePendingIntentsForPackage(java.lang.String str, int i, int i2, boolean z) {
        synchronized (this.mLock) {
            try {
                boolean z2 = false;
                if (this.mIntentSenderRecords.size() <= 0) {
                    return false;
                }
                java.util.Iterator<java.lang.ref.WeakReference<com.android.server.am.PendingIntentRecord>> it = this.mIntentSenderRecords.values().iterator();
                while (it.hasNext()) {
                    java.lang.ref.WeakReference<com.android.server.am.PendingIntentRecord> next = it.next();
                    if (next == null) {
                        it.remove();
                    } else {
                        com.android.server.am.PendingIntentRecord pendingIntentRecord = next.get();
                        if (pendingIntentRecord == null) {
                            it.remove();
                        } else if (str == null) {
                            if (pendingIntentRecord.key.userId == i) {
                                z2 = true;
                                if (z) {
                                    return true;
                                }
                                it.remove();
                                makeIntentSenderCanceled(pendingIntentRecord);
                                decrementUidStatLocked(pendingIntentRecord);
                                if (pendingIntentRecord.key.activity != null) {
                                    this.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.am.PendingIntentController$$ExternalSyntheticLambda0(), this, pendingIntentRecord.key.activity, pendingIntentRecord.ref));
                                }
                            }
                        } else if (android.os.UserHandle.getAppId(pendingIntentRecord.uid) == i2 && (i == -1 || pendingIntentRecord.key.userId == i)) {
                            if (pendingIntentRecord.key.packageName.equals(str)) {
                                z2 = true;
                                if (z) {
                                }
                            }
                        }
                    }
                }
                return z2;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void cancelIntentSender(android.content.IIntentSender iIntentSender) {
        if (!(iIntentSender instanceof com.android.server.am.PendingIntentRecord)) {
            return;
        }
        synchronized (this.mLock) {
            com.android.server.am.PendingIntentRecord pendingIntentRecord = (com.android.server.am.PendingIntentRecord) iIntentSender;
            try {
                if (!android.os.UserHandle.isSameApp(android.app.AppGlobals.getPackageManager().getPackageUid(pendingIntentRecord.key.packageName, 268435456L, android.os.UserHandle.getCallingUserId()), android.os.Binder.getCallingUid())) {
                    java.lang.String str = "Permission Denial: cancelIntentSender() from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " is not allowed to cancel package " + pendingIntentRecord.key.packageName;
                    android.util.Slog.w(TAG, str);
                    throw new java.lang.SecurityException(str);
                }
                cancelIntentSender(pendingIntentRecord, true);
            } catch (android.os.RemoteException e) {
                throw new java.lang.SecurityException(e);
            }
        }
    }

    public void cancelIntentSender(com.android.server.am.PendingIntentRecord pendingIntentRecord, boolean z) {
        synchronized (this.mLock) {
            try {
                makeIntentSenderCanceled(pendingIntentRecord);
                this.mIntentSenderRecords.remove(pendingIntentRecord.key);
                decrementUidStatLocked(pendingIntentRecord);
                if (z && pendingIntentRecord.key.activity != null) {
                    this.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.am.PendingIntentController$$ExternalSyntheticLambda0(), this, pendingIntentRecord.key.activity, pendingIntentRecord.ref));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean registerIntentSenderCancelListener(android.content.IIntentSender iIntentSender, com.android.internal.os.IResultReceiver iResultReceiver) {
        if (!(iIntentSender instanceof com.android.server.am.PendingIntentRecord)) {
            android.util.Slog.w(TAG, "registerIntentSenderCancelListener called on non-PendingIntentRecord");
            return true;
        }
        synchronized (this.mLock) {
            try {
                com.android.server.am.PendingIntentRecord pendingIntentRecord = (com.android.server.am.PendingIntentRecord) iIntentSender;
                if (pendingIntentRecord.canceled) {
                    return false;
                }
                pendingIntentRecord.registerCancelListenerLocked(iResultReceiver);
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void unregisterIntentSenderCancelListener(android.content.IIntentSender iIntentSender, com.android.internal.os.IResultReceiver iResultReceiver) {
        if (!(iIntentSender instanceof com.android.server.am.PendingIntentRecord)) {
            return;
        }
        synchronized (this.mLock) {
            ((com.android.server.am.PendingIntentRecord) iIntentSender).unregisterCancelListenerLocked(iResultReceiver);
        }
    }

    void setPendingIntentAllowlistDuration(android.content.IIntentSender iIntentSender, android.os.IBinder iBinder, long j, int i, int i2, @android.annotation.Nullable java.lang.String str) {
        if (!(iIntentSender instanceof com.android.server.am.PendingIntentRecord)) {
            android.util.Slog.w(TAG, "markAsSentFromNotification(): not a PendingIntentRecord: " + iIntentSender);
            return;
        }
        synchronized (this.mLock) {
            ((com.android.server.am.PendingIntentRecord) iIntentSender).setAllowlistDurationLocked(iBinder, j, i, i2, str);
        }
    }

    int getPendingIntentFlags(android.content.IIntentSender iIntentSender) {
        int i;
        if (!(iIntentSender instanceof com.android.server.am.PendingIntentRecord)) {
            android.util.Slog.w(TAG, "markAsSentFromNotification(): not a PendingIntentRecord: " + iIntentSender);
            return 0;
        }
        synchronized (this.mLock) {
            i = ((com.android.server.am.PendingIntentRecord) iIntentSender).key.flags;
        }
        return i;
    }

    private void makeIntentSenderCanceled(com.android.server.am.PendingIntentRecord pendingIntentRecord) {
        pendingIntentRecord.canceled = true;
        android.os.RemoteCallbackList<com.android.internal.os.IResultReceiver> detachCancelListenersLocked = pendingIntentRecord.detachCancelListenersLocked();
        if (detachCancelListenersLocked != null) {
            this.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.am.PendingIntentController$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((com.android.server.am.PendingIntentController) obj).handlePendingIntentCancelled((android.os.RemoteCallbackList) obj2);
                }
            }, this, detachCancelListenersLocked));
        }
        ((com.android.server.AlarmManagerInternal) com.android.server.LocalServices.getService(com.android.server.AlarmManagerInternal.class)).remove(new android.app.PendingIntent(pendingIntentRecord));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePendingIntentCancelled(android.os.RemoteCallbackList<com.android.internal.os.IResultReceiver> remoteCallbackList) {
        int beginBroadcast = remoteCallbackList.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                remoteCallbackList.getBroadcastItem(i).send(0, (android.os.Bundle) null);
            } catch (android.os.RemoteException e) {
            }
        }
        remoteCallbackList.finishBroadcast();
        remoteCallbackList.kill();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearPendingResultForActivity(android.os.IBinder iBinder, java.lang.ref.WeakReference<com.android.server.am.PendingIntentRecord> weakReference) {
        this.mAtmInternal.clearPendingResultForActivity(iBinder, weakReference);
    }

    void dumpPendingIntents(java.io.PrintWriter printWriter, boolean z, java.lang.String str) {
        boolean z2;
        synchronized (this.mLock) {
            try {
                printWriter.println("ACTIVITY MANAGER PENDING INTENTS (dumpsys activity intents)");
                if (this.mIntentSenderRecords.size() <= 0) {
                    z2 = false;
                } else {
                    android.util.ArrayMap arrayMap = new android.util.ArrayMap();
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    java.util.Iterator<java.lang.ref.WeakReference<com.android.server.am.PendingIntentRecord>> it = this.mIntentSenderRecords.values().iterator();
                    while (it.hasNext()) {
                        java.lang.ref.WeakReference<com.android.server.am.PendingIntentRecord> next = it.next();
                        com.android.server.am.PendingIntentRecord pendingIntentRecord = next != null ? next.get() : null;
                        if (pendingIntentRecord == null) {
                            arrayList.add(next);
                        } else if (str == null || str.equals(pendingIntentRecord.key.packageName)) {
                            java.util.ArrayList arrayList2 = (java.util.ArrayList) arrayMap.get(pendingIntentRecord.key.packageName);
                            if (arrayList2 == null) {
                                arrayList2 = new java.util.ArrayList();
                                arrayMap.put(pendingIntentRecord.key.packageName, arrayList2);
                            }
                            arrayList2.add(pendingIntentRecord);
                        }
                    }
                    int i = 0;
                    z2 = false;
                    while (i < arrayMap.size()) {
                        java.util.ArrayList arrayList3 = (java.util.ArrayList) arrayMap.valueAt(i);
                        printWriter.print("  * ");
                        printWriter.print((java.lang.String) arrayMap.keyAt(i));
                        printWriter.print(": ");
                        printWriter.print(arrayList3.size());
                        printWriter.println(" items");
                        for (int i2 = 0; i2 < arrayList3.size(); i2++) {
                            printWriter.print("    #");
                            printWriter.print(i2);
                            printWriter.print(": ");
                            printWriter.println(arrayList3.get(i2));
                            if (z) {
                                ((com.android.server.am.PendingIntentRecord) arrayList3.get(i2)).dump(printWriter, "      ");
                            }
                        }
                        i++;
                        z2 = true;
                    }
                    if (arrayList.size() > 0) {
                        printWriter.println("  * WEAK REFS:");
                        for (int i3 = 0; i3 < arrayList.size(); i3++) {
                            printWriter.print("    #");
                            printWriter.print(i3);
                            printWriter.print(": ");
                            printWriter.println(arrayList.get(i3));
                        }
                        z2 = true;
                    }
                }
                int size = this.mIntentsPerUid.size();
                if (size > 0) {
                    for (int i4 = 0; i4 < size; i4++) {
                        printWriter.print("  * UID: ");
                        printWriter.print(this.mIntentsPerUid.keyAt(i4));
                        printWriter.print(" total: ");
                        printWriter.println(this.mIntentsPerUid.valueAt(i4));
                    }
                }
                if (!z2) {
                    printWriter.println("  (nothing)");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public java.util.List<android.app.PendingIntentStats> dumpPendingIntentStatsForStatsd() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            try {
                if (this.mIntentSenderRecords.size() > 0) {
                    android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray();
                    android.util.SparseIntArray sparseIntArray2 = new android.util.SparseIntArray();
                    for (java.lang.ref.WeakReference<com.android.server.am.PendingIntentRecord> weakReference : this.mIntentSenderRecords.values()) {
                        if (weakReference != null && weakReference.get() != null) {
                            com.android.server.am.PendingIntentRecord pendingIntentRecord = weakReference.get();
                            int indexOfKey = sparseIntArray.indexOfKey(pendingIntentRecord.uid);
                            if (indexOfKey < 0) {
                                sparseIntArray.put(pendingIntentRecord.uid, 1);
                                sparseIntArray2.put(pendingIntentRecord.uid, pendingIntentRecord.key.requestIntent.getExtrasTotalSize());
                            } else {
                                sparseIntArray.put(pendingIntentRecord.uid, sparseIntArray.valueAt(indexOfKey) + 1);
                                sparseIntArray2.put(pendingIntentRecord.uid, sparseIntArray2.valueAt(indexOfKey) + pendingIntentRecord.key.requestIntent.getExtrasTotalSize());
                            }
                        }
                    }
                    int size = sparseIntArray.size();
                    for (int i = 0; i < size; i++) {
                        arrayList.add(new android.app.PendingIntentStats(sparseIntArray.keyAt(i), sparseIntArray.valueAt(i), sparseIntArray2.valueAt(i) / 1024));
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void incrementUidStatLocked(com.android.server.am.PendingIntentRecord pendingIntentRecord) {
        int i;
        com.android.internal.util.RingBuffer<java.lang.String> ringBuffer;
        int i2 = pendingIntentRecord.uid;
        int indexOfKey = this.mIntentsPerUid.indexOfKey(i2);
        if (indexOfKey >= 0) {
            i = this.mIntentsPerUid.valueAt(indexOfKey) + 1;
            this.mIntentsPerUid.setValueAt(indexOfKey, i);
        } else {
            this.mIntentsPerUid.put(i2, 1);
            i = 1;
        }
        int i3 = (this.mConstants.PENDINGINTENT_WARNING_THRESHOLD - 10) + 1;
        if (i == i3) {
            ringBuffer = new com.android.internal.util.RingBuffer<>(java.lang.String.class, 10);
            this.mRecentIntentsPerUid.put(i2, ringBuffer);
        } else if (i > i3 && i <= this.mConstants.PENDINGINTENT_WARNING_THRESHOLD) {
            ringBuffer = this.mRecentIntentsPerUid.get(i2);
        } else {
            ringBuffer = null;
        }
        if (ringBuffer == null) {
            return;
        }
        ringBuffer.append(pendingIntentRecord.key.toString());
        if (i == this.mConstants.PENDINGINTENT_WARNING_THRESHOLD) {
            android.util.Slog.wtf(TAG, "Too many PendingIntent created for uid " + i2 + ", recent 10: " + java.util.Arrays.toString(ringBuffer.toArray()));
            this.mRecentIntentsPerUid.remove(i2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void decrementUidStatLocked(com.android.server.am.PendingIntentRecord pendingIntentRecord) {
        int i = pendingIntentRecord.uid;
        int indexOfKey = this.mIntentsPerUid.indexOfKey(i);
        if (indexOfKey >= 0) {
            int valueAt = this.mIntentsPerUid.valueAt(indexOfKey) - 1;
            if (valueAt == this.mConstants.PENDINGINTENT_WARNING_THRESHOLD - 10) {
                this.mRecentIntentsPerUid.delete(i);
            }
            if (valueAt == 0) {
                this.mIntentsPerUid.removeAt(indexOfKey);
            } else {
                this.mIntentsPerUid.setValueAt(indexOfKey, valueAt);
            }
        }
    }
}
