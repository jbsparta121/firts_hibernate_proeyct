/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controlador;

import com.mycompany.modelo.Aprendiz;
import com.mycompany.modelo.AprendizDAO;
import com.mycompany.modelo.Instructor;
import com.mycompany.modelo.InstructorDAO;
import com.mycompany.modelo.Token;
import com.mycompany.modelo.TokenDAO;
import com.mycompany.vista.FrmInstructor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import org.edisoncor.gui.textField.TextField;
import org.hibernate.HibernateException;

public class CntInstructor implements ActionListener {

    private final FrmInstructor frmInstructor;
    private Aprendiz aprendiz;
    private AprendizDAO aprendizDAO;
    private Instructor instructor;
    private InstructorDAO instructorDAO;
    private Token token;
    private TokenDAO tokenDAO;
    int valorAgregado = 0;

    public CntInstructor(FrmInstructor frmInstructor) {
        this.frmInstructor = frmInstructor;
        registrarControladores();
    }

    private void registrarControladores() {
        frmInstructor.getBtnConsultar().addActionListener(this);
        frmInstructor.getBtnLimpiar().addActionListener(this);
        frmInstructor.getBtnAgregarToken().addActionListener(this);
    }

    private void buscar() {
        Integer idAprendiz = Integer.valueOf(frmInstructor.getTxtIdAprendiz().getText());
        Integer idInstructor = Integer.valueOf(frmInstructor.getTxtInstructor().getText());
        aprendizDAO = new AprendizDAO();
        instructorDAO = new InstructorDAO();
        tokenDAO = new TokenDAO();
        aprendiz = aprendizDAO.consultar(idAprendiz);
        instructor = instructorDAO.consultar(idInstructor);
        token = tokenDAO.consultarPorAprendiz(idAprendiz);
        if (aprendiz != null) {
            frmInstructor.getTxtNombreInstructor().setText(instructor.getNombre());
            frmInstructor.getTxtNombre().setText(aprendiz.getNombre());
            frmInstructor.getTxtTitulacion().setText(aprendiz.getTitulacion());
            frmInstructor.getTxtCorreo().setText(aprendiz.getCorreo());
            frmInstructor.getTxtTokensTotal().setText(String.valueOf(token.getValor()));
        } else {
            JOptionPane.showMessageDialog(null, "Aprendiz no encontrado");
        }
    }

    public void obtenerToken() {
        int idInstructor = Integer.parseInt(frmInstructor.getTxtInstructor().getText());
        int idAprendiz = Integer.parseInt(frmInstructor.getTxtIdAprendiz().getText());
        aprendizDAO = new AprendizDAO();
        instructorDAO = new InstructorDAO();
        token = new Token();

        int numero = 0;
        int valor = 0;
        if (frmInstructor.getBtnToken200().isSelected()) {
            valor = 200;
        } else if (frmInstructor.getBtnToken500().isSelected()) {
            valor = 500;
        } else if (frmInstructor.getBtnToken1000().isSelected()) {
            valor = 1000;
        }
        Instructor instructor = instructorDAO.consultar(idInstructor);
        Aprendiz aprendiz = aprendizDAO.consultar(idAprendiz);
        Date fechaToken = new Date();
        token = new Token(numero, valor, instructor, aprendiz, fechaToken);
    }

