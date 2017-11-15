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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import org.json.JSONObject;
import cta.org.whslibrary.utils.HttpUtils;

public class MemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText nametxt = findViewById(R.id.editText);
                final EditText emailtxt = findViewById(R.id.editText2);
                final EditText mobiletxt = findViewById(R.id.editText3);
                final EditText studenttxt = findViewById(R.id.editText4);

                if (nametxt.getText()!=null && emailtxt.getText()!=null && mobiletxt.getText()!=null&& studenttxt.getText()!=null ) {
                    String formattedNumber = PhoneNumberUtils.formatNumber(mobiletxt.getText().toString(),"US");
                    String payload = HttpUtils.generatePayload(nametxt.getText().toString(), emailtxt.getText().toString(), formattedNumber, studenttxt.getText().toString());
                    try {
                        HttpUtils.postMember(MemberActivity.this,payload, new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                AlertDialog alertDialog = new AlertDialog.Builder(MemberActivity.this).create();
                                alertDialog.setTitle("Member Added " + statusCode);
                                alertDialog.setMessage(response.toString());
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                nametxt.setText("");
                                emailtxt.setText("");
                                mobiletxt.setText("");
                                studenttxt.setText("");
                                nametxt.requestFocus();
                                alertDialog.show();

                            }
                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                super.onFailure(statusCode, headers, responseString, throwable);
                                AlertDialog alertDialog = new AlertDialog.Builder(MemberActivity.this).create();
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
                    }catch (Exception e){
                        AlertDialog alertDialog = new AlertDialog.Builder(MemberActivity.this).create();
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



            }
        });
    }
}
