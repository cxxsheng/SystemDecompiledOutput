package com.android.server.am;

/* loaded from: classes.dex */
public class EventLogTags {
    public static final int AM_ANR = 30008;
    public static final int AM_BROADCAST_DISCARD_APP = 30025;
    public static final int AM_BROADCAST_DISCARD_FILTER = 30024;
    public static final int AM_CLEAR_APP_DATA_CALLER = 30120;
    public static final int AM_COMPACT = 30063;
    public static final int AM_CPU = 30104;
    public static final int AM_CRASH = 30039;
    public static final int AM_CREATE_SERVICE = 30030;
    public static final int AM_DESTROY_SERVICE = 30031;
    public static final int AM_DROP_PROCESS = 30033;
    public static final int AM_FOREGROUND_SERVICE_DENIED = 30101;
    public static final int AM_FOREGROUND_SERVICE_START = 30100;
    public static final int AM_FOREGROUND_SERVICE_STOP = 30102;
    public static final int AM_FOREGROUND_SERVICE_TIMED_OUT = 30103;
    public static final int AM_FREEZE = 30068;
    public static final int AM_INTENT_SENDER_REDIRECT_USER = 30110;
    public static final int AM_KILL = 30023;
    public static final int AM_LOW_MEMORY = 30017;
    public static final int AM_MEMINFO = 30046;
    public static final int AM_MEM_FACTOR = 30050;
    public static final int AM_PRE_BOOT = 30045;
    public static final int AM_PROCESS_CRASHED_TOO_MUCH = 30032;
    public static final int AM_PROCESS_START_TIMEOUT = 30037;
    public static final int AM_PROC_BAD = 30015;
    public static final int AM_PROC_BOUND = 30010;
    public static final int AM_PROC_DIED = 30011;
    public static final int AM_PROC_GOOD = 30016;
    public static final int AM_PROC_START = 30014;
    public static final int AM_PROVIDER_LOST_PROCESS = 30036;
    public static final int AM_PSS = 30047;
    public static final int AM_SCHEDULE_SERVICE_RESTART = 30035;
    public static final int AM_SERVICE_CRASHED_TOO_MUCH = 30034;
    public static final int AM_STOP_IDLE_SERVICE = 30056;
    public static final int AM_SWITCH_USER = 30041;
    public static final int AM_UID_ACTIVE = 30054;
    public static final int AM_UID_IDLE = 30055;
    public static final int AM_UID_RUNNING = 30052;
    public static final int AM_UID_STOPPED = 30053;
    public static final int AM_UNFREEZE = 30069;
    public static final int AM_USER_STATE_CHANGED = 30051;
    public static final int AM_WTF = 30040;
    public static final int BOOT_PROGRESS_AMS_READY = 3040;
    public static final int BOOT_PROGRESS_ENABLE_SCREEN = 3050;
    public static final int CONFIGURATION_CHANGED = 2719;
    public static final int CPU = 2721;
    public static final int SSM_USER_COMPLETED_EVENT = 30088;
    public static final int SSM_USER_STARTING = 30082;
    public static final int SSM_USER_STOPPED = 30087;
    public static final int SSM_USER_STOPPING = 30086;
    public static final int SSM_USER_SWITCHING = 30083;
    public static final int SSM_USER_UNLOCKED = 30085;
    public static final int SSM_USER_UNLOCKING = 30084;
    public static final int UC_CONTINUE_USER_SWITCH = 30080;
    public static final int UC_DISPATCH_USER_SWITCH = 30079;
    public static final int UC_FINISH_USER_BOOT = 30078;
    public static final int UC_FINISH_USER_STOPPED = 30074;
    public static final int UC_FINISH_USER_STOPPING = 30073;
    public static final int UC_FINISH_USER_UNLOCKED = 30071;
    public static final int UC_FINISH_USER_UNLOCKED_COMPLETED = 30072;
    public static final int UC_FINISH_USER_UNLOCKING = 30070;
    public static final int UC_SEND_USER_BROADCAST = 30081;
    public static final int UC_START_USER_INTERNAL = 30076;
    public static final int UC_SWITCH_USER = 30075;
    public static final int UC_UNLOCK_USER = 30077;
    public static final int UM_USER_VISIBILITY_CHANGED = 30091;

    private EventLogTags() {
    }

    public static void writeConfigurationChanged(int i) {
        android.util.EventLog.writeEvent(CONFIGURATION_CHANGED, i);
    }

    public static void writeCpu(int i, int i2, int i3, int i4, int i5, int i6) {
        android.util.EventLog.writeEvent(CPU, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(i5), java.lang.Integer.valueOf(i6));
    }

