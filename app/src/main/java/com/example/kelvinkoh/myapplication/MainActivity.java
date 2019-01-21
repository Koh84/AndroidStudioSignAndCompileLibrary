package com.example.kelvinkoh.myapplication;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.condeco.ethernet.EthernetDevInfo;
import com.condeco.ethernet.EthernetManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.NetworkInterface;

import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.List;
import java.util.Properties;


public class MainActivity extends AppCompatActivity {

    private Button btnTestLight;
    private Button btnTestStaticIP;
    private Button btnTestDynamicIP;
    private TextView tvStaticIP;
    private CheckBox cbProxy;
    private Button btnTestHttpGet;
    private Button btnTestHttpsGet;
    private Button btnTestHttps2Get;
    private TextView tvPrintContent;
    private TextView tvPrintProgress;
    private EditText etStaticIP;
    private EditText etGatewayIP;
    private EditText etNetmask;
    private EditText etDNS;
    private EditText etProxyIP;
    private EditText etProxyPort;

    private Button btnSetProxy;

    private CheckBox cbProxyAuth;
    private EditText etProxyUsername;
    private EditText etProxyPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTestLight = (Button) findViewById(R.id.button);
        btnTestStaticIP = (Button) findViewById(R.id.button2);
        btnTestDynamicIP = (Button) findViewById(R.id.button3);
        tvStaticIP = (TextView) findViewById(R.id.textView_StaticIP);
        cbProxy = (CheckBox) findViewById(R.id.checkBox_Proxy);
        btnTestHttpGet = (Button) findViewById(R.id.button_HTTP);
        btnTestHttpsGet = (Button) findViewById(R.id.button_HTTPS);
        btnTestHttps2Get = (Button) findViewById(R.id.button_HTTPS2);
        etStaticIP = (EditText) findViewById(R.id.editText_Static_IP);
        etGatewayIP = (EditText) findViewById(R.id.editText_GW_IP);
        etNetmask = (EditText) findViewById(R.id.editText_Netmask);
        etDNS = (EditText) findViewById(R.id.editText_DNS_IP);
        etProxyIP = (EditText) findViewById(R.id.editText_Proxy_IP);
        etProxyPort = (EditText) findViewById(R.id.editText_Proxy_Port);

        btnSetProxy = (Button) findViewById(R.id.buttonSetProxy);

        cbProxyAuth = (CheckBox) findViewById(R.id.checkBox_Auth);
        etProxyUsername = (EditText) findViewById(R.id.editText_Proxy_Username);
        etProxyPassword = (EditText) findViewById(R.id.editText_Proxy_Password);


