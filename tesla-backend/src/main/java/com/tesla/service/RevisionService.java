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

    public Optional<Revision> obtenerRevisionPorId(Long idRevision) { 
        return revisionRepository.findById(idRevision);
    }

    public Optional<Revision> actualizarRevision(Long idRevision, Revision revisionActualizada) {
        Optional<Revision> revisionExistente = revisionRepository.findById(idRevision);
        
        if (revisionExistente.isPresent()) {
            Revision revision = revisionExistente.get();
            
            // Actualizar los campos necesarios con los nombres correctos
            revision.setFecha(revisionActualizada.getFecha());
            revision.setCambioFiltro(revisionActualizada.isCambioFiltro());  
            revision.setCambioAceite(revisionActualizada.isCambioAceite());  
            revision.setCambioFrenos(revisionActualizada.isCambioFrenos());  
            revision.setObservaciones(revisionActualizada.getObservaciones());  
    
            return Optional.of(revisionRepository.save(revision)); // Guardar los cambios
        } else {
            return Optional.empty(); // Si no se encuentra, devolver vacío
        }
    }

    public Revision guardarRevision(Revision revision) {
        return revisionRepository.save(revision);
    }

    public void eliminarRevision(Long idRevision) { 
        revisionRepository.deleteById(idRevision);
    }
}
