package com.example.narutolauncher;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView;
    private RelativeLayout hudOverlay;
    private boolean areControlsVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWebView = findViewById(R.id.myWebView);
        hudOverlay = findViewById(R.id.hudOverlay);

        // Configuración de rendimiento y WebView
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        
        // Optimización gráfica para celulares básicos
        myWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        myWebView.setWebViewClient(new WebViewClient());

        // Enlace del juego Naruto Online (puedes cambiarlo si usas otra URL base)
        myWebView.loadUrl("https://naruto.narutowebgame.com");

        // Botón Cuenta 1
        Button btnAccount1 = findViewById(R.id.btnAccount1);
        btnAccount1.setOnClickListener(v -> myWebView.loadUrl("https://naruto.narutowebgame.com"));

        // Botón Cuenta 2
        Button btnAccount2 = findViewById(R.id.btnAccount2);
        btnAccount2.setOnClickListener(v -> myWebView.loadUrl("https://naruto.narutowebgame.com"));

        // Botón para Ocultar/Mostrar Controles (Gamepad)
        Button btnToggleControls = findViewById(R.id.btnToggleControls);
        btnToggleControls.setOnClickListener(v -> {
            if (areControlsVisible) {
                hudOverlay.setVisibility(View.GONE);
                areControlsVisible = false;
                btnToggleControls.setText("👁️ Ver");
            } else {
                hudOverlay.setVisibility(View.VISIBLE);
                areControlsVisible = true;
                btnToggleControls.setText("🎮 Gamepad");
            }
        });

        // Mapeo táctil de los botones del HUD hacia eventos de teclado virtuales
        setupKeyButton(R.id.btnW, KeyEvent.KEYCODE_W);
        setupKeyButton(R.id.btnA, KeyEvent.KEYCODE_A);
        setupKeyButton(R.id.btnS, KeyEvent.KEYCODE_S);
        setupKeyButton(R.id.btnD, KeyEvent.KEYCODE_D);
        setupKeyButton(R.id.btnQ, KeyEvent.KEYCODE_Q);
        setupKeyButton(R.id.btnEsc, KeyEvent.KEYCODE_ESCAPE);
    }

    // Función auxiliar para enviar las teclas al WebView al tocar la pantalla
    private void setupKeyButton(int buttonId, int keyCode) {
        Button button = findViewById(buttonId);
        if (button != null) {
            button.setOnClickListener(v -> {
                if (myWebView != null) {
                    long downTime = System.currentTimeMillis();
                    KeyEvent downEvent = new KeyEvent(downTime, downTime, KeyEvent.ACTION_DOWN, keyCode, 0);
                    KeyEvent upEvent = new KeyEvent(downTime, downTime + 50, KeyEvent.ACTION_UP, keyCode, 0);
                    myWebView.dispatchKeyEvent(downEvent);
                    myWebView.dispatchKeyEvent(upEvent);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