        btnTestLight.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("ABC","CDE");
            }

        });

        btnSetProxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnTestStaticIP.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("IP","Static");
               // final String userName = "myUser";
               // final String password = "myPasscode";

                try {

                    /////////////////////////////////////////////////////////
                   /* Authenticator.setDefault(new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(userName, password.toCharArray());
                        }
                    });

                    System.setProperty("http.proxyUser",userName);
                    System.setProperty("http.proxyPassword",password);*/
                    /////////////////////////////////////////////////////////
                    //System.setProperty("proxy_username","myUser");
                    //System.setProperty("proxy_password","myPasscode");

                    EthernetManager ethernetManager = new EthernetManager(getApplicationContext());
                    EthernetDevInfo devInfo = new EthernetDevInfo();
                    StringBuilder sb = new StringBuilder();

                    tvStaticIP.setText("Static IP");

                    devInfo.setIpAddress(etStaticIP.getText().toString());
                    devInfo.setGateway(etGatewayIP.getText().toString());
                    devInfo.setNetMask(etNetmask.getText().toString());
                    devInfo.setDnsAddr(etDNS.getText().toString());


                    sb.append("Static IP address  = " + devInfo.getIpAddress() + "\n");
                    sb.append("Gateway IP address = " + devInfo.getGateway() + "\n");
                    sb.append("Netmask            = " + devInfo.getNetMask() + "\n");
                    sb.append("DNS IP address     = " + devInfo.getDnsAddr() + "\n");

                    if(cbProxy.isChecked())
                    {
                        devInfo.setProxyAddr(etProxyIP.getText().toString());
                        devInfo.setProxyPort(etProxyPort.getText().toString());
                        sb.append("Proxy Server IP address  = " + devInfo.getProxyAddr() + "\n");
                        sb.append("Proxy Server Port number = " + devInfo.getProxyPort() + "\n");
                    }
                    else
                    {
                        devInfo.setProxyAddr("");
                        devInfo.setProxyPort("");
                    }

  /* new                  if(cbProxyAuth.isChecked())
                    {
                        devInfo.setProxyUsername(etProxyUsername.getText().toString());
                        devInfo.setProxyPassword(etProxyPassword.getText().toString());
                        sb.append("Proxy Server username  = " + devInfo.getProxyUsername() + "\n");
                        sb.append("Proxy Server password = " + devInfo.getProxyPassword() + "\n");
                    }
                    else
                    {
                        devInfo.setProxyUsername("");
                        devInfo.setProxyPassword("");
                    }
*/
                    tvStaticIP.setText(sb);

                    Log.d("IP", "Static IP Enabled: " + EthernetDevInfo.ETHERNET_CONN_MODE_MANUAL);
                    devInfo.setConnectMode(EthernetDevInfo.ETHERNET_CONN_MODE_MANUAL);

                    ethernetManager.sharedPreferencesStore(devInfo);
                    ethernetManager.resetInterface();
                    Log.d("IP", "IP successfully Set");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

        btnTestDynamicIP.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try {
                    //System.setProperty("proxy_username","myUser");
                    //System.setProperty("proxy_password","myPasscode");

                    EthernetManager ethernetManager = new EthernetManager(getApplicationContext());
                    EthernetDevInfo devInfo = new EthernetDevInfo();
                    StringBuilder sb = new StringBuilder();

                    tvStaticIP.setText("Dynamic IP");

                    if(cbProxy.isChecked())
                    {
                        devInfo.setProxyAddr(etProxyIP.getText().toString());
                        devInfo.setProxyPort(etProxyPort.getText().toString());
                    }
                    else
                    {
                        devInfo.setProxyAddr("");
                        devInfo.setProxyPort("");
                    }

    /*   new             if(cbProxyAuth.isChecked())
                    {
                        //devInfo.setProxyUsername(etProxyUsername.getText().toString());
                        //devInfo.setProxyPassword(etProxyPassword.getText().toString());
                        devInfo.setProxyUsername("myUser");
                        devInfo.setProxyPassword("myPasscode");
                        sb.append("Proxy Server username  = " + devInfo.getProxyUsername() + "\n");
                        sb.append("Proxy Server password = " + devInfo.getProxyPassword() + "\n");
                    }
                    else
                    {
                        devInfo.setProxyUsername("");
                        devInfo.setProxyPassword("");
                    }
*/
                    devInfo.setConnectMode(EthernetDevInfo.ETHERNET_CONN_MODE_DHCP);
                    Log.d("IP", "DHCP Enabled:" + EthernetDevInfo.ETHERNET_CONN_MODE_DHCP);

                    ethernetManager.sharedPreferencesStore(devInfo);
                    ethernetManager.resetInterface();
                    Log.d("IP", "IP successfully Set");

                    sb.append("Dynamic IP address  = " + devInfo.getIpAddress() + "\n");
                    sb.append("Gateway IP address = " + devInfo.getGateway() + "\n");
                    sb.append("Netmask            = " + devInfo.getNetMask() + "\n");
                    sb.append("DNS IP address     = " + devInfo.getDnsAddr() + "\n");

                    if(cbProxy.isChecked())
                    {
                        sb.append("Proxy Server IP address  = " + devInfo.getProxyAddr() + "\n");
                        sb.append("Proxy Server Port number = " + devInfo.getProxyPort() + "\n");
                    }

                    tvStaticIP.setText(sb);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

 /*       cbProxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EthernetManager ethernetManager = new EthernetManager(getApplicationContext());
                EthernetDevInfo devInfo = new EthernetDevInfo();

                if(((CheckBox) v).isChecked())
                {
                    devInfo.setProxyAddr("192.168.1.32");
                    devInfo.setProxyPort("8080");

                    tvProxy.setText("");

                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Proxy Server IP address  = " + devInfo.getProxyAddr() + "\n");
                    sb2.append("Proxy Server Port number = " + devInfo.getProxyPort() + "\n");
                    tvProxy.setText(sb2);
                }
                else
                {
                    tvProxy.setText("");

                    devInfo.setProxyAddr("");
                    devInfo.setProxyPort("");

                }

            }
        });*/

        btnTestHttpGet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                new GetUrlContentTask().execute("http://www.whatsmyip.org/http-compression-test/");
            }
        });

        btnTestHttpsGet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                new GetUrlContentTask().execute("https://www.google.com/search?q=what+is+my+name&rlz=1C1GCEU_enGB822GB822&oq=what+is+my+name&aqs=chrome..69i57j0l5.2407j0j8&sourceid=chrome&ie=UTF-8&safe=active&ssui=on");
            }
        });

        btnTestHttps2Get.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                new GetUrlContentTask().execute("https://outlook.live.com/owa/");
            }
        });
    }

    private class GetUrlContentTask extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            int i = 0;
            String server_response = "";
            URL url;
            HttpURLConnection urlConnection = null;


            try {
                publishProgress(1);
                url = new URL(urls[0]);
                publishProgress(2);
                urlConnection = (HttpURLConnection) url.openConnection();
                publishProgress(3);
                int responseCode = urlConnection.getResponseCode();
                publishProgress(4);
                if(responseCode == HttpURLConnection.HTTP_OK){
                    server_response = readStream(urlConnection.getInputStream());
                    Log.v("HTTPClient", server_response);
                }
                publishProgress(5);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return server_response;
        }

        private String readStream(InputStream is) {
            try {
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                int i = is.read();
                while(i != -1) {
                    bo.write(i);
                    i = is.read();
                }
                return bo.toString();
            } catch (IOException e) {
                return "";
            }
        }

        protected void onProgressUpdate(Integer... result) {
            tvPrintProgress = (TextView) findViewById(R.id.textView_PrintContent);
            tvPrintProgress.setText("Progress : " + Integer.toString(result[0]));
        }

        protected void onPostExecute(String result) {
            // this is executed on the main thread after the process is over
            // update your UI here
            tvPrintContent = (TextView) findViewById(R.id.textView_PrintContent);
            tvPrintContent.setText(result);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
