package android.telecom.Logging;

/* loaded from: classes3.dex */
public class Session {
    public static final java.lang.String CONTINUE_SUBSESSION = "CONTINUE_SUBSESSION";
    public static final java.lang.String CREATE_SUBSESSION = "CREATE_SUBSESSION";
    public static final java.lang.String END_SESSION = "END_SESSION";
    public static final java.lang.String END_SUBSESSION = "END_SUBSESSION";
    public static final java.lang.String EXTERNAL_INDICATOR = "E-";
    public static final java.lang.String LOG_TAG = "Session";
    private static final int SESSION_RECURSION_LIMIT = 25;
    public static final java.lang.String SESSION_SEPARATION_CHAR_CHILD = "_";
    public static final java.lang.String START_EXTERNAL_SESSION = "START_EXTERNAL_SESSION";
    public static final java.lang.String START_SESSION = "START_SESSION";
    public static final java.lang.String SUBSESSION_SEPARATION_CHAR = "->";
    public static final java.lang.String TRUNCATE_STRING = "...";
    public static final int UNDEFINED = -1;
    private java.util.ArrayList<android.telecom.Logging.Session> mChildSessions;
    private long mExecutionStartTimeMs;
    private java.lang.String mFullMethodPathCache;
    private boolean mIsStartedFromActiveSession;
    private java.lang.String mOwnerInfo;
    private android.telecom.Logging.Session mParentSession;
    private java.lang.String mSessionId;
    private java.lang.String mShortMethodName;
    private long mExecutionEndTimeMs = -1;
    private boolean mIsCompleted = false;
    private boolean mIsExternal = false;
    private int mChildCounter = 0;

    public static class Info implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.telecom.Logging.Session.Info> CREATOR = new android.os.Parcelable.Creator<android.telecom.Logging.Session.Info>() { // from class: android.telecom.Logging.Session.Info.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telecom.Logging.Session.Info createFromParcel(android.os.Parcel parcel) {
                return new android.telecom.Logging.Session.Info(parcel.readString(), parcel.readString(), parcel.readString());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telecom.Logging.Session.Info[] newArray(int i) {
                return new android.telecom.Logging.Session.Info[i];
            }
        };
        public final java.lang.String methodPath;
        public final java.lang.String ownerInfo;
        public final java.lang.String sessionId;

        private Info(java.lang.String str, java.lang.String str2, java.lang.String str3) {
            this.sessionId = str;
            this.methodPath = str2;
            this.ownerInfo = str3;
        }

        public static android.telecom.Logging.Session.Info getInfo(android.telecom.Logging.Session session) {
            return new android.telecom.Logging.Session.Info(session.getFullSessionId(), session.getFullMethodPath(!android.telecom.Log.DEBUG && session.isSessionExternal()), session.getOwnerInfo());
        }

