import grails.util.Metadata

def appName = Metadata.current.getApplicationName()
def uniqueCacheManagerName = appName + "ConfigEhcache-" + System.currentTimeMillis()

// customize temp ehcache cache manager name upon startup
grails.cache.ehcache.cacheManagerName = uniqueCacheManagerName

grails {
	cache {
		order = 2000 // higher than default (1000) and plugins, usually 1500
		enabled = true
		clearAtStartup = true // reset caches when redeploying
		ehcache {
			// ehcacheXmlLocation = 'classpath:ehcache.xml' // no custom xml config location (no xml at all)
			reloadable = false
			cacheManagerName = uniqueCacheManagerName
		}
	}
}

grails.cache.config = {
	provider {
		updateCheck false
		monitoring 'on'
		dynamicConfig false
		// unique name when configuring caches
		name uniqueCacheManagerName
	}
	defaultCache {
		maxElementsInMemory 10000
		eternal false
		timeToIdleSeconds 120
		timeToLiveSeconds 120
		overflowToDisk false // no disk use, this would require more config
		maxElementsOnDisk 10000000
		diskPersistent false
		diskExpiryThreadIntervalSeconds 120
		memoryStoreEvictionPolicy 'LRU' // least recently used gets kicked out
	}
}