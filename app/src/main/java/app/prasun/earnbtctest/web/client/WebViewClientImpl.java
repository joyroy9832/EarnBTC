package app.prasun.earnbtctest.web.client;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import app.prasun.earnbtctest.MainActivity;
import app.prasun.earnbtctest.R;

public class WebViewClientImpl extends WebViewClient {
    private Activity activity = null;
    private StateHandler stateHandler = StateHandler.getStateInstance();
    private MainActivity mainActivity = new MainActivity();

    public WebViewClientImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        if(!url.contains(activity.getResources().getString(R.string.main_url_to_load))) return false;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(intent);
        return true;
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }

    @Override
    public void onPageFinished(final WebView view, String url) {
//        view.loadUrl("javascript:(function(){"+
//                "minuteData=document.getElementsByClassName('countdown_amount')[0].innerHTML;"+
//                "secondData=document.getElementsByClassName('countdown_amount')[1].innerHTML;"
//        );
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // In KitKat+ you should use the evaluateJavascript method
            view.evaluateJavascript("(function() { var minuteData=document.getElementsByClassName('countdown_amount')[0].innerHTML; var secondData=document.getElementsByClassName('countdown_amount')[1].innerHTML; return (minuteData + ',' + secondData); })();", new ValueCallback<String>() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void onReceiveValue(String s) {
                    String toBeReplaceString = "\"";
                    s = !s.equals("null") ? s.replaceAll(toBeReplaceString, "") : "0,0";
                    String[] timerData = s.split(",");
                    if(timerData == null && timerData.length > 1) {
                        timerData[0] = "0";
                        timerData[1] = "0";
                    }
                    int minuteInMili = Integer.parseInt(timerData[0]) * 60 * 1000; // 60 for minute and 1000 for mili
                    int secondInMili = Integer.parseInt(timerData[1]) * 1000;
                    if(minuteInMili <= 0 && secondInMili <= 0) {
                        view.loadUrl("javascript:(function(){"+
                                "l=document.getElementById('free_play_form_button');"+
                                "e=document.createEvent('HTMLEvents');"+
                                "e.initEvent('click',true,true);"+
                                "l.dispatchEvent(e);"+
                                "})()");
                        stateHandler.actualTimerData = 3610000;
                    } else {
                        stateHandler.actualTimerData = minuteInMili + secondInMili + 100;
                        stateHandler.actualTimerData = stateHandler.actualTimerData > 0 ? stateHandler.actualTimerData : 3610000;
                    }
                    mainActivity.reloadWebView(stateHandler.actualTimerData);
                }
            });
        }
        // do your stuff here

    }
}
