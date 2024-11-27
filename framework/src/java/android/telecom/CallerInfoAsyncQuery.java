package android.telecom;

/* loaded from: classes3.dex */
public class CallerInfoAsyncQuery {
    private static final boolean DBG = false;
    private static final boolean ENABLE_UNKNOWN_NUMBER_GEO_DESCRIPTION = true;
    private static final int EVENT_ADD_LISTENER = 2;
    private static final int EVENT_EMERGENCY_NUMBER = 4;
    private static final int EVENT_END_OF_QUEUE = 3;
    private static final int EVENT_GET_GEO_DESCRIPTION = 6;
    private static final int EVENT_NEW_QUERY = 1;
    private static final int EVENT_VOICEMAIL_NUMBER = 5;
    private static final java.lang.String LOG_TAG = "CallerInfoAsyncQuery";
    private android.telecom.CallerInfoAsyncQuery.CallerInfoAsyncQueryHandler mHandler;

    public interface OnQueryCompleteListener {
        void onQueryComplete(int i, java.lang.Object obj, android.telecom.CallerInfo callerInfo);
    }

    private static final class CookieWrapper {
        public java.lang.Object cookie;
        public int event;
        public java.lang.String geoDescription;
        public android.telecom.CallerInfoAsyncQuery.OnQueryCompleteListener listener;
        public java.lang.String number;
        public int subId;

        private CookieWrapper() {
        }
    }

    public static class QueryPoolException extends android.database.SQLException {
        public QueryPoolException(java.lang.String str) {
            super(str);
        }
    }

    static android.content.ContentResolver getCurrentProfileContentResolver(android.content.Context context) {
        int currentUser = android.app.ActivityManager.getCurrentUser();
        if (android.os.UserManager.get(context).getProcessUserId() != currentUser) {
            try {
                return context.createPackageContextAsUser(context.getPackageName(), 0, android.os.UserHandle.of(currentUser)).getContentResolver();
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.telecom.Log.e(LOG_TAG, (java.lang.Throwable) e, "Can't find self package", new java.lang.Object[0]);
            }
        }
        return context.getContentResolver();
    }

    private class CallerInfoAsyncQueryHandler extends android.content.AsyncQueryHandler {
        private android.telecom.CallerInfo mCallerInfo;
        private android.content.Context mContext;
        private java.util.List<java.lang.Runnable> mPendingListenerCallbacks;
        private android.net.Uri mQueryUri;

        protected class CallerInfoWorkerHandler extends android.content.AsyncQueryHandler.WorkerHandler {
            public CallerInfoWorkerHandler(android.os.Looper looper) {
                super(looper);
            }

            @Override // android.content.AsyncQueryHandler.WorkerHandler, android.os.Handler
            public void handleMessage(android.os.Message message) {
                android.content.AsyncQueryHandler.WorkerArgs workerArgs = (android.content.AsyncQueryHandler.WorkerArgs) message.obj;
                android.telecom.CallerInfoAsyncQuery.CookieWrapper cookieWrapper = (android.telecom.CallerInfoAsyncQuery.CookieWrapper) workerArgs.cookie;
                if (cookieWrapper == null) {
                    android.telecom.Log.i(android.telecom.CallerInfoAsyncQuery.LOG_TAG, "Unexpected command (CookieWrapper is null): " + message.what + " ignored by CallerInfoWorkerHandler, passing onto parent.", new java.lang.Object[0]);
                    super.handleMessage(message);
                    return;
                }
                android.telecom.Log.d(android.telecom.CallerInfoAsyncQuery.LOG_TAG, "Processing event: " + cookieWrapper.event + " token (arg1): " + message.arg1 + " command: " + message.what + " query URI: " + android.telecom.CallerInfoAsyncQuery.sanitizeUriToString(workerArgs.uri), new java.lang.Object[0]);
                switch (cookieWrapper.event) {
                    case 1:
                        super.handleMessage(message);
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        android.os.Message obtainMessage = workerArgs.handler.obtainMessage(message.what);
                        obtainMessage.obj = workerArgs;
                        obtainMessage.arg1 = message.arg1;
                        obtainMessage.sendToTarget();
                        break;
                    case 6:
                        handleGeoDescription(message);
                        break;
                }
            }

            private void handleGeoDescription(android.os.Message message) {
                android.content.AsyncQueryHandler.WorkerArgs workerArgs = (android.content.AsyncQueryHandler.WorkerArgs) message.obj;
                android.telecom.CallerInfoAsyncQuery.CookieWrapper cookieWrapper = (android.telecom.CallerInfoAsyncQuery.CookieWrapper) workerArgs.cookie;
                if (!android.text.TextUtils.isEmpty(cookieWrapper.number) && cookieWrapper.cookie != null && android.telecom.CallerInfoAsyncQuery.CallerInfoAsyncQueryHandler.this.mContext != null) {
                    android.os.SystemClock.elapsedRealtime();
                    cookieWrapper.geoDescription = android.telecom.CallerInfo.getGeoDescription(android.telecom.CallerInfoAsyncQuery.CallerInfoAsyncQueryHandler.this.mContext, cookieWrapper.number);
                    android.os.SystemClock.elapsedRealtime();
                }
                android.os.Message obtainMessage = workerArgs.handler.obtainMessage(message.what);
                obtainMessage.obj = workerArgs;
                obtainMessage.arg1 = message.arg1;
                obtainMessage.sendToTarget();
            }
        }

