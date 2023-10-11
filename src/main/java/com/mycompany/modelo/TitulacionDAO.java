
package com.mycompany.modelo;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TitulacionDAO {
    private Session sesion;
    private Transaction trns;

    private void iniciaOperacion() {
        sesion = HibernateUtil.getSessionFactory().openSession();
        trns = sesion.beginTransaction();
    }

    private void manejaException(HibernateException e) {
        trns.rollback();
        throw new HibernateException("Error en la capa de datos." + e);
    }
    
    public Titulacion consultar(int id) {
        Titulacion titulacion = null;
        try {
            iniciaOperacion();
            titulacion = (Titulacion) sesion.get(Titulacion.class, id);
        } catch (HibernateException e) {
            manejaException(e);
        } finally {
            sesion.close();
        }
        return titulacion;
    }
}
