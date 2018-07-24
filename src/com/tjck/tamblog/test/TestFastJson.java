package com.tjck.tamblog.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tjck.tamblog.entity.Twxtoken;

public class TestFastJson {
	
	public static void main(String[] args) {
		Twxtoken twxtoken = new Twxtoken();
		twxtoken.setId("1");
		twxtoken.setAppid("appid1");
		twxtoken.setSeconds("2000");
		Twxtoken twxtoken1 = new Twxtoken();
		twxtoken1.setId("2");
		twxtoken1.setAppid("appid2");
		twxtoken1.setSeconds("4000");
		List<Twxtoken> list = new ArrayList<Twxtoken>();
		list.add(twxtoken1);
		list.add(twxtoken);
		String jsonstr = JSON.toJSONString(twxtoken);
		System.err.println("javabean-to-string------" + jsonstr);
		String liststr = JSON.toJSONString(list);
		System.err.println("list-to-string------" + liststr);
		Twxtoken twxtoken2 = JSON.parseObject(jsonstr,Twxtoken.class);
		System.err.println("string -to-javabean_1------" + twxtoken2.getAppid());
		Twxtoken twxtoken3 = JSON.parseObject(jsonstr,new TypeReference<Twxtoken>(){
		});
		System.err.println("string -to-javabean_2------" + twxtoken3.getAppid());
		
		List<Twxtoken> list2 = JSON.parseArray(liststr, Twxtoken.class);
		System.err.println("string-to-list_1------" + list2.get(0).getAppid());
		List<Twxtoken> list3 = JSON.parseObject(liststr,new TypeReference<List<Twxtoken>>(){	
		});
		System.err.println("string-to-list_2------" + list3.get(1).getAppid());
		
		Map<String , Twxtoken> map = new HashMap<String, Twxtoken>();
		map.put("1", twxtoken);
		map.put("2", twxtoken1);
		String jsonmap = JSON.toJSONString(map);
		System.err.println("map-to-string------" + jsonmap);
		Map<String, Twxtoken> map2 = parseToMap(jsonmap, String.class, Twxtoken.class);
		for(Map.Entry<String, Twxtoken> entry : map2.entrySet()){
			Twxtoken tx	= map2.get(entry.getKey());
			System.err.println("string-to-map_1------" + tx.getAppid());
		}
		Map<String , Twxtoken> map4 = JSON.parseObject(jsonmap, new TypeReference<Map<String, Twxtoken>>(){
		});
		for(Map.Entry<String, Twxtoken> entry : map4.entrySet()){
			Twxtoken tx	= map4.get(entry.getKey());
			System.err.println("string-to-map_2------" + tx.getAppid());
		}
		Map<String , List<Twxtoken>> map3 = new HashMap<String, List<Twxtoken>>();
		map3.put("1", list);
		String jsonlistmap = JSON.toJSONString(map3);
		System.err.println("maplist-to-string------" + jsonlistmap);
		Map<String , List<Twxtoken>> map5 = JSON.parseObject(jsonlistmap, new TypeReference<Map<String, List<Twxtoken>>>(){
		});
		List<Twxtoken>list4 = map5.get("1");
		System.err.println("string-to-maplist------" + list4.get(0).getAppid());
	}
	
	public static <K, V> Map<K, V> parseToMap(String json, Class<K> keyType, Class<V> valueType) {
		return JSON.parseObject(json, new TypeReference<Map<K, V>>(keyType, valueType) {
		});
	}
	
}
