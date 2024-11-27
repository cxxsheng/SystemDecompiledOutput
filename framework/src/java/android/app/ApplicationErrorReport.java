package android.app;

/* loaded from: classes.dex */
public class ApplicationErrorReport implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.ApplicationErrorReport> CREATOR = new android.os.Parcelable.Creator<android.app.ApplicationErrorReport>() { // from class: android.app.ApplicationErrorReport.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ApplicationErrorReport createFromParcel(android.os.Parcel parcel) {
            return new android.app.ApplicationErrorReport(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ApplicationErrorReport[] newArray(int i) {
            return new android.app.ApplicationErrorReport[i];
        }
    };
    static final java.lang.String DEFAULT_ERROR_RECEIVER_PROPERTY = "ro.error.receiver.default";
    static final java.lang.String SYSTEM_APPS_ERROR_RECEIVER_PROPERTY = "ro.error.receiver.system.apps";
    public static final int TYPE_ANR = 2;
    public static final int TYPE_BATTERY = 3;
    public static final int TYPE_CRASH = 1;
    public static final int TYPE_NONE = 0;
    public static final int TYPE_RUNNING_SERVICE = 5;
    public android.app.ApplicationErrorReport.AnrInfo anrInfo;
    public android.app.ApplicationErrorReport.BatteryInfo batteryInfo;
    public android.app.ApplicationErrorReport.CrashInfo crashInfo;
    public java.lang.String installerPackageName;
    public java.lang.String packageName;
    public java.lang.String processName;
    public android.app.ApplicationErrorReport.RunningServiceInfo runningServiceInfo;
    public boolean systemApp;
    public long time;
    public int type;

    public ApplicationErrorReport() {
    }

    ApplicationErrorReport(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    public static android.content.ComponentName getErrorReportReceiver(android.content.Context context, java.lang.String str, int i) {
        android.content.ComponentName errorReportReceiver;
        android.content.ComponentName errorReportReceiver2;
        java.lang.String str2 = null;
        if (android.provider.Settings.Global.getInt(context.getContentResolver(), android.provider.Settings.Global.SEND_ACTION_APP_ERROR, 0) == 0) {
            return null;
        }
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        try {
            str2 = packageManager.getInstallerPackageName(str);
        } catch (java.lang.IllegalArgumentException e) {
        }
        if (str2 != null && (errorReportReceiver2 = getErrorReportReceiver(packageManager, str, str2)) != null) {
            return errorReportReceiver2;
        }
        if ((i & 1) != 0 && (errorReportReceiver = getErrorReportReceiver(packageManager, str, android.os.SystemProperties.get(SYSTEM_APPS_ERROR_RECEIVER_PROPERTY))) != null) {
            return errorReportReceiver;
        }
        return getErrorReportReceiver(packageManager, str, android.os.SystemProperties.get(DEFAULT_ERROR_RECEIVER_PROPERTY));
    }

    static android.content.ComponentName getErrorReportReceiver(android.content.pm.PackageManager packageManager, java.lang.String str, java.lang.String str2) {
        if (str2 == null || str2.length() == 0 || str2.equals(str)) {
            return null;
        }
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_APP_ERROR);
        intent.setPackage(str2);
        android.content.pm.ResolveInfo resolveActivity = packageManager.resolveActivity(intent, 0);
        if (resolveActivity == null || resolveActivity.activityInfo == null) {
            return null;
        }
        return new android.content.ComponentName(str2, resolveActivity.activityInfo.name);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.type);
        parcel.writeString(this.packageName);
        parcel.writeString(this.installerPackageName);
        parcel.writeString(this.processName);
        parcel.writeLong(this.time);
        parcel.writeInt(this.systemApp ? 1 : 0);
        parcel.writeInt(this.crashInfo != null ? 1 : 0);
        switch (this.type) {
            case 1:
                if (this.crashInfo != null) {
                    this.crashInfo.writeToParcel(parcel, i);
                    break;
                }
                break;
            case 2:
                this.anrInfo.writeToParcel(parcel, i);
                break;
            case 3:
                this.batteryInfo.writeToParcel(parcel, i);
                break;
            case 5:
                this.runningServiceInfo.writeToParcel(parcel, i);
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.type = parcel.readInt();
        this.packageName = parcel.readString();
        this.installerPackageName = parcel.readString();
        this.processName = parcel.readString();
        this.time = parcel.readLong();
        this.systemApp = parcel.readInt() == 1;
        boolean z = parcel.readInt() == 1;
        switch (this.type) {
            case 1:
                this.crashInfo = z ? new android.app.ApplicationErrorReport.CrashInfo(parcel) : null;
                this.anrInfo = null;
                this.batteryInfo = null;
                this.runningServiceInfo = null;
                break;
            case 2:
                this.anrInfo = new android.app.ApplicationErrorReport.AnrInfo(parcel);
                this.crashInfo = null;
                this.batteryInfo = null;
                this.runningServiceInfo = null;
                break;
            case 3:
                this.batteryInfo = new android.app.ApplicationErrorReport.BatteryInfo(parcel);
                this.anrInfo = null;
                this.crashInfo = null;
                this.runningServiceInfo = null;
                break;
            case 5:
                this.batteryInfo = null;
                this.anrInfo = null;
                this.crashInfo = null;
                this.runningServiceInfo = new android.app.ApplicationErrorReport.RunningServiceInfo(parcel);
                break;
        }
    }

