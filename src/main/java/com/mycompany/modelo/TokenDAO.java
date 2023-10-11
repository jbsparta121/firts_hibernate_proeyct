package com.mycompany.modelo;

import static com.mycompany.modelo.HibernateUtil.cerrarSesion;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class TokenDAO {

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

    public boolean grabar(Token token) {
        boolean ok = false;
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            trns = session.beginTransaction();
            session.save(token);
            trns.commit();
            ok = true;
        } catch (HibernateException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return ok;
    }

    public Token consultarPorAprendiz(int idAprendiz) {
        Token token = null;
        try {
            iniciaOperacion();
            // Consultar el token por el ID del aprendiz
            token = (Token) sesion.createQuery("FROM Token WHERE aprendiz.id = :idAprendiz")
                    .setParameter("idAprendiz", idAprendiz)
                    .uniqueResult();
        } catch (HibernateException e) {
            manejaException(e);
        }
        return token;
    }

    public boolean actualizarPorAprendiz(int idAprendiz, int nuevoValor) {
        boolean actualizacionExitosa = false;

        try {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaUpdate<Token> update = builder.createCriteriaUpdate(Token.class);
            Root<Token> root = update.from(Token.class);

            // Actualiza el valor del token para el aprendiz especificado
            update.set(root.get("valor"), nuevoValor);
            update.where(builder.equal(root.get("aprendiz"), idAprendiz));

            int rowCount = sesion.createQuery(update).executeUpdate();

            // Si rowCount es mayor que 0, la actualización fue exitosa
            actualizacionExitosa = rowCount > 0;
            trns.commit();
        } catch (HibernateException e) {
            manejaException(e);
        } finally {
            sesion.close();
        }

        return actualizacionExitosa;
    }

    public boolean actualizar(Token token) {
        boolean ok = false;

        try {
            iniciaOperacion();
            sesion.update(token);
            trns.commit();
            ok = true;
        } catch (HibernateException e) {
            manejaException(e);
            ok = false;
        } finally {
            sesion.close();
        }

        return ok;
    }

}
