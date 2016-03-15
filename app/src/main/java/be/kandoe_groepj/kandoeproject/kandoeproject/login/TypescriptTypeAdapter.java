package be.kandoe_groepj.kandoeproject.kandoeproject.login;

import com.google.gson.JsonArray;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.transform.stream.StreamResult;

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
                JsonToken type = in.peek();
                System.out.println(type);
                if (type.name().equals("STRING")) {
                    String nextString = in.nextString();
                    if (nextName.equals("_message")) {
                        if (nextString.equals("nope")) {
                            System.out.println("RECEIVED A NOPE");
                            return null;
                        } else {
                            System.out.println("Jasper: " + nextName.replace("_", ""));
                            System.out.println("Jasper: " + Arrays.toString(clazz.getFields()));
                            Field field = clazz.getField(nextName.replace("_", ""));
                            field.setAccessible(true);
                            field.set(o, nextString);
                        }
                    } else {
                        System.out.println("Jasper: " + nextName.replace("_", ""));
                        System.out.println("Jasper: " + Arrays.toString(clazz.getDeclaredFields()));
                        Field field = clazz.getDeclaredField(nextName.replace("_", ""));
                        field.setAccessible(true);
                        field.set(o, nextString);
                    }
                } else if (type.name().equals("BEGIN_ARRAY")) {
                    final List<String> array = new ArrayList<>();
                    in.beginArray();
                    while (in.hasNext()) {
                        JsonToken type2 = in.peek();
                        System.out.println(type2);
                        if (type2.name().equals("STRING")) {
                            String nextString = in.nextString();
                            array.add(nextString);
                        }
                    }
                    in.endArray();
                    Field field = clazz.getDeclaredField(nextName.replace("_", ""));
                    field.setAccessible(true);
                    String[] stringArray = new String[array.size()];
                    for (int i = 0; i < array.size(); i++) stringArray[i] = array.get(i);
                    field.set(o, stringArray);
                } else if (type.name().equals("BOOLEAN")) {
                    boolean nextBool = in.nextBoolean();
                    Field field = clazz.getDeclaredField(nextName.replace("_", ""));
                    field.setAccessible(true);
                    field.set(o, nextBool);
                } else if (type.name().equals("NULL")) {
                    in.nextNull();
                }
            }
            in.endObject();
            return o;
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }
}
