package com.bps.ejercicio.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bps.ejercicio.models.Cliente;

@Repository
@Transactional
public class ClienteDaoImp implements ClienteDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public List<Cliente> getClientes() {
        String query = "FROM Cliente";
        return entityManager.createQuery(query).getResultList();
    }
    
    @Override
    @Transactional
    public Cliente getClienteId(Integer id) {
 
        String query = "FROM Cliente where clienteId = :id and estado = '1'";
        List<Cliente> lista = entityManager.createQuery(query)
                .setParameter("id", id)
                .getResultList();
        if (lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
        

    }

	@Override
	@Transactional
	public void editar(Cliente cliente) throws Exception {
		try {
			Query query2 = entityManager.createQuery("UPDATE Cliente c SET c.nombre = :nombre,"
					+ " c.direccion =:direccion, c.telefono = :telefono, c.contrasena= :contrasena,  " +
			       "c.estado= :estado  WHERE clienteid = :id")
			      .setParameter("nombre", cliente.getNombre())
			      .setParameter("direccion", cliente.getDireccion())
			      .setParameter("telefono", cliente.getTelefono())
			      .setParameter("contrasena", cliente.getContrasena())
			      .setParameter("estado", cliente.getEstado())
			      .setParameter("id", cliente.getId());

	        int rowsUpdated = query2.executeUpdate();
	        System.out.println("entities Updated: " + rowsUpdated);
		} catch (Exception e) {
			System.out.println("Error modificando clientes: " + e.getMessage());
		}
		
		
	}

	@Override
	@Transactional
	public void registrar(Cliente cliente) throws Exception {
		try {
			entityManager.merge(cliente);
		} catch (Exception e) {
			System.out.println("Error registrando clientes: " + e.getMessage());
		}
		
	}

}
