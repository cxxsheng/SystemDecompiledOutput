package com.android.server.am;

/* loaded from: classes.dex */
public class InstrumentationReporter {
    static final boolean DEBUG = false;
    static final int REPORT_TYPE_FINISHED = 1;
    static final int REPORT_TYPE_STATUS = 0;
    static final java.lang.String TAG = "ActivityManager";
    final java.lang.Object mLock = new java.lang.Object();
    java.util.ArrayList<com.android.server.am.InstrumentationReporter.Report> mPendingReports;
    java.lang.Thread mThread;

    final class MyThread extends java.lang.Thread {
        public MyThread() {
            super("InstrumentationReporter");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            java.util.ArrayList<com.android.server.am.InstrumentationReporter.Report> arrayList;
            android.os.Process.setThreadPriority(0);
            boolean z = false;
            while (true) {
                synchronized (com.android.server.am.InstrumentationReporter.this.mLock) {
                    arrayList = com.android.server.am.InstrumentationReporter.this.mPendingReports;
                    com.android.server.am.InstrumentationReporter.this.mPendingReports = null;
                    if (arrayList == null || arrayList.isEmpty()) {
                        if (!z) {
                            try {
                                com.android.server.am.InstrumentationReporter.this.mLock.wait(com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
                            } catch (java.lang.InterruptedException e) {
                            }
                        } else {
                            com.android.server.am.InstrumentationReporter.this.mThread = null;
                            return;
                        }
                    }
                }
                for (int i = 0; i < arrayList.size(); i++) {
                    com.android.server.am.InstrumentationReporter.Report report = arrayList.get(i);
                    try {
                        if (report.mType == 0) {
                            report.mWatcher.instrumentationStatus(report.mName, report.mResultCode, report.mResults);
                        } else {
                            report.mWatcher.instrumentationFinished(report.mName, report.mResultCode, report.mResults);
                        }
                    } catch (android.os.RemoteException e2) {
                        android.util.Slog.i(com.android.server.am.InstrumentationReporter.TAG, "Failure reporting to instrumentation watcher: comp=" + report.mName + " results=" + report.mResults);
                    }
                }
                z = false;
            }
            z = true;
        }
    }

    final class Report {
        final android.content.ComponentName mName;
        final int mResultCode;
        final android.os.Bundle mResults;
        final int mType;
        final android.app.IInstrumentationWatcher mWatcher;

        Report(int i, android.app.IInstrumentationWatcher iInstrumentationWatcher, android.content.ComponentName componentName, int i2, android.os.Bundle bundle) {
            this.mType = i;
            this.mWatcher = iInstrumentationWatcher;
            this.mName = componentName;
            this.mResultCode = i2;
            this.mResults = bundle;
            android.os.Binder.allowBlocking(this.mWatcher.asBinder());
        }
    }

    public void reportStatus(android.app.IInstrumentationWatcher iInstrumentationWatcher, android.content.ComponentName componentName, int i, android.os.Bundle bundle) {
        report(new com.android.server.am.InstrumentationReporter.Report(0, iInstrumentationWatcher, componentName, i, bundle));
    }

    public void reportFinished(android.app.IInstrumentationWatcher iInstrumentationWatcher, android.content.ComponentName componentName, int i, android.os.Bundle bundle) {
        report(new com.android.server.am.InstrumentationReporter.Report(1, iInstrumentationWatcher, componentName, i, bundle));
    }

    private void report(com.android.server.am.InstrumentationReporter.Report report) {
        synchronized (this.mLock) {
            try {
                if (this.mThread == null) {
                    this.mThread = new com.android.server.am.InstrumentationReporter.MyThread();
                    this.mThread.start();
                }
                if (this.mPendingReports == null) {
                    this.mPendingReports = new java.util.ArrayList<>();
                }
                this.mPendingReports.add(report);
                this.mLock.notifyAll();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
