package be.kandoe_groepj.kandoeproject.kandoeproject.login;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;

public class TypescriptTypeAdapter<T> extends TypeAdapter<T> {

    private Class<T> clazz;

    public TypescriptTypeAdapter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void write(JsonWriter out, T value) throws IOException {
        out.beginObject();
        for (Field field : clazz.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (field.get(value) != null)
                    out.name("_" + field.getName()).value(field.get(value).toString());
                    //out.name(field.getName()).value(field.get(value).toString());


            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        out.endObject();
    }

    @Override
    public T read(JsonReader in) throws IOException {
        try {
            T o = clazz.newInstance();
            in.beginObject();
            while (in.hasNext()) {
                String nextName = in.nextName();
                String nextString = in.nextString();
                if (nextName.equals("_message") && nextString.equals("nope")) {
                    System.out.println("RECEIVED A NOPE");
                    return null;
                } else {
                    System.out.println(nextName.replace("_", ""));
                    System.out.println(Arrays.toString(clazz.getFields()));
                    Field field = clazz.getDeclaredField(nextName.replace("_", ""));
                    field.setAccessible(true);
                    field.set(o, nextString);
                }
            }
            in.endObject();
            System.out.println("is o null ? " + (o == null));
            return o;
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }
}
