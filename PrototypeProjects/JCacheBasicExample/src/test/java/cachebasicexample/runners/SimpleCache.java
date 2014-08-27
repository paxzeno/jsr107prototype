package cachebasicexample.runners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Cache simple runner example.
 *
 * Created by Andre Teixeira on 23/08/14.
 */
public class SimpleCache {

    @Test
    public void test() {
        Logger LOGGER = LoggerFactory.getLogger(SimpleCache.class);

        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();
        MutableConfiguration<String, Integer> config = new MutableConfiguration<String, Integer>()
                .setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(Duration.ONE_HOUR))
                .setStatisticsEnabled(true);

        Cache<String, Integer> cache = cacheManager.createCache("SimpleCache", config);

        LOGGER.info("Cache created with name {} ", cache.getName());

        String exampleKey = "key";
        Integer value1 = 1;
        cache.put("key", value1);

        LOGGER.info("Entry [key: key, value: {}] inserted on cache", value1);

        Integer value2 = cache.get(exampleKey);

        LOGGER.info("Getting entry [key: {}, value: {}]", exampleKey, value2);

        assertEquals(value1, value2);

        cache.remove(exampleKey);

        LOGGER.info("Entry with key {} removed", exampleKey);

        assertNull(cache.get(exampleKey));
    }
}
