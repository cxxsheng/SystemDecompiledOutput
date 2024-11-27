package com.android.server.media;

/* loaded from: classes2.dex */
class MediaSessionStack {
    private static final boolean DEBUG = com.android.server.media.MediaSessionService.DEBUG;
    private static final java.lang.String TAG = "MediaSessionStack";
    private final com.android.server.media.AudioPlayerStateMonitor mAudioPlayerStateMonitor;
    private com.android.server.media.MediaSessionRecordImpl mMediaButtonSession;
    private final com.android.server.media.MediaSessionStack.OnMediaButtonSessionChangedListener mOnMediaButtonSessionChangedListener;
    private final java.util.List<com.android.server.media.MediaSessionRecordImpl> mSessions = new java.util.ArrayList();
    private final android.util.SparseArray<java.util.List<com.android.server.media.MediaSessionRecord>> mCachedActiveLists = new android.util.SparseArray<>();

    interface OnMediaButtonSessionChangedListener {
        void onMediaButtonSessionChanged(com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl, com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl2);
    }

    MediaSessionStack(com.android.server.media.AudioPlayerStateMonitor audioPlayerStateMonitor, com.android.server.media.MediaSessionStack.OnMediaButtonSessionChangedListener onMediaButtonSessionChangedListener) {
        this.mAudioPlayerStateMonitor = audioPlayerStateMonitor;
        this.mOnMediaButtonSessionChangedListener = onMediaButtonSessionChangedListener;
    }

