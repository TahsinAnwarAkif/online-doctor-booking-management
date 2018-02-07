# Online Doctor Booking Management

## Overview: 
This is a multi-role based online doctor booking management application with a custom automated scheduling approach where:-

## Doctor:

    * Can enroll himself and update ONLY his profile

    * Can view/update/delete his appointments

    * Can create his speciality field if not found in database

## Patient:

    * Can enroll himself and update ONLY his profile

    * Can view all available doctors

    * Can view/create/delete his appointments to available doctors

    * Appointment date/time can be generated automatically (i.e. Without inputting) OR

    * Patient can create appointment with specific date/time IF valid/available for that doctor

    * Cannot view other patients or others' appointments (i.e. HIPAA Violation)


## Admin:

    * Is enrolled only by Super-admin, cannot register/update his profile

    * Can perform all of Super-admin's tasks EXCEPT

        -> View/Update role actions of anyone

        -> CRUD over admins

## Super-admin:

    * Cannot CRUD over himself, already created by developer using SQL

    * Can CRUD over doctors/patients/admins/specialties/appointments

    * Can update role actions of role types (i.e. Doctor/Patient/Admin)

    * Thus he can block users of specific role type of performing a particular role action.
