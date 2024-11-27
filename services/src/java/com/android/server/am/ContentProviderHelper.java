package com.android.server.am;

/* loaded from: classes.dex */
public class ContentProviderHelper {
    private static final int[] PROCESS_STATE_STATS_FORMAT = {32, com.android.internal.util.FrameworkStatsLog.PACKAGE_MANAGER_SNAPSHOT_REPORTED, 10272};
    private static final java.lang.String TAG = "ContentProviderHelper";
    private final com.android.server.am.ProviderMap mProviderMap;
    private final com.android.server.am.ActivityManagerService mService;
    private boolean mSystemProvidersInstalled;
    private final java.util.ArrayList<com.android.server.am.ContentProviderRecord> mLaunchingProviders = new java.util.ArrayList<>();
    private final java.util.Map<java.lang.String, java.lang.Boolean> mCloneProfileAuthorityRedirectionCache = new java.util.HashMap();
    private final long[] mProcessStateStatsLongs = new long[1];

    ContentProviderHelper(com.android.server.am.ActivityManagerService activityManagerService, boolean z) {
        this.mService = activityManagerService;
        this.mProviderMap = z ? new com.android.server.am.ProviderMap(this.mService) : null;
    }

    com.android.server.am.ProviderMap getProviderMap() {
        return this.mProviderMap;
    }

    android.app.ContentProviderHolder getContentProvider(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, int i, boolean z) {
        this.mService.enforceNotIsolatedCaller("getContentProvider");
        if (iApplicationThread == null) {
            java.lang.String str3 = "null IApplicationThread when getting content provider " + str2;
            android.util.Slog.w(TAG, str3);
            throw new java.lang.SecurityException(str3);
        }
        int callingUid = android.os.Binder.getCallingUid();
        if (str != null && this.mService.mAppOpsService.checkPackage(callingUid, str) != 0) {
            throw new java.lang.SecurityException("Given calling package " + str + " does not match caller's uid " + callingUid);
        }
        return getContentProviderImpl(iApplicationThread, str2, null, callingUid, str, null, z, i);
    }

