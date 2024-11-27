package android.provider;

/* loaded from: classes3.dex */
public final class CalendarContract {
    public static final java.lang.String ACCOUNT_TYPE_LOCAL = "LOCAL";
    public static final java.lang.String ACTION_EVENT_REMINDER = "android.intent.action.EVENT_REMINDER";
    public static final java.lang.String ACTION_HANDLE_CUSTOM_EVENT = "android.provider.calendar.action.HANDLE_CUSTOM_EVENT";
    public static final java.lang.String ACTION_VIEW_MANAGED_PROFILE_CALENDAR_EVENT = "android.provider.calendar.action.VIEW_MANAGED_PROFILE_CALENDAR_EVENT";
    public static final java.lang.String AUTHORITY = "com.android.calendar";
    public static final java.lang.String CALLER_IS_SYNCADAPTER = "caller_is_syncadapter";
    public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://com.android.calendar");
    public static final android.net.Uri ENTERPRISE_CONTENT_URI = android.net.Uri.parse("content://com.android.calendar/enterprise");
    public static final java.lang.String EXTRA_CUSTOM_APP_URI = "customAppUri";
    public static final java.lang.String EXTRA_EVENT_ALL_DAY = "allDay";
    public static final java.lang.String EXTRA_EVENT_BEGIN_TIME = "beginTime";
    public static final java.lang.String EXTRA_EVENT_END_TIME = "endTime";
    public static final java.lang.String EXTRA_EVENT_ID = "id";
    private static final java.lang.String TAG = "Calendar";

    protected interface AttendeesColumns {
        public static final java.lang.String ATTENDEE_EMAIL = "attendeeEmail";
        public static final java.lang.String ATTENDEE_IDENTITY = "attendeeIdentity";
        public static final java.lang.String ATTENDEE_ID_NAMESPACE = "attendeeIdNamespace";
        public static final java.lang.String ATTENDEE_NAME = "attendeeName";
        public static final java.lang.String ATTENDEE_RELATIONSHIP = "attendeeRelationship";
        public static final java.lang.String ATTENDEE_STATUS = "attendeeStatus";
        public static final int ATTENDEE_STATUS_ACCEPTED = 1;
        public static final int ATTENDEE_STATUS_DECLINED = 2;
        public static final int ATTENDEE_STATUS_INVITED = 3;
        public static final int ATTENDEE_STATUS_NONE = 0;
        public static final int ATTENDEE_STATUS_TENTATIVE = 4;
        public static final java.lang.String ATTENDEE_TYPE = "attendeeType";
        public static final java.lang.String EVENT_ID = "event_id";
        public static final int RELATIONSHIP_ATTENDEE = 1;
        public static final int RELATIONSHIP_NONE = 0;
        public static final int RELATIONSHIP_ORGANIZER = 2;
        public static final int RELATIONSHIP_PERFORMER = 3;
        public static final int RELATIONSHIP_SPEAKER = 4;
        public static final int TYPE_NONE = 0;
        public static final int TYPE_OPTIONAL = 2;
        public static final int TYPE_REQUIRED = 1;
        public static final int TYPE_RESOURCE = 3;
    }

    protected interface CalendarAlertsColumns {
        public static final java.lang.String ALARM_TIME = "alarmTime";
        public static final java.lang.String BEGIN = "begin";
        public static final java.lang.String CREATION_TIME = "creationTime";
        public static final java.lang.String DEFAULT_SORT_ORDER = "begin ASC,title ASC";
        public static final java.lang.String END = "end";
        public static final java.lang.String EVENT_ID = "event_id";
        public static final java.lang.String MINUTES = "minutes";
        public static final java.lang.String NOTIFY_TIME = "notifyTime";
        public static final java.lang.String RECEIVED_TIME = "receivedTime";
        public static final java.lang.String STATE = "state";
        public static final int STATE_DISMISSED = 2;
        public static final int STATE_FIRED = 1;
        public static final int STATE_SCHEDULED = 0;
    }

    protected interface CalendarCacheColumns {
        public static final java.lang.String KEY = "key";
        public static final java.lang.String VALUE = "value";
    }

    protected interface CalendarColumns {
        public static final java.lang.String ALLOWED_ATTENDEE_TYPES = "allowedAttendeeTypes";
        public static final java.lang.String ALLOWED_AVAILABILITY = "allowedAvailability";
        public static final java.lang.String ALLOWED_REMINDERS = "allowedReminders";
        public static final java.lang.String CALENDAR_ACCESS_LEVEL = "calendar_access_level";
        public static final java.lang.String CALENDAR_COLOR = "calendar_color";
        public static final java.lang.String CALENDAR_COLOR_KEY = "calendar_color_index";
        public static final java.lang.String CALENDAR_DISPLAY_NAME = "calendar_displayName";
        public static final java.lang.String CALENDAR_TIME_ZONE = "calendar_timezone";
        public static final int CAL_ACCESS_CONTRIBUTOR = 500;
        public static final int CAL_ACCESS_EDITOR = 600;
        public static final int CAL_ACCESS_FREEBUSY = 100;
        public static final int CAL_ACCESS_NONE = 0;
        public static final int CAL_ACCESS_OVERRIDE = 400;
        public static final int CAL_ACCESS_OWNER = 700;
        public static final int CAL_ACCESS_READ = 200;
        public static final int CAL_ACCESS_RESPOND = 300;
        public static final int CAL_ACCESS_ROOT = 800;
        public static final java.lang.String CAN_MODIFY_TIME_ZONE = "canModifyTimeZone";
        public static final java.lang.String CAN_ORGANIZER_RESPOND = "canOrganizerRespond";
        public static final java.lang.String IS_PRIMARY = "isPrimary";
        public static final java.lang.String MAX_REMINDERS = "maxReminders";
        public static final java.lang.String OWNER_ACCOUNT = "ownerAccount";
        public static final java.lang.String SYNC_EVENTS = "sync_events";
        public static final java.lang.String VISIBLE = "visible";
    }

