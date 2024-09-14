package com.std.ec.controller;


import com.std.ec.model.dto.ClienteDto;
import com.std.ec.model.entity.Cliente;
import com.std.ec.model.payload.MensajeResponse;
import com.std.ec.service.ICliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    private ICliente clienteService;

    // CREATE
    @PostMapping("cliente")
    public ResponseEntity<?> create(@RequestBody ClienteDto clienteDto) {

        Cliente clienteSave = null;
        try {
            clienteSave = clienteService.save(clienteDto);

            clienteDto = ClienteDto.builder()
                    .idCliente(clienteSave.getIdCliente())
                    .nombre(clienteSave.getNombre())
                    .apellido(clienteSave.getApellido())
                    .correo(clienteSave.getCorreo())
                    .fechaRegistro(clienteSave.getFechaRegistro())
                    .build();

            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("Guardado correctamente")
                            .object(clienteDto)
                            .build()
                    , HttpStatus.CREATED);

        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // UPDATE
    @PutMapping("cliente")
    public ResponseEntity<?> update(@RequestBody ClienteDto clienteDto) {

        Cliente clienteUpdate = null;
        try {
            clienteUpdate = clienteService.save(clienteDto);

            clienteDto = ClienteDto.builder()
                    .idCliente(clienteUpdate.getIdCliente())
                    .nombre(clienteUpdate.getNombre())
                    .apellido(clienteUpdate.getApellido())
                    .correo(clienteUpdate.getCorreo())
                    .fechaRegistro(clienteUpdate.getFechaRegistro())
                    .build();

            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("Guardado correctamente")
                            .object(clienteDto)
                            .build()
                    , HttpStatus.CREATED);

        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE
    @DeleteMapping("cliente/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {

        try {
            Cliente clienteDelete = clienteService.findById(id);
            clienteService.delete(clienteDelete);
            return new ResponseEntity<>(clienteDelete, HttpStatus.NO_CONTENT);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // SHOWBYID
    @GetMapping("cliente/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {

        Cliente cliente = clienteService.findById(id);

        if (cliente == null) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("El registro que intenta buscar no existe!!")
                            .object(null)
                            .build()
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("")
                        .object(ClienteDto.builder()
                                .idCliente(cliente.getIdCliente())
                                .nombre(cliente.getNombre())
                                .apellido(cliente.getApellido())
                                .correo(cliente.getCorreo())
                                .fechaRegistro(cliente.getFechaRegistro())
                                .build())
                        .build()
                , HttpStatus.OK); 
    }
}
