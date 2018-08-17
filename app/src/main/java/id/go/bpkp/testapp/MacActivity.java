package id.go.bpkp.testapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MacActivity extends AppCompatActivity {

    boolean coarseLoc, fineLoc;
    float userLat, userLong;
    String macAdress;
    TextView macAdressView, latView, longView;
    Button refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mac);

        macAdressView = findViewById(R.id.macval);
        latView = findViewById(R.id.latval);
        longView = findViewById(R.id.longval);
        refresh = findViewById(R.id.button_refresh);
        
        macAdressView.setText(getMac());
        getLocation();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                macAdressView.setText(getMac());
                getLocation();
            }
        });
    }

    private void getLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        } else {
            coarseLoc = true;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        } else {
            fineLoc = true;
        }
        if (coarseLoc && fineLoc) {
            SingleShotLocationProvider.requestSingleUpdate(this,
                    new SingleShotLocationProvider.LocationCallback() {
                        @Override
                        public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                            userLat = location.latitude;
                            userLong = location.longitude;
                        }
                    });

        } else {
            userLat = 0f;
            userLong = 0f;
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PackageManager.PERMISSION_GRANTED);
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PackageManager.PERMISSION_GRANTED);
            Toast.makeText(this, "tolong izinkan App untuk mengakses GPS anda", Toast.LENGTH_SHORT).show();
        }
        latView.setText(Float.toString(userLat));
        longView.setText(Float.toString(userLong));
    }

    public String getMac() {
        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String mac = wifiInfo.getBSSID();
        if (mac != null) {
            return mac;
        } else {
            return "-";
        }
    }
}
