package com.github.sjlian014.jlms.service;

import com.github.sjlian014.jlms.dao.MinorRepository;
import com.github.sjlian014.jlms.model.Major;
import com.github.sjlian014.jlms.model.Minor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MinorService {
    private MinorRepository minorRepository;

    @Autowired
    public MinorService(MinorRepository minorRepository) {
        this.minorRepository = minorRepository;
    }

    public List<Minor> getAll() {
        return minorRepository.findAll();
    }

    public Minor add(Minor minor) {
        return minorRepository.save(minor);
    }

    public void delete(Long id) {
        minorRepository.deleteById(id);
    }

    @Transactional
    public Minor update(Long id, Minor newMinor) {
        return minorRepository.findById(id).map(oldMinor -> {
            oldMinor.copyFrom(newMinor);
            return oldMinor;
        }).orElseThrow(()-> new IllegalStateException("specified minor not found"));
    }

}
