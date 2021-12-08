package com.github.sjlian014.jlms.service;

import com.github.sjlian014.jlms.dao.MajorRepository;
import com.github.sjlian014.jlms.model.Major;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MajorService {
    private MajorRepository majorRepository;

    @Autowired
    public MajorService(MajorRepository majorRepository) {
        this.majorRepository = majorRepository;
    }

    public List<Major> getAll() {
        return majorRepository.findAll();
    }

    public Major add(Major major) {
       return majorRepository.save(major);
    }

    public void delete(Long id) {
        majorRepository.deleteById(id);
    }

    @Transactional
    public Major update(Long id, Major newMajor) {
        return majorRepository.findById(id).map(oldMajor -> {
            oldMajor.copyFrom(newMajor);
            return oldMajor;
        }).orElseThrow(()-> new IllegalStateException("specified major not found"));
    }
}
