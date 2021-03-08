package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudMealRepository;
    private final CrudUserRepository crudUserRepository;

    public DataJpaMealRepository(CrudMealRepository crudMealRepository, CrudUserRepository crudUserRepository) {
        this.crudMealRepository = crudMealRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew() || get(meal.getId(), userId) != null) {
            meal.setUser(crudUserRepository.getOne(userId));
        } else {
            throw new NotFoundException("Not found entity with id=" + meal.getId());
        }
        return crudMealRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudMealRepository.deleteMealByIdAndUser_id(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudMealRepository.getMealsByIdAndUser_Id(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudMealRepository.getMealsByUser_IdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudMealRepository
                .findMealsByDateTimeAfterAndDateTimeBeforeAndUser_IdOrderByDateTimeDesc(
                        startDateTime, endDateTime, userId);
    }
}
