package android.telecom;

/* loaded from: classes3.dex */
public class Log {
    public static boolean DEBUG = false;
    public static boolean ERROR = false;
    private static final int EVENTS_TO_CACHE = 10;
    private static final int EVENTS_TO_CACHE_DEBUG = 20;
    private static final long EXTENDED_LOGGING_DURATION_MILLIS = 1800000;
    private static final boolean FORCE_LOGGING = false;
    public static boolean INFO;
    private static final int NUM_DIALABLE_DIGITS_TO_LOG;
    public static java.lang.String TAG;
    private static final boolean USER_BUILD;
    public static boolean VERBOSE;
    public static boolean WARN;
    private static android.telecom.Logging.EventManager sEventManager;
    private static boolean sIsUnitTestingEnabled;
    private static boolean sIsUserExtendedLoggingEnabled;
    private static java.lang.Object sLock;
    private static android.telecom.Logging.SessionManager sSessionManager;
    private static final java.lang.Object sSingletonSync;
    private static long sUserExtendedLoggingStopTime;

    static {
        NUM_DIALABLE_DIGITS_TO_LOG = android.os.Build.IS_USER ? 0 : 2;
        TAG = "TelecomFramework";
        DEBUG = isLoggable(3);
        INFO = isLoggable(4);
        VERBOSE = isLoggable(2);
        WARN = isLoggable(5);
        ERROR = isLoggable(6);
        USER_BUILD = android.os.Build.IS_USER;
        sSingletonSync = new java.lang.Object();
        sLock = null;
        sIsUserExtendedLoggingEnabled = false;
        sIsUnitTestingEnabled = false;
        sUserExtendedLoggingStopTime = 0L;
    }

    private Log() {
    }

    public static void d(java.lang.String str, java.lang.String str2, java.lang.Object... objArr) {
        if (sIsUserExtendedLoggingEnabled) {
            maybeDisableLogging();
            android.util.Slog.i(TAG, buildMessage(str, str2, objArr));
        } else if (DEBUG) {
            android.util.Slog.d(TAG, buildMessage(str, str2, objArr));
        }
    }

    public static void d(java.lang.Object obj, java.lang.String str, java.lang.Object... objArr) {
        if (sIsUserExtendedLoggingEnabled) {
            maybeDisableLogging();
            android.util.Slog.i(TAG, buildMessage(getPrefixFromObject(obj), str, objArr));
        } else if (DEBUG) {
            android.util.Slog.d(TAG, buildMessage(getPrefixFromObject(obj), str, objArr));
        }
    }

    public static void i(java.lang.String str, java.lang.String str2, java.lang.Object... objArr) {
        if (INFO) {
            android.util.Slog.i(TAG, buildMessage(str, str2, objArr));
        }
    }

    public static void i(java.lang.Object obj, java.lang.String str, java.lang.Object... objArr) {
        if (INFO) {
            android.util.Slog.i(TAG, buildMessage(getPrefixFromObject(obj), str, objArr));
        }
    }

    public static void v(java.lang.String str, java.lang.String str2, java.lang.Object... objArr) {
        if (sIsUserExtendedLoggingEnabled) {
            maybeDisableLogging();
            android.util.Slog.i(TAG, buildMessage(str, str2, objArr));
        } else if (VERBOSE) {
            android.util.Slog.v(TAG, buildMessage(str, str2, objArr));
        }
    }

    public static void v(java.lang.Object obj, java.lang.String str, java.lang.Object... objArr) {
        if (sIsUserExtendedLoggingEnabled) {
            maybeDisableLogging();
            android.util.Slog.i(TAG, buildMessage(getPrefixFromObject(obj), str, objArr));
        } else if (VERBOSE) {
            android.util.Slog.v(TAG, buildMessage(getPrefixFromObject(obj), str, objArr));
        }
    }

