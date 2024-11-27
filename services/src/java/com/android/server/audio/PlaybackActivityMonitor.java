package com.android.server.audio;

/* loaded from: classes.dex */
public final class PlaybackActivityMonitor implements android.media.AudioPlaybackConfiguration.PlayerDeathMonitor, com.android.server.audio.PlayerFocusEnforcer {
    static final boolean DEBUG = false;
    static final java.lang.String EVENT_TYPE_FADE_IN = "fading in";
    static final java.lang.String EVENT_TYPE_FADE_OUT = "fading out";
    private static final int FLAGS_FOR_SILENCE_OVERRIDE = 192;
    private static final int MSG_IIL_UPDATE_PLAYER_FORMAT = 5;
    private static final int MSG_IIL_UPDATE_PLAYER_MUTED_EVENT = 3;
    private static final int MSG_II_UPDATE_PORT_EVENT = 2;
    private static final int MSG_I_CLEAR_PORTS_FOR_PIID = 4;
    private static final int MSG_L_TIMEOUT_MUTE_AWAIT_CONNECTION = 1;
    public static final java.lang.String TAG = "AS.PlaybackActivityMon";
    static final int VOLUME_SHAPER_SYSTEM_DUCK_ID = 1;
    static final int VOLUME_SHAPER_SYSTEM_FADEOUT_ID = 2;
    static final int VOLUME_SHAPER_SYSTEM_MUTE_AWAIT_CONNECTION_ID = 3;
    static final int VOLUME_SHAPER_SYSTEM_STRONG_DUCK_ID = 4;
    private final android.content.Context mContext;
    private android.os.Handler mEventHandler;
    private android.os.HandlerThread mEventThread;
    private final int mMaxAlarmVolume;
    private final java.util.function.Consumer<android.media.AudioDeviceAttributes> mMuteAwaitConnectionTimeoutCb;
    private static final android.media.VolumeShaper.Configuration DUCK_VSHAPE = new android.media.VolumeShaper.Configuration.Builder().setId(1).setCurve(new float[]{com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f}, new float[]{1.0f, 0.2f}).setOptionFlags(2).setDuration(com.android.server.audio.MediaFocusControl.getFocusRampTimeMs(3, new android.media.AudioAttributes.Builder().setUsage(5).build())).build();
    private static final android.media.VolumeShaper.Configuration DUCK_ID = new android.media.VolumeShaper.Configuration(1);
    private static final android.media.VolumeShaper.Configuration STRONG_DUCK_VSHAPE = new android.media.VolumeShaper.Configuration.Builder().setId(4).setCurve(new float[]{com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f}, new float[]{1.0f, 0.017783f}).setOptionFlags(2).setDuration(com.android.server.audio.MediaFocusControl.getFocusRampTimeMs(3, new android.media.AudioAttributes.Builder().setUsage(5).build())).build();
    private static final android.media.VolumeShaper.Configuration STRONG_DUCK_ID = new android.media.VolumeShaper.Configuration(4);
    private static final android.media.VolumeShaper.Operation PLAY_CREATE_IF_NEEDED = new android.media.VolumeShaper.Operation.Builder(android.media.VolumeShaper.Operation.PLAY).createIfNeeded().build();
    private static final long UNMUTE_DURATION_MS = 100;
    private static final android.media.VolumeShaper.Configuration MUTE_AWAIT_CONNECTION_VSHAPE = new android.media.VolumeShaper.Configuration.Builder().setId(3).setCurve(new float[]{com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f}, new float[]{1.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE}).setOptionFlags(2).setDuration(UNMUTE_DURATION_MS).build();
    private static final int[] UNDUCKABLE_PLAYER_TYPES = {13, 3};
    private static final android.media.VolumeShaper.Operation PLAY_SKIP_RAMP = new android.media.VolumeShaper.Operation.Builder(PLAY_CREATE_IF_NEEDED).setXOffset(1.0f).build();
    static final com.android.server.utils.EventLogger sEventLogger = new com.android.server.utils.EventLogger(100, "playback activity as reported through PlayerBase");
    private final java.util.concurrent.ConcurrentLinkedQueue<com.android.server.audio.PlaybackActivityMonitor.PlayMonitorClient> mClients = new java.util.concurrent.ConcurrentLinkedQueue<>();
    private final java.lang.Object mPlayerLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mPlayerLock"})
    private final java.util.HashMap<java.lang.Integer, android.media.AudioPlaybackConfiguration> mPlayers = new java.util.HashMap<>();

    @com.android.internal.annotations.GuardedBy({"mPlayerLock"})
    private final android.util.SparseIntArray mPortIdToPiid = new android.util.SparseIntArray();
    private int mSavedAlarmVolume = -1;
    private int mPrivilegedAlarmActiveCount = 0;
    private final com.android.server.audio.FadeOutManager mFadeOutManager = new com.android.server.audio.FadeOutManager();
    private final java.util.ArrayList<java.lang.Integer> mBannedUids = new java.util.ArrayList<>();

    @com.android.internal.annotations.GuardedBy({"mPlayerLock"})
    private java.util.ArrayList<java.lang.Integer> mDoNotLogPiidList = new java.util.ArrayList<>();
    private final java.util.HashMap<java.lang.Integer, java.lang.Integer> mAllowedCapturePolicies = new java.util.HashMap<>();
    private final java.util.ArrayList<java.lang.Integer> mMutedPlayers = new java.util.ArrayList<>();
    private final com.android.server.audio.PlaybackActivityMonitor.DuckingManager mDuckingManager = new com.android.server.audio.PlaybackActivityMonitor.DuckingManager();

    @com.android.internal.annotations.GuardedBy({"mPlayerLock"})
    private final java.util.ArrayList<java.lang.Integer> mMutedPlayersAwaitingConnection = new java.util.ArrayList<>();

    @com.android.internal.annotations.GuardedBy({"mPlayerLock"})
    @android.annotation.Nullable
    private int[] mMutedUsagesAwaitingConnection = null;

    PlaybackActivityMonitor(android.content.Context context, int i, java.util.function.Consumer<android.media.AudioDeviceAttributes> consumer) {
        this.mContext = context;
        this.mMaxAlarmVolume = i;
        com.android.server.audio.PlaybackActivityMonitor.PlayMonitorClient.sListenerDeathMonitor = this;
        android.media.AudioPlaybackConfiguration.sPlayerDeathMonitor = this;
        this.mMuteAwaitConnectionTimeoutCb = consumer;
        initEventHandler();
    }