    android.app.ContentProviderHolder getContentProviderExternal(java.lang.String str, int i, android.os.IBinder iBinder, java.lang.String str2) {
        this.mService.enforceCallingPermission("android.permission.ACCESS_CONTENT_PROVIDERS_EXTERNALLY", "Do not have permission in call getContentProviderExternal()");
        return getContentProviderExternalUnchecked(str, iBinder, android.os.Binder.getCallingUid(), str2 != null ? str2 : "*external*", this.mService.mUserController.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, 2, "getContentProvider", null));
    }

    android.app.ContentProviderHolder getContentProviderExternalUnchecked(java.lang.String str, android.os.IBinder iBinder, int i, java.lang.String str2, int i2) {
        return getContentProviderImpl(null, str, iBinder, i, null, str2, true, i2);
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0777  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0306 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:192:0x0326 A[Catch: all -> 0x02a2, TRY_ENTER, TRY_LEAVE, TryCatch #1 {all -> 0x02a2, blocks: (B:77:0x0299, B:78:0x029c, B:83:0x02e2, B:186:0x0306, B:189:0x0313, B:192:0x0326, B:196:0x032d, B:198:0x033d, B:201:0x0346, B:206:0x035a, B:209:0x0370, B:211:0x038c, B:213:0x0398, B:214:0x039f, B:216:0x03a0, B:228:0x03dc, B:230:0x03e7, B:231:0x0422, B:235:0x0429, B:237:0x0444, B:242:0x0454, B:244:0x0464, B:251:0x049c, B:252:0x049f, B:256:0x04bf, B:257:0x04cf, B:259:0x04d6, B:261:0x04dc, B:262:0x04e2, B:266:0x04e9, B:268:0x04f2, B:270:0x04fb, B:274:0x0500, B:296:0x067c, B:297:0x068b, B:299:0x0692, B:300:0x0699, B:302:0x06c6, B:86:0x06db, B:88:0x06f6, B:96:0x0748, B:104:0x076c, B:105:0x0770, B:111:0x0776, B:113:0x077a, B:312:0x0653, B:313:0x0656, B:326:0x0680, B:327:0x0683, B:332:0x04c4, B:333:0x04c7, B:335:0x04c8, B:343:0x06d2, B:346:0x0344, B:404:0x0890, B:358:0x02f0, B:359:0x02f3, B:248:0x0469, B:250:0x0483, B:255:0x04aa, B:90:0x06f7, B:92:0x06fb, B:94:0x06ff, B:95:0x0747, B:100:0x0752, B:101:0x0754, B:102:0x076a, B:218:0x03a1, B:220:0x03a5, B:222:0x03ad, B:225:0x03b9, B:226:0x03d6, B:227:0x03db, B:276:0x0504, B:278:0x0510, B:280:0x0522, B:281:0x0561, B:283:0x0574, B:285:0x057a, B:287:0x0580, B:289:0x058a, B:291:0x0594, B:294:0x0599, B:295:0x0675, B:306:0x05d8, B:309:0x05e5, B:311:0x061f, B:317:0x065d, B:323:0x053f), top: B:3:0x0014, inners: #2, #4, #9, #16, #19 }] */
    /* JADX WARN: Removed duplicated region for block: B:195:0x032c  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0357  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x036b  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x03a1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:274:0x0500 A[Catch: all -> 0x02a2, TRY_LEAVE, TryCatch #1 {all -> 0x02a2, blocks: (B:77:0x0299, B:78:0x029c, B:83:0x02e2, B:186:0x0306, B:189:0x0313, B:192:0x0326, B:196:0x032d, B:198:0x033d, B:201:0x0346, B:206:0x035a, B:209:0x0370, B:211:0x038c, B:213:0x0398, B:214:0x039f, B:216:0x03a0, B:228:0x03dc, B:230:0x03e7, B:231:0x0422, B:235:0x0429, B:237:0x0444, B:242:0x0454, B:244:0x0464, B:251:0x049c, B:252:0x049f, B:256:0x04bf, B:257:0x04cf, B:259:0x04d6, B:261:0x04dc, B:262:0x04e2, B:266:0x04e9, B:268:0x04f2, B:270:0x04fb, B:274:0x0500, B:296:0x067c, B:297:0x068b, B:299:0x0692, B:300:0x0699, B:302:0x06c6, B:86:0x06db, B:88:0x06f6, B:96:0x0748, B:104:0x076c, B:105:0x0770, B:111:0x0776, B:113:0x077a, B:312:0x0653, B:313:0x0656, B:326:0x0680, B:327:0x0683, B:332:0x04c4, B:333:0x04c7, B:335:0x04c8, B:343:0x06d2, B:346:0x0344, B:404:0x0890, B:358:0x02f0, B:359:0x02f3, B:248:0x0469, B:250:0x0483, B:255:0x04aa, B:90:0x06f7, B:92:0x06fb, B:94:0x06ff, B:95:0x0747, B:100:0x0752, B:101:0x0754, B:102:0x076a, B:218:0x03a1, B:220:0x03a5, B:222:0x03ad, B:225:0x03b9, B:226:0x03d6, B:227:0x03db, B:276:0x0504, B:278:0x0510, B:280:0x0522, B:281:0x0561, B:283:0x0574, B:285:0x057a, B:287:0x0580, B:289:0x058a, B:291:0x0594, B:294:0x0599, B:295:0x0675, B:306:0x05d8, B:309:0x05e5, B:311:0x061f, B:317:0x065d, B:323:0x053f), top: B:3:0x0014, inners: #2, #4, #9, #16, #19 }] */
    /* JADX WARN: Removed duplicated region for block: B:299:0x0692 A[Catch: all -> 0x02a2, TryCatch #1 {all -> 0x02a2, blocks: (B:77:0x0299, B:78:0x029c, B:83:0x02e2, B:186:0x0306, B:189:0x0313, B:192:0x0326, B:196:0x032d, B:198:0x033d, B:201:0x0346, B:206:0x035a, B:209:0x0370, B:211:0x038c, B:213:0x0398, B:214:0x039f, B:216:0x03a0, B:228:0x03dc, B:230:0x03e7, B:231:0x0422, B:235:0x0429, B:237:0x0444, B:242:0x0454, B:244:0x0464, B:251:0x049c, B:252:0x049f, B:256:0x04bf, B:257:0x04cf, B:259:0x04d6, B:261:0x04dc, B:262:0x04e2, B:266:0x04e9, B:268:0x04f2, B:270:0x04fb, B:274:0x0500, B:296:0x067c, B:297:0x068b, B:299:0x0692, B:300:0x0699, B:302:0x06c6, B:86:0x06db, B:88:0x06f6, B:96:0x0748, B:104:0x076c, B:105:0x0770, B:111:0x0776, B:113:0x077a, B:312:0x0653, B:313:0x0656, B:326:0x0680, B:327:0x0683, B:332:0x04c4, B:333:0x04c7, B:335:0x04c8, B:343:0x06d2, B:346:0x0344, B:404:0x0890, B:358:0x02f0, B:359:0x02f3, B:248:0x0469, B:250:0x0483, B:255:0x04aa, B:90:0x06f7, B:92:0x06fb, B:94:0x06ff, B:95:0x0747, B:100:0x0752, B:101:0x0754, B:102:0x076a, B:218:0x03a1, B:220:0x03a5, B:222:0x03ad, B:225:0x03b9, B:226:0x03d6, B:227:0x03db, B:276:0x0504, B:278:0x0510, B:280:0x0522, B:281:0x0561, B:283:0x0574, B:285:0x057a, B:287:0x0580, B:289:0x058a, B:291:0x0594, B:294:0x0599, B:295:0x0675, B:306:0x05d8, B:309:0x05e5, B:311:0x061f, B:317:0x065d, B:323:0x053f), top: B:3:0x0014, inners: #2, #4, #9, #16, #19 }] */
    /* JADX WARN: Removed duplicated region for block: B:302:0x06c6 A[Catch: all -> 0x02a2, TRY_LEAVE, TryCatch #1 {all -> 0x02a2, blocks: (B:77:0x0299, B:78:0x029c, B:83:0x02e2, B:186:0x0306, B:189:0x0313, B:192:0x0326, B:196:0x032d, B:198:0x033d, B:201:0x0346, B:206:0x035a, B:209:0x0370, B:211:0x038c, B:213:0x0398, B:214:0x039f, B:216:0x03a0, B:228:0x03dc, B:230:0x03e7, B:231:0x0422, B:235:0x0429, B:237:0x0444, B:242:0x0454, B:244:0x0464, B:251:0x049c, B:252:0x049f, B:256:0x04bf, B:257:0x04cf, B:259:0x04d6, B:261:0x04dc, B:262:0x04e2, B:266:0x04e9, B:268:0x04f2, B:270:0x04fb, B:274:0x0500, B:296:0x067c, B:297:0x068b, B:299:0x0692, B:300:0x0699, B:302:0x06c6, B:86:0x06db, B:88:0x06f6, B:96:0x0748, B:104:0x076c, B:105:0x0770, B:111:0x0776, B:113:0x077a, B:312:0x0653, B:313:0x0656, B:326:0x0680, B:327:0x0683, B:332:0x04c4, B:333:0x04c7, B:335:0x04c8, B:343:0x06d2, B:346:0x0344, B:404:0x0890, B:358:0x02f0, B:359:0x02f3, B:248:0x0469, B:250:0x0483, B:255:0x04aa, B:90:0x06f7, B:92:0x06fb, B:94:0x06ff, B:95:0x0747, B:100:0x0752, B:101:0x0754, B:102:0x076a, B:218:0x03a1, B:220:0x03a5, B:222:0x03ad, B:225:0x03b9, B:226:0x03d6, B:227:0x03db, B:276:0x0504, B:278:0x0510, B:280:0x0522, B:281:0x0561, B:283:0x0574, B:285:0x057a, B:287:0x0580, B:289:0x058a, B:291:0x0594, B:294:0x0599, B:295:0x0675, B:306:0x05d8, B:309:0x05e5, B:311:0x061f, B:317:0x065d, B:323:0x053f), top: B:3:0x0014, inners: #2, #4, #9, #16, #19 }] */
    /* JADX WARN: Removed duplicated region for block: B:304:0x06ca  */
    /* JADX WARN: Removed duplicated region for block: B:328:0x0684  */
    /* JADX WARN: Removed duplicated region for block: B:344:0x036e  */
    /* JADX WARN: Removed duplicated region for block: B:345:0x0359  */
    /* JADX WARN: Removed duplicated region for block: B:366:0x02f8  */
    /* JADX WARN: Removed duplicated region for block: B:367:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0162 A[Catch: all -> 0x0050, TRY_LEAVE, TryCatch #13 {all -> 0x0050, blocks: (B:397:0x001b, B:10:0x006c, B:19:0x00b2, B:21:0x00ba, B:23:0x00d0, B:26:0x00d9, B:30:0x0116, B:32:0x011a, B:35:0x0125, B:37:0x012d, B:39:0x0135, B:41:0x0162, B:47:0x0175, B:49:0x017b, B:50:0x01ae, B:56:0x01b6, B:58:0x01c0, B:372:0x00d7, B:373:0x00ea, B:376:0x00f2, B:384:0x008d, B:386:0x0098, B:390:0x00a5, B:400:0x0025, B:401:0x004f), top: B:396:0x001b }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0171 A[Catch: all -> 0x02f4, TRY_ENTER, TRY_LEAVE, TryCatch #14 {all -> 0x02f4, blocks: (B:4:0x0014, B:7:0x0058, B:13:0x0075, B:45:0x0171, B:61:0x01c7, B:382:0x0081), top: B:3:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x06d3  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x06f6 A[Catch: all -> 0x02a2, TRY_LEAVE, TryCatch #1 {all -> 0x02a2, blocks: (B:77:0x0299, B:78:0x029c, B:83:0x02e2, B:186:0x0306, B:189:0x0313, B:192:0x0326, B:196:0x032d, B:198:0x033d, B:201:0x0346, B:206:0x035a, B:209:0x0370, B:211:0x038c, B:213:0x0398, B:214:0x039f, B:216:0x03a0, B:228:0x03dc, B:230:0x03e7, B:231:0x0422, B:235:0x0429, B:237:0x0444, B:242:0x0454, B:244:0x0464, B:251:0x049c, B:252:0x049f, B:256:0x04bf, B:257:0x04cf, B:259:0x04d6, B:261:0x04dc, B:262:0x04e2, B:266:0x04e9, B:268:0x04f2, B:270:0x04fb, B:274:0x0500, B:296:0x067c, B:297:0x068b, B:299:0x0692, B:300:0x0699, B:302:0x06c6, B:86:0x06db, B:88:0x06f6, B:96:0x0748, B:104:0x076c, B:105:0x0770, B:111:0x0776, B:113:0x077a, B:312:0x0653, B:313:0x0656, B:326:0x0680, B:327:0x0683, B:332:0x04c4, B:333:0x04c7, B:335:0x04c8, B:343:0x06d2, B:346:0x0344, B:404:0x0890, B:358:0x02f0, B:359:0x02f3, B:248:0x0469, B:250:0x0483, B:255:0x04aa, B:90:0x06f7, B:92:0x06fb, B:94:0x06ff, B:95:0x0747, B:100:0x0752, B:101:0x0754, B:102:0x076a, B:218:0x03a1, B:220:0x03a5, B:222:0x03ad, B:225:0x03b9, B:226:0x03d6, B:227:0x03db, B:276:0x0504, B:278:0x0510, B:280:0x0522, B:281:0x0561, B:283:0x0574, B:285:0x057a, B:287:0x0580, B:289:0x058a, B:291:0x0594, B:294:0x0599, B:295:0x0675, B:306:0x05d8, B:309:0x05e5, B:311:0x061f, B:317:0x065d, B:323:0x053f), top: B:3:0x0014, inners: #2, #4, #9, #16, #19 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.app.ContentProviderHolder getContentProviderImpl(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.os.IBinder iBinder, int i, java.lang.String str2, java.lang.String str3, boolean z, int i2) {
        com.android.server.am.ActivityManagerService activityManagerService;
        java.lang.Throwable th;
        com.android.server.am.ProcessRecord processRecord;
        com.android.server.am.ContentProviderRecord providerByName;
        boolean z2;
        boolean z3;
        com.android.server.am.ContentProviderRecord contentProviderRecord;
        int i3;
        android.content.pm.ProviderInfo providerInfo;
        boolean z4;
        com.android.server.am.ProcessRecord processRecord2;
        com.android.server.am.ProcessRecord processRecord3;
        int i4;
        java.lang.String str4;
        long j;
        boolean z5;
        com.android.server.am.ProcessRecord processRecord4;
        com.android.server.am.ContentProviderConnection contentProviderConnection;
        int i5;
        android.content.pm.ProviderInfo providerInfo2;
        boolean z6;
        boolean z7;
        com.android.server.am.ProcessRecord processRecord5;
        com.android.server.am.ContentProviderRecord contentProviderRecord2;
        int size;
        int i6;
        com.android.server.am.ProcessRecord processRecord6;
        android.content.ComponentName componentName;
        int i7;
        com.android.server.am.ContentProviderRecord contentProviderRecord3;
        long j2;
        boolean z8;
        int i8;
        com.android.server.am.ProcessRecord processRecord7;
        android.app.IApplicationThread thread;
        android.content.pm.ApplicationInfo applicationInfo;
        java.lang.String str5;
        android.content.pm.ProviderInfo providerInfo3;
        boolean z9;
        com.android.server.am.ActivityManagerService activityManagerService2 = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService2) {
            try {
                try {
                    long uptimeMillis = android.os.SystemClock.uptimeMillis();
                    if (iApplicationThread != null) {
                        try {
                            com.android.server.am.ProcessRecord recordForAppLOSP = this.mService.getRecordForAppLOSP(iApplicationThread);
                            if (recordForAppLOSP == null) {
                                throw new java.lang.SecurityException("Unable to find app for caller " + iApplicationThread + " (pid=" + android.os.Binder.getCallingPid() + ") when getting content provider " + str);
                            }
                            processRecord = recordForAppLOSP;
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                            activityManagerService = activityManagerService2;
                            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    } else {
                        processRecord = null;
                    }
                    checkTime(uptimeMillis, "getContentProviderImpl: getProviderByName");
                    com.android.server.pm.UserManagerInternal userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
                    android.content.pm.UserProperties userProperties = userManagerInternal.getUserProperties(i2);
                    boolean z10 = userProperties != null && userProperties.isMediaSharedWithParent();
                    if (isAuthorityRedirectedForCloneProfileCached(str) && z10) {
                        providerByName = null;
                        z2 = true;
                    } else {
                        providerByName = this.mProviderMap.getProviderByName(str, i2);
                        if (isAuthorityRedirectedForCloneProfileCached(str)) {
                            int userId = android.os.UserHandle.getUserId(i);
                            android.content.pm.UserProperties userProperties2 = userManagerInternal.getUserProperties(userId);
                            if ((userProperties2 != null && userProperties2.isMediaSharedWithParent()) && userManagerInternal.getProfileParentId(userId) == i2) {
                                z2 = false;
                            }
                        }
                        z2 = true;
                    }
                    if (providerByName != null || i2 == 0) {
                        z3 = z2;
                        contentProviderRecord = providerByName;
                        i3 = i2;
                        providerInfo = null;
                    } else {
                        com.android.server.am.ContentProviderRecord providerByName2 = this.mProviderMap.getProviderByName(str, 0);
                        if (providerByName2 != null) {
                            providerInfo = providerByName2.info;
                            z3 = z2;
                            if (this.mService.isSingleton(providerInfo.processName, providerInfo.applicationInfo, providerInfo.name, providerInfo.flags)) {
                                if (this.mService.isValidSingletonCall(processRecord == null ? i : processRecord.uid, providerInfo.applicationInfo.uid)) {
                                    contentProviderRecord = providerByName2;
                                    i3 = 0;
                                    z3 = false;
                                }
                            }
                            if (!isAuthorityRedirectedForCloneProfileCached(str)) {
                                i3 = i2;
                                providerInfo = null;
                                contentProviderRecord = null;
                            } else if (z10) {
                                i3 = userManagerInternal.getProfileParentId(i2);
                                contentProviderRecord = providerByName2;
                                z3 = false;
                            } else {
                                i3 = i2;
                                contentProviderRecord = providerByName2;
                            }
                        } else {
                            z3 = z2;
                            i3 = i2;
                            contentProviderRecord = providerByName2;
                            providerInfo = null;
                        }
                    }
                    if (contentProviderRecord == null || contentProviderRecord.proc == null) {
                        z4 = false;
                    } else {
                        boolean z11 = !contentProviderRecord.proc.isKilled();
                        if (contentProviderRecord.proc.isKilled() && contentProviderRecord.proc.isKilledByAm()) {
                            android.util.Slog.wtf(TAG, contentProviderRecord.proc.toString() + " was killed by AM but isn't really dead");
                            z4 = z11;
                            processRecord2 = contentProviderRecord.proc;
                            int curProcState = processRecord == null ? processRecord.mState.getCurProcState() : -1;
                            if (z4) {
                                processRecord3 = processRecord;
                                activityManagerService = activityManagerService2;
                                i4 = i3;
                                str4 = str;
                                j = uptimeMillis;
                                z5 = z4;
                                processRecord4 = processRecord2;
                                contentProviderConnection = null;
                            } else {
                                android.content.pm.ProviderInfo providerInfo4 = contentProviderRecord.info;
                                if (processRecord != null && contentProviderRecord.canRunHere(processRecord)) {
                                    checkAssociationAndPermissionLocked(processRecord, providerInfo4, i, i3, z3, contentProviderRecord.name.flattenToShortString(), uptimeMillis);
                                    android.app.ContentProviderHolder newHolder = contentProviderRecord.newHolder(null, true);
                                    newHolder.provider = null;
                                    com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.PROVIDER_ACQUISITION_EVENT_REPORTED, processRecord.uid, i, 1, 1, providerInfo4.packageName, str2, curProcState, curProcState);
                                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                                    return newHolder;
                                }
                                com.android.server.am.ProcessRecord processRecord8 = processRecord;
                                try {
                                    if (android.app.AppGlobals.getPackageManager().resolveContentProvider(str, 0L, i3) == null) {
                                        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                                        return null;
                                    }
                                } catch (android.os.RemoteException e) {
                                }
                                checkAssociationAndPermissionLocked(processRecord8, providerInfo4, i, i3, z3, contentProviderRecord.name.flattenToShortString(), uptimeMillis);
                                int curProcState2 = contentProviderRecord.proc.mState.getCurProcState();
                                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                                try {
                                    checkTime(uptimeMillis, "getContentProviderImpl: incProviderCountLocked");
                                    activityManagerService = activityManagerService2;
                                    i4 = i3;
                                    try {
                                        com.android.server.am.ContentProviderConnection incProviderCountLocked = incProviderCountLocked(processRecord8, contentProviderRecord, iBinder, i, str2, str3, z, true, uptimeMillis, this.mService.mProcessList, i2);
                                        j = uptimeMillis;
                                        checkTime(j, "getContentProviderImpl: before updateOomAdj");
                                        int verifiedAdj = contentProviderRecord.proc.mState.getVerifiedAdj();
                                        com.android.server.am.Flags.serviceBindingOomAdjPolicy();
                                        boolean updateOomAdjLocked = this.mService.updateOomAdjLocked(contentProviderRecord.proc, 7);
                                        if (updateOomAdjLocked && verifiedAdj != contentProviderRecord.proc.mState.getSetAdj() && !isProcessAliveLocked(contentProviderRecord.proc)) {
                                            updateOomAdjLocked = false;
                                        }
                                        str4 = str;
                                        maybeUpdateProviderUsageStatsLocked(processRecord8, contentProviderRecord.info.packageName, str4);
                                        checkTime(j, "getContentProviderImpl: after updateOomAdj");
                                        if (updateOomAdjLocked) {
                                            contentProviderRecord.proc.mState.setVerifiedAdj(contentProviderRecord.proc.mState.getSetAdj());
                                            providerInfo3 = providerInfo4;
                                            processRecord3 = processRecord8;
                                            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.PROVIDER_ACQUISITION_EVENT_REPORTED, contentProviderRecord.proc.uid, i, 1, 1, providerInfo4.packageName, str2, curProcState, curProcState2);
                                            z9 = z4;
                                            contentProviderConnection = incProviderCountLocked;
                                        } else {
                                            android.util.Slog.wtf(TAG, "Existing provider " + contentProviderRecord.name.flattenToShortString() + " is crashing; detaching " + processRecord8);
                                            if (!decProviderCountLocked(incProviderCountLocked, contentProviderRecord, iBinder, z, false, false)) {
                                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                                                return null;
                                            }
                                            processRecord2 = contentProviderRecord.proc;
                                            contentProviderConnection = null;
                                            processRecord3 = processRecord8;
                                            providerInfo3 = providerInfo4;
                                            z9 = false;
                                        }
                                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                        z5 = z9;
                                        providerInfo = providerInfo3;
                                        processRecord4 = processRecord2;
                                    } catch (java.lang.Throwable th3) {
                                        th = th3;
                                        java.lang.Throwable th4 = th;
                                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                        throw th4;
                                    }
                                } catch (java.lang.Throwable th5) {
                                    th = th5;
                                }
                            }
                            if (z5) {
                                try {
                                    checkTime(j, "getContentProviderImpl: before resolveContentProvider");
                                    i5 = i4;
                                    try {
                                        providerInfo = android.app.AppGlobals.getPackageManager().resolveContentProvider(str4, 3072L, i5);
                                        checkTime(j, "getContentProviderImpl: after resolveContentProvider");
                                        providerInfo2 = providerInfo;
                                    } catch (android.os.RemoteException e2) {
                                        providerInfo2 = providerInfo;
                                        if (providerInfo2 != null) {
                                        }
                                    }
                                } catch (android.os.RemoteException e3) {
                                    i5 = i4;
                                }
                                if (providerInfo2 != null) {
                                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                                    return null;
                                }
                                if (this.mService.isSingleton(providerInfo2.processName, providerInfo2.applicationInfo, providerInfo2.name, providerInfo2.flags)) {
                                    if (this.mService.isValidSingletonCall(processRecord3 == null ? i : processRecord3.uid, providerInfo2.applicationInfo.uid)) {
                                        z6 = true;
                                        int i9 = !z6 ? 0 : i5;
                                        providerInfo2.applicationInfo = this.mService.getAppInfoForUser(providerInfo2.applicationInfo, i9);
                                        checkTime(j, "getContentProviderImpl: got app info for user");
                                        com.android.server.am.ProcessRecord processRecord9 = processRecord3;
                                        int i10 = i9;
                                        z7 = z5;
                                        com.android.server.am.ProcessRecord processRecord10 = processRecord4;
                                        checkAssociationAndPermissionLocked(processRecord3, providerInfo2, i, i9, z6, str, j);
                                        if (this.mService.mProcessesReady && !providerInfo2.processName.equals("system")) {
                                            throw new java.lang.IllegalArgumentException("Attempt to launch content provider before system ready");
                                        }
                                        synchronized (this) {
                                            try {
                                                if (!this.mSystemProvidersInstalled && providerInfo2.applicationInfo.isSystemApp() && "system".equals(providerInfo2.processName)) {
                                                    throw new java.lang.IllegalStateException("Cannot access system provider: '" + providerInfo2.authority + "' before system providers are installed!");
                                                }
                                            } finally {
                                            }
                                        }
                                        if (!this.mService.mUserController.isUserRunning(i10, 0)) {
                                            android.util.Slog.w(TAG, "Unable to launch app " + providerInfo2.applicationInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + providerInfo2.applicationInfo.uid + " for provider " + str4 + ": user " + i10 + " is stopped");
                                            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                                            return null;
                                        }
                                        android.content.ComponentName componentName2 = new android.content.ComponentName(providerInfo2.packageName, providerInfo2.name);
                                        checkTime(j, "getContentProviderImpl: before getProviderByClass");
                                        com.android.server.am.ContentProviderRecord providerByClass = this.mProviderMap.getProviderByClass(componentName2, i10);
                                        checkTime(j, "getContentProviderImpl: after getProviderByClass");
                                        boolean z12 = providerByClass == null || (processRecord10 == providerByClass.proc && processRecord10 != null);
                                        if (z12) {
                                            long clearCallingIdentity2 = android.os.Binder.clearCallingIdentity();
                                            processRecord5 = processRecord9;
                                            if (!requestTargetProviderPermissionsReviewIfNeededLocked(providerInfo2, processRecord5, i10, this.mService.mContext)) {
                                                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                                                return null;
                                            }
                                            try {
                                                try {
                                                    checkTime(j, "getContentProviderImpl: before getApplicationInfo");
                                                    applicationInfo = android.app.AppGlobals.getPackageManager().getApplicationInfo(providerInfo2.applicationInfo.packageName, 1024L, i10);
                                                    checkTime(j, "getContentProviderImpl: after getApplicationInfo");
                                                } catch (android.os.RemoteException e4) {
                                                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity2);
                                                }
                                                if (applicationInfo == null) {
                                                    android.util.Slog.w(TAG, "No package info for content provider " + providerInfo2.name);
                                                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity2);
                                                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                                                    return null;
                                                }
                                                com.android.server.am.ContentProviderRecord contentProviderRecord4 = new com.android.server.am.ContentProviderRecord(this.mService, providerInfo2, this.mService.getAppInfoForUser(applicationInfo, i10), componentName2, z6);
                                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity2);
                                                contentProviderRecord2 = contentProviderRecord4;
                                                checkTime(j, "getContentProviderImpl: now have ContentProviderRecord");
                                                if (processRecord5 == null && contentProviderRecord2.canRunHere(processRecord5)) {
                                                    android.app.ContentProviderHolder newHolder2 = contentProviderRecord2.newHolder(null, true);
                                                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                                                    return newHolder2;
                                                }
                                                size = this.mLaunchingProviders.size();
                                                i6 = 0;
                                                while (i6 < size && this.mLaunchingProviders.get(i6) != contentProviderRecord2) {
                                                    i6++;
                                                }
                                                if (i6 < size) {
                                                    long clearCallingIdentity3 = android.os.Binder.clearCallingIdentity();
                                                    try {
                                                        if (!android.text.TextUtils.equals(contentProviderRecord2.appInfo.packageName, str2)) {
                                                            this.mService.mUsageStatsService.reportEvent(contentProviderRecord2.appInfo.packageName, i10, 31);
                                                        }
                                                        try {
                                                            checkTime(j, "getContentProviderImpl: before set stopped state");
                                                            this.mService.mPackageManagerInt.notifyComponentUsed(contentProviderRecord2.appInfo.packageName, i10, str2, contentProviderRecord2.toString());
                                                            checkTime(j, "getContentProviderImpl: after set stopped state");
                                                        } catch (java.lang.IllegalArgumentException e5) {
                                                            android.util.Slog.w(TAG, "Failed trying to unstop package " + contentProviderRecord2.appInfo.packageName + ": " + e5);
                                                        }
                                                        checkTime(j, "getContentProviderImpl: looking for process record");
                                                        com.android.server.am.ProcessRecord processRecordLocked = this.mService.getProcessRecordLocked(providerInfo2.processName, contentProviderRecord2.appInfo.uid);
                                                        if (processRecordLocked == null || (thread = processRecordLocked.getThread()) == null) {
                                                            processRecord6 = processRecord5;
                                                            componentName = componentName2;
                                                            i7 = i10;
                                                            contentProviderRecord3 = contentProviderRecord2;
                                                        } else if (processRecordLocked.isKilled()) {
                                                            processRecord6 = processRecord5;
                                                            componentName = componentName2;
                                                            i7 = i10;
                                                            contentProviderRecord3 = contentProviderRecord2;
                                                        } else {
                                                            com.android.server.am.ProcessProviderRecord processProviderRecord = processRecordLocked.mProviders;
                                                            if (!processProviderRecord.hasProvider(providerInfo2.name)) {
                                                                checkTime(j, "getContentProviderImpl: scheduling install");
                                                                processProviderRecord.installProvider(providerInfo2.name, contentProviderRecord2);
                                                                try {
                                                                    thread.scheduleInstallProvider(providerInfo2);
                                                                } catch (android.os.RemoteException e6) {
                                                                }
                                                            }
                                                            processRecord6 = processRecord5;
                                                            componentName = componentName2;
                                                            i7 = i10;
                                                            contentProviderRecord3 = contentProviderRecord2;
                                                            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.PROVIDER_ACQUISITION_EVENT_REPORTED, processRecordLocked.uid, i, 1, 1, providerInfo2.packageName, str2, curProcState, processRecordLocked.mState.getCurProcState());
                                                            processRecord7 = processRecordLocked;
                                                            contentProviderRecord3.launchingApp = processRecord7;
                                                            this.mLaunchingProviders.add(contentProviderRecord3);
                                                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity3);
                                                        }
                                                        int i11 = (contentProviderRecord3.appInfo.flags & 2097152) != 0 ? 2 : 1;
                                                        checkTime(j, "getContentProviderImpl: before start process");
                                                        com.android.server.am.ProcessRecord startProcessLocked = this.mService.startProcessLocked(providerInfo2.processName, contentProviderRecord3.appInfo, false, 0, new com.android.server.am.HostingRecord(com.android.server.am.HostingRecord.HOSTING_TYPE_CONTENT_PROVIDER, new android.content.ComponentName(providerInfo2.applicationInfo.packageName, providerInfo2.name)), 0, false, false);
                                                        checkTime(j, "getContentProviderImpl: after start process");
                                                        if (startProcessLocked == null) {
                                                            android.util.Slog.w(TAG, "Unable to launch app " + providerInfo2.applicationInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + providerInfo2.applicationInfo.uid + " for provider " + str4 + ": process is bad");
                                                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity3);
                                                            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                                                            return null;
                                                        }
                                                        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.PROVIDER_ACQUISITION_EVENT_REPORTED, startProcessLocked.uid, i, 3, i11, providerInfo2.packageName, str2, curProcState, 20);
                                                        processRecord7 = startProcessLocked;
                                                        contentProviderRecord3.launchingApp = processRecord7;
                                                        this.mLaunchingProviders.add(contentProviderRecord3);
                                                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity3);
                                                    } catch (java.lang.Throwable th6) {
                                                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity3);
                                                        throw th6;
                                                    }
                                                } else {
                                                    processRecord6 = processRecord5;
                                                    componentName = componentName2;
                                                    i7 = i10;
                                                    contentProviderRecord3 = contentProviderRecord2;
                                                }
                                                checkTime(j, "getContentProviderImpl: updating data structures");
                                                if (z12) {
                                                    this.mProviderMap.putProviderByClass(componentName, contentProviderRecord3);
                                                }
                                                this.mProviderMap.putProviderByName(str4, contentProviderRecord3);
                                                com.android.server.am.ContentProviderRecord contentProviderRecord5 = contentProviderRecord3;
                                                j2 = j;
                                                contentProviderConnection = incProviderCountLocked(processRecord6, contentProviderRecord3, iBinder, i, str2, str3, z, false, j, this.mService.mProcessList, i2);
                                                if (contentProviderConnection == null) {
                                                    z8 = true;
                                                    contentProviderConnection.waiting = true;
                                                } else {
                                                    z8 = true;
                                                }
                                                providerInfo = providerInfo2;
                                                contentProviderRecord = contentProviderRecord5;
                                                i8 = i7;
                                            } catch (java.lang.Throwable th7) {
                                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity2);
                                                throw th7;
                                            }
                                        } else {
                                            processRecord5 = processRecord9;
                                        }
                                        contentProviderRecord2 = providerByClass;
                                        checkTime(j, "getContentProviderImpl: now have ContentProviderRecord");
                                        if (processRecord5 == null) {
                                        }
                                        size = this.mLaunchingProviders.size();
                                        i6 = 0;
                                        while (i6 < size) {
                                            i6++;
                                        }
                                        if (i6 < size) {
                                        }
                                        checkTime(j, "getContentProviderImpl: updating data structures");
                                        if (z12) {
                                        }
                                        this.mProviderMap.putProviderByName(str4, contentProviderRecord3);
                                        com.android.server.am.ContentProviderRecord contentProviderRecord52 = contentProviderRecord3;
                                        j2 = j;
                                        contentProviderConnection = incProviderCountLocked(processRecord6, contentProviderRecord3, iBinder, i, str2, str3, z, false, j, this.mService.mProcessList, i2);
                                        if (contentProviderConnection == null) {
                                        }
                                        providerInfo = providerInfo2;
                                        contentProviderRecord = contentProviderRecord52;
                                        i8 = i7;
                                    }
                                }
                                z6 = false;
                                if (!z6) {
                                }
                                providerInfo2.applicationInfo = this.mService.getAppInfoForUser(providerInfo2.applicationInfo, i9);
                                checkTime(j, "getContentProviderImpl: got app info for user");
                                com.android.server.am.ProcessRecord processRecord92 = processRecord3;
                                int i102 = i9;
                                z7 = z5;
                                com.android.server.am.ProcessRecord processRecord102 = processRecord4;
                                checkAssociationAndPermissionLocked(processRecord3, providerInfo2, i, i9, z6, str, j);
                                if (this.mService.mProcessesReady) {
                                }
                                synchronized (this) {
                                }
                            } else {
                                z7 = z5;
                                j2 = j;
                                z8 = true;
                                i8 = i4;
                            }
                            checkTime(j2, "getContentProviderImpl: done!");
                            this.mService.grantImplicitAccess(i8, null, i, android.os.UserHandle.getAppId(providerInfo.applicationInfo.uid));
                            if (iApplicationThread == null) {
                                synchronized (contentProviderRecord) {
                                    if (contentProviderRecord.provider == null) {
                                        if (contentProviderRecord.launchingApp == null) {
                                            android.util.Slog.w(TAG, "Unable to launch app " + providerInfo.applicationInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + providerInfo.applicationInfo.uid + " for provider " + str + ": launching app became null");
                                            com.android.server.am.EventLogTags.writeAmProviderLostProcess(android.os.UserHandle.getUserId(providerInfo.applicationInfo.uid), providerInfo.applicationInfo.packageName, providerInfo.applicationInfo.uid, str);
                                            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                                            return null;
                                        }
                                        if (contentProviderConnection != null) {
                                            contentProviderConnection.waiting = z8;
                                        }
                                        android.os.Message obtainMessage = this.mService.mHandler.obtainMessage(73);
                                        obtainMessage.obj = contentProviderRecord;
                                        this.mService.mHandler.sendMessageDelayed(obtainMessage, android.content.ContentResolver.CONTENT_PROVIDER_READY_TIMEOUT_MILLIS);
                                    }
                                    android.app.ContentProviderHolder newHolder3 = contentProviderRecord.newHolder(contentProviderConnection, false);
                                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                                    return newHolder3;
                                }
                            }
                            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                            long uptimeMillis2 = android.os.SystemClock.uptimeMillis() + android.content.ContentResolver.CONTENT_PROVIDER_READY_TIMEOUT_MILLIS;
                            synchronized (contentProviderRecord) {
                                while (true) {
                                    if (contentProviderRecord.provider != null) {
                                        z8 = false;
                                        break;
                                    }
                                    if (contentProviderRecord.launchingApp == null) {
                                        android.util.Slog.w(TAG, "Unable to launch app " + providerInfo.applicationInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + providerInfo.applicationInfo.uid + " for provider " + str + ": launching app became null");
                                        com.android.server.am.EventLogTags.writeAmProviderLostProcess(android.os.UserHandle.getUserId(providerInfo.applicationInfo.uid), providerInfo.applicationInfo.packageName, providerInfo.applicationInfo.uid, str);
                                        return null;
                                    }
                                    try {
                                        try {
                                            try {
                                                long max = java.lang.Math.max(0L, uptimeMillis2 - android.os.SystemClock.uptimeMillis());
                                                if (contentProviderConnection != null) {
                                                    contentProviderConnection.waiting = z8;
                                                }
                                                contentProviderRecord.wait(max);
                                            } catch (java.lang.InterruptedException e7) {
                                                if (contentProviderConnection != null) {
                                                    contentProviderConnection.waiting = false;
                                                }
                                            }
                                        } catch (java.lang.Throwable th8) {
                                            if (contentProviderConnection == null) {
                                                throw th8;
                                            }
                                            contentProviderConnection.waiting = false;
                                            throw th8;
                                        }
                                    } catch (java.lang.InterruptedException e8) {
                                    }
                                    if (contentProviderRecord.provider != null) {
                                        if (contentProviderConnection == null) {
                                        }
                                        contentProviderConnection.waiting = false;
                                    } else if (contentProviderConnection != null) {
                                        contentProviderConnection.waiting = false;
                                    }
                                }
                                if (!z8) {
                                    return contentProviderRecord.newHolder(contentProviderConnection, false);
                                }
                                str5 = "unknown";
                                if (iApplicationThread != null) {
                                    com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mService.mProcLock;
                                    com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                                    synchronized (activityManagerGlobalLock) {
                                        try {
                                            com.android.server.am.ProcessRecord lRURecordForAppLOSP = this.mService.mProcessList.getLRURecordForAppLOSP(iApplicationThread);
                                            str5 = lRURecordForAppLOSP != null ? lRURecordForAppLOSP.processName : "unknown";
                                        } finally {
                                            com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                                        }
                                    }
                                    com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                                }
                                android.util.Slog.wtf(TAG, "Timeout waiting for provider " + providerInfo.applicationInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + providerInfo.applicationInfo.uid + " for provider " + str + " providerRunning=" + z7 + " caller=" + str5 + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + android.os.Binder.getCallingUid());
                                return null;
                            }
                        }
                        z4 = z11;
                    }
                    processRecord2 = null;
                    int curProcState3 = processRecord == null ? processRecord.mState.getCurProcState() : -1;
                    if (z4) {
                    }
                    if (z5) {
                    }
                    checkTime(j2, "getContentProviderImpl: done!");
                    this.mService.grantImplicitAccess(i8, null, i, android.os.UserHandle.getAppId(providerInfo.applicationInfo.uid));
                    if (iApplicationThread == null) {
                    }
                } catch (java.lang.Throwable th9) {
                    th = th9;
                    activityManagerService = activityManagerService2;
                    th = th;
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            } catch (java.lang.Throwable th10) {
                th = th10;
                th = th;
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    private void checkAssociationAndPermissionLocked(com.android.server.am.ProcessRecord processRecord, android.content.pm.ProviderInfo providerInfo, int i, int i2, boolean z, java.lang.String str, long j) {
        java.lang.String checkContentProviderAssociation = checkContentProviderAssociation(processRecord, i, providerInfo);
        if (checkContentProviderAssociation != null) {
            throw new java.lang.SecurityException("Content provider lookup " + str + " failed: association not allowed with package " + checkContentProviderAssociation);
        }
        checkTime(j, "getContentProviderImpl: before checkContentProviderPermission");
        java.lang.String checkContentProviderPermission = checkContentProviderPermission(providerInfo, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i2, z, processRecord != null ? processRecord.toString() : null);
        if (checkContentProviderPermission != null) {
            throw new java.lang.SecurityException(checkContentProviderPermission);
        }
        checkTime(j, "getContentProviderImpl: after checkContentProviderPermission");
    }

    void publishContentProviders(android.app.IApplicationThread iApplicationThread, java.util.List<android.app.ContentProviderHolder> list) {
        com.android.server.am.ContentProviderRecord provider;
        if (list == null) {
            return;
        }
        this.mService.enforceNotIsolatedOrSdkSandboxCaller("publishContentProviders");
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                com.android.server.am.ProcessRecord recordForAppLOSP = this.mService.getRecordForAppLOSP(iApplicationThread);
                if (recordForAppLOSP == null) {
                    throw new java.lang.SecurityException("Unable to find app for caller " + iApplicationThread + " (pid=" + android.os.Binder.getCallingPid() + ") when publishing content providers");
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                int size = list.size();
                boolean z = false;
                for (int i = 0; i < size; i++) {
                    android.app.ContentProviderHolder contentProviderHolder = list.get(i);
                    if (contentProviderHolder != null && contentProviderHolder.info != null && contentProviderHolder.provider != null && (provider = recordForAppLOSP.mProviders.getProvider(contentProviderHolder.info.name)) != null) {
                        this.mProviderMap.putProviderByClass(new android.content.ComponentName(provider.info.packageName, provider.info.name), provider);
                        for (java.lang.String str : provider.info.authority.split(";")) {
                            this.mProviderMap.putProviderByName(str, provider);
                        }
                        int size2 = this.mLaunchingProviders.size();
                        int i2 = 0;
                        boolean z2 = false;
                        while (i2 < size2) {
                            if (this.mLaunchingProviders.get(i2) == provider) {
                                this.mLaunchingProviders.remove(i2);
                                i2--;
                                size2--;
                                z2 = true;
                            }
                            i2++;
                        }
                        if (z2) {
                            this.mService.mHandler.removeMessages(73, provider);
                            this.mService.mHandler.removeMessages(57, recordForAppLOSP);
                        }
                        recordForAppLOSP.addPackage(provider.info.applicationInfo.packageName, provider.info.applicationInfo.longVersionCode, this.mService.mProcessStats);
                        synchronized (provider) {
                            provider.provider = contentProviderHolder.provider;
                            provider.setProcess(recordForAppLOSP);
                            provider.notifyAll();
                            provider.onProviderPublishStatusLocked(true);
                        }
                        provider.mRestartCount = 0;
                        if (hasProviderConnectionLocked(recordForAppLOSP)) {
                            recordForAppLOSP.mProfile.addHostingComponentType(64);
                        }
                        z = true;
                    }
                }
                if (z) {
                    this.mService.updateOomAdjLocked(recordForAppLOSP, 7);
                    int size3 = list.size();
                    for (int i3 = 0; i3 < size3; i3++) {
                        android.app.ContentProviderHolder contentProviderHolder2 = list.get(i3);
                        if (contentProviderHolder2 != null && contentProviderHolder2.info != null && contentProviderHolder2.provider != null) {
                            maybeUpdateProviderUsageStatsLocked(recordForAppLOSP, contentProviderHolder2.info.packageName, contentProviderHolder2.info.authority);
                        }
                    }
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    void removeContentProvider(android.os.IBinder iBinder, boolean z) {
        this.mService.enforceNotIsolatedCaller("removeContentProvider");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                com.android.server.am.ContentProviderConnection contentProviderConnection = (com.android.server.am.ContentProviderConnection) iBinder;
                if (contentProviderConnection == null) {
                    throw new java.lang.NullPointerException("connection is null");
                }
                com.android.server.am.ActivityManagerService.traceBegin(64L, "removeContentProvider: ", (contentProviderConnection.provider == null || contentProviderConnection.provider.info == null) ? "" : contentProviderConnection.provider.info.authority);
                try {
                    com.android.server.am.ActivityManagerService activityManagerService = this.mService;
                    com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService) {
                        try {
                            decProviderCountLocked(contentProviderConnection, null, null, z, true, true);
                        } catch (java.lang.Throwable th) {
                            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                } finally {
                    android.os.Trace.traceEnd(64L);
                }
            } catch (java.lang.ClassCastException e) {
                java.lang.String str = "removeContentProvider: " + iBinder + " not a ContentProviderConnection";
                android.util.Slog.w(TAG, str);
                throw new java.lang.IllegalArgumentException(str);
            }
        } catch (java.lang.Throwable th2) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    void removeContentProviderExternalAsUser(java.lang.String str, android.os.IBinder iBinder, int i) {
        this.mService.enforceCallingPermission("android.permission.ACCESS_CONTENT_PROVIDERS_EXTERNALLY", "Do not have permission in call removeContentProviderExternal()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            removeContentProviderExternalUnchecked(str, iBinder, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void removeContentProviderExternalUnchecked(java.lang.String str, android.os.IBinder iBinder, int i) {
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                com.android.server.am.ContentProviderRecord providerByName = this.mProviderMap.getProviderByName(str, i);
                if (providerByName == null) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                com.android.server.am.ContentProviderRecord providerByClass = this.mProviderMap.getProviderByClass(new android.content.ComponentName(providerByName.info.packageName, providerByName.info.name), i);
                if (providerByClass.hasExternalProcessHandles()) {
                    if (providerByClass.removeExternalProcessHandleLocked(iBinder)) {
                        this.mService.updateOomAdjLocked(providerByClass.proc, 8);
                    } else {
                        android.util.Slog.e(TAG, "Attempt to remove content provider " + providerByClass + " with no external reference for token: " + iBinder + ".");
                    }
                } else {
                    android.util.Slog.e(TAG, "Attempt to remove content provider: " + providerByClass + " with no external references.");
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    boolean refContentProvider(android.os.IBinder iBinder, int i, int i2) {
        try {
            com.android.server.am.ContentProviderConnection contentProviderConnection = (com.android.server.am.ContentProviderConnection) iBinder;
            if (contentProviderConnection == null) {
                throw new java.lang.NullPointerException("connection is null");
            }
            com.android.server.am.ActivityManagerService.traceBegin(64L, "refContentProvider: ", (contentProviderConnection.provider == null || contentProviderConnection.provider.info == null) ? "" : contentProviderConnection.provider.info.authority);
            try {
                contentProviderConnection.adjustCounts(i, i2);
                return !contentProviderConnection.dead;
            } finally {
                android.os.Trace.traceEnd(64L);
            }
        } catch (java.lang.ClassCastException e) {
            java.lang.String str = "refContentProvider: " + iBinder + " not a ContentProviderConnection";
            android.util.Slog.w(TAG, str);
            throw new java.lang.IllegalArgumentException(str);
        }
    }

    /* JADX WARN: Finally extract failed */
    void unstableProviderDied(android.os.IBinder iBinder) {
        android.content.IContentProvider iContentProvider;
        try {
            com.android.server.am.ContentProviderConnection contentProviderConnection = (com.android.server.am.ContentProviderConnection) iBinder;
            if (contentProviderConnection == null) {
                throw new java.lang.NullPointerException("connection is null");
            }
            com.android.server.am.ActivityManagerService.traceBegin(64L, "unstableProviderDied: ", (contentProviderConnection.provider == null || contentProviderConnection.provider.info == null) ? "" : contentProviderConnection.provider.info.authority);
            try {
                com.android.server.am.ActivityManagerService activityManagerService = this.mService;
                com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService) {
                    try {
                        iContentProvider = contentProviderConnection.provider.provider;
                    } finally {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                android.os.Trace.traceEnd(64L);
                throw th;
            }
            if (iContentProvider == null) {
                android.os.Trace.traceEnd(64L);
                return;
            }
            if (iContentProvider.asBinder().pingBinder()) {
                com.android.server.am.ActivityManagerService activityManagerService2 = this.mService;
                com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService2) {
                    try {
                        android.util.Slog.w(TAG, "unstableProviderDied: caller " + android.os.Binder.getCallingUid() + " says " + contentProviderConnection + " died, but we don't agree");
                    } catch (java.lang.Throwable th2) {
                        throw th2;
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                android.os.Trace.traceEnd(64L);
                return;
            }
            com.android.server.am.ActivityManagerService activityManagerService3 = this.mService;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService3) {
                try {
                    if (contentProviderConnection.provider.provider != iContentProvider) {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                        android.os.Trace.traceEnd(64L);
                        return;
                    }
                    com.android.server.am.ProcessRecord processRecord = contentProviderConnection.provider.proc;
                    if (processRecord == null || processRecord.getThread() == null) {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                        android.os.Trace.traceEnd(64L);
                        return;
                    }
                    this.mService.reportUidInfoMessageLocked(TAG, "Process " + processRecord.processName + " (pid " + processRecord.getPid() + ") early provider death", processRecord.info.uid);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        this.mService.appDiedLocked(processRecord, "unstable content provider");
                        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                        android.os.Trace.traceEnd(64L);
                        return;
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } finally {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                }
            }
            android.os.Trace.traceEnd(64L);
            throw th;
        } catch (java.lang.ClassCastException e) {
            java.lang.String str = "refContentProvider: " + iBinder + " not a ContentProviderConnection";
            android.util.Slog.w(TAG, str);
            throw new java.lang.IllegalArgumentException(str);
        }
    }

    void appNotRespondingViaProvider(android.os.IBinder iBinder) {
        this.mService.enforceCallingPermission("android.permission.REMOVE_TASKS", "appNotRespondingViaProvider()");
        com.android.server.am.ContentProviderConnection contentProviderConnection = (com.android.server.am.ContentProviderConnection) iBinder;
        if (contentProviderConnection == null) {
            android.util.Slog.w(TAG, "ContentProviderConnection is null");
            return;
        }
        com.android.server.am.ActivityManagerService.traceBegin(64L, "appNotRespondingViaProvider: ", (contentProviderConnection.provider == null || contentProviderConnection.provider.info == null) ? "" : contentProviderConnection.provider.info.authority);
        try {
            com.android.server.am.ProcessRecord processRecord = contentProviderConnection.provider.proc;
            if (processRecord == null) {
                android.util.Slog.w(TAG, "Failed to find hosting ProcessRecord");
            } else {
                this.mService.mAnrHelper.appNotResponding(processRecord, com.android.internal.os.TimeoutRecord.forContentProvider("ContentProvider not responding"));
            }
        } finally {
            android.os.Trace.traceEnd(64L);
        }
    }

    void getMimeTypeFilterAsync(final android.net.Uri uri, int i, final android.os.RemoteCallback remoteCallback) {
        this.mService.enforceNotIsolatedCaller("getProviderMimeTypeAsync");
        final java.lang.String authority = uri.getAuthority();
        final int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        final int unsafeConvertIncomingUser = this.mService.mUserController.unsafeConvertIncomingUser(i);
        long clearCallingIdentity = canClearIdentity(callingPid, callingUid, unsafeConvertIncomingUser) ? android.os.Binder.clearCallingIdentity() : 0L;
        try {
            android.app.ContentProviderHolder contentProviderExternalUnchecked = getContentProviderExternalUnchecked(authority, null, callingUid, "*getmimetype*", unsafeConvertIncomingUser);
            try {
                if (isHolderVisibleToCaller(contentProviderExternalUnchecked, callingUid, unsafeConvertIncomingUser)) {
                    if (checkGetAnyTypePermission(callingUid, callingPid)) {
                        contentProviderExternalUnchecked.provider.getTypeAsync(new android.content.AttributionSource.Builder(callingUid).build(), uri, new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: com.android.server.am.ContentProviderHelper$$ExternalSyntheticLambda0
                            public final void onResult(android.os.Bundle bundle) {
                                com.android.server.am.ContentProviderHelper.this.lambda$getMimeTypeFilterAsync$0(authority, unsafeConvertIncomingUser, remoteCallback, bundle);
                            }
                        }));
                        return;
                    } else {
                        contentProviderExternalUnchecked.provider.getTypeAnonymousAsync(uri, new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: com.android.server.am.ContentProviderHelper$$ExternalSyntheticLambda1
                            public final void onResult(android.os.Bundle bundle) {
                                com.android.server.am.ContentProviderHelper.this.lambda$getMimeTypeFilterAsync$1(authority, unsafeConvertIncomingUser, remoteCallback, callingUid, uri, bundle);
                            }
                        }));
                        return;
                    }
                }
                remoteCallback.sendResult(android.os.Bundle.EMPTY);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Content provider dead retrieving " + uri, e);
                remoteCallback.sendResult(android.os.Bundle.EMPTY);
            }
        } finally {
            if (clearCallingIdentity != 0) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getMimeTypeFilterAsync$0(java.lang.String str, int i, android.os.RemoteCallback remoteCallback, android.os.Bundle bundle) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            removeContentProviderExternalUnchecked(str, null, i);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            remoteCallback.sendResult(bundle);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getMimeTypeFilterAsync$1(java.lang.String str, int i, android.os.RemoteCallback remoteCallback, int i2, android.net.Uri uri, android.os.Bundle bundle) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            removeContentProviderExternalUnchecked(str, null, i);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            remoteCallback.sendResult(bundle);
            java.lang.String pairValue = bundle.getPairValue();
            if (pairValue != null) {
                logGetTypeData(i2, uri, pairValue);
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private boolean checkGetAnyTypePermission(int i, int i2) {
        if (this.mService.checkPermission("android.permission.GET_ANY_PROVIDER_TYPE", i2, i) == 0) {
            return true;
        }
        return false;
    }

    private void logGetTypeData(int i, android.net.Uri uri, java.lang.String str) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.GET_TYPE_ACCESSED_WITHOUT_PERMISSION, 1, i, uri.getAuthority(), str);
    }

    private boolean canClearIdentity(int i, int i2, int i3) {
        return android.os.UserHandle.getUserId(i2) == i3 || com.android.server.am.ActivityManagerService.checkComponentPermission("android.permission.INTERACT_ACROSS_USERS", i, i2, -1, true) == 0 || com.android.server.am.ActivityManagerService.checkComponentPermission("android.permission.INTERACT_ACROSS_USERS_FULL", i, i2, -1, true) == 0;
    }

    private boolean isHolderVisibleToCaller(@android.annotation.Nullable android.app.ContentProviderHolder contentProviderHolder, int i, int i2) {
        if (contentProviderHolder == null || contentProviderHolder.info == null) {
            return false;
        }
        if (isAuthorityRedirectedForCloneProfileCached(contentProviderHolder.info.authority) && resolveParentUserIdForCloneProfile(i2) != i2) {
            return !this.mService.getPackageManagerInternal().filterAppAccess(contentProviderHolder.info.packageName, i, i2, false);
        }
        return !this.mService.getPackageManagerInternal().filterAppAccess(contentProviderHolder.info.packageName, i, i2);
    }

    private static int resolveParentUserIdForCloneProfile(int i) {
        com.android.server.pm.UserManagerInternal userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        android.content.pm.UserInfo userInfo = userManagerInternal.getUserInfo(i);
        if (userInfo == null || !userInfo.isCloneProfile()) {
            return i;
        }
        return userManagerInternal.getProfileParentId(i);
    }

    java.lang.String checkContentProviderAccess(java.lang.String str, int i) {
        int i2;
        boolean z;
        android.content.pm.ProviderInfo providerInfo;
        com.android.server.pm.UserManagerInternal userManagerInternal;
        android.content.pm.UserInfo userInfo;
        if (i == -1) {
            this.mService.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", TAG);
            i = android.os.UserHandle.getCallingUserId();
        }
        if (isAuthorityRedirectedForCloneProfileCached(str) && (userInfo = (userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getUserInfo(i)) != null && userInfo.isCloneProfile()) {
            i2 = userManagerInternal.getProfileParentId(i);
            z = false;
        } else {
            i2 = i;
            z = true;
        }
        try {
            providerInfo = android.app.AppGlobals.getPackageManager().resolveContentProvider(str, 790016L, i2);
        } catch (android.os.RemoteException e) {
            providerInfo = null;
        }
        if (providerInfo == null) {
            return "Failed to find provider " + str + " for user " + i2 + "; expected to find a valid ContentProvider for this authority";
        }
        int callingPid = android.os.Binder.getCallingPid();
        synchronized (this.mService.mPidsSelfLocked) {
            try {
                com.android.server.am.ProcessRecord processRecord = this.mService.mPidsSelfLocked.get(callingPid);
                if (processRecord == null) {
                    return "Failed to find PID " + callingPid;
                }
                return checkContentProviderPermission(providerInfo, callingPid, android.os.Binder.getCallingUid(), i2, z, processRecord.toString());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00f0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v3, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    int checkContentProviderUriPermission(android.net.Uri uri, int i, int i2, int i3) {
        android.app.ContentProviderHolder contentProviderHolder;
        ?? holdsLock = java.lang.Thread.holdsLock(this.mService.mActivityTaskManager.getGlobalLock());
        if (holdsLock != 0) {
            android.util.Slog.wtf(TAG, new java.lang.IllegalStateException("Unable to check Uri permission because caller is holding WM lock; assuming permission denied"));
            return -1;
        }
        java.lang.String authority = uri.getAuthority();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                contentProviderHolder = getContentProviderExternalUnchecked(authority, null, i2, "*checkContentProviderUriPermission*", i);
                if (contentProviderHolder == null) {
                    if (contentProviderHolder != null) {
                        try {
                            removeContentProviderExternalUnchecked(authority, null, i);
                        } finally {
                        }
                    }
                    return -1;
                }
                try {
                    com.android.server.pm.pkg.AndroidPackage androidPackage = this.mService.getPackageManagerInternal().getPackage(android.os.Binder.getCallingUid());
                    if (androidPackage == null) {
                        try {
                            removeContentProviderExternalUnchecked(authority, null, i);
                            return -1;
                        } finally {
                        }
                    }
                    int checkUriPermission = contentProviderHolder.provider.checkUriPermission(new android.content.AttributionSource(i2, androidPackage.getPackageName(), null), uri, i2, i3);
                    try {
                        removeContentProviderExternalUnchecked(authority, null, i);
                        return checkUriPermission;
                    } finally {
                    }
                } catch (android.os.RemoteException e) {
                    e = e;
                    android.util.Log.w(TAG, "Content provider dead retrieving " + uri, e);
                    if (contentProviderHolder != null) {
                        try {
                            removeContentProviderExternalUnchecked(authority, null, i);
                        } finally {
                        }
                    }
                    return -1;
                } catch (java.lang.Exception e2) {
                    e = e2;
                    android.util.Log.w(TAG, "Exception while determining type of " + uri, e);
                    if (contentProviderHolder != null) {
                        try {
                            removeContentProviderExternalUnchecked(authority, null, i);
                        } finally {
                        }
                    }
                    return -1;
                }
            } catch (android.os.RemoteException e3) {
                e = e3;
                contentProviderHolder = null;
            } catch (java.lang.Exception e4) {
                e = e4;
                contentProviderHolder = null;
            } catch (java.lang.Throwable th) {
                th = th;
                holdsLock = 0;
                if (holdsLock != 0) {
                }
                throw th;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            if (holdsLock != 0) {
                try {
                    removeContentProviderExternalUnchecked(authority, null, i);
                } finally {
                }
            }
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void processContentProviderPublishTimedOutLocked(com.android.server.am.ProcessRecord processRecord) {
        cleanupAppInLaunchingProvidersLocked(processRecord, true);
        this.mService.mProcessList.removeProcessLocked(processRecord, false, true, 7, 0, "timeout publishing content providers");
    }

    java.util.List<android.content.pm.ProviderInfo> generateApplicationProvidersLocked(com.android.server.am.ProcessRecord processRecord) {
        try {
            java.util.List<android.content.pm.ProviderInfo> list = android.app.AppGlobals.getPackageManager().queryContentProviders(processRecord.processName, processRecord.uid, 268438528L, (java.lang.String) null).getList();
            if (list == null) {
                return null;
            }
            int size = list.size();
            com.android.server.am.ProcessProviderRecord processProviderRecord = processRecord.mProviders;
            processProviderRecord.ensureProviderCapacity(processProviderRecord.numberOfProviders() + size);
            int i = 0;
            while (i < size) {
                android.content.pm.ProviderInfo providerInfo = list.get(i);
                boolean isSingleton = this.mService.isSingleton(providerInfo.processName, providerInfo.applicationInfo, providerInfo.name, providerInfo.flags);
                if (isSingletonOrSystemUserOnly(providerInfo) && processRecord.userId != 0) {
                    list.remove(i);
                    size--;
                    i--;
                } else {
                    boolean isInstantApp = providerInfo.applicationInfo.isInstantApp();
                    boolean z = providerInfo.splitName == null || com.android.internal.util.ArrayUtils.contains(providerInfo.applicationInfo.splitNames, providerInfo.splitName);
                    if (isInstantApp && !z) {
                        list.remove(i);
                        size--;
                        i--;
                    } else {
                        android.content.ComponentName componentName = new android.content.ComponentName(providerInfo.packageName, providerInfo.name);
                        com.android.server.am.ContentProviderRecord providerByClass = this.mProviderMap.getProviderByClass(componentName, processRecord.userId);
                        if (providerByClass == null) {
                            com.android.server.am.ContentProviderRecord contentProviderRecord = new com.android.server.am.ContentProviderRecord(this.mService, providerInfo, processRecord.info, componentName, isSingleton);
                            this.mProviderMap.putProviderByClass(componentName, contentProviderRecord);
                            providerByClass = contentProviderRecord;
                        }
                        processProviderRecord.installProvider(providerInfo.name, providerByClass);
                        if (!providerInfo.multiprocess || !com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(providerInfo.packageName)) {
                            processRecord.addPackage(providerInfo.applicationInfo.packageName, providerInfo.applicationInfo.longVersionCode, this.mService.mProcessStats);
                        }
                        this.mService.notifyPackageUse(providerInfo.applicationInfo.packageName, 4);
                    }
                }
                i++;
            }
            if (list.isEmpty()) {
                return null;
            }
            return list;
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    private final class DevelopmentSettingsObserver extends android.database.ContentObserver {
        private final android.content.ComponentName mBugreportStorageProvider;
        private final android.net.Uri mUri;

        DevelopmentSettingsObserver() {
            super(com.android.server.am.ContentProviderHelper.this.mService.mHandler);
            this.mUri = android.provider.Settings.Global.getUriFor("development_settings_enabled");
            this.mBugreportStorageProvider = new android.content.ComponentName(com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.SHELL_PACKAGE_NAME, "com.android.shell.BugreportStorageProvider");
            com.android.server.am.ContentProviderHelper.this.mService.mContext.getContentResolver().registerContentObserver(this.mUri, false, this, -1);
            onChange();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri, int i) {
            if (this.mUri.equals(uri)) {
                onChange();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        private void onChange() {
            com.android.server.am.ContentProviderHelper.this.mService.mContext.getPackageManager().setComponentEnabledSetting(this.mBugreportStorageProvider, (android.provider.Settings.Global.getInt(com.android.server.am.ContentProviderHelper.this.mService.mContext.getContentResolver(), "development_settings_enabled", android.os.Build.IS_ENG ? 1 : 0) != 0) == true ? 1 : 0, 0);
        }
    }

    public final void installSystemProviders() {
        java.util.List<android.content.pm.ProviderInfo> generateApplicationProvidersLocked;
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                generateApplicationProvidersLocked = generateApplicationProvidersLocked((com.android.server.am.ProcessRecord) this.mService.mProcessList.getProcessNamesLOSP().get("system", 1000));
                if (generateApplicationProvidersLocked != null) {
                    for (int size = generateApplicationProvidersLocked.size() - 1; size >= 0; size--) {
                        android.content.pm.ProviderInfo providerInfo = generateApplicationProvidersLocked.get(size);
                        if ((providerInfo.applicationInfo.flags & 1) == 0) {
                            android.util.Slog.w(TAG, "Not installing system proc provider " + providerInfo.name + ": not system .apk");
                            generateApplicationProvidersLocked.remove(size);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        if (generateApplicationProvidersLocked != null) {
            this.mService.mSystemThread.installSystemProviders(generateApplicationProvidersLocked);
        }
        synchronized (this) {
            this.mSystemProvidersInstalled = true;
        }
        this.mService.mConstants.start(this.mService.mContext.getContentResolver());
        this.mService.mCoreSettingsObserver = new com.android.server.am.CoreSettingsObserver(this.mService);
        this.mService.mActivityTaskManager.installSystemProviders();
        new com.android.server.am.ContentProviderHelper.DevelopmentSettingsObserver();
        com.android.server.am.SettingsToPropertiesMapper.start(this.mService.mContext.getContentResolver());
        this.mService.mOomAdjuster.initSettings();
        com.android.server.RescueParty.onSettingsProviderPublished(this.mService.mContext);
    }

    void installEncryptionUnawareProviders(int i) {
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mService.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                android.util.ArrayMap map = this.mService.mProcessList.getProcessNamesLOSP().getMap();
                int size = map.size();
                for (int i2 = 0; i2 < size; i2++) {
                    android.util.SparseArray sparseArray = (android.util.SparseArray) map.valueAt(i2);
                    int size2 = sparseArray.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        final com.android.server.am.ProcessRecord processRecord = (com.android.server.am.ProcessRecord) sparseArray.valueAt(i3);
                        if (processRecord.userId == i && processRecord.getThread() != null && !processRecord.isUnlocked()) {
                            processRecord.getPkgList().forEachPackage(new java.util.function.Consumer() { // from class: com.android.server.am.ContentProviderHelper$$ExternalSyntheticLambda4
                                @Override // java.util.function.Consumer
                                public final void accept(java.lang.Object obj) {
                                    com.android.server.am.ContentProviderHelper.this.lambda$installEncryptionUnawareProviders$2(processRecord, (java.lang.String) obj);
                                }
                            });
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$installEncryptionUnawareProviders$2(com.android.server.am.ProcessRecord processRecord, java.lang.String str) {
        try {
            android.content.pm.PackageInfo packageInfo = android.app.AppGlobals.getPackageManager().getPackageInfo(str, 262152L, processRecord.userId);
            android.app.IApplicationThread thread = processRecord.getThread();
            if (packageInfo != null && !com.android.internal.util.ArrayUtils.isEmpty(packageInfo.providers)) {
                for (android.content.pm.ProviderInfo providerInfo : packageInfo.providers) {
                    boolean z = true;
                    boolean z2 = java.util.Objects.equals(providerInfo.processName, processRecord.processName) || providerInfo.multiprocess;
                    boolean z3 = !isSingletonOrSystemUserOnly(providerInfo) || processRecord.userId == 0;
                    boolean isInstantApp = providerInfo.applicationInfo.isInstantApp();
                    if (providerInfo.splitName != null && !com.android.internal.util.ArrayUtils.contains(providerInfo.applicationInfo.splitNames, providerInfo.splitName)) {
                        z = false;
                    }
                    if (z2 && z3 && (!isInstantApp || z)) {
                        android.util.Log.v(TAG, "Installing " + providerInfo);
                        thread.scheduleInstallProvider(providerInfo);
                    } else {
                        android.util.Log.v(TAG, "Skipping " + providerInfo);
                    }
                }
            }
        } catch (android.os.RemoteException e) {
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private com.android.server.am.ContentProviderConnection incProviderCountLocked(com.android.server.am.ProcessRecord processRecord, com.android.server.am.ContentProviderRecord contentProviderRecord, android.os.IBinder iBinder, int i, java.lang.String str, java.lang.String str2, boolean z, boolean z2, long j, com.android.server.am.ProcessList processList, int i2) {
        if (processRecord == null) {
            contentProviderRecord.addExternalProcessHandleLocked(iBinder, i, str2);
            return null;
        }
        com.android.server.am.ProcessProviderRecord processProviderRecord = processRecord.mProviders;
        int numberOfProviderConnections = processProviderRecord.numberOfProviderConnections();
        for (int i3 = 0; i3 < numberOfProviderConnections; i3++) {
            com.android.server.am.ContentProviderConnection providerConnectionAt = processProviderRecord.getProviderConnectionAt(i3);
            if (providerConnectionAt.provider == contentProviderRecord) {
                providerConnectionAt.incrementCount(z);
                return providerConnectionAt;
            }
        }
        com.android.server.am.ContentProviderConnection contentProviderConnection = new com.android.server.am.ContentProviderConnection(contentProviderRecord, processRecord, str, i2);
        contentProviderConnection.startAssociationIfNeeded();
        contentProviderConnection.initializeCount(z);
        contentProviderRecord.connections.add(contentProviderConnection);
        if (contentProviderRecord.proc != null) {
            contentProviderRecord.proc.mProfile.addHostingComponentType(64);
        }
        processProviderRecord.addProviderConnection(contentProviderConnection);
        this.mService.startAssociationLocked(processRecord.uid, processRecord.processName, processRecord.mState.getCurProcState(), contentProviderRecord.uid, contentProviderRecord.appInfo.longVersionCode, contentProviderRecord.name, contentProviderRecord.info.processName);
        if (z2 && contentProviderRecord.proc != null && processRecord.mState.getSetAdj() <= 250) {
            checkTime(j, "getContentProviderImpl: before updateLruProcess");
            processList.updateLruProcessLocked(contentProviderRecord.proc, false, null);
            checkTime(j, "getContentProviderImpl: after updateLruProcess");
        }
        return contentProviderConnection;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean decProviderCountLocked(final com.android.server.am.ContentProviderConnection contentProviderConnection, com.android.server.am.ContentProviderRecord contentProviderRecord, android.os.IBinder iBinder, final boolean z, boolean z2, final boolean z3) {
        if (contentProviderConnection == null) {
            contentProviderRecord.removeExternalProcessHandleLocked(iBinder);
            return false;
        }
        if (contentProviderConnection.totalRefCount() > 1) {
            contentProviderConnection.decrementCount(z);
            return false;
        }
        if (z2) {
            com.android.internal.os.BackgroundThread.getHandler().postDelayed(new java.lang.Runnable() { // from class: com.android.server.am.ContentProviderHelper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.ContentProviderHelper.this.lambda$decProviderCountLocked$3(contentProviderConnection, z, z3);
                }
            }, 5000L);
        } else {
            lambda$decProviderCountLocked$3(contentProviderConnection, z, z3);
        }
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean hasProviderConnectionLocked(com.android.server.am.ProcessRecord processRecord) {
        for (int numberOfProviders = processRecord.mProviders.numberOfProviders() - 1; numberOfProviders >= 0; numberOfProviders--) {
            if (!processRecord.mProviders.getProviderAt(numberOfProviders).connections.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleProviderRemoval, reason: merged with bridge method [inline-methods] */
    public void lambda$decProviderCountLocked$3(com.android.server.am.ContentProviderConnection contentProviderConnection, boolean z, boolean z2) {
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            if (contentProviderConnection != null) {
                try {
                    if (contentProviderConnection.provider != null && contentProviderConnection.decrementCount(z) == 0) {
                        com.android.server.am.ContentProviderRecord contentProviderRecord = contentProviderConnection.provider;
                        contentProviderConnection.stopAssociation();
                        contentProviderRecord.connections.remove(contentProviderConnection);
                        if (contentProviderRecord.proc != null && !hasProviderConnectionLocked(contentProviderRecord.proc)) {
                            contentProviderRecord.proc.mProfile.clearHostingComponentType(64);
                        }
                        contentProviderConnection.client.mProviders.removeProviderConnection(contentProviderConnection);
                        if (contentProviderConnection.client.mState.getSetProcState() < 15 && contentProviderRecord.proc != null) {
                            contentProviderRecord.proc.mProviders.setLastProviderTime(android.os.SystemClock.uptimeMillis());
                        }
                        this.mService.stopAssociationLocked(contentProviderConnection.client.uid, contentProviderConnection.client.processName, contentProviderRecord.uid, contentProviderRecord.appInfo.longVersionCode, contentProviderRecord.name, contentProviderRecord.info.processName);
                        if (z2) {
                            com.android.server.am.Flags.serviceBindingOomAdjPolicy();
                            this.mService.updateOomAdjLocked(contentProviderConnection.provider.proc, 8);
                        }
                        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        }
    }

    private java.lang.String checkContentProviderPermission(android.content.pm.ProviderInfo providerInfo, int i, int i2, int i3, boolean z, java.lang.String str) {
        int i4;
        java.lang.String str2;
        boolean z2;
        if (!canAccessContentProviderFromSdkSandbox(providerInfo, i2)) {
            return "ContentProvider access not allowed from sdk sandbox UID. ProviderInfo: " + providerInfo.toString();
        }
        boolean z3 = false;
        if (!z) {
            i4 = i3;
        } else {
            int unsafeConvertIncomingUser = this.mService.mUserController.unsafeConvertIncomingUser(i3);
            if (unsafeConvertIncomingUser == android.os.UserHandle.getUserId(i2)) {
                z2 = false;
            } else {
                if (this.mService.mUgmInternal.checkAuthorityGrants(i2, providerInfo, unsafeConvertIncomingUser, z)) {
                    return null;
                }
                z2 = true;
            }
            i4 = this.mService.mUserController.handleIncomingUser(i, i2, i3, false, 0, "checkContentProviderPermissionLocked " + providerInfo.authority, null);
            if (i4 == unsafeConvertIncomingUser) {
                z3 = z2;
            }
        }
        if (com.android.server.am.ActivityManagerService.checkComponentPermission(providerInfo.readPermission, i, i2, providerInfo.applicationInfo.uid, providerInfo.exported) == 0 || com.android.server.am.ActivityManagerService.checkComponentPermission(providerInfo.writePermission, i, i2, providerInfo.applicationInfo.uid, providerInfo.exported) == 0) {
            return null;
        }
        android.content.pm.PathPermission[] pathPermissionArr = providerInfo.pathPermissions;
        if (pathPermissionArr != null) {
            int length = pathPermissionArr.length;
            while (length > 0) {
                length--;
                android.content.pm.PathPermission pathPermission = pathPermissionArr[length];
                java.lang.String readPermission = pathPermission.getReadPermission();
                if (readPermission != null && com.android.server.am.ActivityManagerService.checkComponentPermission(readPermission, i, i2, providerInfo.applicationInfo.uid, providerInfo.exported) == 0) {
                    return null;
                }
                java.lang.String writePermission = pathPermission.getWritePermission();
                if (writePermission != null && com.android.server.am.ActivityManagerService.checkComponentPermission(writePermission, i, i2, providerInfo.applicationInfo.uid, providerInfo.exported) == 0) {
                    return null;
                }
            }
        }
        if (!z3 && this.mService.mUgmInternal.checkAuthorityGrants(i2, providerInfo, i4, z)) {
            return null;
        }
        if (!providerInfo.exported) {
            str2 = " that is not exported from UID " + providerInfo.applicationInfo.uid;
        } else if ("android.permission.MANAGE_DOCUMENTS".equals(providerInfo.readPermission)) {
            str2 = " requires that you obtain access using ACTION_OPEN_DOCUMENT or related APIs";
        } else {
            str2 = " requires " + providerInfo.readPermission + " or " + providerInfo.writePermission;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Permission Denial: opening provider ");
        sb.append(providerInfo.name);
        sb.append(" from ");
        sb.append(str != null ? str : "(null)");
        sb.append(" (pid=");
        sb.append(i);
        sb.append(", uid=");
        sb.append(i2);
        sb.append(")");
        sb.append(str2);
        java.lang.String sb2 = sb.toString();
        android.util.Slog.w(TAG, sb2);
        return sb2;
    }

    private java.lang.String checkContentProviderAssociation(final com.android.server.am.ProcessRecord processRecord, int i, final android.content.pm.ProviderInfo providerInfo) {
        if (processRecord == null) {
            if (this.mService.validateAssociationAllowedLocked(providerInfo.packageName, providerInfo.applicationInfo.uid, null, i)) {
                return null;
            }
            return "<null>";
        }
        return (java.lang.String) processRecord.getPkgList().searchEachPackage(new java.util.function.Function() { // from class: com.android.server.am.ContentProviderHelper$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String lambda$checkContentProviderAssociation$4;
                lambda$checkContentProviderAssociation$4 = com.android.server.am.ContentProviderHelper.this.lambda$checkContentProviderAssociation$4(processRecord, providerInfo, (java.lang.String) obj);
                return lambda$checkContentProviderAssociation$4;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$checkContentProviderAssociation$4(com.android.server.am.ProcessRecord processRecord, android.content.pm.ProviderInfo providerInfo, java.lang.String str) {
        if (!this.mService.validateAssociationAllowedLocked(str, processRecord.uid, providerInfo.packageName, providerInfo.applicationInfo.uid)) {
            return providerInfo.packageName;
        }
        return null;
    }

    android.content.pm.ProviderInfo getProviderInfoLocked(java.lang.String str, int i, int i2) {
        com.android.server.am.ContentProviderRecord providerByName = this.mProviderMap.getProviderByName(str, i);
        if (providerByName != null) {
            return providerByName.info;
        }
        try {
            return android.app.AppGlobals.getPackageManager().resolveContentProvider(str, i2 | 2048, i);
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    private void maybeUpdateProviderUsageStatsLocked(com.android.server.am.ProcessRecord processRecord, java.lang.String str, java.lang.String str2) {
        com.android.server.am.UserState startedUserState;
        if (processRecord == null || processRecord.mState.getCurProcState() > 6 || (startedUserState = this.mService.mUserController.getStartedUserState(processRecord.userId)) == null) {
            return;
        }
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        java.lang.Long l = startedUserState.mProviderLastReportedFg.get(str2);
        if (l == null || l.longValue() < elapsedRealtime - 60000) {
            if (this.mService.mSystemReady) {
                this.mService.mUsageStatsService.reportContentProviderUsage(str2, str, processRecord.userId);
            }
            startedUserState.mProviderLastReportedFg.put(str2, java.lang.Long.valueOf(elapsedRealtime));
        }
    }

    private boolean isProcessAliveLocked(com.android.server.am.ProcessRecord processRecord) {
        int pid = processRecord.getPid();
        if (pid <= 0) {
            return false;
        }
        java.lang.String str = "/proc/" + pid + "/stat";
        this.mProcessStateStatsLongs[0] = 0;
        if (!android.os.Process.readProcFile(str, PROCESS_STATE_STATS_FORMAT, null, this.mProcessStateStatsLongs, null)) {
            return false;
        }
        long j = this.mProcessStateStatsLongs[0];
        return (j == 90 || j == 88 || j == 120 || j == 75 || android.os.Process.getUidForPid(pid) != processRecord.uid) ? false : true;
    }

    private static final class StartActivityRunnable implements java.lang.Runnable {
        private final android.content.Context mContext;
        private final android.content.Intent mIntent;
        private final android.os.UserHandle mUserHandle;

        StartActivityRunnable(android.content.Context context, android.content.Intent intent, android.os.UserHandle userHandle) {
            this.mContext = context;
            this.mIntent = intent;
            this.mUserHandle = userHandle;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mContext.startActivityAsUser(this.mIntent, this.mUserHandle);
        }
    }

    private boolean requestTargetProviderPermissionsReviewIfNeededLocked(android.content.pm.ProviderInfo providerInfo, com.android.server.am.ProcessRecord processRecord, int i, android.content.Context context) {
        boolean z = true;
        if (!this.mService.getPackageManagerInternal().isPermissionsReviewRequired(providerInfo.packageName, i)) {
            return true;
        }
        if (processRecord != null && processRecord.mState.getSetSchedGroup() == 0) {
            z = false;
        }
        if (!z) {
            android.util.Slog.w(TAG, "u" + i + " Instantiating a provider in package " + providerInfo.packageName + " requires a permissions review");
            return false;
        }
        android.content.Intent intent = new android.content.Intent("android.intent.action.REVIEW_PERMISSIONS");
        intent.addFlags(276824064);
        intent.putExtra("android.intent.extra.PACKAGE_NAME", providerInfo.packageName);
        this.mService.mHandler.post(new com.android.server.am.ContentProviderHelper.StartActivityRunnable(context, intent, new android.os.UserHandle(i)));
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:68:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    boolean removeDyingProviderLocked(com.android.server.am.ProcessRecord processRecord, com.android.server.am.ContentProviderRecord contentProviderRecord, boolean z) {
        boolean z2;
        boolean contains = this.mLaunchingProviders.contains(contentProviderRecord);
        if (contains && !z) {
            int i = contentProviderRecord.mRestartCount + 1;
            contentProviderRecord.mRestartCount = i;
            if (i > 3) {
                z2 = true;
                if (contains || z2) {
                    synchronized (contentProviderRecord) {
                        contentProviderRecord.launchingApp = null;
                        contentProviderRecord.notifyAll();
                        contentProviderRecord.onProviderPublishStatusLocked(false);
                        this.mService.mHandler.removeMessages(73, contentProviderRecord);
                    }
                    int userId = android.os.UserHandle.getUserId(contentProviderRecord.uid);
                    if (this.mProviderMap.getProviderByClass(contentProviderRecord.name, userId) == contentProviderRecord) {
                        this.mProviderMap.removeProviderByClass(contentProviderRecord.name, userId);
                    }
                    java.lang.String[] split = contentProviderRecord.info.authority.split(";");
                    for (int i2 = 0; i2 < split.length; i2++) {
                        if (this.mProviderMap.getProviderByName(split[i2], userId) == contentProviderRecord) {
                            this.mProviderMap.removeProviderByName(split[i2], userId);
                        }
                    }
                }
                for (int size = contentProviderRecord.connections.size() - 1; size >= 0; size--) {
                    com.android.server.am.ContentProviderConnection contentProviderConnection = contentProviderRecord.connections.get(size);
                    if (!contentProviderConnection.waiting || !contains || z2) {
                        com.android.server.am.ProcessRecord processRecord2 = contentProviderConnection.client;
                        android.app.IApplicationThread thread = processRecord2.getThread();
                        contentProviderConnection.dead = true;
                        if (contentProviderConnection.stableCount() > 0) {
                            int pid = processRecord2.getPid();
                            if (!processRecord2.isPersistent() && thread != null && pid != 0 && pid != com.android.server.am.ActivityManagerService.MY_PID) {
                                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                sb.append("depends on provider ");
                                sb.append(contentProviderRecord.name.flattenToShortString());
                                sb.append(" in dying proc ");
                                sb.append(processRecord != null ? processRecord.processName : "??");
                                sb.append(" (adj ");
                                sb.append(processRecord != null ? java.lang.Integer.valueOf(processRecord.mState.getSetAdj()) : "??");
                                sb.append(")");
                                processRecord2.killLocked(sb.toString(), 12, 0, true);
                            }
                        } else if (thread != null && contentProviderConnection.provider.provider != null) {
                            try {
                                thread.unstableProviderDied(contentProviderConnection.provider.provider.asBinder());
                            } catch (android.os.RemoteException e) {
                            }
                            contentProviderRecord.connections.remove(size);
                            if (contentProviderRecord.proc != null && !hasProviderConnectionLocked(contentProviderRecord.proc)) {
                                contentProviderRecord.proc.mProfile.clearHostingComponentType(64);
                            }
                            if (contentProviderConnection.client.mProviders.removeProviderConnection(contentProviderConnection)) {
                                this.mService.stopAssociationLocked(processRecord2.uid, processRecord2.processName, contentProviderRecord.uid, contentProviderRecord.appInfo.longVersionCode, contentProviderRecord.name, contentProviderRecord.info.processName);
                            }
                        }
                    }
                }
                if (contains && z2) {
                    this.mLaunchingProviders.remove(contentProviderRecord);
                    contentProviderRecord.mRestartCount = 0;
                    return false;
                }
                return contains;
            }
        }
        z2 = z;
        if (contains) {
        }
        synchronized (contentProviderRecord) {
        }
    }

    boolean checkAppInLaunchingProvidersLocked(com.android.server.am.ProcessRecord processRecord) {
        for (int size = this.mLaunchingProviders.size() - 1; size >= 0; size--) {
            if (this.mLaunchingProviders.get(size).launchingApp == processRecord) {
                return true;
            }
        }
        return false;
    }

    boolean cleanupAppInLaunchingProvidersLocked(com.android.server.am.ProcessRecord processRecord, boolean z) {
        boolean z2 = false;
        for (int size = this.mLaunchingProviders.size() - 1; size >= 0; size--) {
            com.android.server.am.ContentProviderRecord contentProviderRecord = this.mLaunchingProviders.get(size);
            if (contentProviderRecord.launchingApp == processRecord) {
                int i = contentProviderRecord.mRestartCount + 1;
                contentProviderRecord.mRestartCount = i;
                if (i > 3) {
                    z = true;
                }
                if (!z && !processRecord.mErrorState.isBad() && contentProviderRecord.hasConnectionOrHandle()) {
                    z2 = true;
                } else {
                    removeDyingProviderLocked(processRecord, contentProviderRecord, true);
                }
            }
        }
        return z2;
    }

    void cleanupLaunchingProvidersLocked() {
        for (int size = this.mLaunchingProviders.size() - 1; size >= 0; size--) {
            com.android.server.am.ContentProviderRecord contentProviderRecord = this.mLaunchingProviders.get(size);
            if (contentProviderRecord.connections.size() <= 0 && !contentProviderRecord.hasExternalProcessHandles()) {
                synchronized (contentProviderRecord) {
                    contentProviderRecord.launchingApp = null;
                    contentProviderRecord.notifyAll();
                }
            }
        }
    }

    private void checkTime(long j, java.lang.String str) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis() - j;
        if (uptimeMillis > 50) {
            android.util.Slog.w(TAG, "Slow operation: " + uptimeMillis + "ms so far, now at " + str);
        }
    }

    void dumpProvidersLocked(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, int i, boolean z, java.lang.String str) {
        new com.android.server.am.ActivityManagerService.ItemMatcher().build(strArr, i);
        printWriter.println("ACTIVITY MANAGER CONTENT PROVIDERS (dumpsys activity providers)");
        boolean dumpProvidersLocked = this.mProviderMap.dumpProvidersLocked(printWriter, z, str);
        if (this.mLaunchingProviders.size() > 0) {
            boolean z2 = false;
            boolean z3 = dumpProvidersLocked;
            for (int size = this.mLaunchingProviders.size() - 1; size >= 0; size--) {
                com.android.server.am.ContentProviderRecord contentProviderRecord = this.mLaunchingProviders.get(size);
                if (str == null || str.equals(contentProviderRecord.name.getPackageName())) {
                    if (!z2) {
                        if (z3) {
                            printWriter.println();
                        }
                        printWriter.println("  Launching content providers:");
                        dumpProvidersLocked = true;
                        z3 = true;
                        z2 = true;
                    }
                    printWriter.print("  Launching #");
                    printWriter.print(size);
                    printWriter.print(": ");
                    printWriter.println(contentProviderRecord);
                }
            }
        }
        if (!dumpProvidersLocked) {
            printWriter.println("  (nothing)");
        }
    }

    private boolean canAccessContentProviderFromSdkSandbox(android.content.pm.ProviderInfo providerInfo, int i) {
        if (!android.os.Process.isSdkSandboxUid(i)) {
            return true;
        }
        com.android.server.sdksandbox.SdkSandboxManagerLocal sdkSandboxManagerLocal = (com.android.server.sdksandbox.SdkSandboxManagerLocal) com.android.server.LocalManagerRegistry.getManager(com.android.server.sdksandbox.SdkSandboxManagerLocal.class);
        if (sdkSandboxManagerLocal == null) {
            throw new java.lang.IllegalStateException("SdkSandboxManagerLocal not found when checking whether SDK sandbox uid may access the contentprovider.");
        }
        return sdkSandboxManagerLocal.canAccessContentProviderFromSdkSandbox(providerInfo);
    }

    protected boolean dumpProvider(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String str, java.lang.String[] strArr, int i, boolean z) {
        return this.mProviderMap.dumpProvider(fileDescriptor, printWriter, str, strArr, i, z);
    }

    protected boolean dumpProviderProto(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String str, java.lang.String[] strArr) {
        return this.mProviderMap.dumpProviderProto(fileDescriptor, printWriter, str, strArr);
    }

    private boolean isAuthorityRedirectedForCloneProfileCached(java.lang.String str) {
        if (this.mCloneProfileAuthorityRedirectionCache.containsKey(str)) {
            java.lang.Boolean bool = this.mCloneProfileAuthorityRedirectionCache.get(str);
            if (bool == null) {
                return false;
            }
            return bool.booleanValue();
        }
        boolean isAuthorityRedirectedForCloneProfile = android.content.ContentProvider.isAuthorityRedirectedForCloneProfile(str);
        this.mCloneProfileAuthorityRedirectionCache.put(str, java.lang.Boolean.valueOf(isAuthorityRedirectedForCloneProfile));
        return isAuthorityRedirectedForCloneProfile;
    }

    private boolean isSingletonOrSystemUserOnly(android.content.pm.ProviderInfo providerInfo) {
        return (android.multiuser.Flags.enableSystemUserOnlyForServicesAndProviders() && this.mService.isSystemUserOnly(providerInfo.flags)) || this.mService.isSingleton(providerInfo.processName, providerInfo.applicationInfo, providerInfo.name, providerInfo.flags);
    }
}
