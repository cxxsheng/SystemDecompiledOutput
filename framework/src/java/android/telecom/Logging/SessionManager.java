package android.telecom.Logging;

/* loaded from: classes3.dex */
public class SessionManager {
    private static final long DEFAULT_SESSION_TIMEOUT_MS = 30000;
    private static final java.lang.String LOGGING_TAG = "Logging";
    private static final long SESSION_ID_ROLLOVER_THRESHOLD = 262144;
    private static final java.lang.String TIMEOUTS_PREFIX = "telecom.";
    private android.content.Context mContext;
    private int sCodeEntryCounter = 0;
    public java.util.concurrent.ConcurrentHashMap<java.lang.Integer, android.telecom.Logging.Session> mSessionMapper = new java.util.concurrent.ConcurrentHashMap<>(100);
    public java.lang.Runnable mCleanStaleSessions = new java.lang.Runnable() { // from class: android.telecom.Logging.SessionManager$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            android.telecom.Logging.SessionManager.this.lambda$new$0();
        }
    };
    private android.os.Handler mSessionCleanupHandler = new android.os.Handler(android.os.Looper.getMainLooper());
    public android.telecom.Logging.SessionManager.ICurrentThreadId mCurrentThreadId = new android.telecom.Logging.SessionManager.ICurrentThreadId() { // from class: android.telecom.Logging.SessionManager$$ExternalSyntheticLambda1
        @Override // android.telecom.Logging.SessionManager.ICurrentThreadId
        public final int get() {
            return android.os.Process.myTid();
        }
    };
    private android.telecom.Logging.SessionManager.ISessionCleanupTimeoutMs mSessionCleanupTimeoutMs = new android.telecom.Logging.SessionManager.ISessionCleanupTimeoutMs() { // from class: android.telecom.Logging.SessionManager$$ExternalSyntheticLambda2
        @Override // android.telecom.Logging.SessionManager.ISessionCleanupTimeoutMs
        public final long get() {
            long lambda$new$1;
            lambda$new$1 = android.telecom.Logging.SessionManager.this.lambda$new$1();
            return lambda$new$1;
        }
    };
    private java.util.List<android.telecom.Logging.SessionManager.ISessionListener> mSessionListeners = new java.util.ArrayList();

    public interface ICurrentThreadId {
        int get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    interface ISessionCleanupTimeoutMs {
        long get();
    }

    public interface ISessionIdQueryHandler {
        java.lang.String getSessionId();
    }

    public interface ISessionListener {
        void sessionComplete(java.lang.String str, long j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        cleanupStaleSessions(getSessionCleanupTimeoutMs());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ long lambda$new$1() {
        if (this.mContext == null) {
            return 30000L;
        }
        return getCleanupTimeout(this.mContext);
    }

    public void setContext(android.content.Context context) {
        this.mContext = context;
    }

    private long getSessionCleanupTimeoutMs() {
        return this.mSessionCleanupTimeoutMs.get();
    }

    private synchronized void resetStaleSessionTimer() {
        this.mSessionCleanupHandler.removeCallbacksAndMessages(null);
        if (this.mCleanStaleSessions != null) {
            this.mSessionCleanupHandler.postDelayed(this.mCleanStaleSessions, getSessionCleanupTimeoutMs());
        }
    }

    public synchronized void startSession(android.telecom.Logging.Session.Info info, java.lang.String str, java.lang.String str2) {
        if (info == null) {
            startSession(str, str2);
        } else {
            startExternalSession(info, str);
        }
    }

    public synchronized void startSession(java.lang.String str, java.lang.String str2) {
        resetStaleSessionTimer();
        int callingThreadId = getCallingThreadId();
        if (this.mSessionMapper.get(java.lang.Integer.valueOf(callingThreadId)) != null) {
            continueSession(createSubsession(true), str);
            return;
        }
        android.telecom.Log.d(LOGGING_TAG, android.telecom.Logging.Session.START_SESSION, new java.lang.Object[0]);
        this.mSessionMapper.put(java.lang.Integer.valueOf(callingThreadId), new android.telecom.Logging.Session(getNextSessionID(), str, java.lang.System.currentTimeMillis(), false, str2));
    }

    public synchronized void startExternalSession(android.telecom.Logging.Session.Info info, java.lang.String str) {
        if (info == null) {
            return;
        }
        int callingThreadId = getCallingThreadId();
        if (this.mSessionMapper.get(java.lang.Integer.valueOf(callingThreadId)) != null) {
            android.telecom.Log.w(LOGGING_TAG, "trying to start an external session with a session already active.", new java.lang.Object[0]);
            return;
        }
        android.telecom.Log.d(LOGGING_TAG, android.telecom.Logging.Session.START_EXTERNAL_SESSION, new java.lang.Object[0]);
        android.telecom.Logging.Session session = new android.telecom.Logging.Session(android.telecom.Logging.Session.EXTERNAL_INDICATOR + info.sessionId, info.methodPath, java.lang.System.currentTimeMillis(), false, info.ownerInfo);
        session.setIsExternal(true);
        session.markSessionCompleted(-1L);
        this.mSessionMapper.put(java.lang.Integer.valueOf(callingThreadId), session);
        continueSession(createSubsession(), str);
    }

    public android.telecom.Logging.Session createSubsession() {
        return createSubsession(false);
    }

    public synchronized android.telecom.Logging.Session createSubsession(boolean z) {
        android.telecom.Logging.Session session = this.mSessionMapper.get(java.lang.Integer.valueOf(getCallingThreadId()));
        if (session == null) {
            android.telecom.Log.d(LOGGING_TAG, "Log.createSubsession was called with no session active.", new java.lang.Object[0]);
            return null;
        }
        android.telecom.Logging.Session session2 = new android.telecom.Logging.Session(session.getNextChildId(), session.getShortMethodName(), java.lang.System.currentTimeMillis(), z, session.getOwnerInfo());
        session.addChild(session2);
        session2.setParentSession(session);
        if (!z) {
            android.telecom.Log.v(LOGGING_TAG, "CREATE_SUBSESSION " + session2.toString(), new java.lang.Object[0]);
        } else {
            android.telecom.Log.v(LOGGING_TAG, "CREATE_SUBSESSION (Invisible subsession)", new java.lang.Object[0]);
        }
        return session2;
    }

    public synchronized android.telecom.Logging.Session.Info getExternalSession() {
        return getExternalSession(null);
    }

    public synchronized android.telecom.Logging.Session.Info getExternalSession(java.lang.String str) {
        android.telecom.Logging.Session session = this.mSessionMapper.get(java.lang.Integer.valueOf(getCallingThreadId()));
        if (session == null) {
            android.telecom.Log.d(LOGGING_TAG, "Log.getExternalSession was called with no session active.", new java.lang.Object[0]);
            return null;
        }
        return session.getExternalInfo(str);
    }

    public synchronized void cancelSubsession(android.telecom.Logging.Session session) {
        if (session == null) {
            return;
        }
        session.markSessionCompleted(-1L);
        endParentSessions(session);
    }

    public synchronized void continueSession(android.telecom.Logging.Session session, java.lang.String str) {
        if (session == null) {
            return;
        }
        resetStaleSessionTimer();
        session.setShortMethodName(str);
        session.setExecutionStartTimeMs(java.lang.System.currentTimeMillis());
        if (session.getParentSession() == null) {
            android.telecom.Log.i(LOGGING_TAG, "Log.continueSession was called with no session active for method " + str, new java.lang.Object[0]);
            return;
        }
        this.mSessionMapper.put(java.lang.Integer.valueOf(getCallingThreadId()), session);
        if (!session.isStartedFromActiveSession()) {
            android.telecom.Log.v(LOGGING_TAG, android.telecom.Logging.Session.CONTINUE_SUBSESSION, new java.lang.Object[0]);
        } else {
            android.telecom.Log.v(LOGGING_TAG, "CONTINUE_SUBSESSION (Invisible Subsession) with Method " + str, new java.lang.Object[0]);
        }
    }

    public synchronized void endSession() {
        int callingThreadId = getCallingThreadId();
        android.telecom.Logging.Session session = this.mSessionMapper.get(java.lang.Integer.valueOf(callingThreadId));
        if (session == null) {
            android.telecom.Log.w(LOGGING_TAG, "Log.endSession was called with no session active.", new java.lang.Object[0]);
            return;
        }
        session.markSessionCompleted(java.lang.System.currentTimeMillis());
        if (!session.isStartedFromActiveSession()) {
            android.telecom.Log.v(LOGGING_TAG, "END_SUBSESSION (dur: " + session.getLocalExecutionTime() + " mS)", new java.lang.Object[0]);
        } else {
            android.telecom.Log.v(LOGGING_TAG, "END_SUBSESSION (Invisible Subsession) (dur: " + session.getLocalExecutionTime() + " ms)", new java.lang.Object[0]);
        }
        android.telecom.Logging.Session parentSession = session.getParentSession();
        this.mSessionMapper.remove(java.lang.Integer.valueOf(callingThreadId));
        endParentSessions(session);
        if (parentSession != null && !parentSession.isSessionCompleted() && session.isStartedFromActiveSession()) {
            this.mSessionMapper.put(java.lang.Integer.valueOf(callingThreadId), parentSession);
        }
    }

    private void endParentSessions(android.telecom.Logging.Session session) {
        if (!session.isSessionCompleted() || session.getChildSessions().size() != 0) {
            return;
        }
        android.telecom.Logging.Session parentSession = session.getParentSession();
        if (parentSession != null) {
            session.setParentSession(null);
            parentSession.removeChild(session);
            if (parentSession.isExternal()) {
                notifySessionCompleteListeners(session.getShortMethodName(), java.lang.System.currentTimeMillis() - session.getExecutionStartTimeMilliseconds());
            }
            endParentSessions(parentSession);
            return;
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis() - session.getExecutionStartTimeMilliseconds();
        android.telecom.Log.d(LOGGING_TAG, "END_SESSION (dur: " + currentTimeMillis + " ms): " + session.toString(), new java.lang.Object[0]);
        if (!session.isExternal()) {
            notifySessionCompleteListeners(session.getShortMethodName(), currentTimeMillis);
        }
    }

    private void notifySessionCompleteListeners(java.lang.String str, long j) {
        java.util.Iterator<android.telecom.Logging.SessionManager.ISessionListener> it = this.mSessionListeners.iterator();
        while (it.hasNext()) {
            it.next().sessionComplete(str, j);
        }
    }

    public java.lang.String getSessionId() {
        android.telecom.Logging.Session session = this.mSessionMapper.get(java.lang.Integer.valueOf(getCallingThreadId()));
        return session != null ? session.toString() : "";
    }

    public synchronized void registerSessionListener(android.telecom.Logging.SessionManager.ISessionListener iSessionListener) {
        if (iSessionListener != null) {
            this.mSessionListeners.add(iSessionListener);
        }
    }

    private synchronized java.lang.String getNextSessionID() {
        java.lang.Integer valueOf;
        int i = this.sCodeEntryCounter;
        this.sCodeEntryCounter = i + 1;
        valueOf = java.lang.Integer.valueOf(i);
        if (valueOf.intValue() >= 262144) {
            restartSessionCounter();
            int i2 = this.sCodeEntryCounter;
            this.sCodeEntryCounter = i2 + 1;
            valueOf = java.lang.Integer.valueOf(i2);
        }
        return getBase64Encoding(valueOf.intValue());
    }

    private synchronized void restartSessionCounter() {
        this.sCodeEntryCounter = 0;
    }

    private java.lang.String getBase64Encoding(int i) {
        return android.util.Base64.encodeToString(java.util.Arrays.copyOfRange(java.nio.ByteBuffer.allocate(4).putInt(i).array(), 2, 4), 3);
    }

    private int getCallingThreadId() {
        return this.mCurrentThreadId.get();
    }

    public synchronized java.lang.String printActiveSessions() {
        java.lang.StringBuilder sb;
        sb = new java.lang.StringBuilder();
        java.util.Iterator<java.util.Map.Entry<java.lang.Integer, android.telecom.Logging.Session>> it = this.mSessionMapper.entrySet().iterator();
        while (it.hasNext()) {
            sb.append(it.next().getValue().printFullSessionTree());
            sb.append("\n");
        }
        return sb.toString();
    }

    public synchronized void cleanupStaleSessions(long j) {
        java.lang.String str = "Stale Sessions Cleaned:\n";
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        java.util.Iterator<java.util.Map.Entry<java.lang.Integer, android.telecom.Logging.Session>> it = this.mSessionMapper.entrySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            android.telecom.Logging.Session value = it.next().getValue();
            if (currentTimeMillis - value.getExecutionStartTimeMilliseconds() > j) {
                it.remove();
                str = str + value.printFullSessionTree() + "\n";
                z = true;
            }
        }
        if (z) {
            android.telecom.Log.w(LOGGING_TAG, str, new java.lang.Object[0]);
        } else {
            android.telecom.Log.v(LOGGING_TAG, "No stale logging sessions needed to be cleaned...", new java.lang.Object[0]);
        }
    }

    private long getCleanupTimeout(android.content.Context context) {
        android.content.ContentResolver contentResolver = context.getContentResolver();
        return android.provider.Settings.Secure.getLongForUser(contentResolver, "telecom.stale_session_cleanup_timeout_millis", 30000L, contentResolver.getUserId());
    }
}
