package com.android.internal.util;

/* loaded from: classes5.dex */
public class AsyncChannel {
    private static final int BASE = 69632;
    public static final int CMD_CHANNEL_DISCONNECT = 69635;
    public static final int CMD_CHANNEL_DISCONNECTED = 69636;
    public static final int CMD_CHANNEL_FULLY_CONNECTED = 69634;
    public static final int CMD_CHANNEL_FULL_CONNECTION = 69633;
    public static final int CMD_CHANNEL_HALF_CONNECTED = 69632;
    private static final int CMD_TO_STRING_COUNT = 5;
    private static final boolean DBG = false;
    public static final int STATUS_BINDING_UNSUCCESSFUL = 1;
    public static final int STATUS_FULL_CONNECTION_REFUSED_ALREADY_CONNECTED = 3;
    public static final int STATUS_REMOTE_DISCONNECTION = 4;
    public static final int STATUS_SEND_UNSUCCESSFUL = 2;
    public static final int STATUS_SUCCESSFUL = 0;
    private static final java.lang.String TAG = "AsyncChannel";
    private static java.lang.String[] sCmdToString = new java.lang.String[5];
    private com.android.internal.util.AsyncChannel.AsyncChannelConnection mConnection;
    private com.android.internal.util.AsyncChannel.DeathMonitor mDeathMonitor;
    private android.os.Messenger mDstMessenger;
    private android.content.Context mSrcContext;
    private android.os.Handler mSrcHandler;
    private android.os.Messenger mSrcMessenger;

    static {
        sCmdToString[0] = "CMD_CHANNEL_HALF_CONNECTED";
        sCmdToString[1] = "CMD_CHANNEL_FULL_CONNECTION";
        sCmdToString[2] = "CMD_CHANNEL_FULLY_CONNECTED";
        sCmdToString[3] = "CMD_CHANNEL_DISCONNECT";
        sCmdToString[4] = "CMD_CHANNEL_DISCONNECTED";
    }

    protected static java.lang.String cmdToString(int i) {
        int i2 = i - 69632;
        if (i2 >= 0 && i2 < sCmdToString.length) {
            return sCmdToString[i2];
        }
        return null;
    }

