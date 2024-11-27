package com.android.internal.os;

/* loaded from: classes4.dex */
class AppZygoteInit {
    public static final java.lang.String TAG = "AppZygoteInit";
    private static com.android.internal.os.ZygoteServer sServer;

    AppZygoteInit() {
    }

    private static class AppZygoteServer extends com.android.internal.os.ZygoteServer {
        private AppZygoteServer() {
        }

        @Override // com.android.internal.os.ZygoteServer
        protected com.android.internal.os.ZygoteConnection createNewConnection(android.net.LocalSocket localSocket, java.lang.String str) throws java.io.IOException {
            return new com.android.internal.os.AppZygoteInit.AppZygoteConnection(localSocket, str);
        }
    }

    private static class AppZygoteConnection extends com.android.internal.os.ZygoteConnection {
        AppZygoteConnection(android.net.LocalSocket localSocket, java.lang.String str) throws java.io.IOException {
            super(localSocket, str);
        }

        @Override // com.android.internal.os.ZygoteConnection
        protected void preload() {
        }

        @Override // com.android.internal.os.ZygoteConnection
        protected boolean isPreloadComplete() {
            return true;
        }

        @Override // com.android.internal.os.ZygoteConnection
        protected boolean canPreloadApp() {
            return true;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r11v6, types: [java.io.DataOutputStream] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x008a -> B:4:0x00a8). Please report as a decompilation issue!!! */
        @Override // com.android.internal.os.ZygoteConnection
        protected void handlePreloadApp(android.content.pm.ApplicationInfo applicationInfo) {
            android.util.Log.i(com.android.internal.os.AppZygoteInit.TAG, "Beginning application preload for " + applicationInfo.packageName);
            java.lang.ClassLoader classLoader = new android.app.LoadedApk(null, applicationInfo, null, null, false, true, false).getClassLoader();
            com.android.internal.os.Zygote.allowAppFilesAcrossFork(applicationInfo);
            int i = 1;
            if (applicationInfo.zygotePreloadName != null) {
                try {
                    android.content.ComponentName createRelative = android.content.ComponentName.createRelative(applicationInfo.packageName, applicationInfo.zygotePreloadName);
                    java.lang.Class<?> cls = java.lang.Class.forName(createRelative.getClassName(), true, classLoader);
                    if (!android.app.ZygotePreload.class.isAssignableFrom(cls)) {
                        android.util.Log.e(com.android.internal.os.AppZygoteInit.TAG, createRelative.getClassName() + " does not implement " + android.app.ZygotePreload.class.getName());
                    } else {
                        android.app.ZygotePreload zygotePreload = (android.app.ZygotePreload) cls.getConstructor(new java.lang.Class[0]).newInstance(new java.lang.Object[0]);
                        com.android.internal.os.Zygote.markOpenedFilesBeforePreload();
                        zygotePreload.doPreload(applicationInfo);
                        com.android.internal.os.Zygote.allowFilesOpenedByPreload();
                    }
                } catch (java.lang.ReflectiveOperationException e) {
                    android.util.Log.e(com.android.internal.os.AppZygoteInit.TAG, "AppZygote application preload failed for " + applicationInfo.zygotePreloadName, e);
                }
            } else {
                android.util.Log.i(com.android.internal.os.AppZygoteInit.TAG, "No zygotePreloadName attribute specified.");
            }
            try {
                applicationInfo = getSocketOutputStream();
                if (classLoader == null) {
                    i = 0;
                }
                applicationInfo.writeInt(i);
                android.util.Log.i(com.android.internal.os.AppZygoteInit.TAG, "Application preload done");
            } catch (java.io.IOException e2) {
                throw new java.lang.IllegalStateException("Error writing to command socket", e2);
            }
        }
    }

    public static void main(java.lang.String[] strArr) {
        com.android.internal.os.ChildZygoteInit.runZygoteServer(new com.android.internal.os.AppZygoteInit.AppZygoteServer(), strArr);
    }
}
