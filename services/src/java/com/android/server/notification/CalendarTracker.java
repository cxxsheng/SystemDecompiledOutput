package com.android.server.notification;

/* loaded from: classes2.dex */
public class CalendarTracker {
    private static final java.lang.String ATTENDEE_SELECTION = "event_id = ? AND attendeeEmail = ?";
    private static final boolean DEBUG_ATTENDEES = false;
    private static final int EVENT_CHECK_LOOKAHEAD = 86400000;
    private static final java.lang.String INSTANCE_ORDER_BY = "begin ASC";
    private static final java.lang.String TAG = "ConditionProviders.CT";
    private com.android.server.notification.CalendarTracker.Callback mCallback;
    private final android.database.ContentObserver mObserver = new android.database.ContentObserver(null) { // from class: com.android.server.notification.CalendarTracker.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            if (com.android.server.notification.CalendarTracker.DEBUG) {
                android.util.Log.d(com.android.server.notification.CalendarTracker.TAG, "onChange selfChange=" + z + " uri=" + uri + " u=" + com.android.server.notification.CalendarTracker.this.mUserContext.getUserId());
            }
            com.android.server.notification.CalendarTracker.this.mCallback.onChanged();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            if (com.android.server.notification.CalendarTracker.DEBUG) {
                android.util.Log.d(com.android.server.notification.CalendarTracker.TAG, "onChange selfChange=" + z);
            }
        }
    };
    private boolean mRegistered;
    private final android.content.Context mSystemContext;
    private final android.content.Context mUserContext;
    private static final boolean DEBUG = android.util.Log.isLoggable("ConditionProviders", 3);
    private static final java.lang.String[] INSTANCE_PROJECTION = {"begin", "end", "title", com.android.server.wm.ActivityTaskManagerService.DUMP_VISIBLE_ACTIVITIES, "event_id", "calendar_displayName", "ownerAccount", "calendar_id", "availability"};
    private static final java.lang.String[] ATTENDEE_PROJECTION = {"event_id", "attendeeEmail", "attendeeStatus"};

    public interface Callback {
        void onChanged();
    }

    public static class CheckEventResult {
        public boolean inEvent;
        public long recheckAt;
    }

    public CalendarTracker(android.content.Context context, android.content.Context context2) {
        this.mSystemContext = context;
        this.mUserContext = context2;
    }

    public void setCallback(com.android.server.notification.CalendarTracker.Callback callback) {
        if (this.mCallback == callback) {
            return;
        }
        this.mCallback = callback;
        setRegistered(this.mCallback != null);
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("mCallback=");
        printWriter.println(this.mCallback);
        printWriter.print(str);
        printWriter.print("mRegistered=");
        printWriter.println(this.mRegistered);
        printWriter.print(str);
        printWriter.print("u=");
        printWriter.println(this.mUserContext.getUserId());
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x004d, code lost:
    
        if (com.android.server.notification.CalendarTracker.DEBUG == false) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x004f, code lost:
    
        android.util.Log.d(com.android.server.notification.CalendarTracker.TAG, "getCalendarsWithAccess took " + (java.lang.System.currentTimeMillis() - r1));
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0068, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0048, code lost:
    
        if (r4 == null) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.util.ArraySet<java.lang.Long> getCalendarsWithAccess() {
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        android.util.ArraySet<java.lang.Long> arraySet = new android.util.ArraySet<>();
        android.database.Cursor cursor = null;
        try {
            try {
                cursor = this.mUserContext.getContentResolver().query(android.provider.CalendarContract.Calendars.CONTENT_URI, new java.lang.String[]{"_id"}, "calendar_access_level >= 500 AND sync_events = 1", null, null);
                while (cursor != null) {
                    if (!cursor.moveToNext()) {
                        break;
                    }
                    arraySet.add(java.lang.Long.valueOf(cursor.getLong(0)));
                }
            } catch (android.database.sqlite.SQLiteException e) {
                android.util.Slog.w(TAG, "error querying calendar content provider", e);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0101  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public com.android.server.notification.CalendarTracker.CheckEventResult checkEvent(android.service.notification.ZenModeConfig.EventInfo eventInfo, long j) {
        com.android.server.notification.CalendarTracker.CheckEventResult checkEventResult;
        android.util.ArraySet<java.lang.Long> arraySet;
        android.service.notification.ZenModeConfig.EventInfo eventInfo2;
        boolean z;
        android.net.Uri.Builder buildUpon = android.provider.CalendarContract.Instances.CONTENT_URI.buildUpon();
        android.content.ContentUris.appendId(buildUpon, j);
        long j2 = 86400000 + j;
        android.content.ContentUris.appendId(buildUpon, j2);
        android.net.Uri build = buildUpon.build();
        com.android.server.notification.CalendarTracker.CheckEventResult checkEventResult2 = new com.android.server.notification.CalendarTracker.CheckEventResult();
        checkEventResult2.recheckAt = j2;
        android.database.Cursor cursor = null;
        try {
            try {
                cursor = this.mUserContext.getContentResolver().query(build, INSTANCE_PROJECTION, null, null, INSTANCE_ORDER_BY);
            } catch (java.lang.Throwable th) {
                th = th;
            }
        } catch (java.lang.Exception e) {
            e = e;
            checkEventResult = checkEventResult2;
        }
        try {
            try {
                android.util.ArraySet<java.lang.Long> calendarsWithAccess = getCalendarsWithAccess();
                while (cursor != null) {
                    if (!cursor.moveToNext()) {
                        break;
                    }
                    long j3 = cursor.getLong(0);
                    long j4 = cursor.getLong(1);
                    java.lang.String string = cursor.getString(2);
                    boolean z2 = cursor.getInt(3) == 1;
                    int i = cursor.getInt(4);
                    java.lang.String string2 = cursor.getString(5);
                    com.android.server.notification.CalendarTracker.CheckEventResult checkEventResult3 = checkEventResult2;
                    try {
                        java.lang.String string3 = cursor.getString(6);
                        long j5 = cursor.getLong(7);
                        int i2 = cursor.getInt(8);
                        android.database.Cursor cursor2 = cursor;
                        try {
                            try {
                                boolean contains = calendarsWithAccess.contains(java.lang.Long.valueOf(j5));
                                if (DEBUG) {
                                    arraySet = calendarsWithAccess;
                                    android.util.Log.d(TAG, java.lang.String.format("title=%s time=%s-%s vis=%s availability=%s eventId=%s name=%s owner=%s calId=%s canAccessCal=%s", string, new java.util.Date(j3), new java.util.Date(j4), java.lang.Boolean.valueOf(z2), availabilityToString(i2), java.lang.Integer.valueOf(i), string2, string3, java.lang.Long.valueOf(j5), java.lang.Boolean.valueOf(contains)));
                                } else {
                                    arraySet = calendarsWithAccess;
                                }
                                boolean z3 = j >= j3 && j < j4;
                                if (z2 && contains) {
                                    eventInfo2 = eventInfo;
                                    if ((eventInfo2.calName == null && eventInfo2.calendarId == null) || java.util.Objects.equals(eventInfo2.calendarId, java.lang.Long.valueOf(j5)) || java.util.Objects.equals(eventInfo2.calName, string2)) {
                                        z = true;
                                        boolean z4 = i2 == 1;
                                        if (z || !z4) {
                                            checkEventResult = checkEventResult3;
                                        } else {
                                            if (DEBUG) {
                                                android.util.Log.d(TAG, "  MEETS CALENDAR & AVAILABILITY");
                                            }
                                            if (meetsAttendee(eventInfo2, i, string3)) {
                                                if (DEBUG) {
                                                    android.util.Log.d(TAG, "    MEETS ATTENDEE");
                                                }
                                                if (z3) {
                                                    if (DEBUG) {
                                                        android.util.Log.d(TAG, "      MEETS TIME");
                                                    }
                                                    checkEventResult = checkEventResult3;
                                                    try {
                                                        checkEventResult.inEvent = true;
                                                    } catch (java.lang.Exception e2) {
                                                        e = e2;
                                                        cursor = cursor2;
                                                        android.util.Slog.w(TAG, "error reading calendar", e);
                                                        if (cursor != null) {
                                                            cursor.close();
                                                        }
                                                        return checkEventResult;
                                                    }
                                                } else {
                                                    checkEventResult = checkEventResult3;
                                                }
                                                if (j3 > j && j3 < checkEventResult.recheckAt) {
                                                    checkEventResult.recheckAt = j3;
                                                } else if (j4 > j && j4 < checkEventResult.recheckAt) {
                                                    checkEventResult.recheckAt = j4;
                                                }
                                            } else {
                                                checkEventResult = checkEventResult3;
                                            }
                                        }
                                        checkEventResult2 = checkEventResult;
                                        cursor = cursor2;
                                        calendarsWithAccess = arraySet;
                                    }
                                } else {
                                    eventInfo2 = eventInfo;
                                }
                                z = false;
                                if (i2 == 1) {
                                }
                                if (z) {
                                }
                                checkEventResult = checkEventResult3;
                                checkEventResult2 = checkEventResult;
                                cursor = cursor2;
                                calendarsWithAccess = arraySet;
                            } catch (java.lang.Throwable th2) {
                                th = th2;
                                cursor = cursor2;
                                if (cursor != null) {
                                    cursor.close();
                                }
                                throw th;
                            }
                        } catch (java.lang.Exception e3) {
                            e = e3;
                            checkEventResult = checkEventResult3;
                        }
                    } catch (java.lang.Exception e4) {
                        e = e4;
                        checkEventResult = checkEventResult3;
                    }
                }
                checkEventResult = checkEventResult2;
                android.database.Cursor cursor3 = cursor;
                if (cursor3 != null) {
                    cursor3.close();
                }
            } catch (java.lang.Exception e5) {
                e = e5;
                checkEventResult = checkEventResult2;
            }
            return checkEventResult;
        } catch (java.lang.Throwable th3) {
            th = th3;
        }
    }

    private boolean meetsAttendee(android.service.notification.ZenModeConfig.EventInfo eventInfo, int i, java.lang.String str) {
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        int i2 = 0;
        android.database.Cursor cursor = null;
        try {
            try {
                android.database.Cursor query = this.mUserContext.getContentResolver().query(android.provider.CalendarContract.Attendees.CONTENT_URI, ATTENDEE_PROJECTION, ATTENDEE_SELECTION, new java.lang.String[]{java.lang.Integer.toString(i), str}, null);
                int i3 = 1;
                if (query == null || query.getCount() == 0) {
                    if (DEBUG) {
                        android.util.Log.d(TAG, "No attendees found");
                    }
                    if (query != null) {
                        query.close();
                    }
                    if (!DEBUG) {
                        return true;
                    }
                    android.util.Log.d(TAG, "meetsAttendee took " + (java.lang.System.currentTimeMillis() - currentTimeMillis));
                    return true;
                }
                boolean z = false;
                while (query.moveToNext()) {
                    long j = query.getLong(i2);
                    java.lang.String string = query.getString(i3);
                    int i4 = query.getInt(2);
                    boolean meetsReply = meetsReply(eventInfo.reply, i4);
                    if (DEBUG) {
                        android.util.Log.d(TAG, "" + java.lang.String.format("status=%s, meetsReply=%s", attendeeStatusToString(i4), java.lang.Boolean.valueOf(meetsReply)));
                    }
                    z |= j == ((long) i) && java.util.Objects.equals(string, str) && meetsReply;
                    i3 = 1;
                    i2 = 0;
                }
                query.close();
                if (DEBUG) {
                    android.util.Log.d(TAG, "meetsAttendee took " + (java.lang.System.currentTimeMillis() - currentTimeMillis));
                }
                return z;
            } catch (android.database.sqlite.SQLiteException e) {
                android.util.Slog.w(TAG, "error querying attendees content provider", e);
                if (0 != 0) {
                    cursor.close();
                }
                if (!DEBUG) {
                    return false;
                }
                android.util.Log.d(TAG, "meetsAttendee took " + (java.lang.System.currentTimeMillis() - currentTimeMillis));
                return false;
            }
        } catch (java.lang.Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            if (DEBUG) {
                android.util.Log.d(TAG, "meetsAttendee took " + (java.lang.System.currentTimeMillis() - currentTimeMillis));
            }
            throw th;
        }
    }

    private void setRegistered(boolean z) {
        if (this.mRegistered == z) {
            return;
        }
        android.content.ContentResolver contentResolver = this.mSystemContext.getContentResolver();
        int userId = this.mUserContext.getUserId();
        if (this.mRegistered) {
            if (DEBUG) {
                android.util.Log.d(TAG, "unregister content observer u=" + userId);
            }
            contentResolver.unregisterContentObserver(this.mObserver);
        }
        this.mRegistered = z;
        if (DEBUG) {
            android.util.Log.d(TAG, "mRegistered = " + z + " u=" + userId);
        }
        if (this.mRegistered) {
            if (DEBUG) {
                android.util.Log.d(TAG, "register content observer u=" + userId);
            }
            contentResolver.registerContentObserver(android.provider.CalendarContract.Instances.CONTENT_URI, true, this.mObserver, userId);
            contentResolver.registerContentObserver(android.provider.CalendarContract.Events.CONTENT_URI, true, this.mObserver, userId);
            contentResolver.registerContentObserver(android.provider.CalendarContract.Calendars.CONTENT_URI, true, this.mObserver, userId);
        }
    }

    private static java.lang.String attendeeStatusToString(int i) {
        switch (i) {
            case 0:
                return "ATTENDEE_STATUS_NONE";
            case 1:
                return "ATTENDEE_STATUS_ACCEPTED";
            case 2:
                return "ATTENDEE_STATUS_DECLINED";
            case 3:
                return "ATTENDEE_STATUS_INVITED";
            case 4:
                return "ATTENDEE_STATUS_TENTATIVE";
            default:
                return "ATTENDEE_STATUS_UNKNOWN_" + i;
        }
    }

    private static java.lang.String availabilityToString(int i) {
        switch (i) {
            case 0:
                return "AVAILABILITY_BUSY";
            case 1:
                return "AVAILABILITY_FREE";
            case 2:
                return "AVAILABILITY_TENTATIVE";
            default:
                return "AVAILABILITY_UNKNOWN_" + i;
        }
    }

    private static boolean meetsReply(int i, int i2) {
        switch (i) {
            case 0:
                return i2 != 2;
            case 1:
                return i2 == 1 || i2 == 4;
            case 2:
                return i2 == 1;
            default:
                return false;
        }
    }
}
