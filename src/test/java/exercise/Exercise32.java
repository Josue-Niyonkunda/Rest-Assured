import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

import java.util.*;

public class Exercise32 {

    static List<String> keys = new ArrayList<>();
    static List<Object> values = new ArrayList<>();
@Test
    public void keyValueExtractor () throws Exception {

        String json = "[\n" +
                "  {\n" +
                "    \"k\":[1,3,5],\n" +
                "    \"k1\":{\n" +
                "      \"k10\":4,\n" +
                "      \"k11\":[4,7,9],\n" +
                "      \"k12\":{\n" +
                "        \"k120\":{\n" +
                "          \"k121\":\"v121\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"k14\":6\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"k22\":{\n" +
                "      \"k221\":\"v122\"\n" +
                "    }\n" +
                "  }\n" +
                "]";

        ObjectMapper mapper = new ObjectMapper();
        Object parsed = mapper.readValue(json, Object.class);

        extract1(parsed);

        System.out.println("Keys = " + keys);
        System.out.println("Values = " + values);
    }

    public void extract1(Object obj) {
         //Check whether obj is a Map (or a subtype of Map) at runtime.
        if (obj instanceof Map) {
           // casting an Object to a List
            Map<String, Object> map = (Map<String, Object>) obj;
            //Loop through every key-value pair in the Map.
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                keys.add(entry.getKey());

                Object value = entry.getValue();

                if (value instanceof Map || value instanceof List) {
                    extract1(value);
                } else {
                    values.add(value);
                }
            }
        }

        else if (obj instanceof List) {
           // Cast generic Object → List(casting an Object to a List)
            //(? means unknown type inside list)
            List<?> list = (List<?>) obj;

            // If it's a pure value list → treat as value
            if (!list.isEmpty() && !(list.get(0) instanceof Map)) {
                values.add(list);
            } else {
                for (Object item : list) {
                    extract1(item);
                }
            }
        }
    }


}