    protected interface CalendarMetaDataColumns {
        public static final java.lang.String LOCAL_TIMEZONE = "localTimezone";
        public static final java.lang.String MAX_EVENTDAYS = "maxEventDays";
        public static final java.lang.String MAX_INSTANCE = "maxInstance";
        public static final java.lang.String MIN_EVENTDAYS = "minEventDays";
        public static final java.lang.String MIN_INSTANCE = "minInstance";
    }

    protected interface CalendarSyncColumns {
        public static final java.lang.String CAL_SYNC1 = "cal_sync1";
        public static final java.lang.String CAL_SYNC10 = "cal_sync10";
        public static final java.lang.String CAL_SYNC2 = "cal_sync2";
        public static final java.lang.String CAL_SYNC3 = "cal_sync3";
        public static final java.lang.String CAL_SYNC4 = "cal_sync4";
        public static final java.lang.String CAL_SYNC5 = "cal_sync5";
        public static final java.lang.String CAL_SYNC6 = "cal_sync6";
        public static final java.lang.String CAL_SYNC7 = "cal_sync7";
        public static final java.lang.String CAL_SYNC8 = "cal_sync8";
        public static final java.lang.String CAL_SYNC9 = "cal_sync9";
    }

    protected interface ColorsColumns extends android.provider.SyncStateContract.Columns {
        public static final java.lang.String COLOR = "color";
        public static final java.lang.String COLOR_KEY = "color_index";
        public static final java.lang.String COLOR_TYPE = "color_type";
        public static final int TYPE_CALENDAR = 0;
        public static final int TYPE_EVENT = 1;
    }

    protected interface EventDaysColumns {
        public static final java.lang.String ENDDAY = "endDay";
        public static final java.lang.String STARTDAY = "startDay";
    }

    protected interface EventsColumns {
        public static final int ACCESS_CONFIDENTIAL = 1;
        public static final int ACCESS_DEFAULT = 0;
        public static final java.lang.String ACCESS_LEVEL = "accessLevel";
        public static final int ACCESS_PRIVATE = 2;
        public static final int ACCESS_PUBLIC = 3;
        public static final java.lang.String ALL_DAY = "allDay";
        public static final java.lang.String AVAILABILITY = "availability";
        public static final int AVAILABILITY_BUSY = 0;
        public static final int AVAILABILITY_FREE = 1;
        public static final int AVAILABILITY_TENTATIVE = 2;
        public static final java.lang.String CALENDAR_ID = "calendar_id";
        public static final java.lang.String CAN_INVITE_OTHERS = "canInviteOthers";
        public static final java.lang.String CUSTOM_APP_PACKAGE = "customAppPackage";
        public static final java.lang.String CUSTOM_APP_URI = "customAppUri";
        public static final java.lang.String DESCRIPTION = "description";
        public static final java.lang.String DISPLAY_COLOR = "displayColor";
        public static final java.lang.String DTEND = "dtend";
        public static final java.lang.String DTSTART = "dtstart";
        public static final java.lang.String DURATION = "duration";
        public static final java.lang.String EVENT_COLOR = "eventColor";
        public static final java.lang.String EVENT_COLOR_KEY = "eventColor_index";
        public static final java.lang.String EVENT_END_TIMEZONE = "eventEndTimezone";
        public static final java.lang.String EVENT_LOCATION = "eventLocation";
        public static final java.lang.String EVENT_TIMEZONE = "eventTimezone";
        public static final java.lang.String EXDATE = "exdate";
        public static final java.lang.String EXRULE = "exrule";
        public static final java.lang.String GUESTS_CAN_INVITE_OTHERS = "guestsCanInviteOthers";
        public static final java.lang.String GUESTS_CAN_MODIFY = "guestsCanModify";
        public static final java.lang.String GUESTS_CAN_SEE_GUESTS = "guestsCanSeeGuests";
        public static final java.lang.String HAS_ALARM = "hasAlarm";
        public static final java.lang.String HAS_ATTENDEE_DATA = "hasAttendeeData";
        public static final java.lang.String HAS_EXTENDED_PROPERTIES = "hasExtendedProperties";
        public static final java.lang.String IS_ORGANIZER = "isOrganizer";
        public static final java.lang.String LAST_DATE = "lastDate";
        public static final java.lang.String LAST_SYNCED = "lastSynced";
        public static final java.lang.String ORGANIZER = "organizer";
        public static final java.lang.String ORIGINAL_ALL_DAY = "originalAllDay";
        public static final java.lang.String ORIGINAL_ID = "original_id";
        public static final java.lang.String ORIGINAL_INSTANCE_TIME = "originalInstanceTime";
        public static final java.lang.String ORIGINAL_SYNC_ID = "original_sync_id";
        public static final java.lang.String RDATE = "rdate";
        public static final java.lang.String RRULE = "rrule";
        public static final java.lang.String SELF_ATTENDEE_STATUS = "selfAttendeeStatus";
        public static final java.lang.String STATUS = "eventStatus";
        public static final int STATUS_CANCELED = 2;
        public static final int STATUS_CONFIRMED = 1;
        public static final int STATUS_TENTATIVE = 0;
        public static final java.lang.String SYNC_DATA1 = "sync_data1";
        public static final java.lang.String SYNC_DATA10 = "sync_data10";
        public static final java.lang.String SYNC_DATA2 = "sync_data2";
        public static final java.lang.String SYNC_DATA3 = "sync_data3";
        public static final java.lang.String SYNC_DATA4 = "sync_data4";
        public static final java.lang.String SYNC_DATA5 = "sync_data5";
        public static final java.lang.String SYNC_DATA6 = "sync_data6";
        public static final java.lang.String SYNC_DATA7 = "sync_data7";
        public static final java.lang.String SYNC_DATA8 = "sync_data8";
        public static final java.lang.String SYNC_DATA9 = "sync_data9";
        public static final java.lang.String TITLE = "title";
        public static final java.lang.String UID_2445 = "uid2445";
    }