    public void gestionarToken() {
        try {
            int idAprendiz = Integer.parseInt(frmInstructor.getTxtIdAprendiz().getText());

            if (frmInstructor.getBtnToken200().isSelected()) {
                valorAgregado += 200;
            }
            if (frmInstructor.getBtnToken500().isSelected()) {
                valorAgregado += 500;
            }
            if (frmInstructor.getBtnToken1000().isSelected()) {
                valorAgregado += 1000;
            }

            TokenDAO tokenDAO = new TokenDAO();

            // Consultar el token existente para el aprendiz actual
            Token tokenExistente = tokenDAO.consultarPorAprendiz(idAprendiz);

            if (tokenExistente != null) {
                // Si el token existe, suma el nuevo valor al valor existente
                int nuevoValor = tokenExistente.getValor() + valorAgregado;
                tokenExistente.setValor(nuevoValor);

                // Actualiza el token existente en la base de datos
                tokenDAO.actualizar(tokenExistente);

            } else {
                grabar();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Ingresa un ID de aprendiz válido.");
        } catch (HibernateException e) {
            JOptionPane.showMessageDialog(null, "Error al gestionar el token: " + e.getMessage());
        }
    }

    public boolean sumarValorAToken(Token tokenExistente, int valorAgregado) {
        if (tokenExistente != null) {
            int nuevoValor = tokenExistente.getValor() + valorAgregado;
            tokenExistente.setValor(nuevoValor);

            TokenDAO tokenDAO = new TokenDAO();
            return tokenDAO.actualizar(tokenExistente);
        }
        return false;
    }

    private void grabar() {
        tokenDAO = new TokenDAO();
        obtenerToken();
        if (tokenDAO.grabar(token)) {
            JOptionPane.showMessageDialog(null, "Token almacenado y aprendiz registrado");
        } else {
            JOptionPane.showMessageDialog(null, "Token no almacenado");
        }
    }

    private void limpiarCampos() {
        //frmInstructor.getTxtIdAprendiz().setText("");
        //frmInstructor.getTxtNombre().setText("");
        //frmInstructor.getTxtTitulacion().setText("");
        //frmInstructor.getTxtCorreo().setText("");
        //valorAgregado = 0; // Reiniciar el valorAgregado
        frmInstructor.dispose();
        FrmInstructor frmInstructor = new FrmInstructor();
        frmInstructor.setVisible(true);
    }

    private void limpiarRadioBotones() {
        frmInstructor.getButtonGroup1().clearSelection();
        valorAgregado = 0;
        //frmInstructor.getBtnToken1000().setAction(null);
        //frmInstructor.getBtnToken500().setAction(null);
        //frmInstructor.getBtnToken200().setAction(null);
    }

    private void Correo() {
        final String userName = "senatoken@gmail.com"; //same fromMail
        final String password = "krpm afov yuon hxfg";
        final String toEmail = aprendiz.getCorreo();

        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        //props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        // props.put("mail.smtp.ssl.enable", "true");
        // props.put("mail.smtp.socketFactory.port", "587"); //TLS Port

        //Session session = Session.getDefaultInstance(props);
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("senatoken@gmail.com", "krpm afov yuon hxfg");
            }
        });

        try {
            Token token = new Token();
            String nombreCompletoAprendiz = aprendiz.getNombre();
            String nombreCompletoInstructor = instructor.getNombre();
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(aprendiz.getCorreo(), true));
            message.setSubject("Recibimiento Token");
            message.setText(nombreCompletoAprendiz + " usted recibió " + valorAgregado + " Tokens. ¡Bien hecho!"
                    + "\nEl instructor que asignó el Token es " + nombreCompletoInstructor);
            Transport.send(message);
            JOptionPane.showMessageDialog(null, "Correo enviado");

        } catch (MessagingException me) {
            System.out.println("Exception: " + me);

        }
    }

    private void actualizarValor() {
        Integer idAprendiz = Integer.valueOf(frmInstructor.getTxtIdAprendiz().getText());
        tokenDAO = new TokenDAO();
        token = tokenDAO.consultarPorAprendiz(idAprendiz);
        frmInstructor.getTxtTokensTotal().setText(String.valueOf(token.getValor()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmInstructor.getBtnConsultar()) {
            buscar();
        } else if (e.getSource() == frmInstructor.getBtnAgregarToken()) {
            gestionarToken();
            JOptionPane.showMessageDialog(null, "Valor del token actualizado correctamente.");
            actualizarValor();
            Correo();
            limpiarRadioBotones();
        } else if (e.getSource() == frmInstructor.getBtnLimpiar()) {
            limpiarCampos();
        }
    }
}
