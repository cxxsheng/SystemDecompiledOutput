package com.android.server.media;

/* loaded from: classes2.dex */
class AudioPlayerStateMonitor {
    private static final boolean DEBUG = com.android.server.media.MediaSessionService.DEBUG;
    private static java.lang.String TAG = "AudioPlayerStateMonitor";
    private static com.android.server.media.AudioPlayerStateMonitor sInstance;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<com.android.server.media.AudioPlayerStateMonitor.OnAudioPlayerActiveStateChangedListener, com.android.server.media.AudioPlayerStateMonitor.MessageHandler> mListenerMap = new android.util.ArrayMap();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final java.util.Set<java.lang.Integer> mActiveAudioUids = new android.util.ArraySet();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    android.util.ArrayMap<java.lang.Integer, android.media.AudioPlaybackConfiguration> mPrevActiveAudioPlaybackConfigs = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final java.util.List<java.lang.Integer> mSortedAudioPlaybackClientUids = new java.util.ArrayList();

    interface OnAudioPlayerActiveStateChangedListener {
        void onAudioPlayerActiveStateChanged(@android.annotation.NonNull android.media.AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z);
    }

    private static final class MessageHandler extends android.os.Handler {
        private static final int MSG_AUDIO_PLAYER_ACTIVE_STATE_CHANGED = 1;
        private final com.android.server.media.AudioPlayerStateMonitor.OnAudioPlayerActiveStateChangedListener mListener;

        MessageHandler(android.os.Looper looper, com.android.server.media.AudioPlayerStateMonitor.OnAudioPlayerActiveStateChangedListener onAudioPlayerActiveStateChangedListener) {
            super(looper);
            this.mListener = onAudioPlayerActiveStateChangedListener;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    this.mListener.onAudioPlayerActiveStateChanged((android.media.AudioPlaybackConfiguration) message.obj, message.arg1 != 0);
                    break;
            }
        }