    protected interface EventsRawTimesColumns {
        public static final java.lang.String DTEND_2445 = "dtend2445";
        public static final java.lang.String DTSTART_2445 = "dtstart2445";
        public static final java.lang.String EVENT_ID = "event_id";
        public static final java.lang.String LAST_DATE_2445 = "lastDate2445";
        public static final java.lang.String ORIGINAL_INSTANCE_TIME_2445 = "originalInstanceTime2445";
    }

    protected interface ExtendedPropertiesColumns {
        public static final java.lang.String EVENT_ID = "event_id";
        public static final java.lang.String NAME = "name";
        public static final java.lang.String VALUE = "value";
    }

    protected interface RemindersColumns {
        public static final java.lang.String EVENT_ID = "event_id";
        public static final java.lang.String METHOD = "method";
        public static final int METHOD_ALARM = 4;
        public static final int METHOD_ALERT = 1;
        public static final int METHOD_DEFAULT = 0;
        public static final int METHOD_EMAIL = 2;
        public static final int METHOD_SMS = 3;
        public static final java.lang.String MINUTES = "minutes";
        public static final int MINUTES_DEFAULT = -1;
    }

    protected interface SyncColumns extends android.provider.CalendarContract.CalendarSyncColumns {
        public static final java.lang.String ACCOUNT_NAME = "account_name";
        public static final java.lang.String ACCOUNT_TYPE = "account_type";
        public static final java.lang.String CAN_PARTIALLY_UPDATE = "canPartiallyUpdate";
        public static final java.lang.String DELETED = "deleted";
        public static final java.lang.String DIRTY = "dirty";
        public static final java.lang.String MUTATORS = "mutators";
        public static final java.lang.String _SYNC_ID = "_sync_id";
    }

    private CalendarContract() {
    }

    public static boolean startViewCalendarEventInManagedProfile(android.content.Context context, long j, long j2, long j3, boolean z, int i) {
        com.android.internal.util.Preconditions.checkNotNull(context, "Context is null");
        return ((android.app.admin.DevicePolicyManager) context.getSystemService(android.content.Context.DEVICE_POLICY_SERVICE)).startViewCalendarEventInManagedProfile(j, j2, j3, z, i);
    }

    public static final class CalendarEntity implements android.provider.BaseColumns, android.provider.CalendarContract.SyncColumns, android.provider.CalendarContract.CalendarColumns {
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://com.android.calendar/calendar_entities");

        private CalendarEntity() {
        }

        public static android.content.EntityIterator newEntityIterator(android.database.Cursor cursor) {
            return new android.provider.CalendarContract.CalendarEntity.EntityIteratorImpl(cursor);
        }

        private static class EntityIteratorImpl extends android.content.CursorEntityIterator {
            public EntityIteratorImpl(android.database.Cursor cursor) {
                super(cursor);
            }

