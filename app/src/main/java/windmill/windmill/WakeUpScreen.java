package windmill.windmill;

import android.content.Context;

import android.os.PowerManager;


public class WakeUpScreen {


    private static PowerManager.WakeLock wakeLock;
    public static void acquire(Context context, long timeout) {


        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        wakeLock = pm.newWakeLock(

                PowerManager.ACQUIRE_CAUSES_WAKEUP |

                        PowerManager.FULL_WAKE_LOCK |

                        PowerManager.ON_AFTER_RELEASE

                , context.getClass().getName());


        if (timeout > 0)

            wakeLock.acquire(timeout);

        else

            wakeLock.acquire();


    }

    public static void acquire(Context context) {

        acquire(context, 0);

    }


    public static void release() {

        if (wakeLock.isHeld())

            wakeLock.release();

    }

}
