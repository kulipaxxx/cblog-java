package com.cheng.entity.role;

import com.alibaba.druid.proxy.jdbc.ClobProxyImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.Reader;
import java.util.*;

/**
 * @author Wxiaokun
 * @version 1.0
 * @date 2020/9/25 0025 下午 4:15
 */
public class Record extends HashMap implements Map {

        private static final long serialVersionUID = 1L;

        Map map = null;
        HttpServletRequest request;
public Record(HttpServletRequest request){
            this.request = request;
            Map properties = request.getParameterMap();
            Map returnMap = new HashMap();
            Iterator entries = properties.entrySet().iterator();
            Map.Entry entry;
            String name = "";
            String value = "";
            while (entries.hasNext()) {
                entry = (Map.Entry) entries.next();
                name = (String) entry.getKey();
                Object valueObj = entry.getValue();
                if(null == valueObj){
                    value = "";
                }else if(valueObj instanceof String[]){
                    String[] values = (String[])valueObj;
                    for(int i=0;i<values.length;i++){
                        value = values[i] + ",";
                    }
                    value = value.substring(0, value.length()-1);
                }else{
                    value = valueObj.toString();
                }
                returnMap.put(name, value);
            }
            map = returnMap;
        }

public Record() {
            map = new HashMap();
        }

@Override
public Object get(Object key) {
            Object obj = null;
            if(map.get(key) instanceof Object[]) {
                Object[] arr = (Object[])map.get(key);
                obj = request == null ? arr:(request.getParameter((String)key) == null ? arr:arr[0]);
            } else {
                obj = map.get(key);
            }
            return obj;
        }

public String getString(Object key) {
            return (String)get(key);
        }

@SuppressWarnings("unchecked")
@Override
public Object put(Object key, Object value) {
            if(value instanceof ClobProxyImpl){             // 这里必须要到如durid数据源的依赖 读取数据库 Clob类型数据
                try {
                    ClobProxyImpl cpi = (ClobProxyImpl)value;
                    Reader is = cpi.getCharacterStream();     //获取流
                    BufferedReader br = new BufferedReader(is);
                    String str = br.readLine();
                    StringBuffer sb = new StringBuffer();
                    while(str != null){                        //循环读取数据拼接到字符串
                        sb.append(str);
                        sb.append("\n");
                        str = br.readLine();
                    }
                    value = sb.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return map.put(key, value);
        }

        @Override
        public Object remove(Object key) {
            return map.remove(key);
        }

       @Override
        public void clear() {
            map.clear();
        }

        @Override
        public boolean containsKey(Object key) {
            return map.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value){
            return map.containsValue(value);
        }

        public Set entrySet() {
            return map.entrySet();
        }

        @Override
        public boolean isEmpty() {
            return map.isEmpty();
        }

        public Set keySet() {
            return map.keySet();
        }

        @SuppressWarnings("unchecked")
        @Override
        public void putAll(Map t) {
            map.putAll(t);
        }

        @Override
        public int size() {
            return map.size();
        }

        public Collection values() {
            return map.values();
        }
    }