        private CallerInfoAsyncQueryHandler(android.content.Context context) {
            super(android.telecom.CallerInfoAsyncQuery.getCurrentProfileContentResolver(context));
            this.mPendingListenerCallbacks = new java.util.ArrayList();
            this.mContext = context;
        }

        @Override // android.content.AsyncQueryHandler
        protected android.os.Handler createHandler(android.os.Looper looper) {
            return new android.telecom.CallerInfoAsyncQuery.CallerInfoAsyncQueryHandler.CallerInfoWorkerHandler(looper);
        }

        @Override // android.content.AsyncQueryHandler
        protected void onQueryComplete(final int i, java.lang.Object obj, android.database.Cursor cursor) {
            int i2;
            android.telecom.Log.d(android.telecom.CallerInfoAsyncQuery.LOG_TAG, "##### onQueryComplete() #####   query complete for token: " + i, new java.lang.Object[0]);
            final android.telecom.CallerInfoAsyncQuery.CookieWrapper cookieWrapper = (android.telecom.CallerInfoAsyncQuery.CookieWrapper) obj;
            if (cookieWrapper == null) {
                android.telecom.Log.i(android.telecom.CallerInfoAsyncQuery.LOG_TAG, "Cookie is null, ignoring onQueryComplete() request.", new java.lang.Object[0]);
                if (cursor != null) {
                    cursor.close();
                    return;
                }
                return;
            }
            if (cookieWrapper.event == 3) {
                java.util.Iterator<java.lang.Runnable> it = this.mPendingListenerCallbacks.iterator();
                while (it.hasNext()) {
                    it.next().run();
                }
                this.mPendingListenerCallbacks.clear();
                android.telecom.CallerInfoAsyncQuery.this.release();
                if (cursor != null) {
                    cursor.close();
                    return;
                }
                return;
            }
            if (cookieWrapper.event != 6) {
                i2 = 6;
            } else {
                if (this.mCallerInfo != null) {
                    this.mCallerInfo.geoDescription = cookieWrapper.geoDescription;
                }
                android.telecom.CallerInfoAsyncQuery.CookieWrapper cookieWrapper2 = new android.telecom.CallerInfoAsyncQuery.CookieWrapper();
                cookieWrapper2.event = 3;
                i2 = 6;
                startQuery(i, cookieWrapper2, null, null, null, null, null);
            }
            if (this.mCallerInfo == null) {
                if (this.mContext == null || this.mQueryUri == null) {
                    throw new android.telecom.CallerInfoAsyncQuery.QueryPoolException("Bad context or query uri, or CallerInfoAsyncQuery already released.");
                }
                if (cookieWrapper.event == 4) {
                    this.mCallerInfo = new android.telecom.CallerInfo().markAsEmergency(this.mContext);
                } else if (cookieWrapper.event == 5) {
                    this.mCallerInfo = new android.telecom.CallerInfo().markAsVoiceMail(this.mContext, cookieWrapper.subId);
                } else {
                    this.mCallerInfo = android.telecom.CallerInfo.getCallerInfo(this.mContext, this.mQueryUri, cursor);
                    android.telecom.CallerInfo doSecondaryLookupIfNecessary = android.telecom.CallerInfo.doSecondaryLookupIfNecessary(this.mContext, cookieWrapper.number, this.mCallerInfo);
                    if (doSecondaryLookupIfNecessary != this.mCallerInfo) {
                        this.mCallerInfo = doSecondaryLookupIfNecessary;
                    }
                    if (!android.text.TextUtils.isEmpty(cookieWrapper.number)) {
                        this.mCallerInfo.setPhoneNumber(android.telephony.PhoneNumberUtils.formatNumber(cookieWrapper.number, this.mCallerInfo.normalizedNumber, android.telecom.CallerInfo.getCurrentCountryIso(this.mContext)));
                    }
                    if (android.text.TextUtils.isEmpty(this.mCallerInfo.getName())) {
                        cookieWrapper.event = i2;
                        startQuery(i, cookieWrapper, null, null, null, null, null);
                        return;
                    }
                }
                android.telecom.CallerInfoAsyncQuery.CookieWrapper cookieWrapper3 = new android.telecom.CallerInfoAsyncQuery.CookieWrapper();
                cookieWrapper3.event = 3;
                startQuery(i, cookieWrapper3, null, null, null, null, null);
            }
            if (cookieWrapper.listener != null) {
                this.mPendingListenerCallbacks.add(new java.lang.Runnable() { // from class: android.telecom.CallerInfoAsyncQuery.CallerInfoAsyncQueryHandler.1
                    @Override // java.lang.Runnable
                    public void run() {
                        cookieWrapper.listener.onQueryComplete(i, cookieWrapper.cookie, android.telecom.CallerInfoAsyncQuery.CallerInfoAsyncQueryHandler.this.mCallerInfo);
                    }
                });
            } else {
                android.telecom.Log.w(android.telecom.CallerInfoAsyncQuery.LOG_TAG, "There is no listener to notify for this query.", new java.lang.Object[0]);
            }
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private CallerInfoAsyncQuery() {
    }

    public static android.telecom.CallerInfoAsyncQuery startQuery(int i, android.content.Context context, android.net.Uri uri, android.telecom.CallerInfoAsyncQuery.OnQueryCompleteListener onQueryCompleteListener, java.lang.Object obj) {
        android.telecom.CallerInfoAsyncQuery callerInfoAsyncQuery = new android.telecom.CallerInfoAsyncQuery();
        callerInfoAsyncQuery.allocate(context, uri);
        android.telecom.CallerInfoAsyncQuery.CookieWrapper cookieWrapper = new android.telecom.CallerInfoAsyncQuery.CookieWrapper();
        cookieWrapper.listener = onQueryCompleteListener;
        cookieWrapper.cookie = obj;
        cookieWrapper.event = 1;
        callerInfoAsyncQuery.mHandler.startQuery(i, cookieWrapper, uri, null, null, null, null);
        return callerInfoAsyncQuery;
    }

    public static android.telecom.CallerInfoAsyncQuery startQuery(int i, android.content.Context context, java.lang.String str, android.telecom.CallerInfoAsyncQuery.OnQueryCompleteListener onQueryCompleteListener, java.lang.Object obj) {
        return startQuery(i, context, str, onQueryCompleteListener, obj, android.telephony.SubscriptionManager.getDefaultSubscriptionId());
    }

    public static android.telecom.CallerInfoAsyncQuery startQuery(int i, android.content.Context context, java.lang.String str, android.telecom.CallerInfoAsyncQuery.OnQueryCompleteListener onQueryCompleteListener, java.lang.Object obj, int i2) {
        boolean isLocalEmergencyNumber;
        android.net.Uri build = android.provider.ContactsContract.PhoneLookup.ENTERPRISE_CONTENT_FILTER_URI.buildUpon().appendPath(str).appendQueryParameter("sip", java.lang.String.valueOf(android.telephony.PhoneNumberUtils.isUriNumber(str))).build();
        android.telecom.CallerInfoAsyncQuery callerInfoAsyncQuery = new android.telecom.CallerInfoAsyncQuery();
        callerInfoAsyncQuery.allocate(context, build);
        android.telecom.CallerInfoAsyncQuery.CookieWrapper cookieWrapper = new android.telecom.CallerInfoAsyncQuery.CookieWrapper();
        cookieWrapper.listener = onQueryCompleteListener;
        cookieWrapper.cookie = obj;
        cookieWrapper.number = str;
        cookieWrapper.subId = i2;
        try {
            isLocalEmergencyNumber = ((android.telephony.TelephonyManager) context.getSystemService(android.telephony.TelephonyManager.class)).isEmergencyNumber(str);
        } catch (java.lang.IllegalStateException e) {
            isLocalEmergencyNumber = android.telephony.PhoneNumberUtils.isLocalEmergencyNumber(context, str);
        }
        if (isLocalEmergencyNumber) {
            cookieWrapper.event = 4;
        } else if (android.telephony.PhoneNumberUtils.isVoiceMailNumber(context, i2, str)) {
            cookieWrapper.event = 5;
        } else {
            cookieWrapper.event = 1;
        }
        callerInfoAsyncQuery.mHandler.startQuery(i, cookieWrapper, build, null, null, null, null);
        return callerInfoAsyncQuery;
    }

    public void addQueryListener(int i, android.telecom.CallerInfoAsyncQuery.OnQueryCompleteListener onQueryCompleteListener, java.lang.Object obj) {
        android.telecom.CallerInfoAsyncQuery.CookieWrapper cookieWrapper = new android.telecom.CallerInfoAsyncQuery.CookieWrapper();
        cookieWrapper.listener = onQueryCompleteListener;
        cookieWrapper.cookie = obj;
        cookieWrapper.event = 2;
        this.mHandler.startQuery(i, cookieWrapper, null, null, null, null, null);
    }

    private void allocate(android.content.Context context, android.net.Uri uri) {
        if (context == null || uri == null) {
            throw new android.telecom.CallerInfoAsyncQuery.QueryPoolException("Bad context or query uri.");
        }
        this.mHandler = new android.telecom.CallerInfoAsyncQuery.CallerInfoAsyncQueryHandler(context);
        this.mHandler.mQueryUri = uri;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void release() {
        this.mHandler.mContext = null;
        this.mHandler.mQueryUri = null;
        this.mHandler.mCallerInfo = null;
        this.mHandler = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String sanitizeUriToString(android.net.Uri uri) {
        if (uri != null) {
            java.lang.String uri2 = uri.toString();
            int lastIndexOf = uri2.lastIndexOf(47);
            if (lastIndexOf > 0) {
                return uri2.substring(0, lastIndexOf) + "/xxxxxxx";
            }
            return uri2;
        }
        return "";
    }
}
