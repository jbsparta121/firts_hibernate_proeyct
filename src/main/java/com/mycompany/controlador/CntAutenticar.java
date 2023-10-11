/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controlador;

import com.mycompany.modelo.HibernateUtil;
import com.mycompany.vista.FrmAutenticar;
import com.mycompany.vista.FrmInstructor;
import com.mycompany.modelo.Aprendiz;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;

/**
 *
 * @author David
 */
public class CntAutenticar implements ActionListener {

    private FrmAutenticar frmAutenticar;
    private Aprendiz aprendiz;

    public CntAutenticar(FrmAutenticar frmAutenticar) {
        this.frmAutenticar = frmAutenticar;
        registrarControladores();
    }

    private void registrarControladores() {
        frmAutenticar.getBtnAutenticar().addActionListener(this);
        frmAutenticar.getChkContraseña().addActionListener(this);
    }

    public void conectar() {
        String usuario = frmAutenticar.getTxtUsuario().getText();
        String contraseña = frmAutenticar.getTxtContraseña().getText();

        if (HibernateUtil.getSessionFactory(usuario, contraseña) != null) {
            FrmInstructor frmInstructor = new FrmInstructor();
            frmAutenticar.dispose();
            frmInstructor.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Instructor no encontrado");
        }
    }

    public void mostrarCaracteres() {
        if (frmAutenticar.getChkContraseña().isSelected() == true) {
            frmAutenticar.getTxtContraseña().setEchoChar((char) 0);
        } else {
            frmAutenticar.getTxtContraseña().setEchoChar('•');
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frmAutenticar.getChkContraseña()) {
            mostrarCaracteres();
        } else if (e.getSource() == frmAutenticar.getBtnAutenticar()) {
            conectar();
        }
    }
}
