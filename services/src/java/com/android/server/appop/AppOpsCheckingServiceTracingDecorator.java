package com.android.server.appop;

/* loaded from: classes.dex */
public class AppOpsCheckingServiceTracingDecorator implements com.android.server.appop.AppOpsCheckingServiceInterface {
    private static final long TRACE_TAG = 64;
    private final com.android.server.appop.AppOpsCheckingServiceInterface mService;

    AppOpsCheckingServiceTracingDecorator(@android.annotation.NonNull com.android.server.appop.AppOpsCheckingServiceInterface appOpsCheckingServiceInterface) {
        this.mService = appOpsCheckingServiceInterface;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void writeState() {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#writeState");
        try {
            this.mService.writeState();
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void readState() {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#readState");
        try {
            this.mService.readState();
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void shutdown() {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#shutdown");
        try {
            this.mService.shutdown();
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void systemReady() {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#systemReady");
        try {
            this.mService.systemReady();
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public android.util.SparseIntArray getNonDefaultUidModes(int i, java.lang.String str) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#getNonDefaultUidModes");
        try {
            return this.mService.getNonDefaultUidModes(i, str);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public android.util.SparseIntArray getNonDefaultPackageModes(java.lang.String str, int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#getNonDefaultPackageModes");
        try {
            return this.mService.getNonDefaultPackageModes(str, i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public int getUidMode(int i, java.lang.String str, int i2) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#getUidMode");
        try {
            return this.mService.getUidMode(i, str, i2);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean setUidMode(int i, java.lang.String str, int i2, int i3) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#setUidMode");
        try {
            return this.mService.setUidMode(i, str, i2, i3);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public int getPackageMode(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#getPackageMode");
        try {
            return this.mService.getPackageMode(str, i, i2);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void setPackageMode(@android.annotation.NonNull java.lang.String str, int i, int i2, int i3) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#setPackageMode");
        try {
            this.mService.setPackageMode(str, i, i2, i3);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean removePackage(@android.annotation.NonNull java.lang.String str, int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#removePackage");
        try {
            return this.mService.removePackage(str, i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void removeUid(int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#removeUid");
        try {
            this.mService.removeUid(i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void clearAllModes() {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#clearAllModes");
        try {
            this.mService.clearAllModes();
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public android.util.SparseBooleanArray getForegroundOps(int i, java.lang.String str) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#getForegroundOps");
        try {
            return this.mService.getForegroundOps(i, str);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public android.util.SparseBooleanArray getForegroundOps(java.lang.String str, int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#getForegroundOps");
        try {
            return this.mService.getForegroundOps(str, i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean addAppOpsModeChangedListener(com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener appOpsModeChangedListener) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#addAppOpsModeChangedListener");
        try {
            return this.mService.addAppOpsModeChangedListener(appOpsModeChangedListener);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean removeAppOpsModeChangedListener(com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener appOpsModeChangedListener) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingAppOpsCheckingServiceInterfaceImpl#removeAppOpsModeChangedListener");
        try {
            return this.mService.removeAppOpsModeChangedListener(appOpsModeChangedListener);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }
}
