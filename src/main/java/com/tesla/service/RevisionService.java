package com.tesla.service;

import com.tesla.model.Revision;
import com.tesla.repository.RevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RevisionService {

    @Autowired
    private RevisionRepository revisionRepository;

    public List<Revision> listarRevisiones() {
        return revisionRepository.findAll();
    }

    public Optional<Revision> obtenerRevisionPorId(Long id) {
        return revisionRepository.findById(id);
    }

    public Revision guardarRevision(Revision revision) {
        return revisionRepository.save(revision);
    }

    public void eliminarRevision(Long id) {
        revisionRepository.deleteById(id);
    }
}