        public static android.telecom.Logging.Session.Info getExternalInfo(android.telecom.Logging.Session session, java.lang.String str) {
            if (str != null && session.getOwnerInfo() != null) {
                str = session.getOwnerInfo() + "/" + str;
            } else if (str == null) {
                str = session.getOwnerInfo();
            }
            return new android.telecom.Logging.Session.Info(session.getFullSessionId(), session.getFullMethodPath(!android.telecom.Log.DEBUG && session.isSessionExternal()), str);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(this.sessionId);
            parcel.writeString(this.methodPath);
            parcel.writeString(this.ownerInfo);
        }
    }

    public Session(java.lang.String str, java.lang.String str2, long j, boolean z, java.lang.String str3) {
        this.mIsStartedFromActiveSession = false;
        setSessionId(str);
        setShortMethodName(str2);
        this.mExecutionStartTimeMs = j;
        this.mParentSession = null;
        this.mChildSessions = new java.util.ArrayList<>(5);
        this.mIsStartedFromActiveSession = z;
        this.mOwnerInfo = str3;
    }

    public void setSessionId(java.lang.String str) {
        if (str == null) {
            this.mSessionId = "?";
        }
        this.mSessionId = str;
    }

    public java.lang.String getShortMethodName() {
        return this.mShortMethodName;
    }

    public void setShortMethodName(java.lang.String str) {
        if (str == null) {
            str = "";
        }
        this.mShortMethodName = str;
    }

    public void setIsExternal(boolean z) {
        this.mIsExternal = z;
    }

    public boolean isExternal() {
        return this.mIsExternal;
    }

    public void setParentSession(android.telecom.Logging.Session session) {
        this.mParentSession = session;
    }

    public void addChild(android.telecom.Logging.Session session) {
        if (session != null) {
            this.mChildSessions.add(session);
        }
    }

    public void removeChild(android.telecom.Logging.Session session) {
        if (session != null) {
            this.mChildSessions.remove(session);
        }
    }

    public long getExecutionStartTimeMilliseconds() {
        return this.mExecutionStartTimeMs;
    }

    public void setExecutionStartTimeMs(long j) {
        this.mExecutionStartTimeMs = j;
    }

    public android.telecom.Logging.Session getParentSession() {
        return this.mParentSession;
    }

    public java.util.ArrayList<android.telecom.Logging.Session> getChildSessions() {
        return this.mChildSessions;
    }

    public boolean isSessionCompleted() {
        return this.mIsCompleted;
    }

    public boolean isStartedFromActiveSession() {
        return this.mIsStartedFromActiveSession;
    }

    public android.telecom.Logging.Session.Info getInfo() {
        return android.telecom.Logging.Session.Info.getInfo(this);
    }

    public android.telecom.Logging.Session.Info getExternalInfo(java.lang.String str) {
        return android.telecom.Logging.Session.Info.getExternalInfo(this, str);
    }

    public java.lang.String getOwnerInfo() {
        return this.mOwnerInfo;
    }

    public java.lang.String getSessionId() {
        return this.mSessionId;
    }

    public void markSessionCompleted(long j) {
        this.mExecutionEndTimeMs = j;
        this.mIsCompleted = true;
    }

    public long getLocalExecutionTime() {
        if (this.mExecutionEndTimeMs == -1) {
            return -1L;
        }
        return this.mExecutionEndTimeMs - this.mExecutionStartTimeMs;
    }

    public synchronized java.lang.String getNextChildId() {
        int i;
        i = this.mChildCounter;
        this.mChildCounter = i + 1;
        return java.lang.String.valueOf(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String getFullSessionId() {
        return getFullSessionId(0);
    }

    private java.lang.String getFullSessionId(int i) {
        if (i >= 25) {
            android.util.Slog.w(LOG_TAG, "getFullSessionId: Hit recursion limit!");
            return TRUNCATE_STRING + this.mSessionId;
        }
        android.telecom.Logging.Session session = this.mParentSession;
        if (session == null) {
            return this.mSessionId;
        }
        if (android.telecom.Log.VERBOSE) {
            return session.getFullSessionId(i + 1) + SESSION_SEPARATION_CHAR_CHILD + this.mSessionId;
        }
        return session.getFullSessionId(i + 1);
    }

    private android.telecom.Logging.Session getRootSession(java.lang.String str) {
        int i = 0;
        android.telecom.Logging.Session session = this;
        while (true) {
            if (session.getParentSession() == null) {
                break;
            }
            if (i >= 25) {
                android.util.Slog.w(LOG_TAG, "getRootSession: Hit recursion limit from " + str);
                break;
            }
            session = session.getParentSession();
            i++;
        }
        return session;
    }

    public java.lang.String printFullSessionTree() {
        return getRootSession("printFullSessionTree").printSessionTree();
    }

    private java.lang.String printSessionTree() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        printSessionTree(0, sb, 0);
        return sb.toString();
    }

    private void printSessionTree(int i, java.lang.StringBuilder sb, int i2) {
        if (i2 >= 25) {
            android.util.Slog.w(LOG_TAG, "printSessionTree: Hit recursion limit!");
            sb.append(TRUNCATE_STRING);
            return;
        }
        sb.append(toString());
        java.util.Iterator<android.telecom.Logging.Session> it = this.mChildSessions.iterator();
        while (it.hasNext()) {
            android.telecom.Logging.Session next = it.next();
            sb.append("\n");
            for (int i3 = 0; i3 <= i; i3++) {
                sb.append("\t");
            }
            next.printSessionTree(i + 1, sb, i2 + 1);
        }
    }

    public java.lang.String getFullMethodPath(boolean z) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        getFullMethodPath(sb, z, 0);
        return sb.toString();
    }

    private synchronized void getFullMethodPath(java.lang.StringBuilder sb, boolean z, int i) {
        if (i >= 25) {
            android.util.Slog.w(LOG_TAG, "getFullMethodPath: Hit recursion limit!");
            sb.append(TRUNCATE_STRING);
            return;
        }
        if (!android.text.TextUtils.isEmpty(this.mFullMethodPathCache) && !z) {
            sb.append(this.mFullMethodPathCache);
            return;
        }
        android.telecom.Logging.Session parentSession = getParentSession();
        if (parentSession != null) {
            r1 = this.mShortMethodName.equals(parentSession.mShortMethodName) ? false : true;
            parentSession.getFullMethodPath(sb, z, i + 1);
            sb.append(SUBSESSION_SEPARATION_CHAR);
        }
        if (isExternal()) {
            if (z) {
                sb.append(TRUNCATE_STRING);
            } else {
                sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
                sb.append(this.mShortMethodName);
                sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
        } else {
            sb.append(this.mShortMethodName);
        }
        if (r1 && !z) {
            this.mFullMethodPathCache = sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSessionExternal() {
        return getRootSession("isSessionExternal").isExternal();
    }

    public int hashCode() {
        return ((((((((((((((((((this.mSessionId != null ? this.mSessionId.hashCode() : 0) * 31) + (this.mShortMethodName != null ? this.mShortMethodName.hashCode() : 0)) * 31) + ((int) (this.mExecutionStartTimeMs ^ (this.mExecutionStartTimeMs >>> 32)))) * 31) + ((int) (this.mExecutionEndTimeMs ^ (this.mExecutionEndTimeMs >>> 32)))) * 31) + (this.mParentSession != null ? this.mParentSession.hashCode() : 0)) * 31) + (this.mChildSessions != null ? this.mChildSessions.hashCode() : 0)) * 31) + (this.mIsCompleted ? 1 : 0)) * 31) + this.mChildCounter) * 31) + (this.mIsStartedFromActiveSession ? 1 : 0)) * 31) + (this.mOwnerInfo != null ? this.mOwnerInfo.hashCode() : 0);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telecom.Logging.Session session = (android.telecom.Logging.Session) obj;
        if (this.mExecutionStartTimeMs != session.mExecutionStartTimeMs || this.mExecutionEndTimeMs != session.mExecutionEndTimeMs || this.mIsCompleted != session.mIsCompleted || this.mChildCounter != session.mChildCounter || this.mIsStartedFromActiveSession != session.mIsStartedFromActiveSession) {
            return false;
        }
        if (this.mSessionId == null ? session.mSessionId != null : !this.mSessionId.equals(session.mSessionId)) {
            return false;
        }
        if (this.mShortMethodName == null ? session.mShortMethodName != null : !this.mShortMethodName.equals(session.mShortMethodName)) {
            return false;
        }
        if (this.mParentSession == null ? session.mParentSession != null : !this.mParentSession.equals(session.mParentSession)) {
            return false;
        }
        if (this.mChildSessions == null ? session.mChildSessions != null : !this.mChildSessions.equals(session.mChildSessions)) {
            return false;
        }
        if (this.mOwnerInfo != null) {
            return this.mOwnerInfo.equals(session.mOwnerInfo);
        }
        if (session.mOwnerInfo == null) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        android.telecom.Logging.Session session;
        if (getParentSession() != null && isStartedFromActiveSession()) {
            session = getRootSession("toString");
        } else {
            session = this;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(session.getFullMethodPath(false));
        if (session.getOwnerInfo() != null && !session.getOwnerInfo().isEmpty()) {
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
            sb.append(session.getOwnerInfo());
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        return sb.toString() + "@" + session.getFullSessionId();
    }
}
