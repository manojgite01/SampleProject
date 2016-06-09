package com.smartapp.web.utility;

import java.util.List;

public class EhCacheForm {

	private List<String> ehcacheList;
	
	private List<String> ehcacheManagerList;

	private String cacheName;

	private String cacheElementKey;

	private List<CacheElement> ehcacheElementList;
	
	private String cacheManagerName;

	public List<String> getEhcacheList() {
		return ehcacheList;
	}

	public void setEhcacheList(List<String> ehcacheList) {
		this.ehcacheList = ehcacheList;
	}

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public String getCacheElementKey() {
		return cacheElementKey;
	}

	public void setCacheElementKey(String cacheElementKey) {
		this.cacheElementKey = cacheElementKey;
	}

	public List<CacheElement> getEhcacheElementList() {
		return ehcacheElementList;
	}

	public void setEhcacheElementList(List<CacheElement> ehcacheElementList) {
		this.ehcacheElementList = ehcacheElementList;
	}

	@Override
	public String toString() {
		return "EhCacheForm [ehcacheList=" + ehcacheList + "]";
	}

	public static class CacheElement {

		public CacheElement(Object key, Object value) {
			if (key != null) {
				this.key = key.toString();
			} else {
				this.key = "Null";
			}
			if (value != null) {
				this.value = value.toString();
			} else {
				this.value = "Null";
			}
		}

		private String key;
		private String value;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

	public String getCacheManagerName() {
		return cacheManagerName;
	}

	public void setCacheManagerName(String cacheManagerName) {
		this.cacheManagerName = cacheManagerName;
	}

	public List<String> getEhcacheManagerList() {
		return ehcacheManagerList;
	}

	public void setEhcacheManagerList(List<String> ehcacheManagerList) {
		this.ehcacheManagerList = ehcacheManagerList;
	}
	
	
	

}
