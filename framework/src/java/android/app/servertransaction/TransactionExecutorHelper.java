package android.app.servertransaction;

/* loaded from: classes.dex */
public class TransactionExecutorHelper {
    private static final int DESTRUCTION_PENALTY = 10;
    private android.util.IntArray mLifecycleSequence = new android.util.IntArray(6);
    private static final java.lang.String TAG = android.app.servertransaction.TransactionExecutorHelper.class.getSimpleName();
    private static final int[] ON_RESUME_PRE_EXCUTION_STATES = {2, 4};

    public android.util.IntArray getLifecyclePath(int i, int i2, boolean z) {
        if (i == -1 || i2 == -1) {
            throw new java.lang.IllegalArgumentException("Can't resolve lifecycle path for undefined state");
        }
        if (i == 7 || i2 == 7) {
            throw new java.lang.IllegalArgumentException("Can't start or finish in intermittent RESTART state");
        }
        if (i2 == 0 && i != i2) {
            throw new java.lang.IllegalArgumentException("Can only start in pre-onCreate state");
        }
        this.mLifecycleSequence.clear();
        if (i2 >= i) {
            if (i == 2 && i2 == 5) {
                this.mLifecycleSequence.add(5);
            } else {
                for (int i3 = i + 1; i3 <= i2; i3++) {
                    this.mLifecycleSequence.add(i3);
                }
            }
        } else if (i == 4 && i2 == 3) {
            this.mLifecycleSequence.add(3);
        } else if (i <= 5 && i2 >= 2) {
            for (int i4 = i + 1; i4 <= 5; i4++) {
                this.mLifecycleSequence.add(i4);
            }
            this.mLifecycleSequence.add(7);
            for (int i5 = 2; i5 <= i2; i5++) {
                this.mLifecycleSequence.add(i5);
            }
        } else {
            for (int i6 = i + 1; i6 <= 6; i6++) {
                this.mLifecycleSequence.add(i6);
            }
            for (int i7 = 1; i7 <= i2; i7++) {
                this.mLifecycleSequence.add(i7);
            }
        }
        if (z && this.mLifecycleSequence.size() != 0) {
            this.mLifecycleSequence.remove(this.mLifecycleSequence.size() - 1);
        }
        return this.mLifecycleSequence;
    }

    public int getClosestPreExecutionState(android.app.ActivityThread.ActivityClientRecord activityClientRecord, int i) {
        switch (i) {
            case -1:
                return -1;
            case 3:
                return getClosestOfStates(activityClientRecord, ON_RESUME_PRE_EXCUTION_STATES);
            default:
                throw new java.lang.UnsupportedOperationException("Pre-execution states for state: " + i + " is not supported.");
        }
    }

    public int getClosestOfStates(android.app.ActivityThread.ActivityClientRecord activityClientRecord, int[] iArr) {
        int i = -1;
        if (iArr == null || iArr.length == 0) {
            return -1;
        }
        if (activityClientRecord == null) {
            android.util.Log.w(TAG, "ActivityClientRecord was null");
            return -1;
        }
        int lifecycleState = activityClientRecord.getLifecycleState();
        int i2 = Integer.MAX_VALUE;
        for (int i3 = 0; i3 < iArr.length; i3++) {
            getLifecyclePath(lifecycleState, iArr[i3], false);
            int size = this.mLifecycleSequence.size();
            if (pathInvolvesDestruction(this.mLifecycleSequence)) {
                size += 10;
            }
            if (i2 > size) {
                i = iArr[i3];
                i2 = size;
            }
        }
        return i;
    }

    public static android.app.servertransaction.ActivityLifecycleItem getLifecycleRequestForCurrentState(android.app.ActivityThread.ActivityClientRecord activityClientRecord) {
        switch (activityClientRecord.getLifecycleState()) {
            case 2:
            case 4:
                return android.app.servertransaction.PauseActivityItem.obtain(activityClientRecord.token);
            case 3:
            default:
                return android.app.servertransaction.ResumeActivityItem.obtain(activityClientRecord.token, false, false);
            case 5:
                return android.app.servertransaction.StopActivityItem.obtain(activityClientRecord.token, 0);
        }
    }

    private static boolean pathInvolvesDestruction(android.util.IntArray intArray) {
        int size = intArray.size();
        for (int i = 0; i < size; i++) {
            if (intArray.get(i) == 6) {
                return true;
            }
        }
        return false;
    }

