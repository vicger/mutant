package com.exercise.cache;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

/**
 * Created by vgerb on 3/12/18.
 */
@Component
public class MutantCache {
    private ConcurrentHashMap<Long, Boolean> cache;

    public MutantCache() {
        cache = new ConcurrentHashMap<>();
    }

    public boolean contains(Long hash) {
        return cache.containsKey(hash);
    }

    public boolean get(Long hash) {
        return cache.get(hash);
    }

    public void put(Long hash, boolean isMutant) {
        cache.put(hash, isMutant);
    }
}
