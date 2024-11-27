package com.android.server.notification;

/* loaded from: classes2.dex */
public class NotificationShellCmd extends android.os.ShellCommand {
    public static final java.lang.String CHANNEL_ID = "shell_cmd";
    public static final int CHANNEL_IMP = 3;
    public static final java.lang.String CHANNEL_NAME = "Shell command";
    public static final int NOTIFICATION_ID = 2020;
    private static final java.lang.String NOTIFY_USAGE = "usage: cmd notification post [flags] <tag> <text>\n\nflags:\n  -h|--help\n  -v|--verbose\n  -t|--title <text>\n  -i|--icon <iconspec>\n  -I|--large-icon <iconspec>\n  -S|--style <style> [styleargs]\n  -c|--content-intent <intentspec>\n\nstyles: (default none)\n  bigtext\n  bigpicture --picture <iconspec>\n  inbox --line <text> --line <text> ...\n  messaging --conversation <title> --message <who>:<text> ...\n  media\n\nan <iconspec> is one of\n  file:///data/local/tmp/<img.png>\n  content://<provider>/<path>\n  @[<package>:]drawable/<img>\n  data:base64,<B64DATA==>\n\nan <intentspec> is (broadcast|service|activity) <args>\n  <args> are as described in `am start`";
    private static final java.lang.String TAG = "NotifShellCmd";
    private static final java.lang.String USAGE = "usage: cmd notification SUBCMD [args]\n\nSUBCMDs:\n  allow_listener COMPONENT [user_id (current user if not specified)]\n  disallow_listener COMPONENT [user_id (current user if not specified)]\n  allow_assistant COMPONENT [user_id (current user if not specified)]\n  remove_assistant COMPONENT [user_id (current user if not specified)]\n  set_dnd [on|none (same as on)|priority|alarms|all|off (same as all)]  allow_dnd PACKAGE [user_id (current user if not specified)]\n  disallow_dnd PACKAGE [user_id (current user if not specified)]\n  reset_assistant_user_set [user_id (current user if not specified)]\n  get_approved_assistant [user_id (current user if not specified)]\n  post [--help | flags] TAG TEXT\n  set_bubbles PACKAGE PREFERENCE (0=none 1=all 2=selected) [user_id (current user if not specified)]\n  set_bubbles_channel PACKAGE CHANNEL_ID ALLOW [user_id (current user if not specified)]\n  list\n  get <notification-key>\n  snooze --for <msec> <notification-key>\n  unsnooze <notification-key>\n";
    private final android.app.INotificationManager mBinderService;
    private final com.android.server.notification.NotificationManagerService mDirectService;
    private final android.content.pm.PackageManager mPm;

    public NotificationShellCmd(com.android.server.notification.NotificationManagerService notificationManagerService) {
        this.mDirectService = notificationManagerService;
        this.mBinderService = notificationManagerService.getBinderService();
        this.mPm = this.mDirectService.getContext().getPackageManager();
    }