    public void disableAudioForUid(boolean z, int i) {
        synchronized (this.mPlayerLock) {
            try {
                int indexOf = this.mBannedUids.indexOf(new java.lang.Integer(i));
                if (indexOf >= 0) {
                    if (!z) {
                        this.mBannedUids.remove(indexOf);
                    }
                } else if (z) {
                    java.util.Iterator<android.media.AudioPlaybackConfiguration> it = this.mPlayers.values().iterator();
                    while (it.hasNext()) {
                        checkBanPlayer(it.next(), i);
                    }
                    this.mBannedUids.add(new java.lang.Integer(i));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean checkBanPlayer(@android.annotation.NonNull android.media.AudioPlaybackConfiguration audioPlaybackConfiguration, int i) {
        boolean z = audioPlaybackConfiguration.getClientUid() == i;
        if (z) {
            int playerInterfaceId = audioPlaybackConfiguration.getPlayerInterfaceId();
            try {
                android.util.Log.v(TAG, "banning player " + playerInterfaceId + " uid:" + i);
                audioPlaybackConfiguration.getPlayerProxy().pause();
            } catch (java.lang.Exception e) {
                android.util.Log.e(TAG, "error banning player " + playerInterfaceId + " uid:" + i, e);
            }
        }
        return z;
    }

    void ignorePlayerIId(int i) {
        synchronized (this.mPlayerLock) {
            this.mDoNotLogPiidList.add(java.lang.Integer.valueOf(i));
        }
    }

    public int trackPlayer(android.media.PlayerBase.PlayerIdCard playerIdCard) {
        int newAudioPlayerId = android.media.AudioSystem.newAudioPlayerId();
        android.media.AudioPlaybackConfiguration audioPlaybackConfiguration = new android.media.AudioPlaybackConfiguration(playerIdCard, newAudioPlayerId, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid());
        audioPlaybackConfiguration.init();
        synchronized (this.mAllowedCapturePolicies) {
            try {
                int clientUid = audioPlaybackConfiguration.getClientUid();
                if (this.mAllowedCapturePolicies.containsKey(java.lang.Integer.valueOf(clientUid))) {
                    updateAllowedCapturePolicy(audioPlaybackConfiguration, this.mAllowedCapturePolicies.get(java.lang.Integer.valueOf(clientUid)).intValue());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        sEventLogger.enqueue(new com.android.server.audio.PlaybackActivityMonitor.NewPlayerEvent(audioPlaybackConfiguration));
        synchronized (this.mPlayerLock) {
            this.mPlayers.put(java.lang.Integer.valueOf(newAudioPlayerId), audioPlaybackConfiguration);
            maybeMutePlayerAwaitingConnection(audioPlaybackConfiguration);
        }
        return newAudioPlayerId;
    }

    public void playerAttributes(int i, @android.annotation.NonNull android.media.AudioAttributes audioAttributes, int i2) {
        boolean z;
        synchronized (this.mAllowedCapturePolicies) {
            try {
                if (this.mAllowedCapturePolicies.containsKey(java.lang.Integer.valueOf(i2)) && audioAttributes.getAllowedCapturePolicy() < this.mAllowedCapturePolicies.get(java.lang.Integer.valueOf(i2)).intValue()) {
                    audioAttributes = new android.media.AudioAttributes.Builder(audioAttributes).setAllowedCapturePolicy(this.mAllowedCapturePolicies.get(java.lang.Integer.valueOf(i2)).intValue()).build();
                }
            } finally {
            }
        }
        synchronized (this.mPlayerLock) {
            try {
                android.media.AudioPlaybackConfiguration audioPlaybackConfiguration = this.mPlayers.get(new java.lang.Integer(i));
                if (checkConfigurationCaller(i, audioPlaybackConfiguration, i2)) {
                    sEventLogger.enqueue(new com.android.server.audio.PlaybackActivityMonitor.AudioAttrEvent(i, audioAttributes));
                    z = audioPlaybackConfiguration.handleAudioAttributesEvent(audioAttributes);
                } else {
                    android.util.Log.e(TAG, "Error updating audio attributes");
                    z = false;
                }
            } finally {
            }
        }
        if (z) {
            dispatchPlaybackChange(false);
        }
    }

    public void playerSessionId(int i, int i2, int i3) {
        boolean z;
        synchronized (this.mPlayerLock) {
            try {
                android.media.AudioPlaybackConfiguration audioPlaybackConfiguration = this.mPlayers.get(new java.lang.Integer(i));
                if (checkConfigurationCaller(i, audioPlaybackConfiguration, i3)) {
                    z = audioPlaybackConfiguration.handleSessionIdEvent(i2);
                } else {
                    android.util.Log.e(TAG, "Error updating audio session");
                    z = false;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            dispatchPlaybackChange(false);
        }
    }

    private void checkVolumeForPrivilegedAlarm(android.media.AudioPlaybackConfiguration audioPlaybackConfiguration, int i) {
        if (i == 5) {
            return;
        }
        if ((i == 2 || audioPlaybackConfiguration.getPlayerState() == 2) && (audioPlaybackConfiguration.getAudioAttributes().getAllFlags() & 192) == 192 && audioPlaybackConfiguration.getAudioAttributes().getUsage() == 4 && this.mContext.checkPermission("android.permission.MODIFY_PHONE_STATE", audioPlaybackConfiguration.getClientPid(), audioPlaybackConfiguration.getClientUid()) == 0) {
            if (i == 2 && audioPlaybackConfiguration.getPlayerState() != 2) {
                int i2 = this.mPrivilegedAlarmActiveCount;
                this.mPrivilegedAlarmActiveCount = i2 + 1;
                if (i2 == 0) {
                    this.mSavedAlarmVolume = android.media.AudioSystem.getStreamVolumeIndex(4, 2);
                    android.media.AudioSystem.setStreamVolumeIndexAS(4, this.mMaxAlarmVolume, 2);
                    return;
                }
                return;
            }
            if (i != 2 && audioPlaybackConfiguration.getPlayerState() == 2) {
                int i3 = this.mPrivilegedAlarmActiveCount - 1;
                this.mPrivilegedAlarmActiveCount = i3;
                if (i3 == 0 && android.media.AudioSystem.getStreamVolumeIndex(4, 2) == this.mMaxAlarmVolume) {
                    android.media.AudioSystem.setStreamVolumeIndexAS(4, this.mSavedAlarmVolume, 2);
                }
            }
        }
    }

    public void playerEvent(int i, int i2, int i3, int i4) {
        boolean z;
        synchronized (this.mPlayerLock) {
            try {
                android.media.AudioPlaybackConfiguration audioPlaybackConfiguration = this.mPlayers.get(new java.lang.Integer(i));
                if (audioPlaybackConfiguration == null) {
                    return;
                }
                boolean contains = this.mDoNotLogPiidList.contains(java.lang.Integer.valueOf(i));
                if (!contains || i2 == 0) {
                    sEventLogger.enqueue(new com.android.server.audio.PlaybackActivityMonitor.PlayerEvent(i, i2, i3));
                    if (i2 == 6) {
                        this.mEventHandler.sendMessage(this.mEventHandler.obtainMessage(2, i3, i));
                        return;
                    }
                    if (i2 == 2) {
                        java.util.Iterator<java.lang.Integer> it = this.mBannedUids.iterator();
                        while (it.hasNext()) {
                            if (checkBanPlayer(audioPlaybackConfiguration, it.next().intValue())) {
                                sEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("not starting piid:" + i + " ,is banned"));
                                return;
                            }
                        }
                    }
                    if (audioPlaybackConfiguration.getPlayerType() != 3 || i2 == 0) {
                        if (checkConfigurationCaller(i, audioPlaybackConfiguration, i4)) {
                            checkVolumeForPrivilegedAlarm(audioPlaybackConfiguration, i2);
                            z = audioPlaybackConfiguration.handleStateEvent(i2, i3);
                        } else {
                            android.util.Log.e(TAG, "Error handling event " + i2);
                            z = false;
                        }
                        if (z) {
                            if (i2 == 2) {
                                this.mDuckingManager.checkDuck(audioPlaybackConfiguration);
                                this.mFadeOutManager.checkFade(audioPlaybackConfiguration);
                            }
                            if (contains) {
                                z = false;
                            }
                        }
                        if (z) {
                            dispatchPlaybackChange(i2 == 0);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void portEvent(int i, int i2, @android.annotation.Nullable android.os.PersistableBundle persistableBundle, int i3) {
        if (!android.os.UserHandle.isCore(i3)) {
            android.util.Log.e(TAG, "Forbidden operation from uid " + i3);
            return;
        }
        synchronized (this.mPlayerLock) {
            try {
                int i4 = this.mPortIdToPiid.get(i, -1);
                if (i4 == -1) {
                    return;
                }
                android.media.AudioPlaybackConfiguration audioPlaybackConfiguration = this.mPlayers.get(java.lang.Integer.valueOf(i4));
                if (audioPlaybackConfiguration == null) {
                    return;
                }
                if (audioPlaybackConfiguration.getPlayerType() == 3) {
                    return;
                }
                if (i2 == 7) {
                    this.mEventHandler.sendMessage(this.mEventHandler.obtainMessage(3, i4, i, persistableBundle));
                } else if (i2 == 8) {
                    this.mEventHandler.sendMessage(this.mEventHandler.obtainMessage(5, i4, i, persistableBundle));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void playerHasOpPlayAudio(int i, boolean z, int i2) {
        sEventLogger.enqueue(new com.android.server.audio.PlaybackActivityMonitor.PlayerOpPlayAudioEvent(i, z, i2));
    }

    public void releasePlayer(int i, int i2) {
        boolean z;
        synchronized (this.mPlayerLock) {
            try {
                android.media.AudioPlaybackConfiguration audioPlaybackConfiguration = this.mPlayers.get(new java.lang.Integer(i));
                z = false;
                if (checkConfigurationCaller(i, audioPlaybackConfiguration, i2)) {
                    sEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("releasing player piid:" + i));
                    this.mPlayers.remove(new java.lang.Integer(i));
                    this.mDuckingManager.removeReleased(audioPlaybackConfiguration);
                    this.mFadeOutManager.removeReleased(audioPlaybackConfiguration);
                    this.mMutedPlayersAwaitingConnection.remove(java.lang.Integer.valueOf(i));
                    checkVolumeForPrivilegedAlarm(audioPlaybackConfiguration, 0);
                    boolean handleStateEvent = audioPlaybackConfiguration.handleStateEvent(0, 0);
                    this.mEventHandler.sendMessage(this.mEventHandler.obtainMessage(4, i, 0));
                    if (!handleStateEvent || !this.mDoNotLogPiidList.contains(java.lang.Integer.valueOf(i))) {
                        z = handleStateEvent;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            dispatchPlaybackChange(true);
        }
    }

    void onAudioServerDied() {
        sEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("clear port id to piid map"));
        synchronized (this.mPlayerLock) {
            this.mPortIdToPiid.clear();
        }
    }

    public void setAllowedCapturePolicy(int i, int i2) {
        synchronized (this.mAllowedCapturePolicies) {
            try {
                if (i2 == 1) {
                    this.mAllowedCapturePolicies.remove(java.lang.Integer.valueOf(i));
                    return;
                }
                this.mAllowedCapturePolicies.put(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
                synchronized (this.mPlayerLock) {
                    try {
                        for (android.media.AudioPlaybackConfiguration audioPlaybackConfiguration : this.mPlayers.values()) {
                            if (audioPlaybackConfiguration.getClientUid() == i) {
                                updateAllowedCapturePolicy(audioPlaybackConfiguration, i2);
                            }
                        }
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    public int getAllowedCapturePolicy(int i) {
        return this.mAllowedCapturePolicies.getOrDefault(java.lang.Integer.valueOf(i), 1).intValue();
    }

    public java.util.HashMap<java.lang.Integer, java.lang.Integer> getAllAllowedCapturePolicies() {
        java.util.HashMap<java.lang.Integer, java.lang.Integer> hashMap;
        synchronized (this.mAllowedCapturePolicies) {
            hashMap = (java.util.HashMap) this.mAllowedCapturePolicies.clone();
        }
        return hashMap;
    }

    private void updateAllowedCapturePolicy(android.media.AudioPlaybackConfiguration audioPlaybackConfiguration, int i) {
        if (audioPlaybackConfiguration.getAudioAttributes().getAllowedCapturePolicy() >= i) {
            return;
        }
        audioPlaybackConfiguration.handleAudioAttributesEvent(new android.media.AudioAttributes.Builder(audioPlaybackConfiguration.getAudioAttributes()).setAllowedCapturePolicy(i).build());
    }

    public void playerDeath(int i) {
        releasePlayer(i, 0);
    }

    public boolean isPlaybackActiveForUid(int i) {
        synchronized (this.mPlayerLock) {
            try {
                for (android.media.AudioPlaybackConfiguration audioPlaybackConfiguration : this.mPlayers.values()) {
                    if (audioPlaybackConfiguration.isActive() && audioPlaybackConfiguration.getClientUid() == i) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean hasActiveMediaPlaybackOnSubmixWithAddress(@android.annotation.NonNull java.lang.String str) {
        synchronized (this.mPlayerLock) {
            try {
                for (android.media.AudioPlaybackConfiguration audioPlaybackConfiguration : this.mPlayers.values()) {
                    android.media.AudioDeviceInfo audioDeviceInfo = audioPlaybackConfiguration.getAudioDeviceInfo();
                    if (audioPlaybackConfiguration.getAudioAttributes().getUsage() == 1 && audioPlaybackConfiguration.isActive() && audioDeviceInfo != null && audioDeviceInfo.getInternalType() == 32768 && str.equals(audioDeviceInfo.getAddress())) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected void dump(java.io.PrintWriter printWriter) {
        printWriter.println("\nPlaybackActivityMonitor dump time: " + java.text.DateFormat.getTimeInstance().format(new java.util.Date()));
        synchronized (this.mPlayerLock) {
            try {
                printWriter.println("\n  playback listeners:");
                java.util.Iterator<com.android.server.audio.PlaybackActivityMonitor.PlayMonitorClient> it = this.mClients.iterator();
                while (it.hasNext()) {
                    com.android.server.audio.PlaybackActivityMonitor.PlayMonitorClient next = it.next();
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append(" ");
                    sb.append(next.isPrivileged() ? "(S)" : "(P)");
                    sb.append(next.toString());
                    printWriter.print(sb.toString());
                }
                printWriter.println("\n");
                printWriter.println("\n  players:");
                java.util.ArrayList arrayList = new java.util.ArrayList(this.mPlayers.keySet());
                java.util.Collections.sort(arrayList);
                java.util.Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    android.media.AudioPlaybackConfiguration audioPlaybackConfiguration = this.mPlayers.get((java.lang.Integer) it2.next());
                    if (audioPlaybackConfiguration != null) {
                        if (this.mDoNotLogPiidList.contains(java.lang.Integer.valueOf(audioPlaybackConfiguration.getPlayerInterfaceId()))) {
                            printWriter.print("(not logged)");
                        }
                        audioPlaybackConfiguration.dump(printWriter);
                    }
                }
                printWriter.println("\n  ducked players piids:");
                this.mDuckingManager.dump(printWriter);
                printWriter.println("\n  faded out players piids:");
                this.mFadeOutManager.dump(printWriter);
                printWriter.print("\n  muted player piids due to call/ring:");
                java.util.Iterator<java.lang.Integer> it3 = this.mMutedPlayers.iterator();
                while (it3.hasNext()) {
                    printWriter.print(" " + it3.next().intValue());
                }
                printWriter.println();
                printWriter.print("\n  banned uids:");
                java.util.Iterator<java.lang.Integer> it4 = this.mBannedUids.iterator();
                while (it4.hasNext()) {
                    printWriter.print(" " + it4.next().intValue());
                }
                printWriter.println("\n");
                printWriter.print("\n  muted players (piids) awaiting device connection:");
                java.util.Iterator<java.lang.Integer> it5 = this.mMutedPlayersAwaitingConnection.iterator();
                while (it5.hasNext()) {
                    printWriter.print(" " + it5.next().intValue());
                }
                printWriter.println("\n");
                printWriter.println("\n  current portId to piid map:");
                for (int i = 0; i < this.mPortIdToPiid.size(); i++) {
                    printWriter.println("  portId: " + this.mPortIdToPiid.keyAt(i) + " piid: " + this.mPortIdToPiid.valueAt(i));
                }
                printWriter.println("\n");
                sEventLogger.dump(printWriter);
            } finally {
            }
        }
        synchronized (this.mAllowedCapturePolicies) {
            try {
                printWriter.println("\n  allowed capture policies:");
                for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> entry : this.mAllowedCapturePolicies.entrySet()) {
                    printWriter.println("  uid: " + entry.getKey() + " policy: " + entry.getValue());
                }
            } finally {
            }
        }
    }

    private static boolean checkConfigurationCaller(int i, android.media.AudioPlaybackConfiguration audioPlaybackConfiguration, int i2) {
        if (audioPlaybackConfiguration == null) {
            return false;
        }
        if (i2 != 0 && audioPlaybackConfiguration.getClientUid() != i2) {
            android.util.Log.e(TAG, "Forbidden operation from uid " + i2 + " for player " + i);
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchPlaybackChange(boolean z) {
        synchronized (this.mPlayerLock) {
            try {
                if (this.mPlayers.isEmpty()) {
                    return;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList(this.mPlayers.values());
                java.util.Iterator<com.android.server.audio.PlaybackActivityMonitor.PlayMonitorClient> it = this.mClients.iterator();
                java.util.ArrayList<android.media.AudioPlaybackConfiguration> arrayList2 = null;
                while (it.hasNext()) {
                    com.android.server.audio.PlaybackActivityMonitor.PlayMonitorClient next = it.next();
                    if (!next.reachedMaxErrorCount()) {
                        if (next.isPrivileged()) {
                            next.dispatchPlaybackConfigChange(arrayList, z);
                        } else {
                            if (arrayList2 == null) {
                                arrayList2 = anonymizeForPublicConsumption(arrayList);
                            }
                            next.dispatchPlaybackConfigChange(arrayList2, false);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private java.util.ArrayList<android.media.AudioPlaybackConfiguration> anonymizeForPublicConsumption(java.util.List<android.media.AudioPlaybackConfiguration> list) {
        java.util.ArrayList<android.media.AudioPlaybackConfiguration> arrayList = new java.util.ArrayList<>();
        for (android.media.AudioPlaybackConfiguration audioPlaybackConfiguration : list) {
            if (audioPlaybackConfiguration.isActive()) {
                arrayList.add(android.media.AudioPlaybackConfiguration.anonymizedCopy(audioPlaybackConfiguration));
            }
        }
        return arrayList;
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public boolean duckPlayers(@android.annotation.NonNull com.android.server.audio.FocusRequester focusRequester, @android.annotation.NonNull com.android.server.audio.FocusRequester focusRequester2, boolean z) {
        synchronized (this.mPlayerLock) {
            try {
                if (this.mPlayers.isEmpty()) {
                    return true;
                }
                java.util.ArrayList<android.media.AudioPlaybackConfiguration> arrayList = new java.util.ArrayList<>();
                for (android.media.AudioPlaybackConfiguration audioPlaybackConfiguration : this.mPlayers.values()) {
                    if (!focusRequester.hasSameUid(audioPlaybackConfiguration.getClientUid()) && focusRequester2.hasSameUid(audioPlaybackConfiguration.getClientUid()) && audioPlaybackConfiguration.getPlayerState() == 2) {
                        if (!z && audioPlaybackConfiguration.getAudioAttributes().getContentType() == 1) {
                            android.util.Log.v(TAG, "not ducking player " + audioPlaybackConfiguration.getPlayerInterfaceId() + " uid:" + audioPlaybackConfiguration.getClientUid() + " pid:" + audioPlaybackConfiguration.getClientPid() + " - SPEECH");
                            return false;
                        }
                        if (com.android.internal.util.ArrayUtils.contains(UNDUCKABLE_PLAYER_TYPES, audioPlaybackConfiguration.getPlayerType())) {
                            android.util.Log.v(TAG, "not ducking player " + audioPlaybackConfiguration.getPlayerInterfaceId() + " uid:" + audioPlaybackConfiguration.getClientUid() + " pid:" + audioPlaybackConfiguration.getClientPid() + " due to type:" + android.media.AudioPlaybackConfiguration.toLogFriendlyPlayerType(audioPlaybackConfiguration.getPlayerType()));
                            return false;
                        }
                        arrayList.add(audioPlaybackConfiguration);
                    }
                }
                this.mDuckingManager.duckUid(focusRequester2.getClientUid(), arrayList, reqCausesStrongDuck(focusRequester));
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean reqCausesStrongDuck(com.android.server.audio.FocusRequester focusRequester) {
        return focusRequester.getGainRequest() == 3 && focusRequester.getAudioAttributes().getUsage() == 16;
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public void restoreVShapedPlayers(@android.annotation.NonNull com.android.server.audio.FocusRequester focusRequester) {
        synchronized (this.mPlayerLock) {
            this.mDuckingManager.unduckUid(focusRequester.getClientUid(), this.mPlayers);
            this.mFadeOutManager.unfadeOutUid(focusRequester.getClientUid(), this.mPlayers);
        }
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public void mutePlayersForCall(int[] iArr) {
        synchronized (this.mPlayerLock) {
            for (java.lang.Integer num : this.mPlayers.keySet()) {
                android.media.AudioPlaybackConfiguration audioPlaybackConfiguration = this.mPlayers.get(num);
                if (audioPlaybackConfiguration != null) {
                    int usage = audioPlaybackConfiguration.getAudioAttributes().getUsage();
                    int length = iArr.length;
                    boolean z = false;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        if (usage != iArr[i]) {
                            i++;
                        } else {
                            z = true;
                            break;
                        }
                    }
                    if (z) {
                        try {
                            sEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("call: muting piid:" + num + " uid:" + audioPlaybackConfiguration.getClientUid()).printLog(TAG));
                            audioPlaybackConfiguration.getPlayerProxy().setVolume(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
                            this.mMutedPlayers.add(new java.lang.Integer(num.intValue()));
                        } catch (java.lang.Exception e) {
                            android.util.Log.e(TAG, "call: error muting player " + num, e);
                        }
                    }
                }
            }
        }
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public void unmutePlayersForCall() {
        synchronized (this.mPlayerLock) {
            try {
                if (this.mMutedPlayers.isEmpty()) {
                    return;
                }
                java.util.Iterator<java.lang.Integer> it = this.mMutedPlayers.iterator();
                while (it.hasNext()) {
                    int intValue = it.next().intValue();
                    android.media.AudioPlaybackConfiguration audioPlaybackConfiguration = this.mPlayers.get(java.lang.Integer.valueOf(intValue));
                    if (audioPlaybackConfiguration != null) {
                        try {
                            sEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("call: unmuting piid:" + intValue).printLog(TAG));
                            audioPlaybackConfiguration.getPlayerProxy().setVolume(1.0f);
                        } catch (java.lang.Exception e) {
                            android.util.Log.e(TAG, "call: error unmuting player " + intValue + " uid:" + audioPlaybackConfiguration.getClientUid(), e);
                        }
                    }
                }
                this.mMutedPlayers.clear();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public boolean fadeOutPlayers(@android.annotation.NonNull com.android.server.audio.FocusRequester focusRequester, @android.annotation.NonNull com.android.server.audio.FocusRequester focusRequester2) {
        synchronized (this.mPlayerLock) {
            try {
                if (this.mPlayers.isEmpty()) {
                    return false;
                }
                if (!this.mFadeOutManager.canCauseFadeOut(focusRequester, focusRequester2)) {
                    return false;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                boolean z = false;
                for (android.media.AudioPlaybackConfiguration audioPlaybackConfiguration : this.mPlayers.values()) {
                    if (!focusRequester.hasSameUid(audioPlaybackConfiguration.getClientUid()) && focusRequester2.hasSameUid(audioPlaybackConfiguration.getClientUid()) && audioPlaybackConfiguration.getPlayerState() == 2) {
                        if (!this.mFadeOutManager.canBeFadedOut(audioPlaybackConfiguration)) {
                            android.util.Log.v(TAG, "not fading out player " + audioPlaybackConfiguration.getPlayerInterfaceId() + " uid:" + audioPlaybackConfiguration.getClientUid() + " pid:" + audioPlaybackConfiguration.getClientPid() + " type:" + android.media.AudioPlaybackConfiguration.toLogFriendlyPlayerType(audioPlaybackConfiguration.getPlayerType()) + " attr:" + audioPlaybackConfiguration.getAudioAttributes());
                            return false;
                        }
                        arrayList.add(audioPlaybackConfiguration);
                        z = true;
                    }
                }
                if (z) {
                    this.mFadeOutManager.fadeOutUid(focusRequester2.getClientUid(), arrayList);
                }
                return z;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public void forgetUid(int i) {
        java.util.HashMap<java.lang.Integer, android.media.AudioPlaybackConfiguration> hashMap;
        synchronized (this.mPlayerLock) {
            hashMap = (java.util.HashMap) this.mPlayers.clone();
        }
        this.mFadeOutManager.unfadeOutUid(i, hashMap);
        this.mDuckingManager.unduckUid(i, hashMap);
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public long getFadeOutDurationMillis(@android.annotation.NonNull android.media.AudioAttributes audioAttributes) {
        return this.mFadeOutManager.getFadeOutDurationOnFocusLossMillis(audioAttributes);
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public long getFadeInDelayForOffendersMillis(@android.annotation.NonNull android.media.AudioAttributes audioAttributes) {
        return this.mFadeOutManager.getFadeInDelayForOffendersMillis(audioAttributes);
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public boolean shouldEnforceFade() {
        return this.mFadeOutManager.isFadeEnabled();
    }

    void registerPlaybackCallback(android.media.IPlaybackConfigDispatcher iPlaybackConfigDispatcher, boolean z) {
        if (iPlaybackConfigDispatcher == null) {
            return;
        }
        com.android.server.audio.PlaybackActivityMonitor.PlayMonitorClient playMonitorClient = new com.android.server.audio.PlaybackActivityMonitor.PlayMonitorClient(iPlaybackConfigDispatcher, z);
        if (playMonitorClient.init()) {
            this.mClients.add(playMonitorClient);
        }
    }

    void unregisterPlaybackCallback(android.media.IPlaybackConfigDispatcher iPlaybackConfigDispatcher) {
        if (iPlaybackConfigDispatcher == null) {
            return;
        }
        java.util.Iterator<com.android.server.audio.PlaybackActivityMonitor.PlayMonitorClient> it = this.mClients.iterator();
        while (it.hasNext()) {
            com.android.server.audio.PlaybackActivityMonitor.PlayMonitorClient next = it.next();
            if (next.equalsDispatcher(iPlaybackConfigDispatcher)) {
                next.release();
                it.remove();
            }
        }
    }

    java.util.List<android.media.AudioPlaybackConfiguration> getActivePlaybackConfigurations(boolean z) {
        synchronized (this.mPlayerLock) {
            try {
                if (z) {
                    return new java.util.ArrayList(this.mPlayers.values());
                }
                return anonymizeForPublicConsumption(new java.util.ArrayList(this.mPlayers.values()));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    int setFadeManagerConfiguration(int i, android.media.FadeManagerConfiguration fadeManagerConfiguration) {
        return this.mFadeOutManager.setFadeManagerConfiguration(fadeManagerConfiguration);
    }

    int clearFadeManagerConfiguration(int i) {
        return this.mFadeOutManager.clearFadeManagerConfiguration();
    }

    android.media.FadeManagerConfiguration getFadeManagerConfiguration(int i) {
        return this.mFadeOutManager.getFadeManagerConfiguration();
    }

    int setTransientFadeManagerConfiguration(int i, android.media.FadeManagerConfiguration fadeManagerConfiguration) {
        return this.mFadeOutManager.setTransientFadeManagerConfiguration(fadeManagerConfiguration);
    }

    int clearTransientFadeManagerConfiguration(int i) {
        return this.mFadeOutManager.clearTransientFadeManagerConfiguration();
    }

    private static final class PlayMonitorClient implements android.os.IBinder.DeathRecipient {
        private static final int MAX_ERRORS = 5;
        static com.android.server.audio.PlaybackActivityMonitor sListenerDeathMonitor;
        private final android.media.IPlaybackConfigDispatcher mDispatcherCb;

        @com.android.internal.annotations.GuardedBy({"this"})
        private final boolean mIsPrivileged;

        @com.android.internal.annotations.GuardedBy({"this"})
        private boolean mIsReleased = false;

        @com.android.internal.annotations.GuardedBy({"this"})
        private int mErrorCount = 0;

        PlayMonitorClient(android.media.IPlaybackConfigDispatcher iPlaybackConfigDispatcher, boolean z) {
            this.mDispatcherCb = iPlaybackConfigDispatcher;
            this.mIsPrivileged = z;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.util.Log.w(com.android.server.audio.PlaybackActivityMonitor.TAG, "client died");
            sListenerDeathMonitor.unregisterPlaybackCallback(this.mDispatcherCb);
        }

        synchronized boolean init() {
            if (this.mIsReleased) {
                return false;
            }
            try {
                this.mDispatcherCb.asBinder().linkToDeath(this, 0);
                return true;
            } catch (android.os.RemoteException e) {
                android.util.Log.w(com.android.server.audio.PlaybackActivityMonitor.TAG, "Could not link to client death", e);
                return false;
            }
        }

        synchronized void release() {
            this.mDispatcherCb.asBinder().unlinkToDeath(this, 0);
            this.mIsReleased = true;
        }

        void dispatchPlaybackConfigChange(java.util.List<android.media.AudioPlaybackConfiguration> list, boolean z) {
            synchronized (this) {
                try {
                    if (this.mIsReleased) {
                        return;
                    }
                    try {
                        this.mDispatcherCb.dispatchPlaybackConfigChange(list, z);
                    } catch (android.os.RemoteException e) {
                        synchronized (this) {
                            this.mErrorCount++;
                            android.util.Log.e(com.android.server.audio.PlaybackActivityMonitor.TAG, "Error (" + this.mErrorCount + ") trying to dispatch playback config change to " + this, e);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        synchronized boolean isPrivileged() {
            return this.mIsPrivileged;
        }

        synchronized boolean reachedMaxErrorCount() {
            return this.mErrorCount >= 5;
        }

        synchronized boolean equalsDispatcher(android.media.IPlaybackConfigDispatcher iPlaybackConfigDispatcher) {
            if (iPlaybackConfigDispatcher == null) {
                return false;
            }
            return iPlaybackConfigDispatcher.asBinder().equals(this.mDispatcherCb.asBinder());
        }
    }

    private static final class DuckingManager {
        private final java.util.HashMap<java.lang.Integer, com.android.server.audio.PlaybackActivityMonitor.DuckingManager.DuckedApp> mDuckers;

        private DuckingManager() {
            this.mDuckers = new java.util.HashMap<>();
        }

        synchronized void duckUid(int i, java.util.ArrayList<android.media.AudioPlaybackConfiguration> arrayList, boolean z) {
            try {
                if (!this.mDuckers.containsKey(java.lang.Integer.valueOf(i))) {
                    this.mDuckers.put(java.lang.Integer.valueOf(i), new com.android.server.audio.PlaybackActivityMonitor.DuckingManager.DuckedApp(i, z));
                }
                com.android.server.audio.PlaybackActivityMonitor.DuckingManager.DuckedApp duckedApp = this.mDuckers.get(java.lang.Integer.valueOf(i));
                java.util.Iterator<android.media.AudioPlaybackConfiguration> it = arrayList.iterator();
                while (it.hasNext()) {
                    duckedApp.addDuck(it.next(), false);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }

        synchronized void unduckUid(int i, java.util.HashMap<java.lang.Integer, android.media.AudioPlaybackConfiguration> hashMap) {
            com.android.server.audio.PlaybackActivityMonitor.DuckingManager.DuckedApp remove = this.mDuckers.remove(java.lang.Integer.valueOf(i));
            if (remove == null) {
                return;
            }
            remove.removeUnduckAll(hashMap);
        }

        synchronized void checkDuck(@android.annotation.NonNull android.media.AudioPlaybackConfiguration audioPlaybackConfiguration) {
            com.android.server.audio.PlaybackActivityMonitor.DuckingManager.DuckedApp duckedApp = this.mDuckers.get(java.lang.Integer.valueOf(audioPlaybackConfiguration.getClientUid()));
            if (duckedApp == null) {
                return;
            }
            duckedApp.addDuck(audioPlaybackConfiguration, true);
        }

        synchronized void dump(java.io.PrintWriter printWriter) {
            java.util.Iterator<com.android.server.audio.PlaybackActivityMonitor.DuckingManager.DuckedApp> it = this.mDuckers.values().iterator();
            while (it.hasNext()) {
                it.next().dump(printWriter);
            }
        }

        synchronized void removeReleased(@android.annotation.NonNull android.media.AudioPlaybackConfiguration audioPlaybackConfiguration) {
            com.android.server.audio.PlaybackActivityMonitor.DuckingManager.DuckedApp duckedApp = this.mDuckers.get(java.lang.Integer.valueOf(audioPlaybackConfiguration.getClientUid()));
            if (duckedApp == null) {
                return;
            }
            duckedApp.removeReleased(audioPlaybackConfiguration);
        }

        private static final class DuckedApp {
            private final java.util.ArrayList<java.lang.Integer> mDuckedPlayers = new java.util.ArrayList<>();
            private final int mUid;
            private final boolean mUseStrongDuck;

            DuckedApp(int i, boolean z) {
                this.mUid = i;
                this.mUseStrongDuck = z;
            }

            void dump(java.io.PrintWriter printWriter) {
                printWriter.print("\t uid:" + this.mUid + " piids:");
                java.util.Iterator<java.lang.Integer> it = this.mDuckedPlayers.iterator();
                while (it.hasNext()) {
                    printWriter.print(" " + it.next().intValue());
                }
                printWriter.println("");
            }

            void addDuck(@android.annotation.NonNull android.media.AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z) {
                int intValue = new java.lang.Integer(audioPlaybackConfiguration.getPlayerInterfaceId()).intValue();
                if (this.mDuckedPlayers.contains(java.lang.Integer.valueOf(intValue))) {
                    return;
                }
                try {
                    android.media.VolumeShaper.Configuration configuration = this.mUseStrongDuck ? com.android.server.audio.PlaybackActivityMonitor.STRONG_DUCK_VSHAPE : com.android.server.audio.PlaybackActivityMonitor.DUCK_VSHAPE;
                    android.media.VolumeShaper.Operation operation = z ? com.android.server.audio.PlaybackActivityMonitor.PLAY_SKIP_RAMP : com.android.server.audio.PlaybackActivityMonitor.PLAY_CREATE_IF_NEEDED;
                    com.android.server.audio.PlaybackActivityMonitor.sEventLogger.enqueue(new com.android.server.audio.PlaybackActivityMonitor.DuckEvent(audioPlaybackConfiguration, z, this.mUseStrongDuck, configuration, operation).printLog(com.android.server.audio.PlaybackActivityMonitor.TAG));
                    audioPlaybackConfiguration.getPlayerProxy().applyVolumeShaper(configuration, operation);
                    this.mDuckedPlayers.add(java.lang.Integer.valueOf(intValue));
                } catch (java.lang.Exception e) {
                    android.util.Log.e(com.android.server.audio.PlaybackActivityMonitor.TAG, "Error ducking player piid:" + intValue + " uid:" + this.mUid, e);
                }
            }

            void removeUnduckAll(java.util.HashMap<java.lang.Integer, android.media.AudioPlaybackConfiguration> hashMap) {
                java.util.Iterator<java.lang.Integer> it = this.mDuckedPlayers.iterator();
                while (it.hasNext()) {
                    int intValue = it.next().intValue();
                    android.media.AudioPlaybackConfiguration audioPlaybackConfiguration = hashMap.get(java.lang.Integer.valueOf(intValue));
                    if (audioPlaybackConfiguration != null) {
                        try {
                            com.android.server.audio.PlaybackActivityMonitor.sEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("unducking piid:" + intValue).printLog(com.android.server.audio.PlaybackActivityMonitor.TAG));
                            audioPlaybackConfiguration.getPlayerProxy().applyVolumeShaper(this.mUseStrongDuck ? com.android.server.audio.PlaybackActivityMonitor.STRONG_DUCK_ID : com.android.server.audio.PlaybackActivityMonitor.DUCK_ID, android.media.VolumeShaper.Operation.REVERSE);
                        } catch (java.lang.Exception e) {
                            android.util.Log.e(com.android.server.audio.PlaybackActivityMonitor.TAG, "Error unducking player piid:" + intValue + " uid:" + this.mUid, e);
                        }
                    }
                }
                this.mDuckedPlayers.clear();
            }

            void removeReleased(@android.annotation.NonNull android.media.AudioPlaybackConfiguration audioPlaybackConfiguration) {
                this.mDuckedPlayers.remove(new java.lang.Integer(audioPlaybackConfiguration.getPlayerInterfaceId()));
            }
        }
    }

    @android.annotation.NonNull
    protected java.util.List<java.lang.Integer> getFocusDuckedUids() {
        java.util.ArrayList arrayList;
        synchronized (this.mPlayerLock) {
            arrayList = new java.util.ArrayList(this.mDuckingManager.mDuckers.keySet());
        }
        return arrayList;
    }

    private static final class PlayerEvent extends com.android.server.utils.EventLogger.Event {
        final int mEvent;
        final int mEventValue;
        final int mPlayerIId;

        PlayerEvent(int i, int i2, int i3) {
            this.mPlayerIId = i;
            this.mEvent = i2;
            this.mEventValue = i3;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public java.lang.String eventToString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder("player piid:");
            sb.append(this.mPlayerIId);
            sb.append(" event:");
            sb.append(android.media.AudioPlaybackConfiguration.toLogFriendlyPlayerState(this.mEvent));
            switch (this.mEvent) {
                case 5:
                    if (this.mEventValue != 0) {
                        sb.append(" deviceId:");
                        sb.append(this.mEventValue);
                    }
                    break;
                case 6:
                    break;
                case 7:
                    sb.append(" source:");
                    if (this.mEventValue <= 0) {
                        sb.append("none ");
                    } else {
                        if ((this.mEventValue & 1) != 0) {
                            sb.append("masterMute ");
                        }
                        if ((this.mEventValue & 2) != 0) {
                            sb.append("streamVolume ");
                        }
                        if ((this.mEventValue & 4) != 0) {
                            sb.append("streamMute ");
                        }
                        if ((this.mEventValue & 8) != 0) {
                            sb.append("appOps ");
                        }
                        if ((this.mEventValue & 16) != 0) {
                            sb.append("clientVolume ");
                        }
                        if ((this.mEventValue & 32) != 0) {
                            sb.append("volumeShaper ");
                        }
                    }
                    break;
            }
            return sb.toString();
        }
    }

    private static final class PlayerOpPlayAudioEvent extends com.android.server.utils.EventLogger.Event {
        final boolean mHasOp;
        final int mPlayerIId;
        final int mUid;

        PlayerOpPlayAudioEvent(int i, boolean z, int i2) {
            this.mPlayerIId = i;
            this.mHasOp = z;
            this.mUid = i2;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public java.lang.String eventToString() {
            return "player piid:" + this.mPlayerIId + " has OP_PLAY_AUDIO:" + this.mHasOp + " in uid:" + this.mUid;
        }
    }

    private static final class NewPlayerEvent extends com.android.server.utils.EventLogger.Event {
        private final int mClientPid;
        private final int mClientUid;
        private final android.media.AudioAttributes mPlayerAttr;
        private final int mPlayerIId;
        private final int mPlayerType;
        private final int mSessionId;

        NewPlayerEvent(android.media.AudioPlaybackConfiguration audioPlaybackConfiguration) {
            this.mPlayerIId = audioPlaybackConfiguration.getPlayerInterfaceId();
            this.mPlayerType = audioPlaybackConfiguration.getPlayerType();
            this.mClientUid = audioPlaybackConfiguration.getClientUid();
            this.mClientPid = audioPlaybackConfiguration.getClientPid();
            this.mPlayerAttr = audioPlaybackConfiguration.getAudioAttributes();
            this.mSessionId = audioPlaybackConfiguration.getSessionId();
        }

        @Override // com.android.server.utils.EventLogger.Event
        public java.lang.String eventToString() {
            return new java.lang.String("new player piid:" + this.mPlayerIId + " uid/pid:" + this.mClientUid + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + this.mClientPid + " type:" + android.media.AudioPlaybackConfiguration.toLogFriendlyPlayerType(this.mPlayerType) + " attr:" + this.mPlayerAttr + " session:" + this.mSessionId);
        }
    }

    private static abstract class VolumeShaperEvent extends com.android.server.utils.EventLogger.Event {
        private final int mClientPid;
        private final int mClientUid;
        private final android.media.VolumeShaper.Configuration mConfig;
        private final android.media.VolumeShaper.Operation mOperation;
        private final android.media.AudioAttributes mPlayerAttr;
        private final int mPlayerIId;
        private final int mPlayerType;
        private final boolean mSkipRamp;

        abstract java.lang.String getVSAction();

        VolumeShaperEvent(@android.annotation.NonNull android.media.AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z, android.media.VolumeShaper.Configuration configuration, android.media.VolumeShaper.Operation operation) {
            this.mPlayerIId = audioPlaybackConfiguration.getPlayerInterfaceId();
            this.mSkipRamp = z;
            this.mClientUid = audioPlaybackConfiguration.getClientUid();
            this.mClientPid = audioPlaybackConfiguration.getClientPid();
            this.mPlayerAttr = audioPlaybackConfiguration.getAudioAttributes();
            this.mPlayerType = audioPlaybackConfiguration.getPlayerType();
            this.mConfig = configuration;
            this.mOperation = operation;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public java.lang.String eventToString() {
            return getVSAction() + " player piid:" + this.mPlayerIId + " uid/pid:" + this.mClientUid + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + this.mClientPid + " skip ramp:" + this.mSkipRamp + " player type:" + android.media.AudioPlaybackConfiguration.toLogFriendlyPlayerType(this.mPlayerType) + " attr:" + this.mPlayerAttr + " config:" + this.mConfig + " operation:" + this.mOperation;
        }
    }

    static final class DuckEvent extends com.android.server.audio.PlaybackActivityMonitor.VolumeShaperEvent {
        final boolean mUseStrongDuck;

        @Override // com.android.server.audio.PlaybackActivityMonitor.VolumeShaperEvent
        java.lang.String getVSAction() {
            return this.mUseStrongDuck ? "ducking (strong)" : "ducking";
        }

        DuckEvent(@android.annotation.NonNull android.media.AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z, boolean z2, android.media.VolumeShaper.Configuration configuration, android.media.VolumeShaper.Operation operation) {
            super(audioPlaybackConfiguration, z, configuration, operation);
            this.mUseStrongDuck = z2;
        }
    }

    static final class FadeOutEvent extends com.android.server.audio.PlaybackActivityMonitor.VolumeShaperEvent {
        @Override // com.android.server.audio.PlaybackActivityMonitor.VolumeShaperEvent
        java.lang.String getVSAction() {
            return com.android.server.audio.PlaybackActivityMonitor.EVENT_TYPE_FADE_OUT;
        }

        FadeOutEvent(@android.annotation.NonNull android.media.AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z, android.media.VolumeShaper.Configuration configuration, android.media.VolumeShaper.Operation operation) {
            super(audioPlaybackConfiguration, z, configuration, operation);
        }
    }

    static final class FadeInEvent extends com.android.server.audio.PlaybackActivityMonitor.VolumeShaperEvent {
        @Override // com.android.server.audio.PlaybackActivityMonitor.VolumeShaperEvent
        java.lang.String getVSAction() {
            return com.android.server.audio.PlaybackActivityMonitor.EVENT_TYPE_FADE_IN;
        }

        FadeInEvent(@android.annotation.NonNull android.media.AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z, android.media.VolumeShaper.Configuration configuration, android.media.VolumeShaper.Operation operation) {
            super(audioPlaybackConfiguration, z, configuration, operation);
        }
    }

    private static final class AudioAttrEvent extends com.android.server.utils.EventLogger.Event {
        private final android.media.AudioAttributes mPlayerAttr;
        private final int mPlayerIId;

        AudioAttrEvent(int i, android.media.AudioAttributes audioAttributes) {
            this.mPlayerIId = i;
            this.mPlayerAttr = audioAttributes;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public java.lang.String eventToString() {
            return new java.lang.String("player piid:" + this.mPlayerIId + " new AudioAttributes:" + this.mPlayerAttr);
        }
    }

    private static final class MuteAwaitConnectionEvent extends com.android.server.utils.EventLogger.Event {

        @android.annotation.NonNull
        private final int[] mUsagesToMute;

        MuteAwaitConnectionEvent(@android.annotation.NonNull int[] iArr) {
            this.mUsagesToMute = iArr;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public java.lang.String eventToString() {
            return "muteAwaitConnection muting usages " + java.util.Arrays.toString(this.mUsagesToMute);
        }
    }

    private static final class PlayerFormatEvent extends com.android.server.utils.EventLogger.Event {
        private final android.media.AudioPlaybackConfiguration.FormatInfo mFormat;
        private final int mPlayerIId;

        PlayerFormatEvent(int i, android.media.AudioPlaybackConfiguration.FormatInfo formatInfo) {
            this.mPlayerIId = i;
            this.mFormat = formatInfo;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public java.lang.String eventToString() {
            return new java.lang.String("player piid:" + this.mPlayerIId + " format update:" + this.mFormat);
        }
    }

    void muteAwaitConnection(@android.annotation.NonNull int[] iArr, @android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes, long j) {
        sEventLogger.enqueueAndLog("muteAwaitConnection() dev:" + audioDeviceAttributes + " timeOutMs:" + j, 0, TAG);
        synchronized (this.mPlayerLock) {
            mutePlayersExpectingDevice(iArr);
            this.mEventHandler.removeMessages(1);
            this.mEventHandler.sendMessageDelayed(this.mEventHandler.obtainMessage(1, audioDeviceAttributes), j);
        }
    }

    void cancelMuteAwaitConnection(java.lang.String str) {
        sEventLogger.enqueueAndLog("cancelMuteAwaitConnection() from:" + str, 0, TAG);
        synchronized (this.mPlayerLock) {
            this.mEventHandler.removeMessages(1);
            unmutePlayersExpectingDevice();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPlayerLock"})
    private void mutePlayersExpectingDevice(@android.annotation.NonNull int[] iArr) {
        sEventLogger.enqueue(new com.android.server.audio.PlaybackActivityMonitor.MuteAwaitConnectionEvent(iArr));
        this.mMutedUsagesAwaitingConnection = iArr;
        java.util.Iterator<java.lang.Integer> it = this.mPlayers.keySet().iterator();
        while (it.hasNext()) {
            android.media.AudioPlaybackConfiguration audioPlaybackConfiguration = this.mPlayers.get(it.next());
            if (audioPlaybackConfiguration != null) {
                maybeMutePlayerAwaitingConnection(audioPlaybackConfiguration);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPlayerLock"})
    private void maybeMutePlayerAwaitingConnection(@android.annotation.NonNull android.media.AudioPlaybackConfiguration audioPlaybackConfiguration) {
        if (this.mMutedUsagesAwaitingConnection == null) {
            return;
        }
        for (int i : this.mMutedUsagesAwaitingConnection) {
            if (i == audioPlaybackConfiguration.getAudioAttributes().getUsage()) {
                try {
                    sEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("awaiting connection: muting piid:" + audioPlaybackConfiguration.getPlayerInterfaceId() + " uid:" + audioPlaybackConfiguration.getClientUid()).printLog(TAG));
                    audioPlaybackConfiguration.getPlayerProxy().applyVolumeShaper(MUTE_AWAIT_CONNECTION_VSHAPE, PLAY_SKIP_RAMP);
                    this.mMutedPlayersAwaitingConnection.add(java.lang.Integer.valueOf(audioPlaybackConfiguration.getPlayerInterfaceId()));
                } catch (java.lang.Exception e) {
                    android.util.Log.e(TAG, "awaiting connection: error muting player " + audioPlaybackConfiguration.getPlayerInterfaceId(), e);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPlayerLock"})
    public void unmutePlayersExpectingDevice() {
        this.mMutedUsagesAwaitingConnection = null;
        java.util.Iterator<java.lang.Integer> it = this.mMutedPlayersAwaitingConnection.iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            android.media.AudioPlaybackConfiguration audioPlaybackConfiguration = this.mPlayers.get(java.lang.Integer.valueOf(intValue));
            if (audioPlaybackConfiguration != null) {
                try {
                    sEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("unmuting piid:" + intValue).printLog(TAG));
                    audioPlaybackConfiguration.getPlayerProxy().applyVolumeShaper(MUTE_AWAIT_CONNECTION_VSHAPE, android.media.VolumeShaper.Operation.REVERSE);
                } catch (java.lang.Exception e) {
                    android.util.Log.e(TAG, "Error unmuting player " + intValue + " uid:" + audioPlaybackConfiguration.getClientUid(), e);
                }
            }
        }
        this.mMutedPlayersAwaitingConnection.clear();
    }

    private void initEventHandler() {
        this.mEventThread = new android.os.HandlerThread(TAG);
        this.mEventThread.start();
        this.mEventHandler = new android.os.Handler(this.mEventThread.getLooper()) { // from class: com.android.server.audio.PlaybackActivityMonitor.1
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                android.media.AudioPlaybackConfiguration audioPlaybackConfiguration;
                android.media.AudioPlaybackConfiguration audioPlaybackConfiguration2;
                switch (message.what) {
                    case 1:
                        com.android.server.audio.PlaybackActivityMonitor.sEventLogger.enqueueAndLog("Timeout for muting waiting for " + ((android.media.AudioDeviceAttributes) message.obj) + ", unmuting", 0, com.android.server.audio.PlaybackActivityMonitor.TAG);
                        synchronized (com.android.server.audio.PlaybackActivityMonitor.this.mPlayerLock) {
                            com.android.server.audio.PlaybackActivityMonitor.this.unmutePlayersExpectingDevice();
                        }
                        com.android.server.audio.PlaybackActivityMonitor.this.mMuteAwaitConnectionTimeoutCb.accept((android.media.AudioDeviceAttributes) message.obj);
                        return;
                    case 2:
                        synchronized (com.android.server.audio.PlaybackActivityMonitor.this.mPlayerLock) {
                            com.android.server.audio.PlaybackActivityMonitor.this.mPortIdToPiid.put(message.arg1, message.arg2);
                        }
                        return;
                    case 3:
                        android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) message.obj;
                        if (persistableBundle == null) {
                            android.util.Log.w(com.android.server.audio.PlaybackActivityMonitor.TAG, "Received mute event with no extras");
                            return;
                        }
                        int i = persistableBundle.getInt("android.media.extra.PLAYER_EVENT_MUTE");
                        synchronized (com.android.server.audio.PlaybackActivityMonitor.this.mPlayerLock) {
                            try {
                                int i2 = message.arg1;
                                com.android.server.audio.PlaybackActivityMonitor.sEventLogger.enqueue(new com.android.server.audio.PlaybackActivityMonitor.PlayerEvent(i2, 7, i));
                                synchronized (com.android.server.audio.PlaybackActivityMonitor.this.mPlayerLock) {
                                    audioPlaybackConfiguration = (android.media.AudioPlaybackConfiguration) com.android.server.audio.PlaybackActivityMonitor.this.mPlayers.get(java.lang.Integer.valueOf(i2));
                                }
                                if (audioPlaybackConfiguration != null && audioPlaybackConfiguration.handleMutedEvent(i)) {
                                    com.android.server.audio.PlaybackActivityMonitor.this.dispatchPlaybackChange(false);
                                    return;
                                }
                                return;
                            } finally {
                            }
                        }
                    case 4:
                        int i3 = message.arg1;
                        if (i3 == -1) {
                            android.util.Log.w(com.android.server.audio.PlaybackActivityMonitor.TAG, "Received clear ports with invalid piid");
                            return;
                        }
                        synchronized (com.android.server.audio.PlaybackActivityMonitor.this.mPlayerLock) {
                            while (true) {
                                try {
                                    int indexOfValue = com.android.server.audio.PlaybackActivityMonitor.this.mPortIdToPiid.indexOfValue(i3);
                                    if (indexOfValue >= 0) {
                                        com.android.server.audio.PlaybackActivityMonitor.this.mPortIdToPiid.removeAt(indexOfValue);
                                    }
                                } finally {
                                }
                            }
                        }
                        return;
                    case 5:
                        android.os.PersistableBundle persistableBundle2 = (android.os.PersistableBundle) message.obj;
                        if (persistableBundle2 == null) {
                            android.util.Log.w(com.android.server.audio.PlaybackActivityMonitor.TAG, "Received format event with no extras");
                            return;
                        }
                        android.media.AudioPlaybackConfiguration.FormatInfo formatInfo = new android.media.AudioPlaybackConfiguration.FormatInfo(persistableBundle2.getBoolean("android.media.extra.PLAYER_EVENT_SPATIALIZED", false), persistableBundle2.getInt("android.media.extra.PLAYER_EVENT_CHANNEL_MASK", 0), persistableBundle2.getInt("android.media.extra.PLAYER_EVENT_SAMPLE_RATE", 0));
                        com.android.server.audio.PlaybackActivityMonitor.sEventLogger.enqueue(new com.android.server.audio.PlaybackActivityMonitor.PlayerFormatEvent(message.arg1, formatInfo));
                        synchronized (com.android.server.audio.PlaybackActivityMonitor.this.mPlayerLock) {
                            audioPlaybackConfiguration2 = (android.media.AudioPlaybackConfiguration) com.android.server.audio.PlaybackActivityMonitor.this.mPlayers.get(java.lang.Integer.valueOf(message.arg1));
                        }
                        if (audioPlaybackConfiguration2 != null && audioPlaybackConfiguration2.handleFormatEvent(formatInfo)) {
                            com.android.server.audio.PlaybackActivityMonitor.this.dispatchPlaybackChange(false);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        };
    }
}
