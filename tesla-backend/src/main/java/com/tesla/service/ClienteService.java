package com.tesla.service;

import com.tesla.model.Cliente;
import com.tesla.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerClientePorId(Long idCliente) { // Cambio aquí
        return clienteRepository.findById(idCliente);
    }

    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void eliminarCliente(Long idCliente) { // Cambio aquí
        clienteRepository.deleteById(idCliente);
    }
}
