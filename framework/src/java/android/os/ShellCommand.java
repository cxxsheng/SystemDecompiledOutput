package android.os;

/* loaded from: classes3.dex */
public abstract class ShellCommand extends com.android.modules.utils.BasicShellCommandHandler {
    private android.os.ResultReceiver mResultReceiver;
    private android.os.ShellCallback mShellCallback;

    public int exec(android.os.Binder binder, java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        this.mShellCallback = shellCallback;
        this.mResultReceiver = resultReceiver;
        int exec = super.exec(binder, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr);
        if (this.mResultReceiver != null) {
            this.mResultReceiver.send(exec, null);
        }
        return exec;
    }

    public android.os.ResultReceiver adoptResultReceiver() {
        android.os.ResultReceiver resultReceiver = this.mResultReceiver;
        this.mResultReceiver = null;
        return resultReceiver;
    }

    public android.os.ParcelFileDescriptor openFileForSystem(java.lang.String str, java.lang.String str2) {
        try {
            android.os.ParcelFileDescriptor openFile = getShellCallback().openFile(str, "u:r:system_server:s0", str2);
            if (openFile != null) {
                return openFile;
            }
        } catch (java.lang.RuntimeException e) {
            getErrPrintWriter().println("Failure opening file: " + e.getMessage());
        }
        getErrPrintWriter().println("Error: Unable to open file: " + str);
        if (str == null || !str.startsWith("/data/local/tmp/")) {
            getErrPrintWriter().println("Consider using a file under /data/local/tmp/");
            return null;
        }
        return null;
    }

    @Override // com.android.modules.utils.BasicShellCommandHandler
    public int handleDefaultCommands(java.lang.String str) {
        if ("dump".equals(str)) {
            java.lang.String[] strArr = new java.lang.String[getAllArgs().length - 1];
            java.lang.System.arraycopy(getAllArgs(), 1, strArr, 0, getAllArgs().length - 1);
            getTarget().doDump(getOutFileDescriptor(), getOutPrintWriter(), strArr);
            return 0;
        }
        return super.handleDefaultCommands(str);
    }

    @Override // com.android.modules.utils.BasicShellCommandHandler
    public java.lang.String peekNextArg() {
        return super.peekNextArg();
    }

    public android.os.ShellCallback getShellCallback() {
        return this.mShellCallback;
    }
}
