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

    /**
     * Lista todos los clientes de la base de datos.
     * 
     * @return Lista de clientes.
     */
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    /**
     * Obtiene un cliente por su ID.
     * 
     * @param idCliente ID del cliente a buscar.
     * @return Optional con el cliente si existe.
     */
    public Optional<Cliente> obtenerClientePorId(Long idCliente) {
        return clienteRepository.findById(idCliente);
    }

    /**
     * Guarda un nuevo cliente en la base de datos.
     * 
     * @param cliente Cliente a guardar.
     * @return Cliente guardado.
     */
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Modifica un cliente existente en la base de datos.
     * 
     * @param idCliente ID del cliente a modificar.
     * @param clienteDatos Datos actualizados del cliente.
     * @return Cliente modificado.
     * @throws RuntimeException si el cliente no existe.
     */
    public Cliente modificarCliente(Long idCliente, Cliente clienteDatos) {
        return clienteRepository.findById(idCliente).map(clienteExistente -> {
            clienteExistente.setNif(clienteDatos.getNif());
            clienteExistente.setNombre(clienteDatos.getNombre());
            clienteExistente.setDireccion(clienteDatos.getDireccion());
            clienteExistente.setCiudad(clienteDatos.getCiudad());
            clienteExistente.setTelefono(clienteDatos.getTelefono());
            return clienteRepository.save(clienteExistente);
        }).orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + idCliente));
    }

    /**
     * Elimina un cliente por su ID.
     * 
     * @param idCliente ID del cliente a eliminar.
     */
    public void eliminarCliente(Long idCliente) {
        clienteRepository.deleteById(idCliente);
    }
}