    public static class CrashInfo {
        public java.lang.String crashTag;
        public java.lang.String exceptionClassName;
        public java.lang.String exceptionHandlerClassName;
        public java.lang.String exceptionMessage;
        public java.lang.String stackTrace;
        public java.lang.String throwClassName;
        public java.lang.String throwFileName;
        public int throwLineNumber;
        public java.lang.String throwMethodName;

        public CrashInfo() {
        }

        public CrashInfo(java.lang.Throwable th) {
            java.io.StringWriter stringWriter = new java.io.StringWriter();
            com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter((java.io.Writer) stringWriter, false, 256);
            th.printStackTrace(fastPrintWriter);
            fastPrintWriter.flush();
            this.stackTrace = sanitizeString(stringWriter.toString());
            this.exceptionMessage = th.getMessage();
            java.lang.Throwable th2 = th;
            while (th.getCause() != null) {
                th = th.getCause();
                if (th.getStackTrace() != null && th.getStackTrace().length > 0) {
                    th2 = th;
                }
                java.lang.String message = th.getMessage();
                if (message != null && message.length() > 0) {
                    this.exceptionMessage = message;
                }
            }
            java.lang.Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = java.lang.Thread.getDefaultUncaughtExceptionHandler();
            if (defaultUncaughtExceptionHandler != null) {
                this.exceptionHandlerClassName = defaultUncaughtExceptionHandler.getClass().getName();
            } else {
                this.exceptionHandlerClassName = "unknown";
            }
            this.exceptionClassName = th2.getClass().getName();
            if (th2.getStackTrace().length > 0) {
                java.lang.StackTraceElement stackTraceElement = th2.getStackTrace()[0];
                this.throwFileName = stackTraceElement.getFileName();
                this.throwClassName = stackTraceElement.getClassName();
                this.throwMethodName = stackTraceElement.getMethodName();
                this.throwLineNumber = stackTraceElement.getLineNumber();
            } else {
                this.throwFileName = "unknown";
                this.throwClassName = "unknown";
                this.throwMethodName = "unknown";
                this.throwLineNumber = 0;
            }
            this.exceptionMessage = sanitizeString(this.exceptionMessage);
        }

        public void appendStackTrace(java.lang.String str) {
            this.stackTrace = sanitizeString(this.stackTrace + str);
        }

        private java.lang.String sanitizeString(java.lang.String str) {
            if (str != null && str.length() > 20480) {
                java.lang.String str2 = "\n[TRUNCATED " + (str.length() - 20480) + " CHARS]\n";
                java.lang.StringBuilder sb = new java.lang.StringBuilder(20480 + str2.length());
                sb.append(str.substring(0, 10240));
                sb.append(str2);
                sb.append(str.substring(str.length() - 10240));
                return sb.toString();
            }
            return str;
        }

        public CrashInfo(android.os.Parcel parcel) {
            this.exceptionHandlerClassName = parcel.readString();
            this.exceptionClassName = parcel.readString();
            this.exceptionMessage = parcel.readString();
            this.throwFileName = parcel.readString();
            this.throwClassName = parcel.readString();
            this.throwMethodName = parcel.readString();
            this.throwLineNumber = parcel.readInt();
            this.stackTrace = parcel.readString();
            this.crashTag = parcel.readString();
        }

        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.dataPosition();
            parcel.writeString(this.exceptionHandlerClassName);
            parcel.writeString(this.exceptionClassName);
            parcel.writeString(this.exceptionMessage);
            parcel.writeString(this.throwFileName);
            parcel.writeString(this.throwClassName);
            parcel.writeString(this.throwMethodName);
            parcel.writeInt(this.throwLineNumber);
            parcel.writeString(this.stackTrace);
            parcel.writeString(this.crashTag);
            parcel.dataPosition();
        }