    public int connectSrcHandlerToPackageSync(android.content.Context context, android.os.Handler handler, java.lang.String str, java.lang.String str2) {
        this.mConnection = new com.android.internal.util.AsyncChannel.AsyncChannelConnection();
        this.mSrcContext = context;
        this.mSrcHandler = handler;
        this.mSrcMessenger = new android.os.Messenger(handler);
        this.mDstMessenger = null;
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_MAIN);
        intent.setClassName(str, str2);
        return !context.bindService(intent, this.mConnection, 1) ? 1 : 0;
    }

    public int connectSync(android.content.Context context, android.os.Handler handler, android.os.Messenger messenger) {
        connected(context, handler, messenger);
        return 0;
    }

    public int connectSync(android.content.Context context, android.os.Handler handler, android.os.Handler handler2) {
        return connectSync(context, handler, new android.os.Messenger(handler2));
    }

    public int fullyConnectSync(android.content.Context context, android.os.Handler handler, android.os.Handler handler2) {
        int connectSync = connectSync(context, handler, handler2);
        if (connectSync == 0) {
            return sendMessageSynchronously(CMD_CHANNEL_FULL_CONNECTION).arg1;
        }
        return connectSync;
    }

    public void connect(android.content.Context context, android.os.Handler handler, java.lang.String str, java.lang.String str2) {
        new java.lang.Thread(new java.lang.Runnable(context, handler, str, str2) { // from class: com.android.internal.util.AsyncChannel.1ConnectAsync
            java.lang.String mDstClassName;
            java.lang.String mDstPackageName;
            android.content.Context mSrcCtx;
            android.os.Handler mSrcHdlr;

            {
                this.mSrcCtx = context;
                this.mSrcHdlr = handler;
                this.mDstPackageName = str;
                this.mDstClassName = str2;
            }

            @Override // java.lang.Runnable
            public void run() {
                com.android.internal.util.AsyncChannel.this.replyHalfConnected(com.android.internal.util.AsyncChannel.this.connectSrcHandlerToPackageSync(this.mSrcCtx, this.mSrcHdlr, this.mDstPackageName, this.mDstClassName));
            }
        }).start();
    }

    public void connect(android.content.Context context, android.os.Handler handler, java.lang.Class<?> cls) {
        connect(context, handler, cls.getPackage().getName(), cls.getName());
    }

    public void connect(android.content.Context context, android.os.Handler handler, android.os.Messenger messenger) {
        connected(context, handler, messenger);
        replyHalfConnected(0);
    }

    public void connected(android.content.Context context, android.os.Handler handler, android.os.Messenger messenger) {
        this.mSrcContext = context;
        this.mSrcHandler = handler;
        this.mSrcMessenger = new android.os.Messenger(this.mSrcHandler);
        this.mDstMessenger = messenger;
    }

    public void connect(android.content.Context context, android.os.Handler handler, android.os.Handler handler2) {
        connect(context, handler, new android.os.Messenger(handler2));
    }

    public void connect(com.android.internal.util.AsyncService asyncService, android.os.Messenger messenger) {
        connect(asyncService, asyncService.getHandler(), messenger);
    }

    public void disconnected() {
        this.mSrcContext = null;
        this.mSrcHandler = null;
        this.mSrcMessenger = null;
        this.mDstMessenger = null;
        this.mDeathMonitor = null;
        this.mConnection = null;
    }

    public void disconnect() {
        if (this.mConnection != null && this.mSrcContext != null) {
            this.mSrcContext.unbindService(this.mConnection);
            this.mConnection = null;
        }
        try {
            android.os.Message obtain = android.os.Message.obtain();
            obtain.what = CMD_CHANNEL_DISCONNECTED;
            obtain.replyTo = this.mSrcMessenger;
            this.mDstMessenger.send(obtain);
        } catch (java.lang.Exception e) {
        }
        replyDisconnected(0);
        this.mSrcHandler = null;
        if (this.mConnection == null && this.mDstMessenger != null && this.mDeathMonitor != null) {
            this.mDstMessenger.getBinder().unlinkToDeath(this.mDeathMonitor, 0);
            this.mDeathMonitor = null;
        }
    }

    public void sendMessage(android.os.Message message) {
        message.replyTo = this.mSrcMessenger;
        try {
            this.mDstMessenger.send(message);
        } catch (android.os.RemoteException e) {
            replyDisconnected(2);
        }
    }

    public void sendMessage(int i) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = i;
        sendMessage(obtain);
    }

    public void sendMessage(int i, int i2) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        sendMessage(obtain);
    }

    public void sendMessage(int i, int i2, int i3) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        obtain.arg2 = i3;
        sendMessage(obtain);
    }

    public void sendMessage(int i, int i2, int i3, java.lang.Object obj) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        obtain.arg2 = i3;
        obtain.obj = obj;
        sendMessage(obtain);
    }

    public void sendMessage(int i, java.lang.Object obj) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = i;
        obtain.obj = obj;
        sendMessage(obtain);
    }

    public void replyToMessage(android.os.Message message, android.os.Message message2) {
        try {
            message2.replyTo = this.mSrcMessenger;
            message.replyTo.send(message2);
        } catch (android.os.RemoteException e) {
            log("TODO: handle replyToMessage RemoteException" + e);
            e.printStackTrace();
        }
    }

    public void replyToMessage(android.os.Message message, int i) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = i;
        replyToMessage(message, obtain);
    }

    public void replyToMessage(android.os.Message message, int i, int i2) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        replyToMessage(message, obtain);
    }

    public void replyToMessage(android.os.Message message, int i, int i2, int i3) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        obtain.arg2 = i3;
        replyToMessage(message, obtain);
    }

    public void replyToMessage(android.os.Message message, int i, int i2, int i3, java.lang.Object obj) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        obtain.arg2 = i3;
        obtain.obj = obj;
        replyToMessage(message, obtain);
    }

    public void replyToMessage(android.os.Message message, int i, java.lang.Object obj) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = i;
        obtain.obj = obj;
        replyToMessage(message, obtain);
    }

    public android.os.Message sendMessageSynchronously(android.os.Message message) {
        return com.android.internal.util.AsyncChannel.SyncMessenger.sendMessageSynchronously(this.mDstMessenger, message);
    }

    public android.os.Message sendMessageSynchronously(int i) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = i;
        return sendMessageSynchronously(obtain);
    }

    public android.os.Message sendMessageSynchronously(int i, int i2) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        return sendMessageSynchronously(obtain);
    }

    public android.os.Message sendMessageSynchronously(int i, int i2, int i3) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        obtain.arg2 = i3;
        return sendMessageSynchronously(obtain);
    }

    public android.os.Message sendMessageSynchronously(int i, int i2, int i3, java.lang.Object obj) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        obtain.arg2 = i3;
        obtain.obj = obj;
        return sendMessageSynchronously(obtain);
    }

    public android.os.Message sendMessageSynchronously(int i, java.lang.Object obj) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = i;
        obtain.obj = obj;
        return sendMessageSynchronously(obtain);
    }

    private static class SyncMessenger {
        private com.android.internal.util.AsyncChannel.SyncMessenger.SyncHandler mHandler;
        private android.os.HandlerThread mHandlerThread;
        private android.os.Messenger mMessenger;
        private static java.util.Stack<com.android.internal.util.AsyncChannel.SyncMessenger> sStack = new java.util.Stack<>();
        private static int sCount = 0;

        private SyncMessenger() {
        }

        private class SyncHandler extends android.os.Handler {
            private java.lang.Object mLockObject;
            private android.os.Message mResultMsg;

            private SyncHandler(android.os.Looper looper) {
                super(looper);
                this.mLockObject = new java.lang.Object();
            }

            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                android.os.Message obtain = android.os.Message.obtain();
                obtain.copyFrom(message);
                synchronized (this.mLockObject) {
                    this.mResultMsg = obtain;
                    this.mLockObject.notify();
                }
            }
        }

        private static com.android.internal.util.AsyncChannel.SyncMessenger obtain() {
            com.android.internal.util.AsyncChannel.SyncMessenger pop;
            synchronized (sStack) {
                if (sStack.isEmpty()) {
                    pop = new com.android.internal.util.AsyncChannel.SyncMessenger();
                    java.lang.StringBuilder append = new java.lang.StringBuilder().append("SyncHandler-");
                    int i = sCount;
                    sCount = i + 1;
                    pop.mHandlerThread = new android.os.HandlerThread(append.append(i).toString());
                    pop.mHandlerThread.start();
                    java.util.Objects.requireNonNull(pop);
                    pop.mHandler = new com.android.internal.util.AsyncChannel.SyncMessenger.SyncHandler(pop.mHandlerThread.getLooper());
                    pop.mMessenger = new android.os.Messenger(pop.mHandler);
                } else {
                    pop = sStack.pop();
                }
            }
            return pop;
        }

        private void recycle() {
            synchronized (sStack) {
                sStack.push(this);
            }
        }

        /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:22:0x0047
            	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
            	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
            	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
            */
        /* JADX INFO: Access modifiers changed from: private */
        public static android.os.Message sendMessageSynchronously(android.os.Messenger r5, android.os.Message r6) {
            /*
                com.android.internal.util.AsyncChannel$SyncMessenger r0 = obtain()
                r1 = 0
                if (r5 == 0) goto L5b
                if (r6 == 0) goto L5b
                android.os.Messenger r2 = r0.mMessenger     // Catch: android.os.RemoteException -> L4a java.lang.InterruptedException -> L53
                r6.replyTo = r2     // Catch: android.os.RemoteException -> L4a java.lang.InterruptedException -> L53
                com.android.internal.util.AsyncChannel$SyncMessenger$SyncHandler r2 = r0.mHandler     // Catch: android.os.RemoteException -> L4a java.lang.InterruptedException -> L53
                java.lang.Object r2 = com.android.internal.util.AsyncChannel.SyncMessenger.SyncHandler.m7064$$Nest$fgetmLockObject(r2)     // Catch: android.os.RemoteException -> L4a java.lang.InterruptedException -> L53
                monitor-enter(r2)     // Catch: android.os.RemoteException -> L4a java.lang.InterruptedException -> L53
                com.android.internal.util.AsyncChannel$SyncMessenger$SyncHandler r3 = r0.mHandler     // Catch: java.lang.Throwable -> L47
                android.os.Message r3 = com.android.internal.util.AsyncChannel.SyncMessenger.SyncHandler.m7065$$Nest$fgetmResultMsg(r3)     // Catch: java.lang.Throwable -> L47
                if (r3 == 0) goto L29
                java.lang.String r3 = "AsyncChannel"
                java.lang.String r4 = "mResultMsg should be null here"
                android.util.Log.wtf(r3, r4)     // Catch: java.lang.Throwable -> L47
                com.android.internal.util.AsyncChannel$SyncMessenger$SyncHandler r3 = r0.mHandler     // Catch: java.lang.Throwable -> L47
                com.android.internal.util.AsyncChannel.SyncMessenger.SyncHandler.m7066$$Nest$fputmResultMsg(r3, r1)     // Catch: java.lang.Throwable -> L47
            L29:
                r5.send(r6)     // Catch: java.lang.Throwable -> L47
                com.android.internal.util.AsyncChannel$SyncMessenger$SyncHandler r5 = r0.mHandler     // Catch: java.lang.Throwable -> L47
                java.lang.Object r5 = com.android.internal.util.AsyncChannel.SyncMessenger.SyncHandler.m7064$$Nest$fgetmLockObject(r5)     // Catch: java.lang.Throwable -> L47
                r5.wait()     // Catch: java.lang.Throwable -> L47
                com.android.internal.util.AsyncChannel$SyncMessenger$SyncHandler r5 = r0.mHandler     // Catch: java.lang.Throwable -> L47
                android.os.Message r5 = com.android.internal.util.AsyncChannel.SyncMessenger.SyncHandler.m7065$$Nest$fgetmResultMsg(r5)     // Catch: java.lang.Throwable -> L47
                com.android.internal.util.AsyncChannel$SyncMessenger$SyncHandler r6 = r0.mHandler     // Catch: java.lang.Throwable -> L43
                com.android.internal.util.AsyncChannel.SyncMessenger.SyncHandler.m7066$$Nest$fputmResultMsg(r6, r1)     // Catch: java.lang.Throwable -> L43
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L43
                r1 = r5
                goto L5b
            L43:
                r6 = move-exception
                r1 = r5
                r5 = r6
                goto L48
            L47:
                r5 = move-exception
            L48:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L47
                throw r5     // Catch: android.os.RemoteException -> L4a java.lang.InterruptedException -> L53
            L4a:
                r5 = move-exception
                java.lang.String r6 = "AsyncChannel"
                java.lang.String r2 = "error in sendMessageSynchronously"
                android.util.Log.e(r6, r2, r5)
                goto L5c
            L53:
                r5 = move-exception
                java.lang.String r6 = "AsyncChannel"
                java.lang.String r2 = "error in sendMessageSynchronously"
                android.util.Log.e(r6, r2, r5)
            L5b:
            L5c:
                r0.recycle()
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.util.AsyncChannel.SyncMessenger.sendMessageSynchronously(android.os.Messenger, android.os.Message):android.os.Message");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void replyHalfConnected(int i) {
        android.os.Message obtainMessage = this.mSrcHandler.obtainMessage(69632);
        obtainMessage.arg1 = i;
        obtainMessage.obj = this;
        obtainMessage.replyTo = this.mDstMessenger;
        if (!linkToDeathMonitor()) {
            obtainMessage.arg1 = 1;
        }
        this.mSrcHandler.sendMessage(obtainMessage);
    }

    private boolean linkToDeathMonitor() {
        if (this.mConnection == null && this.mDeathMonitor == null) {
            this.mDeathMonitor = new com.android.internal.util.AsyncChannel.DeathMonitor();
            try {
                this.mDstMessenger.getBinder().linkToDeath(this.mDeathMonitor, 0);
                return true;
            } catch (android.os.RemoteException e) {
                this.mDeathMonitor = null;
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void replyDisconnected(int i) {
        if (this.mSrcHandler == null) {
            return;
        }
        android.os.Message obtainMessage = this.mSrcHandler.obtainMessage(CMD_CHANNEL_DISCONNECTED);
        obtainMessage.arg1 = i;
        obtainMessage.obj = this;
        obtainMessage.replyTo = this.mDstMessenger;
        this.mSrcHandler.sendMessage(obtainMessage);
    }

    class AsyncChannelConnection implements android.content.ServiceConnection {
        AsyncChannelConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            com.android.internal.util.AsyncChannel.this.mDstMessenger = new android.os.Messenger(iBinder);
            com.android.internal.util.AsyncChannel.this.replyHalfConnected(0);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            com.android.internal.util.AsyncChannel.this.replyDisconnected(0);
        }
    }

    private static void log(java.lang.String str) {
        android.util.Log.d(TAG, str);
    }

    private final class DeathMonitor implements android.os.IBinder.DeathRecipient {
        DeathMonitor() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.internal.util.AsyncChannel.this.replyDisconnected(4);
        }
    }
}