    public void addSession(com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl) {
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("addSession to bottom of stack | record: %s", new java.lang.Object[]{mediaSessionRecordImpl}));
        this.mSessions.add(mediaSessionRecordImpl);
        clearCache(mediaSessionRecordImpl.getUserId());
        updateMediaButtonSessionIfNeeded();
    }

    public void removeSession(com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl) {
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("removeSession | record: %s", new java.lang.Object[]{mediaSessionRecordImpl}));
        this.mSessions.remove(mediaSessionRecordImpl);
        if (this.mMediaButtonSession == mediaSessionRecordImpl) {
            updateMediaButtonSession(null);
        }
        clearCache(mediaSessionRecordImpl.getUserId());
    }

    public boolean contains(com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl) {
        return this.mSessions.contains(mediaSessionRecordImpl);
    }

    public com.android.server.media.MediaSessionRecord getMediaSessionRecord(android.media.session.MediaSession.Token token) {
        for (com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl : this.mSessions) {
            if (mediaSessionRecordImpl instanceof com.android.server.media.MediaSessionRecord) {
                com.android.server.media.MediaSessionRecord mediaSessionRecord = (com.android.server.media.MediaSessionRecord) mediaSessionRecordImpl;
                if (java.util.Objects.equals(mediaSessionRecord.getSessionToken(), token)) {
                    return mediaSessionRecord;
                }
            }
        }
        return null;
    }

    public void onPlaybackStateChanged(com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl, boolean z) {
        com.android.server.media.MediaSessionRecordImpl findMediaButtonSession;
        if (z) {
            android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("onPlaybackStateChanged - Pushing session to top | record: %s", new java.lang.Object[]{mediaSessionRecordImpl}));
            this.mSessions.remove(mediaSessionRecordImpl);
            this.mSessions.add(0, mediaSessionRecordImpl);
            clearCache(mediaSessionRecordImpl.getUserId());
        }
        if (this.mMediaButtonSession != null && this.mMediaButtonSession.getUid() == mediaSessionRecordImpl.getUid() && (findMediaButtonSession = findMediaButtonSession(this.mMediaButtonSession.getUid())) != this.mMediaButtonSession && (findMediaButtonSession.getSessionPolicies() & 2) == 0) {
            updateMediaButtonSession(findMediaButtonSession);
        }
    }

    public void onSessionActiveStateChanged(com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl) {
        clearCache(mediaSessionRecordImpl.getUserId());
    }

    public void updateMediaButtonSessionIfNeeded() {
        if (DEBUG) {
            android.util.Log.d(TAG, "updateMediaButtonSessionIfNeeded, callers=" + getCallers(2));
        }
        java.util.List<java.lang.Integer> sortedAudioPlaybackClientUids = this.mAudioPlayerStateMonitor.getSortedAudioPlaybackClientUids();
        for (int i = 0; i < sortedAudioPlaybackClientUids.size(); i++) {
            int intValue = sortedAudioPlaybackClientUids.get(i).intValue();
            com.android.server.media.MediaSessionRecordImpl findMediaButtonSession = findMediaButtonSession(intValue);
            if (findMediaButtonSession == null) {
                if (DEBUG) {
                    android.util.Log.d(TAG, "updateMediaButtonSessionIfNeeded, skipping uid=" + intValue);
                }
            } else {
                boolean z = (findMediaButtonSession.getSessionPolicies() & 2) != 0;
                if (DEBUG) {
                    android.util.Log.d(TAG, "updateMediaButtonSessionIfNeeded, checking uid=" + intValue + ", mediaButtonSession=" + findMediaButtonSession + ", ignoreButtonSession=" + z);
                }
                if (!z) {
                    this.mAudioPlayerStateMonitor.cleanUpAudioPlaybackUids(findMediaButtonSession.getUid());
                    if (findMediaButtonSession != this.mMediaButtonSession) {
                        updateMediaButtonSession(findMediaButtonSession);
                        return;
                    }
                    return;
                }
            }
        }
    }

    public void updateMediaButtonSessionBySessionPolicyChange(com.android.server.media.MediaSessionRecord mediaSessionRecord) {
        if ((mediaSessionRecord.getSessionPolicies() & 2) != 0) {
            if (mediaSessionRecord == this.mMediaButtonSession) {
                updateMediaButtonSession(null);
                return;
            }
            return;
        }
        updateMediaButtonSessionIfNeeded();
    }

    private com.android.server.media.MediaSessionRecordImpl findMediaButtonSession(int i) {
        com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl = null;
        for (com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl2 : this.mSessions) {
            if (!(mediaSessionRecordImpl2 instanceof com.android.server.media.MediaSession2Record) && i == mediaSessionRecordImpl2.getUid()) {
                if (mediaSessionRecordImpl2.checkPlaybackActiveState(this.mAudioPlayerStateMonitor.isPlaybackActive(mediaSessionRecordImpl2.getUid()))) {
                    return mediaSessionRecordImpl2;
                }
                if (mediaSessionRecordImpl == null) {
                    mediaSessionRecordImpl = mediaSessionRecordImpl2;
                }
            }
        }
        return mediaSessionRecordImpl;
    }

    public java.util.List<com.android.server.media.MediaSessionRecord> getActiveSessions(int i) {
        java.util.List<com.android.server.media.MediaSessionRecord> list = this.mCachedActiveLists.get(i);
        if (list == null) {
            java.util.List<com.android.server.media.MediaSessionRecord> priorityList = getPriorityList(true, i);
            this.mCachedActiveLists.put(i, priorityList);
            return priorityList;
        }
        return list;
    }

    public java.util.List<android.media.Session2Token> getSession2Tokens(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl : this.mSessions) {
            if (i == -1 || mediaSessionRecordImpl.getUserId() == i) {
                if (mediaSessionRecordImpl.isActive() && (mediaSessionRecordImpl instanceof com.android.server.media.MediaSession2Record)) {
                    arrayList.add(((com.android.server.media.MediaSession2Record) mediaSessionRecordImpl).getSession2Token());
                }
            }
        }
        return arrayList;
    }

    public com.android.server.media.MediaSessionRecordImpl getMediaButtonSession() {
        return this.mMediaButtonSession;
    }

    public void updateMediaButtonSession(com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl) {
        com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl2 = this.mMediaButtonSession;
        this.mMediaButtonSession = mediaSessionRecordImpl;
        this.mOnMediaButtonSessionChangedListener.onMediaButtonSessionChanged(mediaSessionRecordImpl2, mediaSessionRecordImpl);
    }

    public com.android.server.media.MediaSessionRecordImpl getDefaultVolumeSession() {
        java.util.List<com.android.server.media.MediaSessionRecord> priorityList = getPriorityList(true, -1);
        int size = priorityList.size();
        for (int i = 0; i < size; i++) {
            com.android.server.media.MediaSessionRecord mediaSessionRecord = priorityList.get(i);
            if (mediaSessionRecord.checkPlaybackActiveState(true) && mediaSessionRecord.canHandleVolumeKey()) {
                return mediaSessionRecord;
            }
        }
        return null;
    }

    public com.android.server.media.MediaSessionRecordImpl getDefaultRemoteSession(int i) {
        java.util.List<com.android.server.media.MediaSessionRecord> priorityList = getPriorityList(true, i);
        int size = priorityList.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.media.MediaSessionRecord mediaSessionRecord = priorityList.get(i2);
            if (!mediaSessionRecord.isPlaybackTypeLocal()) {
                return mediaSessionRecord;
            }
        }
        return null;
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "Media button session is " + this.mMediaButtonSession);
        printWriter.println(str + "Sessions Stack - have " + this.mSessions.size() + " sessions:");
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str);
        sb.append("  ");
        java.lang.String sb2 = sb.toString();
        java.util.Iterator<com.android.server.media.MediaSessionRecordImpl> it = this.mSessions.iterator();
        while (it.hasNext()) {
            it.next().dump(printWriter, sb2);
        }
    }

    public java.util.List<com.android.server.media.MediaSessionRecord> getPriorityList(boolean z, int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 0;
        int i3 = 0;
        for (com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl : this.mSessions) {
            if (mediaSessionRecordImpl instanceof com.android.server.media.MediaSessionRecord) {
                com.android.server.media.MediaSessionRecord mediaSessionRecord = (com.android.server.media.MediaSessionRecord) mediaSessionRecordImpl;
                if (i == -1 || i == mediaSessionRecord.getUserId()) {
                    if (!mediaSessionRecord.isActive()) {
                        if (!z) {
                            arrayList.add(mediaSessionRecord);
                        }
                    } else if (mediaSessionRecord.checkPlaybackActiveState(true)) {
                        arrayList.add(i3, mediaSessionRecord);
                        i2++;
                        i3++;
                    } else {
                        arrayList.add(i2, mediaSessionRecord);
                        i2++;
                    }
                }
            }
        }
        return arrayList;
    }

    private void clearCache(int i) {
        this.mCachedActiveLists.remove(i);
        this.mCachedActiveLists.remove(-1);
    }

    private static java.lang.String getCallers(int i) {
        java.lang.StackTraceElement[] stackTrace = java.lang.Thread.currentThread().getStackTrace();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(getCaller(stackTrace, i2));
            sb.append(" ");
        }
        return sb.toString();
    }

    private static java.lang.String getCaller(java.lang.StackTraceElement[] stackTraceElementArr, int i) {
        int i2 = i + 4;
        if (i2 >= stackTraceElementArr.length) {
            return "<bottom of call stack>";
        }
        java.lang.StackTraceElement stackTraceElement = stackTraceElementArr[i2];
        return stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + ":" + stackTraceElement.getLineNumber();
    }
}
