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

package cta.org.whslibrary.utils;

import android.content.Context;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.net.URLEncoder;

import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class HttpUtils {
    private static final String BASE_URL = "http://172.18.0.1:8280/members/member";
    private static AsyncHttpClient client = new AsyncHttpClient();
    public static void post(Context context, String payload, AsyncHttpResponseHandler responseHandler) throws Exception {
            StringEntity entity = new StringEntity(payload);
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            client.post(context, BASE_URL, entity, "application/json", responseHandler);
    }

    public static void get(Context context, String payload, AsyncHttpResponseHandler responseHandler) throws Exception{
        String endpoint = BASE_URL+"/"+URLEncoder.encode(payload,"UTF-8").replace("+", "%20");
        client.setURLEncodingEnabled(false);
        client.get(context, endpoint ,responseHandler);

    }

    public static String generatePayload(String fullName, String email, String mobile, String student){
        return "{\"member\": {\"Name\": \""+fullName+"\",\"Email\": \""+email+"\",\"Mobile\": \""+mobile+"\",\"Status\": \"Active\",\"Students\": \""+student+"\"}}";
    }
}
