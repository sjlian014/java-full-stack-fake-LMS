package com.github.sjlian014.jlms.controller;

import com.github.sjlian014.jlms.model.Major;
import com.github.sjlian014.jlms.model.Minor;
import com.github.sjlian014.jlms.service.MajorService;
import com.github.sjlian014.jlms.service.MinorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/minors")
public class MinorController {
    private final MinorService minorService;

    @Autowired
    public MinorController(MinorService minorService) {
        this.minorService = minorService;
    }

    @GetMapping
    public List<Minor> getMinors() {
        return minorService.getAll();
    }

    @PostMapping
    public Minor postMinor(@RequestBody Minor minor) {
        return minorService.add(minor);
    }

    @PutMapping(path = "{minorID}")
    public Minor putMinor(@PathVariable("minorID") Long id, @RequestBody Minor minor) {
        return minorService.update(id, minor);
    }

    @DeleteMapping(path = "{minorID}")
    public void deleteMinor(@PathVariable("minorID") Long id) {
        minorService.delete(id);
    }
}
