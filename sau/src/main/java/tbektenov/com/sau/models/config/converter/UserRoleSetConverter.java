package tbektenov.com.sau.models.config.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tbektenov.com.sau.models.user.UserRole;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class UserRoleSetConverter implements AttributeConverter<Set<UserRole>, String> {

    @Override
    public String convertToDatabaseColumn(Set<UserRole> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        return attribute.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

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

