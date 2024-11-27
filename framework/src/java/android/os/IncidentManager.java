package android.os;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class IncidentManager {
    public static final int FLAG_ALLOW_CONSENTLESS_BUGREPORT = 2;
    public static final int FLAG_CONFIRMATION_DIALOG = 1;
    public static final int PRIVACY_POLICY_AUTO = 200;
    public static final int PRIVACY_POLICY_EXPLICIT = 100;
    public static final int PRIVACY_POLICY_LOCAL = 0;
    private static final java.lang.String TAG = "IncidentManager";
    public static final java.lang.String URI_AUTHORITY = "android.os.IncidentManager";
    public static final java.lang.String URI_PARAM_CALLING_PACKAGE = "pkg";
    public static final java.lang.String URI_PARAM_FLAGS = "flags";
    public static final java.lang.String URI_PARAM_ID = "id";
    public static final java.lang.String URI_PARAM_RECEIVER_CLASS = "receiver";
    public static final java.lang.String URI_PARAM_REPORT_ID = "r";
    public static final java.lang.String URI_PARAM_TIMESTAMP = "t";
    public static final java.lang.String URI_PATH = "/pending";
    public static final java.lang.String URI_SCHEME = "content";
    private android.os.IIncidentCompanion mCompanionService;
    private final android.content.Context mContext;
    private android.os.IIncidentManager mIncidentService;
    private java.lang.Object mLock = new java.lang.Object();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PendingReportFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PrivacyPolicy {
    }

    @android.annotation.SystemApi
    public static class PendingReport {
        private final int mFlags;
        private final java.lang.String mRequestingPackage;
        private final long mTimestamp;
        private final android.net.Uri mUri;

        public PendingReport(android.net.Uri uri) {
            try {
                this.mFlags = java.lang.Integer.parseInt(uri.getQueryParameter("flags"));
                java.lang.String queryParameter = uri.getQueryParameter("pkg");
                if (queryParameter == null) {
                    throw new java.lang.RuntimeException("Invalid URI: No pkg parameter. " + uri);
                }
                this.mRequestingPackage = queryParameter;
                try {
                    this.mTimestamp = java.lang.Long.parseLong(uri.getQueryParameter("t"));
                    this.mUri = uri;
                } catch (java.lang.NumberFormatException e) {
                    throw new java.lang.RuntimeException("Invalid URI: No t parameter. " + uri);
                }
            } catch (java.lang.NumberFormatException e2) {
                throw new java.lang.RuntimeException("Invalid URI: No flags parameter. " + uri);
            }
        }

        public java.lang.String getRequestingPackage() {
            return this.mRequestingPackage;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public long getTimestamp() {
            return this.mTimestamp;
        }

        public android.net.Uri getUri() {
            return this.mUri;
        }

        public java.lang.String toString() {
            return "PendingReport(" + getUri().toString() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.os.IncidentManager.PendingReport)) {
                return false;
            }
            android.os.IncidentManager.PendingReport pendingReport = (android.os.IncidentManager.PendingReport) obj;
            return this.mUri.equals(pendingReport.mUri) && this.mFlags == pendingReport.mFlags && this.mRequestingPackage.equals(pendingReport.mRequestingPackage) && this.mTimestamp == pendingReport.mTimestamp;
        }
    }

    @android.annotation.SystemApi
    public static class IncidentReport implements android.os.Parcelable, java.io.Closeable {
        public static final android.os.Parcelable.Creator<android.os.IncidentManager.IncidentReport> CREATOR = new android.os.Parcelable.Creator() { // from class: android.os.IncidentManager.IncidentReport.1
            @Override // android.os.Parcelable.Creator
            public android.os.IncidentManager.IncidentReport[] newArray(int i) {
                return new android.os.IncidentManager.IncidentReport[i];
            }

            @Override // android.os.Parcelable.Creator
            public android.os.IncidentManager.IncidentReport createFromParcel(android.os.Parcel parcel) {
                return new android.os.IncidentManager.IncidentReport(parcel);
            }
        };
        private android.os.ParcelFileDescriptor mFileDescriptor;
        private final int mPrivacyPolicy;
        private final long mTimestampNs;

        public IncidentReport(android.os.Parcel parcel) {
            this.mTimestampNs = parcel.readLong();
            this.mPrivacyPolicy = parcel.readInt();
            if (parcel.readInt() != 0) {
                this.mFileDescriptor = android.os.ParcelFileDescriptor.CREATOR.createFromParcel(parcel);
            } else {
                this.mFileDescriptor = null;
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            try {
                if (this.mFileDescriptor != null) {
                    this.mFileDescriptor.close();
                    this.mFileDescriptor = null;
                }
            } catch (java.io.IOException e) {
            }
        }

        public long getTimestamp() {
            return this.mTimestampNs / 1000000;
        }

        public long getPrivacyPolicy() {
            return this.mPrivacyPolicy;
        }

        public java.io.InputStream getInputStream() throws java.io.IOException {
            if (this.mFileDescriptor == null) {
                return null;
            }
            return new android.os.ParcelFileDescriptor.AutoCloseInputStream(this.mFileDescriptor);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return this.mFileDescriptor != null ? 1 : 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeLong(this.mTimestampNs);
            parcel.writeInt(this.mPrivacyPolicy);
            if (this.mFileDescriptor != null) {
                parcel.writeInt(1);
                this.mFileDescriptor.writeToParcel(parcel, i);
            } else {
                parcel.writeInt(0);
            }
        }
    }

    public static class AuthListener {
        android.os.IIncidentAuthListener.Stub mBinder = new android.os.IncidentManager.AuthListener.AnonymousClass1();
        java.util.concurrent.Executor mExecutor;

        /* renamed from: android.os.IncidentManager$AuthListener$1, reason: invalid class name */
        class AnonymousClass1 extends android.os.IIncidentAuthListener.Stub {
            AnonymousClass1() {
            }

            @Override // android.os.IIncidentAuthListener
            public void onReportApproved() {
                if (android.os.IncidentManager.AuthListener.this.mExecutor != null) {
                    android.os.IncidentManager.AuthListener.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.os.IncidentManager$AuthListener$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.IncidentManager.AuthListener.AnonymousClass1.this.lambda$onReportApproved$0();
                        }
                    });
                } else {
                    android.os.IncidentManager.AuthListener.this.onReportApproved();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onReportApproved$0() {
                android.os.IncidentManager.AuthListener.this.onReportApproved();
            }

            @Override // android.os.IIncidentAuthListener
            public void onReportDenied() {
                if (android.os.IncidentManager.AuthListener.this.mExecutor != null) {
                    android.os.IncidentManager.AuthListener.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.os.IncidentManager$AuthListener$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.IncidentManager.AuthListener.AnonymousClass1.this.lambda$onReportDenied$1();
                        }
                    });
                } else {
                    android.os.IncidentManager.AuthListener.this.onReportDenied();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onReportDenied$1() {
                android.os.IncidentManager.AuthListener.this.onReportDenied();
            }
        }

        public void onReportApproved() {
        }

        public void onReportDenied() {
        }
    }

    public static class DumpCallback {
        android.os.IIncidentDumpCallback.Stub mBinder = new android.os.IncidentManager.DumpCallback.AnonymousClass1();
        private java.util.concurrent.Executor mExecutor;
        private int mId;

        /* renamed from: android.os.IncidentManager$DumpCallback$1, reason: invalid class name */
        class AnonymousClass1 extends android.os.IIncidentDumpCallback.Stub {
            AnonymousClass1() {
            }

            @Override // android.os.IIncidentDumpCallback
            public void onDumpSection(final android.os.ParcelFileDescriptor parcelFileDescriptor) {
                if (android.os.IncidentManager.DumpCallback.this.mExecutor != null) {
                    android.os.IncidentManager.DumpCallback.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.os.IncidentManager$DumpCallback$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.IncidentManager.DumpCallback.AnonymousClass1.this.lambda$onDumpSection$0(parcelFileDescriptor);
                        }
                    });
                } else {
                    android.os.IncidentManager.DumpCallback.this.onDumpSection(android.os.IncidentManager.DumpCallback.this.mId, new android.os.ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor));
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onDumpSection$0(android.os.ParcelFileDescriptor parcelFileDescriptor) {
                android.os.IncidentManager.DumpCallback.this.onDumpSection(android.os.IncidentManager.DumpCallback.this.mId, new android.os.ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor));
            }
        }

        public void onDumpSection(int i, java.io.OutputStream outputStream) {
        }
    }

    public IncidentManager(android.content.Context context) {
        this.mContext = context;
    }

    public void reportIncident(android.os.IncidentReportArgs incidentReportArgs) {
        reportIncidentInternal(incidentReportArgs);
    }

    public void requestAuthorization(int i, java.lang.String str, int i2, android.os.IncidentManager.AuthListener authListener) {
        requestAuthorization(i, str, i2, this.mContext.getMainExecutor(), authListener);
    }

    public void requestAuthorization(int i, java.lang.String str, int i2, java.util.concurrent.Executor executor, android.os.IncidentManager.AuthListener authListener) {
        try {
            if (authListener.mExecutor != null) {
                throw new java.lang.RuntimeException("Do not reuse AuthListener objects when calling requestAuthorization");
            }
            authListener.mExecutor = executor;
            getCompanionServiceLocked().authorizeReport(i, str, null, null, i2, authListener.mBinder);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public void cancelAuthorization(android.os.IncidentManager.AuthListener authListener) {
        try {
            getCompanionServiceLocked().cancelAuthorization(authListener.mBinder);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public java.util.List<android.os.IncidentManager.PendingReport> getPendingReports() {
        try {
            java.util.List<java.lang.String> pendingReports = getCompanionServiceLocked().getPendingReports();
            int size = pendingReports.size();
            java.util.ArrayList arrayList = new java.util.ArrayList(size);
            for (int i = 0; i < size; i++) {
                arrayList.add(new android.os.IncidentManager.PendingReport(android.net.Uri.parse(pendingReports.get(i))));
            }
            return arrayList;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public void approveReport(android.net.Uri uri) {
        try {
            getCompanionServiceLocked().approveReport(uri.toString());
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public void denyReport(android.net.Uri uri) {
        try {
            getCompanionServiceLocked().denyReport(uri.toString());
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public void registerSection(int i, java.lang.String str, java.util.concurrent.Executor executor, android.os.IncidentManager.DumpCallback dumpCallback) {
        java.util.Objects.requireNonNull(executor, "executor cannot be null");
        java.util.Objects.requireNonNull(dumpCallback, "callback cannot be null");
        try {
            if (dumpCallback.mExecutor != null) {
                throw new java.lang.RuntimeException("Do not reuse DumpCallback objects when calling registerSection");
            }
            dumpCallback.mExecutor = executor;
            dumpCallback.mId = i;
            android.os.IIncidentManager iIncidentManagerLocked = getIIncidentManagerLocked();
            if (iIncidentManagerLocked == null) {
                android.util.Slog.e(TAG, "registerSection can't find incident binder service");
            } else {
                iIncidentManagerLocked.registerSection(i, str, dumpCallback.mBinder);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "registerSection failed", e);
        }
    }

    public void unregisterSection(int i) {
        try {
            android.os.IIncidentManager iIncidentManagerLocked = getIIncidentManagerLocked();
            if (iIncidentManagerLocked == null) {
                android.util.Slog.e(TAG, "unregisterSection can't find incident binder service");
            } else {
                iIncidentManagerLocked.unregisterSection(i);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "unregisterSection failed", e);
        }
    }

    public java.util.List<android.net.Uri> getIncidentReportList(java.lang.String str) {
        try {
            java.util.List<java.lang.String> incidentReportList = getCompanionServiceLocked().getIncidentReportList(this.mContext.getPackageName(), str);
            int size = incidentReportList.size();
            java.util.ArrayList arrayList = new java.util.ArrayList(size);
            for (int i = 0; i < size; i++) {
                arrayList.add(android.net.Uri.parse(incidentReportList.get(i)));
            }
            return arrayList;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("System server or incidentd going down", e);
        }
    }

    public android.os.IncidentManager.IncidentReport getIncidentReport(android.net.Uri uri) {
        java.lang.String queryParameter = uri.getQueryParameter("r");
        if (queryParameter == null) {
            return null;
        }
        java.lang.String queryParameter2 = uri.getQueryParameter("pkg");
        if (queryParameter2 == null) {
            throw new java.lang.RuntimeException("Invalid URI: No pkg parameter. " + uri);
        }
        java.lang.String queryParameter3 = uri.getQueryParameter("receiver");
        if (queryParameter3 == null) {
            throw new java.lang.RuntimeException("Invalid URI: No receiver parameter. " + uri);
        }
        try {
            return getCompanionServiceLocked().getIncidentReport(queryParameter2, queryParameter3, queryParameter);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("System server or incidentd going down", e);
        }
    }

    public void deleteIncidentReports(android.net.Uri uri) {
        if (uri == null) {
            try {
                getCompanionServiceLocked().deleteAllIncidentReports(this.mContext.getPackageName());
                return;
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException("System server or incidentd going down", e);
            }
        }
        java.lang.String queryParameter = uri.getQueryParameter("pkg");
        if (queryParameter == null) {
            throw new java.lang.RuntimeException("Invalid URI: No pkg parameter. " + uri);
        }
        java.lang.String queryParameter2 = uri.getQueryParameter("receiver");
        if (queryParameter2 == null) {
            throw new java.lang.RuntimeException("Invalid URI: No receiver parameter. " + uri);
        }
        java.lang.String queryParameter3 = uri.getQueryParameter("r");
        if (queryParameter3 == null) {
            throw new java.lang.RuntimeException("Invalid URI: No r parameter. " + uri);
        }
        try {
            getCompanionServiceLocked().deleteIncidentReports(queryParameter, queryParameter2, queryParameter3);
        } catch (android.os.RemoteException e2) {
            throw new java.lang.RuntimeException("System server or incidentd going down", e2);
        }
    }

    private void reportIncidentInternal(android.os.IncidentReportArgs incidentReportArgs) {
        try {
            android.os.IIncidentManager iIncidentManagerLocked = getIIncidentManagerLocked();
            if (iIncidentManagerLocked == null) {
                android.util.Slog.e(TAG, "reportIncident can't find incident binder service");
            } else {
                iIncidentManagerLocked.reportIncident(incidentReportArgs);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "reportIncident failed", e);
        }
    }

    private android.os.IIncidentManager getIIncidentManagerLocked() throws android.os.RemoteException {
        if (this.mIncidentService != null) {
            return this.mIncidentService;
        }
        synchronized (this.mLock) {
            if (this.mIncidentService != null) {
                return this.mIncidentService;
            }
            this.mIncidentService = android.os.IIncidentManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.INCIDENT_SERVICE));
            if (this.mIncidentService != null) {
                this.mIncidentService.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: android.os.IncidentManager$$ExternalSyntheticLambda1
                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        android.os.IncidentManager.this.lambda$getIIncidentManagerLocked$0();
                    }
                }, 0);
            }
            return this.mIncidentService;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getIIncidentManagerLocked$0() {
        synchronized (this.mLock) {
            this.mIncidentService = null;
        }
    }

    private android.os.IIncidentCompanion getCompanionServiceLocked() throws android.os.RemoteException {
        if (this.mCompanionService != null) {
            return this.mCompanionService;
        }
        synchronized (this) {
            if (this.mCompanionService != null) {
                return this.mCompanionService;
            }
            this.mCompanionService = android.os.IIncidentCompanion.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.INCIDENT_COMPANION_SERVICE));
            if (this.mCompanionService != null) {
                this.mCompanionService.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: android.os.IncidentManager$$ExternalSyntheticLambda0
                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        android.os.IncidentManager.this.lambda$getCompanionServiceLocked$1();
                    }
                }, 0);
            }
            return this.mCompanionService;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCompanionServiceLocked$1() {
        synchronized (this.mLock) {
            this.mCompanionService = null;
        }
    }
}
