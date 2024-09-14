package com.std.ec.service;

import com.std.ec.model.dto.ClienteDto;
import com.std.ec.model.entity.Cliente;

public interface ICliente {

    Cliente save(ClienteDto cliente);

    Cliente findById(Integer id);

    void delete(Cliente cliente);
}
