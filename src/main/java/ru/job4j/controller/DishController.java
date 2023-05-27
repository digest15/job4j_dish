package ru.job4j.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.domain.Dish;
import ru.job4j.service.DishService;

import java.util.*;

@RestController
@AllArgsConstructor
@Slf4j
public class DishController {

    private final DishService dishService;

    @GetMapping("/")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Dish> finaAll() {
        return dishService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Dish findById(@PathVariable int id) {
        Optional<Dish> dish = dishService.findById(id);
        if (dish.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Not found Dish with id %d", id)
            );
        }
        return dish.get();
    }

    @GetMapping("/name/{name}")
    @ResponseStatus(code = HttpStatus.OK)
    public DishDTO findByName(@PathVariable String name) {
        return dishService.findByName(name)
                .map(dish -> new DishDTO(dish.getName(), dish.getDescription()))
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("Not found Dish with name %s", name)
                        )
                );
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.OK)
    public Dish create(@RequestBody Dish dish) {
        Optional<Dish> saved = dishService.save(dish);
        if (saved.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There was a problem when save Dish");
        }
        return saved.get();
    }

    @PutMapping("/")
    @ResponseStatus(code = HttpStatus.OK)
    public Dish update(@RequestBody Dish dish) {
        Optional<Dish> updated = dishService.save(dish);
        if (updated.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There was a problem when update Dish");
        }
        return updated.get();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        boolean deleted = dishService.delete(id);
        if (!deleted) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("There was a problem when delete Dish by id: %d", id)
            );
        }
        return deleted
                ? ResponseEntity.ok(true)
                : ResponseEntity.badRequest()
                    .body(String.format("There was a problem when delete Dish by id: %d", id));
    }

}
