package net.therap.onlinedoctorbookingmanagement.validators;

import net.therap.onlinedoctorbookingmanagement.domain.Doctor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.text.ParseException;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.OPERATION_RESULTS.DAILY_SCHEDULE_END_ERROR;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.UTILS.CUSTOM_ERROR_CODE;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.UTILS.MINIMUM_TIME_ALLOCATED_FOR_A_PATIENT;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.checkIfAnObjectIsNull;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.TimeUtils.calculateDiffBetweenTimesInMinutes;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.TimeUtils.getDateTimeCombinedAsString;

/**
 * @author anwar
 * @since 2/9/18
 */
@Service
public class OtherDoctorValidators {

    public Integer validateScheduleThenGetAllocatedTime(Doctor doctor, Errors errors) {

        Long timeDifference;

        try {

            timeDifference = calculateDiffBetweenTimesInMinutes(doctor.getDailyScheduleStart(), doctor.getDailyScheduleEnd());

        } catch (ParseException e) {
            return null;
        }

        Long timeAllocatedForAPatient = timeDifference / doctor.getEstimatedVisitsPerDay();

        if (timeAllocatedForAPatient
                >= Long.parseLong(MINIMUM_TIME_ALLOCATED_FOR_A_PATIENT.getValue())) {
            return timeAllocatedForAPatient.intValue();
        }

        if (!checkIfAnObjectIsNull(errors)) {
            errors.rejectValue("dailyScheduleEnd",
                    CUSTOM_ERROR_CODE.getValue(), DAILY_SCHEDULE_END_ERROR.getMessage());
        }

        return null;
    }

    public boolean checkIfUpdatedScheduleIsDifferentThanLastOne(Doctor doctorBeforeUpdate, Doctor doctorAfterUpdate) {
        return getDateTimeCombinedAsString(doctorAfterUpdate.getJoiningDate(),
                doctorAfterUpdate.getDailyScheduleStart())
                .equals(getDateTimeCombinedAsString(doctorBeforeUpdate.getJoiningDate(),
                        doctorBeforeUpdate.getDailyScheduleStart()));
    }
}