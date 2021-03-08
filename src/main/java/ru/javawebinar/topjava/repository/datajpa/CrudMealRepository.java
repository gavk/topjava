package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    List<Meal> getMealsByUser_IdOrderByDateTimeDesc(int id);

    Meal getMealsByIdAndUser_Id(int id, int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    int deleteMealByIdAndUser_id(@Param("id") int id, @Param("userId") int userId);

    List<Meal> findMealsByDateTimeAfterAndDateTimeBeforeAndUser_IdOrderByDateTimeDesc(
            LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);
}
