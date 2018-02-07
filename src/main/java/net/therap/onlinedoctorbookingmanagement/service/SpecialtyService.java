package net.therap.onlinedoctorbookingmanagement.service;

import net.therap.onlinedoctorbookingmanagement.dao.SpecialtyDao;
import net.therap.onlinedoctorbookingmanagement.domain.Specialty;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS;
import net.therap.onlinedoctorbookingmanagement.validators.OtherSpecialtyValidators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author anwar
 * @since 2/8/18
 */
@Service
public class SpecialtyService {

    @Autowired
    private SpecialtyDao specialtyDao;

    @Autowired
    private OtherSpecialtyValidators otherSpecialtyValidators;

    public List<Specialty> findAll() {
        return specialtyDao.findAll();
    }

    public Specialty findOneOnlyIfValid(long id) throws BadRequestException {
        return otherSpecialtyValidators.areAllSpecialtiesValid(id)[0];
    }

    public void saveOrUpdate(Specialty specialty, DB_OPERATIONS operation) throws BadRequestException {
        specialtyDao.saveOrUpdate(operation, specialty);
    }

    public String[] deleteSpecialtiesAndGetNames(Specialty... specialties) {

        long[] ids = new long[specialties.length];

        int idx = 0;

        for (Specialty specialty : specialties) {
            ids[idx++] = specialty.getId();
        }

        String[] names = specialtyDao.findAllNamesByGivenIds(ids);

        specialtyDao.delete(specialties);

        return names;
    }

    public Specialty[] checkIfAllSpecialtiesValid(long[] ids) throws BadRequestException {
        return otherSpecialtyValidators.areAllSpecialtiesValid(ids);
    }

    public void isAnySpecialtyAssociatedWithDoctors(Specialty[] specialties) throws BadRequestException {
        otherSpecialtyValidators.isAnySpecialtyAssociatedWithDoctors(specialties);
    }
}