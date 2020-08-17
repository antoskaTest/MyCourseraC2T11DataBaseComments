package com.courseraandroid.myfirstappcoursera.model;

import com.courseraandroid.myfirstappcoursera.ErrorRegistration;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ErrorDeserializer implements JsonDeserializer<ErrorRegistration> {
    @Override
    public ErrorRegistration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray items = (JsonArray) json;
        ErrorRegistration errorRegistration = new ErrorRegistration();
        for(JsonElement item : items){
            //errorRegistration.setErrors(item.);
        }

        return null;
    }
}
