package com.petron.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.startapp.android.publish.ads.nativead.NativeAdDetails;
import com.startapp.android.publish.ads.nativead.NativeAdPreferences;
import com.startapp.android.publish.ads.nativead.StartAppNativeAd;
import com.startapp.android.publish.ads.splash.SplashConfig;
import com.startapp.android.publish.ads.splash.SplashConfig.Theme;
import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppAd.AdMode;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.startapp.android.publish.adsCommon.VideoListener;
import com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;

public class ScrollingActivity extends AppCompatActivity {

    String Presente,Vida,Estado,Enchufe;
    TextView tV_bat_presente,tV_bat_estado,tV_bat_vida,tV_bat_nivel,tV_bat_alimentacion,tV_bat_tecnologia,
             tv_bat_temperatura,tv_bat_voltage;
    private StartAppAd startAppAd = new StartAppAd(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        StartAppSDK.init(this, "200330184", true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tV_bat_presente = (TextView) findViewById(R.id.textView_resultado_bateria_presente);
        tV_bat_estado = (TextView) findViewById(R.id.textView_resultado_bateria_estado);
        tV_bat_vida =(TextView)findViewById(R.id.textView_resultado_bateria_vida);
        tV_bat_nivel = (TextView) findViewById(R.id.textView_resultado_bateria_nivel);
        tV_bat_alimentacion = (TextView)findViewById(R.id.textView_resultado_bateria_alimentacion);
        tV_bat_tecnologia = (TextView)findViewById(R.id.textView_resultado_bateria_tecnologia);
        tv_bat_temperatura = (TextView)findViewById(R.id.textView_resultado_bateria_temperatura);
        tv_bat_voltage = (TextView)findViewById(R.id.textView_resultado_bateria_voltage);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        this.registerReceiver(this.batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }




    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //bateria presente o no
            boolean presente = intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT);
            if (presente= false){
                Presente = getString(R.string.no_presente);
            }else {
                Presente = getString(R.string.presente);
            }
            tV_bat_presente.setText(Presente);
            //Acaba lo de bateria presente
            ///Vida de la batería
            int vida = intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
            if (vida==1){
                Vida= getString(R.string.vida_desconocida);
            }else if(vida==2){
                Vida=getString(R.string.vida_buena);
            }else if (vida==3){
                Vida=getString(R.string.vida_calentamiento);
            } else if (vida == 4) {
                Vida=getString(R.string.vida_muerta);
            } else if (vida==5){
                Vida=getString(R.string.vida_sobrevolataje);
            }else if (vida==6){
                Vida=getString(R.string.vida_fallo);
            } else if (vida==7){
                Vida=getString(R.string.vida_calentada);
            }else{
                Vida=getString(R.string.vida_nosaber);
            }
            tV_bat_vida.setText(Vida);
            //Acaba la vida de la bateria
            //Estado de la bateria
            int estado = intent.getIntExtra(BatteryManager.EXTRA_STATUS,0);
            if (estado==1){
                Estado=getString(R.string.estado_bat1_desconocido);
            }else if (estado==2){
                Estado=getString(R.string.estado_bat2_cargando);
            }else if (estado==3){
                Estado=getString(R.string.estado_bat3_descargando);
            }else if (estado==4){
                Estado=getString(R.string.estado_bat4_nocarga);
            }else if (estado==5){
                Estado=getString(R.string.estado_bat5_llena);
            }else {
                Estado=getString(R.string.error);
            }
            tV_bat_estado.setText(Estado);
            //Acaba estado de bateria
            //Como esta enchufada la bateria
            int enchufe = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);
            if (enchufe==0){
                Enchufe=getString(R.string.enchufe_no);
            }else if (enchufe==1){
                Enchufe=getString(R.string.enchufe_ac);
            }else if (enchufe==2){
                Enchufe=getString(R.string.enchfe_usb);
            }else if (enchufe==4){
                Enchufe=getString(R.string.enchfe_wireless);
            }else{
                Enchufe=getString(R.string.enchufe_desconocido);
            }
            tV_bat_alimentacion.setText(Enchufe);
            //Acaba como esta enchfada la bateria
            //Nivel de la bateria
            int nivel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            tV_bat_nivel.setText(""+nivel+"%");
            //Barra de progreso para el nivel de la bateria
            ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
            progressBar.setProgress(nivel);
            //Acaba el nivel de la bateria
            //Tecnologia de la bateria
            String  technologia= intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
            tV_bat_tecnologia.setText(technologia);
            //Acaba tecnologia de la bateria
            //Empieza temperatura de la bateria
            int  temperatura= intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
            float temperatura_redondeada = ((float) temperatura) / 10;
            double tem_re_f = temperatura_redondeada*(1.8+32)/10;
            String grafaren = String.format("%.1f",tem_re_f);
            tv_bat_temperatura.setText(""+temperatura_redondeada+" ºC"+ "        "+ grafaren+ " ºF");
            //Acaba temperatura de la bateria
            //Empieza el voltaje de la bateria
            int  voltaje= intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);
            double voltaje1 = voltaje*0.001;
            String volt = String.format("%.1f",voltaje1);
            tv_bat_voltage.setText(""+volt+" "+ getString(R.string.milivoltios));
            //Acaba el voltaje de la bateria
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            ScrollingActivity.this.startAppAd.showAd();
            ScrollingActivity.this.startAppAd.loadAd();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {

        super.onResume();
        startAppAd.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //poner en marcha la publicidad al salir
        ScrollingActivity.this.startAppAd.showAd();
        ScrollingActivity.this.startAppAd.loadAd();

    }

    @Override
    public void onPause() {
        super.onPause();
        startAppAd.onPause();
    }
}