        public void dump(android.util.Printer printer, java.lang.String str) {
            printer.println(str + "exceptionHandlerClassName: " + this.exceptionHandlerClassName);
            printer.println(str + "exceptionClassName: " + this.exceptionClassName);
            printer.println(str + "exceptionMessage: " + this.exceptionMessage);
            printer.println(str + "throwFileName: " + this.throwFileName);
            printer.println(str + "throwClassName: " + this.throwClassName);
            printer.println(str + "throwMethodName: " + this.throwMethodName);
            printer.println(str + "throwLineNumber: " + this.throwLineNumber);
            printer.println(str + "stackTrace: " + this.stackTrace);
        }
    }

    public static class ParcelableCrashInfo extends android.app.ApplicationErrorReport.CrashInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.ApplicationErrorReport.ParcelableCrashInfo> CREATOR = new android.os.Parcelable.Creator<android.app.ApplicationErrorReport.ParcelableCrashInfo>() { // from class: android.app.ApplicationErrorReport.ParcelableCrashInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ApplicationErrorReport.ParcelableCrashInfo createFromParcel(android.os.Parcel parcel) {
                return new android.app.ApplicationErrorReport.ParcelableCrashInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ApplicationErrorReport.ParcelableCrashInfo[] newArray(int i) {
                return new android.app.ApplicationErrorReport.ParcelableCrashInfo[i];
            }
        };

        public ParcelableCrashInfo() {
        }

        public ParcelableCrashInfo(java.lang.Throwable th) {
            super(th);
        }

        public ParcelableCrashInfo(android.os.Parcel parcel) {
            super(parcel);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    public static class AnrInfo {
        public java.lang.String activity;
        public java.lang.String cause;
        public java.lang.String info;

        public AnrInfo() {
        }

        public AnrInfo(android.os.Parcel parcel) {
            this.activity = parcel.readString();
            this.cause = parcel.readString();
            this.info = parcel.readString();
        }

        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(this.activity);
            parcel.writeString(this.cause);
            parcel.writeString(this.info);
        }

        public void dump(android.util.Printer printer, java.lang.String str) {
            printer.println(str + "activity: " + this.activity);
            printer.println(str + "cause: " + this.cause);
            printer.println(str + "info: " + this.info);
        }
    }

    public static class BatteryInfo {
        public java.lang.String checkinDetails;
        public long durationMicros;
        public java.lang.String usageDetails;
        public int usagePercent;

        public BatteryInfo() {
        }

        public BatteryInfo(android.os.Parcel parcel) {
            this.usagePercent = parcel.readInt();
            this.durationMicros = parcel.readLong();
            this.usageDetails = parcel.readString();
            this.checkinDetails = parcel.readString();
        }

        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.usagePercent);
            parcel.writeLong(this.durationMicros);
            parcel.writeString(this.usageDetails);
            parcel.writeString(this.checkinDetails);
        }

        public void dump(android.util.Printer printer, java.lang.String str) {
            printer.println(str + "usagePercent: " + this.usagePercent);
            printer.println(str + "durationMicros: " + this.durationMicros);
            printer.println(str + "usageDetails: " + this.usageDetails);
            printer.println(str + "checkinDetails: " + this.checkinDetails);
        }
    }

    public static class RunningServiceInfo {
        public long durationMillis;
        public java.lang.String serviceDetails;

        public RunningServiceInfo() {
        }

        public RunningServiceInfo(android.os.Parcel parcel) {
            this.durationMillis = parcel.readLong();
            this.serviceDetails = parcel.readString();
        }

        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeLong(this.durationMillis);
            parcel.writeString(this.serviceDetails);
        }

        public void dump(android.util.Printer printer, java.lang.String str) {
            printer.println(str + "durationMillis: " + this.durationMillis);
            printer.println(str + "serviceDetails: " + this.serviceDetails);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void dump(android.util.Printer printer, java.lang.String str) {
        printer.println(str + "type: " + this.type);
        printer.println(str + "packageName: " + this.packageName);
        printer.println(str + "installerPackageName: " + this.installerPackageName);
        printer.println(str + "processName: " + this.processName);
        printer.println(str + "time: " + this.time);
        printer.println(str + "systemApp: " + this.systemApp);
        switch (this.type) {
            case 1:
                this.crashInfo.dump(printer, str);
                break;
            case 2:
                this.anrInfo.dump(printer, str);
                break;
            case 3:
                this.batteryInfo.dump(printer, str);
                break;
            case 5:
                this.runningServiceInfo.dump(printer, str);
                break;
        }
    }
}
