package net.therap.onlinedoctorbookingmanagement.validators;

import net.therap.onlinedoctorbookingmanagement.domain.Doctor;
import net.therap.onlinedoctorbookingmanagement.domain.Specialty;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.OPERATION_RESULTS.DOCTOR_ASSOCIATED_WITH_SPECIALTY_ERROR;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.UTILS.NAME_PARAM;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.isListEmpty;

/**
 * @author anwar
 * @since 2/12/18
 */
@Service
public class OtherSpecialtyValidators {

    @Autowired
    private CommonValidator commonValidator;

    public Specialty[] areAllSpecialtiesValid(long... ids) throws BadRequestException {
        return (Specialty[]) commonValidator.areAllEntitiesValidThenReturnAll(new Specialty(), ids);
    }

    public void isAnySpecialtyAssociatedWithDoctors(Specialty[] specialties) throws BadRequestException {

        for (Specialty specialty : specialties) {

            if (!isListEmpty(specialty.getDoctors())) {

                Set<String> doctorNames = new HashSet<>(specialty.getDoctors().size());

                for (Doctor doctor : specialty.getDoctors()) {
                    doctorNames.add(doctor.getUsername());
                }

                throw new BadRequestException(DOCTOR_ASSOCIATED_WITH_SPECIALTY_ERROR.getMessage()
                        .replace(NAME_PARAM.getValue(), String.join(",", doctorNames)));
            }
        }
    }
}