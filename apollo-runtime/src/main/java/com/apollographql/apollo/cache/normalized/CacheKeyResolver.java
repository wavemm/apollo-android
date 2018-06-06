package com.apollographql.apollo.cache.normalized;

import com.apollographql.apollo.api.ResponseField;
import com.apollographql.apollo.api.Operation;

import java.util.Map;

import javax.annotation.Nonnull;

/**
 * Resolves a cache key for a JSON object.
 */
public abstract class CacheKeyResolver {
  public static final CacheKeyResolver DEFAULT = new CacheKeyResolver() {
    @Nonnull @Override
    public CacheKey fromFieldRecordSet(@Nonnull ResponseField field, @Nonnull Map<String, Object> recordSet) {
      return CacheKey.NO_KEY;
    }

    @Nonnull @Override
    public CacheKey fromFieldArguments(@Nonnull ResponseField field, @Nonnull Operation.Variables variables) {
      return CacheKey.NO_KEY;
    }
  };
  public static final CacheKey QUERY_ROOT_KEY = CacheKey.from("QUERY_ROOT");
  public static final CacheKey MUTATION_ROOT_KEY = CacheKey.from("MUTATION_ROOT");

  public static CacheKey rootKeyForOperation(@Nonnull Operation operation) {
    if (operation instanceof Query) {
      return QUERY_ROOT_KEY;
    } else if (operation instanceof Mutation) {
      return MUTATION_ROOT_KEY;
    }
    throw new IllegalArgumentException("Unknown operation type.");
  }

  @Nonnull public abstract CacheKey fromFieldRecordSet(@Nonnull ResponseField field,
      @Nonnull Map<String, Object> recordSet);

  @Nonnull public abstract CacheKey fromFieldArguments(@Nonnull ResponseField field,
      @Nonnull Operation.Variables variables);
}
