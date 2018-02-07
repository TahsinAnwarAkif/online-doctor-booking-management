package net.therap.onlinedoctorbookingmanagement.utilities.utilmethods;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author anwar
 * @since 2/12/18
 */

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> roleActions) {
        return String.join(",", roleActions);
    }

    @Override
    public List<String> convertToEntityAttribute(String roleActions) {
        return new ArrayList<>(Arrays.asList(roleActions.split(",")));
    }
}