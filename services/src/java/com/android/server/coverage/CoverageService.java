package com.android.server.coverage;

/* loaded from: classes.dex */
public class CoverageService extends android.os.Binder {
    public static final java.lang.String COVERAGE_SERVICE = "coverage";
    public static final boolean ENABLED;

    static {
        boolean z;
        try {
            java.lang.Class.forName("org.jacoco.agent.rt.RT");
            z = true;
        } catch (java.lang.ClassNotFoundException e) {
            z = false;
        }
        ENABLED = z;
    }

    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        new com.android.server.coverage.CoverageService.CoverageCommand().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    private static class CoverageCommand extends android.os.ShellCommand {
        private CoverageCommand() {
        }

        public int onCommand(java.lang.String str) {
            if ("dump".equals(str)) {
                return onDump();
            }
            if ("reset".equals(str)) {
                return onReset();
            }
            return handleDefaultCommands(str);
        }

        public void onHelp() {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("Coverage commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Print this help text.");
            outPrintWriter.println("  dump [FILE]");
            outPrintWriter.println("    Dump code coverage to FILE.");
            outPrintWriter.println("  reset");
            outPrintWriter.println("    Reset coverage information.");
        }

        private int onDump() {
            java.lang.String nextArg = getNextArg();
            if (nextArg == null) {
                nextArg = "/data/local/tmp/coverage.ec";
            } else {
                java.io.File file = new java.io.File(nextArg);
                if (file.isDirectory()) {
                    nextArg = new java.io.File(file, "coverage.ec").getAbsolutePath();
                }
            }
            android.os.ParcelFileDescriptor openFileForSystem = openFileForSystem(nextArg, "w");
            if (openFileForSystem == null) {
                return -1;
            }
            try {
                java.io.BufferedOutputStream bufferedOutputStream = new java.io.BufferedOutputStream(new android.os.ParcelFileDescriptor.AutoCloseOutputStream(openFileForSystem));
                try {
                    bufferedOutputStream.write(org.jacoco.agent.rt.RT.getAgent().getExecutionData(false));
                    bufferedOutputStream.flush();
                    getOutPrintWriter().println(java.lang.String.format("Dumped coverage data to %s", nextArg));
                    bufferedOutputStream.close();
                    return 0;
                } finally {
                }
            } catch (java.io.IOException e) {
                getErrPrintWriter().println("Failed to dump coverage data: " + e.getMessage());
                return -1;
            }
        }

        private int onReset() {
            org.jacoco.agent.rt.RT.getAgent().reset();
            getOutPrintWriter().println("Reset coverage data");
            return 0;
        }
    }
}
