package com.bps.ejercicio.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bps.ejercicio.models.Cuenta;

@Repository
@Transactional
public class CuentaDaoImp implements CuentaDao {

    @PersistenceContext
    EntityManager entityManager;

    @SuppressWarnings("unchecked")
	@Override
    @Transactional
    public List<Cuenta> getCuentas() {
        String query = "FROM Cuenta";
        return entityManager.createQuery(query).getResultList();
    }
    
    @SuppressWarnings("unchecked")
	@Override
    @Transactional
    public Cuenta getCuentaNumero(Integer numero) {
 
        String query = "FROM Cuenta where numero = :numero";
        List<Cuenta> lista = entityManager.createQuery(query)
                .setParameter("numero", numero)
                .getResultList();
        if (lista.isEmpty()) {
            return null;
        }
        return lista.get(0);
        
    }

	@Override
	@Transactional
	public void editar(Cuenta cuenta) throws Exception {
		try {
			Query query2 = entityManager.createQuery("UPDATE Cuenta c SET c.tipo =:tipo,"
					+ " c.saldo = :saldo, c.estado= :estado  WHERE numero = :numero")
			      .setParameter("numero", cuenta.getNumero())
			      .setParameter("tipo", cuenta.getTipo())
			      .setParameter("saldo", cuenta.getSaldo())
			      .setParameter("estado", cuenta.getEstado());

	        int rowsUpdated = query2.executeUpdate();
	        System.out.println("entities Updated: " + rowsUpdated);
		} catch (Exception e) {
			throw new Exception("Error modificando cuenta" + e.getMessage());
		}
		
		
	}

	@Override
	@Transactional
	public Cuenta registrar(Cuenta cuenta) throws Exception {
		try {
			return entityManager.merge(cuenta);
		} catch (Exception e) {
			System.out.println("Error registrando clientes: " + e.getMessage());
		}
		return null;
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