    public static void w(java.lang.String str, java.lang.String str2, java.lang.Object... objArr) {
        if (WARN) {
            android.util.Slog.w(TAG, buildMessage(str, str2, objArr));
        }
    }

    public static void w(java.lang.Object obj, java.lang.String str, java.lang.Object... objArr) {
        if (WARN) {
            android.util.Slog.w(TAG, buildMessage(getPrefixFromObject(obj), str, objArr));
        }
    }

    public static void e(java.lang.String str, java.lang.Throwable th, java.lang.String str2, java.lang.Object... objArr) {
        if (ERROR) {
            android.util.Slog.e(TAG, buildMessage(str, str2, objArr), th);
        }
    }

    public static void e(java.lang.Object obj, java.lang.Throwable th, java.lang.String str, java.lang.Object... objArr) {
        if (ERROR) {
            android.util.Slog.e(TAG, buildMessage(getPrefixFromObject(obj), str, objArr), th);
        }
    }

    public static void wtf(java.lang.String str, java.lang.Throwable th, java.lang.String str2, java.lang.Object... objArr) {
        android.util.Slog.wtf(TAG, buildMessage(str, str2, objArr), th);
    }

    public static void wtf(java.lang.Object obj, java.lang.Throwable th, java.lang.String str, java.lang.Object... objArr) {
        android.util.Slog.wtf(TAG, buildMessage(getPrefixFromObject(obj), str, objArr), th);
    }

    public static void wtf(java.lang.String str, java.lang.String str2, java.lang.Object... objArr) {
        java.lang.String buildMessage = buildMessage(str, str2, objArr);
        android.util.Slog.wtf(TAG, buildMessage, new java.lang.IllegalStateException(buildMessage));
    }

    public static void wtf(java.lang.Object obj, java.lang.String str, java.lang.Object... objArr) {
        java.lang.String buildMessage = buildMessage(getPrefixFromObject(obj), str, objArr);
        android.util.Slog.wtf(TAG, buildMessage, new java.lang.IllegalStateException(buildMessage));
    }

    public static void setSessionContext(android.content.Context context) {
        getSessionManager().setContext(context);
    }

    public static void startSession(java.lang.String str) {
        getSessionManager().startSession(str, null);
    }

    public static void startSession(android.telecom.Logging.Session.Info info, java.lang.String str) {
        getSessionManager().startSession(info, str, null);
    }

    public static void startSession(java.lang.String str, java.lang.String str2) {
        getSessionManager().startSession(str, str2);
    }

    public static void startSession(android.telecom.Logging.Session.Info info, java.lang.String str, java.lang.String str2) {
        getSessionManager().startSession(info, str, str2);
    }

    public static android.telecom.Logging.Session createSubsession() {
        return getSessionManager().createSubsession();
    }

    public static android.telecom.Logging.Session.Info getExternalSession() {
        return getSessionManager().getExternalSession();
    }

    public static android.telecom.Logging.Session.Info getExternalSession(java.lang.String str) {
        return getSessionManager().getExternalSession(str);
    }

    public static void cancelSubsession(android.telecom.Logging.Session session) {
        getSessionManager().cancelSubsession(session);
    }

    public static void continueSession(android.telecom.Logging.Session session, java.lang.String str) {
        getSessionManager().continueSession(session, str);
    }

    public static void endSession() {
        getSessionManager().endSession();
    }

    public static void registerSessionListener(android.telecom.Logging.SessionManager.ISessionListener iSessionListener) {
        getSessionManager().registerSessionListener(iSessionListener);
    }

    public static java.lang.String getSessionId() {
        synchronized (sSingletonSync) {
            if (sSessionManager != null) {
                return getSessionManager().getSessionId();
            }
            return "";
        }
    }

    public static void addEvent(android.telecom.Logging.EventManager.Loggable loggable, java.lang.String str) {
        getEventManager().event(loggable, str, null);
    }

    public static void addEvent(android.telecom.Logging.EventManager.Loggable loggable, java.lang.String str, java.lang.Object obj) {
        getEventManager().event(loggable, str, obj);
    }

