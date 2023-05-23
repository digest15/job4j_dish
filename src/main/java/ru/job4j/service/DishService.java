package ru.job4j.service;

import ru.job4j.domain.Dish;

import java.util.List;
import java.util.Optional;

public interface DishService {

    List<Dish> findAll();

    Optional<Dish> findById(int id);

    Optional<Dish> findByName(String name);

    Optional<Dish> save(Dish dish);

    boolean delete(int id);

}
