package com.android.server.media;

/* loaded from: classes2.dex */
public class MediaShellCommand extends android.os.ShellCommand {
    private static android.media.session.MediaSessionManager sMediaSessionManager;
    private static android.app.ActivityThread sThread;
    private java.io.PrintWriter mErrorWriter;
    private java.io.InputStream mInput;
    private final java.lang.String mPackageName;
    private android.media.session.ISessionManager mSessionService;
    private java.io.PrintWriter mWriter;

    public MediaShellCommand(java.lang.String str) {
        this.mPackageName = str;
    }

    public int onCommand(java.lang.String str) {
        this.mWriter = getOutPrintWriter();
        this.mErrorWriter = getErrPrintWriter();
        this.mInput = getRawInputStream();
        if (android.text.TextUtils.isEmpty(str)) {
            return handleDefaultCommands(str);
        }
        if (sThread == null) {
            android.os.Looper.prepare();
            sThread = android.app.ActivityThread.currentActivityThread();
            sMediaSessionManager = (android.media.session.MediaSessionManager) sThread.getSystemContext().getSystemService("media_session");
        }
        this.mSessionService = android.media.session.ISessionManager.Stub.asInterface(android.os.ServiceManager.checkService("media_session"));
        if (this.mSessionService == null) {
            throw new java.lang.IllegalStateException("Can't connect to media session service; is the system running?");
        }
        try {
            if (str.equals("dispatch")) {
                runDispatch();
                return 0;
            }
            if (str.equals("list-sessions")) {
                runListSessions();
                return 0;
            }
            if (str.equals("monitor")) {
                runMonitor();
                return 0;
            }
            if (str.equals("volume")) {
                runVolume();
                return 0;
            }
            showError("Error: unknown command '" + str + "'");
            return -1;
        } catch (java.lang.Exception e) {
            showError(e.toString());
            return -1;
        }
    }

    public void onHelp() {
        this.mWriter.println("usage: media_session [subcommand] [options]");
        this.mWriter.println("       media_session dispatch KEY");
        this.mWriter.println("       media_session list-sessions");
        this.mWriter.println("       media_session monitor <tag>");
        this.mWriter.println("       media_session volume [options]");
        this.mWriter.println();
        this.mWriter.println("media_session dispatch: dispatch a media key to the system.");
        this.mWriter.println("                KEY may be: play, pause, play-pause, mute, headsethook,");
        this.mWriter.println("                stop, next, previous, rewind, record, fast-forward.");
        this.mWriter.println("media_session list-sessions: print a list of the current sessions.");
        this.mWriter.println("media_session monitor: monitor updates to the specified session.");
        this.mWriter.println("                       Use the tag from list-sessions.");
        this.mWriter.println("media_session volume:  " + com.android.server.media.VolumeCtrl.USAGE);
        this.mWriter.println();
    }

    private void sendMediaKey(android.view.KeyEvent keyEvent) {
        try {
            this.mSessionService.dispatchMediaKeyEvent(this.mPackageName, false, keyEvent, false);
        } catch (android.os.RemoteException e) {
        }
    }

