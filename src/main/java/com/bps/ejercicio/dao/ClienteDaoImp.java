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
    
  
    
   /* public List<Persona> getPersonasSK() {
        String query = "FROM Persona WHERE grupo = 'SK'";
        return entityManager.createQuery(query).getResultList();
    }
    
    public List<Persona> getPersonasTH() {
        String query = "FROM Persona WHERE grupo = 'TH'";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void registrar(Persona persona)  {
    	persona.setLocal("CCI");
    	if(obtenerBeneficio(persona)) {
    		entityManager.merge(persona);
    	}
        
    }
    
    @SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	public boolean obtenerBeneficio(Persona persona)  {
    	List<Beneficio> beneficios;
    	List<Persona> personasEncontradas;
    	if(persona.getGrupo().equals("SK")) {
    		beneficios= FilesUtil.leerArchivoSK();
    		personasEncontradas = getPersonasSK();
    	}else {
    		beneficios= FilesUtil.leerArchivoTH();
    		personasEncontradas = getPersonasTH();
    	}
		
		if (CollectionUtils.isNotEmpty(beneficios)) {
			 
			Collection<String> registros = (Collection<String>) CollectionUtils.collect(personasEncontradas,
					new Transformer<Persona, String>() {
						@Override
						public String transform(Persona input) {
							return input.getBeneficio();
						}
					});

			for (Beneficio be : beneficios) {
				if (CollectionUtils.isNotEmpty(registros)) {
					String existe = (String) CollectionUtils.find(registros, new Predicate() {

						@Override
						public boolean evaluate(Object arg0) {
							String registro = (String) arg0;
							if (registro.equals(be.getName())) {
								return true;
							}

							return false;
						}

					});

					if (existe == null) {
						persona.setBeneficio(be.getName());
						return true;
					}
				}else {
					persona.setBeneficio(be.name);
					return true;
				}

			}

		}
		return false;

	}

    @Override
    public void editar(Persona persona) {

        Query query2 = entityManager.createQuery("UPDATE Persona p SET p.nombre = :nombre, p.email =:valor, p.telefono = :cantidad " +
        "  WHERE ID = :id")
                .setParameter("nombre", persona.getNombre())
                .setParameter("valor", persona.getEmail())
                .setParameter("cantidad", persona.getTelefono())
                .setParameter("id", persona.getId());

        int rowsUpdated = query2.executeUpdate();
        System.out.println("entities Updated: " + rowsUpdated);

    }

    @Override
    public Persona obtenerPersonaPorID(Integer id) {
        String query = "FROM Persona WHERE ID = :id";
        List<Persona> lista = entityManager.createQuery(query)
                .setParameter("id", id)
                .getResultList();
        if (lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
    }

	@Override
	public void deletePersonaId(Integer id) {
		// TODO Auto-generated method stub
		 Query query2 = entityManager.createQuery("DELETE Persona  WHERE ID = :id")
			                .setParameter("id", id);

			        int rowsUpdated = query2.executeUpdate();
			        System.out.println("entities Updated: " + rowsUpdated);
		
	}
*/

}
