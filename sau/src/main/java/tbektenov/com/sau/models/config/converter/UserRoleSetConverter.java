package tbektenov.com.sau.models.config.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tbektenov.com.sau.models.user.UserRole;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * JPA AttributeConverter for converting a Set of UserRole enums to a String
 * and vice versa, enabling the storage of enum sets in a single database column.
 */
@Converter
public class UserRoleSetConverter implements AttributeConverter<Set<UserRole>, String> {

    /**
     * Converts a Set of UserRole enums to a comma-separated String for database storage.
     *
     * @param attribute the Set of UserRole enums to convert
     * @return a comma-separated String representing the UserRole enums
     */
    @Override
    public String convertToDatabaseColumn(Set<UserRole> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        return attribute.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    /**
     * Converts a comma-separated String from the database back to a Set of UserRole enums.
     *
     * @param dbData the String from the database to convert
     * @return a Set of UserRole enums represented by the String
     */
    @Override
    public Set<UserRole> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return EnumSet.noneOf(UserRole.class);
        }
        return Arrays.stream(dbData.split(","))
                .map(UserRole::valueOf)
                .collect(Collectors.toSet());
    }
}
