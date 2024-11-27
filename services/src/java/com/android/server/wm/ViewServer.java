package com.android.server.wm;

/* loaded from: classes3.dex */
class ViewServer implements java.lang.Runnable {
    private static final java.lang.String COMMAND_PROTOCOL_VERSION = "PROTOCOL";
    private static final java.lang.String COMMAND_SERVER_VERSION = "SERVER";
    private static final java.lang.String COMMAND_WINDOW_MANAGER_AUTOLIST = "AUTOLIST";
    private static final java.lang.String COMMAND_WINDOW_MANAGER_GET_FOCUS = "GET_FOCUS";
    private static final java.lang.String COMMAND_WINDOW_MANAGER_LIST = "LIST";
    private static final java.lang.String LOG_TAG = "WindowManager";
    private static final java.lang.String VALUE_PROTOCOL_VERSION = "4";
    private static final java.lang.String VALUE_SERVER_VERSION = "4";
    public static final int VIEW_SERVER_DEFAULT_PORT = 4939;
    private static final int VIEW_SERVER_MAX_CONNECTIONS = 10;
    private final int mPort;
    private java.net.ServerSocket mServer;
    private java.lang.Thread mThread;
    private java.util.concurrent.ExecutorService mThreadPool;
    private final com.android.server.wm.WindowManagerService mWindowManager;

    ViewServer(com.android.server.wm.WindowManagerService windowManagerService, int i) {
        this.mWindowManager = windowManagerService;
        this.mPort = i;
    }

    boolean start() throws java.io.IOException {
        if (this.mThread != null) {
            return false;
        }
        this.mServer = new java.net.ServerSocket(this.mPort, 10, java.net.InetAddress.getLocalHost());
        this.mThread = new java.lang.Thread(this, "Remote View Server [port=" + this.mPort + "]");
        this.mThreadPool = java.util.concurrent.Executors.newFixedThreadPool(10);
        this.mThread.start();
        return true;
    }

    boolean stop() {
        if (this.mThread != null) {
            this.mThread.interrupt();
            if (this.mThreadPool != null) {
                try {
                    this.mThreadPool.shutdownNow();
                } catch (java.lang.SecurityException e) {
                    android.util.Slog.w(LOG_TAG, "Could not stop all view server threads");
                }
            }
            this.mThreadPool = null;
            this.mThread = null;
            try {
                this.mServer.close();
                this.mServer = null;
                return true;
            } catch (java.io.IOException e2) {
                android.util.Slog.w(LOG_TAG, "Could not close the view server");
                return false;
            }
        }
        return false;
    }

    boolean isRunning() {
        return this.mThread != null && this.mThread.isAlive();
    }

