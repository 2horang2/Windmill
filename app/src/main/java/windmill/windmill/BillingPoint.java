package windmill.windmill;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import data.models.PREF;
import util.IabHelper;
import util.IabResult;
import util.Inventory;
import util.Purchase;

public class BillingPoint extends AppCompatActivity {

    @Bind(R.id.p1000)
    RelativeLayout p1000;
    @Bind(R.id.p5000)
    RelativeLayout p5000;
    @Bind(R.id.p10000)
    RelativeLayout p10000;
    @Bind(R.id.p30000)
    RelativeLayout p30000;
    @Bind(R.id.p50000)
    RelativeLayout p50000;
    @Bind(R.id.p100000)
    RelativeLayout p100000;
    @Bind(R.id.pp)
    TextView tt;


    IabHelper mHelper;
    static final String ITEM_1000 = "windmill.msg.p1000";
    static final String ITEM_5000 = "windmill.msg.p5000";
    static final String ITEM_10000 = "windmill.msg.p10000";
    static final String ITEM_30000 = "windmill.msg.p30000";
    static final String ITEM_50000 = "windmill.msg.p50000";
    static final String ITEM_100000 = "windmill.msg.p100000";



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_point);



        ButterKnife.bind(this);


        ActionBar ac = getSupportActionBar();
        if(ac!=null){
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setHomeButtonEnabled(true);
            ac.setTitle("포인트 충전");
        }

            tt.setText(PREF.point+"");

        String base64EncodedPublicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuYcQNp42NHdOxAFyiEalW8SoKuE0iEYQPrrc/nP/X"+"vZ/gzMBeRr7LMozcWBp5JW4EA9C5bvpRLNcVP2Awdeyd52fNBD1xLft"+"7iAV/e6EeVXcwb66bMmQBx0FOgFUaIevUdHy2RZvHE8CeJE7tsDcC8NxlsnejT/j2SiUvlP3bgCjo0aQk9KDNg8YIfOOfzq7Lmjwh5bLHgXVKD4bgTX/yUxxODPuaJAZRlCtcfGaCMl3KHvrkyqUpq0Nhnjjmn2ak8EHfM832rZ7yjeN5QDxMrMpuJ91Y"+"Xzq5eAaBkMytAnfJKJ9XS5kBwJpLNjnc"+"VRdz3z21CuQ"+"X5EhuNNuwIDAQAB";

        mHelper = new IabHelper(this, base64EncodedPublicKey);

        mHelper.startSetup(new
                                   IabHelper.OnIabSetupFinishedListener() {
                                       public void onIabSetupFinished(IabResult result) {
                                           if (!result.isSuccess()) {
                                             //  Log.d(TAG, "In-app Billing setup failed: " "+"
                                              //         result);
                                           } else {
                                             //  Log.d(TAG, "In-app Billing is set up OK");
                                           }
                                       }
                                   });


        p1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelper.launchPurchaseFlow(BillingPoint.this, ITEM_1000, 10001,
                        mPurchaseFinishedListener, "mypurchasetoken");
            }
        });

        p5000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelper.launchPurchaseFlow(BillingPoint.this, ITEM_5000, 50001,
                        mPurchaseFinishedListener, "mypurchasetoken");
            }
        });



        p10000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelper.launchPurchaseFlow(BillingPoint.this, ITEM_10000, 100001,
                        mPurchaseFinishedListener, "mypurchasetoken");
            }
        });

        p30000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelper.launchPurchaseFlow(BillingPoint.this, ITEM_30000, 300001,
                        mPurchaseFinishedListener, "mypurchasetoken");
            }
        });

        p50000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelper.launchPurchaseFlow(BillingPoint.this, ITEM_50000, 500001,
                        mPurchaseFinishedListener, "mypurchasetoken");
            }
        });

        p100000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelper.launchPurchaseFlow(BillingPoint.this, ITEM_100000, 1000001,
                        mPurchaseFinishedListener, "mypurchasetoken");
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }

    public void consumeItem() {
        mHelper.queryInventoryAsync(mReceivedInventoryListener);
    }


    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {

            if (result.isFailure()) {
                // Handle failure
            } else {
              //  mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),
              //          mConsumeFinishedListener);

                mHelper.consumeAsync(inventory.getPurchase(ITEM_1000),
                        mConsumeFinishedListener);

                mHelper.consumeAsync(inventory.getPurchase(ITEM_5000),
                        mConsumeFinishedListener);

                mHelper.consumeAsync(inventory.getPurchase(ITEM_10000),
                        mConsumeFinishedListener);

                mHelper.consumeAsync(inventory.getPurchase(ITEM_30000),
                        mConsumeFinishedListener);

                mHelper.consumeAsync(inventory.getPurchase(ITEM_50000),
                        mConsumeFinishedListener);

                mHelper.consumeAsync(inventory.getPurchase(ITEM_100000),
                        mConsumeFinishedListener);


            }
        }
    };




    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase) {
            if (result.isFailure()) {
                // Handle error
                return;

            } else if (purchase.getSku().equals(ITEM_1000)) {

                PREF.point = PREF.point +500;
                SharedPreferences pref = getSharedPreferences("prev_name", Context.MODE_MULTI_PROCESS);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("point", PREF.point);
                editor.commit();
                new insertPoint().execute();

            }else if (purchase.getSku().equals(ITEM_5000)) {

                PREF.point = PREF.point +2500;
                SharedPreferences pref = getSharedPreferences("prev_name", Context.MODE_MULTI_PROCESS);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("point", PREF.point);
                editor.commit();
                new insertPoint().execute();

            }else if (purchase.getSku().equals(ITEM_10000)) {

                PREF.point = PREF.point +6000;
                SharedPreferences pref = getSharedPreferences("prev_name", Context.MODE_MULTI_PROCESS);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("point", PREF.point);
                editor.commit();
                new insertPoint().execute();

            }else if (purchase.getSku().equals(ITEM_30000)) {

                PREF.point = PREF.point +20000;
                SharedPreferences pref = getSharedPreferences("prev_name", Context.MODE_MULTI_PROCESS);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("point", PREF.point);
                editor.commit();
                new insertPoint().execute();

            }else if (purchase.getSku().equals(ITEM_50000)) {

                PREF.point = PREF.point +35000;
                SharedPreferences pref = getSharedPreferences("prev_name", Context.MODE_MULTI_PROCESS);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("point", PREF.point);
                editor.commit();
                new insertPoint().execute();

            }else if (purchase.getSku().equals(ITEM_100000)) {

                PREF.point = PREF.point +80000;
                SharedPreferences pref = getSharedPreferences("prev_name", Context.MODE_MULTI_PROCESS);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("point", PREF.point);
                editor.commit();
                new insertPoint().execute();

            }

        }
    };

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase,
                                              IabResult result) {

                    if (result.isSuccess()) {
                       // clickButton.setEnabled(true);
                        if (purchase.getSku().equals(ITEM_1000)) {

                            PREF.point = PREF.point +500;
                            SharedPreferences pref = getSharedPreferences("prev_name", Context.MODE_MULTI_PROCESS);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putInt("point", PREF.point);
                            editor.commit();
                            new insertPoint().execute();

                        }else if (purchase.getSku().equals(ITEM_5000)) {

                            PREF.point = PREF.point +2500;
                            SharedPreferences pref = getSharedPreferences("prev_name", Context.MODE_MULTI_PROCESS);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putInt("point", PREF.point);
                            editor.commit();
                            new insertPoint().execute();

                        }else if (purchase.getSku().equals(ITEM_10000)) {

                            PREF.point = PREF.point +6000;
                            SharedPreferences pref = getSharedPreferences("prev_name", Context.MODE_MULTI_PROCESS);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putInt("point", PREF.point);
                            editor.commit();
                            new insertPoint().execute();

                        }else if (purchase.getSku().equals(ITEM_30000)) {

                            PREF.point = PREF.point +20000;
                            SharedPreferences pref = getSharedPreferences("prev_name", Context.MODE_MULTI_PROCESS);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putInt("point", PREF.point);
                            editor.commit();
                            new insertPoint().execute();

                        }else if (purchase.getSku().equals(ITEM_50000)) {

                            PREF.point = PREF.point +35000;
                            SharedPreferences pref = getSharedPreferences("prev_name", Context.MODE_MULTI_PROCESS);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putInt("point", PREF.point);
                            editor.commit();
                            new insertPoint().execute();

                        }else if (purchase.getSku().equals(ITEM_100000)) {

                            PREF.point = PREF.point +80000;
                            SharedPreferences pref = getSharedPreferences("prev_name", Context.MODE_MULTI_PROCESS);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putInt("point", PREF.point);
                            editor.commit();
                            new insertPoint().execute();

                        }

                    } else {
                        // handle error
                    }
                }
            };

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    ProgressDialog loagindDialog;
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    String response2;
    private class insertPoint extends AsyncTask<String, Void, Void> {
        @Override


        protected void onPreExecute() {
            super.onPreExecute();
            loagindDialog = ProgressDialog.show(BillingPoint.this, "잠시만 기다려주세요",
                    "Please wait..", true, false);
        }

        @Override

        protected Void doInBackground(String... params) {
            try {

                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://cmcm1284.cafe24.com/windmill/bill_pooint.php");
                nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("user", PREF.id));
                nameValuePairs.add(new BasicNameValuePair("point", PREF.point+""));


                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response2 = httpclient.execute(httppost, responseHandler);


                //ResponseHandler<String> responseHandler = new BasicResponseHandler();
                // String re = httpclient.execute(httppost, responseHandler);
                //  System.out.println("멍충시발~ : " + re);


            } catch (Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            loagindDialog.dismiss();

            tt.setText(PREF.point + "");
        }
    }



}
