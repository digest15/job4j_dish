package ru.job4j.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Dish;
import ru.job4j.repository.DishRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

    @Override
    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    @Override
    public Optional<Dish> findById(int id) {
        return dishRepository.findById(id);
    }

    @Override
    public Optional<Dish> findByName(String name) {
        return dishRepository.findByName(name);
    }

    @Override
    public Optional<Dish> save(Dish dish) {
        Optional<Dish> res = Optional.empty();
        try {
            dishRepository.save(dish);
            res = Optional.of(dish);
        } catch (Exception e) {
            log.error("Save or Update was wrong", e);
        }
        return res;
    }

    @Override
    public boolean delete(int id) {
        Optional<Dish> dish = dishRepository.findById(id);
        dish.ifPresent(dishRepository::delete);
        return dish.isPresent();
    }
}