        void sendAudioPlayerActiveStateChangedMessage(android.media.AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z) {
            obtainMessage(1, z ? 1 : 0, 0, audioPlaybackConfiguration).sendToTarget();
        }
    }

    static com.android.server.media.AudioPlayerStateMonitor getInstance(android.content.Context context) {
        com.android.server.media.AudioPlayerStateMonitor audioPlayerStateMonitor;
        synchronized (com.android.server.media.AudioPlayerStateMonitor.class) {
            try {
                if (sInstance == null) {
                    sInstance = new com.android.server.media.AudioPlayerStateMonitor(context);
                }
                audioPlayerStateMonitor = sInstance;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return audioPlayerStateMonitor;
    }

    private AudioPlayerStateMonitor(android.content.Context context) {
        ((android.media.AudioManager) context.getSystemService("audio")).registerAudioPlaybackCallback(new com.android.server.media.AudioPlayerStateMonitor.AudioManagerPlaybackListener(), null);
    }

    public void registerListener(com.android.server.media.AudioPlayerStateMonitor.OnAudioPlayerActiveStateChangedListener onAudioPlayerActiveStateChangedListener, android.os.Handler handler) {
        synchronized (this.mLock) {
            try {
                this.mListenerMap.put(onAudioPlayerActiveStateChangedListener, new com.android.server.media.AudioPlayerStateMonitor.MessageHandler(handler == null ? android.os.Looper.myLooper() : handler.getLooper(), onAudioPlayerActiveStateChangedListener));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void unregisterListener(com.android.server.media.AudioPlayerStateMonitor.OnAudioPlayerActiveStateChangedListener onAudioPlayerActiveStateChangedListener) {
        synchronized (this.mLock) {
            this.mListenerMap.remove(onAudioPlayerActiveStateChangedListener);
        }
    }

    public java.util.List<java.lang.Integer> getSortedAudioPlaybackClientUids() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            arrayList.addAll(this.mSortedAudioPlaybackClientUids);
        }
        return arrayList;
    }

    public boolean hasUidPlayedAudioLast(int i) {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = false;
                if (!this.mSortedAudioPlaybackClientUids.isEmpty() && i == this.mSortedAudioPlaybackClientUids.get(0).intValue()) {
                    z = true;
                }
            } finally {
            }
        }
        return z;
    }

    public boolean isPlaybackActive(int i) {
        boolean contains;
        synchronized (this.mLock) {
            contains = this.mActiveAudioUids.contains(java.lang.Integer.valueOf(i));
        }
        return contains;
    }

    public void cleanUpAudioPlaybackUids(int i) {
        synchronized (this.mLock) {
            try {
                int identifier = android.os.UserHandle.getUserHandleForUid(i).getIdentifier();
                for (int size = this.mSortedAudioPlaybackClientUids.size() - 1; size >= 0 && this.mSortedAudioPlaybackClientUids.get(size).intValue() != i; size--) {
                    int intValue = this.mSortedAudioPlaybackClientUids.get(size).intValue();
                    if (identifier == android.os.UserHandle.getUserHandleForUid(intValue).getIdentifier() && !isPlaybackActive(intValue)) {
                        this.mSortedAudioPlaybackClientUids.remove(size);
                    }
                }
            } finally {
            }
        }
    }

    public void dump(android.content.Context context, java.io.PrintWriter printWriter, java.lang.String str) {
        synchronized (this.mLock) {
            try {
                printWriter.println(str + "Audio playback (lastly played comes first)");
                java.lang.String str2 = str + "  ";
                for (int i = 0; i < this.mSortedAudioPlaybackClientUids.size(); i++) {
                    int intValue = this.mSortedAudioPlaybackClientUids.get(i).intValue();
                    printWriter.print(str2 + "uid=" + intValue + " packages=");
                    java.lang.String[] packagesForUid = context.getPackageManager().getPackagesForUid(intValue);
                    if (packagesForUid != null && packagesForUid.length > 0) {
                        for (java.lang.String str3 : packagesForUid) {
                            printWriter.print(str3 + " ");
                        }
                    }
                    printWriter.println();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void sendAudioPlayerActiveStateChangedMessageLocked(android.media.AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z) {
        java.util.Iterator<com.android.server.media.AudioPlayerStateMonitor.MessageHandler> it = this.mListenerMap.values().iterator();
        while (it.hasNext()) {
            it.next().sendAudioPlayerActiveStateChangedMessage(audioPlaybackConfiguration, z);
        }
    }

    private class AudioManagerPlaybackListener extends android.media.AudioManager.AudioPlaybackCallback {
        private AudioManagerPlaybackListener() {
        }

        @Override // android.media.AudioManager.AudioPlaybackCallback
        public void onPlaybackConfigChanged(java.util.List<android.media.AudioPlaybackConfiguration> list) {
            int i;
            synchronized (com.android.server.media.AudioPlayerStateMonitor.this.mLock) {
                try {
                    com.android.server.media.AudioPlayerStateMonitor.this.mActiveAudioUids.clear();
                    android.util.ArrayMap<java.lang.Integer, android.media.AudioPlaybackConfiguration> arrayMap = new android.util.ArrayMap<>();
                    for (android.media.AudioPlaybackConfiguration audioPlaybackConfiguration : list) {
                        if (audioPlaybackConfiguration.isActive()) {
                            com.android.server.media.AudioPlayerStateMonitor.this.mActiveAudioUids.add(java.lang.Integer.valueOf(audioPlaybackConfiguration.getClientUid()));
                            arrayMap.put(java.lang.Integer.valueOf(audioPlaybackConfiguration.getPlayerInterfaceId()), audioPlaybackConfiguration);
                        }
                    }
                    for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                        android.media.AudioPlaybackConfiguration valueAt = arrayMap.valueAt(i2);
                        int clientUid = valueAt.getClientUid();
                        if (!com.android.server.media.AudioPlayerStateMonitor.this.mPrevActiveAudioPlaybackConfigs.containsKey(java.lang.Integer.valueOf(valueAt.getPlayerInterfaceId()))) {
                            if (com.android.server.media.AudioPlayerStateMonitor.DEBUG) {
                                android.util.Log.d(com.android.server.media.AudioPlayerStateMonitor.TAG, "Found a new active media playback. " + valueAt);
                            }
                            int indexOf = com.android.server.media.AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids.indexOf(java.lang.Integer.valueOf(clientUid));
                            if (indexOf != 0) {
                                if (indexOf > 0) {
                                    com.android.server.media.AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids.remove(indexOf);
                                }
                                com.android.server.media.AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids.add(0, java.lang.Integer.valueOf(clientUid));
                            }
                        }
                    }
                    if (com.android.server.media.AudioPlayerStateMonitor.this.mActiveAudioUids.size() > 0 && !com.android.server.media.AudioPlayerStateMonitor.this.mActiveAudioUids.contains(com.android.server.media.AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids.get(0))) {
                        int i3 = 1;
                        while (true) {
                            if (i3 >= com.android.server.media.AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids.size()) {
                                i3 = -1;
                                i = -1;
                                break;
                            } else {
                                i = com.android.server.media.AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids.get(i3).intValue();
                                if (com.android.server.media.AudioPlayerStateMonitor.this.mActiveAudioUids.contains(java.lang.Integer.valueOf(i))) {
                                    break;
                                } else {
                                    i3++;
                                }
                            }
                        }
                        while (i3 > 0) {
                            com.android.server.media.AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids.set(i3, com.android.server.media.AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids.get(i3 - 1));
                            i3--;
                        }
                        com.android.server.media.AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids.set(0, java.lang.Integer.valueOf(i));
                    }
                    for (android.media.AudioPlaybackConfiguration audioPlaybackConfiguration2 : list) {
                        if ((com.android.server.media.AudioPlayerStateMonitor.this.mPrevActiveAudioPlaybackConfigs.remove(java.lang.Integer.valueOf(audioPlaybackConfiguration2.getPlayerInterfaceId())) != null) != audioPlaybackConfiguration2.isActive()) {
                            com.android.server.media.AudioPlayerStateMonitor.this.sendAudioPlayerActiveStateChangedMessageLocked(audioPlaybackConfiguration2, false);
                        }
                    }
                    java.util.Iterator<android.media.AudioPlaybackConfiguration> it = com.android.server.media.AudioPlayerStateMonitor.this.mPrevActiveAudioPlaybackConfigs.values().iterator();
                    while (it.hasNext()) {
                        com.android.server.media.AudioPlayerStateMonitor.this.sendAudioPlayerActiveStateChangedMessageLocked(it.next(), true);
                    }
                    com.android.server.media.AudioPlayerStateMonitor.this.mPrevActiveAudioPlaybackConfigs = arrayMap;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
