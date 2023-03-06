package com.springbatchexample.config;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.json.JsonObjectReader;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jayway.jsonpath.JsonPath;

/*
 * This class follows the structure and functions similar to JacksonJsonObjectReader, with 
 * the difference that it expects a object as root node, instead of an array.
 */
public class CustomJsonReader<T> implements JsonObjectReader<T> {

    Logger logger = Logger.getLogger(CustomJsonReader.class.getName());

    ObjectMapper mapper = new ObjectMapper();
    private JsonParser jsonParser;
    private InputStream inputStream;

    private ArrayNode targetNode;
    private Class<T> targetType;
    private String targetPath;

    public CustomJsonReader(Class<T> targetType, String targetPath) {
        super();
        this.targetType = targetType;
        this.targetPath = targetPath;
    }

    public JsonParser getJsonParser() {
        return jsonParser;
    }

    public void setJsonParser(JsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public ArrayNode getDatasetNode() {
        return targetNode;
    }

    /*
     * JsonObjectReader interface has an empty default method and must be
     * implemented in this case to set
     * the mapper and the parser
     */
    @Override
    public void open(Resource resource) throws Exception {
        logger.info("Opening json object reader");
        this.inputStream = resource.getInputStream();
        JsonNode jsonNode = this.mapper.readTree(this.inputStream).findPath(targetPath);
        if (!jsonNode.isMissingNode()) {
            this.jsonParser = startArrayParser(jsonNode);
            logger.info("Reader open with parser reference: " + this.jsonParser);
            this.targetNode = (ArrayNode) jsonNode; // for testing purposes
        } else {
            logger.severe("Couldn't read target node " + this.targetPath);
            throw new Exception();
        }
    }

    @Override
    public T read() throws Exception {
        try {
            if (this.jsonParser.nextToken() == JsonToken.START_OBJECT) {
                // T instance = targetType.getDeclaredConstructor(targetType).newInstance();
                String jsonText = this.mapper.readTree(this.jsonParser).toString();
                T result = getNewInstance(jsonText);
                logger.info("Object read: " + result.hashCode());
                return result;
            }
        } catch (IOException e) {
            throw new ParseException("Unable to read next JSON object", e);
        }
        return null;
    }

    /**
     * Creates a new parser from an array node
     */
    private JsonParser startArrayParser(JsonNode jsonArrayNode) throws IOException {
        JsonParser jsonParser = this.mapper.getFactory().createParser(jsonArrayNode.toString());
        if (jsonParser.nextToken() == JsonToken.START_ARRAY) {
            return jsonParser;
        } else {
            throw new IOException();
        }
    }

    @Override
    public void close() throws Exception {
        this.inputStream.close();
        this.jsonParser.close();
    }

    private T getNewInstance(String jsonData) throws InstantiationException, IllegalAccessException,
            NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        T instance = this.targetType.newInstance();
        Field[] fields = this.targetType.getDeclaredFields();
        for (Field field : fields) {
            WithJson val = field.getAnnotation(WithJson.class);
            if (val != null) {
                Class<?> fieldType = field.getType();
                Object data = JsonPath.read(jsonData, val.value());
                Method method = targetType.getMethod("set" + StringUtils.capitalize(field.getName()), fieldType);
                method.invoke(instance, fieldType.cast(data));
            }

        }
        return instance;

    }

}