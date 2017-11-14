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

package cta.org.whslibrary.dao;

/**
 * Created by vanjikumaran on 11/13/17.
 */

public class Member {

    private String fullName;
    private String email;
    private String active;
    private String student;
    private String mobile;
    private String id;
    private String name;




    public Member(String id, String name, String fullName, String email, String mobile, String active, String student) {
        this.id= id;
        this.name =name;
        this.fullName = fullName;
        this.email = email;
        this.mobile = mobile;
        this.active = active;
        this.student = student;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }


    public String generatePayload(){
        return "{\"member\": {\"Name\": \""+fullName+"\",\"Email\": \""+email+"\",\"Mobile\": \""+mobile+"\",\"Status\": \"Active\",\"Students\": \""+student+"\"}}";
    }
}
