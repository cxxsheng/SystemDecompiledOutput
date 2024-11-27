package com.android.server.permission.access;

/* compiled from: AccessPersistence.kt */
/* loaded from: classes2.dex */
public final class AccessPersistence {

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.access.AccessPersistence.Companion Companion = new com.android.server.permission.access.AccessPersistence.Companion(null);
    private static final java.lang.String LOG_TAG = com.android.server.permission.access.AccessPersistence.class.getSimpleName();

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.AccessPolicy policy;

    @com.android.internal.annotations.GuardedBy({"scheduleLock"})
    private com.android.server.permission.access.AccessPersistence.WriteHandler writeHandler;

    @org.jetbrains.annotations.NotNull
    private final java.lang.Object scheduleLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"scheduleLock"})
    @org.jetbrains.annotations.NotNull
    private final android.util.SparseLongArray pendingMutationTimesMillis = new android.util.SparseLongArray();

    @com.android.internal.annotations.GuardedBy({"scheduleLock"})
    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.immutable.MutableIntMap<com.android.server.permission.access.AccessState> pendingStates = new com.android.server.permission.access.immutable.MutableIntMap<>(null, 1, null);

    @org.jetbrains.annotations.NotNull
    private final java.lang.Object writeLock = new java.lang.Object();

    public AccessPersistence(@org.jetbrains.annotations.NotNull com.android.server.permission.access.AccessPolicy policy) {
        this.policy = policy;
    }

    public final void initialize() {
        this.writeHandler = new com.android.server.permission.access.AccessPersistence.WriteHandler(com.android.internal.os.BackgroundThread.getHandler().getLooper());
    }

    public final void read(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state) {
        readSystemState(state);
        com.android.server.permission.access.immutable.IntSet $this$forEachIndexed$iv = state.getExternalState().getUserIds();
        int size = $this$forEachIndexed$iv.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            int userId = $this$forEachIndexed$iv.elementAt(index$iv);
            readUserState(state, userId);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void readSystemState(com.android.server.permission.access.MutableAccessState state) {
        boolean fileExists;
        java.io.FileInputStream it$iv;
        java.lang.Throwable th;
        com.android.modules.utils.BinaryXmlPullParser $this$parseBinaryXml_u24lambda_u240$iv$iv;
        java.io.File $this$parse$iv = getSystemFile();
        try {
            android.util.AtomicFile $this$readWithReserveCopy$iv$iv = new android.util.AtomicFile($this$parse$iv);
            try {
                try {
                    try {
                        java.io.FileInputStream it$iv2 = $this$readWithReserveCopy$iv$iv.openRead();
                        try {
                            try {
                                com.android.modules.utils.BinaryXmlPullParser $this$parseBinaryXml_u24lambda_u240$iv$iv2 = new com.android.modules.utils.BinaryXmlPullParser();
                                $this$parseBinaryXml_u24lambda_u240$iv$iv2.setInput(it$iv2, (java.lang.String) null);
                                com.android.server.permission.access.AccessPolicy $this$readSystemState_u24lambda_u242_u24lambda_u241 = this.policy;
                                $this$readSystemState_u24lambda_u242_u24lambda_u241.parseSystemState($this$parseBinaryXml_u24lambda_u240$iv$iv2, state);
                                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                                com.android.server.permission.jarjar.kotlin.io.CloseableKt.closeFinally(it$iv2, null);
                            } catch (java.lang.Throwable th2) {
                                try {
                                    throw th2;
                                } catch (java.lang.Throwable th3) {
                                    com.android.server.permission.jarjar.kotlin.io.CloseableKt.closeFinally(it$iv2, th2);
                                    throw th3;
                                }
                            }
                        } catch (java.io.FileNotFoundException e$iv$iv) {
                            throw e$iv$iv;
                        }
                    } catch (java.io.FileNotFoundException e$iv$iv2) {
                        throw e$iv$iv2;
                    }
                } catch (java.lang.Exception e$iv$iv3) {
                    android.util.Slog.wtf("AccessPersistence", "Failed to read " + $this$readWithReserveCopy$iv$iv, e$iv$iv3);
                    java.io.File reserveFile$iv$iv = new java.io.File($this$readWithReserveCopy$iv$iv.getBaseFile().getParentFile(), $this$readWithReserveCopy$iv$iv.getBaseFile().getName() + ".reservecopy");
                    try {
                        it$iv = new android.util.AtomicFile(reserveFile$iv$iv).openRead();
                    } catch (java.lang.Exception e) {
                        e2$iv$iv = e;
                    }
                    try {
                        try {
                            $this$parseBinaryXml_u24lambda_u240$iv$iv = new com.android.modules.utils.BinaryXmlPullParser();
                        } catch (java.lang.Throwable th4) {
                            th = th4;
                        }
                        try {
                            $this$parseBinaryXml_u24lambda_u240$iv$iv.setInput(it$iv, (java.lang.String) null);
                            com.android.server.permission.access.AccessPolicy $this$readSystemState_u24lambda_u242_u24lambda_u2412 = this.policy;
                            $this$readSystemState_u24lambda_u242_u24lambda_u2412.parseSystemState($this$parseBinaryXml_u24lambda_u240$iv$iv, state);
                            com.android.server.permission.jarjar.kotlin.Unit unit2 = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                            com.android.server.permission.jarjar.kotlin.io.CloseableKt.closeFinally(it$iv, null);
                        } catch (java.lang.Throwable th5) {
                            th = th5;
                            try {
                                throw th;
                            } catch (java.lang.Throwable th6) {
                                com.android.server.permission.jarjar.kotlin.io.CloseableKt.closeFinally(it$iv, th);
                                throw th6;
                            }
                        }
                    } catch (java.lang.Exception e2) {
                        e2$iv$iv = e2;
                        android.util.Slog.e("AccessPersistence", "Failed to read " + reserveFile$iv$iv, e2$iv$iv);
                        throw e$iv$iv3;
                    }
                }
                fileExists = true;
            } catch (java.io.FileNotFoundException e3) {
                android.util.Slog.i(LOG_TAG, $this$parse$iv + " not found");
                fileExists = false;
                if (fileExists) {
                }
            } catch (java.lang.Exception e4) {
                e2$iv$iv = e4;
                throw new java.lang.IllegalStateException("Failed to read " + $this$parse$iv, e2$iv$iv);
            }
        } catch (java.io.FileNotFoundException e5) {
        } catch (java.lang.Exception e6) {
            e2$iv$iv = e6;
        }
        if (fileExists) {
            this.policy.migrateSystemState(state);
            write(state.getSystemState(), state, -1);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void readUserState(com.android.server.permission.access.MutableAccessState state, int userId) {
        boolean fileExists;
        android.util.AtomicFile $this$readWithReserveCopy$iv$iv;
        java.lang.Throwable th;
        java.io.File $this$parse$iv = getUserFile(userId);
        try {
            $this$readWithReserveCopy$iv$iv = new android.util.AtomicFile($this$parse$iv);
        } catch (java.io.FileNotFoundException e) {
            android.util.Slog.i(LOG_TAG, $this$parse$iv + " not found");
            fileExists = false;
            if (fileExists) {
            }
        } catch (java.lang.Exception e2) {
            e2$iv$iv = e2;
            throw new java.lang.IllegalStateException("Failed to read " + $this$parse$iv, e2$iv$iv);
        }
        try {
            try {
                try {
                    java.io.FileInputStream it$iv = $this$readWithReserveCopy$iv$iv.openRead();
                    try {
                        try {
                            com.android.modules.utils.BinaryXmlPullParser $this$parseBinaryXml_u24lambda_u240$iv$iv = new com.android.modules.utils.BinaryXmlPullParser();
                            $this$parseBinaryXml_u24lambda_u240$iv$iv.setInput(it$iv, (java.lang.String) null);
                            com.android.server.permission.access.AccessPolicy $this$readUserState_u24lambda_u244_u24lambda_u243 = this.policy;
                            $this$readUserState_u24lambda_u244_u24lambda_u243.parseUserState($this$parseBinaryXml_u24lambda_u240$iv$iv, state, userId);
                            com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                            com.android.server.permission.jarjar.kotlin.io.CloseableKt.closeFinally(it$iv, null);
                        } catch (java.lang.Throwable th2) {
                            try {
                                throw th2;
                            } catch (java.lang.Throwable th3) {
                                com.android.server.permission.jarjar.kotlin.io.CloseableKt.closeFinally(it$iv, th2);
                                throw th3;
                            }
                        }
                    } catch (java.io.FileNotFoundException e$iv$iv) {
                        throw e$iv$iv;
                    }
                } catch (java.io.FileNotFoundException e$iv$iv2) {
                    throw e$iv$iv2;
                }
            } catch (java.lang.Exception e$iv$iv3) {
                android.util.Slog.wtf("AccessPersistence", "Failed to read " + $this$readWithReserveCopy$iv$iv, e$iv$iv3);
                java.io.File reserveFile$iv$iv = new java.io.File($this$readWithReserveCopy$iv$iv.getBaseFile().getParentFile(), $this$readWithReserveCopy$iv$iv.getBaseFile().getName() + ".reservecopy");
                try {
                    java.io.FileInputStream it$iv2 = new android.util.AtomicFile(reserveFile$iv$iv).openRead();
                    try {
                        try {
                            com.android.modules.utils.BinaryXmlPullParser $this$parseBinaryXml_u24lambda_u240$iv$iv2 = new com.android.modules.utils.BinaryXmlPullParser();
                            try {
                                $this$parseBinaryXml_u24lambda_u240$iv$iv2.setInput(it$iv2, (java.lang.String) null);
                                com.android.server.permission.access.AccessPolicy $this$readUserState_u24lambda_u244_u24lambda_u2432 = this.policy;
                                $this$readUserState_u24lambda_u244_u24lambda_u2432.parseUserState($this$parseBinaryXml_u24lambda_u240$iv$iv2, state, userId);
                                com.android.server.permission.jarjar.kotlin.Unit unit2 = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                                com.android.server.permission.jarjar.kotlin.io.CloseableKt.closeFinally(it$iv2, null);
                            } catch (java.lang.Throwable th4) {
                                th = th4;
                                try {
                                    throw th;
                                } catch (java.lang.Throwable th5) {
                                    com.android.server.permission.jarjar.kotlin.io.CloseableKt.closeFinally(it$iv2, th);
                                    throw th5;
                                }
                            }
                        } catch (java.lang.Throwable th6) {
                            th = th6;
                        }
                    } catch (java.lang.Exception e3) {
                        e2$iv$iv = e3;
                        android.util.Slog.e("AccessPersistence", "Failed to read " + reserveFile$iv$iv, e2$iv$iv);
                        throw e$iv$iv3;
                    }
                } catch (java.lang.Exception e4) {
                    e2$iv$iv = e4;
                }
            }
            fileExists = true;
        } catch (java.io.FileNotFoundException e5) {
            android.util.Slog.i(LOG_TAG, $this$parse$iv + " not found");
            fileExists = false;
            if (fileExists) {
            }
        } catch (java.lang.Exception e6) {
            e2$iv$iv = e6;
            throw new java.lang.IllegalStateException("Failed to read " + $this$parse$iv, e2$iv$iv);
        }
        if (fileExists) {
            return;
        }
        this.policy.migrateUserState(state, userId);
        com.android.server.permission.access.UserState userState = state.getUserStates().get(userId);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(userState);
        write(userState, state, userId);
    }

    public final void write(@org.jetbrains.annotations.NotNull com.android.server.permission.access.AccessState state) {
        write(state.getSystemState(), state, -1);
        com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv = state.getUserStates();
        int size = $this$forEachIndexed$iv.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            int userId = $this$forEachIndexed$iv.keyAt(index$iv);
            com.android.server.permission.access.UserState userState = $this$forEachIndexed$iv.valueAt(index$iv);
            write(userState, state, userId);
        }
    }

    private final void write(com.android.server.permission.access.WritableState $this$write, com.android.server.permission.access.AccessState state, int userId) {
        long it$iv;
        long newDelayMillis;
        int writeMode = $this$write.getWriteMode();
        switch (writeMode) {
            case 0:
                return;
            case 1:
                synchronized (this.scheduleLock) {
                    try {
                        com.android.server.permission.access.AccessPersistence.WriteHandler writeHandler = this.writeHandler;
                        if (writeHandler == null) {
                            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("writeHandler");
                            writeHandler = null;
                        }
                        writeHandler.removeMessages(userId);
                        com.android.server.permission.access.immutable.IntMapExtensionsKt.set(this.pendingStates, userId, state);
                        long currentTimeMillis = android.os.SystemClock.uptimeMillis();
                        android.util.SparseLongArray $this$getOrPut$iv = this.pendingMutationTimesMillis;
                        int index$iv = $this$getOrPut$iv.indexOfKey(userId);
                        if (index$iv >= 0) {
                            it$iv = $this$getOrPut$iv.valueAt(index$iv);
                        } else {
                            $this$getOrPut$iv.put(userId, currentTimeMillis);
                            it$iv = currentTimeMillis;
                        }
                        long pendingMutationTimeMillis = it$iv;
                        long currentDelayMillis = currentTimeMillis - pendingMutationTimeMillis;
                        com.android.server.permission.access.AccessPersistence.WriteHandler writeHandler2 = this.writeHandler;
                        if (writeHandler2 == null) {
                            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("writeHandler");
                            writeHandler2 = null;
                        }
                        android.os.Message message = writeHandler2.obtainMessage(userId);
                        if (currentDelayMillis > 2000) {
                            message.sendToTarget();
                            com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                        } else {
                            newDelayMillis = com.android.server.permission.jarjar.kotlin.ranges.RangesKt___RangesKt.coerceAtMost(1000L, 2000 - currentDelayMillis);
                            com.android.server.permission.access.AccessPersistence.WriteHandler writeHandler3 = this.writeHandler;
                            if (writeHandler3 == null) {
                                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("writeHandler");
                                writeHandler3 = null;
                            }
                            java.lang.Boolean.valueOf(writeHandler3.sendMessageDelayed(message, newDelayMillis));
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                return;
            case 2:
                synchronized (this.scheduleLock) {
                    com.android.server.permission.access.immutable.IntMapExtensionsKt.set(this.pendingStates, userId, state);
                    com.android.server.permission.jarjar.kotlin.Unit unit2 = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                }
                writePendingState(userId);
                return;
            default:
                throw new java.lang.IllegalStateException(java.lang.Integer.valueOf(writeMode).toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v2, types: [T, java.lang.Object] */
    public final void writePendingState(int userId) {
        synchronized (this.writeLock) {
            try {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$ObjectRef state = new com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$ObjectRef();
                synchronized (this.scheduleLock) {
                    try {
                        android.util.SparseLongArray $this$minusAssign$iv = this.pendingMutationTimesMillis;
                        $this$minusAssign$iv.delete(userId);
                        state.element = this.pendingStates.remove(userId);
                        com.android.server.permission.access.AccessPersistence.WriteHandler writeHandler = this.writeHandler;
                        if (writeHandler == null) {
                            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("writeHandler");
                            writeHandler = null;
                        }
                        writeHandler.removeMessages(userId);
                        com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                    } finally {
                    }
                }
                if (state.element == 0) {
                    return;
                }
                if (userId == -1) {
                    writeSystemState((com.android.server.permission.access.AccessState) state.element);
                } else {
                    writeUserState((com.android.server.permission.access.AccessState) state.element, userId);
                }
                com.android.server.permission.jarjar.kotlin.Unit unit2 = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private final void writeSystemState(com.android.server.permission.access.AccessState state) {
        android.util.AtomicFile $this$writeWithReserveCopy$iv$iv;
        java.io.File reserveFile$iv$iv;
        java.io.FileOutputStream it$iv$iv$iv;
        com.android.modules.utils.BinaryXmlSerializer $this$serializeBinaryXml_u24lambda_u240$iv$iv;
        com.android.server.permission.access.AccessPolicy $this$writeSystemState_u24lambda_u2413_u24lambda_u2412;
        java.io.File $this$serialize$iv = getSystemFile();
        try {
            $this$writeWithReserveCopy$iv$iv = new android.util.AtomicFile($this$serialize$iv);
            reserveFile$iv$iv = new java.io.File($this$writeWithReserveCopy$iv$iv.getBaseFile().getParentFile(), $this$writeWithReserveCopy$iv$iv.getBaseFile().getName() + ".reservecopy");
            reserveFile$iv$iv.delete();
            it$iv$iv$iv = $this$writeWithReserveCopy$iv$iv.startWrite();
            try {
            } finally {
            }
        } catch (java.lang.Exception e) {
            e$iv = e;
        }
        try {
            try {
                $this$serializeBinaryXml_u24lambda_u240$iv$iv = new com.android.modules.utils.BinaryXmlSerializer();
                $this$serializeBinaryXml_u24lambda_u240$iv$iv.setOutput(it$iv$iv$iv, (java.lang.String) null);
                try {
                    $this$serializeBinaryXml_u24lambda_u240$iv$iv.startDocument((java.lang.String) null, true);
                    try {
                        $this$writeSystemState_u24lambda_u2413_u24lambda_u2412 = this.policy;
                    } catch (java.lang.Throwable th) {
                        t$iv$iv$iv = th;
                    }
                } catch (java.lang.Throwable th2) {
                    t$iv$iv$iv = th2;
                }
            } catch (java.lang.Throwable th3) {
                t$iv$iv$iv = th3;
            }
            try {
                $this$writeSystemState_u24lambda_u2413_u24lambda_u2412.serializeSystemState($this$serializeBinaryXml_u24lambda_u240$iv$iv, state);
                $this$serializeBinaryXml_u24lambda_u240$iv$iv.endDocument();
                $this$writeWithReserveCopy$iv$iv.finishWrite(it$iv$iv$iv);
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                com.android.server.permission.jarjar.kotlin.io.CloseableKt.closeFinally(it$iv$iv$iv, null);
                try {
                    java.io.FileInputStream inputStream$iv$iv = new java.io.FileInputStream($this$writeWithReserveCopy$iv$iv.getBaseFile());
                    try {
                        java.io.FileOutputStream outputStream$iv$iv = new java.io.FileOutputStream(reserveFile$iv$iv);
                        try {
                            android.os.FileUtils.copy(inputStream$iv$iv, outputStream$iv$iv);
                            outputStream$iv$iv.getFD().sync();
                            com.android.server.permission.jarjar.kotlin.Unit unit2 = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                            com.android.server.permission.jarjar.kotlin.io.CloseableKt.closeFinally(outputStream$iv$iv, null);
                            com.android.server.permission.jarjar.kotlin.Unit unit3 = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                            com.android.server.permission.jarjar.kotlin.io.CloseableKt.closeFinally(inputStream$iv$iv, null);
                        } finally {
                        }
                    } finally {
                    }
                } catch (java.lang.Exception e$iv$iv) {
                    android.util.Slog.e("AccessPersistence", "Failed to write " + reserveFile$iv$iv, e$iv$iv);
                }
            } catch (java.lang.Throwable th4) {
                t$iv$iv$iv = th4;
                $this$writeWithReserveCopy$iv$iv.failWrite(it$iv$iv$iv);
                throw t$iv$iv$iv;
            }
        } catch (java.lang.Exception e2) {
            e$iv = e2;
            android.util.Slog.e(LOG_TAG, "Failed to serialize " + $this$serialize$iv, e$iv);
        }
    }

    private final void writeUserState(com.android.server.permission.access.AccessState state, int userId) {
        android.util.AtomicFile $this$writeWithReserveCopy$iv$iv;
        java.io.File reserveFile$iv$iv;
        java.io.FileOutputStream it$iv$iv$iv;
        com.android.server.permission.access.AccessPolicy $this$writeUserState_u24lambda_u2415_u24lambda_u2414;
        java.io.File $this$serialize$iv = getUserFile(userId);
        try {
            $this$writeWithReserveCopy$iv$iv = new android.util.AtomicFile($this$serialize$iv);
            reserveFile$iv$iv = new java.io.File($this$writeWithReserveCopy$iv$iv.getBaseFile().getParentFile(), $this$writeWithReserveCopy$iv$iv.getBaseFile().getName() + ".reservecopy");
            reserveFile$iv$iv.delete();
            it$iv$iv$iv = $this$writeWithReserveCopy$iv$iv.startWrite();
            try {
            } finally {
            }
        } catch (java.lang.Exception e) {
            e$iv = e;
        }
        try {
            try {
                com.android.modules.utils.BinaryXmlSerializer $this$serializeBinaryXml_u24lambda_u240$iv$iv = new com.android.modules.utils.BinaryXmlSerializer();
                try {
                    $this$serializeBinaryXml_u24lambda_u240$iv$iv.setOutput(it$iv$iv$iv, (java.lang.String) null);
                    try {
                        $this$serializeBinaryXml_u24lambda_u240$iv$iv.startDocument((java.lang.String) null, true);
                        $this$writeUserState_u24lambda_u2415_u24lambda_u2414 = this.policy;
                    } catch (java.lang.Throwable th) {
                        t$iv$iv$iv = th;
                    }
                } catch (java.lang.Throwable th2) {
                    t$iv$iv$iv = th2;
                }
                try {
                    $this$writeUserState_u24lambda_u2415_u24lambda_u2414.serializeUserState($this$serializeBinaryXml_u24lambda_u240$iv$iv, state, userId);
                    $this$serializeBinaryXml_u24lambda_u240$iv$iv.endDocument();
                    $this$writeWithReserveCopy$iv$iv.finishWrite(it$iv$iv$iv);
                    com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                    com.android.server.permission.jarjar.kotlin.io.CloseableKt.closeFinally(it$iv$iv$iv, null);
                    try {
                        java.io.FileInputStream inputStream$iv$iv = new java.io.FileInputStream($this$writeWithReserveCopy$iv$iv.getBaseFile());
                        try {
                            java.io.FileOutputStream outputStream$iv$iv = new java.io.FileOutputStream(reserveFile$iv$iv);
                            try {
                                android.os.FileUtils.copy(inputStream$iv$iv, outputStream$iv$iv);
                                outputStream$iv$iv.getFD().sync();
                                com.android.server.permission.jarjar.kotlin.Unit unit2 = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                                com.android.server.permission.jarjar.kotlin.io.CloseableKt.closeFinally(outputStream$iv$iv, null);
                                com.android.server.permission.jarjar.kotlin.Unit unit3 = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                                com.android.server.permission.jarjar.kotlin.io.CloseableKt.closeFinally(inputStream$iv$iv, null);
                            } finally {
                            }
                        } finally {
                        }
                    } catch (java.lang.Exception e$iv$iv) {
                        android.util.Slog.e("AccessPersistence", "Failed to write " + reserveFile$iv$iv, e$iv$iv);
                    }
                } catch (java.lang.Throwable th3) {
                    t$iv$iv$iv = th3;
                    $this$writeWithReserveCopy$iv$iv.failWrite(it$iv$iv$iv);
                    throw t$iv$iv$iv;
                }
            } catch (java.lang.Throwable th4) {
                t$iv$iv$iv = th4;
            }
        } catch (java.lang.Exception e2) {
            e$iv = e2;
            android.util.Slog.e(LOG_TAG, "Failed to serialize " + $this$serialize$iv, e$iv);
        }
    }

    private final java.io.File getSystemFile() {
        return new java.io.File(com.android.server.permission.access.util.PermissionApex.INSTANCE.getSystemDataDirectory(), "access.abx");
    }

    private final java.io.File getUserFile(int userId) {
        return new java.io.File(com.android.server.permission.access.util.PermissionApex.INSTANCE.getUserDataDirectory(userId), "access.abx");
    }

    /* compiled from: AccessPersistence.kt */
    public static final class Companion {
        public /* synthetic */ Companion(com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: AccessPersistence.kt */
    private final class WriteHandler extends android.os.Handler {
        public WriteHandler(@org.jetbrains.annotations.NotNull android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(@org.jetbrains.annotations.NotNull android.os.Message message) {
            int userId = message.what;
            com.android.server.permission.access.AccessPersistence.this.writePendingState(userId);
        }
    }
}
