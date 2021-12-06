package com.github.sjlian014.jlms.controller;

import com.github.sjlian014.jlms.model.Major;
import com.github.sjlian014.jlms.model.Student;
import com.github.sjlian014.jlms.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/majors")
public class MajorController {
    private final MajorService majorService;

    @Autowired
    public MajorController(MajorService majorService) {
        this.majorService = majorService;
    }

    @GetMapping
    public List<Major> getMajors() {
        return  majorService.getAll();
    }

    @PostMapping
    public Major postMajor(@RequestBody Major major) {
        return majorService.add(major);
    }

    @PutMapping(path = "{majorID}")
    public Major putMajor(@PathVariable("majorID") Long id, @RequestBody Major major) {
        return majorService.update(id, major);
    }

    @DeleteMapping(path = "{majorID}")
    public void deleteMajor(@PathVariable("majorID") Long id) {
        majorService.delete(id);
    }
}