            @Override // android.content.CursorEntityIterator
            public android.content.Entity getEntityAndIncrementCursor(android.database.Cursor cursor) throws android.os.RemoteException {
                long j = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                android.content.ContentValues contentValues = new android.content.ContentValues();
                contentValues.put("_id", java.lang.Long.valueOf(j));
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "account_name");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "account_type");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "_sync_id");
                android.database.DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "dirty");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.SyncColumns.MUTATORS);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC1);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC2);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC3);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC4);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC5);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC6);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC7);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC8);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC9);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC10);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "name");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "calendar_displayName");
                android.database.DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarColumns.CALENDAR_COLOR);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarColumns.CALENDAR_COLOR_KEY);
                android.database.DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarColumns.CALENDAR_ACCESS_LEVEL);
                android.database.DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarColumns.VISIBLE);
                android.database.DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarColumns.SYNC_EVENTS);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.Calendars.CALENDAR_LOCATION);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarColumns.CALENDAR_TIME_ZONE);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarColumns.OWNER_ACCOUNT);
                android.database.DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarColumns.CAN_ORGANIZER_RESPOND);
                android.database.DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarColumns.CAN_MODIFY_TIME_ZONE);
                android.database.DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarColumns.MAX_REMINDERS);
                android.database.DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.SyncColumns.CAN_PARTIALLY_UPDATE);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarColumns.ALLOWED_REMINDERS);
                android.database.DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, "deleted");
                android.content.Entity entity = new android.content.Entity(contentValues);
                cursor.moveToNext();
                return entity;
            }
        }
    }

    public static final class Calendars implements android.provider.BaseColumns, android.provider.CalendarContract.SyncColumns, android.provider.CalendarContract.CalendarColumns {
        public static final java.lang.String DEFAULT_SORT_ORDER = "calendar_displayName";
        public static final java.lang.String NAME = "name";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://com.android.calendar/calendars");
        public static final android.net.Uri ENTERPRISE_CONTENT_URI = android.net.Uri.parse("content://com.android.calendar/enterprise/calendars");
        public static final java.lang.String CALENDAR_LOCATION = "calendar_location";
        public static final java.lang.String[] SYNC_WRITABLE_COLUMNS = {"account_name", "account_type", "_sync_id", "dirty", android.provider.CalendarContract.SyncColumns.MUTATORS, android.provider.CalendarContract.CalendarColumns.OWNER_ACCOUNT, android.provider.CalendarContract.CalendarColumns.MAX_REMINDERS, android.provider.CalendarContract.CalendarColumns.ALLOWED_REMINDERS, android.provider.CalendarContract.CalendarColumns.CAN_MODIFY_TIME_ZONE, android.provider.CalendarContract.CalendarColumns.CAN_ORGANIZER_RESPOND, android.provider.CalendarContract.SyncColumns.CAN_PARTIALLY_UPDATE, CALENDAR_LOCATION, android.provider.CalendarContract.CalendarColumns.CALENDAR_TIME_ZONE, android.provider.CalendarContract.CalendarColumns.CALENDAR_ACCESS_LEVEL, "deleted", android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC1, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC2, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC3, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC4, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC5, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC6, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC7, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC8, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC9, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC10};

        private Calendars() {
        }
    }

    public static final class Attendees implements android.provider.BaseColumns, android.provider.CalendarContract.AttendeesColumns, android.provider.CalendarContract.EventsColumns {
        private static final java.lang.String ATTENDEES_WHERE = "event_id=?";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://com.android.calendar/attendees");

        private Attendees() {
        }

        public static final android.database.Cursor query(android.content.ContentResolver contentResolver, long j, java.lang.String[] strArr) {
            return contentResolver.query(CONTENT_URI, strArr, ATTENDEES_WHERE, new java.lang.String[]{java.lang.Long.toString(j)}, null);
        }
    }

    public static final class EventsEntity implements android.provider.BaseColumns, android.provider.CalendarContract.SyncColumns, android.provider.CalendarContract.EventsColumns {
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://com.android.calendar/event_entities");

        private EventsEntity() {
        }

        public static android.content.EntityIterator newEntityIterator(android.database.Cursor cursor, android.content.ContentResolver contentResolver) {
            return new android.provider.CalendarContract.EventsEntity.EntityIteratorImpl(cursor, contentResolver);
        }

        public static android.content.EntityIterator newEntityIterator(android.database.Cursor cursor, android.content.ContentProviderClient contentProviderClient) {
            return new android.provider.CalendarContract.EventsEntity.EntityIteratorImpl(cursor, contentProviderClient);
        }

        private static class EntityIteratorImpl extends android.content.CursorEntityIterator {
            private static final int COLUMN_ATTENDEE_EMAIL = 1;
            private static final int COLUMN_ATTENDEE_IDENTITY = 5;
            private static final int COLUMN_ATTENDEE_ID_NAMESPACE = 6;
            private static final int COLUMN_ATTENDEE_NAME = 0;
            private static final int COLUMN_ATTENDEE_RELATIONSHIP = 2;
            private static final int COLUMN_ATTENDEE_STATUS = 4;
            private static final int COLUMN_ATTENDEE_TYPE = 3;
            private static final int COLUMN_ID = 0;
            private static final int COLUMN_METHOD = 1;
            private static final int COLUMN_MINUTES = 0;
            private static final int COLUMN_NAME = 1;
            private static final int COLUMN_VALUE = 2;
            private static final java.lang.String WHERE_EVENT_ID = "event_id=?";
            private final android.content.ContentProviderClient mProvider;
            private final android.content.ContentResolver mResolver;
            private static final java.lang.String[] REMINDERS_PROJECTION = {"minutes", android.provider.CalendarContract.RemindersColumns.METHOD};
            private static final java.lang.String[] ATTENDEES_PROJECTION = {android.provider.CalendarContract.AttendeesColumns.ATTENDEE_NAME, android.provider.CalendarContract.AttendeesColumns.ATTENDEE_EMAIL, android.provider.CalendarContract.AttendeesColumns.ATTENDEE_RELATIONSHIP, android.provider.CalendarContract.AttendeesColumns.ATTENDEE_TYPE, android.provider.CalendarContract.AttendeesColumns.ATTENDEE_STATUS, android.provider.CalendarContract.AttendeesColumns.ATTENDEE_IDENTITY, android.provider.CalendarContract.AttendeesColumns.ATTENDEE_ID_NAMESPACE};
            private static final java.lang.String[] EXTENDED_PROJECTION = {"_id", "name", "value"};

            public EntityIteratorImpl(android.database.Cursor cursor, android.content.ContentResolver contentResolver) {
                super(cursor);
                this.mResolver = contentResolver;
                this.mProvider = null;
            }

            public EntityIteratorImpl(android.database.Cursor cursor, android.content.ContentProviderClient contentProviderClient) {
                super(cursor);
                this.mResolver = null;
                this.mProvider = contentProviderClient;
            }

            @Override // android.content.CursorEntityIterator
            public android.content.Entity getEntityAndIncrementCursor(android.database.Cursor cursor) throws android.os.RemoteException {
                android.database.Cursor query;
                android.database.Cursor cursor2;
                android.database.Cursor query2;
                long j = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                android.content.ContentValues contentValues = new android.content.ContentValues();
                contentValues.put("_id", java.lang.Long.valueOf(j));
                android.database.DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.CALENDAR_ID);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "title");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "description");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.EVENT_LOCATION);
                android.database.DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.STATUS);
                android.database.DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.SELF_ATTENDEE_STATUS);
                android.database.DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.DTSTART);
                android.database.DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.DTEND);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "duration");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.EVENT_TIMEZONE);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.EVENT_END_TIMEZONE);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "allDay");
                android.database.DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.ACCESS_LEVEL);
                android.database.DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, "availability");
                android.database.DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.EVENT_COLOR);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.EVENT_COLOR_KEY);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.HAS_ALARM);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.HAS_EXTENDED_PROPERTIES);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.RRULE);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.RDATE);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.EXRULE);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.EXDATE);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.ORIGINAL_SYNC_ID);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.ORIGINAL_ID);
                android.database.DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.ORIGINAL_INSTANCE_TIME);
                android.database.DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.ORIGINAL_ALL_DAY);
                android.database.DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.LAST_DATE);
                android.database.DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.HAS_ATTENDEE_DATA);
                android.database.DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.GUESTS_CAN_INVITE_OTHERS);
                android.database.DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.GUESTS_CAN_MODIFY);
                android.database.DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.GUESTS_CAN_SEE_GUESTS);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.CUSTOM_APP_PACKAGE);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "customAppUri");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.UID_2445);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.ORGANIZER);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.IS_ORGANIZER);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "_sync_id");
                android.database.DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "dirty");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.SyncColumns.MUTATORS);
                android.database.DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.LAST_SYNCED);
                android.database.DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, contentValues, "deleted");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.SYNC_DATA1);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.SYNC_DATA2);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.SYNC_DATA3);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.SYNC_DATA4);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.SYNC_DATA5);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.SYNC_DATA6);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.SYNC_DATA7);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.SYNC_DATA8);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.SYNC_DATA9);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.EventsColumns.SYNC_DATA10);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC1);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC2);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC3);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC4);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC5);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC6);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC7);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC8);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC9);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC10);
                android.content.Entity entity = new android.content.Entity(contentValues);
                if (this.mResolver != null) {
                    query = this.mResolver.query(android.provider.CalendarContract.Reminders.CONTENT_URI, REMINDERS_PROJECTION, WHERE_EVENT_ID, new java.lang.String[]{java.lang.Long.toString(j)}, null);
                } else {
                    query = this.mProvider.query(android.provider.CalendarContract.Reminders.CONTENT_URI, REMINDERS_PROJECTION, WHERE_EVENT_ID, new java.lang.String[]{java.lang.Long.toString(j)}, null);
                }
                while (cursor2.moveToNext()) {
                    try {
                        android.content.ContentValues contentValues2 = new android.content.ContentValues();
                        contentValues2.put("minutes", java.lang.Integer.valueOf(cursor2.getInt(0)));
                        contentValues2.put(android.provider.CalendarContract.RemindersColumns.METHOD, java.lang.Integer.valueOf(cursor2.getInt(1)));
                        entity.addSubValue(android.provider.CalendarContract.Reminders.CONTENT_URI, contentValues2);
                    } finally {
                    }
                }
                cursor2.close();
                if (this.mResolver != null) {
                    query2 = this.mResolver.query(android.provider.CalendarContract.Attendees.CONTENT_URI, ATTENDEES_PROJECTION, WHERE_EVENT_ID, new java.lang.String[]{java.lang.Long.toString(j)}, null);
                } else {
                    query2 = this.mProvider.query(android.provider.CalendarContract.Attendees.CONTENT_URI, ATTENDEES_PROJECTION, WHERE_EVENT_ID, new java.lang.String[]{java.lang.Long.toString(j)}, null);
                }
                while (cursor2.moveToNext()) {
                    try {
                        android.content.ContentValues contentValues3 = new android.content.ContentValues();
                        contentValues3.put(android.provider.CalendarContract.AttendeesColumns.ATTENDEE_NAME, cursor2.getString(0));
                        contentValues3.put(android.provider.CalendarContract.AttendeesColumns.ATTENDEE_EMAIL, cursor2.getString(1));
                        contentValues3.put(android.provider.CalendarContract.AttendeesColumns.ATTENDEE_RELATIONSHIP, java.lang.Integer.valueOf(cursor2.getInt(2)));
                        contentValues3.put(android.provider.CalendarContract.AttendeesColumns.ATTENDEE_TYPE, java.lang.Integer.valueOf(cursor2.getInt(3)));
                        contentValues3.put(android.provider.CalendarContract.AttendeesColumns.ATTENDEE_STATUS, java.lang.Integer.valueOf(cursor2.getInt(4)));
                        contentValues3.put(android.provider.CalendarContract.AttendeesColumns.ATTENDEE_IDENTITY, cursor2.getString(5));
                        contentValues3.put(android.provider.CalendarContract.AttendeesColumns.ATTENDEE_ID_NAMESPACE, cursor2.getString(6));
                        entity.addSubValue(android.provider.CalendarContract.Attendees.CONTENT_URI, contentValues3);
                    } finally {
                    }
                }
                cursor2.close();
                if (this.mResolver != null) {
                    cursor2 = this.mResolver.query(android.provider.CalendarContract.ExtendedProperties.CONTENT_URI, EXTENDED_PROJECTION, WHERE_EVENT_ID, new java.lang.String[]{java.lang.Long.toString(j)}, null);
                } else {
                    cursor2 = this.mProvider.query(android.provider.CalendarContract.ExtendedProperties.CONTENT_URI, EXTENDED_PROJECTION, WHERE_EVENT_ID, new java.lang.String[]{java.lang.Long.toString(j)}, null);
                }
                while (cursor2.moveToNext()) {
                    try {
                        android.content.ContentValues contentValues4 = new android.content.ContentValues();
                        contentValues4.put("_id", cursor2.getString(0));
                        contentValues4.put("name", cursor2.getString(1));
                        contentValues4.put("value", cursor2.getString(2));
                        entity.addSubValue(android.provider.CalendarContract.ExtendedProperties.CONTENT_URI, contentValues4);
                    } finally {
                    }
                }
                cursor2.close();
                cursor.moveToNext();
                return entity;
            }
        }
    }

    public static final class Events implements android.provider.BaseColumns, android.provider.CalendarContract.SyncColumns, android.provider.CalendarContract.EventsColumns, android.provider.CalendarContract.CalendarColumns {
        private static final java.lang.String DEFAULT_SORT_ORDER = "";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://com.android.calendar/events");
        public static final android.net.Uri ENTERPRISE_CONTENT_URI = android.net.Uri.parse("content://com.android.calendar/enterprise/events");
        public static final android.net.Uri CONTENT_EXCEPTION_URI = android.net.Uri.parse("content://com.android.calendar/exception");
        public static java.lang.String[] PROVIDER_WRITABLE_COLUMNS = {"account_name", "account_type", android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC1, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC2, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC3, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC4, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC5, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC6, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC7, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC8, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC9, android.provider.CalendarContract.CalendarSyncColumns.CAL_SYNC10, android.provider.CalendarContract.CalendarColumns.ALLOWED_REMINDERS, android.provider.CalendarContract.CalendarColumns.ALLOWED_ATTENDEE_TYPES, android.provider.CalendarContract.CalendarColumns.ALLOWED_AVAILABILITY, android.provider.CalendarContract.CalendarColumns.CALENDAR_ACCESS_LEVEL, android.provider.CalendarContract.CalendarColumns.CALENDAR_COLOR, android.provider.CalendarContract.CalendarColumns.CALENDAR_TIME_ZONE, android.provider.CalendarContract.CalendarColumns.CAN_MODIFY_TIME_ZONE, android.provider.CalendarContract.CalendarColumns.CAN_ORGANIZER_RESPOND, "calendar_displayName", android.provider.CalendarContract.SyncColumns.CAN_PARTIALLY_UPDATE, android.provider.CalendarContract.CalendarColumns.SYNC_EVENTS, android.provider.CalendarContract.CalendarColumns.VISIBLE};
        public static final java.lang.String[] SYNC_WRITABLE_COLUMNS = {"_sync_id", "dirty", android.provider.CalendarContract.SyncColumns.MUTATORS, android.provider.CalendarContract.EventsColumns.SYNC_DATA1, android.provider.CalendarContract.EventsColumns.SYNC_DATA2, android.provider.CalendarContract.EventsColumns.SYNC_DATA3, android.provider.CalendarContract.EventsColumns.SYNC_DATA4, android.provider.CalendarContract.EventsColumns.SYNC_DATA5, android.provider.CalendarContract.EventsColumns.SYNC_DATA6, android.provider.CalendarContract.EventsColumns.SYNC_DATA7, android.provider.CalendarContract.EventsColumns.SYNC_DATA8, android.provider.CalendarContract.EventsColumns.SYNC_DATA9, android.provider.CalendarContract.EventsColumns.SYNC_DATA10};

        private Events() {
        }
    }

    public static final class Instances implements android.provider.BaseColumns, android.provider.CalendarContract.EventsColumns, android.provider.CalendarContract.CalendarColumns {
        public static final java.lang.String BEGIN = "begin";
        private static final java.lang.String DEFAULT_SORT_ORDER = "begin ASC";
        public static final java.lang.String END = "end";
        public static final java.lang.String END_DAY = "endDay";
        public static final java.lang.String END_MINUTE = "endMinute";
        public static final java.lang.String EVENT_ID = "event_id";
        public static final java.lang.String START_DAY = "startDay";
        public static final java.lang.String START_MINUTE = "startMinute";
        private static final java.lang.String WHERE_CALENDARS_SELECTED = "visible=?";
        private static final java.lang.String[] WHERE_CALENDARS_ARGS = {"1"};
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://com.android.calendar/instances/when");
        public static final android.net.Uri CONTENT_BY_DAY_URI = android.net.Uri.parse("content://com.android.calendar/instances/whenbyday");
        public static final android.net.Uri CONTENT_SEARCH_URI = android.net.Uri.parse("content://com.android.calendar/instances/search");
        public static final android.net.Uri CONTENT_SEARCH_BY_DAY_URI = android.net.Uri.parse("content://com.android.calendar/instances/searchbyday");
        public static final android.net.Uri ENTERPRISE_CONTENT_URI = android.net.Uri.parse("content://com.android.calendar/enterprise/instances/when");
        public static final android.net.Uri ENTERPRISE_CONTENT_BY_DAY_URI = android.net.Uri.parse("content://com.android.calendar/enterprise/instances/whenbyday");
        public static final android.net.Uri ENTERPRISE_CONTENT_SEARCH_URI = android.net.Uri.parse("content://com.android.calendar/enterprise/instances/search");
        public static final android.net.Uri ENTERPRISE_CONTENT_SEARCH_BY_DAY_URI = android.net.Uri.parse("content://com.android.calendar/enterprise/instances/searchbyday");

        private Instances() {
        }

        public static final android.database.Cursor query(android.content.ContentResolver contentResolver, java.lang.String[] strArr, long j, long j2) {
            android.net.Uri.Builder buildUpon = CONTENT_URI.buildUpon();
            android.content.ContentUris.appendId(buildUpon, j);
            android.content.ContentUris.appendId(buildUpon, j2);
            return contentResolver.query(buildUpon.build(), strArr, WHERE_CALENDARS_SELECTED, WHERE_CALENDARS_ARGS, DEFAULT_SORT_ORDER);
        }

        public static final android.database.Cursor query(android.content.ContentResolver contentResolver, java.lang.String[] strArr, long j, long j2, java.lang.String str) {
            android.net.Uri.Builder buildUpon = CONTENT_SEARCH_URI.buildUpon();
            android.content.ContentUris.appendId(buildUpon, j);
            android.content.ContentUris.appendId(buildUpon, j2);
            return contentResolver.query(buildUpon.appendPath(str).build(), strArr, WHERE_CALENDARS_SELECTED, WHERE_CALENDARS_ARGS, DEFAULT_SORT_ORDER);
        }
    }

    public static final class CalendarCache implements android.provider.CalendarContract.CalendarCacheColumns {
        public static final java.lang.String KEY_TIMEZONE_INSTANCES = "timezoneInstances";
        public static final java.lang.String KEY_TIMEZONE_INSTANCES_PREVIOUS = "timezoneInstancesPrevious";
        public static final java.lang.String KEY_TIMEZONE_TYPE = "timezoneType";
        public static final java.lang.String TIMEZONE_TYPE_AUTO = "auto";
        public static final java.lang.String TIMEZONE_TYPE_HOME = "home";
        public static final android.net.Uri URI = android.net.Uri.parse("content://com.android.calendar/properties");

        private CalendarCache() {
        }
    }

    public static final class CalendarMetaData implements android.provider.CalendarContract.CalendarMetaDataColumns, android.provider.BaseColumns {
        private CalendarMetaData() {
        }
    }

    public static final class EventDays implements android.provider.CalendarContract.EventDaysColumns {
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://com.android.calendar/instances/groupbyday");
        private static final java.lang.String SELECTION = "selected=1";

        private EventDays() {
        }

        public static final android.database.Cursor query(android.content.ContentResolver contentResolver, int i, int i2, java.lang.String[] strArr) {
            if (i2 < 1) {
                return null;
            }
            android.net.Uri.Builder buildUpon = CONTENT_URI.buildUpon();
            android.content.ContentUris.appendId(buildUpon, i);
            android.content.ContentUris.appendId(buildUpon, (i2 + i) - 1);
            return contentResolver.query(buildUpon.build(), strArr, SELECTION, null, "startDay");
        }
    }

    public static final class Reminders implements android.provider.BaseColumns, android.provider.CalendarContract.RemindersColumns, android.provider.CalendarContract.EventsColumns {
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://com.android.calendar/reminders");
        private static final java.lang.String REMINDERS_WHERE = "event_id=?";

        private Reminders() {
        }

        public static final android.database.Cursor query(android.content.ContentResolver contentResolver, long j, java.lang.String[] strArr) {
            return contentResolver.query(CONTENT_URI, strArr, REMINDERS_WHERE, new java.lang.String[]{java.lang.Long.toString(j)}, null);
        }
    }

    public static final class CalendarAlerts implements android.provider.BaseColumns, android.provider.CalendarContract.CalendarAlertsColumns, android.provider.CalendarContract.EventsColumns, android.provider.CalendarContract.CalendarColumns {
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://com.android.calendar/calendar_alerts");
        public static final android.net.Uri CONTENT_URI_BY_INSTANCE = android.net.Uri.parse("content://com.android.calendar/calendar_alerts/by_instance");
        private static final boolean DEBUG = false;
        private static final java.lang.String SORT_ORDER_ALARMTIME_ASC = "alarmTime ASC";
        public static final java.lang.String TABLE_NAME = "CalendarAlerts";
        private static final java.lang.String WHERE_ALARM_EXISTS = "event_id=? AND begin=? AND alarmTime=?";
        private static final java.lang.String WHERE_FINDNEXTALARMTIME = "alarmTime>=?";
        private static final java.lang.String WHERE_RESCHEDULE_MISSED_ALARMS = "state=0 AND alarmTime<? AND alarmTime>? AND end>=?";

        private CalendarAlerts() {
        }

        public static final android.net.Uri insert(android.content.ContentResolver contentResolver, long j, long j2, long j3, long j4, int i) {
            android.content.ContentValues contentValues = new android.content.ContentValues();
            contentValues.put("event_id", java.lang.Long.valueOf(j));
            contentValues.put("begin", java.lang.Long.valueOf(j2));
            contentValues.put("end", java.lang.Long.valueOf(j3));
            contentValues.put(android.provider.CalendarContract.CalendarAlertsColumns.ALARM_TIME, java.lang.Long.valueOf(j4));
            contentValues.put("creationTime", java.lang.Long.valueOf(java.lang.System.currentTimeMillis()));
            contentValues.put(android.provider.CalendarContract.CalendarAlertsColumns.RECEIVED_TIME, (java.lang.Integer) 0);
            contentValues.put(android.provider.CalendarContract.CalendarAlertsColumns.NOTIFY_TIME, (java.lang.Integer) 0);
            contentValues.put("state", (java.lang.Integer) 0);
            contentValues.put("minutes", java.lang.Integer.valueOf(i));
            return contentResolver.insert(CONTENT_URI, contentValues);
        }

        /* JADX WARN: Removed duplicated region for block: B:5:0x0045 A[DONT_GENERATE] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static final long findNextAlarmTime(android.content.ContentResolver contentResolver, long j) {
            long j2;
            java.lang.String str = "alarmTime>=" + j;
            android.database.Cursor query = contentResolver.query(CONTENT_URI, new java.lang.String[]{android.provider.CalendarContract.CalendarAlertsColumns.ALARM_TIME}, WHERE_FINDNEXTALARMTIME, new java.lang.String[]{java.lang.Long.toString(j)}, SORT_ORDER_ALARMTIME_ASC);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        j2 = query.getLong(0);
                        return j2;
                    }
                } finally {
                    if (query != null) {
                        query.close();
                    }
                }
            }
            j2 = -1;
            return j2;
        }

        public static final void rescheduleMissedAlarms(android.content.ContentResolver contentResolver, android.content.Context context, android.app.AlarmManager alarmManager) {
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            android.database.Cursor query = contentResolver.query(CONTENT_URI, new java.lang.String[]{android.provider.CalendarContract.CalendarAlertsColumns.ALARM_TIME}, WHERE_RESCHEDULE_MISSED_ALARMS, new java.lang.String[]{java.lang.Long.toString(currentTimeMillis), java.lang.Long.toString(currentTimeMillis - 86400000), java.lang.Long.toString(currentTimeMillis)}, SORT_ORDER_ALARMTIME_ASC);
            if (query == null) {
                return;
            }
            long j = -1;
            while (query.moveToNext()) {
                try {
                    long j2 = query.getLong(0);
                    if (j != j2) {
                        scheduleAlarm(context, alarmManager, j2);
                        j = j2;
                    }
                } finally {
                    query.close();
                }
            }
        }

        public static void scheduleAlarm(android.content.Context context, android.app.AlarmManager alarmManager, long j) {
            if (alarmManager == null) {
                alarmManager = (android.app.AlarmManager) context.getSystemService("alarm");
            }
            android.content.Intent intent = new android.content.Intent(android.provider.CalendarContract.ACTION_EVENT_REMINDER);
            intent.setData(android.content.ContentUris.withAppendedId(android.provider.CalendarContract.CONTENT_URI, j));
            intent.putExtra(android.provider.CalendarContract.CalendarAlertsColumns.ALARM_TIME, j);
            intent.setFlags(16777216);
            android.os.StrictMode.VmPolicy allowVmViolations = android.os.StrictMode.allowVmViolations();
            android.app.PendingIntent broadcast = android.app.PendingIntent.getBroadcast(context, 0, intent, 67108864);
            android.os.StrictMode.setVmPolicy(allowVmViolations);
            alarmManager.setExactAndAllowWhileIdle(0, j, broadcast);
        }

        /* JADX WARN: Removed duplicated region for block: B:5:0x0036 A[DONT_GENERATE] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static final boolean alarmExists(android.content.ContentResolver contentResolver, long j, long j2, long j3) {
            boolean z;
            android.database.Cursor query = contentResolver.query(CONTENT_URI, new java.lang.String[]{android.provider.CalendarContract.CalendarAlertsColumns.ALARM_TIME}, WHERE_ALARM_EXISTS, new java.lang.String[]{java.lang.Long.toString(j), java.lang.Long.toString(j2), java.lang.Long.toString(j3)}, null);
            if (query != null) {
                try {
                    if (query.getCount() > 0) {
                        z = true;
                        return z;
                    }
                } finally {
                    if (query != null) {
                        query.close();
                    }
                }
            }
            z = false;
            return z;
        }
    }

    public static final class Colors implements android.provider.CalendarContract.ColorsColumns {
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://com.android.calendar/colors");
        public static final java.lang.String TABLE_NAME = "Colors";

        private Colors() {
        }
    }

    public static final class ExtendedProperties implements android.provider.BaseColumns, android.provider.CalendarContract.ExtendedPropertiesColumns, android.provider.CalendarContract.EventsColumns {
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://com.android.calendar/extendedproperties");

        private ExtendedProperties() {
        }
    }

    public static final class SyncState implements android.provider.SyncStateContract.Columns {
        private static final java.lang.String CONTENT_DIRECTORY = "syncstate";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.CalendarContract.CONTENT_URI, "syncstate");

        private SyncState() {
        }
    }

    public static final class EventsRawTimes implements android.provider.BaseColumns, android.provider.CalendarContract.EventsRawTimesColumns {
        private EventsRawTimes() {
        }
    }
}
