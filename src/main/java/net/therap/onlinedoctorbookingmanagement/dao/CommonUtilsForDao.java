package net.therap.onlinedoctorbookingmanagement.dao;

import javafx.util.Pair;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.OPERATION_RESULTS.BAD_REQUEST_ERROR;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.autoBoxLongArray;

/**
 * @author anwar
 * @since 1/17/18
 */
@Repository
public class CommonUtilsForDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Object findOneByColumnNames(Class entityClass, Pair<String, Object>... columns) {

        Object object;

        try {
            TypedQuery preparedQuery = prepareQueryWithRestrictions(entityClass, columns);

            object = preparedQuery != null
                    ? preparedQuery.getSingleResult()
                    : null;
        } catch (Exception e) {
            return null;
        }
        return object;
    }

    public List findAllByColumnNames(Class entityClass, Pair<String, Object>... columns) {

        List<Object> objects = new ArrayList<>();

        try {
            TypedQuery preparedQuery = prepareQueryWithRestrictions(entityClass, columns);

            if (preparedQuery != null) {
                objects.addAll(preparedQuery.getResultList());
            } else {
                return null;
            }
        } catch (NoResultException e) {
            return null;
        }
        return objects;
    }

    public Object findOne(Class entityClass, long id) {
        return entityManager.find(entityClass, id);
    }

    @Transactional
    public void saveOrUpdate(DB_OPERATIONS operation, Object entity) throws BadRequestException {

        switch (operation) {

            case SAVE: {
                entityManager.persist(entity);
                entityManager.flush();
                break;
            }

            case UPDATE: {
                entityManager.merge(entity);
                break;
            }

            default: {
                throw new BadRequestException(BAD_REQUEST_ERROR.getMessage());
            }
        }
    }

    @Transactional
    public void delete(Object... entities) {

        for (Object entity : entities) {
            entityManager.remove(entityManager.contains(entity)
                    ? entity
                    : entityManager.merge(entity));
        }
    }

    public List<String> findSpecificColumnValuesOfEntity(Class entityClass, String column, long... ids) {

        List<Long> idsParam = Arrays.asList(autoBoxLongArray(ids));

        Query query = entityManager
                .createQuery("SELECT entity." + column
                        + " FROM " + entityClass.getSimpleName() + " entity "
                        + "WHERE entity.id IN :ids", String.class);

        query.setParameter("ids", idsParam);

        return query.getResultList();
    }

    private TypedQuery prepareQueryWithRestrictions(Class entityClass, Pair<String, Object>... columns) {

        StringBuilder query = new StringBuilder("SELECT entity FROM " + entityClass.getSimpleName() + " entity ");

        if (columns.length != 0) {
            query.append(" WHERE ");
        }

        StringBuilder restrictions = new StringBuilder();

        int idx = 1;

        for (Pair<String, Object> column : columns) {

            StringBuilder conjunction = new StringBuilder((idx != columns.length) ? " AND " : "");

            restrictions.append("entity.")
                    .append(column.getKey())
                    .append(" = :")
                    .append(column.getKey())
                    .append(conjunction);
            idx++;
        }

        query.append(restrictions);

        TypedQuery preparedQuery = entityManager.createQuery(new String(query), entityClass);

        for (Pair<String, Object> column : columns) {
            preparedQuery.setParameter(column.getKey(), column.getValue());
        }

        return preparedQuery;
    }
}