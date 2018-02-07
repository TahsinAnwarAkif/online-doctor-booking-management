package net.therap.onlinedoctorbookingmanagement.dao;

import javafx.util.Pair;
import net.therap.onlinedoctorbookingmanagement.domain.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author anwar
 * @since 2/11/18
 */
@Repository
public class PatientDao {

    @Autowired
    private CommonUtilsForDao commonUtilsForDao;

    public Patient findOneBySSN(String ssn) {
        return (Patient) commonUtilsForDao.findOneByColumnNames(Patient.class,
                new Pair<>("ssn", ssn));
    }
}