package app.prasun.earnbtctest;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import app.prasun.earnbtctest.web.client.WebViewClientImpl;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private WebView webView = null;
    private LinearLayout noConnection = null;
    private Button retryBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        super.onCreate(savedInstanceState);
        webView = findViewById(R.id.webViewMain);
        retryBtn = findViewById(R.id.retryButton);
        noConnection = findViewById(R.id.noConnection);
        tryToConnect();
        retryBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                tryToConnect();
            }
        });

    }

    private void tryToConnect() {
        if(NetworkUtil.getConnectivityStatusType(this) == NetworkUtil.TYPE_NOT_CONNECTED) {
            noConnection.setVisibility(View.VISIBLE);
            webView.setVisibility(View.INVISIBLE);
            Toast.makeText(MainActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
        } else {
            webView.setVisibility(View.VISIBLE);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            WebViewClientImpl webViewClient = new WebViewClientImpl(this);
            webView.setWebViewClient(webViewClient);
            webView.loadUrl("https://freebitco.in?r=3602124");
        }
    }

    public void reloadWebView(int timer) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                Toast.makeText(MainActivity.this, R.string.toast_message_for_btc, Toast.LENGTH_SHORT).show();
                webView.reload();
                reloadWebView(3600010);

            }
        }, timer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        MenuItem item = menu.findItem(R.id.myswitch);
        item.setActionView(R.layout.switch_layout);
        return true;
    }
}
