package AppMedico;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RegistroMedico {
    private ArrayList<Usuario> usuarios;
    private ArrayList<Especialidad> especialidades;

    public RegistroMedico(ArrayList<Usuario> usuarios, ArrayList<Especialidad> especialidades) {
        this.usuarios = usuarios;
        this.especialidades = especialidades;
    }

    public void mostrarVentana(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Registro de Médico", true);
        dialog.setSize(500, 600);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos
        JLabel lblCedula = new JLabel("Cédula:");
        JTextField tfCedula = new JTextField(20);

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField tfNombre = new JTextField(20);

        JLabel lblApellido = new JLabel("Apellido:");
        JTextField tfApellido = new JTextField(20);

        JLabel lblTelefono = new JLabel("Teléfono:");
        JTextField tfTelefono = new JTextField(20);

        JLabel lblFecha = new JLabel("Fecha de nacimiento:");
        JSpinner spFecha = new JSpinner(new SpinnerDateModel());
        spFecha.setEditor(new JSpinner.DateEditor(spFecha, "yyyy-MM-dd"));

        JLabel lblCorreo = new JLabel("Correo:");
        JTextField tfCorreo = new JTextField(20);

        JLabel lblBarrio = new JLabel("Barrio:");
        JTextField tfBarrio = new JTextField(20);

        JLabel lblContrasena = new JLabel("Contraseña:");
        JPasswordField pfContrasena = new JPasswordField(20);

        JLabel lblEspecialidad = new JLabel("Especialidad:");
        JComboBox<Especialidad> cbEspecialidad = new JComboBox<>();
        for (Especialidad e : especialidades) cbEspecialidad.addItem(e);

        // Posicionar componentes
        int fila = 0;
        gbc.gridx = 0; gbc.gridy = fila; panel.add(lblCedula, gbc);
        gbc.gridx = 1; panel.add(tfCedula, gbc); fila++;

        gbc.gridx = 0; gbc.gridy = fila; panel.add(lblNombre, gbc);
        gbc.gridx = 1; panel.add(tfNombre, gbc); fila++;

        gbc.gridx = 0; gbc.gridy = fila; panel.add(lblApellido, gbc);
        gbc.gridx = 1; panel.add(tfApellido, gbc); fila++;

        gbc.gridx = 0; gbc.gridy = fila; panel.add(lblTelefono, gbc);
        gbc.gridx = 1; panel.add(tfTelefono, gbc); fila++;

        gbc.gridx = 0; gbc.gridy = fila; panel.add(lblFecha, gbc);
        gbc.gridx = 1; panel.add(spFecha, gbc); fila++;

        gbc.gridx = 0; gbc.gridy = fila; panel.add(lblCorreo, gbc);
        gbc.gridx = 1; panel.add(tfCorreo, gbc); fila++;

        gbc.gridx = 0; gbc.gridy = fila; panel.add(lblBarrio, gbc);
        gbc.gridx = 1; panel.add(tfBarrio, gbc); fila++;

        gbc.gridx = 0; gbc.gridy = fila; panel.add(lblContrasena, gbc);
        gbc.gridx = 1; panel.add(pfContrasena, gbc); fila++;

        gbc.gridx = 0; gbc.gridy = fila; panel.add(lblEspecialidad, gbc);
        gbc.gridx = 1; panel.add(cbEspecialidad, gbc); fila++;

        // Botones
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);

        // Acción guardar
        btnGuardar.addActionListener(ev -> {
            String ced = tfCedula.getText().trim();
            String nombre = tfNombre.getText().trim();
            String apellido = tfApellido.getText().trim();
            String telefono = tfTelefono.getText().trim();
            String fechaNac = new java.text.SimpleDateFormat("yyyy-MM-dd").format(spFecha.getValue());
            String correo = tfCorreo.getText().trim();
            String barrio = tfBarrio.getText().trim();
            String contrasena = new String(pfContrasena.getPassword());
            Especialidad esp = (Especialidad) cbEspecialidad.getSelectedItem();

            if (ced.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() ||
                    correo.isEmpty() || barrio.isEmpty() || contrasena.isEmpty() || esp == null) {
                JOptionPane.showMessageDialog(dialog, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Medico nuevo = new Medico(ced, nombre, apellido, telefono, fechaNac, correo, barrio, contrasena, esp);
            usuarios.add(nuevo);

            JOptionPane.showMessageDialog(dialog, "Médico registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        btnCancelar.addActionListener(ev -> dialog.dispose());

        dialog.setVisible(true);
    }
}



