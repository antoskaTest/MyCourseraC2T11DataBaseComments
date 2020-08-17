package com.courseraandroid.myfirstappcoursera;

import java.util.List;

public class ErrorRegistration {

    /**
     * errors : {"email":["string"],"name":["string"],"password":["string"]}
     */

    private ErrorsBean errors;

    public ErrorsBean getErrors() {
        return errors;
    }

    public void setErrors(ErrorsBean errors) {
        this.errors = errors;
    }

    public static class ErrorsBean {
        private List<String> email;
        private List<String> name;
        private List<String> password;

        public List<String> getEmail() {
            return email;
        }

        public void setEmail(List<String> email) {
            this.email = email;
        }

        public List<String> getName() {
            return name;
        }

        public void setName(List<String> name) {
            this.name = name;
        }

        public List<String> getPassword() {
            return password;
        }

        public void setPassword(List<String> password) {
            this.password = password;
        }
    }
}
