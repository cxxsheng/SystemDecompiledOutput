package com.android.server.net;

/* loaded from: classes2.dex */
public class NetworkPolicyLogger {
    private static final int EVENT_APP_IDLE_STATE_CHANGED = 8;
    private static final int EVENT_APP_IDLE_WL_CHANGED = 14;
    private static final int EVENT_DEVICE_IDLE_MODE_ENABLED = 7;
    private static final int EVENT_FIREWALL_CHAIN_ENABLED = 12;
    private static final int EVENT_INTERFACES_CHANGED = 18;
    private static final int EVENT_METEREDNESS_CHANGED = 4;
    private static final int EVENT_METERED_ALLOWLIST_CHANGED = 15;
    private static final int EVENT_METERED_DENYLIST_CHANGED = 16;
    private static final int EVENT_NETWORK_BLOCKED = 1;
    private static final int EVENT_PAROLE_STATE_CHANGED = 9;
    private static final int EVENT_POLICIES_CHANGED = 3;
    private static final int EVENT_RESTRICT_BG_CHANGED = 6;
    private static final int EVENT_ROAMING_CHANGED = 17;
    private static final int EVENT_TEMP_POWER_SAVE_WL_CHANGED = 10;
    private static final int EVENT_TYPE_GENERIC = 0;
    private static final int EVENT_UID_FIREWALL_RULE_CHANGED = 11;
    private static final int EVENT_UID_STATE_CHANGED = 2;
    private static final int EVENT_UPDATE_METERED_RESTRICTED_PKGS = 13;
    private static final int EVENT_USER_STATE_REMOVED = 5;
    private static final int MAX_LOG_SIZE;
    private static final int MAX_NETWORK_BLOCKED_LOG_SIZE;
    static final java.lang.String TAG = "NetworkPolicy";
    static final boolean LOGD = android.util.Log.isLoggable(TAG, 3);
    static final boolean LOGV = android.util.Log.isLoggable(TAG, 2);
    private final com.android.server.net.NetworkPolicyLogger.LogBuffer mNetworkBlockedBuffer = new com.android.server.net.NetworkPolicyLogger.LogBuffer(MAX_NETWORK_BLOCKED_LOG_SIZE);
    private final com.android.server.net.NetworkPolicyLogger.LogBuffer mUidStateChangeBuffer = new com.android.server.net.NetworkPolicyLogger.LogBuffer(MAX_LOG_SIZE);
    private final com.android.server.net.NetworkPolicyLogger.LogBuffer mEventsBuffer = new com.android.server.net.NetworkPolicyLogger.LogBuffer(MAX_LOG_SIZE);
    private int mDebugUid = -1;
    private final java.lang.Object mLock = new java.lang.Object();

    static {
        MAX_LOG_SIZE = android.app.ActivityManager.isLowRamDeviceStatic() ? 100 : 400;
        MAX_NETWORK_BLOCKED_LOG_SIZE = android.app.ActivityManager.isLowRamDeviceStatic() ? 100 : 400;
    }