    @Override // java.lang.Runnable
    public void run() {
        while (java.lang.Thread.currentThread() == this.mThread) {
            try {
                java.net.Socket accept = this.mServer.accept();
                if (this.mThreadPool != null) {
                    this.mThreadPool.submit(new com.android.server.wm.ViewServer.ViewServerWorker(accept));
                } else {
                    try {
                        accept.close();
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (java.lang.Exception e2) {
                android.util.Slog.w(LOG_TAG, "Connection error: ", e2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean writeValue(java.net.Socket socket, java.lang.String str) {
        java.io.BufferedWriter bufferedWriter;
        boolean z = false;
        java.io.BufferedWriter bufferedWriter2 = null;
        try {
            try {
                bufferedWriter = new java.io.BufferedWriter(new java.io.OutputStreamWriter(socket.getOutputStream()), 8192);
            } catch (java.lang.Exception e) {
            } catch (java.lang.Throwable th) {
                th = th;
            }
            try {
                bufferedWriter.write(str);
                bufferedWriter.write("\n");
                bufferedWriter.flush();
                bufferedWriter.close();
                z = true;
            } catch (java.lang.Exception e2) {
                bufferedWriter2 = bufferedWriter;
                if (bufferedWriter2 != null) {
                    bufferedWriter2.close();
                }
                return z;
            } catch (java.lang.Throwable th2) {
                th = th2;
                bufferedWriter2 = bufferedWriter;
                if (bufferedWriter2 != null) {
                    try {
                        bufferedWriter2.close();
                    } catch (java.io.IOException e3) {
                    }
                }
                throw th;
            }
        } catch (java.io.IOException e4) {
        }
        return z;
    }

    class ViewServerWorker implements java.lang.Runnable, com.android.server.wm.WindowManagerService.WindowChangeListener {
        private java.net.Socket mClient;
        private boolean mNeedWindowListUpdate = false;
        private boolean mNeedFocusedWindowUpdate = false;

        public ViewServerWorker(java.net.Socket socket) {
            this.mClient = socket;
        }

        /* JADX WARN: Removed duplicated region for block: B:61:0x00f9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:68:? A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:69:0x00ed A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            java.io.BufferedReader bufferedReader;
            java.io.IOException e;
            java.lang.String substring;
            java.io.BufferedReader bufferedReader2 = null;
            try {
                try {
                    bufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(this.mClient.getInputStream()), 1024);
                } catch (java.io.IOException e2) {
                    bufferedReader = null;
                    e = e2;
                } catch (java.lang.Throwable th) {
                    th = th;
                    if (bufferedReader2 != null) {
                    }
                    if (this.mClient != null) {
                    }
                }
                try {
                    try {
                        java.lang.String readLine = bufferedReader.readLine();
                        int indexOf = readLine.indexOf(32);
                        if (indexOf == -1) {
                            substring = "";
                        } else {
                            java.lang.String substring2 = readLine.substring(0, indexOf);
                            substring = readLine.substring(indexOf + 1);
                            readLine = substring2;
                        }
                        if (!(com.android.server.wm.ViewServer.COMMAND_PROTOCOL_VERSION.equalsIgnoreCase(readLine) ? com.android.server.wm.ViewServer.writeValue(this.mClient, "4") : com.android.server.wm.ViewServer.COMMAND_SERVER_VERSION.equalsIgnoreCase(readLine) ? com.android.server.wm.ViewServer.writeValue(this.mClient, "4") : com.android.server.wm.ViewServer.COMMAND_WINDOW_MANAGER_LIST.equalsIgnoreCase(readLine) ? com.android.server.wm.ViewServer.this.mWindowManager.viewServerListWindows(this.mClient) : com.android.server.wm.ViewServer.COMMAND_WINDOW_MANAGER_GET_FOCUS.equalsIgnoreCase(readLine) ? com.android.server.wm.ViewServer.this.mWindowManager.viewServerGetFocusedWindow(this.mClient) : com.android.server.wm.ViewServer.COMMAND_WINDOW_MANAGER_AUTOLIST.equalsIgnoreCase(readLine) ? windowManagerAutolistLoop() : com.android.server.wm.ViewServer.this.mWindowManager.viewServerWindowCommand(this.mClient, readLine, substring))) {
                            android.util.Slog.w(com.android.server.wm.ViewServer.LOG_TAG, "An error occurred with the command: " + readLine);
                        }
                        try {
                            bufferedReader.close();
                        } catch (java.io.IOException e3) {
                            e3.printStackTrace();
                        }
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                        bufferedReader2 = bufferedReader;
                        if (bufferedReader2 != null) {
                            try {
                                bufferedReader2.close();
                            } catch (java.io.IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        if (this.mClient != null) {
                            throw th;
                        }
                        try {
                            this.mClient.close();
                            throw th;
                        } catch (java.io.IOException e5) {
                            e5.printStackTrace();
                            throw th;
                        }
                    }
                } catch (java.io.IOException e6) {
                    e = e6;
                    android.util.Slog.w(com.android.server.wm.ViewServer.LOG_TAG, "Connection error: ", e);
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (java.io.IOException e7) {
                            e7.printStackTrace();
                        }
                    }
                    if (this.mClient != null) {
                        this.mClient.close();
                    }
                    return;
                }
                if (this.mClient != null) {
                    this.mClient.close();
                }
            } catch (java.io.IOException e8) {
                e8.printStackTrace();
            }
        }

        @Override // com.android.server.wm.WindowManagerService.WindowChangeListener
        public void windowsChanged() {
            synchronized (this) {
                this.mNeedWindowListUpdate = true;
                notifyAll();
            }
        }

        @Override // com.android.server.wm.WindowManagerService.WindowChangeListener
        public void focusChanged() {
            synchronized (this) {
                this.mNeedFocusedWindowUpdate = true;
                notifyAll();
            }
        }

        private boolean windowManagerAutolistLoop() {
            boolean z;
            boolean z2;
            com.android.server.wm.ViewServer.this.mWindowManager.addWindowChangeListener(this);
            java.io.BufferedWriter bufferedWriter = null;
            try {
                java.io.BufferedWriter bufferedWriter2 = new java.io.BufferedWriter(new java.io.OutputStreamWriter(this.mClient.getOutputStream()));
                while (!java.lang.Thread.interrupted()) {
                    try {
                        synchronized (this) {
                            while (!this.mNeedWindowListUpdate && !this.mNeedFocusedWindowUpdate) {
                                try {
                                    wait();
                                } finally {
                                }
                            }
                            z = false;
                            if (this.mNeedWindowListUpdate) {
                                this.mNeedWindowListUpdate = false;
                                z2 = true;
                            } else {
                                z2 = false;
                            }
                            if (this.mNeedFocusedWindowUpdate) {
                                this.mNeedFocusedWindowUpdate = false;
                                z = true;
                            }
                        }
                        if (z2) {
                            bufferedWriter2.write("LIST UPDATE\n");
                            bufferedWriter2.flush();
                        }
                        if (z) {
                            bufferedWriter2.write("ACTION_FOCUS UPDATE\n");
                            bufferedWriter2.flush();
                        }
                    } catch (java.lang.Exception e) {
                        bufferedWriter = bufferedWriter2;
                        if (bufferedWriter != null) {
                            try {
                                bufferedWriter.close();
                            } catch (java.io.IOException e2) {
                            }
                        }
                        com.android.server.wm.ViewServer.this.mWindowManager.removeWindowChangeListener(this);
                        return true;
                    } catch (java.lang.Throwable th) {
                        th = th;
                        bufferedWriter = bufferedWriter2;
                        if (bufferedWriter != null) {
                            try {
                                bufferedWriter.close();
                            } catch (java.io.IOException e3) {
                            }
                        }
                        com.android.server.wm.ViewServer.this.mWindowManager.removeWindowChangeListener(this);
                        throw th;
                    }
                }
                try {
                    bufferedWriter2.close();
                } catch (java.io.IOException e4) {
                }
            } catch (java.lang.Exception e5) {
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
            com.android.server.wm.ViewServer.this.mWindowManager.removeWindowChangeListener(this);
            return true;
        }
    }
}
