package com.android.internal.os;

/* loaded from: classes4.dex */
public class BinderfsStatsReader {
    private final java.lang.String mPath;

    public BinderfsStatsReader() {
        this.mPath = "/dev/binderfs/binder_logs/stats";
    }

    public BinderfsStatsReader(java.lang.String str) {
        this.mPath = str;
    }

    public void handleFreeAsyncSpace(java.util.function.Predicate<java.lang.Integer> predicate, java.util.function.BiConsumer<java.lang.Integer, java.lang.Integer> biConsumer, java.util.function.Consumer<java.lang.Exception> consumer) {
        try {
            com.android.internal.util.ProcFileReader procFileReader = new com.android.internal.util.ProcFileReader(new java.io.FileInputStream(this.mPath));
            while (procFileReader.hasMoreData()) {
                try {
                    if (!procFileReader.nextString().equals("proc")) {
                        procFileReader.finishLine();
                    } else {
                        int nextInt = procFileReader.nextInt();
                        procFileReader.finishLine();
                        if (predicate.test(java.lang.Integer.valueOf(nextInt))) {
                            procFileReader.finishLine();
                            procFileReader.finishLine();
                            procFileReader.finishLine();
                            procFileReader.finishLine();
                            if (!procFileReader.nextString().equals("free")) {
                                procFileReader.finishLine();
                            } else if (!procFileReader.nextString().equals("async")) {
                                procFileReader.finishLine();
                            } else if (!procFileReader.nextString().equals(android.inputmethodservice.navigationbar.NavigationBarInflaterView.NAVSPACE)) {
                                procFileReader.finishLine();
                            } else {
                                int nextInt2 = procFileReader.nextInt();
                                procFileReader.finishLine();
                                biConsumer.accept(java.lang.Integer.valueOf(nextInt), java.lang.Integer.valueOf(nextInt2));
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    try {
                        procFileReader.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
            procFileReader.close();
        } catch (java.io.IOException | java.lang.NumberFormatException e) {
            consumer.accept(e);
        }
    }
}