    void networkBlocked(int i, @android.annotation.Nullable com.android.server.net.NetworkPolicyManagerService.UidBlockedState uidBlockedState) {
        synchronized (this.mLock) {
            try {
                if (LOGD || i == this.mDebugUid) {
                    android.util.Slog.d(TAG, "Blocked state of " + i + ": " + uidBlockedState);
                }
                if (uidBlockedState == null) {
                    this.mNetworkBlockedBuffer.networkBlocked(i, 0, 0, 0);
                } else {
                    this.mNetworkBlockedBuffer.networkBlocked(i, uidBlockedState.blockedReasons, uidBlockedState.allowedReasons, uidBlockedState.effectiveBlockedReasons);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void uidStateChanged(int i, int i2, long j, int i3) {
        synchronized (this.mLock) {
            try {
                if (LOGV || i == this.mDebugUid) {
                    android.util.Slog.v(TAG, i + " state changed to " + com.android.server.am.ProcessList.makeProcStateString(i2) + ",seq=" + j + ",cap=" + android.app.ActivityManager.getCapabilitiesSummary(i3));
                }
                this.mUidStateChangeBuffer.uidStateChanged(i, i2, j, i3);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void event(java.lang.String str) {
        synchronized (this.mLock) {
            try {
                if (LOGV) {
                    android.util.Slog.v(TAG, str);
                }
                this.mEventsBuffer.event(str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void uidPolicyChanged(int i, int i2, int i3) {
        synchronized (this.mLock) {
            try {
                if (LOGV || i == this.mDebugUid) {
                    android.util.Slog.v(TAG, getPolicyChangedLog(i, i2, i3));
                }
                this.mEventsBuffer.uidPolicyChanged(i, i2, i3);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void meterednessChanged(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                if (LOGD || this.mDebugUid != -1) {
                    android.util.Slog.d(TAG, getMeterednessChangedLog(i, z));
                }
                this.mEventsBuffer.meterednessChanged(i, z);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void removingUserState(int i) {
        synchronized (this.mLock) {
            try {
                if (LOGD || this.mDebugUid != -1) {
                    android.util.Slog.d(TAG, getUserRemovedLog(i));
                }
                this.mEventsBuffer.userRemoved(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void restrictBackgroundChanged(boolean z, boolean z2) {
        synchronized (this.mLock) {
            try {
                if (LOGD || this.mDebugUid != -1) {
                    android.util.Slog.d(TAG, getRestrictBackgroundChangedLog(z, z2));
                }
                this.mEventsBuffer.restrictBackgroundChanged(z, z2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void deviceIdleModeEnabled(boolean z) {
        synchronized (this.mLock) {
            try {
                if (LOGD || this.mDebugUid != -1) {
                    android.util.Slog.d(TAG, getDeviceIdleModeEnabled(z));
                }
                this.mEventsBuffer.deviceIdleModeEnabled(z);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void appIdleStateChanged(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                if (LOGD || i == this.mDebugUid) {
                    android.util.Slog.d(TAG, getAppIdleChangedLog(i, z));
                }
                this.mEventsBuffer.appIdleStateChanged(i, z);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void appIdleWlChanged(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                if (LOGD || i == this.mDebugUid) {
                    android.util.Slog.d(TAG, getAppIdleWlChangedLog(i, z));
                }
                this.mEventsBuffer.appIdleWlChanged(i, z);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void paroleStateChanged(boolean z) {
        synchronized (this.mLock) {
            try {
                if (LOGD || this.mDebugUid != -1) {
                    android.util.Slog.d(TAG, getParoleStateChanged(z));
                }
                this.mEventsBuffer.paroleStateChanged(z);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void tempPowerSaveWlChanged(int i, boolean z, int i2, java.lang.String str) {
        synchronized (this.mLock) {
            try {
                if (LOGV || i == android.os.UserHandle.getAppId(this.mDebugUid)) {
                    android.util.Slog.v(TAG, getTempPowerSaveWlChangedLog(i, z, i2, str));
                }
                this.mEventsBuffer.tempPowerSaveWlChanged(i, z, i2, str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void uidFirewallRuleChanged(int i, int i2, int i3) {
        synchronized (this.mLock) {
            try {
                if (LOGV || i2 == this.mDebugUid) {
                    android.util.Slog.v(TAG, getUidFirewallRuleChangedLog(i, i2, i3));
                }
                this.mEventsBuffer.uidFirewallRuleChanged(i, i2, i3);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void firewallChainEnabled(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                if (LOGD || this.mDebugUid != -1) {
                    android.util.Slog.d(TAG, getFirewallChainEnabledLog(i, z));
                }
                this.mEventsBuffer.firewallChainEnabled(i, z);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void firewallRulesChanged(int i, int[] iArr, int[] iArr2) {
        synchronized (this.mLock) {
            try {
                java.lang.String str = "Firewall rules changed for " + getFirewallChainName(i) + "; uids=" + java.util.Arrays.toString(iArr) + "; rules=" + java.util.Arrays.toString(iArr2);
                if (LOGD || this.mDebugUid != -1) {
                    android.util.Slog.d(TAG, str);
                }
                this.mEventsBuffer.event(str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void meteredRestrictedPkgsChanged(java.util.Set<java.lang.Integer> set) {
        synchronized (this.mLock) {
            try {
                java.lang.String str = "Metered restricted uids: " + set;
                if (LOGD || this.mDebugUid != -1) {
                    android.util.Slog.d(TAG, str);
                }
                this.mEventsBuffer.event(str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void meteredAllowlistChanged(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                if (LOGD || this.mDebugUid == i) {
                    android.util.Slog.d(TAG, getMeteredAllowlistChangedLog(i, z));
                }
                this.mEventsBuffer.meteredAllowlistChanged(i, z);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void meteredDenylistChanged(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                if (LOGD || this.mDebugUid == i) {
                    android.util.Slog.d(TAG, getMeteredDenylistChangedLog(i, z));
                }
                this.mEventsBuffer.meteredDenylistChanged(i, z);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void roamingChanged(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                if (LOGD || this.mDebugUid != -1) {
                    android.util.Slog.d(TAG, getRoamingChangedLog(i, z));
                }
                this.mEventsBuffer.roamingChanged(i, z);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void interfacesChanged(int i, android.util.ArraySet<java.lang.String> arraySet) {
        synchronized (this.mLock) {
            try {
                if (LOGD || this.mDebugUid != -1) {
                    android.util.Slog.d(TAG, getInterfacesChangedLog(i, arraySet.toString()));
                }
                this.mEventsBuffer.interfacesChanged(i, arraySet.toString());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setDebugUid(int i) {
        this.mDebugUid = i;
    }

    void dumpLogs(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            indentingPrintWriter.println();
            indentingPrintWriter.println("mEventLogs (most recent first):");
            indentingPrintWriter.increaseIndent();
            this.mEventsBuffer.reverseDump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.println("mNetworkBlockedLogs (most recent first):");
            indentingPrintWriter.increaseIndent();
            this.mNetworkBlockedBuffer.reverseDump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.println("mUidStateChangeLogs (most recent first):");
            indentingPrintWriter.increaseIndent();
            this.mUidStateChangeBuffer.reverseDump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getPolicyChangedLog(int i, int i2, int i3) {
        return "Policy for " + i + " changed from " + android.net.NetworkPolicyManager.uidPoliciesToString(i2) + " to " + android.net.NetworkPolicyManager.uidPoliciesToString(i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getMeterednessChangedLog(int i, boolean z) {
        return "Meteredness of netId=" + i + " changed to " + z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getUserRemovedLog(int i) {
        return "Remove state for u" + i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getRestrictBackgroundChangedLog(boolean z, boolean z2) {
        return "Changed restrictBackground: " + z + "->" + z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getDeviceIdleModeEnabled(boolean z) {
        return "DeviceIdleMode enabled: " + z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getAppIdleChangedLog(int i, boolean z) {
        return "App idle state of uid " + i + ": " + z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getAppIdleWlChangedLog(int i, boolean z) {
        return "App idle whitelist state of uid " + i + ": " + z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getParoleStateChanged(boolean z) {
        return "Parole state: " + z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getTempPowerSaveWlChangedLog(int i, boolean z, int i2, java.lang.String str) {
        return "temp-power-save whitelist for " + i + " changed to: " + z + "; reason=" + android.os.PowerExemptionManager.reasonCodeToString(i2) + " <" + str + ">";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getUidFirewallRuleChangedLog(int i, int i2, int i3) {
        return java.lang.String.format("Firewall rule changed: %d-%s-%s", java.lang.Integer.valueOf(i2), getFirewallChainName(i), getFirewallRuleName(i3));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getFirewallChainEnabledLog(int i, boolean z) {
        return "Firewall chain " + getFirewallChainName(i) + " state: " + z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getMeteredAllowlistChangedLog(int i, boolean z) {
        return "metered-allowlist for " + i + " changed to " + z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getMeteredDenylistChangedLog(int i, boolean z) {
        return "metered-denylist for " + i + " changed to " + z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getRoamingChangedLog(int i, boolean z) {
        return "Roaming of netId=" + i + " changed to " + z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getInterfacesChangedLog(int i, java.lang.String str) {
        return "Interfaces of netId=" + i + " changed to " + str;
    }

    private static java.lang.String getFirewallChainName(int i) {
        switch (i) {
            case 1:
                return "dozable";
            case 2:
                return "standby";
            case 3:
                return "powersave";
            case 4:
                return "restricted";
            case 5:
                return com.android.server.power.LowPowerStandbyController.DeviceConfigWrapper.NAMESPACE;
            case 6:
                return "background";
            default:
                return java.lang.String.valueOf(i);
        }
    }

    private static java.lang.String getFirewallRuleName(int i) {
        switch (i) {
            case 0:
                return "default";
            case 1:
                return "allow";
            case 2:
                return "deny";
            default:
                return java.lang.String.valueOf(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class LogBuffer extends com.android.internal.util.RingBuffer<com.android.server.net.NetworkPolicyLogger.Data> {
        private static final java.text.SimpleDateFormat sFormatter = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS");
        private static final java.util.Date sDate = new java.util.Date();

        public static /* synthetic */ com.android.server.net.NetworkPolicyLogger.Data $r8$lambda$kDNlKKkLZ8vfiKJuYgxljbBLzHE() {
            return new com.android.server.net.NetworkPolicyLogger.Data();
        }

        public LogBuffer(int i) {
            super(new java.util.function.Supplier() { // from class: com.android.server.net.NetworkPolicyLogger$LogBuffer$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    return com.android.server.net.NetworkPolicyLogger.LogBuffer.$r8$lambda$kDNlKKkLZ8vfiKJuYgxljbBLzHE();
                }
            }, new java.util.function.IntFunction() { // from class: com.android.server.net.NetworkPolicyLogger$LogBuffer$$ExternalSyntheticLambda1
                @Override // java.util.function.IntFunction
                public final java.lang.Object apply(int i2) {
                    com.android.server.net.NetworkPolicyLogger.Data[] lambda$new$0;
                    lambda$new$0 = com.android.server.net.NetworkPolicyLogger.LogBuffer.lambda$new$0(i2);
                    return lambda$new$0;
                }
            }, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ com.android.server.net.NetworkPolicyLogger.Data[] lambda$new$0(int i) {
            return new com.android.server.net.NetworkPolicyLogger.Data[i];
        }

        public void uidStateChanged(int i, int i2, long j, int i3) {
            com.android.server.net.NetworkPolicyLogger.Data data = (com.android.server.net.NetworkPolicyLogger.Data) getNextSlot();
            if (data == null) {
                return;
            }
            data.reset();
            data.type = 2;
            data.ifield1 = i;
            data.ifield2 = i2;
            data.ifield3 = i3;
            data.lfield1 = j;
            data.timeStamp = java.lang.System.currentTimeMillis();
        }

        public void event(java.lang.String str) {
            com.android.server.net.NetworkPolicyLogger.Data data = (com.android.server.net.NetworkPolicyLogger.Data) getNextSlot();
            if (data == null) {
                return;
            }
            data.reset();
            data.type = 0;
            data.sfield1 = str;
            data.timeStamp = java.lang.System.currentTimeMillis();
        }

        public void networkBlocked(int i, int i2, int i3, int i4) {
            com.android.server.net.NetworkPolicyLogger.Data data = (com.android.server.net.NetworkPolicyLogger.Data) getNextSlot();
            if (data == null) {
                return;
            }
            data.reset();
            data.type = 1;
            data.ifield1 = i;
            data.ifield2 = i2;
            data.ifield3 = i3;
            data.ifield4 = i4;
            data.timeStamp = java.lang.System.currentTimeMillis();
        }

        public void uidPolicyChanged(int i, int i2, int i3) {
            com.android.server.net.NetworkPolicyLogger.Data data = (com.android.server.net.NetworkPolicyLogger.Data) getNextSlot();
            if (data == null) {
                return;
            }
            data.reset();
            data.type = 3;
            data.ifield1 = i;
            data.ifield2 = i2;
            data.ifield3 = i3;
            data.timeStamp = java.lang.System.currentTimeMillis();
        }

        public void meterednessChanged(int i, boolean z) {
            com.android.server.net.NetworkPolicyLogger.Data data = (com.android.server.net.NetworkPolicyLogger.Data) getNextSlot();
            if (data == null) {
                return;
            }
            data.reset();
            data.type = 4;
            data.ifield1 = i;
            data.bfield1 = z;
            data.timeStamp = java.lang.System.currentTimeMillis();
        }

        public void userRemoved(int i) {
            com.android.server.net.NetworkPolicyLogger.Data data = (com.android.server.net.NetworkPolicyLogger.Data) getNextSlot();
            if (data == null) {
                return;
            }
            data.reset();
            data.type = 5;
            data.ifield1 = i;
            data.timeStamp = java.lang.System.currentTimeMillis();
        }

        public void restrictBackgroundChanged(boolean z, boolean z2) {
            com.android.server.net.NetworkPolicyLogger.Data data = (com.android.server.net.NetworkPolicyLogger.Data) getNextSlot();
            if (data == null) {
                return;
            }
            data.reset();
            data.type = 6;
            data.bfield1 = z;
            data.bfield2 = z2;
            data.timeStamp = java.lang.System.currentTimeMillis();
        }

        public void deviceIdleModeEnabled(boolean z) {
            com.android.server.net.NetworkPolicyLogger.Data data = (com.android.server.net.NetworkPolicyLogger.Data) getNextSlot();
            if (data == null) {
                return;
            }
            data.reset();
            data.type = 7;
            data.bfield1 = z;
            data.timeStamp = java.lang.System.currentTimeMillis();
        }

        public void appIdleStateChanged(int i, boolean z) {
            com.android.server.net.NetworkPolicyLogger.Data data = (com.android.server.net.NetworkPolicyLogger.Data) getNextSlot();
            if (data == null) {
                return;
            }
            data.reset();
            data.type = 8;
            data.ifield1 = i;
            data.bfield1 = z;
            data.timeStamp = java.lang.System.currentTimeMillis();
        }

        public void appIdleWlChanged(int i, boolean z) {
            com.android.server.net.NetworkPolicyLogger.Data data = (com.android.server.net.NetworkPolicyLogger.Data) getNextSlot();
            if (data == null) {
                return;
            }
            data.reset();
            data.type = 14;
            data.ifield1 = i;
            data.bfield1 = z;
            data.timeStamp = java.lang.System.currentTimeMillis();
        }

        public void paroleStateChanged(boolean z) {
            com.android.server.net.NetworkPolicyLogger.Data data = (com.android.server.net.NetworkPolicyLogger.Data) getNextSlot();
            if (data == null) {
                return;
            }
            data.reset();
            data.type = 9;
            data.bfield1 = z;
            data.timeStamp = java.lang.System.currentTimeMillis();
        }

        public void tempPowerSaveWlChanged(int i, boolean z, int i2, java.lang.String str) {
            com.android.server.net.NetworkPolicyLogger.Data data = (com.android.server.net.NetworkPolicyLogger.Data) getNextSlot();
            if (data == null) {
                return;
            }
            data.reset();
            data.type = 10;
            data.ifield1 = i;
            data.ifield2 = i2;
            data.bfield1 = z;
            data.sfield1 = str;
            data.timeStamp = java.lang.System.currentTimeMillis();
        }

        public void uidFirewallRuleChanged(int i, int i2, int i3) {
            com.android.server.net.NetworkPolicyLogger.Data data = (com.android.server.net.NetworkPolicyLogger.Data) getNextSlot();
            if (data == null) {
                return;
            }
            data.reset();
            data.type = 11;
            data.ifield1 = i;
            data.ifield2 = i2;
            data.ifield3 = i3;
            data.timeStamp = java.lang.System.currentTimeMillis();
        }

        public void firewallChainEnabled(int i, boolean z) {
            com.android.server.net.NetworkPolicyLogger.Data data = (com.android.server.net.NetworkPolicyLogger.Data) getNextSlot();
            if (data == null) {
                return;
            }
            data.reset();
            data.type = 12;
            data.ifield1 = i;
            data.bfield1 = z;
            data.timeStamp = java.lang.System.currentTimeMillis();
        }

        public void meteredAllowlistChanged(int i, boolean z) {
            com.android.server.net.NetworkPolicyLogger.Data data = (com.android.server.net.NetworkPolicyLogger.Data) getNextSlot();
            if (data == null) {
                return;
            }
            data.reset();
            data.type = 15;
            data.ifield1 = i;
            data.bfield1 = z;
            data.timeStamp = java.lang.System.currentTimeMillis();
        }

        public void meteredDenylistChanged(int i, boolean z) {
            com.android.server.net.NetworkPolicyLogger.Data data = (com.android.server.net.NetworkPolicyLogger.Data) getNextSlot();
            if (data == null) {
                return;
            }
            data.reset();
            data.type = 16;
            data.ifield1 = i;
            data.bfield1 = z;
            data.timeStamp = java.lang.System.currentTimeMillis();
        }

        public void roamingChanged(int i, boolean z) {
            com.android.server.net.NetworkPolicyLogger.Data data = (com.android.server.net.NetworkPolicyLogger.Data) getNextSlot();
            if (data == null) {
                return;
            }
            data.reset();
            data.type = 17;
            data.ifield1 = i;
            data.bfield1 = z;
            data.timeStamp = java.lang.System.currentTimeMillis();
        }

        public void interfacesChanged(int i, java.lang.String str) {
            com.android.server.net.NetworkPolicyLogger.Data data = (com.android.server.net.NetworkPolicyLogger.Data) getNextSlot();
            if (data == null) {
                return;
            }
            data.reset();
            data.type = 18;
            data.ifield1 = i;
            data.sfield1 = str;
            data.timeStamp = java.lang.System.currentTimeMillis();
        }

        public void reverseDump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            com.android.server.net.NetworkPolicyLogger.Data[] dataArr = (com.android.server.net.NetworkPolicyLogger.Data[]) toArray();
            for (int length = dataArr.length - 1; length >= 0; length--) {
                if (dataArr[length] == null) {
                    indentingPrintWriter.println("NULL");
                } else {
                    indentingPrintWriter.print(formatDate(dataArr[length].timeStamp));
                    indentingPrintWriter.print(" - ");
                    indentingPrintWriter.println(getContent(dataArr[length]));
                }
            }
        }

        public java.lang.String getContent(com.android.server.net.NetworkPolicyLogger.Data data) {
            switch (data.type) {
                case 0:
                    return data.sfield1;
                case 1:
                    return data.ifield1 + "-" + com.android.server.net.NetworkPolicyManagerService.UidBlockedState.toString(data.ifield2, data.ifield3, data.ifield4);
                case 2:
                    return data.ifield1 + ":" + com.android.server.am.ProcessList.makeProcStateString(data.ifield2) + ":" + android.app.ActivityManager.getCapabilitiesSummary(data.ifield3) + ":" + data.lfield1;
                case 3:
                    return com.android.server.net.NetworkPolicyLogger.getPolicyChangedLog(data.ifield1, data.ifield2, data.ifield3);
                case 4:
                    return com.android.server.net.NetworkPolicyLogger.getMeterednessChangedLog(data.ifield1, data.bfield1);
                case 5:
                    return com.android.server.net.NetworkPolicyLogger.getUserRemovedLog(data.ifield1);
                case 6:
                    return com.android.server.net.NetworkPolicyLogger.getRestrictBackgroundChangedLog(data.bfield1, data.bfield2);
                case 7:
                    return com.android.server.net.NetworkPolicyLogger.getDeviceIdleModeEnabled(data.bfield1);
                case 8:
                    return com.android.server.net.NetworkPolicyLogger.getAppIdleChangedLog(data.ifield1, data.bfield1);
                case 9:
                    return com.android.server.net.NetworkPolicyLogger.getParoleStateChanged(data.bfield1);
                case 10:
                    return com.android.server.net.NetworkPolicyLogger.getTempPowerSaveWlChangedLog(data.ifield1, data.bfield1, data.ifield2, data.sfield1);
                case 11:
                    return com.android.server.net.NetworkPolicyLogger.getUidFirewallRuleChangedLog(data.ifield1, data.ifield2, data.ifield3);
                case 12:
                    return com.android.server.net.NetworkPolicyLogger.getFirewallChainEnabledLog(data.ifield1, data.bfield1);
                case 13:
                default:
                    return java.lang.String.valueOf(data.type);
                case 14:
                    return com.android.server.net.NetworkPolicyLogger.getAppIdleWlChangedLog(data.ifield1, data.bfield1);
                case 15:
                    return com.android.server.net.NetworkPolicyLogger.getMeteredAllowlistChangedLog(data.ifield1, data.bfield1);
                case 16:
                    return com.android.server.net.NetworkPolicyLogger.getMeteredDenylistChangedLog(data.ifield1, data.bfield1);
                case 17:
                    return com.android.server.net.NetworkPolicyLogger.getRoamingChangedLog(data.ifield1, data.bfield1);
                case 18:
                    return com.android.server.net.NetworkPolicyLogger.getInterfacesChangedLog(data.ifield1, data.sfield1);
            }
        }

        private java.lang.String formatDate(long j) {
            sDate.setTime(j);
            return sFormatter.format(sDate);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class Data {
        public boolean bfield1;
        public boolean bfield2;
        public int ifield1;
        public int ifield2;
        public int ifield3;
        public int ifield4;
        public long lfield1;
        public java.lang.String sfield1;
        public long timeStamp;
        public int type;

        private Data() {
        }

        public void reset() {
            this.sfield1 = null;
        }
    }
}
