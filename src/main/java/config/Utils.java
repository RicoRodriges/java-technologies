package config;

import dao.GroupDAO;
import dao.UniversityDAO;
import dto.UserDto;
import entity.GroupEntity;
import entity.University;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static String dateFormat(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.LLL.yyyy HH:mm");
        return localDateTime.format(formatter);
    }

    public static List<GroupEntity> getAvailableGroups(UserDto user, UniversityDAO universityDAO, GroupDAO groupDAO) {
        University university = user.getUniversity();
        if (university == null) {
            return groupDAO.findAll();
        } else {
            Long universityId = university.getId();
            return universityDAO.findById(universityId).get().getFaculties().stream()
                    .flatMap(f -> f.getDepartments().stream()
                            .flatMap(d -> d.getGroups().stream()))
                    .collect(Collectors.toList());
        }
    }
}
