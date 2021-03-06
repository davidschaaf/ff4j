package org.ff4j.hazelcast.store;

import java.util.Properties;

import org.ff4j.cache.FF4JCacheManager;
import org.ff4j.core.FeatureStore;
import org.ff4j.hazelcast.CacheManagerHazelCast;
import org.ff4j.store.FeatureStoreJCache;
import org.ff4j.store.PropertyStoreJCache;

import com.hazelcast.config.Config;

/**
 * Implementation of {@link FeatureStore} for hazelcast.
 *
 * @author Cedrick LUNVEN (@clunven)
 */
public class PropertyStoreHazelCast extends PropertyStoreJCache {

    /**
     * Default constructor.
     */
    public PropertyStoreHazelCast() {
        this(new CacheManagerHazelCast());
    }
    
    /**
     * Default constructor.
     */
    public PropertyStoreHazelCast(String xmlConfigFileName, Properties systemProperties) {
        this(new CacheManagerHazelCast(xmlConfigFileName, systemProperties));
    }
            
    /**
     * Leverage on JCACHE but initialize from Hazelcast.
     *
     * @param cacheManager
     */
    public PropertyStoreHazelCast(Config hazelcastConfig, Properties systemProperties) {
        this(new CacheManagerHazelCast(hazelcastConfig, systemProperties));
    }
    
    /**
     * Init from hazelcast, cast manager (logic in {@link FeatureStoreJCache}).
     * 
     * @param cacheManager
     *      implementation of {@link FF4JCacheManager} for hazel cast
     */
    public PropertyStoreHazelCast(CacheManagerHazelCast cacheManager) {
        super(cacheManager);
    }

}
