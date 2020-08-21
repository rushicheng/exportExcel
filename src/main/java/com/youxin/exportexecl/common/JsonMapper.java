package com.youxin.exportexecl.common;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 简单封装Jackson，实现JSON String<->Java Object的Mapper.
 * <p/>
 * 封装不同的输出风格, 使用不同的builder函数创建实例.
 *
 * @author liyanxin
 */
public class JsonMapper {
    private ObjectMapper mapper;

    public JsonMapper() {
        this(null);
    }

    public JsonMapper(Include include) {
        this.mapper = new ObjectMapper();
        //设置日期格式当使用jackson在处理时间时，默认是将时间输出为timestamps格式
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.mapper.setDateFormat(fmt);
        this.mapper.setTimeZone(TimeZone.getDefault());
        //设置输出时包含属性的风格
        if (include != null) {
            this.mapper.setSerializationInclusion(include);
        }
        //设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    //Include.ALWAYS 默认
    //Include.NON_DEFAULT 属性为默认值不序列化
    //Include.NON_EMPTY 属性为 空（“”）  或者为 NULL 都不序列化
    //Include.NON_NULL 属性为NULL 不序列化

    /**
     * 属性为 空（“”） 或者为 NULL 都不序列化
     *
     * @return
     */
    public static JsonMapper nonEmptyMapper() {
        return new JsonMapper(Include.NON_EMPTY);
    }

    public static JsonMapper nonEmptyMapperWithView() {
        JsonMapper jsonMapper = new JsonMapper(Include.NON_EMPTY);
        jsonMapper.mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        return jsonMapper;
    }

    /**
     * 属性为默认值不序列化
     *
     * @return
     */
    public static JsonMapper nonDefaultMapper() {
        return new JsonMapper(Include.NON_DEFAULT);
    }

    /**
     * 属性为NULL不序列化
     *
     * @return
     */
    public static JsonMapper nonNullMapper() {
        return new JsonMapper(Include.NON_NULL);
    }

    /**
     * 默认的Mapper
     *
     * @return
     */
    public static JsonMapper defaultMapper() {
        return new JsonMapper(Include.ALWAYS);
    }

    public <T> JsonMapper registerDeserialClassMapping(Class<T> superType,
            Class<? extends T> subType) {
        SimpleModule module = new SimpleModule(superType.getName(),
            Version.unknownVersion());
        module.addAbstractTypeMapping(superType, subType);
        this.mapper.registerModule(module);
        return this;
    }

    /**
     * Object可以是POJO，也可以是Collection或数组。
     * 如果对象为Null, 返回"".
     * 如果集合为空集合, 返回"[]".
     *
     * @param object
     * @return
     */
    public String toJson(Object object) {

        try {
            if (object == null) {
                return "";
            }
            return this.mapper.writeValueAsString(object);
        } catch (IOException e) {
            return null;
        }
    }

    public String toJson(Class<?> viewClass, Object object) {

        try {
            if (object == null) {
                return "";
            }
            return this.mapper.writerWithView(viewClass)
                .writeValueAsString(object);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 反序列化POJO或简单Collection如List<String>.
     * <p/>
     * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
     * <p/>
     * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String,JavaType)
     *
     * @param jsonString
     * @param clazz
     * @return
     * @see #fromJson(String, JavaType)
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return this.mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            return null;
        }
    }

    public <T> T fromJsonT(String jsonString, TypeReference<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return this.mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 反序列化复杂Collection如List<Bean>, 先使用函數createCollectionType构造类型,然后调用本函数.
     *
     * @param jsonString
     * @param javaType
     * @return
     * @see #createCollectionType(Class, Class...)
     */
    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return (T) this.mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 構造泛型的Collection Type如:
     * ArrayList<MyBean>,
     * 则调用constructCollectionType(ArrayList.class,MyBean.class)
     * HashMap<String,MyBean>, 则调用(HashMap.class,String.class, MyBean.class)
     *
     * @param collectionClass
     * @param elementClasses
     * @return
     */
    public JavaType createCollectionType(Class<?> collectionClass,
                                         Class<?>... elementClasses) {
        return this.mapper.getTypeFactory()
            .constructParametricType(collectionClass, elementClasses);
    }

    /**
     * @param functionName
     * @param object
     * @return
     */
    public String toJsonP(String functionName, Object object) {
        if (object == null) {
            return "";
        }
        return this.toJson(new JSONPObject(functionName, object));
    }

}