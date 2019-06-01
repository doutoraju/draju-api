package br.com.draju.templateapi.util;

import br.com.draju.templateapi.data.Action;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class GeneratorUtils {

    public static Map<String, String> getReplacingParametersFromDTO(Object dtoObject) {
        Map<String, String> map = new HashMap<>();

        Class<?> thisClass = null;
        try {
            thisClass = Class.forName(dtoObject.getClass().getName());
            log.info("Starting getReplacingParametersFromDTO for: {}", dtoObject.getClass().getSimpleName() );
            Field[] aClassFields = thisClass.getDeclaredFields();
            for(Field f : aClassFields){
                String fName = thisClass.getSimpleName() + "." + f.getName();
                log.info("(" + f.getType() + ") " + fName + " = " + f.get(dtoObject) + ", ");
                if(f.getType().toString().contains("java.lang.String")) {
                    log.info(" putting value in map {} = {} ", fName, f.get(dtoObject) );
                    map.put(fName, (String) f.get(dtoObject));
                } else {
                    Object obj = f.get(dtoObject);
                    if(obj != null) {
                        map.putAll(getReplacingParametersFromDTO(obj));
                    }
                }
            }
            log.info("Ending getReplacingParametersFromDTO for: {}", dtoObject.getClass().getSimpleName() );
        } catch (Exception ex) {
            log.error("Error in creating parameter from DTO {}", dtoObject, ex);
        }

        return map;
    }
}
