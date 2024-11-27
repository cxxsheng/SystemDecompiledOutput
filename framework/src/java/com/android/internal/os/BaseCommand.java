package com.android.internal.os;

/* loaded from: classes4.dex */
public abstract class BaseCommand {
    public static final java.lang.String FATAL_ERROR_CODE = "Error type 1";
    public static final java.lang.String NO_CLASS_ERROR_CODE = "Error type 3";
    public static final java.lang.String NO_SYSTEM_ERROR_CODE = "Error type 2";
    protected final com.android.modules.utils.BasicShellCommandHandler mArgs = new com.android.modules.utils.BasicShellCommandHandler() { // from class: com.android.internal.os.BaseCommand.1
        @Override // com.android.modules.utils.BasicShellCommandHandler
        public int onCommand(java.lang.String str) {
            return 0;
        }

        @Override // com.android.modules.utils.BasicShellCommandHandler
        public void onHelp() {
        }
    };
    private java.lang.String[] mRawArgs;

    public abstract void onRun() throws java.lang.Exception;

    public abstract void onShowUsage(java.io.PrintStream printStream);

    public void run(java.lang.String[] strArr) {
        if (strArr.length < 1) {
            onShowUsage(java.lang.System.out);
            return;
        }
        this.mRawArgs = strArr;
        this.mArgs.init(null, null, null, null, strArr, 0);
        try {
            onRun();
        } catch (java.lang.IllegalArgumentException e) {
            onShowUsage(java.lang.System.err);
            java.lang.System.err.println();
            java.lang.System.err.println("Error: " + e.getMessage());
        } catch (java.lang.Exception e2) {
            e2.printStackTrace(java.lang.System.err);
            java.lang.System.exit(1);
        }
    }

    public void showUsage() {
        onShowUsage(java.lang.System.err);
    }

    public void showError(java.lang.String str) {
        onShowUsage(java.lang.System.err);
        java.lang.System.err.println();
        java.lang.System.err.println(str);
    }

    public java.lang.String nextOption() {
        return this.mArgs.getNextOption();
    }

    public java.lang.String nextArg() {
        return this.mArgs.getNextArg();
    }

    public java.lang.String peekNextArg() {
        return this.mArgs.peekNextArg();
    }

    public java.lang.String nextArgRequired() {
        return this.mArgs.getNextArgRequired();
    }

    public java.lang.String[] getRawArgs() {
        return this.mRawArgs;
    }
}
