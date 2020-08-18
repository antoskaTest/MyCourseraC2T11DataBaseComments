package com.courseraandroid.myfirstappcoursera.model.converter;

import androidx.annotation.Nullable;

import com.courseraandroid.myfirstappcoursera.model.Data;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class DataConverterFactory extends Converter.Factory{
    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Type envelopedType = TypeToken.getParameterized(Data.class, type).getType();
        final Converter<ResponseBody, Data> delegate = retrofit.nextResponseBodyConverter(this, envelopedType, annotations);

//        return new Converter<ResponseBody, Object>() {
//            @Override
//            public Object convert(ResponseBody body) throws IOException {
//                Data<?> data = delegate.convert(body);
//                return data.response;
//            }
//        };
          return      body ->{
          Data<?> data = delegate.convert(body);
          return data.response;
        };
    }
}
