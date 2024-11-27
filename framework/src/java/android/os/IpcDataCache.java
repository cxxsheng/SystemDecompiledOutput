package android.os;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public class IpcDataCache<Query, Result> extends android.app.PropertyInvalidatedCache<Query, Result> {

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final java.lang.String MODULE_BLUETOOTH = "bluetooth";
    public static final java.lang.String MODULE_SYSTEM = "system_server";
    public static final java.lang.String MODULE_TEST = "test";

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface IpcDataCacheModule {
    }

    public interface RemoteCall<Query, Result> {
        Result apply(Query query) throws android.os.RemoteException;
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static abstract class QueryHandler<Q, R> extends android.app.PropertyInvalidatedCache.QueryHandler<Q, R> {
        @Override // android.app.PropertyInvalidatedCache.QueryHandler
        public abstract R apply(Q q);

        @Override // android.app.PropertyInvalidatedCache.QueryHandler
        public boolean shouldBypassCache(Q q) {
            return false;
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public IpcDataCache(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.IpcDataCache.QueryHandler<Query, Result> queryHandler) {
        super(i, str, str2, str3, queryHandler);
    }

    @Override // android.app.PropertyInvalidatedCache
    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void disableForCurrentProcess() {
        super.disableForCurrentProcess();
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static void disableForCurrentProcess(java.lang.String str) {
        android.app.PropertyInvalidatedCache.disableForCurrentProcess(str);
    }

    @Override // android.app.PropertyInvalidatedCache
    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public Result query(Query query) {
        return (Result) super.query(query);
    }

    @Override // android.app.PropertyInvalidatedCache
    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void invalidateCache() {
        super.invalidateCache();
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static void invalidateCache(java.lang.String str, java.lang.String str2) {
        android.app.PropertyInvalidatedCache.invalidateCache(str, str2);
    }

    public static class Config {
        private final java.lang.String mApi;
        private android.util.ArraySet<java.lang.String> mChildren;
        private boolean mDisabled;
        private final int mMaxEntries;
        private final java.lang.String mModule;
        private final java.lang.String mName;

        public Config(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) {
            this.mDisabled = false;
            this.mMaxEntries = i;
            this.mModule = str;
            this.mApi = str2;
            this.mName = str3;
        }

        public Config(int i, java.lang.String str, java.lang.String str2) {
            this(i, str, str2, str2);
        }

        public Config(android.os.IpcDataCache.Config config, java.lang.String str, java.lang.String str2) {
            this(config.maxEntries(), config.module(), str, str2);
        }

        public Config(android.os.IpcDataCache.Config config, java.lang.String str) {
            this(config.maxEntries(), config.module(), str, str);
        }

        public android.os.IpcDataCache.Config child(java.lang.String str) {
            android.os.IpcDataCache.Config config = new android.os.IpcDataCache.Config(this, api(), str);
            registerChild(str);
            return config;
        }

        public final int maxEntries() {
            return this.mMaxEntries;
        }

        public final java.lang.String module() {
            return this.mModule;
        }

        public final java.lang.String api() {
            return this.mApi;
        }

        public final java.lang.String name() {
            return this.mName;
        }

        private final void registerChild(java.lang.String str) {
            synchronized (this) {
                if (this.mChildren == null) {
                    this.mChildren = new android.util.ArraySet<>();
                }
                this.mChildren.add(str);
                if (this.mDisabled) {
                    android.os.IpcDataCache.disableForCurrentProcess(str);
                }
            }
        }

        public void invalidateCache() {
            android.os.IpcDataCache.invalidateCache(this.mModule, this.mApi);
        }

        public void disableForCurrentProcess() {
            android.os.IpcDataCache.disableForCurrentProcess(this.mName);
        }

        public void disableAllForCurrentProcess() {
            synchronized (this) {
                this.mDisabled = true;
                disableForCurrentProcess();
                if (this.mChildren != null) {
                    java.util.Iterator<java.lang.String> it = this.mChildren.iterator();
                    while (it.hasNext()) {
                        android.os.IpcDataCache.disableForCurrentProcess(it.next());
                    }
                }
            }
        }
    }

    public IpcDataCache(android.os.IpcDataCache.Config config, android.os.IpcDataCache.QueryHandler<Query, Result> queryHandler) {
        super(config.maxEntries(), config.module(), config.api(), config.name(), queryHandler);
    }

    private static class SystemServerCallHandler<Query, Result> extends android.os.IpcDataCache.QueryHandler<Query, Result> {
        private final android.os.IpcDataCache.RemoteCall<Query, Result> mHandler;

        public SystemServerCallHandler(android.os.IpcDataCache.RemoteCall remoteCall) {
            this.mHandler = remoteCall;
        }

        @Override // android.os.IpcDataCache.QueryHandler, android.app.PropertyInvalidatedCache.QueryHandler
        public Result apply(Query query) {
            try {
                return this.mHandler.apply(query);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public IpcDataCache(android.os.IpcDataCache.Config config, android.os.IpcDataCache.RemoteCall<Query, Result> remoteCall) {
        this(config, new android.os.IpcDataCache.SystemServerCallHandler(remoteCall));
    }
}
