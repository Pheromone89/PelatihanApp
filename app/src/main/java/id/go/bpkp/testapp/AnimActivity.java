package id.go.bpkp.testapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class AnimActivity extends AppCompatActivity {

    private LinearLayout red, green, blue;
    private Button toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

        red = findViewById(R.id.red);
        green = findViewById(R.id.green);
        blue = findViewById(R.id.blue);
        toggle = findViewById(R.id.button_toggle);

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (green.isShown()){
                    green.setVisibility(View.GONE);
                } else {
                    green.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
