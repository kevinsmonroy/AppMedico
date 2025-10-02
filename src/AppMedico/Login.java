package AppMedico;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Login extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private final List<Usuario> usuarios;
    private final GestorCitas gestor;


    public Login(List<Usuario> usuarios, GestorCitas gestor) {
        this.usuarios = usuarios;
        this.gestor = gestor;

        setTitle("Login - Sistema Médico");
        setSize(420, 260);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Usuario
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Usuario (cédula):"), gbc);
        gbc.gridx = 1;
        txtUsuario = new JTextField(15);
        panel.add(txtUsuario, gbc);

        // Contraseña
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        txtContrasena = new JPasswordField(15);
        panel.add(txtContrasena, gbc);

        // Botones
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnLogin = new JButton("Ingresar");
        JButton btnRegistrar = new JButton("Registrar Médico");
        btnLogin.setPreferredSize(new Dimension(120, 30));
        btnRegistrar.setPreferredSize(new Dimension(160, 30));
        botones.add(btnLogin);
        botones.add(btnRegistrar);

        // Agregar todo
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(botones, BorderLayout.SOUTH);

        // Eventos
        btnLogin.addActionListener(e -> autenticar());
        btnRegistrar.addActionListener(e -> mostrarDialogoRegistro());
        txtContrasena.addActionListener(e -> autenticar());
    }

    private void autenticar() {
        String usuario = txtUsuario.getText().trim();
        String pass = new String(txtContrasena.getPassword());

        if (usuario.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese usuario y contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Usuario u : usuarios) {
            if (u instanceof Medico) {
                if (u.getCedula().equals(usuario) && u.getContrasena().equals(pass)) {
                    SwingUtilities.invokeLater(() -> new VentanaMedico((Medico) u, gestor).setVisible(true));
                    dispose();
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(this, "Credenciales inválidas.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Registro de médico con spinner para fecha
    private void mostrarDialogoRegistro() {
        JDialog dialog = new JDialog(this, "Registro de Médico", true);
        dialog.setSize(450, 450);
        dialog.setLocationRelativeTo(this);

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField tfCedula = new JTextField();
        JTextField tfNombre = new JTextField();
        JTextField tfApellido = new JTextField();
        JTextField tfTelefono = new JTextField();
        JSpinner spFecha = new JSpinner(new SpinnerDateModel());
        spFecha.setEditor(new JSpinner.DateEditor(spFecha, "yyyy-MM-dd"));
        JTextField tfCorreo = new JTextField();
        JTextField tfBarrio = new JTextField();
        JPasswordField pfContrasena = new JPasswordField();
        JComboBox<Especialidad> cbEspecialidad = new JComboBox<>(Especialidad.values());

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y; p.add(new JLabel("Cédula:"), gbc); gbc.gridx = 1; p.add(tfCedula, gbc);
        gbc.gridx = 0; gbc.gridy = ++y; p.add(new JLabel("Nombre:"), gbc); gbc.gridx = 1; p.add(tfNombre, gbc);
        gbc.gridx = 0; gbc.gridy = ++y; p.add(new JLabel("Apellido:"), gbc); gbc.gridx = 1; p.add(tfApellido, gbc);
        gbc.gridx = 0; gbc.gridy = ++y; p.add(new JLabel("Teléfono:"), gbc); gbc.gridx = 1; p.add(tfTelefono, gbc);
        gbc.gridx = 0; gbc.gridy = ++y; p.add(new JLabel("Fecha Nac:"), gbc); gbc.gridx = 1; p.add(spFecha, gbc);
        gbc.gridx = 0; gbc.gridy = ++y; p.add(new JLabel("Correo:"), gbc); gbc.gridx = 1; p.add(tfCorreo, gbc);
        gbc.gridx = 0; gbc.gridy = ++y; p.add(new JLabel("Barrio:"), gbc); gbc.gridx = 1; p.add(tfBarrio, gbc);
        gbc.gridx = 0; gbc.gridy = ++y; p.add(new JLabel("Contraseña:"), gbc); gbc.gridx = 1; p.add(pfContrasena, gbc);
        gbc.gridx = 0; gbc.gridy = ++y; p.add(new JLabel("Especialidad:"), gbc); gbc.gridx = 1; p.add(cbEspecialidad, gbc);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        botones.add(btnGuardar);
        botones.add(btnCancelar);

        dialog.setLayout(new BorderLayout());
        dialog.add(p, BorderLayout.CENTER);
        dialog.add(botones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(ev -> {
            String ced = tfCedula.getText().trim();
            String nombre = tfNombre.getText().trim();
            String apellido = tfApellido.getText().trim();
            String telefono = tfTelefono.getText().trim();
            String fechaNac = new java.text.SimpleDateFormat("yyyy-MM-dd").format(spFecha.getValue());
            String correo = tfCorreo.getText().trim();
            String barrio = tfBarrio.getText().trim();
            String contra = new String(pfContrasena.getPassword()).trim();
            Especialidad esp = (Especialidad) cbEspecialidad.getSelectedItem();

            if (ced.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty()
                    || correo.isEmpty() || barrio.isEmpty() || contra.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (Usuario u : usuarios) {
                if (u.getCedula().equals(ced)) {
                    JOptionPane.showMessageDialog(dialog, "Ya existe un usuario con esa cédula.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            Medico nuevo = new Medico(ced, nombre, apellido, telefono, fechaNac, correo, contra, barrio, esp);
            usuarios.add(nuevo);

            JOptionPane.showMessageDialog(this, "Médico registrado correctamente.", "Registro", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        btnCancelar.addActionListener(ev -> dialog.dispose());
        dialog.setVisible(true);
    }


}



