package com.luisfuturist.core.registries;

import java.util.HashMap;
import java.util.Map;

import com.luisfuturist.core.models.Feature;

public class FeatureRegistry {

    private static Map<Class<? extends Feature>, Feature> registry = new HashMap<>();

    public static void registerFeature(Class<? extends Feature> featureClass, Feature feature) {
        registry.put(featureClass, feature);
    }

    public static Feature getFeature(Class<? extends Feature> featureClass) {
        return registry.get(featureClass);
    }
}
