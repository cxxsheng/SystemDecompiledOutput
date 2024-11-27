package com.android.server.pm;

/* loaded from: classes2.dex */
class ParallelPackageParser {
    private static final int MAX_THREADS = 4;
    private static final int QUEUE_CAPACITY = 30;
    private final java.util.concurrent.ExecutorService mExecutorService;
    private volatile java.lang.String mInterruptedInThread;
    private final com.android.internal.pm.parsing.PackageParser2 mPackageParser;
    private final java.util.concurrent.BlockingQueue<com.android.server.pm.ParallelPackageParser.ParseResult> mQueue = new java.util.concurrent.ArrayBlockingQueue(30);

    static java.util.concurrent.ExecutorService makeExecutorService() {
        return com.android.internal.util.ConcurrentUtils.newFixedThreadPool(4, "package-parsing-thread", -2);
    }

    ParallelPackageParser(com.android.internal.pm.parsing.PackageParser2 packageParser2, java.util.concurrent.ExecutorService executorService) {
        this.mPackageParser = packageParser2;
        this.mExecutorService = executorService;
    }

    static class ParseResult {
        com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage;
        java.io.File scanFile;
        java.lang.Throwable throwable;

        ParseResult() {
        }

        public java.lang.String toString() {
            return "ParseResult{parsedPackage=" + this.parsedPackage + ", scanFile=" + this.scanFile + ", throwable=" + this.throwable + '}';
        }
    }

    public com.android.server.pm.ParallelPackageParser.ParseResult take() {
        try {
            if (this.mInterruptedInThread != null) {
                throw new java.lang.InterruptedException("Interrupted in " + this.mInterruptedInThread);
            }
            return this.mQueue.take();
        } catch (java.lang.InterruptedException e) {
            java.lang.Thread.currentThread().interrupt();
            throw new java.lang.IllegalStateException(e);
        }
    }

    public void submit(final java.io.File file, final int i) {
        this.mExecutorService.submit(new java.lang.Runnable() { // from class: com.android.server.pm.ParallelPackageParser$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.ParallelPackageParser.this.lambda$submit$0(file, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$submit$0(java.io.File file, int i) {
        com.android.server.pm.ParallelPackageParser.ParseResult parseResult = new com.android.server.pm.ParallelPackageParser.ParseResult();
        android.os.Trace.traceBegin(262144L, "parallel parsePackage [" + file + "]");
        try {
            parseResult.scanFile = file;
            parseResult.parsedPackage = parsePackage(file, i);
        } finally {
            try {
                this.mQueue.put(parseResult);
            } finally {
            }
        }
        try {
            this.mQueue.put(parseResult);
        } catch (java.lang.InterruptedException e) {
            java.lang.Thread.currentThread().interrupt();
            this.mInterruptedInThread = java.lang.Thread.currentThread().getName();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.internal.pm.parsing.pkg.ParsedPackage parsePackage(java.io.File file, int i) throws com.android.server.pm.PackageManagerException {
        try {
            return this.mPackageParser.parsePackage(file, i, true);
        } catch (com.android.internal.pm.parsing.PackageParserException e) {
            throw new com.android.server.pm.PackageManagerException(e.error, e.getMessage(), (java.lang.Throwable) e);
        }
    }
}