    protected boolean checkShellCommandPermission(int i) {
        return i == 0 || i == 2000;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        java.lang.String str2;
        char c;
        int i;
        char c2;
        boolean z;
        java.lang.String str3;
        long j;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        if (callingUid == 0) {
            str2 = "root";
        } else {
            try {
                try {
                    java.lang.String[] packagesForUid = this.mPm.getPackagesForUid(callingUid);
                    str2 = (packagesForUid == null || packagesForUid.length <= 0) ? null : packagesForUid[0];
                } catch (java.lang.Exception e) {
                    android.util.Slog.e(TAG, "failed to get caller pkg", e);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    str2 = null;
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        if (!checkShellCommandPermission(callingUid)) {
            android.util.Slog.e(TAG, "error: permission denied: callingUid=" + callingUid + " callingPackage=" + str2);
            outPrintWriter.println("error: permission denied: callingUid=" + callingUid + " callingPackage=" + str2);
            return 255;
        }
        try {
            java.lang.String replace = str.replace('-', '_');
            c = 5;
            i = 4;
            switch (replace.hashCode()) {
                case -2056114370:
                    if (replace.equals("snoozed")) {
                        c2 = 15;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1736066994:
                    if (replace.equals("set_bubbles_channel")) {
                        c2 = '\n';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1325770982:
                    if (replace.equals("disallow_assistant")) {
                        c2 = 6;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1039689911:
                    if (replace.equals("notify")) {
                        c2 = '\f';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -897610266:
                    if (replace.equals("snooze")) {
                        c2 = 17;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -432999190:
                    if (replace.equals("allow_listener")) {
                        c2 = 3;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -429832618:
                    if (replace.equals("disallow_dnd")) {
                        c2 = 2;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -414550305:
                    if (replace.equals("get_approved_assistant")) {
                        c2 = '\b';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -11106881:
                    if (replace.equals("unsnooze")) {
                        c2 = 16;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 102230:
                    if (replace.equals("get")) {
                        c2 = 14;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 3322014:
                    if (replace.equals("list")) {
                        c2 = '\r';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 3446944:
                    if (replace.equals("post")) {
                        c2 = 11;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 212123274:
                    if (replace.equals("set_bubbles")) {
                        c2 = '\t';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 372345636:
                    if (replace.equals("allow_dnd")) {
                        c2 = 1;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 683492127:
                    if (replace.equals("reset_assistant_user_set")) {
                        c2 = 7;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 1257269496:
                    if (replace.equals("disallow_listener")) {
                        c2 = 4;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 1985310653:
                    if (replace.equals("set_dnd")) {
                        c2 = 0;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 2110474600:
                    if (replace.equals("allow_assistant")) {
                        c2 = 5;
                        break;
                    }
                    c2 = 65535;
                    break;
                default:
                    c2 = 65535;
                    break;
            }
        } catch (java.lang.Exception e2) {
            outPrintWriter.println("Error occurred. Check logcat for details. " + e2.getMessage());
            android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, "Error running shell command", e2);
        }
        switch (c2) {
            case 0:
                java.lang.String nextArgRequired = getNextArgRequired();
                switch (nextArgRequired.hashCode()) {
                    case -1415196606:
                        if (nextArgRequired.equals("alarms")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1165461084:
                        if (nextArgRequired.equals("priority")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3551:
                        if (nextArgRequired.equals("on")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 96673:
                        if (nextArgRequired.equals("all")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 109935:
                        if (nextArgRequired.equals("off")) {
                            break;
                        }
                        c = 65535;
                        break;
                    case 3387192:
                        if (nextArgRequired.equals("none")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                    case 1:
                        i = 3;
                        break;
                    case 2:
                        i = 2;
                        break;
                    case 3:
                        break;
                    case 4:
                    case 5:
                        i = 1;
                        break;
                    default:
                        i = 0;
                        break;
                }
                if (android.app.Flags.modesApi()) {
                    this.mBinderService.setInterruptionFilter(str2, i, true);
                } else {
                    this.mBinderService.setInterruptionFilter(str2, i, false);
                }
                return 0;
            case 1:
                java.lang.String nextArgRequired2 = getNextArgRequired();
                int currentUser = android.app.ActivityManager.getCurrentUser();
                if (peekNextArg() != null) {
                    currentUser = java.lang.Integer.parseInt(getNextArgRequired());
                }
                this.mBinderService.setNotificationPolicyAccessGrantedForUser(nextArgRequired2, currentUser, true);
                return 0;
            case 2:
                java.lang.String nextArgRequired3 = getNextArgRequired();
                int currentUser2 = android.app.ActivityManager.getCurrentUser();
                if (peekNextArg() != null) {
                    currentUser2 = java.lang.Integer.parseInt(getNextArgRequired());
                }
                this.mBinderService.setNotificationPolicyAccessGrantedForUser(nextArgRequired3, currentUser2, false);
                return 0;
            case 3:
                android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(getNextArgRequired());
                if (unflattenFromString == null) {
                    outPrintWriter.println("Invalid listener - must be a ComponentName");
                    return -1;
                }
                int currentUser3 = android.app.ActivityManager.getCurrentUser();
                if (peekNextArg() != null) {
                    currentUser3 = java.lang.Integer.parseInt(getNextArgRequired());
                }
                this.mBinderService.setNotificationListenerAccessGrantedForUser(unflattenFromString, currentUser3, true, true);
                return 0;
            case 4:
                android.content.ComponentName unflattenFromString2 = android.content.ComponentName.unflattenFromString(getNextArgRequired());
                if (unflattenFromString2 == null) {
                    outPrintWriter.println("Invalid listener - must be a ComponentName");
                    return -1;
                }
                int currentUser4 = android.app.ActivityManager.getCurrentUser();
                if (peekNextArg() != null) {
                    currentUser4 = java.lang.Integer.parseInt(getNextArgRequired());
                }
                this.mBinderService.setNotificationListenerAccessGrantedForUser(unflattenFromString2, currentUser4, false, true);
                return 0;
            case 5:
                android.content.ComponentName unflattenFromString3 = android.content.ComponentName.unflattenFromString(getNextArgRequired());
                if (unflattenFromString3 == null) {
                    outPrintWriter.println("Invalid assistant - must be a ComponentName");
                    return -1;
                }
                int currentUser5 = android.app.ActivityManager.getCurrentUser();
                if (peekNextArg() != null) {
                    currentUser5 = java.lang.Integer.parseInt(getNextArgRequired());
                }
                this.mBinderService.setNotificationAssistantAccessGrantedForUser(unflattenFromString3, currentUser5, true);
                return 0;
            case 6:
                android.content.ComponentName unflattenFromString4 = android.content.ComponentName.unflattenFromString(getNextArgRequired());
                if (unflattenFromString4 == null) {
                    outPrintWriter.println("Invalid assistant - must be a ComponentName");
                    return -1;
                }
                int currentUser6 = android.app.ActivityManager.getCurrentUser();
                if (peekNextArg() != null) {
                    currentUser6 = java.lang.Integer.parseInt(getNextArgRequired());
                }
                this.mBinderService.setNotificationAssistantAccessGrantedForUser(unflattenFromString4, currentUser6, false);
                return 0;
            case 7:
                int currentUser7 = android.app.ActivityManager.getCurrentUser();
                if (peekNextArg() != null) {
                    currentUser7 = java.lang.Integer.parseInt(getNextArgRequired());
                }
                this.mDirectService.resetAssistantUserSet(currentUser7);
                return 0;
            case '\b':
                int currentUser8 = android.app.ActivityManager.getCurrentUser();
                if (peekNextArg() != null) {
                    currentUser8 = java.lang.Integer.parseInt(getNextArgRequired());
                }
                android.content.ComponentName approvedAssistant = this.mDirectService.getApprovedAssistant(currentUser8);
                if (approvedAssistant == null) {
                    outPrintWriter.println("null");
                } else {
                    outPrintWriter.println(approvedAssistant.flattenToString());
                }
                return 0;
            case '\t':
                java.lang.String nextArgRequired4 = getNextArgRequired();
                int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
                if (parseInt > 3 || parseInt < 0) {
                    outPrintWriter.println("Invalid preference - must be between 0-3 (0=none 1=all 2=selected)");
                    return -1;
                }
                int currentUser9 = android.app.ActivityManager.getCurrentUser();
                if (peekNextArg() != null) {
                    currentUser9 = java.lang.Integer.parseInt(getNextArgRequired());
                }
                this.mBinderService.setBubblesAllowed(nextArgRequired4, android.os.UserHandle.getUid(currentUser9, this.mPm.getPackageUid(nextArgRequired4, 0)), parseInt);
                return 0;
            case '\n':
                java.lang.String nextArgRequired5 = getNextArgRequired();
                java.lang.String nextArgRequired6 = getNextArgRequired();
                boolean parseBoolean = java.lang.Boolean.parseBoolean(getNextArgRequired());
                int currentUser10 = android.app.ActivityManager.getCurrentUser();
                if (peekNextArg() != null) {
                    currentUser10 = java.lang.Integer.parseInt(getNextArgRequired());
                }
                android.app.NotificationChannel notificationChannel = this.mBinderService.getNotificationChannel(str2, currentUser10, nextArgRequired5, nextArgRequired6);
                notificationChannel.setAllowBubbles(parseBoolean);
                this.mBinderService.updateNotificationChannelForPackage(nextArgRequired5, android.os.UserHandle.getUid(currentUser10, this.mPm.getPackageUid(nextArgRequired5, 0)), notificationChannel);
                return 0;
            case 11:
            case '\f':
                doNotify(outPrintWriter, str2, callingUid);
                return 0;
            case '\r':
                java.util.Iterator<java.lang.String> it = this.mDirectService.mNotificationsByKey.keySet().iterator();
                while (it.hasNext()) {
                    outPrintWriter.println(it.next());
                }
                return 0;
            case 14:
                java.lang.String nextArgRequired7 = getNextArgRequired();
                com.android.server.notification.NotificationRecord notificationRecord = this.mDirectService.getNotificationRecord(nextArgRequired7);
                if (notificationRecord != null) {
                    notificationRecord.dump(outPrintWriter, "", this.mDirectService.getContext(), false);
                    return 0;
                }
                outPrintWriter.println("error: no active notification matching key: " + nextArgRequired7);
                return 1;
            case 15:
                new java.lang.StringBuilder();
                com.android.server.notification.SnoozeHelper snoozeHelper = this.mDirectService.mSnoozeHelper;
                for (com.android.server.notification.NotificationRecord notificationRecord2 : snoozeHelper.getSnoozed()) {
                    java.lang.String packageName = notificationRecord2.getSbn().getPackageName();
                    java.lang.String key = notificationRecord2.getKey();
                    outPrintWriter.println(key + " snoozed, time=" + snoozeHelper.getSnoozeTimeForUnpostedNotification(notificationRecord2.getUserId(), packageName, key) + " context=" + snoozeHelper.getSnoozeContextForUnpostedNotification(notificationRecord2.getUserId(), packageName, key));
                }
                return 0;
            case 16:
                java.lang.String nextArgRequired8 = getNextArgRequired();
                if ("--mute".equals(nextArgRequired8)) {
                    nextArgRequired8 = getNextArgRequired();
                    z = true;
                } else {
                    z = false;
                }
                if (this.mDirectService.mSnoozeHelper.getNotification(nextArgRequired8) == null) {
                    outPrintWriter.println("error: no snoozed otification matching key: " + nextArgRequired8);
                    return 1;
                }
                outPrintWriter.println("unsnoozing: " + nextArgRequired8);
                this.mDirectService.unsnoozeNotificationInt(nextArgRequired8, null, z);
                return 0;
            case 17:
                java.lang.String nextArg = getNextArg();
                java.lang.String str4 = "help";
                if (nextArg == null) {
                    nextArg = "help";
                } else if (nextArg.startsWith("--")) {
                    nextArg = nextArg.substring(2);
                }
                java.lang.String nextArg2 = getNextArg();
                java.lang.String nextArg3 = getNextArg();
                if (nextArg3 != null) {
                    str4 = nextArg;
                }
                switch (str4.hashCode()) {
                    case -1992012396:
                        if (str4.equals("duration")) {
                            break;
                        }
                        c = 65535;
                        break;
                    case -861311717:
                        if (str4.equals("condition")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 101577:
                        if (str4.equals("for")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 111443806:
                        if (str4.equals("until")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 383913633:
                        if (str4.equals("criterion")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 951530927:
                        if (str4.equals("context")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                    case 1:
                    case 2:
                        str3 = nextArg2;
                        j = 0;
                        break;
                    case 3:
                    case 4:
                    case 5:
                        j = java.lang.Long.parseLong(nextArg2);
                        str3 = null;
                        break;
                    default:
                        outPrintWriter.println("usage: cmd notification snooze (--for <msec> | --context <snooze-criterion-id>) <key>");
                        return 1;
                }
                if (j <= 0 && str3 == null) {
                    outPrintWriter.println("error: invalid value for --" + str4 + ": " + nextArg2);
                    return 1;
                }
                com.android.server.notification.NotificationShellCmd.ShellNls shellNls = new com.android.server.notification.NotificationShellCmd.ShellNls();
                shellNls.registerAsSystemService(this.mDirectService.getContext(), new android.content.ComponentName(com.android.server.notification.NotificationShellCmd.ShellNls.class.getPackageName(), com.android.server.notification.NotificationShellCmd.ShellNls.class.getName()), android.app.ActivityManager.getCurrentUser());
                if (!waitForBind(shellNls)) {
                    outPrintWriter.println("error: could not bind a listener in time");
                    return 1;
                }
                if (j > 0) {
                    outPrintWriter.println(java.lang.String.format("snoozing <%s> until time: %s", nextArg3, new java.util.Date(java.lang.System.currentTimeMillis() + j)));
                    shellNls.snoozeNotification(nextArg3, j);
                } else {
                    outPrintWriter.println(java.lang.String.format("snoozing <%s> until criterion: %s", nextArg3, str3));
                    shellNls.snoozeNotification(nextArg3, str3);
                }
                waitForSnooze(shellNls, nextArg3);
                shellNls.unregisterAsSystemService();
                waitForUnbind(shellNls);
                return 0;
            default:
                return handleDefaultCommands(str);
        }
    }

    void ensureChannel(java.lang.String str, int i) throws android.os.RemoteException {
        this.mBinderService.createNotificationChannels(str, new android.content.pm.ParceledListSlice(java.util.Collections.singletonList(new android.app.NotificationChannel(CHANNEL_ID, CHANNEL_NAME, 3))));
        android.util.Slog.v(com.android.server.notification.NotificationManagerService.TAG, "created channel: " + this.mBinderService.getNotificationChannel(str, android.os.UserHandle.getUserId(i), str, CHANNEL_ID));
    }

    android.graphics.drawable.Icon parseIcon(android.content.res.Resources resources, java.lang.String str) throws java.lang.IllegalArgumentException {
        if (android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.startsWith(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER)) {
            str = "file://" + str;
        }
        if (str.startsWith("http:") || str.startsWith("https:") || str.startsWith("content:") || str.startsWith("file:") || str.startsWith("android.resource:")) {
            return android.graphics.drawable.Icon.createWithContentUri(android.net.Uri.parse(str));
        }
        if (str.startsWith("@")) {
            int identifier = resources.getIdentifier(str.substring(1), "drawable", com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
            if (identifier != 0) {
                return android.graphics.drawable.Icon.createWithResource(resources, identifier);
            }
        } else if (str.startsWith("data:")) {
            byte[] decode = android.util.Base64.decode(str.substring(str.indexOf(44) + 1), 0);
            return android.graphics.drawable.Icon.createWithData(decode, 0, decode.length);
        }
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x038f, code lost:
    
        if (r3.equals(com.android.server.am.HostingRecord.HOSTING_TYPE_ACTIVITY) != false) goto L181;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x02ef, code lost:
    
        if (r3.equals("messaging") != false) goto L155;
     */
    /* JADX WARN: Removed duplicated region for block: B:116:0x043f  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0429 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int doNotify(java.io.PrintWriter printWriter, java.lang.String str, int i) throws android.os.RemoteException, java.net.URISyntaxException {
        int i2;
        android.app.Notification.InboxStyle inboxStyle;
        android.app.Notification.BigTextStyle bigTextStyle;
        android.graphics.drawable.Icon icon;
        android.app.Notification.MessagingStyle messagingStyle;
        boolean z;
        android.graphics.drawable.Icon parseIcon;
        java.lang.String nextArg;
        android.app.Notification.MessagingStyle messagingStyle2;
        android.app.Notification.InboxStyle inboxStyle2;
        android.app.PendingIntent activityAsUser;
        java.lang.String str2;
        android.content.Context context = this.mDirectService.getContext();
        android.content.res.Resources resources = context.getResources();
        android.app.Notification.Builder builder = new android.app.Notification.Builder(context, CHANNEL_ID);
        int i3 = 0;
        boolean z2 = false;
        android.app.Notification.MessagingStyle messagingStyle3 = null;
        android.app.Notification.InboxStyle inboxStyle3 = null;
        android.app.Notification.BigTextStyle bigTextStyle2 = null;
        android.graphics.drawable.Icon icon2 = null;
        android.app.Notification.BigPictureStyle bigPictureStyle = null;
        while (true) {
            java.lang.String nextOption = getNextOption();
            int i4 = 3;
            if (nextOption != null) {
                char c = 2;
                switch (nextOption.hashCode()) {
                    case -1954060697:
                        if (nextOption.equals("--message")) {
                            i2 = 25;
                            break;
                        }
                        i2 = -1;
                        break;
                    case -1613915119:
                        if (nextOption.equals("--style")) {
                            i2 = 19;
                            break;
                        }
                        i2 = -1;
                        break;
                    case -1613324104:
                        if (nextOption.equals("--title")) {
                            i2 = 3;
                            break;
                        }
                        i2 = -1;
                        break;
                    case -1210178960:
                        if (nextOption.equals("content-intent")) {
                            i2 = 15;
                            break;
                        }
                        i2 = -1;
                        break;
                    case -1183762788:
                        if (nextOption.equals("intent")) {
                            i2 = 17;
                            break;
                        }
                        i2 = -1;
                        break;
                    case -853380573:
                        if (nextOption.equals("--conversation")) {
                            i2 = 26;
                            break;
                        }
                        i2 = -1;
                        break;
                    case -45879957:
                        if (nextOption.equals("--large-icon")) {
                            i2 = 6;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 1468:
                        if (nextOption.equals("-I")) {
                            i2 = 5;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 1478:
                        if (nextOption.equals("-S")) {
                            i2 = 18;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 1494:
                        if (nextOption.equals("-c")) {
                            i2 = 13;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 1499:
                        if (nextOption.equals("-h")) {
                            i2 = 27;
                            break;
                        }
                        i2 = -1;
                        break;
                    case android.net.util.NetworkConstants.ETHER_MTU /* 1500 */:
                        if (nextOption.equals("-i")) {
                            i2 = 10;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 1511:
                        if (nextOption.equals("-t")) {
                            i2 = 2;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 1513:
                        if (nextOption.equals("-v")) {
                            i2 = i3;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 3226745:
                        if (nextOption.equals("icon")) {
                            i2 = 12;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 43017097:
                        if (nextOption.equals("--wtf")) {
                            i2 = 29;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 110371416:
                        if (nextOption.equals("title")) {
                            i2 = 4;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 704999290:
                        if (nextOption.equals("--big-text")) {
                            i2 = 22;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 705941520:
                        if (nextOption.equals("--content-intent")) {
                            i2 = 14;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 758833716:
                        if (nextOption.equals("largeicon")) {
                            i2 = 8;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 808239966:
                        if (nextOption.equals("--picture")) {
                            i2 = 23;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 1216250940:
                        if (nextOption.equals("--intent")) {
                            i2 = 16;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 1247228052:
                        if (nextOption.equals("--largeicon")) {
                            i2 = 7;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 1270815917:
                        if (nextOption.equals("--bigText")) {
                            i2 = 20;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 1271769229:
                        if (nextOption.equals("--bigtext")) {
                            i2 = 21;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 1333069025:
                        if (nextOption.equals("--help")) {
                            i2 = 28;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 1333096985:
                        if (nextOption.equals("--icon")) {
                            i2 = 11;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 1333192084:
                        if (nextOption.equals("--line")) {
                            i2 = 24;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 1737088994:
                        if (nextOption.equals("--verbose")) {
                            i2 = 1;
                            break;
                        }
                        i2 = -1;
                        break;
                    case 1993764811:
                        if (nextOption.equals("large-icon")) {
                            i2 = 9;
                            break;
                        }
                        i2 = -1;
                        break;
                    default:
                        i2 = -1;
                        break;
                }
                switch (i2) {
                    case 0:
                    case 1:
                        z2 = true;
                        icon2 = icon2;
                        i3 = 0;
                    case 2:
                    case 3:
                    case 4:
                        inboxStyle = inboxStyle3;
                        bigTextStyle = bigTextStyle2;
                        icon = icon2;
                        messagingStyle = messagingStyle3;
                        builder.setContentTitle(getNextArgRequired());
                        messagingStyle3 = messagingStyle;
                        inboxStyle3 = inboxStyle;
                        icon2 = icon;
                        bigTextStyle2 = bigTextStyle;
                        i3 = 0;
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        inboxStyle = inboxStyle3;
                        bigTextStyle = bigTextStyle2;
                        icon = icon2;
                        z = true;
                        messagingStyle = messagingStyle3;
                        java.lang.String nextArgRequired = getNextArgRequired();
                        parseIcon = parseIcon(resources, nextArgRequired);
                        if (parseIcon != null) {
                            printWriter.println("error: invalid icon: " + nextArgRequired);
                            return -1;
                        }
                        if (z) {
                            builder.setLargeIcon(parseIcon);
                            messagingStyle3 = messagingStyle;
                            inboxStyle3 = inboxStyle;
                            icon2 = icon;
                            bigTextStyle2 = bigTextStyle;
                            i3 = 0;
                        } else {
                            messagingStyle3 = messagingStyle;
                            inboxStyle3 = inboxStyle;
                            bigTextStyle2 = bigTextStyle;
                            icon2 = parseIcon;
                            i3 = 0;
                        }
                    case 10:
                    case 11:
                    case 12:
                        inboxStyle = inboxStyle3;
                        bigTextStyle = bigTextStyle2;
                        icon = icon2;
                        messagingStyle = messagingStyle3;
                        z = false;
                        java.lang.String nextArgRequired2 = getNextArgRequired();
                        parseIcon = parseIcon(resources, nextArgRequired2);
                        if (parseIcon != null) {
                        }
                        break;
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                        icon = icon2;
                        java.lang.String peekNextArg = peekNextArg();
                        switch (peekNextArg.hashCode()) {
                            case -1655966961:
                                break;
                            case -1618876223:
                                if (peekNextArg.equals("broadcast")) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1984153269:
                                if (peekNextArg.equals(com.android.server.am.HostingRecord.HOSTING_TYPE_SERVICE)) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                            case 1:
                            case 2:
                                nextArg = getNextArg();
                                break;
                            default:
                                nextArg = null;
                                break;
                        }
                        android.content.Intent parseCommandArgs = android.content.Intent.parseCommandArgs(this, null);
                        if (parseCommandArgs.getData() != null) {
                            messagingStyle2 = messagingStyle3;
                            inboxStyle2 = inboxStyle3;
                        } else {
                            java.lang.StringBuilder sb = new java.lang.StringBuilder();
                            sb.append("xyz:");
                            messagingStyle2 = messagingStyle3;
                            inboxStyle2 = inboxStyle3;
                            sb.append(java.lang.System.currentTimeMillis());
                            parseCommandArgs.setData(android.net.Uri.parse(sb.toString()));
                        }
                        if ("broadcast".equals(nextArg)) {
                            activityAsUser = android.app.PendingIntent.getBroadcastAsUser(context, 0, parseCommandArgs, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD, android.os.UserHandle.CURRENT);
                            messagingStyle = messagingStyle2;
                            inboxStyle = inboxStyle2;
                            bigTextStyle = bigTextStyle2;
                        } else if (com.android.server.am.HostingRecord.HOSTING_TYPE_SERVICE.equals(nextArg)) {
                            activityAsUser = android.app.PendingIntent.getService(context, 0, parseCommandArgs, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD);
                            messagingStyle = messagingStyle2;
                            inboxStyle = inboxStyle2;
                            bigTextStyle = bigTextStyle2;
                        } else {
                            messagingStyle = messagingStyle2;
                            inboxStyle = inboxStyle2;
                            bigTextStyle = bigTextStyle2;
                            activityAsUser = android.app.PendingIntent.getActivityAsUser(context, 0, parseCommandArgs, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD, null, android.os.UserHandle.CURRENT);
                        }
                        builder.setContentIntent(activityAsUser);
                        messagingStyle3 = messagingStyle;
                        inboxStyle3 = inboxStyle;
                        icon2 = icon;
                        bigTextStyle2 = bigTextStyle;
                        i3 = 0;
                    case 18:
                    case 19:
                        android.graphics.drawable.Icon icon3 = icon2;
                        java.lang.String lowerCase = getNextArgRequired().toLowerCase();
                        switch (lowerCase.hashCode()) {
                            case -1440008444:
                                break;
                            case -114212307:
                                if (lowerCase.equals("bigtext")) {
                                    i4 = 0;
                                    break;
                                }
                                i4 = -1;
                                break;
                            case -44548098:
                                if (lowerCase.equals("bigpicture")) {
                                    i4 = 1;
                                    break;
                                }
                                i4 = -1;
                                break;
                            case 100344454:
                                if (lowerCase.equals("inbox")) {
                                    i4 = 2;
                                    break;
                                }
                                i4 = -1;
                                break;
                            case 103772132:
                                if (lowerCase.equals("media")) {
                                    i4 = 4;
                                    break;
                                }
                                i4 = -1;
                                break;
                            default:
                                i4 = -1;
                                break;
                        }
                        switch (i4) {
                            case 0:
                                bigTextStyle2 = new android.app.Notification.BigTextStyle();
                                builder.setStyle(bigTextStyle2);
                                break;
                            case 1:
                                bigPictureStyle = new android.app.Notification.BigPictureStyle();
                                builder.setStyle(bigPictureStyle);
                                break;
                            case 2:
                                inboxStyle3 = new android.app.Notification.InboxStyle();
                                builder.setStyle(inboxStyle3);
                                break;
                            case 3:
                                if (!"--user".equals(peekNextArg())) {
                                    str2 = "You";
                                } else {
                                    getNextArg();
                                    str2 = getNextArgRequired();
                                }
                                messagingStyle3 = new android.app.Notification.MessagingStyle(new android.app.Person.Builder().setName(str2).build());
                                builder.setStyle(messagingStyle3);
                                break;
                            case 4:
                                builder.setStyle(new android.app.Notification.MediaStyle());
                                break;
                            default:
                                throw new java.lang.IllegalArgumentException("unrecognized notification style: " + lowerCase);
                        }
                        icon2 = icon3;
                        i3 = 0;
                    case 20:
                    case 21:
                    case 22:
                        icon = icon2;
                        if (bigTextStyle2 == null) {
                            throw new java.lang.IllegalArgumentException("--bigtext requires --style bigtext");
                        }
                        bigTextStyle2.bigText(getNextArgRequired());
                        messagingStyle = messagingStyle3;
                        inboxStyle = inboxStyle3;
                        bigTextStyle = bigTextStyle2;
                        messagingStyle3 = messagingStyle;
                        inboxStyle3 = inboxStyle;
                        icon2 = icon;
                        bigTextStyle2 = bigTextStyle;
                        i3 = 0;
                    case 23:
                        icon = icon2;
                        if (bigPictureStyle == null) {
                            throw new java.lang.IllegalArgumentException("--picture requires --style bigpicture");
                        }
                        java.lang.String nextArgRequired3 = getNextArgRequired();
                        android.graphics.drawable.Icon parseIcon2 = parseIcon(resources, nextArgRequired3);
                        if (parseIcon2 == null) {
                            throw new java.lang.IllegalArgumentException("bad picture spec: " + nextArgRequired3);
                        }
                        android.graphics.drawable.Drawable loadDrawable = parseIcon2.loadDrawable(context);
                        if (loadDrawable instanceof android.graphics.drawable.BitmapDrawable) {
                            bigPictureStyle.bigPicture(((android.graphics.drawable.BitmapDrawable) loadDrawable).getBitmap());
                            messagingStyle = messagingStyle3;
                            inboxStyle = inboxStyle3;
                            bigTextStyle = bigTextStyle2;
                            messagingStyle3 = messagingStyle;
                            inboxStyle3 = inboxStyle;
                            icon2 = icon;
                            bigTextStyle2 = bigTextStyle;
                            i3 = 0;
                        } else {
                            throw new java.lang.IllegalArgumentException("not a bitmap: " + nextArgRequired3);
                        }
                    case 24:
                        icon = icon2;
                        if (inboxStyle3 == null) {
                            throw new java.lang.IllegalArgumentException("--line requires --style inbox");
                        }
                        inboxStyle3.addLine(getNextArgRequired());
                        messagingStyle = messagingStyle3;
                        inboxStyle = inboxStyle3;
                        bigTextStyle = bigTextStyle2;
                        messagingStyle3 = messagingStyle;
                        inboxStyle3 = inboxStyle;
                        icon2 = icon;
                        bigTextStyle2 = bigTextStyle;
                        i3 = 0;
                    case 25:
                        if (messagingStyle3 == null) {
                            throw new java.lang.IllegalArgumentException("--message requires --style messaging");
                        }
                        java.lang.String[] split = getNextArgRequired().split(":", 2);
                        if (split.length > 1) {
                            icon = icon2;
                            messagingStyle3.addMessage(split[1], java.lang.System.currentTimeMillis(), split[i3]);
                            messagingStyle = messagingStyle3;
                            inboxStyle = inboxStyle3;
                            bigTextStyle = bigTextStyle2;
                        } else {
                            icon = icon2;
                            messagingStyle3.addMessage(split[i3], java.lang.System.currentTimeMillis(), new java.lang.String[]{messagingStyle3.getUserDisplayName().toString(), "Them"}[messagingStyle3.getMessages().size() % 2]);
                            messagingStyle = messagingStyle3;
                            inboxStyle = inboxStyle3;
                            bigTextStyle = bigTextStyle2;
                        }
                        messagingStyle3 = messagingStyle;
                        inboxStyle3 = inboxStyle;
                        icon2 = icon;
                        bigTextStyle2 = bigTextStyle;
                        i3 = 0;
                    case 26:
                        if (messagingStyle3 == null) {
                            throw new java.lang.IllegalArgumentException("--conversation requires --style messaging");
                        }
                        messagingStyle3.setConversationTitle(getNextArgRequired());
                        inboxStyle = inboxStyle3;
                        bigTextStyle = bigTextStyle2;
                        icon = icon2;
                        messagingStyle = messagingStyle3;
                        messagingStyle3 = messagingStyle;
                        inboxStyle3 = inboxStyle;
                        icon2 = icon;
                        bigTextStyle2 = bigTextStyle;
                        i3 = 0;
                    default:
                        printWriter.println(NOTIFY_USAGE);
                        return i3;
                }
            } else {
                android.graphics.drawable.Icon icon4 = icon2;
                java.lang.String nextArg2 = getNextArg();
                java.lang.String nextArg3 = getNextArg();
                if (nextArg2 == null || nextArg3 == null) {
                    printWriter.println(NOTIFY_USAGE);
                    return -1;
                }
                builder.setContentText(nextArg3);
                if (icon4 == null) {
                    builder.setSmallIcon(android.R.drawable.stat_notify_chat);
                } else {
                    builder.setSmallIcon(icon4);
                }
                ensureChannel(str, i);
                android.app.Notification build = builder.build();
                printWriter.println("posting:\n  " + build);
                android.util.Slog.v("NotificationManager", "posting: " + build);
                this.mBinderService.enqueueNotificationWithTag(str, str, nextArg2, NOTIFICATION_ID, build, android.os.UserHandle.getUserId(i));
                if (!z2) {
                    return 0;
                }
                com.android.server.notification.NotificationRecord findNotificationLocked = this.mDirectService.findNotificationLocked(str, nextArg2, NOTIFICATION_ID, android.os.UserHandle.getUserId(i));
                while (true) {
                    int i5 = i4 - 1;
                    if (i4 > 0 && findNotificationLocked == null) {
                        try {
                            printWriter.println("waiting for notification to post...");
                            java.lang.Thread.sleep(500L);
                        } catch (java.lang.InterruptedException e) {
                        }
                        findNotificationLocked = this.mDirectService.findNotificationLocked(str, nextArg2, NOTIFICATION_ID, android.os.UserHandle.getUserId(i));
                        i4 = i5;
                    }
                }
                if (findNotificationLocked == null) {
                    printWriter.println("warning: couldn't find notification after enqueueing");
                    return 0;
                }
                printWriter.println("posted: ");
                findNotificationLocked.dump(printWriter, "  ", context, false);
                return 0;
            }
        }
    }

    private void waitForSnooze(com.android.server.notification.NotificationShellCmd.ShellNls shellNls, java.lang.String str) {
        for (int i = 0; i < 20; i++) {
            for (android.service.notification.StatusBarNotification statusBarNotification : shellNls.getSnoozedNotifications()) {
                if (statusBarNotification.getKey().equals(str)) {
                    return;
                }
            }
            try {
                java.lang.Thread.sleep(100L);
            } catch (java.lang.InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean waitForBind(com.android.server.notification.NotificationShellCmd.ShellNls shellNls) {
        for (int i = 0; i < 20; i++) {
            if (shellNls.isConnected) {
                android.util.Slog.i(TAG, "Bound Shell NLS");
                return true;
            }
            try {
                java.lang.Thread.sleep(100L);
            } catch (java.lang.InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void waitForUnbind(com.android.server.notification.NotificationShellCmd.ShellNls shellNls) {
        for (int i = 0; i < 10; i++) {
            if (!shellNls.isConnected) {
                android.util.Slog.i(TAG, "Unbound Shell NLS");
                return;
            }
            try {
                java.lang.Thread.sleep(100L);
            } catch (java.lang.InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void onHelp() {
        getOutPrintWriter().println(USAGE);
    }

    @android.annotation.SuppressLint({"OverrideAbstract"})
    private static class ShellNls extends android.service.notification.NotificationListenerService {
        private static com.android.server.notification.NotificationShellCmd.ShellNls sNotificationListenerInstance = null;
        boolean isConnected;

        private ShellNls() {
        }

        @Override // android.service.notification.NotificationListenerService
        public void onListenerConnected() {
            super.onListenerConnected();
            sNotificationListenerInstance = this;
            this.isConnected = true;
        }

        @Override // android.service.notification.NotificationListenerService
        public void onListenerDisconnected() {
            this.isConnected = false;
        }

        public static com.android.server.notification.NotificationShellCmd.ShellNls getInstance() {
            return sNotificationListenerInstance;
        }
    }
}
