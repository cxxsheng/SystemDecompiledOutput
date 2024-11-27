package com.android.server.am;

/* loaded from: classes.dex */
public class BroadcastHistory {
    private final int MAX_BROADCAST_HISTORY;
    private final int MAX_BROADCAST_SUMMARY_HISTORY;
    final com.android.server.am.BroadcastRecord[] mBroadcastHistory;
    final android.content.Intent[] mBroadcastSummaryHistory;
    final long[] mSummaryHistoryDispatchTime;
    final long[] mSummaryHistoryEnqueueTime;
    final long[] mSummaryHistoryFinishTime;
    private final java.util.ArrayList<com.android.server.am.BroadcastRecord> mPendingBroadcasts = new java.util.ArrayList<>();
    int mHistoryNext = 0;
    int mSummaryHistoryNext = 0;

    public BroadcastHistory(@android.annotation.NonNull com.android.server.am.BroadcastConstants broadcastConstants) {
        this.MAX_BROADCAST_HISTORY = broadcastConstants.MAX_HISTORY_COMPLETE_SIZE;
        this.MAX_BROADCAST_SUMMARY_HISTORY = broadcastConstants.MAX_HISTORY_SUMMARY_SIZE;
        this.mBroadcastHistory = new com.android.server.am.BroadcastRecord[this.MAX_BROADCAST_HISTORY];
        this.mBroadcastSummaryHistory = new android.content.Intent[this.MAX_BROADCAST_SUMMARY_HISTORY];
        this.mSummaryHistoryEnqueueTime = new long[this.MAX_BROADCAST_SUMMARY_HISTORY];
        this.mSummaryHistoryDispatchTime = new long[this.MAX_BROADCAST_SUMMARY_HISTORY];
        this.mSummaryHistoryFinishTime = new long[this.MAX_BROADCAST_SUMMARY_HISTORY];
    }

    void onBroadcastEnqueuedLocked(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord) {
        this.mPendingBroadcasts.add(broadcastRecord);
    }

    void onBroadcastFinishedLocked(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord) {
        this.mPendingBroadcasts.remove(broadcastRecord);
        addBroadcastToHistoryLocked(broadcastRecord);
    }

    public void addBroadcastToHistoryLocked(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord) {
        com.android.server.am.BroadcastRecord maybeStripForHistory = broadcastRecord.maybeStripForHistory();
        this.mBroadcastHistory[this.mHistoryNext] = maybeStripForHistory;
        this.mHistoryNext = ringAdvance(this.mHistoryNext, 1, this.MAX_BROADCAST_HISTORY);
        this.mBroadcastSummaryHistory[this.mSummaryHistoryNext] = maybeStripForHistory.intent;
        this.mSummaryHistoryEnqueueTime[this.mSummaryHistoryNext] = maybeStripForHistory.enqueueClockTime;
        this.mSummaryHistoryDispatchTime[this.mSummaryHistoryNext] = maybeStripForHistory.dispatchClockTime;
        this.mSummaryHistoryFinishTime[this.mSummaryHistoryNext] = java.lang.System.currentTimeMillis();
        this.mSummaryHistoryNext = ringAdvance(this.mSummaryHistoryNext, 1, this.MAX_BROADCAST_SUMMARY_HISTORY);
    }

    private final int ringAdvance(int i, int i2, int i3) {
        int i4 = i + i2;
        if (i4 < 0) {
            return i3 - 1;
        }
        if (i4 >= i3) {
            return 0;
        }
        return i4;
    }

    @dalvik.annotation.optimization.NeverCompile
    public void dumpDebug(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream) {
        for (int i = 0; i < this.mPendingBroadcasts.size(); i++) {
            this.mPendingBroadcasts.get(i).dumpDebug(protoOutputStream, 2246267895815L);
        }
        int i2 = this.mHistoryNext;
        int i3 = i2;
        do {
            i3 = ringAdvance(i3, -1, this.MAX_BROADCAST_HISTORY);
            com.android.server.am.BroadcastRecord broadcastRecord = this.mBroadcastHistory[i3];
            if (broadcastRecord != null) {
                broadcastRecord.dumpDebug(protoOutputStream, 2246267895813L);
            }
        } while (i3 != i2);
        int i4 = this.mSummaryHistoryNext;
        int i5 = i4;
        do {
            i5 = ringAdvance(i5, -1, this.MAX_BROADCAST_SUMMARY_HISTORY);
            android.content.Intent intent = this.mBroadcastSummaryHistory[i5];
            if (intent != null) {
                long start = protoOutputStream.start(2246267895814L);
                intent.dumpDebug(protoOutputStream, 1146756268033L, false, true, true, false);
                protoOutputStream.write(1112396529666L, this.mSummaryHistoryEnqueueTime[i5]);
                protoOutputStream.write(1112396529667L, this.mSummaryHistoryDispatchTime[i5]);
                protoOutputStream.write(1112396529668L, this.mSummaryHistoryFinishTime[i5]);
                protoOutputStream.end(start);
            }
        } while (i5 != i4);
    }