    @java.lang.Deprecated
    static int lastCallbackRequestingState(android.app.servertransaction.ClientTransaction clientTransaction) {
        java.util.List<android.app.servertransaction.ClientTransactionItem> callbacks = clientTransaction.getCallbacks();
        if (callbacks == null || callbacks.isEmpty() || clientTransaction.getLifecycleStateRequest() == null) {
            return -1;
        }
        return lastCallbackRequestingStateIndex(callbacks, 0, callbacks.size() - 1, clientTransaction.getLifecycleStateRequest().getActivityToken());
    }

    private static int lastCallbackRequestingStateIndex(java.util.List<android.app.servertransaction.ClientTransactionItem> list, int i, int i2, android.os.IBinder iBinder) {
        int i3 = -1;
        int i4 = -1;
        while (i2 >= i) {
            android.app.servertransaction.ClientTransactionItem clientTransactionItem = list.get(i2);
            int postExecutionState = clientTransactionItem.getPostExecutionState();
            if (postExecutionState != -1 && iBinder.equals(clientTransactionItem.getActivityToken())) {
                if (i3 != -1 && i3 != postExecutionState) {
                    break;
                }
                i4 = i2;
                i3 = postExecutionState;
            }
            i2--;
        }
        return i4;
    }

    static boolean shouldExcludeLastLifecycleState(java.util.List<android.app.servertransaction.ClientTransactionItem> list, int i) {
        int findNextLifecycleItemIndex;
        android.app.servertransaction.ClientTransactionItem clientTransactionItem = list.get(i);
        android.os.IBinder activityToken = clientTransactionItem.getActivityToken();
        int postExecutionState = clientTransactionItem.getPostExecutionState();
        if (activityToken == null || postExecutionState == -1 || (findNextLifecycleItemIndex = findNextLifecycleItemIndex(list, i + 1, activityToken)) == -1 || postExecutionState != ((android.app.servertransaction.ActivityLifecycleItem) list.get(findNextLifecycleItemIndex)).getTargetState() || i != lastCallbackRequestingStateIndex(list, i, findNextLifecycleItemIndex - 1, activityToken)) {
            return false;
        }
        return true;
    }

    private static int findNextLifecycleItemIndex(java.util.List<android.app.servertransaction.ClientTransactionItem> list, int i, android.os.IBinder iBinder) {
        int size = list.size();
        while (i < size) {
            android.app.servertransaction.ClientTransactionItem clientTransactionItem = list.get(i);
            if (!clientTransactionItem.isActivityLifecycleItem() || !clientTransactionItem.getActivityToken().equals(iBinder)) {
                i++;
            } else {
                return i;
            }
        }
        return -1;
    }

    static java.lang.String transactionToString(android.app.servertransaction.ClientTransaction clientTransaction, android.app.ClientTransactionHandler clientTransactionHandler) {
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        clientTransaction.dump(tId(clientTransaction), new java.io.PrintWriter(stringWriter), clientTransactionHandler);
        return stringWriter.toString();
    }

    static java.lang.String tId(android.app.servertransaction.ClientTransaction clientTransaction) {
        return "tId:" + clientTransaction.hashCode() + " ";
    }

    static java.lang.String getActivityName(android.os.IBinder iBinder, android.app.ClientTransactionHandler clientTransactionHandler) {
        android.app.Activity activityForToken = getActivityForToken(iBinder, clientTransactionHandler);
        if (activityForToken != null) {
            return activityForToken.getComponentName().getClassName();
        }
        return "Not found for token: " + iBinder;
    }

    static java.lang.String getShortActivityName(android.os.IBinder iBinder, android.app.ClientTransactionHandler clientTransactionHandler) {
        android.app.Activity activityForToken = getActivityForToken(iBinder, clientTransactionHandler);
        if (activityForToken != null) {
            return activityForToken.getComponentName().getShortClassName();
        }
        return "Not found for token: " + iBinder;
    }

    private static android.app.Activity getActivityForToken(android.os.IBinder iBinder, android.app.ClientTransactionHandler clientTransactionHandler) {
        if (iBinder == null) {
            return null;
        }
        return clientTransactionHandler.getActivity(iBinder);
    }

    static java.lang.String getStateName(int i) {
        switch (i) {
            case -1:
                return android.app.admin.DevicePolicyResources.UNDEFINED;
            case 0:
                return "PRE_ON_CREATE";
            case 1:
                return "ON_CREATE";
            case 2:
                return "ON_START";
            case 3:
                return "ON_RESUME";
            case 4:
                return "ON_PAUSE";
            case 5:
                return "ON_STOP";
            case 6:
                return "ON_DESTROY";
            case 7:
                return "ON_RESTART";
            default:
                throw new java.lang.IllegalArgumentException("Unexpected lifecycle state: " + i);
        }
    }
}
