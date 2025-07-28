package net.hubek.nn.currencyaccount.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ManagedEntityResolver {

    private final EntityManager entityManager;

    public <T> T getManagedEntity(Class<T> entityClass, Object primaryKey) {
        T entity = entityManager.find(entityClass, primaryKey);

        if (entity != null && entityManager.contains(entity)) {
            return entity;
        }

        return null;
    }
}