    private void runMonitor() throws java.lang.Exception {
        java.lang.String nextArgRequired = getNextArgRequired();
        if (nextArgRequired == null) {
            showError("Error: must include a session id");
            return;
        }
        boolean z = false;
        try {
            java.util.Iterator<android.media.session.MediaController> it = sMediaSessionManager.getActiveSessions(null).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                android.media.session.MediaController next = it.next();
                if (next != null) {
                    try {
                        if (nextArgRequired.equals(next.getTag())) {
                            new com.android.server.media.MediaShellCommand.ControllerMonitor(next).run();
                            z = true;
                            break;
                        }
                        continue;
                    } catch (android.os.RemoteException e) {
                    }
                }
            }
        } catch (java.lang.Exception e2) {
            this.mErrorWriter.println("***Error monitoring session*** " + e2.getMessage());
        }
        if (!z) {
            this.mErrorWriter.println("No session found with id " + nextArgRequired);
        }
    }

    private void runDispatch() throws java.lang.Exception {
        int i;
        java.lang.String nextArgRequired = getNextArgRequired();
        if ("play".equals(nextArgRequired)) {
            i = 126;
        } else if ("pause".equals(nextArgRequired)) {
            i = 127;
        } else if ("play-pause".equals(nextArgRequired)) {
            i = 85;
        } else if ("mute".equals(nextArgRequired)) {
            i = 91;
        } else if ("headsethook".equals(nextArgRequired)) {
            i = 79;
        } else if ("stop".equals(nextArgRequired)) {
            i = 86;
        } else if ("next".equals(nextArgRequired)) {
            i = 87;
        } else if ("previous".equals(nextArgRequired)) {
            i = 88;
        } else if ("rewind".equals(nextArgRequired)) {
            i = 89;
        } else if ("record".equals(nextArgRequired)) {
            i = 130;
        } else if ("fast-forward".equals(nextArgRequired)) {
            i = 90;
        } else {
            showError("Error: unknown dispatch code '" + nextArgRequired + "'");
            return;
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        sendMediaKey(new android.view.KeyEvent(uptimeMillis, uptimeMillis, 0, i, 0, 0, -1, 0, 0, 257));
        sendMediaKey(new android.view.KeyEvent(uptimeMillis, uptimeMillis, 1, i, 0, 0, -1, 0, 0, 257));
    }

    void log(java.lang.String str, java.lang.String str2) {
        this.mWriter.println(str + " " + str2);
    }

    void showError(java.lang.String str) {
        onHelp();
        this.mErrorWriter.println(str);
    }

    class ControllerCallback extends android.media.session.MediaController.Callback {
        ControllerCallback() {
        }

        @Override // android.media.session.MediaController.Callback
        public void onSessionDestroyed() {
            com.android.server.media.MediaShellCommand.this.mWriter.println("onSessionDestroyed. Enter q to quit.");
        }

        @Override // android.media.session.MediaController.Callback
        public void onSessionEvent(java.lang.String str, android.os.Bundle bundle) {
            com.android.server.media.MediaShellCommand.this.mWriter.println("onSessionEvent event=" + str + ", extras=" + bundle);
        }

        @Override // android.media.session.MediaController.Callback
        public void onPlaybackStateChanged(android.media.session.PlaybackState playbackState) {
            com.android.server.media.MediaShellCommand.this.mWriter.println("onPlaybackStateChanged " + playbackState);
        }

        @Override // android.media.session.MediaController.Callback
        public void onMetadataChanged(android.media.MediaMetadata mediaMetadata) {
            java.lang.String str;
            if (mediaMetadata == null) {
                str = null;
            } else {
                str = "title=" + mediaMetadata.getDescription();
            }
            com.android.server.media.MediaShellCommand.this.mWriter.println("onMetadataChanged " + str);
        }

        @Override // android.media.session.MediaController.Callback
        public void onQueueChanged(java.util.List<android.media.session.MediaSession.QueueItem> list) {
            java.lang.String str;
            java.io.PrintWriter printWriter = com.android.server.media.MediaShellCommand.this.mWriter;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("onQueueChanged, ");
            if (list == null) {
                str = "null queue";
            } else {
                str = " size=" + list.size();
            }
            sb.append(str);
            printWriter.println(sb.toString());
        }

        @Override // android.media.session.MediaController.Callback
        public void onQueueTitleChanged(java.lang.CharSequence charSequence) {
            com.android.server.media.MediaShellCommand.this.mWriter.println("onQueueTitleChange " + ((java.lang.Object) charSequence));
        }

        @Override // android.media.session.MediaController.Callback
        public void onExtrasChanged(android.os.Bundle bundle) {
            com.android.server.media.MediaShellCommand.this.mWriter.println("onExtrasChanged " + bundle);
        }

        @Override // android.media.session.MediaController.Callback
        public void onAudioInfoChanged(android.media.session.MediaController.PlaybackInfo playbackInfo) {
            com.android.server.media.MediaShellCommand.this.mWriter.println("onAudioInfoChanged " + playbackInfo);
        }
    }

    private class ControllerMonitor {
        private final android.media.session.MediaController mController;
        private final com.android.server.media.MediaShellCommand.ControllerCallback mControllerCallback;

        ControllerMonitor(android.media.session.MediaController mediaController) {
            this.mController = mediaController;
            this.mControllerCallback = com.android.server.media.MediaShellCommand.this.new ControllerCallback();
        }

        void printUsageMessage() {
            try {
                com.android.server.media.MediaShellCommand.this.mWriter.println("V2Monitoring session " + this.mController.getTag() + "...  available commands: play, pause, next, previous");
            } catch (java.lang.RuntimeException e) {
                com.android.server.media.MediaShellCommand.this.mWriter.println("Error trying to monitor session!");
            }
            com.android.server.media.MediaShellCommand.this.mWriter.println("(q)uit: finish monitoring");
        }

        void run() throws android.os.RemoteException {
            boolean z;
            printUsageMessage();
            android.os.HandlerThread handlerThread = new android.os.HandlerThread("MediaCb") { // from class: com.android.server.media.MediaShellCommand.ControllerMonitor.1
                @Override // android.os.HandlerThread
                protected void onLooperPrepared() {
                    try {
                        com.android.server.media.MediaShellCommand.ControllerMonitor.this.mController.registerCallback(com.android.server.media.MediaShellCommand.ControllerMonitor.this.mControllerCallback);
                    } catch (java.lang.RuntimeException e) {
                        com.android.server.media.MediaShellCommand.this.mErrorWriter.println("Error registering monitor callback");
                    }
                }
            };
            handlerThread.start();
            try {
                try {
                    try {
                        java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(com.android.server.media.MediaShellCommand.this.mInput));
                        while (true) {
                            com.android.server.media.MediaShellCommand.this.mWriter.flush();
                            com.android.server.media.MediaShellCommand.this.mErrorWriter.flush();
                            java.lang.String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            if (readLine.length() > 0) {
                                if ("q".equals(readLine) || "quit".equals(readLine)) {
                                    break;
                                }
                                if ("play".equals(readLine)) {
                                    dispatchKeyCode(126);
                                } else if ("pause".equals(readLine)) {
                                    dispatchKeyCode(127);
                                } else if ("next".equals(readLine)) {
                                    dispatchKeyCode(87);
                                } else if ("previous".equals(readLine)) {
                                    dispatchKeyCode(88);
                                } else {
                                    com.android.server.media.MediaShellCommand.this.mErrorWriter.println("Invalid command: " + readLine);
                                }
                                z = true;
                            } else {
                                z = false;
                            }
                            synchronized (this) {
                                if (z) {
                                    try {
                                        com.android.server.media.MediaShellCommand.this.mWriter.println("");
                                    } catch (java.lang.Throwable th) {
                                        throw th;
                                    }
                                }
                                printUsageMessage();
                            }
                        }
                        handlerThread.getLooper().quit();
                        this.mController.unregisterCallback(this.mControllerCallback);
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                        handlerThread.getLooper().quit();
                        this.mController.unregisterCallback(this.mControllerCallback);
                    }
                } catch (java.lang.Throwable th2) {
                    handlerThread.getLooper().quit();
                    try {
                        this.mController.unregisterCallback(this.mControllerCallback);
                    } catch (java.lang.Exception e2) {
                    }
                    throw th2;
                }
            } catch (java.lang.Exception e3) {
            }
        }

        private void dispatchKeyCode(int i) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            android.view.KeyEvent keyEvent = new android.view.KeyEvent(uptimeMillis, uptimeMillis, 0, i, 0, 0, -1, 0, 0, 257);
            android.view.KeyEvent keyEvent2 = new android.view.KeyEvent(uptimeMillis, uptimeMillis, 1, i, 0, 0, -1, 0, 0, 257);
            try {
                this.mController.dispatchMediaButtonEvent(keyEvent);
                this.mController.dispatchMediaButtonEvent(keyEvent2);
            } catch (java.lang.RuntimeException e) {
                com.android.server.media.MediaShellCommand.this.mErrorWriter.println("Failed to dispatch " + i);
            }
        }
    }

    private void runListSessions() {
        this.mWriter.println("Sessions:");
        try {
            for (android.media.session.MediaController mediaController : sMediaSessionManager.getActiveSessions(null)) {
                if (mediaController != null) {
                    try {
                        this.mWriter.println("  tag=" + mediaController.getTag() + ", package=" + mediaController.getPackageName());
                    } catch (java.lang.RuntimeException e) {
                    }
                }
            }
        } catch (java.lang.Exception e2) {
            this.mErrorWriter.println("***Error listing sessions***");
        }
    }

    private void runVolume() throws java.lang.Exception {
        com.android.server.media.VolumeCtrl.run(this);
    }
}
