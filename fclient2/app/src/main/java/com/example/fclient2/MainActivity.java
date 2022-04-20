package com.example.fclient2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
<<<<<<< Updated upstream
import android.widget.TextView;

import com.example.fclient2.databinding.ActivityMainBinding;

import java.util.Arrays;
import java.util.Random;
public class MainActivity extends AppCompatActivity {
    // Used to load the 'native-lib' library on application startup
        static {
            System.loadLibrary("fclient2");
            System.loadLibrary("mbedcrypto");
        }
=======
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.app.Activity;
import android.content.Intent;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class MainActivity extends AppCompatActivity implements TransactionActivity {

    ActivityResultLauncher<Intent> activityResultLauncher;

>>>>>>> Stashed changes
    static {
        System.loadLibrary("fclient2");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< Updated upstream
=======
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        pin = data.getStringExtra("pin");
                        synchronized (MainActivity.this) {
                            MainActivity.this.notifyAll();
                        }
                    }
                });

>>>>>>> Stashed changes
        int res = initRng();
        byte[] rnd = randomBytes(16);

        Random random = new Random();

        byte[] data = new byte[16];
        for (int i = 0; i < data.length; ++i) {
            data[i] = (byte) ((byte) random.nextInt() % 255);
        }
        Log.i("data", Arrays.toString(data));
        byte[] encrypt_data = encrypt(rnd, data);

<<<<<<< Updated upstream
        byte[] decrypt_data = decrypt(rnd, encrypt_data);

        Log.i("enc_data", Arrays.toString(encrypt_data));
        Log.i("dec_data", Arrays.toString(decrypt_data));

        TextView tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
=======
    public void onButtonClick(View view) {
//        new Thread(() -> {
//            try {
//                byte[] trd = stringToHex("9F0206000000000100");
//                transaction(trd);
//            } catch (Exception exception) {
//                Log.println(Log.ERROR, "MtLog", Arrays.toString(exception.getStackTrace()));
//            }
//        }).start();
        byte[] trd = stringToHex("9F0206000000000100");
        transaction(trd);
    }

    private String pin;

    @Override
    public String enterPin(int ptc, String amount) {
        pin = new String();

        Intent intent = new Intent(MainActivity.this, PinpadActivity.class);
        intent.putExtra("ptc", ptc);
        intent.putExtra("amount", amount);

        synchronized (MainActivity.this) {
            activityResultLauncher.launch(intent);
            try {
                MainActivity.this.wait();
            } catch (Exception exception) {
                Log.println(Log.ERROR, "MtLog", exception.getMessage());
            }
        }

        return pin;
    }
    @Override
    public void transactionResult(boolean result) {
        runOnUiThread(() -> {
            Toast.makeText(MainActivity.this, result ? "ok" : "failed", Toast.LENGTH_SHORT).show();
        });
    }


    public static byte[] stringToHex(String s) {
        byte[] hex;
        try {
            hex = Hex.decodeHex(s.toCharArray());
        } catch (DecoderException ex) {
            hex = null;
        }
        return hex;
>>>>>>> Stashed changes
    }

    public native String stringFromJNI();
    public static native int initRng();

    public static native byte[] randomBytes(int no);

    public static native byte[] encrypt(byte[] key, byte[] data);

    public static native byte[] decrypt(byte[] key, byte[] data);
<<<<<<< Updated upstream
}
=======

    public native boolean transaction(byte[] trd);

}
>>>>>>> Stashed changes