    public static void addEvent(android.telecom.Logging.EventManager.Loggable loggable, java.lang.String str, java.lang.String str2, java.lang.Object... objArr) {
        getEventManager().event(loggable, str, str2, objArr);
    }

    public static void registerEventListener(android.telecom.Logging.EventManager.EventListener eventListener) {
        getEventManager().registerEventListener(eventListener);
    }

    public static void addRequestResponsePair(android.telecom.Logging.EventManager.TimedEventPair timedEventPair) {
        getEventManager().addRequestResponsePair(timedEventPair);
    }

    public static void dumpEvents(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (sSingletonSync) {
            if (sEventManager != null) {
                getEventManager().dumpEvents(indentingPrintWriter);
            } else {
                indentingPrintWriter.println("No Historical Events Logged.");
            }
        }
    }

    public static void dumpEventsTimeline(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (sSingletonSync) {
            if (sEventManager != null) {
                getEventManager().dumpEventsTimeline(indentingPrintWriter);
            } else {
                indentingPrintWriter.println("No Historical Events Logged.");
            }
        }
    }

    public static void setIsExtendedLoggingEnabled(boolean z) {
        if (sIsUserExtendedLoggingEnabled == z) {
            return;
        }
        if (sEventManager != null) {
            sEventManager.changeEventCacheSize(z ? 20 : 10);
        }
        sIsUserExtendedLoggingEnabled = z;
        if (sIsUserExtendedLoggingEnabled) {
            sUserExtendedLoggingStopTime = java.lang.System.currentTimeMillis() + 1800000;
        } else {
            sUserExtendedLoggingStopTime = 0L;
        }
    }

    public static void setUnitTestingEnabled(boolean z) {
        sIsUnitTestingEnabled = z;
    }

    public static boolean isUnitTestingEnabled() {
        return sIsUnitTestingEnabled;
    }

    private static android.telecom.Logging.EventManager getEventManager() {
        if (sEventManager == null) {
            synchronized (sSingletonSync) {
                if (sEventManager == null) {
                    sEventManager = new android.telecom.Logging.EventManager(new android.telecom.Logging.SessionManager.ISessionIdQueryHandler() { // from class: android.telecom.Log$$ExternalSyntheticLambda1
                        @Override // android.telecom.Logging.SessionManager.ISessionIdQueryHandler
                        public final java.lang.String getSessionId() {
                            return android.telecom.Log.getSessionId();
                        }
                    });
                    return sEventManager;
                }
            }
        }
        return sEventManager;
    }

    public static android.telecom.Logging.SessionManager getSessionManager() {
        if (sSessionManager == null) {
            synchronized (sSingletonSync) {
                if (sSessionManager == null) {
                    sSessionManager = new android.telecom.Logging.SessionManager();
                    return sSessionManager;
                }
            }
        }
        return sSessionManager;
    }

    public static void setTag(java.lang.String str) {
        TAG = str;
        DEBUG = isLoggable(3);
        INFO = isLoggable(4);
        VERBOSE = isLoggable(2);
        WARN = isLoggable(5);
        ERROR = isLoggable(6);
    }

    public static void setLock(java.lang.Object obj) {
        if (!android.os.Build.IS_USER) {
            sLock = obj;
        }
    }

    private static void maybeDisableLogging() {
        if (sIsUserExtendedLoggingEnabled && sUserExtendedLoggingStopTime < java.lang.System.currentTimeMillis()) {
            sUserExtendedLoggingStopTime = 0L;
            sIsUserExtendedLoggingEnabled = false;
        }
    }

    public static boolean isLoggable(int i) {
        return android.util.Log.isLoggable(TAG, i);
    }

