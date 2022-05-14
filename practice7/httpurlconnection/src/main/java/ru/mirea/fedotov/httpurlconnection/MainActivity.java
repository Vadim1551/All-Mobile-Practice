package ru.mirea.fedotov.httpurlconnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView ip;
    private TextView country;
    private TextView region;
    private TextView city;

    private final String url = "http://whatismyip.akamai.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ip = (TextView) findViewById(R.id.resultTextView);
        country = (TextView) findViewById(R.id.resultTextView4);
        region = (TextView) findViewById(R.id.resultTextView2);
        city = (TextView) findViewById(R.id.resultTextView3);
    }


    public void onClick(View view){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = null;
        if (connectivityManager != null) {
            networkinfo = connectivityManager.getActiveNetworkInfo();
        }
        if (networkinfo != null && networkinfo.isConnected()) {
            new DownloadPageTask().execute(url);
        } else {
            Toast.makeText(this, "Error",
                    Toast.LENGTH_SHORT).show();
        }
    }


    private class DownloadPageTask extends AsyncTask<String, Void, IPinfo>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ip.setText("Загрузка...");
            country.setText("Загрузка...");
            region.setText("Загрузка...");
            city.setText("Загрузка...");
        }

        @Override
        protected IPinfo doInBackground(String... strings) {
            try {
                String ip = downloadIpInfo(strings[0]);
                return getInformationByIp(ip);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(IPinfo info) {
            ip.setText(info.getIp());
            country.setText(info.getCountry());
            region.setText(info.getRegion());
            city.setText(info.getCity());
            super.onPostExecute(info);
        }

        private IPinfo getInformationByIp(String ip){
            try {
                String content = getContentFromApi("http://ip-api.com/json/" + ip, "GET");
                JSONObject responseJson = new JSONObject(content);
                String country = String.valueOf(responseJson.get("country"));
                String region = String.valueOf(responseJson.get("regionName"));
                String city = String.valueOf(responseJson.get("city"));

                return new IPinfo(ip, city, country, region);

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        private String getContentFromApi(String address, String method) throws IOException {
            InputStream inputStream = null;
            String data = "";
            try {
                URL url = new URL(address);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setReadTimeout(100000);
                connection.setConnectTimeout(100000);
                connection.setRequestMethod(method);
                connection.setInstanceFollowRedirects(true);
                connection.setUseCaches(false);
                connection.setDoInput(true);
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    inputStream = connection.getInputStream();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    int read;
                    while ((read = inputStream.read()) != -1) {
                        bos.write(read);
                    }
                    bos.close();
                    data = bos.toString();
                } else {
                    data = connection.getResponseMessage() + " . Error Code : " + responseCode;
                }
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return data;
        }

        private String downloadIpInfo(String address) throws IOException {
            return getContentFromApi(address, "GET");
        }
    }
}
