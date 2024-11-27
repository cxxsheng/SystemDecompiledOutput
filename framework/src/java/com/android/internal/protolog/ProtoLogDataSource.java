package com.android.internal.protolog;

/* loaded from: classes5.dex */
public class ProtoLogDataSource extends android.tracing.perfetto.DataSource<com.android.internal.protolog.ProtoLogDataSource.Instance, com.android.internal.protolog.ProtoLogDataSource.TlsState, com.android.internal.protolog.ProtoLogDataSource.IncrementalState> {
    private final java.lang.Runnable mOnFlush;
    private final java.lang.Runnable mOnStart;
    private final java.lang.Runnable mOnStop;

    public static class IncrementalState {
        public final java.util.Map<java.lang.String, java.lang.Integer> argumentInterningMap = new java.util.HashMap();
        public final java.util.Map<java.lang.String, java.lang.Integer> stacktraceInterningMap = new java.util.HashMap();
        public boolean clearReported = false;
    }

    public ProtoLogDataSource(java.lang.Runnable runnable, java.lang.Runnable runnable2, java.lang.Runnable runnable3) {
        super("android.protolog");
        this.mOnStart = runnable;
        this.mOnFlush = runnable2;
        this.mOnStop = runnable3;
    }

    @Override // android.tracing.perfetto.DataSource
    public com.android.internal.protolog.ProtoLogDataSource.Instance createInstance(android.util.proto.ProtoInputStream protoInputStream, int i) {
        com.android.internal.protolog.ProtoLogDataSource.ProtoLogConfig protoLogConfig;
        com.android.internal.protolog.ProtoLogDataSource.ProtoLogConfig protoLogConfig2 = null;
        while (protoInputStream.nextField() != -1) {
            try {
                try {
                    if (protoInputStream.getFieldNumber() == 126) {
                        if (protoLogConfig2 != null) {
                            throw new java.lang.RuntimeException("ProtoLog config already set in loop");
                        }
                        protoLogConfig2 = readProtoLogConfig(protoInputStream);
                    }
                } catch (android.util.proto.WireTypeMismatchException e) {
                    throw new java.lang.RuntimeException("Failed to parse ProtoLog DataSource config", e);
                }
            } catch (java.io.IOException e2) {
                throw new java.lang.RuntimeException("Failed to read ProtoLog DataSource config", e2);
            }
        }
        if (protoLogConfig2 != null) {
            protoLogConfig = protoLogConfig2;
        } else {
            protoLogConfig = com.android.internal.protolog.ProtoLogDataSource.ProtoLogConfig.DEFAULT;
        }
        return new com.android.internal.protolog.ProtoLogDataSource.Instance(this, i, protoLogConfig, this.mOnStart, this.mOnFlush, this.mOnStop);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.tracing.perfetto.DataSource
    public com.android.internal.protolog.ProtoLogDataSource.TlsState createTlsState(android.tracing.perfetto.CreateTlsStateArgs<com.android.internal.protolog.ProtoLogDataSource.Instance> createTlsStateArgs) {
        com.android.internal.protolog.ProtoLogDataSource.Instance dataSourceInstanceLocked = createTlsStateArgs.getDataSourceInstanceLocked();
        try {
            if (dataSourceInstanceLocked == null) {
                com.android.internal.protolog.ProtoLogDataSource.TlsState tlsState = new com.android.internal.protolog.ProtoLogDataSource.TlsState(com.android.internal.protolog.ProtoLogDataSource.ProtoLogConfig.DEFAULT);
                if (dataSourceInstanceLocked != null) {
                    dataSourceInstanceLocked.close();
                }
                return tlsState;
            }
            com.android.internal.protolog.ProtoLogDataSource.TlsState tlsState2 = new com.android.internal.protolog.ProtoLogDataSource.TlsState(dataSourceInstanceLocked.mConfig);
            if (dataSourceInstanceLocked != null) {
                dataSourceInstanceLocked.close();
            }
            return tlsState2;
        } catch (java.lang.Throwable th) {
            if (dataSourceInstanceLocked != null) {
                try {
                    dataSourceInstanceLocked.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.tracing.perfetto.DataSource
    public com.android.internal.protolog.ProtoLogDataSource.IncrementalState createIncrementalState(android.tracing.perfetto.CreateIncrementalStateArgs<com.android.internal.protolog.ProtoLogDataSource.Instance> createIncrementalStateArgs) {
        return new com.android.internal.protolog.ProtoLogDataSource.IncrementalState();
    }

    public static class TlsState {
        private final com.android.internal.protolog.ProtoLogDataSource.ProtoLogConfig mConfig;

        private TlsState(com.android.internal.protolog.ProtoLogDataSource.ProtoLogConfig protoLogConfig) {
            this.mConfig = protoLogConfig;
        }

        public com.android.internal.protolog.common.LogLevel getLogFromLevel(java.lang.String str) {
            return getConfigFor(str).logFrom;
        }

        public boolean getShouldCollectStacktrace(java.lang.String str) {
            return getConfigFor(str).collectStackTrace;
        }

        private com.android.internal.protolog.ProtoLogDataSource.GroupConfig getConfigFor(java.lang.String str) {
            return this.mConfig.getConfigFor(str);
        }
    }

    private static class ProtoLogConfig {
        private static final com.android.internal.protolog.ProtoLogDataSource.ProtoLogConfig DEFAULT = new com.android.internal.protolog.ProtoLogDataSource.ProtoLogConfig(com.android.internal.protolog.common.LogLevel.WTF, new java.util.HashMap());
        private final com.android.internal.protolog.common.LogLevel mDefaultLogFromLevel;
        private final java.util.Map<java.lang.String, com.android.internal.protolog.ProtoLogDataSource.GroupConfig> mGroupConfigs;

        private ProtoLogConfig(com.android.internal.protolog.common.LogLevel logLevel, java.util.Map<java.lang.String, com.android.internal.protolog.ProtoLogDataSource.GroupConfig> map) {
            this.mDefaultLogFromLevel = logLevel;
            this.mGroupConfigs = map;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.internal.protolog.ProtoLogDataSource.GroupConfig getConfigFor(java.lang.String str) {
            return this.mGroupConfigs.getOrDefault(str, getDefaultGroupConfig());
        }

        private com.android.internal.protolog.ProtoLogDataSource.GroupConfig getDefaultGroupConfig() {
            return new com.android.internal.protolog.ProtoLogDataSource.GroupConfig(this.mDefaultLogFromLevel, false);
        }
    }

    public static class GroupConfig {
        public final boolean collectStackTrace;
        public final com.android.internal.protolog.common.LogLevel logFrom;

        public GroupConfig(com.android.internal.protolog.common.LogLevel logLevel, boolean z) {
            this.logFrom = logLevel;
            this.collectStackTrace = z;
        }
    }

    private com.android.internal.protolog.ProtoLogDataSource.ProtoLogConfig readProtoLogConfig(android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
        long j;
        long start = protoInputStream.start(1146756268158L);
        com.android.internal.protolog.common.LogLevel logLevel = com.android.internal.protolog.common.LogLevel.WTF;
        java.util.HashMap hashMap = new java.util.HashMap();
        while (true) {
            java.lang.String str = null;
            byte b = 0;
            if (protoInputStream.nextField() != -1) {
                if (protoInputStream.getFieldNumber() == 2) {
                    switch (protoInputStream.readInt(1159641169922L)) {
                        case 0:
                            break;
                        case 1:
                            logLevel = com.android.internal.protolog.common.LogLevel.DEBUG;
                            break;
                        default:
                            throw new java.lang.RuntimeException("Unhandled ProtoLog tracing mode type");
                    }
                }
                int i = 1;
                if (protoInputStream.getFieldNumber() == 1) {
                    long start2 = protoInputStream.start(2246267895809L);
                    boolean z = false;
                    com.android.internal.protolog.common.LogLevel logLevel2 = logLevel;
                    while (protoInputStream.nextField() != -1) {
                        if (protoInputStream.getFieldNumber() != i) {
                            j = start2;
                        } else {
                            j = start2;
                            str = protoInputStream.readString(1138166333441L);
                        }
                        if (protoInputStream.getFieldNumber() == 2) {
                            switch (protoInputStream.readInt(1159641169922L)) {
                                case 1:
                                    logLevel2 = com.android.internal.protolog.common.LogLevel.DEBUG;
                                    break;
                                case 2:
                                    logLevel2 = com.android.internal.protolog.common.LogLevel.VERBOSE;
                                    break;
                                case 3:
                                    logLevel2 = com.android.internal.protolog.common.LogLevel.INFO;
                                    break;
                                case 4:
                                    logLevel2 = com.android.internal.protolog.common.LogLevel.WARN;
                                    break;
                                case 5:
                                    logLevel2 = com.android.internal.protolog.common.LogLevel.ERROR;
                                    break;
                                case 6:
                                    logLevel2 = com.android.internal.protolog.common.LogLevel.WTF;
                                    break;
                                default:
                                    throw new java.lang.RuntimeException("Unhandled log level");
                            }
                        }
                        if (protoInputStream.getFieldNumber() == 3) {
                            z = protoInputStream.readBoolean(1133871366147L);
                            start2 = j;
                            i = 1;
                        } else {
                            start2 = j;
                            i = 1;
                        }
                    }
                    long j2 = start2;
                    if (str == null) {
                        throw new java.lang.RuntimeException("Failed to decode proto config. Got a group override without a group tag.");
                    }
                    hashMap.put(str, new com.android.internal.protolog.ProtoLogDataSource.GroupConfig(logLevel2, z));
                    protoInputStream.end(j2);
                }
            } else {
                protoInputStream.end(start);
                return new com.android.internal.protolog.ProtoLogDataSource.ProtoLogConfig(logLevel, hashMap);
            }
        }
    }

    public static class Instance extends android.tracing.perfetto.DataSourceInstance {
        private final com.android.internal.protolog.ProtoLogDataSource.ProtoLogConfig mConfig;
        private final java.lang.Runnable mOnFlush;
        private final java.lang.Runnable mOnStart;
        private final java.lang.Runnable mOnStop;

        public Instance(android.tracing.perfetto.DataSource<com.android.internal.protolog.ProtoLogDataSource.Instance, com.android.internal.protolog.ProtoLogDataSource.TlsState, com.android.internal.protolog.ProtoLogDataSource.IncrementalState> dataSource, int i, com.android.internal.protolog.ProtoLogDataSource.ProtoLogConfig protoLogConfig, java.lang.Runnable runnable, java.lang.Runnable runnable2, java.lang.Runnable runnable3) {
            super(dataSource, i);
            this.mOnStart = runnable;
            this.mOnFlush = runnable2;
            this.mOnStop = runnable3;
            this.mConfig = protoLogConfig;
        }

        @Override // android.tracing.perfetto.DataSourceInstance
        public void onStart(android.tracing.perfetto.StartCallbackArguments startCallbackArguments) {
            this.mOnStart.run();
        }

        @Override // android.tracing.perfetto.DataSourceInstance
        public void onFlush(android.tracing.perfetto.FlushCallbackArguments flushCallbackArguments) {
            this.mOnFlush.run();
        }

        @Override // android.tracing.perfetto.DataSourceInstance
        public void onStop(android.tracing.perfetto.StopCallbackArguments stopCallbackArguments) {
            this.mOnStop.run();
        }
    }
}
