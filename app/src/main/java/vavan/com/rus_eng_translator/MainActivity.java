package vavan.com.rus_eng_translator;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {

    static final String apiKey = "trnsl.1.1.20151008T152506Z.60a06849d2e0dee1.7ef601ab547048e195ab433d3fe1ab7612f5dda7";
    static final String yaUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=";

    EditText etInput;
    Button btTranslate;
    TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInput = (EditText)findViewById(R.id.etInput);
        btTranslate = (Button)findViewById(R.id.btTranslate);
        tvOutput = (TextView)findViewById(R.id.tvOutput);

        btTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BKTask task = new BKTask();
                task.execute(new String[]{String.valueOf(etInput.getText())});
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etInput.getWindowToken(), 0);

            }
        });
    }


    private class BKTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... translatingTextArray) {
            String output = null;
            for (String text : translatingTextArray) {
                try {
                    output = getOutputFromUrl(text);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return output;
        }

        private String getOutputFromUrl(String text) throws IOException {

            String output;

            URL urlObj = new URL(yaUrl+apiKey);
            HttpsURLConnection connection = (HttpsURLConnection)urlObj.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeBytes("&text=" + URLEncoder.encode(text,"UTF-8") +"&lang=ru-en");

            InputStream response = connection.getInputStream();
            output = new Scanner(response).nextLine();

            return output;
        }


        @Override
        protected void onPostExecute(String output) {

            tvOutput.setText(output);
        }
    }

}