    public static void writeBootProgressAmsReady(long j) {
        android.util.EventLog.writeEvent(BOOT_PROGRESS_AMS_READY, j);
    }

    public static void writeBootProgressEnableScreen(long j) {
        android.util.EventLog.writeEvent(BOOT_PROGRESS_ENABLE_SCREEN, j);
    }

    public static void writeAmAnr(int i, int i2, java.lang.String str, int i3, java.lang.String str2) {
        android.util.EventLog.writeEvent(AM_ANR, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str, java.lang.Integer.valueOf(i3), str2);
    }

    public static void writeAmProcBound(int i, int i2, java.lang.String str) {
        android.util.EventLog.writeEvent(AM_PROC_BOUND, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str);
    }

    public static void writeAmProcDied(int i, int i2, java.lang.String str, int i3, int i4) {
        android.util.EventLog.writeEvent(AM_PROC_DIED, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str, java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4));
    }

    public static void writeAmProcStart(int i, int i2, int i3, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        android.util.EventLog.writeEvent(AM_PROC_START, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), str, str2, str3);
    }

    public static void writeAmProcBad(int i, int i2, java.lang.String str) {
        android.util.EventLog.writeEvent(AM_PROC_BAD, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str);
    }

    public static void writeAmProcGood(int i, int i2, java.lang.String str) {
        android.util.EventLog.writeEvent(AM_PROC_GOOD, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str);
    }

    public static void writeAmLowMemory(int i) {
        android.util.EventLog.writeEvent(AM_LOW_MEMORY, i);
    }

    public static void writeAmKill(int i, int i2, java.lang.String str, int i3, java.lang.String str2, long j) {
        android.util.EventLog.writeEvent(AM_KILL, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str, java.lang.Integer.valueOf(i3), str2, java.lang.Long.valueOf(j));
    }

    public static void writeAmBroadcastDiscardFilter(int i, int i2, java.lang.String str, int i3, int i4) {
        android.util.EventLog.writeEvent(AM_BROADCAST_DISCARD_FILTER, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str, java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4));
    }

    public static void writeAmBroadcastDiscardApp(int i, int i2, java.lang.String str, int i3, java.lang.String str2) {
        android.util.EventLog.writeEvent(AM_BROADCAST_DISCARD_APP, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str, java.lang.Integer.valueOf(i3), str2);
    }

    public static void writeAmCreateService(int i, int i2, java.lang.String str, int i3, int i4) {
        android.util.EventLog.writeEvent(AM_CREATE_SERVICE, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str, java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4));
    }

    public static void writeAmDestroyService(int i, int i2, int i3) {
        android.util.EventLog.writeEvent(AM_DESTROY_SERVICE, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3));
    }

    public static void writeAmProcessCrashedTooMuch(int i, java.lang.String str, int i2) {
        android.util.EventLog.writeEvent(AM_PROCESS_CRASHED_TOO_MUCH, java.lang.Integer.valueOf(i), str, java.lang.Integer.valueOf(i2));
    }

    public static void writeAmDropProcess(int i) {
        android.util.EventLog.writeEvent(AM_DROP_PROCESS, i);
    }

    public static void writeAmServiceCrashedTooMuch(int i, int i2, java.lang.String str, int i3) {
        android.util.EventLog.writeEvent(AM_SERVICE_CRASHED_TOO_MUCH, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str, java.lang.Integer.valueOf(i3));
    }

    public static void writeAmScheduleServiceRestart(int i, java.lang.String str, long j) {
        android.util.EventLog.writeEvent(AM_SCHEDULE_SERVICE_RESTART, java.lang.Integer.valueOf(i), str, java.lang.Long.valueOf(j));
    }

    public static void writeAmProviderLostProcess(int i, java.lang.String str, int i2, java.lang.String str2) {
        android.util.EventLog.writeEvent(AM_PROVIDER_LOST_PROCESS, java.lang.Integer.valueOf(i), str, java.lang.Integer.valueOf(i2), str2);
    }

    public static void writeAmProcessStartTimeout(int i, int i2, int i3, java.lang.String str) {
        android.util.EventLog.writeEvent(AM_PROCESS_START_TIMEOUT, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), str);
    }

    public static void writeAmCrash(int i, int i2, java.lang.String str, int i3, java.lang.String str2, java.lang.String str3, java.lang.String str4, int i4, int i5) {
        android.util.EventLog.writeEvent(AM_CRASH, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str, java.lang.Integer.valueOf(i3), str2, str3, str4, java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(i5));
    }

    public static void writeAmWtf(int i, int i2, java.lang.String str, int i3, java.lang.String str2, java.lang.String str3) {
        android.util.EventLog.writeEvent(AM_WTF, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str, java.lang.Integer.valueOf(i3), str2, str3);
    }

    public static void writeAmSwitchUser(int i) {
        android.util.EventLog.writeEvent(AM_SWITCH_USER, i);
    }

    public static void writeAmPreBoot(int i, java.lang.String str) {
        android.util.EventLog.writeEvent(AM_PRE_BOOT, java.lang.Integer.valueOf(i), str);
    }

    public static void writeAmMeminfo(long j, long j2, long j3, long j4, long j5) {
        android.util.EventLog.writeEvent(AM_MEMINFO, java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2), java.lang.Long.valueOf(j3), java.lang.Long.valueOf(j4), java.lang.Long.valueOf(j5));
    }

    public static void writeAmPss(int i, int i2, java.lang.String str, long j, long j2, long j3, long j4, int i3, int i4, long j5) {
        android.util.EventLog.writeEvent(AM_PSS, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str, java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2), java.lang.Long.valueOf(j3), java.lang.Long.valueOf(j4), java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4), java.lang.Long.valueOf(j5));
    }

    public static void writeAmMemFactor(int i, int i2) {
        android.util.EventLog.writeEvent(AM_MEM_FACTOR, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
    }

    public static void writeAmUserStateChanged(int i, int i2) {
        android.util.EventLog.writeEvent(AM_USER_STATE_CHANGED, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
    }

    public static void writeAmUidRunning(int i) {
        android.util.EventLog.writeEvent(AM_UID_RUNNING, i);
    }

    public static void writeAmUidStopped(int i) {
        android.util.EventLog.writeEvent(AM_UID_STOPPED, i);
    }

    public static void writeAmUidActive(int i) {
        android.util.EventLog.writeEvent(AM_UID_ACTIVE, i);
    }

    public static void writeAmUidIdle(int i) {
        android.util.EventLog.writeEvent(AM_UID_IDLE, i);
    }

    public static void writeAmStopIdleService(int i, java.lang.String str) {
        android.util.EventLog.writeEvent(AM_STOP_IDLE_SERVICE, java.lang.Integer.valueOf(i), str);
    }

    public static void writeAmCompact(int i, java.lang.String str, java.lang.String str2, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, int i2, long j10, int i3, int i4, long j11, long j12) {
        android.util.EventLog.writeEvent(AM_COMPACT, java.lang.Integer.valueOf(i), str, str2, java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2), java.lang.Long.valueOf(j3), java.lang.Long.valueOf(j4), java.lang.Long.valueOf(j5), java.lang.Long.valueOf(j6), java.lang.Long.valueOf(j7), java.lang.Long.valueOf(j8), java.lang.Long.valueOf(j9), java.lang.Integer.valueOf(i2), java.lang.Long.valueOf(j10), java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4), java.lang.Long.valueOf(j11), java.lang.Long.valueOf(j12));
    }

    public static void writeAmFreeze(int i, java.lang.String str) {
        android.util.EventLog.writeEvent(AM_FREEZE, java.lang.Integer.valueOf(i), str);
    }

    public static void writeAmUnfreeze(int i, java.lang.String str) {
        android.util.EventLog.writeEvent(AM_UNFREEZE, java.lang.Integer.valueOf(i), str);
    }

    public static void writeUcFinishUserUnlocking(int i) {
        android.util.EventLog.writeEvent(UC_FINISH_USER_UNLOCKING, i);
    }

    public static void writeUcFinishUserUnlocked(int i) {
        android.util.EventLog.writeEvent(UC_FINISH_USER_UNLOCKED, i);
    }

    public static void writeUcFinishUserUnlockedCompleted(int i) {
        android.util.EventLog.writeEvent(UC_FINISH_USER_UNLOCKED_COMPLETED, i);
    }

    public static void writeUcFinishUserStopping(int i) {
        android.util.EventLog.writeEvent(UC_FINISH_USER_STOPPING, i);
    }

    public static void writeUcFinishUserStopped(int i) {
        android.util.EventLog.writeEvent(UC_FINISH_USER_STOPPED, i);
    }

    public static void writeUcSwitchUser(int i) {
        android.util.EventLog.writeEvent(UC_SWITCH_USER, i);
    }

    public static void writeUcStartUserInternal(int i, int i2, int i3) {
        android.util.EventLog.writeEvent(UC_START_USER_INTERNAL, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3));
    }

    public static void writeUcUnlockUser(int i) {
        android.util.EventLog.writeEvent(UC_UNLOCK_USER, i);
    }

    public static void writeUcFinishUserBoot(int i) {
        android.util.EventLog.writeEvent(UC_FINISH_USER_BOOT, i);
    }

    public static void writeUcDispatchUserSwitch(int i, int i2) {
        android.util.EventLog.writeEvent(UC_DISPATCH_USER_SWITCH, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
    }

    public static void writeUcContinueUserSwitch(int i, int i2) {
        android.util.EventLog.writeEvent(UC_CONTINUE_USER_SWITCH, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
    }

    public static void writeUcSendUserBroadcast(int i, java.lang.String str) {
        android.util.EventLog.writeEvent(UC_SEND_USER_BROADCAST, java.lang.Integer.valueOf(i), str);
    }

    public static void writeSsmUserStarting(int i) {
        android.util.EventLog.writeEvent(SSM_USER_STARTING, i);
    }

    public static void writeSsmUserSwitching(int i, int i2) {
        android.util.EventLog.writeEvent(SSM_USER_SWITCHING, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
    }

    public static void writeSsmUserUnlocking(int i) {
        android.util.EventLog.writeEvent(SSM_USER_UNLOCKING, i);
    }

    public static void writeSsmUserUnlocked(int i) {
        android.util.EventLog.writeEvent(SSM_USER_UNLOCKED, i);
    }

    public static void writeSsmUserStopping(int i) {
        android.util.EventLog.writeEvent(SSM_USER_STOPPING, i);
    }

    public static void writeSsmUserStopped(int i) {
        android.util.EventLog.writeEvent(SSM_USER_STOPPED, i);
    }

    public static void writeSsmUserCompletedEvent(int i, int i2) {
        android.util.EventLog.writeEvent(SSM_USER_COMPLETED_EVENT, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
    }

    public static void writeUmUserVisibilityChanged(int i, int i2) {
        android.util.EventLog.writeEvent(UM_USER_VISIBILITY_CHANGED, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
    }

    public static void writeAmForegroundServiceStart(int i, java.lang.String str, int i2, java.lang.String str2, int i3, int i4, int i5, int i6, int i7, int i8, java.lang.String str3, int i9) {
        android.util.EventLog.writeEvent(AM_FOREGROUND_SERVICE_START, java.lang.Integer.valueOf(i), str, java.lang.Integer.valueOf(i2), str2, java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(i5), java.lang.Integer.valueOf(i6), java.lang.Integer.valueOf(i7), java.lang.Integer.valueOf(i8), str3, java.lang.Integer.valueOf(i9));
    }

    public static void writeAmForegroundServiceDenied(int i, java.lang.String str, int i2, java.lang.String str2, int i3, int i4, int i5, int i6, int i7, int i8, java.lang.String str3, int i9) {
        android.util.EventLog.writeEvent(AM_FOREGROUND_SERVICE_DENIED, java.lang.Integer.valueOf(i), str, java.lang.Integer.valueOf(i2), str2, java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(i5), java.lang.Integer.valueOf(i6), java.lang.Integer.valueOf(i7), java.lang.Integer.valueOf(i8), str3, java.lang.Integer.valueOf(i9));
    }

    public static void writeAmForegroundServiceStop(int i, java.lang.String str, int i2, java.lang.String str2, int i3, int i4, int i5, int i6, int i7, int i8, java.lang.String str3, int i9) {
        android.util.EventLog.writeEvent(AM_FOREGROUND_SERVICE_STOP, java.lang.Integer.valueOf(i), str, java.lang.Integer.valueOf(i2), str2, java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(i5), java.lang.Integer.valueOf(i6), java.lang.Integer.valueOf(i7), java.lang.Integer.valueOf(i8), str3, java.lang.Integer.valueOf(i9));
    }

    public static void writeAmForegroundServiceTimedOut(int i, java.lang.String str, int i2, java.lang.String str2, int i3, int i4, int i5, int i6, int i7, int i8, java.lang.String str3, int i9) {
        android.util.EventLog.writeEvent(AM_FOREGROUND_SERVICE_TIMED_OUT, java.lang.Integer.valueOf(i), str, java.lang.Integer.valueOf(i2), str2, java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(i5), java.lang.Integer.valueOf(i6), java.lang.Integer.valueOf(i7), java.lang.Integer.valueOf(i8), str3, java.lang.Integer.valueOf(i9));
    }

    public static void writeAmCpu(long j, long j2, java.lang.String str, long j3, long j4, long j5) {
        android.util.EventLog.writeEvent(AM_CPU, java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2), str, java.lang.Long.valueOf(j3), java.lang.Long.valueOf(j4), java.lang.Long.valueOf(j5));
    }

    public static void writeAmIntentSenderRedirectUser(int i) {
        android.util.EventLog.writeEvent(AM_INTENT_SENDER_REDIRECT_USER, i);
    }

    public static void writeAmClearAppDataCaller(int i, int i2, java.lang.String str) {
        android.util.EventLog.writeEvent(AM_CLEAR_APP_DATA_CALLER, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str);
    }
}
