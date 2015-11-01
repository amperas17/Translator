package vavan.com.rus_eng_translator;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 *form request and send it to Yandex in AsyncTask thread, then it get response, show it in textview
 * and add to history database.
 */
public class TranslateFragment extends Fragment {


    static final String API_KEY = "trnsl.1.1.20151008T152506Z.60a06849d2e0dee1.7ef601ab547048e195ab433d3fe1ab7612f5dda7";
    static final String YA_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=";

    static final String COPYRIGHT_COMMENT = "Переведено сервисом «Яндекс.Переводчик» http://translate.yandex.ru/";

    static final String[] LANG_ARRAY = {"sq","en","ar","hy","az","af","eu","be","bg","bs","cy","vi","hu","gl","nl",
            "el","ka","da","he","ga","it","is","es","kk","ca","ky","zh","ko","la","lv",
            "lt","mg","ms","mt","mk","mn","de","no","fa","pl","pt","ro","ru","sr","sk",
            "sl","sw","tg","th","tl","tt","tr","uz","uk","fi","fr","hr","cs","sv","et","ja"};

    static final String[] LANG_SHORT_ARRAY = {"en","ar","el","it","es","zh","ko","de","no","fa",
            "pl","pt","uk","ru","fr","sv","ja"};

    static final String[] LANG_SHORT_ARRAY_FULL = {"english","arabian","hellenic","italian","spanish",
            "chinese","korean","german","norwegian","persian",
            "polish","portuguese","ukrainian","russian","french",
            "swedish","japanese"};

    static final int START_LANG_FROM = 13;
    static final int START_LANG_TO = 0;


    EditText etInput;
    Button btTranslate;
    TextView tvOutput,tvChangeLangs;
    Spinner spLangFrom, spLangTo;

    String strLangFrom = "ru";
    String strLangTo = "en";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translate, container, false);

        etInput = (EditText)view.findViewById(R.id.etInput);
        btTranslate = (Button)view.findViewById(R.id.btTranslate);
        tvOutput = (TextView)view.findViewById(R.id.tvOutput);

        btTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BKTask task = new BKTask();
                task.execute(new String[]{String.valueOf(etInput.getText())});

                //To hide the keyboard after typing
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etInput.getWindowToken(), 0);
            }
        });


        ArrayAdapter<String> adapterSpinnerTo = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, LANG_SHORT_ARRAY_FULL);
        adapterSpinnerTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spLangTo = (Spinner)view.findViewById(R.id.spLangTo);
        spLangTo.setAdapter(adapterSpinnerTo);
        spLangTo.setSelection(START_LANG_TO);
        spLangTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strLangTo = LANG_SHORT_ARRAY[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapterSpinnerFrom = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, LANG_SHORT_ARRAY_FULL);
        adapterSpinnerFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spLangFrom = (Spinner)view.findViewById(R.id.spLangFrom);
        spLangFrom.setAdapter(adapterSpinnerFrom);
        spLangFrom.setSelection(START_LANG_FROM);
        spLangFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strLangFrom = LANG_SHORT_ARRAY[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tvChangeLangs = (TextView)view.findViewById(R.id.tvChangeLangs);
        tvChangeLangs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int temp = spLangFrom.getSelectedItemPosition();
                spLangFrom.setSelection(spLangTo.getSelectedItemPosition());
                spLangTo.setSelection(temp);
            }
        });

        return view;
    }


    private class BKTask extends AsyncTask<String,Void,String> {

        /*Work with yandex.translate api through the network */

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

            /*Work with yandex.translate api through the network */
            String translated;

            URL urlObj = new URL(YA_URL + API_KEY);
            HttpsURLConnection connection = (HttpsURLConnection)urlObj.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeBytes("&text=" + URLEncoder.encode(text, "UTF-8") + "&lang=" + strLangFrom + '-' + strLangTo);

            InputStream response = connection.getInputStream();
            String jsonString = new Scanner(response).nextLine();

            int start = jsonString.indexOf("[");
            int end = jsonString.indexOf("]");
            translated = jsonString.substring(start + 2, end - 1);

            connection.disconnect();

            return translated;
        }

        @Override
        protected void onPostExecute(String output) {

            tvOutput.setText(output);

            DBHelper dbHelper = new DBHelper(getActivity());
            dbHelper.addRecord(etInput.getText().toString(),output);

        }

        @Override
        protected void onPreExecute() {
            tvOutput.setText("Идёт подключение к интернету...");

        }
    }

}
