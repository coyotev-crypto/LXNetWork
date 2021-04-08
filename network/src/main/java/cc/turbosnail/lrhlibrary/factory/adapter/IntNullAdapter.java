package cc.turbosnail.lrhlibrary.factory.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class IntNullAdapter extends TypeAdapter<Number> {

    @Override
    public void write(JsonWriter out, Number value) throws IOException {
        out.value(value);
    }

    @Override
    public Number read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        try {
            return reader.nextInt();
        } catch (NumberFormatException e) {
            //这里解析int出错，那么捕获异常并且返回默认值，因为nextInt出错中断了方法，没有完成位移，所以调用nextString()方法完成位移。
            reader.nextString();
            return 0;
        }
    }
}