    @dalvik.annotation.optimization.NeverCompile
    public boolean dumpLocked(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.text.SimpleDateFormat simpleDateFormat, boolean z, boolean z2) {
        java.lang.String str3;
        int i;
        java.lang.String str4;
        java.lang.String str5;
        int i2;
        java.lang.String str6;
        java.lang.String str7;
        java.lang.String str8;
        com.android.server.am.BroadcastHistory broadcastHistory = this;
        java.lang.String str9 = str2;
        printWriter.println("  Pending broadcasts:");
        java.lang.String str10 = ":";
        java.lang.String str11 = "    ";
        if (broadcastHistory.mPendingBroadcasts.isEmpty()) {
            printWriter.println("    <empty>");
        } else {
            for (int size = broadcastHistory.mPendingBroadcasts.size() - 1; size >= 0; size--) {
                com.android.server.am.BroadcastRecord broadcastRecord = broadcastHistory.mPendingBroadcasts.get(size);
                printWriter.print("  Broadcast #");
                printWriter.print(size);
                printWriter.println(":");
                broadcastRecord.dump(printWriter, "    ", simpleDateFormat);
            }
        }
        int i3 = broadcastHistory.mHistoryNext;
        int i4 = -1;
        boolean z3 = z2;
        int i5 = i3;
        int i6 = -1;
        boolean z4 = false;
        while (true) {
            i5 = broadcastHistory.ringAdvance(i5, i4, broadcastHistory.MAX_BROADCAST_HISTORY);
            com.android.server.am.BroadcastRecord broadcastRecord2 = broadcastHistory.mBroadcastHistory[i5];
            str3 = ": ";
            if (broadcastRecord2 == null) {
                str4 = "]:";
                i = i3;
                str5 = str10;
            } else {
                i6++;
                if (str != null) {
                    i = i3;
                    if (!str.equals(broadcastRecord2.callerPackage)) {
                        str4 = "]:";
                        str5 = str10;
                    }
                } else {
                    i = i3;
                }
                if (!z4) {
                    if (z3) {
                        printWriter.println();
                    }
                    printWriter.println("  Historical broadcasts [" + str9 + "]:");
                    z3 = true;
                    z4 = true;
                }
                if (z) {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    str4 = "]:";
                    sb.append("  Historical Broadcast ");
                    sb.append(str9);
                    sb.append(" #");
                    printWriter.print(sb.toString());
                    printWriter.print(i6);
                    printWriter.println(str10);
                    broadcastRecord2.dump(printWriter, "    ", simpleDateFormat);
                    str5 = str10;
                } else {
                    str4 = "]:";
                    printWriter.print("  #");
                    printWriter.print(i6);
                    printWriter.print(": ");
                    printWriter.println(broadcastRecord2);
                    printWriter.print("    ");
                    str5 = str10;
                    printWriter.println(broadcastRecord2.intent.toShortString(false, true, true, false));
                    if (broadcastRecord2.targetComp != null && broadcastRecord2.targetComp != broadcastRecord2.intent.getComponent()) {
                        printWriter.print("    targetComp: ");
                        printWriter.println(broadcastRecord2.targetComp.toShortString());
                    }
                    android.os.Bundle extras = broadcastRecord2.intent.getExtras();
                    if (extras != null) {
                        printWriter.print("    extras: ");
                        printWriter.println(extras.toString());
                    }
                }
            }
            int i7 = i;
            if (i5 == i7) {
                break;
            }
            str10 = str5;
            i4 = -1;
            str9 = str2;
            i3 = i7;
            broadcastHistory = this;
        }
        if (str == null) {
            java.lang.String str12 = str4;
            int i8 = this.mSummaryHistoryNext;
            if (z) {
                i2 = i8;
                i6 = -1;
                z4 = false;
            } else {
                i2 = i8;
                int i9 = i6;
                while (i9 > 0 && i2 != i8) {
                    boolean z5 = z3;
                    i2 = ringAdvance(i2, -1, this.MAX_BROADCAST_SUMMARY_HISTORY);
                    if (this.mBroadcastHistory[i2] == null) {
                        z3 = z5;
                    } else {
                        i9--;
                        z3 = z5;
                    }
                }
                z3 = z3;
            }
            while (true) {
                i2 = ringAdvance(i2, -1, this.MAX_BROADCAST_SUMMARY_HISTORY);
                android.content.Intent intent = this.mBroadcastSummaryHistory[i2];
                if (intent == null) {
                    str6 = str12;
                    str7 = str11;
                    str8 = str3;
                } else {
                    if (!z4) {
                        if (z3) {
                            printWriter.println();
                        }
                        printWriter.println("  Historical broadcasts summary [" + str9 + str12);
                        z3 = true;
                        z4 = true;
                    }
                    if (!z && i6 >= 50) {
                        printWriter.println("  ...");
                        break;
                    }
                    i6++;
                    printWriter.print("  #");
                    printWriter.print(i6);
                    printWriter.print(str3);
                    str6 = str12;
                    printWriter.println(intent.toShortString(false, true, true, false));
                    printWriter.print(str11);
                    str7 = str11;
                    str8 = str3;
                    android.util.TimeUtils.formatDuration(this.mSummaryHistoryDispatchTime[i2] - this.mSummaryHistoryEnqueueTime[i2], printWriter);
                    printWriter.print(" dispatch ");
                    android.util.TimeUtils.formatDuration(this.mSummaryHistoryFinishTime[i2] - this.mSummaryHistoryDispatchTime[i2], printWriter);
                    printWriter.println(" finish");
                    printWriter.print("    enq=");
                    printWriter.print(simpleDateFormat.format(new java.util.Date(this.mSummaryHistoryEnqueueTime[i2])));
                    printWriter.print(" disp=");
                    printWriter.print(simpleDateFormat.format(new java.util.Date(this.mSummaryHistoryDispatchTime[i2])));
                    printWriter.print(" fin=");
                    printWriter.println(simpleDateFormat.format(new java.util.Date(this.mSummaryHistoryFinishTime[i2])));
                    android.os.Bundle extras2 = intent.getExtras();
                    if (extras2 != null) {
                        printWriter.print("    extras: ");
                        printWriter.println(extras2.toString());
                    }
                }
                if (i2 == i8) {
                    break;
                }
                str11 = str7;
                str12 = str6;
                str3 = str8;
                str9 = str2;
            }
        }
        return z3;
    }
}
