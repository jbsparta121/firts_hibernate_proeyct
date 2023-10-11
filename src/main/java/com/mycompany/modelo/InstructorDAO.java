/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelo;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class InstructorDAO {
    
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
    
    public Instructor consultar(int id) {
        Instructor instructor = null;
        try {
            iniciaOperacion();
            instructor = sesion.get(com.mycompany.modelo.Instructor.class, id);
        } catch (HibernateException e) {
            manejaException(e);
        } finally {
            sesion.close();
        }
        return instructor;
    }
}
