/*
 * Copyright (c) 2017 Vanjikumaran
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cta.org.whslibrary;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cta.org.whslibrary.dao.Book;
import cta.org.whslibrary.dao.Member;
import cta.org.whslibrary.utils.HttpUtils;
import cz.msebera.android.httpclient.Header;


public class ScanActivity extends AppCompatActivity {

    private Button btnScan1;
    private Button btnFind;
    private Button btnSave;


    private TextView txtVUserFullName;
    private TextView txtvbar1;

    private EditText txtUserNumber;


    private Member member;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        btnScan1 = findViewById(R.id.btnScan1);
        btnScan1.setEnabled(false);

        txtvbar1 = findViewById(R.id.txtvbar1);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setEnabled(false);


        btnScan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(ScanActivity.this);
                integrator.initiateScan();
            }
        });

        //Find Member
        btnFind = findViewById(R.id.btnFind);
        txtVUserFullName = findViewById(R.id.txtVUserFullName);
        txtUserNumber = findViewById(R.id.txtUserNumber);

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (txtUserNumber.getText()!=null) {
                        String formattedNumber = PhoneNumberUtils.formatNumber(txtUserNumber.getText().toString(),"US");

                        HttpUtils.getMember(ScanActivity.this,formattedNumber,new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                JSONObject records=null;
                                try {
                                     records = response.getJSONObject("queryResponse").getJSONObject("result").getJSONObject("records");
                                    if (records.getString("type").equals("Member__c")) {
                                        member = new Member(records.getString("Id"),records.getString("Name"),records.getString("Name__c"),records.getString("Email_Id__c"),records.getString("Contact_Number__c"),records.getString("Status__c"),records.getString("Students_Name__c"));
                                        txtVUserFullName.setText(member.getFullName());
                                        btnScan1.setEnabled(true);
                                    }
                                }catch (Exception ex){

                                }
                            }
                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                super.onFailure(statusCode, headers, responseString, throwable);
                                AlertDialog alertDialog = new AlertDialog.Builder(ScanActivity.this).create();
                                alertDialog.setTitle("Failed" + statusCode);
                                alertDialog.setMessage("failed");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }

                        });
                    }


                }catch (Exception ex){
                    AlertDialog alertDialog = new AlertDialog.Builder(ScanActivity.this).create();
                    alertDialog.setTitle("Oppssssss...");
                    alertDialog.setMessage("Something went wrong!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });



    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            String re = scanResult.getContents();
            if (re!=null) {
                Log.d("code", re);
                txtvbar1.setText(re);
                try {
                    HttpUtils.getBook(ScanActivity.this, re, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            JSONObject records=null;
                            try {
                                records = response.getJSONObject("queryResponse").getJSONObject("result").getJSONObject("records");
                                if (records.getString("type").equals("Book__c")) {
                                    book = new Book(records.getString("Id"), records.getString("Name"),records.getString("Book_Number__c"),records.getString("Book_Name_in_Tamil__c"),records.getString("Status__c"));
                                    member = new Member(records.getString("Id"),records.getString("Name"),records.getString("Name__c"),records.getString("Email_Id__c"),records.getString("Contact_Number__c"),records.getString("Status__c"),records.getString("Students_Name__c"));
                                    txtVUserFullName.setText(member.getFullName());
                                }
                            }catch (Exception ex){

                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }
                    });
                }catch (Exception ex){

                }

            }
        }
    }

    public void clearAll(){
        member=null;
        book = null;
    }
}
