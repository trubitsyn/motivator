package io.github.trubitsyn.motivator.notification;

public class TimeManager {

    public static boolean shouldIgnoreNow(int ignoreFromHour, int ignoreToHour, int currentHour) {
        if (ignoreFromHour < ignoreToHour) {
            if (currentHour >= ignoreFromHour && currentHour < ignoreToHour) {
                return true;
            }
        } else if (ignoreFromHour > ignoreToHour) {
            if ((currentHour >= ignoreFromHour && currentHour <= 23) || (currentHour < ignoreFromHour && currentHour < ignoreToHour)) {
                return true;
            }
        }
        return false;
    }
}
