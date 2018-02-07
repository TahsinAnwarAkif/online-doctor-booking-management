package net.therap.onlinedoctorbookingmanagement.dao;

import javafx.util.Pair;
import net.therap.onlinedoctorbookingmanagement.domain.Specialty;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.convertStringListToArray;

/**
 * @author anwar
 * @since 2/8/18
 */
@Repository
public class SpecialtyDao {

    @Autowired
    private CommonUtilsForDao commonUtilsForDao;

    public List<Specialty> findAll() {
        return (List<Specialty>) commonUtilsForDao.findAllByColumnNames(Specialty.class);
    }

    public String[] findAllNamesByGivenIds(long[] ids) {
        return convertStringListToArray(commonUtilsForDao
                .findSpecificColumnValuesOfEntity(Specialty.class,
                        "name",
                        ids));
    }

    public Specialty findOne(long id) {
        return (Specialty) commonUtilsForDao.findOne(Specialty.class, id);
    }

    @Transactional
    public void saveOrUpdate(DB_OPERATIONS operation, Specialty specialty) throws BadRequestException {
        commonUtilsForDao.saveOrUpdate(operation, specialty);
    }

    public Specialty findOneByName(String name) {
        return (Specialty) commonUtilsForDao.findOneByColumnNames(Specialty.class, new Pair<>("name", name));
    }

    @Transactional
    public void delete(Specialty... specialties) {
        commonUtilsForDao.delete(specialties);
    }
}