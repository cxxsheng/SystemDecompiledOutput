package com.android.modules.utils;

/* loaded from: classes5.dex */
public abstract class BasicShellCommandHandler {
    protected static final boolean DEBUG = false;
    protected static final java.lang.String TAG = "ShellCommand";
    private int mArgPos;
    private java.lang.String[] mArgs;
    private java.lang.String mCmd;
    private java.lang.String mCurArgData;
    private java.io.FileDescriptor mErr;
    private java.io.PrintWriter mErrPrintWriter;
    private java.io.FileOutputStream mFileErr;
    private java.io.FileInputStream mFileIn;
    private java.io.FileOutputStream mFileOut;
    private java.io.FileDescriptor mIn;
    private java.io.InputStream mInputStream;
    private java.io.FileDescriptor mOut;
    private java.io.PrintWriter mOutPrintWriter;
    private android.os.Binder mTarget;

    public abstract int onCommand(java.lang.String str);

    public abstract void onHelp();

    public void init(android.os.Binder binder, java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, int i) {
        this.mTarget = binder;
        this.mIn = fileDescriptor;
        this.mOut = fileDescriptor2;
        this.mErr = fileDescriptor3;
        this.mArgs = strArr;
        this.mCmd = null;
        this.mArgPos = i;
        this.mCurArgData = null;
        this.mFileIn = null;
        this.mFileOut = null;
        this.mFileErr = null;
        this.mOutPrintWriter = null;
        this.mErrPrintWriter = null;
        this.mInputStream = null;
    }

    public int exec(android.os.Binder binder, java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr) {
        int i;
        java.lang.String str;
        if (strArr == null || strArr.length <= 0) {
            i = 0;
            str = null;
        } else {
            str = strArr[0];
            i = 1;
        }
        init(binder, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, i);
        this.mCmd = str;
        try {
            int onCommand = onCommand(this.mCmd);
        } catch (java.lang.Throwable th) {
            try {
                java.io.PrintWriter errPrintWriter = getErrPrintWriter();
                errPrintWriter.println();
                errPrintWriter.println("Exception occurred while executing '" + this.mCmd + "':");
                th.printStackTrace(errPrintWriter);
                if (this.mOutPrintWriter != null) {
                    this.mOutPrintWriter.flush();
                }
                if (this.mErrPrintWriter != null) {
                    this.mErrPrintWriter.flush();
                }
                return -1;
            } finally {
                if (this.mOutPrintWriter != null) {
                    this.mOutPrintWriter.flush();
                }
                if (this.mErrPrintWriter != null) {
                    this.mErrPrintWriter.flush();
                }
            }
        }
    }

    public java.io.FileDescriptor getOutFileDescriptor() {
        return this.mOut;
    }

    public java.io.OutputStream getRawOutputStream() {
        if (this.mFileOut == null) {
            this.mFileOut = new java.io.FileOutputStream(this.mOut);
        }
        return this.mFileOut;
    }

    public java.io.PrintWriter getOutPrintWriter() {
        if (this.mOutPrintWriter == null) {
            this.mOutPrintWriter = new java.io.PrintWriter(getRawOutputStream());
        }
        return this.mOutPrintWriter;
    }

    public java.io.FileDescriptor getErrFileDescriptor() {
        return this.mErr;
    }

    public java.io.OutputStream getRawErrorStream() {
        if (this.mFileErr == null) {
            this.mFileErr = new java.io.FileOutputStream(this.mErr);
        }
        return this.mFileErr;
    }

    public java.io.PrintWriter getErrPrintWriter() {
        if (this.mErr == null) {
            return getOutPrintWriter();
        }
        if (this.mErrPrintWriter == null) {
            this.mErrPrintWriter = new java.io.PrintWriter(getRawErrorStream());
        }
        return this.mErrPrintWriter;
    }

    public java.io.FileDescriptor getInFileDescriptor() {
        return this.mIn;
    }

    public java.io.InputStream getRawInputStream() {
        if (this.mFileIn == null) {
            this.mFileIn = new java.io.FileInputStream(this.mIn);
        }
        return this.mFileIn;
    }

    public java.io.InputStream getBufferedInputStream() {
        if (this.mInputStream == null) {
            this.mInputStream = new java.io.BufferedInputStream(getRawInputStream());
        }
        return this.mInputStream;
    }

    public java.lang.String getNextOption() {
        if (this.mCurArgData != null) {
            throw new java.lang.IllegalArgumentException("No argument expected after \"" + this.mArgs[this.mArgPos - 1] + "\"");
        }
        if (this.mArgPos >= this.mArgs.length) {
            return null;
        }
        java.lang.String str = this.mArgs[this.mArgPos];
        if (!str.startsWith(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE)) {
            return null;
        }
        this.mArgPos++;
        if (str.equals("--")) {
            return null;
        }
        if (str.length() > 1 && str.charAt(1) != '-') {
            if (str.length() > 2) {
                this.mCurArgData = str.substring(2);
                return str.substring(0, 2);
            }
            this.mCurArgData = null;
            return str;
        }
        this.mCurArgData = null;
        return str;
    }

    public java.lang.String getNextArg() {
        if (this.mCurArgData != null) {
            java.lang.String str = this.mCurArgData;
            this.mCurArgData = null;
            return str;
        }
        if (this.mArgPos >= this.mArgs.length) {
            return null;
        }
        java.lang.String[] strArr = this.mArgs;
        int i = this.mArgPos;
        this.mArgPos = i + 1;
        return strArr[i];
    }

    public java.lang.String peekNextArg() {
        if (this.mCurArgData != null) {
            return this.mCurArgData;
        }
        if (this.mArgPos < this.mArgs.length) {
            return this.mArgs[this.mArgPos];
        }
        return null;
    }

    public java.lang.String[] peekRemainingArgs() {
        java.lang.String[] strArr = new java.lang.String[getRemainingArgsCount()];
        for (int i = this.mArgPos; i < this.mArgs.length; i++) {
            strArr[i - this.mArgPos] = this.mArgs[i];
        }
        return strArr;
    }

    public int getRemainingArgsCount() {
        if (this.mArgPos >= this.mArgs.length) {
            return 0;
        }
        return this.mArgs.length - this.mArgPos;
    }

    public java.lang.String getNextArgRequired() {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            throw new java.lang.IllegalArgumentException("Argument expected after \"" + this.mArgs[this.mArgPos - 1] + "\"");
        }
        return nextArg;
    }

    public int handleDefaultCommands(java.lang.String str) {
        if (str == null || "help".equals(str) || "-h".equals(str)) {
            onHelp();
            return -1;
        }
        getOutPrintWriter().println("Unknown command: " + str);
        return -1;
    }

    public android.os.Binder getTarget() {
        return this.mTarget;
    }

    public java.lang.String[] getAllArgs() {
        return this.mArgs;
    }
}