    public static java.lang.String piiHandle(java.lang.Object obj) {
        if (obj == null || VERBOSE) {
            return java.lang.String.valueOf(obj);
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (obj instanceof android.net.Uri) {
            android.net.Uri uri = (android.net.Uri) obj;
            java.lang.String scheme = uri.getScheme();
            if (!android.text.TextUtils.isEmpty(scheme)) {
                sb.append(scheme).append(":");
            }
            java.lang.String schemeSpecificPart = uri.getSchemeSpecificPart();
            if (android.telecom.PhoneAccount.SCHEME_TEL.equals(scheme)) {
                obfuscatePhoneNumber(sb, schemeSpecificPart);
            } else if ("sip".equals(scheme)) {
                for (int i = 0; i < schemeSpecificPart.length(); i++) {
                    char charAt = schemeSpecificPart.charAt(i);
                    if (charAt != '@' && charAt != '.') {
                        charAt = '*';
                    }
                    sb.append(charAt);
                }
            } else {
                sb.append(pii(obj));
            }
        } else if (obj instanceof java.lang.String) {
            obfuscatePhoneNumber(sb, (java.lang.String) obj);
        }
        return sb.toString();
    }

    private static void obfuscatePhoneNumber(java.lang.StringBuilder sb, java.lang.String str) {
        int dialableCount = getDialableCount(str) - NUM_DIALABLE_DIGITS_TO_LOG;
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            boolean isDialable = android.telephony.PhoneNumberUtils.isDialable(charAt);
            if (isDialable) {
                dialableCount--;
            }
            sb.append((!isDialable || dialableCount < 0) ? java.lang.Character.valueOf(charAt) : "*");
        }
    }

    private static int getDialableCount(java.lang.String str) {
        int i = 0;
        for (char c : str.toCharArray()) {
            if (android.telephony.PhoneNumberUtils.isDialable(c)) {
                i++;
            }
        }
        return i;
    }

    public static java.lang.String pii(java.lang.Object obj) {
        if (obj == null || VERBOSE) {
            return java.lang.String.valueOf(obj);
        }
        return "***";
    }

    private static java.lang.String getPrefixFromObject(java.lang.Object obj) {
        return obj == null ? "<null>" : obj.getClass().getSimpleName();
    }

    private static java.lang.String buildMessage(java.lang.String str, java.lang.String str2, java.lang.Object... objArr) {
        java.lang.String sessionId = getSessionId();
        java.lang.String str3 = android.text.TextUtils.isEmpty(sessionId) ? "" : ": " + sessionId;
        if (objArr != null) {
            try {
                if (objArr.length != 0) {
                    str2 = java.lang.String.format(java.util.Locale.US, str2, objArr);
                }
            } catch (java.util.IllegalFormatException e) {
                e(TAG, (java.lang.Throwable) e, "Log: IllegalFormatException: formatString='%s' numArgs=%d", str2, java.lang.Integer.valueOf(objArr.length));
                str2 = str2 + " (An error occurred while formatting the message.)";
            }
        }
        return java.lang.String.format(java.util.Locale.US, "%s: %s%s%s", str, str2, str3, sLock != null ? java.lang.Thread.holdsLock(sLock) ? "🔒" : "❗" : "");
    }

    public static java.lang.String getPackageAbbreviation(android.content.ComponentName componentName) {
        if (componentName == null) {
            return "";
        }
        return getPackageAbbreviation(componentName.getPackageName());
    }

    public static java.lang.String getPackageAbbreviation(java.lang.String str) {
        if (str == null) {
            return "";
        }
        return (java.lang.String) java.util.Arrays.stream(str.split("\\.")).map(new java.util.function.Function() { // from class: android.telecom.Log$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return android.telecom.Log.lambda$getPackageAbbreviation$0((java.lang.String) obj);
            }
        }).collect(java.util.stream.Collectors.joining(""));
    }

    static /* synthetic */ java.lang.String lambda$getPackageAbbreviation$0(java.lang.String str) {
        return str.length() == 0 ? "" : str.substring(0, 1);
    }
}
