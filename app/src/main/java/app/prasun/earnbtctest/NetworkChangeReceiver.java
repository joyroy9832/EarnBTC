package app.prasun.earnbtctest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        int type = NetworkUtil.getConnectivityStatusType(context);
        if (type == NetworkUtil.TYPE_WIFI) {
            Toast.makeText(context, context.getResources().getString(R.string.connected_with_wifi), Toast.LENGTH_LONG).show();
        } else if (type == NetworkUtil.TYPE_MOBILE) {
            Toast.makeText(context, context.getResources().getString(R.string.connected_with_mobile), Toast.LENGTH_LONG).show();
        } else if (type == NetworkUtil.TYPE_NOT_CONNECTED) {
            Toast.makeText(context, context.getResources().getString(R.string.no_connection), Toast.LENGTH_LONG).show();
        }
    }
}