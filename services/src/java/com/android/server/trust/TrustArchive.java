package com.android.server.trust;

/* loaded from: classes2.dex */
public class TrustArchive {
    private static final int HISTORY_LIMIT = 200;
    private static final int TYPE_AGENT_CONNECTED = 4;
    private static final int TYPE_AGENT_DIED = 3;
    private static final int TYPE_AGENT_STOPPED = 5;
    private static final int TYPE_GRANT_TRUST = 0;
    private static final int TYPE_MANAGING_TRUST = 6;
    private static final int TYPE_POLICY_CHANGED = 7;
    private static final int TYPE_REVOKE_TRUST = 1;
    private static final int TYPE_TRUST_TIMEOUT = 2;
    java.util.ArrayDeque<com.android.server.trust.TrustArchive.Event> mEvents = new java.util.ArrayDeque<>();

    private static class Event {
        final android.content.ComponentName agent;
        final long duration;
        final long elapsedTimestamp;
        final int flags;
        final boolean managingTrust;
        final java.lang.String message;
        final int type;
        final int userId;

        private Event(int i, int i2, android.content.ComponentName componentName, java.lang.String str, long j, int i3, boolean z) {
            this.type = i;
            this.userId = i2;
            this.agent = componentName;
            this.elapsedTimestamp = android.os.SystemClock.elapsedRealtime();
            this.message = str;
            this.duration = j;
            this.flags = i3;
            this.managingTrust = z;
        }
    }

    public void logGrantTrust(int i, android.content.ComponentName componentName, java.lang.String str, long j, int i2) {
        addEvent(new com.android.server.trust.TrustArchive.Event(0, i, componentName, str, j, i2, false));
    }

    public void logRevokeTrust(int i, android.content.ComponentName componentName) {
        addEvent(new com.android.server.trust.TrustArchive.Event(1, i, componentName, null, 0L, 0, false));
    }

    public void logTrustTimeout(int i, android.content.ComponentName componentName) {
        addEvent(new com.android.server.trust.TrustArchive.Event(2, i, componentName, null, 0L, 0, false));
    }

    public void logAgentDied(int i, android.content.ComponentName componentName) {
        addEvent(new com.android.server.trust.TrustArchive.Event(3, i, componentName, null, 0L, 0, false));
    }

    public void logAgentConnected(int i, android.content.ComponentName componentName) {
        addEvent(new com.android.server.trust.TrustArchive.Event(4, i, componentName, null, 0L, 0, false));
    }

    public void logAgentStopped(int i, android.content.ComponentName componentName) {
        addEvent(new com.android.server.trust.TrustArchive.Event(5, i, componentName, null, 0L, 0, false));
    }

    public void logManagingTrust(int i, android.content.ComponentName componentName, boolean z) {
        addEvent(new com.android.server.trust.TrustArchive.Event(6, i, componentName, null, 0L, 0, z));
    }

    public void logDevicePolicyChanged() {
        addEvent(new com.android.server.trust.TrustArchive.Event(7, -1, null, null, 0L, 0, false));
    }

    private void addEvent(com.android.server.trust.TrustArchive.Event event) {
        if (this.mEvents.size() >= 200) {
            this.mEvents.removeFirst();
        }
        this.mEvents.addLast(event);
    }

    public void dump(java.io.PrintWriter printWriter, int i, int i2, java.lang.String str, boolean z) {
        java.util.Iterator<com.android.server.trust.TrustArchive.Event> descendingIterator = this.mEvents.descendingIterator();
        int i3 = 0;
        while (descendingIterator.hasNext() && i3 < i) {
            com.android.server.trust.TrustArchive.Event next = descendingIterator.next();
            if (i2 == -1 || i2 == next.userId || next.userId == -1) {
                printWriter.print(str);
                printWriter.printf("#%-2d %s %s: ", java.lang.Integer.valueOf(i3), formatElapsed(next.elapsedTimestamp), dumpType(next.type));
                if (i2 == -1) {
                    printWriter.print("user=");
                    printWriter.print(next.userId);
                    printWriter.print(", ");
                }
                if (next.agent != null) {
                    printWriter.print("agent=");
                    if (z) {
                        printWriter.print(next.agent.flattenToShortString());
                    } else {
                        printWriter.print(getSimpleName(next.agent));
                    }
                }
                switch (next.type) {
                    case 0:
                        printWriter.printf(", message=\"%s\", duration=%s, flags=%s", next.message, formatDuration(next.duration), dumpGrantFlags(next.flags));
                        break;
                    case 6:
                        printWriter.printf(", managingTrust=" + next.managingTrust, new java.lang.Object[0]);
                        break;
                }
                printWriter.println();
                i3++;
            }
        }
    }

    public static java.lang.String formatDuration(long j) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        android.util.TimeUtils.formatDuration(j, sb);
        return sb.toString();
    }

    private static java.lang.String formatElapsed(long j) {
        return android.util.TimeUtils.logTimeOfDay((j - android.os.SystemClock.elapsedRealtime()) + java.lang.System.currentTimeMillis());
    }

    static java.lang.String getSimpleName(android.content.ComponentName componentName) {
        java.lang.String className = componentName.getClassName();
        int lastIndexOf = className.lastIndexOf(46);
        if (lastIndexOf < className.length() && lastIndexOf >= 0) {
            return className.substring(lastIndexOf + 1);
        }
        return className;
    }

    private java.lang.String dumpType(int i) {
        switch (i) {
            case 0:
                return "GrantTrust";
            case 1:
                return "RevokeTrust";
            case 2:
                return "TrustTimeout";
            case 3:
                return "AgentDied";
            case 4:
                return "AgentConnected";
            case 5:
                return "AgentStopped";
            case 6:
                return "ManagingTrust";
            case 7:
                return "DevicePolicyChanged";
            default:
                return "Unknown(" + i + ")";
        }
    }

    private java.lang.String dumpGrantFlags(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if ((i & 1) != 0) {
            if (sb.length() != 0) {
                sb.append('|');
            }
            sb.append("INITIATED_BY_USER");
        }
        if ((i & 2) != 0) {
            if (sb.length() != 0) {
                sb.append('|');
            }
            sb.append("DISMISS_KEYGUARD");
        }
        if (sb.length() == 0) {
            sb.append('0');
        }
        return sb.toString();
    }
}
