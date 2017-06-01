package org.ff4j.ignite.jcache;

import java.net.URI;
import java.util.Arrays;
import java.util.Properties;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;

import org.apache.ignite.Ignite;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.ff4j.cache.FF4jJCacheManager;

/**
 * Implementation of {@link CacheManager} for Ignite as not provided by default.
 * 
 * (Why leveraging on JCache ?)
 * 
 * @author Cedrick LUNVEN (@clunven)
 */
public class IgniteCachingManagerImpl implements CacheManager {

    /** Relative cache manager. */
    private IgniteCachingProviderImpl cacheProvider = null;
   
    /**
     * Implementation of caching.
     *
     * @param provider
     *      caching provider
     */
    public IgniteCachingManagerImpl(IgniteCachingProviderImpl provider) {
        this.cacheProvider = provider;
    }
    
    /** {@inheritDoc} */
    @Override
    public CachingProvider getCachingProvider() {
        return cacheProvider;
    }

    /** {@inheritDoc} */
    @Override
    public URI getURI() {
        return getCachingProvider().getDefaultURI();
    }

    /** {@inheritDoc} */
    @Override
    public ClassLoader getClassLoader() {
        return getClassLoader();
    }

    /** {@inheritDoc} */
    @Override
    public Properties getProperties() {
        return getCachingProvider().getDefaultProperties();
    }

    /** {@inheritDoc} */
    @Override
    public <K, V, C extends Configuration<K, V>> Cache<K, V> createCache(String cacheName, C conf)
    throws IllegalArgumentException {
        CacheConfiguration<K, V> cfg = (CacheConfiguration<K, V>) conf;
        cfg.setName(cacheName);
        cfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        return (Cache<K, V>) getIgnite().getOrCreateCache(cfg);
    }
    
    /**
     * Retrieve ignite reference from the caching provider.
     *
     * @return
     *      current ignite instance.
     */
    private Ignite getIgnite() {
        return ((IgniteCachingProviderImpl) getCachingProvider()).getIgnite();
    }

    /** {@inheritDoc} */
    @Override
    public <K, V> Cache<K, V> getCache(String cacheName, Class<K> keyType, Class<V> valueType) {
        return createCache(cacheName, new CacheConfiguration<K, V>());
    }

    /** {@inheritDoc} */
    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) {
        return createCache(cacheName, null);
    }

    /** {@inheritDoc} */
    @Override
    public Iterable<String> getCacheNames() {
        return Arrays.asList(FF4jJCacheManager.CACHENAME_FEATURES, FF4jJCacheManager.CACHENAME_PROPERTIES);
    }

    /** {@inheritDoc} */
    @Override
    public void destroyCache(String cacheName) {
        getIgnite().destroyCache(cacheName);
    }

    /** {@inheritDoc} */
    @Override
    public void enableManagement(String cacheName, boolean enabled) {
        throw new UnsupportedOperationException("Not implemented for FF4J");
    }

    /** {@inheritDoc} */
    @Override
    public void enableStatistics(String cacheName, boolean enabled) {
        throw new UnsupportedOperationException("Not implemented for FF4J");
    }

    /** {@inheritDoc} */
    @Override
    public void close() {
        getIgnite().close();
     }

    /** {@inheritDoc} */
    @Override
    public boolean isClosed() {
        throw new UnsupportedOperationException("Not implemented for FF4J");
    }

    /** {@inheritDoc} */
    @Override
    public <T> T unwrap(Class<T> clazz) {
        throw new UnsupportedOperationException("Not implemented for FF4J");
    }

}
