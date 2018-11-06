package config;

import dao.UniversityDAO;
import dto.UserDto;
import entity.GroupEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static String dateFormat(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.LLL.yyyy HH:mm");
        return localDateTime.format(formatter);
    }

    public static List<GroupEntity> getAvailableGroups(UserDto user, UniversityDAO universityDAO) {
        Long universityId = user.getGroupEntity().getDepartment().getFaculty().getUniversity().getId();
        return universityDAO.findById(universityId).get().getFaculties().stream()
                .flatMap(f -> f.getDepartments().stream()
                        .flatMap(d -> d.getGroups().stream()))
                .collect(Collectors.toList());
    }
}
