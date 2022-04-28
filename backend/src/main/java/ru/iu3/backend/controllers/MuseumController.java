package ru.iu3.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import ru.iu3.backend.models.Museum;
import ru.iu3.backend.repositories.MuseumRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class MuseumController {
    @Autowired
    MuseumRepository museumRepository;

    @GetMapping("/museums")
    public List getAllCountries() {
        return museumRepository.findAll();
    }

    /**
     * Метод, который добавляет country в таблиц
     * RequestBody - это наш экземпляр (через curl передаётся в виде JSON)
     * @param museum - наш экземпляр класса museum
     * @return - статус (ОК/НЕ ОК)
     */
    @PostMapping("/museums")
    public ResponseEntity<Object> createMuseum(@RequestBody Museum museum) throws Exception {
        try {
            // Попытка сохранить что-либо в базу данных
            Museum newMusem = museumRepository.save(museum);
            return new ResponseEntity<Object>(newMusem, HttpStatus.OK);
        } catch (Exception exception) {
            // Указываем тип ошибки
            String error;
            if (exception.getMessage().contains("ConstraintViolationException")) {
                error = "museumAlreadyExists";
            } else {
                error = exception.getMessage();
            }

            Map<String, String> map = new HashMap<>();
            map.put("error", error + "\n");

            return ResponseEntity.ok(map);
        }
    }

    /**
     * Метод, который обновляет данные в таблице
     * @param museumID - указываем id по которому будем обновлять данные
     * @param museumDetails - сводки по Museum
     * @return - ОК/НЕ ОК
     */
    @PutMapping("/museums/{id}")
    public ResponseEntity<Museum> updateCountry(@PathVariable(value = "id") Long museumID,
                                                @RequestBody Museum museumDetails) {
        Museum museum = null;
        Optional<Museum> cc = museumRepository.findById(museumID);

        if (cc.isPresent()) {
            museum = cc.get();

            museum.name = museumDetails.name;
            museum.location = museumDetails.location;

            museumRepository.save(museum);
            return ResponseEntity.ok(museum);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Museum not found");
        }
    }

    /**
     * Метод, который удаляет информацию из базы данных
     * @param museumID - по какому ID-шнику удаляем информацию
     * @return - возвращает true, если удалено успешно, false - в противном случае
     */
    @DeleteMapping("/museums/{id}")
    public ResponseEntity<Object> deleteCountry(@PathVariable(value = "id") Long museumID) {
        Optional<Museum> museum = museumRepository.findById(museumID);
        Map<String, Boolean> resp = new HashMap<>();

        // Возвратит true, если объект существует (не пустой)
        if (museum.isPresent()) {
            museumRepository.delete(museum.get());
            resp.put("deleted", Boolean.TRUE);
        } else {
            resp.put("deleted", Boolean.FALSE);
        }

        return ResponseEntity.ok(resp);
    }
}